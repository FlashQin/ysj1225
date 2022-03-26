package com.kalacheng.buslive.socketcontroller;

import com.wengying666.imsocket.IMApiCallBack;
import com.wengying666.imsocket.SocketClient;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Date;

import java.math.BigDecimal;

import com.kalacheng.libbas.model.*;

import com.kalacheng.libuser.model.*;




/**
 * 主播PKsocket
 */
public class IMApiLiveAnchorPk
{

 SocketClient m_client;
    public void init(SocketClient client)
    {
        m_client=client;
    }


/**
 * invitationAnchorPKResp
 * @param isAgree 是否同意连麦1同意,2拒绝,3超时
 * @param fromUid 请求PK者主播id
 * @param pkSessionID null
 */
public void invitationAnchorPKResp(int isAgree,long fromUid,long pkSessionID,IMApiCallBack<com.kalacheng.libbas.model.SingleString>callBack)
{
Map<String, Object> mapData = new ConcurrentHashMap<>();
mapData.put("isAgree",isAgree);
mapData.put("fromUid",fromUid);
mapData.put("pkSessionID",pkSessionID); 
m_client.sendMsg("LiveAnchorPk","invitationAnchorPKResp",mapData,callBack, com.kalacheng.libbas.model.SingleString.class );

}



/**
 * invitationAnchorLinePK
 * @param toUid 被邀请PK主播id
 */
public void invitationAnchorLinePK(long toUid,IMApiCallBack<com.kalacheng.libuser.model.ApiLinkEntity>callBack)
{
Map<String, Object> mapData = new ConcurrentHashMap<>();
mapData.put("toUid",toUid); 
m_client.sendMsg("LiveAnchorPk","invitationAnchorLinePK",mapData,callBack, com.kalacheng.libuser.model.ApiLinkEntity.class );

}


}
