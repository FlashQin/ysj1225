package com.kalacheng.shopping.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busshop.httpApi.HttpApiShopCar;
import com.kalacheng.busshop.model.ShopAddress;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.buyer.adapter.AddressAdapter;
import com.kalacheng.shopping.buyer.viewmodel.AddressListViewModel;
import com.kalacheng.shopping.databinding.ActivityAddressListBinding;

import java.util.List;

import cn.we.swipe.helper.WeSwipe;

/**
 * 收货地址
 */
@Route(path = ARouterPath.AddressListActivity)
public class AddressListActivity extends SBaseActivity<ActivityAddressListBinding, AddressListViewModel> implements AddressAdapter.OnClickSelectListenes {
    @Autowired(name = ARouterValueNameConfig.addressId)
    public long addressId = -1;

    AddressAdapter adapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_address_list;
    }

    @Override
    public void initData() {
        super.initData();

        adapter = new AddressAdapter(addressId);
        adapter.setOnClickSelectListenes(this);
        adapter.setActivity(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
        WeSwipe.attach(binding.recyclerView);

        binding.addTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPath.AddAddressActivity).navigation(AddressListActivity.this, 1001);
            }
        });

        getAddressList();
    }

    private void getAddressList() {

        HttpApiShopCar.getShopAddrList(new HttpApiCallBackArr<ShopAddress>() {
            @Override
            public void onHttpRet(int code, String msg, List<ShopAddress> retModel) {
                if (code == 1) {
                    adapter.setList(retModel);
                }
            }
        });

    }


    @Override
    public void onClickSelectListenes(final int index) {
        binding.recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.putExtra(ARouterValueNameConfig.addressBean, adapter.getItem(index));
                setResult(RESULT_OK, intent);
                finish();
            }
        }, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            getAddressList();
        }
    }

}
