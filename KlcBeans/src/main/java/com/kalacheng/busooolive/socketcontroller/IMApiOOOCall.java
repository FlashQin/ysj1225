package com.kalacheng.busooolive.socketcontroller;

import com.wengying666.imsocket.IMApiCallBack;
import com.wengying666.imsocket.SocketClient;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Date;

import java.math.BigDecimal;




/**
 * OOOCall的控制器
 */
public class IMApiOOOCall
{

 SocketClient m_client;
    public void init(SocketClient client)
    {
        m_client=client;
    }

}
