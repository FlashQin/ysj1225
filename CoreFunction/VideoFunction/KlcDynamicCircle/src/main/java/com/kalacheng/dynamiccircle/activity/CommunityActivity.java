package com.kalacheng.dynamiccircle.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.dynamiccircle.R;
import com.kalacheng.dynamiccircle.fragment.CommunityListFragment;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.activty.BaseActivity;

/**
 * 社区
 */
@Route(path = ARouterPath.CommunityActivity)
public class CommunityActivity extends BaseActivity {
    @Autowired(name = ARouterValueNameConfig.HOT_ID)
    public int hotId;
    @Autowired(name = ARouterValueNameConfig.Name)
    public String name;

    CommunityListFragment fragment;
    TextView titleView;

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiity_community);

        titleView = findViewById(R.id.titleView);
        titleView.setText("#" + name + "#");
        fragment = new CommunityListFragment(0, hotId, 0);
        addFragment(fragment, R.id.frame);

    }
}
