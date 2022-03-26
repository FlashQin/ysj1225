package com.kalacheng.shopping.seller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.busshop.httpApi.HttpApiShopGoods;
import com.kalacheng.busshop.model.ShopAttrCompose;
import com.kalacheng.busshop.model.ShopGoodsAttr;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.AttributeValueBean;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.databinding.ActivityEditPriceBinding;
import com.kalacheng.shopping.seller.adapter.EditPriceAdapter;
import com.kalacheng.shopping.seller.viewmodel.EditPriceViewModel;
import com.kalacheng.util.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 编辑属性价格
 */
@Route(path = ARouterPath.EditPriceActivity)
public class EditPriceActivity extends SBaseActivity<ActivityEditPriceBinding, EditPriceViewModel> {
    @Autowired(name = ARouterValueNameConfig.ShopAttrList)
    public ArrayList<ShopAttrCompose> list;

    EditPriceAdapter adapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_edit_price;
    }

    @Override
    public void initData() {
        super.initData();

        adapter = new EditPriceAdapter();
        adapter.setList(list);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);

        binding.addTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPrices();
            }
        });

    }

    private void getPrices() {
        for (int i = 0; i < list.size(); i++) {
            AttributeValueBean bean = adapter.getObjects().get(i);

            String price = bean.price1Et.getText().toString().trim();
            String favorablePrice = bean.price2Et.getText().toString().trim();
            String stock = bean.countEt.getText().toString().trim();

            list.get(i).price = Double.parseDouble(TextUtils.isEmpty(price) ? "0" : price);
            list.get(i).favorablePrice = Double.parseDouble(TextUtils.isEmpty(favorablePrice) ? "0" : favorablePrice);
            list.get(i).stock = Integer.parseInt(TextUtils.isEmpty(stock) ? "0" : stock);
        }

        for (ShopAttrCompose compose : list) {
            if (compose.price == 0) {
                ToastUtil.show("请输入价格");
                return;
            } else if (compose.stock == 0) {
                ToastUtil.show("请输入库存");
                return;
            }
        }

        setPriceInventory();
    }

    private void setPriceInventory() {
        HttpApiShopGoods.setPriceInventory(list, new HttpApiCallBackArr<ShopGoodsAttr>() {
            @Override
            public void onHttpRet(int code, String msg, List<ShopGoodsAttr> retModel) {
                if (code == 1) {
//                    BaseApplication.containsActivity("GoodsAttributeActivitry");
                    setResult(RESULT_OK, new Intent().putParcelableArrayListExtra("EditPriceActivity", (ArrayList<? extends Parcelable>) retModel));
                    finish();
                }
                ToastUtil.show(msg);
            }
        });

    }


}
