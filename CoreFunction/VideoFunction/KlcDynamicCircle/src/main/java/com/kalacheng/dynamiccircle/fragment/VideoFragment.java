package com.kalacheng.dynamiccircle.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.kalacheng.busfinance.httpApi.HttpApiSupport;
import com.kalacheng.commonview.fragment.ImageFragment;
import com.kalacheng.dynamiccircle.R;
import com.kalacheng.dynamiccircle.adpater.VideoAdapter;
import com.kalacheng.dynamiccircle.dialog.TrendCommentFragmentDialog;
import com.kalacheng.dynamiccircle.listener.FinishCallBack;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiAppVideo;
import com.kalacheng.libuser.model.ApiUserVideo;
import com.kalacheng.libuser.model.InviteDto;
import com.kalacheng.mob.MobConst;
import com.kalacheng.mob.MobShareUtil;
import com.kalacheng.commonview.bean.ShareDialogBean;
import com.kalacheng.commonview.dialog.ShareDialog;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.PagerLayoutManager;
import com.kalacheng.base.adapter.FragmentAdapter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackPageArr;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.base.http.PageInfo;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends BaseFragment {
    private int videoType;// -1 列表；0 关注、推荐、全部、某人动态
    /**
     * 初始 传入的索引
     */
    private int mInitPosition;
    private List<ApiUserVideo> appShortVideos = new ArrayList<>();
    private int page;
    private int communityType;
    private int communityHotId;
    private long communityUid;

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView recyclerView;
    private VideoAdapter mAdapter;
    private TXVodPlayer mVodPlayer;
    private TXVodPlayConfig config;

    private int mSelectPosition = -1;
    private PagerLayoutManager mLayoutManager;
    private boolean mClickPaused;//点击暂停

    private int itemPosition;
    private String location;

    public VideoFragment() {
    }

    public VideoFragment(int videoType, int itemPosition, String location, int position, ArrayList<ApiUserVideo> appShortVideos, int page, int communityType, int communityHotId, long communityUid) {
        this.videoType = videoType;
        this.itemPosition = itemPosition;
        this.location = location;
        this.mInitPosition = position;
        this.appShortVideos = appShortVideos;
        this.page = page;
        this.communityType = communityType;
        this.communityHotId = communityHotId;
        this.communityUid = communityUid;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView() {
        mRefreshLayout = mParentView.findViewById(R.id.refreshLayout);
        mRefreshLayout.setProgressViewOffset(false, 0, DpUtil.dp2px(70));//圆圈位置向下偏移
        mRefreshLayout.setColorSchemeResources(R.color.swipe_refresh);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                mClickPaused = false;
                getVideoData();
            }
        });
        recyclerView = mParentView.findViewById(R.id.recyclerView);
    }


    private FinishCallBack finishCallBack;

    public void setFinishCallBack(FinishCallBack finishCallBack) {
        this.finishCallBack = finishCallBack;
    }

    /*-------- 分享 复制链接 S ------------------------------------------------------------*/
    InviteDto inviteDto;
    /**
     * 获取邀请码信息
     */
    private void getInviteCodeInfo() {
        HttpApiSupport.getInviteCodeInfo(new HttpApiCallBack<InviteDto>() {
            @Override
            public void onHttpRet(int code, String msg, InviteDto retModel) {
                if (code == 1 && null != retModel) {
                    inviteDto = retModel;
                }
            }
        });
    }
    /*-------- 分享 复制链接 E ------------------------------------------------------------*/

    /**
     * 分享 其他按钮 参数配置用
     */
    private Bundle bundle = new Bundle();

    @Override
    protected void initData() {

        mLayoutManager = new PagerLayoutManager(getContext(), OrientationHelper.VERTICAL);
        mAdapter = new VideoAdapter(getActivity());
        mAdapter.setItemPosition(itemPosition, location);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnVideoCallBack(new VideoAdapter.OnVideoCallBack() {
            @Override
            public void onComment(final ApiUserVideo apiUserVideo) {
                TrendCommentFragmentDialog commentFragment = new TrendCommentFragmentDialog();
                Bundle trendBundle = new Bundle();
                trendBundle.putParcelable(ARouterValueNameConfig.ACTIVITYBEAN, apiUserVideo);
                commentFragment.setArguments(trendBundle);
                commentFragment.setOnCommentNumChangeListener(new TrendCommentFragmentDialog.OnCommentNumChangeListener() {
                    @Override
                    public void onChange(int commentNum) {
                        apiUserVideo.comments = commentNum;
                        tvCommentNum.setText(commentNum + "");
                    }
                });
                commentFragment.show(getChildFragmentManager(), "TrendCommentFragmentDialog");
                commentFragment.setPosition(itemPosition, location);
            }

            @Override
            public void onShare(final ApiUserVideo apiUserVideo, final int position) {
                ShareDialog shareDialog = new ShareDialog();

                /*-------- 分享 其他按钮 S ------------------------------------------------------------*/
                ArrayList<ShareDialogBean> otherBeans = new ArrayList<>();

                ShareDialogBean beanCopy = new ShareDialogBean();
                beanCopy.id = 1002;
                beanCopy.src = R.mipmap.icon_invitation_url_copy;
                beanCopy.text = "复制链接";
                otherBeans.add(beanCopy);


                //判断 当前 查看动态 是否为自己的动态 （通过uid判断）
                if (apiUserVideo.uid == HttpClient.getUid()) {
                    ShareDialogBean beanPicture = new ShareDialogBean();
                    beanPicture.id = 1001;
                    beanPicture.src = R.mipmap.icon_share_delete;
                    beanPicture.text = "删除";
                    otherBeans.add(beanPicture);
                }

                bundle.putParcelableArrayList(ShareDialog.ShareDialogOtherBeans, otherBeans);
                shareDialog.setArguments(bundle);
                /*-------- 分享 其他按钮 E ------------------------------------------------------------*/

                shareDialog.setShareDialogListener(new ShareDialog.ShareDialogListener() {
                    @Override
                    public void onItemClick(long id) {
                        if (id == 1) {
                            MobShareUtil.getInstance().shareApp(MobConst.Type.WX);
                        } else if (id == 2) {
                            MobShareUtil.getInstance().shareApp(MobConst.Type.WX_PYQ);
                        } else if (id == 3) {
                            MobShareUtil.getInstance().shareApp(MobConst.Type.QQ);
                        } else if (id == 4) {
                            MobShareUtil.getInstance().shareApp(MobConst.Type.QZONE);
                        } else if(id == 1002) {//复制链接
                            if(inviteDto == null) {
                                return;
                            }

                            WordUtil.copyLink(inviteDto.inviteUrl);
                        } else if (id == 1001) { //删除

                            HttpApiAppVideo.videoDel(apiUserVideo.id, new HttpApiCallBack<HttpNone>() {
                                @Override
                                public void onHttpRet(int code, String msg, HttpNone retModel) {
                                    if (!TextUtils.isEmpty(msg)) {
                                        ToastUtil.show(msg);
                                    }

                                    if (code == 1) {
                                        //先暂停 正在 播放的视频，再进行其他操作
                                        releaseVideo();

                                        List<ApiUserVideo> mList = new ArrayList<>();
                                        L.e("删除==   先获取列表  mList  " + mAdapter.getList().size());
                                        mAdapter.getList().remove(position);
                                        L.e("删除==   移除  mList  i  " + position + "  " + mList.size());

                                        for (int i = 0; i < mAdapter.getList().size(); i++) {
                                            mList.add(i, mAdapter.getList().get(i));
                                        }


                                        mInitPosition = position;
                                        mSelectPosition = -1;

                                        mAdapter.setData(mList);
                                        mAdapter.notifyDataSetChanged();

                                        //删除 执行父页面回调
                                        if (null != finishCallBack) {
                                            finishCallBack.deleteList(apiUserVideo, position);
                                        }

                                        if (mAdapter.getItemCount() == 0) {
                                            if (null != finishCallBack) {
                                                finishCallBack.isFinish();
                                            }
                                        } else {

                                        }
                                    }
                                }
                            });
                        }
                    }
                });
                shareDialog.show(getChildFragmentManager(), "ShareDialog");
            }
        });

        config = new TXVodPlayConfig();
        config.setCacheFolderPath(getContext().getCacheDir().getAbsolutePath());
        config.setMaxCacheItems(15);
        mLayoutManager.setOnViewPagerListener(new PagerLayoutManager.OnViewPagerListener() {
            @Override
            public void onInitComplete(View view) {
                if (mSelectPosition == -1) {
                    if (mAdapter.getItemCount() == 0) {
                        return;
                    }

                    if (mAdapter.getItemCount() == mInitPosition) {
                        //制定标识mInitPosition 等于 列表数据数（删除最后一条数据）
                        mSelectPosition = mAdapter.getItemCount() - 1;
                    } else {
                        mSelectPosition = mInitPosition;
                    }

                    initPlay(mSelectPosition, view);
                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom, View view) {
                mClickPaused = false;
                if (mAdapter != null && position < mAdapter.getItemCount()) {
                    if (mSelectPosition != position) {
                        releaseVideo();

                        mSelectPosition = position;
                        initPlay(position, view);
                        if (position == mAdapter.getItemCount() - 2 && videoType != -1) {
                            ++page;
                            getVideoData();
                        }
                    }
                }
            }

            @Override
            public void onPageRelease(boolean isNext, int position, View view) {

            }
        });
    }

    @Override
    public void refreshData() {
        super.refreshData();
        if (!mFirstLoadData) {
            if (mRefreshLayout != null && recyclerView != null) {
                if (mVodPlayer != null) {
                    mVodPlayer.stopPlay(false);
                }
                recyclerView.scrollToPosition(0);
                page = 0;
                mClickPaused = false;
                mRefreshLayout.setRefreshing(true);
                getVideoData();
            }
        }
    }

    @Override
    public void loadData() {
        super.loadData();
        if (isFirstLoadData()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (videoType == -1) {
                        if (mRefreshLayout != null) {
                            mRefreshLayout.setEnabled(false);
                        }
                    } else {
                        if (mRefreshLayout != null) {
                            mRefreshLayout.setEnabled(true);
                        }
                    }

                    mClickPaused = false;
//                    if (videoType == 0) {
//
//                        getVideoData();
//                    } else {
                    if (null != appShortVideos) {
                        mAdapter.setData(appShortVideos);
                        recyclerView.scrollToPosition(mInitPosition);
                    }
//                    }
                }
            }, 300);
        }
    }

    /**
     * 播放视频
     */
    private void initPlay(final int position, View view) {
        final ApiUserVideo apiShortVideoDto = mAdapter.getItem(position);
        tvCommentNum = view.findViewById(R.id.comment_num);
        tvCommentNum.setText(apiShortVideoDto.comments + "");
        if (apiShortVideoDto.isAtt == 1 || apiShortVideoDto.uid == HttpClient.getUid()) {
            view.findViewById(R.id.tvFollow).setVisibility(View.INVISIBLE);
        } else {
            view.findViewById(R.id.tvFollow).setVisibility(View.VISIBLE);
        }
        if (apiShortVideoDto.type == 1) {//视频
            view.findViewById(R.id.video_view).setVisibility(View.VISIBLE);
            view.findViewById(R.id.cover).setVisibility(View.VISIBLE);
            view.findViewById(R.id.btn_play).setVisibility(View.GONE);
            view.findViewById(R.id.viewpager).setVisibility(View.GONE);
            view.findViewById(R.id.tvImage).setVisibility(View.GONE);
            playVideo(apiShortVideoDto, view);
        } else {
            view.findViewById(R.id.video_view).setVisibility(View.GONE);
            view.findViewById(R.id.cover).setVisibility(View.GONE);
            view.findViewById(R.id.btn_play).setVisibility(View.GONE);
            view.findViewById(R.id.viewpager).setVisibility(View.VISIBLE);
            view.findViewById(R.id.tvImage).setVisibility(View.VISIBLE);
            showPic(mAdapter.getItem(position), view);
        }
    }

    private TXCloudVideoView videoView;
    private ImageView btnPlay;
    private ImageView cover;
    private TextView tvCommentNum;

    private void playVideo(ApiUserVideo apiShortVideoDto, View view) {
        String url = apiShortVideoDto.href;
        if (TextUtils.isEmpty(url)) {
            return;
        }
        videoView = (TXCloudVideoView) view.findViewById(R.id.video_view);
        btnPlay = view.findViewById(R.id.btn_play);
        cover = view.findViewById(R.id.cover);
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mVodPlayer != null) {
                    if (mVodPlayer.isPlaying()) {
                        mClickPaused = true;
                        mVodPlayer.pause();
                        if (btnPlay != null) {
                            btnPlay.setVisibility(View.VISIBLE);
                        }
                    } else {
                        mClickPaused = false;
                        mVodPlayer.resume();
                        if (btnPlay != null) {
                            btnPlay.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });
        if (mVodPlayer == null) {
            mVodPlayer = new TXVodPlayer(getContext());
            mVodPlayer.setConfig(config);
            mVodPlayer.setAutoPlay(true);
            mVodPlayer.setLoop(true);
            mVodPlayer.setVodListener(new ITXVodPlayListener() {
                @Override
                public void onPlayEvent(TXVodPlayer txVodPlayer, int e, Bundle bundle) {
                    switch (e) {
                        case TXLiveConstants.PLAY_EVT_PLAY_BEGIN://加载完成，开始播放的回调

                            break;
                        case TXLiveConstants.PLAY_EVT_PLAY_LOADING: //开始加载的回调

                            break;
                        case TXLiveConstants.PLAY_EVT_PLAY_END://获取到视频播放完毕的回调

                            break;
                        case TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME://获取到视频首帧回调
                            cover.setVisibility(View.GONE);
                            if (!mShowed && mVodPlayer != null) {//进入首帧时，如果此时已经离开视频界面，则暂停
                                mVodPlayer.pause();
                            }
                            break;
                        case TXLiveConstants.PLAY_EVT_CHANGE_RESOLUTION://获取到视频宽高回调
                            if (ConfigUtil.getBoolValue(R.bool.videoPlayCut)) {
                                int videoWidth = bundle.getInt(TXLiveConstants.EVT_PARAM1, 0);
                                int videoHeight = bundle.getInt(TXLiveConstants.EVT_PARAM2, 0);
                                if (videoWidth >= videoHeight)
                                    mVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
                                else
                                    mVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
                            } else {
                                mVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
                            }
                            break;
                    }
                }

                @Override
                public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

                }
            });
        }
        //关键 player 对象与界面 view
        mVodPlayer.setPlayerView(videoView);
        mVodPlayer.startPlay(url);
    }

    private TextView tvImage;
    private ViewPager viewpager;

    private void showPic(ApiUserVideo apiShortVideoDto, View view) {
        viewpager = view.findViewById(R.id.viewpager);
        viewpager.setVisibility(View.VISIBLE);
        tvImage = view.findViewById(R.id.tvImage);
        tvImage.setVisibility(View.VISIBLE);

        String images = apiShortVideoDto.images;
        if (!TextUtils.isEmpty(images)) {
            List<Fragment> mFragments = new ArrayList<>();
            String[] strings = images.split(",");
            final int circularNum = strings.length;
            tvImage.setText("1/" + (circularNum));
            for (String str : strings) {
                mFragments.add(new ImageFragment(str));
            }
            FragmentAdapter mAdapter = new FragmentAdapter(getChildFragmentManager(), mFragments);
            viewpager.setAdapter(mAdapter);
            viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageSelected(int arg0) {
                    tvImage.setText((arg0 + 1) + "/" + (circularNum));
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int arg0) {

                }
            });
        }
    }

    /**
     * 停止播放
     */
    private void releaseVideo() {
        if (mVodPlayer != null) {
            mVodPlayer.stopPlay(false);
        }
    }

    private void getVideoData() {
        if (videoType == 0) {
            HttpApiAppVideo.getVideoList(communityHotId, page, HttpConstants.PAGE_SIZE, (int) communityUid, communityType, new HttpApiCallBackPageArr<ApiUserVideo>() {
                @Override
                public void onHttpRet(int code, String msg, PageInfo pageInfo, List<ApiUserVideo> retModel) {
                    if (mRefreshLayout != null) {
                        mRefreshLayout.setRefreshing(false);
                    }
                    if (code == 1 && null != retModel && !retModel.isEmpty()) {
                        if (page == 0) {
                            mInitPosition = 0;
                            mSelectPosition = -1;
                            mAdapter.setData(retModel);
                        } else {
                            mAdapter.loadData(retModel);
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        if (page == 0) {
                            mAdapter.clearData();
                            if (mVodPlayer != null) {
                                mVodPlayer.stopPlay(false);
                                mVodPlayer = null;
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mVodPlayer != null) {
            mVodPlayer.stopPlay(false);
            mVodPlayer = null;
        }
    }

    @Override
    public void onResumeFragment() {
        super.onResumeFragment();
        if (mPaused) {
            if (!mClickPaused && mVodPlayer != null) {
                mVodPlayer.resume();
            }
        }
        mPaused = false;

        getInviteCodeInfo();
    }

    @Override
    public void onPauseFragment() {
        super.onPauseFragment();
        mPaused = true;
        if (!mClickPaused && mVodPlayer != null) {
            mVodPlayer.pause();
        }
    }

    @Override
    public void setShowed(boolean showed) {
        super.setShowed(showed);
        if (showed) {
            Activity activity = getActivity();
            if (activity != null) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
            if (!mClickPaused && mVodPlayer != null) {
                mVodPlayer.resume();
            }
        } else {
            Activity activity = getActivity();
            if (activity != null) {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
            if (!mClickPaused && mVodPlayer != null) {
                mVodPlayer.pause();
            }
        }
    }
}
