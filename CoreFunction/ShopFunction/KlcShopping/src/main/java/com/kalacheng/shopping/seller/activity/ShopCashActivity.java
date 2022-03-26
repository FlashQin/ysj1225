package com.kalacheng.shopping.seller.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.busfinance.httpApi.HttpApiAPPFinance;
import com.kalacheng.busshop.httpApi.HttpApiShopBusiness;
import com.kalacheng.busshop.model.ShopBusiness;
import com.kalacheng.busshop.model.ShopWithdrawDTO;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.AppUsersCashAccount;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.databinding.ActivityShopCashBinding;
import com.kalacheng.shopping.seller.viewmodel.ShopCashViewModel;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.TextViewUtil;
import com.kalacheng.util.utils.ToastUtil;

/**
 * 提现
 */
@Route(path = ARouterPath.ShopCashActivity)
public class ShopCashActivity extends BaseMVVMActivity<ActivityShopCashBinding, ShopCashViewModel> {
    ShopWithdrawDTO shopWithdrawDTO;
    long accountId;
    String account;
    int code = 2;
    int type;

    private long mMaxCanMoney;//最大可提现数

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_shop_cash;
    }

    @Override
    public void initData() {
        binding.tvOther.setVisibility(View.VISIBLE);
        binding.tvOther.setText("提现记录");
        binding.tvOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                        + "/api/h5/userMoneyDetails?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken() + "&pageSize=10&pageIndex=0&anchorId=" + HttpClient.getUid() + "&type=1").navigation();

            }
        });

        binding.etVotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("DefaultLocale")
            @Override
            public void afterTextChanged(Editable s) {
                String profitStr = s.toString().trim();
                if (TextUtils.isEmpty(profitStr) || Double.parseDouble(profitStr) == 0) {
                    binding.tvDeduct.setText("0.00");
                    binding.tvReallyMoney.setText("0.00");
                } else {
                    if (null != shopWithdrawDTO) {
                        double profit = Double.parseDouble(profitStr);
                        if (profit > mMaxCanMoney) {
                            profit = mMaxCanMoney;
                            String str = mMaxCanMoney + "";
                            binding.etVotes.setText(str);
                            binding.etVotes.setSelection(str.length());
                        }
                        binding.tvCommission.setText(String.format("%.2f", profit * shopWithdrawDTO.sellRate / 100));
                        binding.tvDeduct.setText(String.format("%.2f", (profit - profit * shopWithdrawDTO.sellRate / 100) * shopWithdrawDTO.service / 100));
                        binding.tvReallyMoney.setText(String.format("%.2f", profit - (profit * shopWithdrawDTO.sellRate / 100) - ((profit - profit * shopWithdrawDTO.sellRate / 100) * shopWithdrawDTO.service / 100)));
                    }
                }
            }
        });

        binding.tvCashAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.etVotes.setText(mMaxCanMoney + "");
                TextViewUtil.cursorAtEditTextEnd(binding.etVotes);
            }
        });


        binding.llAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.CashAccountActivity).navigation(ShopCashActivity.this, code);

            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                withdraw();
            }
        });


        HttpApiShopBusiness.getWithdrawInfo(new HttpApiCallBack<ShopWithdrawDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ShopWithdrawDTO retModel) {
                if (code == 1) {
                    shopWithdrawDTO = retModel;
                    if (retModel.account != null) {
                        binding.ivCashAccount.setVisibility(View.VISIBLE);
                        if (retModel.account.type == 1) {
                            binding.ivCashAccount.setImageResource(R.mipmap.icon_cash_ali);
                        } else if (retModel.account.type == 2) {
                            binding.ivCashAccount.setImageResource(R.mipmap.icon_cash_wx);
                        } else if (retModel.account.type == 3) {
                            binding.ivCashAccount.setImageResource(R.mipmap.icon_cash_bank);
                        }
                        account = retModel.account.account;
                        binding.tvCashAccount.setText(account);
                        accountId = retModel.account.id;
                        type = retModel.account.type;
                    }
                }
            }
        });

        getOne();
    }


    private void withdraw() {
        String withdrawMoney = binding.etVotes.getText().toString().trim();
        if (TextUtils.isEmpty(withdrawMoney) || Double.parseDouble(withdrawMoney) == 0) {
            ToastUtil.show("请输入提现金额");
            return;
        }
        if (accountId <= 0 || null == account) {
            ToastUtil.show("提现账户为空");
            return;
        }

        HttpApiAPPFinance.withdraw_account_apply(accountId, account, type, Integer.parseInt(withdrawMoney), 4, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    getOne();
                }
                ToastUtil.show(msg);
            }
        });
    }

    /**
     * 获取商家信息
     */
    public void getOne() {
        HttpApiShopBusiness.getOne(new HttpApiCallBack<ShopBusiness>() {
            @Override
            public void onHttpRet(int code, String msg, ShopBusiness retModel) {
                if (code == 1 && retModel != null) {
                    mMaxCanMoney = (long) retModel.amount;
                    binding.tvProfit.setText(DecimalFormatUtils.toTwo(retModel.amount));
                    binding.etVotes.setText("");
                    binding.tvCommission.setText("");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == code) {
                AppUsersCashAccount appUsersCashAccount = data.getParcelableExtra(ARouterValueNameConfig.AppUsersCashAccount);
                binding.tvCashAccount.setText(appUsersCashAccount.account);
                binding.ivCashAccount.setVisibility(View.VISIBLE);
                accountId = appUsersCashAccount.id;
                account = appUsersCashAccount.account;
                type = appUsersCashAccount.type;
//                     * 账号类型，1表示支付宝，2表示微信，3表示银行卡
                if (appUsersCashAccount.type == 1) {
                    binding.ivCashAccount.setImageResource(R.mipmap.icon_cash_ali);
                } else if (appUsersCashAccount.type == 2) {
                    binding.ivCashAccount.setImageResource(R.mipmap.icon_cash_wx);
                } else if (appUsersCashAccount.type == 3) {
                    binding.ivCashAccount.setImageResource(R.mipmap.icon_cash_bank);
                }

            }
        }
    }
}
