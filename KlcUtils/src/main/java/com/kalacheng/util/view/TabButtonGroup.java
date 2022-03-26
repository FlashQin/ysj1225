package com.kalacheng.util.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxf on 2018/9/22.
 */

public class TabButtonGroup extends LinearLayout implements View.OnClickListener {

    private List<TabButton> mList;
    private int mCurPosition;
    private ValueAnimator mAnimator;
    private View mAnimView;
    private Runnable mRunnable;
    private TabButtonClickListener listener;
    private TabButtonClickListener repeatListener;

    public TabButtonGroup(Context context) {
        this(context, null);
    }

    public TabButtonGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabButtonGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mList = new ArrayList<>();
        mCurPosition = 0;
        mAnimator = ValueAnimator.ofFloat(0.9f, 1.1f, 1f);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                if (mAnimView != null) {
                    mAnimView.setScaleX(v);
                    mAnimView.setScaleY(v);
                }
            }
        });
        mAnimator.setDuration(300);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimView = null;
            }
        });
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (null != listener) {
                    listener.onTabButtonClick(mCurPosition);
                }
            }
        };
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0, count = getChildCount(); i < count; i++) {
            View v = getChildAt(i);
            v.setTag(i);
            v.setOnClickListener(this);
            mList.add((TabButton) v);
        }
    }

    @Override
    public void onClick(View v) {
//        if (CheckDoubleClick.isFastDoubleClick()) {
//            return;
//        }
        Object tag = v.getTag();
        if (tag != null) {
            int position = (int) tag;
            if (position == mCurPosition) {
                if (repeatListener != null) {
                    repeatListener.onTabButtonClick(position);
                }
                return;
            }
            mList.get(mCurPosition).setChecked(false);
            TabButton tbn = mList.get(position);
            tbn.setChecked(true);
            mCurPosition = position;
            mAnimView = tbn;
            mAnimator.start();
            postDelayed(mRunnable, 150);
        }
    }

    public void cancelAnim() {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
    }

    public interface TabButtonClickListener {
        void onTabButtonClick(int position);
    }

    public void setTabButtonClickListener(TabButtonClickListener listener) {
        this.listener = listener;
    }

    /**
     * 选中后再次点击事件
     */
    public void setRepeatClickListener(TabButtonClickListener listener) {
        this.repeatListener = listener;
    }
}
