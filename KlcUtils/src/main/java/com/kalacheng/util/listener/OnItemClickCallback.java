package com.kalacheng.util.listener;

import android.view.View;

public interface OnItemClickCallback<T> {
    void onClick(View view, T item);
}
