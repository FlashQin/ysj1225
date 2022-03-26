package com.kalacheng.message.fragment;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.commonview.jguangIm.ImUnReadCountEvent;
import com.kalacheng.commonview.jguangIm.UnReadCountEvent;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiChatRoom;
import com.kalacheng.libuser.model.ApiNoRead;
import com.kalacheng.message.R;
import com.kalacheng.message.event.ReadMsgEvent;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MsgFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private View commentView;
    private View noticeView;
    private View msgView;
    private ImageView btn_clean;
    private RelativeLayout rlComment, rlSystem, rlAuthority;

    private int unReviewCount = 0;  // 评论通知
    private int unNotifyCount = 0;  // 系统消息
    private int unOfficialCount = 0; // 官方消息
    private int unMessageCount = 0; // 聊天消息数量
    private int unShortVideoNoRead = 0; // 短视频未读数

    public MsgFragment() {
    }

    public MsgFragment(Context mContext, ViewGroup mParentView) {
        this.mContext = mContext;

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        commentView = mParentView.findViewById(R.id.commentView);
        noticeView = mParentView.findViewById(R.id.noticeView);
        msgView = mParentView.findViewById(R.id.msgView);
        btn_clean = mParentView.findViewById(R.id.btn_clean);

        rlComment = mParentView.findViewById(R.id.rlComment);
        rlSystem = mParentView.findViewById(R.id.rlSystem);
        rlAuthority = mParentView.findViewById(R.id.rlAuthority);

        rlComment.setOnClickListener(this);
        rlSystem.setOnClickListener(this);
        rlAuthority.setOnClickListener(this);
        btn_clean.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        HttpApiChatRoom.getAppSystemNoRead(new HttpApiCallBack<ApiNoRead>() {
            @Override
            public void onHttpRet(int code, String msg, ApiNoRead retModel) {
                if (code == 1 && retModel != null) {
                    if (retModel.videoNoRead + retModel.shortVideoNoRead < 1) {
                        commentView.setVisibility(View.GONE);
                    } else {
                        commentView.setVisibility(View.VISIBLE);
                    }
                    if (retModel.systemNoRead < 1) {
                        noticeView.setVisibility(View.GONE);
                    } else {
                        noticeView.setVisibility(View.VISIBLE);
                    }
                    if (retModel.officialNewsNoRead < 1) {
                        msgView.setVisibility(View.GONE);
                    } else {
                        msgView.setVisibility(View.VISIBLE);
                    }

                    unReviewCount = retModel.videoNoRead;
                    unNotifyCount = retModel.systemNoRead;
                    unOfficialCount = retModel.officialNewsNoRead;
                    unShortVideoNoRead = retModel.shortVideoNoRead;
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUnReadCountEvent(UnReadCountEvent e) {
        if (e != null) {
            unReviewCount = e.getVideoNoRead();
            unShortVideoNoRead = e.getShortVideoNoRead();
            if (unReviewCount > 0 || unShortVideoNoRead > 0)
                commentView.setVisibility(View.VISIBLE);
            else {
                commentView.setVisibility(View.GONE);
            }

            unNotifyCount = e.getSystemNoRead();
            if (unNotifyCount < 1)
                noticeView.setVisibility(View.GONE);
            else {
                noticeView.setVisibility(View.VISIBLE);
            }

            unOfficialCount = e.getOfficialNewsNoRead();
            if (unOfficialCount < 1) {
                msgView.setVisibility(View.GONE);
            } else {
                msgView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onImUnReadCountEvent(ImUnReadCountEvent event) {
        if (event != null) {
            unMessageCount = Integer.parseInt(event.getUnReadCount());
        }
    }

    private Dialog dialog;

    private void unReanCountAll() {
        int allCount = unOfficialCount + unNotifyCount + unReviewCount + unMessageCount + unShortVideoNoRead;
        if (allCount > 0) {
            DialogUtil.show2BtnDialog(mContext, com.kalacheng.message.R.style.dialog, R.layout.dialog_one_click_readl, true, true, new DialogUtil.SimpleCallback() {
                @Override
                public void onConfirmClick() {
                    dialog = DialogUtil.loadingDialog(mContext);
                    dialog.show();
                    HttpApiChatRoom.clearNoticeMsg(-1, 0, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            if (code == 1) {
                                EventBus.getDefault().post(new UnReadCountEvent(0, 0, 0, 0, 0));
                                EventBus.getDefault().post(new ReadMsgEvent());
                            } else {
                                ToastUtil.show(msg);
                            }
                            if (dialog != null) {
                                dialog.dismiss();
                                dialog = null;
                            }
                        }
                    });
                }

                @Override
                public void onConfirmClick(String input) {

                }

                @Override
                public void onCancelClick() {

                }
            });
        } else {
            ToastUtil.show("无未读的消息");
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.rlComment) {
            ARouter.getInstance().build(ARouterPath.ReviewsListActivity).navigation();
        } else if (id == R.id.rlSystem) {
            ARouter.getInstance().build(ARouterPath.NotifyListActivity).navigation();
        } else if (id == R.id.rlAuthority) {
            ARouter.getInstance().build(ARouterPath.OfficialNewsActivity).navigation();
        } else if (id == R.id.btn_clean) {
            unReanCountAll();
        }
    }
}
