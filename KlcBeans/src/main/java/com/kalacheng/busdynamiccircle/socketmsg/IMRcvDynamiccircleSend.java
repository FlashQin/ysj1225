package com.kalacheng.busdynamiccircle.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.libuser.model.*;




/**
 * 动态消息发送socket
 */
public abstract class IMRcvDynamiccircleSend implements IMReceiver
{

 public String getMsgType() {
        return "DynamiccircleSend";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onUserVideoCommentCount": {
onUserVideoCommentCount(content.getObject("apiSendVideoUnReadNumber", ApiSendVideoUnReadNumber.class));
}
break;
}
}

/**
 * 动态评论或回复发送消息显示个数
 * @param apiSendVideoUnReadNumber null
 */
public abstract void onUserVideoCommentCount(com.kalacheng.libuser.model.ApiSendVideoUnReadNumber apiSendVideoUnReadNumber);
}
