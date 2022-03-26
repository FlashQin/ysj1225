package com.kalacheng.shopping.seller.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busshop.httpApi.HttpApiShopBusiness;
import com.kalacheng.busshop.model.ShopBusiness;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.databinding.ActivityMyIncomeBinding;
import com.kalacheng.shopping.seller.viewmodel.MyIncomeViewModel;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DecimalFormatUtils;

/**
 * 我的收入
 */
@Route(path = ARouterPath.MyIncomeActivity)
public class MyIncomeActivity extends SBaseActivity<ActivityMyIncomeBinding, MyIncomeViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_my_income;
    }

    @Override
    public void initData() {
        super.initData();

        binding.tvIncomeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.WebActivity).withInt(ARouterValueNameConfig.WebActivityType, 3).navigation();
//                startActivity(new Intent(MyIncomeActivity.this, InComeDetailsActivity.class));
            }
        });

        binding.cashTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.ShopCashActivity).navigation();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getOne();
    }

    /**
     * 获取商家信息
     */
    public void getOne() {
        HttpApiShopBusiness.getOne(new HttpApiCallBack<ShopBusiness>() {
            @Override
            public void onHttpRet(int code, String msg, ShopBusiness retModel) {
                if (code == 1 && retModel != null) {
                    binding.amountTv.setText(DecimalFormatUtils.toTwo(retModel.amount));
                    binding.totalAmountTv.setText(DecimalFormatUtils.toTwo(retModel.totalAmount));
                    binding.totalCashTv.setText(DecimalFormatUtils.toTwo(retModel.totalCash));
                }
            }
        });
    }
}
