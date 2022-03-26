package com.kalacheng.livecommon.component;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.ActivityLife;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.model.ApiCloseLive;
import com.kalacheng.livecommon.R;
import com.wengying666.imsocket.IMUtil;
import com.wengying666.imsocket.SocketClient;

import io.reactivex.disposables.Disposable;

public abstract class LiveBaseActivity extends BaseActivity {
    public SocketClient mSocket;
    Disposable pauseDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState && savedInstanceState.getBoolean("isRecycler")) {
            finish();
        }
        ARouter.getInstance().inject(this);
        setContentView(getLayout());
        initParams();
        initComponent();
        initSocket();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    protected abstract void initParams();

    protected abstract int getLayout();

    protected abstract void initComponent();

    protected void initSocket() {
        mSocket = IMUtil.getClient();

        LiveBundle.getInstance().setSocketClient(this.getLocalClassName(), mSocket);
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ExitRoom, new MsgListener.SimpleMsgListener<ApiCloseLive>() {
            @Override
            public void onMsg(ApiCloseLive apiCloseLive) {
                ActivityLife.getInstance().reomveActivityResumeListener();
                ActivityLife.getInstance().reomveActivityPauseListener();
//                mSocket.sendDisCconnect(0);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
////                        mSocket.disCconnectClose();
//                    }
//                }, 50);
            }

            @Override
            public void onTagMsg(String tag, ApiCloseLive apiCloseLive) {

            }
        });
    }

    public void setComponent(Class[] clazz, ViewGroup frameLayout) {
        for (int i = 0; i < clazz.length; i++) {
            Class type = clazz[i];
            try {
                Class[] parameterTypes = {Context.class, ViewGroup.class};
                java.lang.reflect.Constructor constructor = type.getConstructor(parameterTypes);
                Object[] parameters = {this, frameLayout};
                constructor.newInstance(parameters);
            } catch (Exception e) {
                Log.i("Exception", "aaa" + e.getMessage());
            }
        }
    }

    @Override
    protected void onDestroy() {
        ((FrameLayout) findViewById(R.id.fl_root1)).removeAllViews();
        ((FrameLayout) findViewById(R.id.fl_root2)).removeAllViews();
        ((FrameLayout) findViewById(R.id.fl_root3)).removeAllViews();
        ((FrameLayout) findViewById(R.id.fl_root4)).removeAllViews();
        ((FrameLayout) findViewById(R.id.fl_root5)).removeAllViews();

        if (LiveConstants.LiveType == 1) {
            LiveBundle.getInstance().removeAllListener();

        }
        ActivityLife.getInstance().sendActivityDestory();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityLife.getInstance().clear();
            }
        }, 50);
        LiveBundle.getInstance().removeSocketClient(this.getLocalClassName());
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
//        ActivityLife.getInstance().sendActivityResume();
        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_GetMapAddress, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        ActivityLife.getInstance().sendActivityPause();

    }

    // 用于开启过多其他应用， 直播间内被CG 资源回收的情况 finish当前直播间
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isRecycler", true);
    }

}
