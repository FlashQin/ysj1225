package com.kalacheng.commonview.beauty;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.kalacheng.commonview.R;
import com.kalacheng.base.base.BaseViewHolder;

public class BaBaBeautyComponent extends BaseViewHolder implements BeautyViewHolder, View.OnClickListener {
    private VisibleListener mVisibleListener;
    private boolean mShowed;


    public BaBaBeautyComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_ba_ba_beauty;
    }

    @Override
    protected void init() {
        findViewById(R.id.btn_hide).setOnClickListener(this);

//        BaBaBeautyView baBaBeautyView = (BaBaBeautyView) findViewById(R.id.babaBeautyView);
//        baBaBeautyView.init();
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_hide) {
            hide();
        }
    }
}
