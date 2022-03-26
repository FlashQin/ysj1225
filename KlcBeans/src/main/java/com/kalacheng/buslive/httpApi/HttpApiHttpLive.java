package com.kalacheng.buslive.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 视频直播间相关创建房间及加入房间接口
 */
public class HttpApiHttpLive
{




/**
 * http方式加入房间
 * @param roomId 房间id
 * @param roomType 房间类型 0:是一般直播 1:是私密直播 2:是收费直播 3:是计时直播 4:贵族房间
 * @param roomTypeVal 如果是密码房间传入密码
 */
public static void joinRoom(com.kalacheng.buslive.model_fun.HttpLive_joinRoom _mdl,HttpApiCallBack<com.kalacheng.libuser.model.AppJoinRoomVO> callback)
{
HttpClient.getInstance().post("/api/httplive/joinRoom","/api/httplive/joinRoom")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("roomId", _mdl.roomId)
.params("roomType", _mdl.roomType)
.params("roomTypeVal", _mdl.roomTypeVal)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppJoinRoomVO_Ret.class));

}



/**
 * http方式加入房间
 * @param roomId 房间id
 * @param roomType 房间类型 0:是一般直播 1:是私密直播 2:是收费直播 3:是计时直播 4:贵族房间
 * @param roomTypeVal 如果是密码房间传入密码
 */
public static void joinRoom(long roomId,int roomType,String roomTypeVal,HttpApiCallBack<com.kalacheng.libuser.model.AppJoinRoomVO> callback)
{
HttpClient.getInstance().post("/api/httplive/joinRoom","/api/httplive/joinRoom")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("roomId", roomId)
.params("roomType", roomType)
.params("roomTypeVal", roomTypeVal)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppJoinRoomVO_Ret.class));

}





/**
 * http方式离开房间
 * @param roomId 房间id
 */
public static void leaveRoomOpt(long roomId,HttpApiCallBack<com.kalacheng.libuser.model.ApiLeaveRoom> callback)
{
HttpClient.getInstance().post("/api/httplive/leaveRoomOpt","/api/httplive/leaveRoomOpt")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("roomId", roomId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiLeaveRoom_Ret.class));

}





/**
 * no_use为1时是已扣费过0为未扣费
 * @param showid showid
 */
public static void isPayRoom(String showid,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httplive/isPayRoom","/api/httplive/isPayRoom")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("showid", showid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * http方式创建房间code为3时请先认证
 * @param address address
 * @param avatar avatar
 * @param avatarThumb avatarThumb
 * @param channelId channelId
 * @param city city
 * @param lat lat
 * @param liveFunction liveFunction
 * @param liveType 直播类型
 * @param lng lng
 * @param nickname nickname
 * @param province province
 * @param pull pull
 * @param roomType 房间类型 0:是一般直播 1:是私密直播 2:是收费直播 3:是计时直播 4:贵族房间
 * @param roomTypeVal roomTypeVal
 * @param shopRoomLabel shopRoomLabel
 * @param thumb thumb
 * @param title title
 */
public static void openLive(com.kalacheng.buslive.model_fun.HttpLive_openLive _mdl,HttpApiCallBack<com.kalacheng.libuser.model.AppJoinRoomVO> callback)
{
HttpClient.getInstance().post("/api/httplive/openLive","/api/httplive/openLive")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("address", _mdl.address)
.params("avatar", _mdl.avatar)
.params("avatarThumb", _mdl.avatarThumb)
.params("channelId", _mdl.channelId)
.params("city", _mdl.city)
.params("lat", _mdl.lat)
.params("liveFunction", _mdl.liveFunction)
.params("liveType", _mdl.liveType)
.params("lng", _mdl.lng)
.params("nickname", _mdl.nickname)
.params("province", _mdl.province)
.params("pull", _mdl.pull)
.params("roomType", _mdl.roomType)
.params("roomTypeVal", _mdl.roomTypeVal)
.params("shopRoomLabel", _mdl.shopRoomLabel)
.params("thumb", _mdl.thumb)
.params("title", _mdl.title)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppJoinRoomVO_Ret.class));

}



/**
 * http方式创建房间code为3时请先认证
 * @param address address
 * @param avatar avatar
 * @param avatarThumb avatarThumb
 * @param channelId channelId
 * @param city city
 * @param lat lat
 * @param liveFunction liveFunction
 * @param liveType 直播类型
 * @param lng lng
 * @param nickname nickname
 * @param province province
 * @param pull pull
 * @param roomType 房间类型 0:是一般直播 1:是私密直播 2:是收费直播 3:是计时直播 4:贵族房间
 * @param roomTypeVal roomTypeVal
 * @param shopRoomLabel shopRoomLabel
 * @param thumb thumb
 * @param title title
 */
public static void openLive(String address,String avatar,String avatarThumb,int channelId,String city,double lat,int liveFunction,int liveType,double lng,String nickname,String province,String pull,int roomType,String roomTypeVal,String shopRoomLabel,String thumb,String title,HttpApiCallBack<com.kalacheng.libuser.model.AppJoinRoomVO> callback)
{
HttpClient.getInstance().post("/api/httplive/openLive","/api/httplive/openLive")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("address", address)
.params("avatar", avatar)
.params("avatarThumb", avatarThumb)
.params("channelId", channelId)
.params("city", city)
.params("lat", lat)
.params("liveFunction", liveFunction)
.params("liveType", liveType)
.params("lng", lng)
.params("nickname", nickname)
.params("province", province)
.params("pull", pull)
.params("roomType", roomType)
.params("roomTypeVal", roomTypeVal)
.params("shopRoomLabel", shopRoomLabel)
.params("thumb", thumb)
.params("title", title)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppJoinRoomVO_Ret.class));

}





/**
 * 设置直播横幅
 * @param roomId 房间号
 * @param shopLiveBanner 横幅地址
 */
public static void setShopLiveBanner(com.kalacheng.buslive.model_fun.HttpLive_setShopLiveBanner _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httplive/setShopLiveBanner","/api/httplive/setShopLiveBanner")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("roomId", _mdl.roomId)
.params("shopLiveBanner", _mdl.shopLiveBanner)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 设置直播横幅
 * @param roomId 房间号
 * @param shopLiveBanner 横幅地址
 */
public static void setShopLiveBanner(long roomId,String shopLiveBanner,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httplive/setShopLiveBanner","/api/httplive/setShopLiveBanner")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("roomId", roomId)
.params("shopLiveBanner", shopLiveBanner)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * http方式关播
 * @param roomId 房间id
 */
public static void closeLive(long roomId,HttpApiCallBack<com.kalacheng.libuser.model.ApiCloseLive> callback)
{
HttpClient.getInstance().post("/api/httplive/closeLive","/api/httplive/closeLive")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("roomId", roomId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiCloseLive_Ret.class));

}


}
