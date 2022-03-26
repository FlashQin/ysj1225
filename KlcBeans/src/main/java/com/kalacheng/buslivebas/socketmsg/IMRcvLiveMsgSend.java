package com.kalacheng.buslivebas.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.libuser.model.*;




/**
 * 直播间发送消息
 */
public abstract class IMRcvLiveMsgSend implements IMReceiver
{

 public String getMsgType() {
        return "LiveMsgSend";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onUserNoticMsg": {
onUserNoticMsg(content.getString("conetnt"));
}
break;
   case "onUserSendApiJoinRoom": {
onUserSendApiJoinRoom(content.getObject("appJoinRoomVO", AppJoinRoomVO.class));
}
break;
   case "onAppointUserSend": {
onAppointUserSend(content.getObject("apiSendMsgRoom", ApiSendMsgRoom.class));
}
break;
   case "onSimpleMsgRoom": {
onSimpleMsgRoom(content.getObject("apiSimpleMsgRoom", ApiSimpleMsgRoom.class));
}
break;
   case "onUserTimmerRoomRemind": {
onUserTimmerRoomRemind(content.getInteger("times"));
}
break;
   case "onUserSendMsgRoom": {
onUserSendMsgRoom(content.getObject("apiSendMsgRoom", ApiSendMsgRoom.class));
}
break;
   case "onUserUpLiveTypeExitRoom": {
onUserUpLiveTypeExitRoom(content.getObject("apiExitRoom", ApiExitRoom.class));
}
break;
   case "onTimerExitRoom": {
onTimerExitRoom(content.getObject("apiTimerExitRoom", ApiTimerExitRoom.class));
}
break;
   case "onSimpleMsgAll": {
onSimpleMsgAll(content.getObject("apiSimpleMsgRoom", ApiSimpleMsgRoom.class));
}
break;
   case "onRoomBan": {
onRoomBan(content.getLong("sessionID"),content.getString("banInfo"));
}
break;
   case "onUserBan": {
onUserBan(content.getLong("sessionID"),content.getString("banInfo"));
}
break;
}
}

/**
 * 修改房间公告推送公告内容
 * @param conetnt null
 */
public abstract void onUserNoticMsg(String conetnt);

/**
 * 在直播间发送消息
 * @param appJoinRoomVO null
 */
public abstract void onUserSendApiJoinRoom(com.kalacheng.libuser.model.AppJoinRoomVO appJoinRoomVO);

/**
 * 指定用户发消息
 * @param apiSendMsgRoom null
 */
public abstract void onAppointUserSend(com.kalacheng.libuser.model.ApiSendMsgRoom apiSendMsgRoom);

/**
 * 简单消息
 * @param apiSimpleMsgRoom null
 */
public abstract void onSimpleMsgRoom(com.kalacheng.libuser.model.ApiSimpleMsgRoom apiSimpleMsgRoom);

/**
 * 计时房间余额不足时发送提醒
 * @param times 还能观看倒计时秒
 */
public abstract void onUserTimmerRoomRemind(int times);

/**
 * 在直播间发送消息
 * @param apiSendMsgRoom null
 */
public abstract void onUserSendMsgRoom(com.kalacheng.libuser.model.ApiSendMsgRoom apiSendMsgRoom);

/**
 * 修改房间类型
 * @param apiExitRoom null
 */
public abstract void onUserUpLiveTypeExitRoom(com.kalacheng.libuser.model.ApiExitRoom apiExitRoom);

/**
 * 计时房间余额不足退出房间
 * @param apiTimerExitRoom null
 */
public abstract void onTimerExitRoom(com.kalacheng.libuser.model.ApiTimerExitRoom apiTimerExitRoom);

/**
 * 后台发送系统消息
 * @param apiSimpleMsgRoom null
 */
public abstract void onSimpleMsgAll(com.kalacheng.libuser.model.ApiSimpleMsgRoom apiSimpleMsgRoom);

/**
 * 主播封禁后,向发送socket信息
 * @param sessionID null
 * @param banInfo null
 */
public abstract void onRoomBan(long sessionID,String banInfo);

/**
 * 用户封禁后,向发送socket信息
 * @param sessionID null
 * @param banInfo null
 */
public abstract void onUserBan(long sessionID,String banInfo);
}
