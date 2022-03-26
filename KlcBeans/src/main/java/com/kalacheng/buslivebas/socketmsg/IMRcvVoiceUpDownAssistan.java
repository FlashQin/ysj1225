package com.kalacheng.buslivebas.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.libuser.model.*;




/**
 * 多人语音上下麦相关
 */
public abstract class IMRcvVoiceUpDownAssistan implements IMReceiver
{

 public String getMsgType() {
        return "VoiceUpDownAssistan";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onVoiceLineNumber": {
onVoiceLineNumber(content.getInteger("num"));
}
break;
   case "agreeUpAstApply": {
agreeUpAstApply(content.getLong("roomID"));
}
break;
   case "refuseUpAstApply": {
refuseUpAstApply(content.getLong("roomID"));
}
break;
   case "cancelApplyUp": {
cancelApplyUp(content.getLong("authId"));
}
break;
   case "onVoiceLineRequset": {
onVoiceLineRequset(content.getObject("apiUsersVoiceAssistans", ApiUsersVoiceAssistan.class));
}
break;
   case "applyUpTimeOut": {
applyUpTimeOut(content.getLong("applyUid"));
}
break;
}
}

/**
 * 直播间内申请上麦的游客数量
 * @param num null
 */
public abstract void onVoiceLineNumber(int num);

/**
 * 房主同意游客的上麦申请
 * @param roomID null
 */
public abstract void agreeUpAstApply(long roomID);

/**
 * 房主拒绝游客的上麦申请
 * @param roomID null
 */
public abstract void refuseUpAstApply(long roomID);

/**
 * 撤销上麦申请
 * @param authId 撤销方ID
 */
public abstract void cancelApplyUp(long authId);

/**
 * 直播间内游客申请上麦，给主播发送申请通知信息
 * @param apiUsersVoiceAssistans null
 */
public abstract void onVoiceLineRequset(com.kalacheng.libuser.model.ApiUsersVoiceAssistan apiUsersVoiceAssistans);

/**
 * 申请上麦超时 两边都发
 * @param applyUid 申请人ID
 */
public abstract void applyUpTimeOut(long applyUid);
}
