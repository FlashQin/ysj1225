package com.kalacheng.shortvideo.fragment;

import android.content.Context;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.busshortvideo.httpApi.HttpApiAppShortVideo;
import com.kalacheng.dynamiccircle.adpater.HomePageTrendAdapter;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libuser.model.ApiShortVideo;
import com.kalacheng.libuser.model.ApiShortVideoDto;
import com.kalacheng.libuser.model.AppAds;
import com.kalacheng.libuser.model.AppHotSort;
import com.kalacheng.shortvideo.R;
import com.kalacheng.shortvideo.adapter.HotClassifyAdpater;
import com.kalacheng.shortvideo.adapter.MustSeeAdpater;
import com.kalacheng.shortvideo.event.DeleteShortVideoItemEvent;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.commonview.utils.WebUtil;
import com.kalacheng.util.view.ItemDecoration;
import com.kalacheng.util.view.ItemLayout;
import com.kalacheng.util.view.SpaceItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

// 短视频 - 看点
public class ShortVideoPointFragment extends BaseFragment implements View.OnClickListener {

    private static String TAG = ShortVideoPointFragment.class.getSimpleName();

    private RecyclerView recyclerViewSee, recyclerViewHot;
    private MustSeeAdpater mustSeeAdpater;
    private HotClassifyAdpater hotClassifyAdpater;
    //分页
    private int page;
    private SmartRefreshLayout refreshLayout;
    private ConvenientBanner convenientBanner;
    private ItemLayout itemImages;
    private TextView tvMostPayment;

    //是否显示title所在布局
    private boolean showTitle;

    public ShortVideoPointFragment() {

    }

    public ShortVideoPointFragment(boolean showTitle) {
        this.showTitle = showTitle;
    }

    public ShortVideoPointFragment(Context context, ViewGroup parentView) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_short_video_point;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        if (showTitle) {
            mParentView.findViewById(R.id.layoutSvPointTitle).setVisibility(View.VISIBLE);
        }
        refreshLayout = mParentView.findViewById(R.id.refreshLayout);
        convenientBanner = mParentView.findViewById(R.id.convenientBanner);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                getShortVideoData(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getShortVideoData(false);
            }
        });
        recyclerViewSee = mParentView.findViewById(R.id.recyclerView_must_see);
        recyclerViewHot = mParentView.findViewById(R.id.recyclerView_hot);
        itemImages = mParentView.findViewById(R.id.layoutImages);
        recyclerViewSee.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerViewSee.addItemDecoration(new SpaceItemDecoration(DpUtil.dp2px(10), 0));
        recyclerViewHot.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        recyclerViewHot.addItemDecoration(new SpaceItemDecoration(DpUtil.dp2px(3), DpUtil.dp2px(3)));
        mParentView.findViewById(R.id.tvMostBrowse).setOnClickListener(this);
        mParentView.findViewById(R.id.tvMostComment).setOnClickListener(this);
        mParentView.findViewById(R.id.tvMostZan).setOnClickListener(this);
        mParentView.findViewById(R.id.tvMostPayment).setOnClickListener(this);

        tvMostPayment = mParentView.findViewById(R.id.tvMostPayment);
        if (!ConfigUtil.getBoolValue(R.bool.shortVideoMaxPay)){
            mParentView.findViewById(R.id.ll_must_see).setVisibility(View.GONE);
            tvMostPayment.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.icon_most_week, 0, 0);
            tvMostPayment.setText("本周必看");
        }
    }

    @Override
    protected void initData() {
        mustSeeAdpater = new MustSeeAdpater();
        recyclerViewSee.setAdapter(mustSeeAdpater);
        mustSeeAdpater.setOnItemClickListener(new OnBeanCallback<ApiShortVideoDto>() {
            @Override
            public void onClick(ApiShortVideoDto bean) {
                List<ApiShortVideoDto> list = new ArrayList<>();
                list.add(bean);
                ARouter.getInstance().build(ARouterPath.VideoPlayActivity)
                        .withInt(ARouterValueNameConfig.VIDEO_TYPE, -1)
                        .withInt(ARouterValueNameConfig.POSITION, 0)
                        .withParcelableArrayList(ARouterValueNameConfig.Beans, (ArrayList<? extends Parcelable>) list)
                        .navigation();
            }
        });

        hotClassifyAdpater = new HotClassifyAdpater();
        recyclerViewHot.setAdapter(hotClassifyAdpater);
        recyclerViewHot.addItemDecoration(new ItemDecoration(getContext(), 0x00000000, 4, 4));
        hotClassifyAdpater.setOnItemClickListener(new OnBeanCallback<AppHotSort>() {
            @Override
            public void onClick(AppHotSort bean) {
                ARouter.getInstance().build(ARouterPath.VideoClassifyActivity).withParcelable(ARouterValueNameConfig.AppHotSort, bean).navigation();
            }
        });
        getShortVideoData(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeleteShortVideoItemEvent(DeleteShortVideoItemEvent message) {
        long id = null != message && null != message.appShortVideo ? message.appShortVideo.id : 0;
        L.e(TAG, "onDeleteShortVideoItemEvent  网格");
        L.e(TAG, "onDeleteShortVideoItemEvent  看点");
        L.e(TAG, "onDeleteShortVideoItemEvent  " + id);

        if (mustSeeAdpater != null && id != 0) {
            int deleteIndex = -1;

            //通过 动态的id获取相对列表中的索引位置
            for (int i = 0; i < mustSeeAdpater.getData().size(); i++) {
                if (mustSeeAdpater.getData().get(i).id == id) {
                    deleteIndex = i;
                }
            }

            if (deleteIndex != -1) {
                mustSeeAdpater.getData().remove(deleteIndex);
                mustSeeAdpater.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void getShortVideoData(final boolean isRefresh) {
        HttpApiAppShortVideo.getHighlights(page, HttpConstants.PAGE_SIZE, new HttpApiCallBack<ApiShortVideo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiShortVideo retModel) {
                if (code == 1 && null != retModel) {
                    if (isRefresh) {
                        refreshLayout.finishRefresh();
                        if (null != retModel.adsList && !retModel.adsList.isEmpty()) {
                            initBanner(retModel.adsList);
                            itemImages.setVisibility(View.VISIBLE);
                        } else {
                            itemImages.setVisibility(View.GONE);
                        }
                        if (null != retModel.weekList && retModel.weekList.size() > 0 && ConfigUtil.getBoolValue(R.bool.shortVideoMaxPay)) {
                            mParentView.findViewById(R.id.ll_must_see).setVisibility(View.VISIBLE);
                            mustSeeAdpater.setData(retModel.weekList);
                        } else {
                            mParentView.findViewById(R.id.ll_must_see).setVisibility(View.GONE);
                        }
                        if (null != retModel.classifyList && !retModel.classifyList.isEmpty()) {
                            hotClassifyAdpater.setData(retModel.classifyList);
                        }
                    } else {
                        refreshLayout.finishLoadMore();
                        if (null != retModel.classifyList && !retModel.classifyList.isEmpty()) {
                            hotClassifyAdpater.loadData(retModel.classifyList);
                        }
                    }
                }
            }
        });
    }

    private void initBanner(final List<AppAds> mSlideList) {
        List<String> data_banner_string = new ArrayList<>();
        for (AppAds bean : mSlideList) {
            data_banner_string.add(bean.thumb);
        }
        convenientBanner.setPages(new CBViewHolderCreator<HomePageTrendAdapter.NetworkImageHolderView>() {
            @Override
            public HomePageTrendAdapter.NetworkImageHolderView createHolder() {
                return new HomePageTrendAdapter.NetworkImageHolderView();
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
        convenientBanner.setPageIndicator(new int[]{R.drawable.banner_indicator_grey, R.drawable.banner_indicator_white});
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvMostBrowse) {
            AppHotSort bean = new AppHotSort();
            bean.sort = 1;
            ARouter.getInstance().build(ARouterPath.VideoClassifyActivity).withParcelable(ARouterValueNameConfig.AppHotSort, bean).navigation();
        } else if (view.getId() == R.id.tvMostComment) {
            AppHotSort bean = new AppHotSort();
            bean.sort = 2;
            ARouter.getInstance().build(ARouterPath.VideoClassifyActivity).withParcelable(ARouterValueNameConfig.AppHotSort, bean).navigation();
        } else if (view.getId() == R.id.tvMostZan) {
            AppHotSort bean = new AppHotSort();
            bean.sort = 3;
            ARouter.getInstance().build(ARouterPath.VideoClassifyActivity).withParcelable(ARouterValueNameConfig.AppHotSort, bean).navigation();
        } else if (view.getId() == R.id.tvMostPayment) {
            if (!ConfigUtil.getBoolValue(R.bool.shortVideoMaxPay)){
                AppHotSort bean = new AppHotSort();
                bean.sort = 5;
                ARouter.getInstance().build(ARouterPath.VideoClassifyActivity).withParcelable(ARouterValueNameConfig.AppHotSort, bean).navigation();
            }else {
                AppHotSort bean = new AppHotSort();
                bean.sort = 4;
                ARouter.getInstance().build(ARouterPath.VideoClassifyActivity).withParcelable(ARouterValueNameConfig.AppHotSort, bean).navigation();
            }
        }
    }
}
