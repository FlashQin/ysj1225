package com.kalacheng.base.base;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.base.BR;
import com.kalacheng.util.utils.L;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by hgy on 2019/9/19.
 * 一个拥有DataBinding框架的基Activity
 * 这里根据项目业务可以换成你自己熟悉的BaseActivity, 但是需要继承RxAppCompatActivity,方便LifecycleProvider管理生命周期
 */
public abstract class BaseMVVMFragment<V extends ViewDataBinding, VM extends AndroidViewModel> extends Fragment {
    protected boolean mShowed;//是否切换到了当前页面
    protected V binding;
    protected VM viewModel;
    private int viewModelId;
    protected Context mContext;
    protected ViewGroup mParentView;
    protected int currentPosition = 0;
    //弱引用持有
    private WeakReference<LifecycleProvider> lifecycle;

    public BaseMVVMFragment(Context context, ViewGroup parentView) {
        mContext = context;
        mParentView = parentView;
    }

    public BaseMVVMFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //页面接受的参数方法
        initParam();
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding(savedInstanceState);
        //页面数据初始化方法
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        L.e("Fragment -> " + getClass().getName());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (binding != null) {
            binding.unbind();
        }
    }

    /**
     * 注入绑定
     */
    private void initViewDataBinding(Bundle savedInstanceState) {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
//        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), getLayoutId(), mParentView, false);
        viewModelId = BR.viewModel;
//        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass = null;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            }
            viewModel = (VM) createViewModel((Fragment) this, modelClass);
        }
        //关联ViewModel
        binding.setVariable(viewModelId, viewModel);
    }

    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(viewModelId, viewModel);
        }
    }

    public void initParam() {

    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
//    protected abstract int getLayoutId();
    public abstract void initData();

    public void initViewObservable() {

    }

    public void onPauseFragment() {
    }

    ;

    public void onResumeFragment() {
    }

    ;

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return ViewModelProviders.of(fragment).get(cls);
    }

    /**
     * 注入RxLifecycle生命周期
     *
     * @param lifecycle
     */
    public void injectLifecycleProvider(LifecycleProvider lifecycle) {
        this.lifecycle = new WeakReference<>(lifecycle);
    }

//    protected void setTitle(String title) {
//        TextView titleView = (TextView) findViewById(R.id.titleView);
//        if (titleView != null) {
//            titleView.setText(title);
//        }
//    }

//    public void backClick(View v) {
//        finish();
//    }

    public void setShowed(boolean showed) {
        mShowed = showed;
    }

    public void refreshData() {

    }
}
