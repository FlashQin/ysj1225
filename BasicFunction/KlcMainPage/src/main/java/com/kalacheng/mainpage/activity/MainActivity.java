package com.kalacheng.mainpage.activity;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.base.base.BaseMVVMFragment;
import com.kalacheng.commonview.activity.BaseMainActivity;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.main.MainFragmentConfig;
import com.kalacheng.mainpage.R;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.LruJsonCache;
import com.kalacheng.util.view.TabButtonGroup;

import java.util.ArrayList;
import java.util.List;


@Route(path = ARouterPath.MainActivity)
public class MainActivity extends BaseMainActivity {
    private List<Fragment> mFragments = new ArrayList<>();
    private ArrayList<String> mTags = new ArrayList<>();
    private LruJsonCache cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cache = LruJsonCache.get(this);
        cache.remove(LiveConstants.Cache_SmallMessage);
        cache.remove(LiveConstants.Cache_SmallStatus);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            mTags = savedInstanceState.getStringArrayList("tags");
            if (mTags != null) {
                for (String tag : mTags) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Fragment fragment = fragmentManager.findFragmentByTag(tag);
                    if (fragment != null) {
                        transaction.remove(fragment);
                        transaction.commit();
                    }
                }
            }
        }
        if (mTags != null) {
            mTags.clear();
        }
        for (int i = 0; i < MainFragmentConfig.FRAGMENTCOMPONENT.length; i++) {
            Class type = MainFragmentConfig.FRAGMENTCOMPONENT[i];
            try {
                Class[] parameterTypes = {Context.class, ViewGroup.class};
                java.lang.reflect.Constructor constructor = type.getConstructor(parameterTypes);
                Object[] parameters = {this, findViewById(R.id.fl_fragment)};
                constructor.newInstance(parameters);
                Fragment fragment = (Fragment) constructor.newInstance(parameters);
                mFragments.add(fragment);
                mTags.add(fragment.getClass().getSimpleName());
            } catch (Exception e) {
                Log.i("Exception", e.getMessage());
            }
        }
        showFragment(mFragments.get(0), R.id.fl_fragment);
        ((BaseFragment) mFragments.get(0)).setShowed(true);
        ((BaseFragment) mFragments.get(0)).loadData();

        if (ConfigUtil.getIntValue(R.integer.mainStatusBarWhite0) == 0) {
            setStatusBarWhite(true);
        } else if (ConfigUtil.getIntValue(R.integer.mainStatusBarWhite0) == 1) {
            setStatusBarWhite(false);
        }

        ((TabButtonGroup) findViewById(R.id.tab_group)).setTabButtonClickListener(new TabButtonGroup.TabButtonClickListener() {
            @Override
            public void onTabButtonClick(int position) {
                switch (position) {
                    case 0:
                        if (ConfigUtil.getIntValue(R.integer.mainStatusBarWhite0) == 0) {
                            setStatusBarWhite(true);
                        } else if (ConfigUtil.getIntValue(R.integer.mainStatusBarWhite0) == 1) {
                            setStatusBarWhite(false);
                        }
                        break;
                    case 1:
                        if (ConfigUtil.getIntValue(R.integer.mainStatusBarWhite1) == 0) {
                            setStatusBarWhite(true);
                        } else if (ConfigUtil.getIntValue(R.integer.mainStatusBarWhite1) == 1) {
                            setStatusBarWhite(false);
                        }
                        break;
                    case 2:
                        if (ConfigUtil.getIntValue(R.integer.mainStatusBarWhite2) == 0) {
                            setStatusBarWhite(true);
                        } else if (ConfigUtil.getIntValue(R.integer.mainStatusBarWhite2) == 1) {
                            setStatusBarWhite(false);
                        }
                        break;
                    case 3:
                        if (ConfigUtil.getIntValue(R.integer.mainStatusBarWhite3) == 0) {
                            setStatusBarWhite(true);
                        } else if (ConfigUtil.getIntValue(R.integer.mainStatusBarWhite3) == 1) {
                            setStatusBarWhite(false);
                        }
                        break;
                    case 4:
                        if (ConfigUtil.getIntValue(R.integer.mainStatusBarWhite4) == 0) {
                            setStatusBarWhite(true);
                        } else if (ConfigUtil.getIntValue(R.integer.mainStatusBarWhite4) == 1) {
                            setStatusBarWhite(false);
                        }
                        break;
                    default:
                        break;
                }

                if (position < mFragments.size()) {
                    showFragment(mFragments.get(position), R.id.fl_fragment);
                    for (int i = 0; i < mFragments.size(); i++) {
                        if (i == position) {
                            if (mFragments.get(i) instanceof BaseFragment) {
                                ((BaseFragment) mFragments.get(i)).setShowed(true);
                            } else if (mFragments.get(i) instanceof BaseMVVMFragment) {
                                ((BaseMVVMFragment) mFragments.get(i)).setShowed(true);
                            }
                        } else {
                            if (mFragments.get(i) instanceof BaseFragment) {
                                ((BaseFragment) mFragments.get(i)).setShowed(false);
                            } else if (mFragments.get(i) instanceof BaseMVVMFragment) {
                                ((BaseMVVMFragment) mFragments.get(i)).setShowed(false);
                            }
                        }
                    }
                }
            }
        });

        if (ConfigUtil.getBoolValue(R.bool.showMainStartViewMargin)) {
            ImageView ivStart = (ImageView) findViewById(R.id.btn_start);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivStart.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, DpUtil.dp2px(10));
        }

    }

    @Override
    protected void onAllUnReadCount() {
        super.onAllUnReadCount();
        int allCount = notifyNum + msgNum;
        boolean isShow = allCount > 0;
        allCount = Math.min(allCount, 99);
        TextView allCountTv = findViewById(R.id.allCountTv);
        allCountTv.setVisibility(isShow ? View.VISIBLE : View.GONE);
        allCountTv.setText(String.valueOf(allCount));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFragments != null && mFragments.size() > 0) {
            for (int i = 0; i < mFragments.size(); i++) {
                if (mFragments.get(i) instanceof BaseFragment) {
                    ((BaseFragment) mFragments.get(i)).onResumeFragment();
                } else if (mFragments.get(i) instanceof BaseMVVMFragment) {
                    ((BaseMVVMFragment) mFragments.get(i)).onResumeFragment();
                }
            }
        }
    }

    @Override
    public void startOnClick(View v) {
        super.startOnClick(v);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        if (mFragments != null && mFragments.size() > 0) {
            for (int i = 0; i < mFragments.size(); i++) {
                if (mFragments.get(i) instanceof BaseFragment) {
                    ((BaseFragment) mFragments.get(i)).onPauseFragment();
                } else if (mFragments.get(i) instanceof BaseMVVMFragment) {
                    ((BaseMVVMFragment) mFragments.get(i)).onPauseFragment();
                }
            }
        }
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("tags", mTags);
    }
}
