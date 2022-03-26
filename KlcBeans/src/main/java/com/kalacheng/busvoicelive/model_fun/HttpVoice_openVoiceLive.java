package com.kalacheng.busvoicelive.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * http方式创建房间code为3时请先认证
*/
public class HttpVoice_openVoiceLive
{


/**
 * 主播地址
 */
	public String addr;

/**
 * 小头像(改从后台取)
 */
	public String avatar;

/**
 * 主播头像(改从后台取)
 */
	public String avatarThumb;

/**
 * 频道ID
 */
	public int channelId;

/**
 * 市
 */
	public String city;

/**
 * 纬度
 */
	public double lat;

/**
 * 直播分类ID
 */
	public int liveclassid;

/**
 * 经度
 */
	public double lng;

/**
 * 主播昵称(不使用,统一使用userName)
 */
	public String nickname;

/**
 * 省
 */
	public String province;

/**
 * 直播云推/拉流地址
 */
	public String pull;

/**
 * 房间模式 0:普通房间 1:私密房间 2:收费房间 3:计时房间 4:贵族房间
 */
	public int roomType;

/**
 * 房间模式对应的值 密码房间：密码  收费房间：收费金额
 */
	public String roomTypeVal;

/**
 * 封面图
 */
	public String thumb;

/**
 * 开播标题
 */
	public String title;

/**
 * 自动上麦状态 1:开 0:关
 */
	public int upStatus;




}
