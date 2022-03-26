package com.kalacheng.commonview.utils;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.kalacheng.commonview.dialog.AppUpdateDialog;

/**
 * Created by cxf on 2017/10/9.
 */

public class VersionUtil {

    /**
     * APP升级Dialog
     *
     * @param context         Context
     * @param versionName     版本号
     * @param versionTip      提示信息
     * @param downloadRealUrl 下载链接
     * @param force           是否强制升级
     */
    public static void showAppUpdateDialog(Context context, String versionName, String versionTip, String downloadRealUrl, boolean force) {
        AppUpdateDialog fragment = new AppUpdateDialog();
        Bundle bundle = new Bundle();
        bundle.putString("versionName", versionName);
        bundle.putString("versionTip", versionTip);
        bundle.putString("downloadRealUrl", downloadRealUrl);
        bundle.putBoolean("force", force);
        fragment.setArguments(bundle);
        fragment.show(((FragmentActivity) context).getSupportFragmentManager(), "VersionUpdateDialogFragment");
    }
}
