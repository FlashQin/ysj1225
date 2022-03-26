package com.kalacheng.message.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.busshop.model.ShopAddress;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.Constants;
import com.kalacheng.message.R;
import com.kalacheng.message.fragment.ReviewAllFragment;
import com.kalacheng.message.fragment.ReviewListFragment;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.base.activty.BaseActivity;

@Route(path = ARouterPath.ReviewsListActivity)
public class ReviewsListActivity extends BaseActivity {
    @Autowired(name = ARouterValueNameConfig.commentType)
    public int commentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_empty_activity);
        initView();
    }

    private void initView() {
        TextView textView = findViewById(R.id.titleView);
        textView.setText("评论列表");

        if (ConfigUtil.getBoolValue(R.bool.isVideo)) {//只有短视频
            showFragment(new ReviewListFragment(2), R.id.fl);
        } else if (ConfigUtil.getBoolValue(R.bool.containShortVideo)) {//动态+短视频
            showFragment(new ReviewAllFragment(commentType), R.id.fl);
        } else {//只有动态
            showFragment(new ReviewListFragment(1), R.id.fl);
        }
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }
}
