package com.kalacheng.bususer.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.buscommon.model.*;




/**
 * 主播相关的socket
 */
public abstract class IMRcvAnchorMsgSender implements IMReceiver
{

 public String getMsgType() {
        return "AnchorMsgSender";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onAnchorAuthUser": {
onAnchorAuthUser(content.getObject("user", ApiUserInfo.class));
}
break;
}
}

/**
 * 主播认证成功推送socket
 * @param user null
 */
public abstract void onAnchorAuthUser(com.kalacheng.buscommon.model.ApiUserInfo user);
}
