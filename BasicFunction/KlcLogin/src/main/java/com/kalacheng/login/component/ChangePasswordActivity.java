package com.kalacheng.login.component;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.login.viewmodel.ChangePasswordModel;
import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.login.R;
import com.kalacheng.login.databinding.ChangepasswordBinding;

/**
 * 修改密码
 */
@Route(path = ARouterPath.ChangePassword)
public class ChangePasswordActivity extends BaseMVVMActivity<ChangepasswordBinding, ChangePasswordModel> implements ChangePasswordModel.ChangePasswordCallBack {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.changepassword;
    }

    @Override
    public void initData() {
        viewModel.setOnclick(this);
    }


    @Override
    public void onClick() {
        finish();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }
}
