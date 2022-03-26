package com.kalacheng.centercommon.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.fragment.BuyDressingFragment;
import com.kalacheng.commonview.adapter.UserFragmentAdpater;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.util.view.ViewPagerIndicator;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.base.base.BaseMVVMFragment;

import java.util.ArrayList;
import java.util.List;

/*
* 装扮中心
* */
@Route(path = ARouterPath.DressingCenterActivity)
public class DressingCenterActivity extends BaseActivity {

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dressing_center);

        getInitView();
    }
    public void getInitView(){
        ViewPagerIndicator DressingCenter_Indicator = findViewById(R.id.DressingCenter_Indicator);

        ViewPager DressingCenter_Viewpager = findViewById(R.id.DressingCenter_Viewpager);

        final List<Fragment> fragmentList = new ArrayList<>();

        BuyDressingFragment buyDressingFragment = new BuyDressingFragment();
        buyDressingFragment.setType(1);
        fragmentList.add(buyDressingFragment);

        BuyDressingFragment buyDressingFragment1 = new BuyDressingFragment();
        buyDressingFragment1.setType(2);
        fragmentList.add(buyDressingFragment1);

        DressingCenter_Indicator.setTitles(LiveConstants.DRESSING_CENTER);
        DressingCenter_Indicator.setViewPager(DressingCenter_Viewpager);



        UserFragmentAdpater userFragmentAdpater = new UserFragmentAdpater(getSupportFragmentManager());
        userFragmentAdpater.loadData(fragmentList);
        DressingCenter_Viewpager.setAdapter(userFragmentAdpater);
        DressingCenter_Viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < fragmentList.size(); i++) {
                    if (i == position) {
                        if (fragmentList.get(i) instanceof BaseFragment) {
                            ((BaseFragment) fragmentList.get(i)).setShowed(true);
                        }
                    } else {
                        if (fragmentList.get(i) instanceof BaseFragment) {
                            ((BaseFragment) fragmentList.get(i)).setShowed(false);
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
