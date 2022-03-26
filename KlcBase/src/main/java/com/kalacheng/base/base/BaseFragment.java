package com.kalacheng.base.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.base.R;
import com.kalacheng.util.utils.L;

public abstract class BaseFragment extends Fragment {
    protected boolean mFirstLoadData = true;
    protected boolean mShowed;//是否切换到了当前页面
    protected boolean mPaused;//生命周期暂停

    protected View mParentView;
    protected int currentPosition = 0;

    public BaseFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mParentView = inflater.inflate(getLayoutId(), container, false);
        return mParentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        L.e("Fragment -> " + getClass().getName());
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected void setTitle(String title) {
        TextView titleView = (TextView) mParentView.findViewById(R.id.titleView);
        if (titleView != null) {
            titleView.setText(title);
        }
    }

    public void refreshData() {

    }

    public void loadData() {
    }

    public void onPauseFragment() {
    }

    public void onResumeFragment() {
    }

    public void getUserVisibleHintFragment(){
    }

    protected boolean isFirstLoadData() {
        if (mFirstLoadData) {
            mFirstLoadData = false;
            return true;
        }
        return false;
    }

    public void setShowed(boolean showed) {
        mShowed = showed;
        getUserVisibleHintFragment();
    }

}
