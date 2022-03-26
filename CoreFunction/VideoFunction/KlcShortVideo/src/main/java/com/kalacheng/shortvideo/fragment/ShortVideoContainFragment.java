package com.kalacheng.shortvideo.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.adapter.FragmentAdapter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.shortvideo.R;
import com.kalacheng.shortvideo.ShortVideoFragmentConfig;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.util.view.ScaleTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

public class ShortVideoContainFragment extends BaseFragment implements View.OnClickListener {

    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    public static String[] INDICATOR = {"关注", "推荐", "看点"};
    private ImageView ivRank;
    public static Class[] COMPONENT = new Class[]{ShortVideoFragment.class, ShortVideoFragment.class, ShortVideoPointFragment.class};
    private List<SimplePagerTitleView> listTitleView = new ArrayList<>();
    private List<LinePagerIndicator> listIndicator = new ArrayList<>();

    public ShortVideoContainFragment() {
    }

    public ShortVideoContainFragment(Context context, ViewGroup parentView) {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_short_video_contain;
    }

    @Override
    protected void initView() {
        ivRank = mParentView.findViewById(R.id.ivRank);
        if (ConfigUtil.getBoolValue(R.bool.isShortVideoVisibilityRank)){
            ivRank.setVisibility(View.VISIBLE);
            ivRank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    ARouter.getInstance().build(ARouterPath.RankListActivity).navigation();
                }
            });
        }

        if (null != ShortVideoFragmentConfig.FRAGMENTINDICATOR) {
            INDICATOR = ShortVideoFragmentConfig.FRAGMENTINDICATOR;
        }
        if (null != ShortVideoFragmentConfig.FRAGMENTCOMPONENT) {
            COMPONENT = ShortVideoFragmentConfig.FRAGMENTCOMPONENT;
        }

        for (int i = 0; i < COMPONENT.length; i++) {
            Class type = COMPONENT[i];
            try {
                java.lang.reflect.Constructor constructor = null;
                Object[] parameters = new Object[0];
                if (type == ShortVideoPointFragment.class) {
                    Class[] parameterTypes = {boolean.class};
                    constructor = type.getConstructor(parameterTypes);
                    parameters = new Object[]{true};
                }
                else if (type == ShortVideoFragment.class) {
                    Class[] parameterTypes = {boolean.class, int.class};
                    constructor = type.getConstructor(parameterTypes);
                    switch (INDICATOR[i]) {
                        case "关注":
                            parameters = new Object[]{false, 1};
                            break;
                        case "推荐":
                            parameters = new Object[]{false, 0};
                            break;
                        default:
                            break;
                    }
                }
                constructor.newInstance(parameters);
                fragmentList.add((Fragment) constructor.newInstance(parameters));
            } catch (Exception e) {
                Log.i("Exception", e.getMessage());
            }
        }

        viewPager = mParentView.findViewById(R.id.viewPager);
        FragmentAdapter adpater = new FragmentAdapter(getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(adpater);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(ConfigUtil.getIntValue(R.integer.shortVideoPosition));
        ((BaseFragment) fragmentList.get(ConfigUtil.getIntValue(R.integer.shortVideoPosition))).setShowed(true);
        ((BaseFragment) fragmentList.get(ConfigUtil.getIntValue(R.integer.shortVideoPosition))).loadData();

        // 如果初始化是看点（白色布局） 设置状态栏为深色字体
        if (INDICATOR[ConfigUtil.getIntValue(R.integer.shortVideoPosition)].equals(WordUtil.getString(R.string.short_point))){
            setWhiteStyle();
        }

        mParentView.findViewById(R.id.btn_search).setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < fragmentList.size(); i++) {
                    if (i == position) {
                        ((BaseFragment) fragmentList.get(i)).setShowed(true);
                        ((BaseFragment) fragmentList.get(i)).loadData();
                    } else {
                        ((BaseFragment) fragmentList.get(i)).setShowed(false);
                    }
                }
                // 如果初始化是看点(白色布局) 显示黑色 其他都显示白色
                if (INDICATOR[position].equals(WordUtil.getString(R.string.short_point))) {
                    setWhiteStyle();
                } else {
                    setBlackStyle();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    // 黑色风格
    private void setBlackStyle(){
        ((ImageView) mParentView.findViewById(R.id.btn_search)).setImageResource(R.mipmap.icon_main_search_white);
        BaseActivity baseActivity = (BaseActivity) getActivity();
        if (baseActivity != null) {
            baseActivity.setStatusBarWhite(true);
        }
        for (SimplePagerTitleView titleView : listTitleView) {
            titleView.setTextColor(Color.parseColor("#FFFFFF"));
            titleView.setNormalColor(Color.parseColor("#FFFFFF"));
            titleView.setSelectedColor(Color.parseColor("#FFFFFF"));
        }
        for (LinePagerIndicator indicator : listIndicator) {
            indicator.setColors(Color.parseColor("#FFFFFF"));
        }
    }

    // 白色风格
    private void setWhiteStyle(){
        ((ImageView) mParentView.findViewById(R.id.btn_search)).setImageResource(R.mipmap.icon_main_search);
        BaseActivity baseActivity = (BaseActivity) getActivity();
        if (baseActivity != null) {
            baseActivity.setStatusBarWhite(false);
        }
        for (int i = 0; i < listTitleView.size(); i++) {
            if (INDICATOR[ConfigUtil.getIntValue(R.integer.shortVideoPosition)].equals(WordUtil.getString(R.string.short_point))) {
                listTitleView.get(i).setTextColor(Color.parseColor("#333333"));
                listTitleView.get(i).setNormalColor(Color.parseColor("#333333"));
                listTitleView.get(i).setSelectedColor(Color.parseColor("#333333"));
            } else {
                listTitleView.get(i).setTextColor(Color.parseColor("#666666"));
                listTitleView.get(i).setNormalColor(Color.parseColor("#666666"));
                listTitleView.get(i).setSelectedColor(Color.parseColor("#666666"));
            }
        }
        for (LinePagerIndicator indicator : listIndicator) {
            indicator.setColors(Color.parseColor("#FF54A0"));
        }
    }

    @Override
    protected void initData() {
        initMagicIndicator();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_search) {
            if (CheckDoubleClick.isFastDoubleClick()) {
                return;
            }
            ARouter.getInstance().build(ARouterPath.Search).navigation();
        }
    }

    private void initMagicIndicator() {
        MagicIndicator magicIndicator = (MagicIndicator) mParentView.findViewById(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return INDICATOR.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(INDICATOR[index]);
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (viewPager.getCurrentItem() == index) {
                            if (CheckDoubleClick.isFastDoubleClick()) {
                                return;
                            }
                            ((BaseFragment) fragmentList.get(index)).refreshData();
                        } else {
                            viewPager.setCurrentItem(index);
                        }
                    }
                });
                listTitleView.add(simplePagerTitleView);
                // 如果初始化是看点(白色布局) 显示黑色 其他都显示白色
                if (INDICATOR[ConfigUtil.getIntValue(R.integer.shortVideoPosition)].equals(WordUtil.getString(R.string.short_point))) {
                    setWhiteStyle();
                } else {
                    setBlackStyle();
                }
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 3));
                indicator.setLineWidth(UIUtil.dip2px(context, 10));
                indicator.setColors(Color.parseColor("#FFFFFF"));
                indicator.setYOffset(UIUtil.dip2px(context, 5));
                listIndicator.add(indicator);
                return indicator;
            }

        });
        magicIndicator.setNavigator(commonNavigator);
        magicIndicator.onPageSelected(1);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @Override
    public void onResumeFragment() {
        super.onResumeFragment();
        if (mShowed && viewPager != null) {
            ((BaseFragment) fragmentList.get(viewPager.getCurrentItem())).onResumeFragment();
        }
        if (mShowed) {
            Activity activity = getActivity();
            if (activity != null) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        }
    }

    @Override
    public void onPauseFragment() {
        super.onPauseFragment();
        if (mShowed && viewPager != null) {
            ((BaseFragment) fragmentList.get(viewPager.getCurrentItem())).onPauseFragment();
        }
    }

    @Override
    public void setShowed(boolean showed) {
        super.setShowed(showed);
        if (viewPager != null) {
            ((BaseFragment) fragmentList.get(viewPager.getCurrentItem())).setShowed(showed);
        }
        if (showed) {
            Activity activity = getActivity();
            if (activity != null) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
            if (viewPager != null) {
                if (INDICATOR[viewPager.getCurrentItem()].equals(WordUtil.getString(R.string.short_point))) {
                    BaseActivity baseActivity = (BaseActivity) getActivity();
                    if (baseActivity != null) {
                        baseActivity.setStatusBarWhite(false);
                    }
                } else {
                    BaseActivity baseActivity = (BaseActivity) getActivity();
                    if (baseActivity != null) {
                        baseActivity.setStatusBarWhite(true);
                    }
                }
            }
        } else {
            Activity activity = getActivity();
            if (activity != null) {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        }
    }

}
