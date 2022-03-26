package com.kalacheng.centercommon.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.centercommon.R;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.busfinance.httpApi.HttpApiAPPFinance;
import com.kalacheng.libuser.model.AppUsersCashAccount;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBack;

/**
 * 添加提现账户
 */
@Route(path = ARouterPath.AddCashAccountActivity)
public class AddCashAccountActivity extends BaseActivity implements View.OnClickListener {
    @Autowired(name = ARouterValueNameConfig.AddCashAccountActivity)
    public AppUsersCashAccount appUsersCashAccount;
    TextView tvAlipay, tvWx, tvBank, tvAccount, tvName;
    EditText etBankName, etBankName2, etAccount, etName;
    LinearLayout llBank, llName, llPayType;
    View lineName;
    int type = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cash);
        initView();
        initData();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    private void initView() {
        setTitle("添加提现账户");
        tvAccount = findViewById(R.id.tv_account);
        tvAlipay = findViewById(R.id.tv_alipay);
        tvWx = findViewById(R.id.tv_wx);
        tvBank = findViewById(R.id.tv_bank);
        tvName = findViewById(R.id.tv_name);
        llBank = findViewById(R.id.ll_bank);
        llName = findViewById(R.id.ll_name);
        lineName = findViewById(R.id.line_name);
        etBankName = findViewById(R.id.et_bank_name);
        etBankName2 = findViewById(R.id.et_bank_name2);
        etAccount = findViewById(R.id.et_account);
        etName = findViewById(R.id.et_name);
        llPayType = findViewById(R.id.ll_pay_type);
        findViewById(R.id.btn_next).setOnClickListener(this);
        tvAlipay.setOnClickListener(this);
        tvWx.setOnClickListener(this);
        tvBank.setOnClickListener(this);
        if (appUsersCashAccount == null) {
            llPayType.setVisibility(View.VISIBLE);
            tvAlipay.performClick();
        } else {
            llPayType.setVisibility(View.GONE);
            if (appUsersCashAccount.type == 1) {
                tvAlipay.performClick();
                llBank.setVisibility(View.GONE);
                llName.setVisibility(View.VISIBLE);
                lineName.setVisibility(View.VISIBLE);
                tvAccount.setText("支付宝账号");
                etAccount.setHint("请输入支付宝账号");
                tvName.setText("支付宝账号姓名");
                etName.setHint("请输入支付宝账号姓名");
            } else if (appUsersCashAccount.type == 2) {
                tvWx.performClick();
                llBank.setVisibility(View.GONE);
                llName.setVisibility(View.GONE);
                lineName.setVisibility(View.GONE);
                tvAccount.setText("微信账号");
                etAccount.setHint("请输入微信账号");
            }
            etBankName.setText(appUsersCashAccount.accountBank);
            etBankName2.setText(appUsersCashAccount.branch);
            etAccount.setText(appUsersCashAccount.account);
            etName.setText(appUsersCashAccount.name);
        }
    }

    private void initData() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_alipay) {
            type = 1;
            tvAlipay.setSelected(true);
            tvWx.setSelected(false);
            tvBank.setSelected(false);
            llBank.setVisibility(View.GONE);
            llName.setVisibility(View.VISIBLE);
            lineName.setVisibility(View.VISIBLE);
            tvAccount.setText("支付宝账号");
            etAccount.setHint("请输入支付宝账号");
            tvName.setText("支付宝账号姓名");
            etName.setHint("请输入支付宝账号姓名");
        } else if (view.getId() == R.id.tv_wx) {
            type = 2;
            tvAlipay.setSelected(false);
            tvWx.setSelected(true);
            tvBank.setSelected(false);
            llBank.setVisibility(View.GONE);
            llName.setVisibility(View.GONE);
            lineName.setVisibility(View.GONE);
            tvAccount.setText("微信账号");
            etAccount.setHint("请输入微信账号");
        } else if (view.getId() == R.id.tv_bank) {
            type = 3;
            tvAlipay.setSelected(false);
            tvWx.setSelected(false);
            tvBank.setSelected(true);
            llBank.setVisibility(View.VISIBLE);
            llName.setVisibility(View.VISIBLE);
            lineName.setVisibility(View.VISIBLE);
            tvAccount.setText("银行卡号");
            etAccount.setHint("请输入储蓄卡卡号");
            tvName.setText("账号姓名");
            etName.setHint("请输入姓名");
        } else if (view.getId() == R.id.btn_next) {
            /**
             * 我的收益里添加提现账号
             * @param account 账号
             * @param accountBank 银行名称(type为3时传入)
             * @param name 姓名
             * @param type 类型1表示支付宝，2表示微信，3表示银行卡
             */
            String accountBank = etBankName.getText().toString().trim();
            String bankName = etBankName2.getText().toString().trim();
            String account = etAccount.getText().toString().trim();
            String name = etName.getText().toString().trim();
            if (type == 1) {
                if (TextUtils.isEmpty(account)) {
                    ToastUtil.show("账号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.show("账号姓名不能为空");
                    return;
                }
            } else if (type == 2) {
                if (TextUtils.isEmpty(account)) {
                    ToastUtil.show("账号不能为空");
                    return;
                }
            } else {
                if (TextUtils.isEmpty(accountBank)) {
                    ToastUtil.show("银行名称不能为空");
                    return;
                }
                if (TextUtils.isEmpty(bankName)) {
                    ToastUtil.show("开户支行不能为空");
                    return;
                }
                if (TextUtils.isEmpty(account)) {
                    ToastUtil.show("银行卡号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.show("账号姓名不能为空");
                    return;
                }
            }

            HttpApiAPPFinance.withdraw_account_add(account, accountBank, bankName, name, appUsersCashAccount == null ? 0 : appUsersCashAccount.id, type, new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    if (!TextUtils.isEmpty(msg)) {
                        ToastUtil.show(msg);
                    }
                    if (code == 1) {
                        finish();
                    }
                }
            });

        }
    }
}
