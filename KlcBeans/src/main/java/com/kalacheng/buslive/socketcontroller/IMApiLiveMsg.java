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
 * 直播间消息socket
 */
public class IMApiLiveMsg
{

 SocketClient m_client;
    public void init(SocketClient client)
    {
        m_client=client;
    }


/**
 * senAppointUserSend
 * @param touid 被发送用户id
 * @param content 发送内容
 */
public void senAppointUserSend(long touid,String content,IMApiCallBack<com.kalacheng.libbas.model.SingleString>callBack)
{
Map<String, Object> mapData = new ConcurrentHashMap<>();
mapData.put("touid",touid);
mapData.put("content",content); 
m_client.sendMsg("LiveMsg","senAppointUserSend",mapData,callBack, com.kalacheng.libbas.model.SingleString.class );

}



/**
 * sendMsgRoom
 * @param content 发送内容
 * @param type 消息类型0系统消息(超管)1是普通消息  2弹幕消息
 * @param anchorId 主播id,没有传0
 */
public void sendMsgRoom(String content,int type,int anchorId,IMApiCallBack<com.kalacheng.libbas.model.SingleString>callBack)
{
Map<String, Object> mapData = new ConcurrentHashMap<>();
mapData.put("content",content);
mapData.put("type",type);
mapData.put("anchorId",anchorId); 
m_client.sendMsg("LiveMsg","sendMsgRoom",mapData,callBack, com.kalacheng.libbas.model.SingleString.class );

}


}
