package com.kalacheng.login.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.kalacheng.login.R;
import com.kalacheng.util.utils.WordUtil;

public class LoginViewModel extends AndroidViewModel {


    //用户名的绑定
    public ObservableField<String> phone = new ObservableField<>("");
    //密码的绑定
    public ObservableField<String> password = new ObservableField<>("");
    //确认密码
    public ObservableField<String> password_melody = new ObservableField<>("");
    //密码的绑定
    public ObservableField<String> code = new ObservableField<>("");

    //验证码读秒的绑定
    public ObservableField<String> btnCode = new ObservableField<>(WordUtil.getString(R.string.reg_get_code));

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }
}
