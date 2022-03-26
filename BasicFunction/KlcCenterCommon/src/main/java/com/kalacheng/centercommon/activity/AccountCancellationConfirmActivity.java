package com.kalacheng.centercommon.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.centercommon.R;
import com.kalacheng.commonview.dialog.ShowSuccessDialog;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiAppLogin;
import com.kalacheng.libuser.httpApi.HttpApiYunthModel;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.SystemUtils;
import com.kalacheng.util.utils.TextViewUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 账户验证
 */
@Route(path = ARouterPath.AccountCancellationConfirmActivity)
public class AccountCancellationConfirmActivity extends BaseActivity implements View.OnClickListener {
    @Autowired(name = "type")
    int type;//1 账号注销 2,青少年模式忘记密码


    private LinearLayout layoutAccountTitle;
    private TextView tvAccount;
    private LinearLayout layoutAccountInfo;
    private EditText etPassword;

    private LinearLayout layoutCodeTitle;
    private TextView tvCode;
    private LinearLayout layoutCodeInfo;
    private TextView tvPhone;
    private EditText etCode;
    private TextView tvCodeGet;
    private TextView tvConfirm;

    private String mobile;//手机号
    private Disposable disposable;//验证码计时器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_cancellation_confirm);
        intView();
        initData();
        inListeners();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    protected void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    private void intView() {
        layoutAccountTitle = findViewById(R.id.layoutAccountTitle);
        tvAccount = findViewById(R.id.tvAccount);
        layoutCodeTitle = findViewById(R.id.layoutCodeTitle);
        tvCode = findViewById(R.id.tvCode);
        layoutAccountInfo = findViewById(R.id.layoutAccountInfo);
        layoutCodeInfo = findViewById(R.id.layoutCodeInfo);
        etPassword = findViewById(R.id.etPassword);
        tvPhone = findViewById(R.id.tvPhone);
        etCode = findViewById(R.id.etCode);
        tvCodeGet = findViewById(R.id.tvCodeGet);
        tvConfirm = findViewById(R.id.tvConfirm);

        if (type == 1) {
            tvConfirm.setText("确认注销");
        } else {
            tvConfirm.setText("设置新密码");
        }

        layoutAccountTitle.setSelected(true);
    }

    private void initData() {
        setTitle("账户验证");
        ApiUserInfo userInfo = SpUtil.getInstance().getModel("UserInfo", ApiUserInfo.class);
        if (userInfo != null) {
            mobile = userInfo.mobile;
            if (mobile.length() > 7) {
                tvPhone.setText(mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4, mobile.length()));
            } else {
                tvPhone.setText(mobile);
            }
        }
    }

    private void inListeners() {
        layoutAccountTitle.setOnClickListener(this);
        layoutCodeTitle.setOnClickListener(this);
        tvCodeGet.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);

        findViewById(R.id.layoutCancellationContent).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SystemUtils.closeKeyboard(etPassword);
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.layoutAccountTitle) {
            if (!layoutAccountTitle.isSelected()) {
                layoutAccountTitle.setSelected(true);
                layoutCodeTitle.setSelected(false);
                layoutAccountTitle.setBackgroundResource(R.drawable.bg_user_task_white_btn);
                layoutCodeTitle.setBackgroundResource(0);
                tvAccount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                tvAccount.setTextColor(Color.parseColor("#8A8DFF"));
                tvCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tvCode.setTextColor(Color.parseColor("#AAAAAA"));
                layoutAccountInfo.setVisibility(View.VISIBLE);
                layoutCodeInfo.setVisibility(View.GONE);
                SystemUtils.closeKeyboard(etPassword);
            }
        } else if (view.getId() == R.id.layoutCodeTitle) {
            if (!layoutCodeTitle.isSelected()) {
                layoutAccountTitle.setSelected(false);
                layoutCodeTitle.setSelected(true);
                layoutAccountTitle.setBackgroundResource(0);
                layoutCodeTitle.setBackgroundResource(R.drawable.bg_user_task_white_btn);
                tvAccount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tvAccount.setTextColor(Color.parseColor("#AAAAAA"));
                tvCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                tvCode.setTextColor(Color.parseColor("#8A8DFF"));
                layoutAccountInfo.setVisibility(View.GONE);
                layoutCodeInfo.setVisibility(View.VISIBLE);
                SystemUtils.closeKeyboard(etPassword);
            }
        } else if (view.getId() == R.id.tvCodeGet) {//获取验证码
            if (TextUtils.isEmpty(mobile)) {
                ToastUtil.show("未绑定手机号，请选择其它验证方式");
            } else {
                getCode();
            }
        } else if (view.getId() == R.id.tvConfirm) {//确认注销
            if (layoutAccountTitle.isSelected()) {
                if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
                    ToastUtil.show("请输入密码");
                } else {
                    if (type == 1) {
                        userCancelAccount(etPassword.getText().toString().trim(), 1);
                    } else {
                        setYoungAccountPwy();
                    }
                }
            } else {
                if (TextUtils.isEmpty(etCode.getText().toString().trim())) {
                    ToastUtil.show("请输入验证码");
                } else {
                    if (type == 1) {
                        userCancelAccount(etCode.getText().toString().trim(), 2);
                    } else if (type == 2) {
                        setYoungCodePwy();
                    }
                }
            }
        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        etCode.requestFocus();
        TextViewUtil.cursorAtEditTextEnd(etCode);
        HttpApiAppLogin.getVerCode(4, mobile, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    if (retModel != null) {
                        tvCodeGet.setEnabled(false);
                        tvCodeGet.setTextColor(Color.parseColor("#B7B7B7"));
                        disposable = Flowable.interval(1000, TimeUnit.MILLISECONDS).take(60).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                tvCodeGet.setText("获取验证码(" + (60 - aLong) + "s)");
                            }
                        }).doOnComplete(new Action() {
                            @Override
                            public void run() throws Exception {
                                tvCodeGet.setText("获取验证码");
                                tvCodeGet.setEnabled(true);
                                tvCodeGet.setTextColor(Color.parseColor("#DD85FD"));
                            }
                        }).subscribe();
                    }
                }
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * 注销
     */
    private void userCancelAccount(String code, int type) {
        HttpApiAppUser.userCancelAccount(code, type, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    ShowSuccessDialog showSuccessDialog = new ShowSuccessDialog("注销成功", true);
                    showSuccessDialog.show(getSupportFragmentManager(), "AccountCancellation");
                } else {
                    SystemUtils.closeKeyboard(etPassword);
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /*
     * 青少年模式密码
     * */
    public void setYoungAccountPwy() {
        HttpApiYunthModel.yunthAuthByAccount(etPassword.getText().toString().trim(), new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    ARouter.getInstance().build(ARouterPath.YoungPatternConfirmPassWordActivity).navigation();
                    finish();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /*
     * 青少年模式验证吗
     * */
    public void setYoungCodePwy() {
        HttpApiYunthModel.yunthAuthByCode(etCode.getText().toString().trim(), mobile, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    ARouter.getInstance().build(ARouterPath.YoungPatternConfirmPassWordActivity).navigation();
                    finish();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }
}
