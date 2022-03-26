package com.kalacheng.busooolive.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 是否能发起一对一通话 code:1成功 2:对方正忙 3:对方正在通话 4:对方不在线 9:用户余额不足无法邀请通话 10:不能向自己发起邀请11:数据错误 12:贵族才能通话 13:用户和用户不能打电话 14:主播和主播不能打电话 15:对方开启了勿扰 16:主播正忙 17:被邀请用户设置了勿扰 18:您已被拉黑 20:未认证
*/
public class OOOCall_inviteChat
{


/**
 * 对方id
 */
	public long inviteUId;

/**
 * 0:语音 1:视频
 */
	public int isVideo;




}
