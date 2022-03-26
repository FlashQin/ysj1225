package com.kalacheng.busfinance.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.buscommon.model.*;

import com.kalacheng.libuser.model.*;




/**
 * 发送消息给所有人
 */
public abstract class IMRcvLiveMoneyMsgAllSend implements IMReceiver
{

 public String getMsgType() {
        return "LiveMoneyMsgAllSend";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onUserRechargeCallbackMsg": {
onUserRechargeCallbackMsg(content.getDouble("coin"),content.getObject("user", ApiUserInfo.class));
}
break;
   case "onElasticFrameMember": {
onElasticFrameMember(content.getObject("elasticFrame", ApiElasticFrame.class));
}
break;
}
}

/**
 * 充值金币成功后通知前端
 * @param coin 充值的金币数
 * @param user null
 */
public abstract void onUserRechargeCallbackMsg(double coin,com.kalacheng.buscommon.model.ApiUserInfo user);

/**
 * 开通会员提示飘窗
 * @param elasticFrame null
 */
public abstract void onElasticFrameMember(com.kalacheng.libuser.model.ApiElasticFrame elasticFrame);
}
