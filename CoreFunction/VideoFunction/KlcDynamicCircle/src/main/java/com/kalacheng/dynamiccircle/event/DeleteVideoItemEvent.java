package com.kalacheng.dynamiccircle.event;

import com.kalacheng.libuser.model.ApiUserVideo;

/**
 * 删除 对应动态（视频）
 */
public class DeleteVideoItemEvent {

    public ApiUserVideo apiUserVideo;

    public static DeleteVideoItemEvent getInstance(ApiUserVideo apiUserVideo) {
        return new DeleteVideoItemEvent(apiUserVideo);
    }

    public DeleteVideoItemEvent() {
    }

    public DeleteVideoItemEvent(ApiUserVideo apiUserVideo) {
        this.apiUserVideo = apiUserVideo;
    }
}
