package com.kalacheng.livecommon.component;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.activty.ActivityLife;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.listener.MsgListener;
import com.wengying666.imsocket.IMUtil;
import com.wengying666.imsocket.SocketClient;

import java.lang.reflect.InvocationTargetException;

import io.reactivex.disposables.Disposable;

public abstract class OOOLiveBaseActivity extends BaseActivity {
    public SocketClient msocket;
    Disposable pauseDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(getLayout());
        initParams();
        initComponent();
        initSocket();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // 禁止录屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }


    protected abstract void initParams();

    protected abstract int getLayout();

    protected abstract void initComponent();

//    protected abstract void startComponent();


    public void setComponent(Class[] clazz, ViewGroup frameLayout) {
        for (int i = 0; i < clazz.length; i++) {
            Class type = clazz[i];
            Class[] parameterTypes = {Context.class, ViewGroup.class};
            java.lang.reflect.Constructor constructor = null;
            try {
                constructor = type.getConstructor(parameterTypes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            Object[] parameters = {this, frameLayout};
            try {
                constructor.newInstance(parameters);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
//        ((FrameLayout) findViewById(R.id.fl_root1)).removeAllViews();
//        ((FrameLayout) findViewById(R.id.fl_root2)).removeAllViews();
//        ((FrameLayout) findViewById(R.id.fl_root3)).removeAllViews();
//        ((FrameLayout) findViewById(R.id.fl_root4)).removeAllViews();
//        ((FrameLayout) findViewById(R.id.fl_root5)).removeAllViews();

        ActivityLife.getInstance().sendActivityDestory();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityLife.getInstance().clear();
            }
        }, 50);


        super.onDestroy();
    }

    protected void initSocket() {
        msocket = IMUtil.getClient();

        LiveBundle.getInstance().setSocketClient(this.getLocalClassName(), msocket);
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_CloseLive, new MsgListener.SimpleMsgListener<Object>() {
            @Override
            public void onMsg(Object o) {
                ActivityLife.getInstance().reomveActivityResumeListener();
                ActivityLife.getInstance().reomveActivityPauseListener();

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityLife.getInstance().sendActivityResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityLife.getInstance().sendActivityPause();
    }

}
