package com.kalacheng.shortvideo.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.libuser.model.*;




/**
 * 短视频相关的socket发送
 */
public abstract class IMRcvShortVideoSend implements IMReceiver
{

 public String getMsgType() {
        return "ShortVideoSend";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onUserShortVideoCommentCount": {
onUserShortVideoCommentCount(content.getObject("apiSendVideoUnReadNumber", ApiSendVideoUnReadNumber.class));
}
break;
}
}

/**
 * 短视频评论或回复发送消息显示个数
 * @param apiSendVideoUnReadNumber null
 */
public abstract void onUserShortVideoCommentCount(com.kalacheng.libuser.model.ApiSendVideoUnReadNumber apiSendVideoUnReadNumber);
}
