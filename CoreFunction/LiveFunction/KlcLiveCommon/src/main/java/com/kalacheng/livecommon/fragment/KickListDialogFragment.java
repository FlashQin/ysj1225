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

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.baseLive.httpApi.HttpApiPublicLive;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.ApiKick;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.KickListAdpater;
import com.kalacheng.util.utils.DpUtil;

import java.util.List;

//踢人列表
public class KickListDialogFragment extends BaseDialogFragment {

    private long mAnchorId;
    private long mLiveType;

    private KickListAdpater adminListAdpater;
    private List<ApiKick> mList;

    @Override
    protected int getLayoutId() {
        return R.layout.kick_list;
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
        params.height = (DpUtil.getScreenHeight() / 2);
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mAnchorId = getArguments().getLong("AnchorId");
        mLiveType = getArguments().getLong(ARouterValueNameConfig.Livetype);


        RecyclerView kicklist_rec = mRootView.findViewById(R.id.kicklist_rec);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        kicklist_rec.setLayoutManager(manager);
        adminListAdpater = new KickListAdpater(mContext);
        kicklist_rec.setAdapter(adminListAdpater);
        adminListAdpater.setKickListViewCallBack(new KickListAdpater.KickListViewCallBack() {
            @Override
            public void onClick(int poistion) {
                HttpApiPublicLive.delKick(mAnchorId, (int) mLiveType, mList.get(poistion).uid, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            getAdminList();
                        } else {
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

    public void getAdminList() {
        HttpApiPublicLive.getKickList(mAnchorId, (int) mLiveType, new HttpApiCallBackArr<ApiKick>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiKick> retModel) {
                if (code == 1) {
                    mList = retModel;
                    adminListAdpater.getData(retModel);

                }
            }
        });
    }
}
