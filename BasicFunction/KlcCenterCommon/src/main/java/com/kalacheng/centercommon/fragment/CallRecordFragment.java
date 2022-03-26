package com.kalacheng.centercommon.fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busooolive.httpApi.HttpApiOOOLive;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.adapter.CallRecordAdapter;
import com.kalacheng.libuser.model.CallRecordDto;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.base.http.HttpClient;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

public class CallRecordFragment extends BaseFragment {
    int pageIndex;
    RecyclerView recyclerView;
    RefreshLayout refreshLayout;
    CallRecordAdapter callRecordAdapter;

    public CallRecordFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_empty_recycleview;
    }

    @Override
    protected void initView() {
        recyclerView = mParentView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshLayout = (RefreshLayout) mParentView.findViewById(R.id.refreshLayout);
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
        if (null == callRecordAdapter) {
            callRecordAdapter = new CallRecordAdapter();
            recyclerView.setAdapter(callRecordAdapter);
        }
    }


    @Override
    protected void initData() {
        getData(true);
    }

    private void getData(final boolean isRefresh) {
        HttpApiOOOLive.getCallRecordList(HttpClient.getUid(), pageIndex, 10, new HttpApiCallBackArr<CallRecordDto>() {
            @Override
            public void onHttpRet(int code, String msg, List<CallRecordDto> retModel) {
                if (code == 1 && null != retModel) {
                    if (isRefresh) {
                        refreshLayout.finishRefresh();
                        callRecordAdapter.setRefreshData(retModel);
                    } else {
                        refreshLayout.finishLoadMore();
                        callRecordAdapter.setLoadData(retModel);
                    }
                } else {
                    if (isRefresh) {
                        refreshLayout.finishRefresh();
                    } else {
                        refreshLayout.finishLoadMore();
                    }
                }
            }
        });
    }
}
