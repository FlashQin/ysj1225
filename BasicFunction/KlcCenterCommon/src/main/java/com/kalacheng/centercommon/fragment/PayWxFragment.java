package com.kalacheng.centercommon.fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.adapter.PaySettingAdapter;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.libuser.model.ChangeDto;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.base.http.HttpClient;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

public class PayWxFragment extends BaseFragment {

    private RecyclerView recyclerView;
    RefreshLayout refreshLayout;
    int pageIndex = 0;
    PaySettingAdapter adapter;

    public PayWxFragment() {
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
    }

    @Override
    protected void initData() {
        getData(true);
    }

    private void getData(final boolean isfresh) {
        HttpApiAppUser.getContactRecord(30002, pageIndex, HttpConstants.PAGE_SIZE, HttpClient.getUid(), new HttpApiCallBackArr<ChangeDto>() {
            @Override
            public void onHttpRet(int code, String msg, List<ChangeDto> retModel) {
                if (code == 1 && retModel != null) {
                    if (isfresh) {
                        refreshLayout.finishRefresh();
                        if (adapter == null) {
                            adapter = new PaySettingAdapter(retModel);
                            recyclerView.setAdapter(adapter);
                        } else {
                            adapter.setRefreshData(retModel);
                        }
                    } else {
                        refreshLayout.finishLoadMore();
                        adapter.setLoadData(retModel);
                    }
                }
            }
        });
    }
}
