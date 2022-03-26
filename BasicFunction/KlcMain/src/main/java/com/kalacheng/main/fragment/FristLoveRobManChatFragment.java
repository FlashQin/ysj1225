package com.kalacheng.main.fragment;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busooolive.httpApi.HttpApiOOOCall;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.libuser.model.ApiUsersLine;
import com.kalacheng.main.R;
import com.kalacheng.main.adapter.FristLoveManRobChatAdpater;
import com.kalacheng.util.utils.MySlideInRightAnimator;
import com.kalacheng.util.view.ItemDecoration;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/*
 * 男用户抢聊
 * */
public class FristLoveRobManChatFragment extends BaseFragment {

    SmartRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    FristLoveManRobChatAdpater manRobChatAdpater;
    Runnable runnable;
    long time = 0;

    public FristLoveRobManChatFragment() {

    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if (refreshLayout.getState() == RefreshState.None) {
                        getData();
                    }
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fristloverob_chat;
    }

    @Override
    protected void initView() {

        refreshLayout = mParentView.findViewById(R.id.smartRefresh);
        recyclerView = mParentView.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new ItemDecoration(getActivity(), 0, 10, 10));
        recyclerView.setHasFixedSize(true);

        manRobChatAdpater = new FristLoveManRobChatAdpater(getActivity());
        recyclerView.setAdapter(manRobChatAdpater);


        MySlideInRightAnimator animator = new MySlideInRightAnimator();
        animator.setAddDuration(500);
        animator.setRemoveDuration(0);
        recyclerView.setItemAnimator(animator);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (handler != null) {
                    handler.removeMessages(1);
                }
                getData();
            }
        });
        handler.sendEmptyMessageDelayed(1,0);



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
        if (handler != null) {
            handler.removeMessages(1);
        }
    }

    private void getData() {
        HttpApiOOOCall.getAnchorOrMailList((float) SpUtil.getInstance().getSharedPreference(SpUtil.LATITUDE, HttpConstants.LAT),  (float) SpUtil.getInstance().getSharedPreference(SpUtil.LONGITUDE, HttpConstants.LNG), 0, 4, 2, new HttpApiCallBackArr<ApiUsersLine>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiUsersLine> retModel) {
                handler.sendEmptyMessageDelayed(1, 10000);
                refreshLayout.finishRefresh();
                if (code == 1 && retModel != null) {
                    manRobChatAdpater.addData(retModel);
                    if (retModel.size() > 0) {
                        mParentView.findViewById(R.id.tvNoData).setVisibility(View.GONE);
                    } else {
                        mParentView.findViewById(R.id.tvNoData).setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

}