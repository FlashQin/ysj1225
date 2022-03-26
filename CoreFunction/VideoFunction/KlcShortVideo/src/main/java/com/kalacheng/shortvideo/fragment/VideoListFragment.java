package com.kalacheng.shortvideo.fragment;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busshortvideo.httpApi.HttpApiAppShortVideo;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.libuser.model.ApiShortVideoDto;
import com.kalacheng.shortvideo.R;
import com.kalacheng.shortvideo.adapter.MyVideoViewAdpater;
import com.kalacheng.shortvideo.event.DeleteShortVideoItemEvent;
import com.kalacheng.shortvideo.event.ShortVideosEvent;
import com.kalacheng.util.view.ItemDecoration;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.base.http.HttpApiCallBackPageArr;
import com.kalacheng.base.http.PageInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的视图 个人资料 视频列表 fragment (作品/喜欢/购买   列表fragment)
 */
public class VideoListFragment extends BaseFragment {
    private static String TAG = VideoListFragment.class.getSimpleName();

    private int type;
    private long toUid;
    private boolean enableRefresh;
    private int page = 0;

    private SmartRefreshLayout refreshVideoList;
    private RecyclerView recyclerViewVideo;
    private MyVideoViewAdpater myVideoViewAdpater;

    public VideoListFragment() {

    }

    public VideoListFragment(int type, long toUid, boolean enableRefresh) {
        this.type = type;
        this.toUid = toUid;
        this.enableRefresh = enableRefresh;
    }

    @Override
    public void getUserVisibleHintFragment() {
        super.getUserVisibleHintFragment();
        if (mShowed) {
            page = 0;
            getUserShortVideoPage();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_list;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        refreshVideoList = mParentView.findViewById(R.id.refreshVideoList);
        recyclerViewVideo = mParentView.findViewById(R.id.recyclerView_video);
        recyclerViewVideo.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerViewVideo.setHasFixedSize(true);
        recyclerViewVideo.setNestedScrollingEnabled(false);
        myVideoViewAdpater = new MyVideoViewAdpater();
        recyclerViewVideo.setAdapter(myVideoViewAdpater);
        recyclerViewVideo.addItemDecoration(new ItemDecoration(getActivity(), 0x00000000, 3, 3));

        myVideoViewAdpater.setOnItemClickListener(new OnBeanCallback<ApiShortVideoDto>() {
            @Override
            public void onClick(ApiShortVideoDto bean) {
                int position = myVideoViewAdpater.getData().indexOf(bean);
                ARouter.getInstance().build(ARouterPath.VideoPlayActivity)
                        .withInt(ARouterValueNameConfig.VIDEO_TYPE, 3)
                        .withInt(ARouterValueNameConfig.POSITION, position)
                        .withParcelableArrayList(ARouterValueNameConfig.Beans, (ArrayList<? extends Parcelable>) myVideoViewAdpater.getData())
                        .withInt(ARouterValueNameConfig.VIDEO_PAGE, page)
                        .withLong(ARouterValueNameConfig.VIDEO_WORKS_USER_ID, toUid)
                        .withInt(ARouterValueNameConfig.VIDEO_WORKS_TYPE, type)
                        .navigation();
            }
        });

        refreshVideoList.setEnableRefresh(enableRefresh);
        refreshVideoList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                getUserShortVideoPage();
            }
        });
        refreshVideoList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getUserShortVideoPage();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeleteShortVideoItemEvent(DeleteShortVideoItemEvent message) {
        long id = null != message && null != message.appShortVideo ? message.appShortVideo.id : 0;
        L.e(TAG, "onDeleteShortVideoItemEvent  网格");
        L.e(TAG, "onDeleteShortVideoItemEvent  type " + type + "  toUid  " + toUid);
        L.e(TAG, "onDeleteShortVideoItemEvent  " + id);

        if (type != 3) {
            if (myVideoViewAdpater != null && id != 0) {
                int deleteIndex = -1;

                //通过 动态的id获取相对列表中的索引位置
                for (int i = 0; i < myVideoViewAdpater.getData().size(); i++) {
                    if (myVideoViewAdpater.getData().get(i).id == id) {
                        deleteIndex = i;
                    }
                }

                if (deleteIndex != -1) {
                    myVideoViewAdpater.getData().remove(deleteIndex);
                    myVideoViewAdpater.notifyDataSetChanged();
                }
            }
        } else {
            L.e(TAG, "onDeleteShortVideoItemEvent  购买视频，不显示自己的视频，不用处理");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(ShortVideosEvent event) {
        try {
            if (type == event.type){
                if (null != event.appShortVideos && event.appShortVideos.size() > 0) {
                    myVideoViewAdpater.setData(event.appShortVideos);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @Override
    protected void initData() {
        getUserShortVideoPage();
    }

    private void getUserShortVideoPage() {
        HttpApiAppShortVideo.getUserShortVideoPage(page, HttpConstants.PAGE_SIZE, toUid, type, new HttpApiCallBackPageArr<ApiShortVideoDto>() {
            @Override
            public void onHttpRet(int code, String msg, PageInfo pageInfo, List<ApiShortVideoDto> retModel) {
                refreshVideoList.finishRefresh();
                refreshVideoList.finishLoadMore();
                if (code == 1 && retModel != null && !retModel.isEmpty()) {
                    if (page == 0) {
                        myVideoViewAdpater.setData(retModel);
                    } else {
                        myVideoViewAdpater.addData(retModel);
                    }
                }
            }
        });
    }

    @Override
    public void refreshData() {
        super.refreshData();
        page = 0;
        getUserShortVideoPage();
    }
}
