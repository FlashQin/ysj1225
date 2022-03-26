package com.kalacheng.login.component;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.commonview.utils.WebUtil;
import com.kalacheng.base.event.TokenInvalidEvent;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.httpApi.HttpApiAppLogin;
import com.kalacheng.libuser.model.APPConfig;
import com.kalacheng.libuser.model.AppAds;
import com.kalacheng.login.R;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.xuantongyun.livecloud.config.TTTConfigUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.tillusory.sdk.TiSDK;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class BaseLauncherActivity extends BaseActivity {

    private Handler mHandler;
    private ImageView ivAdvert;
    private FrameLayout layoutAdvertSkip;
    private TextView tvAdvertSkip;
    private VideoView video;
    private Disposable disposable;
    private String url;
    private NavigationCallback navigationCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //下面的代码是为了防止一个bug:
        // 收到极光通知后，点击通知，如果没有启动app,则启动app。然后切后台，再次点击桌面图标，app会重新启动，而不是回到前台。
        Intent intent = getIntent();
        if (!isTaskRoot()
                && intent != null
                && intent.hasCategory(Intent.CATEGORY_LAUNCHER)
                && intent.getAction() != null
                && intent.getAction().equals(Intent.ACTION_MAIN)) {
            finish();
            return;
        }
        setContentView(R.layout.activity_launcher);
        EventBus.getDefault().register(this);

        initViews();
        initData();
    }

    private void initViews() {
        ivAdvert = findViewById(R.id.ivAdvert);
        layoutAdvertSkip = findViewById(R.id.layoutAdvertSkip);
        tvAdvertSkip = findViewById(R.id.tvAdvertSkip);
        video = findViewById(R.id.video_view);

        layoutAdvertSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNext(true);
            }
        });

        ivAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNext(false);
            }
        });
    }

    private void initData() {
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getConfig();
            }
        }, 300);

        navigationCallback = new NavigationCallback() {
            @Override
            public void onFound(Postcard postcard) {

            }

            @Override
            public void onLost(Postcard postcard) {

            }

            @Override
            public void onArrival(Postcard postcard) {
                finish();
            }

            @Override
            public void onInterrupt(Postcard postcard) {

            }
        };
    }

    @Override
    protected void onDestroy() {
        HttpClient.getInstance().cancel("/api/login/getConfig");
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if (video != null) {
            video.stopPlayback();
            video.setOnCompletionListener(null);
            video.setOnPreparedListener(null);
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 获取Config信息
     */
    private void getConfig() {
        HttpApiAppLogin.getConfig(new HttpApiCallBack<APPConfig>() {
            @Override
            public void onHttpRet(int code, String msg, APPConfig retModel) {
                if (code == 1 && null != retModel) {
                    SpUtil.getInstance().put(SpUtil.CONFIG_VCUNIT, retModel.vcUnit);//虚拟货币单位
                    SpUtil.getInstance().put(SpUtil.CONFIG_IMAGE_QUALITY, retModel.imageQuality);//直播画质
                    SpUtil.getInstance().put(SpUtil.CONFIG_SOCKET_IP, retModel.liveKey.imIp);
                    SpUtil.getInstance().put(SpUtil.CONFIG_SOCKET_PORT, retModel.liveKey.imProt);
                    SpUtil.getInstance().put(SpUtil.CONFIG_LOGIN_TYPE, retModel.loginSwitch.loginType);
                    SpUtil.getInstance().put(SpUtil.CONFIG_SHARE_TYPE, retModel.loginSwitch.shareType);
                    SpUtil.getInstance().put(SpUtil.CONFIG_STY_KEY, retModel.liveKey.liveKey);
                    SpUtil.getInstance().put(SpUtil.CONFIG_CDN_KEY, retModel.liveKey.cdnCfgKey);
                    SpUtil.getInstance().put(SpUtil.CONFIG_IS_REG_CODE, retModel.loginSwitch.isRegCode);
                    SpUtil.getInstance().put(SpUtil.CONFIG_IM_KEY, retModel.liveKey.imKey);
                    SpUtil.getInstance().put(SpUtil.CONFIG_BARRAGEFEE, retModel.adminLiveConfig.barrageFee);//弹幕费用
                    SpUtil.getInstance().put(SpUtil.CONFIG_BANNER_JUMP_MODE, retModel.adminLiveConfig.jumpMode);//广告跳转方式，0 app内；1 外部浏览器
                    SpUtil.getInstance().put(SpUtil.BEAUTY_SWITCH, retModel.liveKey.beautySwitch);
                    SpUtil.getInstance().put(SpUtil.CONFIG_VIDEO_CLIP_KEY, retModel.liveKey.videoClipsKey);
                    SpUtil.getInstance().put(SpUtil.BEAUTY_KEY, retModel.liveKey.beautyKey);
                    SpUtil.getInstance().put(SpUtil.AUTH_IS_SEX, retModel.adminLiveConfig.authIsSex);
                    SpUtil.getInstance().put(SpUtil.CONFIG_ISSHWCOIN, retModel.adminLiveConfig.isShowCoin);
                    SpUtil.getInstance().put(SpUtil.CONFIG_MUISC, retModel.adminLiveConfig.pushMusic);
                    SpUtil.getInstance().put(SpUtil.CONFIG_ISCOMMENT, retModel.adminLiveConfig.isComment);
                    SpUtil.getInstance().put(SpUtil.CONFIG_VIDEO_FEE, retModel.adminLiveConfig.isShortVideoFee);
                    SpUtil.getInstance().putModel(SpUtil.AdminVideoConfig, retModel.adminVideoConfig);
                    SpUtil.getInstance().put(SpUtil.CONFIG_CLOUD_TYPE, retModel.adminVideoConfig.cloudtype);
                    SpUtil.getInstance().put(SpUtil.CONFIG_USER_CANCEL, retModel.adminLiveConfig.userCancel);
                    SpUtil.getInstance().put(SpUtil.CONFIG_WITHDRAWAL_RULE, retModel.adminLiveConfig.withdrawalRule);
                    SpUtil.getInstance().put(SpUtil.CONFIG_VIPSTATESFEE, retModel.adminLiveConfig.VIPStatesFee);
                    SpUtil.getInstance().put(SpUtil.CONFIG_WX_APP_ID, retModel.wxAppId);
                    SpUtil.getInstance().put(SpUtil.CONFIG_XIEYI_RULE, retModel.adminLiveConfig.xieyiRule);
                    SpUtil.getInstance().put(SpUtil.CONFIG_BIND_PHONE, retModel.adminLiveConfig.isBindPhone);
                    SpUtil.getInstance().putModel(SpUtil.CONFIG_PAY_LIST, retModel.payConfigList);
                    if (retModel.customerServiceSetting != null) {
                        SpUtil.getInstance().put(SpUtil.CONFIG_HOT_LINE, retModel.customerServiceSetting.consumerHotline);
                        SpUtil.getInstance().put(SpUtil.CONFIG_HOT_QQ, retModel.customerServiceSetting.qq);
                        SpUtil.getInstance().put(SpUtil.CONFIG_HOT_WX, retModel.customerServiceSetting.wechat);
                        SpUtil.getInstance().put(SpUtil.CONFIG_HOT_WXCODE, retModel.customerServiceSetting.wechatCode);
                    }
                    SpUtil.getInstance().put(SpUtil.CONFIG_DEFAULT_SIGNATURE, retModel.loginSwitch.defaultSignature);

                    //注意：再次添加新的信息时，在SpUtil.java类的clear()方法中进行排除
                    if (retModel.liveKey.beautySwitch == 1) {
                        TiSDK.init(TTTConfigUtils.getInstance().getBeautyKey(), BaseApplication.getInstance());
                        ((BaseApplication) BaseApplication.getInstance()).initVideoCaptureAsync();
                    }
                    //私聊开关信息
                    SpUtil.getInstance().put(SpUtil.CONFIG_A_TO_A, retModel.adminLiveConfig.anchorToAnchor);
                    SpUtil.getInstance().put(SpUtil.CONFIG_U_TO_U, retModel.adminLiveConfig.userToUser);
                    SpUtil.getInstance().put(SpUtil.CONFIF_NOBLE_CHAT_FREE, retModel.adminLiveConfig.nobleChatFree);


                    HttpApiAppLogin.adslist(1, 0, new HttpApiCallBackArr<AppAds>() {
                        @Override
                        public void onHttpRet(int code, String msg, List<AppAds> retModel) {
                            if (code == 1 && null != retModel && !retModel.isEmpty()) {
//                                boolean isPic = retModel.get(0).thumb.contains("jpg") || retModel.get(0).thumb.contains("jpeg") || retModel.get(0).thumb.contains("png");
                                url = retModel.get(0).url;
//                                if (isPic) {
                                setAdvert(retModel.get(0).thumb);
//                                } else {
//                                    video.setVisibility(View.VISIBLE);
//                                    ivAdvert.setVisibility(View.GONE);
//                                    layoutAdvertSkip.setVisibility(View.VISIBLE);
//                                    Uri uri = Uri.parse(retModel.get(0).thumb);
//                                    video.setVideoURI(uri);
//                                    video.start();
//                                    final int second = video.getDuration() / 1000;
//                                    disposable = Flowable.interval(1000, TimeUnit.MILLISECONDS).take(second).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Long>() {
//                                        @Override
//                                        public void accept(Long aLong) throws Exception {
//                                            tvAdvertSkip.setText("跳过 (" + (second - aLong) + "s)");
//                                        }
//                                    }).doOnComplete(new Action() {
//                                        @Override
//                                        public void run() throws Exception {
//                                            goNext(true);
//                                        }
//                                    }).subscribe();
//                                }
                            } else {
                                goNext(true);
                            }
                        }
                    });
                } else {
                    //ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * 显示广告图
     */
    private void setAdvert(String thumb) {
        video.setVisibility(View.GONE);
        ivAdvert.setVisibility(View.VISIBLE);
        ImageLoader.display(thumb, ivAdvert, 0, 0, false, null, new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                goNext(true);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                return false;
            }
        }, null);

        if (LiveConstants.isJump){
            goNext(true);
            LiveConstants.isJump = false;
            return;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutAdvertSkip.setVisibility(View.VISIBLE);
                disposable = Flowable.interval(1000, TimeUnit.MILLISECONDS).take(6).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        long surplusTime = 4 - aLong;
                        if (surplusTime <= 0) {
                            tvAdvertSkip.setText("跳过 (0s)");
                        } else {
                            tvAdvertSkip.setText("跳过 (" + (4 - aLong) + "s)");
                        }
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        goNext(true);
                    }
                }).subscribe();
            }
        }, 1000);
    }

    /**
     * @param advertSkip 是否跳过广告
     */
    private void goNext(boolean advertSkip) {
        if (null != disposable) {
            disposable.dispose();
        }
        if (HttpClient.getUid() != 0 && !TextUtils.isEmpty(HttpClient.getToken())) {
            ApiUserInfo userInfo = SpUtil.getInstance().<ApiUserInfo>getModel("UserInfo", ApiUserInfo.class);
            if (userInfo == null) {
                ARouter.getInstance().build(ARouterPath.LoginActivity).navigation(BaseLauncherActivity.this, navigationCallback);
                if (!advertSkip && !TextUtils.isEmpty(url)) {
                    openAdvert();
                }
            } else {
                if (TextUtils.isEmpty(userInfo.mobile)) {
                    if ((int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_BIND_PHONE, 0) == 0) {
                        ARouter.getInstance().build(ARouterPath.LoginActivity).navigation(BaseLauncherActivity.this, navigationCallback);
                        if (!advertSkip && !TextUtils.isEmpty(url)) {
                            openAdvert();
                        }
                    } else if (TextUtils.isEmpty(userInfo.username) || (0 == userInfo.sex) || TextUtils.isEmpty(userInfo.avatar) || TextUtils.isEmpty(userInfo.birthday)) {
                        ARouter.getInstance().build(ARouterPath.LoginActivity).navigation(BaseLauncherActivity.this, navigationCallback);
                        if (!advertSkip && !TextUtils.isEmpty(url)) {
                            openAdvert();
                        }
                    } else {
                        ARouter.getInstance().build(ARouterPath.MainActivity).withParcelable(ARouterValueNameConfig.ApiUserInfo, userInfo).navigation(BaseLauncherActivity.this, navigationCallback);
                        if (!advertSkip && !TextUtils.isEmpty(url)) {
                            openAdvert();
                        }
                    }
                } else if (TextUtils.isEmpty(userInfo.username) || (0 == userInfo.sex) || TextUtils.isEmpty(userInfo.avatar) || TextUtils.isEmpty(userInfo.birthday)) {
                    ARouter.getInstance().build(ARouterPath.LoginActivity).navigation(BaseLauncherActivity.this, navigationCallback);
                    if (!advertSkip && !TextUtils.isEmpty(url)) {
                        openAdvert();
                    }
                } else {
                    ARouter.getInstance().build(ARouterPath.MainActivity).withParcelable(ARouterValueNameConfig.ApiUserInfo, userInfo).navigation(BaseLauncherActivity.this, navigationCallback);
                    if (!advertSkip && !TextUtils.isEmpty(url)) {
                        openAdvert();
                    }
                }
            }
        } else {
            ARouter.getInstance().build(ARouterPath.LoginActivity).navigation(BaseLauncherActivity.this, navigationCallback);
            if (!advertSkip && !TextUtils.isEmpty(url)) {
                openAdvert();
            }
        }
    }

    /**
     * 打开广告
     */
    private void openAdvert() {
        if ((int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_BANNER_JUMP_MODE, 0) == 0) {
            ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, url).navigation();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    WebUtil.goExternalWeb(BaseLauncherActivity.this, url);
                }
            }, 200);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTokenInvalidEvent(TokenInvalidEvent e) {
        ARouter.getInstance().build(ARouterPath.LoginActivity).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).
                navigation(BaseLauncherActivity.this, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {

                    }

                    @Override
                    public void onLost(Postcard postcard) {

                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        finish();
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {

                    }
                });
    }

    @Override
    protected void setActivityBackgroundResource() {
//        super.setActivityBackgroundResource();
    }
}