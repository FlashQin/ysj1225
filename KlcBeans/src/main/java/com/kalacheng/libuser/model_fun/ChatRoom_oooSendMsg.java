package com.kalacheng.libuser.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 1V1发消息成功记录数据
*/
public class ChatRoom_oooSendMsg
{


/**
 * 消息内容
 */
	public String content;

/**
 * 付钱人用户id(发送方)
 */
	public long feeId;

/**
 * 受益人用户id(接收方)
 */
	public long hostId;

/**
 * 发送的消息类型: 1:文字 2:图片 3:语音 4:视频通话 5:语音通话
 */
	public int sendMsgType;




}
