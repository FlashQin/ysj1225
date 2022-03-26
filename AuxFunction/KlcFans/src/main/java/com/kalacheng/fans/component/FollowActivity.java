package com.kalacheng.fans.component;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.fans.adapter.FollowAdpater;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.libbas.model.HttpNone;

import com.kalacheng.libuser.model.ApiUserAtten;
import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.fans.R;
import com.kalacheng.fans.databinding.FollowBinding;
import com.kalacheng.fans.viewmodel.FollowViewModel;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.util.utils.ToastUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;


/*
 * 关注列表
 * */
@Route(path = ARouterPath.Follow)
public class FollowActivity extends BaseMVVMActivity<FollowBinding, FollowViewModel> implements OnRefreshListener, OnLoadMoreListener {
    @Autowired(name = ARouterValueNameConfig.USERID)
    public long UserID;

    //页数
    private int page = 0;
    private FollowAdpater adpater;
    private String TYPE = "Refresh";//Refresh 刷新 Load加载更多，

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.follow;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        binding.FollowRefresh.setOnLoadMoreListener(this);
        binding.FollowRefresh.setOnRefreshListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        binding.FollowList.setLayoutManager(manager);

        adpater = new FollowAdpater(mContext);
        adpater.setOnFollowClickListener(new OnBeanCallback<ApiUserAtten>() {
            @Override
            public void onClick(final ApiUserAtten bean) {
                HttpApiAppUser.set_atten(bean.status == 1 ? 0 : 1, bean.uid, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            bean.status = bean.status == 1 ? 0 : 1;
                            adpater.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
        binding.FollowList.setAdapter(adpater);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getFollowData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        TYPE = "Refresh";
        page = 0;
        getFollowData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        TYPE = "Load";
        page++;
        getFollowData();
    }

    //获取数据
    public void getFollowData() {

        HttpApiAppUser.getAttenList(page, HttpConstants.PAGE_SIZE, UserID, new HttpApiCallBackArr<ApiUserAtten>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiUserAtten> retModel) {
                if (code == 1) {
                    if (TYPE.equals("Load")) {
                        adpater.getLoadData(retModel);
                        binding.FollowRefresh.finishLoadMore();
                    } else {
                        adpater.getRefreshData(retModel);
                        binding.FollowRefresh.finishRefresh();
                    }

                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }
}
