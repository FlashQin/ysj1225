package com.kalacheng.dynamiccircle.listener;

import com.kalacheng.libuser.model.ApiUserVideo;

public interface FinishCallBack {

    /**
     * 回调 关闭 外层 父Activity
     */
    void isFinish();

    /**
     * 对应删除 父Activity 列表对应 item
     * @param apiUserVideo
     * @param position
     */
    void deleteList(ApiUserVideo apiUserVideo, int position);
}
