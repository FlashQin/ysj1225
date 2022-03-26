package com.kalacheng.centercommon.fragment;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.adapter.FragmentAdapter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.centercommon.R;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.shortvideo.activity.MyViewFragment;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DialogUtil;
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

public class PersonInfoFragment extends BaseFragment implements View.OnClickListener {
    private ViewPager viewPager;
    private long userId;
    private TextView textView;
    private ImageView ivRight;
    private ApiUserInfo apiUserInfo;
    private Class[] mClasses;
    private String[] mIndicator;
    private List<Fragment> mFragments = new ArrayList<>();

    public PersonInfoFragment() {
    }

    public PersonInfoFragment(long userId) {
        this.userId = userId;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_person_info;
    }

    @Override
    protected void initView() {
        viewPager = mParentView.findViewById(R.id.viewpager);
        textView = mParentView.findViewById(R.id.titleView);
        ivRight = mParentView.findViewById(R.id.iv_right);
        if (userId == HttpClient.getUid()) {
            ivRight.setVisibility(View.VISIBLE);
            ivRight.setImageResource(R.mipmap.icon_edit);
            ivRight.setOnClickListener(this);
        } else {
            ivRight.setVisibility(View.VISIBLE);
            ivRight.setImageResource(R.mipmap.icon_more_button);
            ivRight.setOnClickListener(this);
        }

        if (ConfigUtil.getBoolValue(R.bool.isVideo)) {
            mClasses = new Class[]{BasicInfoFragment.class, AnchorInfoFragment.class, MyViewFragment.class};
        } else if (ConfigUtil.getBoolValue(R.bool.containShortVideo)) {
            mClasses = new Class[]{BasicInfoFragment.class, AnchorInfoFragment.class, MyViewFragment.class, DynamicInfoFragment.class};
        } else {
            mClasses = new Class[]{BasicInfoFragment.class, AnchorInfoFragment.class, DynamicInfoFragment.class};
        }

        for (int i = 0; i < mClasses.length; i++) {
            Class type = mClasses[i];
            try {
                Class[] parameterTypes = {Long.class};
                java.lang.reflect.Constructor constructor = type.getConstructor(parameterTypes);
                Object[] parameters = {userId};
                constructor.newInstance(parameters);
                mFragments.add((Fragment) constructor.newInstance(parameters));
            } catch (Exception e) {
                Log.i("Exception", e.getMessage());
            }
        }

        FragmentAdapter mAdapter = new FragmentAdapter(getChildFragmentManager(), mFragments);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setCurrentItem(0);
    }

    @Override
    protected void initData() {
        HttpApiAppUser.personCenter(-1, -1, userId, new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && null != retModel) {
                    apiUserInfo = retModel;
                    if (retModel.role != 1) {
                        //观众
                        if (ConfigUtil.getBoolValue(R.bool.isVideo)) {
                            mIndicator = new String[]{WordUtil.getString(R.string.info),
                                    WordUtil.getString(R.string.user),
                                    WordUtil.getString(R.string.video)};
                        } else if (ConfigUtil.getBoolValue(R.bool.containShortVideo)) {
                            mIndicator = new String[]{WordUtil.getString(R.string.info),
                                    WordUtil.getString(R.string.user),
                                    WordUtil.getString(R.string.video),
                                    WordUtil.getString(R.string.main_trend)};
                        } else {
                            mIndicator = new String[]{WordUtil.getString(R.string.info),
                                    WordUtil.getString(R.string.user),
                                    WordUtil.getString(R.string.main_trend)};
                        }
                    } else {
                        if (ConfigUtil.getBoolValue(R.bool.isVideo)) {
                            mIndicator = new String[]{WordUtil.getString(R.string.info),
                                    WordUtil.getString(R.string.anchor),
                                    WordUtil.getString(R.string.video)};
                        } else if (ConfigUtil.getBoolValue(R.bool.containShortVideo)) {
                            mIndicator = new String[]{WordUtil.getString(R.string.info),
                                    WordUtil.getString(R.string.anchor),
                                    WordUtil.getString(R.string.video),
                                    WordUtil.getString(R.string.main_trend)};
                        } else {
                            mIndicator = new String[]{WordUtil.getString(R.string.info),
                                    WordUtil.getString(R.string.anchor),
                                    WordUtil.getString(R.string.main_trend)};
                        }
                    }
                    initMagicIndicator();
                    textView.setText(retModel.username);
                }
            }
        });
    }

    private void initMagicIndicator() {
        MagicIndicator magicIndicator = (MagicIndicator) mParentView.findViewById(R.id.magic_indicator);
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mIndicator == null ? 0 : mIndicator.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mIndicator[index]);
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setNormalColor(Color.parseColor("#888888"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#222222"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setLineWidth(UIUtil.dip2px(context, 15));
                indicator.setColors(Color.parseColor("#B540FF"));
                return indicator;
            }

//            @Override
//            public float getTitleWeight(Context context, int index) {
//                if (index == 0) {
//                    return 2.0f;
//                } else if (index == 1) {
//                    return 1.2f;
//                } else {
//                    return 1.0f;
//                }
//            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.iv_right) {
            // 自己的主页
            if (userId == HttpClient.getUid()) {
                if (null != apiUserInfo) {
                    ARouter.getInstance().build(ARouterPath.EidtInformation).withParcelable("UserInfoDto", apiUserInfo).navigation();
                }
            } else {
                // 举报
                DialogUtil.showStringArrayDialog2(getActivity(), new String[]{"举报"}, new DialogUtil.ChannelDialogCallback() {
                    @Override
                    public void onItemClick(long id, String text) {
                        if (text.equals("举报")) {
                            ARouter.getInstance().build(ARouterPath.VideoReport).withLong(ARouterValueNameConfig.USERID, userId).navigation();
                        }
                    }
                });
            }

        }
    }
}
