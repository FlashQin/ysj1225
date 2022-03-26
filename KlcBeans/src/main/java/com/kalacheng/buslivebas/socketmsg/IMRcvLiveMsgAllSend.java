package com.kalacheng.buslivebas.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.libuser.model.*;




/**
 * 发送消息给所有人
 */
public abstract class IMRcvLiveMsgAllSend implements IMReceiver
{

 public String getMsgType() {
        return "LiveMsgAllSend";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onMsgAll": {
onMsgAll(content.getObject("apiGiftSender", ApiGiftSender.class));
}
break;
   case "onMsgAllForBroadCast": {
onMsgAllForBroadCast(content.getLong("roomId"),content.getObject("apiSimpleMsgRoom", ApiSimpleMsgRoom.class));
}
break;
}
}

/**
 * 全直播间飘屏信息
 * @param apiGiftSender null
 */
public abstract void onMsgAll(com.kalacheng.libuser.model.ApiGiftSender apiGiftSender);

/**
 * 直播间发消息全站广播
 * @param roomId null
 * @param apiSimpleMsgRoom null
 */
public abstract void onMsgAllForBroadCast(long roomId,com.kalacheng.libuser.model.ApiSimpleMsgRoom apiSimpleMsgRoom);
}
