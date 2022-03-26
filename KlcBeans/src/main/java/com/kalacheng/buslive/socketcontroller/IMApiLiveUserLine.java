package com.kalacheng.buslive.socketcontroller;

import com.wengying666.imsocket.IMApiCallBack;
import com.wengying666.imsocket.SocketClient;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Date;

import java.math.BigDecimal;

import com.kalacheng.libuser.model.*;

import com.kalacheng.libbas.model.*;




/**
 * 用户连麦socket
 */
public class IMApiLiveUserLine
{

 SocketClient m_client;
    public void init(SocketClient client)
    {
        m_client=client;
    }


/**
 * invitationUserLineReq
 * @param toUid 被邀请PK主播id
 */
public void invitationUserLineReq(long toUid,IMApiCallBack<com.kalacheng.libuser.model.ApiLinkEntity>callBack)
{
Map<String, Object> mapData = new ConcurrentHashMap<>();
mapData.put("toUid",toUid); 
m_client.sendMsg("LiveUserLine","invitationUserLineReq",mapData,callBack, com.kalacheng.libuser.model.ApiLinkEntity.class );

}



/**
 * invitationUserLineResp
 * @param isAgree 是否同意连麦1同意,2拒绝,3超时
 * @param fromUid 请求用户id
 * @param linkSessionID null
 */
public void invitationUserLineResp(int isAgree,long fromUid,long linkSessionID,IMApiCallBack<com.kalacheng.libbas.model.SingleString>callBack)
{
Map<String, Object> mapData = new ConcurrentHashMap<>();
mapData.put("isAgree",isAgree);
mapData.put("fromUid",fromUid);
mapData.put("linkSessionID",linkSessionID); 
m_client.sendMsg("LiveUserLine","invitationUserLineResp",mapData,callBack, com.kalacheng.libbas.model.SingleString.class );

}



/**
 * setAnchorLineStatus
 * @param status 设置连麦1开启连麦0关闭连麦
 */
public void setAnchorLineStatus(int status,IMApiCallBack<com.kalacheng.libbas.model.SingleString>callBack)
{
Map<String, Object> mapData = new ConcurrentHashMap<>();
mapData.put("status",status); 
m_client.sendMsg("LiveUserLine","setAnchorLineStatus",mapData,callBack, com.kalacheng.libbas.model.SingleString.class );

}



/**
 * invitationUserLineClose
 * @param anchorId 主播id
 * @param linkSessionID null
 */
public void invitationUserLineClose(long anchorId,long linkSessionID,IMApiCallBack<com.kalacheng.libbas.model.SingleString>callBack)
{
Map<String, Object> mapData = new ConcurrentHashMap<>();
mapData.put("anchorId",anchorId);
mapData.put("linkSessionID",linkSessionID); 
m_client.sendMsg("LiveUserLine","invitationUserLineClose",mapData,callBack, com.kalacheng.libbas.model.SingleString.class );

}


}
