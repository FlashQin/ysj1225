package com.kalacheng.commonview.music.listener;

public interface OnMusicChooseItemClickListener<T> {
    void onItemClick(T bean, int position);

    void onItemSelect(T bean, int position);

    void onItemDelete(T bean, int position);
}
