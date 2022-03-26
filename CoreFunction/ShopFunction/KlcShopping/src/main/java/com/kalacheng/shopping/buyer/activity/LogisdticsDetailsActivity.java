package com.kalacheng.shopping.buyer.activity;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.busshop.httpApi.HttpApiShopOrder;
import com.kalacheng.busshop.model.ApiShopLogisticsDTO;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.buyer.adapter.LogisdticsAdapter;
import com.kalacheng.shopping.buyer.viewmodel.LogisdticsDetailsViewModel;
import com.kalacheng.shopping.databinding.ActivityLogisticsDetailsBinding;
import com.kalacheng.util.utils.ToastUtil;

/**
 * 物流详情
 */
@Route(path = ARouterPath.LogisdticsDetailsActivity)
public class LogisdticsDetailsActivity extends SBaseActivity<ActivityLogisticsDetailsBinding, LogisdticsDetailsViewModel> {
    @Autowired(name = ARouterValueNameConfig.orderNo)
    public String orderNo;//订单编号
    @Autowired(name = ARouterValueNameConfig.logisticsNumber)
    public String logisticsNumber;//物流单号

    LogisdticsAdapter adapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_logistics_details;
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    public void initData() {
        super.initData();

        adapter = new LogisdticsAdapter();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);

        HttpApiShopOrder.getLogistics(logisticsNumber, orderNo, new HttpApiCallBack<ApiShopLogisticsDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ApiShopLogisticsDTO retModel) {
                if (code == 1 && retModel != null) {
                    if (TextUtils.isEmpty(retModel.expName) && TextUtils.isEmpty(retModel.number) && retModel.nodeList == null) {
                        binding.numTv.setText("暂无物流信息");
                    } else {
                        binding.numTv.setText((TextUtils.isEmpty(retModel.expName) ? "" : retModel.expName + "  ") + (TextUtils.isEmpty(retModel.number) ? "" : retModel.number));
                        adapter.setList(retModel.nodeList);
                    }
                } else {
                    ToastUtil.show(msg);
                }
            }
        });

    }
}
