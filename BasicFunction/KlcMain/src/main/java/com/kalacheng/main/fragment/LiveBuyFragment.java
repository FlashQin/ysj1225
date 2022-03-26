package com.kalacheng.main.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.buscommon.AppHomeHallDTO;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.busshop.httpApi.HttpApiShopBusiness;
import com.kalacheng.busshop.model.ShopLiveAnnouncementDTO;
import com.kalacheng.busshop.model.ShopLiveAnnouncementDetailDTO;
import com.kalacheng.commonview.utils.LookRoomUtlis;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiHome;
import com.kalacheng.libuser.model.AppLiveChannel;
import com.kalacheng.main.R;
import com.kalacheng.main.adapter.LiveChannelAdpater;
import com.kalacheng.main.adapter.MainRecommendAdapter;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.CardPageTransformer;
import com.kalacheng.util.view.ItemDecoration;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class LiveBuyFragment extends BaseFragment {

    SmartRefreshLayout refreshLayout;
    RecyclerView recyclerView0;
    RecyclerView recyclerView1;

    LinearLayout llNoData;
    RelativeLayout bannerRl;
    ConvenientBanner convenientBanner;

    MainRecommendAdapter channelAdapter;

    LiveChannelAdpater liveChannelAdpater;
    int page = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live_buy;
    }

    @Override
    protected void initView() {
        bannerRl = mParentView.findViewById(R.id.bannerRl);
        convenientBanner = mParentView.findViewById(R.id.convenientBanner);

        llNoData = mParentView.findViewById(R.id.ll_no_data);
        refreshLayout = mParentView.findViewById(R.id.refreshLayout);
        recyclerView0 = mParentView.findViewById(R.id.recyclerView0);
        recyclerView1 = mParentView.findViewById(R.id.recyclerView1);
        recyclerView0.setHasFixedSize(true);
        recyclerView1.setHasFixedSize(true);


        liveChannelAdpater = new LiveChannelAdpater(new ArrayList<AppLiveChannel>(), true);
        recyclerView0.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView0.setAdapter(liveChannelAdpater);
//        recyclerView0.addItemDecoration(new ItemDecoration(getActivity(), 0x00000000, 4, 0));
        liveChannelAdpater.setOnItemClickListener(new OnBeanCallback<AppLiveChannel>() {
            @Override
            public void onClick(AppLiveChannel bean) {
                getLiveChannelData();
            }
        });


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                getLiveAnnouncementList();
                getLiveChannelData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getLiveChannelData();
            }
        });

        channelAdapter = new MainRecommendAdapter(getActivity());
        recyclerView1.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView1.setAdapter(channelAdapter);
        recyclerView1.addItemDecoration(new ItemDecoration(getContext(), 0x00000000, 4, 6));
    }

    @Override
    protected void initData() {
        getLiveAnnouncementList();
        getLiveChannelData();
//        HttpApiHome.getHomeSquareLiveHeader(6, new HttpApiCallBack<HomeDto>() {
//            @Override
//            public void onHttpRet(int code, String msg, HomeDto retModel) {
//                if (code == 1) {
//                    if (null != retModel) {
//                        if (null != retModel.liveChannels && !retModel.liveChannels.isEmpty()) {
//                            retModel.liveChannels.get(0).isChecked = 1;
//                            liveChannelAdpater.setData(retModel.liveChannels);
//                            getLiveChannelData();
//                        }
//                    }
//                }
//            }
//        });

    }

    // -1 查询所有   liveFunction = 1
    private void getLiveChannelData() {
        //HttpApiHome.getHomeDataList("", (int) liveChannelAdpater.channelId, -1, 0, -1, 5, page, 20, 0, "", new HttpApiCallBackArr<AppHomeHall>() {
        HttpApiHome.getHomeDataList("", -1, -1, -1, 1, 1, page, HttpConstants.PAGE_SIZE, 0, "", new HttpApiCallBackArr<AppHomeHallDTO>() {
            @Override
            public void onHttpRet(int code, String msg, List<AppHomeHallDTO> retModel) {
                if (code == 1 && null != retModel && retModel.size() > 0) {
                    recyclerView1.setVisibility(View.VISIBLE);
                    llNoData.setVisibility(View.GONE);
                    channelAdapter.RefreshData(retModel);
                    refreshLayout.setEnableLoadMore(retModel.size() == HttpConstants.PAGE_SIZE);
                } else {
                    recyclerView1.setVisibility(View.GONE);
                    llNoData.setVisibility(View.VISIBLE);
                    refreshLayout.setEnableLoadMore(false);
                }
                if (page == 0) {
                    refreshLayout.finishRefresh();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }
        });
    }

    // 获取直播列表
    private void getLiveAnnouncementList() {
        HttpApiShopBusiness.getLiveAnnouncementList(new HttpApiCallBack<ShopLiveAnnouncementDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ShopLiveAnnouncementDTO retModel) {
                if (code == 1 && retModel != null && retModel.shopLiveAnnouncementList != null && retModel.shopLiveAnnouncementList.size() > 0) {
                    bannerRl.setVisibility(View.VISIBLE);
                    initBanner(retModel.shopLiveAnnouncementList);
                } else {
                    bannerRl.setVisibility(View.GONE);
                }
            }
        });

    }

    private void initBanner(final List<ShopLiveAnnouncementDetailDTO> mSlideList) {
        if (convenientBanner.getViewPager() != null) {
            convenientBanner.getViewPager().setClipToPadding(false);
            convenientBanner.getViewPager().setClipChildren(false);
            try {
                ((RelativeLayout) convenientBanner.getViewPager().getParent()).setClipChildren(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            convenientBanner.getViewPager().setOffscreenPageLimit(3);
        }
        convenientBanner.setPages(new CBViewHolderCreator<LiveBuyNoticeHolderView>() {
            @Override
            public LiveBuyNoticeHolderView createHolder() {
                return new LiveBuyNoticeHolderView();
            }
        }, mSlideList);

        convenientBanner.setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }

                if (mSlideList.get(position).roomId == 0) {//未开播
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, mSlideList.get(position).anchorId).navigation();
                } else {//在直播
                    AppHomeHallDTO bean = new AppHomeHallDTO();
                    bean.roomId = mSlideList.get(position).roomId;
                    bean.liveType = 1;
                    LookRoomUtlis.getInstance().getData(bean, getContext());
                }
            }
        });
//        convenientBanner.setPageIndicator(new int[]{com.kalacheng.dynamiccircle.R.drawable.banner_indicator_grey, com.kalacheng.dynamiccircle.R.drawable.banner_indicator_white});
//        convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        convenientBanner.startTurning(3000);
        convenientBanner.setPageTransformer(new CardPageTransformer(0.8f, 0.45f));
        convenientBanner.setManualPageable(true);

    }

    static class LiveBuyNoticeHolderView implements Holder<ShopLiveAnnouncementDetailDTO> {
        RoundedImageView coverIv;
        TextView timeTv;
        TextView titleTv;
        TextView followBuyTv;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_live_buy_notice, null);
            titleTv = view.findViewById(R.id.titleTv);
            coverIv = view.findViewById(R.id.coverIv);
            timeTv = view.findViewById(R.id.timeTv);
            followBuyTv = view.findViewById(R.id.followBuyTv);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, final ShopLiveAnnouncementDetailDTO data) {
            ImageLoader.display(data.posterStickers, coverIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            timeTv.setText(data.startTime);
            titleTv.setText(data.title);
            followBuyTv.setText(data.isFollow == 0 ? " + 关注" : "已关注");

            followBuyTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    HttpApiAppUser.set_atten(data.isFollow == 1 ? 0 : 1, data.anchorId, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            if (code == 1) {
                                data.isFollow = data.isFollow == 1 ? 0 : 1;
                                followBuyTv.setText(data.isFollow == 0 ? " + 关注" : "已关注");
                            }
                        }
                    });

                }
            });


        }
    }


}
