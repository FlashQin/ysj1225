package com.kalacheng.shopping.base;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;

import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.shopping.R;

import me.jessyan.autosize.internal.CustomAdapt;

public abstract class SBaseActivity<V extends ViewDataBinding, VM extends AndroidViewModel> extends BaseMVVMActivity<V, VM> implements CustomAdapt {

    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        return 360;
    }

    @Override
    public void initData() {
        getWindow().setBackgroundDrawableResource(R.drawable.bg_window);
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

}
