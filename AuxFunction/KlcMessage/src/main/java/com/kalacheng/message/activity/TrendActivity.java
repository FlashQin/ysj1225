package com.kalacheng.message.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.commonview.dialog.ShareDialog;
import com.kalacheng.dynamiccircle.adpater.HomePageTrendAdapter;
import com.kalacheng.dynamiccircle.dialog.TrendCommentFragmentDialog;
import com.kalacheng.dynamiccircle.utlis.InputPopwindow;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiAppVideo;
import com.kalacheng.libuser.model.ApiUserVideo;
import com.kalacheng.libuser.model.ApiUsersVideoComments;
import com.kalacheng.message.R;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

// 动态 详情
@Route(path = ARouterPath.TrendDetails)
public class TrendActivity extends BaseActivity {

    @Autowired(name = "type")
    int type;   // 来源 -1:查看详情 1:消息评论 2:消息点赞
    @Autowired(name = "commentId")
    long commentId;   // 评论id(通知列表的commentId)没有则传-1
    @Autowired(name = "videoId")
    long videoId;   // 视频ID
    @Autowired(name = "userName")
    String userName;   // 用户昵称

    private RecyclerView recyclerView;
    private HomePageTrendAdapter adpater;
    private LinearLayoutManager manager;
    private List list;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarWhite(false);
        setContentView(R.layout.activity_trend);
        list = new ArrayList<ApiUserVideo>();
        findViewById(R.id.backIv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);

        manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        adpater = new HomePageTrendAdapter(false);
        recyclerView.setAdapter(adpater);
        adpater.setDynamicListItemCallBack(new HomePageTrendAdapter.DynamicListItemCallBack() {
            @Override
            public void onShare(ApiUserVideo videoBean, int locationY) {
                ShareDialog shareDialog = new ShareDialog();
                shareDialog.setShareDialogListener(new ShareDialog.ShareDialogListener() {
                    @Override
                    public void onItemClick(long id) {
                        if (id == 1) {
//                            MobShareUtil.getInstance(). shareLive(1,1, MobConst.Type.QQ);
                        } else if (id == 2) {
//                            MobShareUtil.getInstance(). shareLive(1,1,MobConst.Type.QZONE);
                        } else if (id == 3) {
//                            MobShareUtil.getInstance().shareLive(1,1,MobConst.Type.WX);
                        } else if (id == 4) {
//                            MobShareUtil.getInstance().shareLive(1,1,MobConst.Type.WX_PYQ);
                        }
                    }
                });
                shareDialog.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "ShareDialog");
            }

            @Override
            public void onComment(ApiUserVideo bean, int positon) {
                toComment(bean, positon);
            }


            @Override
            public void onReply(ApiUserVideo bean, ApiUsersVideoComments comments, int position) {
                toReply(bean, comments, position);
            }

            @Override
            public void onLike(int poistion) {
                setLike(poistion);
            }

            @Override
            public void onLookMoreComment(final ApiUserVideo bean) {
                TrendCommentFragmentDialog commentfragment = new TrendCommentFragmentDialog();
                Bundle Trendbundle = new Bundle();
                Trendbundle.putParcelable(ARouterValueNameConfig.ACTIVITYBEAN, bean);
                commentfragment.setArguments(Trendbundle);
                commentfragment.setOnCommentNumChangeListener(new TrendCommentFragmentDialog.OnCommentNumChangeListener() {
                    @Override
                    public void onChange(int commentNum) {
                        bean.comments = commentNum;
                        adpater.notifyDataSetChanged();
                    }
                });
                commentfragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "TrendCommentFragmentDialog");
            }
        });

        getData();
    }

    private void getData() {
        commentId = getIntent().getLongExtra("commentId", -1L);
        type = getIntent().getIntExtra("type", -1);
        videoId = getIntent().getLongExtra("videoId", -1L);
        userName = getIntent().getStringExtra("userName");
        HttpApiAppVideo.getUserVideoInfo((int) commentId, type, videoId, new HttpApiCallBack<ApiUserVideo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserVideo retModel) {
                if (code == 1 && retModel != null) {
                    list.add(retModel);
                    adpater.RefreshData(list);
                    if (list.size() > 0 && manager != null) {
                        manager.scrollToPositionWithOffset(list.size() - 1, -DpUtil.dp2px(20));
                    }
                }
            }
        });
    }

    //喜欢操作
    public void setLike(final int postion) {
        HttpApiAppVideo.videoZan(adpater.getData().get(postion).id, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    if (adpater.getData().get(postion).isLike == 1) {
                        adpater.getData().get(postion).isLike = 0;
                        adpater.getData().get(postion).likes = (adpater.getData().get(postion).likes - 1);
                    } else {
                        adpater.getData().get(postion).isLike = 1;
                        adpater.getData().get(postion).likes = (adpater.getData().get(postion).likes + 1);

                    }

                    adpater.notifyDataSetChanged();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    //评论
    public void toComment(ApiUserVideo bean, final int postion) {
        InputPopwindow popwindow = new InputPopwindow(mContext);
        popwindow.showShadow(false, recyclerView, 1, bean.id, false, "");
        popwindow.setInputSuccessCallBack(new InputPopwindow.InputSuccessCallBack() {
            @Override
            public void Success(ApiUsersVideoComments comments) {
                adpater.getData().get(postion).commentList.add(0, comments);
                adpater.notifyDataSetChanged();
//                adpater.RefreshComent();
            }
        });
    }

    //回复
    public void toReply(ApiUserVideo bean, ApiUsersVideoComments comments, final int postion) {
        InputPopwindow popwindow = new InputPopwindow(mContext);
        popwindow.showShadow(false, recyclerView, 2, comments.commentid, false, comments.userName);
        popwindow.setInputSuccessCallBack(new InputPopwindow.InputSuccessCallBack() {
            @Override
            public void Success(ApiUsersVideoComments comments) {
                adpater.getData().get(postion).commentList.add(0, comments);
                adpater.notifyDataSetChanged();
//                adpater.RefreshComent();
            }
        });
    }

    /**
     * 回复
     */
    public void onReply(int type, long id, String name) {
        InputPopwindow popwindow = new InputPopwindow(mContext);
        popwindow.showShadow(false, recyclerView, type, id, false, false, name);
        popwindow.setInputSuccessCallBack(new InputPopwindow.InputSuccessCallBack() {
            @Override
            public void Success(ApiUsersVideoComments comments1) {
                adpater.getData().get(0).commentList.add(0, comments1);
                adpater.notifyDataSetChanged();
            }
        });
    }

    boolean isShow = false;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isShow) {
            if (type == 1) {
                onReply(2, commentId, userName);
            } else {
                onReply(1, videoId, userName);
            }
            isShow = true;
        }
    }
}
