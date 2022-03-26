package com.kalacheng.shopping.base;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;

import com.kalacheng.base.base.BaseMVVMFragment;

import me.jessyan.autosize.internal.CustomAdapt;

public abstract class SBaseFragment<V extends ViewDataBinding,VM extends AndroidViewModel> extends BaseMVVMFragment<V,VM> implements CustomAdapt {

    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        return 360;
    }


    public void onPauseFragment(){};
    public void onResumeFragment(){};
}
