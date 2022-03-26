package com.kalacheng.commonview.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.commonview.R;
import com.kalacheng.util.utils.ConfigUtil;


/**
 * Created by hgy on 2018/9/29.
 * <p>
 * 首页底部 选择照片 小视频 视频直播 语音
 */

public class MainStartDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    private MainStartChooseCallback mCallback;

    public MainStartDialogFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_main_start;
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
        mRootView.findViewById(R.id.btn_live).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_voicelive).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_close).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_shot).setOnClickListener(this);

        if (ConfigUtil.getBoolValue(R.bool.isPublish)){
            mRootView.findViewById(R.id.btn_shot).setVisibility(View.VISIBLE);
        }

        if (ConfigUtil.getBoolValue(R.bool.isVideo)) {
            mRootView.findViewById(R.id.btn_live).setVisibility(View.GONE);
            mRootView.findViewById(R.id.btn_voicelive).setVisibility(View.GONE);
        } else {
            if (!ConfigUtil.getBoolValue(R.bool.containShortVideo)) {
                mRootView.findViewById(R.id.btn_pic).setVisibility(View.GONE);
                mRootView.findViewById(R.id.btn_video).setVisibility(View.GONE);
            }
            if (!ConfigUtil.getBoolValue(R.bool.containLive)) {
                mRootView.findViewById(R.id.btn_live).setVisibility(View.GONE);
            }
            if (!ConfigUtil.getBoolValue(R.bool.containVoice)) {
                mRootView.findViewById(R.id.btn_voicelive).setVisibility(View.GONE);
            }
        }
    }

    public void setMainStartChooseCallback(MainStartChooseCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onClick(View v) {
        dismissAllowingStateLoss();
        int i = v.getId();
        if (i == R.id.btn_close) {
        } else if (i == R.id.btn_live) {
            if (mCallback != null) {
                mCallback.onLiveClick();
            }
        } else if (i == R.id.btn_video) {
            if (mCallback != null) {
                mCallback.onVideoClick();
            }
        } else if (i == R.id.btn_pic) {
            if (mCallback != null) {
                mCallback.onPicClick();
            }
        } else if (i == R.id.btn_voicelive) {
            if (mCallback != null) {
                mCallback.onVoiceClick();
            }
        }else if (i == R.id.btn_shot){
            if (mCallback != null) {
                mCallback.onPublishClick();
            }
        }
    }


    public interface MainStartChooseCallback {
        void onLiveClick();

        void onVideoClick();

        void onVoiceClick();

        void onPicClick();

        void onPublishClick();
    }
}
