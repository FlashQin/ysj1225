package com.kalacheng.template.socketcontroller;

import com.wengying666.imsocket.IMApiCallBack;
import com.wengying666.imsocket.SocketClient;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Date;

import java.math.BigDecimal;

import com.kalacheng.template.model.*;

import com.kalacheng.libbas.model.*;




/**
 * 测试用的控制器
 */
public class IMApiATestSocket
{

 SocketClient m_client;
    public void init(SocketClient client)
    {
        m_client=client;
    }


/**
 * getMsg2
 * @param mdl null
 * @param reqUid null
 */
public void getMsg2(com.kalacheng.template.model.aTestModle mdl,long reqUid,IMApiCallBack<com.kalacheng.template.model.aTestModle>callBack)
{
Map<String, Object> mapData = new ConcurrentHashMap<>();
mapData.put("mdl",mdl);
mapData.put("reqUid",reqUid); 
m_client.sendMsg("ATestSocket","getMsg2",mapData,callBack, com.kalacheng.template.model.aTestModle.class );

}



/**
 * getMsg3
 * @param mdl null
 * @param reqUid null
 */
public void getMsg3(com.kalacheng.template.model.aTestModle mdl,long reqUid,IMApiCallBack<com.kalacheng.libbas.model.SingleString>callBack)
{
Map<String, Object> mapData = new ConcurrentHashMap<>();
mapData.put("mdl",mdl);
mapData.put("reqUid",reqUid); 
m_client.sendMsg("ATestSocket","getMsg3",mapData,callBack, com.kalacheng.libbas.model.SingleString.class );

}



/**
 * getMsg1
 * @param assistanNo 麦位编号
 * @param type 是否能直接上麦,1:能直接上麦,2,不能
 */
public void getMsg1(int assistanNo,int type,IMApiCallBack<com.kalacheng.template.model.aTestModle>callBack)
{
Map<String, Object> mapData = new ConcurrentHashMap<>();
mapData.put("assistanNo",assistanNo);
mapData.put("type",type); 
m_client.sendMsg("ATestSocket","getMsg1",mapData,callBack, com.kalacheng.template.model.aTestModle.class );

}


}
