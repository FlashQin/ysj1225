package com.kalacheng.base.upload;

import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.base.http.HttpClient;
import com.xuantongyun.storagecloud.upload.IGetUploadConfig;

public class UploadConfig implements IGetUploadConfig {

    @Override
    public int getCloudType() {
        return (int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_CLOUD_TYPE, 0);
    }

    @Override
    public long getUid() {
        return HttpClient.getUid();
    }

    @Override
    public String getApplicationId() {
        return BaseApplication.getInstance().getPackageName();
    }

    @Override
    public String getUserToken() {
        return HttpClient.getToken();
    }

    @Override
    public String getUrl() {
        return HttpClient.getInstance().getUrl();
    }

    @Override
    public String getHwEndPoint() {
        return "https://obs.cn-east-3.myhuaweicloud.com";
    }

    @Override
    public String getHwUrl() {
        return "https://klcdev123.obs.cn-east-3.myhuaweicloud.com/";
    }

    @Override
    public String getHwAccessKey() {
        return "CGBU3TUMOFXQUSFI7BUW";
    }

    @Override
    public String getHwSecretKey() {
        return "q2eDIME9xxt9aKP11e1pUiJ6nVADt1qdONGr1a8E";
    }

    @Override
    public String getHwBucketName() {
        return "klcdev123";
    }

    @Override
    public String getVideoClipsKey() {
        return (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_VIDEO_CLIP_KEY, "");
    }
}