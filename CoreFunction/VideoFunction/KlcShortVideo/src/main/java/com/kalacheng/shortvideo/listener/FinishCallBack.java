package com.kalacheng.shortvideo.listener;


import com.kalacheng.libuser.model.ApiShortVideoDto;

public interface FinishCallBack {

    /**
     * 回调 关闭 外层 父Activity
     */
    void isFinish();

    /**
     * 对应删除 父Activity 列表对应 item
     * @param appShortVideo
     * @param position
     */
    void deleteList(ApiShortVideoDto appShortVideo, int position);
}
