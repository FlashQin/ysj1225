package com.kalacheng.shop.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.libuser.model.*;




/**
 * 直播购相关的socket
 */
public abstract class IMRcvShopMsgSend implements IMReceiver
{

 public String getMsgType() {
        return "ShopMsgSend";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onUsersShopBanner": {
onUsersShopBanner(content.getString("shopLiveBanner"));
}
break;
   case "onUsersLiveGoodsStatus": {
onUsersLiveGoodsStatus(content.getObject("apiShopLiveGoods", ApiShopLiveGoods.class));
}
break;
   case "onBuyGoodsRoom": {
onBuyGoodsRoom(content.getObject("userBuyDTO", UserBuyDTO.class));
}
break;
}
}

/**
 * 设置直播购横幅成功后发送
 * @param shopLiveBanner null
 */
public abstract void onUsersShopBanner(String shopLiveBanner);

/**
 * 修改直播商品讲解中状态
 * @param apiShopLiveGoods null
 */
public abstract void onUsersLiveGoodsStatus(com.kalacheng.libuser.model.ApiShopLiveGoods apiShopLiveGoods);

/**
 * 用户下单发送房间消息
 * @param userBuyDTO null
 */
public abstract void onBuyGoodsRoom(com.kalacheng.libuser.model.UserBuyDTO userBuyDTO);
}
