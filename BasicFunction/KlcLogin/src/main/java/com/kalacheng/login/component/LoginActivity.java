package com.kalacheng.login.component;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.commonview.dialog.AccountDisableDialogFragment;
import com.kalacheng.base.event.AccountDisableEvent;
import com.kalacheng.frame.config.APPProConfig;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiAppLogin;
import com.kalacheng.libuser.model_fun.AppLogin_ChartLogin;
import com.kalacheng.libuser.model_fun.AppLogin_login;
import com.kalacheng.login.R;
import com.kalacheng.login.interfaces.OnLoginAgreementListener;
import com.kalacheng.mob.MobCallback;
import com.kalacheng.mob.MobConst;
import com.kalacheng.mob.MobLoginUtil;
import com.kalacheng.mob.bean.LoginData;
import com.kalacheng.base.socket.SocketUtils;
import com.kalacheng.util.adapter.SimpleImgAdapter;
import com.kalacheng.util.bean.SimpleImgBean;
import com.kalacheng.util.utils.AppUtil;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.StringUtil;
import com.kalacheng.util.utils.SystemUtils;
import com.kalacheng.util.utils.TextViewUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.util.view.ItemDecoration;
import com.kalacheng.util.view.LinkTouchMovementMethod;
import com.kalacheng.util.view.MySpannableTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

@Route(path = ARouterPath.LoginActivity)
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final int TOTAL = 60;
    private TextView tvAccountLogin;
    private TextView tvCodeLogin;
    private EditText etPhone;
    private EditText etPassword;
    private LinearLayout layoutCode;
    private EditText etCode;
    private TextView tvCode;
    private RecyclerView recyclerViewLoginType;
    private SimpleImgAdapter loginTypeAdapter;

    private boolean isAccountLogin = true;//true 账号登录；false 验证码登录
    private String phone, password, code;
    private Disposable disposable;
    private Dialog mLoginDialog, dialog;
    private MobLoginUtil mLoginUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EventBus.getDefault().register(this);
        initView();
        initData();
        initListeners();
    }

    private void initView() {
        mLoginDialog = DialogUtil.loadingDialog(mContext, R.style.dialog2, com.kalacheng.util.R.layout.dialog_loading, false, false, getString(R.string.login_ing));
        etPassword = findViewById(R.id.et_password);
        layoutCode = findViewById(R.id.layout_code);
        etCode = findViewById(R.id.et_code);
        etPhone = findViewById(R.id.et_phone);
        tvCode = findViewById(R.id.tv_code);
        tvAccountLogin = findViewById(R.id.tv_account_login);
        tvCodeLogin = findViewById(R.id.tv_code_login);
        tvCodeLogin.setTextColor(Color.argb(125, 255, 255, 255));  //文字透明度
        TextView tvRegister = findViewById(R.id.tv_register);
        tvRegister.setText(WordUtil.addUnderline(tvRegister.getText().toString()));
        recyclerViewLoginType = findViewById(R.id.recyclerViewLoginType);
        recyclerViewLoginType.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewLoginType.setHasFixedSize(true);
        recyclerViewLoginType.setNestedScrollingEnabled(false);
        loginTypeAdapter = new SimpleImgAdapter();
        loginTypeAdapter.setImgWidthHeight(34, 34);
        loginTypeAdapter.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        recyclerViewLoginType.setAdapter(loginTypeAdapter);
        recyclerViewLoginType.addItemDecoration(new ItemDecoration(this, 0, 50, 0));
    }

    public void initData() {
//        loginUtil = new MobLoginUtil();
//        getLoginType();

        String configLoginType = (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_LOGIN_TYPE, "");
        if (!TextUtils.isEmpty(configLoginType)) {
            List<String> loginTypeList = Arrays.asList(configLoginType.split(","));
            if (loginTypeList.size() > 0) {
                List<SimpleImgBean> beans = new ArrayList<>();
                for (String str : loginTypeList) {
                    if ("1".equals(str)) {
                        SimpleImgBean simpleImgBean = new SimpleImgBean(1, R.mipmap.icon_login_weixin);
                        beans.add(simpleImgBean);
                    } else if ("2".equals(str)) {
                        SimpleImgBean simpleImgBean = new SimpleImgBean(2, R.mipmap.icon_login_qq);
                        beans.add(simpleImgBean);
                    }
                }
                if (beans.size() > 0) {
                    findViewById(R.id.tvOtherLoginTip).setVisibility(View.VISIBLE);
                    loginTypeAdapter.setData(beans);
                    mLoginUtil = new MobLoginUtil();
                }
            }
        }

        if ((boolean) SpUtil.getInstance().getSharedPreference(SpUtil.FIRST, true)) {
            showServiceDialog();
        }
    }

    private void initListeners() {
        tvCode.setOnClickListener(this);
        findViewById(R.id.tvPwdForget).setOnClickListener(this);
        findViewById(R.id.tv_register).setOnClickListener(this);
        findViewById(R.id.ll_code_login).setOnClickListener(this);
        findViewById(R.id.ll_account_login).setOnClickListener(this);
        findViewById(R.id.btn_sure).setOnClickListener(this);

        findViewById(R.id.ivLoginBg).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SystemUtils.closeKeyboard(etPassword);
                return true;
            }
        });
        loginTypeAdapter.setOnItemClickCallback(new OnBeanCallback<SimpleImgBean>() {
            @Override
            public void onClick(SimpleImgBean bean) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                if (bean.id == 1) {//微信
                    loginByMob(MobConst.Type.WX);
                } else if (bean.id == 2) {//QQ
                    loginByMob(MobConst.Type.QQ);
                }
            }
        });
    }

    private void loginByMob(String type) {
        if (mLoginUtil == null) {
            return;
        }
        mLoginDialog.show();
        mLoginUtil.execute(type, new MobCallback() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {
                    loginBuyThird((LoginData) data);
                }
            }

            @Override
            public void onError() {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onFinish() {
                if (mLoginDialog != null) {
                    mLoginDialog.dismiss();
                }
            }
        });
    }

    /**
     * 三方登录
     */
    private void loginBuyThird(LoginData data) {
        AppLogin_ChartLogin chartLogin = new AppLogin_ChartLogin();
        chartLogin.nickname = data.getNickName();
        chartLogin.appVersionCode = AppUtil.getVersionName() + "";
        chartLogin.appVersion = AppUtil.getVersionCode() + "";
        chartLogin.openid = data.getOpenID();
        chartLogin.pic = data.getAvatar();
        chartLogin.phoneFirm = AppUtil.getDeviceBrand();
        chartLogin.phoneSystem = AppUtil.getSystemVersion();
        chartLogin.phoneModel = AppUtil.getSystemModel();
        chartLogin.phoneUuid = "";
        chartLogin.source = "android";
        chartLogin.sex = -1;
        if (data.getType().equals(MobConst.Type.QQ)) {
            chartLogin.type = 1;
        } else {
            chartLogin.type = 2;
        }
        HttpApiAppLogin.ChartLogin(chartLogin, new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && retModel != null) {
                    handleLoginInfo(retModel);
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.tv_register) {
            ARouter.getInstance().build(ARouterPath.RegisterActivity).withInt(ARouterValueNameConfig.FindPwdOrRegister, APPProConfig.REGISTER).navigation(this, 1001);
        } else if (view.getId() == R.id.tvPwdForget) {
            ARouter.getInstance().build(ARouterPath.RegisterActivity).withInt(ARouterValueNameConfig.FindPwdOrRegister, APPProConfig.FINDPEW).navigation(this, 1002);
        } else if (view.getId() == R.id.ll_account_login) {
            isAccountLogin = true;
            tvAccountLogin.setTextColor(Color.argb(255, 255, 255, 255));  //文字透明度
            tvCodeLogin.setTextColor(Color.argb(125, 255, 255, 255));  //文字透明度
            findViewById(R.id.line_account).setVisibility(View.VISIBLE);
            findViewById(R.id.line_code).setVisibility(View.INVISIBLE);
            etPassword.setVisibility(View.VISIBLE);
            layoutCode.setVisibility(View.GONE);
            SystemUtils.closeKeyboard(etPhone);
        } else if (view.getId() == R.id.ll_code_login) {
            isAccountLogin = false;
            tvAccountLogin.setTextColor(Color.argb(125, 255, 255, 255));  //文字透明度
            tvCodeLogin.setTextColor(Color.argb(255, 255, 255, 255));  //文字透明度
            findViewById(R.id.line_account).setVisibility(View.INVISIBLE);
            findViewById(R.id.line_code).setVisibility(View.VISIBLE);
            etPassword.setVisibility(View.GONE);
            layoutCode.setVisibility(View.VISIBLE);
            SystemUtils.closeKeyboard(etPhone);
        } else if (view.getId() == R.id.tv_code) {
            getCode();
        } else if (view.getId() == R.id.btn_sure) {
            login();
        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        if (!verificationPhone()) {
            return;
        }
        etCode.requestFocus();
        TextViewUtil.cursorAtEditTextEnd(etCode);
        HttpApiAppLogin.getVerCode(3, phone, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    if (retModel != null) {
                        tvCode.setEnabled(false);
                        disposable = Flowable.interval(1000, TimeUnit.MILLISECONDS).take(TOTAL).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                tvCode.setText(WordUtil.getString(R.string.reg_get_code_again) + "(" + (TOTAL - aLong) + "s)");
                            }
                        }).doOnComplete(new Action() {
                            @Override
                            public void run() throws Exception {
                                tvCode.setText(WordUtil.getString(R.string.reg_get_code_again));
                                tvCode.setEnabled(true);
                            }
                        }).subscribe();
                    }
                }
                ToastUtil.show(msg);
            }
        });
    }

    //手机号密码登录
    private void login() {
        if (!verificationPhone()) {
            return;
        }
        if (isAccountLogin) {
            if (TextUtils.isEmpty(password)) {
                ToastUtil.show("密码不能为空");
                etPassword.requestFocus();
                TextViewUtil.cursorAtEditTextEnd(etPassword);
                return;
            }
        } else {
            if (TextUtils.isEmpty(code)) {
                ToastUtil.show("验证码不能为空");
                etCode.requestFocus();
                TextViewUtil.cursorAtEditTextEnd(etCode);
                return;
            }
        }
        mLoginDialog.show();
        if (isAccountLogin) {
            AppLogin_login userInfo = new AppLogin_login();
            userInfo.mobile = phone;
            userInfo.password = password;
            userInfo.appVersion = AppUtil.getVersionName();
            userInfo.phoneFirm = AppUtil.getDeviceBrand();
            userInfo.phoneModel = AppUtil.getSystemModel();
            userInfo.phoneSystem = AppUtil.getSystemVersion();
            userInfo.appVersionCode = AppUtil.getVersionCode() + "";
            userInfo.phoneUuid = "";
            HttpApiAppLogin.login(userInfo, new HttpApiCallBack<ApiUserInfo>() {
                @Override
                public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                    mLoginDialog.dismiss();
                    if (code == 1 && retModel != null) {
                        handleLoginInfo(retModel);
                    } else {
                        ToastUtil.show(msg);
                    }

                }
            });
        } else {
            HttpApiAppLogin.phoneCodeLogin(AppUtil.getVersionName(), AppUtil.getVersionCode() + "", code, phone, AppUtil.getDeviceBrand(), AppUtil.getSystemModel(), AppUtil.getSystemVersion(), "", "android",
                    new HttpApiCallBack<ApiUserInfo>() {
                        @Override
                        public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                            if (code == 1 && retModel != null) {
                                handleLoginInfo(retModel);
                            } else {
                                mLoginDialog.dismiss();
                                ToastUtil.show("登录失败");
                            }
                        }
                    });
        }
    }

    public boolean verificationPhone() {
        phone = etPhone.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        code = etCode.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.show("手机号不能为空");
            etPhone.requestFocus();
            TextViewUtil.cursorAtEditTextEnd(etPhone);
            return false;
        }
        if (!StringUtil.isPhoneNum(phone)) {
            ToastUtil.show(WordUtil.getString(R.string.login_phone_error));
            etPhone.requestFocus();
            TextViewUtil.cursorAtEditTextEnd(etPhone);
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
        }
        if (mLoginUtil != null) {
            mLoginUtil.release();
        }
        if (null != mLoginDialog || mLoginDialog.isShowing()) {
            mLoginDialog.dismiss();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void showServiceDialog() {
        dialog = DialogUtil.getBaseDialog(this, R.style.dialog, R.layout.dialog_service_conditions, false, false);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = DpUtil.getScreenWidth() - DpUtil.dp2px(90);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        ((TextView) dialog.findViewById(R.id.tvTipInfo)).setText(Html.fromHtml((String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_XIEYI_RULE, "")));
        MySpannableTextView tvTipAgreement = dialog.findViewById(R.id.tvTipAgreement);
        LinkTouchMovementMethod linkTouchMovementMethod = new LinkTouchMovementMethod();
        tvTipAgreement.setLinkTouchMovementMethod(linkTouchMovementMethod);
        tvTipAgreement.setMovementMethod(linkTouchMovementMethod);
        tvTipAgreement.setText(getRenderText(new OnLoginAgreementListener() {
            @Override
            public void onUserAgreementClick() {
                ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                        + "/api/login/appSite?type=10").navigation();
            }

            @Override
            public void onPrivacyAgreementClick() {
                ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                        + "/api/login/appSite?type=2").navigation();
            }
        }));
        dialog.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpUtil.getInstance().put(SpUtil.FIRST, false);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_disagree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
    }

    /**
     * 处理登录信息
     */
    private void handleLoginInfo(ApiUserInfo retModel) {
        SpUtil.getInstance().putModel(SpUtil.USER_INFO, retModel);
        SpUtil.getInstance().put(SpUtil.UID, retModel.userId);
        SpUtil.getInstance().put(SpUtil.TOKEN, retModel.user_token);
        SpUtil.getInstance().put(SpUtil.USER_IS_FIRST_LOGIN, retModel.isFirstLogin);
        SpUtil.getInstance().put(SpUtil.USER_IS_PID, retModel.isPid);
        SocketUtils.startSocket(LoginActivity.this, true);
        //链接soket,如果已经连接，则重新连接
        //SocketService.startService(PhoneLoginActivity.this, true);
        if (TextUtils.isEmpty(retModel.mobile)) {
            if ((int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_BIND_PHONE, 0) == 0) {
                ARouter.getInstance().build(ARouterPath.RegisterActivity).withInt(ARouterValueNameConfig.FindPwdOrRegister, APPProConfig.BINDING_PHONE).navigation(LoginActivity.this, 1004);
            } else if (TextUtils.isEmpty(retModel.username) || (0 == retModel.sex) || TextUtils.isEmpty(retModel.avatar) || TextUtils.isEmpty(retModel.birthday)) {
                ARouter.getInstance().build(ARouterPath.RequiredInfoActivity).navigation();
                finish();
            } else {
                ARouter.getInstance().build(ARouterPath.MainActivity).navigation();
                finish();
            }
        } else if (TextUtils.isEmpty(retModel.username) || (0 == retModel.sex) || TextUtils.isEmpty(retModel.avatar) || TextUtils.isEmpty(retModel.birthday)) {
            ARouter.getInstance().build(ARouterPath.RequiredInfoActivity).navigation();
            finish();
        } else {
            ARouter.getInstance().build(ARouterPath.MainActivity).navigation();
            finish();
        }
    }

    public static CharSequence getRenderText(final OnLoginAgreementListener onLoginAgreementListener) {
        String content = "请在使用前查看并同意完整的";
        String name1 = "《用户协议》";
        String name2 = "《隐私政策》";
        SpannableStringBuilder builder;
        builder = new SpannableStringBuilder(content + name1 + "及" + name2 + " ");
        // 设置文字的前景色（必须放于最后）
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#DC92F5")), content.length(), content.length() + name1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置文字的单击事件
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                if (onLoginAgreementListener != null) {
                    onLoginAgreementListener.onUserAgreementClick();
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //去掉可点击文字的下划线
                ds.setUnderlineText(false);
            }
        }, content.length(), content.length() + name1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int end = content.length() + name1.length() + 1 + name2.length();
        // 设置文字的前景色（必须放于最后）
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#DC92F5")), content.length() + name1.length() + 1, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置文字的单击事件
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                if (onLoginAgreementListener != null) {
                    onLoginAgreementListener.onPrivacyAgreementClick();
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //去掉可点击文字的下划线
                ds.setUnderlineText(false);
            }
        }, content.length() + name1.length() + 1, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    //账号禁用
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAccountDisableEvent(AccountDisableEvent accountDisableEvent) {
        if (accountDisableEvent != null) {
            if (mLoginDialog != null) {
                mLoginDialog.dismiss();
            }
            new AccountDisableDialogFragment(getApplicationContext(), accountDisableEvent.obj);
        }
    }
}
