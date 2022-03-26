package com.kalacheng.shopping.buyer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kalacheng.busshop.model.ShopBusiness;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseFragment;
import com.kalacheng.shopping.buyer.adapter.GoodsDetailPicAdapter;
import com.kalacheng.shopping.buyer.viewmodel.ShopSynopsisViewModel;
import com.kalacheng.shopping.databinding.FragmentShopSynopsisBinding;

import java.util.Arrays;

public class ShopSynopsisFragment extends SBaseFragment<FragmentShopSynopsisBinding, ShopSynopsisViewModel> {

    ShopBusiness shopBusiness;
    GoodsDetailPicAdapter detailPicAdapter;
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_shop_synopsis;
    }

    @Override
    public void initData() {

        detailPicAdapter = new GoodsDetailPicAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(detailPicAdapter);

    }


    public void setShopBusiness(ShopBusiness shopBusiness) {
        this.shopBusiness = shopBusiness;
        binding.setModel(this.shopBusiness);
        detailPicAdapter.setList(Arrays.asList(shopBusiness.businessLicense.split(",")));

    }
}
