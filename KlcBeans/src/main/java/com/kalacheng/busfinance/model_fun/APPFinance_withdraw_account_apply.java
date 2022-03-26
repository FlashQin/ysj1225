package com.kalacheng.busfinance.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 我的收益提现申请
*/
public class APPFinance_withdraw_account_apply
{


/**
 * 提现账号id
 */
	public long accountId;

/**
 * 账号
 */
	public String accountName;

/**
 * 账号类型 1：支付宝  2：微信，3：银行卡
 */
	public int accountType;

/**
 * 提现金币/佣金数量 (男朋友中为钻石)
 */
	public int delta;

/**
 * 佣金提现 1：金币/钻石提现 (男朋友中为钻石)  2：佣金提现 3：公会账户提现， 4：商家提现
 */
	public int type;




}
