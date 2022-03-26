package com.kalacheng.main.fragment;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.buscommon.AppHomeHallDTO;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.commonview.jguangIm.RequestChatEvent;
import com.kalacheng.commonview.utils.WebUtil;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libuser.httpApi.HttpApiAppLogin;
import com.kalacheng.libuser.httpApi.HttpApiHome;
import com.kalacheng.libuser.model.AppAds;
import com.kalacheng.libuser.model.AppLiveChannel;
import com.kalacheng.libuser.model.HomeDto;
import com.kalacheng.libuser.model.HomeO2OData;
import com.kalacheng.main.R;
import com.kalacheng.main.adapter.AutoPlayTool;
import com.kalacheng.main.adapter.LiveChannelAdpater;
import com.kalacheng.main.adapter.MainRecommendAdapter;
import com.kalacheng.main.adapter.One2OneBigHomeAdapter;
import com.kalacheng.main.dialog.O2OSearchDialog;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.PermissionsUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.view.ItemDecoration;
import com.kalacheng.util.view.ItemLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

// 1v1 (绯喵ui)
public class One2OneNewFragment extends BaseFragment implements View.OnClickListener {
    private RefreshLayout refreshLayout;
    private One2OneBigHomeAdapter one2OneHomeAdpater;
    private List<HomeO2OData> homeO2ODataList = new ArrayList<>();
    private MainRecommendAdapter mainRecommendAdpater;
    private List<AppHomeHallDTO> appHomeHallList = new ArrayList<>();
    private int pageIndex = 0;
    private LinearLayout layoutLabel;
    private RecyclerView recyclerView, recyclerViewLable;
    private ItemDecoration itemDecorationBig;
    private int sex = -1;//性别 0：不限 1：男， 2女
    private String tabId = "";
    private String address = "";
    private int showType = -1;
    private PermissionsUtil mProcessResultUtil;
    private StaggeredGridLayoutManager layoutManager;
    private RecyclerView.ItemDecoration gridItemDecoration;
    private boolean isOne2OneBig;
    private ConvenientBanner convenientBanner;
    private NestedScrollView scrollView;
    private boolean isScoll;
    private int lastY = 0;
    private MyHandler myHandler;
    private LiveChannelAdpater liveChannelAdpater;
    private List<AppLiveChannel> liveChannels = new ArrayList<>();
    private TextView tvLabelIndicator;
    private ItemLayout itemLayout;

    private AutoPlayTool autoPlayTool = new AutoPlayTool(60, 1);
    //private AutoPlayTool autoPlayTool = new AutoPlayTool(60, 1);

    private int lastType = -1;
    private boolean mIndicatorInit;//指示器是否初始化过
    private boolean mIndicatorEnable = true;//指示器是否可滑动

    public One2OneNewFragment() {
    }

    public One2OneNewFragment(int showType, boolean isOne2OneBig) {
        this.isOne2OneBig = isOne2OneBig;
        this.showType = showType;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one2one_new;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != myHandler) {
            myHandler.sendMessageDelayed(myHandler.obtainMessage(2, 1, 0), 5);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != myHandler) {
            if (mShowed) {
                myHandler.sendMessageDelayed(myHandler.obtainMessage(2, 2, 0), 5);
            } else {
                myHandler.sendMessageDelayed(myHandler.obtainMessage(2, 1, 0), 5);
            }
        }
    }

    @Override
    public void getUserVisibleHintFragment() {
        super.getUserVisibleHintFragment();
        if (null != myHandler) {
            if (mShowed) {
                myHandler.sendMessageDelayed(myHandler.obtainMessage(2, 2, 0), 5);
            } else {
                myHandler.sendMessageDelayed(myHandler.obtainMessage(2, 1, 0), 5);
            }
        }
    }

    @Override
    protected void initView() {
        tvLabelIndicator = mParentView.findViewById(R.id.tvLabelIndicator);

        recyclerView = mParentView.findViewById(R.id.recyclerView);
        layoutLabel = mParentView.findViewById(R.id.layoutLabel);
        recyclerViewLable = mParentView.findViewById(R.id.recyclerView_lable);
        convenientBanner = mParentView.findViewById(R.id.convenientBanner);
        scrollView = mParentView.findViewById(R.id.scroll_view);
        itemLayout = mParentView.findViewById(R.id.item_layout);

        recyclerViewLable.setHasFixedSize(true);
        recyclerViewLable.setNestedScrollingEnabled(false);
        recyclerViewLable.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        liveChannelAdpater = new LiveChannelAdpater(new ArrayList<AppLiveChannel>());
        recyclerViewLable.setAdapter(liveChannelAdpater);
        recyclerViewLable.addItemDecoration(new ItemDecoration(getActivity(), 0x00000000, 12, 0));


        //防止item 交换位置
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.getItemAnimator().setChangeDuration(0);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        gridItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (view.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
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
            }
        };
        mParentView.findViewById(R.id.ivOneScreen).setOnClickListener(this);
        mParentView.findViewById(R.id.ivOneBigSwitch).setOnClickListener(this);
        mParentView.findViewById(R.id.iv_meet).setOnClickListener(this);
        mParentView.findViewById(R.id.layoutChooseChat).setOnClickListener(this);
        mParentView.findViewById(R.id.layoutRequestChat).setOnClickListener(this);
        if (SpUtil.getInstance().getModel("UserInfo", ApiUserInfo.class).sex == 2) {
            ((TextView) mParentView.findViewById(R.id.tvRequestChat)).setText("抢聊");
        }

        refreshLayout = mParentView.findViewById(R.id.refreshLayout);
        ApiUserInfo userInfo = SpUtil.getInstance().getModel("UserInfo", ApiUserInfo.class);
        if (ConfigUtil.getBoolValue(R.bool.mainIndicatorOne2one)) {
            layoutLabel.setVisibility(View.GONE);
            mParentView.findViewById(R.id.flLabelIndicator).setVisibility(View.GONE);
        } else {
            layoutLabel.setVisibility(View.VISIBLE);
            mParentView.findViewById(R.id.flLabelIndicator).setVisibility(View.VISIBLE);
            recyclerViewLable.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    refreshLabelIndicator();
                }
            });
            initLabel();
        }
    }

    private void initLabel() {
        HttpApiHome.getHomeSquareLiveHeader(3, new HttpApiCallBack<HomeDto>() {
            @Override
            public void onHttpRet(int code, String msg, HomeDto retModel) {
                if (code == 1 && null != retModel) {
                    liveChannels.clear();
                    String[] strs = new String[retModel.liveChannels.size()];
                    for (int i = 0; i < retModel.liveChannels.size(); i++) {
                        strs[i] = retModel.liveChannels.get(i).title;
                        AppLiveChannel appLiveChannel = new AppLiveChannel();
                        appLiveChannel.title = retModel.liveChannels.get(i).title;
                        appLiveChannel.id = retModel.liveChannels.get(i).id;
                        liveChannels.add(appLiveChannel);
                        liveChannels.get(0).isChecked = 1;
                    }
                    liveChannelAdpater.setData(liveChannels);
                    showType = (int) liveChannels.get(0).id;

                    isOne2OneBig = true;
                    one2OneHomeAdpater = new One2OneBigHomeAdapter(getActivity(), isOne2OneBig, mProcessResultUtil);
                    recyclerView.setAdapter(one2OneHomeAdpater);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.removeItemDecoration(gridItemDecoration);
                    recyclerView.addItemDecoration(itemDecorationBig);
                    myHandler.sendMessageDelayed(myHandler.obtainMessage(1, recyclerView), 2000);
                    scrollView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            isScoll = false;
                            int eventAction = event.getAction();
                            switch (eventAction) {
                                case MotionEvent.ACTION_UP:
//                                case MotionEvent.ACTION_DOWN:
                                    myHandler.sendMessageDelayed(myHandler.obtainMessage(1, v), 500);
                                    break;
                                default:
                                    break;
                            }
                            return false;
                        }
                    });

                    getAdsList();
                    getRecommendData(true);
                }
            }
        });
        liveChannelAdpater.setOnItemClickListener(new OnBeanCallback<AppLiveChannel>() {
            @Override
            public void onClick(AppLiveChannel bean) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                one2OneHomeAdpater.stopPlayer();
                autoPlayTool.setDestroy();
                showType = (int) bean.id;
                switchListData(showType);
                getAdsList();
                getRecommendData(true);
            }
        });
    }

    public void switchListData(int showType) {
        pageIndex = 0;
//        one2OneHomeAdpater.clearData();
        if (lastType != showType) {
            if (lastType == 3 && showType != 3) {
                isOne2OneBig = true;
                one2OneHomeAdpater = new One2OneBigHomeAdapter(getActivity(), isOne2OneBig, mProcessResultUtil);
                recyclerView.setAdapter(one2OneHomeAdpater);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.removeItemDecoration(gridItemDecoration);
                recyclerView.addItemDecoration(itemDecorationBig);
                scrollView.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        isScoll = false;
                        int eventAction = event.getAction();
                        switch (eventAction) {
                            case MotionEvent.ACTION_UP:
//                            case MotionEvent.ACTION_DOWN:
                                myHandler.sendMessageDelayed(myHandler.obtainMessage(1, v), 500);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
            } else if (lastType != 3 && showType == 3) {
                if (isOne2OneBig) {
                    isOne2OneBig = false;
                    mainRecommendAdpater = new MainRecommendAdapter(getActivity());
                    recyclerView.setAdapter(mainRecommendAdpater);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.removeItemDecoration(itemDecorationBig);
                    recyclerView.addItemDecoration(gridItemDecoration);
                }
            } else {
                if (!isOne2OneBig) {
                    isOne2OneBig = true;
                    one2OneHomeAdpater = new One2OneBigHomeAdapter(getActivity(), isOne2OneBig, mProcessResultUtil);
                    recyclerView.setAdapter(one2OneHomeAdpater);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.removeItemDecoration(gridItemDecoration);
                    recyclerView.addItemDecoration(itemDecorationBig);
                    scrollView.setOnTouchListener(new View.OnTouchListener() {

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            isScoll = false;
                            int eventAction = event.getAction();
                            switch (eventAction) {
                                case MotionEvent.ACTION_UP:
//                                case MotionEvent.ACTION_DOWN:
                                    myHandler.sendMessageDelayed(myHandler.obtainMessage(1, v), 500);
                                    break;
                                default:
                                    break;
                            }
                            return false;
                        }
                    });
                }
            }
        }
        lastType = showType;
    }

    static class MyHandler extends Handler {
        One2OneNewFragment mOne2OneFragment;

        MyHandler(One2OneNewFragment one2OneFragment) {
            mOne2OneFragment = new WeakReference<>(one2OneFragment).get();
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            View scroller = (View) msg.obj;
            switch (msg.what) {
                case 1:
                    if (mOne2OneFragment.lastY == scroller.getScrollY()) {
                        mOne2OneFragment.isScoll = true;
                        mOne2OneFragment.autoPlayTool.onActiveWhenNoScrolling(mOne2OneFragment.recyclerView);
                        Log.e("handler>>>", "播放");
                    } else {
                        mOne2OneFragment.myHandler.sendMessageDelayed(mOne2OneFragment.myHandler.obtainMessage(1, scroller), 5);
                        mOne2OneFragment.autoPlayTool.onScrolledAndDeactivate();
                        mOne2OneFragment.lastY = scroller.getScrollY();
                        Log.e("handler>>>", "停止");
                    }
                    break;
                case 2:
                    if (msg.arg1 == 1) {
                        mOne2OneFragment.autoPlayTool.setPause();
                    } else {
                        mOne2OneFragment.autoPlayTool.setResume();
                    }
                    break;
                default:
                    break;
            }

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {
        mProcessResultUtil = new PermissionsUtil(getActivity());
        myHandler = new MyHandler(this);
        itemDecorationBig = new ItemDecoration(getActivity(), 0x00000000, 0, 5);
        if (isOne2OneBig) {
            one2OneHomeAdpater = new One2OneBigHomeAdapter(getActivity(), isOne2OneBig, mProcessResultUtil);
            recyclerView.setAdapter(one2OneHomeAdpater);
            recyclerView.addItemDecoration(itemDecorationBig);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            myHandler.sendMessageDelayed(myHandler.obtainMessage(1, recyclerView), 500);
            scrollView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    isScoll = false;
                    int eventAction = event.getAction();
                    switch (eventAction) {
                        case MotionEvent.ACTION_UP:
//                        case MotionEvent.ACTION_DOWN:
                            myHandler.sendMessageDelayed(myHandler.obtainMessage(1, v), 500);
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });
        } else {
            mainRecommendAdpater = new MainRecommendAdapter(getActivity());
            recyclerView.setAdapter(mainRecommendAdpater);
            recyclerView.addItemDecoration(gridItemDecoration);
            recyclerView.setLayoutManager(layoutManager);
        }
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 0;
                getAdsList();
                getRecommendData(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                getRecommendData(false);

            }
        });
        if (ConfigUtil.getBoolValue(R.bool.mainIndicatorOne2one)) {
            getRecommendData(true);
            getAdsList();
        }
    }

    /**
     * 获取广告
     */
    private void getAdsList() {
        HttpApiAppLogin.adslist(5, showType, new HttpApiCallBackArr<AppAds>() {
            @Override
            public void onHttpRet(int code, String msg, List<AppAds> retModel) {
                if (code == 1 && null != retModel && retModel.size() > 0) {
                    initBanner(retModel);
                    convenientBanner.setVisibility(View.VISIBLE);
                    itemLayout.setVisibility(View.VISIBLE);
                } else {
                    convenientBanner.setVisibility(View.GONE);
                    itemLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    public void getRecommendData(final boolean isRefresh) {
        if (isOne2OneBig) {
            /**
             * 首页列表数据
             * @param address 地址
             * @param channelId 频道ID
             * @param headNo 头部分类id(取头部接口中的showType),logicType=2时传入其他传-1
             * @param hotSortId 热门类型ID
             * @param isRecommend 是否推荐-1全部0不推荐1已推荐
             * @param logicType 业务类型  -1:全部  0：直播， 1、语音 2一对一  3动态
             * @param pageIndex 当前页
             * @param pageSize 每页大小
             * @param sex 性别
             * @param tabIds 标签ID数组， 逗号隔开
             */

            HttpApiHome.getHomO2ODataList(address, showType, -1, (float) SpUtil.getInstance().getSharedPreference(SpUtil.LATITUDE, HttpConstants.LAT), (float) SpUtil.getInstance().getSharedPreference(SpUtil.LONGITUDE, HttpConstants.LNG), pageIndex, HttpConstants.PAGE_SIZE, sex == -1 ? 0 : sex, tabId, new HttpApiCallBackArr<HomeO2OData>() {
                @Override
                public void onHttpRet(int code, String msg, List<HomeO2OData> retModel) {
                    if (code == 1 && null != retModel) {
                        if (isRefresh) {
                            homeO2ODataList.clear();
                            homeO2ODataList.addAll(retModel);
                            refreshLayout.finishRefresh();
                            one2OneHomeAdpater.RefreshData(homeO2ODataList);
                        } else {
                            homeO2ODataList.addAll(retModel);
                            one2OneHomeAdpater.loadData(retModel);
                            refreshLayout.finishLoadMore();
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
        } else {
            HttpApiHome.getHomeDataList(address, showType, -1, -1, -1, 3, pageIndex, HttpConstants.PAGE_SIZE, sex == -1 ? 0 : sex, tabId, new HttpApiCallBackArr<AppHomeHallDTO>() {
                @Override
                public void onHttpRet(int code, String msg, List<AppHomeHallDTO> retModel) {
                    if (code == 1 && null != retModel) {
                        if (isRefresh) {
                            appHomeHallList.clear();
                            appHomeHallList.addAll(retModel);
                            refreshLayout.finishRefresh();
                            mainRecommendAdpater.RefreshData(appHomeHallList);
                        } else {
                            appHomeHallList.addAll(retModel);
                            refreshLayout.finishLoadMore();
                            mainRecommendAdpater.loadData(retModel);
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
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.ivOneBigSwitch) {
            isOne2OneBig = !isOne2OneBig;
            if (isOne2OneBig) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            } else {
                recyclerView.setLayoutManager(layoutManager);
            }
            if (isOne2OneBig) {
                one2OneHomeAdpater = new One2OneBigHomeAdapter(getActivity(), isOne2OneBig, mProcessResultUtil);
                recyclerView.setAdapter(one2OneHomeAdpater);
                recyclerView.removeItemDecoration(gridItemDecoration);
                recyclerView.addItemDecoration(itemDecorationBig);
                scrollView.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        isScoll = false;
                        int eventAction = event.getAction();
                        switch (eventAction) {
                            case MotionEvent.ACTION_UP:
//                            case MotionEvent.ACTION_DOWN:
                                myHandler.sendMessageDelayed(myHandler.obtainMessage(1, v), 500);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                one2OneHomeAdpater.RefreshData(homeO2ODataList);
            } else {
                mainRecommendAdpater = new MainRecommendAdapter(getActivity());
                recyclerView.setAdapter(mainRecommendAdpater);
                recyclerView.removeItemDecoration(itemDecorationBig);
                recyclerView.addItemDecoration(gridItemDecoration);
                mainRecommendAdpater.RefreshData(appHomeHallList);
            }

            pageIndex = 0;
            getRecommendData(true);
        } else if (view.getId() == R.id.ivOneScreen) {
            O2OSearchDialog o2OSearchDialog = new O2OSearchDialog();
            o2OSearchDialog.setO2OSearchListener(new O2OSearchDialog.O2OSearchListener() {
                @Override
                public void onSearch(String address, int sex, String tabId) {
                    One2OneNewFragment.this.address = address;
                    One2OneNewFragment.this.sex = sex;
                    One2OneNewFragment.this.tabId = tabId;
                    pageIndex = 0;
                    getRecommendData(true);
                }
            });
            Bundle bundle = new Bundle();
            bundle.putString(O2OSearchDialog.O20_SEARCH_ADDRESS, address);
            bundle.putInt(O2OSearchDialog.O20_SEARCH_SEX, sex);
            bundle.putString(O2OSearchDialog.O20_SEARCH_TAB, tabId);
            o2OSearchDialog.setArguments(bundle);
            o2OSearchDialog.show(getActivity().getSupportFragmentManager(), "O2OSearchDialog");
        } else if (view.getId() == R.id.iv_meet) {
            HttpApiAppUser.personCenter(-1, -1, HttpClient.getUid(), new HttpApiCallBack<ApiUserInfo>() {
                @Override
                public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                    if (code == 1 && retModel != null) {
                        if (retModel.role == 0) {
                            ARouter.getInstance().build(ARouterPath.MeetAudienceSingleActivity).navigation();
                        } else {
                            ARouter.getInstance().build(ARouterPath.MeetAnchorActivity).navigation();
                        }
                    }
                }
            });

        } else if (view.getId() == R.id.layoutChooseChat) {//选聊
            HttpApiAppUser.personCenter(-1, -1, HttpClient.getUid(), new HttpApiCallBack<ApiUserInfo>() {
                @Override
                public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                    if (code == 1 && retModel != null) {
                        if (retModel.role == 0) {
                            ARouter.getInstance().build(ARouterPath.MeetAudienceManyActivity).navigation();
                        } else {
                            ARouter.getInstance().build(ARouterPath.MeetAnchorActivity).navigation();
                        }
                    }
                }
            });
        } else if (view.getId() == R.id.layoutRequestChat) {//求聊
            EventBus.getDefault().post(new RequestChatEvent(((TextView) mParentView.findViewById(R.id.tvRequestChat)).getText().toString().trim()));
        }
    }

}