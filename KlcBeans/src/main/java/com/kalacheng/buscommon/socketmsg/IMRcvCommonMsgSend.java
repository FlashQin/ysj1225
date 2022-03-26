package com.kalacheng.buscommon.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;




/**
 * 用户获取消息socket
 */
public abstract class IMRcvCommonMsgSend implements IMReceiver
{

 public String getMsgType() {
        return "CommonMsgSend";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onUserGetNoReadAll": {
onUserGetNoReadAll(content.getInteger("count"));
}
break;
}
}

/**
 * 总未读数
 * @param count 总未读数
 */
public abstract void onUserGetNoReadAll(int count);
}
