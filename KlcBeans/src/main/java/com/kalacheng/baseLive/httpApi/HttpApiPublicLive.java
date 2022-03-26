package com.kalacheng.baseLive.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 用户直播api
 */
public class HttpApiPublicLive
{




/**
 * http方式修改房间模式
 * @param roomId 房间id
 * @param roomType 直播类型 0:一般直播 1:私密直播 2:收费直播 3:计时直播 4:贵族房间
 * @param roomTypeVal 类型值
 */
public static void updateLiveType(com.kalacheng.baseLive.model_fun.PublicLive_updateLiveType _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/updateLiveType","/api/live/updateLiveType")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("roomId", _mdl.roomId)
.params("roomType", _mdl.roomType)
.params("roomTypeVal", _mdl.roomTypeVal)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * http方式修改房间模式
 * @param roomId 房间id
 * @param roomType 直播类型 0:一般直播 1:私密直播 2:收费直播 3:计时直播 4:贵族房间
 * @param roomTypeVal 类型值
 */
public static void updateLiveType(long roomId,int roomType,String roomTypeVal,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/updateLiveType","/api/live/updateLiveType")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("roomId", roomId)
.params("roomType", roomType)
.params("roomTypeVal", roomTypeVal)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 全站广播功能
 * @param broadCast 全站广播功能 0:关闭功能 1:开启功能
 */
public static void liveVipPrivilege(int broadCast,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/liveVipPrivilege","/api/live/liveVipPrivilege")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("broadCast", broadCast)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 获取房间模式
 * @param liveType 直播类型  1:直播 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param showId 直播标识(直播间内传,其他位置传-1)
 */
public static void getLiveRoomType(com.kalacheng.baseLive.model_fun.PublicLive_getLiveRoomType _mdl,HttpApiCallBackArr<com.kalacheng.buslivebas.entity.LiveRoomType> callback)
{
HttpClient.getInstance().post("/api/live/getLiveRoomType","/api/live/getLiveRoomType")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveType", _mdl.liveType)
.params("showId", _mdl.showId)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.buslivebas.entity.LiveRoomType_RetArr.class));

}



/**
 * 获取房间模式
 * @param liveType 直播类型  1:直播 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param showId 直播标识(直播间内传,其他位置传-1)
 */
public static void getLiveRoomType(int liveType,String showId,HttpApiCallBackArr<com.kalacheng.buslivebas.entity.LiveRoomType> callback)
{
HttpClient.getInstance().post("/api/live/getLiveRoomType","/api/live/getLiveRoomType")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveType", liveType)
.params("showId", showId)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.buslivebas.entity.LiveRoomType_RetArr.class));

}





/**
 * http查看贵宾席列表
 * @param anchorId 主播id
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 */
public static void userVIPSeatsList(com.kalacheng.baseLive.model_fun.PublicLive_userVIPSeatsList _mdl,HttpApiCallBackArr<com.kalacheng.buscommon.model.ApiUserBasicInfo> callback)
{
HttpClient.getInstance().post("/api/live/userVIPSeatsList","/api/live/userVIPSeatsList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("liveType", _mdl.liveType)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.buscommon.model.ApiUserBasicInfo_RetArr.class));

}



/**
 * http查看贵宾席列表
 * @param anchorId 主播id
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 */
public static void userVIPSeatsList(long anchorId,int liveType,HttpApiCallBackArr<com.kalacheng.buscommon.model.ApiUserBasicInfo> callback)
{
HttpClient.getInstance().post("/api/live/userVIPSeatsList","/api/live/userVIPSeatsList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("liveType", liveType)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.buscommon.model.ApiUserBasicInfo_RetArr.class));

}





/**
 * 获取直播间管理员列表
 * @param anchorId 主播用户id
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 */
public static void getLiveManagerList(com.kalacheng.baseLive.model_fun.PublicLive_getLiveManagerList _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiUsersLiveManager> callback)
{
HttpClient.getInstance().post("/api/live/getLiveManagerList","/api/live/getLiveManagerList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("liveType", _mdl.liveType)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiUsersLiveManager_RetArr.class));

}



/**
 * 获取直播间管理员列表
 * @param anchorId 主播用户id
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 */
public static void getLiveManagerList(long anchorId,int liveType,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiUsersLiveManager> callback)
{
HttpClient.getInstance().post("/api/live/getLiveManagerList","/api/live/getLiveManagerList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("liveType", liveType)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiUsersLiveManager_RetArr.class));

}





/**
 * 直播间踢人操作
 * @param anchorId 主播id
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param touid 被踢人id
 */
public static void addKick(com.kalacheng.baseLive.model_fun.PublicLive_addKick _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/addKick","/api/live/addKick")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("liveType", _mdl.liveType)
.params("touid", _mdl.touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 直播间踢人操作
 * @param anchorId 主播id
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param touid 被踢人id
 */
public static void addKick(long anchorId,int liveType,long touid,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/addKick","/api/live/addKick")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("liveType", liveType)
.params("touid", touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 查看直播间公告
 * @param anchorId 主播ID
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 */
public static void getLiveNotice(com.kalacheng.baseLive.model_fun.PublicLive_getLiveNotice _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/getLiveNotice","/api/live/getLiveNotice")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("liveType", _mdl.liveType)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 查看直播间公告
 * @param anchorId 主播ID
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 */
public static void getLiveNotice(long anchorId,int liveType,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/getLiveNotice","/api/live/getLiveNotice")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("liveType", liveType)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 视频语音,门票/计时发起扣费
 * @param anchorId 主播id 
 * @param liveType 直播类型 1:直播 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态 
 * @param roomId 房间号
 * @param roomType 房间模式(暂时弃用,使用主播的) 0:普通房间 1:私密房间 2:收费房间 3:计时房间 4:贵族房间
 * @param roomTypeVal 类型值
 * @param showId 直播标识 
 */
public static void startDeduction(com.kalacheng.baseLive.model_fun.PublicLive_startDeduction _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/startDeduction","/api/live/startDeduction")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("liveType", _mdl.liveType)
.params("roomId", _mdl.roomId)
.params("roomType", _mdl.roomType)
.params("roomTypeVal", _mdl.roomTypeVal)
.params("showId", _mdl.showId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 视频语音,门票/计时发起扣费
 * @param anchorId 主播id 
 * @param liveType 直播类型 1:直播 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态 
 * @param roomId 房间号
 * @param roomType 房间模式(暂时弃用,使用主播的) 0:普通房间 1:私密房间 2:收费房间 3:计时房间 4:贵族房间
 * @param roomTypeVal 类型值
 * @param showId 直播标识 
 */
public static void startDeduction(long anchorId,int liveType,long roomId,int roomType,String roomTypeVal,String showId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/startDeduction","/api/live/startDeduction")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("liveType", liveType)
.params("roomId", roomId)
.params("roomType", roomType)
.params("roomTypeVal", roomTypeVal)
.params("showId", showId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 直播间禁言/取消禁言操作
 * @param anchorId 主播id
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param touid 被禁言人id
 */
public static void addShutup(com.kalacheng.baseLive.model_fun.PublicLive_addShutup _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/addShutup","/api/live/addShutup")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("liveType", _mdl.liveType)
.params("touid", _mdl.touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 直播间禁言/取消禁言操作
 * @param anchorId 主播id
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param touid 被禁言人id
 */
public static void addShutup(long anchorId,int liveType,long touid,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/addShutup","/api/live/addShutup")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("liveType", liveType)
.params("touid", touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 取消管理员
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param touid 对方用户id
 */
public static void cancelLivemanager(com.kalacheng.baseLive.model_fun.PublicLive_cancelLivemanager _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/cancelLivemanager","/api/live/cancelLivemanager")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveType", _mdl.liveType)
.params("touid", _mdl.touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 取消管理员
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param touid 对方用户id
 */
public static void cancelLivemanager(int liveType,long touid,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/cancelLivemanager","/api/live/cancelLivemanager")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveType", liveType)
.params("touid", touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 直播间踢人列表
 * @param anchorId 主播id
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 */
public static void getKickList(com.kalacheng.baseLive.model_fun.PublicLive_getKickList _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiKick> callback)
{
HttpClient.getInstance().post("/api/live/getKickList","/api/live/getKickList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("liveType", _mdl.liveType)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiKick_RetArr.class));

}



/**
 * 直播间踢人列表
 * @param anchorId 主播id
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 */
public static void getKickList(long anchorId,int liveType,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiKick> callback)
{
HttpClient.getInstance().post("/api/live/getKickList","/api/live/getKickList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("liveType", liveType)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiKick_RetArr.class));

}





/**
 * 发送消息
 * @param anchorId 主播id,没有传0
 * @param content 消息内容
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param roomId 房间id
 * @param type 消息类型 0:系统消息(超管) 1:是普通消息 2:弹幕消息
 */
public static void sendMsgRoom(com.kalacheng.baseLive.model_fun.PublicLive_sendMsgRoom _mdl,HttpApiCallBack<com.kalacheng.libuser.model.ApiBaseEntity> callback)
{
HttpClient.getInstance().post("/api/live/sendMsgRoom","/api/live/sendMsgRoom")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("content", _mdl.content)
.params("liveType", _mdl.liveType)
.params("roomId", _mdl.roomId)
.params("type", _mdl.type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiBaseEntity_Ret.class));

}



/**
 * 发送消息
 * @param anchorId 主播id,没有传0
 * @param content 消息内容
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param roomId 房间id
 * @param type 消息类型 0:系统消息(超管) 1:是普通消息 2:弹幕消息
 */
public static void sendMsgRoom(long anchorId,String content,int liveType,long roomId,int type,HttpApiCallBack<com.kalacheng.libuser.model.ApiBaseEntity> callback)
{
HttpClient.getInstance().post("/api/live/sendMsgRoom","/api/live/sendMsgRoom")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("content", content)
.params("liveType", liveType)
.params("roomId", roomId)
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiBaseEntity_Ret.class));

}





/**
 * 移除踢人列表用户
 * @param anchorId 主播id
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param touid 被踢人id
 */
public static void delKick(com.kalacheng.baseLive.model_fun.PublicLive_delKick _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/delKick","/api/live/delKick")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("liveType", _mdl.liveType)
.params("touid", _mdl.touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 移除踢人列表用户
 * @param anchorId 主播id
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param touid 被踢人id
 */
public static void delKick(long anchorId,int liveType,long touid,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/delKick","/api/live/delKick")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("liveType", liveType)
.params("touid", touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 观众席当前用户所在排行
 * @param anchorId 主播id
 * @param userId 当前用户id
 */
public static void getLiveUserRank(com.kalacheng.baseLive.model_fun.PublicLive_getLiveUserRank _mdl,HttpApiCallBack<com.kalacheng.libuser.model.RanksDto> callback)
{
HttpClient.getInstance().post("/api/live/getLiveUserRank","/api/live/getLiveUserRank")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("userId", _mdl.userId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.RanksDto_Ret.class));

}



/**
 * 观众席当前用户所在排行
 * @param anchorId 主播id
 * @param userId 当前用户id
 */
public static void getLiveUserRank(long anchorId,long userId,HttpApiCallBack<com.kalacheng.libuser.model.RanksDto> callback)
{
HttpClient.getInstance().post("/api/live/getLiveUserRank","/api/live/getLiveUserRank")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("userId", userId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.RanksDto_Ret.class));

}





/**
 * 修改直播间公告
 * @param anchorId 主播ID
 * @param content 公告内容
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param roomId 房间号
 */
public static void updateLiveNotice(com.kalacheng.baseLive.model_fun.PublicLive_updateLiveNotice _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/updateLiveNotice","/api/live/updateLiveNotice")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("content", _mdl.content)
.params("liveType", _mdl.liveType)
.params("roomId", _mdl.roomId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 修改直播间公告
 * @param anchorId 主播ID
 * @param content 公告内容
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param roomId 房间号
 */
public static void updateLiveNotice(long anchorId,String content,int liveType,long roomId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/updateLiveNotice","/api/live/updateLiveNotice")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("content", content)
.params("liveType", liveType)
.params("roomId", roomId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 添加管理员
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param userId 用户id
 */
public static void addLivemanager(com.kalacheng.baseLive.model_fun.PublicLive_addLivemanager _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/addLivemanager","/api/live/addLivemanager")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveType", _mdl.liveType)
.params("userId", _mdl.userId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 添加管理员
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param userId 用户id
 */
public static void addLivemanager(int liveType,long userId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/addLivemanager","/api/live/addLivemanager")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveType", liveType)
.params("userId", userId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 主播在直播间点击PK选择可连麦的主播前端需要取的字段头像,性别,名称,等级,id
 * @param keyWord 搜索主播id/名称
 */
public static void getUsableAnchor(String keyWord,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiUsableAnchorResp> callback)
{
HttpClient.getInstance().post("/api/live/pk/getUsableAnchor","/api/live/pk/getUsableAnchor")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("keyWord", keyWord)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiUsableAnchorResp_RetArr.class));

}





/**
 * 直播间禁言列表
 * @param liveType 直播间类型 1:视频直播间 2:语音直播间
 */
public static void shutupList(int liveType,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiShutUp> callback)
{
HttpClient.getInstance().post("/api/live/shutupList","/api/live/shutupList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveType", liveType)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiShutUp_RetArr.class));

}





/**
 * http购买贵宾席
 * @param anchorId 主播id
 * @param coin 金币 (男朋友中为钻石)
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 */
public static void purchaseVIPSeats(com.kalacheng.baseLive.model_fun.PublicLive_purchaseVIPSeats _mdl,HttpApiCallBack<com.kalacheng.libuser.model.AppVIPSeats> callback)
{
HttpClient.getInstance().post("/api/live/purchaseVIPSeats","/api/live/purchaseVIPSeats")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("coin", _mdl.coin)
.params("liveType", _mdl.liveType)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppVIPSeats_Ret.class));

}



/**
 * http购买贵宾席
 * @param anchorId 主播id
 * @param coin 金币 (男朋友中为钻石)
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 */
public static void purchaseVIPSeats(long anchorId,double coin,int liveType,HttpApiCallBack<com.kalacheng.libuser.model.AppVIPSeats> callback)
{
HttpClient.getInstance().post("/api/live/purchaseVIPSeats","/api/live/purchaseVIPSeats")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("coin", coin)
.params("liveType", liveType)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppVIPSeats_Ret.class));

}





/**
 * http方式获取加入房间数据
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param roomId 房间id
 */
public static void joinRoomData(com.kalacheng.baseLive.model_fun.PublicLive_joinRoomData _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/joinRoomData","/api/live/joinRoomData")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveType", _mdl.liveType)
.params("roomId", _mdl.roomId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * http方式获取加入房间数据
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param roomId 房间id
 */
public static void joinRoomData(int liveType,long roomId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/live/joinRoomData","/api/live/joinRoomData")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveType", liveType)
.params("roomId", roomId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 获取直播间观众列表
 * @param anchorId 主播id
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param page 当前页
 */
public static void getLiveUser(com.kalacheng.baseLive.model_fun.PublicLive_getLiveUser _mdl,HttpApiCallBackArr<com.kalacheng.buscommon.model.ApiUserBasicInfo> callback)
{
HttpClient.getInstance().post("/api/live/getLiveUser","/api/live/getLiveUser")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("liveType", _mdl.liveType)
.params("page", _mdl.page)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.buscommon.model.ApiUserBasicInfo_RetArr.class));

}



/**
 * 获取直播间观众列表
 * @param anchorId 主播id
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param page 当前页
 */
public static void getLiveUser(long anchorId,int liveType,int page,HttpApiCallBackArr<com.kalacheng.buscommon.model.ApiUserBasicInfo> callback)
{
HttpClient.getInstance().post("/api/live/getLiveUser","/api/live/getLiveUser")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("liveType", liveType)
.params("page", page)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.buscommon.model.ApiUserBasicInfo_RetArr.class));

}


}
