package com.kalacheng.live.component.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseDialogFragment;

import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.live.R;
import com.kalacheng.live.component.adapter.LiveRoomTypeAdapter;
import com.kalacheng.util.utils.CommonCallback;
import com.klc.bean.LiveRoomTypeBean;

/**
 * Created by cxf on 2018/10/8.
 */

public class LiveRoomTypeDialogFragment extends BaseDialogFragment {

    private RecyclerView mRecyclerView;
    private LiveRoomTypeAdapter mAdapter;
    private CommonCallback<LiveRoomTypeBean> mCallback;


    @Override
    protected int getLayoutId() {
        return R.layout.dialog_live_room_type;
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
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        int checkedId = bundle.getInt(ARouterValueNameConfig.RoomType);
        mAdapter = new LiveRoomTypeAdapter(mContext, checkedId);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new LiveRoomTypeAdapter.OnItemClickListener() {
            @Override
            public void onClcik(LiveRoomTypeBean bean,int poistion) {
                mAdapter.setClick(poistion);
                mCallback.callback(bean);
            }
        });
    }



    public void setCallback(CommonCallback<LiveRoomTypeBean> callback) {
        mCallback = callback;
    }

    @Override
    public void onDestroy() {
        mCallback = null;
        super.onDestroy();
    }
}
