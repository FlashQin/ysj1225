package com.kalacheng.shortvideo.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busshortvideo.httpApi.HttpApiAppShortVideo;
import com.kalacheng.base.event.VideoZanEvent;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.libuser.model.ApiShortVideoDto;
import com.kalacheng.libuser.model.AppHotSort;
import com.kalacheng.shortvideo.adapter.VideoClassifyAdpater;
import com.kalacheng.shortvideo.event.DeleteShortVideoItemEvent;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.base.http.HttpApiCallBackPageArr;
import com.kalacheng.base.http.PageInfo;
import com.kalacheng.shortvideo.R;
import com.kalacheng.util.view.ItemDecoration;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

// 看点 - 分类 （最多观看、最多评论、最多点赞、最多付费）
@Route(path = ARouterPath.VideoClassifyActivity)
public class VideoClassifyActivity extends BaseActivity {
    private static String TAG = VideoClassifyActivity.class.getSimpleName();

    @Autowired(name = ARouterValueNameConfig.AppHotSort)
    public AppHotSort appHotSort;

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private int page = 0;
    private VideoClassifyAdpater videoClassifyAdpater;
    private List<ApiShortVideoDto> apiShortVideoDtos = new ArrayList<>();

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_recycleview);
        initView();
    }

    protected void initView() {
        EventBus.getDefault().register(this);

        TextView textView = findViewById(R.id.titleView);
        if (appHotSort.sort == 1) {
            textView.setText("最多观看");
        } else if (appHotSort.sort == 2) {
            textView.setText("最多评论");
        } else if (appHotSort.sort == 3) {
            textView.setText("最多点赞");
        } else if (appHotSort.sort == 4) {
            textView.setText("最多付费");
        } else {
            textView.setText(appHotSort.name);
        }
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        videoClassifyAdpater = new VideoClassifyAdpater();
        recyclerView.setAdapter(videoClassifyAdpater);
        recyclerView.addItemDecoration(new ItemDecoration(this, 0x00000000, 4, 4));
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                getData(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getData(false);

            }
        });
        getData(true);
        videoClassifyAdpater.setOnItemClickListener(new OnBeanCallback<ApiShortVideoDto>() {
            @Override
            public void onClick(ApiShortVideoDto bean) {
                int position = apiShortVideoDtos.indexOf(bean);
                long classifyId;
                if (appHotSort.sort == 1 || appHotSort.sort == 2 || appHotSort.sort == 3 || appHotSort.sort == 4) {
                    classifyId = -1;
                } else {
                    classifyId = appHotSort.id;
                }
                ARouter.getInstance().build(ARouterPath.VideoPlayActivity)
                        .withInt(ARouterValueNameConfig.VIDEO_TYPE, 2)
                        .withInt(ARouterValueNameConfig.POSITION, position)
                        .withParcelableArrayList(ARouterValueNameConfig.Beans, (ArrayList<? extends Parcelable>) apiShortVideoDtos)
                        .withInt(ARouterValueNameConfig.VIDEO_PAGE, page)
                        .withLong(ARouterValueNameConfig.CLASSIFY_ID, classifyId)
                        .withLong(ARouterValueNameConfig.VIDEO_SORT, appHotSort.sort)
                        .withInt(ARouterValueNameConfig.ITEM_POSITION, position)
                        .withString(ARouterValueNameConfig.COMMENT_LOCATION, TAG)
                        .navigation();
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeleteShortVideoItemEvent(DeleteShortVideoItemEvent message) {
        long id = null != message && null != message.appShortVideo ? message.appShortVideo.id : 0;
        L.e(TAG, "onDeleteShortVideoItemEvent  网格");
        L.e(TAG, "onDeleteShortVideoItemEvent  appHotSort.sort " + (null != appHotSort ? appHotSort.sort : 0));
        L.e(TAG, "onDeleteShortVideoItemEvent  " + id);
        if(videoClassifyAdpater != null && id != 0) {
            int deleteIndex = -1;
            //通过 动态的id获取相对列表中的索引位置
            for (int i = 0; i < videoClassifyAdpater.getData().size(); i++) {
                if(videoClassifyAdpater.getData().get(i).id == id) {
                    deleteIndex = i;
                }
            }
            if(deleteIndex != -1) {
                videoClassifyAdpater.getData().remove(deleteIndex);
                videoClassifyAdpater.notifyDataSetChanged();
            }
        }
    }

    //修改 评论点赞
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setZanCommentEvent(VideoZanEvent event) {
        try {
            if (null != event && event.getLocation().equals(TAG)){
                videoClassifyAdpater.setZanComment(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getData(final boolean isRefresh) {
        long classifyId;
        if (appHotSort.sort == 1 || appHotSort.sort == 2 || appHotSort.sort == 3 || appHotSort.sort == 4) {
            classifyId = -1;
        } else {
            classifyId = appHotSort.id;
        }

        HttpApiAppShortVideo.getShortVideoList(-1, classifyId, page, HttpConstants.PAGE_SIZE, appHotSort.sort, -1, new HttpApiCallBackPageArr<ApiShortVideoDto>() {
            @Override
            public void onHttpRet(int code, String msg, PageInfo pageInfo, List<ApiShortVideoDto> retModel) {
                if (isRefresh)
                    refreshLayout.finishRefresh();
                else
                    refreshLayout.finishLoadMore();
                if (code == 1 && null != retModel) {
                    if (isRefresh) {
                        apiShortVideoDtos.clear();
                        apiShortVideoDtos.addAll(retModel);
                        videoClassifyAdpater.setData(retModel);
                    } else {
                        apiShortVideoDtos.addAll(retModel);
                        videoClassifyAdpater.addData(retModel);
                    }
                }

            }
        });
    }
}
