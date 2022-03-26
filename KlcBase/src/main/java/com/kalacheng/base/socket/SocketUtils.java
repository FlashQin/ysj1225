package com.kalacheng.base.socket;

import android.app.Activity;


import com.kalacheng.base.http.HttpClient;

import com.wengying666.imsocket.IMUtil;

public class SocketUtils {


    public static void startSocket(Activity act, boolean needReconnect) {

        if (needReconnect) {
            initSocket(act);
        } else {
            checkSocket(act);
        }
    }

    public static void checkSocket(Activity act) {
        if (IMUtil.isStart() == false) {
            String strToken = HttpClient.getToken();
            long uid = HttpClient.getUid();
            if (strToken != null && strToken.length() > 0 && uid > 0) {
                initSocket(act);
            }
        }
    }


    public static void initSocket(Activity act) {
        IMUtil.reConnect(act, new SocketConfig());
    }


    public static void stopSocket( ) {
        IMUtil.disCconnect();
    }

}