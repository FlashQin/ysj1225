package com.kalacheng.buspersonalcenter.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 查询在线用户和在线主播列表
*/
public class APPAnchor_getLineUser
{


/**
 * 城市筛选 没有指定城市就传空字符串
 */
	public String city;

/**
 * 纬度
 */
	public double lat;

/**
 * 经度
 */
	public double lng;

/**
 * 当前页
 */
	public int pageIndex;

/**
 * 每页大小
 */
	public int pageSize;

/**
 * 性别 0:未设置 1:男 2:女 (男朋友新增 3(0) 4(0.5) 5(...)
 */
	public int sex;

/**
 * 状态 -1:全部 1:直播中 2:房间中 3:在线 4:离线
 */
	public int status;




}
