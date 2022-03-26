package com.kalacheng.busooolive.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 用户踢人只能踢副播,副播退出的挂断接口。(只自己退出，1v1视频继续)
*/
public class OTMCall_kickOutAnch
{


/**
 * 被踢出的副播id
 */
	public long assitId;

/**
 * 是否为视频通话 目前1v1v7只支持视频 1视频 0语音
 */
	public int isVideo;

/**
 * 会话ID
 */
	public long sessionID;




}
