package com.kalacheng.busfinance.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 提现记录(平台/公会)
*/
public class APPFinance_userCashRecord_list
{


/**
 * 日期
 */
	public String date;

/**
 * 每页的条数
 */
	public int pageIndex;

/**
 * 每页的条数
 */
	public int pageSize;

/**
 * 状态，0审核中，1审核通过，2审核拒绝，-1全部
 */
	public int status;

/**
 * 0.平台，1.公会
 */
	public int type;




}
