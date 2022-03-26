package com.kalacheng.busfinance.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 贡献榜
*/
public class APPFinance_contributionList
{


/**
 * 主播ID(查看礼物榜时必传,默认传0)
 */
	public long anchorId;

/**
 * 页数
 */
	public int page;

/**
 * 每页的条数
 */
	public int pageSize;

/**
 * 类型 0:总榜 1:日榜 2:周榜 3:月榜
 */
	public int type;




}
