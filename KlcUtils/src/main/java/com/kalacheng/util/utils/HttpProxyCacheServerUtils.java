package com.kalacheng.util.utils;

import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;

public class HttpProxyCacheServerUtils {
    private static class Singleton {
        private static final HttpProxyCacheServerUtils INSTANCE = new HttpProxyCacheServerUtils();
    }

    private HttpProxyCacheServerUtils() {
    }

    public static final HttpProxyCacheServerUtils getInstance() {
        return Singleton.INSTANCE;
    }

    public HttpProxyCacheServer getProxy(Context context) {
        return new HttpProxyCacheServer(context);
    }
}
