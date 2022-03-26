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
import com.kalacheng.fans.viewmodel.FansViewModel;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.libbas.model.HttpNone;

import com.kalacheng.libuser.model.ApiUserAtten;
import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.fans.R;
import com.kalacheng.fans.databinding.FansBinding;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.util.utils.ToastUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

@Route(path = ARouterPath.Fans)
public class FansActivity extends BaseMVVMActivity<FansBinding, FansViewModel> implements OnRefreshListener, OnLoadMoreListener {

    public int page = 0;

    private FollowAdpater adpater;
    private String TYPE = "Refresh";//Refresh 刷新 Load加载更多，

    @Autowired(name = ARouterValueNameConfig.TYPE)
    public int type;//0 粉丝  1 谁看过我
    @Autowired(name = ARouterValueNameConfig.USERID)
    public long UserID;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fans;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        binding.smartFans.setOnRefreshListener(this);
        binding.smartFans.setOnLoadMoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        binding.recyFans.setLayoutManager(manager);
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
        binding.recyFans.setAdapter(adpater);
        if (type == 0) {
            setTitle("粉丝");
        } else {
            setTitle("看过我的人");
            binding.tvTips.setText(R.string.browse_tips1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFansData();
    }

    public void getFansData() {
        if (type == 0) {
            HttpApiAppUser.getFansList(page, HttpConstants.PAGE_SIZE, UserID, new HttpApiCallBackArr<ApiUserAtten>() {
                @Override
                public void onHttpRet(int code, String msg, List<ApiUserAtten> retModel) {
                    if (code == 1 && retModel != null) {
                        if (TYPE.equals("Load")) {
                            adpater.getLoadData(retModel);
                            binding.smartFans.finishLoadMore();
                        } else {
                            viewModel.listsize.set(retModel.size());
                            binding.tvTips.setText(R.string.fans_tips1);
                            adpater.getRefreshData(retModel);
                            binding.smartFans.finishRefresh();
                        }
                    } else {
                        ToastUtil.show(msg);
                    }
                }
            });
        } else {
            HttpApiAppUser.browseRecord(page, HttpConstants.PAGE_SIZE, new HttpApiCallBackArr<ApiUserAtten>() {
                @Override
                public void onHttpRet(int code, String msg, List<ApiUserAtten> retModel) {
                    if (code == 1 && retModel != null) {
                        if (TYPE.equals("Load")) {
                            adpater.getLoadData(retModel);
                            binding.smartFans.finishLoadMore();
                        } else {
                            viewModel.listsize.set(retModel.size());
                            binding.tvTips.setText(R.string.browse_tips1);
                            adpater.getRefreshData(retModel);
                            binding.smartFans.finishRefresh();
                        }
                    } else {
                        ToastUtil.show(msg);
                    }
                }
            });
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        TYPE = "Load";
        page++;
        getFansData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        TYPE = "Refresh";
        page = 0;
        getFansData();
    }
}
