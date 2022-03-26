package com.kalacheng.centercommon.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.fragment.MeAnchorFragment;
import com.kalacheng.centercommon.fragment.UserTaskFragment;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.util.view.ViewPagerIndicator2;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户任务中心
 */
@Route(path = ARouterPath.UserTaskCenterActivity)
public class UserTaskCenterActivity extends BaseActivity {
    private ViewPagerIndicator2 Task_Indicator;
    private ViewPager Task_Viewpager;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] TaskTitle = new String[]{"用户任务", "主播任务"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_task_center);
        initView();
        initData();
    }

    private void initView() {
        Task_Indicator = findViewById(R.id.Task_Indicator);
        Task_Viewpager = findViewById(R.id.Task_Viewpager);

        Task_Indicator.setTitles(TaskTitle);

        UserTaskFragment userTaskFragment = new UserTaskFragment();
        mFragments.add(userTaskFragment);

        MeAnchorFragment meAnchorFragment = new MeAnchorFragment();
        mFragments.add(meAnchorFragment);

        FragmentAdapter mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragments);
        Task_Viewpager.setAdapter(mAdapter);
        Task_Viewpager.setCurrentItem(0);
        Task_Viewpager.setOffscreenPageLimit(mFragments.size());
        Task_Indicator.setViewPager(Task_Viewpager);
        Task_Indicator.setSelect(0);

    }

    private void initData() {
//        getMyTask();
    }


}
