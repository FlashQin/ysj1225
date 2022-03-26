package com.kalacheng.util.listener;

/**
 * Created by cxf on 2017/8/9.
 * RecyclerView的Adapter点击事件
 */

public interface OnBeanCallback<T> {
    void onClick(T bean);
}
