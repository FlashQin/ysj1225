package com.kalacheng.message.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.message.R;
import com.kalacheng.message.fragment.NotifyListFragment;
import com.kalacheng.base.activty.BaseActivity;

@Route(path = ARouterPath.NotifyListActivity)
public class NotifyListActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_empty_activity);
        initView();
    }

    private void initView() {
        TextView textView = findViewById(R.id.titleView);
        textView.setText("通知");
        showFragment(new NotifyListFragment(), R.id.fl);
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }
}
