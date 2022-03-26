package com.kalacheng.busfinance.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 我的收益里添加提现账号
*/
public class APPFinance_withdraw_account_add
{


/**
 * 账号
 */
	public String account;

/**
 * 银行名称(type为3时传入)
 */
	public String accountBank;

/**
 * 支行
 */
	public String branch;

/**
 * 姓名
 */
	public String name;

/**
 * 相关记录ID
 */
	public long recordId;

/**
 * 类型1表示支付宝，2表示微信，3表示银行卡
 */
	public int type;




}
