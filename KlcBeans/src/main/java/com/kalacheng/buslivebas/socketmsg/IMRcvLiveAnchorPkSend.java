package com.kalacheng.buslivebas.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.libuser.model.*;




/**
 * 主播PK发送消息
 */
public abstract class IMRcvLiveAnchorPkSend implements IMReceiver
{

 public String getMsgType() {
        return "LiveAnchorPkSend";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onAnchorPKReq": {
onAnchorPKReq(content.getObject("apiSendMsgUser", ApiSendMsgUser.class));
}
break;
   case "onAnchorPKResp": {
onAnchorPKResp(content.getObject("ApiSendPKMsgRoom", ApiSendPKMsgRoom.class));
}
break;
   case "onPKResultResp": {
onPKResultResp(content.getObject("apiPkResultRoom", ApiPkResultRoom.class));
}
break;
}
}

/**
 * 主播发送PK请求
 * @param apiSendMsgUser null
 */
public abstract void onAnchorPKReq(com.kalacheng.libuser.model.ApiSendMsgUser apiSendMsgUser);

/**
 * 主播在直播间发送是否同意PK请求
 * @param ApiSendPKMsgRoom null
 */
public abstract void onAnchorPKResp(com.kalacheng.libuser.model.ApiSendPKMsgRoom ApiSendPKMsgRoom);

/**
 * PK结果响应
 * @param apiPkResultRoom null
 */
public abstract void onPKResultResp(com.kalacheng.libuser.model.ApiPkResultRoom apiPkResultRoom);
}
