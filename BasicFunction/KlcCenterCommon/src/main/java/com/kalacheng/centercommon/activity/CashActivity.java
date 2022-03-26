package com.kalacheng.centercommon.activity;

//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.Html;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentActivity;
//
//import com.alibaba.android.arouter.facade.annotation.Route;
//import com.alibaba.android.arouter.launcher.ARouter;
//import com.kalacheng.centercommon.R;
//import com.kalacheng.centercommon.dialog.CashGuildDialog;
//import com.kalacheng.commonview.dialog.AnchorRequestDialog;
//import com.kalacheng.frame.config.ARouterPath;
//import com.kalacheng.frame.config.ARouterValueNameConfig;
//import com.kalacheng.libbas.model.HttpNone;
//import com.kalacheng.busfinance.httpApi.HttpApiAPPFinance;
//import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
//import com.kalacheng.libuser.model.ApiMyEarnings;
//import com.kalacheng.libuser.model.ApiUserInfo;
//import com.kalacheng.libuser.model.AppUsersCashAccount;
//import com.kalacheng.util.utils.DialogUtil;
//import com.kalacheng.util.utils.TextViewUtil;
//import com.kalacheng.base.activty.BaseActivity;
//import com.kalacheng.util.utils.SpUtil;
//import com.kalacheng.util.utils.ToastUtil;
//import com.kalacheng.base.http.HttpApiCallBack;
//import com.kalacheng.base.http.HttpClient;

/**
 * 收益中心
 */
//@Route(path = ARouterPath.CashActivity)
//public class CashActivity extends BaseActivity implements View.OnClickListener {
//    TextView tvCashAccount, tvOther, tvCashMoney, tvDeduct, tvReallyMoney, tvProfit, tvguild, Platform_Cash, Guild_Cash;
//    EditText etVotes;
//    ImageView ivCashAccount;
//    ApiMyEarnings apiMyEarnings;
//    long accountId;
//    public String account;
//    int code = 2;
//    int type;
//    private long mMaxCanMoney;//最大可提现数
//    private long mMaxGuildAccount;//最大可提公会收益数
//
//    private int cashType = 1;//1 ，平台提现 3 公会提现
//
//    private ImageView Cash_Guild_dialog;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cash);
//        initView();
//        initData();
//    }
//
//    @Override
//    protected boolean isStatusBarWhite() {
//        return false;
//    }
//
//    private void initView() {
//        setTitle("收益中心");
//        tvOther = findViewById(R.id.tvOther);
//        tvOther.setVisibility(View.VISIBLE);
//        tvOther.setText("查看明细");
//        tvProfit = findViewById(R.id.tv_profit);
//        tvCashAccount = findViewById(R.id.tv_cash_account);
//        tvCashMoney = findViewById(R.id.tv_cash_money);
//        tvDeduct = findViewById(R.id.tv_deduct);
//        tvReallyMoney = findViewById(R.id.tv_really_money);
//        etVotes = findViewById(R.id.et_votes);
//        ivCashAccount = findViewById(R.id.iv_cash_account);
//        tvguild = findViewById(R.id.tv_guild);
//
//        Platform_Cash = findViewById(R.id.Platform_Cash);
//        Platform_Cash.setOnClickListener(this);
//        Guild_Cash = findViewById(R.id.Guild_Cash);
//        Guild_Cash.setOnClickListener(this);
//        Cash_Guild_dialog = findViewById(R.id.Cash_Guild_dialog);
//        Cash_Guild_dialog.setOnClickListener(this);
//
//        findViewById(R.id.btn_next).setOnClickListener(this);
//        findViewById(R.id.ll_account).setOnClickListener(this);
//        findViewById(R.id.tv_cash_all).setOnClickListener(this);
//        findViewById(R.id.tv_cash_record).setOnClickListener(this);
//        tvOther.setOnClickListener(this);
//
//        String withdrawalRule = (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_WITHDRAWAL_RULE, "");
//        if (!TextUtils.isEmpty(withdrawalRule)) {
//            findViewById(R.id.layoutWithdrawalRule).setVisibility(View.VISIBLE);
//            ((TextView) findViewById(R.id.tvWithdrawalRule)).setText(Html.fromHtml(withdrawalRule));
//        }
//    }
//
//    private void initData() {
//        etVotes.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String profitStr = editable.toString().trim();
//                if (TextUtils.isEmpty(profitStr) || Double.parseDouble(profitStr) == 0) {
//                    tvCashMoney.setText("0.00");
//                    tvDeduct.setText("0.00");
//                    tvReallyMoney.setText("0.00");
//                } else {
//                    if (null != apiMyEarnings) {
//                        double profit = Double.parseDouble(profitStr);
//                        if (cashType == 1) {
//                            if (profit > mMaxCanMoney) {
//                                profit = mMaxCanMoney;
//                                String str = mMaxCanMoney + "";
//                                etVotes.setText(str);
//                                etVotes.setSelection(str.length());
//                            }
//                        } else {
//                            if (profit > mMaxGuildAccount) {
//                                profit = mMaxGuildAccount;
//                                String str = mMaxGuildAccount + "";
//                                etVotes.setText(str);
//                                etVotes.setSelection(str.length());
//                            }
//                        }
//                        double cashMoney = profit / apiMyEarnings.cashRate;
//                        tvCashMoney.setText(String.format("%.2f", cashMoney));
//                        tvDeduct.setText(String.format("%.2f", cashMoney * apiMyEarnings.service / 100));
//                        tvReallyMoney.setText(String.format("%.2f", cashMoney - (cashMoney * apiMyEarnings.service / 100)));
//                    }
//                }
//            }
//        });
//        HttpApiAPPFinance.my_earnings(new HttpApiCallBack<ApiMyEarnings>() {
//            @Override
//            public void onHttpRet(int code, String msg, ApiMyEarnings retModel) {
//                if (code == 1 && null != retModel) {
//                    apiMyEarnings = retModel;
//                    tvProfit.setText(String.format("%.2f", retModel.votes));
//                    tvguild.setText(String.format("%.2f", retModel.guildAccount));
//                    mMaxCanMoney = (long) retModel.votes;
//                    mMaxGuildAccount = (long) retModel.guildAccount;
//                    if (retModel.account != null) {
//                        ivCashAccount.setVisibility(View.VISIBLE);
//                        if (retModel.account.type == 1) {
//                            ivCashAccount.setImageResource(R.mipmap.icon_cash_ali);
//                        } else if (retModel.account.type == 2) {
//                            ivCashAccount.setImageResource(R.mipmap.icon_cash_wx);
//                        } else if (retModel.account.type == 3) {
//                            ivCashAccount.setImageResource(R.mipmap.icon_cash_bank);
//                        }
//                        account = retModel.account.account;
//                        tvCashAccount.setText(account);
//                        accountId = retModel.account.id;
//                        type = retModel.account.type;
//                    }
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View view) {
//        if (view.getId() == R.id.btn_next) {
//            if (!isAnchor()) {
//                return;
//            }
//            String withdrawMoney = etVotes.getText().toString().trim();
//            if (TextUtils.isEmpty(withdrawMoney) || Double.parseDouble(withdrawMoney) == 0) {
//                ToastUtil.show("请输入提现金额");
//                return;
//            }
//            if (Double.parseDouble(withdrawMoney) > apiMyEarnings.votes) {
//                ToastUtil.show("提现受益大于可提现金额");
//                return;
//            }
//            if (accountId <= 0 || null == account) {
//                ToastUtil.show("提现账户为空");
//                return;
//            }
//            double votes = Double.parseDouble(withdrawMoney);
//            /**
//             * 我的收益提现申请
//             * @param accountId 提现账号id
//             * @param accountName 账号
//             * @param accountType 账号类型 1：支付宝  2：微信
//             * @param delta 提现金币/佣金数量
//             * @param type 佣金提现 1：金币提现  2：佣金提现 3：公会提现
//             */
//            HttpApiAPPFinance.withdraw_account_apply(accountId, account, type, (int) votes, cashType, new HttpApiCallBack<HttpNone>() {
//                @Override
//                public void onHttpRet(int code, String msg, HttpNone retModel) {
//                    if (!TextUtils.isEmpty(msg)) {
//                        ToastUtil.show(msg);
//                    }
//                    if (code == 1) {
//                        finish();
//                    }
//                }
//            });
//        } else if (view.getId() == R.id.tvOther) {
//            DialogUtil.showStringArrayDialog(mContext, new Integer[]{
//                    R.string.profit_detail, R.string.guild_profit_detail, R.string.cash_record}, new DialogUtil.StringArrayDialogCallback() {
//                @RequiresApi(api = Build.VERSION_CODES.M)
//                @Override
//                public void onItemClick(String text, int tag) {
//                    if (tag == R.string.profit_detail) {
//                        ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
//                                + "/api/h5/incomeRecord?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken() + "&pageSize=15&pageIndex=0&type=1").navigation();
//                    } else if (tag == R.string.cash_record) {
//                        HttpApiAppUser.personCenter(-1, -1, HttpClient.getUid(), new HttpApiCallBack<ApiUserInfo>() {
//                            @Override
//                            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
//                                if (code == 1 && retModel != null) {
//                                    if (retModel.role == 0) {
//                                        DialogUtil.showSimpleDialog(CashActivity.this, "提示", "你还没有认证,无法提现哦", "去认证", true, new DialogUtil.SimpleCallback() {
//                                            @Override
//                                            public void onConfirmClick() {
//                                                ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
//                                                if (apiUserInfo != null && apiUserInfo.sex == 2) {
//                                                    ARouter.getInstance().build(ARouterPath.ApplyAnchorActivity).navigation();
//                                                } else {
//                                                    DialogUtil.showKnowDialog(CashActivity.this, "暂时只支持小姐姐认证哦~", null);
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onConfirmClick(java.lang.String input) {
//
//                                            }
//
//                                            @Override
//                                            public void onCancelClick() {
//
//                                            }
//                                        });
//                                    } else {
//                                        ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
//                                                + "/api/h5/userMoneyDetails?type=1&_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken() + "&pageSize=10&pageIndex=0&anchorId=" + HttpClient.getUid()).navigation();
//
//                                    }
//                                }
//                            }
//                        });
//                    } else if (tag == R.string.guild_profit_detail) {
//                        ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
//                                + "/static/h5page/index.html#/profitDetails?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken()).navigation();
//
//                    }
//                }
//            });
//
//        } else if (view.getId() == R.id.ll_account) {
//            ARouter.getInstance().build(ARouterPath.CashAccountActivity).withParcelable(ARouterValueNameConfig.AppUsersCashAccount, apiMyEarnings.account).navigation(this, code);
//        } else if (view.getId() == R.id.tv_cash_all) {
//            if (cashType == 1) {
//                etVotes.setText(mMaxCanMoney + "");
//            } else {
//                etVotes.setText(mMaxGuildAccount + "");
//            }
//            TextViewUtil.cursorAtEditTextEnd(etVotes);
//        } else if (view.getId() == R.id.Platform_Cash) {//平台提现
//            cashType = 1;
//            Platform_Cash.setBackgroundResource(R.drawable.red_cash_rect);
//            Platform_Cash.setTextColor(getResources().getColor(R.color.white));
//
//            Guild_Cash.setBackgroundResource(R.drawable.bg_btn_cancel);
//            Guild_Cash.setTextColor(getResources().getColor(R.color.textColor3));
//
//            String withdrawMoney = etVotes.getText().toString().trim();
//            if (!TextUtils.isEmpty(withdrawMoney) && Double.parseDouble(withdrawMoney) > mMaxCanMoney) {
//                etVotes.setText(mMaxCanMoney + "");
//                TextViewUtil.cursorAtEditTextEnd(etVotes);
//            }
//        } else if (view.getId() == R.id.Guild_Cash) {//公会提现
//            cashType = 3;
//            Guild_Cash.setBackgroundResource(R.drawable.red_cash_rect);
//            Guild_Cash.setTextColor(getResources().getColor(R.color.white));
//
//            Platform_Cash.setBackgroundResource(R.drawable.bg_btn_cancel);
//            Platform_Cash.setTextColor(getResources().getColor(R.color.textColor3));
//
//            String withdrawMoney = etVotes.getText().toString().trim();
//            if (!TextUtils.isEmpty(withdrawMoney) && Double.parseDouble(withdrawMoney) > mMaxGuildAccount) {
//                etVotes.setText(mMaxGuildAccount + "");
//                TextViewUtil.cursorAtEditTextEnd(etVotes);
//            }
//        } else if (view.getId() == R.id.Cash_Guild_dialog) {
//            CashGuildDialog guildDialog = new CashGuildDialog();
//            guildDialog.show(((FragmentActivity) mContext).getSupportFragmentManager(), "CashGuildDialog");
//
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == code) {
//                AppUsersCashAccount appUsersCashAccount = data.getParcelableExtra(ARouterValueNameConfig.AppUsersCashAccount);
//                tvCashAccount.setText(appUsersCashAccount.account);
//                ivCashAccount.setVisibility(View.VISIBLE);
//                accountId = appUsersCashAccount.id;
//                account = appUsersCashAccount.account;
//                type = appUsersCashAccount.type;
////                     * 账号类型，1表示支付宝，2表示微信，3表示银行卡
//                if (appUsersCashAccount.type == 1) {
//                    ivCashAccount.setImageResource(R.mipmap.icon_cash_ali);
//                } else if (appUsersCashAccount.type == 2) {
//                    ivCashAccount.setImageResource(R.mipmap.icon_cash_wx);
//                } else if (appUsersCashAccount.type == 3) {
//                    ivCashAccount.setImageResource(R.mipmap.icon_cash_bank);
//                }
//
//            }
//        }
//    }
//
//    /**
//     * 是否为主播
//     */
//    private boolean isAnchor() {
//        ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
//        if (apiUserInfo.role == 1) {
//            return true;
//        } else {
//            AnchorRequestDialog anchorRequestDialog = new AnchorRequestDialog();
//            anchorRequestDialog.setAnchorAuditStatus(apiUserInfo.anchorAuditStatus);
//            anchorRequestDialog.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "AnchorRequestDialog");
//            return false;
//        }
//    }
//}
