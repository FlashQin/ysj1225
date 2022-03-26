package com.kalacheng.buslivebas.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.libuser.model.*;

import com.kalacheng.buscommon.model.*;




/**
 * 多人语音邀请相关
 */
public abstract class IMRcvVoiceInvite implements IMReceiver
{

 public String getMsgType() {
        return "VoiceInvite";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "invtUserUpAssitan": {
invtUserUpAssitan(content.getObject("joinRoom", AppJoinRoomVO.class),content.getObject("inviteInfo", ApiUserInfo.class),content.getInteger("isPay"));
}
break;
   case "acceptVoice": {
acceptVoice(content.getLong("roomID"));
}
break;
   case "refuseVoice": {
refuseVoice(content.getLong("roomID"));
}
break;
   case "invtTimeOut": {
invtTimeOut(content.getLong("sendInvtUid"));
}
break;
}
}

/**
 * 主播邀请上麦
 * @param joinRoom null
 * @param inviteInfo null
 * @param isPay 门票房间是否付过钱  -1不是门票房间  1是门票房间付过钱 0是门票房间没付过钱
 */
public abstract void invtUserUpAssitan(com.kalacheng.libuser.model.AppJoinRoomVO joinRoom,com.kalacheng.buscommon.model.ApiUserInfo inviteInfo,int isPay);

/**
 * 接受房主的连麦邀请
 * @param roomID null
 */
public abstract void acceptVoice(long roomID);

/**
 * 拒绝房主的连麦邀请
 * @param roomID null
 */
public abstract void refuseVoice(long roomID);

/**
 * 邀请上麦超时(双方都要收到)
 * @param sendInvtUid 邀请人ID
 */
public abstract void invtTimeOut(long sendInvtUid);
}
