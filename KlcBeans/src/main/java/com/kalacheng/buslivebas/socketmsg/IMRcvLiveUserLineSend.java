package com.kalacheng.buslivebas.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.libuser.model.*;




/**
 * 用户连麦发送消息
 */
public abstract class IMRcvLiveUserLineSend implements IMReceiver
{

 public String getMsgType() {
        return "LiveUserLineSend";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onUserCloseLine": {
onUserCloseLine(content.getObject("apiCloseLine", ApiCloseLine.class));
}
break;
   case "onSetAnchorLineStatus": {
onSetAnchorLineStatus(content.getObject("apiAnchorLineStatus", ApiAnchorLineStatus.class));
}
break;
   case "onUserLineReq": {
onUserLineReq(content.getObject("apiSendMsgUser", ApiSendMsgUser.class));
}
break;
   case "onUserLineResp": {
onUserLineResp(content.getObject("apiUserLineRoom", ApiUserLineRoom.class));
}
break;
}
}

/**
 * 用户/主播关闭连麦
 * @param apiCloseLine null
 */
public abstract void onUserCloseLine(com.kalacheng.libuser.model.ApiCloseLine apiCloseLine);

/**
 * 设置连麦状态,1:开启连麦,2:关闭连麦
 * @param apiAnchorLineStatus null
 */
public abstract void onSetAnchorLineStatus(com.kalacheng.libuser.model.ApiAnchorLineStatus apiAnchorLineStatus);

/**
 * 用户发送连麦请求
 * @param apiSendMsgUser null
 */
public abstract void onUserLineReq(com.kalacheng.libuser.model.ApiSendMsgUser apiSendMsgUser);

/**
 * 主播同意用户连麦,给房间发消息
 * @param apiUserLineRoom null
 */
public abstract void onUserLineResp(com.kalacheng.libuser.model.ApiUserLineRoom apiUserLineRoom);
}
