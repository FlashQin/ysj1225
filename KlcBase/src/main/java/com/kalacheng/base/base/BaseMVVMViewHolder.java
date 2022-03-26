package com.kalacheng.base.base;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;


import com.example.base.BR;
import com.kalacheng.util.utils.L;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseMVVMViewHolder<V extends ViewDataBinding, VM extends AndroidViewModel> {
    protected V binding;
    protected Context mContext;
    protected ViewGroup mParentView;
    protected VM viewModel;
    private int viewModelId;

    public BaseMVVMViewHolder(Context context, ViewGroup parentView) {
        mContext = context;
        mParentView = parentView;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), mParentView, false);
        initViewDataBinding();
        init();
        ((AppCompatActivity)mContext).getWindow().setBackgroundDrawable(null);
        setStatusBar();
    }

    private void initViewDataBinding() {
        viewModelId = BR.viewModel;
        if (viewModel == null) {
            Class modelClass = null;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            }
            viewModel = (VM) createViewModel((FragmentActivity) mContext, modelClass);
        }
        //关联ViewModel
        binding.setVariable(viewModelId, viewModel);
     /*   ActivityLife.getInstance().addActivityDestory(new MsgListener.NullMsgListener() {
            @Override
            public void onMsg() {
                if (binding != null) {
                    binding.unbind();
                }
            }
        });*/
    }

    protected abstract int getLayoutId();

    protected abstract void init();


    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return ViewModelProviders.of(activity).get(cls);
    }

    public void addToParent() {
        if (binding.getRoot() != null) {
            ViewGroup parentViewGroup = (ViewGroup) binding.getRoot().getParent();
            if (parentViewGroup != null ) {
                parentViewGroup.removeView(binding.getRoot());
            }
        }
        mParentView.addView(binding.getRoot());
//        if (null != mParentView && null != binding.getRoot())
        L.e("ViewHolder = "+getClass().getSimpleName());
    }

    public void removeFromParent() {
        if (null != mParentView && null != binding.getRoot()) {
            mParentView.removeView(binding.getRoot());
        }
        if (binding != null) {
            binding.unbind();
        }

    }
    //隐藏
    public void setGONE(View viewGroup){
        viewGroup.setVisibility(View.GONE);
    }
    //显示
    public void setVisibility(View viewGroup){
        viewGroup.setVisibility(View.VISIBLE);
    }


    /**
     * 设置透明状态栏
     */
    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ((AppCompatActivity)mContext).getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0);
        }
    }
}
