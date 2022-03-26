package com.kalacheng.busooolive.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 对指定主播发起加入房间1v1v7群聊邀请，主播接通后成为该房间的副播(该功能只有开通了SVIP服务的用户才可以使用) 14:用户金币不足 15:房间人数超限 16:该副播已经处于被邀请中状态中,不能再次邀请！17:对方开启了勿扰 18:男孩子之前不能通话 19:正处于免费通话期间,不能邀请副播
*/
public class OTMCall_invtJoinOneVsOne
{


/**
 * 对方id
 */
	public long inviteUId;

/**
 * 是否视频 0语音1视频 目前1v1v7只能视频
 */
	public int isVideo;

/**
 * 1v1v多邀请时填入的1v1房间的sessionId
 */
	public long sessionId;




}
