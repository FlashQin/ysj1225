package com.kalacheng.libuser.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 用户登录接口API
 */
public class HttpApiAppLogin
{




/**
 * app用户注册
 * @param code 验证码
 * @param mobile 手机号码
 * @param password 密码
 * @param source 注册来源android/ios
 */
public static void register(com.kalacheng.libuser.model_fun.AppLogin_register _mdl,HttpApiCallBack<com.kalacheng.libbas.model.UserToken> callback)
{
HttpClient.getInstance().post("/api/login/register","/api/login/register")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("code", _mdl.code)
.params("mobile", _mdl.mobile)
.params("password", _mdl.password)
.params("source", _mdl.source)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.UserToken_Ret.class));

}



/**
 * app用户注册
 * @param code 验证码
 * @param mobile 手机号码
 * @param password 密码
 * @param source 注册来源android/ios
 */
public static void register(String code,String mobile,String password,String source,HttpApiCallBack<com.kalacheng.libbas.model.UserToken> callback)
{
HttpClient.getInstance().post("/api/login/register","/api/login/register")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("code", code)
.params("mobile", mobile)
.params("password", password)
.params("source", source)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.UserToken_Ret.class));

}





/**
 * 包含用户等级，财富等级，主播等级，贵族等级
 */
public static void getGradeList(HttpApiCallBackArr<com.kalacheng.libuser.model.AppGrade> callback)
{
HttpClient.getInstance().post("/api/login/getGradeList","/api/login/getGradeList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppGrade_RetArr.class));

}





/**
 * 获取轮播图列表 启动图:(1,0) 直播:(2,1) 推荐:(3,12) 附近:(4,13) 一对一:(5,n) 短视频看点:(6,7) 视频分类:(6,8) 动态:(21,21) 语聊(15,1) 电台(16,16) 直播购(17,17)
 * @param pid 一级分类
 * @param type 二级分类: 0:启动图 1:直播 2:推荐 3:附近
 */
public static void adslist(com.kalacheng.libuser.model_fun.AppLogin_adslist _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.AppAds> callback)
{
HttpClient.getInstance().post("/api/login/adslist","/api/login/adslist")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pid", _mdl.pid)
.params("type", _mdl.type)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppAds_RetArr.class));

}



/**
 * 获取轮播图列表 启动图:(1,0) 直播:(2,1) 推荐:(3,12) 附近:(4,13) 一对一:(5,n) 短视频看点:(6,7) 视频分类:(6,8) 动态:(21,21) 语聊(15,1) 电台(16,16) 直播购(17,17)
 * @param pid 一级分类
 * @param type 二级分类: 0:启动图 1:直播 2:推荐 3:附近
 */
public static void adslist(int pid,int type,HttpApiCallBackArr<com.kalacheng.libuser.model.AppAds> callback)
{
HttpClient.getInstance().post("/api/login/adslist","/api/login/adslist")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pid", pid)
.params("type", type)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppAds_RetArr.class));

}





/**
 * 手机号码和密码登录
 * @param appVersion 当前版本号
 * @param appVersionCode 当前版本code
 * @param mobile 手机号码
 * @param password 密码
 * @param phoneFirm 手机厂商
 * @param phoneModel 手机型号
 * @param phoneSystem 手机系统
 * @param phoneUuid 手机唯一标识
 */
public static void login(com.kalacheng.libuser.model_fun.AppLogin_login _mdl,HttpApiCallBack<com.kalacheng.buscommon.model.ApiUserInfo> callback)
{
HttpClient.getInstance().post("/api/login/login","/api/login/login")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("appVersion", _mdl.appVersion)
.params("appVersionCode", _mdl.appVersionCode)
.params("mobile", _mdl.mobile)
.params("password", _mdl.password)
.params("phoneFirm", _mdl.phoneFirm)
.params("phoneModel", _mdl.phoneModel)
.params("phoneSystem", _mdl.phoneSystem)
.params("phoneUuid", _mdl.phoneUuid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.model.ApiUserInfo_Ret.class));

}



/**
 * 手机号码和密码登录
 * @param appVersion 当前版本号
 * @param appVersionCode 当前版本code
 * @param mobile 手机号码
 * @param password 密码
 * @param phoneFirm 手机厂商
 * @param phoneModel 手机型号
 * @param phoneSystem 手机系统
 * @param phoneUuid 手机唯一标识
 */
public static void login(String appVersion,String appVersionCode,String mobile,String password,String phoneFirm,String phoneModel,String phoneSystem,String phoneUuid,HttpApiCallBack<com.kalacheng.buscommon.model.ApiUserInfo> callback)
{
HttpClient.getInstance().post("/api/login/login","/api/login/login")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("appVersion", appVersion)
.params("appVersionCode", appVersionCode)
.params("mobile", mobile)
.params("password", password)
.params("phoneFirm", phoneFirm)
.params("phoneModel", phoneModel)
.params("phoneSystem", phoneSystem)
.params("phoneUuid", phoneUuid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.model.ApiUserInfo_Ret.class));

}

//  /api/login/upJiguang
//  /api/login/upJiguang  此函数没有开放POST请求。




/**
 * 第三方登录
 * @param appVersion 当前版本号
 * @param appVersionCode 当前版本code
 * @param nickname 昵称
 * @param openid 第三方标识
 * @param phoneFirm 手机厂商
 * @param phoneModel 手机型号
 * @param phoneSystem 手机系统
 * @param phoneUuid 手机唯一标识
 * @param pic 图片地址
 * @param sex 性别 0:保密 1:男 2:女
 * @param source 注册来源android/ios
 * @param type 类型 1:QQ 2:微信
 */
public static void ChartLogin(com.kalacheng.libuser.model_fun.AppLogin_ChartLogin _mdl,HttpApiCallBack<com.kalacheng.buscommon.model.ApiUserInfo> callback)
{
HttpClient.getInstance().post("/api/login/ChartLogin","/api/login/ChartLogin")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("appVersion", _mdl.appVersion)
.params("appVersionCode", _mdl.appVersionCode)
.params("nickname", _mdl.nickname)
.params("openid", _mdl.openid)
.params("phoneFirm", _mdl.phoneFirm)
.params("phoneModel", _mdl.phoneModel)
.params("phoneSystem", _mdl.phoneSystem)
.params("phoneUuid", _mdl.phoneUuid)
.params("pic", _mdl.pic)
.params("sex", _mdl.sex)
.params("source", _mdl.source)
.params("type", _mdl.type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.model.ApiUserInfo_Ret.class));

}



/**
 * 第三方登录
 * @param appVersion 当前版本号
 * @param appVersionCode 当前版本code
 * @param nickname 昵称
 * @param openid 第三方标识
 * @param phoneFirm 手机厂商
 * @param phoneModel 手机型号
 * @param phoneSystem 手机系统
 * @param phoneUuid 手机唯一标识
 * @param pic 图片地址
 * @param sex 性别 0:保密 1:男 2:女
 * @param source 注册来源android/ios
 * @param type 类型 1:QQ 2:微信
 */
public static void ChartLogin(String appVersion,String appVersionCode,String nickname,String openid,String phoneFirm,String phoneModel,String phoneSystem,String phoneUuid,String pic,int sex,String source,int type,HttpApiCallBack<com.kalacheng.buscommon.model.ApiUserInfo> callback)
{
HttpClient.getInstance().post("/api/login/ChartLogin","/api/login/ChartLogin")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("appVersion", appVersion)
.params("appVersionCode", appVersionCode)
.params("nickname", nickname)
.params("openid", openid)
.params("phoneFirm", phoneFirm)
.params("phoneModel", phoneModel)
.params("phoneSystem", phoneSystem)
.params("phoneUuid", phoneUuid)
.params("pic", pic)
.params("sex", sex)
.params("source", source)
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.model.ApiUserInfo_Ret.class));

}





/**
 * 获取勋章图片地址
 */
public static void getMedalList(HttpApiCallBackArr<com.kalacheng.libuser.model.AppMedal> callback)
{
HttpClient.getInstance().post("/api/login/getMedalList","/api/login/getMedalList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppMedal_RetArr.class));

}





/**
 * 获取APP版本更新信息
 */
public static void getAppUpdateInfo(HttpApiCallBack<com.kalacheng.libuser.model.AppUpdateInfo> callback)
{
HttpClient.getInstance().post("/api/login/getAppUpdateInfo","/api/login/getAppUpdateInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppUpdateInfo_Ret.class));

}





/**
 * app获取验证码
 * @param smsType 类型1注册2找回密码3验证码登录4用户注销5绑定手机号6青少年验证码7修改手机号8:微信公众号登录
 * @param tel 手机号码
 */
public static void getVerCode(com.kalacheng.libuser.model_fun.AppLogin_getVerCode _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/login/getVerCode","/api/login/getVerCode")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("smsType", _mdl.smsType)
.params("tel", _mdl.tel)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * app获取验证码
 * @param smsType 类型1注册2找回密码3验证码登录4用户注销5绑定手机号6青少年验证码7修改手机号8:微信公众号登录
 * @param tel 手机号码
 */
public static void getVerCode(int smsType,String tel,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/login/getVerCode","/api/login/getVerCode")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("smsType", smsType)
.params("tel", tel)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 忘记密码
 * @param code 短信验证码
 * @param freshPwd 新密码
 * @param freshPwd2 再次输入新密码
 * @param phone 手机号码
 */
public static void forget_pwd(com.kalacheng.libuser.model_fun.AppLogin_forget_pwd _mdl,HttpApiCallBack<com.kalacheng.libbas.model.SingleString> callback)
{
HttpClient.getInstance().post("/api/login/forget_pwd","/api/login/forget_pwd")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("code", _mdl.code)
.params("freshPwd", _mdl.freshPwd)
.params("freshPwd2", _mdl.freshPwd2)
.params("phone", _mdl.phone)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.SingleString_Ret.class));

}



/**
 * 忘记密码
 * @param code 短信验证码
 * @param freshPwd 新密码
 * @param freshPwd2 再次输入新密码
 * @param phone 手机号码
 */
public static void forget_pwd(String code,String freshPwd,String freshPwd2,String phone,HttpApiCallBack<com.kalacheng.libbas.model.SingleString> callback)
{
HttpClient.getInstance().post("/api/login/forget_pwd","/api/login/forget_pwd")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("code", code)
.params("freshPwd", freshPwd)
.params("freshPwd2", freshPwd2)
.params("phone", phone)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.SingleString_Ret.class));

}





/**
 * 手机验证码登录
 * @param appVersion 当前版本号
 * @param appVersionCode 当前版本code
 * @param code 验证码
 * @param mobile 手机号码
 * @param phoneFirm 手机厂商
 * @param phoneModel 手机型号
 * @param phoneSystem 手机系统
 * @param phoneUuid 手机唯一标识
 * @param source 注册来源android/ios
 */
public static void phoneCodeLogin(com.kalacheng.libuser.model_fun.AppLogin_phoneCodeLogin _mdl,HttpApiCallBack<com.kalacheng.buscommon.model.ApiUserInfo> callback)
{
HttpClient.getInstance().post("/api/login/phoneCodeLogin","/api/login/phoneCodeLogin")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("appVersion", _mdl.appVersion)
.params("appVersionCode", _mdl.appVersionCode)
.params("code", _mdl.code)
.params("mobile", _mdl.mobile)
.params("phoneFirm", _mdl.phoneFirm)
.params("phoneModel", _mdl.phoneModel)
.params("phoneSystem", _mdl.phoneSystem)
.params("phoneUuid", _mdl.phoneUuid)
.params("source", _mdl.source)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.model.ApiUserInfo_Ret.class));

}



/**
 * 手机验证码登录
 * @param appVersion 当前版本号
 * @param appVersionCode 当前版本code
 * @param code 验证码
 * @param mobile 手机号码
 * @param phoneFirm 手机厂商
 * @param phoneModel 手机型号
 * @param phoneSystem 手机系统
 * @param phoneUuid 手机唯一标识
 * @param source 注册来源android/ios
 */
public static void phoneCodeLogin(String appVersion,String appVersionCode,String code,String mobile,String phoneFirm,String phoneModel,String phoneSystem,String phoneUuid,String source,HttpApiCallBack<com.kalacheng.buscommon.model.ApiUserInfo> callback)
{
HttpClient.getInstance().post("/api/login/phoneCodeLogin","/api/login/phoneCodeLogin")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("appVersion", appVersion)
.params("appVersionCode", appVersionCode)
.params("code", code)
.params("mobile", mobile)
.params("phoneFirm", phoneFirm)
.params("phoneModel", phoneModel)
.params("phoneSystem", phoneSystem)
.params("phoneUuid", phoneUuid)
.params("source", source)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.model.ApiUserInfo_Ret.class));

}

//  /api/login/appSite
//  /api/login/appSite  此函数没有开放POST请求。




/**
 * 初始化APP公共设置
 */
public static void getConfig(HttpApiCallBack<com.kalacheng.libuser.model.APPConfig> callback)
{
HttpClient.getInstance().post("/api/login/getConfig","/api/login/getConfig")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.APPConfig_Ret.class));

}

//  /api/login/sysn
//  /api/login/sysn  此函数没有开放POST请求。




/**
 * 获取礼物列表
 */
public static void giftList(HttpApiCallBackArr<com.kalacheng.libuser.model.NobLiveGift> callback)
{
HttpClient.getInstance().post("/api/login/giftList","/api/login/giftList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.NobLiveGift_RetArr.class));

}


}
