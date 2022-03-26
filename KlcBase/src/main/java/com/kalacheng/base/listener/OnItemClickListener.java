package com.kalacheng.base.listener;

/**
 * Created by hgy on 2019/12/29.
 * RecyclerView的Adapter点击事件
 */

public interface OnItemClickListener<T> {
    void onItemClick(int position, T bean);
}
