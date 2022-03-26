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
 * 主播互动socket
 */
public class IMApiLiveAnchorLine
{

 SocketClient m_client;
    public void init(SocketClient client)
    {
        m_client=client;
    }


/**
 * invitationAnchorLine
 * @param toUid 被邀请主播id
 */
public void invitationAnchorLine(long toUid,IMApiCallBack<com.kalacheng.libuser.model.ApiLinkEntity>callBack)
{
Map<String, Object> mapData = new ConcurrentHashMap<>();
mapData.put("toUid",toUid); 
m_client.sendMsg("LiveAnchorLine","invitationAnchorLine",mapData,callBack, com.kalacheng.libuser.model.ApiLinkEntity.class );

}



/**
 * invitationAnchorLineResp
 * @param isAgree 是否同意连麦 1:同意 2:拒绝 3:超时
 * @param fromUid 请求者主播id
 * @param linkSessionID null
 */
public void invitationAnchorLineResp(int isAgree,long fromUid,long linkSessionID,IMApiCallBack<com.kalacheng.libbas.model.SingleString>callBack)
{
Map<String, Object> mapData = new ConcurrentHashMap<>();
mapData.put("isAgree",isAgree);
mapData.put("fromUid",fromUid);
mapData.put("linkSessionID",linkSessionID); 
m_client.sendMsg("LiveAnchorLine","invitationAnchorLineResp",mapData,callBack, com.kalacheng.libbas.model.SingleString.class );

}



/**
 * invitationAnchorLineClose
 * @param roomId 房间id
 * @param linkSessionID null
 */
public void invitationAnchorLineClose(long roomId,long linkSessionID,IMApiCallBack<com.kalacheng.libbas.model.SingleString>callBack)
{
Map<String, Object> mapData = new ConcurrentHashMap<>();
mapData.put("roomId",roomId);
mapData.put("linkSessionID",linkSessionID); 
m_client.sendMsg("LiveAnchorLine","invitationAnchorLineClose",mapData,callBack, com.kalacheng.libbas.model.SingleString.class );

}


}
