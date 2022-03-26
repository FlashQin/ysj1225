package com.kalacheng.commonview.beauty;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.kalacheng.base.base.BaseViewHolder;
import com.kalacheng.commonview.R;
import com.kalacheng.tiui.view.TiBeautyView;
import com.kalacheng.util.utils.ToastUtil;
import com.xuantongyun.livecloud.config.TTTConfigUtils;

import cn.tillusory.sdk.TiSDKManager;

public class LiveBeautyComponent extends BaseViewHolder implements View.OnClickListener, BeautyViewHolder {

    private VisibleListener mVisibleListener;
    private boolean mShowed;

    public LiveBeautyComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_beauty;
    }

    @Override
    public void init() {

        TiBeautyView tiBeautyTrimView = (TiBeautyView) findViewById(R.id.tiBeautyTrimView);
        tiBeautyTrimView.init(TiSDKManager.getInstance());
        findViewById(R.id.btn_hide).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_hide) {
            hide();
        }
    }

    @Override
    public void show() {
        if (mVisibleListener != null) {
            mVisibleListener.onVisibleChanged(true);
        }
        if (mParentView != null && mContentView != null) {
            ViewParent parent = mContentView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mContentView);
            }
            mParentView.addView(mContentView);
        }
        mShowed = true;
        if (TTTConfigUtils.getInstance().beautyKeyOverdue()) {
            ToastUtil.show("美颜已过期");
        }
    }

    @Override
    public void hide() {
        removeFromParent();
        if (mVisibleListener != null) {
            mVisibleListener.onVisibleChanged(false);
        }
        mShowed = false;
    }

    @Override
    public boolean isShowed() {
        return mShowed;
    }


    @Override
    public void release() {
        mVisibleListener = null;
    }


    @Override
    public void setVisibleListener(VisibleListener visibleListener) {
        mVisibleListener = visibleListener;
    }
}
