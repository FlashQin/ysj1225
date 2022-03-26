package com.kalacheng.util.utils;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kalacheng.util.utils.fragment.PermissionsFragment;


public class PermissionsUtil {
    private PermissionsFragment mFragment;

    public PermissionsUtil(FragmentActivity activity) {
        mFragment = new PermissionsFragment();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();
        tx.add(mFragment, "ProcessFragment").commitAllowingStateLoss();
    }

    public void requestPermissions(String[] permissions, Runnable runnable) {
        mFragment.requestPermissions(permissions, runnable);
    }

    public void release() {
        if (mFragment != null) {
            mFragment.release();
        }
    }

}