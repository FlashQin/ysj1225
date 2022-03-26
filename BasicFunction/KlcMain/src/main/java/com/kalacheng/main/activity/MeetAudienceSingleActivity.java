package com.kalacheng.main.activity;

import android.Manifest;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.busooolive.httpApi.HttpApiOOOLive;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.commonview.utils.OOOLiveCallUtils;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.ApiCfgPayCallOneVsOne;
import com.kalacheng.libuser.model.AppLiveChannel;
import com.kalacheng.main.R;
import com.kalacheng.main.adapter.LiveChannelAdpater;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.PermissionsUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.aop.SingleClick;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.SpaceItemDecoration;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 单人匹配
 */
@Route(path = ARouterPath.MeetAudienceSingleActivity)
public class MeetAudienceSingleActivity extends BaseActivity implements View.OnClickListener {
    @Autowired(name = ARouterValueNameConfig.ApiCfgPayCallOneVsOne)
    public ApiCfgPayCallOneVsOne apiCfgPayCallOneVsOne;
    @Autowired(name = ARouterValueNameConfig.TITLE_NAME)
    public String titleName;

    TextView tvCount, tvNickname, tvAddress, tvVideoPrice, tvVoicePrice;
    ImageView ivAvatar, ivThumb, ivLiveState;
    RecyclerView recyclerView;
    protected int mCountDownCount = 10;
    List<ApiCfgPayCallOneVsOne> apiCfgPayCallOneVsOneList;
    int index = 0;
    PermissionsUtil mProcessResultUtil;
    private TXCloudVideoView video;
    private ScaleAnimation animation;
    private TXVodPlayConfig config;
    private TXVodPlayer mVodPlayer;
    private LiveChannelAdpater liveChannelAdpater;
    private LinearLayout ll_interest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audience_single_meet);
        initView();
        initData();
    }

    private void initView() {
        if (!TextUtils.isEmpty(titleName)) {
            ((TextView) findViewById(R.id.titleView)).setText(titleName);
        }
        tvCount = findViewById(R.id.tv_count);
        tvNickname = findViewById(R.id.tv_nickname);
        tvAddress = findViewById(R.id.tv_address);
        tvVideoPrice = findViewById(R.id.tv_video_price);
        tvVoicePrice = findViewById(R.id.tv_voice_price);
        ivAvatar = findViewById(R.id.iv_avatar);
        ivThumb = findViewById(R.id.iv_thumb);
        video = (TXCloudVideoView) findViewById(R.id.video_view);
        recyclerView = findViewById(R.id.recyclerView);
        ivLiveState = findViewById(R.id.ivLiveState);
        ll_interest = findViewById(R.id.ll_interest);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.addItemDecoration(new SpaceItemDecoration(DpUtil.dp2px(12), 0));

        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.ll_refresh).setOnClickListener(this);
        findViewById(R.id.ll_info).setOnClickListener(this);
        findViewById(R.id.ll_video).setOnClickListener(this);
        findViewById(R.id.ll_voice).setOnClickListener(this);
        findViewById(R.id.iv_avatar).setOnClickListener(this);

        config = new TXVodPlayConfig();
        config.setCacheFolderPath(mContext.getCacheDir().getAbsolutePath());
        config.setMaxCacheItems(15);
    }

    private void initData() {
        mProcessResultUtil = new PermissionsUtil(this);
        animation = new ScaleAnimation(3, 1, 3, 1, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setRepeatCount(10);
        if (apiCfgPayCallOneVsOne == null) {
            HttpApiOOOLive.meetUser((float) SpUtil.getInstance().getSharedPreference(SpUtil.LATITUDE, HttpConstants.LAT), (float) SpUtil.getInstance().getSharedPreference(SpUtil.LONGITUDE, HttpConstants.LNG), new HttpApiCallBackArr<ApiCfgPayCallOneVsOne>() {
                @Override
                public void onHttpRet(int code, String msg, List<ApiCfgPayCallOneVsOne> retModel) {
                    if (code == 1 && null != retModel) {
                        apiCfgPayCallOneVsOneList = retModel;
                        showUserInfo(retModel.get(0));
                    }
                }
            });
            HttpApiAppUser.personCenter(-1, -1, HttpClient.getUid(), new HttpApiCallBack<ApiUserInfo>() {
                @Override
                public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                    if (code == 1 && retModel != null) {
                        ImageLoader.display(retModel.avatar, ivThumb);
                    }
                }
            });
        } else {
            video.setVisibility(View.VISIBLE);
            tvCount.setVisibility(View.GONE);
            findViewById(R.id.ll_refresh).setVisibility(View.GONE);
            showUserInfo(apiCfgPayCallOneVsOne);
        }
    }

    public void showUserInfo(ApiCfgPayCallOneVsOne apiCfgPayCallOneVsOne) {
        if (null != apiCfgPayCallOneVsOne) {
            if (apiCfgPayCallOneVsOne.tabIdList != null && !TextUtils.isEmpty(apiCfgPayCallOneVsOne.tabIdList)) {
                List<AppLiveChannel> appLiveChannels = new ArrayList<AppLiveChannel>();
                List<String> tabs = Arrays.asList(apiCfgPayCallOneVsOne.tabIdList.split(","));
                for (String tab : tabs) {
                    AppLiveChannel appLiveChannel = new AppLiveChannel();
                    appLiveChannel.title = tab;
                    appLiveChannels.add(appLiveChannel);
                }
//                Map<String, String> map = JSONObject.parseObject(apiCfgPayCallOneVsOne.tabIdList, Map.class);
//                for (String key : map.keySet()) {
//                    AppLiveChannel appLiveChannel = new AppLiveChannel();
//                    appLiveChannel.title = map.get(key);
//                    appLiveChannels.add(appLiveChannel);
//                }
                if (null == liveChannelAdpater) {
                    liveChannelAdpater = new LiveChannelAdpater(appLiveChannels);
                    liveChannelAdpater.setEnable(false);
                    recyclerView.setAdapter(liveChannelAdpater);
                } else {
                    liveChannelAdpater.setData(appLiveChannels);
                }
                ll_interest.setVisibility(View.VISIBLE);
            } else {
                ll_interest.setVisibility(View.GONE);
                if (null != liveChannelAdpater) {
                    liveChannelAdpater.clearData();
                }
            }
            if (apiCfgPayCallOneVsOne.liveThumb != null && !TextUtils.isEmpty(apiCfgPayCallOneVsOne.liveThumb)) {
                ImageLoader.display(apiCfgPayCallOneVsOne.liveThumb, ivAvatar);
            }
            if (apiCfgPayCallOneVsOne.userName != null && !TextUtils.isEmpty(apiCfgPayCallOneVsOne.userName)) {
                tvNickname.setText(apiCfgPayCallOneVsOne.userName);
            }
            if (apiCfgPayCallOneVsOne.city != null && !TextUtils.isEmpty(apiCfgPayCallOneVsOne.city)) {
                tvAddress.setText(apiCfgPayCallOneVsOne.city + "," + apiCfgPayCallOneVsOne.distance + " km");
            }
            if (apiCfgPayCallOneVsOne.openState == 0) {
                ivLiveState.setImageResource(R.drawable.lightgrey_oval);
            } else {
                ivLiveState.setImageResource(R.drawable.green_oval);
            }
            if (this.apiCfgPayCallOneVsOne == null) {
                startVideo(apiCfgPayCallOneVsOne);
            } else {
                initPlayer(this.apiCfgPayCallOneVsOne.video);
            }
            tvVoicePrice.setText((int) apiCfgPayCallOneVsOne.voiceCoin + SpUtil.getInstance().getCoinUnit() + "/分钟");
            tvVideoPrice.setText((int) apiCfgPayCallOneVsOne.videoCoin + SpUtil.getInstance().getCoinUnit() + "/分钟");
        }
    }

    private void startVideo(ApiCfgPayCallOneVsOne apiCfgPayCallOneVsOne) {
        if (null != apiCfgPayCallOneVsOne) {
            startCountDown();
            if (apiCfgPayCallOneVsOne.video != null && !TextUtils.isEmpty(apiCfgPayCallOneVsOne.video)) {
                if (TextUtils.isEmpty(apiCfgPayCallOneVsOne.video)) {
                    return;
                }
                initPlayer(apiCfgPayCallOneVsOne.video);
            }
        }
    }

    private void initPlayer(String url) {
        if (mVodPlayer == null) {
            mVodPlayer = new TXVodPlayer(mContext);
            mVodPlayer.setConfig(config);
            mVodPlayer.setAutoPlay(true);
            mVodPlayer.setLoop(true);
//        mVodPlayer.setMute(true);
//关键 player 对象与界面 view
            mVodPlayer.setPlayerView(video);
            mVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
            mVodPlayer.setVodListener(new ITXVodPlayListener() {
                @Override
                public void onPlayEvent(TXVodPlayer txVodPlayer, int e, Bundle bundle) {
                    switch (e) {
                        case TXLiveConstants.PLAY_EVT_PLAY_BEGIN://加载完成，开始播放的回调
//                                binding.ivThumb.setVisibility(View.GONE);
                            break;
                        case TXLiveConstants.PLAY_EVT_PLAY_LOADING: //开始加载的回调

                            break;
                        case TXLiveConstants.PLAY_EVT_PLAY_END://获取到视频播放完毕的回调
                            break;
                        case TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME://获取到视频首帧回调

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
                    }
                }

                @Override
                public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

                }
            });
        } else {
            mVodPlayer.stopPlay(false);
        }
        mVodPlayer.startPlay(url);
    }

    protected void startCountDown() {
        tvCount.clearAnimation();
        mCountDownCount = 10;
        tvCount.setText(String.valueOf(mCountDownCount));
        if (null != animation) {
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (null != apiCfgPayCallOneVsOneList && apiCfgPayCallOneVsOneList.size() != 0) {
                        if (index < apiCfgPayCallOneVsOneList.size() - 1) {
                            ++index;
                        } else {
                            index = 0;
                        }
                        showUserInfo(apiCfgPayCallOneVsOneList.get(index));
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    mCountDownCount--;
                    tvCount.setText(String.valueOf(mCountDownCount));
                }
            });
            tvCount.startAnimation(animation);
        }
    }

    // 返回调用接口 取消我再遇见里的状态
    private void exitMeetUser() {
        HttpApiOOOLive.exitMeetUser(new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                finish();
            }
        });
    }

    // 返回调用接口 取消我再遇见里的状态
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitMeetUser();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.btn_back) {
            exitMeetUser();
        } else if (view.getId() == R.id.ll_refresh) {
            if ((null != apiCfgPayCallOneVsOneList && apiCfgPayCallOneVsOneList.size() != 0)) {
                if (index < apiCfgPayCallOneVsOneList.size() - 1) {
                    ++index;
                } else {
                    index = 0;
                }
                showUserInfo(apiCfgPayCallOneVsOneList.get(index));
            }
        } else if (view.getId() == R.id.ll_video) {
            if ((null != apiCfgPayCallOneVsOneList && apiCfgPayCallOneVsOneList.size() != 0)) {
                final ApiUserInfo info = new ApiUserInfo();
                info.userId = apiCfgPayCallOneVsOneList.get(index).userId;
                LiveConstants.mIsOOOSend = true;
                info.avatar = apiCfgPayCallOneVsOneList.get(index).liveThumb;
                info.username = apiCfgPayCallOneVsOneList.get(index).userName;
                mProcessResultUtil.requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                }, new Runnable() {
                    @Override
                    public void run() {
                        OOOLiveCallUtils.getInstance().OnetoOneinviteAnchorChat(mContext, 1, info, 1);

                    }
                });
            }
            if (null != apiCfgPayCallOneVsOne) {
                final ApiUserInfo info = new ApiUserInfo();
                info.userId = apiCfgPayCallOneVsOne.userId;
                LiveConstants.mIsOOOSend = true;
                info.avatar = apiCfgPayCallOneVsOne.liveThumb;
                info.username = apiCfgPayCallOneVsOne.userName;
                mProcessResultUtil.requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                }, new Runnable() {
                    @Override
                    public void run() {
                        OOOLiveCallUtils.getInstance().OnetoOneinviteAnchorChat(mContext, 1, info, 1);

                    }
                });
            }
        } else if (view.getId() == R.id.ll_voice) {
            if ((null != apiCfgPayCallOneVsOneList && apiCfgPayCallOneVsOneList.size() != 0)) {
                final ApiUserInfo info = new ApiUserInfo();
                info.userId = apiCfgPayCallOneVsOneList.get(index).userId;
                LiveConstants.mIsOOOSend = true;
                info.avatar = apiCfgPayCallOneVsOneList.get(index).liveThumb;
                info.username = apiCfgPayCallOneVsOneList.get(index).userName;
                mProcessResultUtil.requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                }, new Runnable() {
                    @Override
                    public void run() {
                        OOOLiveCallUtils.getInstance().OnetoOneinviteAnchorChat(mContext, 0, info, 1);

                    }
                });
            }
            if (null != apiCfgPayCallOneVsOne) {
                final ApiUserInfo info = new ApiUserInfo();
                info.userId = apiCfgPayCallOneVsOne.userId;
                LiveConstants.mIsOOOSend = true;
                info.avatar = apiCfgPayCallOneVsOne.liveThumb;
                info.username = apiCfgPayCallOneVsOne.userName;
                mProcessResultUtil.requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                }, new Runnable() {
                    @Override
                    public void run() {
                        OOOLiveCallUtils.getInstance().OnetoOneinviteAnchorChat(mContext, 0, info, 1);

                    }
                });
            }
        } else if (view.getId() == R.id.iv_avatar || view.getId() == R.id.ll_info) {
            if ((null != apiCfgPayCallOneVsOneList && apiCfgPayCallOneVsOneList.size() != 0)) {
                ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, apiCfgPayCallOneVsOneList.get(index).userId).navigation();
            }
            if (null != apiCfgPayCallOneVsOne) {
                ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, apiCfgPayCallOneVsOne.userId).navigation();

            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mVodPlayer) {
            mVodPlayer.stopPlay(false);
            mVodPlayer = null;
        }
        tvCount.clearAnimation();
        animation = null;
    }

    @Override
    protected void onPause() {
        if (mVodPlayer != null) {
            mVodPlayer.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVodPlayer != null) {
            mVodPlayer.resume();
        }
    }
}
