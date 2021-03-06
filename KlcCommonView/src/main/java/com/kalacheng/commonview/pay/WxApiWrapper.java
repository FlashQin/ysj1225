package com.kalacheng.commonview.pay;

import android.text.TextUtils;

import com.kalacheng.base.activty.BaseApplication;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by cxf on 2018/10/23.
 */

public class WxApiWrapper {

    private static WxApiWrapper sInstance;
    private IWXAPI mApi;
    private String mAppID;

    private WxApiWrapper() {

    }

    public static WxApiWrapper getInstance() {
        if (sInstance == null) {
            synchronized (WxApiWrapper.class) {
                if (sInstance == null) {
                    sInstance = new WxApiWrapper();
                }
            }
        }
        return sInstance;
    }

    public void setAppID(String appID) {
        if (!TextUtils.isEmpty(appID)) {
            if (!appID.equals(mAppID) || mApi == null) {
                if (mApi != null) {
                    mApi.unregisterApp();
                }
                mAppID = appID;
                mApi = WXAPIFactory.createWXAPI(BaseApplication.getInstance(), appID);
                mApi.registerApp(appID);
            }
        }
    }

    public String getAppID() {
        return mAppID;
    }

    public IWXAPI getWxApi() {
        return mApi;
    }
}
