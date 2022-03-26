package com.kalacheng.centercommon.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.adapter.LiveDataAdapter;
import com.kalacheng.frame.config.HttpConstants;

import com.kalacheng.libuser.model.LiveRoomDTO;
import com.kalacheng.util.adapter.SimpleTextAdapter;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.bean.SimpleTextBean;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 直播数据
 */
@Route(path = ARouterPath.FansLiveDataActivity)
public class FansLiveDataActivity extends BaseActivity implements View.OnClickListener {
    RecyclerView recyclerViewRank;
    RefreshLayout refreshLayout;
    LinearLayout llRecyclerView;
    TextView tvNoData;
    int pageIndex;
    private ImageView ivRight;
    int beforedate = -1;
    LiveDataAdapter liveDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_recycleview);
        initView();
        initData();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    private void initData() {
        getFansdData(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 0;
                getFansdData(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                getFansdData(false);

            }
        });
    }

    private void getFansdData(final boolean isRefresh) {
        HttpApiAppUser.getLiveData(beforedate, pageIndex, HttpConstants.PAGE_SIZE, new HttpApiCallBackArr<LiveRoomDTO>() {
            @Override
            public void onHttpRet(int code, String msg, List<LiveRoomDTO> retModel) {
                if (code == 1 && retModel != null) {
                    if (isRefresh) {
                        refreshLayout.finishRefresh();
                        liveDataAdapter.setRefreshData(retModel);
                        llRecyclerView.setVisibility(View.VISIBLE);
                        tvNoData.setVisibility(View.GONE);
                    } else {
                        refreshLayout.finishLoadMore();
                        liveDataAdapter.setLoadData(retModel);
                    }
                } else {
                    llRecyclerView.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initView() {
        recyclerViewRank = findViewById(R.id.recyclerView);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        llRecyclerView = (LinearLayout) findViewById(R.id.ll_recyclerView);
        tvNoData = (TextView) findViewById(R.id.tv_no_data);
        recyclerViewRank.setLayoutManager(new LinearLayoutManager(this));
        ivRight = findViewById(R.id.iv_right);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.icon_switch_date);
        ivRight.setOnClickListener(this);
        liveDataAdapter = new LiveDataAdapter();
        recyclerViewRank.setAdapter(liveDataAdapter);
        setTitle("直播数据");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_right) {
            showPopwindow();
        }
    }

    private void showPopwindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.popwindow_switch_time, null);
        final PopupWindow popupWindow = new PopupWindow(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(ivRight);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;//代表透明程度，范围为0 - 1.0f
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        RecyclerView recyclerView = contentView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<SimpleTextBean> list = new ArrayList<>();
        list.add(new SimpleTextBean(0, "最近3天"));
        list.add(new SimpleTextBean(1, "最近7天"));
        list.add(new SimpleTextBean(2, "最近15天"));
        list.add(new SimpleTextBean(3, "最近30天"));
        list.add(new SimpleTextBean(4, "全部"));
        SimpleTextAdapter simpleTextAdapter = new SimpleTextAdapter(list);
        simpleTextAdapter.setTextWidthHight(0, 35);
        recyclerView.setAdapter(simpleTextAdapter);
        simpleTextAdapter.setOnItemClickCallback(new OnBeanCallback<SimpleTextBean>() {
            @Override
            public void onClick(SimpleTextBean bean) {
                switch ((int) bean.id) {
                    case 0:
                        beforedate = 3;
                        pageIndex = 0;
                        popupWindow.dismiss();
                        getFansdData(true);
                        break;
                    case 1:
                        beforedate = 7;
                        pageIndex = 0;
                        popupWindow.dismiss();
                        getFansdData(true);
                        break;
                    case 2:
                        beforedate = 15;
                        pageIndex = 0;
                        popupWindow.dismiss();
                        getFansdData(true);
                        break;
                    case 3:
                        beforedate = 30;
                        pageIndex = 0;
                        popupWindow.dismiss();
                        getFansdData(true);
                        break;
                    case 4:
                        beforedate = -1;
                        pageIndex = 0;
                        popupWindow.dismiss();
                        getFansdData(true);
                        break;
                }
            }
        });

    }
}
