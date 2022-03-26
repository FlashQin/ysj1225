package com.kalacheng.util.utils;

import android.app.Application;

public class ApplicationUtil extends Application {
    /**
     * 实例对象
     */
    private static ApplicationUtil INSTANCE = null;
    public static boolean sDeBug;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    /**
     * 获取实例对象
     */
    public static ApplicationUtil getInstance() {
        return INSTANCE;
    }
}
