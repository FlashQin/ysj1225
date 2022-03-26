package com.kalacheng.busgraderight.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.libuser.model.*;




/**
 * 等级特权相关的socket
 */
public abstract class IMRcvGradeRightMsgSender implements IMReceiver
{

 public String getMsgType() {
        return "GradeRightMsgSender";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onElasticFrameFinshTask": {
onElasticFrameFinshTask(content.getObject("elasticFrame", ApiElasticFrame.class));
}
break;
   case "onElasticFrameMedal": {
onElasticFrameMedal(content.getObject("elasticFrame", ApiElasticFrame.class));
}
break;
   case "onElasticFrameUpgrade": {
onElasticFrameUpgrade(content.getObject("elasticFrame", ApiElasticFrame.class));
}
break;
}
}

/**
 * 完成任务弹框
 * @param elasticFrame null
 */
public abstract void onElasticFrameFinshTask(com.kalacheng.libuser.model.ApiElasticFrame elasticFrame);

/**
 * 获得勋章提示弹窗
 * @param elasticFrame null
 */
public abstract void onElasticFrameMedal(com.kalacheng.libuser.model.ApiElasticFrame elasticFrame);

/**
 * 升级提示弹窗
 * @param elasticFrame null
 */
public abstract void onElasticFrameUpgrade(com.kalacheng.libuser.model.ApiElasticFrame elasticFrame);
}
