package com.kalacheng.base.base;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

import com.kalacheng.base.activty.BaseActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

//悬浮框
public class SuspensionFramePermission {


    private static class SingletonHolder {
        private static final SuspensionFramePermission INSTANCE = new SuspensionFramePermission();
    }

    private SuspensionFramePermission() {

    }

    public static final SuspensionFramePermission getInstance() {
        return SingletonHolder.INSTANCE;
    }


    //判断悬浮框是否开启
    public boolean checkFloatPermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return true;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            try {
                Class cls = Class.forName("android.content.Context");
                Field declaredField = cls.getDeclaredField("APP_OPS_SERVICE");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(cls);
                if (!(obj instanceof String)) {
                    return false;
                }
                String str2 = (String) obj;
                obj = cls.getMethod("getSystemService", String.class).invoke(context, str2);
                cls = Class.forName("android.app.AppOpsManager");
                Field declaredField2 = cls.getDeclaredField("MODE_ALLOWED");
                declaredField2.setAccessible(true);
                Method checkOp = cls.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class);
                int result = (Integer) checkOp.invoke(obj, 24, Binder.getCallingUid(), context.getPackageName());
                return result == declaredField2.getInt(cls);
            } catch (Exception e) {
                return false;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                AppOpsManager appOpsMgr = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                if (appOpsMgr == null)
                    return false;
                int mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", android.os.Process.myUid(), context
                        .getPackageName());
                return mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED;
            } else {
                return Settings.canDrawOverlays(context);
            }
        }
    }

    //开启权限
    public void onpenPermission(Context mContext) {
        try {
            int sdkInt = Build.VERSION.SDK_INT;
            if (sdkInt >= Build.VERSION_CODES.O) {//8.0以上
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + ((AppCompatActivity) mContext).getPackageName()));
                ((AppCompatActivity) mContext).startActivityForResult(intent, BaseActivity.REQUEST_DIALOG_PERMISSION);

            } else if (sdkInt >= Build.VERSION_CODES.M) {//6.0-8.0
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + ((AppCompatActivity) mContext).getPackageName()));
                ((AppCompatActivity) mContext).startActivityForResult(intent, BaseActivity.REQUEST_DIALOG_PERMISSION);
            } else {//4.4-6.0一下
                //无需处理了
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
