package com.kalacheng.buspersonalcenter.model_fun;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
/**
 * 更新用户推送的注册id
*/
public class AppUser_upUserPushInfo
{


/**
 * 推送平台 1:小米 2:华为 3:vivo 4:oppo 5:apns 6:极光 7:miApns
 */
	public int pushPlatform;

/**
 * 推送平台对应的注册id
 */
	public String pushRegisterId;

/**
 * 苹果voip推送(安卓不传)
 */
	public String voipToken;




}
