package com.kalacheng.libuser.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 首页列表数据
*/
public class Home_getHomeDataList
{


/**
 * 地址
 */
	public String address;

/**
 * 频道ID(全部传-1)
 */
	public long channelId;

/**
 * 热门类型ID(没有传-1)
 */
	public long hotSortId;

/**
 * 是否推荐 -1:全部 0:不推荐 1:已推荐
 */
	public int isRecommend;

/**
 * 是否有直播购 -1:全部 0:没有 1:有
 */
	public int liveFunction;

/**
 * 类型 1:直播 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 */
	public int liveType;

/**
 * 当前页
 */
	public int pageIndex;

/**
 * 每页大小
 */
	public int pageSize;

/**
 * 性别
 */
	public int sex;

/**
 * 标签ID数组， 逗号隔开
 */
	public String tabIds;




}
