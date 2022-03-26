package com.kalacheng.login.component;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.commonview.dialog.ShowSuccessDialog;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libbas.model.SingleString;
import com.kalacheng.libbas.model.UserToken;
import com.kalacheng.libuser.httpApi.HttpApiAppLogin;
import com.kalacheng.libuser.httpApi.HttpApiBingAccount;
import com.kalacheng.libuser.model_fun.AppLogin_login;
import com.kalacheng.login.R;
import com.kalacheng.login.databinding.ActivityRegisterBinding;
import com.kalacheng.login.viewmodel.LoginViewModel;
import com.kalacheng.base.socket.SocketUtils;
import com.kalacheng.util.utils.AppUtil;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.StringUtil;
import com.kalacheng.util.utils.SystemUtils;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by hgy on 2019/9/19.
 */

@Route(path = ARouterPath.RegisterActivity)
public class RegisterActivity extends BaseMVVMActivity<ActivityRegisterBinding, LoginViewModel> {

    private static final int TOTAL = 60;
    private Dialog mRegisterDialog, mLoginDialog, mBindingDialog;

    /**
     * //1 手机号注册；2 忘记密码；5 绑定手机号; 7修改手机号
     */
    @Autowired(name = ARouterValueNameConfig.FindPwdOrRegister)
    public int tpye;

    /**
     * //接受是那个页面跳转过来（路由标识）
     * ARouterPath.SettingApp 设置 【APP自定义】
     */
    @Autowired(name = ARouterValueNameConfig.FROM_PAGE)
    public String from_page;

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_register;
    }

    @Override
    public void initViewObservable() {
        binding.setPhoneTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && (s.length() == 9 || s.length() == 10 || s.length() == 11)) {
                    binding.setIsCodeEnabled(true);
                } else {
                    binding.setIsCodeEnabled(false);
                }
                if (tpye == 1 || tpye == 2) {
                    binding.setIsSureEnabled(!TextUtils.isEmpty(viewModel.phone.get().trim()) && !TextUtils.isEmpty(viewModel.code.get().trim()) && !TextUtils.isEmpty(viewModel.password.get().trim()));
                } else {
                    binding.setIsSureEnabled(!TextUtils.isEmpty(viewModel.phone.get().trim()) && !TextUtils.isEmpty(viewModel.code.get().trim()));
                }
            }
        });
        binding.setCodeTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (tpye == 1 || tpye == 2) {
                    binding.setIsSureEnabled(!TextUtils.isEmpty(viewModel.phone.get().trim()) && !TextUtils.isEmpty(viewModel.code.get().trim()) && !TextUtils.isEmpty(viewModel.password.get().trim()));
                } else {
                    binding.setIsSureEnabled(!TextUtils.isEmpty(viewModel.phone.get().trim()) && !TextUtils.isEmpty(viewModel.code.get().trim()));
                }
            }
        });
    }

    @Override
    public void initData() {
        if (tpye == 1) {
            setTitle(WordUtil.getString(R.string.reg_register));
            findViewById(R.id.ll_forget).setVisibility(View.GONE);
        } else if (tpye == 2) {
            setTitle(WordUtil.getString(R.string.login_forget_pwd));
            findViewById(R.id.ll_forget).setVisibility(View.VISIBLE);
        } else if (tpye == 5) {
            setTitle(WordUtil.getString(R.string.login_binding_phone));
            ((TextView) findViewById(R.id.tvForgetInfo)).setText("依据工信部的规定，请填写您的手机号");
            findViewById(R.id.layoutPwd).setVisibility(View.GONE);
            findViewById(R.id.viewPwdLine).setVisibility(View.GONE);
        } else if (tpye == 7) {
            setTitle(WordUtil.getString(R.string.login_modify_phone));
            binding.editPhone.setHint(R.string.reg_input_new_phone);
            findViewById(R.id.ll_forget).setVisibility(View.GONE);
            findViewById(R.id.layoutPwd).setVisibility(View.GONE);
            findViewById(R.id.viewPwdLine).setVisibility(View.GONE);
        }
//        setTileGradient();
//        Dialog dialog = new Dialog(context, );
//        dialog.setContentView();
//        dialog.setCancelable(false);
//        dialog.setCanceledOnTouchOutside(false);
        mRegisterDialog = DialogUtil.loadingDialog(mContext, R.style.dialog2, com.kalacheng.util.R.layout.dialog_loading, false, false, getString(R.string.reg_register_ing));
        mLoginDialog = DialogUtil.loadingDialog(mContext, R.style.dialog2, com.kalacheng.util.R.layout.dialog_loading, false, false, getString(R.string.login_ing));
        mBindingDialog = DialogUtil.loadingDialog(mContext, R.style.dialog2, com.kalacheng.util.R.layout.dialog_loading, false, false, getString(R.string.login_binding_phone_ing));
    }

    public void registerClick(View v) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        int i = v.getId();
        if (i == R.id.btn_code) {
            getCode();
        } else if (i == R.id.btn_register) {
            SystemUtils.closeKeyboard(findViewById(R.id.btn_register));

            if (tpye == 1) {
                register();
            } else if (tpye == 2) {
                getForgotPassword();
            } else if (tpye == 5) {
                bindingPhone();
            } else if (tpye == 7) {
                modifyPhone();
            }
        }
    }

    Disposable disposable;

    /**
     * 获取验证码
     */
    private void getCode() {
        if (!StringUtil.isPhoneNum(viewModel.phone.get())) {
            ToastUtil.show(WordUtil.getString(R.string.login_phone_error));
            binding.editPhone.requestFocus();
            return;
        }
        binding.editCode.requestFocus();
        HttpApiAppLogin.getVerCode(tpye, viewModel.phone.get(), new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    if (retModel != null) {
                        binding.setIsCodeEnabled(false);
                        disposable = Flowable.interval(1000, TimeUnit.MILLISECONDS).take(TOTAL).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                viewModel.btnCode.set(WordUtil.getString(R.string.reg_get_code_again) + "(" + (TOTAL - aLong) + "s)");
                            }
                        }).doOnComplete(new Action() {
                            @Override
                            public void run() throws Exception {
                                viewModel.btnCode.set(WordUtil.getString(R.string.reg_get_code_again));
                                binding.setIsCodeEnabled(true);
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

    @Override
    protected void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    /**
     * 注册并登陆
     */
    private void register() {
        if (TextUtils.isEmpty(viewModel.phone.get().trim())) {
            ToastUtil.show(WordUtil.getString(R.string.reg_input_phone));
            binding.editPhone.requestFocus();
            return;
        }
        if (!StringUtil.isPhoneNum(viewModel.phone.get().trim())) {
            ToastUtil.show(WordUtil.getString(R.string.login_phone_error));
            binding.editPhone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(viewModel.code.get().trim())) {
            ToastUtil.show(WordUtil.getString(R.string.reg_input_code));
            binding.editCode.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(viewModel.password.get().trim())) {
            ToastUtil.show(WordUtil.getString(R.string.reg_input_pwd_1));
            binding.editPwd.requestFocus();
            return;
        }
        if (!viewModel.password.get().equals("") && viewModel.password.get().trim().length() < 6) {
            ToastUtil.show(WordUtil.getString(R.string.reg_input_pwd_1_info));
            binding.editPwd.requestFocus();
            return;
        }
        if (mRegisterDialog != null) {
            mRegisterDialog.show();
        }
        HttpApiAppLogin.register(viewModel.code.get().trim(), viewModel.phone.get().trim(), viewModel.password.get().trim(), "android", new HttpApiCallBack<UserToken>() {
            @Override
            public void onHttpRet(int code, String msg, UserToken retModel) {
                if (code == 1) {
                    mRegisterDialog.dismiss();
                    mLoginDialog.show();

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
                            if (code == 1) {
                                if (retModel != null) {
                                    SpUtil.getInstance().putModel(SpUtil.USER_INFO, retModel);
                                    SpUtil.getInstance().put(SpUtil.UID, retModel.userId);
                                    SpUtil.getInstance().put(SpUtil.TOKEN, retModel.user_token);
                                    SpUtil.getInstance().put(SpUtil.USER_IS_FIRST_LOGIN, retModel.isFirstLogin);
                                    SpUtil.getInstance().put(SpUtil.USER_IS_PID, retModel.isPid);

                                }
                                mLoginDialog.dismiss();

                                SocketUtils.startSocket(RegisterActivity.this, true);
                                //链接soket,如果已经连接，则重新连接
                                //SocketService.startService(RegisterActivity.this, true);

                                ARouter.getInstance().build(ARouterPath.RequiredInfoActivity).navigation(RegisterActivity.this, new NavigationCallback() {
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

                            } else {
                                ToastUtil.show(msg);
                            }

                        }
                    });

                } else {
                    mRegisterDialog.dismiss();
                    ToastUtil.show(msg);
                }
            }

        });
    }

    /**
     * 找回密码
     */
    public void getForgotPassword() {
        if (TextUtils.isEmpty(viewModel.phone.get().trim())) {
            ToastUtil.show(WordUtil.getString(R.string.reg_input_phone));
            binding.editPhone.requestFocus();
            return;
        }
        if (!StringUtil.isPhoneNum(viewModel.phone.get().trim())) {
            ToastUtil.show(WordUtil.getString(R.string.login_phone_error));
            binding.editPhone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(viewModel.code.get().trim())) {
            ToastUtil.show(WordUtil.getString(R.string.reg_input_code));
            binding.editCode.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(viewModel.password.get().trim())) {
            ToastUtil.show(WordUtil.getString(R.string.reg_input_pwd_1));
            binding.editPwd.requestFocus();
            return;
        }
        if (!viewModel.password.get().equals("") && viewModel.password.get().trim().length() < 6) {
            ToastUtil.show(WordUtil.getString(R.string.reg_input_pwd_1_info));
            binding.editPwd.requestFocus();
            return;
        }
        if (mRegisterDialog != null) {
            mRegisterDialog.show();
        }
        HttpApiAppLogin.forget_pwd(viewModel.code.get(), viewModel.password.get(), viewModel.password.get(), viewModel.phone.get(), new HttpApiCallBack<SingleString>() {
            @Override
            public void onHttpRet(int code, String msg, SingleString retModel) {
                mRegisterDialog.dismiss();
                if (code == 1) {
                    if (retModel != null) {
                    }

                    mLoginDialog.show();
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
                                    SpUtil.getInstance().put(SpUtil.USER_IS_FIRST_LOGIN, retModel.isFirstLogin);
                                    SpUtil.getInstance().put(SpUtil.USER_IS_PID, retModel.isPid);
                                }

                                SocketUtils.startSocket(RegisterActivity.this, true);
                                //链接soket,如果已经连接，则重新连接
                                //SocketService.startService(RegisterActivity.this, true);
                                ARouter.getInstance().build(ARouterPath.MainActivity).navigation(RegisterActivity.this, new NavigationCallback() {
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
                            } else {
                                ToastUtil.show(msg);
                            }

                        }
                    });

                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * 绑定手机号
     */
    private void bindingPhone() {
        if (TextUtils.isEmpty(viewModel.phone.get().trim())) {
            ToastUtil.show(WordUtil.getString(R.string.reg_input_phone));
            binding.editPhone.requestFocus();
            return;
        }
        if (!StringUtil.isPhoneNum(viewModel.phone.get().trim())) {
            ToastUtil.show(WordUtil.getString(R.string.login_phone_error));
            binding.editPhone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(viewModel.code.get().trim())) {
            ToastUtil.show(WordUtil.getString(R.string.reg_input_code));
            binding.editCode.requestFocus();
            return;
        }
        mBindingDialog.show();
        HttpApiAppUser.bind_mobile(viewModel.code.get(), viewModel.phone.get(), new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (null != mBindingDialog) {
                    mBindingDialog.dismiss();
                }
                if (code == 1) {
                    ApiUserInfo userInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
                    if (null != userInfo) {
                        userInfo.mobile = viewModel.phone.get();
                        SpUtil.getInstance().putModel(SpUtil.USER_INFO, userInfo);

                        if (!TextUtils.isEmpty(from_page) && from_page.equals(ARouterPath.SettingApp)) {
                            //从设置 过来
                            ShowSuccessDialog showSuccessDialog = new ShowSuccessDialog("绑定成功", new ShowSuccessDialog.ShoSuccessCallback() {
                                @Override
                                public void dismissCallback(String tag) {
                                    finish();
                                }
                            });
                            showSuccessDialog.show(getSupportFragmentManager(), "bindingPhone");
                        } else {
                            //从登陆 过来
                            ARouter.getInstance().build(ARouterPath.RequiredInfoActivity).navigation(RegisterActivity.this, new NavigationCallback() {
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

                    }
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * 修改手机号码
     */
    private void modifyPhone() {
        if (TextUtils.isEmpty(viewModel.phone.get().trim())) {
            ToastUtil.show(WordUtil.getString(R.string.reg_input_new_phone));
            binding.editPhone.requestFocus();
            return;
        }
        if (!StringUtil.isPhoneNum(viewModel.phone.get().trim())) {
            ToastUtil.show(WordUtil.getString(R.string.login_phone_error));
            binding.editPhone.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(viewModel.code.get().trim())) {
            ToastUtil.show(WordUtil.getString(R.string.reg_input_code));
            binding.editCode.requestFocus();
            return;
        }
        mBindingDialog.show();
        HttpApiBingAccount.updateBindMobile(viewModel.code.get(), viewModel.phone.get(), "android", new HttpApiCallBack<UserToken>() {
            @Override
            public void onHttpRet(int code, String msg, UserToken retModel) {
                if (mBindingDialog != null) {
                    mBindingDialog.dismiss();
                }
                if (code == 1) {
                    ApiUserInfo userInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
                    if (userInfo != null) {
                        userInfo.mobile = viewModel.phone.get();
                        SpUtil.getInstance().putModel(SpUtil.USER_INFO, userInfo);

                        ShowSuccessDialog showSuccessDialog = new ShowSuccessDialog("修改成功", true);
                        showSuccessDialog.show(getSupportFragmentManager(), "modifyPhone");
                    }
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }


}
