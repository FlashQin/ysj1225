package com.kalacheng.points.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.busfinance.httpApi.HttpApiAPPFinance;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.libuser.model.RanksDto;
import com.kalacheng.points.R;
import com.kalacheng.points.adpater.FansGroupContributionAdapter;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.base.http.HttpClient;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * 贡献榜
 */
@Route(path = ARouterPath.FansContributionActivity)
public class FansContributionActivity extends BaseActivity {
    RecyclerView recyclerViewRank;
    RefreshLayout refreshLayout;
    FansGroupContributionAdapter fansGroupRankdpater;
    int pageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_recycleview);
        initView();
        initData();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    private void initData() {
        getFansdData(true);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 0;
                getFansdData(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                getFansdData(false);

            }
        });
    }

    private void getFansdData(final boolean isRefresh) {
        HttpApiAPPFinance.contributionList(HttpClient.getUid(), pageIndex, HttpConstants.PAGE_SIZE, 0, new HttpApiCallBackArr<RanksDto>() {
            @Override
            public void onHttpRet(int code, String msg, List<RanksDto> retModel) {
                if (isRefresh) {
                    refreshLayout.finishRefresh();
                    fansGroupRankdpater.setRefreshData(retModel);
                } else {
                    refreshLayout.finishLoadMore();
                    fansGroupRankdpater.setLoadData(retModel);
                }
            }
        });

    }

    private void initView() {
        recyclerViewRank = findViewById(R.id.recyclerView);
        recyclerViewRank.setLayoutManager(new LinearLayoutManager(this));
        fansGroupRankdpater = new FansGroupContributionAdapter(FansContributionActivity.this);
        recyclerViewRank.setAdapter(fansGroupRankdpater);
        setTitle("贡献榜");
    }

}
