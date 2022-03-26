package com.kalacheng.busfinance.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 苹果内购校验  1校验成功  2苹果验证失败，返回数据为空
*/
public class ApiPay_iosPayCallBack
{


/**
 * 订单号
 */
	public String orderNo;

/**
 * 校验体（base64字符串）
 */
	public String payload;

/**
 * 苹果内购交易ID
 */
	public String transactionId;




}
