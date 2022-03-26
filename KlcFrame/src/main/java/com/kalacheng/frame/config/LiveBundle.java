package com.kalacheng.frame.config;

import android.os.Handler;
import android.os.Looper;

import com.kalacheng.base.listener.MsgListener;
import com.wengying666.imsocket.IMUtil;
import com.wengying666.imsocket.SocketClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiveBundle {

    static Map<String, List<MsgListener.SimpleMsgListener>> MAPLISTENER = new HashMap<>();


    public interface onLiveSocket {
        void init(String groupName, SocketClient socketClient);

    }

    private static class SingletonHolder {
        private static final LiveBundle INSTANCE = new LiveBundle();
    }

    private LiveBundle() {

    }

    public static final LiveBundle getInstance() {
        return SingletonHolder.INSTANCE;
    }


    private List<onLiveSocket> socketList = new ArrayList<>();

    public void setSocketClient(String activityName, SocketClient socketClient) {
        for (int i = 0; i < socketList.size(); i++) {
            socketList.get(i).init(activityName, socketClient);
        }
    }

    public void removeSocketClient(String activityName) {
        IMUtil.removeReceiver(activityName);
    }


    public void addLiveSocketListener(onLiveSocket socket) {
        socketList.add(socket);
    }

    //销毁所以监听
    public void removeAllListener() {
        socketList.clear();
        MAPLISTENER.clear();
    }

    //接收监听
    public void addSimpleMsgListener(String key, MsgListener.SimpleMsgListener liveListener) {
        if (!MAPLISTENER.containsKey(key)) {
            List listener = new ArrayList<MsgListener.SimpleMsgListener>();
            listener.add(liveListener);
            MAPLISTENER.put(key, listener);
        } else {
            List<MsgListener.SimpleMsgListener> list = MAPLISTENER.get(key);
            list.add(liveListener);
            MAPLISTENER.put(key, list);
        }
    }

    //发送监听
    public void sendSimpleMsg(String key, final Object object) {
        if (MAPLISTENER.containsKey(key)) {
            List<MsgListener.SimpleMsgListener> list = MAPLISTENER.get(key);
            for (final MsgListener.SimpleMsgListener listener : list) {
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onMsg(object);
                    }
                });
            }
        }
    }

    //发送监听
    public void sendTagMsg(String key, final String tag, final Object object) {
        if (MAPLISTENER.containsKey(key)) {
            List<MsgListener.SimpleMsgListener> list = MAPLISTENER.get(key);
            for (final MsgListener.SimpleMsgListener listener : list) {
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onTagMsg(tag, object);
                    }
                });
            }
        }
    }

    //销毁某一个监听
    public void removeNullMsgListener(String key) {
        if (MAPLISTENER.containsKey(key)) {
            List<MsgListener.SimpleMsgListener> list = MAPLISTENER.get(key);
            list.clear();
        }
    }

    //保留某一个监听不被销毁
    public void removeOtherMsgListener(String key) {
        if (MAPLISTENER.containsKey(key)) {
            List<MsgListener.SimpleMsgListener> list = MAPLISTENER.get(key);
            MAPLISTENER.clear();
            MAPLISTENER.put(key, list);

        }
    }

    //判断是否存在某一个监听事件
    public boolean isHaveCallBack(String key) {
        boolean isHAVE;
        if (MAPLISTENER.get(key).size() > 0) {
            isHAVE = true;
        } else {
            isHAVE = false;
        }
        return isHAVE;
    }

}