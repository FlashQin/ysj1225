package com.kalacheng.money.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.busfinance.httpApi.HttpApiAPPFinance;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libuser.model.ApiAppChargeRulesResp;
import com.kalacheng.money.R;
import com.kalacheng.money.adapter.CoinAdapter;
import com.kalacheng.money.databinding.ActivityCoinBinding;
import com.kalacheng.money.dialog.FirstRechargeDialogFragment;
import com.kalacheng.commonview.dialog.PayMethodSelectDialog;
import com.kalacheng.money.viewmodel.MyCoinViewModel;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.view.ItemDecoration;

/**
 * 充值中心
 */
@Route(path = ARouterPath.MyCoinActivity)
public class MyCoinActivity extends BaseMVVMActivity<ActivityCoinBinding, MyCoinViewModel> implements View.OnClickListener {
    private CoinAdapter mAdapter;
    private ApiAppChargeRulesResp apiAppChargeRulesResp;
    private boolean isSuccess;
    /**
     * 是否首次充值1是2否
     */
    private int isFirstRecharge = 2;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_coin;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void initData() {
        setTitle("充值中心");
        TextView tvOther = findViewById(R.id.tvOther);
        tvOther.setVisibility(View.VISIBLE);
        tvOther.setText("消费记录");
        tvOther.setOnClickListener(this);
        binding.tvCoinConfirm.setOnClickListener(this);
        ((TextView) findViewById(R.id.tvCoinUnit)).setText("我的" + SpUtil.getInstance().getCoinUnit());

        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setNestedScrollingEnabled(false);//禁止滑动,解决ScrollView包裹的时候滑动的过程中卡顿
        mAdapter = new CoinAdapter();
        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.addItemDecoration(new ItemDecoration(this, 0x00000000, 15, 20));

        getRulesList();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyAccount();
    }

    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.tvOther) {//消费记录
            ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                    + "/api/h5/consumRecord?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken() + "&pageSize=10&pageIndex=0&type=1").navigation();
        } else if (view.getId() == R.id.tvCoinConfirm) {
            if (mAdapter.getSelectPosition() == -1) {
                ToastUtil.show("请选择充值金额");
            } else {
                if (isFirstRecharge == 1) {
                    FirstRechargeDialogFragment firstRechargeDialogFragment = new FirstRechargeDialogFragment();
                    firstRechargeDialogFragment.setOnFirstRechargeCancelListener(new FirstRechargeDialogFragment.OnFirstRechargeCancelListener() {
                        @Override
                        public void onCancel() {
                            goPayMethodSelectDialog();
                        }
                    });
                    firstRechargeDialogFragment.show(getSupportFragmentManager(), "FirstRechargeDialogFragment");
                } else {
                    goPayMethodSelectDialog();
                }
            }
        }
    }

    /**
     * 进入支付方式选择弹框
     */
    private void goPayMethodSelectDialog() {
        PayMethodSelectDialog fragment = new PayMethodSelectDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("AppUsersCharge", mAdapter.getData().get(mAdapter.getSelectPosition()));
        bundle.putParcelable("ApiAppChargeRulesResp", apiAppChargeRulesResp);
        bundle.putLong("orderId", mAdapter.getData().get(mAdapter.getSelectPosition()).id);
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(), "PayMethodSelectDialog");
        fragment.setListener(new PayMethodSelectDialog.PayMethodListener() {
            @Override
            public void onSuccess() {
                isSuccess = true;
                // 7101 视频观看 余额不足 充值成功返回去继续观看
                Intent intent = new Intent();
                intent.putExtra("isSuccess", isSuccess);
                setResult(7101, intent);
            }

            @Override
            public void onFailed() {
                isSuccess = false;
            }
        });
    }

    /**
     * 我的账户
     */
    private void getMyAccount() {
        HttpApiAppUser.getMyAccount(new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && retModel != null) {
                    binding.tvCoin.setText(DecimalFormatUtils.isIntegerDouble(retModel.coin));
                }
            }
        });
    }

    /**
     * 充值规则
     */
    private void getRulesList() {
        HttpApiAPPFinance.rules_list(new HttpApiCallBack<ApiAppChargeRulesResp>() {
            @Override
            public void onHttpRet(int code, String msg, ApiAppChargeRulesResp retModel) {
                if (code == 1 && retModel != null && retModel.appChargeRules != null) {
                    isFirstRecharge = retModel.isFirstRecharge;
                    apiAppChargeRulesResp = retModel;
                    mAdapter.setData(retModel.appChargeRules);
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

}
