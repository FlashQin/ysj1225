package com.kalacheng.busshop.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 获取主播(商家)订单列表
*/
public class ShopOrder_getAnchorOrderList
{


/**
 * 当前页
 */
	public int pageIndex;

/**
 * 每页大小
 */
	public int pageSize;

/**
 * 退款订单状态0,全部 1,待审核 2,待发货 3,待收货 4,退款中 5.退款完成 6.退款失败
 */
	public int quitStatus;

/**
 * 订单状态0.全部 1.待付款 2.待发货 3.待收货 4.订单完成
 */
	public int status;




}
