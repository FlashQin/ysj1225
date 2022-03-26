package com.kalacheng.points.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import com.kalacheng.busfinance.httpApi.HttpApiAPPFinance;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.libuser.model.RanksDto;
import com.kalacheng.points.R;
import com.kalacheng.points.adpater.GiftContributionAdapter;
import com.kalacheng.points.databinding.ProfitlistBinding;
import com.kalacheng.points.viewmodel.ProfitListViewModel;
import com.kalacheng.base.base.BaseMVVMFragment;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.util.utils.ToastUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

public class RankContributionListFragment extends BaseMVVMFragment<ProfitlistBinding, ProfitListViewModel> implements View.OnClickListener, OnLoadMoreListener, OnRefreshListener {

    private GiftContributionAdapter adpater;
    private int page = 0;
    private int type = 1;//类型0总榜1日榜2周榜3月榜
    private int action = 1;//1刷新。2加载更多

    public RankContributionListFragment(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.profitlist;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void initData() {
        binding.smartProfit.setPrimaryColorsId(R.color.color_000);
        binding.smartProfit.setOnLoadMoreListener(this);
        binding.smartProfit.setOnRefreshListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        binding.recyProfit.setLayoutManager(manager);

        adpater = new GiftContributionAdapter(mContext);
        binding.recyProfit.setAdapter(adpater);
        getProfit();

        binding.btnProAll.setOnClickListener(this);
        binding.btnProDay.setOnClickListener(this);
        binding.btnProMonth.setOnClickListener(this);
        binding.btnProWeek.setOnClickListener(this);
    }

    //加载数据
    public void getProfit() {
        HttpApiAPPFinance.contributionList(0, page, HttpConstants.PAGE_SIZE, type, new HttpApiCallBackArr<RanksDto>() {
            @Override
            public void onHttpRet(int code, String msg, List<RanksDto> retModel) {
                if (code == 1) {
                    if (action == 1) {
                        adpater.setRefreshData(retModel);
                        binding.smartProfit.finishRefresh();
                    } else {
                        adpater.setLoadData(retModel);
                        binding.smartProfit.finishLoadMore();
                    }
                } else {
                    if (action == 1) {
                        binding.smartProfit.finishRefresh();
                    } else {
                        binding.smartProfit.finishLoadMore();
                    }
                    ToastUtil.show(msg);
                }
            }
        });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        action = 2;
        getProfit();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 0;
        action = 1;
        getProfit();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_pro_day) {//日榜
            page = 0;
            action = 1;
            type = 1;
            binding.smartProfit.autoRefresh();
        } else if (i == R.id.btn_pro_week) {//周榜
            page = 0;
            action = 1;
            type = 2;
            binding.smartProfit.autoRefresh();
        } else if (i == R.id.btn_pro_month) {//月榜
            page = 0;
            action = 1;
            type = 3;
            binding.smartProfit.autoRefresh();
        } else if (i == R.id.btn_pro_all) {//总榜
            page = 0;
            action = 1;
            type = 0;
            binding.smartProfit.autoRefresh();
        }
    }
}
