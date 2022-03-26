package com.kalacheng.libuser.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 青少年模式
 */
public class HttpApiYunthModel
{




/**
 * 开启/关闭青少年模式
 * @param isYouthModel 1开启，2关闭
 * @param pwd 密码
 */
public static void setYunthModel(com.kalacheng.libuser.model_fun.YunthModel_setYunthModel _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/h5/setYunthModel","/api/h5/setYunthModel")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("isYouthModel", _mdl.isYouthModel)
.params("pwd", _mdl.pwd)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 开启/关闭青少年模式
 * @param isYouthModel 1开启，2关闭
 * @param pwd 密码
 */
public static void setYunthModel(int isYouthModel,String pwd,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/h5/setYunthModel","/api/h5/setYunthModel")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("isYouthModel", isYouthModel)
.params("pwd", pwd)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 修改青少年密码
 * @param password 青少年密码
 */
public static void updateYunthPwd(String password,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/h5/updateYunthPwd","/api/h5/updateYunthPwd")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("password", password)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 账号密码验证
 * @param password 当前账号密码
 */
public static void yunthAuthByAccount(String password,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/h5/yunthAuthByAccount","/api/h5/yunthAuthByAccount")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("password", password)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 通过验证码验证
 * @param code 验证码
 * @param mobile 手机号
 */
public static void yunthAuthByCode(com.kalacheng.libuser.model_fun.YunthModel_yunthAuthByCode _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/h5/yunthAuthByCode","/api/h5/yunthAuthByCode")
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
 * 通过验证码验证
 * @param code 验证码
 * @param mobile 手机号
 */
public static void yunthAuthByCode(String code,String mobile,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/h5/yunthAuthByCode","/api/h5/yunthAuthByCode")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("code", code)
.params("mobile", mobile)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}


}
