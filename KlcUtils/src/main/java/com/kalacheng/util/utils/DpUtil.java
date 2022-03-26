package com.kalacheng.util.utils;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Created by cxf on 2017/8/9.
 * dp转px工具类
 */

public class DpUtil {

    private static float scale;

    static {
        scale = ApplicationUtil.getInstance().getResources().getDisplayMetrics().density;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(int dpVal) {
        return (int) (scale * dpVal + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(float pxVal) {
        return (int) (pxVal / scale + 0.5f);
    }

    /**
     * 从dp 的单位 转成为 px
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, ApplicationUtil.getInstance().getResources().getDisplayMetrics());
    }

    /**
     * 从px 的单位 转成为 sp
     */
    public static int px2sp(float pxVal) {
        return (int) (pxVal / ApplicationUtil.getInstance().getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * 获取屏幕可显示宽高(包括进度条,不包括虚拟按钮)，例 DpUtil.getScreenOrientation(1).x
     *
     * @param nOrientation 横屏为0,竖屏为1
     * @return 返回x为宽y为高
     */
    public static Point getScreenOrientation(int nOrientation) {
        int nWidth = ApplicationUtil.getInstance().getResources().getDisplayMetrics().widthPixels;
        int nHeight = ApplicationUtil.getInstance().getResources().getDisplayMetrics().heightPixels;
        Point ptScreen = new Point();
        if (0 == nOrientation) {
            ptScreen.x = Math.max(nWidth, nHeight);
            ptScreen.y = Math.min(nWidth, nHeight);
        } else {
            ptScreen.x = Math.min(nWidth, nHeight);
            ptScreen.y = Math.max(nWidth, nHeight);
        }
        return ptScreen;

        // /** 版本最低为13 */
        // Rect outSize = new Rect();
        // getWindowManager().getDefaultDisplay().getRectSize(outSize);
        // Log.e("!!!", outSize.toString());
        // Log.d("!!!", outSize.width() + "");
        // Log.d("!!!", outSize.height() + "");
    }

    /**
     * 获取布局宽高
     *
     * @param vgFrame      布局对象
     * @param nOrientation 横屏为0,竖屏为1
     * @return 返回x为宽y为高
     */
    public static Point getViewOrientation(View vgFrame, int nOrientation) {
        int nWidth = vgFrame.getWidth();
        int nHeight = vgFrame.getHeight();
        Point ptScreen = new Point();
        if (0 == nOrientation) {
            ptScreen.x = Math.max(nWidth, nHeight);
            ptScreen.y = Math.min(nWidth, nHeight);
        } else {
            ptScreen.x = Math.min(nWidth, nHeight);
            ptScreen.y = Math.max(nWidth, nHeight);
        }
        return ptScreen;
    }


    /**
     * 获取控件的高度或者宽度  isHeight=true则为测量该控件的高度，isHeight=false则为测量该控件的宽度
     *
     * @param view
     * @param isHeight
     * @return
     */
    public static int getViewHeight(View view, boolean isHeight) {
        int result;
        if (view == null) return 0;
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        if (isHeight) {
            view.measure(h, 0);
            result = view.getMeasuredHeight();
        } else {
            view.measure(0, h);
            result = view.getMeasuredWidth();
        }
        return result;
    }

    /**
     * 获得屏幕宽度
     */
    public static int getScreenWidth() {
        return ApplicationUtil.getInstance().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕高度
     */
    public static int getScreenHeight() {
        return ApplicationUtil.getInstance().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获得状态栏的高度
     */
    public static int getStatusHeight() {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = ApplicationUtil.getInstance().getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 状态栏是否存在
     *
     * @return true 存在； false 不存在
     */
    public static boolean statusBarVisable(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        return !((attrs.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

}
