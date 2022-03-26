package com.kalacheng.libuser.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 勋章支持接口
 */
public class HttpApiMedal
{




/**
 * 获取用户已经持有的和未持有的所有勋章
 * @param userId 被查看的用户ID
 */
public static void getMyAllMedal(long userId,HttpApiCallBack<com.kalacheng.libuser.model.MedalDto> callback)
{
HttpClient.getInstance().post("/api/medal/getMyAllMedal","/api/medal/getMyAllMedal")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("userId", userId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.MedalDto_Ret.class));

}





/**
 * 用户礼物勋章墙
 * @param userId 被查看的用户ID
 */
public static void getUserGiftMedal(long userId,HttpApiCallBackArr<com.kalacheng.buscommon.model.GiftWallDto> callback)
{
HttpClient.getInstance().post("/api/medal/getUserGiftMedal","/api/medal/getUserGiftMedal")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("userId", userId)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.buscommon.model.GiftWallDto_RetArr.class));

}


}
