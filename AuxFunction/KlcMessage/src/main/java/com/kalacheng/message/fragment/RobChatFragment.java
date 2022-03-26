package com.kalacheng.message.fragment;

import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busooolive.httpApi.HttpApiOOOCall;
import com.kalacheng.libuser.model.ApiPushChat;
import com.kalacheng.message.R;
import com.kalacheng.message.adapter.RobChatAdapter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.utils.L;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class RobChatFragment extends BaseFragment {

    SmartRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    RobChatAdapter adapter;

    Handler handler;
    Runnable runnable;
    long time = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rob_chat;
    }

    @Override
    protected void initView() {
        refreshLayout = mParentView.findViewById(R.id.smartRefresh);
        recyclerView = mParentView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        adapter = new RobChatAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        SlideInLeftAnimator animator = new SlideInLeftAnimator();
        animator.setAddDuration(500);
        animator.setRemoveDuration(100);
        recyclerView.setItemAnimator(animator);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                getData();
            }
        });


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (refreshLayout.getState() == RefreshState.None) {
                    getData();
                }
            }
        };
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResumeFragment() {
        super.onResumeFragment();
        L.e("onResumeFragment");
        getData();
    }

    @Override
    public void onPauseFragment() {
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
        if (adapter != null) {
            adapter.removed();
        }
        super.onPauseFragment();
    }

    private void getData() {
        HttpApiOOOCall.getPushChatList(0, 0, new HttpApiCallBackArr<ApiPushChat>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiPushChat> retModel) {
                handler.postDelayed(runnable, 5000);
                refreshLayout.finishRefresh();
                if (code == 1 && retModel != null) {
                    adapter.addData(retModel);
                    if (retModel.size() > 0) {
                        mParentView.findViewById(R.id.tvNoData).setVisibility(View.GONE);
                    } else {
                        adapter.removed();
                        mParentView.findViewById(R.id.tvNoData).setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }


}
