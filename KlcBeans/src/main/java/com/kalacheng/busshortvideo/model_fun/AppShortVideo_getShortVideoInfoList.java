package com.kalacheng.busshortvideo.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 查看单个短视频
*/
public class AppShortVideo_getShortVideoInfoList
{


/**
 * 评论id(通知列表的commentId)没有则传-1
 */
	public int commentId;

/**
 * 短视频id
 */
	public long shortVideoId;

/**
 * 来源 -1:查看详情 1:消息评论 2:消息点赞
 */
	public int type;




}
