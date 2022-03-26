package com.kalacheng.base.activty;

import android.os.Handler;
import android.os.Looper;

import com.kalacheng.base.listener.MsgListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityLife {
    private static class SingletonHolder {
        private static final ActivityLife INSTANCE = new ActivityLife();
    }

    private ActivityLife() {
    }

    public static final ActivityLife getInstance() {
        return ActivityLife.SingletonHolder.INSTANCE;
    }

    private List<MsgListener.NullMsgListener> activityResume = new ArrayList<>();

    public void sendActivityResume() {
        if (!activityResume.isEmpty()) {
            for (int i = 0; i < activityResume.size(); i++) {
                Handler mainHandler = new Handler(Looper.getMainLooper());
                final int finalI = i;
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (activityResume != null && activityResume.size() > finalI) {
                            activityResume.get(finalI).onMsg();
                        }
                    }
                });
            }
        }
    }

    public void addActivityResume(MsgListener.NullMsgListener liveListener) {
        this.activityResume.add(liveListener);
    }

    public void reomveActivityResumeListener() {
        this.activityResume.clear();
    }

    private List<MsgListener.NullMsgListener> activityPause = new ArrayList<>();

    public void sendActivityPause() {
        if (!activityPause.isEmpty()) {
            for (int i = 0; i < activityPause.size(); i++) {
                Handler mainHandler = new Handler(Looper.getMainLooper());
                final int finalI = i;
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (activityPause != null && activityPause.size() > finalI) {
                            activityPause.get(finalI).onMsg();
                        }
                    }
                });
            }
        }
    }

    public void addActivityPause(MsgListener.NullMsgListener liveListener) {
        this.activityPause.add(liveListener);
    }

    public void reomveActivityPauseListener() {
        this.activityPause.clear();
    }

    private List<MsgListener.NullMsgListener> activityDestory = new ArrayList<>();

    public void sendActivityDestory() {
        for (int i = 0; i < activityDestory.size(); i++) {
            Handler mainHandler = new Handler(Looper.getMainLooper());
            final int finalI = i;
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (activityDestory != null && activityDestory.size() > finalI) {
                        activityDestory.get(finalI).onMsg();

                    }
                }
            });
        }
    }

    public void addActivityDestory(MsgListener.NullMsgListener liveListener) {
        this.activityDestory.add(liveListener);
    }

    public void reomveActivityDestoryListener() {
        this.activityDestory.clear();
    }


    public void clear() {
        activityResume.clear();
        activityPause.clear();
        activityDestory.clear();
    }
}
