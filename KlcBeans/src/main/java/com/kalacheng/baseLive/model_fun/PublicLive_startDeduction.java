package com.kalacheng.baseLive.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 视频语音,门票/计时发起扣费
*/
public class PublicLive_startDeduction
{


/**
 * 主播id 
 */
	public long anchorId;

/**
 * 直播类型 1:直播 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态 
 */
	public int liveType;

/**
 * 房间号
 */
	public long roomId;

/**
 * 房间模式(暂时弃用,使用主播的) 0:普通房间 1:私密房间 2:收费房间 3:计时房间 4:贵族房间
 */
	public int roomType;

/**
 * 类型值
 */
	public String roomTypeVal;

/**
 * 直播标识 
 */
	public String showId;




}
