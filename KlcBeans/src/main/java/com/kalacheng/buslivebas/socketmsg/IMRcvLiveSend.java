package com.kalacheng.buslivebas.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.libuser.model.*;

import com.kalacheng.buscommon.model.*;




/**
 * 直播间live发送消息
 */
public abstract class IMRcvLiveSend implements IMReceiver
{

 public String getMsgType() {
        return "LiveSend";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onCloseLive": {
onCloseLive(content.getObject("apiCloseLive", ApiCloseLive.class));
}
break;
   case "onUserLeaveRoom": {
onUserLeaveRoom(content.getObject("apiLeaveRoom", ApiLeaveRoom.class));
}
break;
   case "onManageLeaveRoom": {
onManageLeaveRoom(content.getObject("apiCloseLive", ApiCloseLive.class));
}
break;
   case "onAnchorLeaveRoom": {
onAnchorLeaveRoom(content.getObject("ApiLeaveRoomAnchor", ApiLeaveRoomAnchor.class));
}
break;
   case "onDownVoiceAssistan": {
onDownVoiceAssistan(content.getObject("apiVoiceAssistanEntity", ApiUsersVoiceAssistan.class));
}
break;
   case "onAnchorJoinRoom": {
onAnchorJoinRoom(content.getObject("apiJoinRoomAnchor", ApiJoinRoomAnchor.class));
}
break;
   case "onUserBackground": {
onUserBackground(content.getString("voicethumb"));
}
break;
   case "onBuyGuardListRoom": {
onBuyGuardListRoom((new JsonObjConvert<GuardUserDto>()).getObjArr(content,"list", GuardUserDto.class));
}
break;
   case "onJoinRoomMsgRoom": {
onJoinRoomMsgRoom(content.getObject("apiSimpleMsgRoom", ApiSimpleMsgRoom.class));
}
break;
   case "onManageKickRoom": {
onManageKickRoom(content.getObject("apiKickLive", ApiKickLive.class));
}
break;
   case "onUsersVIPSeats": {
onUsersVIPSeats(content.getObject("apiUserSeats", ApiUserSeats.class));
}
break;
   case "onUserJoinRoom": {
onUserJoinRoom(content.getObject("appJoinRoomVO", AppJoinRoomVO.class));
}
break;
}
}

/**
 * 关闭直播间发送消息
 * @param apiCloseLive null
 */
public abstract void onCloseLive(com.kalacheng.libuser.model.ApiCloseLive apiCloseLive);

/**
 * 离开房间给房间发送消息
 * @param apiLeaveRoom null
 */
public abstract void onUserLeaveRoom(com.kalacheng.libuser.model.ApiLeaveRoom apiLeaveRoom);

/**
 * 后台强制关闭房间
 * @param apiCloseLive null
 */
public abstract void onManageLeaveRoom(com.kalacheng.libuser.model.ApiCloseLive apiCloseLive);

/**
 * 主播离开房间
 * @param ApiLeaveRoomAnchor null
 */
public abstract void onAnchorLeaveRoom(com.kalacheng.libuser.model.ApiLeaveRoomAnchor ApiLeaveRoomAnchor);

/**
 * 下麦操作
 * @param apiVoiceAssistanEntity null
 */
public abstract void onDownVoiceAssistan(com.kalacheng.libuser.model.ApiUsersVoiceAssistan apiVoiceAssistanEntity);

/**
 * 主播回来房间
 * @param apiJoinRoomAnchor null
 */
public abstract void onAnchorJoinRoom(com.kalacheng.libuser.model.ApiJoinRoomAnchor apiJoinRoomAnchor);

/**
 * 修改房间背景图推送公告内容
 * @param voicethumb null
 */
public abstract void onUserBackground(String voicethumb);

/**
 * 购买守护列表消息
 * @param list null
 */
public abstract void onBuyGuardListRoom(List<com.kalacheng.buscommon.model.GuardUserDto>  list);

/**
 * 土豪进场消息
 * @param apiSimpleMsgRoom null
 */
public abstract void onJoinRoomMsgRoom(com.kalacheng.libuser.model.ApiSimpleMsgRoom apiSimpleMsgRoom);

/**
 * 踢人消息
 * @param apiKickLive null
 */
public abstract void onManageKickRoom(com.kalacheng.libuser.model.ApiKickLive apiKickLive);

/**
 * 购买贵宾席推送公告内容
 * @param apiUserSeats null
 */
public abstract void onUsersVIPSeats(com.kalacheng.libuser.model.ApiUserSeats apiUserSeats);

/**
 * 加入直播间发送消息
 * @param appJoinRoomVO null
 */
public abstract void onUserJoinRoom(com.kalacheng.libuser.model.AppJoinRoomVO appJoinRoomVO);
}
