package com.kalacheng.shopping.buyer.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busshop.httpApi.HttpApiShopCar;
import com.kalacheng.busshop.model.ShopAddress;
import com.kalacheng.busshop.model.ShopCarAskDTO;
import com.kalacheng.busshop.model.ShopParentOrder;
import com.kalacheng.commonview.dialog.PayMethodSelectDialog;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.buyer.adapter.SubmitOrderAdapter;
import com.kalacheng.shopping.buyer.bean.ShopCarBean;
import com.kalacheng.shopping.buyer.viewmodel.SubmitOrderViewModel;
import com.kalacheng.shopping.databinding.ActivitySubmitOrderBinding;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.jmessage.support.google.gson.Gson;

/**
 * 确认订单
 */
@Route(path = ARouterPath.SubmitOrderActivity)
public class SubmitOrderActivity extends SBaseActivity<ActivitySubmitOrderBinding, SubmitOrderViewModel> {
    @Autowired(name = ARouterValueNameConfig.shopCarBeans)
    ArrayList<ShopCarBean> shopCarBeans;

    private ShopAddress address;
    SubmitOrderAdapter adapter;
    int allCount = 0;
    double allTotal = 0;
    private List<ShopCarAskDTO> shopCarAskDTOS = new ArrayList<>();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_submit_order;
    }

    @Override
    public void initData() {
        super.initData();

        getShopAddrList();

        adapter = new SubmitOrderAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);

        adapter.setShopCarDTOS(makeUp());
        binding.priceTv.setText("¥ " + DecimalFormatUtils.toTwo(allTotal));
        binding.countTv.setText("共" + allCount + "件,合计:");

        binding.addressTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (address != null) {
                    ARouter.getInstance().build(ARouterPath.AddressListActivity).withLong(ARouterValueNameConfig.addressId, address.id).navigation(SubmitOrderActivity.this, 1000);
                } else {
                    ARouter.getInstance().build(ARouterPath.AddAddressActivity).navigation(SubmitOrderActivity.this, 1001);
                }
            }
        });

        binding.submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                submitOrder();
            }
        });
    }

    private ArrayList<ShopCarBean> makeUp() {
        ArrayList<ShopCarBean> newShopCarBeans = new ArrayList<>();
        double total = 0;
        int count = 0;
        if (shopCarBeans != null && shopCarBeans.size() > 0) {
            for (int i = 0; i < shopCarBeans.size(); i++) {
                if (i == 0 || shopCarBeans.get(i).businessId != shopCarBeans.get(i - 1).businessId) {
                    newShopCarBeans.add(careteTitleView(i));
                }

                total += shopCarBeans.get(i).goodsPrice * shopCarBeans.get(i).goodsNum;
                allTotal += shopCarBeans.get(i).goodsPrice * shopCarBeans.get(i).goodsNum;

                count += shopCarBeans.get(i).goodsNum;
                allCount += shopCarBeans.get(i).goodsNum;
                shopCarBeans.get(i).viewType = 1;
                newShopCarBeans.add(shopCarBeans.get(i));

                if (i == shopCarBeans.size() - 1 || shopCarBeans.get(i).businessId != shopCarBeans.get(i + 1).businessId) {
                    newShopCarBeans.add(careteEndView(count, total));
                    count = 0;
                    total = 0;
                }
            }
        }
        Log.e("cjh>>>", "newShopCarBeans:" + new Gson().toJson(newShopCarBeans));
        return newShopCarBeans;
    }

    private ShopCarBean careteTitleView(int index) {
        ShopCarBean shopCarBean = new ShopCarBean();
        shopCarBean.businessId = shopCarBeans.get(index).businessId;
        shopCarBean.businessLogo = shopCarBeans.get(index).businessLogo;
        shopCarBean.businessName = shopCarBeans.get(index).businessName;
        return shopCarBean;
    }

    private ShopCarBean careteEndView(int count, double total) {
        ShopCarBean shopCarBean = new ShopCarBean();
        shopCarBean.count = count;
        shopCarBean.total = total;
        shopCarBean.viewType = 2;
        return shopCarBean;
    }

    /**
     * 收货地址列表
     */
    private void getShopAddrList() {
        HttpApiShopCar.getShopAddrList(new HttpApiCallBackArr<ShopAddress>() {
            @Override
            public void onHttpRet(int code, String msg, List<ShopAddress> retModel) {
                if (code == 1 && retModel != null && retModel.size() > 0) {
                    int index = 0;
                    for (int i = 0; i < retModel.size(); i++) {
                        if (retModel.get(i).isDefault == 1) {
                            index = i;
                        }
                    }
                    address = retModel.get(index);
                    binding.addressTv.setText(address.userName);
                    binding.addressTv.append(" " + address.phoneNum);
                    binding.addressTv.append("\n" + address.pro);
                    binding.addressTv.append(" " + address.city);
                    binding.addressTv.append(" " + address.area);
                    binding.addressTv.append(" " + address.address);
                }
            }
        });
    }

    private void submitOrder() {
        if (null == address) {
            ToastUtil.show("请添加收货地址");
            return;
        }
        createShopCarAskDTOS();
        HttpApiShopCar.purchaseGoods(address.id, shopCarAskDTOS, new HttpApiCallBack<ShopParentOrder>() {
            @Override
            public void onHttpRet(int code, String msg, ShopParentOrder retModel) {
                if (code == 1) {
                    goPayMethodSelectDialog(retModel);
                }
                ToastUtil.show(msg);
            }
        });
    }

    private void createShopCarAskDTOS() {
        for (ShopCarBean bean : shopCarBeans) {
            ShopCarAskDTO askDTO = new ShopCarAskDTO();
            askDTO.carId = bean.id;
            askDTO.goodsId = bean.goodsId;
            askDTO.goodsNum = bean.goodsNum;
            askDTO.skuId = bean.composeId;
            shopCarAskDTOS.add(askDTO);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {//修改收货地址
                address = (ShopAddress) data.getParcelableExtra(ARouterValueNameConfig.addressBean);
                if (address != null) {
                    binding.addressTv.setText(address.userName);
                    binding.addressTv.append(" " + address.phoneNum);
                    binding.addressTv.append("\n" + address.pro);
                    binding.addressTv.append(" " + address.city);
                    binding.addressTv.append(" " + address.area);
                    binding.addressTv.append(" " + address.address);
                }
            } else if (requestCode == 1001) {//添加收货地址
                getShopAddrList();
            }
        }
    }

    /**
     * 进入支付方式选择弹框
     */
    private void goPayMethodSelectDialog(ShopParentOrder order) {
        PayMethodSelectDialog fragment = new PayMethodSelectDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("shop", 1);
        bundle.putParcelable("ShopParentOrder", order);
        bundle.putLong("orderId", order.id);
        fragment.setArguments(bundle);
        fragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                setResult(RESULT_OK);
                finish();
            }
        });
        fragment.show(getSupportFragmentManager(), "PayMethodSelectDialog");
    }

}
