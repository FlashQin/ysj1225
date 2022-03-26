package com.kalacheng.shopping.seller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.busshop.httpApi.HttpApiShopOrder;
import com.kalacheng.busshop.model.ShopLogisticsDTO;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.databinding.ActivityChooseLogisticsBinding;
import com.kalacheng.shopping.seller.viewmodel.ChooseLogisticsViewModel;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.ToastUtil;

import java.util.List;

/**
 * 选择物流
 */
@Route(path = ARouterPath.ChooseLogisticsActivity)
public class ChooseLogisticsActivity extends SBaseActivity<ActivityChooseLogisticsBinding, ChooseLogisticsViewModel> {
    @Autowired(name = ARouterValueNameConfig.orderId)
    public long orderId = 0;

    public List<String> logisticsList;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_choose_logistics;
    }

    @Override
    public void initData() {
        super.initData();

        binding.llPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
//                DialogUtil.showStringArrayDialog2(mContext, logisticsList, new DialogUtil.ChannelDialogCallback() {
//                    @Override
//                    public void onItemClick(long id, String text) {
//                        binding.logisticsCompanyEt.setText(text);
//                    }
//                });
                DialogUtil.showSingleTextPickerDialog(mContext, logisticsList.toArray(new String[]{}), new DialogUtil.SingleTextCallback() {
                    @Override
                    public void onConfirmClick(int id, String date) {
                        binding.logisticsCompanyEt.setText(date);
                        binding.orderNumEt.requestFocus();
                    }
                });

            }
        });

        binding.confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                confirmSent();
            }
        });

        getLogisticsList();

    }

    private void confirmSent() {
        String logisticsCompany = binding.logisticsCompanyEt.getText().toString().trim();

        if (TextUtils.isEmpty(logisticsCompany)) {
            ToastUtil.show("请选择物流公司");
            return;
        }

        String waybillNum = binding.orderNumEt.getText().toString().trim();
        if (TextUtils.isEmpty(waybillNum)) {
            ToastUtil.show("请填写运单号");
            return;
        }

        HttpApiShopOrder.confirmSent(orderId, logisticsCompany, waybillNum, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    ToastUtil.show("发货成功");
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });

    }

    private void getLogisticsList() {
        HttpApiShopOrder.getLogisticsList(new HttpApiCallBack<ShopLogisticsDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ShopLogisticsDTO retModel) {
                if (code == 1) {
                    logisticsList = retModel.logisticsList;
                }
            }
        });
    }

}
