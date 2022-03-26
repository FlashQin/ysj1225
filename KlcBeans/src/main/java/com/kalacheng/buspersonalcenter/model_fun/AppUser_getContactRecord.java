package com.kalacheng.buspersonalcenter.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 购买查看联系方式的记录
*/
public class AppUser_getContactRecord
{


/**
 * 交易类型 16：查看微信号  17：查看手机号
 */
	public int changeType;

/**
 * 当前页
 */
	public int page;

/**
 * 每页的条数
 */
	public int pageSize;

/**
 * 被查看的用户
 */
	public long userId;




}
