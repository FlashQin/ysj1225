package com.kalacheng.libuser.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 短视频（动态）列表
*/
public class AppVideo_getVideoList
{


/**
 * 分类id
 */
	public long hotId;

/**
 * 页数
 */
	public int page;

/**
 * 每页的条数
 */
	public int pageSize;

/**
 * 要查看的用户的ID
 */
	public long touid;

/**
 * 类型 0:全部 1:推荐 2:关注
 */
	public int type;




}
