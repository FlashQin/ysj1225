package com.kalacheng.main.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAPPAnchor;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libuser.model.ApiUsersLine;
import com.kalacheng.main.R;
import com.kalacheng.main.adapter.NearbyAdapter;
import com.kalacheng.main.dialog.ScreenPop;
import com.kalacheng.util.utils.ConfigUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 附近的人（语聊）
 */
public class NearbyFragment extends BaseFragment implements ScreenPop.OnDismissListener {

    private LinearLayout topView;
    private TextView partialityTv;
    private TextView statusTv;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private NearbyAdapter adapter;
    private ScreenPop screenPop;
    /**
     * 列表无数据时的提示语
     */
    private TextView showNoDataNote;
    private int page = 0;

    private int sex = -1;
    private int status = -1;

    private String city = "";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nearby;
    }

    @Override
    protected void initView() {

        refreshLayout = mParentView.findViewById(R.id.refreshLayout);
        topView = mParentView.findViewById(R.id.topView);
        partialityTv = mParentView.findViewById(R.id.partialityTv);
        statusTv = mParentView.findViewById(R.id.statusTv);
        recyclerView = mParentView.findViewById(R.id.recyclerView);

        screenPop = new ScreenPop(getContext(), this);
        showNoDataNote = mParentView.findViewById(R.id.show_no_data_note);
    }

    @Override
    protected void initData() {

        ApiUserInfo userInfo = SpUtil.getInstance().getModel("UserInfo", ApiUserInfo.class);
        if (ConfigUtil.getBoolValue(R.bool.nearbyIsCity)){
            city = userInfo.city;
        }

        adapter = new NearbyAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                getData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getData();
            }
        });

        partialityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewType(partialityTv, true);
                screenPop.show(topView, 0, sex);
            }
        });
        statusTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewType(statusTv, true);
                screenPop.show(topView, 1, status);
            }
        });

        getData();

    }

    /**
     * 是否为正常性别展示
     *
     * @return true 正常
     */
    private boolean isSexNormal() {
        return ConfigUtil.getBoolValue(R.bool.sexNormal);
    }

    /**
     * 选择改变 对应的 性别值
     *
     * @param i 下拉选择索引
     */
    private void changeSexData(int i) {
        //0性别
        if (isSexNormal()) {
            //正常
            switch (i) {
                case 1:
                    //全部
                    sex = -1;
                    break;
                case 2:
                    //男
                    sex = 1;
                    break;
                case 3:
                    //女
                    sex = 2;
                    break;
            }
        } else {
            switch (i) {
                //性别-1全部1(1) 3(0) 4(0.5) 5(...)
                case 1:
                    //全部、偏好
                    sex = -1;
                    break;
                case 2:
                    //0
                    sex = 3;
                    break;
                case 3:
                    //0.5
                    sex = 4;
                    break;
                case 4:
                    //1
                    sex = 1;
                    break;
                case 5:
                    //...
                    sex = 5;
                    break;
            }
        }
    }


    @Override
    public void onDismiss(int type, int i) {

        String str = "";
        switch (i) {
            case 1:
                str = type == 0 ? "偏好" : "全部";
                break;
            case 2:
                str = type == 0 ? (isSexNormal() ? "男" : "0") : "直播中";
                break;
            case 3:
                str = type == 0 ? (isSexNormal() ? "女" : "0.5") : "房间中";
                break;
            case 4:
                str = type == 0 ? "1" : "在线";
                break;
            case 5:
                str = type == 0 ? "..." : "离线";
                break;
            default:
                str = "全部";
        }
        if (type == 0) {
            partialityTv.setText(str);
            //选择改变 对应的 性别值
            changeSexData(i);
//            L.e("onDismiss  type    "+type +"   i   "+i+"   str "+str+"   sex "+sex);
//            if (i == sex) return;

        } else {
            statusTv.setText(str);
            if (i == 1) {
                i -= 2;
            } else {
                i--;
            }
//            if (i == status) return;
            status = i;
        }

        goTop();
    }

    @Override
    public void onDismissShow(int type) {
        setTextViewType(type == 0 ? partialityTv : statusTv, false);
    }


    private void setTextViewType(TextView textView, boolean b) {
        textView.setBackgroundResource(b ? R.drawable.bg_zise0 : R.drawable.bg_grey0);
        textView.setTextColor(b ? 0xFFDC92F5 : 0xff666666);
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, b ? R.mipmap.icon_xiala_zise : R.mipmap.icon_xiala, 0);
    }


    private void getData() {
        showNoDataNote.setVisibility(View.GONE);

        HttpApiAPPAnchor.getLineUser(city, (float) SpUtil.getInstance().getSharedPreference(SpUtil.LATITUDE, HttpConstants.LAT), (float) SpUtil.getInstance().getSharedPreference(SpUtil.LONGITUDE, HttpConstants.LNG), page, HttpConstants.PAGE_SIZE, sex, status, new HttpApiCallBackArr<ApiUsersLine>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiUsersLine> retModel) {
                if (code == 1 && retModel != null) {

                    if (page == 0) {
                        adapter.refreshList(retModel);
                        refreshLayout.finishRefresh(true);
                    } else {
                        adapter.loadMoreList(retModel);
                        refreshLayout.finishLoadMore(true);
                    }
                } else {
                    if (page == 0) {
                        adapter.refreshList(new ArrayList<ApiUsersLine>());
                        showNoDataNote.setVisibility(View.VISIBLE);
                    }

                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                }
            }
        });
    }

    @Override
    public void refreshData() {
        super.refreshData();
        goTop();
    }

    /**
     * 调用刷新动画， 列表回到 顶端
     */
    private void goTop() {
        if (refreshLayout != null) {

            if (refreshLayout.autoRefresh()) {
                recyclerView.scrollToPosition(0);
//            recyclerView.smoothScrollTo(0, 0);
            }

        }
    }
}
