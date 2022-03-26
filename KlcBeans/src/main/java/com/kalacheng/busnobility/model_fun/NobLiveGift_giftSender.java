package com.kalacheng.busnobility.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 发送礼物HTTP
*/
public class NobLiveGift_giftSender
{


/**
 * 主播id
 */
	public long anchorId;

/**
 * 背包id(没有传-1)
 */
	public long backid;

/**
 * 礼物id
 */
	public long giftId;

/**
 * 礼物个数
 */
	public int number;

/**
 * 房间id(没有传-1)
 */
	public long roomId;

/**
 * 短视频id(没有传-1)
 */
	public long shortVideoId;

/**
 * 直播标识(在直播间时传入)
 */
	public String showid;

/**
 * 送礼物类型 1:多人视频 2:语音直播 3:一对一 4:派对 5:电台 6:直播购物 7:私聊礼物 8:群聊礼物 9:短视频
 */
	public int type;

/**
 * 房主(开播方)ID (没有传-1)
 */
	public long uid;




}
