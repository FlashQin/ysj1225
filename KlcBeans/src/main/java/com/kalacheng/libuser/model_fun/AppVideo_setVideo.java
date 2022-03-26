package com.kalacheng.libuser.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 发布短视频（动态）code为3时请先认证
*/
public class AppVideo_setVideo
{


/**
 * 详细地址
 */
	public String address;

/**
 * 频道id
 */
	public long channelId;

/**
 * 城市
 */
	public String city;

/**
 * 默认0元即可， 填写金币金额
 */
	public double coin;

/**
 * 视频高
 */
	public int height;

/**
 * 视频地址(视频类型时才传入)
 */
	public String href;

/**
 * 动态图片（逗号拼接）
 */
	public String images;

/**
 * 是否私密 0：正常 1：私密
 */
	public int isPrivate;

/**
 * 纬度
 */
	public double lat;

/**
 * 经度
 */
	public double lng;

/**
 * 音乐id
 */
	public long musicId;

/**
 * 资源来源 1:首页发布动态 2:其他地方发布出来的动态
 */
	public int sourceFrom;

/**
 * 动态封面图
 */
	public String thumb;

/**
 * 动态标题
 */
	public String title;

/**
 * 动态话题ID
 */
	public long topicId;

/**
 * 类型 0:只有文字 1:视频动态 2:图片动态
 */
	public int type;

/**
 * 视频时长
 */
	public String videoTime;

/**
 * 视频宽
 */
	public int width;




}
