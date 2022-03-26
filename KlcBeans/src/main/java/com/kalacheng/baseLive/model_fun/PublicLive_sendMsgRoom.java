package com.kalacheng.baseLive.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 发送消息
*/
public class PublicLive_sendMsgRoom
{


/**
 * 主播id,没有传0
 */
	public long anchorId;

/**
 * 消息内容
 */
	public String content;

/**
 * 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 */
	public int liveType;

/**
 * 房间id
 */
	public long roomId;

/**
 * 消息类型 0:系统消息(超管) 1:是普通消息 2:弹幕消息
 */
	public int type;




}
