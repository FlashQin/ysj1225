package com.kalacheng.busshortvideo.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 发布短视频code为3时请先认证
*/
public class AppShortVideo_setShortVideo
{


/**
 * 详细地址
 */
	public String address;

/**
 * 城市
 */
	public String city;

/**
 * 分类ID
 */
	public String classifyId;

/**
 * 默认0元即可， 填写金币金额
 */
	public double coin;

/**
 * 文字内容
 */
	public String content;

/**
 * 封面图高
 */
	public int height;

/**
 * 视频地址(短视频时才传入)
 */
	public String href;

/**
 * 图片（逗号拼接）
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
 * 商品id
 */
	public long productId;

/**
 * 封面图(如果是视频就取第一帧,如果是图片取第一个图片)
 */
	public String thumb;

/**
 * 类型 1:视频 2:图片
 */
	public int type;

/**
 * 视频时长（单位秒）
 */
	public int videoTime;

/**
 * 封面图宽
 */
	public int width;




}
