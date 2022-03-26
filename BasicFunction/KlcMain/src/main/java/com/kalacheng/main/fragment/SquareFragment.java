package com.kalacheng.main.fragment;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.kalacheng.buscommon.AppHomeHallDTO;
import com.kalacheng.commonview.utils.WebUtil;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libuser.httpApi.HttpApiAppLogin;
import com.kalacheng.libuser.httpApi.HttpApiHome;
import com.kalacheng.libuser.model.AppAds;
import com.kalacheng.libuser.model.AppHotSort;
import com.kalacheng.libuser.model.AppLiveChannel;
import com.kalacheng.libuser.model.HomeDto;
import com.kalacheng.main.R;
import com.kalacheng.main.adapter.LiveChannelAdpater;
import com.kalacheng.main.adapter.MainRecommendAdapter;
import com.kalacheng.main.adapter.SquareImgAdapter;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.ItemDecoration;
import com.kalacheng.util.view.ItemLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

//直播
public class SquareFragment extends BaseFragment {

    private MainRecommendAdapter channelAdapter;
    private MainRecommendAdapter liveAdapter;
    private RecyclerView recyclerViewSubject;
    private SquareImgAdapter adapterSubject;
    private LiveChannelAdpater liveChannelAdpater;
    private RefreshLayout refreshLayout;
    private ItemLayout layoutBanner;
    private ConvenientBanner convenientBanner;
    private LinearLayout llNoData;
    private RecyclerView recyclerViewLable;
    private FrameLayout layoutLabelIndicator;
    private TextView tvLabelIndicator;
    private RecyclerView recyclerViewLableValue;

    int pageIndex = 0;
    private boolean mIndicatorInit;//指示器是否初始化过
    private boolean mIndicatorEnable = true;//指示器是否可滑动

    public SquareFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_square;
    }

    @Override
    protected void initView() {
        layoutLabelIndicator = mParentView.findViewById(R.id.layoutLabelIndicator);
        tvLabelIndicator = mParentView.findViewById(R.id.tvLabelIndicator);

        recyclerViewSubject = mParentView.findViewById(R.id.recyclerView_subject);
        recyclerViewSubject.setHasFixedSize(true);
        recyclerViewSubject.setNestedScrollingEnabled(false);
        recyclerViewSubject.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapterSubject = new SquareImgAdapter(new ArrayList<AppHotSort>());
        recyclerViewSubject.setAdapter(adapterSubject);
        recyclerViewSubject.addItemDecoration(new ItemDecoration(getActivity(), 0x00000000, 10, 10));
        adapterSubject.setOnItemClickListener(new OnBeanCallback<AppHotSort>() {
            @Override
            public void onClick(AppHotSort bean) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                ARouter.getInstance().build(ARouterPath.SquareTagActivity).withParcelable(ARouterValueNameConfig.AppHotSort, bean).navigation();
            }
        });

        recyclerViewLable = mParentView.findViewById(R.id.recyclerView_lable);
        recyclerViewLable.setHasFixedSize(true);
        recyclerViewLable.setNestedScrollingEnabled(false);
        recyclerViewLable.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        liveChannelAdpater = new LiveChannelAdpater(new ArrayList<AppLiveChannel>());
        recyclerViewLable.setAdapter(liveChannelAdpater);
        recyclerViewLable.addItemDecoration(new ItemDecoration(getActivity(), 0x00000000, 12, 0));
        recyclerViewLable.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                refreshLabelIndicator();
            }
        });

        recyclerViewLableValue = mParentView.findViewById(R.id.recyclerView_lable_value);
        recyclerViewLableValue.setHasFixedSize(true);
        recyclerViewLableValue.setNestedScrollingEnabled(false);
        recyclerViewLableValue.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        channelAdapter = new MainRecommendAdapter(getActivity());
        recyclerViewLableValue.setAdapter(channelAdapter);
        recyclerViewLableValue.addItemDecoration(new ItemDecoration(getActivity(), 0x00000000, 4, 6));

        llNoData = mParentView.findViewById(R.id.ll_no_data);

        layoutBanner = mParentView.findViewById(R.id.layoutBanner);
        convenientBanner = mParentView.findViewById(R.id.convenientBanner);

        RecyclerView recyclerViewLive = mParentView.findViewById(R.id.recyclerView_live);
        recyclerViewLive.setHasFixedSize(true);
        recyclerViewLive.setNestedScrollingEnabled(false);
        recyclerViewLive.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        liveAdapter = new MainRecommendAdapter(getActivity());
        recyclerViewLive.setAdapter(liveAdapter);
        recyclerViewLive.addItemDecoration(new ItemDecoration(getActivity(), 0x00000000, 4, 6));

        refreshLayout = mParentView.findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 0;
                getSquareData(true);
                getLiveChannelData();
                getHomeSquareLiveHeader();
                getAdsList();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++pageIndex;
                getSquareData(false);

            }
        });
    }

    /**
     * 刷新指示器
     */
    private void refreshLabelIndicator() {
        if (mIndicatorEnable) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerViewLable.getLayoutManager();
            if (layoutManager != null) {
                int firstVisible = layoutManager.findFirstVisibleItemPosition();
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                if (!mIndicatorInit) {
                    mIndicatorInit = true;
                    if (lastVisible + 1 >= liveChannelAdpater.getItemCount()) {
                        mIndicatorEnable = false;
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) tvLabelIndicator.getLayoutParams();
                        layoutParams.width = DpUtil.dp2px(30);
                        tvLabelIndicator.setLayoutParams(layoutParams);
                        return;
                    }
                }

                int visibleItemCount = lastVisible - firstVisible;
                if (lastVisible == 0) {
                    visibleItemCount = 0;
                }
                if (visibleItemCount != 0) {
                    int totalCount = liveChannelAdpater.getItemCount();
                    if (totalCount > 0) {
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) tvLabelIndicator.getLayoutParams();
                        layoutParams.width = visibleItemCount * DpUtil.dp2px(30) / totalCount;
                        if (lastVisible == totalCount - 1) {
                            layoutParams.gravity = Gravity.RIGHT;
                            layoutParams.setMargins(0, 0, 0, 0);
                        } else {
                            layoutParams.gravity = Gravity.LEFT;
                            layoutParams.setMargins((DpUtil.dp2px(30) / totalCount) * firstVisible, 0, 0, 0);
                        }
                        tvLabelIndicator.setLayoutParams(layoutParams);
                    }
                }
            }
        }
    }

    @Override
    protected void initData() {
        getHomeSquareLiveHeader();
        getAdsList();
        liveChannelAdpater.setOnItemClickListener(new OnBeanCallback<AppLiveChannel>() {
            @Override
            public void onClick(AppLiveChannel bean) {
                getLiveChannelData();
            }
        });
    }

    /**
     * 获取直播内容分类
     */
    private void getHomeSquareLiveHeader() {
        HttpApiHome.getHomeSquareLiveHeader(1, new HttpApiCallBack<HomeDto>() {
            @Override
            public void onHttpRet(int code, String msg, HomeDto retModel) {
                if (code == 1 && null != retModel) {
                    if (null != retModel.hotSorts && !retModel.hotSorts.isEmpty()) {
                        recyclerViewSubject.setVisibility(View.VISIBLE);
                        adapterSubject.upDataList(retModel.hotSorts);
                    } else {
                        recyclerViewSubject.setVisibility(View.GONE);
                    }
                    if (null != retModel.liveChannels && !retModel.liveChannels.isEmpty()) {
                        recyclerViewLable.setVisibility(View.VISIBLE);
                        layoutLabelIndicator.setVisibility(View.VISIBLE);
                        tvLabelIndicator.setVisibility(View.VISIBLE);
                        recyclerViewLable.scrollToPosition(0);

                        retModel.liveChannels.get(0).isChecked = 1;
                        liveChannelAdpater.setData(retModel.liveChannels);
                        getLiveChannelData();
                        getSquareData(true);
                    } else {
                        recyclerViewLable.setVisibility(View.GONE);
                        layoutLabelIndicator.setVisibility(View.GONE);
                        tvLabelIndicator.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    /**
     * 获取广告轮播图
     */
    private void getAdsList() {
        HttpApiAppLogin.adslist(2, 2, new HttpApiCallBackArr<AppAds>() {
            @Override
            public void onHttpRet(int code, String msg, List<AppAds> retModel) {
                if (code == 1 && null != retModel && retModel.size() > 0) {
                    initBanner(retModel);
                    layoutBanner.setVisibility(View.VISIBLE);
                } else {
                    layoutBanner.setVisibility(View.GONE);
                }
            }
        });
    }

    public void getSquareData(final boolean isRefresh) {
        /**
         * 首页列表数据
         * @param address 地址
         * @param channelId 频道ID
         * @param hotSortId 热门类型ID
         * @param logicType 业务类型  -1:全部  0：直播， 1、语音 2一对一  3动态
         * @param pageIndex 当前页
         * @param pageSize 每页大小
         * @param sex 性别
         * @param tabIds 标签ID数组， 逗号隔开
         */

        HttpApiHome.getHomeDataList("", -1, -1, -1, -1, 1, pageIndex, HttpConstants.PAGE_SIZE, 0, "", new HttpApiCallBackArr<AppHomeHallDTO>() {
            @Override
            public void onHttpRet(int code, String msg, List<AppHomeHallDTO> retModel) {
                if (code == 1) {
                    if (isRefresh) {
                        refreshLayout.finishRefresh();
                        liveAdapter.RefreshData(retModel);
                    } else {
                        refreshLayout.finishLoadMore();
                        liveAdapter.loadData(retModel);
                    }
                } else {
                    if (isRefresh) {
                        refreshLayout.finishRefresh();
                    } else {
                        refreshLayout.finishLoadMore();
                    }
                }
            }
        });
    }

    private void getLiveChannelData() {
        HttpApiHome.getHomeDataList("", (int) liveChannelAdpater.channelId, -1, -1, -1, 1, 0, 4, 0, "", new HttpApiCallBackArr<AppHomeHallDTO>() {
            @Override
            public void onHttpRet(int code, String msg, List<AppHomeHallDTO> retModel) {
                if (code == 1 && null != retModel && retModel.size() > 0) {
                    recyclerViewLableValue.setVisibility(View.VISIBLE);
                    llNoData.setVisibility(View.GONE);
                    channelAdapter.RefreshData(retModel);
                } else {
                    recyclerViewLableValue.setVisibility(View.GONE);
                    llNoData.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    private void initBanner(final List<AppAds> mSlideList) {
        List<String> data_banner_string = new ArrayList<>();
        for (AppAds bean : mSlideList) {
            data_banner_string.add(bean.thumb);
        }
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, data_banner_string);

        convenientBanner.setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                WebUtil.goWeb(getActivity(), mSlideList.get(position).url);
            }
        });
        convenientBanner.setPageIndicator(new int[]{com.kalacheng.dynamiccircle.R.drawable.banner_indicator_grey, com.kalacheng.dynamiccircle.R.drawable.banner_indicator_white});
        convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        convenientBanner.startTurning(3000);
        convenientBanner.setManualPageable(true);
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
    }

    // banner加载图片适配
    public static class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.home_banner_item, null);
            imageView = view.findViewById(R.id.iv_banner_img);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            // 图片
            ImageLoader.display(data, imageView);
        }
    }
}
