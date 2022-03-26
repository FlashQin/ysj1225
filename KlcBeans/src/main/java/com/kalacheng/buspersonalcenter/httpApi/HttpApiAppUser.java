package com.kalacheng.buspersonalcenter.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 用户接口API
 */
public class HttpApiAppUser
{




/**
 * 修改定位开关
 * @param isLocation 0开启1关闭
 */
public static void updateLocation(int isLocation,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/updateLocation","/api/user/updateLocation")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("isLocation", isLocation)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 版本控制
 * @param type 类型 1:安卓 2:ios
 * @param versionCode 版本号
 */
public static void version_control(com.kalacheng.buspersonalcenter.model_fun.AppUser_version_control _mdl,HttpApiCallBack<com.kalacheng.libuser.model.ApiVersion> callback)
{
HttpClient.getInstance().post("/api/user/version_control","/api/user/version_control")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("type", _mdl.type)
.params("versionCode", _mdl.versionCode)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiVersion_Ret.class));

}



/**
 * 版本控制
 * @param type 类型 1:安卓 2:ios
 * @param versionCode 版本号
 */
public static void version_control(int type,int versionCode,HttpApiCallBack<com.kalacheng.libuser.model.ApiVersion> callback)
{
HttpClient.getInstance().post("/api/user/version_control","/api/user/version_control")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("type", type)
.params("versionCode", versionCode)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiVersion_Ret.class));

}





/**
 * 守护我的列表
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param userId 用户ID
 */
public static void getGuardMyList(com.kalacheng.buspersonalcenter.model_fun.AppUser_getGuardMyList _mdl,HttpApiCallBackArr<com.kalacheng.buscommon.model.GuardUserDto> callback)
{
HttpClient.getInstance().post("/api/user/getGuardMyList","/api/user/getGuardMyList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.params("userId", _mdl.userId)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.buscommon.model.GuardUserDto_RetArr.class));

}



/**
 * 守护我的列表
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param userId 用户ID
 */
public static void getGuardMyList(int pageIndex,int pageSize,long userId,HttpApiCallBackArr<com.kalacheng.buscommon.model.GuardUserDto> callback)
{
HttpClient.getInstance().post("/api/user/getGuardMyList","/api/user/getGuardMyList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.params("userId", userId)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.buscommon.model.GuardUserDto_RetArr.class));

}





/**
 * 签到
 */
public static void sign(HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/sign","/api/user/sign")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 获取主播的直播记录
 * @param beforeDay 天数(如最近3天传3,5天传5即可,-1查所有)
 * @param page 当前页
 * @param pageSize 每页的条数
 */
public static void getLiveData(com.kalacheng.buspersonalcenter.model_fun.AppUser_getLiveData _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.LiveRoomDTO> callback)
{
HttpClient.getInstance().post("/api/user/getLiveData","/api/user/getLiveData")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("beforeDay", _mdl.beforeDay)
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.LiveRoomDTO_RetArr.class));

}



/**
 * 获取主播的直播记录
 * @param beforeDay 天数(如最近3天传3,5天传5即可,-1查所有)
 * @param page 当前页
 * @param pageSize 每页的条数
 */
public static void getLiveData(int beforeDay,int page,int pageSize,HttpApiCallBackArr<com.kalacheng.libuser.model.LiveRoomDTO> callback)
{
HttpClient.getInstance().post("/api/user/getLiveData","/api/user/getLiveData")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("beforeDay", beforeDay)
.params("page", page)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.LiveRoomDTO_RetArr.class));

}





/**
 * 谁看过我
 * @param pageIndex pageIndex
 * @param pageSize pageSize
 */
public static void browseRecord(com.kalacheng.buspersonalcenter.model_fun.AppUser_browseRecord _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiUserAtten> callback)
{
HttpClient.getInstance().post("/api/user/browseRecord","/api/user/browseRecord")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiUserAtten_RetArr.class));

}



/**
 * 谁看过我
 * @param pageIndex pageIndex
 * @param pageSize pageSize
 */
public static void browseRecord(int pageIndex,int pageSize,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiUserAtten> callback)
{
HttpClient.getInstance().post("/api/user/browseRecord","/api/user/browseRecord")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiUserAtten_RetArr.class));

}





/**
 * 设置查看账号的费用
 * @param price 联系方式类型 1：手机号价格  2：微信号价格 3：直播回放价格
 * @param state 是否开启收费 0：不开启  1：开启
 * @param type 联系方式类型 1：手机号  2：微信号， 3：直播回放
 */
public static void setViewContactPrice(com.kalacheng.buspersonalcenter.model_fun.AppUser_setViewContactPrice _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/setViewContactPrice","/api/user/setViewContactPrice")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("price", _mdl.price)
.params("state", _mdl.state)
.params("type", _mdl.type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 设置查看账号的费用
 * @param price 联系方式类型 1：手机号价格  2：微信号价格 3：直播回放价格
 * @param state 是否开启收费 0：不开启  1：开启
 * @param type 联系方式类型 1：手机号  2：微信号， 3：直播回放
 */
public static void setViewContactPrice(double price,int state,int type,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/setViewContactPrice","/api/user/setViewContactPrice")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("price", price)
.params("state", state)
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 获取一对一付费通话设置
 */
public static void getPayCallOneVsOneCfg(HttpApiCallBack<com.kalacheng.libuser.model.CfgPayCallOneVsOne> callback)
{
HttpClient.getInstance().post("/api/user/getPayCallOneVsOneCfg","/api/user/getPayCallOneVsOneCfg")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.CfgPayCallOneVsOne_Ret.class));

}





/**
 * 获取APP主播信息接口
 * @param anchorId 主播id(非直播间进去传0)
 * @param touid 指定查看人的id
 * @param type 直播间类型1视频直播间2语音直播间
 */
public static void anchor_info(com.kalacheng.buspersonalcenter.model_fun.AppUser_anchor_info _mdl,HttpApiCallBack<com.kalacheng.libuser.model.ApiAnchorResp> callback)
{
HttpClient.getInstance().post("/api/user/anchor_info","/api/user/anchor_info")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("touid", _mdl.touid)
.params("type", _mdl.type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiAnchorResp_Ret.class));

}



/**
 * 获取APP主播信息接口
 * @param anchorId 主播id(非直播间进去传0)
 * @param touid 指定查看人的id
 * @param type 直播间类型1视频直播间2语音直播间
 */
public static void anchor_info(long anchorId,long touid,int type,HttpApiCallBack<com.kalacheng.libuser.model.ApiAnchorResp> callback)
{
HttpClient.getInstance().post("/api/user/anchor_info","/api/user/anchor_info")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("touid", touid)
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiAnchorResp_Ret.class));

}





/**
 * 收入排行
 * @param page 当前页
 * @param pageSize 每页的条数
 */
public static void incomeRanking(com.kalacheng.buspersonalcenter.model_fun.AppUser_incomeRanking _mdl,HttpApiCallBackPageArr<com.kalacheng.buscommon.model.AppUserIncomeRankingDto> callback)
{
HttpClient.getInstance().post("/api/user/incomeRanking","/api/user/incomeRanking")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.buscommon.model.AppUserIncomeRankingDto_RetPageArr.class));

}



/**
 * 收入排行
 * @param page 当前页
 * @param pageSize 每页的条数
 */
public static void incomeRanking(int page,int pageSize,HttpApiCallBackPageArr<com.kalacheng.buscommon.model.AppUserIncomeRankingDto> callback)
{
HttpClient.getInstance().post("/api/user/incomeRanking","/api/user/incomeRanking")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", page)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.buscommon.model.AppUserIncomeRankingDto_RetPageArr.class));

}





/**
 * 我的--注销账户
 * @param code 密码/验证码
 * @param type 注销方式1密码2短信验证码
 */
public static void userCancelAccount(com.kalacheng.buspersonalcenter.model_fun.AppUser_userCancelAccount _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/userCancelAccount","/api/user/userCancelAccount")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("code", _mdl.code)
.params("type", _mdl.type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 我的--注销账户
 * @param code 密码/验证码
 * @param type 注销方式1密码2短信验证码
 */
public static void userCancelAccount(String code,int type,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/userCancelAccount","/api/user/userCancelAccount")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("code", code)
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 查看定位开关状态，no_use值0是开启1是关闭
 */
public static void getLocation(HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/getLocation","/api/user/getLocation")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 修改个人直播封面
 * @param value {'封面序号':'封面图片地址'} 
 */
public static void updateUserImgInfo(String value,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/updateUserImgInfo","/api/user/updateUserImgInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("value", value)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 用户登出
 */
public static void logout(HttpApiCallBack<com.kalacheng.libbas.model.SingleString> callback)
{
HttpClient.getInstance().post("/api/user/logout","/api/user/logout")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.SingleString_Ret.class));

}





/**
 * 更新用户推送的注册id
 * @param pushPlatform 推送平台 1:小米 2:华为 3:vivo 4:oppo 5:apns 6:极光 7:miApns
 * @param pushRegisterId 推送平台对应的注册id
 * @param voipToken 苹果voip推送(安卓不传)
 */
public static void upUserPushInfo(com.kalacheng.buspersonalcenter.model_fun.AppUser_upUserPushInfo _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/upUserPushInfo","/api/user/upUserPushInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pushPlatform", _mdl.pushPlatform)
.params("pushRegisterId", _mdl.pushRegisterId)
.params("voipToken", _mdl.voipToken)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 更新用户推送的注册id
 * @param pushPlatform 推送平台 1:小米 2:华为 3:vivo 4:oppo 5:apns 6:极光 7:miApns
 * @param pushRegisterId 推送平台对应的注册id
 * @param voipToken 苹果voip推送(安卓不传)
 */
public static void upUserPushInfo(int pushPlatform,String pushRegisterId,String voipToken,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/upUserPushInfo","/api/user/upUserPushInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pushPlatform", pushPlatform)
.params("pushRegisterId", pushRegisterId)
.params("voipToken", voipToken)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 用户绑定推荐人(邀请码)
 * @param touid 推荐人用户id
 */
public static void bindPid(long touid,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/bindPid","/api/user/bindPid")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("touid", touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 全局获取用户免费通话时间和通话次数(邀请码)
 * @param userid 用户id
 */
public static void getUserByregister(long userid,HttpApiCallBack<com.kalacheng.libuser.model.AppUser> callback)
{
HttpClient.getInstance().post("/api/user/getUserByregister","/api/user/getUserByregister")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("userid", userid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppUser_Ret.class));

}





/**
 * 举报用户
 * @param classifyId 举报类型id
 * @param content 举报内容
 * @param touid 被举报用户
 */
public static void usersReport(com.kalacheng.buspersonalcenter.model_fun.AppUser_usersReport _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/usersReport","/api/user/usersReport")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("classifyId", _mdl.classifyId)
.params("content", _mdl.content)
.params("touid", _mdl.touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 举报用户
 * @param classifyId 举报类型id
 * @param content 举报内容
 * @param touid 被举报用户
 */
public static void usersReport(long classifyId,String content,long touid,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/usersReport","/api/user/usersReport")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("classifyId", classifyId)
.params("content", content)
.params("touid", touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 我的动态
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 */
public static void getMyTrendsPage(com.kalacheng.buspersonalcenter.model_fun.AppUser_getMyTrendsPage _mdl,HttpApiCallBack<com.kalacheng.buscommon.model.ApiUserInfo> callback)
{
HttpClient.getInstance().post("/api/user/getMyTrendsPage","/api/user/getMyTrendsPage")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.model.ApiUserInfo_Ret.class));

}



/**
 * 我的动态
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 */
public static void getMyTrendsPage(int pageIndex,int pageSize,HttpApiCallBack<com.kalacheng.buscommon.model.ApiUserInfo> callback)
{
HttpClient.getInstance().post("/api/user/getMyTrendsPage","/api/user/getMyTrendsPage")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.model.ApiUserInfo_Ret.class));

}





/**
 * 用户关注/取消关注；3还没关注TA
 * @param is_atten 1:去关注 0:取消关注
 * @param touid 要关注的人ID
 */
public static void set_atten(com.kalacheng.buspersonalcenter.model_fun.AppUser_set_atten _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/set_atten","/api/user/set_atten")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("is_atten", _mdl.is_atten)
.params("touid", _mdl.touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 用户关注/取消关注；3还没关注TA
 * @param is_atten 1:去关注 0:取消关注
 * @param touid 要关注的人ID
 */
public static void set_atten(int is_atten,long touid,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/set_atten","/api/user/set_atten")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("is_atten", is_atten)
.params("touid", touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 群聊加入房间
 * @param roomId 群id
 */
public static void groupJoinRoom(long roomId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/groupJoinRoom","/api/user/groupJoinRoom")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("roomId", roomId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * APP修改个人信息
 * @param address 详细地址
 * @param avatar 头像地址(传入轩嗵云后返回的地址)
 * @param birthday 生日
 * @param city 城市
 * @param constellation 星座
 * @param height 身高
 * @param lat 纬度(没值传-1)
 * @param liveThumb 封面
 * @param lng 经度(没值传-1)
 * @param portrait 图片
 * @param sex 性别0：保密，1：男；2：女
 * @param signature 签名
 * @param username 用户名
 * @param vocation 职业
 * @param wechat wechat
 * @param weight 体重
 */
public static void user_update(com.kalacheng.buspersonalcenter.model_fun.AppUser_user_update _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/user_update","/api/user/user_update")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("address", _mdl.address)
.params("avatar", _mdl.avatar)
.params("birthday", _mdl.birthday)
.params("city", _mdl.city)
.params("constellation", _mdl.constellation)
.params("height", _mdl.height)
.params("lat", _mdl.lat)
.params("liveThumb", _mdl.liveThumb)
.params("lng", _mdl.lng)
.params("portrait", _mdl.portrait)
.params("sex", _mdl.sex)
.params("signature", _mdl.signature)
.params("username", _mdl.username)
.params("vocation", _mdl.vocation)
.params("wechat", _mdl.wechat)
.params("weight", _mdl.weight)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * APP修改个人信息
 * @param address 详细地址
 * @param avatar 头像地址(传入轩嗵云后返回的地址)
 * @param birthday 生日
 * @param city 城市
 * @param constellation 星座
 * @param height 身高
 * @param lat 纬度(没值传-1)
 * @param liveThumb 封面
 * @param lng 经度(没值传-1)
 * @param portrait 图片
 * @param sex 性别0：保密，1：男；2：女
 * @param signature 签名
 * @param username 用户名
 * @param vocation 职业
 * @param wechat wechat
 * @param weight 体重
 */
public static void user_update(String address,String avatar,String birthday,String city,String constellation,int height,double lat,String liveThumb,double lng,String portrait,int sex,String signature,String username,String vocation,String wechat,double weight,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/user_update","/api/user/user_update")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("address", address)
.params("avatar", avatar)
.params("birthday", birthday)
.params("city", city)
.params("constellation", constellation)
.params("height", height)
.params("lat", lat)
.params("liveThumb", liveThumb)
.params("lng", lng)
.params("portrait", portrait)
.params("sex", sex)
.params("signature", signature)
.params("username", username)
.params("vocation", vocation)
.params("wechat", wechat)
.params("weight", weight)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 获取连续登录奖励
 */
public static void getContinueLogin(HttpApiCallBack<com.kalacheng.libuser.model.AdminLoginAward> callback)
{
HttpClient.getInstance().post("/api/user/getContinueLogin","/api/user/getContinueLogin")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AdminLoginAward_Ret.class));

}





/**
 * 获取系统公告
 */
public static void getSysNotic(HttpApiCallBack<com.kalacheng.libuser.model.SysNotic> callback)
{
HttpClient.getInstance().post("/api/user/getSysNotic","/api/user/getSysNotic")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.SysNotic_Ret.class));

}





/**
 * 我的头部信息
 */
public static void getMyHeadInfo(HttpApiCallBack<com.kalacheng.buscommon.model.ApiUserInfo> callback)
{
HttpClient.getInstance().post("/api/user/getMyHeadInfo","/api/user/getMyHeadInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.model.ApiUserInfo_Ret.class));

}





/**
 * 我的会员
 */
public static void getMyMember(HttpApiCallBack<com.kalacheng.buscommon.model.ApiUserInfo> callback)
{
HttpClient.getInstance().post("/api/user/getMyMember","/api/user/getMyMember")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.model.ApiUserInfo_Ret.class));

}





/**
 * 查询贵族隐身特权
 */
public static void vipInvisiblePrivilege(HttpApiCallBack<com.kalacheng.libuser.model.InvisiblePrivilegeDTO> callback)
{
HttpClient.getInstance().post("/api/user/vipInvisiblePrivilege","/api/user/vipInvisiblePrivilege")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.InvisiblePrivilegeDTO_Ret.class));

}





/**
 * 绑定手机号码
 * @param code 验证码
 * @param mobile 绑定手机号码
 */
public static void bind_mobile(com.kalacheng.buspersonalcenter.model_fun.AppUser_bind_mobile _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/bind_mobile","/api/user/bind_mobile")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("code", _mdl.code)
.params("mobile", _mdl.mobile)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 绑定手机号码
 * @param code 验证码
 * @param mobile 绑定手机号码
 */
public static void bind_mobile(String code,String mobile,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/bind_mobile","/api/user/bind_mobile")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("code", code)
.params("mobile", mobile)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 查看用户个人主页
 * @param anchorId 主播id(非直播间调用该接口传-1)
 * @param type 直播间类型(非直播间调用该接口传-1) 1:视频直播间 2:语音直播间
 * @param userId 被查看的用户ID
 */
public static void personCenter(com.kalacheng.buspersonalcenter.model_fun.AppUser_personCenter _mdl,HttpApiCallBack<com.kalacheng.buscommon.model.ApiUserInfo> callback)
{
HttpClient.getInstance().post("/api/user/personCenter","/api/user/personCenter")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("type", _mdl.type)
.params("userId", _mdl.userId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.model.ApiUserInfo_Ret.class));

}



/**
 * 查看用户个人主页
 * @param anchorId 主播id(非直播间调用该接口传-1)
 * @param type 直播间类型(非直播间调用该接口传-1) 1:视频直播间 2:语音直播间
 * @param userId 被查看的用户ID
 */
public static void personCenter(long anchorId,int type,long userId,HttpApiCallBack<com.kalacheng.buscommon.model.ApiUserInfo> callback)
{
HttpClient.getInstance().post("/api/user/personCenter","/api/user/personCenter")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("type", type)
.params("userId", userId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.model.ApiUserInfo_Ret.class));

}





/**
 * 我的--动态--时间轴分页
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 */
public static void getMyTrendsTime(com.kalacheng.buspersonalcenter.model_fun.AppUser_getMyTrendsTime _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.AppTrendsRecord> callback)
{
HttpClient.getInstance().post("/api/user/getMyTrendsTime","/api/user/getMyTrendsTime")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppTrendsRecord_RetArr.class));

}



/**
 * 我的--动态--时间轴分页
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 */
public static void getMyTrendsTime(int pageIndex,int pageSize,HttpApiCallBackArr<com.kalacheng.libuser.model.AppTrendsRecord> callback)
{
HttpClient.getInstance().post("/api/user/getMyTrendsTime","/api/user/getMyTrendsTime")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppTrendsRecord_RetArr.class));

}





/**
 * 根据主播id查询主播直播记录
 * @param page 当前页
 * @param pageSize 每页的条数
 * @param uid 主播id
 */
public static void getLiveList(com.kalacheng.buspersonalcenter.model_fun.AppUser_getLiveList _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.LiveRoomDTO> callback)
{
HttpClient.getInstance().post("/api/user/getLiveList","/api/user/getLiveList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.params("uid", _mdl.uid)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.LiveRoomDTO_RetArr.class));

}



/**
 * 根据主播id查询主播直播记录
 * @param page 当前页
 * @param pageSize 每页的条数
 * @param uid 主播id
 */
public static void getLiveList(int page,int pageSize,long uid,HttpApiCallBackArr<com.kalacheng.libuser.model.LiveRoomDTO> callback)
{
HttpClient.getInstance().post("/api/user/getLiveList","/api/user/getLiveList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", page)
.params("pageSize", pageSize)
.params("uid", uid)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.LiveRoomDTO_RetArr.class));

}





/**
 * 1v1对主播评论操作
 * @param anchorId 主播id
 * @param value '颜色':'标签', '#ccc':'大方','#000':'性感'
 */
public static void addCommentByAnchor(com.kalacheng.buspersonalcenter.model_fun.AppUser_addCommentByAnchor _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/addCommentByAnchor","/api/user/addCommentByAnchor")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("value", _mdl.value)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 1v1对主播评论操作
 * @param anchorId 主播id
 * @param value '颜色':'标签', '#ccc':'大方','#000':'性感'
 */
public static void addCommentByAnchor(long anchorId,String value,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/addCommentByAnchor","/api/user/addCommentByAnchor")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("value", value)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 我的--主播
 */
public static void getMyAnchor(HttpApiCallBack<com.kalacheng.buscommon.model.ApiUserInfo> callback)
{
HttpClient.getInstance().post("/api/user/getMyAnchor","/api/user/getMyAnchor")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.model.ApiUserInfo_Ret.class));

}





/**
 * 我的账户
 */
public static void getMyAccount(HttpApiCallBack<com.kalacheng.buscommon.model.ApiUserInfo> callback)
{
HttpClient.getInstance().post("/api/user/getMyAccount","/api/user/getMyAccount")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.model.ApiUserInfo_Ret.class));

}





/**
 * 设置查看账号的费用
 */
public static void getViewContactPrice(HttpApiCallBackArr<com.kalacheng.libuser.model.CfgContactViewPrice> callback)
{
HttpClient.getInstance().post("/api/user/getViewContactPrice","/api/user/getViewContactPrice")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.CfgContactViewPrice_RetArr.class));

}





/**
 * 用户粉丝列表
 * @param pageIndex 当前页
 * @param pageSize 每页代销
 * @param touid 要查看的用户ID
 */
public static void getFansList(com.kalacheng.buspersonalcenter.model_fun.AppUser_getFansList _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiUserAtten> callback)
{
HttpClient.getInstance().post("/api/user/getFansList","/api/user/getFansList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.params("touid", _mdl.touid)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiUserAtten_RetArr.class));

}



/**
 * 用户粉丝列表
 * @param pageIndex 当前页
 * @param pageSize 每页代销
 * @param touid 要查看的用户ID
 */
public static void getFansList(int pageIndex,int pageSize,long touid,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiUserAtten> callback)
{
HttpClient.getInstance().post("/api/user/getFansList","/api/user/getFansList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.params("touid", touid)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiUserAtten_RetArr.class));

}





/**
 * 大厅直播列表
 * @param keyWord 搜索关键字
 * @param page 当前页
 * @param pageSize 每页的条数
 * @param type 类型 1:大厅 2:live 3:热门
 */
public static void lobby(com.kalacheng.buspersonalcenter.model_fun.AppUser_lobby _mdl,HttpApiCallBackPageArr<com.kalacheng.buscommon.model.LiveBean> callback)
{
HttpClient.getInstance().post("/api/user/lobby","/api/user/lobby")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("keyWord", _mdl.keyWord)
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.params("type", _mdl.type)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.buscommon.model.LiveBean_RetPageArr.class));

}



/**
 * 大厅直播列表
 * @param keyWord 搜索关键字
 * @param page 当前页
 * @param pageSize 每页的条数
 * @param type 类型 1:大厅 2:live 3:热门
 */
public static void lobby(String keyWord,int page,int pageSize,int type,HttpApiCallBackPageArr<com.kalacheng.buscommon.model.LiveBean> callback)
{
HttpClient.getInstance().post("/api/user/lobby","/api/user/lobby")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("keyWord", keyWord)
.params("page", page)
.params("pageSize", pageSize)
.params("type", type)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.buscommon.model.LiveBean_RetPageArr.class));

}





/**
 * 查询用户信息
 */
public static void info(HttpApiCallBack<com.kalacheng.buscommon.model.ApiUserInfo> callback)
{
HttpClient.getInstance().post("/api/user/info","/api/user/info")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.model.ApiUserInfo_Ret.class));

}





/**
 * 兑换金币
 * @param payTerminal 支付终端android/iOS
 * @param ruleId 充值规则ID
 */
public static void exchangeCoin(com.kalacheng.buspersonalcenter.model_fun.AppUser_exchangeCoin _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/exchangeCoin","/api/user/exchangeCoin")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("payTerminal", _mdl.payTerminal)
.params("ruleId", _mdl.ruleId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 兑换金币
 * @param payTerminal 支付终端android/iOS
 * @param ruleId 充值规则ID
 */
public static void exchangeCoin(String payTerminal,long ruleId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/exchangeCoin","/api/user/exchangeCoin")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("payTerminal", payTerminal)
.params("ruleId", ruleId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 用户/主播等级信息
 * @param type 类型 1:用户 2:主播
 */
public static void userLevelInfo(int type,HttpApiCallBack<com.kalacheng.libuser.model.ApiGradeReWarRe> callback)
{
HttpClient.getInstance().post("/api/user/userLevelInfo","/api/user/userLevelInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiGradeReWarRe_Ret.class));

}





/**
 * 我的--修改是否开启定位显示
 * @param type 是否开启定位显示 0:未开启 1:开启
 */
public static void upPositioningShow(int type,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/upPositioningShow","/api/user/upPositioningShow")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 举报类型列表
 */
public static void getUsersReportClassifyList(HttpApiCallBackArr<com.kalacheng.libuser.model.AppUsersVideoReportClassify> callback)
{
HttpClient.getInstance().post("/api/user/getUsersReportClassifyList","/api/user/getUsersReportClassifyList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppUsersVideoReportClassify_RetArr.class));

}





/**
 * 获取APP个人信息接口
 */
public static void info_index(HttpApiCallBack<com.kalacheng.libuser.model.ApiUserIndexResp> callback)
{
HttpClient.getInstance().post("/api/user/info_index","/api/user/info_index")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiUserIndexResp_Ret.class));

}





/**
 * 我守护的列表
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param userId 用户ID
 */
public static void getMyGuardList(com.kalacheng.buspersonalcenter.model_fun.AppUser_getMyGuardList _mdl,HttpApiCallBackArr<com.kalacheng.buscommon.model.GuardUserDto> callback)
{
HttpClient.getInstance().post("/api/user/getMyGuardList","/api/user/getMyGuardList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.params("userId", _mdl.userId)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.buscommon.model.GuardUserDto_RetArr.class));

}



/**
 * 我守护的列表
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param userId 用户ID
 */
public static void getMyGuardList(int pageIndex,int pageSize,long userId,HttpApiCallBackArr<com.kalacheng.buscommon.model.GuardUserDto> callback)
{
HttpClient.getInstance().post("/api/user/getMyGuardList","/api/user/getMyGuardList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.params("userId", userId)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.buscommon.model.GuardUserDto_RetArr.class));

}





/**
 * 查看签到数据
 */
public static void getSignInfo(HttpApiCallBack<com.kalacheng.libuser.model.ApiSignInDto> callback)
{
HttpClient.getInstance().post("/api/user/getSignInfo","/api/user/getSignInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiSignInDto_Ret.class));

}





/**
 * 购买查看联系方式的记录
 * @param changeType 交易类型 16：查看微信号  17：查看手机号
 * @param page 当前页
 * @param pageSize 每页的条数
 * @param userId 被查看的用户
 */
public static void getContactRecord(com.kalacheng.buspersonalcenter.model_fun.AppUser_getContactRecord _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.ChangeDto> callback)
{
HttpClient.getInstance().post("/api/user/getContactRecord","/api/user/getContactRecord")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("changeType", _mdl.changeType)
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.params("userId", _mdl.userId)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ChangeDto_RetArr.class));

}



/**
 * 购买查看联系方式的记录
 * @param changeType 交易类型 16：查看微信号  17：查看手机号
 * @param page 当前页
 * @param pageSize 每页的条数
 * @param userId 被查看的用户
 */
public static void getContactRecord(int changeType,int page,int pageSize,long userId,HttpApiCallBackArr<com.kalacheng.libuser.model.ChangeDto> callback)
{
HttpClient.getInstance().post("/api/user/getContactRecord","/api/user/getContactRecord")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("changeType", changeType)
.params("page", page)
.params("pageSize", pageSize)
.params("userId", userId)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ChangeDto_RetArr.class));

}





/**
 * 获取分享配置
 * @param terminalType 终端类型 1:安卓 2:苹果
 * @param type 类型 1:动态 2:直播间 3:App 4:短视频
 */
public static void share(com.kalacheng.buspersonalcenter.model_fun.AppUser_share _mdl,HttpApiCallBack<com.kalacheng.libuser.model.ApiShareConfig> callback)
{
HttpClient.getInstance().post("/api/user/share","/api/user/share")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("terminalType", _mdl.terminalType)
.params("type", _mdl.type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiShareConfig_Ret.class));

}



/**
 * 获取分享配置
 * @param terminalType 终端类型 1:安卓 2:苹果
 * @param type 类型 1:动态 2:直播间 3:App 4:短视频
 */
public static void share(int terminalType,int type,HttpApiCallBack<com.kalacheng.libuser.model.ApiShareConfig> callback)
{
HttpClient.getInstance().post("/api/user/share","/api/user/share")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("terminalType", terminalType)
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiShareConfig_Ret.class));

}





/**
 * 主播任务列表
 */
public static void anchorTaskList(HttpApiCallBackArr<com.kalacheng.buscommon.model.TaskDto> callback)
{
HttpClient.getInstance().post("/api/user/anchorTaskList","/api/user/anchorTaskList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.buscommon.model.TaskDto_RetArr.class));

}





/**
 * 直播间查看主播联系方式
 */
public static void takeAnchorContact(HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/takeAnchorContact","/api/user/takeAnchorContact")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 根据用户id用户信息
 * @param touid 要查询的人ID
 */
public static void getUserinfo(long touid,HttpApiCallBack<com.kalacheng.buscommon.model.ApiUserInfo> callback)
{
HttpClient.getInstance().post("/api/user/getUserinfo","/api/user/getUserinfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("touid", touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.model.ApiUserInfo_Ret.class));

}





/**
 * 我的排行
 */
public static void myIncomeRanking(HttpApiCallBack<com.kalacheng.buscommon.model.AppUserIncomeRankingDto> callback)
{
HttpClient.getInstance().post("/api/user/myIncomeRanking","/api/user/myIncomeRanking")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.model.AppUserIncomeRankingDto_Ret.class));

}





/**
 * 签到
 */
public static void signIn(HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/signIn","/api/user/signIn")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 首页关注列表
 * @param page 当前页
 * @param pageSize 每页的条数
 */
public static void lobbyAttention(com.kalacheng.buspersonalcenter.model_fun.AppUser_lobbyAttention _mdl,HttpApiCallBackPageArr<com.kalacheng.buscommon.model.LiveBean> callback)
{
HttpClient.getInstance().post("/api/user/lobbyAttention","/api/user/lobbyAttention")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.buscommon.model.LiveBean_RetPageArr.class));

}



/**
 * 首页关注列表
 * @param page 当前页
 * @param pageSize 每页的条数
 */
public static void lobbyAttention(int page,int pageSize,HttpApiCallBackPageArr<com.kalacheng.buscommon.model.LiveBean> callback)
{
HttpClient.getInstance().post("/api/user/lobbyAttention","/api/user/lobbyAttention")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", page)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.buscommon.model.LiveBean_RetPageArr.class));

}





/**
 * 用户关注列表
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param touid 要查看的用户ID
 */
public static void getAttenList(com.kalacheng.buspersonalcenter.model_fun.AppUser_getAttenList _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiUserAtten> callback)
{
HttpClient.getInstance().post("/api/user/getAttenList","/api/user/getAttenList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.params("touid", _mdl.touid)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiUserAtten_RetArr.class));

}



/**
 * 用户关注列表
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param touid 要查看的用户ID
 */
public static void getAttenList(int pageIndex,int pageSize,long touid,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiUserAtten> callback)
{
HttpClient.getInstance().post("/api/user/getAttenList","/api/user/getAttenList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.params("touid", touid)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiUserAtten_RetArr.class));

}





/**
 * 个人中心访问操作(去掉红点)
 */
public static void delVisit(HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/delVisit","/api/user/delVisit")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 我的--勿扰
 * @param type 类型 1:开启 2:关闭
 */
public static void isNotDisturb(int type,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/isNotDisturb","/api/user/isNotDisturb")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 我的声音
 * @param path 文件路径(调用文件上传接口获得)
 */
public static void myvoice(String path,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/myvoice","/api/user/myvoice")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("path", path)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 用户任务列表
 */
public static void userTaskList(HttpApiCallBackArr<com.kalacheng.buscommon.model.TaskDto> callback)
{
HttpClient.getInstance().post("/api/user/userTaskList","/api/user/userTaskList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.buscommon.model.TaskDto_RetArr.class));

}





/**
 * 修改密码
 * @param freshPwd 新密码
 * @param freshPwd2 再次输入新密码
 * @param oldPwd 原密码
 */
public static void update_pwd(com.kalacheng.buspersonalcenter.model_fun.AppUser_update_pwd _mdl,HttpApiCallBack<com.kalacheng.libbas.model.SingleString> callback)
{
HttpClient.getInstance().post("/api/user/update_pwd","/api/user/update_pwd")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("freshPwd", _mdl.freshPwd)
.params("freshPwd2", _mdl.freshPwd2)
.params("oldPwd", _mdl.oldPwd)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.SingleString_Ret.class));

}



/**
 * 修改密码
 * @param freshPwd 新密码
 * @param freshPwd2 再次输入新密码
 * @param oldPwd 原密码
 */
public static void update_pwd(String freshPwd,String freshPwd2,String oldPwd,HttpApiCallBack<com.kalacheng.libbas.model.SingleString> callback)
{
HttpClient.getInstance().post("/api/user/update_pwd","/api/user/update_pwd")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("freshPwd", freshPwd)
.params("freshPwd2", freshPwd2)
.params("oldPwd", oldPwd)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.SingleString_Ret.class));

}





/**
 * 查询全部标签
 */
public static void allTabs(HttpApiCallBackArr<com.kalacheng.buscommon.model.TabTypeDto> callback)
{
HttpClient.getInstance().post("/api/user/allTabs","/api/user/allTabs")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.buscommon.model.TabTypeDto_RetArr.class));

}





/**
 * 获取标签
 * @param type 类型1兴趣爱好， 2性格标签， 3自我描述，4关注主题， 5用户评价标签
 */
public static void getlabels(int type,HttpApiCallBackArr<com.kalacheng.libuser.model.AppTabInfo> callback)
{
HttpClient.getInstance().post("/api/user/getlabels","/api/user/getlabels")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("type", type)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppTabInfo_RetArr.class));

}





/**
 * 个人中心访问人是否有未读(红点)：no_use 访问未读人数 0没有未读
 */
public static void isVisit(HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/isVisit","/api/user/isVisit")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 查询是否存在我的声音
 */
public static void is_myvoice(HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/is_myvoice","/api/user/is_myvoice")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 查看联系方式
 * @param type 联系方式类型 1：手机号  2：微信号
 * @param userId 被查看的用户ID
 */
public static void payViewContact(com.kalacheng.buspersonalcenter.model_fun.AppUser_payViewContact _mdl,HttpApiCallBack<com.kalacheng.libbas.model.SingleString> callback)
{
HttpClient.getInstance().post("/api/user/payViewContact","/api/user/payViewContact")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("type", _mdl.type)
.params("userId", _mdl.userId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.SingleString_Ret.class));

}



/**
 * 查看联系方式
 * @param type 联系方式类型 1：手机号  2：微信号
 * @param userId 被查看的用户ID
 */
public static void payViewContact(int type,long userId,HttpApiCallBack<com.kalacheng.libbas.model.SingleString> callback)
{
HttpClient.getInstance().post("/api/user/payViewContact","/api/user/payViewContact")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("type", type)
.params("userId", userId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.SingleString_Ret.class));

}





/**
 * 修改个人兴趣标签
 * @param value 'tabId':'tab名','tabId':'tab名'  '1':'王者荣耀','7':'热情似火','8':'嫉恶如仇'
 */
public static void updateInterest(String value,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/updateInterest","/api/user/updateInterest")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("value", value)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 贵族隐身特权修改
 * @param chargeShow 充值隐身 0不隐身1隐身
 * @param devoteShow 贡献榜排行隐身 0不隐身1隐身
 * @param joinRoomShow 进入直播间隐身 0不隐身1隐身
 */
public static void VipPrivilege(com.kalacheng.buspersonalcenter.model_fun.AppUser_VipPrivilege _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/VipPrivilege","/api/user/VipPrivilege")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("chargeShow", _mdl.chargeShow)
.params("devoteShow", _mdl.devoteShow)
.params("joinRoomShow", _mdl.joinRoomShow)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 贵族隐身特权修改
 * @param chargeShow 充值隐身 0不隐身1隐身
 * @param devoteShow 贡献榜排行隐身 0不隐身1隐身
 * @param joinRoomShow 进入直播间隐身 0不隐身1隐身
 */
public static void VipPrivilege(int chargeShow,int devoteShow,int joinRoomShow,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/VipPrivilege","/api/user/VipPrivilege")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("chargeShow", chargeShow)
.params("devoteShow", devoteShow)
.params("joinRoomShow", joinRoomShow)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 拉黑用户
 * @param touid 被拉黑用户
 */
public static void usersBlack(long touid,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/usersBlack","/api/user/usersBlack")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("touid", touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 绑定
 * @param code 邀请码
 */
public static void binding(String code,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/binding","/api/user/binding")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("code", code)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 一对一付费通话设置
 * @param liveState 一对一在线状态  0在线1忙碌2离开3通话中
 * @param openState 一对一打开状态 0：默认，不打开  1：打开
 * @param poster 海报, 修改时可为null
 * @param video 视频地址, 修改时可为null
 * @param videoCoin 视频通话费用/min, 修改时可为null
 * @param videoImg 视频封面地址, 修改时可为null
 * @param voice 录音地址, 修改时可为null
 * @param voiceCoin 语音通话费用/min, 修改时可为null
 */
public static void setPayCallOneVsOne(com.kalacheng.buspersonalcenter.model_fun.AppUser_setPayCallOneVsOne _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/setPayCallOneVsOne","/api/user/setPayCallOneVsOne")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveState", _mdl.liveState)
.params("openState", _mdl.openState)
.params("poster", _mdl.poster)
.params("video", _mdl.video)
.params("videoCoin", _mdl.videoCoin)
.params("videoImg", _mdl.videoImg)
.params("voice", _mdl.voice)
.params("voiceCoin", _mdl.voiceCoin)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 一对一付费通话设置
 * @param liveState 一对一在线状态  0在线1忙碌2离开3通话中
 * @param openState 一对一打开状态 0：默认，不打开  1：打开
 * @param poster 海报, 修改时可为null
 * @param video 视频地址, 修改时可为null
 * @param videoCoin 视频通话费用/min, 修改时可为null
 * @param videoImg 视频封面地址, 修改时可为null
 * @param voice 录音地址, 修改时可为null
 * @param voiceCoin 语音通话费用/min, 修改时可为null
 */
public static void setPayCallOneVsOne(int liveState,int openState,String poster,String video,double videoCoin,String videoImg,String voice,double voiceCoin,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/user/setPayCallOneVsOne","/api/user/setPayCallOneVsOne")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveState", liveState)
.params("openState", openState)
.params("poster", poster)
.params("video", video)
.params("videoCoin", videoCoin)
.params("videoImg", videoImg)
.params("voice", voice)
.params("voiceCoin", voiceCoin)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}


}
