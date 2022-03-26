package com.kalacheng.shopping.buyer.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.busshop.httpApi.HttpApiShopCar;
import com.kalacheng.busshop.model.ShopAddress;
import com.kalacheng.busshop.model_fun.ShopCar_saveAddress;
import com.kalacheng.busshop.model_fun.ShopCar_updateShopAddress;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.buyer.fragment.CityAreaFragment;
import com.kalacheng.shopping.buyer.viewmodel.AddAddressViewModel;
import com.kalacheng.shopping.databinding.ActivityAddAddressBinding;
import com.kalacheng.util.bean.AddressBean;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加收货地址
 */
@Route(path = ARouterPath.AddAddressActivity)
public class AddAddressActivity extends SBaseActivity<ActivityAddAddressBinding, AddAddressViewModel> implements CityAreaFragment.OnDismissListenes {
    @Autowired(name = ARouterValueNameConfig.shopAddress)
    public ShopAddress shopAddress;

    private String pro;
    private String city;
    private String area;
    ArrayList<AddressBean> list;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_add_address;
    }

    @Override
    public void initData() {
        super.initData();

        if (shopAddress != null) {
            binding.nameEt.setText(shopAddress.userName);
            binding.phoneEt.setText(shopAddress.phoneNum);
            binding.areaTv.setText(shopAddress.pro + " " + shopAddress.city + " " + shopAddress.city);
            binding.addressTv.setText(shopAddress.address);
            binding.checkBox.setChecked(shopAddress.isDefault == 1);
        }

        binding.saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                saveAddress();
            }
        });

        binding.areaTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityAreaFragment cityAreaFragment = new CityAreaFragment();
                cityAreaFragment.setOnDismissListenes(AddAddressActivity.this);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("addressBeans", list);
                cityAreaFragment.setArguments(bundle);
                cityAreaFragment.show(getSupportFragmentManager(), "CityAreaFragment");
            }
        });

    }

    // 同样的判断 无需写2次 根据shopAddress是否为null 分别请求 Update、Add接口
    private void saveAddress() {
        if (TextUtils.isEmpty(binding.nameEt.getText().toString().trim())) {
            ToastUtil.show("请填写收货人姓名");
            return;
        }
        if (TextUtils.isEmpty(binding.phoneEt.getText().toString().trim())) {
            ToastUtil.show("请填写收手机号码");
            return;
        }
        if (TextUtils.isEmpty(binding.areaTv.getText().toString().trim())) {
            ToastUtil.show("请填写收货地区");
            return;
        }
        if (TextUtils.isEmpty(binding.addressTv.getText().toString().trim())) {
            ToastUtil.show("请填写详细地址");
            return;
        }

        if (null != shopAddress) {
            ShopCar_updateShopAddress updateAddress = new ShopCar_updateShopAddress();
            updateAddress.userName = binding.nameEt.getText().toString().trim();
            updateAddress.phoneNum = binding.phoneEt.getText().toString().trim();
            updateAddress.address = binding.addressTv.getText().toString().trim();
            updateAddress.pro = pro;
            updateAddress.city = city;
            updateAddress.area = area;
            updateAddress.isDefault = binding.checkBox.isChecked() ? 1 : 0;
            updateAddress.addressId = shopAddress.id;
            updateAddressHttp(updateAddress);
        } else {
            ShopCar_saveAddress saveAddress = new ShopCar_saveAddress();
            saveAddress.userName = binding.nameEt.getText().toString().trim();
            saveAddress.phoneNum = binding.phoneEt.getText().toString().trim();
            saveAddress.address = binding.addressTv.getText().toString().trim();
            saveAddress.pro = pro;
            saveAddress.city = city;
            saveAddress.area = area;
            saveAddress.isDefault = binding.checkBox.isChecked() ? 1 : 0;
            addAddressHttp(saveAddress);
        }

    }

//    private void addAddress() {
//
//        ShopCar_saveAddress saveAddress = new ShopCar_saveAddress();
//
//        if (TextUtils.isEmpty(binding.nameEt.getText().toString().trim())) {
//            ToastUtil.show("请填写收货人姓名");
//        } else {
//            saveAddress.userName = binding.nameEt.getText().toString().trim();
//        }
//
//        if (TextUtils.isEmpty(binding.phoneEt.getText().toString().trim())) {
//            ToastUtil.show("请填写收手机号码");
//        } else {
//            saveAddress.phoneNum = binding.phoneEt.getText().toString().trim();
//        }
//        if (TextUtils.isEmpty(binding.areaTv.getText().toString().trim())) {
//            ToastUtil.show("请填写收货地区");
//        } else {
//            saveAddress.pro = pro;
//            saveAddress.city = city;
//            saveAddress.area = area;
//        }
//        if (TextUtils.isEmpty(binding.addressTv.getText().toString().trim())) {
//            ToastUtil.show("请填写详细地址");
//        } else {
//            saveAddress.address = binding.addressTv.getText().toString().trim();
//        }
//
//        saveAddress.isDefault = binding.checkBox.isChecked() ? 1 : 0;
//
//        HttpApiShopCar.saveAddress(saveAddress, new HttpApiCallBack<HttpNone>() {
//            @Override
//            public void onHttpRet(int code, String msg, HttpNone retModel) {
//                if (code == 1) {
//                    setResult(RESULT_OK);
//                    finish();
//                }
//            }
//        });
//
//    }

    // 添加地址
    private void addAddressHttp(ShopCar_saveAddress saveAddress) {
        HttpApiShopCar.saveAddress(saveAddress, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    // 更新地址
    private void updateAddressHttp(ShopCar_updateShopAddress updateAddress) {
        HttpApiShopCar.updateShopAddress(updateAddress, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }


    @Override
    public void onDismiss(List<AddressBean> addressBeans) {
        this.list = new ArrayList<>();
        this.list.addAll(addressBeans);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isChecked()) {
                binding.areaTv.setText(list.get(i).getAreaName());
                pro = list.get(i).getAreaName();
                for (int j = 0; j < list.get(i).getCities().size(); j++) {
                    if (list.get(i).getCities().get(j).isChecked()) {
                        binding.areaTv.append(" " + list.get(i).getCities().get(j).getAreaName());
                        city = list.get(i).getCities().get(j).getAreaName();
                        for (int k = 0; k < list.get(i).getCities().get(j).getCounties().size(); k++) {
                            if (list.get(i).getCities().get(j).getCounties().get(k).isChecked()) {
                                binding.areaTv.append(" " + list.get(i).getCities().get(j).getCounties().get(k).getAreaName());
                                area = list.get(i).getCities().get(j).getCounties().get(k).getAreaName();
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }

    }
}
