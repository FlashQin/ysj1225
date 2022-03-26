package com.kalacheng.busshop.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 确认申请退款
*/
public class ShopQuiteOrder_applyRefund
{


/**
 * 商家订单id
 */
	public long businessOrderId;

/**
 * 退款原因id
 */
	public long reasonId;

/**
 * 退款备注
 */
	public String refundNotes;

/**
 * 退款备注图片
 */
	public String refundNotesImages;

/**
 * 1.仅退款，2.退货退款
 */
	public int type;




}
