package com.kalacheng.game.socketmsg;

import com.alibaba.fastjson.JSONObject;
import com.wengying666.imsocket.IMReceiver;
import com.wengying666.imsocket.JsonObjConvert;
import java.util.List;
import java.math.BigDecimal;

import com.kalacheng.libuser.model.*;




/**
 * 发送游戏消息给所有人
 */
public abstract class IMRcvLiveGameMsgAllSend implements IMReceiver
{

 public String getMsgType() {
        return "LiveGameMsgAllSend";
    }

   public void onMsg(String subType, JSONObject content) {
        switch (subType) {
   case "onUserWinAPrize": {
onUserWinAPrize(content.getObject("gameUserWinAwardsDTO", GameUserWinAwardsDTO.class));
}
break;
}
}

/**
 * 中奖之后发送socket信息
 * @param gameUserWinAwardsDTO null
 */
public abstract void onUserWinAPrize(com.kalacheng.libuser.model.GameUserWinAwardsDTO gameUserWinAwardsDTO);
}
