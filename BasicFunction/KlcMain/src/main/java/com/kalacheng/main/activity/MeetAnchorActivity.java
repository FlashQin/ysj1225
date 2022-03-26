package com.kalacheng.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.busooolive.httpApi.HttpApiOOOLive;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libuser.model.OOOMeetAnchor;
import com.kalacheng.main.R;
import com.kalacheng.main.adapter.MeetAnchorAdpater;
import com.kalacheng.main.adapter.MissCallAdpater;
import com.kalacheng.util.utils.ProcessResultUtil;
import com.kalacheng.util.view.SpacesItemDecoration;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;

@Route(path = ARouterPath.MeetAnchorActivity)
public class MeetAnchorActivity extends BaseActivity {
    RecyclerView recyclerViewMatch, recyclerViewInvite, recyclerViewMiss;
    TextView tvMissCall, tvInviteCall, tvMatch;
    ProcessResultUtil mProcessResultUtil;
    private RefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anchor_meet);
        initView();
        initData();
    }

    private void initView() {
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerViewMatch = findViewById(R.id.recyclerView_match);
        recyclerViewInvite = findViewById(R.id.recyclerView_invite_call);
        recyclerViewMiss = findViewById(R.id.recyclerView_miss_call);
        tvMissCall = findViewById(R.id.tv_miss_call);
        tvMatch = findViewById(R.id.tv_match);
        tvInviteCall = findViewById(R.id.tv_invite_call);
        recyclerViewMatch.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(SpacesItemDecoration.RIGHT_DECORATION, 40);
        recyclerViewMatch.addItemDecoration(new SpacesItemDecoration(stringIntegerHashMap));

        recyclerViewInvite.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerViewInvite.addItemDecoration(new SpacesItemDecoration(stringIntegerHashMap));
        recyclerViewMiss.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        HashMap<String, Integer> stringIntegerHashMap1 = new HashMap<>();
        stringIntegerHashMap1.put(SpacesItemDecoration.TOP_DECORATION, 25);
        recyclerViewMiss.addItemDecoration(new SpacesItemDecoration(stringIntegerHashMap1));
        setTitle("1v1通话");

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMeetAnchor();
            }
        });
    }

    private void initData() {
        mProcessResultUtil = new ProcessResultUtil(this);
        getMeetAnchor();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    private void getMeetAnchor() {
        HttpApiOOOLive.meetAnchor(new HttpApiCallBack<OOOMeetAnchor>() {
            @Override
            public void onHttpRet(int code, String msg, OOOMeetAnchor retModel) {
                refreshLayout.finishRefresh();
                if (code == 1 && null != retModel) {
                    if (null != retModel.matchingUserList && !retModel.matchingUserList.isEmpty()) {
                        tvMatch.setVisibility(View.GONE);
                        recyclerViewMatch.setVisibility(View.VISIBLE);
                        MeetAnchorAdpater meetAnchorAdpater = new MeetAnchorAdpater(retModel.matchingUserList, true, mProcessResultUtil);
                        recyclerViewMatch.setAdapter(meetAnchorAdpater);
                    } else {
                        tvMatch.setVisibility(View.VISIBLE);
                        recyclerViewMatch.setVisibility(View.GONE);
                    }
                    if (null != retModel.reqUserList && !retModel.reqUserList.isEmpty()) {
                        tvInviteCall.setVisibility(View.GONE);
                        recyclerViewInvite.setVisibility(View.VISIBLE);
                        MeetAnchorAdpater meetAnchorAdpater = new MeetAnchorAdpater(retModel.reqUserList, false, mProcessResultUtil);
                        recyclerViewInvite.setAdapter(meetAnchorAdpater);
                    } else {
                        tvInviteCall.setVisibility(View.VISIBLE);
                        recyclerViewInvite.setVisibility(View.GONE);
                    }
                    if (null != retModel.noAnswerUserList && !retModel.noAnswerUserList.isEmpty()) {
                        tvMissCall.setVisibility(View.GONE);
                        recyclerViewMiss.setVisibility(View.VISIBLE);
                        MissCallAdpater missCallAdpater = new MissCallAdpater(MeetAnchorActivity.this, retModel.noAnswerUserList, mProcessResultUtil);
                        recyclerViewMiss.setAdapter(missCallAdpater);
                    } else {
                        tvMissCall.setVisibility(View.VISIBLE);
                        recyclerViewMiss.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}
