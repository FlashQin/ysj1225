package com.kalacheng.main.fragment;

import android.os.Handler;
import android.os.Looper;

import com.kalacheng.base.listener.MsgListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeBundle {
    public static String City ="City";

    static Map<String, List<MsgListener.SimpleMsgListener>> MAPLISTENER = new HashMap<>();

    private static class SingletonHolder {
        private static final MeBundle INSTANCE = new MeBundle();
    }

    private MeBundle() {

    }

    public static final MeBundle getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void removeAllListener() {
        MAPLISTENER.clear();
    }


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

    public void removeNullMsgListener(String key) {
        if (MAPLISTENER.containsKey(key)) {
            List<MsgListener.SimpleMsgListener> list = MAPLISTENER.get(key);
            list.clear();
        }
    }


}