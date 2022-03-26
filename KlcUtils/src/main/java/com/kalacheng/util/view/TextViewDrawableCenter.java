package com.kalacheng.util.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * <com.yy.view.YYTextViewDrawableCenter
 * android:layout_width="match_parent"
 * android:layout_height="200dp"
 * android:drawableLeft="@mipmap/icon_bluetooth"
 * android:drawablePadding="10dp"
 * android:gravity="center_vertical"
 * android:text="大家好"
 * android:textSize="20dp" />
 * <p>
 * TextView的drawableLeft与文本一起居中
 * <p>
 * Created by Administrator on 2017/3/17.
 */

public class TextViewDrawableCenter extends TextView {

    public TextViewDrawableCenter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TextViewDrawableCenter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewDrawableCenter(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        Drawable drawableLeft = drawables[0];
        if (drawableLeft != null) {
            float textWidth = getPaint().measureText(getText().toString());
            int drawablePadding = getCompoundDrawablePadding();
            int drawableWidth = drawableLeft.getIntrinsicWidth();
            float bodyWidth = textWidth + drawableWidth + drawablePadding;
            canvas.translate((getWidth() - bodyWidth) / 2, 0);
        }
        super.onDraw(canvas);
    }
}