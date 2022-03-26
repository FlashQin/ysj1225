package com.kalacheng.dynamiccircle.fragment;

import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.base.listener.OnItemClickListener;
import com.kalacheng.dynamiccircle.R;
import com.kalacheng.dynamiccircle.adpater.CommunityAdapter;
import com.kalacheng.dynamiccircle.event.DeleteVideoItemEvent;
import com.kalacheng.base.event.VideoZanEvent;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBackPageArr;
import com.kalacheng.base.http.PageInfo;
import com.kalacheng.libuser.httpApi.HttpApiAppVideo;
import com.kalacheng.libuser.model.ApiUserVideo;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.L;
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
 * 社区列表
 */
public class CommunityListFragment extends BaseFragment {
    private static String TAG = CommunityListFragment.class.getSimpleName();
    private boolean enableRefresh = true;
    int page = 0;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private CommunityAdapter adapter;

    private TextView Community_NoData;
    private int type = 0;
    private int hotId = 0;
    private long uid = 0;
    private String location = "";

    public CommunityListFragment() {

    }

    public CommunityListFragment(int type, long uid) {
        this.type = type;
        this.uid = uid;
    }

    public CommunityListFragment(int type, long uid, boolean enableRefresh) {
        this.type = type;
        this.uid = uid;
        this.enableRefresh = enableRefresh;
    }

    public CommunityListFragment(int type, int hotId, long uid) {
        this.type = type;
        this.hotId = hotId;
        this.uid = uid;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community_list;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        location = TAG + type;

        refreshLayout = mParentView.findViewById(R.id.refreshLayout);
        recyclerView = mParentView.findViewById(R.id.recyclerView);
        Community_NoData = mParentView.findViewById(R.id.Community_NoData);

        adapter = new CommunityAdapter();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener<ApiUserVideo>() {
            @Override
            public void onItemClick(int position, ApiUserVideo bean) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                ARouter.getInstance().build(ARouterPath.LookVideo)
                        .withInt(ARouterValueNameConfig.VIDEO_TYPE, 0)
                        .withInt(ARouterValueNameConfig.POSITION, position)
                        .withParcelableArrayList(ARouterValueNameConfig.Beans, (ArrayList<? extends Parcelable>) adapter.getData())
                        .withInt(ARouterValueNameConfig.VIDEO_PAGE, page)
                        .withInt(ARouterValueNameConfig.COMMUNITY_TYPE, type)
                        .withInt(ARouterValueNameConfig.COMMUNITY_HOT_ID, hotId)
                        .withLong(ARouterValueNameConfig.COMMUNITY_UID, uid)
                        .withInt(ARouterValueNameConfig.ITEM_POSITION, position)
                        .withString(ARouterValueNameConfig.COMMENT_LOCATION, location)
                        .navigation();
            }
        });

        refreshLayout.setEnableRefresh(enableRefresh);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                getDynamicListData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getDynamicListData();
            }
        });

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initData() {
        getDynamicListData();
    }

    @Override
    public void refreshData() {
        super.refreshData();
        page = 0;
        getDynamicListData();
    }

    //获取动态列表
    public void getDynamicListData() {
        HttpApiAppVideo.getVideoList(hotId, page, HttpConstants.PAGE_SIZE, (int) uid, type, new HttpApiCallBackPageArr<ApiUserVideo>() {
            @Override
            public void onHttpRet(int code, String msg, PageInfo pageInfo, List<ApiUserVideo> retModel) {
                if (code == 1 && retModel != null) {
                    if (page == 0) {
                        adapter.setData(retModel);
                        refreshLayout.finishRefresh();
                    } else {
                        adapter.loadData(retModel);
                        refreshLayout.finishLoadMore();
                    }
                    refreshLayout.setEnableLoadMore(retModel.size() == HttpConstants.PAGE_SIZE);
                } else {
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                }

                if (adapter.getItemCount() > 0) {
                    Community_NoData.setVisibility(View.GONE);
                } else {
                    Community_NoData.setVisibility(View.VISIBLE);
                }

                switch (type) {
                    case 0:
                        Community_NoData.setText("暂时没有动态\n去其他页面看看吧！");
                        break;
                    case 1:
                        Community_NoData.setText("暂时没有推荐动态\n去其他页面看看吧！");
                        break;
                    case 2:
                        Community_NoData.setText("你还没有关注任何人\n去其他页面看看吧！");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    //修改 评论点赞
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setZanCommentEvent(VideoZanEvent event) {
        if (null != event && event.getLocation().equals(location)){
            adapter.setZanComment(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeleteVideoItemEvent(DeleteVideoItemEvent message) {
        int id = null != message && null != message.apiUserVideo ? message.apiUserVideo.id : 0;
        L.e(TAG, "onDeleteVideoItemEvent  瀑布流");
        L.e(TAG, "onDeleteVideoItemEvent  type " + type + "  hotId  " + hotId);
        L.e(TAG, "onDeleteVideoItemEvent  " + id);

        if (adapter != null && id != 0) {
            int deleteIndex = -1;
            //通过 动态的id获取相对列表中的索引位置
            for (int i = 0; i < adapter.getData().size(); i++) {
                if (adapter.getData().get(i).id == id) {
                    deleteIndex = i;
                }
            }
            if (deleteIndex != -1) {
                adapter.getData().remove(deleteIndex);
                adapter.notifyDataSetChanged();
            }
        }
    }

}
