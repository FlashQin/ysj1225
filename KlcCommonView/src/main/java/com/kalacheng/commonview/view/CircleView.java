package com.kalacheng.commonview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.kalacheng.commonview.R;
import com.kalacheng.util.utils.ConfigUtil;

public class CircleView extends View {
    private Paint mPaint;
    private VoiceAnchorAnimation mVoiceAnchorAnimation;
    private int color;

    public int getColor() {
        return color;
    }

    public CircleView(VoiceAnchorAnimation mVoiceAnchorAnimation) {
        this(mVoiceAnchorAnimation.getContext(), null);
        this.mVoiceAnchorAnimation = mVoiceAnchorAnimation;
        setVisibility(INVISIBLE);//默认不可见
        initPaint();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }


    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        if (ConfigUtil.getBoolValue(R.bool.sexNormal)) {
            if (color == 1) {
                mPaint.setColor(Color.parseColor("#3568de"));
            } else {
                mPaint.setColor(Color.parseColor("#ff6e70"));
            }
        } else {
            if (color == 3) {
                mPaint.setColor(getResources().getColor(R.color.color_FF859E));
            } else if (color == 4) {
                mPaint.setColor(getResources().getColor(R.color.color_AAAAAA));
            } else if (color == 1) {
                mPaint.setColor(getResources().getColor(R.color.color_72A3FF));
            } else {
                mPaint.setColor(getResources().getColor(R.color.color_9D9EFF));
            }
        }
        mPaint.setStrokeWidth(mVoiceAnchorAnimation.getStrokeWidth());
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置半径
        int radius = Math.min(canvas.getHeight() / 2, canvas.getWidth() / 2);
        //中心点位置
        canvas.drawCircle(radius, radius, radius - mVoiceAnchorAnimation.getStrokeWidth(), mPaint);
    }

    public void setColor(int color) {
        this.color = color;
        initPaint();
    }
}
