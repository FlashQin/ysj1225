package com.kalacheng.shopping.seller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kalacheng.busshop.httpApi.HttpApiShopOrder;
import com.kalacheng.busshop.model.ShopUserOrderDTO;
import com.kalacheng.base.event.ShopOrderManualRefreshEvent;
import com.kalacheng.base.event.ShopOrderRefreshEvent;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseFragment;
import com.kalacheng.shopping.buyer.bean.ShopUserOrderBean;
import com.kalacheng.shopping.databinding.FragmentGoodsManageBinding;
import com.kalacheng.shopping.seller.adapter.OrderManageListAdapter2;
import com.kalacheng.shopping.seller.viewmodel.GoodsManageListViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class OrderManageListFragment2 extends SBaseFragment<FragmentGoodsManageBinding, GoodsManageListViewModel> {
    int page = 0;
    OrderManageListAdapter2 adapter;
    private int quitStatus;
    private int status = 0;

    public OrderManageListFragment2(int quitStatus, int status) {
        this.quitStatus = quitStatus;
        this.status = status;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_goods_manage;
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);

        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                getOrderList();
                EventBus.getDefault().post(new ShopOrderManualRefreshEvent());
            }
        });
        binding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getOrderList();
            }
        });

        adapter = new OrderManageListAdapter2();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
    }

    private void getOrderList() {
        HttpApiShopOrder.getAnchorOrderList(page, HttpConstants.PAGE_SIZE, quitStatus, status, new HttpApiCallBackArr<ShopUserOrderDTO>() {
            @Override
            public void onHttpRet(int code, String msg, List<ShopUserOrderDTO> retModel) {
                if (code == 1) {
                    if (page == 0) {
                        adapter.getList().clear();
                    }
                    adapter.getList().addAll(createList(retModel));
                    adapter.notifyDataSetChanged();
                    if (retModel.size() < HttpConstants.PAGE_SIZE) {
                        binding.refreshLayout.setEnableLoadMore(false);
                    }
                }
                binding.refreshLayout.finishRefresh();
                binding.refreshLayout.finishLoadMore();
            }
        });
    }

    private List<ShopUserOrderBean> createList(List<ShopUserOrderDTO> list) {
        List<ShopUserOrderBean> orderBeans = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            ShopUserOrderBean titleBean = new ShopUserOrderBean();
            titleBean.setViewType(0);
            titleBean.setBusinessOrder(list.get(i).businessOrder);
            titleBean.setBuyUser(list.get(i).buyUser);
            orderBeans.add(titleBean);
            for (int j = 0; j < list.get(i).subOrderList.size(); j++) {
                ShopUserOrderBean orderBean = new ShopUserOrderBean();
                orderBean.setViewType(1);
                orderBean.setBusinessOrder(list.get(i).businessOrder);
                orderBean.setSubOrder(list.get(i).subOrderList.get(j));
                orderBeans.add(orderBean);
            }
            ShopUserOrderBean endBean = new ShopUserOrderBean();
            endBean.setViewType(2);
            endBean.setBusinessOrder(list.get(i).businessOrder);
            endBean.setGoodsNum(list.get(i).goodsNum);
            endBean.setTotal(list.get(i).businessOrder.transactionAmount);
            endBean.setAnchorId(list.get(i).anchorId);
            endBean.setLogisticsNum(list.get(i).logisticsNum);
            endBean.setRefundLogisticsNum(list.get(i).refundLogisticsNum);
            orderBeans.add(endBean);
        }
        return orderBeans;
    }


    @Override
    public void onResume() {
        super.onResume();
        page = 0;
        getOrderList();
    }

    @Override
    public void onResumeFragment() {
        super.onResumeFragment();
        if (binding != null) {
            page = 0;
            getOrderList();
        }
    }

    @Override
    public void onPauseFragment() {
        super.onPauseFragment();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShopOrderRefreshEvent(ShopOrderRefreshEvent event) {
        binding.refreshLayout.autoRefresh();
    }
}
