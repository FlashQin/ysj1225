package com.kalacheng.centercommon.fragment;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.buscommon.AppHomeHallDTO;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buscommon.model.GiftWallDto;
import com.kalacheng.buscommon.model.GuardUserDto;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.adapter.AnchorCommentAdpater;
import com.kalacheng.centercommon.adapter.HomePageGiftAdapter;
import com.kalacheng.centercommon.adapter.HomePageLiveAdapter;
import com.kalacheng.commonview.adapter.GuardAdapter;
import com.kalacheng.commonview.dialog.GuardDialogFragment;
import com.kalacheng.commonview.utils.LookRoomUtlis;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libuser.model.AppMedal;
import com.kalacheng.libuser.model.LiveDto;
import com.kalacheng.util.adapter.SimpleImgAdapter;
import com.kalacheng.util.bean.SimpleImgBean;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.listener.OnItemClickCallback;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.view.ItemDecoration;
import com.kalacheng.util.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnchorInfoFragment extends BaseFragment implements View.OnClickListener {
    private long userId;
    private RecyclerView recyclerViewGift, recyclerViewHonor, recyclerViewGuard, recyclerViewEvaluate;
    private RecyclerView recyclerViewLive;
    private HomePageLiveAdapter homePageLiveAdapter;
    private GuardAdapter guardAdapter;
    private AnchorCommentAdpater anchorCommentAdpater;
    private int type = 1;

    public AnchorInfoFragment() {
    }

    public AnchorInfoFragment(Long userId) {
        this.userId = userId;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_anchor_info;
    }

    @Override
    protected void initView() {
        recyclerViewGift = mParentView.findViewById(R.id.recyclerView_gift);
        recyclerViewHonor = mParentView.findViewById(R.id.recyclerView_honor);
        recyclerViewLive = mParentView.findViewById(R.id.recyclerView_live);
        recyclerViewEvaluate = mParentView.findViewById(R.id.recyclerViewEvaluate);
        recyclerViewLive.setLayoutManager(new GridLayoutManager(getContext(), 3));

        recyclerViewLive.setHasFixedSize(true);
        recyclerViewLive.setNestedScrollingEnabled(false);
        recyclerViewHonor.setHasFixedSize(true);
        recyclerViewHonor.setNestedScrollingEnabled(false);
        recyclerViewGift.setHasFixedSize(true);
        recyclerViewGift.setNestedScrollingEnabled(false);
        recyclerViewEvaluate.setHasFixedSize(true);
        recyclerViewEvaluate.setNestedScrollingEnabled(false);
        GridLayoutManager layoutManagerGift = new GridLayoutManager(getContext(), 4);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(SpacesItemDecoration.TOP_DECORATION, 20);//上下间距
        stringIntegerHashMap.put(SpacesItemDecoration.RIGHT_DECORATION, 20);
        recyclerViewGift.setLayoutManager(layoutManagerGift);
        recyclerViewGift.addItemDecoration(new SpacesItemDecoration(stringIntegerHashMap));
        recyclerViewHonor.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerViewEvaluate.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerViewGuard = mParentView.findViewById(R.id.recyclerViewGuard);
        recyclerViewGuard.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewGuard.setHasFixedSize(true);
        recyclerViewGuard.setNestedScrollingEnabled(false);
        guardAdapter = new GuardAdapter();
        recyclerViewGuard.setAdapter(guardAdapter);
        recyclerViewGuard.addItemDecoration(new ItemDecoration(getContext(), 0x00000000, 6, 7));
        guardAdapter.setGuardCallBack(new GuardAdapter.GuardListener() {
            @Override
            public void onClick(GuardUserDto guardUserDto) {
                GuardDialogFragment guardDialogFragment = new GuardDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                bundle.putLong("anchorId", guardUserDto.anchorId);
                bundle.putString("anchorAvatar", guardUserDto.anchorIdImg);
                bundle.putString("anchorName", "");
                guardDialogFragment.setArguments(bundle);
                guardDialogFragment.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), "GuardDialogFragment");
                guardDialogFragment.GuardDialogListener(new GuardDialogFragment.GuardDialogListener() {
                    @Override
                    public void onDismiss() {
                        http();
                    }
                });
            }
        });

        homePageLiveAdapter = new HomePageLiveAdapter();
        homePageLiveAdapter.setOnItemClickCallback(new OnItemClickCallback<LiveDto>() {
            @Override
            public void onClick(View view, LiveDto item) {
//                if (item.islive == 1 && userId != HttpClient.getUid()) {
//                    AppHomeHallDTO bean = new AppHomeHallDTO();
//                    bean.liveType = 1;
//                    bean.roomId = item.roomId;
//                    bean.coin = item.coin;
//                    bean.typeVal = item.typeVal;
//                    bean.showid = item.showid;
//                    bean.isPay = item.isPay;
//                    LookRoomUtlis.getInstance().getData(bean, getContext());
//                }
            }
        });
        recyclerViewLive.setAdapter(homePageLiveAdapter);
        recyclerViewLive.addItemDecoration(new ItemDecoration(getContext(), 0x00000000, 10, 10));
        mParentView.findViewById(R.id.ll_honor).setOnClickListener(this);
        mParentView.findViewById(R.id.ll_gift).setOnClickListener(this);
        mParentView.findViewById(R.id.tvGuardMore).setOnClickListener(this);
        mParentView.findViewById(R.id.ll_live).setOnClickListener(this);
        recyclerViewGift.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (!CheckDoubleClick.isFastDoubleClick()) {
                        mParentView.findViewById(R.id.ll_gift).performClick();
                    }
                }
                return false;
            }
        });
        recyclerViewHonor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (!CheckDoubleClick.isFastDoubleClick()) {
                        mParentView.findViewById(R.id.ll_honor).performClick();
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        http();
    }

    private void http() {
        HttpApiAppUser.personCenter(-1, -1, userId, new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && null != retModel) {
                    type = retModel.role;
                    if (ConfigUtil.getBoolValue(R.bool.containLive) || ConfigUtil.getBoolValue(R.bool.containVoice)) {
                        if (retModel.role == 1) {
                            //主播
                            mParentView.findViewById(R.id.ll_live).setVisibility(View.VISIBLE);
                            recyclerViewLive.setVisibility(View.VISIBLE);
                            if (retModel.liveList != null && !retModel.liveList.isEmpty()) {
                                List<LiveDto> liveList = new ArrayList<>();
                                if (retModel.liveList.size() < 4) {
                                    liveList = retModel.liveList;
                                } else {
                                    liveList = retModel.liveList.subList(0, 3);
                                }
                                homePageLiveAdapter.setRefreshData(liveList);
                            } else {
                                mParentView.findViewById(R.id.tv_live).setVisibility(View.VISIBLE);
                            }
                        } else {
                            mParentView.findViewById(R.id.ll_live).setVisibility(View.GONE);
                            recyclerViewLive.setVisibility(View.GONE);
                        }
                    } else {
                        mParentView.findViewById(R.id.ll_live).setVisibility(View.GONE);
                        recyclerViewLive.setVisibility(View.GONE);
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
                        mParentView.findViewById(R.id.ll_gift).setVisibility(View.GONE);
                        mParentView.findViewById(R.id.tv_gift).setVisibility(View.VISIBLE);
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
                        honorAdapter.setOnItemClickCallback(new OnBeanCallback<SimpleImgBean>() {
                            @Override
                            public void onClick(SimpleImgBean bean) {
                                ARouter.getInstance().build(ARouterPath.HonorActivity).withLong(ARouterValueNameConfig.USERID, userId).navigation();
                            }
                        });
                    } else {
                        mParentView.findViewById(R.id.ll_honor).setVisibility(View.GONE);
                        mParentView.findViewById(R.id.tv_honor).setVisibility(View.VISIBLE);
                    }
                    if (ConfigUtil.getBoolValue(R.bool.showGuardInHomePage)) {
                        mParentView.findViewById(R.id.layoutGuard).setVisibility(View.VISIBLE);
                        if (retModel.role == 0) {
                            // 普通用户
                            if (retModel.myGuardList != null && retModel.myGuardList.size() > 0) {
                                recyclerViewGuard.setVisibility(View.VISIBLE);
                                mParentView.findViewById(R.id.tvGuardNone).setVisibility(View.GONE);
                                guardAdapter.setRefreshData(retModel.myGuardList);
                            } else {
                                recyclerViewGuard.setVisibility(View.GONE);
                                mParentView.findViewById(R.id.tvGuardNone).setVisibility(View.VISIBLE);
                            }
                        } else {
                            // 主播
                            if (retModel.guardMyList != null && retModel.guardMyList.size() > 0) {
                                recyclerViewGuard.setVisibility(View.VISIBLE);
                                mParentView.findViewById(R.id.tvGuardNone).setVisibility(View.GONE);
                                guardAdapter.setRefreshData(retModel.guardMyList);
                            } else {
                                recyclerViewGuard.setVisibility(View.GONE);
                                mParentView.findViewById(R.id.tvGuardNone).setVisibility(View.VISIBLE);
                            }
                        }

                    }
                    if (retModel.userCommentList != null && retModel.userCommentList.size() > 0) {
                        mParentView.findViewById(R.id.tvEvaluateTitle).setVisibility(View.VISIBLE);
                        recyclerViewEvaluate.setVisibility(View.VISIBLE);
                        anchorCommentAdpater = new AnchorCommentAdpater(getContext(), retModel.userCommentList);
                        recyclerViewEvaluate.setAdapter(anchorCommentAdpater);
                    } else {
                        mParentView.findViewById(R.id.tvEvaluateTitle).setVisibility(View.GONE);
                        recyclerViewEvaluate.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_honor) {
            ARouter.getInstance().build(ARouterPath.HonorActivity).withLong(ARouterValueNameConfig.USERID, userId).navigation();
        } else if (view.getId() == R.id.ll_gift) {
            ARouter.getInstance().build(ARouterPath.GiftActivity).withLong(ARouterValueNameConfig.USERID, userId).navigation();
        } else if (view.getId() == R.id.tvGuardMore) {
            ARouter.getInstance().build(ARouterPath.GuardActivity).withString(ARouterValueNameConfig.GUARD_TITLE, "TA的守护").withInt(ARouterValueNameConfig.GUARD_TYPE, type)
                    .withLong(ARouterValueNameConfig.GUARD_ID, userId).navigation();
        } else if (view.getId() == R.id.ll_live) {
            ARouter.getInstance().build(ARouterPath.TALiveActivity).withLong(ARouterValueNameConfig.ANCHORID, userId).navigation();
        }
    }
}
