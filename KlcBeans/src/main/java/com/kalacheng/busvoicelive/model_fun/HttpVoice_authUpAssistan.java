package com.kalacheng.busvoicelive.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 默认直接上麦，当房间主播设置开关需审核才能上麦时，点击上麦的用户进入申请列表等待,主播可在待审核列表同意指定的观众进入直播间0:观众申请上麦发送成功 1:观众上麦操作成功 2:主播ID不一致 3:麦位数据有误
*/
public class HttpVoice_authUpAssistan
{


/**
 * 麦位序号  -1时为弹窗功能的申请上麦，不能指定上的麦序
 */
	public int assistanNo;

/**
 * 直播间ID
 */
	public long roomId;

/**
 * 直播间主播ID
 */
	public long uid;




}
