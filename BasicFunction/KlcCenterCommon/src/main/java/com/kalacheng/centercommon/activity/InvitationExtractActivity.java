package com.kalacheng.centercommon.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busfinance.httpApi.HttpApiSupport;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.centercommon.R;
import com.kalacheng.commonview.dialog.InvitationExtractAccountTypeDialog;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.busfinance.httpApi.HttpApiAPPFinance;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;

import com.kalacheng.libuser.model.ApiAppChargeRulesResp;
import com.kalacheng.libuser.model.InviteDto;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.money.adapter.CoinAdapter;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.TextViewUtil;
import com.kalacheng.util.view.ItemDecoration;

/**
 * 佣金提现
 */
@Route(path = ARouterPath.InvitationExtractActivity)
public class InvitationExtractActivity extends BaseActivity implements View.OnClickListener {
    private InviteDto inviteDto;
    private boolean mGetRules;//是否获取过充值规则

    private TextView tvAmount;
    private TextView tvExtract;
    private TextView tvExchange;
    private LinearLayout layoutExtract;
    private LinearLayout layoutExchange;

    private EditText etExtractMoney;
    private TextView tvExtractMoneyAll;
    private TextView tvAccountType;
    private EditText etAccountName;
    private TextView tvExtractConfirm;

    private RecyclerView recyclerView;
    private CoinAdapter mAdapter;
    private TextView tvExchangeConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_extract);
        initView();
        getInviteCodeInfo();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    private void initView() {
        setTitle("提现");
        TextView tvOther = findViewById(R.id.tvOther);
        tvOther.setVisibility(View.VISIBLE);
        tvOther.setText("提现记录");
        tvOther.setOnClickListener(this);

        tvAmount = findViewById(R.id.tvAmount);
        tvExtract = findViewById(R.id.tvExtract);
        tvExchange = findViewById(R.id.tvExchange);
        layoutExtract = findViewById(R.id.layoutExtract);
        layoutExchange = findViewById(R.id.layoutExchange);
        tvExtractConfirm = findViewById(R.id.tvExtractConfirm);
        tvExchangeConfirm = findViewById(R.id.tvExchangeConfirm);
        etExtractMoney = findViewById(R.id.etExtractMoney);
        etAccountName = findViewById(R.id.etAccountName);
        tvExtractMoneyAll = findViewById(R.id.tvExtractMoneyAll);
        tvAccountType = findViewById(R.id.tvAccountType);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);//禁止滑动,解决ScrollView包裹的时候滑动的过程中卡顿
        mAdapter = new CoinAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new ItemDecoration(this, 0x00000000, 15, 10));


        tvExchange.setText("兑换" + SpUtil.getInstance().getCoinUnit());
        tvExchangeConfirm.setText("兑换" + SpUtil.getInstance().getCoinUnit());

        tvExtract.setSelected(true);
        tvExtract.setOnClickListener(this);
        tvExchange.setOnClickListener(this);
        tvExtractConfirm.setOnClickListener(this);
        tvExchangeConfirm.setOnClickListener(this);
        tvExtractMoneyAll.setOnClickListener(this);
        tvAccountType.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.tvOther) {//提现记录
            ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                    + "/api/h5/userMoneyDetails?type=2&_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken() + "&pageSize=10&pageIndex=0&anchorId=" + HttpClient.getUid()).navigation();
        } else if (view.getId() == R.id.tvExtract) {//提取现金切换
            if (!tvExtract.isSelected()) {
                tvExtract.setSelected(true);
                tvExchange.setSelected(false);
                layoutExtract.setVisibility(View.VISIBLE);
                layoutExchange.setVisibility(View.GONE);
            }
        } else if (view.getId() == R.id.tvExchange) {//兑换金币切换
            if (!tvExchange.isSelected()) {
                tvExtract.setSelected(false);
                tvExchange.setSelected(true);
                layoutExtract.setVisibility(View.GONE);
                layoutExchange.setVisibility(View.VISIBLE);
                if (!mGetRules) {
                    mGetRules = true;
                    getRulesList();
                }
            }
        } else if (view.getId() == R.id.tvExtractMoneyAll) {//全部提现
            if (inviteDto != null) {
                etExtractMoney.setText((int) inviteDto.amount + "");
                TextViewUtil.cursorAtEditTextEnd(etExtractMoney);
            }
        } else if (view.getId() == R.id.tvAccountType) {//账号类型
            InvitationExtractAccountTypeDialog accountTypeDialog = new InvitationExtractAccountTypeDialog();
            accountTypeDialog.setAccountTypeSelectListener(new InvitationExtractAccountTypeDialog.AccountTypeSelectListener() {
                @Override
                public void onItemSelect(String type) {
                    tvAccountType.setText(type);
                }
            });
            accountTypeDialog.show(getSupportFragmentManager(), "InvitationExtractAccountTypeDialog");
        } else if (view.getId() == R.id.tvExtractConfirm) {//确认提现
            if (inviteDto == null || inviteDto.amount <= 0) {
                ToastUtil.show("暂无可提取佣金");
            } else if (TextUtils.isEmpty(etExtractMoney.getText().toString().trim())) {
                ToastUtil.show("请输入提取金额");
            } else if (Double.parseDouble(etExtractMoney.getText().toString().trim()) > inviteDto.amount) {
                ToastUtil.show("佣金不足");
            } else if (TextUtils.isEmpty(etAccountName.getText().toString().trim())) {
                ToastUtil.show("请输入账号");
            } else {
                HttpApiAPPFinance.withdraw_account_apply(-1, etAccountName.getText().toString().trim(), "支付宝".equals(tvAccountType.getText().toString().trim()) ? 1 : 2,
                        Integer.parseInt(etExtractMoney.getText().toString().trim()), 2, new HttpApiCallBack<HttpNone>() {
                            @Override
                            public void onHttpRet(int code, String msg, HttpNone retModel) {
                                if (code == 1) {
                                    finish();
                                }
                                ToastUtil.show(msg);
                            }
                        });
            }
        } else if (view.getId() == R.id.tvExchangeConfirm) {//确认兑换
            if (inviteDto == null || inviteDto.amount <= 0) {
                ToastUtil.show("暂无可兑换佣金");
            } else if (mAdapter.getSelectPosition() == -1) {
                ToastUtil.show("请选择兑换规则");
            } else if (mAdapter.getData().get(mAdapter.getSelectPosition()).discountMoney > inviteDto.amount) {
                ToastUtil.show("佣金不足");
            } else {
                HttpApiAppUser.exchangeCoin("android", mAdapter.getData().get(mAdapter.getSelectPosition()).id, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            finish();
                            ToastUtil.show(msg);
                        }
                    }
                });
            }
        }
    }

    /**
     * 获取邀请码信息
     */
    private void getInviteCodeInfo() {
        HttpApiSupport.getInviteCodeInfo(new HttpApiCallBack<InviteDto>() {
            @Override
            public void onHttpRet(int code, String msg, InviteDto retModel) {
                if (code == 1 && null != retModel) {
                    inviteDto = retModel;
                    tvAmount.setText(retModel.amount + "");
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
                    mAdapter.setData(retModel.appChargeRules);
                }
            }
        });
    }
}
