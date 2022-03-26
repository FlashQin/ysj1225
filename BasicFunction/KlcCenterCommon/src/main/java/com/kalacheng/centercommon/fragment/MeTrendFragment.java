package com.kalacheng.centercommon.fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.adapter.TimeAxisAdapter;
import com.kalacheng.base.event.MeFragmentRefreshEvent;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MeTrendFragment extends BaseFragment {
    private SmartRefreshLayout refreshLoadTrend;
    private RecyclerView recyclerView;
    private TimeAxisAdapter timeAxisAdapter;
    int pageIndex = 0;

    public MeTrendFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_metrend;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        refreshLoadTrend = mParentView.findViewById(R.id.refreshLoadTrend);
        recyclerView = mParentView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        timeAxisAdapter = new TimeAxisAdapter();
        recyclerView.setAdapter(timeAxisAdapter);

        refreshLoadTrend.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                getMyTrends(false);
            }
        });
    }

    @Override
    protected void initData() {
        pageIndex = 0;
        getMyTrends(true);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTokenInvalidEvent(MeFragmentRefreshEvent event) {
        pageIndex = 0;
        getMyTrends(true);
    }

    /**
     * 获取时间轴
     */
    private void getMyTrends(final boolean isRefresh) {
        HttpApiAppUser.getMyTrendsPage(pageIndex, HttpConstants.PAGE_SIZE, new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && retModel.trends != null) {
                    if (isRefresh) {
                        timeAxisAdapter.RefreshData(retModel.trends);
                    } else {
                        timeAxisAdapter.loadData(retModel.trends);
                        refreshLoadTrend.finishLoadMore();
                    }
                } else {
                    if (!isRefresh) {
                        refreshLoadTrend.finishLoadMore();
                    }
                }
            }
        });
    }
}
