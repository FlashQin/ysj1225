package com.kalacheng.busooolive.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 回复用户,是否接受邀请。code:1成功；2会话已经结束；3重复请求; 4数据错误 9余额不足；12贵族才能拨打
*/
public class OTMCall_replyInviteOTM
{


/**
 * 付费用户id
 */
	public long feeUid;

/**
 * 是否同意 1:同意 0:拒绝
 */
	public int replyType;

/**
 * 会话ID
 */
	public long sessionID;




}
