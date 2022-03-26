package com.kalacheng.util.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 网络工具类
 * Created by ysj on 2016/11/18.
 */

public class NetUtil {
    /**
     * 获取当前网络状态
     *
     * @return （-1：没有网络，0：未知网络，1：移动网络，2：Wifi网络）
     */
    public static int getNetworkState() {
        int selState = -1;
        ConnectivityManager connectivityManager = (ConnectivityManager) ApplicationUtil.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return selState;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            selState = 1;
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            selState = 2;
        } else {
            selState = 0;
        }
        return selState;
    }

    /**
     * 获取当前连接的WIFI名称
     */
    public static String getWifiName() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) ApplicationUtil.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWiFiNetworkInfo != null && mWiFiNetworkInfo.isAvailable()) {
            String str = mWiFiNetworkInfo.getExtraInfo();
            if (TextUtils.isEmpty(str)) {
                return "";
            } else {
                return str.substring(1, str.length() - 1);
            }
        }
        return "";
    }

    /**
     * 获取IP地址，优先获取WIFI地址
     */
    public static String getIpAddress() {
        String wifiIp = getWifiIpAddress();
        if (TextUtils.isEmpty(wifiIp)) {
            return getLocalIpAddress();
        } else {
            return wifiIp;
        }
    }

    /**
     * 获取IP地址（WIFI地址）
     */
    public static String getWifiIpAddress() {
        WifiManager wifiManager = (WifiManager) ApplicationUtil.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            return intToIp(wifiInfo.getIpAddress());
        }
        return "";
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
    }

    /**
     * 获取本机IP地址（手机卡地址）
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * 获取设备MAC地址
     */
    public static String getLocalMacAddress() {
        WifiManager wifi = (WifiManager) ApplicationUtil.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting() {
        Intent intent = null;
        // 判断手机系统的版本 即API大于10 就是3.0或以上版本
        if (android.os.Build.VERSION.SDK_INT > 10) {
            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        ApplicationUtil.getInstance().startActivity(intent);
    }
}
