package com.kalacheng.busfinance.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 选择充值规则进行充值,返回value为支付宝/微信支付等需要的参数
*/
public class ApiPay_startPay
{


/**
 * 支付渠道ID 1:支付宝 2:微信 3:ios内购
 */
	public long channelId;

/**
 * 商品id
 */
	public long productId;

/**
 * 业务类型 1：金币充值  2：购物支付 3.待定 4.购买贵族 5.购买SVIP
 */
	public int type;




}
