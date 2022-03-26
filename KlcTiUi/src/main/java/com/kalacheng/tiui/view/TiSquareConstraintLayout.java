package com.kalacheng.tiui.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Created by Anko on 2018/5/24.
 * Copyright (c) 2018-2019 鑫颜科技 - tillusory.cn. All rights reserved.
 */
public class TiSquareConstraintLayout extends ConstraintLayout {
    public TiSquareConstraintLayout(Context context) {
        super(context);
    }

    public TiSquareConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TiSquareConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
