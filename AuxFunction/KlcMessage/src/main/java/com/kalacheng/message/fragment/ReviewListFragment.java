package com.kalacheng.message.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.busshortvideo.httpApi.HttpApiAppShortVideo;
import com.kalacheng.commonview.jguangIm.UnReadCountEvent;
import com.kalacheng.dynamiccircle.utlis.InputPopwindow;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackPageArr;
import com.kalacheng.base.http.PageInfo;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiAppVideo;
import com.kalacheng.libuser.httpApi.HttpApiChatRoom;
import com.kalacheng.libuser.model.ApiCommentsMsg;
import com.kalacheng.libuser.model.ApiNoRead;
import com.kalacheng.libuser.model.ApiUsersVideoComments;
import com.kalacheng.message.R;
import com.kalacheng.message.activity.TrendActivity;
import com.kalacheng.message.adapter.ReviewsAdapter;
import com.kalacheng.message.event.ReadMsgEvent;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 评论   1:动态;2:短视频
 */
public class ReviewListFragment extends BaseFragment implements OnLoadMoreListener, OnRefreshListener, ReviewsAdapter.OnClick {

    private SmartRefreshLayout smartRefresh;
    private RecyclerView recyclerView;
    private ReviewsAdapter adapter;
    private LinearLayout llReviews;
    private TextView tv_no_data;

    private int page;
    private int mType; // ==1 动态   ==2 短视频
    private int pageStatus; // 1是动态  4是短视频

    /**
     * @param type 1:动态;2:短视频
     */
    public ReviewListFragment(int type) {
        this.mType = type;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_reviews_list;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (pageStatus != 0) {
            clearMessage();
        }
    }

    @Override
    protected void initView() {
        adapter = new ReviewsAdapter();
        adapter.setOnClick(this);
        tv_no_data = mParentView.findViewById(R.id.tv_no_data);
        llReviews = mParentView.findViewById(R.id.llReviews);
        smartRefresh = mParentView.findViewById(R.id.smartRefresh);
        smartRefresh.setOnRefreshListener(this);
        smartRefresh.setOnLoadMoreListener(this);

        recyclerView = mParentView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        if (mType == 1) {
            pageStatus = 1;
        } else {
            pageStatus = 4;
        }
    }

    @Override
    protected void initData() {

    }

    /**
     * 动态回复消息列表
     */
    private void getData() {
        if (mType == 1) {
            HttpApiChatRoom.videoCommentsList(page, HttpConstants.PAGE_SIZE, new HttpApiCallBackPageArr<ApiCommentsMsg>() {
                @Override
                public void onHttpRet(int code, String msg, PageInfo pageInfo, List<ApiCommentsMsg> retModel) {
                    onSuccess(code, retModel);
                }
            });
        } else {
            HttpApiChatRoom.shortVideoCommentsList(page, HttpConstants.PAGE_SIZE, new HttpApiCallBackPageArr<ApiCommentsMsg>() {
                @Override
                public void onHttpRet(int code, String msg, PageInfo pageInfo, List<ApiCommentsMsg> retModel) {
                    onSuccess(code, retModel);
                }
            });
        }
    }

    // 动态评论 / 短视频评论设置为已读
    private void clearMessage() {
        HttpApiChatRoom.clearNoticeMsg(-1, pageStatus, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    getData();
                    getMessage();
                }
            }
        });
    }

    /**
     * 获取系统通知未读数
     */
    private void getMessage() {
        HttpApiChatRoom.getAppSystemNoRead(new HttpApiCallBack<ApiNoRead>() {
            @Override
            public void onHttpRet(int code, String msg, ApiNoRead retModel) {
                if (code == 1 && retModel != null) {
                    EventBus.getDefault().post(new UnReadCountEvent(retModel.totalNoRead, retModel.systemNoRead, retModel.videoNoRead, retModel.shortVideoNoRead, retModel.officialNewsNoRead));
                    EventBus.getDefault().post(new ReadMsgEvent());
                }
            }
        });
    }

    private void onSuccess(int code, List<ApiCommentsMsg> retModel) {
        if (code == 1 && retModel != null) {
            if (page == 0) {
                adapter.refreshData(retModel);
                smartRefresh.finishRefresh();
                if (null != retModel && retModel.size() > 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    tv_no_data.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    tv_no_data.setVisibility(View.VISIBLE);
                }
            } else {
                adapter.loadData(retModel);
                smartRefresh.finishLoadMore();
            }
        }
        getUnReadReviewsCount();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        getData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 0;
        getData();
    }

    /**
     * 获取系统通知未读数
     */
    private void getUnReadReviewsCount() {
        HttpApiChatRoom.getAppSystemNoRead(new HttpApiCallBack<ApiNoRead>() {
            @Override
            public void onHttpRet(int code, String msg, ApiNoRead retModel) {
                if (code == 1) {
                    EventBus.getDefault().post(new UnReadCountEvent(retModel.totalNoRead, retModel.systemNoRead, retModel.videoNoRead, retModel.shortVideoNoRead, retModel.officialNewsNoRead));
                }
            }
        });
    }

    /**
     * 删除动态评论
     */
    private void deleteDynamicComment(ApiCommentsMsg noticeUser, final int poistion) {
        HttpApiAppVideo.delComment(noticeUser.commentId, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    adapter.delete(poistion);
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * 删除短视频评论
     */
    private void deleteVideoComment(ApiCommentsMsg noticeUser, final int poistion) {
        HttpApiAppShortVideo.delShortVideoComment(noticeUser.commentId, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    adapter.delete(poistion);
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * 回复
     */
    public void onReply(int type, long id, String name) {
        InputPopwindow popwindow = new InputPopwindow(getActivity());
        popwindow.showShadow(pageStatus != 1, llReviews, type, id, false, false, name);
        popwindow.setInputSuccessCallBack(new InputPopwindow.InputSuccessCallBack() {
            @Override
            public void Success(ApiUsersVideoComments comments1) {
                //binding.getBean().commentList.add(0, comments1);
            }
        });
    }

    @Override
    public void onClick(ApiCommentsMsg noticeUser) {
        if (mType == 1) {
            startActivityForResult(new Intent(getContext(), TrendActivity.class)
                    .putExtra("userName", noticeUser.userName)
                    .putExtra("commentId", noticeUser.commentId)
                    .putExtra("type", noticeUser.type)
                    .putExtra("videoId", noticeUser.videoId), 111);
        } else {
            ARouter.getInstance().build(ARouterPath.VideoPlayActivity)
                    .withInt(ARouterValueNameConfig.VIDEO_TYPE, 4)
                    .withInt(ARouterValueNameConfig.VIDEO_WORKS_TYPE, (int) noticeUser.videoId)
                    .withInt(ARouterValueNameConfig.MESSAGE_TYPE, noticeUser.type)
                    .withInt(ARouterValueNameConfig.COMMENT_ID, (int) noticeUser.commentId)
                    .navigation();
        }
    }

    @Override
    public void onReply(ApiCommentsMsg noticeUser) {
        if (noticeUser.type == 1) {//评论，回复消息
            onReply(2, noticeUser.commentId, noticeUser.userName);
        } else {//点赞，直接评论动态
            onReply(1, noticeUser.videoId, noticeUser.userName);
        }
    }

    @Override
    public void onDelete(final ApiCommentsMsg noticeUser, final int position) {
        DialogUtil.showPublicTips(getActivity(), "删除评论", "是否删除这条评论", new DialogUtil.CurrencyCallback() {
            @Override
            public void onConfirmClick() {
                if (mType == 1) {
                    deleteDynamicComment(noticeUser, position);
                } else {
                    deleteVideoComment(noticeUser, position);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e("requestCode = " + requestCode + "resultCode = " + resultCode);
        if (requestCode == 111) {
            getUnReadReviewsCount();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
