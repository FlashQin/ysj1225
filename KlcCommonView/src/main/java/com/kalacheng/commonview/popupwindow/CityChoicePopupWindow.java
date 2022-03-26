package com.kalacheng.commonview.popupwindow;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.commonview.R;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libuser.httpApi.HttpApiHome;
import com.kalacheng.libuser.model.AppArea;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.PinyinUtils;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.city_select.CityAdapter;
import com.kalacheng.util.utils.city_select.CityBean;
import com.kalacheng.util.utils.city_select.SideBarView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Administrator
 * @date: 2020/9/7
 * @info: 附近的人筛选城市
 */
public class CityChoicePopupWindow extends PopupWindow {

    private Context context;
    private String choiceCityName = "";
    private OnDismissListener onDismissListener;
    private List beanList;
    private List<List<CityBean>> list = new ArrayList<>();
    private CityAdapter adapter;
    private TextView tvClear;

    /**
     * 动态设置 索引首字母
     */
    private ArrayList<String> numSet = new ArrayList<>();

    public CityChoicePopupWindow(Context context, OnDismissListener onDismissListener) {
        this.context = context;
        this.onDismissListener = onDismissListener;
        setContentView(LayoutInflater.from(context).inflate(R.layout.popupwindow_city_choice_layout, null));
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setFocusable(true);
        setOutsideTouchable(false);
        setClippingEnabled(false);
        ColorDrawable dw = new ColorDrawable(0x33000000);
        setBackgroundDrawable(dw);

        initNearbySearch();
        initCity();
    }

    private void initCity() {

        //手机当前定位位置
        TextView tvCity = getContentView().findViewById(R.id.tv_city);
        TextView tvAddress = getContentView().findViewById(R.id.tv_address);
        tvClear = getContentView().findViewById(R.id.tvClear);
        tvCity.setText(SpUtil.getInstance().getSharedPreference(SpUtil.CITY, "").toString());
        tvAddress.setText(SpUtil.getInstance().getSharedPreference(SpUtil.ADDRESS, "").toString());

        LinearLayout nowCityTag = getContentView().findViewById(R.id.now_city_tag);

        if (tvCity.getText().length() > 0) {
            nowCityTag.setTag(tvCity.getText().toString());
            nowCityTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    String cityName = String.valueOf(v.getTag());
                    setCityNameDate(cityName);
                    tvClear.setVisibility(View.VISIBLE);
                }
            });
        }


        TextView tvLetter = getContentView().findViewById(R.id.tv_letter);
        SideBarView sideBar = getContentView().findViewById(R.id.sidebar);
        if (TextUtils.isEmpty(choiceCityName)) {
            ((TextView) getContentView().findViewById(R.id.titleView)).setText("选择城市");
            tvClear.setVisibility(View.GONE);
        } else {
            if (choiceCityName.length() <= 4) {
                ((TextView) getContentView().findViewById(R.id.titleView)).setText("当前【" + choiceCityName + "】");
            } else {
                ((TextView) getContentView().findViewById(R.id.titleView)).setText("当前【" + choiceCityName.substring(0, 4) + "】");
            }
            tvClear.setVisibility(View.VISIBLE);
        }

        RecyclerView recyclerCity = getContentView().findViewById(R.id.recycler_city);
        final LinearLayoutManager manager = new LinearLayoutManager(context);
        adapter = new CityAdapter(context, list);
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
                dismiss();
                choiceCityName = cityName;
                setCityNameDate(cityName);
                tvClear.setText("全部");
                tvClear.setVisibility(View.VISIBLE);
            }
        });
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                setCityNameDate("");
                tvClear.setVisibility(View.GONE);
            }
        });
        getContentView().findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (null != onDismissListener) {
                    onDismissListener.onDismiss();
                }
            }
        });
    }

    private void initNearbySearch() {
        try {
            InputStream in = context.getResources().getAssets().open("city.xml");
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
                    adapter.setCityAdapter(list);
                }
            }
        });
    }

    /**
     * 设置 城市名称数据
     */
    private void setCityNameDate(String cityName) {
        if (null != onDismissListener) {
            onDismissListener.onChoiceCity(cityName);
        }
    }

    /**
     * 下拉内容展示
     *
     * @param anchor 控件view
     */
    public void show(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = DpUtil.getScreenHeight() - rect.bottom;
            setHeight(h);
        }
        showAsDropDown(anchor);
    }

    public interface OnDismissListener {
        void onDismiss();

        void onChoiceCity(String item);
    }

}