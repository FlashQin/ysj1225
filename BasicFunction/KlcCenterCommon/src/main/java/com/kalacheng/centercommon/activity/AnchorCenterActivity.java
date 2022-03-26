package com.kalacheng.centercommon.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.fragment.MeAnchorFragment;
import com.kalacheng.frame.config.ARouterPath;

/**
 * 认证中心，短视频
 */
@Route(path = ARouterPath.AnchorCenterActivity)
public class AnchorCenterActivity extends BaseActivity {
    private MeAnchorFragment meAnchorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anchor_center);
        initView(savedInstanceState);
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    private void initView(Bundle savedInstanceState) {
        setTitle("认证中心");

        if (savedInstanceState != null) {
            meAnchorFragment = (MeAnchorFragment) getSupportFragmentManager().findFragmentByTag("meAnchorFragment");
            getSupportFragmentManager().beginTransaction()
                    .show(meAnchorFragment)
                    .commit();
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            meAnchorFragment = new MeAnchorFragment();
            transaction.add(R.id.layoutAnchorCenter, meAnchorFragment, "meAnchorFragment");
            transaction.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (meAnchorFragment != null) {
            outState.putString("lastVisibleFragment", meAnchorFragment.getTag());
        }
    }
}
