package com.kalacheng.buspersonalcenter.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.libuser.model.*;




/**
 * 用户推送公告内容
 */
public abstract class IMRcvUserMsgSender implements IMReceiver
{

 public String getMsgType() {
        return "UserMsgSender";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onUsersBeautifulNumber": {
onUsersBeautifulNumber(content.getObject("user", ApiBeautifulNumber.class));
}
break;
}
}

/**
 * 赠送靓号推送公告内容
 * @param user null
 */
public abstract void onUsersBeautifulNumber(com.kalacheng.libuser.model.ApiBeautifulNumber user);
}
