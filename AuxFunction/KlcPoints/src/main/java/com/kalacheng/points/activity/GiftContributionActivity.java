package com.kalacheng.points.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busfinance.httpApi.HttpApiAPPFinance;
import com.kalacheng.libuser.model.RanksDto;
import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.points.R;
import com.kalacheng.points.adpater.GiftContributionAdapter;
import com.kalacheng.points.databinding.GiftcontributionBinding;
import com.kalacheng.points.viewmodel.GiftContributionViewModel;
import com.kalacheng.util.utils.ToastUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/*
* 礼物贡献榜
* */
@Route(path = ARouterPath.GiftContribution)
public class GiftContributionActivity extends BaseMVVMActivity<GiftcontributionBinding, GiftContributionViewModel> implements View.OnClickListener, OnLoadMoreListener, OnRefreshListener {
    //用户id
    @Autowired(name = ARouterValueNameConfig.USERID)
    public long userID;

    private int type=1;//类型0总榜1日榜2周榜3月榜
    private int page=1;
    private int action=1;//1刷新。2加载更多

    private GiftContributionAdapter adpater;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.giftcontribution;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        binding.SmarGiftcontri.setOnRefreshListener(this);
        binding.SmarGiftcontri.setOnLoadMoreListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        binding.RecyGiftcontri.setLayoutManager(manager);

        adpater = new GiftContributionAdapter(mContext);
        binding.RecyGiftcontri.setAdapter(adpater);
        getGiftContribution();
        binding.btnConAll.setOnClickListener(this);
        binding.btnConDay.setOnClickListener(this);
        binding.btnConWeek.setOnClickListener(this);
        binding.btnConMonth.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);
    }

    public void getGiftContribution(){
        HttpApiAPPFinance.contributionList((int) userID, page, 10, type, new HttpApiCallBackArr<RanksDto>() {
            @Override
            public void onHttpRet(int code, String msg, List<RanksDto> retModel) {
                if (code==1){
                    if (action==1){
                        adpater.setRefreshData(retModel);
                        binding.SmarGiftcontri.finishRefresh();
                    }else {
                        adpater.setLoadData(retModel);
                        binding.SmarGiftcontri.finishLoadMore();
                    }

                }else {
                    if (action==1){
                        binding.SmarGiftcontri.finishRefresh();
                    }else {
                        binding.SmarGiftcontri.finishLoadMore();
                    }
                    ToastUtil.show(msg);
                }

            }
        });

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();//日
//周
//月
//总
        if (i == R.id.btn_con_day) {
            type = 1;
            page = 1;
            binding.SmarGiftcontri.autoRefresh();
        } else if (i == R.id.btn_con_week) {
            page = 1;
            type = 2;
            binding.SmarGiftcontri.autoRefresh();
        } else if (i == R.id.btn_con_month) {
            page = 1;
            type = 3;
            binding.SmarGiftcontri.autoRefresh();
        } else if (i == R.id.btn_con_all) {
            page = 1;
            type = 0;
            binding.SmarGiftcontri.autoRefresh();
        } else if (i == R.id.btn_back) {
            page = 1;
            finish();
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        action=2;
        getGiftContribution();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page=1;
        action=1;
        getGiftContribution();
    }
}
