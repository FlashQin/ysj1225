package com.kalacheng.livecommon.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.busfinance.httpApi.HttpApiAPPFinance;
import com.kalacheng.libuser.model.ApiUsersVoterecord;
import com.kalacheng.base.base.BaseMVVMFragment;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBackPageArr;
import com.kalacheng.base.http.PageInfo;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.ProfitAdpater;
import com.kalacheng.livecommon.databinding.TotalProfitBinding;
import com.kalacheng.livecommon.viewmodel.TotalProfitViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 累计收益榜
 */
public class TotalProfitDialogFragment extends BaseMVVMFragment<TotalProfitBinding, TotalProfitViewModel> {
    private ProfitAdpater adpater;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.total_profit;
    }

    private int page = 0;

    @SuppressLint("WrongConstant")
    @Override
    public void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(OrientationHelper.VERTICAL);
        binding.totalProfitRecy.setLayoutManager(manager);


        adpater = new ProfitAdpater(getActivity());
        binding.totalProfitRecy.setAdapter(adpater);
        adpater.setProfitCallBack(new ProfitAdpater.ProfitCallBack() {
            @Override
            public void onClick(int poistion) {
                if (adpater != null && adpater.getItemCount() > 0) {
                    LiveConstants.TOUID = adpater.getList().get(poistion).uid;
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.Information, null);
                }
            }
        });

        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                getProfit();
            }
        });
        binding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getProfit();
            }
        });

        getProfit();
    }

    //加载数据
    public void getProfit() {
        HttpApiAPPFinance.votesList(page, HttpConstants.PAGE_SIZE, 0, 0, new HttpApiCallBackPageArr<ApiUsersVoterecord>() {
            @Override
            public void onHttpRet(int code, String msg, PageInfo pageInfo, List<ApiUsersVoterecord> retModel) {
                if (code == 1 && retModel != null) {

                    if (page == 0) {
                        adpater.setData(retModel);
                        binding.refreshLayout.finishRefresh(true);
                    } else {
                        adpater.loadMoreList(retModel);
                        binding.refreshLayout.finishLoadMore(true);
                    }
                } else {
                    if (page == 0) {
                        adpater.setData(new ArrayList<ApiUsersVoterecord>());
//                        showNoDataNote.setVisibility(View.VISIBLE);
                    }
                    ToastUtil.show(msg);
                    binding.refreshLayout.finishRefresh();
                    binding.refreshLayout.finishLoadMore();
                }
            }
        });

    }

    @Override
    public void refreshData() {
        super.refreshData();
        goTop();
    }

    /**
     * 调用刷新动画， 列表回到 顶端
     */
    private void goTop() {
        if (binding.refreshLayout != null) {

            if (binding.refreshLayout.autoRefresh()) {
                binding.totalProfitRecy.scrollToPosition(0);

            }

        }
    }
}
