package com.kalacheng.util.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;

import java.util.List;

public class QQUtil {
    /**
     * 跳转到QQ客服
     */
    public static void jumpToQQ(Activity mContext, String mQQNumber) {
        // 跳转之前，可以先判断手机是否安装QQ
        if (AppUtil.isAppExist("com.tencent.qqlite") || AppUtil.isAppExist("com.tencent.mobileqq")) {
            // 跳转到客服的QQ
            if (TextUtils.isEmpty(mQQNumber)) {
                ToastUtil.show("QQ客服未设置");
                return;
            }
            String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + mQQNumber;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            // 跳转前先判断Uri是否存在，如果打开一个不存在的Uri，App可能会崩溃
            if (isValidIntent(mContext, intent)) {
                mContext.startActivity(intent);
            } else {
                ToastUtil.show("维护中,请稍后再试");
            }
        } else {
            ToastUtil.show("用户未安装QQ");
        }
    }

    /**
     * 判断 Uri是否有效
     */
    private static boolean isValidIntent(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return !activities.isEmpty();
    }
}
