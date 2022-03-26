package com.kalacheng.util.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Vibrator;
import android.text.format.Formatter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * 系统工具类
 * Created by ysj on 2016/11/18.
 */

public class SystemUtils {
    /**
     * 上次点击退出时间
     */
    private static long firstTime;

//    /**
//     * 退出程序
//     */
//    public static void exitAppByClick(Context context) {
//        BaseApplication.exit();
//    }
//
//    /**
//     * 退出程序
//     */
//    public static void exitAppBy2Click(Context context) {
//        long secondTime = System.currentTimeMillis();
//        if (secondTime - firstTime > 2500) {
//            Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//            firstTime = secondTime;
//        } else {
//            BaseApplication.exit();
//        }
//    }

    /**
     * 打开软键盘
     */
    public static void openKeyboard(View vView) {
        InputMethodManager imm = (InputMethodManager) ApplicationUtil.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && vView != null) {
            vView.requestFocus();
            imm.showSoftInput(vView, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * 关闭软键盘
     */
    public static void closeKeyboard(View vView) {
        InputMethodManager imm = (InputMethodManager) ApplicationUtil.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && vView != null) {
            vView.clearFocus();
            imm.hideSoftInputFromWindow(vView.getWindowToken(), 0);
        }
    }

    /**
     * 分享文字
     *
     * @param title 对话框标题
     * @param text  享出去的内容
     */
    public static void shareText(String title, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ApplicationUtil.getInstance().startActivity(Intent.createChooser(intent, title));
    }

    /**
     * 拨打电话
     */
    public static void dial(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(ApplicationUtil.getInstance().getPackageManager()) != null) {
            ApplicationUtil.getInstance().startActivity(intent);
        }
    }

    /**
     * 发送短信
     */
    public static void shortMessage(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("smsto:" + phoneNumber));
        if (intent.resolveActivity(ApplicationUtil.getInstance().getPackageManager()) != null) {
            ApplicationUtil.getInstance().startActivity(intent);
        }
    }

    /**
     * 获取设备可用内存信息（例：540MB）
     */
    public static String upAvailableMemory() {
        ActivityManager myActivityManager = (ActivityManager) ApplicationUtil.getInstance().getSystemService(Activity.ACTIVITY_SERVICE);
        if (myActivityManager != null) {
            // 获得MemoryInfo对象
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            // 获得系统可用内存，保存在MemoryInfo对象上
            myActivityManager.getMemoryInfo(memoryInfo);
            long memSize = memoryInfo.availMem;
            // 字符类型转换
            return Formatter.formatFileSize(ApplicationUtil.getInstance(), memSize);
        } else {
            return "0MB";
        }
    }

    /**
     * 手机震动
     *
     * @param milliseconds ：震动的时长，单位是毫秒
     *                     long[] pattern ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     *                     boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    public static void vibrate(Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        if (vib != null) {
            vib.vibrate(milliseconds);
        }
    }

    public static void vibrate(Context context, long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        if (vib != null) {
            vib.vibrate(pattern, isRepeat ? 1 : -1);
        }
    }

    /**
     * 获取系统语言
     * getResources().getConfiguration().locale.toString()  -->  输出 zh-CN、zh-TW、en-US、en-CN
     * getResources().getConfiguration().locale.getLanguage()  -->  输出 zh、en
     * <p>
     * 系统语言切换广播
     * <receiver android:name=".utils.FinishAppReceiver" >
     * <intent-filter>
     * <action android:name="android.intent.action.LOCALE_CHANGED" />
     * </intent-filter>
     * </receiver>
     */
    public static void getLanguage() {
        switch (ApplicationUtil.getInstance().getResources().getConfiguration().locale.getCountry()) {
            case "CN":
                //中文
                break;
            case "TW":
                //繁体中文
                break;
            case "UK":
                //英文(英式)
                break;
            case "US":
                //英文(美式)
                break;
        }
    }
}
