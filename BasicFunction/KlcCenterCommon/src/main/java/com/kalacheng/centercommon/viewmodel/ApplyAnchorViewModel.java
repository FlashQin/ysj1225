package com.kalacheng.centercommon.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.frame.config.ARouterPath;

//个人中心基本信息
public class ApplyAnchorViewModel extends AndroidViewModel {

    //用户名的绑定
    public ObservableField<String> phone = new ObservableField<>("");
    //密码的绑定
    public ObservableField<String> password = new ObservableField<>("");

    public ApplyAnchorViewModel(@NonNull Application application) {
        super(application);
    }

}
