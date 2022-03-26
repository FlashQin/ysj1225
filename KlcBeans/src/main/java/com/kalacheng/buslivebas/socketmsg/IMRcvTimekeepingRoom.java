package com.kalacheng.buslivebas.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;




/**
 * 计时房间消息
 */
public abstract class IMRcvTimekeepingRoom implements IMReceiver
{

 public String getMsgType() {
        return "TimekeepingRoom";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "insufficientUserBalancePrompt": {
insufficientUserBalancePrompt(content.getLong("times"));
}
break;
   case "remindToRecharge": {
remindToRecharge(content.getString("msg"));
}
break;
}
}

/**
 * 余额不足提示
 * @param times 60费用仅够1分钟；180费用仅够3分钟；返回的是180秒倒计时
 */
public abstract void insufficientUserBalancePrompt(long times);

/**
 * 提醒充值
 * @param msg null
 */
public abstract void remindToRecharge(String msg);
}
