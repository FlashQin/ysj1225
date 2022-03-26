package com.kalacheng.commonview.listener;

public interface OnPictureChooseItemClickListener<T> {
    void onItemClick(T bean, int position);

    void onItemSelect(T bean, int position);

    void onItemDelete(T bean, int position);
}
