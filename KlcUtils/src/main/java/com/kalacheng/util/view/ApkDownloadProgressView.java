package com.kalacheng.util.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class ApkDownloadProgressView extends AppCompatTextView {
    private int mProgress;
    private int mWidth;
    private int mHeight;
    private Paint mPaint1;

    public ApkDownloadProgressView(Context context) {
        this(context, null);
    }

    public ApkDownloadProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ApkDownloadProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setDither(true);
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint1.setColor(Color.parseColor("#A570FE"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, mWidth * (mProgress / 100f), mHeight, mPaint1);
        super.onDraw(canvas);
    }


    public void setProgress(int progress) {
        if (progress > 100) {
            progress = 100;
        }
        if (progress < 0) {
            progress = 0;
        }
        setText(progress + "%");
        mProgress = progress;
        invalidate();
    }
}