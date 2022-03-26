package com.kalacheng.message.fragment;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.httpApi.HttpApiChatRoom;
import com.kalacheng.libuser.model.ApiNoRead;
import com.kalacheng.libuser.model.AppSystemNotice;
import com.kalacheng.message.R;
import com.kalacheng.message.activity.NotifyDetailsListActivity;
import com.kalacheng.message.adapter.NotifyAdapter;
import com.kalacheng.commonview.jguangIm.UnReadCountEvent;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.utils.L;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackPageArr;
import com.kalacheng.base.http.PageInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class NotifyListFragment extends BaseFragment implements NotifyAdapter.OnClick, NotifyAdapter.ReadAndDelete, OnRefreshListener {

    SmartRefreshLayout smartRefresh;
    RecyclerView recyclerView;
    NotifyAdapter adapter;

    public NotifyListFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notify;
    }

    @Override
    protected void initView() {
        smartRefresh = mParentView.findViewById(R.id.smartRefresh);
        smartRefresh.setOnRefreshListener(this);
        smartRefresh.setEnableLoadMore(false);

        adapter = new NotifyAdapter();
        adapter.setOnClick(this);
        adapter.setReadAndDelete(this);
        recyclerView = mParentView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        HttpApiChatRoom.getAppSystemNoticeList(new HttpApiCallBackPageArr<AppSystemNotice>() {
            @Override
            public void onHttpRet(int code, String msg, PageInfo pageInfo, List<AppSystemNotice> retModel) {
                if (code == 1) {
                    adapter.setList(retModel);
                    if (null == retModel || retModel.isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
                        mParentView.findViewById(R.id.tv_no_data).setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        mParentView.findViewById(R.id.tv_no_data).setVisibility(View.GONE);
                    }
                }
                smartRefresh.finishRefresh();
                getUnReadNotifyCount();
            }
        });
    }

    @Override
    public void onClick(AppSystemNotice systemNotice) {
        startActivityForResult(new Intent(getContext(), NotifyDetailsListActivity.class)
                .putExtra("title", systemNotice.showTitle)
                .putExtra("id", systemNotice.id), 112);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e("requestCode = " + requestCode + "resultCode = " + resultCode);
        if (requestCode == 112) {
            getUnReadNotifyCount();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getData();
    }

    private void getUnReadNotifyCount() {
        HttpApiChatRoom.getAppSystemNoRead(new HttpApiCallBack<ApiNoRead>() {
            @Override
            public void onHttpRet(int code, String msg, ApiNoRead retModel) {
                if (code == 1) {
                    EventBus.getDefault().post(new UnReadCountEvent(retModel.totalNoRead, retModel.systemNoRead, retModel.videoNoRead, retModel.shortVideoNoRead, retModel.officialNewsNoRead));
                }
            }
        });
    }

    @Override
    public void onRead() {
        getData();
    }

    @Override
    public void onDelte() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
