package com.kalacheng.busooolive.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.busooolive.model.*;

import com.kalacheng.buscommon.model.*;




/**
 * 一对一对多获取消息
 */
public abstract class IMRcvOTMLive implements IMReceiver
{

 public String getMsgType() {
        return "OTMLive";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "upVolumeOperation": {
upVolumeOperation(content.getLong("sessionID"),content.getObject("volumeRet", OOOVolumeRet.class));
}
break;
   case "hangupCall": {
hangupCall(content.getLong("sessionID"),content.getObject("hangupInfo", OOOHangupReturn.class));
}
break;
   case "cancelInvite": {
cancelInvite(content.getLong("sessionID"));
}
break;
   case "kickOutRoom": {
kickOutRoom(content.getLong("assisId"),content.getLong("sessionID"),content.getObject("hangupInfo", OOOHangupReturn.class));
}
break;
   case "inviteYouToChat": {
inviteYouToChat(content.getObject("userInfo", ApiUserInfo.class),content.getLong("sessionID"),content.getLong("TimeOutMilliSecond"),content.getInteger("isVideo"),content.getLong("feeUid"),content.getInteger("userStatus"),content.getDouble("oooFee"));
}
break;
   case "agreeLive": {
agreeLive(content.getObject("info", OOOReturn.class));
}
break;
   case "otmInviteEnd": {
otmInviteEnd(content.getLong("sessionID"),content.getInteger("reason"));
}
break;
   case "hangupCallUser": {
hangupCallUser(content.getLong("sessionID"),content.getObject("hangupInfo", OOOHangupReturn.class));
}
break;
   case "otmUptUserCoin": {
otmUptUserCoin(content.getLong("sessionID"),content.getDouble("coin"));
}
break;
   case "runningOutOfCoin": {
runningOutOfCoin(content.getLong("sessionID"),content.getInteger("times"));
}
break;
   case "refuseLive": {
refuseLive(content.getLong("sessionID"));
}
break;
}
}

/**
 * 1v1v7 开关麦克风 或者 禁言操作
 * @param sessionID null
 * @param volumeRet null
 */
public abstract void upVolumeOperation(long sessionID,com.kalacheng.busooolive.model.OOOVolumeRet volumeRet);

/**
 * 副播发起挂断电话，挂断聊天
 * @param sessionID null
 * @param hangupInfo null
 */
public abstract void hangupCall(long sessionID,com.kalacheng.busooolive.model.OOOHangupReturn hangupInfo);

/**
 * 1v1v7 付费方撤销了对指定副播的邀请
 * @param sessionID null
 */
public abstract void cancelInvite(long sessionID);

/**
 * 1v1v7 将指定id的副播踢出了房间
 * @param assisId 被踢出的副播id
 * @param sessionID null
 * @param hangupInfo null
 */
public abstract void kickOutRoom(long assisId,long sessionID,com.kalacheng.busooolive.model.OOOHangupReturn hangupInfo);

/**
 * 别人邀请你聊天
 * @param userInfo null
 * @param sessionID null
 * @param TimeOutMilliSecond null
 * @param isVideo null
 * @param feeUid 被收费用户
 * @param userStatus 被邀请人状态 0:离线 1:忙碌 2:在线 3:通话中 4:看直播 5:匹配中 6:直播中 7:离开
 * @param oooFee null
 */
public abstract void inviteYouToChat(com.kalacheng.buscommon.model.ApiUserInfo userInfo,long sessionID,long TimeOutMilliSecond,int isVideo,long feeUid,int userStatus,double oooFee);

/**
 * 同意邀请
 * @param info null
 */
public abstract void agreeLive(com.kalacheng.busooolive.model.OOOReturn info);

/**
 * 邀请终止-双方都会收到
 * @param sessionID null
 * @param reason 原因：13等待超时；12：网络断开
 */
public abstract void otmInviteEnd(long sessionID,int reason);

/**
 * 主播/用户 挂断电话，挂断聊天
 * @param sessionID null
 * @param hangupInfo null
 */
public abstract void hangupCallUser(long sessionID,com.kalacheng.busooolive.model.OOOHangupReturn hangupInfo);

/**
 * 消费者金币余额
 * @param sessionID null
 * @param coin 金币余额
 */
public abstract void otmUptUserCoin(long sessionID,double coin);

/**
 * 金币不足，余额不足提醒，金币快用光了
 * @param sessionID null
 * @param times 60费用仅够1分钟；180费用仅够3分钟；返回的是180秒倒计时
 */
public abstract void runningOutOfCoin(long sessionID,int times);

/**
 * 拒绝邀请
 * @param sessionID null
 */
public abstract void refuseLive(long sessionID);
}
