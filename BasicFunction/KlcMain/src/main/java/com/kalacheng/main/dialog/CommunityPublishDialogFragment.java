package com.kalacheng.main.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kalacheng.main.R;
import com.kalacheng.base.base.BaseDialogFragment;

/**
 * 社区动态发布弹框
 */
public class CommunityPublishDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    private ChooseCallback mCallback;

    public CommunityPublishDialogFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_community_publish;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog;
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
        mRootView.findViewById(R.id.btn_pic).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_video).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_close).setOnClickListener(this);
    }

    public void setChooseCallback(ChooseCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onClick(View v) {
        dismissAllowingStateLoss();
        int i = v.getId();
        if (i == R.id.btn_close) {
        } else if (i == R.id.btn_video) {
            if (mCallback != null) {
                mCallback.onVideoClick();
            }
        } else if (i == R.id.btn_pic) {
            if (mCallback != null) {
                mCallback.onPicClick();
            }
        }
    }


    public interface ChooseCallback {

        void onVideoClick();

        void onPicClick();
    }
}