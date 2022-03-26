package com.kalacheng.login.component;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.event.RequiredInfoEvent;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.login.LoginConfig;
import com.kalacheng.login.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/*
* 注册设置个人信息
* */
@Route(path = ARouterPath.RequiredInfoActivity)
public class RequiredInfoActivity extends BaseActivity {

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_require_info);
        EventBus.getDefault().register(this);
        Class type = LoginConfig.REQUIRED_FRAGMENT_COMPONENT;
        try {
            Class[] parameterTypes = {Context.class, ViewGroup.class};
            java.lang.reflect.Constructor constructor = type.getConstructor(parameterTypes);
            Object[] parameters = {this, findViewById(R.id.layoutRequireInfo)};
            constructor.newInstance(parameters);
            Fragment fragment = (Fragment) constructor.newInstance(parameters);
            showFragment(fragment, R.id.layoutRequireInfo);
        } catch (Exception e) {
            Log.i("Exception", e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequiredInfoEvent(RequiredInfoEvent e) {
        finish();
    }
}
