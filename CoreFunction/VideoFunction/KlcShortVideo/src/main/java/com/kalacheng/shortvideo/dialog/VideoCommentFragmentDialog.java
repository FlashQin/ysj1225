package com.kalacheng.shortvideo.dialog;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.busshortvideo.httpApi.HttpApiAppShortVideo;
import com.kalacheng.commonview.listener.OnTrendCommentItemClickListener;
import com.kalacheng.dynamiccircle.R;
import com.kalacheng.dynamiccircle.adpater.TrendCommentAdpater;
import com.kalacheng.dynamiccircle.utlis.InputPopwindow;
import com.kalacheng.base.event.VideoZanEvent;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBackPageArr;
import com.kalacheng.base.http.PageInfo;
import com.kalacheng.libuser.model.ApiShortVideoDto;
import com.kalacheng.libuser.model.ApiUsersVideoComments;
import com.kalacheng.util.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 动态视频评论列表
 */
public class VideoCommentFragmentDialog extends BaseDialogFragment implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {

    private ApiShortVideoDto bean;

    private TextView tvCommentNum;
    private SmartRefreshLayout Smart_commentList;
    private RecyclerView refreshView;
    private TrendCommentAdpater adpater;

    private int page = 0;
    private int active = 1;//1刷新 2加载
    private int mCommentNum;//评论数量
    private OnCommentNumChangeListener onCommentNumChangeListener;//评论数量变化回调

    private int position;
    private String location;

    @Override
    protected int getLayoutId() {
        return R.layout.view_video_comment;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        //防止软键盘弹出时，头部被挤压
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        bean = getArguments().getParcelable(ARouterValueNameConfig.ACTIVITYBEAN);
        if (bean == null) {
            return;
        }
        getInitView();
        getComment();
    }

    @SuppressLint("WrongConstant")
    public void getInitView() {
        refreshView = mRootView.findViewById(R.id.refreshView);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        refreshView.setLayoutManager(manager);
        adpater = new TrendCommentAdpater(mContext);
        refreshView.setAdapter(adpater);

        adpater.setOnTrendCommentItemClickListener(new OnTrendCommentItemClickListener() {
            @Override
            public void onItemClick(ApiUsersVideoComments commentBean) {
                InputPopwindow popwindow = new InputPopwindow(mContext);
                popwindow.showShadow(true, mRootView.findViewById(R.id.input), 2, commentBean.commentid, false, commentBean.userName);
                popwindow.setInputSuccessCallBack(new InputPopwindow.InputSuccessCallBack() {
                    @Override
                    public void Success(ApiUsersVideoComments comments) {
                        insertComment(comments);
                    }
                });
            }

            @Override
            public void onUserName(ApiUsersVideoComments commentBean) {
//                InputPopwindow popwindow = new InputPopwindow(mContext);
//                popwindow.showShadow(mRootView.findViewById(R.id.input), 2, commentBean.commentid, false);
//                popwindow.setInputSuccessCallBack(new InputPopwindow.InputSuccessCallBack() {
//                    @Override
//                    public void Success(ApiUsersVideoComments comments) {
//                        Smart_commentList.finishRefresh();
//                    }
//                });
            }

            @Override
            public void onToUserName(ApiUsersVideoComments commentBean) {
//                InputPopwindow popwindow = new InputPopwindow(mContext);
//                popwindow.showShadow(mRootView.findViewById(R.id.input), 2, commentBean.commentid, false);
//                popwindow.setInputSuccessCallBack(new InputPopwindow.InputSuccessCallBack() {
//                    @Override
//                    public void Success(ApiUsersVideoComments comments) {
//                        Smart_commentList.finishRefresh();
//                    }
//                });
            }
        });
        mRootView.findViewById(R.id.input).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_face).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_close).setOnClickListener(this);
        tvCommentNum = mRootView.findViewById(R.id.comment_num);
        tvCommentNum.setText(bean.comments + "评论");
        mCommentNum = bean.comments;

        Smart_commentList = mRootView.findViewById(R.id.Smart_commentList);
        Smart_commentList.setOnRefreshListener(this);
        Smart_commentList.setOnLoadMoreListener(this);
    }

    /**
     * 获取评论列表
     */
    private void getComment() {
        HttpApiAppShortVideo.getShortVideoCommentBasicInfo(page, 10, bean.id, new HttpApiCallBackPageArr<ApiUsersVideoComments>() {
            @Override
            public void onHttpRet(int code, String msg, PageInfo pageInfo, List<ApiUsersVideoComments> retModel) {
                if (code == 1) {
                    if (active == 1) {
                        if (retModel != null) {
                            adpater.getRefresh(retModel);
                        }
                        Smart_commentList.finishRefresh();
                    } else {
                        if (retModel != null) {
                            adpater.getLoadData(retModel);
                        }
                        Smart_commentList.finishLoadMore();
                    }

                } else {
                    ToastUtil.show(msg);
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.input) {/*       InputDialogFragment dialogFragment = new InputDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(ARouterValueNameConfig.COMMEMTBEAN,bean);
                bundle.putInt(ARouterValueNameConfig.COMMENTTYPE, APPProConfig.CommentType);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "InputDialogFragment");
                dialogFragment.setOnTrendCommentSuccess(new InputDialogFragment.OnTrendCommentSuccess() {
                    @Override
                    public void onCommentSuccess() {//评论成功回调
                        Smart_commentList.finishRefresh();
                        dialogFragment.dismiss();
                    }
                });*/

//                FragmentTransaction mFragTransaction = getFragmentManager().beginTransaction();
//                Fragment fragment = getFragmentManager().findFragmentByTag("InputDialogFragment");
//                if (fragment != null) {
//                    // 为了不重复显示dialog，在显示对话框之前移除正在显示的对话框
//                    mFragTransaction.remove(fragment);
//                }
            InputPopwindow popwindow = new InputPopwindow(mContext);
            popwindow.showShadow(true, mRootView.findViewById(R.id.input), 1, bean.id, false, "");
            popwindow.setInputSuccessCallBack(new InputPopwindow.InputSuccessCallBack() {
                @Override
                public void Success(ApiUsersVideoComments comments) {
                    insertComment(comments);
                }
            });
        } else if (i == R.id.btn_face) {
            InputPopwindow facepopwindow = new InputPopwindow(mContext);
            facepopwindow.showShadow(true, mRootView.findViewById(R.id.input), 1, bean.id, true, "");
            facepopwindow.setInputSuccessCallBack(new InputPopwindow.InputSuccessCallBack() {
                @Override
                public void Success(ApiUsersVideoComments comments) {
                    insertComment(comments);
                }
            });
        } else if (i == R.id.btn_close) {
            dismiss();
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        active = 2;
        getComment();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 0;
        active = 1;
        getComment();
    }

    /**
     * 第一行插入一条评论
     */
    private void insertComment(ApiUsersVideoComments comments) {
        comments.addtimeStr = "刚刚";
        adpater.insertData(0, comments);
        refreshView.smoothScrollToPosition(0);

        mCommentNum++;
        tvCommentNum.setText(mCommentNum + "评论");

        // 点赞 评论了 广播通知刷新外层列表
        VideoZanEvent event = new VideoZanEvent();
        event.setIsZanComment(2);
        event.setPosition(position);
        if (!TextUtils.isEmpty(location)) {
            event.setLocation(location);
        }
        event.setCommentNumber(mCommentNum);
        EventBus.getDefault().post(event);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        if (onCommentNumChangeListener != null && mCommentNum != 0) {
            onCommentNumChangeListener.onChange(mCommentNum);
        }
        super.onDismiss(dialog);
    }

    public void setOnCommentNumChangeListener(OnCommentNumChangeListener onCommentNumChangeListener) {
        this.onCommentNumChangeListener = onCommentNumChangeListener;
    }

    public interface OnCommentNumChangeListener {
        void onChange(int commentNum);
    }
}
