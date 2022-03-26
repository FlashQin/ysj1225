package com.kalacheng.centercommon.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.buscommon.model.AppUserIncomeRankingDto;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.adapter.InvitationSortAdapter;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackPageArr;
import com.kalacheng.base.http.PageInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * 收入排行
 */
@Route(path = ARouterPath.InvitationRankActivity)
public class InvitationRankActivity extends BaseActivity implements OnLoadMoreListener, OnRefreshListener {
    RecyclerView recyclerView;
    TextView tv_sort_index;
    ImageView ivAvatar;
    TextView tvPeopleNum;
    TextView tvTotalAmount;
    SmartRefreshLayout refreshLayout;
    private int page;
    private InvitationSortAdapter invitationSortAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_rank);
        initView();
        incomeRanking();
        myIncomeRanking();
    }

    private void initView() {
        setTitle("收入排行榜");
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        ClassicsFooter footer = new ClassicsFooter(this);
        footer.setAccentColor(Color.parseColor("#FFFFFF"));
        refreshLayout.setRefreshFooter(footer);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setPrimaryColors(Color.parseColor("#00000000"));
        recyclerView = findViewById(R.id.invitation_rank_rv);
        tv_sort_index = findViewById(R.id.tv_sort_index);
        ivAvatar = findViewById(R.id.iv_avatar);
        tvPeopleNum = findViewById(R.id.tvPeopleNum);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        View main = findViewById(R.id.main);
        main.setPadding(DpUtil.dp2px(21), 0, DpUtil.dp2px(15), 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        invitationSortAdapter = new InvitationSortAdapter();
        recyclerView.setAdapter(invitationSortAdapter);
    }

    /**
     * 排行榜
     */
    private void incomeRanking() {
        HttpApiAppUser.incomeRanking(page, HttpConstants.PAGE_SIZE, new HttpApiCallBackPageArr<AppUserIncomeRankingDto>() {
            @Override
            public void onHttpRet(int code, String msg, PageInfo pageInfo, List<AppUserIncomeRankingDto> retModel) {
                if (code == 1 && retModel != null) {
                    if (page == 0) {
                        refreshLayout.finishRefresh();
                        invitationSortAdapter.setData(retModel);
                    } else {
                        refreshLayout.finishLoadMore();
                        invitationSortAdapter.loadData(retModel);
                    }
                } else {
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                }
            }
        });
    }

    /**
     * 个人排行
     */
    private void myIncomeRanking() {
        HttpApiAppUser.myIncomeRanking(new HttpApiCallBack<AppUserIncomeRankingDto>() {
            @Override
            public void onHttpRet(int code, String msg, AppUserIncomeRankingDto retModel) {
                if (code == 1 && retModel != null) {
                    if (retModel.isRanking > 99 || retModel.isRanking == 0) {
                        tv_sort_index.setText("99+");
                    } else {
                        tv_sort_index.setText((int) retModel.isRanking + "");
                    }
                    ImageLoader.display(retModel.avatar, ivAvatar, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                    tvPeopleNum.setText("已邀请" + retModel.numberOfInvitations + "人");
                    tvTotalAmount.setText("获得" + String.format("%.2f", retModel.totalAmount) + "元");
                }
            }
        });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        incomeRanking();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 0;
        incomeRanking();
        myIncomeRanking();
    }
}
