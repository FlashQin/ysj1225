package com.kalacheng.live.component.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.kalacheng.util.utils.DpUtil;

/**
 * @author: Administrator
 * @date: 2020/8/7
 * @info:
 */
@SuppressLint("AppCompatCustomView")
public class SlideImageView extends ImageView {

    private static final String TAG = "MoveTextView";

    private int lastX = 0;
    private int lastY = 0;
    private int left, top, right, bottom;
    private boolean isAnchor; // 如果是主播 才可以拖动

    private static final int screenWidth = DpUtil.getScreenWidth();
    private static final int screenHeight = DpUtil.getScreenHeight() - DpUtil.getStatusHeight();//屏幕高度-状态栏

    private slideListener listener;

    public SlideImageView(Context context) {
        super(context);
    }

    public SlideImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setListener(slideListener listener){
        this.listener = listener;
    }

    public void isAnchor(boolean isAnchor){
        this.isAnchor = isAnchor;
    }

    public void setPosition(int left, int top, int right, int bottom){
//        this.left = left;
//        this.top = top;
//        this.right = right;
//        this.bottom = bottom;
        layout(left, top, right, bottom);
    }

//    public SlideImageView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;

                int left = getLeft() + dx;
                int top = getTop() + dy;
                int right = getRight() + dx;
                int bottom = getBottom() + dy;
                if (left < 0) {
                    left = 0;
                    right = left + getWidth();
                }
                if (right > screenWidth) {
                    right = screenWidth;
                    left = right - getWidth();
                }
                if (top < 0) {
                    top = 0;
                    bottom = top + getHeight();
                }
                if (bottom > screenHeight) {
                    bottom = screenHeight;
                    top = bottom - getHeight();
                }
                if (isAnchor){
                    layout(left, top, right, bottom);
                }
                this.left = left;
                this.top = top;
                this.right = right;
                this.bottom = bottom;
                if (null != listener){
                    listener.positionListener(left, top, right, bottom);
                }
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }

    public interface slideListener{
        void positionListener(int l, int t, int r, int b);
    }

}
