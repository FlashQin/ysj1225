package com.kalacheng.commonview.beauty;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.kalacheng.base.base.BaseViewHolder;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.view.TextSeekBar;

import com.kalacheng.tiui.R;
import com.xuantongyun.livecloud.protocol.PulicUtils;


/**
 * Created by cxf on 2018/6/22.
 * 默认美颜
 */

public class DefaultBeautyViewHolder extends BaseViewHolder implements View.OnClickListener, BeautyViewHolder {
    private VisibleListener mVisibleListener;
    private boolean mShowed;
    private int mBeautyLevel = 0;//美颜
    private int mBrightLevel = 0;//亮度

    private TextSeekBar.OnSeekChangeListener onSeekChangeListener = new TextSeekBar.OnSeekChangeListener() {
        @Override
        public void onProgressChanged(View view, int progress) {
            int i = view.getId();
            if (i == R.id.seekBeauty) {
                SpUtil.getInstance().put(SpUtil.BEAUTY, progress);
               mBeautyLevel = progress;
                PulicUtils.getInstance().setBeautyFaceStatus(true, mBeautyLevel / 100f, mBrightLevel / 100f);
            } else if (i == R.id.seekBright) {
                SpUtil.getInstance().put(SpUtil.BRIGHT, progress);
                mBrightLevel = progress;
                PulicUtils.getInstance().setBeautyFaceStatus(true, mBeautyLevel / 100f, mBrightLevel / 100f);
            }
        }
    };

    public DefaultBeautyViewHolder(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_beauty_default;
    }

    @Override
    public void init() {
        findViewById(R.id.btn_hide).setOnClickListener(this);
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

        mBeautyLevel = (int) SpUtil.getInstance().getSharedPreference(SpUtil.BEAUTY, 50);
        mBrightLevel = (int) SpUtil.getInstance().getSharedPreference(SpUtil.BRIGHT, 50);
        TextSeekBar seekBeauty = ((TextSeekBar) findViewById(R.id.seekBeauty));
        TextSeekBar seekBright = ((TextSeekBar) findViewById(R.id.seekBright));
        seekBeauty.setProgress(mBeautyLevel);
        seekBright.setProgress(mBrightLevel);
        seekBeauty.setOnSeekChangeListener(onSeekChangeListener);
        seekBright.setOnSeekChangeListener(onSeekChangeListener);
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
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_hide) {
            hide();
        }
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
