package com.kalacheng.commonview.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.util.utils.SpUtil;

public class WebUtil {

    /**
     * 链接跳转
     */
    public static void goWeb(Context context, String url) {
        if (!TextUtils.isEmpty(url)) {
            if ("app://goToInvitation".equals(url)) {//邀请赚钱
                ARouter.getInstance().build(ARouterPath.InvitationCodeActivity).navigation();
            } else if ((int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_BANNER_JUMP_MODE, 0) == 0) {
                ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, url).navigation();
            } else {
                goExternalWeb(context, url);
            }
        }
    }

    /**
     * 跳转至外部浏览器
     */
    public static boolean goExternalWeb(Context context, String url) {
        if (!TextUtils.isEmpty(url)) {
            try {
                if (!url.startsWith("https://") && !url.startsWith("http://")) {
                    url = "http://" + url;
                }
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
