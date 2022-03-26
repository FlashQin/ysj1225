package com.kalacheng.busshortvideo.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 短视频首页列表、根据分类查询短视频
*/
public class AppShortVideo_getShortVideoList
{


/**
 * 最后一个广告id(没有传-1)
 */
	public long adsId;

/**
 * 分类id(根据分类查询短视频默认传-1)
 */
	public long classifyId;

/**
 * 页数
 */
	public int page;

/**
 * 每页的条数
 */
	public int pageSize;

/**
 * 排序(-1:默认 1:最多观看 2:最多评论 3:最多点赞 4:最多付费 5:最多观看)
 */
	public int sort;

/**
 * 列表类型 0:推荐 1:关注
 */
	public int type;




}
