package com.kalacheng.shortvideo.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.shortvideo.R;

@Route(path = ARouterPath.MyViewActivity)
public class MyViewActivity extends BaseActivity {

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view);
        initView();
    }

    private void initView() {
        setTitle("我的视图");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        MyViewFragment myViewFragment = new MyViewFragment(HttpClient.getUid(), true);
        transaction.add(R.id.layoutContain, myViewFragment);
        transaction.commit();
    }

}
