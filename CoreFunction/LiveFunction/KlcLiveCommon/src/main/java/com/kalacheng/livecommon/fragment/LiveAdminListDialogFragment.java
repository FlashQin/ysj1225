package com.kalacheng.livecommon.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.baseLive.httpApi.HttpApiPublicLive;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.ApiUsersLiveManager;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.LiveAdminListAdpater;
import com.kalacheng.util.utils.DpUtil;

import java.util.List;

public class LiveAdminListDialogFragment extends BaseDialogFragment {
    private long mAnchorId;

    private long mLiveType;//直播类型（语音和视频）

    private  LiveAdminListAdpater adminListAdpater;
    private List<ApiUsersLiveManager> mList;
    @Override
    protected int getLayoutId() {
        return R.layout.view_live_admin_list;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height =(DpUtil.getScreenHeight()/2);
        params.gravity = Gravity.BOTTOM;
//        params.y = DpUtil.dp2px(230);
        window.setAttributes(params);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAnchorId =getArguments().getLong("AnchorId");
        mLiveType = getArguments().getLong(ARouterValueNameConfig.Livetype);


        RecyclerView recyclerView =mRootView.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adminListAdpater = new LiveAdminListAdpater(mContext);
        recyclerView.setAdapter(adminListAdpater);
        adminListAdpater.setLiveAdminListCallBack(new LiveAdminListAdpater.LiveAdminListCallBack() {
            @Override
            public void onClick(int poistion) {
                HttpApiPublicLive.cancelLivemanager((int) mLiveType, mList.get(poistion).uid, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code==1){
                            getAdminList();
                        }else {
                            ToastUtil.show(msg);
                        }
                    }
                });
            }
        });

        mRootView.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        getAdminList();
    }

    public void getAdminList(){
        HttpApiPublicLive.getLiveManagerList(mAnchorId, (int) mLiveType, new HttpApiCallBackArr<ApiUsersLiveManager>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiUsersLiveManager> retModel) {
                if (code==1){
                    mList = retModel;
                    adminListAdpater.getData(retModel);
                }else {
                    ToastUtil.show(msg);
                }
            }
        });
    }
}
