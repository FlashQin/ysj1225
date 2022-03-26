package com.kalacheng.main.fragment;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.adapter.FragmentAdapter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.commonview.activity.BaseMainActivity;
import com.kalacheng.commonview.jguangIm.RequestChatEvent;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libuser.httpApi.HttpApiHome;
import com.kalacheng.libuser.model.AppArea;
import com.kalacheng.libuser.model.AppUser;
import com.kalacheng.main.MainFragmentConfig;
import com.kalacheng.main.R;
import com.kalacheng.message.fragment.OnlineUserFragment;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.PinyinUtils;
import com.kalacheng.util.utils.city_select.CityAdapter;
import com.kalacheng.util.utils.city_select.CityBean;
import com.kalacheng.util.utils.city_select.SideBarView;
import com.kalacheng.util.view.ViewPagerIndicator2;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FristLoveMainFragment extends BaseFragment {
    Context mContext;
    List<List<CityBean>> list = new ArrayList<>();
    private List beanList;
    private ViewPager viewPager;
    private String cityName;
    private ViewPagerIndicator2 indicator2;
    private List<Fragment> mFragments = new ArrayList<>();

    private Class[] classes;
    private String[] titleString;

    private LinearLayout ll_free_call;
    private ImageView close;
    private TextView tv_time, tv_count;
    private ApiUserInfo apiUserInfo;

    public FristLoveMainFragment(Context mContext, ViewGroup mParentView) {
        this.mContext = mContext;
    }

    public FristLoveMainFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fristlove_main;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        tv_time = mParentView.findViewById(R.id.tv_time);
        tv_count = mParentView.findViewById(R.id.tv_count);
        ll_free_call = mParentView.findViewById(R.id.ll_free_call);
        close = mParentView.findViewById(R.id.close);

        apiUserInfo = SpUtil.getInstance().getModel("UserInfo", ApiUserInfo.class);
        if (apiUserInfo.sex == 1) {
            if (ConfigUtil.getBoolValue(R.bool.isDynamicSetting1V1Main)) {
                titleString = MainFragmentConfig.ONEMANINDICATOR;
                classes = MainFragmentConfig.ONEMANTCOMPONENT;
            } else {
                titleString = new String[]{
                        WordUtil.getString(R.string.friends),
                        WordUtil.getString(R.string.robchat),
                        WordUtil.getString(R.string.askchat),
                        WordUtil.getString(R.string.main_near2)};
                classes = new Class[]{One2OneNewFragment.class, FristLoveRobManChatFragment.class, FristLoveSeekChatFragment.class, OnlineUserFragment.class};
            }
        } else {
            if (ConfigUtil.getBoolValue(R.bool.isDynamicSetting1V1Main)) {
                titleString = MainFragmentConfig.ONEWOMANMANINDICATOR;
                classes = MainFragmentConfig.ONEWOMANMANTCOMPONENT;
            } else {
                titleString = new String[]{
                        WordUtil.getString(R.string.friends),
                        WordUtil.getString(R.string.chatlocal),
                        WordUtil.getString(R.string.robchat),
                        WordUtil.getString(R.string.main_near2)};
                classes = new Class[]{One2OneNewFragment.class, ChatLocalFragment.class, FristLoveRobWomanChatFragment.class, OnlineUserFragment.class};
            }
        }

        indicator2 = mParentView.findViewById(R.id.indicator2);
        indicator2.setOnTabClickListener(new ViewPagerIndicator2.OnTabClickListener() {
            @Override
            public void onTabClick(int position, String name) {
                if (indicator2.getTitle().get(position).equals(WordUtil.getString(R.string.main_near)) && viewPager.getCurrentItem() == position) {
                    showCityDialog();
                }
            }
        });
        viewPager = mParentView.findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (indicator2.getTitle().get(position).equals(WordUtil.getString(R.string.main_video)))
//                    LiveBundle.getInstance().sendSimpleMsg("LifeResume", MainVideoFragment.VideoType.RECOMMEND);
//                else
//                    LiveBundle.getInstance().sendSimpleMsg("LifePause", MainVideoFragment.VideoType.RECOMMEND);

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
        ImageView ivPublishMedia = mParentView.findViewById(R.id.ivPublishMedia);
        ivPublishMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                BaseMainActivity activity = (BaseMainActivity) getActivity();
                if (activity != null) {
                    activity.startOnClick(view);
                }
            }
        });
        if (ConfigUtil.getIntValue(R.integer.firstLoveMainRightType) == 0) {
            btn_search.setVisibility(View.VISIBLE);
            ivPublishMedia.setVisibility(View.GONE);
        } else if (ConfigUtil.getIntValue(R.integer.firstLoveMainRightType) == 1) {
            btn_search.setVisibility(View.GONE);
            ivPublishMedia.setVisibility(View.VISIBLE);
        }

        indicator2.setTitles(titleString);
        if (classes.length > 5) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) indicator2.getLayoutParams();
            layoutParams.setMargins(0, DpUtil.dp2px(28), DpUtil.dp2px(50), 0);
        }
        for (int i = 0; i < classes.length; i++) {
            Class type = classes[i];
            try {
                mFragments.add((Fragment) type.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        FragmentAdapter mAdapter = new FragmentAdapter(getChildFragmentManager(), mFragments);
        viewPager.setAdapter(mAdapter);

        if (SpUtil.getInstance().getModel("UserInfo", ApiUserInfo.class).sex == 1) {
            viewPager.setCurrentItem(0);
            viewPager.setOffscreenPageLimit(classes.length);
            indicator2.setViewPager(viewPager);
            indicator2.setSelect(0);
        } else {
            viewPager.setCurrentItem(0);
            viewPager.setOffscreenPageLimit(classes.length);
            indicator2.setViewPager(viewPager);
            indicator2.setSelect(0);
        }

        viewPager.setCurrentItem(ConfigUtil.getIntValue(R.integer.firstLoveMainPosition));

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_free_call.setVisibility(View.GONE);
                SpUtil.getInstance().put(SpUtil.FREE_CALL_two, true);
            }
        });

        if (ConfigUtil.getBoolValue(com.kalacheng.centercommon.R.bool.containOne2One) && !(Boolean) SpUtil.getInstance().getSharedPreference(SpUtil.FREE_CALL_two, false)) {
            getFreeCall();
        }

    }

    private void getFreeCall() {
        HttpApiAppUser.getUserByregister(HttpClient.getUid(), new HttpApiCallBack<AppUser>() {
            @Override
            public void onHttpRet(int code, String msg, AppUser retModel) {
                if (code == 1 && null != retModel) {
                    if (retModel.registerCallSecond > 0 && retModel.registerCallTime > 0) {
                        ll_free_call.setVisibility(View.VISIBLE);
                        tv_time.setText("" + retModel.registerCallTime);
                        tv_count.setText("分钟  X" + retModel.registerCallSecond);
                    }
                }
            }
        });
    }

    private void showCityDialog() {
        final Dialog dialog = DialogUtil.getBaseDialog(mContext, R.style.dialog, R.layout.dialog_city_select, true, true);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);//宽高最大
        RecyclerView recyclerCity = dialog.findViewById(R.id.recycler_city);
        TextView tvLetter = dialog.findViewById(R.id.tv_letter);
        SideBarView sideBar = dialog.findViewById(R.id.sidebar);
        if (TextUtils.isEmpty(cityName)) {
            ((TextView) dialog.findViewById(R.id.titleView)).setText("选择城市");
            dialog.findViewById(R.id.tvClear).setVisibility(View.GONE);
        } else {
            if (cityName.length() <= 4) {
                ((TextView) dialog.findViewById(R.id.titleView)).setText("当前【" + cityName + "】");
            } else {
                ((TextView) dialog.findViewById(R.id.titleView)).setText("当前【" + cityName.substring(0, 4) + "】");
            }
            dialog.findViewById(R.id.tvClear).setVisibility(View.VISIBLE);
        }
        final CityAdapter adapter = new CityAdapter(getActivity(), list);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerCity.setLayoutManager(manager);
        recyclerCity.setAdapter(adapter);
        sideBar.setTextView(tvLetter);
        sideBar.setOnTouchingLetterChangedListener(new SideBarView.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(int position) {
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);//当前的manager
//                    manager.setStackFromEnd(true);//让最后添加的item始终显示在RecycleView中
                }
            }
        });
        adapter.setListener(new CityAdapter.MoveToZeroListener() {
            @Override
            public void cityName(String cityName) {
                dialog.dismiss();
                FristLoveMainFragment.this.cityName = cityName;
                MeBundle.getInstance().sendSimpleMsg(MeBundle.City, cityName);
//                Toast.makeText(getActivity(), cityName, Toast.LENGTH_LONG).show();
            }
        });
        dialog.findViewById(R.id.tvClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                FristLoveMainFragment.this.cityName = "";
                MeBundle.getInstance().sendSimpleMsg(MeBundle.City, "");
            }
        });
        dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    @Override
    protected void initData() {
        initNearbySearch();
    }

    private void initNearbySearch() {
        try {
            InputStream in = getResources().getAssets().open("city.xml");
            //使用PULL解析
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setInput(in, "UTF-8");
            //获取解析的标签的类型
            int type = xmlPullParser.getEventType();
            while (type != XmlPullParser.END_DOCUMENT) {
                switch (type) {
                    case XmlPullParser.START_TAG:
                        //获取开始标签的名字
                        String starttgname = xmlPullParser.getName();
                        if ("array".equals(starttgname)) {
                            beanList = new ArrayList<CityBean>();
                        } else if ("string".equals(starttgname)) {
                            String name = xmlPullParser.nextText();
                            String pinyin = PinyinUtils.getPingYin(name);
                            //讲名字set进实体中
                            //截取第一位即可，并转化为大写字母
                            String sortString = pinyin.substring(0, 1).toUpperCase();
                            beanList.add(new CityBean(name, sortString));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        String endtgname = xmlPullParser.getName();
                        if ("array".equals(endtgname)) {
                            list.add(beanList);
                        }
                        break;
                }
                type = xmlPullParser.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        HttpApiHome.getNearbySearchCondition(new HttpApiCallBackArr<AppArea>() {
            @Override
            public void onHttpRet(int code, String msg, List<AppArea> retModel) {
                if (code == 1) {
                    List hotCity = new ArrayList<CityBean>();
                    for (AppArea appArea : retModel) {
                        hotCity.add(new CityBean(appArea.name, ""));
                    }
                    list.add(0, hotCity);
                }
            }
        });
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
        if (showed && mContext != null && mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).setStatusBarWhite(false);
        }
        if (viewPager != null && mFragments.size() > viewPager.getCurrentItem()) {
            if (mFragments.get(viewPager.getCurrentItem()) instanceof BaseFragment) {
                ((BaseFragment) mFragments.get(viewPager.getCurrentItem())).setShowed(showed);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequestChatEvent(RequestChatEvent e) {
        try {
            if (null != e && !TextUtils.isEmpty(e.getTitle())) {
                for (int i = 0; i < titleString.length; i++) {
                    if (titleString[i].equals(e.getTitle())) {
                        viewPager.setCurrentItem(i);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
