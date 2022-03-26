package com.kalacheng.busnobility.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 贵族体系礼物接口API
 */
public class HttpApiNobLiveGift
{




/**
 * 获取贵族体系礼物
 * @param type 礼物类型 -1:查所有礼物 0:普通礼物 1:粉丝团(豪华礼物) 2:守护(专属礼物) 3:贵族礼物(特殊礼物) 4:背包礼物
 */
public static void getGiftList(int type,HttpApiCallBackArr<com.kalacheng.busnobility.model.ApiNobLiveGift> callback)
{
HttpClient.getInstance().post("/api/live/getGiftList","/api/live/getGiftList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("type", type)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busnobility.model.ApiNobLiveGift_RetArr.class));

}





/**
 * 发送礼物HTTP
 * @param anchorId 主播id
 * @param backid 背包id(没有传-1)
 * @param giftId 礼物id
 * @param number 礼物个数
 * @param roomId 房间id(没有传-1)
 * @param shortVideoId 短视频id(没有传-1)
 * @param showid 直播标识(在直播间时传入)
 * @param type 送礼物类型 1:多人视频 2:语音直播 3:一对一 4:派对 5:电台 6:直播购物 7:私聊礼物 8:群聊礼物 9:短视频
 * @param uid 房主(开播方)ID (没有传-1)
 */
public static void giftSender(com.kalacheng.busnobility.model_fun.NobLiveGift_giftSender _mdl,HttpApiCallBack<com.kalacheng.libuser.model.ApiGiftSender> callback)
{
HttpClient.getInstance().post("/api/live/giftSender","/api/live/giftSender")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("backid", _mdl.backid)
.params("giftId", _mdl.giftId)
.params("number", _mdl.number)
.params("roomId", _mdl.roomId)
.params("shortVideoId", _mdl.shortVideoId)
.params("showid", _mdl.showid)
.params("type", _mdl.type)
.params("uid", _mdl.uid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiGiftSender_Ret.class));

}



/**
 * 发送礼物HTTP
 * @param anchorId 主播id
 * @param backid 背包id(没有传-1)
 * @param giftId 礼物id
 * @param number 礼物个数
 * @param roomId 房间id(没有传-1)
 * @param shortVideoId 短视频id(没有传-1)
 * @param showid 直播标识(在直播间时传入)
 * @param type 送礼物类型 1:多人视频 2:语音直播 3:一对一 4:派对 5:电台 6:直播购物 7:私聊礼物 8:群聊礼物 9:短视频
 * @param uid 房主(开播方)ID (没有传-1)
 */
public static void giftSender(long anchorId,long backid,long giftId,int number,long roomId,long shortVideoId,String showid,int type,long uid,HttpApiCallBack<com.kalacheng.libuser.model.ApiGiftSender> callback)
{
HttpClient.getInstance().post("/api/live/giftSender","/api/live/giftSender")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("backid", backid)
.params("giftId", giftId)
.params("number", number)
.params("roomId", roomId)
.params("shortVideoId", shortVideoId)
.params("showid", showid)
.params("type", type)
.params("uid", uid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiGiftSender_Ret.class));

}





/**
 * 我的的背包礼物
 */
public static void getMyGiftList(HttpApiCallBackArr<com.kalacheng.libuser.model.NobLiveGift> callback)
{
HttpClient.getInstance().post("/api/live/getMyGiftList","/api/live/getMyGiftList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.NobLiveGift_RetArr.class));

}


}
