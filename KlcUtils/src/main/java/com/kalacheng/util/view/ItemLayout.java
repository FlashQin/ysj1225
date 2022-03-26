package com.kalacheng.util.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.kalacheng.util.R;


public class ItemLayout extends RelativeLayout {
    private int widthRatio;
    private int heightRatio;

    public ItemLayout(Context context) {
        super(context);
    }

    public ItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        getTypedArray(context, attrs);
    }

    public ItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getTypedArray(context, attrs);
    }

    private void getTypedArray(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ItemLayout);
        widthRatio = a.getInteger(R.styleable.ItemLayout_width_ratio, 0);
        heightRatio = a.getInteger(R.styleable.ItemLayout_height_ratio, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthRatio != 0 && heightRatio != 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (widthSize * heightRatio / widthRatio), MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /***
     * 设置宽高比
     */
    public void setRatio(int widthRatio, int heightRatio) {
        this.widthRatio = widthRatio;
        this.heightRatio = heightRatio;
        invalidate();
    }
}

