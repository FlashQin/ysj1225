package com.kalacheng.login.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.libbas.model.SingleString;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.util.utils.ToastUtil;

public class ChangePasswordModel extends AndroidViewModel {
    public ChangePasswordModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> title = new ObservableField<>("修改密码");

    public ObservableField<String> oldpassword = new ObservableField<>("");//旧密码

    public ObservableField<String> newpassword = new ObservableField<>("");//新密码

    public ObservableField<String> newpasswordconfirm = new ObservableField<>("");//确认新密码


    //提交数据
    public void onClick() {
            HttpApiAppUser.update_pwd(newpassword.get(), newpasswordconfirm.get(), oldpassword.get(), new HttpApiCallBack<SingleString>() {
                @Override
                public void onHttpRet(int code, String msg, SingleString retModel) {
                    if (code==1){
                        back.onClick();
                    }else {
                        ToastUtil.show(msg);
                    }
                }
            });
    }


    public ChangePasswordCallBack back;
    public void setOnclick(ChangePasswordCallBack callBack){
        this.back=callBack;
    };
    public interface ChangePasswordCallBack{
        void onClick();
    }
}
