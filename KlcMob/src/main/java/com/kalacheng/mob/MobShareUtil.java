package com.kalacheng.mob;


import android.content.Context;
import android.text.TextUtils;

import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libuser.model.ApiShareConfig;
import com.kalacheng.mob.bean.ShareData;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;

import java.util.HashMap;

import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by cxf on 2017/8/29.
 * Mob 分享
 */

public class MobShareUtil {

    private PlatformActionListener mPlatformActionListener;
    private Context mContext;
    private static volatile MobShareUtil singleton;


    public MobShareUtil() {
        mPlatformActionListener = new PlatformActionListener() {

            @Override
            public void onComplete(cn.sharesdk.framework.Platform platform, int i, HashMap<String, Object> hashMap) {
                if (mMobCallback != null) {
                    mMobCallback.onSuccess(null);
                    mMobCallback.onFinish();
                    mMobCallback = null;
                }
            }

            @Override
            public void onError(cn.sharesdk.framework.Platform platform, int i, Throwable throwable) {
                if (mMobCallback != null) {
                    mMobCallback.onError();
                    mMobCallback.onFinish();
                    mMobCallback = null;
                }
            }

            @Override
            public void onCancel(cn.sharesdk.framework.Platform platform, int i) {
                if (mMobCallback != null) {
                    mMobCallback.onCancel();
                    mMobCallback.onFinish();
                    mMobCallback = null;
                }
            }


        };
    }

    public static MobShareUtil getInstance() {

        if (singleton == null) {
            synchronized (MobShareUtil.class) {
                if (singleton == null) {
                    singleton = new MobShareUtil();
                }
            }
        }
        return singleton;
    }

    public void execute(String platType, ShareData data, MobCallback callback) {
        if (TextUtils.isEmpty(platType) || data == null) {
            return;
        }
        String platName = MobConst.MAP.get(platType);
        if (TextUtils.isEmpty(platName)) {
            return;
        }
        mMobCallback = callback;
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();//设置一个总开关，用于在分享前若需要授权，则禁用sso功能
        oks.setPlatform(platName);
        oks.setSilent(true);//是否直接分享
        oks.setSite(WordUtil.getString(R.string.app_name));//site是分享此内容的网站名称，仅在QQ空间使用，否则可以不提供
        oks.setSiteUrl(HttpClient.getInstance().getUrl());//siteUrl是分享此内容的网站地址，仅在QQ空间使用，否则可以不提供
        oks.setTitle(data.getTitle());
        oks.setText(data.getDes());
        oks.setImageUrl(data.getImgUrl());
        String webUrl = data.getWebUrl();
        oks.setUrl(webUrl);
        oks.setSiteUrl(webUrl);
        oks.setTitleUrl(webUrl);
        oks.setCallback(mPlatformActionListener);
        oks.show(BaseApplication.getInstance());
    }

    public void release() {
        mMobCallback = null;
    }

    /**
     * 分享
     */
    public void shareLive(long terminalType, long locationType, final String type) {

        HttpApiAppUser.share((int) terminalType, (int) locationType, new HttpApiCallBack<ApiShareConfig>() {
            @Override
            public void onHttpRet(int code, String msg, ApiShareConfig retModel) {
                if (code == 1) {
                    ShareData data = new ShareData();
                    data.setTitle(retModel.shareTitle);
                    data.setDes(retModel.shareDes);
                    data.setImgUrl(retModel.logo);
                    data.setWebUrl(retModel.url);
                    execute(type, data, mMobCallback);
                } else {
                    ToastUtil.show(msg);
                }
            }
        });

    }

    /**
     * 分享APP
     */
    public void shareApp(final String type) {
        HttpApiAppUser.share(1, 3, new HttpApiCallBack<ApiShareConfig>() {
            @Override
            public void onHttpRet(int code, String msg, ApiShareConfig retModel) {
                if (code == 1) {
                    ShareData data = new ShareData();
                    data.setTitle(retModel.shareTitle);
                    data.setDes(retModel.shareDes);
                    data.setImgUrl(retModel.logo);
                    data.setWebUrl(retModel.url);
                    execute(type, data, mMobCallback);
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    private MobCallback mMobCallback = new MobCallback() {

        @Override
        public void onSuccess(Object data) {
            ToastUtil.show("分享成功");
        }

        @Override
        public void onError() {

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onFinish() {

        }
    };
}
