package com.kalacheng.main.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.libuser.httpApi.HttpApiHome;
import com.kalacheng.libuser.model.AppArea;
import com.kalacheng.libuser.model.AppTabInfo;
import com.kalacheng.libuser.model.KeyValueDto;
import com.kalacheng.libuser.model.SearchConditionDto;
import com.kalacheng.main.R;
import com.kalacheng.util.adapter.SimpleGreyStorkeAdapter;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.bean.SimpleTextBean;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.util.view.ItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class O2OSearchDialog extends BaseDialogFragment {
    public static final String O20_SEARCH_ADDRESS = "o2oSearchAddress";
    public static final String O20_SEARCH_SEX = "o2oSearchSex";
    public static final String O20_SEARCH_TAB = "o2oSearchTab";

    private String address = "";
    private int sex;//性别 0：不限 1：男， 2女
    private String tabId = "";

    private SimpleGreyStorkeAdapter adapterCity;
    private SimpleGreyStorkeAdapter adapterSex;
    private SimpleGreyStorkeAdapter adapterInterest;

    private O2OSearchListener o2OSearchListener;

    private TextView tv_city, tv_address;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_one2one_select;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.BottomDialogStyle;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            address = bundle.getString(O20_SEARCH_ADDRESS, "");
            sex = bundle.getInt(O20_SEARCH_SEX, 0);
            tabId = bundle.getString(O20_SEARCH_TAB, "");
        }

        tv_city = mRootView.findViewById(R.id.tv_city);
        tv_address = mRootView.findViewById(R.id.tv_address);
        tv_city.setText(SpUtil.getInstance().getSharedPreference(SpUtil.CITY, "").toString());
        tv_address.setText(SpUtil.getInstance().getSharedPreference(SpUtil.ADDRESS, "").toString());

        RecyclerView recyclerViewCity = mRootView.findViewById(R.id.recycler_city);
        recyclerViewCity.setLayoutManager(new GridLayoutManager(getContext(), 4));
        adapterCity = new SimpleGreyStorkeAdapter();
        recyclerViewCity.setAdapter(adapterCity);
        recyclerViewCity.addItemDecoration(new ItemDecoration(getContext(), 0, 13, 10));

        RecyclerView recyclerViewSex = mRootView.findViewById(R.id.recycler_sex);
        recyclerViewSex.setLayoutManager(new GridLayoutManager(getContext(), 4));
        adapterSex = new SimpleGreyStorkeAdapter();
        recyclerViewSex.setAdapter(adapterSex);
        recyclerViewSex.addItemDecoration(new ItemDecoration(getContext(), 0, 13, 10));

        RecyclerView recyclerViewInterest = mRootView.findViewById(R.id.recycler_interest);
        recyclerViewInterest.setLayoutManager(new GridLayoutManager(getContext(), 4));
        adapterInterest = new SimpleGreyStorkeAdapter();
        recyclerViewInterest.setAdapter(adapterInterest);
        recyclerViewInterest.addItemDecoration(new ItemDecoration(getContext(), 0, 13, 10));

        adapterCity.setOnItemClickListener(new OnBeanCallback<SimpleTextBean>() {
            @Override
            public void onClick(SimpleTextBean bean) {
                if (bean.isChecked) {
                    bean.isChecked = false;
                } else {
                    for (SimpleTextBean simpleTextBean : adapterCity.getData()) {
                        simpleTextBean.isChecked = false;
                    }
                    bean.isChecked = true;
                }
                adapterCity.notifyDataSetChanged();
            }
        });

        adapterSex.setOnItemClickListener(new OnBeanCallback<SimpleTextBean>() {
            @Override
            public void onClick(SimpleTextBean bean) {
                if (bean.isChecked) {
                    bean.isChecked = false;
                } else {
                    for (SimpleTextBean simpleTextBean : adapterSex.getData()) {
                        simpleTextBean.isChecked = false;
                    }
                    bean.isChecked = true;
                }
                adapterSex.notifyDataSetChanged();
            }
        });

        adapterInterest.setOnItemClickListener(new OnBeanCallback<SimpleTextBean>() {
            @Override
            public void onClick(SimpleTextBean bean) {
                bean.isChecked = !bean.isChecked;
                adapterInterest.notifyDataSetChanged();
            }
        });

        mRootView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mRootView.findViewById(R.id.tvClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (SimpleTextBean bean : adapterCity.getData()) {
                    bean.isChecked = false;
                }
                for (SimpleTextBean bean : adapterSex.getData()) {
                    bean.isChecked = false;
                }
                for (SimpleTextBean bean : adapterInterest.getData()) {
                    bean.isChecked = false;
                }
                adapterCity.notifyDataSetChanged();
                adapterSex.notifyDataSetChanged();
                adapterInterest.notifyDataSetChanged();
            }
        });

        mRootView.findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address = "";
                for (SimpleTextBean bean : adapterCity.getData()) {
                    if (bean.isChecked) {
                        address = bean.name;
                        break;
                    }
                }
                sex = -1;
                for (SimpleTextBean bean : adapterSex.getData()) {
                    if (bean.isChecked) {
                        if (bean.name.trim().equals("男生") || bean.name.trim().equals("男神")) {
                            sex = 1;
                        } else if (bean.name.trim().equals("女生") || bean.name.trim().equals("女神")) {
                            sex = 2;
                        } else {
                            sex = 0;
                        }
                        break;
                    }
                }
                tabId = "";
                for (SimpleTextBean simpleTextBean : adapterInterest.getData()) {
                    if (simpleTextBean.isChecked) {
                        tabId = tabId + simpleTextBean.id + ",";
                    }
                }
                if (!TextUtils.isEmpty(tabId) && tabId.endsWith(",")) {
                    tabId = tabId.substring(0, tabId.length() - 1);
                }
                if (o2OSearchListener != null) {
                    o2OSearchListener.onSearch(address, sex, tabId);
                }
                dismiss();
            }
        });

        initO2OSearch();
    }

    private void initO2OSearch() {
        HttpApiHome.getO2OSearchCondition(new HttpApiCallBack<SearchConditionDto>() {
            @Override
            public void onHttpRet(int code, String msg, SearchConditionDto retModel) {
                if (code == 1) {
                    if (retModel != null) {
                        if (retModel.hotCitys != null) {
                            List<SimpleTextBean> hotCityList = new ArrayList<>();
                            for (AppArea appArea : retModel.hotCitys) {
                                hotCityList.add(new SimpleTextBean(appArea.name));
                            }
                            if (hotCityList.size() > 0) {
                                if (!TextUtils.isEmpty(address)) {
                                    for (SimpleTextBean bean : hotCityList) {
                                        if (address.equals(bean.name)) {
                                            bean.isChecked = true;
                                            break;
                                        }
                                    }
                                }
                                adapterCity.refreshData(hotCityList);
                            }
                        }
                        if (retModel.sexs != null) {
                            List<SimpleTextBean> sexList = new ArrayList<>();
                            for (KeyValueDto keyValueDto : retModel.sexs) {
                                sexList.add(new SimpleTextBean(keyValueDto.value));
                            }
                            if (sexList.size() > 0) {
                                if (sex == 0) {
                                    for (SimpleTextBean bean : sexList) {
                                        if ("不限".equals(bean.name)) {
                                            bean.isChecked = true;
                                            break;
                                        }
                                    }
                                } else if (sex == 1) {
                                    for (SimpleTextBean bean : sexList) {
                                        if ("男生".equals(bean.name) || "男神".equals(bean.name)) {
                                            bean.isChecked = true;
                                            break;
                                        }
                                    }
                                } else if (sex == 2) {
                                    for (SimpleTextBean bean : sexList) {
                                        if ("女生".equals(bean.name) || "女神".equals(bean.name)) {
                                            bean.isChecked = true;
                                            break;
                                        }
                                    }
                                }
                                adapterSex.refreshData(sexList);
                            }
                        }
                        if (retModel.allTabInfoList != null) {
                            List<SimpleTextBean> interestList = new ArrayList<>();
                            for (AppTabInfo appTabInfo : retModel.allTabInfoList) {
                                interestList.add(new SimpleTextBean(appTabInfo.id, appTabInfo.name));
                            }
                            if (interestList.size() > 0) {
                                if (!TextUtils.isEmpty(tabId)) {
                                    List<String> tabs = Arrays.asList(tabId.split(","));
                                    for (String tab : tabs) {
                                        for (SimpleTextBean bean : interestList) {
                                            if (tab.equals(bean.id + "")) {
                                                bean.isChecked = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                                adapterInterest.refreshData(interestList);
                            }
                        }
                    }
                }
            }
        });
    }

    public void setO2OSearchListener(O2OSearchListener o2OSearchListener) {
        this.o2OSearchListener = o2OSearchListener;
    }

    public interface O2OSearchListener {
        void onSearch(String address, int sex, String tabId);
    }
}
