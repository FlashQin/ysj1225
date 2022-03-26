package com.kalacheng.busnobility.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.libuser.model.*;

import com.kalacheng.busnobility.model.*;




/**
 * 赠送礼物发送消息
 */
public abstract class IMRcvLiveGiftSend implements IMReceiver
{

 public String getMsgType() {
        return "LiveGiftSend";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onSimpleGiftMsgRoom": {
onSimpleGiftMsgRoom(content.getObject("apiSimpleMsgRoom", ApiSimpleMsgRoom.class));
}
break;
   case "onGiftPKResult": {
onGiftPKResult(content.getObject("apiPKResult", ApiPkResult.class));
}
break;
   case "onGiveGiftUser": {
onGiveGiftUser(content.getObject("apiGiftSender", ApiGiftSender.class));
}
break;
   case "onGiveGift": {
onGiveGift(content.getObject("apiGiftSender", ApiGiftSender.class));
}
break;
   case "onGiftMsgAll": {
onGiftMsgAll(content.getObject("apiGiftSender", ApiGiftSender.class));
}
break;
}
}

/**
 * 简单消息
 * @param apiSimpleMsgRoom null
 */
public abstract void onSimpleGiftMsgRoom(com.kalacheng.libuser.model.ApiSimpleMsgRoom apiSimpleMsgRoom);

/**
 * 赠送礼物后血条信息
 * @param apiPKResult null
 */
public abstract void onGiftPKResult(com.kalacheng.busnobility.model.ApiPkResult apiPKResult);

/**
 * 赠送礼物后给用户发送消息
 * @param apiGiftSender null
 */
public abstract void onGiveGiftUser(com.kalacheng.libuser.model.ApiGiftSender apiGiftSender);

/**
 * 赠送礼物后给房间发送消息
 * @param apiGiftSender null
 */
public abstract void onGiveGift(com.kalacheng.libuser.model.ApiGiftSender apiGiftSender);

/**
 * 全直播间飘屏信息
 * @param apiGiftSender null
 */
public abstract void onGiftMsgAll(com.kalacheng.libuser.model.ApiGiftSender apiGiftSender);
}
