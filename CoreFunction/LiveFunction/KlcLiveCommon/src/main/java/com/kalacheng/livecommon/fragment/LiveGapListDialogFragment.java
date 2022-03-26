package com.kalacheng.livecommon.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.baseLive.httpApi.HttpApiPublicLive;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.ApiShutUp;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.LiveGapListAdapter;
import com.kalacheng.util.utils.DpUtil;

import java.util.List;

public class LiveGapListDialogFragment extends BaseDialogFragment {

    private LiveGapListAdapter gapListAdapter;
    private List<ApiShutUp> mList;
    private long mLiveType;
    private long AnchorId;

    @Override
    protected int getLayoutId() {
        return R.layout.view_live_gap_list;
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
        params.height = (DpUtil.getScreenHeight()/2);;
        params.gravity = Gravity.BOTTOM;
//        params.y = DpUtil.dp2px(60);
        window.setAttributes(params);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        mLiveType =  bundle.getLong(ARouterValueNameConfig.Livetype);
        AnchorId = bundle.getLong("AnchorId");
        RecyclerView recyclerView = mRootView.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);
        gapListAdapter = new LiveGapListAdapter(mContext);
        recyclerView.setAdapter(gapListAdapter);
        gapListAdapter.setLiveGapListCallBack(new LiveGapListAdapter.LiveGapListCallBack() {
            @Override
            public void onClick(int poistion) {
                HttpApiPublicLive.addShutup(AnchorId, (int) mLiveType, mList.get(poistion).uid, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code==1){
                            getLiveGap();
                        }else {
                            ToastUtil.show(msg);
                        }
                    }
                });
            }
        });
        getLiveGap();

        ImageView btn_back = mRootView.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void getLiveGap() {
        HttpApiPublicLive.shutupList((int)mLiveType,new HttpApiCallBackArr<ApiShutUp>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiShutUp> retModel) {
                if (code == 1) {
                    mList = retModel;
                    gapListAdapter.getData(retModel);
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }
}
