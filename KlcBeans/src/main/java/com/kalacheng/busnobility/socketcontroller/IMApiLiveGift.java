package com.kalacheng.busnobility.socketcontroller;

import com.wengying666.imsocket.IMApiCallBack;
import com.wengying666.imsocket.SocketClient;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Date;

import java.math.BigDecimal;

import com.kalacheng.busnobility.model.*;




/**
 * 直播间送礼物socket
 */
public class IMApiLiveGift
{

 SocketClient m_client;
    public void init(SocketClient client)
    {
        m_client=client;
    }


/**
 * sendLight
 * @param anchorId 主播id
 * @param type 类型 1:视频直播 2:语音直播
 */
public void sendLight(int anchorId,int type,IMApiCallBack<com.kalacheng.busnobility.model.ApiLightSender>callBack)
{
Map<String, Object> mapData = new ConcurrentHashMap<>();
mapData.put("anchorId",anchorId);
mapData.put("type",type); 
m_client.sendMsg("LiveGift","sendLight",mapData,callBack, com.kalacheng.busnobility.model.ApiLightSender.class );

}


}
