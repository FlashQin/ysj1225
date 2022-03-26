package com.kalacheng.buspersonalcenter.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 一对一付费通话设置
*/
public class AppUser_setPayCallOneVsOne
{


/**
 * 一对一在线状态  0在线1忙碌2离开3通话中
 */
	public int liveState;

/**
 * 一对一打开状态 0：默认，不打开  1：打开
 */
	public int openState;

/**
 * 海报, 修改时可为null
 */
	public String poster;

/**
 * 视频地址, 修改时可为null
 */
	public String video;

/**
 * 视频通话费用/min, 修改时可为null
 */
	public double videoCoin;

/**
 * 视频封面地址, 修改时可为null
 */
	public String videoImg;

/**
 * 录音地址, 修改时可为null
 */
	public String voice;

/**
 * 语音通话费用/min, 修改时可为null
 */
	public double voiceCoin;




}
