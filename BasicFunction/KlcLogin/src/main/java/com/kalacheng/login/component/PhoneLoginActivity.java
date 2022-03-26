package com.kalacheng.login.component;

import android.app.Dialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.frame.config.APPProConfig;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libuser.httpApi.HttpApiAppLogin;
import com.kalacheng.libuser.model_fun.AppLogin_login;
import com.kalacheng.login.R;
import com.kalacheng.login.databinding.ActivityLoginPhoneBinding;
import com.kalacheng.login.viewmodel.LoginViewModel;
import com.kalacheng.base.socket.SocketUtils;
import com.kalacheng.util.utils.AppUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.StringUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;

@Route(path = ARouterPath.PhoneLoginActivity)
public class PhoneLoginActivity extends BaseMVVMActivity<ActivityLoginPhoneBinding, LoginViewModel> {
    private Dialog mLoginDialog;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login_phone;
    }

    @Override
    public void initData() {
        setTitle(WordUtil.getString(R.string.login_phone_tip));
//        setTileGradient();
        mLoginDialog = DialogUtil.loadingDialog(mContext, R.style.dialog2, com.kalacheng.util.R.layout.dialog_loading, false, false, getString(R.string.login_ing));
        binding.btnTip.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void initViewObservable() {
        binding.setEtWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.setIsSureEnabled(!TextUtils.isEmpty(viewModel.phone.get().trim()) && !TextUtils.isEmpty(viewModel.password.get().trim()));
            }
        });
    }

    public void loginClick(View v) {
        int i = v.getId();
        if (i == R.id.tvLogin) {
            login();
        } else if (i == R.id.tvPwdForget) {
            ARouter.getInstance().build(ARouterPath.RegisterActivity).withInt(ARouterValueNameConfig.FindPwdOrRegister, APPProConfig.FINDPEW).navigation();
//                startActivity(new Intent(mContext, FindPwdActivity.class));
        } else if (i == R.id.btn_tip) {//                WebViewActivity.forward2(mContext, HtmlConfig.LOGIN_PRIVCAY);
        }
    }

    //手机号密码登录
    private void login() {
        mLoginDialog.show();
        if (!StringUtil.isPhoneNum(viewModel.phone.get().trim())) {
            ToastUtil.show(WordUtil.getString(R.string.login_phone_error));
            binding.etPhone.requestFocus();
            mLoginDialog.dismiss();
            return;
        }
        AppLogin_login userInfo = new AppLogin_login();
        userInfo.mobile = viewModel.phone.get().trim();
        userInfo.password = viewModel.password.get().trim();
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
                if (code == 1) {
                    if (retModel != null) {
                        SpUtil.getInstance().putModel(SpUtil.USER_INFO, retModel);
                        SpUtil.getInstance().put(SpUtil.UID, retModel.userId);
                        SpUtil.getInstance().put(SpUtil.TOKEN, retModel.user_token);
//                        if (null != retModel.socketUrl)
////                    AppConfig.getInstance().setLoginInfo(retModel.userid + "", retModel.UserToken, true);
//                        if (null != retModel.socketUrl)
//                            SpUtil.getInstance().put("socketUrl", retModel.socketUrl);

                        SocketUtils.startSocket(PhoneLoginActivity.this, true);
                        //链接soket,如果已经连接，则重新连接
                        //SocketService.startService(PhoneLoginActivity.this, true);
                        SpUtil.getInstance().put(SpUtil.USER_IS_FIRST_LOGIN, retModel.isFirstLogin);
                        SpUtil.getInstance().put(SpUtil.USER_IS_PID, retModel.isPid);

                        ARouter.getInstance().build(ARouterPath.MainActivity).withParcelable(ARouterValueNameConfig.ApiUserInfo, retModel).navigation(PhoneLoginActivity.this, new NavigationCallback() {
                            @Override
                            public void onFound(Postcard postcard) {

                            }

                            @Override
                            public void onLost(Postcard postcard) {

                            }

                            @Override
                            public void onArrival(Postcard postcard) {
                                setResult(RESULT_OK);
                                finish();
                            }

                            @Override
                            public void onInterrupt(Postcard postcard) {

                            }
                        });


                    }
                } else {
                    ToastUtil.show(msg);
                }

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
