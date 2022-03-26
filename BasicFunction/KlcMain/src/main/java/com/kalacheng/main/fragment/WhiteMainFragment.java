package com.kalacheng.main.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.adapter.FragmentAdapter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.commonview.activity.BaseMainActivity;
import com.kalacheng.base.event.SignEvent;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.main.MainFragmentConfig;
import com.kalacheng.main.R;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.view.ViewPagerIndicator2;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class WhiteMainFragment extends BaseFragment {

    private String TAG = WhiteMainFragment.class.getSimpleName();

    private Context mContext;
    private ViewPager viewPager;
    private View isSingInView;
    private ViewPagerIndicator2 indicator2;
    private List<Fragment> mFragments = new ArrayList<>();

    public WhiteMainFragment(Context mContext, ViewGroup mParentView) {
        this.mContext = mContext;
        this.mParentView = mParentView;
    }

    public WhiteMainFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_white_main;
    }

    public String uid;

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        uid = String.valueOf(HttpClient.getUid());

        indicator2 = mParentView.findViewById(R.id.indicator2);
        viewPager = mParentView.findViewById(R.id.viewpager);
        isSingInView = mParentView.findViewById(R.id.isSingInView);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mFragments.size(); i++) {
                    if (i == position) {
                        if (mFragments.get(i) instanceof BaseFragment) {
                            ((BaseFragment) mFragments.get(i)).setShowed(true);
                            ((BaseFragment) mFragments.get(i)).loadData();
                        }
                    } else {
                        if (mFragments.get(i) instanceof BaseFragment) {
                            ((BaseFragment) mFragments.get(i)).setShowed(false);
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        indicator2.setTitles(MainFragmentConfig.MAININDICATOR);
        if (MainFragmentConfig.MAINCOMPONENT.length > 5) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) indicator2.getLayoutParams();
            layoutParams.setMargins(0, DpUtil.dp2px(28), DpUtil.dp2px(50), 0);
        }
        for (int i = 0; i < MainFragmentConfig.MAINCOMPONENT.length; i++) {
            Class type = MainFragmentConfig.MAINCOMPONENT[i];
            try {
                mFragments.add((Fragment) type.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ImageView btn_search = mParentView.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                ARouter.getInstance().build(ARouterPath.Search).navigation();
            }
        });

        if (ConfigUtil.getIntValue(com.kalacheng.commonview.R.integer.whiteMainFragmentRightStyle) == 0) {
            FragmentAdapter mAdapter = new FragmentAdapter(getChildFragmentManager(), mFragments);
            viewPager.setAdapter(mAdapter);
            viewPager.setCurrentItem(1);
            viewPager.setOffscreenPageLimit(MainFragmentConfig.MAINCOMPONENT.length);
            indicator2.setViewPager(viewPager);
            indicator2.setSelect(1);

            //签到
            ImageView btn_signIn = mParentView.findViewById(R.id.btn_signIn);
            btn_signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    BaseMainActivity baseMainActivity = (BaseMainActivity) getActivity();
                    if (baseMainActivity != null) {
                        baseMainActivity.showSignInDialog();
                    }
                }
            });
            isSingInView.setVisibility(View.VISIBLE);
//            getSignInfo();
        } else if (ConfigUtil.getIntValue(com.kalacheng.commonview.R.integer.whiteMainFragmentRightStyle) == 1) {
            FragmentAdapter mAdapter = new FragmentAdapter(getChildFragmentManager(), mFragments);
            viewPager.setAdapter(mAdapter);
            viewPager.setOffscreenPageLimit(MainFragmentConfig.MAINCOMPONENT.length);

            indicator2.setVisibility(View.GONE);
            mParentView.findViewById(R.id.titleView).setVisibility(View.VISIBLE);
            mParentView.findViewById(R.id.btn_search).findViewById(R.id.btn_search).setVisibility(View.GONE);
            mParentView.findViewById(R.id.btn_signIn).setVisibility(View.GONE);
            mParentView.findViewById(R.id.ivVoiceStart).setVisibility(View.VISIBLE);
            mParentView.findViewById(R.id.ivVoiceStart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    ((BaseMainActivity) mContext).voiceClick();
                }
            });
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResumeFragment() {
        super.onResumeFragment();
        if (mShowed && viewPager != null && mFragments.size() > viewPager.getCurrentItem()) {
            if (mFragments.get(viewPager.getCurrentItem()) instanceof BaseFragment) {
                ((BaseFragment) mFragments.get(viewPager.getCurrentItem())).onResumeFragment();
            }
        }
    }

    @Override
    public void onPauseFragment() {
        super.onPauseFragment();
        if (mShowed && viewPager != null && mFragments.size() > viewPager.getCurrentItem()) {
            if (mFragments.get(viewPager.getCurrentItem()) instanceof BaseFragment) {
                ((BaseFragment) mFragments.get(viewPager.getCurrentItem())).onPauseFragment();
            }
        }
    }

    @Override
    public void setShowed(boolean showed) {
        super.setShowed(showed);
        if (viewPager != null && mFragments.size() > viewPager.getCurrentItem()) {
            if (mFragments.get(viewPager.getCurrentItem()) instanceof BaseFragment) {
                ((BaseFragment) mFragments.get(viewPager.getCurrentItem())).setShowed(showed);
            }
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void refreshData() {
        super.refreshData();
        if (mFragments != null && mFragments.size() > viewPager.getCurrentItem()) {
            if (mFragments.get(viewPager.getCurrentItem()) instanceof BaseFragment) {
                ((BaseFragment) mFragments.get(viewPager.getCurrentItem())).refreshData();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSignEvent(SignEvent event) {
        isSingInView.setVisibility(View.GONE);
    }

}
