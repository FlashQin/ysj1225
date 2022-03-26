package com.kalacheng.shopping.seller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kalacheng.busshop.httpApi.HttpApiShopGoods;
import com.kalacheng.busshop.model.ShopGoodsDTO;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseFragment;
import com.kalacheng.shopping.databinding.FragmentGoodsManageBinding;
import com.kalacheng.shopping.seller.adapter.GoodsManageListAdapter;
import com.kalacheng.shopping.seller.viewmodel.GoodsManageListViewModel;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

public class GoodsManageListFragment extends SBaseFragment<FragmentGoodsManageBinding, GoodsManageListViewModel>
        implements GoodsManageListAdapter.OnClickListener {

    int page = 0;
    private GoodsManageListAdapter adapter;
    private int status = 0;

    public GoodsManageListFragment(int status) {
        this.status = status;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_goods_manage;
    }

    @Override
    public void initData() {

        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                getGoodsList();
            }
        });
        binding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getGoodsList();
            }
        });

        adapter = new GoodsManageListAdapter();
        adapter.setListener(this);
        adapter.setStatus(status);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
    }

    private void getGoodsList() {
        HttpApiShopGoods.getGoodsList(page, HttpConstants.PAGE_SIZE, status, new HttpApiCallBackArr<ShopGoodsDTO>() {
            @Override
            public void onHttpRet(int code, String msg, List<ShopGoodsDTO> retModel) {
                if (code == 1) {
                    if (page == 0) {
                        adapter.getList().clear();
                    }
                    adapter.getList().addAll(retModel);
                    adapter.notifyDataSetChanged();
                }
                binding.refreshLayout.finishRefresh();
                binding.refreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    public void onResumeFragment() {
        super.onResumeFragment();
        page = 0;
        getGoodsList();
    }

    @Override
    public void onPauseFragment() {
        super.onPauseFragment();
    }

    @Override
    public void updateGoods() {
        binding.refreshLayout.autoRefresh();
    }
}
