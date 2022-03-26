package com.kalacheng.commonview.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.buscommon.model.GuardUserDto;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.adapter.GuardAdapter;
import com.kalacheng.commonview.dialog.GuardDialogFragment;

import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.view.ItemDecoration;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * 我（或TA）的守护
 */
@Route(path = ARouterPath.GuardActivity)
public class GuardActivity extends BaseActivity {
    @Autowired(name = ARouterValueNameConfig.GUARD_TITLE)
    public String title;
    @Autowired(name = ARouterValueNameConfig.GUARD_TYPE)
    public int type;//1 被守护； 其他为守护别人
    @Autowired(name = ARouterValueNameConfig.GUARD_ID)
    public long id;

    private int pageIndex;
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private LinearLayout llRecyclerView;
    private TextView tvNoData;
    private GuardAdapter guardAdapter;

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
    }

    private void initView() {
        setTitle(title);
        recyclerView = findViewById(R.id.recyclerView);
        llRecyclerView = findViewById(R.id.ll_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
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
        guardAdapter = new GuardAdapter();
        recyclerView.setAdapter(guardAdapter);
        recyclerView.addItemDecoration(new ItemDecoration(this, 0x00000000, 6, 7));
        recyclerView.setPadding(DpUtil.dp2px(10), DpUtil.dp2px(6), DpUtil.dp2px(10), 0);

        guardAdapter.setGuardCallBack(new GuardAdapter.GuardListener() {
            @Override
            public void onClick(GuardUserDto guardUserDto) {
                GuardDialogFragment guardDialogFragment = new GuardDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                bundle.putLong("anchorId", guardUserDto.anchorId);
                bundle.putString("anchorAvatar", guardUserDto.anchorIdImg);
                bundle.putString("anchorName", "");
                guardDialogFragment.setArguments(bundle);
                guardDialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "GuardDialogFragment");
            }
        });

    }

    private void getData(final boolean isRefresh) {
        if (type == 1) {
            HttpApiAppUser.getGuardMyList(pageIndex, 10, id, new HttpApiCallBackArr<GuardUserDto>() {
                @Override
                public void onHttpRet(int code, String msg, List<GuardUserDto> retModel) {
                    if (code == 1 && null != retModel) {
                        if (isRefresh) {
                            refreshLayout.finishRefresh();
                            if (retModel != null && !retModel.isEmpty()) {
                                tvNoData.setVisibility(View.GONE);
                                llRecyclerView.setVisibility(View.VISIBLE);
                                guardAdapter.setRefreshData(retModel);
                            } else {
                                tvNoData.setVisibility(View.VISIBLE);
                                llRecyclerView.setVisibility(View.GONE);
                            }
                        } else {
                            refreshLayout.finishLoadMore();
                            guardAdapter.setLoadData(retModel);
                        }
                    }
                }
            });
        } else {
            HttpApiAppUser.getMyGuardList(pageIndex, 10, id, new HttpApiCallBackArr<GuardUserDto>() {
                @Override
                public void onHttpRet(int code, String msg, List<GuardUserDto> retModel) {
                    if (code == 1 && null != retModel) {
                        if (isRefresh) {
                            refreshLayout.finishRefresh();
                            if (retModel != null && !retModel.isEmpty()) {
                                tvNoData.setVisibility(View.GONE);
                                llRecyclerView.setVisibility(View.VISIBLE);
                                guardAdapter.setRefreshData(retModel);
                            } else {
                                tvNoData.setVisibility(View.VISIBLE);
                                llRecyclerView.setVisibility(View.GONE);
                            }
                        } else {
                            refreshLayout.finishLoadMore();
                            guardAdapter.setLoadData(retModel);
                        }
                    }
                }
            });
        }
    }

    private void showDialog(long id, String avatar, String anchorName){
        GuardDialogFragment guardDialogFragment = new GuardDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 0);
        bundle.putLong("anchorId", id);
        bundle.putString("anchorAvatar", avatar);
        bundle.putString("anchorName", anchorName);
        guardDialogFragment.setArguments(bundle);
        guardDialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "GuardDialogFragment");
    }

}
