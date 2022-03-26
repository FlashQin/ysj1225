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
import com.kalacheng.base.adapter.FragmentAdapter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.commonview.activity.BaseMainActivity;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libuser.httpApi.HttpApiHome;
import com.kalacheng.libuser.model.AppArea;
import com.kalacheng.main.MainFragmentConfig;
import com.kalacheng.main.R;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.PinyinUtils;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.util.utils.city_select.CityAdapter;
import com.kalacheng.util.utils.city_select.CityBean;
import com.kalacheng.util.utils.city_select.SideBarView;
import com.kalacheng.util.view.ViewPagerIndicator2;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Administrator
 * @date: 2020/10/28
 * @info: 代替MainFragment的  （用于综合版）
 */
public class AllGroupFragment extends BaseFragment {
    private Context mContext;
    private List<List<CityBean>> list = new ArrayList<>();
    private List beanList;
    private ViewPager viewPager;
    private String cityName;
    private ViewPagerIndicator2 indicator2;
    private List<Fragment> mFragments = new ArrayList<>();
    private RelativeLayout title_bar;

    public AllGroupFragment(Context mContext, ViewGroup mParentView) {
        this.mContext = mContext;
        this.mParentView = mParentView;
    }

    public AllGroupFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initView() {
        title_bar = mParentView.findViewById(R.id.title_bar);
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
        ApiUserInfo apiUserInfo = SpUtil.getInstance().getModel("UserInfo", ApiUserInfo.class);
        if (apiUserInfo.sex == 1) {
            MainFragmentConfig.MAINCOMPONENT = new Class[]{
                    RecommendFragment.class,
                    One2OneNewFragment.class,
                    SquareFragment.class,
                    VoiceChatFragment.class,
                    LiveBuyFragment.class,
                    FristLoveRobManChatFragment.class,
                    FristLoveSeekChatFragment.class,
                    NearFragment.class};
            MainFragmentConfig.MAININDICATOR = new String[]{
                    WordUtil.getString(com.kalacheng.frame.R.string.recommend),
                    WordUtil.getString(R.string.friends),
                    WordUtil.getString(com.kalacheng.frame.R.string.living_chinese),
                    WordUtil.getString(com.kalacheng.frame.R.string.party),
                    WordUtil.getString(R.string.live_buy),
                    WordUtil.getString(R.string.robchat),
                    WordUtil.getString(R.string.askchat),
                    WordUtil.getString(com.kalacheng.frame.R.string.main_near)};
        } else {
            MainFragmentConfig.MAINCOMPONENT = new Class[]{
                    RecommendFragment.class,
                    One2OneNewFragment.class,
                    SquareFragment.class,
                    VoiceChatFragment.class,
                    LiveBuyFragment.class,
                    ChatLocalFragment.class,
                    FristLoveRobWomanChatFragment.class,
                    NearFragment.class};
            MainFragmentConfig.MAININDICATOR = new String[]{
                    WordUtil.getString(com.kalacheng.frame.R.string.recommend),
                    WordUtil.getString(R.string.friends),
                    WordUtil.getString(com.kalacheng.frame.R.string.living_chinese),
                    WordUtil.getString(com.kalacheng.frame.R.string.party),
                    WordUtil.getString(R.string.live_buy),
                    WordUtil.getString(R.string.chatlocal),
                    WordUtil.getString(R.string.robchat),
                    WordUtil.getString(com.kalacheng.frame.R.string.main_near)};
        }

        indicator2.setTitles(MainFragmentConfig.MAININDICATOR);
        for (int i = 0; i < MainFragmentConfig.MAINCOMPONENT.length; i++) {
            Class type = MainFragmentConfig.MAINCOMPONENT[i];
            try {
                mFragments.add((Fragment) type.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        FragmentAdapter mAdapter = new FragmentAdapter(getChildFragmentManager(), mFragments);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(MainFragmentConfig.MAINCOMPONENT.length);
        indicator2.setViewPager(viewPager);

        viewPager.setCurrentItem(ConfigUtil.getIntValue(R.integer.mainVideoPosition));
        indicator2.setSelect(ConfigUtil.getIntValue(R.integer.mainVideoPosition));
        ((BaseFragment) mFragments.get(ConfigUtil.getIntValue(R.integer.mainVideoPosition))).setShowed(true);
        ((BaseFragment) mFragments.get(ConfigUtil.getIntValue(R.integer.mainVideoPosition))).loadData();
    }

    private void showCityDialog() {
        final Dialog dialog = DialogUtil.getBaseDialog(mContext, R.style.dialog, R.layout.dialog_city_select, true, true);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);//宽高最大

        //手机当前定位位置
        TextView tvCity = dialog.findViewById(R.id.tv_city);
        TextView tvAddress = dialog.findViewById(R.id.tv_address);
        tvCity.setText(SpUtil.getInstance().getSharedPreference(SpUtil.CITY, "").toString());
        tvAddress.setText(SpUtil.getInstance().getSharedPreference(SpUtil.ADDRESS, "").toString());

        LinearLayout nowCityTag = dialog.findViewById(R.id.now_city_tag);

        if (tvCity.getText().length() > 0) {
            nowCityTag.setTag(tvCity.getText().toString());
            nowCityTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    String cityName = String.valueOf(v.getTag());
                    setCityNameDate(cityName);
                }
            });
        }


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


        if (numSet.size() > 0) {
            sideBar.setIndexText(numSet);
        }

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
                setCityNameDate(cityName);
//                Toast.makeText(getActivity(), cityName, Toast.LENGTH_LONG).show();
            }
        });
        dialog.findViewById(R.id.tvClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                setCityNameDate("");
            }
        });
        dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 设置 城市名称数据
     */
    private void setCityNameDate(String cityName) {
        AllGroupFragment.this.cityName = cityName;
        MeBundle.getInstance().sendSimpleMsg(MeBundle.City, cityName);
    }


    @Override
    protected void initData() {
        initNearbySearch();
    }

    /**
     * 动态设置 索引首字母
     */
    private ArrayList<String> numSet = new ArrayList<>();

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

            //动态设置 索引首字母
            numSet.clear();
            numSet.add("#");
            for (List<CityBean> cityBeans : list) {
                if (null != cityBeans && cityBeans.size() > 0) {
                    numSet.add(cityBeans.get(0).sortLetters);
                }
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
        if (viewPager != null && mFragments.size() > viewPager.getCurrentItem()) {
            if (mFragments.get(viewPager.getCurrentItem()) instanceof BaseFragment) {
                ((BaseFragment) mFragments.get(viewPager.getCurrentItem())).setShowed(showed);
            }
        }
    }

}
