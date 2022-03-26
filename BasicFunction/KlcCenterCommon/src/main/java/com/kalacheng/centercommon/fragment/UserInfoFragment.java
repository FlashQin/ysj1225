package com.kalacheng.centercommon.fragment;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buscommon.model.GiftWallDto;
import com.kalacheng.buscommon.model.TabInfoDto;
import com.kalacheng.buscommon.model.TabTypeDto;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.adapter.AnchorCommentAdpater;
import com.kalacheng.centercommon.adapter.HomePageGiftAdapter;
import com.kalacheng.centercommon.adapter.InterestTagAdpater;
import com.kalacheng.commonview.adapter.GuardAdapter;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libuser.model.AppMedal;
import com.kalacheng.util.adapter.SimpleImgAdapter;
import com.kalacheng.util.bean.SimpleImgBean;
import com.kalacheng.util.bean.SimpleTextBean;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.ProcessResultUtil;
import com.kalacheng.util.view.ItemDecoration;
import com.kalacheng.util.view.MyGridLayoutManager;
import com.kalacheng.util.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserInfoFragment extends BaseFragment implements View.OnClickListener {
    long userId;
    LinearLayout llTag;
    ApiUserInfo apiUserInfo;
    LinearLayout presoninfoLl;
    ProcessResultUtil mProcessResultUtil;

    RecyclerView recyclerViewGift, recyclerViewHonor, recyclerViewGuard;
    GuardAdapter guardAdapter;
    AnchorCommentAdpater anchorCommentAdpater;

    public UserInfoFragment() {

    }

    public UserInfoFragment(long userId) {
        this.userId = userId;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_info;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView() {
        llTag = mParentView.findViewById(R.id.ll_tag);
        presoninfoLl = mParentView.findViewById(R.id.presoninfoLl);

        recyclerViewGift = mParentView.findViewById(com.kalacheng.centercommon.R.id.recyclerView_gift);
        recyclerViewGift.setNestedScrollingEnabled(false);
        recyclerViewGift.setLayoutManager(new MyGridLayoutManager(getContext(), 4));
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(SpacesItemDecoration.TOP_DECORATION, 20);//上下间距
        stringIntegerHashMap.put(SpacesItemDecoration.RIGHT_DECORATION, 20);
        recyclerViewGift.addItemDecoration(new SpacesItemDecoration(stringIntegerHashMap));
        recyclerViewGift.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (!CheckDoubleClick.isFastDoubleClick()) {
                        mParentView.findViewById(com.kalacheng.centercommon.R.id.ll_gift).performClick();  //模拟父控件的点击
                    }
                }
                return false;
            }
        });

        recyclerViewHonor = mParentView.findViewById(com.kalacheng.centercommon.R.id.recyclerView_honor);
        recyclerViewHonor.setNestedScrollingEnabled(false);
        recyclerViewHonor.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerViewHonor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (!CheckDoubleClick.isFastDoubleClick()) {
                        mParentView.findViewById(com.kalacheng.centercommon.R.id.ll_honor).performClick();  //模拟父控件的点击
                    }
                }
                return false;
            }
        });

        recyclerViewGuard = mParentView.findViewById(com.kalacheng.centercommon.R.id.recyclerViewGuard);
        recyclerViewGuard.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewGuard.setNestedScrollingEnabled(false);
        guardAdapter = new GuardAdapter();
        recyclerViewGuard.setAdapter(guardAdapter);
        recyclerViewGuard.addItemDecoration(new ItemDecoration(getContext(), 0x00000000, 6, 7));

        mParentView.findViewById(com.kalacheng.centercommon.R.id.ll_gift).setOnClickListener(this);
        mParentView.findViewById(com.kalacheng.centercommon.R.id.ll_honor).setOnClickListener(this);
        mParentView.findViewById(com.kalacheng.centercommon.R.id.tvGuardMore).setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void initData() {
        mProcessResultUtil = new ProcessResultUtil(getActivity());
        HttpApiAppUser.personCenter(-1, -1, userId, new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && null != retModel) {
                    apiUserInfo = retModel;
                    List<SimpleTextBean> list = new ArrayList<>();
                    if (TextUtils.isEmpty(retModel.goodnum)) {
                        list.add(new SimpleTextBean("ID:  " + retModel.userId));
                    } else {
                        list.add(new SimpleTextBean("靓号:  " + retModel.goodnum));
                    }
                    list.add(new SimpleTextBean("身高:  " + retModel.height));
                    list.add(new SimpleTextBean("体重:  " + retModel.weight));
                    list.add(new SimpleTextBean("职业:  " + retModel.vocation));
                    list.add(new SimpleTextBean("星座:  " + retModel.constellation));
                    presoninfoLl.removeAllViews();
                    for (SimpleTextBean bean : list) {
                        View view = LayoutInflater.from(getContext()).inflate(R.layout.simple_text, presoninfoLl, false);
                        TextView textView = view.findViewById(R.id.text);
                        textView.setText(bean.name);
                        presoninfoLl.addView(view);
                    }

//                    if (null == simpleTextAdapter) {
//                        simpleTextAdapter = new SimpleTextAdapter(list);
//                        recyclerViewPerson.setAdapter(simpleTextAdapter);
//                    } else {
//                        simpleTextAdapter.setData(list);
//                    }
                    if (null != retModel.interestList && !retModel.interestList.isEmpty()) {
                        llTag.removeAllViews();
                        for (TabTypeDto tabTypeDto : retModel.interestList) {
                            View tagTitle = LayoutInflater.from(getContext()).inflate(R.layout.layout_right_title, null);
                            LinearLayout llText = tagTitle.findViewById(R.id.ll_text);
                            llText.setBackgroundColor(getResources().getColor(R.color.white));
                            llTag.addView(tagTitle);
                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tagTitle.getLayoutParams();
                            layoutParams.height = DpUtil.dp2px(35);
                            TextView text = tagTitle.findViewById(R.id.text);
                            LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) text.getLayoutParams();
                            layoutParams1.setMargins(DpUtil.dp2px(10), 0, 0, 0);
                            text.getPaint().setFakeBoldText(true);
                            text.setText(tabTypeDto.name);
                            RecyclerView recyclerView = new RecyclerView(getContext());
                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
                            List<TabInfoDto> infoDtoList;
                            if (null == tabTypeDto.tabInfoList) {
                                infoDtoList = new ArrayList<>();
                            } else {
                                infoDtoList = tabTypeDto.tabInfoList;
                            }
                            InterestTagAdpater adpater = new InterestTagAdpater(infoDtoList);
                            recyclerView.setAdapter(adpater);
                            llTag.addView(recyclerView);
                            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
                            layoutParams2.setMargins(DpUtil.dp2px(10), -DpUtil.dp2px(5), 0, 0);
                        }
                    } else {
                        mParentView.findViewById(R.id.tv_tag).setVisibility(View.VISIBLE);
                    }
                    if (retModel.giftWall != null && !retModel.giftWall.isEmpty()) {
                        List<GiftWallDto> giftWall = new ArrayList<>();
                        if (retModel.giftWall.size() < 5) {
                            giftWall = retModel.giftWall;
                        } else {
                            giftWall = retModel.giftWall.subList(0, 4);
                        }
                        HomePageGiftAdapter giftAdapter = new HomePageGiftAdapter(giftWall);
                        giftAdapter.setForbidClick(true);
                        recyclerViewGift.setAdapter(giftAdapter);
                    } else {
                        mParentView.findViewById(com.kalacheng.centercommon.R.id.ll_gift).setVisibility(View.GONE);
                        mParentView.findViewById(com.kalacheng.centercommon.R.id.tv_gift).setVisibility(View.VISIBLE);
                    }
                    if (retModel.medalWall != null && !retModel.medalWall.isEmpty()) {
                        List<SimpleImgBean> simpleImgBeans = new ArrayList<>();
                        for (AppMedal appMedal : retModel.medalWall) {
                            simpleImgBeans.add(new SimpleImgBean(appMedal.id, appMedal.medalLogo));
                        }
                        SimpleImgAdapter honorAdapter = new SimpleImgAdapter();
                        honorAdapter.addData(simpleImgBeans);
                        honorAdapter.setImgWidthHeight(70, 55);
                        honorAdapter.setForbidClick(true);
                        recyclerViewHonor.setAdapter(honorAdapter);
                    } else {
                        mParentView.findViewById(com.kalacheng.centercommon.R.id.ll_honor).setVisibility(View.GONE);
                        mParentView.findViewById(com.kalacheng.centercommon.R.id.tv_honor).setVisibility(View.VISIBLE);
                    }
                    if (retModel.guardMyList != null && retModel.guardMyList.size() > 0) {
                        recyclerViewGuard.setVisibility(View.VISIBLE);
                        mParentView.findViewById(com.kalacheng.centercommon.R.id.tvGuardNone).setVisibility(View.GONE);
                        guardAdapter.setRefreshData(retModel.guardMyList);
                    } else {
                        recyclerViewGuard.setVisibility(View.GONE);
                        mParentView.findViewById(com.kalacheng.centercommon.R.id.tvGuardNone).setVisibility(View.VISIBLE);
                    }

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvGuardMore) {
            ARouter.getInstance().build(ARouterPath.GuardActivity).withString(ARouterValueNameConfig.GUARD_TITLE, "TA的守护").withInt(ARouterValueNameConfig.GUARD_TYPE, 1)
                    .withLong(ARouterValueNameConfig.GUARD_ID, userId).navigation();
        } else if (view.getId() == R.id.ll_honor) {//勋章墙
            ARouter.getInstance().build(ARouterPath.HonorActivity).withLong(ARouterValueNameConfig.USERID, userId).navigation();
        } else if (view.getId() == R.id.ll_gift) {//礼物墙
            ARouter.getInstance().build(ARouterPath.GiftActivity).withLong(ARouterValueNameConfig.USERID, userId).navigation();
        }
    }

}
