package com.kalacheng.base.livecloud;

import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.base.http.HttpClient;
import com.xuantongyun.livecloud.config.IGetTTTConfig;

import io.agora.capture.video.camera.CameraVideoManager;

public class TTTConfig implements IGetTTTConfig {

    @Override
    public boolean isTTT() {
        return false;
    }

    @Override
    public long getUid() {
        return HttpClient.getUid();
    }

    @Override
    public int getBeautySwitch() {
        return (int) SpUtil.getInstance().getSharedPreference(SpUtil.BEAUTY_SWITCH, 0);
    }

    @Override
    public String getBeautyKey() {
        return (String) SpUtil.getInstance().getSharedPreference(SpUtil.BEAUTY_KEY, "");
    }

    @Override
    public int getVideoQuality() {
        return (int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_IMAGE_QUALITY, 0);
    }

    @Override
    public String getTTTKey() {
        return (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_STY_KEY, "");
    }

    @Override
    public String getCdnKey() {
        return (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_CDN_KEY, "");
    }

    @Override
    public long getAnchorID() {
        return (long) SpUtil.getInstance().getSharedPreference(SpUtil.ANCHOR_ID, 0l);
    }

    @Override
    public int getScreenWidth() {
        return BaseApplication.getInstance().getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public int getScreenHeight() {
        return BaseApplication.getInstance().getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public int dp2px(int dpVal) {
        return (int) (BaseApplication.getInstance().getResources().getDisplayMetrics().density * dpVal + 0.5f);
    }

    @Override
    public int getVideoHeight() {
        return (int) (BaseApplication.getInstance().getResources().getDisplayMetrics().widthPixels / 2 * 1.72f);
    }

    @Override
    public CameraVideoManager getCameraVideoManager() {
        return ((BaseApplication)BaseApplication.getInstance()).getCameraVideoManager();
    }

    @Override
    public int getDefaultBeautyLevel() {
        return (int) SpUtil.getInstance().getSharedPreference(SpUtil.BEAUTY, 50);
    }

    @Override
    public int getDefaultBrightLevel() {
        return (int) SpUtil.getInstance().getSharedPreference(SpUtil.BRIGHT, 50);
    }
}
