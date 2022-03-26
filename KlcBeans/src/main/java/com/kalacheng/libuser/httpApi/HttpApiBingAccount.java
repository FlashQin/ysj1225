package com.kalacheng.libuser.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 用户账号
 */
public class HttpApiBingAccount
{




/**
 * 验证当前登录密码
 * @param password 登录密码
 */
public static void verifyLoginPwd(String password,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/bind/verifyLoginPwd","/api/bind/verifyLoginPwd")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("password", password)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 更换绑定手机号
 * @param code 验证码
 * @param mobile 手机号码
 * @param source 注册来源android/ios
 */
public static void updateBindMobile(com.kalacheng.libuser.model_fun.BingAccount_updateBindMobile _mdl,HttpApiCallBack<com.kalacheng.libbas.model.UserToken> callback)
{
HttpClient.getInstance().post("/api/bind/updateBindMobile","/api/bind/updateBindMobile")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("code", _mdl.code)
.params("mobile", _mdl.mobile)
.params("source", _mdl.source)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.UserToken_Ret.class));

}



/**
 * 更换绑定手机号
 * @param code 验证码
 * @param mobile 手机号码
 * @param source 注册来源android/ios
 */
public static void updateBindMobile(String code,String mobile,String source,HttpApiCallBack<com.kalacheng.libbas.model.UserToken> callback)
{
HttpClient.getInstance().post("/api/bind/updateBindMobile","/api/bind/updateBindMobile")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("code", code)
.params("mobile", mobile)
.params("source", source)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.UserToken_Ret.class));

}





/**
 * 用户注销验证
 */
public static void logoutVerification(HttpApiCallBack<com.kalacheng.buscommon.model.UserLogoutVerificationDTO> callback)
{
HttpClient.getInstance().post("/api/bind/logoutVerification","/api/bind/logoutVerification")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.model.UserLogoutVerificationDTO_Ret.class));

}


}
