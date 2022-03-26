package com.kalacheng.commonview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.kalacheng.util.utils.DpUtil;

public class DynamicProgressView extends AppCompatTextView {
    private int mProgress;
    private int mWidth;
    private int mHeight;
    private int mCenter;
    private Paint mPaint1;
    private Paint mPaint2;

    public DynamicProgressView(Context context) {
        this(context, null);
    }

    public DynamicProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setDither(true);
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint1.setColor(Color.parseColor("#ffffff"));
        mPaint1.setStrokeWidth(DpUtil.dp2px(4));

        mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setDither(true);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setColor(Color.parseColor("#9B58FE"));
        mPaint2.setStrokeWidth(DpUtil.dp2px(4));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mCenter = mWidth / 2;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mCenter, mCenter, mWidth / 2 - DpUtil.dp2px(4), mPaint1);

        RectF rect2 = new RectF(mCenter - (mWidth / 2 - DpUtil.dp2px(4)),
                mCenter - (mWidth / 2 - DpUtil.dp2px(4)),
                mCenter + (mWidth / 2 - DpUtil.dp2px(4)),
                mCenter + (mWidth / 2 - DpUtil.dp2px(4)));
        canvas.drawArc(rect2, 270, 360 * mProgress / 100, false, mPaint2);
        super.onDraw(canvas);
    }

    public void setProgress(int progress) {
        if (progress > 100) {
            progress = 100;
        }
        if (progress < 0) {
            progress = 0;
        }
        mProgress = progress;
        invalidate();
    }
}
