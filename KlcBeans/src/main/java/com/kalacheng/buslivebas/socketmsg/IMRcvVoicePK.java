package com.kalacheng.buslivebas.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.libuser.model.*;




/**
 * 多人语音PK相关
 */
public abstract class IMRcvVoicePK implements IMReceiver
{

 public String getMsgType() {
        return "VoicePK";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "updatePK": {
updatePK(content.getObject("thisPkVO", VoicePkVO.class),content.getLong("optUid"),content.getInteger("optType"));
}
break;
   case "kickedBeforeOpen": {
kickedBeforeOpen(content.getInteger("pkType"));
}
break;
   case "quitPK": {
quitPK((new JsonObjConvert<ApiUsersVoiceAssistan>()).getObjArr(content,"thisAssistans", ApiUsersVoiceAssistan.class),content.getLong("optUid"),content.getInteger("pkType"));
}
break;
   case "tellAuthorMatched": {
tellAuthorMatched(content.getObject("thisPkVO", VoicePkVO.class));
}
break;
   case "startPK": {
startPK(content.getObject("thisPkVO", VoicePkVO.class));
}
break;
   case "OpenPKSuccess": {
OpenPKSuccess(content.getObject("thisPkVO", VoicePkVO.class));
}
break;
   case "matchPkTimeOut": {
matchPkTimeOut(content.getInteger("pktype"));
}
break;
   case "beforefinishPK": {
beforefinishPK(content.getObject("thisPkVO", VoicePkVO.class));
}
break;
   case "sendGiftPk": {
sendGiftPk((new JsonObjConvert<PkGiftSender>()).getObjArr(content,"thisSenders", PkGiftSender.class),(new JsonObjConvert<PkGiftSender>()).getObjArr(content,"otherSenders", PkGiftSender.class));
}
break;
}
}

/**
 * 中途加入PK人员变动 开关麦 或者 送礼活力值变更  两边都要发
 * @param thisPkVO null
 * @param optUid 麦位上的涉及方ID 或者火力值更新时的送礼方ID
 * @param optType 操作类型  1上麦 2下麦 3开麦 4关麦 5火力值更新 6被踢下麦 7锁麦 8表情包
 */
public abstract void updatePK(com.kalacheng.libuser.model.VoicePkVO thisPkVO,long optUid,int optType);

/**
 * PK进入倒计时前踢出未参与的用户
 * @param pkType null
 */
public abstract void kickedBeforeOpen(int pkType);

/**
 * 主播提前退出PK 两边都发
 * @param thisAssistans null
 * @param optUid 发起强退方的ID
 * @param pkType 所退出的PK类型
 */
public abstract void quitPK(List<com.kalacheng.libuser.model.ApiUsersVoiceAssistan>  thisAssistans,long optUid,int pkType);

/**
 * 通知双方主播PK匹配成功，准备进入PK开始倒计时
 * @param thisPkVO null
 */
public abstract void tellAuthorMatched(com.kalacheng.libuser.model.VoicePkVO thisPkVO);

/**
 * 倒计时结束后开始PK
 * @param thisPkVO null
 */
public abstract void startPK(com.kalacheng.libuser.model.VoicePkVO thisPkVO);

/**
 * 开始倒计时 预备进入PK (只向主播发) 
 * @param thisPkVO null
 */
public abstract void OpenPKSuccess(com.kalacheng.libuser.model.VoicePkVO thisPkVO);

/**
 * 主播PK匹配超时
 * @param pktype null
 */
public abstract void matchPkTimeOut(int pktype);

/**
 * PK结果 惩罚或平局
 * @param thisPkVO null
 */
public abstract void beforefinishPK(com.kalacheng.libuser.model.VoicePkVO thisPkVO);

/**
 * pk更新礼物
 * @param thisSenders null
 * @param otherSenders null
 */
public abstract void sendGiftPk(List<com.kalacheng.libuser.model.PkGiftSender>  thisSenders,List<com.kalacheng.libuser.model.PkGiftSender>  otherSenders);
}
