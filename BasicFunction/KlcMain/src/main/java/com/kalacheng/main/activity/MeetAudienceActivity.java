package com.kalacheng.main.activity;

import android.Manifest;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.listener.OnItemClickCallback;
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
import com.kalacheng.libuser.model.ApiCfgPayCallOneVsOne;
import com.kalacheng.libuser.model.AppLiveChannel;
import com.kalacheng.main.R;
import com.kalacheng.main.adapter.LiveChannelAdpater;
import com.kalacheng.main.adapter.ManyPeopleVideoAdapter;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.PermissionsUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.SpaceItemDecoration;
import com.kalacheng.util.view.SpacesItemDecoration;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.xuantongyun.livecloud.protocol.OtherLiveSetUtlis;
import com.xuantongyun.livecloud.protocol.PulicUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Route(path = ARouterPath.MeetAudienceActivity)
public class MeetAudienceActivity extends BaseActivity implements View.OnClickListener {
    @Autowired(name = ARouterValueNameConfig.ApiCfgPayCallOneVsOne)
    public ApiCfgPayCallOneVsOne apiCfgPayCallOneVsOne;

    TextView tvCount, tvNickname, tvAddress, tvVideoPrice, tvVoicePrice, tvCountMany, tvSpeed;
    ImageView ivSpeed, ivAvatar, ivUserVideo, ivUserVoice, ivThumb, ivLiveState;
    RecyclerView recyclerView, recyclerViewMany;
    protected int mCountDownCount = 9;
    List<ApiCfgPayCallOneVsOne> apiCfgPayCallOneVsOneList;
    int index = 0;
    PermissionsUtil mProcessResultUtil;
    private TXCloudVideoView video;
    private ManyPeopleVideoAdapter adapter;
    boolean isMany, isShowVideo, isShowVoice;
    private List<ApiCfgPayCallOneVsOne> newList = new ArrayList<>();
    RelativeLayout rlVideo;
    private SurfaceView surfaceView;
    private ScaleAnimation animation;
    private TXVodPlayConfig config;
    TXVodPlayer mVodPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audience_meet);
        initView();
        initData();
    }

    private void initView() {
        tvCount = findViewById(R.id.tv_count);
        tvNickname = findViewById(R.id.tv_nickname);
        tvAddress = findViewById(R.id.tv_address);
        tvVideoPrice = findViewById(R.id.tv_video_price);
        tvVoicePrice = findViewById(R.id.tv_voice_price);
        tvCountMany = findViewById(R.id.tv_count_many);
        tvSpeed = findViewById(R.id.tv_speed);
        ivSpeed = findViewById(R.id.iv_speed);
        ivAvatar = findViewById(R.id.iv_avatar);
        ivThumb = findViewById(R.id.iv_thumb);
        rlVideo = findViewById(R.id.rl_video);
        ivUserVideo = findViewById(R.id.iv_user_video);
        ivUserVoice = findViewById(R.id.iv_user_voice);
        video = (TXCloudVideoView) findViewById(R.id.video_view);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewMany = findViewById(R.id.recyclerView_many);
        ivLiveState = findViewById(R.id.ivLiveState);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.addItemDecoration(new SpaceItemDecoration(DpUtil.dp2px(12), DpUtil.dp2px(5)));

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(SpacesItemDecoration.TOP_DECORATION, 15);//上下间距
        stringIntegerHashMap.put(SpacesItemDecoration.RIGHT_DECORATION, 25);
        recyclerViewMany.addItemDecoration(new SpacesItemDecoration(stringIntegerHashMap));
        recyclerViewMany.setLayoutManager(new GridLayoutManager(this, 3));

        findViewById(R.id.ll_speed).setOnClickListener(this);
        findViewById(R.id.ll_refresh).setOnClickListener(this);
        findViewById(R.id.ll_video).setOnClickListener(this);
        findViewById(R.id.ll_voice).setOnClickListener(this);
        findViewById(R.id.icon_back).setOnClickListener(this);
        findViewById(R.id.iv_refresh).setOnClickListener(this);
        findViewById(R.id.ll_info).setOnClickListener(this);
        findViewById(R.id.iv_avatar).setOnClickListener(this);
        ivUserVideo.setOnClickListener(this);
        ivUserVoice.setOnClickListener(this);
    }

    private void initData() {
        mProcessResultUtil = new PermissionsUtil(this);
        animation = new ScaleAnimation(3, 1, 3, 1, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setRepeatCount(9);
        config = new TXVodPlayConfig();
        config.setCacheFolderPath(mContext.getCacheDir().getAbsolutePath());
        config.setMaxCacheItems(15);
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
            findViewById(R.id.ll_speed).setVisibility(View.GONE);
        }

    }

    public void showUserInfo(ApiCfgPayCallOneVsOne apiCfgPayCallOneVsOne) {
        if (null != apiCfgPayCallOneVsOne) {
            if (apiCfgPayCallOneVsOne.tabIdList != null && !TextUtils.isEmpty(apiCfgPayCallOneVsOne.tabIdList)) {
                String[] tabIdStr = apiCfgPayCallOneVsOne.tabIdList.split(",");
                List<AppLiveChannel> appLiveChannels = new ArrayList<AppLiveChannel>();
                for (int i = 0; i < tabIdStr.length; i++) {
                    String[] strings = tabIdStr[i].split(":");
                    AppLiveChannel appLiveChannel = new AppLiveChannel();
                    appLiveChannel.title = strings[1];
                    appLiveChannels.add(appLiveChannel);
                }
                LiveChannelAdpater liveChannelAdpater = new LiveChannelAdpater(appLiveChannels);
                liveChannelAdpater.setEnable(false);
                recyclerView.setAdapter(liveChannelAdpater);

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
        mVodPlayer = new TXVodPlayer(mContext);
        mVodPlayer.setConfig(config);
        mVodPlayer.setAutoPlay(true);
        mVodPlayer.setLoop(true);
        mVodPlayer.setMute(true);
//关键 player 对象与界面 view
        mVodPlayer.setPlayerView(video);
        mVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
        mVodPlayer.startPlay(url);
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
                }
            }

            @Override
            public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

            }
        });
    }

    protected void startCountDown() {
        tvCount.clearAnimation();
        mCountDownCount = 9;
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_speed) {
            if (isMany) {
                initSinglePeople();
            } else {
                initManyPeople();
            }
            isMany = !isMany;

        } else if (view.getId() == R.id.ll_refresh) {
            if (null != apiCfgPayCallOneVsOneList && apiCfgPayCallOneVsOneList.size() != 0) {
                if (index < apiCfgPayCallOneVsOneList.size() - 1) {
                    ++index;
                } else {
                    index = 0;
                }
                showUserInfo(apiCfgPayCallOneVsOneList.get(index));
            }
        } else if (view.getId() == R.id.ll_video) {
            if (null != apiCfgPayCallOneVsOneList && apiCfgPayCallOneVsOneList.size() != 0) {
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
        } else if (view.getId() == R.id.ll_voice) {
            if (null != apiCfgPayCallOneVsOneList && apiCfgPayCallOneVsOneList.size() != 0) {
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
        } else if (view.getId() == R.id.icon_back) {
            finish();
        } else if (view.getId() == R.id.iv_refresh) {
            updateManyPeople();
        } else if (view.getId() == R.id.iv_user_video) {
            if (isShowVideo) {
                ivThumb.setVisibility(View.VISIBLE);
                removeLocalVideo();
                ivUserVideo.setImageResource(R.mipmap.bg_video_open);
            } else {
                ivThumb.setVisibility(View.GONE);
                showLocalVideo();
                ivUserVideo.setImageResource(R.mipmap.bg_video_close);
            }
            isShowVideo = !isShowVideo;
        } else if (view.getId() == R.id.iv_user_voice) {
            if (isShowVoice) {
                PulicUtils.getInstance().muteLocalAudioStream(false);
                ivUserVoice.setImageResource(R.mipmap.anchor_voice_open);
            } else {
                PulicUtils.getInstance().muteLocalAudioStream(true);
                ivUserVoice.setImageResource(R.mipmap.anchor_voice_close);
            }
            isShowVoice = !isShowVoice;
        } else if (view.getId() == R.id.iv_avatar || view.getId() == R.id.ll_info) {
            if (null != apiCfgPayCallOneVsOneList && apiCfgPayCallOneVsOneList.size() != 0) {
                ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, apiCfgPayCallOneVsOneList.get(index).userId).navigation();
            }
        }
    }

    private void initSinglePeople() {
        video.setVisibility(View.VISIBLE);
        tvCount.setVisibility(View.VISIBLE);
        tvCountMany.clearAnimation();
        tvCount.clearAnimation();
        newList.clear();
        if (null != adapter)
            adapter.notifyDataSetChanged();
        if (null != apiCfgPayCallOneVsOneList && apiCfgPayCallOneVsOneList.size() != 0)
            startVideo(apiCfgPayCallOneVsOneList.get(index));
        findViewById(R.id.rl_single).setVisibility(View.VISIBLE);
        findViewById(R.id.rl_many).setVisibility(View.GONE);
        ivSpeed.setImageResource(R.mipmap.icon_many_people);
        tvSpeed.setText("多人匹配");


    }

    public void initManyPeople() {
//        video.setVisibility(View.GONE);
//        video.stopPlayback();
        tvCount.setVisibility(View.GONE);
        tvCount.clearAnimation();
        if (null != animation) {
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    updateManyPeople();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    mCountDownCount--;
                    tvCountMany.setText(String.valueOf(mCountDownCount));
                }
            });
        }
        findViewById(R.id.rl_single).setVisibility(View.GONE);
        findViewById(R.id.rl_many).setVisibility(View.VISIBLE);
        ivSpeed.setImageResource(R.mipmap.icon_single_people);
        tvSpeed.setText("单人匹配");
        if (null != apiCfgPayCallOneVsOneList && apiCfgPayCallOneVsOneList.size() != 0) {
            if (null == adapter) {
                newList.clear();
                newList.addAll(createRandomList(apiCfgPayCallOneVsOneList, 6));
                adapter = new ManyPeopleVideoAdapter(newList, mProcessResultUtil);
                recyclerViewMany.setAdapter(adapter);
                adapter.setItemClickCallback(new OnItemClickCallback<ApiCfgPayCallOneVsOne>() {
                    @Override
                    public void onClick(View view, ApiCfgPayCallOneVsOne item) {
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
                });
            } else {
                updateManyPeople();
            }
        }
    }

    private void showLocalVideo() {
        mProcessResultUtil.requestPermissions(new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        }, new Runnable() {
            @Override
            public void run() {
                OtherLiveSetUtlis.getInstance().init(mContext);
                surfaceView = OtherLiveSetUtlis.getInstance().setupLocalVideo((int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_IMAGE_QUALITY, 0));
//                surfaceView = tcloud.playLocalVideo((int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_IMAGE_QUALITY, 0), HttpClient.getUid());
                surfaceView.setZOrderMediaOverlay(true);
                rlVideo.addView(surfaceView);
            }
        });
    }

    private void removeLocalVideo() {
        rlVideo.removeView(surfaceView);
        surfaceView = null;
    }

    private void updateManyPeople() {
        mCountDownCount = 9;
        tvCountMany.clearAnimation();
        tvCountMany.setText(String.valueOf(mCountDownCount));
        if (null != animation)
            tvCountMany.startAnimation(animation);
        HttpClient.getInstance().cancel("/api/OOOLive/meetUser");
        HttpApiOOOLive.meetUser((float) SpUtil.getInstance().getSharedPreference(SpUtil.LATITUDE, HttpConstants.LAT), (float) SpUtil.getInstance().getSharedPreference(SpUtil.LONGITUDE, HttpConstants.LNG), new HttpApiCallBackArr<ApiCfgPayCallOneVsOne>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiCfgPayCallOneVsOne> retModel) {
                if (code == 1 && null != retModel) {
                    apiCfgPayCallOneVsOneList.clear();
                    apiCfgPayCallOneVsOneList.addAll(retModel);
                    newList.clear();
                    newList.addAll(createRandomList(apiCfgPayCallOneVsOneList, 6));
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 从list中随机抽取元素
     *
     * @param list
     * @param n
     * @return void
     * @throws
     * @Title: createRandomList
     */
    private List createRandomList(List list, int n) {
        Map map = new HashMap();
        List listNew = new ArrayList();
        int size;
        if (list.size() < n) {
            size = list.size();
        } else {
            size = n;
        }
        while (map.size() < size) {
            int random = (int) (Math.random() * list.size());
            if (!map.containsKey(random)) {
                map.put(random, "");
                listNew.add(list.get(random));
            }
        }
        return listNew;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        video.stopPlayback();
//        video.setOnCompletionListener(null);
//        video.setOnPreparedListener(null);
        tvCount.clearAnimation();
        tvCountMany.clearAnimation();
        animation = null;
    }
}
