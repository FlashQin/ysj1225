package com.kalacheng.shopping.seller.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.BaseFragmentAdapter;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.databinding.ActivityLiveBuyNoticeBinding;
import com.kalacheng.shopping.seller.fragment.LiveNoticeFragment;
import com.kalacheng.shopping.seller.viewmodel.LiveBuyNoticeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 直播预告
 */
@Route(path = ARouterPath.LiveBuyNoticeActivity)
public class LiveBuyNoticeActivity extends SBaseActivity<ActivityLiveBuyNoticeBinding, LiveBuyNoticeViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_live_buy_notice;
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    public void initData() {
        super.initData();

        List<Fragment> list = new ArrayList<>();
        list.add(new LiveNoticeFragment());
//        list.add(new OrderManageListFragment2(0));
        binding.viewPager.setAdapter(new BaseFragmentAdapter(this, list));
        binding.viewPager.setCurrentItem(0);
        binding.viewPager.setUserInputEnabled(false);

    }
}
