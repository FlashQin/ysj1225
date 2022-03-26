package com.kalacheng.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.buscommon.AppHomeHallDTO;
import com.kalacheng.center.R;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libuser.httpApi.HttpApiHome;
import com.kalacheng.libuser.model.AppHotSort;
import com.kalacheng.main.adapter.MainRecommendAdapter;
import com.kalacheng.util.view.ItemDecoration;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * 直播分类
 */
@Route(path = ARouterPath.SquareTagActivity)
public class SquareTagActivity extends BaseActivity implements View.OnClickListener {
    @Autowired(name = ARouterValueNameConfig.AppHotSort)
    public AppHotSort appHotSort;

    RecyclerView recyclerView;
    RefreshLayout refreshLayout;
    int pageIndex;
    MainRecommendAdapter liveAdapter;
    LinearLayout llRecyclerView;
    TextView tvNoData;

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
        getData(true);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 0;
                getData(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                getData(false);

            }
        });
    }

    private void getData(final boolean isRefresh) {
        HttpApiHome.getHomeDataList("", -1, appHotSort.id, -1, -1, 1, pageIndex, HttpConstants.PAGE_SIZE, 0, "", new HttpApiCallBackArr<AppHomeHallDTO>() {
            @Override
            public void onHttpRet(int code, String msg, List<AppHomeHallDTO> retModel) {
                if (code == 1 && null != retModel) {
                    if (isRefresh) {
                        tvNoData.setVisibility(View.GONE);
                        llRecyclerView.setVisibility(View.VISIBLE);
                        refreshLayout.finishRefresh();
                        liveAdapter.RefreshData(retModel);
                    } else {
                        refreshLayout.finishLoadMore();
                        liveAdapter.loadData(retModel);
                    }
                } else {
                    tvNoData.setVisibility(View.VISIBLE);
                    llRecyclerView.setVisibility(View.GONE);
                }
            }
        });

    }

    private void initView() {
        if (appHotSort != null) {
            setTitle(appHotSort.name);
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        liveAdapter = new MainRecommendAdapter(this);
        recyclerView.setAdapter(liveAdapter);
        recyclerView.addItemDecoration(new ItemDecoration(this, 0x00000000, 4, 6));


        llRecyclerView = (LinearLayout) findViewById(R.id.ll_recyclerView);
        tvNoData = (TextView) findViewById(R.id.tv_no_data);
    }

    @Override
    public void onClick(View view) {

    }
}
