package com.kalacheng.centercommon.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.fragment.UserTaskFragment;
import com.kalacheng.frame.config.ARouterPath;

/**
 * 任务中心
 */
@Route(path = ARouterPath.TaskCenterActivity)
public class TaskCenterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_center);
        initView();
    }

    private void initView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        UserTaskFragment userTaskFragment = new UserTaskFragment();
        transaction.add(R.id.layoutTaskCenterContain, userTaskFragment);
        transaction.commit();
    }
}
