package com.kalacheng.shopping.buyer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.kalacheng.busshop.model.ShopGoodsDTO;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseFragment;
import com.kalacheng.shopping.buyer.adapter.GoodsAdapter2;
import com.kalacheng.shopping.buyer.viewmodel.ShopGoodsListViewModel;
import com.kalacheng.shopping.databinding.FragmentShopGoodsListBinding;

import java.util.List;

public class ShopGoodsListFragment extends SBaseFragment<FragmentShopGoodsListBinding, ShopGoodsListViewModel> {


    GoodsAdapter2 adapter2;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_shop_goods_list;
    }

    @Override
    public void initData() {
        adapter2 = new GoodsAdapter2();
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter2);
    }

    public void setList( List<ShopGoodsDTO> list){
        adapter2.setList(list);
    }

}
