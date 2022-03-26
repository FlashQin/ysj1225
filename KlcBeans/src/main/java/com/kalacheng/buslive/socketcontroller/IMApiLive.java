package com.kalacheng.buslive.socketcontroller;

import com.wengying666.imsocket.IMApiCallBack;
import com.wengying666.imsocket.SocketClient;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Date;

import java.math.BigDecimal;

import com.kalacheng.libbas.model.*;




/**
 * 直播间socket
 */
public class IMApiLive
{

 SocketClient m_client;
    public void init(SocketClient client)
    {
        m_client=client;
    }


/**
 * joinRoomAnchor
 * @param roomId 房间id
 */
public void joinRoomAnchor(long roomId,IMApiCallBack<com.kalacheng.libbas.model.SingleString>callBack)
{
Map<String, Object> mapData = new ConcurrentHashMap<>();
mapData.put("roomId",roomId); 
m_client.sendMsg("Live","joinRoomAnchor",mapData,callBack, com.kalacheng.libbas.model.SingleString.class );

}



/**
 * leaveRoomAnchor
 * @param roomId 房间id
 */
public void leaveRoomAnchor(long roomId,IMApiCallBack<com.kalacheng.libbas.model.SingleString>callBack)
{
Map<String, Object> mapData = new ConcurrentHashMap<>();
mapData.put("roomId",roomId); 
m_client.sendMsg("Live","leaveRoomAnchor",mapData,callBack, com.kalacheng.libbas.model.SingleString.class );

}


}
