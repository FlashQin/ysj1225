package com.kalacheng.busgraderight.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 贵族控制器
 */
public class HttpApiNoble
{




/**
 * 查询贵族特权
 */
public static void vipPrivilegeShow(HttpApiCallBack<com.kalacheng.libuser.model.VipPrivilegeDto> callback)
{
HttpClient.getInstance().post("/api/noble/vipPrivilegeShow","/api/noble/vipPrivilegeShow")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.VipPrivilegeDto_Ret.class));

}


}
