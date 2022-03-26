package com.kalacheng.base.activty;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.webkit.WebView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.base.BuildConfig;
import com.example.base.R;
import com.fm.openinstall.OpenInstall;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.ApplicationUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.xuantongyun.livecloud.agora.framework.TiPreprocessor;

import java.util.ArrayList;

import io.agora.capture.video.camera.CameraVideoManager;
import io.agora.capture.video.camera.VideoCapture;


/**
 * Created by ysj on 2016/11/18.
 */

public class BaseApplication extends ApplicationUtil {
    private CameraVideoManager mVideoManager;

    /**
     * Activity跟踪结构
     */
    private static ArrayList<ActivityManage> listActivitys = new ArrayList<ActivityManage>();

    @Override
    public void onCreate() {
        super.onCreate();
        sDeBug = BuildConfig.DEBUG;
        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this);

        if (getResources().getInteger(R.integer.bindingSuperiorType) == 1 && isMainProcess()) {
            OpenInstall.init(this);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String processName = getProcessName();
            String packageName = this.getPackageName();
            if (!packageName.equals(processName)) {
                WebView.setDataDirectorySuffix(processName);
            }
        }
    }

    /**
     * 获取Activity跟踪结构
     */
    public static ArrayList<ActivityManage> getListActivitys() {
        return listActivitys;
    }

    /**
     * 退出程序
     */
    public static void exit() {
        for (ActivityManage activity : listActivitys) {
            activity.close();
        }
//        System.exit(0);
    }

    /**
     * 关闭某个Activity外的其他所有界面
     */
    public static void closeOthers(ActivityManage activity) {
        for (ActivityManage otherActivity : listActivitys) {
            if (otherActivity != activity) {
                otherActivity.close();
            }
        }
    }

    /**
     * 某个Activity是否正在运行
     */
    public static boolean containsActivity(ActivityManage activity) {
        for (ActivityManage otherActivity : listActivitys) {
            if (otherActivity == activity) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据名称判断某个Activity 是否在运行并关闭
     */
    public static void containsActivity(String activityName) {
        for (ActivityManage otherActivity : listActivitys) {
            Activity activity = (Activity) otherActivity;
            if (activity.getClass().getSimpleName().equals(activityName)) {
                activity.finish();
                L.e(activityName + "  在运行。。。。。。。。");
            }
        }
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.smart_refresh_bg, R.color.smart_refresh_text);//全局设置主题颜色，下拉刷新背景色、字体颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    /**
     * “我的”，获取底部列表资源名称
     */
    public String[] getCenterConfigBottomNames() {
        return null;
    }

    /**
     * “我的”，获取底部列表资源图片
     */
    public TypedArray getCenterConfigBottomIds() {
        return null;
    }


    public CameraVideoManager getCameraVideoManager() {
        return mVideoManager;
    }


    public void initVideoCaptureAsync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Context application = getApplicationContext();
                mVideoManager = new CameraVideoManager(application, new TiPreprocessor(application));
                mVideoManager.setCameraStateListener(new VideoCapture.VideoCaptureStateListener() {
                    @Override
                    public void onFirstCapturedFrame(int width, int height) {

                    }

                    @Override
                    public void onCameraCaptureError(int error, String msg) {
                        if (mVideoManager != null) {
                            // When there is a camera error, the capture should
                            // be stopped to reset the internal states.
                            mVideoManager.stopCapture();
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 当应用存在多个进程时，确保只在主进程进行初始化 OpenInstall
     */
    public boolean isMainProcess() {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    return getApplicationInfo().packageName.equals(appProcess.processName);
                }
            }
        }
        return false;
    }
}
