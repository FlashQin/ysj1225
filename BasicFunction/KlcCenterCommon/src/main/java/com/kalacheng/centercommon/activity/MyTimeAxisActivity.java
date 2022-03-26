package com.kalacheng.centercommon.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.fragment.MeTrendFragment;
import com.kalacheng.base.activty.BaseActivity;

/**
 * 我的时间轴
 */
@Route(path = ARouterPath.MyTimeAxisActivity)
public class MyTimeAxisActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_time_axis);
        initView();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    private void initView() {
        setTitle("我的时间轴");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        MeTrendFragment meTrendFragment = new MeTrendFragment();
        transaction.add(R.id.layoutAnchorCenter, meTrendFragment);
        transaction.commit();
    }
}
