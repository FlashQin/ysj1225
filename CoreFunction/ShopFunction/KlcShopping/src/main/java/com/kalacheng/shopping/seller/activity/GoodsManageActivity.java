package com.kalacheng.shopping.seller.activity;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.databinding.ActivityGoodsManageBinding;
import com.kalacheng.shopping.seller.adapter.GoodsManageFragmentAdapter;
import com.kalacheng.shopping.seller.viewmodel.GoodsManageViewModel;

/**
 * 商品管理
 */
@Route(path = ARouterPath.GoodsManageActivity)
public class GoodsManageActivity extends SBaseActivity<ActivityGoodsManageBinding, GoodsManageViewModel> {

    private GoodsManageFragmentAdapter adapter;
    private int currentPosition = 0;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_goods_manage;
    }

    @Override
    public void initData() {
        super.initData();

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.allBtn) {
                    if (binding.viewPager.getCurrentItem() != 0)
                        binding.viewPager.setCurrentItem(0);
                } else if (checkedId == R.id.alreadyBtn) {
                    if (binding.viewPager.getCurrentItem() != 1)
                        binding.viewPager.setCurrentItem(1);
                } else if (checkedId == R.id.stayBtn) {
                    if (binding.viewPager.getCurrentItem() != 2)
                        binding.viewPager.setCurrentItem(2);
                } else if (checkedId == R.id.freezingBtn) {
                    if (binding.viewPager.getCurrentItem() != 3)
                        binding.viewPager.setCurrentItem(3);
                }
            }
        });

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        binding.allBtn.setChecked(true);
                        break;
                    case 1:
                        binding.alreadyBtn.setChecked(true);
                        break;
                    case 2:
                        binding.stayBtn.setChecked(true);
                        break;
                    case 3:
                        binding.freezingBtn.setChecked(true);
                        break;
                }

                adapter.getItem(position).onResumeFragment();
                adapter.getItem(currentPosition).onPauseFragment();
                currentPosition = position;

            }
        });

        adapter = new GoodsManageFragmentAdapter(this);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setCurrentItem(0);

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.getItem(currentPosition).onResumeFragment();
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.getItem(currentPosition).onPauseFragment();
    }
}
