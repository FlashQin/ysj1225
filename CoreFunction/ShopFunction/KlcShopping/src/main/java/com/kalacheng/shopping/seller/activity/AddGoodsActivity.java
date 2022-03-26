package com.kalacheng.shopping.seller.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.busshop.httpApi.HttpApiShopGoods;
import com.kalacheng.busshop.model.ShopGoodsChannel;
import com.kalacheng.busshop.model.ShopGoodsDTO;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.databinding.ActivityAddGoodsBinding;
import com.kalacheng.shopping.seller.dialog.ShopChannelSelectDialog;
import com.kalacheng.shopping.seller.fragment.AddAuthorityGoodsFragment;
import com.kalacheng.shopping.seller.fragment.AddOtherGoodsFragment;
import com.kalacheng.shopping.seller.viewmodel.AddGoodsViewModel;
import com.kalacheng.util.utils.DialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加商品
 */
@Route(path = ARouterPath.AddGoodsActivity)
public class AddGoodsActivity extends SBaseActivity<ActivityAddGoodsBinding, AddGoodsViewModel> {
    @Autowired(name = ARouterValueNameConfig.shopGoods)
    public ShopGoodsDTO goods;

    AddAuthorityGoodsFragment addAuthorityGoodsFragment;
    AddOtherGoodsFragment addOtherGoodsFragment;
    List<ShopGoodsChannel> list;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_add_goods;
    }

    @Override
    public void initData() {
        super.initData();

        list = new ArrayList<>();
        addAuthorityGoodsFragment = new AddAuthorityGoodsFragment();
        addOtherGoodsFragment = new AddOtherGoodsFragment();

        if (goods == null) {
            showFragment(addAuthorityGoodsFragment, R.id.frameLayout);
            binding.titleTv.setText("添加商品");
        } else {
            binding.titleTv.setText("编辑商品");
            if (goods.channelId == 1) {
                showFragment(addAuthorityGoodsFragment, R.id.frameLayout);
            } else {
                showFragment(addOtherGoodsFragment, R.id.frameLayout);
            }
            binding.goodsChannelTv.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            binding.goodsChannelTv.setEnabled(false);
            binding.goodsChannelTv.setText(goods.channelName);
        }

        binding.goodsChannelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopChannelSelectDialog.showStringArrayDialog(mContext, list, new DialogUtil.ChannelDialogCallback() {
                    @Override
                    public void onItemClick(long id, String text) {
                        if (text.equals("官方小店")) {
                            addAuthorityGoodsFragment.setChannelId(id);
                            showFragment(addAuthorityGoodsFragment, R.id.frameLayout);
                        } else {
                            addOtherGoodsFragment.setChannelId(id);
                            showFragment(addOtherGoodsFragment, R.id.frameLayout);
                        }
                        binding.goodsChannelTv.setText(text);
                    }
                });
            }
        });
        getChannelList();
    }

    private void getChannelList() {
        HttpApiShopGoods.getChannelList(new HttpApiCallBackArr<ShopGoodsChannel>() {
            @Override
            public void onHttpRet(int code, String msg, List<ShopGoodsChannel> retModel) {
                if (code == 1 && retModel != null) {
                    list.addAll(retModel);
                    for (ShopGoodsChannel channel : list) {
                        if (channel.goodsChannel.equals("官方小店")) {
                            addAuthorityGoodsFragment.setChannelId(channel.id);
                        }
                    }
                }
            }
        });

    }


}
