package com.kalacheng.busooolive.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.buscommon.model.*;

import com.kalacheng.busooolive.model.*;

import com.kalacheng.libuser.model.*;




/**
 * 一对一获取消息
 */
public abstract class IMRcvOOOLive implements IMReceiver
{

 public String getMsgType() {
        return "OOOLive";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "oooInviteYouToChat": {
oooInviteYouToChat(content.getObject("userInfo", ApiUserInfo.class),content.getLong("sessionID"),content.getLong("TimeOutMilliSecond"),content.getInteger("isVideo"),content.getLong("feeUid"),content.getInteger("userStatus"),content.getDouble("oooFee"));
}
break;
   case "oooAgreeLive": {
oooAgreeLive(content.getObject("info", OOOReturn.class));
}
break;
   case "oooCancelInvite": {
oooCancelInvite(content.getLong("sessionID"));
}
break;
   case "oooRefuseLive": {
oooRefuseLive(content.getLong("sessionID"));
}
break;
   case "oooHangupCall": {
oooHangupCall(content.getLong("sessionID"),content.getObject("hangupInfo", OOOHangupReturn.class));
}
break;
   case "onGirlUserToMaleUser": {
onGirlUserToMaleUser(content.getObject("pushChat", ApiPushChat.class));
}
break;
   case "oooInviteEnd": {
oooInviteEnd(content.getLong("sessionID"),content.getInteger("resion"));
}
break;
}
}

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
public abstract void oooInviteYouToChat(com.kalacheng.buscommon.model.ApiUserInfo userInfo,long sessionID,long TimeOutMilliSecond,int isVideo,long feeUid,int userStatus,double oooFee);

/**
 * 同意邀请
 * @param info null
 */
public abstract void oooAgreeLive(com.kalacheng.busooolive.model.OOOReturn info);

/**
 * 邀请被发起方撤销
 * @param sessionID null
 */
public abstract void oooCancelInvite(long sessionID);

/**
 * 拒绝邀请
 * @param sessionID null
 */
public abstract void oooRefuseLive(long sessionID);

/**
 * 挂断电话，挂断聊天
 * @param sessionID null
 * @param hangupInfo null
 */
public abstract void oooHangupCall(long sessionID,com.kalacheng.busooolive.model.OOOHangupReturn hangupInfo);

/**
 * 向女用户推送某个求聊信息
 * @param pushChat null
 */
public abstract void onGirlUserToMaleUser(com.kalacheng.libuser.model.ApiPushChat pushChat);

/**
 * 邀请终止-双方都会收到
 * @param sessionID null
 * @param resion 原因：13等待超时；12：网络断开
 */
public abstract void oooInviteEnd(long sessionID,int resion);
}
