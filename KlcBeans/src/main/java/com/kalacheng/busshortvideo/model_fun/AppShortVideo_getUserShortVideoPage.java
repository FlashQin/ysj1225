package com.kalacheng.busshortvideo.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * (分页)某个人/自己发的作品、点赞的作品、购买的作品
*/
public class AppShortVideo_getUserShortVideoPage
{


/**
 * 页数
 */
	public int page;

/**
 * 每页的条数
 */
	public int pageSize;

/**
 * 对方用户ID（传-1查询自己的）
 */
	public long toUid;

/**
 * 类型 1:我的作品 2:我喜欢的 3:我购买的
 */
	public int type;




}
