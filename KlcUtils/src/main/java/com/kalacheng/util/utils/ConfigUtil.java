package com.kalacheng.util.utils;

public class ConfigUtil {

    /**
     * 获取布尔值
     */
    public static boolean getBoolValue(int id) {
        return ApplicationUtil.getInstance().getResources().getBoolean(id);
    }

    /**
     * 获取int值
     */
    public static int getIntValue(int id) {
        return ApplicationUtil.getInstance().getResources().getInteger(id);
    }
}
