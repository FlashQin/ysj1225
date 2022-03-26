package com.kalacheng.libuser.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 官方消息
 */
public class HttpApiOfficialNews
{




/**
 * 查询官方消息列表
 * @param pageIndex pageIndex
 * @param pageSize pageSize
 */
public static void getOfficialNewsList(com.kalacheng.libuser.model_fun.OfficialNews_getOfficialNewsList _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.AppOfficialNewsDTO> callback)
{
HttpClient.getInstance().post("/api/officialNews/getOfficialNewsList","/api/officialNews/getOfficialNewsList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppOfficialNewsDTO_RetArr.class));

}



/**
 * 查询官方消息列表
 * @param pageIndex pageIndex
 * @param pageSize pageSize
 */
public static void getOfficialNewsList(int pageIndex,int pageSize,HttpApiCallBackArr<com.kalacheng.libuser.model.AppOfficialNewsDTO> callback)
{
HttpClient.getInstance().post("/api/officialNews/getOfficialNewsList","/api/officialNews/getOfficialNewsList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppOfficialNewsDTO_RetArr.class));

}





/**
 * 查看官方消息
 * @param id 消息id
 */
public static void messageViewed(long id,HttpApiCallBack<com.kalacheng.libuser.model.AppOfficialNewsDTO> callback)
{
HttpClient.getInstance().post("/api/officialNews/messageViewed","/api/officialNews/messageViewed")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("id", id)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppOfficialNewsDTO_Ret.class));

}


}
