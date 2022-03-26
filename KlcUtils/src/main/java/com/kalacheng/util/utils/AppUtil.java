package com.kalacheng.util.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * APP工具类
 * Created by ysj on 2016/11/18.
 */

public class AppUtil {
    /**
     * 获取应用程序名称
     */
    public static String getAppName() {
        try {
            PackageInfo packageInfo = ApplicationUtil.getInstance().getPackageManager().getPackageInfo(ApplicationUtil.getInstance().getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return ApplicationUtil.getInstance().getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当前应用的外部版本号
     */
    public static String getVersionName() {
        try {
            return ApplicationUtil.getInstance().getPackageManager().getPackageInfo(ApplicationUtil.getInstance().getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    /**
     * 获取当前应用的内部版本号
     */
    public static int getVersionCode() {
        try {
            return ApplicationUtil.getInstance().getPackageManager().getPackageInfo(ApplicationUtil.getInstance().getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            return 0;
        }
    }

    /**
     * 获取AndroidManifest.xml文件中配制的信息
     */
    public static String getMetaDataString(String key) {
        String res = null;
        try {
            ApplicationInfo appInfo = ApplicationUtil.getInstance().getPackageManager().getApplicationInfo(ApplicationUtil.getInstance().getPackageName(), PackageManager.GET_META_DATA);
            res = appInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 获取设备Android SDK版本号
     */
    public static int getAndroidSdkInt() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号（例：Redmi Note 3；A370）
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }

    /**
     * 获取UUID信息
     *
     * @return 例 00000000-54b3-e7c7-0000-000046bffd97
     */
    @SuppressLint("MissingPermission")
    public static UUID getUUID() {
        TelephonyManager tm = (TelephonyManager) ApplicationUtil.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice = "", tmSerial = "", androidId;
        if (tm != null) {
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
        }
        androidId = "" + android.provider.Settings.Secure.getString(ApplicationUtil.getInstance().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        return new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
    }

    /**
     * 判断某APP是否安装
     */
    public static boolean isAppExist(String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            PackageManager manager = ApplicationUtil.getInstance().getPackageManager();
            List<PackageInfo> list = manager.getInstalledPackages(0);
            for (PackageInfo info : list) {
                if (packageName.equalsIgnoreCase(info.packageName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
