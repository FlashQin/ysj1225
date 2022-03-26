package com.kalacheng.shortvideo.fragment;

import android.content.Context;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kalacheng.shortvideo.R;
import com.kalacheng.base.base.BaseFragment;


public class ViewPointContainFragment extends BaseFragment {
    protected Context mContext;

    public ViewPointContainFragment() {

    }

    public ViewPointContainFragment(Context context, ViewGroup parentView) {
        mContext = context;
        mParentView = parentView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view_point_contain;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ShortVideoPointFragment meAnchorFragment = new ShortVideoPointFragment();
        transaction.add(R.id.layoutContain, meAnchorFragment);
        transaction.commit();
    }
}
