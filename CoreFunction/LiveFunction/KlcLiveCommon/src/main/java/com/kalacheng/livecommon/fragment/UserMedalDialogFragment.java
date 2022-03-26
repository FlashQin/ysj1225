package com.kalacheng.livecommon.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.kalacheng.libuser.model.ApiElasticFrame;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.DpUtil;

public class UserMedalDialogFragment extends BaseDialogFragment {
    private ApiElasticFrame apiElasticFrame;
    @Override
    protected int getLayoutId() {
        return R.layout.user_medal_dialog;
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
        window.setWindowAnimations(com.kalacheng.livecommon.R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.y = DpUtil.dp2px(70);
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        apiElasticFrame = getArguments().getParcelable("ApiElasticFrame");



    }
}
