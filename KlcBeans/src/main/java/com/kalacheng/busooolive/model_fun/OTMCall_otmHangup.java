package com.kalacheng.busooolive.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 主播或用户挂断，整个房间销毁.副播挂断，副播退出,房间通话继续; code;1成功；2通话已经结束；11数据错误
*/
public class OTMCall_otmHangup
{


/**
 * 挂断原因 1:正常 2:直播云断掉了 11:费用不足 13:超时自动撤销
 */
	public int reason;

/**
 * 会话ID
 */
	public long sessionID;




}
