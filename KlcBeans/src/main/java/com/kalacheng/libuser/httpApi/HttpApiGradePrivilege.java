package com.kalacheng.libuser.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 等级特权
 */
public class HttpApiGradePrivilege
{




/**
 * 查询用户/财富/主播 等级特权信息
 * @param type type
 */
public static void privlilegeInfo(int type,HttpApiCallBack<com.kalacheng.libuser.model.AppUserPrivilegeDTO> callback)
{
HttpClient.getInstance().post("/api/h5/privlilegeInfo","/api/h5/privlilegeInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppUserPrivilegeDTO_Ret.class));

}


}
