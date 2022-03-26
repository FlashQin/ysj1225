package com.kalacheng.shortvideo.event;


import com.kalacheng.libuser.model.ApiShortVideoDto;

/**
 * 删除短视频
 */
public class DeleteShortVideoItemEvent {

    public ApiShortVideoDto appShortVideo;

    public static DeleteShortVideoItemEvent getInstance(ApiShortVideoDto appShortVideo) {
        return new DeleteShortVideoItemEvent(appShortVideo);
    }

    public DeleteShortVideoItemEvent() {
    }

    public DeleteShortVideoItemEvent(ApiShortVideoDto appShortVideo) {
        this.appShortVideo = appShortVideo;
    }
}
