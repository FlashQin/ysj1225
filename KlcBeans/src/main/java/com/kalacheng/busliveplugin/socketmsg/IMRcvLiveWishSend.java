package com.kalacheng.busliveplugin.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.libuser.model.*;




/**
 * 心愿单 socket
 */
public abstract class IMRcvLiveWishSend implements IMReceiver
{

 public String getMsgType() {
        return "LiveWishSend";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onSendWishUser": {
onSendWishUser((new JsonObjConvert<ApiUsersLiveWish>()).getObjArr(content,"awList", ApiUsersLiveWish.class));
}
break;
   case "onSendWish": {
onSendWish((new JsonObjConvert<ApiUsersLiveWish>()).getObjArr(content,"awList", ApiUsersLiveWish.class));
}
break;
   case "onUserAddWishMsg": {
onUserAddWishMsg((new JsonObjConvert<ApiUsersLiveWish>()).getObjArr(content,"list", ApiUsersLiveWish.class));
}
break;
   case "onUserAddWishMsgUser": {
onUserAddWishMsgUser((new JsonObjConvert<ApiUsersLiveWish>()).getObjArr(content,"list", ApiUsersLiveWish.class));
}
break;
}
}

/**
 * 心愿单设置成功推给用户
 * @param awList null
 */
public abstract void onSendWishUser(List<com.kalacheng.libuser.model.ApiUsersLiveWish>  awList);

/**
 * 心愿单设置成功推给房间
 * @param awList null
 */
public abstract void onSendWish(List<com.kalacheng.libuser.model.ApiUsersLiveWish>  awList);

/**
 * 主播收到心愿单礼物后推送给直播间
 * @param list null
 */
public abstract void onUserAddWishMsg(List<com.kalacheng.libuser.model.ApiUsersLiveWish>  list);

/**
 * 主播收到心愿单礼物后推送给直播间(用户)
 * @param list null
 */
public abstract void onUserAddWishMsgUser(List<com.kalacheng.libuser.model.ApiUsersLiveWish>  list);
}
