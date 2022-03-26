package com.kalacheng.main.fragment;

import android.graphics.Rect;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.buscommon.AppHomeHallDTO;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libuser.httpApi.HttpApiAppLogin;
import com.kalacheng.libuser.httpApi.HttpApiHome;
import com.kalacheng.libuser.model.AppAds;
import com.kalacheng.main.R;
import com.kalacheng.main.adapter.MainRecommendAdapter;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.commonview.utils.WebUtil;
import com.kalacheng.util.view.ItemLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

// 附近
public class NearFragment extends BaseFragment {

    private RefreshLayout refreshLayout;
    private MainRecommendAdapter adpater;
    private int pageIndex = 0;
    private String address;
    private ItemLayout layoutBanner;
    private ConvenientBanner convenientBanner;

    public NearFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initView() {
        RecyclerView recyclerView = mParentView.findViewById(R.id.recyclerView);
        //防止item 交换位置
        layoutBanner = mParentView.findViewById(R.id.layoutBanner);
        convenientBanner = mParentView.findViewById(R.id.convenientBanner);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.getItemAnimator().setChangeDuration(0);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adpater = new MainRecommendAdapter(getActivity());
        recyclerView.setAdapter(adpater);
        RecyclerView.ItemDecoration gridItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
                int spanIndex = layoutParams.getSpanIndex();
                outRect.bottom = DpUtil.dp2px(6);
                if (spanIndex == 0) {
                    // left
                    outRect.left = 0;
                    outRect.right = DpUtil.dp2px(2);
                } else {
                    outRect.left = DpUtil.dp2px(2);
                    outRect.right = 0;
                }
            }
        };
        recyclerView.addItemDecoration(gridItemDecoration);
        refreshLayout = (RefreshLayout) mParentView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 0;
                getRecommendData(true);
                getAdsList();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                getRecommendData(false);

            }
        });
    }

    @Override
    protected void initData() {
        getRecommendData(true);
        getAdsList();
        MeBundle.getInstance().addSimpleMsgListener(MeBundle.City, new MsgListener.SimpleMsgListener<String>() {
            @Override
            public void onMsg(String name) {
                address = name;
                getRecommendData(true);
            }

            @Override
            public void onTagMsg(String tag, String s) {

            }
        });
    }

    /**
     * 获取广告轮播图
     */
    private void getAdsList() {
        HttpApiAppLogin.adslist(4, 13, new HttpApiCallBackArr<AppAds>() {
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

    private void initBanner(final List<AppAds> mSlideList) {
        List<String> data_banner_string = new ArrayList<>();
        for (AppAds bean : mSlideList) {
            data_banner_string.add(bean.thumb);
        }
        convenientBanner.setPages(new CBViewHolderCreator<SquareFragment.NetworkImageHolderView>() {
            @Override
            public SquareFragment.NetworkImageHolderView createHolder() {
                return new SquareFragment.NetworkImageHolderView();
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

    public void getRecommendData(final boolean isRefresh) {
        /**
         * 首页列表数据
         * @param address 地址
         * @param channelId 频道ID
         * @param headNo 头部分类id(取头部接口中的showType),logicType=2时传入其他传-1
         * @param hotSortId 热门类型ID
         * @param isRecommend 是否推荐 -1:全部 0:不推荐 1:已推荐
         * @param liveFunction 是否有直播购 0:没有 1:有
         * @param logicType 业务类型 -1:全部 0:直播 1:语音 2:一对一  3:动态 4:短视频 5:直播购
         * @param pageIndex 当前页
         * @param pageSize 每页大小
         * @param sex 性别
         * @param tabIds 标签ID数组， 逗号隔开
         */
        HttpApiHome.getHomeDataList(address, -1, -1, -1, -1, -1, pageIndex, HttpConstants.PAGE_SIZE, 0, "", new HttpApiCallBackArr<AppHomeHallDTO>() {
            @Override
            public void onHttpRet(int code, String msg, List<AppHomeHallDTO> retModel) {
                if (code == 1) {
                    if (isRefresh) {
                        refreshLayout.finishRefresh();
                        adpater.RefreshData(retModel);
                    } else {
                        refreshLayout.finishLoadMore();
                        adpater.loadData(retModel);
                    }
                }
            }
        });
    }
}
