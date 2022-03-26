package com.klc.zbydy.activity;


import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.login.component.BaseLauncherActivity;

/**
 * Created by hgy on 2019/9/17.
 */

public class LauncherActivity extends BaseLauncherActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().build(ARouterPath.LoginActivity).navigation();
        finish();
    }
}
