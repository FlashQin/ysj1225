package com.kalacheng.main.fragment;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.busooolive.httpApi.HttpApiOOOCall;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libuser.model.ApiUsersLine;
import com.kalacheng.main.R;
import com.kalacheng.main.adapter.ChatLocalAdpater;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;


/*
 * 聊场
 * */
public class ChatLocalFragment extends BaseFragment {
    private RecyclerView ChatLocal_list;
    SmartRefreshLayout refreshLayout;
    ChatLocalAdpater adpater;

    public ChatLocalFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.chat_local;
    }

    @SuppressLint("WrongConstant")

    @Override
    protected void initView() {
        ChatLocal_list = mParentView.findViewById(R.id.ChatLocal_list);
        refreshLayout = mParentView.findViewById(R.id.refreshLayout);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(OrientationHelper.VERTICAL);
        ChatLocal_list.setLayoutManager(manager);

        ChatLocal_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ChatLocal_list.setHasFixedSize(true);

        adpater = new ChatLocalAdpater(getActivity());
        ChatLocal_list.setAdapter(adpater);


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
        getData();
    }

    @Override
    protected void initData() {


    }

    @Override
    public void onResumeFragment() {
        super.onResumeFragment();
        L.e("onResumeFragment");
        getData();
    }

    @Override
    public void onPauseFragment() {
        super.onPauseFragment();
    }

    public void getData() {
        HttpApiOOOCall.getAnchorOrMailList((float) SpUtil.getInstance().getSharedPreference(SpUtil.LATITUDE, HttpConstants.LAT), (float) SpUtil.getInstance().getSharedPreference(SpUtil.LONGITUDE, HttpConstants.LNG), 0, 30, 1, new HttpApiCallBackArr<ApiUsersLine>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiUsersLine> retModel) {
                refreshLayout.finishRefresh();
                if (code == 1) {
                    adpater.getData(retModel);
                    if (retModel != null && retModel.size() > 0) {
                        mParentView.findViewById(R.id.tvNoData).setVisibility(View.GONE);
                    } else {
                        mParentView.findViewById(R.id.tvNoData).setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    public void setShowed(boolean showed) {
        super.setShowed(showed);
    }
}
