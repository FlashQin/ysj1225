package com.kalacheng.message.fragment;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAPPAnchor;
import com.kalacheng.commonview.popupwindow.CityChoicePopupWindow;
import com.kalacheng.commonview.popupwindow.SexChoicePopupWindow;
import com.kalacheng.base.event.GpsEvent;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libuser.model.ApiUsersLine;
import com.kalacheng.message.R;
import com.kalacheng.message.adapter.OnlineUserAdapter;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.GpsUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

//附近的人
public class OnlineUserFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {

    private int page = 0;
    private SmartRefreshLayout smartRefresh;
    private RecyclerView recyclerView;
    private OnlineUserAdapter adapter;
    private LinearLayout ll_location, ll_sex, ll_choice;
    private TextView tv_location, tv_sex;
    private ImageView iv_location_arrow, iv_sex_arrow;
    private LinearLayout layoutGpsWarning;

    private int sex = -1;
    private String city = "";
    private SexChoicePopupWindow sexChoicePopupWindow;
    private CityChoicePopupWindow cityChoicePopupWindow;

    public OnlineUserFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_online_user;
    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        layoutGpsWarning = mParentView.findViewById(R.id.layoutGpsWarning);
        smartRefresh = mParentView.findViewById(R.id.smartRefresh);
        recyclerView = mParentView.findViewById(R.id.recyclerView);
        ll_location = mParentView.findViewById(R.id.ll_location);
        ll_sex = mParentView.findViewById(R.id.ll_sex);
        tv_location = mParentView.findViewById(R.id.tv_location);
        tv_sex = mParentView.findViewById(R.id.tv_sex);
        iv_location_arrow = mParentView.findViewById(R.id.iv_location_arrow);
        iv_sex_arrow = mParentView.findViewById(R.id.iv_sex_arrow);
        ll_choice = mParentView.findViewById(R.id.ll_choice);
        adapter = new OnlineUserAdapter(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        smartRefresh.setOnRefreshListener(this);
        smartRefresh.setOnLoadMoreListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

//        if (!TextUtils.isEmpty((String) SpUtil.getInstance().getSharedPreference(SpUtil.CITY, ""))) {
//            city = (String) SpUtil.getInstance().getSharedPreference(SpUtil.CITY, "");
//        } else {
//            ApiUserInfo userInfo = SpUtil.getInstance().getModel("UserInfo", ApiUserInfo.class);
//            if (userInfo != null) {
//                city = userInfo.city;
//            }
//        }
        tv_location.setText("全部");
        setListener();
        Context context = getContext();
        if (context != null && !GpsUtil.isOPen(context)) {
            layoutGpsWarning.setVisibility(View.VISIBLE);
        }
    }

    private void setListener() {
        ll_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewType(1);
                if (null != cityChoicePopupWindow) {
                    cityChoicePopupWindow.show(ll_choice);
                    return;
                }
                cityChoicePopupWindow = new CityChoicePopupWindow(getActivity(), new CityChoicePopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        setTextViewType(0);
                    }

                    @Override
                    public void onChoiceCity(String item) {
                        city = item;
                        getData();
                        if (!TextUtils.isEmpty(item)) {
                            tv_location.setText(item);
                        } else {
                            tv_location.setText("全部");
                        }
                    }

                });
                cityChoicePopupWindow.show(ll_choice);
            }
        });
        ll_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewType(2);
                if (null != sexChoicePopupWindow) {
                    sexChoicePopupWindow.show(ll_choice);
                    return;
                }
                sexChoicePopupWindow = new SexChoicePopupWindow(getActivity(), new SexChoicePopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        setTextViewType(0);
                    }

                    @Override
                    public void onChoiceSex(int item) {
                        sex = item;
                        getData();
                        switch (item) {
                            case -1:
                                tv_sex.setText("不限性别");
                                break;
                            case 1:
                                tv_sex.setText("男");
                                break;
                            case 2:
                                tv_sex.setText("女");
                                break;
                            default:
                                break;
                        }
                    }

                });
                sexChoicePopupWindow.show(ll_choice);
            }
        });

        layoutGpsWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                Activity activity = getActivity();
                if (activity != null) {
                    GpsUtil.startGps(getActivity(), 10031);
                }
            }
        });
    }

    private void setTextViewType(int select) {
        ll_location.setBackgroundResource(R.drawable.shape_f4f4f4_50_bg);
        tv_location.setTextColor(getResources().getColor(R.color.textColor6));
        iv_location_arrow.setImageResource(R.mipmap.icon_arrow_down);
        ll_sex.setBackgroundResource(R.drawable.shape_f4f4f4_50_bg);
        tv_sex.setTextColor(getResources().getColor(R.color.textColor6));
        iv_sex_arrow.setImageResource(R.mipmap.icon_arrow_down);
        if (select == 1) {
            ll_location.setBackgroundResource(R.drawable.bg_gradient_purple3);
            tv_location.setTextColor(getResources().getColor(R.color.white));
            iv_location_arrow.setImageResource(R.mipmap.icon_arrow_top);
        } else if (select == 2) {
            ll_sex.setBackgroundResource(R.drawable.bg_gradient_purple3);
            tv_sex.setTextColor(getResources().getColor(R.color.white));
            iv_sex_arrow.setImageResource(R.mipmap.icon_arrow_top);
        }
    }

    private void getData() {
        HttpApiAPPAnchor.getLineUser(city, (float) SpUtil.getInstance().getSharedPreference(SpUtil.LATITUDE, HttpConstants.LAT), (float) SpUtil.getInstance().getSharedPreference(SpUtil.LONGITUDE, HttpConstants.LNG), page, HttpConstants.PAGE_SIZE, sex, -1, new HttpApiCallBackArr<ApiUsersLine>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiUsersLine> retModel) {
                if (code == 1) {
                    if (page == 0) {
                        adapter.setList(retModel);
                        smartRefresh.finishRefresh();
                    } else {
                        adapter.onLoadMore(retModel);
                        smartRefresh.finishLoadMore();
                    }
                } else {
                    smartRefresh.finishRefresh();
                    smartRefresh.finishLoadMore();
                }
            }
        });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        getData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 0;
        getData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGpsEvent(GpsEvent event) {
        layoutGpsWarning.setVisibility(View.GONE);
    }
}
