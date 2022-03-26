package com.kalacheng.busooolive.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 一对一直播间会话管理
 */
public class HttpApiOOOCall
{




/**
 * 是否能发起一对一通话 code:1成功 2:对方正忙 3:对方正在通话 4:对方不在线 9:用户余额不足无法邀请通话 10:不能向自己发起邀请11:数据错误 12:贵族才能通话 13:用户和用户不能打电话 14:主播和主播不能打电话 15:对方开启了勿扰 16:主播正忙 17:被邀请用户设置了勿扰 18:您已被拉黑 20:未认证
 * @param inviteUId 对方id
 * @param isVideo 0:语音 1:视频
 */
public static void inviteChat(com.kalacheng.busooolive.model_fun.OOOCall_inviteChat _mdl,HttpApiCallBack<com.kalacheng.busooolive.model.OOOInviteRet> callback)
{
HttpClient.getInstance().post("/api/OOOCall/inviteChat","/api/OOOCall/inviteChat")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("inviteUId", _mdl.inviteUId)
.params("isVideo", _mdl.isVideo)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busooolive.model.OOOInviteRet_Ret.class));

}



/**
 * 是否能发起一对一通话 code:1成功 2:对方正忙 3:对方正在通话 4:对方不在线 9:用户余额不足无法邀请通话 10:不能向自己发起邀请11:数据错误 12:贵族才能通话 13:用户和用户不能打电话 14:主播和主播不能打电话 15:对方开启了勿扰 16:主播正忙 17:被邀请用户设置了勿扰 18:您已被拉黑 20:未认证
 * @param inviteUId 对方id
 * @param isVideo 0:语音 1:视频
 */
public static void inviteChat(long inviteUId,int isVideo,HttpApiCallBack<com.kalacheng.busooolive.model.OOOInviteRet> callback)
{
HttpClient.getInstance().post("/api/OOOCall/inviteChat","/api/OOOCall/inviteChat")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("inviteUId", inviteUId)
.params("isVideo", isVideo)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busooolive.model.OOOInviteRet_Ret.class));

}





/**
 * 通过推送恢复发送邀请的socket
 * @param feeUid 扣费者id
 * @param isVideo 0:语音 1:视频
 * @param oooFee 通话每分钟费用
 * @param receiveUid 接收者id
 * @param sendUid 发起者id
 * @param sessionId 会话id
 */
public static void oooCallPushDataRestore(com.kalacheng.busooolive.model_fun.OOOCall_oooCallPushDataRestore _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/OOOCall/oooCallPushDataRestore","/api/OOOCall/oooCallPushDataRestore")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("feeUid", _mdl.feeUid)
.params("isVideo", _mdl.isVideo)
.params("oooFee", _mdl.oooFee)
.params("receiveUid", _mdl.receiveUid)
.params("sendUid", _mdl.sendUid)
.params("sessionId", _mdl.sessionId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 通过推送恢复发送邀请的socket
 * @param feeUid 扣费者id
 * @param isVideo 0:语音 1:视频
 * @param oooFee 通话每分钟费用
 * @param receiveUid 接收者id
 * @param sendUid 发起者id
 * @param sessionId 会话id
 */
public static void oooCallPushDataRestore(long feeUid,int isVideo,double oooFee,long receiveUid,long sendUid,long sessionId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/OOOCall/oooCallPushDataRestore","/api/OOOCall/oooCallPushDataRestore")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("feeUid", feeUid)
.params("isVideo", isVideo)
.params("oooFee", oooFee)
.params("receiveUid", receiveUid)
.params("sendUid", sendUid)
.params("sessionId", sessionId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 发布求聊信息code=2时为余额不足 3:不是贵族无法发布
 * @param chatType 聊天类型 1:视频聊天 2:语音聊天
 * @param feeId 话费id
 */
public static void addPushChat(com.kalacheng.busooolive.model_fun.OOOCall_addPushChat _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/OOOCall/addPushChat","/api/OOOCall/addPushChat")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("chatType", _mdl.chatType)
.params("feeId", _mdl.feeId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 发布求聊信息code=2时为余额不足 3:不是贵族无法发布
 * @param chatType 聊天类型 1:视频聊天 2:语音聊天
 * @param feeId 话费id
 */
public static void addPushChat(int chatType,long feeId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/OOOCall/addPushChat","/api/OOOCall/addPushChat")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("chatType", chatType)
.params("feeId", feeId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 查询抢聊用户列表
 * @param pageIndex pageIndex
 * @param pageSize pageSize
 */
public static void getPushChatList(com.kalacheng.busooolive.model_fun.OOOCall_getPushChatList _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiPushChat> callback)
{
HttpClient.getInstance().post("/api/OOOCall/getPushChatList","/api/OOOCall/getPushChatList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiPushChat_RetArr.class));

}



/**
 * 查询抢聊用户列表
 * @param pageIndex pageIndex
 * @param pageSize pageSize
 */
public static void getPushChatList(int pageIndex,int pageSize,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiPushChat> callback)
{
HttpClient.getInstance().post("/api/OOOCall/getPushChatList","/api/OOOCall/getPushChatList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiPushChat_RetArr.class));

}





/**
 * 直播间查看主播联系方式
 */
public static void takeAnchorContact(HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/OOOCall/takeAnchorContact","/api/OOOCall/takeAnchorContact")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 女主播列表
 * @param lat 纬度
 * @param lng 经度
 * @param pageIndex 当前页
 * @param pageSize 每页条数
 * @param type 类型 1,聊场2,女主播
 */
public static void getAnchorOrMailList(com.kalacheng.busooolive.model_fun.OOOCall_getAnchorOrMailList _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiUsersLine> callback)
{
HttpClient.getInstance().post("/api/OOOCall/getAnchorOrMailList","/api/OOOCall/getAnchorOrMailList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("lat", _mdl.lat)
.params("lng", _mdl.lng)
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.params("type", _mdl.type)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiUsersLine_RetArr.class));

}



/**
 * 女主播列表
 * @param lat 纬度
 * @param lng 经度
 * @param pageIndex 当前页
 * @param pageSize 每页条数
 * @param type 类型 1,聊场2,女主播
 */
public static void getAnchorOrMailList(double lat,double lng,int pageIndex,int pageSize,int type,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiUsersLine> callback)
{
HttpClient.getInstance().post("/api/OOOCall/getAnchorOrMailList","/api/OOOCall/getAnchorOrMailList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("lat", lat)
.params("lng", lng)
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.params("type", type)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiUsersLine_RetArr.class));

}





/**
 * 抢聊操作
 * @param sessionID sessionID
 * @param toUid 用户id(被抢用户id)
 */
public static void robPushChat(com.kalacheng.busooolive.model_fun.OOOCall_robPushChat _mdl,HttpApiCallBack<com.kalacheng.busooolive.model.OOOReturn> callback)
{
HttpClient.getInstance().post("/api/OOOCall/robPushChat","/api/OOOCall/robPushChat")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("sessionID", _mdl.sessionID)
.params("toUid", _mdl.toUid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busooolive.model.OOOReturn_Ret.class));

}



/**
 * 抢聊操作
 * @param sessionID sessionID
 * @param toUid 用户id(被抢用户id)
 */
public static void robPushChat(long sessionID,long toUid,HttpApiCallBack<com.kalacheng.busooolive.model.OOOReturn> callback)
{
HttpClient.getInstance().post("/api/OOOCall/robPushChat","/api/OOOCall/robPushChat")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("sessionID", sessionID)
.params("toUid", toUid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busooolive.model.OOOReturn_Ret.class));

}





/**
 * 撤销邀请
 * @param sessionID 会话ID
 */
public static void cancelInvite(long sessionID,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/OOOCall/cancelInvite","/api/OOOCall/cancelInvite")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("sessionID", sessionID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 回复用户,是否接受邀请 code 1:成功 2:会话已经结束 3:重复请求 9:余额不足 12:贵族才能拨打
 * @param replyType 是否同意 1:同意 0:拒绝
 * @param sessionID 会话ID
 */
public static void replyInvite(com.kalacheng.busooolive.model_fun.OOOCall_replyInvite _mdl,HttpApiCallBack<com.kalacheng.busooolive.model.OOOReturn> callback)
{
HttpClient.getInstance().post("/api/OOOCall/replyInvite","/api/OOOCall/replyInvite")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("replyType", _mdl.replyType)
.params("sessionID", _mdl.sessionID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busooolive.model.OOOReturn_Ret.class));

}



/**
 * 回复用户,是否接受邀请 code 1:成功 2:会话已经结束 3:重复请求 9:余额不足 12:贵族才能拨打
 * @param replyType 是否同意 1:同意 0:拒绝
 * @param sessionID 会话ID
 */
public static void replyInvite(int replyType,long sessionID,HttpApiCallBack<com.kalacheng.busooolive.model.OOOReturn> callback)
{
HttpClient.getInstance().post("/api/OOOCall/replyInvite","/api/OOOCall/replyInvite")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("replyType", replyType)
.params("sessionID", sessionID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busooolive.model.OOOReturn_Ret.class));

}





/**
 * 1v1抢聊列表
 * @param pageIndex pageIndex
 * @param pageSize pageSize
 */
public static void getGrabAchatList(com.kalacheng.busooolive.model_fun.OOOCall_getGrabAchatList _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiPushChat> callback)
{
HttpClient.getInstance().post("/api/OOOCall/getGrabAchatList","/api/OOOCall/getGrabAchatList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiPushChat_RetArr.class));

}



/**
 * 1v1抢聊列表
 * @param pageIndex pageIndex
 * @param pageSize pageSize
 */
public static void getGrabAchatList(int pageIndex,int pageSize,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiPushChat> callback)
{
HttpClient.getInstance().post("/api/OOOCall/getGrabAchatList","/api/OOOCall/getGrabAchatList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiPushChat_RetArr.class));

}





/**
 * 退出求聊信息
 */
public static void exitPushChat(HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/OOOCall/exitPushChat","/api/OOOCall/exitPushChat")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 获取求聊页面数据
 * @param type 类型1视频聊天2语音聊天
 */
public static void getPushChatData(int type,HttpApiCallBack<com.kalacheng.libuser.model.ApiPushChatData> callback)
{
HttpClient.getInstance().post("/api/OOOCall/getPushChatData","/api/OOOCall/getPushChatData")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiPushChatData_Ret.class));

}





/**
 * 查询是否在求聊中no_use=0未求聊1视频求聊2语音求聊
 */
public static void isPushChat(HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/OOOCall/isPushChat","/api/OOOCall/isPushChat")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 挂断通话code;1成功；2通话已经结束；
 * @param resion 挂断原因；1正常；2直播云断掉了；
 * @param sessionID 会话ID
 */
public static void hangupCall(com.kalacheng.busooolive.model_fun.OOOCall_hangupCall _mdl,HttpApiCallBack<com.kalacheng.busooolive.model.OOOHangupReturn> callback)
{
HttpClient.getInstance().post("/api/OOOCall/hangupCall","/api/OOOCall/hangupCall")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("resion", _mdl.resion)
.params("sessionID", _mdl.sessionID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busooolive.model.OOOHangupReturn_Ret.class));

}



/**
 * 挂断通话code;1成功；2通话已经结束；
 * @param resion 挂断原因；1正常；2直播云断掉了；
 * @param sessionID 会话ID
 */
public static void hangupCall(int resion,long sessionID,HttpApiCallBack<com.kalacheng.busooolive.model.OOOHangupReturn> callback)
{
HttpClient.getInstance().post("/api/OOOCall/hangupCall","/api/OOOCall/hangupCall")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("resion", resion)
.params("sessionID", sessionID)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busooolive.model.OOOHangupReturn_Ret.class));

}


}
