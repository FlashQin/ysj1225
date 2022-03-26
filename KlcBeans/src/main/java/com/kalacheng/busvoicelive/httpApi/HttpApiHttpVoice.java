package com.kalacheng.busvoicelive.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 语音直播间
 */
public class HttpApiHttpVoice
{




/**
 * 开关麦
 * @param assistanNo 麦位序号
 * @param onOffState 麦克风状态 0:关麦 1:开麦(音量)  (主播操作时传-1)
 * @param roomId 直播间ID
 * @param uid 直播间主播ID
 */
public static void offVolumn(com.kalacheng.busvoicelive.model_fun.HttpVoice_offVolumn _mdl,HttpApiCallBack<com.kalacheng.libuser.model.ApiUsersVoiceAssistan> callback)
{
HttpClient.getInstance().post("/api/httpvoice/offVolumn","/api/httpvoice/offVolumn")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("assistanNo", _mdl.assistanNo)
.params("onOffState", _mdl.onOffState)
.params("roomId", _mdl.roomId)
.params("uid", _mdl.uid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiUsersVoiceAssistan_Ret.class));

}



/**
 * 开关麦
 * @param assistanNo 麦位序号
 * @param onOffState 麦克风状态 0:关麦 1:开麦(音量)  (主播操作时传-1)
 * @param roomId 直播间ID
 * @param uid 直播间主播ID
 */
public static void offVolumn(int assistanNo,int onOffState,long roomId,long uid,HttpApiCallBack<com.kalacheng.libuser.model.ApiUsersVoiceAssistan> callback)
{
HttpClient.getInstance().post("/api/httpvoice/offVolumn","/api/httpvoice/offVolumn")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("assistanNo", assistanNo)
.params("onOffState", onOffState)
.params("roomId", roomId)
.params("uid", uid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiUsersVoiceAssistan_Ret.class));

}





/**
 * 查看申请上麦(主播尚未同意的)用户 和 被主播邀请，但尚未上麦(待接入状态)的用户 列表
 * @param roomId 直播间ID
 */
public static void getInvitingUsers(long roomId,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiLineVoice> callback)
{
HttpClient.getInstance().post("/api/httpvoice/getInvitingUsers","/api/httpvoice/getInvitingUsers")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("roomId", roomId)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiLineVoice_RetArr.class));

}





/**
 * null
 * @param pktype PK类型 1:房间PK 2:单人Pk 3:激情团战
 * @param roomID 多人语音直播间ID
 */
public static void applyPK(com.kalacheng.busvoicelive.model_fun.HttpVoice_applyPK _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/applyPK","/api/httpvoice/applyPK")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pktype", _mdl.pktype)
.params("roomID", _mdl.roomID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * null
 * @param pktype PK类型 1:房间PK 2:单人Pk 3:激情团战
 * @param roomID 多人语音直播间ID
 */
public static void applyPK(int pktype,long roomID,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/applyPK","/api/httpvoice/applyPK")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pktype", pktype)
.params("roomID", roomID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 语音直播间背景图
 * @param type 类型 0:多人语音 1:一对一语音
 */
public static void voiceThumbList(int type,HttpApiCallBackArr<com.kalacheng.libuser.model.AppVoiceThumb> callback)
{
HttpClient.getInstance().post("/api/httpvoice/voiceThumbList","/api/httpvoice/voiceThumbList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("type", type)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppVoiceThumb_RetArr.class));

}





/**
 * null
 * @param isAuto 自动上麦开关 1打开 0关闭
 * @param roomID 多人语音直播间ID
 */
public static void setAutoUpAssistan(com.kalacheng.busvoicelive.model_fun.HttpVoice_setAutoUpAssistan _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/setAutoUpAssistan","/api/httpvoice/setAutoUpAssistan")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("isAuto", _mdl.isAuto)
.params("roomID", _mdl.roomID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * null
 * @param isAuto 自动上麦开关 1打开 0关闭
 * @param roomID 多人语音直播间ID
 */
public static void setAutoUpAssistan(int isAuto,long roomID,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/setAutoUpAssistan","/api/httpvoice/setAutoUpAssistan")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("isAuto", isAuto)
.params("roomID", roomID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * http方式加入语音房间
 * @param roomId 房间id
 * @param roomType 房间类型 0:一般直播 1:私密直播 2:收费直播 3:计时直播 4:贵族房间
 * @param roomTypeVal 如果是密码房间传入密码
 */
public static void joinVoiceRoom(com.kalacheng.busvoicelive.model_fun.HttpVoice_joinVoiceRoom _mdl,HttpApiCallBack<com.kalacheng.libuser.model.AppJoinRoomVO> callback)
{
HttpClient.getInstance().post("/api/httpvoice/joinVoiceRoom","/api/httpvoice/joinVoiceRoom")
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
 * http方式加入语音房间
 * @param roomId 房间id
 * @param roomType 房间类型 0:一般直播 1:私密直播 2:收费直播 3:计时直播 4:贵族房间
 * @param roomTypeVal 如果是密码房间传入密码
 */
public static void joinVoiceRoom(long roomId,int roomType,String roomTypeVal,HttpApiCallBack<com.kalacheng.libuser.model.AppJoinRoomVO> callback)
{
HttpClient.getInstance().post("/api/httpvoice/joinVoiceRoom","/api/httpvoice/joinVoiceRoom")
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
 * http方式关播
 * @param roomId 房间id
 */
public static void closeVoiceLive(long roomId,HttpApiCallBack<com.kalacheng.libuser.model.ApiCloseLive> callback)
{
HttpClient.getInstance().post("/api/httpvoice/closeVoiceLive","/api/httpvoice/closeVoiceLive")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("roomId", roomId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiCloseLive_Ret.class));

}





/**
 * 麦位用户禁麦(禁止说话)/麦位用户取消禁麦(允许说话)
 * @param anchorId 主播id
 * @param roomId 房间号
 * @param touid 被禁止说话人id
 */
public static void addNoTalking(com.kalacheng.busvoicelive.model_fun.HttpVoice_addNoTalking _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/addNoTalking","/api/httpvoice/addNoTalking")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("roomId", _mdl.roomId)
.params("touid", _mdl.touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 麦位用户禁麦(禁止说话)/麦位用户取消禁麦(允许说话)
 * @param anchorId 主播id
 * @param roomId 房间号
 * @param touid 被禁止说话人id
 */
public static void addNoTalking(long anchorId,long roomId,long touid,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/addNoTalking","/api/httpvoice/addNoTalking")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("roomId", roomId)
.params("touid", touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * http方式创建房间code为3时请先认证
 * @param addr 主播地址
 * @param avatar 小头像(改从后台取)
 * @param avatarThumb 主播头像(改从后台取)
 * @param channelId 频道ID
 * @param city 市
 * @param lat 纬度
 * @param liveclassid 直播分类ID
 * @param lng 经度
 * @param nickname 主播昵称(不使用,统一使用userName)
 * @param province 省
 * @param pull 直播云推/拉流地址
 * @param roomType 房间模式 0:普通房间 1:私密房间 2:收费房间 3:计时房间 4:贵族房间
 * @param roomTypeVal 房间模式对应的值 密码房间：密码  收费房间：收费金额
 * @param thumb 封面图
 * @param title 开播标题
 * @param upStatus 自动上麦状态 1:开 0:关
 */
public static void openVoiceLive(com.kalacheng.busvoicelive.model_fun.HttpVoice_openVoiceLive _mdl,HttpApiCallBack<com.kalacheng.libuser.model.AppJoinRoomVO> callback)
{
HttpClient.getInstance().post("/api/httpvoice/openVoiceLive","/api/httpvoice/openVoiceLive")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("addr", _mdl.addr)
.params("avatar", _mdl.avatar)
.params("avatarThumb", _mdl.avatarThumb)
.params("channelId", _mdl.channelId)
.params("city", _mdl.city)
.params("lat", _mdl.lat)
.params("liveclassid", _mdl.liveclassid)
.params("lng", _mdl.lng)
.params("nickname", _mdl.nickname)
.params("province", _mdl.province)
.params("pull", _mdl.pull)
.params("roomType", _mdl.roomType)
.params("roomTypeVal", _mdl.roomTypeVal)
.params("thumb", _mdl.thumb)
.params("title", _mdl.title)
.params("upStatus", _mdl.upStatus)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppJoinRoomVO_Ret.class));

}



/**
 * http方式创建房间code为3时请先认证
 * @param addr 主播地址
 * @param avatar 小头像(改从后台取)
 * @param avatarThumb 主播头像(改从后台取)
 * @param channelId 频道ID
 * @param city 市
 * @param lat 纬度
 * @param liveclassid 直播分类ID
 * @param lng 经度
 * @param nickname 主播昵称(不使用,统一使用userName)
 * @param province 省
 * @param pull 直播云推/拉流地址
 * @param roomType 房间模式 0:普通房间 1:私密房间 2:收费房间 3:计时房间 4:贵族房间
 * @param roomTypeVal 房间模式对应的值 密码房间：密码  收费房间：收费金额
 * @param thumb 封面图
 * @param title 开播标题
 * @param upStatus 自动上麦状态 1:开 0:关
 */
public static void openVoiceLive(String addr,String avatar,String avatarThumb,int channelId,String city,double lat,int liveclassid,double lng,String nickname,String province,String pull,int roomType,String roomTypeVal,String thumb,String title,int upStatus,HttpApiCallBack<com.kalacheng.libuser.model.AppJoinRoomVO> callback)
{
HttpClient.getInstance().post("/api/httpvoice/openVoiceLive","/api/httpvoice/openVoiceLive")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("addr", addr)
.params("avatar", avatar)
.params("avatarThumb", avatarThumb)
.params("channelId", channelId)
.params("city", city)
.params("lat", lat)
.params("liveclassid", liveclassid)
.params("lng", lng)
.params("nickname", nickname)
.params("province", province)
.params("pull", pull)
.params("roomType", roomType)
.params("roomTypeVal", roomTypeVal)
.params("thumb", thumb)
.params("title", title)
.params("upStatus", upStatus)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppJoinRoomVO_Ret.class));

}





/**
 * null
 * @param roomId 多人语音直播间ID
 * @param strickerId 表情包Id
 */
public static void sendSticker(com.kalacheng.busvoicelive.model_fun.HttpVoice_sendSticker _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/sendSticker","/api/httpvoice/sendSticker")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("roomId", _mdl.roomId)
.params("strickerId", _mdl.strickerId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * null
 * @param roomId 多人语音直播间ID
 * @param strickerId 表情包Id
 */
public static void sendSticker(long roomId,long strickerId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/sendSticker","/api/httpvoice/sendSticker")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("roomId", roomId)
.params("strickerId", strickerId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 下麦操作
 * @param assistanNo 麦位序号
 * @param roomId 直播间ID
 * @param uid 直播间主播ID
 */
public static void authDownAssistan(com.kalacheng.busvoicelive.model_fun.HttpVoice_authDownAssistan _mdl,HttpApiCallBack<com.kalacheng.libuser.model.ApiUsersVoiceAssistan> callback)
{
HttpClient.getInstance().post("/api/httpvoice/authDownAssistan","/api/httpvoice/authDownAssistan")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("assistanNo", _mdl.assistanNo)
.params("roomId", _mdl.roomId)
.params("uid", _mdl.uid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiUsersVoiceAssistan_Ret.class));

}



/**
 * 下麦操作
 * @param assistanNo 麦位序号
 * @param roomId 直播间ID
 * @param uid 直播间主播ID
 */
public static void authDownAssistan(int assistanNo,long roomId,long uid,HttpApiCallBack<com.kalacheng.libuser.model.ApiUsersVoiceAssistan> callback)
{
HttpClient.getInstance().post("/api/httpvoice/authDownAssistan","/api/httpvoice/authDownAssistan")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("assistanNo", assistanNo)
.params("roomId", roomId)
.params("uid", uid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiUsersVoiceAssistan_Ret.class));

}





/**
 * 获取直播间数据
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param roomId 房间id
 */
public static void getApiJoinRoom(com.kalacheng.busvoicelive.model_fun.HttpVoice_getApiJoinRoom _mdl,HttpApiCallBack<com.kalacheng.libuser.model.AppJoinRoomVO> callback)
{
HttpClient.getInstance().post("/api/httpvoice/getApiJoinRoom","/api/httpvoice/getApiJoinRoom")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveType", _mdl.liveType)
.params("roomId", _mdl.roomId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppJoinRoomVO_Ret.class));

}



/**
 * 获取直播间数据
 * @param liveType 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param roomId 房间id
 */
public static void getApiJoinRoom(long liveType,long roomId,HttpApiCallBack<com.kalacheng.libuser.model.AppJoinRoomVO> callback)
{
HttpClient.getInstance().post("/api/httpvoice/getApiJoinRoom","/api/httpvoice/getApiJoinRoom")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveType", liveType)
.params("roomId", roomId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppJoinRoomVO_Ret.class));

}





/**
 * 修改直播间背景图
 * @param roomId 房间号
 * @param voicethumbid 背景图Id
 */
public static void updateLiveBackground(com.kalacheng.busvoicelive.model_fun.HttpVoice_updateLiveBackground _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/updateLiveBackground","/api/httpvoice/updateLiveBackground")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("roomId", _mdl.roomId)
.params("voicethumbid", _mdl.voicethumbid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 修改直播间背景图
 * @param roomId 房间号
 * @param voicethumbid 背景图Id
 */
public static void updateLiveBackground(long roomId,long voicethumbid,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/updateLiveBackground","/api/httpvoice/updateLiveBackground")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("roomId", roomId)
.params("voicethumbid", voicethumbid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * http方式离开房间
 * @param roomId 房间id
 */
public static void leaveVoiceRoomOpt(long roomId,HttpApiCallBack<com.kalacheng.libuser.model.ApiLeaveRoom> callback)
{
HttpClient.getInstance().post("/api/httpvoice/leaveVoiceRoomOpt","/api/httpvoice/leaveVoiceRoomOpt")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("roomId", roomId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiLeaveRoom_Ret.class));

}





/**
 * null
 */
public static void quitPK(HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/quitPK","/api/httpvoice/quitPK")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 主播邀请成为副播 code 2:只有主播才能邀请观众连麦 3:被邀请的不在直播间内 4:被邀请的已经在麦上 5:没有空余麦位了 6:被邀请方数据错误
 * @param inviteUid 被邀请方ID
 * @param roomID 直播间ID
 */
public static void inviteUserUpAssitan(com.kalacheng.busvoicelive.model_fun.HttpVoice_inviteUserUpAssitan _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/inviteUserUpAssitan","/api/httpvoice/inviteUserUpAssitan")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("inviteUid", _mdl.inviteUid)
.params("roomID", _mdl.roomID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 主播邀请成为副播 code 2:只有主播才能邀请观众连麦 3:被邀请的不在直播间内 4:被邀请的已经在麦上 5:没有空余麦位了 6:被邀请方数据错误
 * @param inviteUid 被邀请方ID
 * @param roomID 直播间ID
 */
public static void inviteUserUpAssitan(long inviteUid,long roomID,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/inviteUserUpAssitan","/api/httpvoice/inviteUserUpAssitan")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("inviteUid", inviteUid)
.params("roomID", roomID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * null
 * @param assistanNo 申请上的麦序（弹窗中时为-1）
 * @param authId 申请方ID
 * @param isAgree 是否同意申请方上麦 1是 0否
 * @param roomID 多人语音直播间ID
 */
public static void dealUpAssistanAsk(com.kalacheng.busvoicelive.model_fun.HttpVoice_dealUpAssistanAsk _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/dealUpAssistanAsk","/api/httpvoice/dealUpAssistanAsk")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("assistanNo", _mdl.assistanNo)
.params("authId", _mdl.authId)
.params("isAgree", _mdl.isAgree)
.params("roomID", _mdl.roomID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * null
 * @param assistanNo 申请上的麦序（弹窗中时为-1）
 * @param authId 申请方ID
 * @param isAgree 是否同意申请方上麦 1是 0否
 * @param roomID 多人语音直播间ID
 */
public static void dealUpAssistanAsk(int assistanNo,long authId,int isAgree,long roomID,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/dealUpAssistanAsk","/api/httpvoice/dealUpAssistanAsk")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("assistanNo", assistanNo)
.params("authId", authId)
.params("isAgree", isAgree)
.params("roomID", roomID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 锁定或解锁麦位
 * @param assistanNo 麦位序号
 * @param retireState 封麦状态值 0封麦 1解封
 * @param roomId 直播间ID
 */
public static void lockAssistan(com.kalacheng.busvoicelive.model_fun.HttpVoice_lockAssistan _mdl,HttpApiCallBack<com.kalacheng.libuser.model.ApiUsersVoiceAssistan> callback)
{
HttpClient.getInstance().post("/api/httpvoice/lockAssistan","/api/httpvoice/lockAssistan")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("assistanNo", _mdl.assistanNo)
.params("retireState", _mdl.retireState)
.params("roomId", _mdl.roomId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiUsersVoiceAssistan_Ret.class));

}



/**
 * 锁定或解锁麦位
 * @param assistanNo 麦位序号
 * @param retireState 封麦状态值 0封麦 1解封
 * @param roomId 直播间ID
 */
public static void lockAssistan(int assistanNo,int retireState,long roomId,HttpApiCallBack<com.kalacheng.libuser.model.ApiUsersVoiceAssistan> callback)
{
HttpClient.getInstance().post("/api/httpvoice/lockAssistan","/api/httpvoice/lockAssistan")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("assistanNo", assistanNo)
.params("retireState", retireState)
.params("roomId", roomId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiUsersVoiceAssistan_Ret.class));

}





/**
 * 观众撤销申请上麦
 * @param uid 房主ID
 */
public static void cancelUpAssistan(long uid,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/cancelUpAssistan","/api/httpvoice/cancelUpAssistan")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("uid", uid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 主播踢人
 * @param assisId 被踢下麦的副播ID
 * @param assistanNo 麦位序号
 * @param roomId 直播间ID
 */
public static void kickOutAssistan(com.kalacheng.busvoicelive.model_fun.HttpVoice_kickOutAssistan _mdl,HttpApiCallBack<com.kalacheng.libuser.model.ApiUsersVoiceAssistan> callback)
{
HttpClient.getInstance().post("/api/httpvoice/kickOutAssistan","/api/httpvoice/kickOutAssistan")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("assisId", _mdl.assisId)
.params("assistanNo", _mdl.assistanNo)
.params("roomId", _mdl.roomId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiUsersVoiceAssistan_Ret.class));

}



/**
 * 主播踢人
 * @param assisId 被踢下麦的副播ID
 * @param assistanNo 麦位序号
 * @param roomId 直播间ID
 */
public static void kickOutAssistan(long assisId,int assistanNo,long roomId,HttpApiCallBack<com.kalacheng.libuser.model.ApiUsersVoiceAssistan> callback)
{
HttpClient.getInstance().post("/api/httpvoice/kickOutAssistan","/api/httpvoice/kickOutAssistan")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("assisId", assisId)
.params("assistanNo", assistanNo)
.params("roomId", roomId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiUsersVoiceAssistan_Ret.class));

}





/**
 * 默认直接上麦，当房间主播设置开关需审核才能上麦时，点击上麦的用户进入申请列表等待,主播可在待审核列表同意指定的观众进入直播间0:观众申请上麦发送成功 1:观众上麦操作成功 2:主播ID不一致 3:麦位数据有误
 * @param assistanNo 麦位序号  -1时为弹窗功能的申请上麦，不能指定上的麦序
 * @param roomId 直播间ID
 * @param uid 直播间主播ID
 */
public static void authUpAssistan(com.kalacheng.busvoicelive.model_fun.HttpVoice_authUpAssistan _mdl,HttpApiCallBack<com.kalacheng.libuser.model.ApiUsersVoiceAssistan> callback)
{
HttpClient.getInstance().post("/api/httpvoice/authUpAssistan","/api/httpvoice/authUpAssistan")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("assistanNo", _mdl.assistanNo)
.params("roomId", _mdl.roomId)
.params("uid", _mdl.uid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiUsersVoiceAssistan_Ret.class));

}



/**
 * 默认直接上麦，当房间主播设置开关需审核才能上麦时，点击上麦的用户进入申请列表等待,主播可在待审核列表同意指定的观众进入直播间0:观众申请上麦发送成功 1:观众上麦操作成功 2:主播ID不一致 3:麦位数据有误
 * @param assistanNo 麦位序号  -1时为弹窗功能的申请上麦，不能指定上的麦序
 * @param roomId 直播间ID
 * @param uid 直播间主播ID
 */
public static void authUpAssistan(int assistanNo,long roomId,long uid,HttpApiCallBack<com.kalacheng.libuser.model.ApiUsersVoiceAssistan> callback)
{
HttpClient.getInstance().post("/api/httpvoice/authUpAssistan","/api/httpvoice/authUpAssistan")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("assistanNo", assistanNo)
.params("roomId", roomId)
.params("uid", uid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiUsersVoiceAssistan_Ret.class));

}





/**
 * 查看申请上麦(主播尚未同意的)列表
 * @param roomId 直播间ID
 */
public static void getWaitingUsers(long roomId,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiLineVoice> callback)
{
HttpClient.getInstance().post("/api/httpvoice/getWaitingUsers","/api/httpvoice/getWaitingUsers")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("roomId", roomId)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiLineVoice_RetArr.class));

}





/**
 * null
 * @param role 搜索的角色类型 0:普通用户 1:主播
 */
public static void enableInvtVoice(int role,HttpApiCallBackArr<com.kalacheng.buscommon.model.AppUserDto> callback)
{
HttpClient.getInstance().post("/api/httpvoice/enableInvtVoice","/api/httpvoice/enableInvtVoice")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("role", role)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.buscommon.model.AppUserDto_RetArr.class));

}





/**
 * null
 */
public static void getStrickerList(HttpApiCallBackArr<com.kalacheng.busvoicelive.model.AppStricker> callback)
{
HttpClient.getInstance().post("/api/httpvoice/getStrickerList","/api/httpvoice/getStrickerList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busvoicelive.model.AppStricker_RetArr.class));

}





/**
 * null
 * @param reply 回复邀请方房主，同意或拒绝上麦  1同意 0拒绝
 * @param roomID 多人语音直播间ID
 */
public static void replyAuthorInvt(com.kalacheng.busvoicelive.model_fun.HttpVoice_replyAuthorInvt _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/replyAuthorInvt","/api/httpvoice/replyAuthorInvt")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("reply", _mdl.reply)
.params("roomID", _mdl.roomID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * null
 * @param reply 回复邀请方房主，同意或拒绝上麦  1同意 0拒绝
 * @param roomID 多人语音直播间ID
 */
public static void replyAuthorInvt(int reply,long roomID,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/replyAuthorInvt","/api/httpvoice/replyAuthorInvt")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("reply", reply)
.params("roomID", roomID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 房主抱房间内观众（角色可以是主播也可以是用户）上麦
 * @param inviteUid 被邀请上麦方ID
 * @param roomID 直播间ID
 */
public static void letUserUpAssitan(com.kalacheng.busvoicelive.model_fun.HttpVoice_letUserUpAssitan _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/letUserUpAssitan","/api/httpvoice/letUserUpAssitan")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("inviteUid", _mdl.inviteUid)
.params("roomID", _mdl.roomID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 房主抱房间内观众（角色可以是主播也可以是用户）上麦
 * @param inviteUid 被邀请上麦方ID
 * @param roomID 直播间ID
 */
public static void letUserUpAssitan(long inviteUid,long roomID,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/httpvoice/letUserUpAssitan","/api/httpvoice/letUserUpAssitan")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("inviteUid", inviteUid)
.params("roomID", roomID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 用户连线请求列表
 */
public static void voiceLineRequestList(HttpApiCallBackArr<com.kalacheng.libuser.model.ApiLineVoice> callback)
{
HttpClient.getInstance().post("/api/httpvoice/voiceLineRequestList","/api/httpvoice/voiceLineRequestList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiLineVoice_RetArr.class));

}


}
