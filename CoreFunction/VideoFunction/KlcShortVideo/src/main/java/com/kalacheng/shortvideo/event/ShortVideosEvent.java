package com.kalacheng.shortvideo.event;

import com.kalacheng.libuser.model.ApiShortVideoDto;

import java.util.List;

/**
 * @author: Administrator
 * @date: 2020/11/2
 * @info: 在短视频观看时  操作了 购买 点赞 评论等  回传数据
 *
 *      type为 从哪一个fragment开始的跳转。  回传的时候判断type 做出相应的处理。
 */
public class ShortVideosEvent {

    public int type;
    public List<ApiShortVideoDto> appShortVideos;

}