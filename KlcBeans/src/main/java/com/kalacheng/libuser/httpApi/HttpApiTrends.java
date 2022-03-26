package com.kalacheng.libuser.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 第一次动态和动态接口
 */
public class HttpApiTrends
{




/**
 * 第一次动态列表
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 */
public static void firstTrendsList(com.kalacheng.libuser.model_fun.Trends_firstTrendsList _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.AppTrendsRecord> callback)
{
HttpClient.getInstance().post("/api/trends/firstTrendsList","/api/trends/firstTrendsList")
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
 * 第一次动态列表
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 */
public static void firstTrendsList(int pageIndex,int pageSize,HttpApiCallBackArr<com.kalacheng.libuser.model.AppTrendsRecord> callback)
{
HttpClient.getInstance().post("/api/trends/firstTrendsList","/api/trends/firstTrendsList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppTrendsRecord_RetArr.class));

}


}
