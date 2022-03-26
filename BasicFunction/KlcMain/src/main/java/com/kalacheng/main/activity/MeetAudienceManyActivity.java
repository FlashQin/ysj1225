package com.kalacheng.main.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.activty.BaseApplication;
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
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.ApiCfgPayCallOneVsOne;
import com.kalacheng.main.R;
import com.kalacheng.main.adapter.ManyPeopleVideoAdapter;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.PermissionsUtil;
import com.kalacheng.util.utils.RandomUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.ItemDecoration;
import com.kalacheng.util.view.ItemLayout;
import com.xuantongyun.livecloud.agora.framework.TiPreprocessor;
import com.xuantongyun.livecloud.config.TTTConfigUtils;
import com.xuantongyun.livecloud.protocol.OtherLiveSetUtlis;
import com.xuantongyun.livecloud.protocol.PulicUtils;

import java.util.ArrayList;
import java.util.List;

import io.agora.capture.video.camera.CameraVideoManager;

/**
 * 视频速配 多人匹配
 */
@Route(path = ARouterPath.MeetAudienceManyActivity)
public class MeetAudienceManyActivity extends BaseActivity implements View.OnClickListener {

    TextView tvCountMany, tvTips;
    ImageView ivUserVideo, ivUserVoice, ivThumb;
    RecyclerView recyclerViewMany;
    protected int mCountDownCount = 10;
    List<ApiCfgPayCallOneVsOne> apiCfgPayCallOneVsOneList = new ArrayList<>();
    int index = 0;
    PermissionsUtil mProcessResultUtil;
    private ManyPeopleVideoAdapter adapter;
    boolean isShowVideo, isShowVoice;
    private List<ApiCfgPayCallOneVsOne> newList = new ArrayList<>();
    CardView rlVideo;
    private SurfaceView surfaceView;
    private ScaleAnimation animation;
    private CameraVideoManager mVideoManager;
    private boolean isMeetUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audience_many_meet);
        initView();
        initData();
    }

    private void initView() {
        ItemLayout layoutMeAvatar = findViewById(R.id.layoutMeAvatar);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layoutMeAvatar.getLayoutParams();
        params.width = (DpUtil.getScreenWidth() - DpUtil.dp2px(60)) / 3;
        tvCountMany = findViewById(R.id.tv_count_many);
        tvTips = findViewById(R.id.tv_tips);
        ivThumb = findViewById(R.id.iv_thumb);
        rlVideo = findViewById(R.id.rl_video);
        ivUserVideo = findViewById(R.id.iv_user_video);
        ivUserVoice = findViewById(R.id.iv_user_voice);
        recyclerViewMany = findViewById(R.id.recyclerView_many);
        recyclerViewMany.setLayoutManager(new GridLayoutManager(this, 3));

        findViewById(R.id.iv_refresh).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);

        ivUserVideo.setOnClickListener(this);
        ivUserVoice.setOnClickListener(this);
    }

    private void initData() {
        mProcessResultUtil = new PermissionsUtil(this);
        animation = new ScaleAnimation(3, 1, 3, 1, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setRepeatCount(10);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!isMeetUser){
                    updateManyPeople();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                mCountDownCount--;
                tvCountMany.setText(String.valueOf(mCountDownCount));
            }
        });

        HttpApiOOOLive.meetposts(9, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1 && null != retModel) {
                    tvTips.setText(Html.fromHtml(retModel.no_use));
                }
            }
        });
        HttpApiAppUser.personCenter(-1, -1, HttpClient.getUid(), new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && null != retModel) {
                    ImageLoader.display(retModel.avatar, ivThumb);
                }
            }
        });
        updateManyPeople();
    }

    // 返回调用接口 取消我再遇见里的状态
    private void exitMeetUser(){
        isMeetUser = true;
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
        if(keyCode == KeyEvent.KEYCODE_BACK){
            exitMeetUser();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.btn_back) {
            exitMeetUser();
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

    @Override
    protected void onResume() {
        super.onResume();
        if (isShowVideo) {
            showLocalVideo();
        }
    }

    @Override
    protected void onPause() {
        if (isShowVideo) {
            removeLocalVideo();
        }
        super.onPause();
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
                OtherLiveSetUtlis.getInstance().setClientRole(1);
//                surfaceView = tcloud.playLocalVideo((int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_IMAGE_QUALITY, 0), HttpClient.getUid());
                surfaceView.setZOrderMediaOverlay(true);
                rlVideo.addView(surfaceView, 0);

                if (!TTTConfigUtils.getInstance().getConfig().isTTT() && TTTConfigUtils.getInstance().getConfig().getBeautySwitch() == 1) {
                    TiPreprocessor.mEnabled = true;
                    mVideoManager = ((BaseApplication)BaseApplication.getInstance()).getCameraVideoManager();
                    if (mVideoManager != null) {
                        mVideoManager.setLocalPreview(surfaceView);
                        mVideoManager.startCapture();
                    } else {
                        ((BaseApplication)BaseApplication.getInstance()).initVideoCaptureAsync();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mVideoManager = ((BaseApplication)BaseApplication.getInstance()).getCameraVideoManager();
                                mVideoManager.setLocalPreview(surfaceView);
                                mVideoManager.startCapture();
                            }
                        }, 500);
                    }
                }
            }
        });
    }

    private void removeLocalVideo() {
        rlVideo.removeView(surfaceView);
        surfaceView = null;

        if (mVideoManager != null) {
            mVideoManager.stopCapture();
            TiPreprocessor.mEnabled = false;
        }
    }

    private void updateManyPeople() {
        mCountDownCount = 10;
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
                    newList.addAll(RandomUtil.createRandomList(apiCfgPayCallOneVsOneList, 3));
                    if (null == adapter) {
                        adapter = new ManyPeopleVideoAdapter(newList, mProcessResultUtil);
                        recyclerViewMany.setAdapter(adapter);
                        recyclerViewMany.addItemDecoration(new ItemDecoration(mContext, 0, 18, 0));
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
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvCountMany.clearAnimation();
        animation = null;
    }
}
