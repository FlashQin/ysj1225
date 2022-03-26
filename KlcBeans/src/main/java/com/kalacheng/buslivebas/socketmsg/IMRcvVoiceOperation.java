package com.kalacheng.buslivebas.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.libuser.model.*;




/**
 * 多人语音操作的推送消息
 */
public abstract class IMRcvVoiceOperation implements IMReceiver
{

 public String getMsgType() {
        return "VoiceOperation";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onUpVoiceAssistan": {
onUpVoiceAssistan((new JsonObjConvert<ApiUsersVoiceAssistan>()).getObjArr(content,"apiVoiceAssistanEntity", ApiUsersVoiceAssistan.class),content.getLong("upUid"));
}
break;
   case "downVoiceAssistan": {
downVoiceAssistan((new JsonObjConvert<ApiUsersVoiceAssistan>()).getObjArr(content,"apiVoiceAssistanEntity", ApiUsersVoiceAssistan.class),content.getLong("downUid"));
}
break;
   case "sendAnchorSticker": {
sendAnchorSticker(content.getString("gifUrl"));
}
break;
   case "kickOutAssistan": {
kickOutAssistan(content.getLong("assisId"),(new JsonObjConvert<ApiUsersVoiceAssistan>()).getObjArr(content,"assitan", ApiUsersVoiceAssistan.class));
}
break;
   case "offVolumn": {
offVolumn((new JsonObjConvert<ApiUsersVoiceAssistan>()).getObjArr(content,"apiVoiceAssistanEntity", ApiUsersVoiceAssistan.class),content.getLong("setUid"));
}
break;
   case "LockThisAssistan": {
LockThisAssistan(content.getInteger("assistanNo"),(new JsonObjConvert<ApiUsersVoiceAssistan>()).getObjArr(content,"assitan", ApiUsersVoiceAssistan.class));
}
break;
   case "hostOffVolumn": {
hostOffVolumn(content.getInteger("onOffState"));
}
break;
   case "sendStricker": {
sendStricker(content.getInteger("no"),(new JsonObjConvert<ApiUsersVoiceAssistan>()).getObjArr(content,"hGetAssistanList", ApiUsersVoiceAssistan.class));
}
break;
   case "sendGift": {
sendGift((new JsonObjConvert<ApiUsersVoiceAssistan>()).getObjArr(content,"hGetAssistanList", ApiUsersVoiceAssistan.class));
}
break;
}
}

/**
 * 观众上麦操作
 * @param apiVoiceAssistanEntity null
 * @param upUid null
 */
public abstract void onUpVoiceAssistan(List<com.kalacheng.libuser.model.ApiUsersVoiceAssistan>  apiVoiceAssistanEntity,long upUid);

/**
 * 副播下麦操作
 * @param apiVoiceAssistanEntity null
 * @param downUid null
 */
public abstract void downVoiceAssistan(List<com.kalacheng.libuser.model.ApiUsersVoiceAssistan>  apiVoiceAssistanEntity,long downUid);

/**
 * 语音房间非PK状态下的主播发送表情包
 * @param gifUrl null
 */
public abstract void sendAnchorSticker(String gifUrl);

/**
 * 主播将指定麦序的麦位上副播踢出
 * @param assisId 被踢出的副播id
 * @param assitan null
 */
public abstract void kickOutAssistan(long assisId,List<com.kalacheng.libuser.model.ApiUsersVoiceAssistan>  assitan);

/**
 * 副播开关麦操作
 * @param apiVoiceAssistanEntity null
 * @param setUid null
 */
public abstract void offVolumn(List<com.kalacheng.libuser.model.ApiUsersVoiceAssistan>  apiVoiceAssistanEntity,long setUid);

/**
 * 封锁指定麦序的麦位
 * @param assistanNo 指定麦序
 * @param assitan null
 */
public abstract void LockThisAssistan(int assistanNo,List<com.kalacheng.libuser.model.ApiUsersVoiceAssistan>  assitan);

/**
 * 主播开关麦操作
 * @param onOffState null
 */
public abstract void hostOffVolumn(int onOffState);

/**
 * 语音房间非PK状态下的副播发送表情包
 * @param no null
 * @param hGetAssistanList null
 */
public abstract void sendStricker(int no,List<com.kalacheng.libuser.model.ApiUsersVoiceAssistan>  hGetAssistanList);

/**
 * 语音房间非PK状态下的送礼
 * @param hGetAssistanList null
 */
public abstract void sendGift(List<com.kalacheng.libuser.model.ApiUsersVoiceAssistan>  hGetAssistanList);
}
