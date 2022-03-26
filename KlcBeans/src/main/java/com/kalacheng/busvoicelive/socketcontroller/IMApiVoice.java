package com.kalacheng.busvoicelive.socketcontroller;

import com.wengying666.imsocket.IMApiCallBack;
import com.wengying666.imsocket.SocketClient;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Date;

import java.math.BigDecimal;




/**
 * 语音直播间socket
 */
public class IMApiVoice
{

 SocketClient m_client;
    public void init(SocketClient client)
    {
        m_client=client;
    }

}
