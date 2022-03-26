package com.kalacheng.shortvideo.utils;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleviewSlideHelper implements RecyclerView.OnItemTouchListener {
    private float mDownX;
    private float mDownY;
    private float mTouchSlop;
    Callback callback;

    public RecycleviewSlideHelper(Context context, Callback callback) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.callback = callback;
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent event) {
        boolean intercept = false;
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = x;
                mDownY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - mDownX);
                float dy = Math.abs(y - mDownY);
                if (dx > mTouchSlop && dx * 0.5f > dy) {
                    if (mDownX - x > 0) {
                        intercept = true;
                        callback.onLeftScroll();
                    }
                }
                break;
            default:
                break;
        }
        return intercept;

    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {


    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    /**
     * 左滑菜单Callback
     */
    public interface Callback {

        void onLeftScroll();

    }
}
