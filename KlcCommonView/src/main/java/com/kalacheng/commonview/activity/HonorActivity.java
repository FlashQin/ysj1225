package com.kalacheng.commonview.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.commonview.R;
import com.kalacheng.libuser.httpApi.HttpApiMedal;
import com.kalacheng.libuser.model.AppMedal;
import com.kalacheng.libuser.model.MedalDto;
import com.kalacheng.util.adapter.SimpleImgTextAdapter;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.util.bean.SimpleImageUrlTextBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 勋章墙
 */
@Route(path = ARouterPath.HonorActivity)
public class HonorActivity extends BaseActivity {
    @Autowired(name = ARouterValueNameConfig.USERID)
    public long UserID;

    RecyclerView recyclerViewUser, recyclerViewMoney, recyclerViewNoble, recyclerViewMine;
    private TextView tvMineNone;
    private TextView tvUserNone;
    private TextView tvMoneyNone;
    private TextView tvNobleNone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honor);
        initView();
        initData();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    private void initData() {
        HttpApiMedal.getMyAllMedal((int) UserID, new HttpApiCallBack<MedalDto>() {
            @Override
            public void onHttpRet(int code, String msg, MedalDto retModel) {
                if (code == 1) {
                    if (null != retModel.myAllMedals && !retModel.myAllMedals.isEmpty()) {
                        List<SimpleImageUrlTextBean> mList = new ArrayList<>();
                        for (AppMedal appMedal : retModel.myAllMedals) {
                            SimpleImageUrlTextBean simpleImageUrlTextBean = new SimpleImageUrlTextBean(appMedal.medalLogo, appMedal.name);
                            mList.add(simpleImageUrlTextBean);
                        }
                        SimpleImgTextAdapter simpleImgTextAdapter = new SimpleImgTextAdapter(mList);
                        simpleImgTextAdapter.setPadding(0, 10, 0, 0);
                        simpleImgTextAdapter.setImgWidthHeight(70, 55);
                        recyclerViewMine.setAdapter(simpleImgTextAdapter);
                        recyclerViewMine.setVisibility(View.VISIBLE);
                        tvMineNone.setVisibility(View.GONE);
                    } else {
                        recyclerViewMine.setVisibility(View.GONE);
                        tvMineNone.setVisibility(View.VISIBLE);
                    }
                    if (null != retModel.noUserMedals && !retModel.noUserMedals.isEmpty()) {
                        List<SimpleImageUrlTextBean> mList = new ArrayList<>();
                        for (AppMedal appMedal : retModel.noUserMedals) {
                            SimpleImageUrlTextBean simpleImageUrlTextBean = new SimpleImageUrlTextBean(appMedal.medalLogo, appMedal.name);
                            mList.add(simpleImageUrlTextBean);
                        }
                        SimpleImgTextAdapter simpleImgTextAdapter = new SimpleImgTextAdapter(mList);
                        simpleImgTextAdapter.setPadding(0, 10, 0, 0);
                        simpleImgTextAdapter.setImgWidthHeight(70, 55);
                        recyclerViewUser.setAdapter(simpleImgTextAdapter);
                        recyclerViewUser.setVisibility(View.VISIBLE);
                        tvUserNone.setVisibility(View.GONE);
                    } else {
                        recyclerViewUser.setVisibility(View.GONE);
                        tvUserNone.setVisibility(View.VISIBLE);
                    }
                    if (null != retModel.noWealthMedals && !retModel.noWealthMedals.isEmpty()) {
                        List<SimpleImageUrlTextBean> mList = new ArrayList<>();
                        for (AppMedal appMedal : retModel.noWealthMedals) {
                            SimpleImageUrlTextBean simpleImageUrlTextBean = new SimpleImageUrlTextBean(appMedal.medalLogo, appMedal.name);
                            mList.add(simpleImageUrlTextBean);
                        }
                        SimpleImgTextAdapter simpleImgTextAdapter = new SimpleImgTextAdapter(mList);
                        simpleImgTextAdapter.setPadding(0, 10, 0, 0);
                        simpleImgTextAdapter.setImgWidthHeight(70, 55);
                        recyclerViewMoney.setAdapter(simpleImgTextAdapter);
                        recyclerViewMoney.setVisibility(View.VISIBLE);
                        tvMoneyNone.setVisibility(View.GONE);
                    } else {
                        recyclerViewMoney.setVisibility(View.GONE);
                        tvMoneyNone.setVisibility(View.VISIBLE);
                    }
                    if (null != retModel.noNobleMedals && !retModel.noNobleMedals.isEmpty()) {
                        List<SimpleImageUrlTextBean> mList = new ArrayList<>();
                        for (AppMedal appMedal : retModel.noNobleMedals) {
                            SimpleImageUrlTextBean simpleImageUrlTextBean = new SimpleImageUrlTextBean(appMedal.medalLogo, appMedal.name);
                            mList.add(simpleImageUrlTextBean);
                        }
                        SimpleImgTextAdapter simpleImgTextAdapter = new SimpleImgTextAdapter(mList);
                        simpleImgTextAdapter.setPadding(0, 10, 0, 0);
                        simpleImgTextAdapter.setImgWidthHeight(70, 55);
                        recyclerViewNoble.setAdapter(simpleImgTextAdapter);
                        recyclerViewNoble.setVisibility(View.VISIBLE);
                        tvNobleNone.setVisibility(View.GONE);
                    } else {
                        recyclerViewNoble.setVisibility(View.GONE);
                        tvNobleNone.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void initView() {
        recyclerViewUser = findViewById(R.id.recyclerView_user);
        tvUserNone = findViewById(R.id.tvUserNone);
        recyclerViewMoney = findViewById(R.id.recyclerView_money);
        tvMoneyNone = findViewById(R.id.tvMoneyNone);
        recyclerViewNoble = findViewById(R.id.recyclerView_noble);
        tvNobleNone = findViewById(R.id.tvNobleNone);
        recyclerViewMine = findViewById(R.id.recyclerView_mine);
        tvMineNone = findViewById(R.id.tvMineNone);
        recyclerViewUser.setLayoutManager(new GridLayoutManager(HonorActivity.this, 4));
        recyclerViewMoney.setLayoutManager(new GridLayoutManager(HonorActivity.this, 4));
        recyclerViewNoble.setLayoutManager(new GridLayoutManager(HonorActivity.this, 4));
        recyclerViewMine.setLayoutManager(new GridLayoutManager(HonorActivity.this, 4));

        recyclerViewUser.setHasFixedSize(true);
        recyclerViewUser.setNestedScrollingEnabled(false);
        recyclerViewMoney.setHasFixedSize(true);
        recyclerViewMoney.setNestedScrollingEnabled(false);
        recyclerViewNoble.setHasFixedSize(true);
        recyclerViewNoble.setNestedScrollingEnabled(false);
        recyclerViewMine.setHasFixedSize(true);
        recyclerViewMine.setNestedScrollingEnabled(false);
        setTitle("勋章墙");
    }
}
