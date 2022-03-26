package com.kalacheng.shopping.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busshop.httpApi.HttpApiShopCar;
import com.kalacheng.busshop.model.ShopCar;
import com.kalacheng.busshop.model.ShopCarDTO;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.buyer.adapter.ShopCartAdapter;
import com.kalacheng.shopping.buyer.bean.ShopCarBean;
import com.kalacheng.shopping.buyer.viewmodel.ShoppingCartViewModel;
import com.kalacheng.shopping.databinding.ActivityShoppingCartBinding;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.we.swipe.helper.WeSwipe;

/**
 * 购物车
 */
@Route(path = ARouterPath.ShoppingCartActivity)
public class ShoppingCartActivity extends SBaseActivity<ActivityShoppingCartBinding, ShoppingCartViewModel> implements ShopCartAdapter.OnTodalListener {

    ShopCartAdapter shopCartAdapter;
    int count = 0;


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_shopping_cart;
    }

    @Override
    public void initData() {
        super.initData();

        shopCartAdapter = new ShopCartAdapter(this);
        shopCartAdapter.setOnTodalListener(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(shopCartAdapter);
        WeSwipe.attach(binding.recyclerView);

        binding.checkAllCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopCartAdapter.shopCartAll(binding.checkAllCb.isChecked() ? 1 : 0);
            }
        });

        binding.goodsCountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                getShopCart();
            }
        });

        getShopCarList();
    }

    private void getShopCarList() {

        HttpApiShopCar.getShopCarList(new HttpApiCallBackArr<ShopCarDTO>() {
            @Override
            public void onHttpRet(int code, String msg, List<ShopCarDTO> retModel) {
                if (code == 1) {
                    if (retModel != null && retModel.size() > 0) {
                        List<ShopCarDTO> shopCarDTOS = new ArrayList<>();
                        for (ShopCarDTO shopCarDTO : retModel) {
                            shopCarDTOS.add(createTitle(shopCarDTO));
                            for (ShopCar car : shopCarDTO.shopCarList) {
                                shopCarDTOS.add(createGoods(shopCarDTO, car));
                            }
                        }
                        shopCartAdapter.setShopCarDTOS(shopCarDTOS);
                    } else {
                        shopCartAdapter.setShopCarDTOS(null);
                    }

                }
            }
        });

    }

    private ShopCarDTO createTitle(ShopCarDTO shopCarDTO) {
        ShopCarDTO titleDTo = new ShopCarDTO();
        titleDTo.businessId = shopCarDTO.businessId;
        titleDTo.businessLogo = shopCarDTO.businessLogo;
        titleDTo.businessName = shopCarDTO.businessName;
        titleDTo.checked = 1;
        titleDTo.viewType = 0;
        return titleDTo;
    }

    private ShopCarDTO createGoods(ShopCarDTO shopCarDTO, ShopCar car) {
        ShopCarDTO titleDTo = new ShopCarDTO();
        titleDTo.businessId = shopCarDTO.businessId;
        titleDTo.businessLogo = shopCarDTO.businessLogo;
        titleDTo.businessName = shopCarDTO.businessName;
        titleDTo.checked = 1;
        titleDTo.viewType = 1;
        titleDTo.shopCarList = new ArrayList<>();
        titleDTo.shopCarList.add(car);
        return titleDTo;
    }

    private void getShopCart() {
        if (count != 0) {
            ArrayList<ShopCarBean> shopCarBeans = new ArrayList<>();
            for (ShopCarDTO shopCarDTO : shopCartAdapter.getList()) {
                if (shopCarDTO.viewType == 1 && shopCarDTO.checked == 1) {
                    shopCarBeans.add(createShopCarDTO(shopCarDTO));
                }
            }
            ARouter.getInstance().build(ARouterPath.SubmitOrderActivity)
                    .withParcelableArrayList(ARouterValueNameConfig.shopCarBeans, shopCarBeans)
                    .navigation(this, 10001);
        } else {
            ToastUtil.show("请选择要购买的商品");
        }
    }

    private ShopCarBean createShopCarDTO(ShopCarDTO shopCarDTO) {
        ShopCarBean shopCarBean = new ShopCarBean();
        shopCarBean.businessId = shopCarDTO.businessId;
        shopCarBean.businessLogo = shopCarDTO.businessLogo;
        shopCarBean.businessName = shopCarDTO.businessName;

        ShopCar shopCar = shopCarDTO.shopCarList.get(0);
        shopCarBean.goodsId = shopCar.goodsId;
        shopCarBean.goodsName = shopCar.goodsName;
        double price = shopCar.goodsPrice;
        shopCarBean.goodsPrice = price;
        shopCarBean.skuName = shopCar.skuName;
        shopCarBean.goodsNum = shopCar.goodsNum;
        if (!TextUtils.isEmpty(shopCar.goodsPicture)) {
            shopCarBean.goodsPicture = shopCar.goodsPicture.split(",")[0];
        } else {
            shopCarBean.goodsPicture = "";
        }
        shopCarBean.id = shopCar.id;
        shopCarBean.composeId = shopCar.composeId;
        return shopCarBean;
    }


    @Override
    public void onTodalListener(double todal, int size) {
        count = size;
        binding.priceTv.setText("¥ " + DecimalFormatUtils.toTwo(todal));
        binding.goodsCountTv.setText("结算(" + size + ")");
    }

    @Override
    public void isAllCheckedListener(boolean b) {
        binding.checkAllCb.setChecked(b);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10001 && resultCode == RESULT_OK) {
            getShopCarList();
        }
    }
}
