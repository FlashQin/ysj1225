package com.kalacheng.commonview.jguangIm;

import android.content.Context;


import com.kalacheng.base.activty.BaseApplication;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by cxf on 2017/8/3.
 * 极光推送相关
 */

public class ImPushUtil {

    public static final String TAG = "极光推送";
    private static ImPushUtil sInstance;
    private boolean mClickNotification;
    private int mNotificationType;

    private ImPushUtil() {

    }

    public static ImPushUtil getInstance() {
        if (sInstance == null) {
            synchronized (ImPushUtil.class) {
                if (sInstance == null) {
                    sInstance = new ImPushUtil();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        JPushInterface.init(context);
//        if (AppContext.sDeBug) {
//            Log.e(TAG, "regID------>" + JPushInterface.getRegistrationID(context));
//        }
    }


    public void logout() {
        stopPush();
    }

    public void resumePush() {
        if (JPushInterface.isPushStopped(BaseApplication.getInstance())) {
            JPushInterface.resumePush(BaseApplication.getInstance());
        }
    }

    public void stopPush() {
        JPushInterface.stopPush(BaseApplication.getInstance());
    }

    public boolean isClickNotification() {
        return mClickNotification;
    }

    public void setClickNotification(boolean clickNotification) {
        mClickNotification = clickNotification;
    }

    public int getNotificationType() {
        return mNotificationType;
    }

    public void setNotificationType(int notificationType) {
        mNotificationType = notificationType;
    }

    /**
     * 获取极光推送 RegistrationID
     */
    public String getPushID() {
        return JPushInterface.getRegistrationID(BaseApplication.getInstance());
    }
}
