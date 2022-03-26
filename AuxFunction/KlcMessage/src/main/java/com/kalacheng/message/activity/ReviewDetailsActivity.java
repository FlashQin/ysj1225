package com.kalacheng.message.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.commonview.dialog.ShareDialog;
import com.kalacheng.commonview.listener.OnTrendCommentItemClickListener;
import com.kalacheng.dynamiccircle.adpater.CommentAdpater;
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
import com.kalacheng.message.databinding.ActivityReviewDetailsBinding;
import com.kalacheng.message.viewmodel.ReviewDetailsViewModel;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DateUtil;
import com.kalacheng.util.utils.TextViewUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class ReviewDetailsActivity extends BaseMVVMActivity<ActivityReviewDetailsBinding, ReviewDetailsViewModel> {

    private long commentId;
    private int type;
    private long videoId;
    private String userName;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_review_details;
    }

    @Override
    public void initData() {
        setStatusBarWhite(false);
        binding.backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                    setBean(retModel);
                }
            }
        });
    }

    private void setBean(final ApiUserVideo retModel) {
        binding.setBean(retModel);
        binding.executePendingBindings();

        ImageLoader.display(retModel.avatar, binding.ivAvatar, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        binding.ivSex.setImageResource(retModel.sex == 2 ? R.mipmap.icon_girl : R.mipmap.icon_boy);
        ImageLoader.display(retModel.gradeImg, binding.ivGrade);
        ImageLoader.display(retModel.wealthGradeImg, binding.ivWealthGrade);
        if (TextUtils.isEmpty(retModel.nobleGradeImg)) {
            binding.ivNobleGrade.setVisibility(View.GONE);
        } else {
            binding.ivNobleGrade.setVisibility(View.VISIBLE);
            ImageLoader.display(retModel.nobleGradeImg, binding.ivNobleGrade);
        }
        DateUtil addTime = new DateUtil(retModel.addtimeStr, "yyyy-MM-dd HH:mm:ss");
        binding.tvAddTime.setText(addTime.getDateToFormat("yyyy.MM.dd"));
        if (!TextUtils.isEmpty(retModel.title)) {
            binding.tvTitle.setVisibility(View.VISIBLE);
        } else {
            binding.tvTitle.setVisibility(View.GONE);
        }
        if (retModel.isLike == 1) {
            TextViewUtil.setDrawableLeft(binding.tvLikes, R.mipmap.icon_likes);
        } else {
            TextViewUtil.setDrawableLeft(binding.tvLikes, R.mipmap.icon_likes_none);
        }
        if (retModel.images != null && !TextUtils.isEmpty(retModel.images)) {
            String images = retModel.images;
            String[] imagesStr = images.split(",");
            initBanner(retModel, imagesStr, binding.convenientBanner);
        }
        if (!TextUtils.isEmpty(retModel.videoTime)) {
            long minute = Long.parseLong(retModel.videoTime) / (1000 * 60);
            long second = (Long.parseLong(retModel.videoTime) - (1000 * 60 * minute)) / 1000;
            String str = "";
            if (minute >= 10) {
                str += minute;
            } else {
                str += "0" + minute;
            }
            str += ":";
            if (second >= 10) {
                str += second;
            } else {
                str += "0" + second;
            }
            binding.tvVideoTime.setText(str);
        } else {
            binding.tvVideoTime.setText("00:00");
        }

        //观看动态视频
        if (retModel.type == 1) {
            binding.layoutVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<ApiUserVideo> list = new ArrayList<>();
                    list.add(retModel);
                    ARouter.getInstance().build(ARouterPath.LookVideo)
                            .withInt(ARouterValueNameConfig.VIDEO_TYPE, -1)
                            .withInt(ARouterValueNameConfig.POSITION, 0)
                            .withParcelableArrayList(ARouterValueNameConfig.Beans, (ArrayList<? extends Parcelable>) list).navigation();
                }
            });
        }
        //查看个人信息
        binding.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, retModel.uid).navigation();
            }
        });
        //喜欢
        binding.tvLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLike();
            }
        });
        //评论
        binding.tvCommentMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toComment(retModel);
            }
        });
        //分享
        binding.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
        //去评论
        binding.tvComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrendCommentFragmentDialog commentfragment = new TrendCommentFragmentDialog();
                Bundle Trendbundle = new Bundle();
                Trendbundle.putParcelable(ARouterValueNameConfig.ACTIVITYBEAN, binding.getBean());
                commentfragment.setArguments(Trendbundle);
                commentfragment.setOnCommentNumChangeListener(new TrendCommentFragmentDialog.OnCommentNumChangeListener() {
                    @Override
                    public void onChange(int commentNum) {
                        binding.getBean().comments = commentNum;
                    }
                });
                commentfragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "TrendCommentFragmentDialog");

            }
        });

        //评论
        if (retModel.commentList.size() > 0) {
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            ((DefaultItemAnimator) binding.rvComments.getItemAnimator()).setSupportsChangeAnimations(false);
            ((SimpleItemAnimator) binding.rvComments.getItemAnimator()).setSupportsChangeAnimations(false);
            binding.rvComments.getItemAnimator().setChangeDuration(0);
            binding.rvComments.setHasFixedSize(true);
            binding.rvComments.setLayoutManager(manager);
            CommentAdpater adapter = new CommentAdpater(mContext);
            binding.rvComments.setAdapter(adapter);
            adapter.load(retModel.commentList);

            adapter.setOnTrendCommentItemClickListener(new OnTrendCommentItemClickListener() {
                @Override
                public void onItemClick(ApiUsersVideoComments commentBean) {
                    onReply(commentBean);

                }

                @Override
                public void onUserName(ApiUsersVideoComments commentBean) {

                }

                @Override
                public void onToUserName(ApiUsersVideoComments commentBean) {

                }
            });
        }
    }


    /**
     * 喜欢操作
     */
    public void setLike() {
        HttpApiAppVideo.videoZan(binding.getBean().id, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    if (binding.getBean().isLike == 1) {
                        binding.getBean().isLike = 0;
                        TextViewUtil.setDrawableLeft(binding.tvLikes, R.mipmap.icon_likes_none);
                    } else {
                        binding.getBean().isLike = 1;
                        TextViewUtil.setDrawableLeft(binding.tvLikes, R.mipmap.icon_likes);
                    }
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * 评论
     */
    public void toComment(ApiUserVideo bean) {
        InputPopwindow popwindow = new InputPopwindow(mContext);
        popwindow.showShadow(false, binding.root, 1, bean.id, false, "");
        popwindow.setInputSuccessCallBack(new InputPopwindow.InputSuccessCallBack() {
            @Override
            public void Success(ApiUsersVideoComments comments) {
                binding.getBean().commentList.add(0, comments);
            }
        });
    }

    /**
     * 回复
     */
    public void onReply(ApiUsersVideoComments comments) {
        InputPopwindow popwindow = new InputPopwindow(mContext);
        popwindow.showShadow(false, binding.root, 2, comments.commentid, false, comments.userName);
        popwindow.setInputSuccessCallBack(new InputPopwindow.InputSuccessCallBack() {
            @Override
            public void Success(ApiUsersVideoComments comments1) {
                binding.getBean().commentList.add(0, comments1);
            }
        });
    }

    /**
     * 回复
     */
    public void onReply(int type, long id, String name) {
        InputPopwindow popwindow = new InputPopwindow(mContext);
        popwindow.showShadow(false, binding.root, type, id, true, false, name);
        popwindow.setInputSuccessCallBack(new InputPopwindow.InputSuccessCallBack() {
            @Override
            public void Success(ApiUsersVideoComments comments1) {
                binding.getBean().commentList.add(0, comments1);
            }
        });
    }

    boolean isShow = false;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isShow) {
            binding.nestedScrollView.fullScroll(NestedScrollView.FOCUS_DOWN);
            onReply(type == 1 ? 2 : 1, commentId, userName);
            isShow = true;
        }
    }

    private void initBanner(final ApiUserVideo apiUserVideo, String[] imagesStr, ConvenientBanner convenientBanner) {
        final List<String> stringList = new ArrayList<>();
        for (int i = 0; i < imagesStr.length; i++) {
            stringList.add(imagesStr[i]);
        }
        if (stringList.size() > 1) {
            convenientBanner.setCanLoop(true);
            convenientBanner.setPointViewVisible(true);
        } else {
            convenientBanner.setCanLoop(false);
            convenientBanner.setPointViewVisible(false);
        }
        convenientBanner.setPages(new CBViewHolderCreator<HomePageTrendAdapter.NetworkImageHolderView>() {
            @Override
            public HomePageTrendAdapter.NetworkImageHolderView createHolder() {
                return new HomePageTrendAdapter.NetworkImageHolderView();
            }
        }, stringList);
        convenientBanner.setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                List<ApiUserVideo> list = new ArrayList<>();
                list.add(apiUserVideo);
                ARouter.getInstance().build(ARouterPath.LookVideo)
                        .withInt(ARouterValueNameConfig.VIDEO_TYPE, -1)
                        .withInt(ARouterValueNameConfig.POSITION, 0)
                        .withParcelableArrayList(ARouterValueNameConfig.Beans, (ArrayList<? extends Parcelable>) list).navigation();
            }
        });
        convenientBanner.setPageIndicator(new int[]{com.kalacheng.dynamiccircle.R.drawable.banner_indicator_grey, com.kalacheng.dynamiccircle.R.drawable.banner_indicator_white});
        convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
//        convenientBanner.startTurning(3000);
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

}
