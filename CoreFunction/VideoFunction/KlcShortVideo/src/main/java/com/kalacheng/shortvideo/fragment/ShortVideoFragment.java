package com.kalacheng.shortvideo.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.adapter.FragmentAdapter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.busfinance.httpApi.HttpApiSupport;
import com.kalacheng.busshop.httpApi.HttpApiShopGoods;
import com.kalacheng.busshop.model.ShopGoods;
import com.kalacheng.busshortvideo.httpApi.HttpApiAppShortVideo;
import com.kalacheng.commonview.bean.ShareDialogBean;
import com.kalacheng.commonview.component.FloatingScreenDialogComponent;
import com.kalacheng.commonview.component.LiveOutGiftComponent;
import com.kalacheng.commonview.dialog.LiveGiftDialogFragment;
import com.kalacheng.commonview.dialog.NoMoneyTipsDialogFragment;
import com.kalacheng.commonview.dialog.ShareDialog;
import com.kalacheng.commonview.dialog.VideoPayTipsDialogFragment;
import com.kalacheng.commonview.fragment.ImageFragment;
import com.kalacheng.commonview.jguangIm.ImMessageUtil;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackPageArr;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.base.http.PageInfo;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.ApiBaseEntity;
import com.kalacheng.libuser.model.ApiShortVideoDto;
import com.kalacheng.libuser.model.InviteDto;
import com.kalacheng.libuser.model.NobLiveGift;
import com.kalacheng.mob.MobConst;
import com.kalacheng.mob.MobShareUtil;
import com.kalacheng.shortvideo.R;
import com.kalacheng.shortvideo.adapter.MainVideoAdapter;
import com.kalacheng.shortvideo.dialog.VideoCommentFragmentDialog;
import com.kalacheng.shortvideo.event.ShortVideosEvent;
import com.kalacheng.shortvideo.listener.FinishCallBack;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.PagerLayoutManager;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.MaskImageView;
import com.klc.bean.SendGiftPeopleBean;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.wengying666.imsocket.IMUtil;
import com.wengying666.imsocket.SocketClient;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static android.app.Activity.RESULT_OK;
import static com.kalacheng.util.utils.SpUtil.READ_SHORT_VIDEO_NUMBER;

// 短视频
public class ShortVideoFragment extends BaseFragment {

    private static String TAG = ShortVideoFragment.class.getSimpleName();

    private int videoType;//0 推荐；1 关注；2 看点；3 作品；4 单视频；-1 列表；
    private int mInitPosition;
    private List<ApiShortVideoDto> appShortVideos = new ArrayList<>();
    private int page;
    private long classifyId;
    private long sort;
    private long videoWorksUserId;//作品，用户ID
    private int videoWorksType;//作品，类型

    private SocketClient mSocket;
    private FloatingScreenDialogComponent floatingScreenDialog;
    private String groupNameID;

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView recyclerView;
    private MainVideoAdapter mAdapter;
    private TXVodPlayer mVodPlayer;
    private TXVodPlayConfig config;
    private int PlayType = 0;

    private RelativeLayout rlTimer;
    private TextView tvTimer;
    private ImageView ivPlay;
    private TXCloudVideoView videoView;
    private ImageView btnPlay;
    private ImageView cover;
    private SeekBar seekBar;
    private TextView tvImage;
    private ViewPager viewpager;

    private int mSelectPosition = -1;
    private PagerLayoutManager mLayoutManager;
    private boolean mClickPaused;//点击暂停
    private int videoPlayTime = 0; // 进度条设置播放位置
    private int shortVideoTrialTime = 0; // 视频试看时长
    private Disposable timeDisposable;
    private boolean mControlScreenOn = true;//是否需要控件常亮
    private int commentId = -1;
    private int type = -1;
    private int itemPosition;
    private String location;
    private boolean isShowVideoTips;
    private View adapterItenView;

    private FinishCallBack finishCallBack;

    private VideoPayTipsDialogFragment videoPayTipsDialogFragment;
    private NoMoneyTipsDialogFragment noMoneyTipsDialogFragment;

    public void setFinishCallBack(FinishCallBack finishCallBack) {
        this.finishCallBack = finishCallBack;
    }

    public ShortVideoFragment() {
    }

    public ShortVideoFragment(boolean controlScreenOn, int videoType) {
        this.mControlScreenOn = controlScreenOn;
        this.videoType = videoType;
    }

    public ShortVideoFragment(boolean controlScreenOn, int videoType, int itemPosition, String location, int position, ArrayList<ApiShortVideoDto> appShortVideos, int page, long classifyId, long sort, long videoWorksUserId, int videoWorksType, int commentId, int type) {
        this.mControlScreenOn = controlScreenOn;
        this.videoType = videoType;
        this.itemPosition = itemPosition;
        this.location = location;
        this.mInitPosition = position;
        this.appShortVideos = appShortVideos;
        this.page = page;
        this.classifyId = classifyId;
        this.sort = sort;
        this.videoWorksUserId = videoWorksUserId;
        this.videoWorksType = videoWorksType;
        if (commentId != 0) {
            this.commentId = commentId;
        }
        if (type != 0) {
            this.type = type;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_short_video;
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

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty((String) SpUtil.getInstance().getSharedPreference(SpUtil.FIRST_VIDEO_SHOP_TIPS, ""))) {
            isShowVideoTips = false;
        } else {
            isShowVideoTips = true;
        }
        initAdapter();
        // 试看倒计时
        if (null == timeDisposable && null != mAdapter) {
            timeDisposable = Observable.interval(1000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) throws Exception {
                    int position = mSelectPosition == -1 ? 0 : mSelectPosition;
                    if (null != tvTimer && shortVideoTrialTime >= 0 && mAdapter.getItemCount() > 0 && mAdapter.getItem(position).type == 1 && mAdapter.getItem(position).isPrivate == 1 && mAdapter.getItem(position).adsType == 0 && mAdapter.getItem(position).isPay == 0 && !SpUtil.getInstance().isFree(mAdapter.getItem(position).id + "", (int) SpUtil.getInstance().getSharedPreference(READ_SHORT_VIDEO_NUMBER, 0))) {
                        shortVideoTrialTime--;
                        tvTimer.setText((shortVideoTrialTime + 1) + "");
                        rlTimer.setVisibility(View.VISIBLE);
                        if (shortVideoTrialTime < 0) {
                            if (null != ivPlay){
                                ivPlay.setVisibility(View.GONE);
                            }
                            rlTimer.setVisibility(View.GONE);
                            if (null != adapterItenView) {
                                showVipPayDialog(mAdapter.getItem(position), adapterItenView);
                            }
                        }
                    }
                }
            });
        }
    }

    private void initGiftAnimate() {
        mSocket = IMUtil.getClient();
        if (mParentView != null) {
            LiveConstants.ROOMID = -1;
            ViewGroup frameLayout = mParentView.findViewById(R.id.fl_all);
            LiveOutGiftComponent liveOutGiftComponent = new LiveOutGiftComponent(getContext(), frameLayout);
            liveOutGiftComponent.init(getActivity().getLocalClassName(), mSocket);
            groupNameID = getActivity().getLocalClassName();
            floatingScreenDialog = new FloatingScreenDialogComponent(getActivity(), frameLayout);
            floatingScreenDialog.init(getActivity().getLocalClassName(), mSocket);
        }
    }

    public void removeReceiver() {
        IMUtil.removeReceiver(groupNameID);
        if (null != mSocket) {
            LiveBundle.getInstance().removeSocketClient(getActivity().getLocalClassName());
            mSocket = null;
        }
        if (floatingScreenDialog != null) {
            floatingScreenDialog.clean();
            floatingScreenDialog = null;
        }
    }

    /**
     * 播放视频
     */
    private void initPlay(final int position, final View itemView) {
        final ApiShortVideoDto apiShortVideoDto = mAdapter.getItem(position);
        shortVideoTrialTime = apiShortVideoDto.shortVideoTrialTime > 0 ? apiShortVideoDto.shortVideoTrialTime : -1;

        rlTimer = itemView.findViewById(R.id.rlTimer);
        tvTimer = itemView.findViewById(R.id.tvTimer);
        seekBar = itemView.findViewById(R.id.sb);
        ivPlay = itemView.findViewById(R.id.btn_play);

        tvTimer.setText(shortVideoTrialTime + "");
        itemView.findViewById(R.id.btn_pay).setVisibility(View.VISIBLE);
        itemView.findViewById(R.id.btn_play).setVisibility(View.GONE);

        if (apiShortVideoDto.isFollow == 1 || apiShortVideoDto.userId == HttpClient.getUid()) {
            itemView.findViewById(R.id.tvFollow).setVisibility(View.INVISIBLE);
        } else {
            itemView.findViewById(R.id.tvFollow).setVisibility(View.VISIBLE);
        }
        if (shortVideoTrialTime > 0 || apiShortVideoDto.isPrivate == 0 || apiShortVideoDto.isPay == 1 || SpUtil.getInstance().isFree(apiShortVideoDto.id + "", (int) SpUtil.getInstance().getSharedPreference(READ_SHORT_VIDEO_NUMBER, 0))) {
            //  免费/已付费/免费次数观看过
            if (apiShortVideoDto.type == 1) {//视频
                itemView.findViewById(R.id.video_view).setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.cover).setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.viewpager).setVisibility(View.GONE);
                itemView.findViewById(R.id.tv_image).setVisibility(View.GONE);
                playVideo(apiShortVideoDto, itemView);
            } else {
                itemView.findViewById(R.id.video_view).setVisibility(View.GONE);
                itemView.findViewById(R.id.cover).setVisibility(View.GONE);
                itemView.findViewById(R.id.viewpager).setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.tv_image).setVisibility(View.VISIBLE);
                showPic(mAdapter.getItem(position), itemView);
            }

        } else {
            // 需要付费
            if (apiShortVideoDto.type == 1) {//视频
                itemView.findViewById(R.id.video_view).setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.cover).setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.ivImagesShadow).setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.btn_pay).setVisibility(View.VISIBLE);
            } else {
                itemView.findViewById(R.id.video_view).setVisibility(View.GONE);
                itemView.findViewById(R.id.cover).setVisibility(View.GONE);
                itemView.findViewById(R.id.ivImagesShadow).setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.btn_pay).setVisibility(View.VISIBLE);
            }
            itemView.findViewById(R.id.btn_play).setVisibility(View.GONE);
            itemView.findViewById(R.id.viewpager).setVisibility(View.GONE);
            itemView.findViewById(R.id.tv_image).setVisibility(View.GONE);
            itemView.findViewById(R.id.btn_pay).setVisibility(View.VISIBLE);

            showVipPayDialog(apiShortVideoDto, itemView);
            if (shortVideoTrialTime > 0) {
                MaskImageView maskImageView = itemView.findViewById(R.id.ivImagesShadow);
                ImageLoader.displayBlur(apiShortVideoDto.thumb, maskImageView);
                maskImageView.setVisibility(View.VISIBLE);
            }
        }
        itemView.findViewById(R.id.btn_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                showVipPayDialog(apiShortVideoDto, itemView);
            }
        });
    }

    private void playVideo(final ApiShortVideoDto apiShortVideoDto, final View view) {
        String url = apiShortVideoDto.videoUrl;
        if (TextUtils.isEmpty(url)) {
            return;
        }
        view.findViewById(R.id.btn_pay).setVisibility(View.GONE);
        view.findViewById(R.id.ivImagesShadow).setVisibility(View.GONE);
        videoView = (TXCloudVideoView) view.findViewById(R.id.video_view);
        btnPlay = view.findViewById(R.id.btn_play);
        cover = view.findViewById(R.id.cover);
        view.findViewById(R.id.btn_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVipPayDialog(apiShortVideoDto, view);
            }
        });

        if (apiShortVideoDto.adsType != 0) {
            shortVideoTrialTime = 0;
            mAdapter.getItem(mSelectPosition).isPay = 1;
            rlTimer.setVisibility(View.GONE);
        }
        if (shortVideoTrialTime > 0 && apiShortVideoDto.isPay == 0) {
            seekBar.setVisibility(View.GONE);
        } else {
            rlTimer.setVisibility(View.GONE);
            seekBar.setVisibility(View.VISIBLE);
        }
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
                            Log.e(">>>", "加载完成，开始播放的回调");
                            break;
                        case TXLiveConstants.PLAY_EVT_PLAY_LOADING: //开始加载的回调
                            Log.e(">>>", "开始加载的回调");
                            break;
                        case TXLiveConstants.PLAY_EVT_PLAY_END://获取到视频播放完毕的回调
                            Log.e(">>>", "获取到视频播放完毕的回调");
                            break;
                        case TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME://获取到视频首帧回调
                            Log.e(">>>", "获取到视频首帧回调");
                            cover.setVisibility(View.GONE);
                            if (!mShowed && mVodPlayer != null) {//进入首帧时，如果此时已经离开视频界面，则暂停
                                mVodPlayer.pause();
                            }
                            break;
                        case TXLiveConstants.PLAY_EVT_CHANGE_RESOLUTION://获取到视频宽高回调
                            if (ConfigUtil.getBoolValue(com.kalacheng.dynamiccircle.R.bool.videoPlayCut)) {
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
                        case TXLiveConstants.PLAY_EVT_PLAY_PROGRESS:// 加载进度, 单位是秒
                            // 加载进度, 单位是秒
                            int duration = bundle.getInt(TXLiveConstants.EVT_PLAYABLE_DURATION_MS);
                            // 播放进度, 单位是秒
                            int progress = bundle.getInt(TXLiveConstants.EVT_PLAY_PROGRESS);
                            // 视频总长, 单位是秒
                            int duration2 = bundle.getInt(TXLiveConstants.EVT_PLAY_DURATION);
                            // 可以用于设置时长显示等等
                            if (videoPlayTime == 0) {
                                videoPlayTime = bundle.getInt(TXLiveConstants.EVT_PLAY_DURATION); //总时长
                                seekBar.setMax(videoPlayTime);
                            } else {
                                videoPlayTime = bundle.getInt(TXLiveConstants.EVT_PLAY_PROGRESS);
                                seekBar.setProgress(videoPlayTime);
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
        if (videoPlayTime > 0) {
            mVodPlayer.seek(videoPlayTime);
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 滑动实时调用
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 滑动开始时调用
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 滑动结束时调用
                int process = seekBar.getProgress();
                if (null != mVodPlayer) {
                    mVodPlayer.seek(process);
                }
            }
        });
    }

    private void showPic(final ApiShortVideoDto apiShortVideoDto, final View view) {
        view.findViewById(R.id.rlTimer).setVisibility(View.GONE);
        // 需要付费
        if (apiShortVideoDto.isPrivate == 1 && apiShortVideoDto.isPay == 0 && !SpUtil.getInstance().isFree(apiShortVideoDto.id + "", (int) SpUtil.getInstance().getSharedPreference(READ_SHORT_VIDEO_NUMBER, 0))) {
            MaskImageView maskImageView = view.findViewById(R.id.ivImagesShadow);
            String[] strings = apiShortVideoDto.images.trim().split(",");
            if (strings.length > 0) {
                ImageLoader.displayBlur(strings[0], maskImageView);
            }
            maskImageView.setVisibility(View.VISIBLE);
            view.findViewById(R.id.viewpager).setVisibility(View.GONE);
            view.findViewById(R.id.tv_image).setVisibility(View.GONE);
            view.findViewById(R.id.ivImagesShadow).setVisibility(View.VISIBLE);
            view.findViewById(R.id.btn_pay).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.sb).setVisibility(View.GONE);
            view.findViewById(R.id.ivImagesShadow).setVisibility(View.GONE);
            view.findViewById(R.id.btn_pay).setVisibility(View.GONE);
            viewpager = view.findViewById(R.id.viewpager);
            viewpager.setVisibility(View.VISIBLE);
            tvImage = view.findViewById(R.id.tv_image);
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
        view.findViewById(R.id.btn_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVipPayDialog(apiShortVideoDto, view);
            }
        });
    }

    private void showVipPayDialog(final ApiShortVideoDto apiShortVideoDto, final View itemView) {
        if (null != mVodPlayer) {
            mVodPlayer.pause();
        }
        mClickPaused = true;
        MaskImageView maskImageView = itemView.findViewById(R.id.ivImagesShadow);
        if (apiShortVideoDto.type == 1) {
            ImageLoader.displayBlur(apiShortVideoDto.thumb, maskImageView);
        } else {
            String[] strings = apiShortVideoDto.images.trim().split(",");
            if (strings.length > 0) {
                ImageLoader.displayBlur(strings[0], maskImageView);
            }
        }
        maskImageView.setVisibility(View.VISIBLE);
        itemView.findViewById(R.id.btn_pay).setVisibility(View.VISIBLE);
        itemView.findViewById(R.id.sb).setVisibility(View.GONE);

        if (!mShowed || mAdapter.getItem(mSelectPosition).id != apiShortVideoDto.id) {
            return;
        }
        videoPayTipsDialogFragment = new VideoPayTipsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ApiShortVideoDto", apiShortVideoDto);
        videoPayTipsDialogFragment.setArguments(bundle);
        videoPayTipsDialogFragment.show(getActivity().getSupportFragmentManager(), "VideoPayTipsDialogFragment");
        videoPayTipsDialogFragment.setListener(new VideoPayTipsDialogFragment.VideoPayTipsChoiceListener() {
            @Override
            public void openVip() {
                ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                        + HttpConstants.URL_NOBLE + "_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken()).navigation(getActivity(), RESULT_OK);
            }

            @Override
            public void pey() {
                HttpApiAppShortVideo.useReadShortVideoNumber(apiShortVideoDto.id, 1, new HttpApiCallBack<ApiBaseEntity>() {
                    @Override
                    public void onHttpRet(int code, String msg, ApiBaseEntity retModel) {
                        if (code == 1){
                            if (retModel.code == 1) {
                                if (apiShortVideoDto.type == 1) {
                                    itemView.findViewById(R.id.sb).setVisibility(View.VISIBLE);
                                    if (shortVideoTrialTime > 0 || apiShortVideoDto.isPrivate == 0 || apiShortVideoDto.isPay == 1 || SpUtil.getInstance().isFree(apiShortVideoDto.id + "", (int) SpUtil.getInstance().getSharedPreference(READ_SHORT_VIDEO_NUMBER, 0))){
                                        mVodPlayer.resume();
                                    }else {
                                        playVideo(apiShortVideoDto, itemView);
                                    }
                                } else {
                                    apiShortVideoDto.isPay = 1;
                                    showPic(apiShortVideoDto, itemView);
                                }
                                apiShortVideoDto.isPay = 1;
                                apiShortVideoDto.shortVideoTrialTime = 0;
                                if (null != appShortVideos && appShortVideos.size() > 0) {
                                    appShortVideos.get(mSelectPosition).shortVideoTrialTime = 0;
                                    appShortVideos.get(mSelectPosition).isPay = 1;
                                }
                                itemView.findViewById(R.id.ivImagesShadow).setVisibility(View.GONE);
                                itemView.findViewById(R.id.btn_pay).setVisibility(View.GONE);
                                mAdapter.setItemData(mSelectPosition, apiShortVideoDto);
                                videoPayTipsDialogFragment.dismiss();
                                mClickPaused = false;
                                ToastUtil.show("付费成功");
                            } else if (retModel.code == 2) {
                                ToastUtil.show("观影次数不足");
                            } else if (retModel.code == 3) {//余额不足
                                noMoneyTipsDialogFragment = new NoMoneyTipsDialogFragment();
                                noMoneyTipsDialogFragment.setCanCancel(false);
                                noMoneyTipsDialogFragment.show(getActivity().getSupportFragmentManager(), "NoMoneyTipsDialogFragment");
                                noMoneyTipsDialogFragment.setListener(new NoMoneyTipsDialogFragment.NoMoneyTipsListener() {
                                    @Override
                                    public void goRecharge() {
                                        ARouter.getInstance().build(ARouterPath.MyCoinActivity).navigation();
                                    }

                                    @Override
                                    public void close() {
                                        noMoneyTipsDialogFragment.dismiss();
                                    }
                                });
                            } else {
                                ToastUtil.show(msg);
                            }
                        }else {
                            ToastUtil.show(msg);
                        }
                    }
                });
            }
        });
    }

    /**
     * 停止播放
     */
    private void releaseVideo() {
//        TXCloudVideoView videoView = view.findViewById(R.id.video_view);
//        videoView.removeVideoView();
        if (mVodPlayer != null) {
            mVodPlayer.stopPlay(false);
//            mVodPlayer = null;
        }
    }

    // 查看单个短视频
    private void getVideoData() {
        if (videoType == 4) {
            HttpApiAppShortVideo.getShortVideoInfoList(commentId, videoWorksType, type, new HttpApiCallBackPageArr<ApiShortVideoDto>() {
                @Override
                public void onHttpRet(int code, String msg, PageInfo pageInfo, List<ApiShortVideoDto> retModel) {
                    if (code == 1 && null != retModel && !retModel.isEmpty()) {
                        mParentView.findViewById(R.id.tv_no_data).setVisibility(View.GONE);
                        mAdapter.setData(retModel);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        mParentView.findViewById(R.id.tv_no_data).setVisibility(View.VISIBLE);
                        mAdapter.clearData();
                        if (mVodPlayer != null) {
                            mVodPlayer.stopPlay(false);
                            mVodPlayer = null;
                        }
                    }
                }
            });
        } else if (videoType == 3) {
            HttpApiAppShortVideo.getUserShortVideoPage(page, HttpConstants.PAGE_SIZE, videoWorksUserId, videoWorksType, new HttpApiCallBackPageArr<ApiShortVideoDto>() {
                @Override
                public void onHttpRet(int code, String msg, PageInfo pageInfo, List<ApiShortVideoDto> retModel) {
                    if (mRefreshLayout != null) {
                        mRefreshLayout.setRefreshing(false);
                    }
                    if (code == 1 && null != retModel && !retModel.isEmpty()) {
                        mParentView.findViewById(R.id.tv_no_data).setVisibility(View.GONE);
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
                            mParentView.findViewById(R.id.tv_no_data).setVisibility(View.VISIBLE);
                            mAdapter.clearData();
                            if (mVodPlayer != null) {
                                mVodPlayer.stopPlay(false);
                                mVodPlayer = null;
                            }
                        }
                    }
                }
            });
        } else {
            int type = -1;
            if (videoType == 0 || videoType == 1) {
                classifyId = -1;
                sort = -1;
                type = videoType;
            }
            long adsId = -1;
            if (videoType == 0) {
                if (page > 0 && mAdapter.getItemCount() > 0) {
                    for (int i = mAdapter.getItemCount() - 1; i > 0; i--) {
                        ApiShortVideoDto dto = mAdapter.getItem(i);
                        if (dto.adsType == 2) {
                            adsId = dto.id;
                            break;
                        }
                    }
                }
            }

            /**
             * 短视频首页列表、根据分类查询短视频
             * @param classifyId 分类id(根据分类查询短视频默认传-1)
             * @param page 页数
             * @param pageSize 每页的条数
             * @param sort 排序(-1:默认,1:最多观看、2:最多评论、3:最多点赞、4:最多付费)
             * @param type 列表类型0推荐1关注
             */

            HttpApiAppShortVideo.getShortVideoList(adsId, classifyId, page, HttpConstants.PAGE_SIZE, (int) sort, type, new HttpApiCallBackPageArr<ApiShortVideoDto>() {

                @Override
                public void onHttpRet(int code, String msg, PageInfo pageInfo, List<ApiShortVideoDto> retModel) {
                    if (mRefreshLayout != null) {
                        mRefreshLayout.setRefreshing(false);
                    }
                    if (code == 1 && null != retModel && !retModel.isEmpty()) {
                        mParentView.findViewById(R.id.tv_no_data).setVisibility(View.GONE);
                        if (page == 0) {
                            mInitPosition = 0;
                            mSelectPosition = -1;
                            mAdapter.setData(retModel);
                        } else {
                            mAdapter.loadData(retModel);
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        if (page == 0 && mParentView != null) {
                            mParentView.findViewById(R.id.tv_no_data).setVisibility(View.VISIBLE);
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
    public void onResumeFragment() {
        super.onResumeFragment();
        // 没暂停 / TXVodPlayer非空 / Adapter非空 / 已支付 / 有赠送的免费观看次数  满足条件 让播放
        if (mPaused && null != mAdapter && mAdapter.getItemCount() > 0) {
            if (!mClickPaused && mVodPlayer != null) {
                // 是私密
                if (mAdapter.getItem(mSelectPosition).isPrivate == 1) {
                    //已支付 或者有试看时长
                    if (mAdapter.getItem(mSelectPosition).isPay == 1 || SpUtil.getInstance().isFree(mAdapter.getItem(mSelectPosition).id + "", (int) SpUtil.getInstance().getSharedPreference(READ_SHORT_VIDEO_NUMBER, 0))) {
                        mVodPlayer.resume();
                    }
                    // 非私密 如果没有试看时间  或者 用了免费是看次数
                } else if (mAdapter.getItem(mSelectPosition).shortVideoTrialTime == 0 || SpUtil.getInstance().isFree(mAdapter.getItem(mSelectPosition).id + "", (int) SpUtil.getInstance().getSharedPreference(READ_SHORT_VIDEO_NUMBER, 0))) {
                    mVodPlayer.resume();
                }
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
    public void onStop() {
        super.onStop();
        postEvent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mVodPlayer != null) {
            mVodPlayer.stopPlay(false);
            mVodPlayer = null;
        }
        if (null != timeDisposable) {
            timeDisposable.dispose();
        }
    }

    @Override
    public void setShowed(boolean showed) {
        super.setShowed(showed);
        if (showed) {
            Activity activity = getActivity();
            if (mControlScreenOn && activity != null) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
            if (!mClickPaused && mVodPlayer != null) {
                mVodPlayer.resume();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mShowed) {
                        initGiftAnimate();
                    }
                }
            }, 300);
        } else {
            Activity activity = getActivity();
            if (mControlScreenOn && activity != null) {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
            if (!mClickPaused && mVodPlayer != null) {
                mVodPlayer.pause();
            }
            removeReceiver();
        }
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
                    if (videoType == -1 || videoType == 4) {
                        if (mRefreshLayout != null) {
                            mRefreshLayout.setEnabled(false);
                        }
                    } else {
                        if (mRefreshLayout != null) {
                            mRefreshLayout.setEnabled(true);
                        }
                    }
                    if (videoType == 0 || videoType == 1 || videoType == 4) {
                        mClickPaused = false;
                        getVideoData();
                    } else {
                        if (null != appShortVideos) {
                            mAdapter.setData(appShortVideos);
                            recyclerView.scrollToPosition(mInitPosition);
                        }
                    }
                }
            }, 300);
        }
    }

    //  初始化Adapter 初始化 PagerLayoutManager  Recycler相关
    private void initAdapter() {
        mLayoutManager = new PagerLayoutManager(getContext(), OrientationHelper.VERTICAL);
        mAdapter = new MainVideoAdapter(getActivity());
        mAdapter.setLocation(location);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MainVideoAdapter.onVideoCallBack() {
            @Override
            public void onGift(final ApiShortVideoDto appShortVideo) {
                LiveGiftDialogFragment giftdialogFragment = new LiveGiftDialogFragment();
                SendGiftPeopleBean bean = new SendGiftPeopleBean();
                bean.name = appShortVideo.username;
                bean.headimage = appShortVideo.avatar;
                bean.uid = appShortVideo.userId;
                bean.liveType = 9;
                bean.shortVideoId = -1;
                bean.roomID = -1;
                bean.showid = "-1";
                bean.anchorID = -1;
                List<SendGiftPeopleBean> beanList = new ArrayList<>();
                beanList.add(bean);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("SendList", (ArrayList<? extends Parcelable>) beanList);
                giftdialogFragment.setArguments(bundle);
                giftdialogFragment.show(getChildFragmentManager(), "LiveGiftDialogFragment");

                giftdialogFragment.setSendGiftSuccessCallBack(new LiveGiftDialogFragment.SendGiftSuccessCallBack() {
                    @Override
                    public void onSuccess(NobLiveGift nobLiveGift, int giftNum, SendGiftPeopleBean bean) {
                        ApiUserInfo info = SpUtil.getInstance().<ApiUserInfo>getModel("UserInfo", ApiUserInfo.class);
                        ImMessageUtil.getInstance().sendGiftMsg(bean.uid, bean.headimage, HttpClient.getUid(), info.avatar, nobLiveGift.gifticon, giftNum);
                    }
                });
            }

            @Override
            public void onComment(final ApiShortVideoDto ApiShortVideoDto, final int position) {
                VideoCommentFragmentDialog commentfragment = new VideoCommentFragmentDialog();
                Bundle Trendbundle = new Bundle();
                Trendbundle.putParcelable(ARouterValueNameConfig.ACTIVITYBEAN, ApiShortVideoDto);
                commentfragment.setArguments(Trendbundle);
                commentfragment.show(getChildFragmentManager(), "TrendCommentFragmentDialog");
                commentfragment.setOnCommentNumChangeListener(new VideoCommentFragmentDialog.OnCommentNumChangeListener() {
                    @Override
                    public void onChange(int commentNum) {
                        mAdapter.getList().get(position).comments = commentNum;
                        mAdapter.updateData(mAdapter.getList(), position);
                    }
                });
                commentfragment.setPosition(itemPosition);
                commentfragment.setLocation(location);
            }

            @Override
            public void onShare(final ApiShortVideoDto appShortVideo, final int position) {
                ShareDialog shareDialog = new ShareDialog();
                /*-------- 分享 其他按钮 S ------------------------------------------------------------*/
                ArrayList<ShareDialogBean> otherBeans = new ArrayList<>();
                ShareDialogBean beanCopy = new ShareDialogBean();
                beanCopy.id = 1002;
                beanCopy.src = com.kalacheng.dynamiccircle.R.mipmap.icon_invitation_url_copy;
                beanCopy.text = "复制链接";
                otherBeans.add(beanCopy);
                //判断 当前 查看动态 是否为自己的动态 （通过uid判断）
                if (appShortVideo.userId == HttpClient.getUid()) {
                    ShareDialogBean beanPicture = new ShareDialogBean();
                    beanPicture.id = 1001;
                    beanPicture.src = com.kalacheng.dynamiccircle.R.mipmap.icon_share_delete;
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
                        } else if (id == 1002) {//复制链接
                            if (inviteDto == null) {
                                return;
                            }
                            WordUtil.copyLink(inviteDto.inviteUrl);
                        } else if (id == 1001) { //删除
                            HttpApiAppShortVideo.delShortVideo(appShortVideo.id, new HttpApiCallBack<HttpNone>() {
                                @Override
                                public void onHttpRet(int code, String msg, HttpNone retModel) {
                                    if (!TextUtils.isEmpty(msg)) {
                                        ToastUtil.show(msg);
                                    }
                                    if (code == 1) {
                                        //先暂停 正在 播放的视频，再进行其他操作
                                        releaseVideo();
                                        mInitPosition = position;
                                        mSelectPosition = -1;
                                        if (mAdapter != null && appShortVideo.id != 0) {
                                            int deleteIndex = -1;

                                            //通过 动态的id获取相对列表中的索引位置
                                            for (int i = 0; i < mAdapter.getList().size(); i++) {
                                                if (mAdapter.getList().get(i).id == appShortVideo.id) {
                                                    deleteIndex = i;
                                                }
                                            }
                                            if (deleteIndex != -1) {
                                                mAdapter.getList().remove(deleteIndex);
                                                mAdapter.notifyDataSetChanged();
                                            }
                                        }
                                        //删除 执行父页面回调
                                        if (null != finishCallBack) {
                                            finishCallBack.deleteList(appShortVideo, position);
                                        }
                                        if (mAdapter.getItemCount() == 0) {
                                            if (null != finishCallBack) {
                                                finishCallBack.isFinish();
                                            }
                                        }
                                    }
                                }

                            });
                        }
                    }
                });
                shareDialog.show(getChildFragmentManager(), "ShareDialog");
            }

            @Override
            public void onShop(View view, long GoodsId) {
                // 发布带货视频 带货商品
                showShopPopupWindow(view, GoodsId);
            }
        });

        config = new TXVodPlayConfig();
        config.setCacheFolderPath(getContext().getCacheDir().getAbsolutePath());
        config.setMaxCacheItems(15);
        mLayoutManager.setOnViewPagerListener(new PagerLayoutManager.OnViewPagerListener() {
            @Override
            public void onInitComplete(final View view) {
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
                    adapterItenView = view;
                    initPlay(mSelectPosition, view);
                    if (isShowVideoTips) {
                        getShopView(view, mAdapter.getItem(mSelectPosition));
                    }
                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom, View view) {
                mClickPaused = false;
                if (mAdapter != null && position < mAdapter.getItemCount()) {
                    if (mSelectPosition != position) {
                        releaseVideo();
                        mSelectPosition = position;
                        adapterItenView = view;
                        initPlay(position, view);
                        if (position == mAdapter.getItemCount() - 2 && videoType != -1) {
                            ++page;
                            getVideoData();
                        }
                        if (isShowVideoTips) {
                            getShopView(view, mAdapter.getItem(mSelectPosition));
                        }
                    }
                }
            }

            @Override
            public void onPageRelease(boolean isNext, int position, View view) {
            }
        });
    }

    // 直播购 初次商品操作提示
    private void getShopView(View view, final ApiShortVideoDto apiShortVideoDto) {
        if (ConfigUtil.getBoolValue(R.bool.containShopping) && !TextUtils.isEmpty(apiShortVideoDto.productName) && apiShortVideoDto.productId != 0) {
            final LinearLayout ll = view.findViewById(R.id.llVideoShop);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isShowVideoTips = false;
                    SpUtil.getInstance().put(SpUtil.FIRST_VIDEO_SHOP_TIPS, "isVideoShopShow");
                    showShopTipsPopupWindow(ll, apiShortVideoDto);
                }
            }, 500);
        }
    }

    // 点击商品 弹出商品详情pop 点击pop跳转到商品详情
    private void showShopPopupWindow(final View view, final long goodsId) {
        HttpApiShopGoods.getShopGoods(goodsId, new HttpApiCallBack<ShopGoods>() {
            @Override
            public void onHttpRet(int code, String msg, ShopGoods retModel) {
                if (code == 1 && null != retModel){
                    View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_video_shop_pop, null);
                    final PopupWindow popupWindow = new PopupWindow(contentView);
                    popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                    popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                    popupWindow.setOutsideTouchable(true);
                    // 设置此参数获得焦点，否则无法点击
                    popupWindow.setFocusable(true);
                    popupWindow.showAsDropDown(view);
                    WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                    lp.alpha = 0.7f;//代表透明程度，范围为0 - 1.0f
                    getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    getActivity().getWindow().setAttributes(lp);

                    TextView tvName = contentView.findViewById(R.id.tvName);
                    TextView tvNumber = contentView.findViewById(R.id.tvNumber);
                    TextView tvPrice1 = contentView.findViewById(R.id.tvPrice1);
                    TextView tvPrice2 = contentView.findViewById(R.id.tvPrice2);
                    ImageView ivPic = contentView.findViewById(R.id.ivPic);
                    ImageView ivClose = contentView.findViewById(R.id.ivClose);

                    if (!TextUtils.isEmpty(retModel.goodsName)){
                        tvName.setText(retModel.goodsName);
                    }
                    tvNumber.setText("已卖出" + retModel.soldNum);
                    if (retModel.favorablePrice > 0) {
                        tvPrice1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        tvPrice1.setTextColor(Color.parseColor("#999999"));
                        tvPrice1.setText("¥" + DecimalFormatUtils.toTwo(retModel.price));
                        tvPrice2.setText("¥" + DecimalFormatUtils.toTwo(retModel.favorablePrice));
                    } else {
                        tvPrice1.setVisibility(View.GONE);
                        tvPrice2.setText("¥" + DecimalFormatUtils.toTwo(retModel.price));
                    }
                    ImageLoader.display(retModel.goodsPicture.split(",")[0], ivPic, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

                    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                            lp.alpha = 1f;
                            getActivity().getWindow().setAttributes(lp);
                        }
                    });
                    contentView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                            if (goodsId > 0) {
                                ARouter.getInstance().build(ARouterPath.GoodsDetailsActivity).withLong(ARouterValueNameConfig.goodsId, goodsId).navigation();
                            }
                        }
                    });
                    ivClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                }
            }
        });
    }

    // 全屏的pop 直播购提示覆盖层
    private void showShopTipsPopupWindow(final View view, ApiShortVideoDto apiShortVideoDto) {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_video_shop_tips_pop, null);
        final PopupWindow popupWindow = new PopupWindow(contentView);
        popupWindow.setWidth(DpUtil.getScreenWidth());
        popupWindow.setHeight(DpUtil.getScreenHeight());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setClippingEnabled(false);

        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);

        contentView.findViewById(R.id.rlLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });

        int height = view.getHeight();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1] - DpUtil.dp2px(50) - height;
        TextView textView = contentView.findViewById(R.id.tvVideoShopName);
        textView.setText("视频同款-" + apiShortVideoDto.productName);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, x, y);
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

    private void postEvent() {
        ShortVideosEvent event = new ShortVideosEvent();
        event.type = videoWorksType;
        event.appShortVideos = appShortVideos;
        EventBus.getDefault().post(event);
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void openVip(String vipSuccess) {
//        if (null != vipSuccess && vipSuccess.equals("vipSuccess")) {
//            if (null != videoPayTipsDialogFragment){
//                videoPayTipsDialogFragment.dismiss();
//            }
//            page = 0;
//            getVideoData();
//        }
//    }

}
