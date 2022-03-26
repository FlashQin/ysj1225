package com.kalacheng.livecommon.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.livecommon.R;

import com.kalacheng.commonview.beauty.BeautyViewHolder;
import com.kalacheng.commonview.beauty.DefaultBeautyViewHolder;

/*
 * 美颜
 * */
public class BeautyDialogFragment extends BaseDialogFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.dialog_beauty;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return false;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DefaultBeautyViewHolder defaultBeautyViewHolder = new DefaultBeautyViewHolder(mContext, (RelativeLayout) mRootView.findViewById(R.id.rl_beauty));
        defaultBeautyViewHolder.show();
        defaultBeautyViewHolder.setVisibleListener(new BeautyViewHolder.VisibleListener() {
            @Override
            public void onVisibleChanged(boolean visible) {
                dismiss();
            }
        });
    }
}
