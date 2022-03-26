package com.kalacheng.buspersonalcenter.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 查看用户个人主页
*/
public class AppUser_personCenter
{


/**
 * 主播id(非直播间调用该接口传-1)
 */
	public long anchorId;

/**
 * 直播间类型(非直播间调用该接口传-1) 1:视频直播间 2:语音直播间
 */
	public int type;

/**
 * 被查看的用户ID
 */
	public long userId;




}
