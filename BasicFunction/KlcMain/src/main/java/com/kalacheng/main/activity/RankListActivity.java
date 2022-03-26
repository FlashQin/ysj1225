package com.kalacheng.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.points.fragment.RankListFragment;
import com.kalacheng.main.R;
import com.kalacheng.base.activty.BaseActivity;

@Route(path = ARouterPath.RankListActivity)
public class RankListActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_empty_framelayout);
        initData();
    }

    private void initData() {
        findViewById(R.id.ivBack).setVisibility(View.GONE);

        FrameLayout fl = findViewById(R.id.fl);
        RankListFragment rankListFragment = new RankListFragment(this, fl);
        showFragment(rankListFragment, R.id.fl);
    }

}
