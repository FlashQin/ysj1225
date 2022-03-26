package com.kalacheng.shopping.seller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.busshop.httpApi.HttpApiShopOrder;
import com.kalacheng.base.event.ShopOrderManualRefreshEvent;
import com.kalacheng.base.event.ShopOrderRefreshEvent;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libuser.model.ShopOrderNumDTO;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.databinding.ActivityOrderManage1Binding;
import com.kalacheng.shopping.seller.adapter.OrderManageFragmentAdapter2;
import com.kalacheng.shopping.seller.viewmodel.OrderManageViewModel2;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 全部订单 - 卖家
 */
@Route(path = ARouterPath.OrderManageActivity2)
public class OrderManageActivity2 extends SBaseActivity<ActivityOrderManage1Binding, OrderManageViewModel2> {
    @Autowired(name = ARouterValueNameConfig.TYPE)
    public int currentPosition;

    OrderManageFragmentAdapter2 adapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_order_manage1;
    }

    @Override
    public void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        binding.titleTv.setText("全部订单");

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.allBtn) {
                    if (binding.viewPager.getCurrentItem() != 0)
                        binding.viewPager.setCurrentItem(0);
                } else if (checkedId == R.id.payBtn) {
                    if (binding.viewPager.getCurrentItem() != 1)
                        binding.viewPager.setCurrentItem(1);
                } else if (checkedId == R.id.sendBtn) {
                    if (binding.viewPager.getCurrentItem() != 2)
                        binding.viewPager.setCurrentItem(2);
                } else if (checkedId == R.id.receivedBtn) {
                    if (binding.viewPager.getCurrentItem() != 3)
                        binding.viewPager.setCurrentItem(3);
                } else if (checkedId == R.id.afterBtn) {
                    if (binding.viewPager.getCurrentItem() != 4)
                        binding.viewPager.setCurrentItem(4);
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
                        binding.payBtn.setChecked(true);
                        break;
                    case 2:
                        binding.sendBtn.setChecked(true);
                        break;
                    case 3:
                        binding.receivedBtn.setChecked(true);
                        break;
                    case 4:
                        binding.afterBtn.setChecked(true);
                        break;
                }

                adapter.getItem(position).onResumeFragment();
                adapter.getItem(currentPosition).onPauseFragment();
                currentPosition = position;

            }
        });

        adapter = new OrderManageFragmentAdapter2(this);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setCurrentItem(currentPosition);
        binding.viewPager.setOffscreenPageLimit(5);
    }


    @Override
    protected void onResume() {
        super.onResume();
        adapter.getItem(currentPosition).onResumeFragment();
        getOrderNum();
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.getItem(currentPosition).onPauseFragment();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShopOrderRefreshEvent(ShopOrderRefreshEvent event) {
        getOrderNum();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShopOrderManualRefreshEvent(ShopOrderManualRefreshEvent event) {
        getOrderNum();
    }

    /**
     * 获取订单数量
     */
    private void getOrderNum() {
        HttpApiShopOrder.getOrderNum(2, new HttpApiCallBack<ShopOrderNumDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ShopOrderNumDTO retModel) {
                if (code == 1 && retModel != null) {
                    if (retModel.toBePayNum > 0) {
                        binding.tvToBePayNum.setVisibility(View.VISIBLE);
                        binding.tvToBePayNum.setText(retModel.toBePayNum + "");
                    } else {
                        binding.tvToBePayNum.setVisibility(View.GONE);
                    }
                    if (retModel.toBeDeliveredNum > 0) {
                        binding.tvToBeDeliveredNum.setVisibility(View.VISIBLE);
                        binding.tvToBeDeliveredNum.setText(retModel.toBeDeliveredNum + "");
                    } else {
                        binding.tvToBeDeliveredNum.setVisibility(View.GONE);
                    }
                    if (retModel.toBeReceivedNum > 0) {
                        binding.tvToBeReceivedNum.setVisibility(View.VISIBLE);
                        binding.tvToBeReceivedNum.setText(retModel.toBeReceivedNum + "");
                    } else {
                        binding.tvToBeReceivedNum.setVisibility(View.GONE);
                    }
                    if (retModel.cancelGoodsNum > 0) {
                        binding.tvCancelGoodsNum.setVisibility(View.VISIBLE);
                        binding.tvCancelGoodsNum.setText(retModel.cancelGoodsNum + "");
                    } else {
                        binding.tvCancelGoodsNum.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}
