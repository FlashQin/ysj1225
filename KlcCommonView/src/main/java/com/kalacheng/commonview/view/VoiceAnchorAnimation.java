package com.kalacheng.commonview.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.kalacheng.commonview.R;
import com.kalacheng.util.utils.DpUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VoiceAnchorAnimation extends RelativeLayout {
    private float radius;
    private float strokeWidth;
    public int strokeColor;
    private List<CircleView> mViewList;
    private AnimatorSet animatorSet;
    public boolean isRunning = false;
    public int radiusWidth = 32;
    public int sex = 1;
    public int mun = 6;
    Context mContext;

    public VoiceAnchorAnimation(Context context) {
        this(context, null);
    }

    public VoiceAnchorAnimation(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoiceAnchorAnimation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WaterView);
        radius = array.getDimension(R.styleable.WaterView_waterradius, DpUtil.dp2px(radiusWidth));
        strokeWidth = array.getDimension(R.styleable.WaterView_waterstrokeWidth, 5);
        strokeColor = array.getColor(R.styleable.WaterView_waterstrokeColor, context.getResources().getColor(R.color.bg_6E78D9));
//        sex = array.getInteger(R.styleable.WaterView_watersexg,1);
        array.recycle();

        initView();
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int color) {
        this.strokeColor = color;

    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public int getRadius() {
        return radiusWidth;
    }

    public void setRadius(int radiusWidth) {
        this.radius = radiusWidth;
    }

    public void initView() {
        mViewList = new ArrayList<>();
        animatorSet = new AnimatorSet();
        List<Animator> animationList = new ArrayList<>();
        LayoutParams lp = new LayoutParams((int) radius, (int) radius);
        lp.addRule(CENTER_IN_PARENT);

        int scaleFlag = 5;//缩放系数
        int animTime = 3500;
        int perTime = 3500 / 6;
        for (int i = 0; i < mun; i++) {
            CircleView myCircleView = new CircleView(this);
            myCircleView.setColor(sex);
            addView(myCircleView, lp);
            mViewList.add(myCircleView);

            //设置x缩放动画
            ObjectAnimator xScaleAnim = ObjectAnimator.ofFloat(myCircleView, "ScaleX", 1, scaleFlag);
            xScaleAnim.setDuration(animTime);
            xScaleAnim.setRepeatCount(ValueAnimator.INFINITE);
            xScaleAnim.setRepeatMode(ValueAnimator.RESTART);
            xScaleAnim.setStartDelay(perTime * i);
            //设置y缩放动画
            ObjectAnimator yScaleAnim = ObjectAnimator.ofFloat(myCircleView, "ScaleY", 1, scaleFlag);
            yScaleAnim.setDuration(animTime);
            yScaleAnim.setRepeatCount(ValueAnimator.INFINITE);
            yScaleAnim.setRepeatMode(ValueAnimator.RESTART);
            yScaleAnim.setStartDelay(perTime * i);
            //设置透明度变化
            ObjectAnimator aScaleAnim = ObjectAnimator.ofFloat(myCircleView, "Alpha", 1f, 0.3f);
            aScaleAnim.setDuration(animTime);
            aScaleAnim.setRepeatMode(ValueAnimator.RESTART);
            aScaleAnim.setRepeatCount(ValueAnimator.INFINITE);
            aScaleAnim.setStartDelay(perTime * i);

            if (i == 5) {
                aScaleAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        if (isRunning) {
                            isRunning = false;
                        } else {
                            //倒叙一下， 从外往里面停止
                            Collections.reverse(mViewList);
                            for (CircleView myCircleView : mViewList) {
                                myCircleView.setVisibility(INVISIBLE);
                            }
//                            animatorSet.cancel();
                        }
                    }
                });
            }

            animationList.add(xScaleAnim);
            animationList.add(yScaleAnim);
            animationList.add(aScaleAnim);
        }

        animatorSet.setDuration(animTime);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(animationList);
    }

    public boolean isRunning() {
        return isRunning;
    }


    /**
     * 开始动画
     */

    public void startAnim() {
        setVisibility(VISIBLE);
        if (!isRunning) {
            isRunning = true;
            for (CircleView myCircleView : mViewList) {
                myCircleView.setVisibility(VISIBLE);
                myCircleView.setColor(sex);
            }
            if (!animatorSet.isRunning()) {
                animatorSet.start();
            }
        }
    }

    public void stopAnim() {
        setVisibility(INVISIBLE);
        if (isRunning) {
            isRunning = false;
//            //倒叙一下， 从外往里面停止
//            Collections.reverse(mViewList);
//            for (CircleView myCircleView : mViewList) {
//                myCircleView.setVisibility(INVISIBLE);
//            }
//            animatorSet.end();
        }

    }

    public void clean() {
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        mViewList.clear();
    }
}
