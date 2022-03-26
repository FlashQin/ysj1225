package com.kalacheng.buspersonalcenter.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 设置查看账号的费用
*/
public class AppUser_setViewContactPrice
{


/**
 * 联系方式类型 1：手机号价格  2：微信号价格 3：直播回放价格
 */
	public double price;

/**
 * 是否开启收费 0：不开启  1：开启
 */
	public int state;

/**
 * 联系方式类型 1：手机号  2：微信号， 3：直播回放
 */
	public int type;




}
