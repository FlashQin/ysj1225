package com.kalacheng.buslivebas.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.libuser.model.*;




/**
 * 主播互动发送消息
 */
public abstract class IMRcvLiveAnchorLineSend implements IMReceiver
{

 public String getMsgType() {
        return "LiveAnchorLineSend";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onAnchorCloseLine": {
onAnchorCloseLine(content.getObject("apiCloseLine", ApiCloseLine.class));
}
break;
   case "onAnchorLineResp": {
onAnchorLineResp(content.getObject("apiSendLineMsgRoom", ApiSendLineMsgRoom.class));
}
break;
   case "onAnchorLineReq": {
onAnchorLineReq(content.getObject("apiSendMsgUser", ApiSendMsgUser.class));
}
break;
}
}

/**
 * 主播关闭互动
 * @param apiCloseLine null
 */
public abstract void onAnchorCloseLine(com.kalacheng.libuser.model.ApiCloseLine apiCloseLine);

/**
 * 主播在直播间发送是否同意连麦消息
 * @param apiSendLineMsgRoom null
 */
public abstract void onAnchorLineResp(com.kalacheng.libuser.model.ApiSendLineMsgRoom apiSendLineMsgRoom);

/**
 * 主播发送连麦请求
 * @param apiSendMsgUser null
 */
public abstract void onAnchorLineReq(com.kalacheng.libuser.model.ApiSendMsgUser apiSendMsgUser);
}
