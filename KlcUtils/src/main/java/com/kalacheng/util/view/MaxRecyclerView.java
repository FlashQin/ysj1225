package com.kalacheng.util.view;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * sgf
 * 最大化的RecyclerView，嵌套于ScrollView之中使用,处理多个RecyclerView显示不全的问题
 */
public class MaxRecyclerView extends RecyclerView {

    public MaxRecyclerView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    public MaxRecyclerView(android.content.Context context) {
        super(context);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

