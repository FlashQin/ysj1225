package com.kalacheng.util.utils;

import android.util.Log;

/**
 * Created by cxf on 2017/8/3.
 */

public class L {
    private final static String TAG = "log--->";

    public static void e(String s) {
        e(TAG, s);
    }

    public static void e(String tag, String s) {
        if (ApplicationUtil.sDeBug) {
            Log.e(tag, s);
        }
    }
}