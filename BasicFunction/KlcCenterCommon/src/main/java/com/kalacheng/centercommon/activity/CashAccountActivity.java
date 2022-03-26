package com.kalacheng.centercommon.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.adapter.CashAccountAdapter;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.busfinance.httpApi.HttpApiAPPFinance;
import com.kalacheng.libuser.model.AppUsersCashAccount;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.util.utils.DialogUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 提现账户
 */
@Route(path = ARouterPath.CashAccountActivity)
public class CashAccountActivity extends BaseActivity implements View.OnClickListener {
    @Autowired(name = ARouterValueNameConfig.AppUsersCashAccount)
    AppUsersCashAccount apiMyEarnings;

    RecyclerView recyclerView;
    RefreshLayout refreshLayout;
    int pageIndex;
    CashAccountAdapter accountAdapter;
    TextView tvNoData, tvOther;
    LinearLayout llRecyclerView;
    private List<AppUsersCashAccount> appUsersCashAccounts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_account);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    private void initView() {
        setTitle("提现账户");
        findViewById(R.id.btn_next).setOnClickListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        tvNoData = findViewById(R.id.tv_no_data);
        llRecyclerView = (LinearLayout) findViewById(R.id.ll_recyclerView);

        if (null == accountAdapter) {
            accountAdapter = new CashAccountAdapter(appUsersCashAccounts);
            recyclerView.setAdapter(accountAdapter);
            accountAdapter.setOnItemClickListener(new OnBeanCallback<AppUsersCashAccount>() {
                @Override
                public void onClick(AppUsersCashAccount bean) {
                    Intent intent = new Intent();
                    intent.putExtra(ARouterValueNameConfig.AppUsersCashAccount, bean);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            accountAdapter.setOnItemLongClickListener(new OnBeanCallback<AppUsersCashAccount>() {
                @Override
                public void onClick(final AppUsersCashAccount bean) {
                    DialogUtil.showStringArrayDialog(mContext, new Integer[]{
                            R.string.edit, R.string.delete}, new DialogUtil.StringArrayDialogCallback() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onItemClick(String text, int tag) {
                            if (tag == R.string.edit) {
                                ARouter.getInstance().build(ARouterPath.AddCashAccountActivity).withParcelable(ARouterValueNameConfig.AddCashAccountActivity, bean).navigation();
                            } else if (tag == R.string.delete) {
                                DialogUtil.showAccountDeteleDialog(mContext, new DialogUtil.CashAccountDeleteCallBack() {
                                    @Override
                                    public void onConfirmClick() {
                                        HttpApiAPPFinance.withdraw_account_del(bean.id, new HttpApiCallBack<HttpNone>() {
                                            @Override
                                            public void onHttpRet(int code, String msg, HttpNone retModel) {
                                                if (code == 1) {
                                                    getData(true);
                                                    ToastUtil.show("删除成功");
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);
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

    protected void initData() {
        getData(true);
    }

    private void getData(final boolean isfresh) {
        HttpApiAPPFinance.withdraw_account(new HttpApiCallBackArr<AppUsersCashAccount>() {
            @Override
            public void onHttpRet(int code, String msg, List<AppUsersCashAccount> retModel) {
                if (code == 1) {
                    if (isfresh) {
                        refreshLayout.finishRefresh();
                        if (retModel != null && !retModel.isEmpty()) {
                            tvNoData.setVisibility(View.GONE);
                            llRecyclerView.setVisibility(View.VISIBLE);
                            appUsersCashAccounts.clear();
                            appUsersCashAccounts.addAll(retModel);
                            if (null != apiMyEarnings && appUsersCashAccounts.contains(apiMyEarnings)) {
                                apiMyEarnings.isDefault = 1;
                            }
                            accountAdapter.setRefreshData(appUsersCashAccounts);
                        } else {
                            tvNoData.setVisibility(View.VISIBLE);
                            llRecyclerView.setVisibility(View.GONE);
                        }
                    } else {
                        refreshLayout.finishLoadMore();
                        accountAdapter.setLoadData(retModel);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_next) {
            ARouter.getInstance().build(ARouterPath.AddCashAccountActivity).navigation();
        }
    }
}
