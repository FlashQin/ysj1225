package com.kalacheng.main.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.busooolive.httpApi.HttpApiOOOCall;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libuser.model.ApiPushChat;
import com.kalacheng.main.R;
import com.kalacheng.main.adapter.FristLoveRobChatAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

/*
 * 女主播抢聊
 * */
public class FristLoveRobWomanChatFragment extends BaseFragment {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ImageView All_State;
    private LinearLayout womenChat_top;
    private FristLoveRobChatAdapter adapter;
    private Handler handler;
    private Runnable runnable;

    public FristLoveRobWomanChatFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fristloverob_chat;
    }

    @Override
    protected void initView() {

        refreshLayout = mParentView.findViewById(R.id.smartRefresh);
        recyclerView = mParentView.findViewById(R.id.recyclerView);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        adapter = new FristLoveRobChatAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        SlideInRightAnimator animator = new SlideInRightAnimator();
        animator.setAddDuration(1000);
        animator.setRemoveDuration(500);
        recyclerView.setItemAnimator(animator);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                getData();
            }
        });


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (refreshLayout.getState() == RefreshState.None) {
                    getData();
                }
            }
        };
        handler.postDelayed(runnable, 0);

        All_State = mParentView.findViewById(R.id.All_State);
        womenChat_top = mParentView.findViewById(R.id.womenChat_top);

        if (SpUtil.getInstance().getModel("UserInfo", ApiUserInfo.class).sex == 1) {
            womenChat_top.setVisibility(View.GONE);
        } else {
            womenChat_top.setVisibility(View.VISIBLE);
            if ((boolean) SpUtil.getInstance().getSharedPreference(SpUtil.STATIC_GRAD_CHAT, false)) {
                All_State.setBackgroundResource(R.mipmap.icon_o2o_switch_open);
            } else {
                All_State.setBackgroundResource(R.mipmap.icon_o2o_switch_close);
            }
            All_State.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((boolean) SpUtil.getInstance().getSharedPreference(SpUtil.STATIC_GRAD_CHAT, false)) {
                        All_State.setBackgroundResource(R.mipmap.icon_o2o_switch_close);
                        SpUtil.getInstance().put(SpUtil.STATIC_GRAD_CHAT, false);
                    } else {
                        All_State.setBackgroundResource(R.mipmap.icon_o2o_switch_open);
                        SpUtil.getInstance().put(SpUtil.STATIC_GRAD_CHAT, true);
                    }
                }
            });
        }


    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResumeFragment() {
        super.onResumeFragment();
        L.e("onResumeFragment");
        getData();
        if (mShowed) {
            LiveConstants.isDisplayRobChat = true;
        }
    }

    @Override
    public void onPauseFragment() {
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
        if (adapter != null) {
            adapter.removed();
        }
        if (mShowed) {
            LiveConstants.isDisplayRobChat = false;
        }
        super.onPauseFragment();
    }

    private void getData() {
        HttpApiOOOCall.getGrabAchatList(0, 4, new HttpApiCallBackArr<ApiPushChat>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiPushChat> retModel) {
                handler.postDelayed(runnable, 5000);
                refreshLayout.finishRefresh();
                if (code == 1 && retModel != null) {
                    adapter.addData(retModel);
                    if (retModel.size() > 0) {
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
        if (showed) {
            LiveConstants.isDisplayRobChat = true;
        } else {
            LiveConstants.isDisplayRobChat = false;
        }
    }
}