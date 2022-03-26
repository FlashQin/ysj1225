package com.kalacheng.busfinance.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 系统支持接口
 */
public class HttpApiSupport
{




/**
 * 分享成功
 * @param roomId 房间ID， 没有就默认0
 * @param shortVideoId 短视频id
 * @param type 分享类型：1：视频直播间  2：语音直播间 3 分享短视频   4：待定，先别管其他了
 */
public static void shareSuccess(com.kalacheng.busfinance.model_fun.Support_shareSuccess _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/support/shareSuccess","/api/support/shareSuccess")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("roomId", _mdl.roomId)
.params("shortVideoId", _mdl.shortVideoId)
.params("type", _mdl.type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 分享成功
 * @param roomId 房间ID， 没有就默认0
 * @param shortVideoId 短视频id
 * @param type 分享类型：1：视频直播间  2：语音直播间 3 分享短视频   4：待定，先别管其他了
 */
public static void shareSuccess(long roomId,long shortVideoId,int type,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/support/shareSuccess","/api/support/shareSuccess")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("roomId", roomId)
.params("shortVideoId", shortVideoId)
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 根据生日获取星座
 * @param date 生日
 */
public static void getStarByDate(String date,HttpApiCallBack<com.kalacheng.libbas.model.SingleString> callback)
{
HttpClient.getInstance().post("/api/support/getStarByDate","/api/support/getStarByDate")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("date", date)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.SingleString_Ret.class));

}

//  /api/support/getShareImg
//  /api/support/getShareImg  此函数没有开放POST请求。




/**
 * 和TA第一次打招呼
 * @param toUserId TA的ID
 */
public static void firstSayHello(long toUserId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/support/firstSayHello","/api/support/firstSayHello")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("toUserId", toUserId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 获取邀请码
 */
public static void getInviteCodeInfo(HttpApiCallBack<com.kalacheng.libuser.model.InviteDto> callback)
{
HttpClient.getInstance().post("/api/support/getInviteCodeInfo","/api/support/getInviteCodeInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.InviteDto_Ret.class));

}





/**
 * 万能通用二维码生成器
 * @param content 二维码的内容：如链接地址，邀请码等
 * @param height 二维码高度
 * @param width 二维码宽度
 */
public static void qrCode(com.kalacheng.busfinance.model_fun.Support_qrCode _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/support/qrCode","/api/support/qrCode")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("content", _mdl.content)
.params("height", _mdl.height)
.params("width", _mdl.width)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 万能通用二维码生成器
 * @param content 二维码的内容：如链接地址，邀请码等
 * @param height 二维码高度
 * @param width 二维码宽度
 */
public static void qrCode(String content,int height,int width,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/support/qrCode","/api/support/qrCode")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("content", content)
.params("height", height)
.params("width", width)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 获取查询时间
 */
public static void getSearchDate(HttpApiCallBackArr<com.kalacheng.libuser.model.CfgSearchDate> callback)
{
HttpClient.getInstance().post("/api/support/getSearchDate","/api/support/getSearchDate")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.CfgSearchDate_RetArr.class));

}


}
