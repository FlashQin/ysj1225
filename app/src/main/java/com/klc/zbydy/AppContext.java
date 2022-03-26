package com.klc.zbydy;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.center.fragment.MeFragment;
import com.kalacheng.commonview.jguangIm.ImMessageUtil;
import com.kalacheng.commonview.utils.AllSocketUtlis;
import com.kalacheng.main.MainFragmentConfig;
import com.kalacheng.main.fragment.ChatLocalFragment;
import com.kalacheng.main.fragment.FristLoveMainFragment;
import com.kalacheng.main.fragment.FristLoveRobManChatFragment;
import com.kalacheng.main.fragment.FristLoveRobWomanChatFragment;
import com.kalacheng.main.fragment.FristLoveSeekChatFragment;
import com.kalacheng.main.fragment.LiveBuyFragment;
import com.kalacheng.main.fragment.NearFragment;
import com.kalacheng.main.fragment.One2OneNewFragment;
import com.kalacheng.main.fragment.RecommendFragment;
import com.kalacheng.main.fragment.SquareFragment;
import com.kalacheng.main.fragment.TrendContainFragment;
import com.kalacheng.mainpage.activity.MainActivity;
import com.kalacheng.message.fragment.MsgFragment;
import com.kalacheng.shortvideo.fragment.ShortVideoContainFragment;
import com.kalacheng.util.crash.CaocConfig;
import com.kalacheng.util.utils.AppUtil;
import com.kalacheng.util.utils.WordUtil;
import com.tencent.bugly.crashreport.CrashReport;


public class AppContext extends BaseApplication {
//    public static String URL = "http://192.168.8.20:1801";

    @Override
    public void onCreate() {
        super.onCreate();
        HttpClient.getInstance().init(this);
        HttpClient.getInstance().setUrl(BuildConfig.SERVER_URL);
        //初始化全局异常崩溃
        initCrash();
        //初始化腾讯bugly
        CrashReport.initCrashReport(this);
        CrashReport.setAppVersion(this, AppUtil.getVersionName() + "_" + BuildConfig.FLAVOR + (BuildConfig.DEBUG ? "_debug" : ""));
        //极光im初始化
        ImMessageUtil.getInstance().init();

        MainFragmentConfig.FRAGMENTCOMPONENT = new Class[]{ShortVideoContainFragment.class, FristLoveMainFragment.class, TrendContainFragment.class, MsgFragment.class, MeFragment.class};

        MainFragmentConfig.ONEMANTCOMPONENT = new Class[]{RecommendFragment.class, One2OneNewFragment.class, SquareFragment.class, LiveBuyFragment.class, FristLoveRobManChatFragment.class, FristLoveSeekChatFragment.class, NearFragment.class};
        MainFragmentConfig.ONEMANINDICATOR = new String[]{
                WordUtil.getString(com.kalacheng.frame.R.string.recommend),
                WordUtil.getString(com.kalacheng.frame.R.string.friends),
                WordUtil.getString(com.kalacheng.frame.R.string.living_chinese),
                WordUtil.getString(R.string.live_buy),
                WordUtil.getString(com.kalacheng.frame.R.string.robchat),
                WordUtil.getString(com.kalacheng.frame.R.string.askchat),
                WordUtil.getString(com.kalacheng.frame.R.string.near)};

        MainFragmentConfig.ONEWOMANMANTCOMPONENT = new Class[]{RecommendFragment.class, One2OneNewFragment.class, SquareFragment.class, LiveBuyFragment.class, ChatLocalFragment.class, FristLoveRobWomanChatFragment.class, NearFragment.class};
        MainFragmentConfig.ONEWOMANMANINDICATOR = new String[]{
                WordUtil.getString(com.kalacheng.frame.R.string.recommend),
                WordUtil.getString(com.kalacheng.frame.R.string.friends),
                WordUtil.getString(com.kalacheng.frame.R.string.living_chinese),
                WordUtil.getString(R.string.live_buy),
                WordUtil.getString(com.kalacheng.frame.R.string.chatlocal),
                WordUtil.getString(com.kalacheng.frame.R.string.robchat),
                WordUtil.getString(com.kalacheng.frame.R.string.near)};


        //控制全局飘屏退出后台还显示的情况
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacksImpl());
    }

    private void initCrash() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(R.mipmap.ic_launcher) //错误图标
                .restartActivity(MainActivity.class) //重新启动后的activity
//                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
//                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();
    }

    private int ActivityCount;

    private class ActivityLifecycleCallbacksImpl implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.i("aaa", "onActivityCreated");
        }

        @Override
        public void onActivityStarted(Activity activity) {
            AllSocketUtlis.getInstance().closeBackstage();
            Log.i("aaa", "onActivityStarted");
            ActivityCount++;
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Log.i("aaa", "onActivityResumed");
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Log.i("aaa", "onActivityPaused");
        }

        @Override
        public void onActivityStopped(Activity activity) {

            ActivityCount--;
            if (ActivityCount == 0) {
                AllSocketUtlis.getInstance().clean();
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Log.i("aaa","onActivityStopped");
                    }
                }).start();*/
            }

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Log.i("aaa", "onActivitySaveInstanceState");
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.i("aaa", "onActivityDestroyed");
        }


    }
}
