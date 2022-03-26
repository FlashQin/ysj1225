package com.kalacheng.template.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.template.model.*;




/**
 * 用于测试的SOCKET
 */
public abstract class IMRcvATestSender implements IMReceiver
{

 public String getMsgType() {
        return "aTestSender";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onMyMsg": {
onMyMsg(content.getObject("mdl", aTestModle.class));
}
break;
   case "onRoomMsg": {
onRoomMsg(content.getObject("mdl", aTestModle.class));
}
break;
   case "onAllMsg": {
onAllMsg(content.getObject("mdl", aTestModle.class));
}
break;
}
}

/**
 * 
 * @param mdl null
 */
public abstract void onMyMsg(com.kalacheng.template.model.aTestModle mdl);

/**
 * 
 * @param mdl null
 */
public abstract void onRoomMsg(com.kalacheng.template.model.aTestModle mdl);

/**
 * 
 * @param mdl null
 */
public abstract void onAllMsg(com.kalacheng.template.model.aTestModle mdl);
}
