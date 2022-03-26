package com.kalacheng.commonview.activity;

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
import com.kalacheng.util.listener.OnItemClickCallback;
import com.kalacheng.buscommon.AppHomeHallDTO;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.adapter.TALiveAdapter;
import com.kalacheng.commonview.utils.LookRoomUtlis;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libuser.model.LiveRoomDTO;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.view.ItemDecoration;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * TA的直播
 */
@Route(path = ARouterPath.TALiveActivity)
public class TALiveActivity extends BaseActivity {
    @Autowired(name = ARouterValueNameConfig.ANCHORID)
    public long AnchorID;

    int pageIndex;
    RecyclerView recyclerView;
    RefreshLayout refreshLayout;
    LinearLayout llRecyclerView;
    TextView tvNoData;
    TALiveAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_recycleview);
        initView();
        initData();
    }

    private void initData() {
        getData(true);
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setPadding(DpUtil.dp2px(10), DpUtil.dp2px(6), DpUtil.dp2px(10), 0);
        llRecyclerView = findViewById(R.id.ll_recyclerView);
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
        tvNoData = findViewById(R.id.tv_no_data);
        if (null == adapter) {
            adapter = new TALiveAdapter();
            adapter.setOnItemClickCallback(new OnItemClickCallback<LiveRoomDTO>() {
                @Override
                public void onClick(View view, LiveRoomDTO item) {
                    if (item.status == 1 && AnchorID != HttpClient.getUid()) {
                        AppHomeHallDTO bean = new AppHomeHallDTO();
                        bean.liveType = 1;
                        bean.roomId = item.roomId;
                        LookRoomUtlis.getInstance().getData(bean, mContext);
                    }
                }
            });
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new ItemDecoration(this, 0x00000000, 10, 10));
        }
        setTitle("TA的直播");
    }

    private void getData(final boolean isRefresh) {
        HttpApiAppUser.getLiveList(pageIndex, 30, AnchorID, new HttpApiCallBackArr<LiveRoomDTO>() {
            @Override
            public void onHttpRet(int code, String msg, List<LiveRoomDTO> retModel) {
                if (code == 1) {
                    if (isRefresh) {
                        refreshLayout.finishRefresh();
                        if (retModel != null && retModel.size() > 0) {
                            tvNoData.setVisibility(View.GONE);
                            llRecyclerView.setVisibility(View.VISIBLE);
                            adapter.setRefreshData(retModel);
                        } else {
                            tvNoData.setVisibility(View.VISIBLE);
                            llRecyclerView.setVisibility(View.GONE);
                        }
                    } else {
                        refreshLayout.finishLoadMore();
                        adapter.setLoadData(retModel);
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
