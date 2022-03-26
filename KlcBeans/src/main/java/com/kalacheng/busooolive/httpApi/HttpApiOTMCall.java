package com.kalacheng.busooolive.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 一对一对多直播间会话管理
 */
public class HttpApiOTMCall
{




/**
 * 对指定主播发起加入房间1v1v7群聊邀请，主播接通后成为该房间的副播(该功能只有开通了SVIP服务的用户才可以使用) 14:用户金币不足 15:房间人数超限 16:该副播已经处于被邀请中状态中,不能再次邀请！17:对方开启了勿扰 18:男孩子之前不能通话 19:正处于免费通话期间,不能邀请副播
 * @param inviteUId 对方id
 * @param isVideo 是否视频 0语音1视频 目前1v1v7只能视频
 * @param sessionId 1v1v多邀请时填入的1v1房间的sessionId
 */
public static void invtJoinOneVsOne(com.kalacheng.busooolive.model_fun.OTMCall_invtJoinOneVsOne _mdl,HttpApiCallBack<com.kalacheng.busooolive.model.OOOInviteRet> callback)
{
HttpClient.getInstance().post("/api/OTMCall/invtJoinOneVsOne","/api/OTMCall/invtJoinOneVsOne")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("inviteUId", _mdl.inviteUId)
.params("isVideo", _mdl.isVideo)
.params("sessionId", _mdl.sessionId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busooolive.model.OOOInviteRet_Ret.class));

}



/**
 * 对指定主播发起加入房间1v1v7群聊邀请，主播接通后成为该房间的副播(该功能只有开通了SVIP服务的用户才可以使用) 14:用户金币不足 15:房间人数超限 16:该副播已经处于被邀请中状态中,不能再次邀请！17:对方开启了勿扰 18:男孩子之前不能通话 19:正处于免费通话期间,不能邀请副播
 * @param inviteUId 对方id
 * @param isVideo 是否视频 0语音1视频 目前1v1v7只能视频
 * @param sessionId 1v1v多邀请时填入的1v1房间的sessionId
 */
public static void invtJoinOneVsOne(long inviteUId,int isVideo,long sessionId,HttpApiCallBack<com.kalacheng.busooolive.model.OOOInviteRet> callback)
{
HttpClient.getInstance().post("/api/OTMCall/invtJoinOneVsOne","/api/OTMCall/invtJoinOneVsOne")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("inviteUId", inviteUId)
.params("isVideo", isVideo)
.params("sessionId", sessionId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busooolive.model.OOOInviteRet_Ret.class));

}





/**
 * 回复用户,是否接受邀请。code:1成功；2会话已经结束；3重复请求; 4数据错误 9余额不足；12贵族才能拨打
 * @param feeUid 付费用户id
 * @param replyType 是否同意 1:同意 0:拒绝
 * @param sessionID 会话ID
 */
public static void replyInviteOTM(com.kalacheng.busooolive.model_fun.OTMCall_replyInviteOTM _mdl,HttpApiCallBack<com.kalacheng.busooolive.model.OOOReturn> callback)
{
HttpClient.getInstance().post("/api/OTMCall/replyInviteOTM","/api/OTMCall/replyInviteOTM")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("feeUid", _mdl.feeUid)
.params("replyType", _mdl.replyType)
.params("sessionID", _mdl.sessionID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busooolive.model.OOOReturn_Ret.class));

}



/**
 * 回复用户,是否接受邀请。code:1成功；2会话已经结束；3重复请求; 4数据错误 9余额不足；12贵族才能拨打
 * @param feeUid 付费用户id
 * @param replyType 是否同意 1:同意 0:拒绝
 * @param sessionID 会话ID
 */
public static void replyInviteOTM(long feeUid,int replyType,long sessionID,HttpApiCallBack<com.kalacheng.busooolive.model.OOOReturn> callback)
{
HttpClient.getInstance().post("/api/OTMCall/replyInviteOTM","/api/OTMCall/replyInviteOTM")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("feeUid", feeUid)
.params("replyType", replyType)
.params("sessionID", sessionID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busooolive.model.OOOReturn_Ret.class));

}





/**
 * 用户踢人只能踢副播,副播退出的挂断接口。(只自己退出，1v1视频继续)
 * @param assitId 被踢出的副播id
 * @param isVideo 是否为视频通话 目前1v1v7只支持视频 1视频 0语音
 * @param sessionID 会话ID
 */
public static void kickOutAnch(com.kalacheng.busooolive.model_fun.OTMCall_kickOutAnch _mdl,HttpApiCallBack<com.kalacheng.busooolive.model.OOOHangupReturn> callback)
{
HttpClient.getInstance().post("/api/OTMCall/kickOutAnch","/api/OTMCall/kickOutAnch")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("assitId", _mdl.assitId)
.params("isVideo", _mdl.isVideo)
.params("sessionID", _mdl.sessionID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busooolive.model.OOOHangupReturn_Ret.class));

}



/**
 * 用户踢人只能踢副播,副播退出的挂断接口。(只自己退出，1v1视频继续)
 * @param assitId 被踢出的副播id
 * @param isVideo 是否为视频通话 目前1v1v7只支持视频 1视频 0语音
 * @param sessionID 会话ID
 */
public static void kickOutAnch(long assitId,int isVideo,long sessionID,HttpApiCallBack<com.kalacheng.busooolive.model.OOOHangupReturn> callback)
{
HttpClient.getInstance().post("/api/OTMCall/kickOutAnch","/api/OTMCall/kickOutAnch")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("assitId", assitId)
.params("isVideo", isVideo)
.params("sessionID", sessionID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busooolive.model.OOOHangupReturn_Ret.class));

}





/**
 * null
 * @param gender 主播性别 0:保密 1:男 2:女
 * @param keyWord 搜索的主播ID/昵称
 */
public static void invitingAnchor(com.kalacheng.busooolive.model_fun.OTMCall_invitingAnchor _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.EnableInvtHost> callback)
{
HttpClient.getInstance().post("/api/OTMCall/invitingAnchor","/api/OTMCall/invitingAnchor")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("gender", _mdl.gender)
.params("keyWord", _mdl.keyWord)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.EnableInvtHost_RetArr.class));

}



/**
 * null
 * @param gender 主播性别 0:保密 1:男 2:女
 * @param keyWord 搜索的主播ID/昵称
 */
public static void invitingAnchor(int gender,String keyWord,HttpApiCallBackArr<com.kalacheng.libuser.model.EnableInvtHost> callback)
{
HttpClient.getInstance().post("/api/OTMCall/invitingAnchor","/api/OTMCall/invitingAnchor")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("gender", gender)
.params("keyWord", keyWord)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.EnableInvtHost_RetArr.class));

}





/**
 * 主播或用户挂断，整个房间销毁.副播挂断，副播退出,房间通话继续; code;1成功；2通话已经结束；11数据错误
 * @param reason 挂断原因 1:正常 2:直播云断掉了 11:费用不足 13:超时自动撤销
 * @param sessionID 会话ID
 */
public static void otmHangup(com.kalacheng.busooolive.model_fun.OTMCall_otmHangup _mdl,HttpApiCallBack<com.kalacheng.busooolive.model.OOOHangupReturn> callback)
{
HttpClient.getInstance().post("/api/OTMCall/otmHangup","/api/OTMCall/otmHangup")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("reason", _mdl.reason)
.params("sessionID", _mdl.sessionID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busooolive.model.OOOHangupReturn_Ret.class));

}



/**
 * 主播或用户挂断，整个房间销毁.副播挂断，副播退出,房间通话继续; code;1成功；2通话已经结束；11数据错误
 * @param reason 挂断原因 1:正常 2:直播云断掉了 11:费用不足 13:超时自动撤销
 * @param sessionID 会话ID
 */
public static void otmHangup(int reason,long sessionID,HttpApiCallBack<com.kalacheng.busooolive.model.OOOHangupReturn> callback)
{
HttpClient.getInstance().post("/api/OTMCall/otmHangup","/api/OTMCall/otmHangup")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("reason", reason)
.params("sessionID", sessionID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busooolive.model.OOOHangupReturn_Ret.class));

}





/**
 * 在通话过程中修改开关麦操作
 * @param sessionID 会话ID
 * @param status 麦克风状态值 1：开启 0：关闭
 */
public static void otmVolume(com.kalacheng.busooolive.model_fun.OTMCall_otmVolume _mdl,HttpApiCallBack<com.kalacheng.busooolive.model.OOOVolumeRet> callback)
{
HttpClient.getInstance().post("/api/OTMCall/otmVolume","/api/OTMCall/otmVolume")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("sessionID", _mdl.sessionID)
.params("status", _mdl.status)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busooolive.model.OOOVolumeRet_Ret.class));

}



/**
 * 在通话过程中修改开关麦操作
 * @param sessionID 会话ID
 * @param status 麦克风状态值 1：开启 0：关闭
 */
public static void otmVolume(long sessionID,int status,HttpApiCallBack<com.kalacheng.busooolive.model.OOOVolumeRet> callback)
{
HttpClient.getInstance().post("/api/OTMCall/otmVolume","/api/OTMCall/otmVolume")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("sessionID", sessionID)
.params("status", status)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busooolive.model.OOOVolumeRet_Ret.class));

}





/**
 * 在通话过程中对指定副播进行禁言操作
 * @param assisUid 指定禁言的副播id
 * @param sessionID 会话ID
 * @param shutStatus 禁言状态 1:禁言 0:解禁
 */
public static void addOOOShutup(com.kalacheng.busooolive.model_fun.OTMCall_addOOOShutup _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/OTMCall/addOOOShutup","/api/OTMCall/addOOOShutup")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("assisUid", _mdl.assisUid)
.params("sessionID", _mdl.sessionID)
.params("shutStatus", _mdl.shutStatus)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 在通话过程中对指定副播进行禁言操作
 * @param assisUid 指定禁言的副播id
 * @param sessionID 会话ID
 * @param shutStatus 禁言状态 1:禁言 0:解禁
 */
public static void addOOOShutup(long assisUid,long sessionID,int shutStatus,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/OTMCall/addOOOShutup","/api/OTMCall/addOOOShutup")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("assisUid", assisUid)
.params("sessionID", sessionID)
.params("shutStatus", shutStatus)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * null
 * @param sid SVIP服务id 默认为1 当前只有1种svip服务
 */
public static void getAppSvip(long sid,HttpApiCallBack<com.kalacheng.libuser.model.AppSvipDto> callback)
{
HttpClient.getInstance().post("/api/OTMCall/getAppSvip","/api/OTMCall/getAppSvip")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("sid", sid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppSvipDto_Ret.class));

}





/**
 * null
 * @param anchUid 被撤销邀请的副播id
 * @param sessionID 会话ID
 */
public static void anchCancelInvite(com.kalacheng.busooolive.model_fun.OTMCall_anchCancelInvite _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/OTMCall/anchCancelInvite","/api/OTMCall/anchCancelInvite")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchUid", _mdl.anchUid)
.params("sessionID", _mdl.sessionID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * null
 * @param anchUid 被撤销邀请的副播id
 * @param sessionID 会话ID
 */
public static void anchCancelInvite(long anchUid,long sessionID,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/OTMCall/anchCancelInvite","/api/OTMCall/anchCancelInvite")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchUid", anchUid)
.params("sessionID", sessionID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 用户点击开通SVIP服务 1开通成功！ 2余额不足请充值！ 3扣费不成功开通SVIP失败！ 4已经是svip会员了
 * @param pid 要开通的svip服务的套餐id
 * @param svipId 要开通的svip服务的id 目前svip只有一种，故默认为1
 */
public static void openMember(com.kalacheng.busooolive.model_fun.OTMCall_openMember _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/OTMCall/openMember","/api/OTMCall/openMember")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pid", _mdl.pid)
.params("svipId", _mdl.svipId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 用户点击开通SVIP服务 1开通成功！ 2余额不足请充值！ 3扣费不成功开通SVIP失败！ 4已经是svip会员了
 * @param pid 要开通的svip服务的套餐id
 * @param svipId 要开通的svip服务的id 目前svip只有一种，故默认为1
 */
public static void openMember(long pid,long svipId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/OTMCall/openMember","/api/OTMCall/openMember")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pid", pid)
.params("svipId", svipId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}


}
