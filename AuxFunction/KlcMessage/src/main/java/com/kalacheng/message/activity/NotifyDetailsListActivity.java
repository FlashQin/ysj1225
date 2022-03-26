package com.kalacheng.message.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.httpApi.HttpApiChatRoom;
import com.kalacheng.libuser.model.AppSystemNoticeUser;
import com.kalacheng.message.R;
import com.kalacheng.message.adapter.NotifyDetailsAdapter;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.http.HttpApiCallBackPageArr;
import com.kalacheng.base.http.PageInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

public class NotifyDetailsListActivity extends BaseActivity implements OnLoadMoreListener, OnRefreshListener {

    int page = 0;
    SmartRefreshLayout smartRefresh;
    RecyclerView recyclerView;
    NotifyDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarWhite(false);
        setContentView(R.layout.activity_notify_details_list);



        findViewById(R.id.backIv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((TextView)findViewById(R.id.titleNameTv)).setText(getIntent().getStringExtra("title"));

        smartRefresh = findViewById(R.id.smartRefresh);
        smartRefresh.setOnRefreshListener(this);
        smartRefresh.setOnLoadMoreListener(this);

        adapter = new NotifyDetailsAdapter();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData() {
        long id = getIntent().getLongExtra("id",-1);
        HttpApiChatRoom.getAppSystemNoticeUserList((int) id,page, 20, new HttpApiCallBackPageArr<AppSystemNoticeUser>() {
            @Override
            public void onHttpRet(int code, String msg, PageInfo pageInfo, List<AppSystemNoticeUser> retModel) {

                if (code == 1 && retModel != null) {
                    if (page == 0){
                        adapter.refreshData(retModel);
                        smartRefresh.finishRefresh();
                    }else {
                        adapter.loadData(retModel);
                        smartRefresh.finishLoadMore();
                    }
                }else {
                    if (page == 0){
                        smartRefresh.finishRefresh();
                    }else {
                        smartRefresh.finishLoadMore();
                    }
                }
            }
        });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        getData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 0;
        getData();
    }
}
