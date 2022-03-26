package com.kalacheng.live.component.componentlive;


import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.MediaController;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.base.BaseMVVMViewHolder;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.buscommon.model.ApiUserBasicInfo;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buscommon.model.GuardUserDto;
import com.kalacheng.buscommon.socketmsg.IMRcvCommonMsgSend;
import com.kalacheng.busdynamiccircle.socketmsg.IMRcvDynamiccircleSend;
import com.kalacheng.busgraderight.httpApi.HttpApiNoble;
import com.kalacheng.busgraderight.socketmsg.IMRcvGradeRightMsgSender;
import com.kalacheng.buslive.httpApi.HttpApiHttpLive;
import com.kalacheng.buslive.socketcontroller.IMApiLive;
import com.kalacheng.buslive.socketcontroller.IMApiLiveAnchorPk;
import com.kalacheng.buslive.socketcontroller.IMApiLiveUserLine;
import com.kalacheng.buslivebas.socketmsg.IMRcvLiveMsgSend;
import com.kalacheng.buslivebas.socketmsg.IMRcvLiveSend;
import com.kalacheng.busliveplugin.socketmsg.IMRcvLiveWishSend;
import com.kalacheng.busnobility.model.ApiLightSender;
import com.kalacheng.busnobility.socketcontroller.IMApiLiveGift;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.buspersonalcenter.socketmsg.IMRcvUserMsgSender;
import com.kalacheng.busshop.httpApi.HttpApiShopBusiness;
import com.kalacheng.busshop.model.ShopLiveInfoDTO;
import com.kalacheng.commonview.utils.GifCacheUtil;
import com.kalacheng.commonview.utils.IconUtil;
import com.kalacheng.commonview.utils.LiveMusicView;
import com.kalacheng.commonview.utils.LookRoomUtlis;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.Constants;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiAnchorWishList;
import com.kalacheng.libuser.model.ApiBeautifulNumber;
import com.kalacheng.libuser.model.ApiCloseLine;
import com.kalacheng.libuser.model.ApiCloseLive;
import com.kalacheng.libuser.model.ApiElasticFrame;
import com.kalacheng.libuser.model.ApiExitRoom;
import com.kalacheng.libuser.model.ApiGiftSender;
import com.kalacheng.libuser.model.ApiJoinRoomAnchor;
import com.kalacheng.libuser.model.ApiKickLive;
import com.kalacheng.libuser.model.ApiLeaveRoom;
import com.kalacheng.libuser.model.ApiLeaveRoomAnchor;
import com.kalacheng.libuser.model.ApiLinkEntity;
import com.kalacheng.libuser.model.ApiSendMsgRoom;
import com.kalacheng.libuser.model.ApiSendPKMsgRoom;
import com.kalacheng.libuser.model.ApiSendVideoUnReadNumber;
import com.kalacheng.libuser.model.ApiShopLiveGoods;
import com.kalacheng.libuser.model.ApiSimpleMsgRoom;
import com.kalacheng.libuser.model.ApiTimerExitRoom;
import com.kalacheng.libuser.model.ApiUserLineRoom;
import com.kalacheng.libuser.model.ApiUserSeats;
import com.kalacheng.libuser.model.ApiUsersLiveWish;
import com.kalacheng.libuser.model.ApiUsersVoiceAssistan;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.libuser.model.UserBuyDTO;
import com.kalacheng.libuser.model.VipPrivilegeDto;
import com.kalacheng.live.R;
import com.kalacheng.live.component.fragment.LiveLinkMicListDialogFragment;
import com.kalacheng.live.component.view.LiveLightView;
import com.kalacheng.live.component.viewmodel.LiveCommonViewModel;
import com.kalacheng.live.databinding.ViewLiveRoomBinding;
import com.kalacheng.livecommon.adapter.InfoGuardListAdpater;
import com.kalacheng.livecommon.adapter.LiveUserAdapter;
import com.kalacheng.livecommon.fragment.GiveBeautifulNumberDialogFragment;
import com.kalacheng.shop.socketmsg.IMRcvShopMsgSend;
import com.kalacheng.shortvideo.socketmsg.IMRcvShortVideoSend;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.CommonCallback;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.KeyBoardHeightUtil;
import com.kalacheng.util.utils.PermissionsUtil;
import com.kalacheng.util.utils.RandomUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.ItemDecoration;
import com.makeramen.roundedimageview.RoundedImageView;
import com.klc.bean.SendGiftPeopleBean;
import com.wengying666.imsocket.IMApiCallBack;
import com.wengying666.imsocket.IMUtil;
import com.wengying666.imsocket.SocketClient;
import com.xuantongyun.livecloud.protocol.PulicUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import pl.droidsonroids.gif.GifDrawable;


public class LiveInfoComponent extends BaseMVVMViewHolder<ViewLiveRoomBinding, LiveCommonViewModel>
        implements View.OnClickListener, KeyBoardHeightUtil.KeyBoardHeightChangeListener, LiveBundle.onLiveSocket {

    private IMApiLiveUserLine imApiLiveUserLine;
    private IMApiLiveAnchorPk imApiLiveAnchorPk;
    private IMApiLive imApiLive;
    private IMApiLiveGift imApiLiveGift;
    private SocketClient mSocket;
    private boolean isLinkMic = false;
    public long toUid;
    private GiveBeautifulNumberDialogFragment dialogGiveFulNumber;
    private Disposable timeDisposable;
    private LiveUserAdapter liveUserAdapter;
    private int mStartX;
    private int mStartY;
    private PathMeasure[] mPathMeasures;
    private List<LiveLightView> mViewList;
    private TimeInterpolator mInterpolator;
    private boolean mEnd;
    private boolean isFirstLight = true;
    protected int mCountDownCount = 3;
    private boolean mIsAnimating;//是否在执行动画
    private CommonCallback<File> mDownloadGifCallback;
    private GifDrawable mGifDrawable;
    private MediaController mMediaController;//koral--/android-gif-drawable 这个库用来播放gif动画的
    private ConcurrentLinkedQueue<AppJoinRoomVO> mQueue;
    private Handler mHandler;
    private boolean mShowGif;
    private ObjectAnimator mBgAnimator1;
    private ObjectAnimator mBgAnimator2;
    private ObjectAnimator mBgAnimator3;
    private ObjectAnimator mUserAnimator1;
    private ObjectAnimator mUserAnimator2;
    private ObjectAnimator mUserAnimator3;
    private Animation mStarAnim;
    private int mDp500;
    public static int isFollow;
    public int isGruad;
    private int guardNumber;

    private String GroupName;

    //是否静音
    private boolean muteAllRemote = false;

    private PermissionsUtil mProcessResultUtil;

    //连麦，互动，PK三种类型申请都添加控制事件，不等到对方回复不许连续点击，
    private boolean isRequestMick = false;
    private boolean isRequestPK = false;

    //直播购保存讲解的数据
    private ApiShopLiveGoods apiShopLiveGoods;

    private int freeWatchTime = 0; // 房间试看时间(秒),0标识没有限制

    public LiveInfoComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_live_room;
    }

    /**
     * 自己是否是 直播间的贵宾
     * true 是 贵宾
     */
    private boolean isMyVip = false;

    @Override
    protected void init() {

        //初始化  自己是否为 判断的默认值
        isMyVip = false;

        imApiLive = new IMApiLive();
        imApiLiveUserLine = new IMApiLiveUserLine();
        imApiLiveAnchorPk = new IMApiLiveAnchorPk();
        imApiLiveGift = new IMApiLiveGift();
        mQueue = new ConcurrentLinkedQueue<>();

        muteAllRemote = false;
        binding.setIsAnchor(LiveConstants.IDENTITY == LiveConstants.IDENTITY.ANCHOR);
        if (LiveConstants.IDENTITY == LiveConstants.IDENTITY.ANCHOR) {
            binding.btnFunction.setImageResource(R.mipmap.icon_live_func_0);

//            binding.tvMic.setText(WordUtil.getString(R.string.mic_enable));
            //控制底部按钮显示
        } else {
            addToParent();
            binding.btnFunction.setImageResource(R.mipmap.icon_live_gift);

//            binding.tvMic.setText(WordUtil.getString(R.string.live_link_mic_2));

            //控制底部按钮显示


            binding.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isFirstLight) {
                        imApiLiveGift.sendLight((int) LiveConstants.ANCHORID, viewModel.apiJoinRoom.get().liveType, new IMApiCallBack<ApiLightSender>() {
                            @Override
                            public void onImRet(int code, String errMsg, ApiLightSender retModel) {
                                if (retModel != null && retModel.msg != null)
                                    Log.e("sendLight>>>", "retModel.msg");
                            }
                        });
                        isFirstLight = false;
                    } else {
                        play();
                    }
                }
            });
        }
        initListener();
        initLight();
        initAnim();
        getNobleBarrage();
    }

    private void initAnim() {
        mDp500 = DpUtil.dp2px(500);
        Interpolator interpolator1 = new AccelerateDecelerateInterpolator();
        Interpolator interpolator2 = new LinearInterpolator();
        Interpolator interpolator3 = new AccelerateInterpolator();
        mBgAnimator1 = ObjectAnimator.ofFloat(binding.ivJinguang, "translationX", DpUtil.dp2px(70));
        mBgAnimator1.setDuration(1000);
        mBgAnimator1.setInterpolator(interpolator1);

        mBgAnimator2 = ObjectAnimator.ofFloat(binding.ivJinguang, "translationX", 0);
        mBgAnimator2.setDuration(700);
        mBgAnimator2.setInterpolator(interpolator2);

        mBgAnimator3 = ObjectAnimator.ofFloat(binding.ivJinguang, "translationX", -mDp500);
        mBgAnimator3.setDuration(300);
        mBgAnimator3.setInterpolator(interpolator3);

        mUserAnimator1 = ObjectAnimator.ofFloat(binding.rlJinguang, "translationX", DpUtil.dp2px(70));
        mUserAnimator1.setDuration(1000);
        mUserAnimator1.setInterpolator(interpolator1);
        mUserAnimator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mBgAnimator2.start();
                mUserAnimator2.start();
            }
        });

        mUserAnimator2 = ObjectAnimator.ofFloat(binding.rlJinguang, "translationX", 0);
        mUserAnimator2.setDuration(700);
        mUserAnimator2.setInterpolator(interpolator2);
        mUserAnimator2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                binding.star.startAnimation(mStarAnim);
            }
        });

        mUserAnimator3 = ObjectAnimator.ofFloat(binding.rlJinguang, "translationX", mDp500);
        mUserAnimator3.setDuration(450);
        mUserAnimator3.setInterpolator(interpolator3);
        mUserAnimator3.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                binding.ivJinguang.setTranslationX(mDp500);
                binding.rlJinguang.setTranslationX(-mDp500);
                if (!mShowGif) {
                    getNextGif();
                }
            }
        });
        mStarAnim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mStarAnim.setDuration(1500);
        mStarAnim.setInterpolator(interpolator2);
        mStarAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBgAnimator3.start();
                mUserAnimator3.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initListener() {
        mProcessResultUtil = new PermissionsUtil((FragmentActivity) mContext);
        liveUserAdapter = new LiveUserAdapter();
        binding.recyclerViewUser.setAdapter(liveUserAdapter);
        LiveBundle.getInstance().addLiveSocketListener(this);
        binding.btnClose.setOnClickListener(this);
        binding.btnFunction.setOnClickListener(this);
        binding.btnPk.setOnClickListener(this);
//        binding.llMic.setOnClickListener(this);
        binding.ivAvatar.setOnClickListener(this);
        binding.btnChat.setOnClickListener(this);
        binding.llGuard.setOnClickListener(this);
        binding.btnShare.setOnClickListener(this);
        binding.recyclerViewUser.setHasFixedSize(true);
        binding.tvWatchnum.setOnClickListener(this);
        binding.btnMore.setOnClickListener(this);
        binding.btnGift.setOnClickListener(this);
        binding.btnVoice.setOnClickListener(this);
        binding.tvVotes.setOnClickListener(this);
        binding.VoiceLiveAnchorHead.setOnClickListener(this);

        binding.btnUserguizu.setOnClickListener(this);
        binding.btnUsergame.setOnClickListener(this);
        binding.btnConnectMik.setOnClickListener(this);
        binding.btnFollow.setOnClickListener(this);
        binding.btnShopList.setOnClickListener(this);
        binding.btnShopStore.setOnClickListener(this);
        binding.btnShopActivity.setOnClickListener(this);
        binding.liveShopGoods.setOnClickListener(this);

        binding.llId.setOnClickListener(this);

        binding.recyclerViewUser.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ExitRoom, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                clear();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_HttpCloseLive, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, null);
                ARouter.getInstance().build(ARouterPath.LiveEndActivity).withParcelable("ApiJoinRoom", viewModel.apiJoinRoom.get()).withParcelable("ApiCloseLive", (ApiCloseLive) o).navigation();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OpenLiveMsg, new MsgListener.SimpleMsgListener<AppJoinRoomVO>() {
            @Override
            public void onMsg(AppJoinRoomVO apiJoinRoom) {
                addToParent();
                if (mCountDownCount == 3) {
                    startCountDown();
                }
            }

            @Override
            public void onTagMsg(String tag, AppJoinRoomVO apiJoinRoom) {

            }
        });

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_StartInteraction, new MsgListener.SimpleMsgListener<ApiUserLineRoom>() {
            @Override
            public void onMsg(ApiUserLineRoom apiUserLineRoom) {
                if (apiUserLineRoom.status == 1 && LiveConstants.IDENTITY == LiveConstants.IDENTITY.ANCHOR) {
                    toUid = apiUserLineRoom.toUid;
                }
            }

            @Override
            public void onTagMsg(String tag, ApiUserLineRoom apiUserLineRoom) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.RoomInfoList, new MsgListener.SimpleMsgListener<AppJoinRoomVO>() {
            @Override
            public void onMsg(final AppJoinRoomVO apiJoinRoom) {
                if (null != apiJoinRoom) {
                    if (HttpClient.getUid() == LiveConstants.ANCHORID) {
                        // 刷新直播
                        binding.userLin.setVisibility(View.GONE);
                        if (apiJoinRoom.liveFunction == 1) {
                            binding.btnShopList.setVisibility(View.VISIBLE);
                            binding.btnShopStore.setVisibility(View.GONE);
                            binding.btnShopActivity.setVisibility(View.GONE);
                            binding.btnConnectMik.setVisibility(View.VISIBLE);
                            binding.anchorLin.setVisibility(View.GONE);
                            binding.anchorLin.setVisibility(View.VISIBLE);
                            binding.btnVoice.setVisibility(View.GONE);
                            binding.btnSkill.setVisibility(View.GONE);
                            binding.btnFunction.setVisibility(View.GONE);
                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveGoodsList, null);
                        } else {
                            binding.btnShopList.setVisibility(View.GONE);
                            binding.btnShopStore.setVisibility(View.GONE);
                            binding.btnShopActivity.setVisibility(View.GONE);
                            binding.btnConnectMik.setVisibility(View.VISIBLE);
                            binding.anchorLin.setVisibility(View.VISIBLE);
                            binding.btnVoice.setVisibility(View.VISIBLE);
                        }
                    } else {
                        binding.btnConnectMik.setVisibility(View.VISIBLE);
                        binding.anchorLin.setVisibility(View.GONE);
                        binding.btnShopStore.setVisibility(View.GONE);
                        binding.btnShopActivity.setVisibility(View.GONE);
                        if (apiJoinRoom.liveFunction == 1) {
                            binding.btnShopList.setVisibility(View.VISIBLE);

                            getLiveShopInformation();
                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveGoodsList, 2);
                        } else {
                            binding.btnShopList.setVisibility(View.GONE);
                        }
                    }

                    binding.getViewModel().apiJoinRoom.set(apiJoinRoom);
                    binding.setVotes(((int) apiJoinRoom.votes) + "");
                    guardNumber = apiJoinRoom.guardNumber;
                    binding.setGuardNum(guardNumber + "");
                    if (null == apiJoinRoom.anchorGoodNum || TextUtils.isEmpty(apiJoinRoom.anchorGoodNum)) {
                        binding.tvNameId.setText("");
                        binding.setLiveId(apiJoinRoom.anchorId + "");
                    } else {
                        binding.tvNameId.setText("");
                        binding.setLiveId(apiJoinRoom.anchorGoodNum + "");
                    }
                    isFollow = apiJoinRoom.isFollow;
                    isGruad = apiJoinRoom.isSh;

                    if (LiveConstants.ANCHORID != HttpClient.getUid()) {
                        binding.btnFollow.setVisibility(View.VISIBLE);
                        if (isFollow == 0) {
                            binding.btnFollow.setBackgroundResource(R.mipmap.follow_icon);
                        } else {
                            binding.btnFollow.setBackgroundResource(R.mipmap.followed_icon);
                        }
                    } else {
                        binding.btnFollow.setVisibility(View.GONE);
                    }


                    if (null != apiJoinRoom.userList) {
                        liveUserAdapter.setliveBean(apiJoinRoom.userList);
                    }

                    liveUserAdapter.setOnItemClickCallback(new OnBeanCallback<ApiUserBasicInfo>() {
                        @Override
                        public void onClick(ApiUserBasicInfo basicInfo) {
                            //查看用户信息
                            LiveConstants.TOUID = basicInfo.uid;
                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.Information, null);
                        }
                    });


                    //是否加入贵宾席0未加入1加入

                    //加载 直播间 此时的实体是 操作者自己的实体（观众 在这个地方 判断自己 是不是 贵宾）
                    //非主播
                    if (viewModel.apiJoinRoom.get().role != 3 && viewModel.apiJoinRoom.get().isJoinSeats == 1) {
                        isMyVip = true;
                    }

                    //贵宾席
                    if (ConfigUtil.getBoolValue(R.bool.showVIPSeats)) {
                        binding.VoiceLiveVip.setVisibility(View.VISIBLE);
                        if (viewModel.apiJoinRoom.get().role != 3) {
                            ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
                            if (viewModel.apiJoinRoom.get().isJoinSeats == 1 || (apiUserInfo != null && apiUserInfo.vipType == 1)) {
                                isMyVip = true;
                            }
                            if (isMyVip && apiUserInfo != null) {
                                ImageLoader.display(apiUserInfo.avatar, binding.VoiceLiveAnchorHead, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                            }
                        }
                    } else {
                        binding.VoiceLiveVip.setVisibility(View.GONE);
                    }
                }
                getWish();
                getGuradList();

                freeWatchTime = viewModel.apiJoinRoom.get().freeWatchTime;

                //开播计时
                if (apiJoinRoom.anchorId != HttpClient.getUid()) {
                    timeDisposable = Observable.interval(1000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            if (aLong == 120 && isFollow == 0) {
                                // 120秒时弹出 用户关注
                                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_UserFollew, null);
                            } else if (aLong == 180 && isGruad == 0) {
                                // 120秒时弹出 加入粉丝团
                                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_TipsAddFansGroup, null);
                            }
                            if (freeWatchTime > 0) {
                                binding.llTimer.setVisibility(View.VISIBLE);
                                freeWatchTime--;
                                binding.tvTimer.setText(freeWatchTime + "");
                                if (freeWatchTime == 0) {
                                    // 倒计时结束 弹出弹框
                                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_Deduction, 1);
                                    binding.llTimer.setVisibility(View.GONE);
                                }
                            }
                            if (aLong >= 180){
                                if (null != timeDisposable)
                                    timeDisposable.dispose();
                            }
                        }
                    });
                }

                //主播的状态
                if (apiJoinRoom.liveStatus == 1) {
                    isLinkMic = false;
                } else {
                    isLinkMic = true;
                }

            }

            @Override
            public void onTagMsg(String tag, AppJoinRoomVO apiJoinRoom) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ReceiveStartLinkMic, new MsgListener.SimpleMsgListener<ApiUserLineRoom>() {
            @Override
            public void onMsg(ApiUserLineRoom apiUserLineRoom) {
                if (LiveConstants.IDENTITY == LiveConstants.IDENTITY.AUDIENCE) {
                    isLinkMic = true;
                    if (apiUserLineRoom.uid == HttpClient.getUid()) {
                        binding.root.setClickable(false);
                    }
                }
            }

            @Override
            public void onTagMsg(String tag, ApiUserLineRoom apiUserLineRoom) {
            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LaunchCloseLinkMic, new MsgListener.SimpleMsgListener<ApiCloseLine>() {
            @Override
            public void onMsg(ApiCloseLine apiCloseLine) {
                if (LiveConstants.IDENTITY == LiveConstants.IDENTITY.AUDIENCE) {
                    isLinkMic = false;
                    if (apiCloseLine.uid == HttpClient.getUid()) {
                        binding.root.setClickable(true);
                    }
                }
            }

            @Override
            public void onTagMsg(String tag, ApiCloseLine apiCloseLine) {
            }
        });

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ReceiveCloseInteraction, new MsgListener.SimpleMsgListener<ApiCloseLine>() {
            @Override
            public void onMsg(ApiCloseLine apiCloseLine) {
//                binding.btnPk.setVisibility(View.GONE);
            }

            @Override
            public void onTagMsg(String tag, ApiCloseLine apiCloseLine) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_MessageForGift, new MsgListener.SimpleMsgListener<ApiGiftSender>() {
            @Override
            public void onMsg(ApiGiftSender apiGiftSender) {
                binding.setVotes(((int) apiGiftSender.votes) + "");
            }

            @Override
            public void onTagMsg(String tag, ApiGiftSender apiGiftSender) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_PKStart, new MsgListener.SimpleMsgListener<ApiSendPKMsgRoom>() {
            @Override
            public void onMsg(ApiSendPKMsgRoom apiSendPKMsgRoom) {
            }

            @Override
            public void onTagMsg(String tag, ApiSendPKMsgRoom apiSendPKMsgRoom) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_PKFinish, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_GuardNumber, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                binding.setGuardNum(++guardNumber + "");
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //用户关注成功
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_UserFollewSusser, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                isFollow = 1;
                binding.btnFollow.setBackgroundResource(R.mipmap.followed_icon);
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //加入粉丝团成功
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_AddFansGroupSusser, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                isGruad = 1;
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //连麦请求控制
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_IsRequestMick, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                isRequestMick = false;
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //pk请求控制
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_IsRequestPK, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                isRequestPK = false;
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        // 切换麦克风状态
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OPEN_CLOSE_MIC, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                try {
                    muteAllRemote = (boolean) o;
                    if (muteAllRemote) {
                        //关闭
                        binding.btnVoice.setBackgroundResource(R.mipmap.anchor_voice_open);
                        PulicUtils.getInstance().muteLocalAudioStream(true);
                    } else {
                        //开启
                        binding.btnVoice.setBackgroundResource(R.mipmap.anchor_voice_close);
                        PulicUtils.getInstance().muteLocalAudioStream(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });


       /* TTTcloud.getInstance().setOnQualityListener(new TTTcloud.onQualityListener() {
            @Override
            public void onQualityBad() {
                if (LiveConstants.IDENTITY == LiveConstants.IDENTITY.ANCHOR)
                    binding.tvNetbad.setVisibility(View.VISIBLE);
            }

            @Override
            public void onQualityDwon() {
                if (LiveConstants.IDENTITY == LiveConstants.IDENTITY.ANCHOR)
                    binding.tvNetbad.setVisibility(View.VISIBLE);
            }

            @Override
            public void onQualityGood() {
                if (LiveConstants.IDENTITY == LiveConstants.IDENTITY.ANCHOR)
                    binding.tvNetbad.setVisibility(View.GONE);
            }
        });*/
        mDownloadGifCallback = new CommonCallback<File>() {
            @Override
            public void callback(File file) {
                if (file != null) {
                    playGif(file);
                }
            }
        };
        mHandler = new MyHandler(this);
    }

    private int i = 0;

    static class MyHandler extends Handler {
        WeakReference<LiveInfoComponent> mActivityReference;

        MyHandler(LiveInfoComponent liveInfoComponent) {
            mActivityReference = new WeakReference<LiveInfoComponent>(liveInfoComponent);
        }

        @Override
        public void handleMessage(Message msg) {
            final LiveInfoComponent liveInfoComponent = mActivityReference.get();
            if (liveInfoComponent != null) {
                liveInfoComponent.mShowGif = false;
//                if (liveInfoComponent.binding.tvWord != null) {
//                    liveInfoComponent.binding.tvWord.setText("");
//                }
                if (liveInfoComponent.mMediaController != null) {
                    liveInfoComponent.mMediaController.hide();
                }
                if (liveInfoComponent.binding.enterRoomGif != null) {
                    liveInfoComponent.binding.enterRoomGif.setImageDrawable(null);
                }
                if (liveInfoComponent.mGifDrawable != null && !liveInfoComponent.mGifDrawable.isRecycled()) {
                    liveInfoComponent.mGifDrawable.stop();
                    liveInfoComponent.mGifDrawable.recycle();
                }
                liveInfoComponent.getNextGif();
            }
        }
    }

    private void initLight() {
        mStartX = DpUtil.getScreenWidth() * 4 / 5;
        mStartY = DpUtil.getScreenHeight() - DpUtil.dp2px(50);
        mPathMeasures = new PathMeasure[6];
        int dp50 = DpUtil.dp2px(50);
        int dp100 = DpUtil.dp2px(100);
        int dp200 = DpUtil.dp2px(200);
        int dp300 = DpUtil.dp2px(300);
        //第1条轨迹
        Path path = new Path();
        path.lineTo(0, 0);
        path.moveTo(mStartX, mStartY);
        path.rCubicTo(0, -dp100, -dp100, -dp200, -dp100, -dp300);
        mPathMeasures[0] = new PathMeasure(path, false);

        //第2条轨迹
        path = new Path();
        path.lineTo(0, 0);
        path.moveTo(mStartX, mStartY);
        path.rCubicTo(0, -dp200, -dp50, -dp200, -dp50, -dp300);
        mPathMeasures[1] = new PathMeasure(path, false);

        //第3条轨迹
        path = new Path();
        path.lineTo(0, 0);
        path.moveTo(mStartX, mStartY);
        path.rCubicTo(dp100, -dp100, 0, -dp300, -dp50, -dp300);
        mPathMeasures[2] = new PathMeasure(path, false);

        //第4条轨迹
        path = new Path();
        path.lineTo(0, 0);
        path.moveTo(mStartX, mStartY);
        path.rCubicTo(0, -dp100, dp100, -dp200, 0, -dp300);
        mPathMeasures[3] = new PathMeasure(path, false);

        //第5条轨迹
        path = new Path();
        path.lineTo(0, 0);
        path.moveTo(mStartX, mStartY);
        path.rCubicTo(0, -dp200, dp50, -dp200, dp50, -dp300);
        mPathMeasures[4] = new PathMeasure(path, false);

        //第6条轨迹
        path = new Path();
        path.lineTo(0, 0);
        path.moveTo(mStartX, mStartY);
        path.rCubicTo(-dp100, -dp100, 0, -dp300, dp50, -dp300);
        mPathMeasures[5] = new PathMeasure(path, false);
        mViewList = new ArrayList<>();
        mInterpolator = new AccelerateDecelerateInterpolator();
    }

    private void getNextGif() {
        if (mQueue == null) {
            return;
        }
        AppJoinRoomVO bean = mQueue.poll();
        if (bean == null) {
            mIsAnimating = false;
        } else {
//            enterAnim(bean);
        }
    }

    public void enterAnim(AppJoinRoomVO bean) {
//        if (bean.isVip == 1) {
////            ImageLoader.displayRound(bean.getUserBean().getAvatar(), mAvatar);
//            TextRender.renderEnterRoom(binding.tvJgName, bean);
//            mBgAnimator1.start();
//            mUserAnimator1.start();
//        }
        if (mIsAnimating) {
            mQueue.offer(bean);
        } else {
            if (null != bean.carSwf && !TextUtils.isEmpty(bean.carSwf)) {
                String[] splitStr = bean.carSwf.split("/");
                String gifStr = splitStr[splitStr.length - 1];
                String[] splitStr2 = gifStr.split("\\.");
                String gifId = splitStr2[0];
//                binding.tvWord.setText(bean.userName + "骑着"+bean.carName+"进入了直播间");
                GifCacheUtil.getFile(Constants.Mounts + gifId, bean.carSwf, mDownloadGifCallback);
            }
        }
    }

    /**
     * 播放gif
     */
    private void playGif(File file) {
        mShowGif = true;
        if (mEnd) {
            return;
        }
        try {
            mGifDrawable = new GifDrawable(file);
            mGifDrawable.setLoopCount(1);
//            resizeGifImageView(mGifDrawable);
            binding.enterRoomGif.setImageDrawable(mGifDrawable);
            if (mMediaController == null) {
                mMediaController = new MediaController(mContext);
                mMediaController.setVisibility(View.GONE);
            }
            mMediaController.setMediaPlayer((GifDrawable) binding.enterRoomGif.getDrawable());
            mMediaController.setAnchorView(binding.enterRoomGif);
            int duration = mGifDrawable.getDuration();
            mMediaController.show(duration);
            if (duration < 4000) {
                duration = 4000;
            }
            if (mHandler != null) {
                mHandler.sendEmptyMessageDelayed(0, duration);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (mHandler != null) {
                mHandler.sendEmptyMessageDelayed(0, 4000);
            }
        }
    }

    @Override
    public void onClick(final View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        int i = view.getId();
        if (i == R.id.btn_close) { // 退出
            if (LiveConstants.IDENTITY == LiveConstants.IDENTITY.ANCHOR) {
                DialogUtil.showSimpleDialog(mContext, null, "当前有" + viewModel.apiJoinRoom.get().userList.size() + "位观众正在看你表演，是否结束直播？", true, new DialogUtil.SimpleCallback() {
                    @Override
                    public void onConfirmClick() {
                        HttpApiHttpLive.closeLive(LiveConstants.ROOMID, new HttpApiCallBack<ApiCloseLive>() {
                            @Override
                            public void onHttpRet(int code, String msg, ApiCloseLive retModel) {
                                if (code == 1 && null != retModel && retModel.code != 7201) {
                                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_HttpCloseLive, retModel);
                                } else if (null != retModel && retModel.code == 7201) {
                                    // 时间未到 禁止关播
                                    ToastUtil.show("" + retModel.msg);
                                } else {
                                    ApiCloseLive closeLive = new ApiCloseLive();
                                    closeLive.anchorId = LiveConstants.ANCHORID;
                                    closeLive.addFansGroup = 0;
                                    closeLive.addFollow = 0;
                                    closeLive.anchorName = viewModel.apiJoinRoom.get().anchorName;
                                    closeLive.avatar = viewModel.apiJoinRoom.get().anchorAvatar;
                                    closeLive.nums = liveUserAdapter.getList().size();
                                    closeLive.rewardNumber = 0;
                                    closeLive.roomId = LiveConstants.ROOMID;
                                    closeLive.votes = Double.parseDouble(binding.tvVotes.getText().toString());
                                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_HttpCloseLive, closeLive);
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
                LookRoomUtlis.getInstance().closeLive();
            }
        } else if (i == R.id.btn_share) {
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LiveShare, null);
        } else if (i == R.id.ll_id) {
            dialogGiveFulNumber = new GiveBeautifulNumberDialogFragment();
            dialogGiveFulNumber.getApiJoinRoom(viewModel.apiJoinRoom.get());
            dialogGiveFulNumber.show(((FragmentActivity) mContext).getSupportFragmentManager(), "GiveBeautifulNumberDialogFragment");
        } else if (i == R.id.btn_function) {

//
            /*if (LiveConstants.IDENTITY == LiveConstants.IDENTITY.ANCHOR) {
                dialogFunction = new LiveFunctionDialogFragment();
                dialogFunction.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveFunctionDialogFragment");
            } else {
                LiveGiftDialogFragment dialogFragment = new LiveGiftDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(ARouterValueNameConfig.Livetype, viewModel.apiJoinRoom.get().type);
                bundle.putString(ARouterValueNameConfig.ShowID, viewModel.apiJoinRoom.get().showid);
                dialogFragment.setArguments(bundle);
                dialogFragment.initSocket(mSocket);
                dialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveGiftDialogFragment");
            }*/
//                ARouter.getInstance().build(ARouterPath.LiveFunctionDialogFragment).navigation();AS
        } else if (i == R.id.btn_pk) {
//            if (!isRequestPK){
            imApiLiveAnchorPk.invitationAnchorLinePK(toUid, new IMApiCallBack<ApiLinkEntity>() {
                @Override
                public void onImRet(int i, String s, ApiLinkEntity apiLinkEntity) {
                    if (i == 1) {
                        ToastUtil.show(s);
                        isRequestPK = true;
                    } else {
                        ToastUtil.show(s);
                    }
                }
            });
//            }else {
//                ToastUtil.show("正在pk");
//            }

        } else if (i == R.id.iv_avatar) {//查看主播信息
            LiveConstants.TOUID = LiveConstants.ANCHORID;
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.Information, null);
        } else if (i == R.id.btn_chat) {
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OpenChat, null);
        } else if (i == R.id.ll_guard) {
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveGuard, null);

        } else if (i == R.id.btn_follow) {
            if (LiveConstants.IDENTITY == LiveConstants.IDENTITY.ANCHOR) {

            } else {
                if (isFollow == 0) {
                    HttpApiAppUser.set_atten(1, LiveConstants.ANCHORID, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            if (code == 1) {
                                isFollow = 1;
                                binding.btnFollow.setBackgroundResource(R.mipmap.followed_icon);
                            }
                        }
                    });

                } else {
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_AddFansGroup, null);
                }
            }

        } else if (i == R.id.tv_watchnum) {//查看人数
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LookNumber, 1);
        } else if (i == R.id.btn_more) {//查看更多
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveMore, viewModel.apiJoinRoom.get().liveFunction);
        } else if (i == R.id.btn_gift) {//赠送礼物
            SendGiftPeopleBean bean = new SendGiftPeopleBean();
            bean.name = viewModel.apiJoinRoom.get().anchorName;
            bean.headimage = viewModel.apiJoinRoom.get().anchorAvatar;
            bean.uid = viewModel.apiJoinRoom.get().anchorId;
            bean.showid = viewModel.apiJoinRoom.get().showid;
            bean.roomID = viewModel.apiJoinRoom.get().roomId;
            bean.liveType = viewModel.apiJoinRoom.get().liveType;
            bean.anchorID = viewModel.apiJoinRoom.get().anchorId;
            bean.shortVideoId = -1;
            List<SendGiftPeopleBean> beanList = new ArrayList<>();
            beanList.add(bean);
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_SendGift, beanList);
        } else if (i == R.id.btn_connect_mik) {//连麦
            if (!isRequestMick) {
                mProcessResultUtil.requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                }, new Runnable() {
                    @Override
                    public void run() {
                        if (LiveConstants.IDENTITY == LiveConstants.IDENTITY.ANCHOR) {
                            LiveLinkMicListDialogFragment fragment = new LiveLinkMicListDialogFragment();
                            fragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveLinkMicListDialogFragment");
                        } else {
                            if (!isLinkMic) {
                                imApiLiveUserLine.invitationUserLineReq(LiveConstants.ANCHORID, new IMApiCallBack<ApiLinkEntity>() {
                                    @Override
                                    public void onImRet(int i, String s, ApiLinkEntity apiLinkEntity) {
                                        if (i == 1) {
                                            LiveConstants.LinkSessionID = apiLinkEntity.sessionID;
                                            ToastUtil.show("邀请中，请稍后");
                                            isRequestMick = true;
                                        } else {
                                            ToastUtil.show(s);
                                        }
                                    }
                                });
                            } else {
                                ToastUtil.show("主播正在连麦");
                            }
                        }
                    }
                });

            } else {
                ToastUtil.show("等待对方的回复");

            }


        } else if (i == R.id.btn_userguizu) {//贵族
            if (CheckDoubleClick.isFastDoubleClick()) {
                return;
            }
            ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                    + HttpConstants.URL_NOBLE + "_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken()).navigation();
        } else if (i == R.id.btn_usergame) {//游戏

        } else if (i == R.id.btn_voice) {  //静音
            isVoice();
        } else if (i == R.id.tv_votes) {
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveHot, null);
        } else if (view.getId() == R.id.VoiceLive_AnchorHead) {//贵宾席
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_VoiceBuyVipSeats, null);
        } else if (i == R.id.btn_shop_list) {//直播购物列表
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveGoodsList, 1);
        } else if (i == R.id.btn_shop_store) {//直播购物橱窗列表
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveGoodsWindow, null);

        } else if (i == R.id.btn_shop_activity) {//横幅
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveGoodsActivity, null);
        } else {//点击讲解中的商品  如果是连接商品跳转去webView  如果是官方商品跳转去详情
            if (view.getTag() != null) {
                if (null != apiShopLiveGoods) {
                    if (apiShopLiveGoods.channelId != 1 && !apiShopLiveGoods.productLinks.isEmpty()) {
                        ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, apiShopLiveGoods.productLinks).navigation();
                    } else {
                        ARouter.getInstance().build(ARouterPath.GoodsDetailsActivity).withLong(ARouterValueNameConfig.goodsId, (long) view.getTag()).navigation();
                    }
                }
            }
        }
    }

    private void isVoice() {
        if (muteAllRemote) {
            muteAllRemote = false;
        } else {
            muteAllRemote = true;
        }
        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OPEN_CLOSE_MIC, muteAllRemote);
    }

    private void clear() {
        if (null != timeDisposable)
            timeDisposable.dispose();
        mEnd = true;
        if (mViewList != null) {
            for (LiveLightView view : mViewList) {
                view.cancel();
            }
            mViewList.clear();
        }
        isFirstLight = true;
        mPathMeasures = null;
        if (binding.tvCount != null) {
            binding.tvCount.clearAnimation();
        }
        if (mBgAnimator1 != null) {
            mBgAnimator1.cancel();
        }
        if (mBgAnimator2 != null) {
            mBgAnimator2.cancel();
        }
        if (mBgAnimator3 != null) {
            mBgAnimator3.cancel();
        }
        if (mUserAnimator1 != null) {
            mUserAnimator1.cancel();
        }
        if (mUserAnimator2 != null) {
            mUserAnimator2.cancel();
        }
        if (mUserAnimator3 != null) {
            mUserAnimator3.cancel();
        }
        if (binding.star != null) {
            binding.star.clearAnimation();
        }
        if (mMediaController != null) {
            mMediaController.hide();
            mMediaController.setAnchorView(null);
        }
        if (binding.enterRoomGif != null) {
            binding.enterRoomGif.setImageDrawable(null);
        }
        if (mGifDrawable != null && !mGifDrawable.isRecycled()) {
            mGifDrawable.stop();
            mGifDrawable.recycle();
            mGifDrawable = null;
        }
        IMUtil.removeReceiver(GroupName);
        removeFromParent();
    }

    @Override
    public void onKeyBoardHeightChanged(int visibleHeight, int keyboardHeight) {

    }

    @Override
    public boolean isSoftInputShowed() {
        return false;
    }

    @Override
    public void init(String groupName, SocketClient socketClient) {
        GroupName = groupName;
        mSocket = socketClient;
        imApiLive.init(mSocket);
        imApiLiveGift.init(socketClient);
        imApiLiveUserLine.init(mSocket);
        imApiLiveAnchorPk.init(mSocket);
        Log.i("aaa", groupName);
        IMUtil.addReceiver(groupName, new IMRcvLiveMsgSend() {

            @Override
            public void onRoomBan(long sessionID, String banInfo) {

            }

            @Override
            public void onUserBan(long sessionID, String banInfo) {

            }

            @Override
            public void onAppointUserSend(ApiSendMsgRoom apiSendMsgRoom) {

            }

            @Override
            public void onUserSendMsgRoom(ApiSendMsgRoom apiSendMsgRoom) {

            }

            @Override
            public void onUserSendApiJoinRoom(AppJoinRoomVO apiJoinRoom) {
                // 指定用户发送消息  根据 liveFunction 来判断 是否开启直播购功能  如果是直播购并且是观众 在主播切换直播购与普通直播时在此刷新状态
                if (ConfigUtil.getBoolValue(R.bool.containShopping) && LiveConstants.IDENTITY == LiveConstants.IDENTITY.AUDIENCE) {
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_UPDATE_LIVE_STATUS, apiJoinRoom.liveFunction);
                    if (null != apiJoinRoom) {
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.RoomInfoList, apiJoinRoom);
                    }
                }
                if (apiJoinRoom.liveFunction == 0) {
                    binding.liveShopGoods.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSimpleMsgRoom(ApiSimpleMsgRoom apiSimpleMsgRoom) {

            }


            @Override
            public void onSimpleMsgAll(ApiSimpleMsgRoom apiSimpleMsgRoom) {

            }


            @Override
            public void onUserNoticMsg(String conetnt) {

            }

            @Override
            public void onUserUpLiveTypeExitRoom(ApiExitRoom apiExitRoom) {
                if (viewModel.apiJoinRoom.get().anchorId != HttpClient.getUid()) {
                    //LookRoomUtlis.getInstance().closeLive();
                    // (用户没有钱了,需要提醒他去充值,充值成功,重新调 计时/门票收费接口(/api/live/startDeduction))
                    viewModel.apiJoinRoom.get().roomType = apiExitRoom.roomType;
                    viewModel.apiJoinRoom.get().roomTypeVal = apiExitRoom.roomTypeVal;
                    if (apiExitRoom.roomType == 0){
                        freeWatchTime = 0;
                        binding.llTimer.setVisibility(View.GONE);
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ChoiceLiveTypeSusser, apiExitRoom);
                    }else {
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ChoiceLiveTypeSusser, apiExitRoom);
                    }
                }
            }

            @Override
            public void onUserTimmerRoomRemind(int times) {
                // (余额不足提示)  返回值 times(还可以在房间待多久(秒))
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOGoldInsufficient, times);
            }

            @Override
            public void onTimerExitRoom(ApiTimerExitRoom apiTimerExitRoom) {
                // 余额不足了 （费用口完已不够支付了 0金币的情况）
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_NoMoney, null);
            }

            @Override
            public void onOtherMsg(Object o) {

            }
        });

        IMUtil.addReceiver(groupName, new IMRcvDynamiccircleSend() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUserVideoCommentCount(ApiSendVideoUnReadNumber apiSendVideoUnReadNumber) {

            }
        });

        IMUtil.addReceiver(groupName, new IMRcvShortVideoSend() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUserShortVideoCommentCount(ApiSendVideoUnReadNumber apiSendVideoUnReadNumber) {

            }
        });
        IMUtil.addReceiver(groupName, new IMRcvCommonMsgSend() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUserGetNoReadAll(int count) {

            }

        });
        IMUtil.addReceiver(groupName, new IMRcvGradeRightMsgSender() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onElasticFrameFinshTask(ApiElasticFrame elasticFrame) {

            }

            @Override
            public void onElasticFrameMedal(ApiElasticFrame elasticFrame) {

            }

            @Override
            public void onElasticFrameUpgrade(ApiElasticFrame elasticFrame) {

            }

        });


        IMUtil.addReceiver(groupName, new IMRcvLiveWishSend() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUserAddWishMsgUser(List<ApiUsersLiveWish> list) {

            }

            @Override//心愿单推送
            public void onUserAddWishMsg(List<ApiUsersLiveWish> list) {
                getMarquee(list);
            }

            @Override
            public void onSendWishUser(List<ApiUsersLiveWish> awList) {

            }

            @Override
            public void onSendWish(List<ApiUsersLiveWish> awList) {
                getMarquee(awList);
            }
        });


        IMUtil.addReceiver(groupName, new IMRcvLiveSend() {
            @Override
            public void onCloseLive(ApiCloseLive apiCloseLive) {

                if (!ConfigUtil.getBoolValue(R.bool.useMusicOld)) {
                    LiveMusicView.getInstance().close();
                }
                if (LiveConstants.IDENTITY == LiveConstants.IDENTITY.ANCHOR) {
                    //主播

                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_HttpCloseLive, apiCloseLive);
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_CloseLive, null);
                } else {
                    //观众
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_HttpCloseLive, apiCloseLive);
                }
            }

            @Override
            public void onUserJoinRoom(AppJoinRoomVO apiJoinRoom) {
                if (null != apiJoinRoom && null != apiJoinRoom.userList)
                    if (liveUserAdapter != null) {
                        liveUserAdapter.setliveBean(apiJoinRoom.userList);
                        viewModel.apiJoinRoom.set(apiJoinRoom);
                    }
                binding.tvWatchnum.setText(String.valueOf(apiJoinRoom.watchNumber));

                //在此显示 贵宾席图像
                //用户为 贵宾，始终显示 自己在贵宾席。
                //用户不为 贵宾，贵宾席 显示 刚进入的贵宾图像


                if (!isMyVip) {
                    //是否加入贵宾席0未加入1加入
                    if (!TextUtils.isEmpty(apiJoinRoom.seatsColor)) {
                        ImageLoader.display(apiJoinRoom.seatsColor, binding.VoiceLiveAnchorHead, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                    } else {
                        ImageLoader.display(R.mipmap.icon_vipseat, binding.VoiceLiveAnchorHead);
                    }
                }

                enterAnim(apiJoinRoom);
            }

            @Override
            public void onJoinRoomMsgRoom(ApiSimpleMsgRoom apiSimpleMsgRoom) {

            }

            @Override
            public void onDownVoiceAssistan(ApiUsersVoiceAssistan apiVoiceAssistanEntity) {

            }

            @Override
            public void onAnchorJoinRoom(ApiJoinRoomAnchor apiJoinRoomAnchor) {

            }

            @Override
            public void onUserBackground(String voicethumb) {

            }

            @Override
            public void onUsersVIPSeats(ApiUserSeats apiUserSeats) {
                if (!isMyVip) {
                    //自己 不是 贵宾时，其他人 购买贵宾席  贵宾席头像改变
                    ImageLoader.display(apiUserSeats.avatar, binding.VoiceLiveAnchorHead, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_VoiceBuyVipSeatsSuccess, null);
                }
                //自己购买 贵宾席 改变 判断标识
                if (HttpClient.getUid() == apiUserSeats.id) {
                    isMyVip = true;
                    ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
                    if (apiUserInfo != null) {
                        ImageLoader.display(apiUserInfo.avatar, binding.VoiceLiveAnchorHead, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                    }
                }
            }


            @Override
            public void onUserLeaveRoom(ApiLeaveRoom apiLeaveRoom) {
                userLeave(apiLeaveRoom.uid, apiLeaveRoom.watchNumber);
            }

            @Override
            public void onManageKickRoom(ApiKickLive apiKickLive) {
                if (apiKickLive.touid == HttpClient.getUid()) {
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, null);
                } else {
                    userLeave(apiKickLive.touid, apiKickLive.watchNumber);
                }

            }

            @Override
            public void onManageLeaveRoom(ApiCloseLive apiCloseLive) {

            }

            @Override
            public void onBuyGuardListRoom(List<GuardUserDto> list) {
                setGuradListUi(list);
            }

            @Override
            public void onAnchorLeaveRoom(ApiLeaveRoomAnchor ApiLeaveRoomAnchor) {

            }

            @Override
            public void onOtherMsg(Object obj) {

            }
        });

        IMUtil.addReceiver(groupName, new IMRcvShopMsgSend() {
            @Override
            public void onUsersShopBanner(String shopLiveBanner) {
//                if (shopLiveBanner != null && !shopLiveBanner.equals("")) {
//                    ImageLoader.display(shopLiveBanner, binding.LiveActivity, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
//                    binding.LiveActivity.setVisibility(View.VISIBLE);
//                }else {
//                    binding.LiveActivity.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onBuyGoodsRoom(UserBuyDTO userBuyDTO) {

            }

            @Override
            public void onUsersLiveGoodsStatus(ApiShopLiveGoods shopLiveGoods) {

                try {
                    if (apiShopLiveGoods == null) {
                        apiShopLiveGoods = shopLiveGoods;
                        binding.liveShopGoods.setVisibility(View.VISIBLE);
                        binding.liveShopGoods.setTag(apiShopLiveGoods.goodsId);
                        if (!TextUtils.isEmpty(apiShopLiveGoods.goodsPicture)) {
                            List<String> pictures = Arrays.asList(apiShopLiveGoods.goodsPicture.split(","));
                            if (pictures.size() > 0) {
                                ImageLoader.display(pictures.get(0), binding.liveShopGoodsImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                            }
                        }
                    } else {
                        if (apiShopLiveGoods.goodsId == shopLiveGoods.goodsId) {
                            apiShopLiveGoods = null;
                            binding.liveShopGoods.setVisibility(View.GONE);

                        } else {
                            apiShopLiveGoods = shopLiveGoods;
                            binding.liveShopGoods.setVisibility(View.VISIBLE);
                            binding.liveShopGoods.setTag(apiShopLiveGoods.goodsId);
                            if (!TextUtils.isEmpty(apiShopLiveGoods.goodsPicture)) {
                                List<String> pictures = Arrays.asList(apiShopLiveGoods.goodsPicture.split(","));
                                if (pictures.size() > 0) {
                                    ImageLoader.display(pictures.get(0), binding.liveShopGoodsImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                                }
                            }
                        }
                    }

                    if (shopLiveGoods.idExplain == 1) {
                        binding.liveShopGoods.setVisibility(View.VISIBLE);
                    } else {
                        binding.liveShopGoods.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        IMUtil.addReceiver(groupName, new IMRcvUserMsgSender() {

            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUsersBeautifulNumber(ApiBeautifulNumber user) {

            }
        });
    }

    /**
     * 播放飘心动画，每次飘一个心
     */
    public void play() {
        if (mEnd) {
            return;
        }
        LiveLightView liveLightView = getIdleImageView();
        if (liveLightView != null) {
            liveLightView.setImageResource(IconUtil.getLiveLightIcon(1 + RandomUtil.nextInt(6)));
            liveLightView.play(mPathMeasures[RandomUtil.nextInt(6)]);
        }
    }


    /**
     * 获得空闲的ImageView
     */
    private LiveLightView getIdleImageView() {
        if (mEnd) {
            return null;
        }
        for (LiveLightView view : mViewList) {
            if (view.isIdle()) {
                view.setIdle(false);
                return view;
            }
        }
        if (mViewList.size() < 10) {
            LiveLightView view = new LiveLightView(mContext);
            view.setLayoutParams(new ViewGroup.LayoutParams(DpUtil.dp2px(26), DpUtil.dp2px(26)));
            view.setIdle(false);
            view.setInterpolator(mInterpolator);
            mViewList.add(view);
            binding.root.addView(view);
            return view;
        }
        return null;
    }

    /**
     * 开播的时候 3 2 1倒计时
     */
    protected void startCountDown() {
        mCountDownCount = 3;
        binding.tvCount.setText(String.valueOf(mCountDownCount));
        ScaleAnimation animation = new ScaleAnimation(3, 1, 3, 1, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setRepeatCount(2);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.tvCount.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                mCountDownCount--;
                binding.tvCount.setText(String.valueOf(mCountDownCount));
            }
        });
        binding.tvCount.startAnimation(animation);
    }

    public void userLeave(long uid, int watchNumber) {
        List<ApiUserBasicInfo> list = liveUserAdapter.getList();
        ApiUserBasicInfo apiUserBasicInfo = null;
        for (ApiUserBasicInfo bean : list) {
            if (uid == bean.uid)
                apiUserBasicInfo = bean;
        }
        if (null != apiUserBasicInfo)
            list.remove(apiUserBasicInfo);
        liveUserAdapter.notifyDataSetChanged();

        Objects.requireNonNull(viewModel.apiJoinRoom.get()).watchNumber = watchNumber;
        binding.tvWatchnum.setText(String.valueOf(Objects.requireNonNull(viewModel.apiJoinRoom.get()).watchNumber));
    }

    //获取心愿单
    public void getWish() {
        HttpApiAnchorWishList.getWishList(LiveConstants.ANCHORID, new HttpApiCallBackArr<ApiUsersLiveWish>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiUsersLiveWish> retModel) {
                if (code == 1) {
                    getMarquee(retModel);
                }
            }
        });
    }

    //跑马灯
    public void getMarquee(List<ApiUsersLiveWish> retModel) {
        binding.VoiceLiveMarquee.removeAllViews();
        if (retModel.size() == 0) {
            binding.VoiceLiveMarquee.setVisibility(View.GONE);
        } else {
            binding.VoiceLiveMarquee.setVisibility(View.VISIBLE);
            for (int i = 0; i < retModel.size(); i++) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.wish_roll_item, null);
                ((TextView) view.findViewById(R.id.gift_will_name)).setText(retModel.get(i).giftname + " " + String.valueOf(retModel.get(i).sendNum) + "/" + String.valueOf(retModel.get(i).num));

                RoundedImageView imageView = view.findViewById(R.id.gift_will_image);
                ImageLoader.display(retModel.get(i).gifticon, imageView);
                binding.VoiceLiveMarquee.addView(view);
            }
        }


    }

    //获取守护列表数据
    public void getGuradList() {
        HttpApiAppUser.getGuardMyList(0, 10, LiveConstants.ANCHORID, new HttpApiCallBackArr<GuardUserDto>() {
            @Override
            public void onHttpRet(int code, String msg, List<GuardUserDto> retModel) {
                if (code == 1) {
                    setGuradListUi(retModel);
                }
            }
        });

    }

    @SuppressLint("WrongConstant")
    public void setGuradListUi(final List<GuardUserDto> retModel) {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        binding.guardList.setLayoutManager(manager);
        binding.guardList.addItemDecoration(new ItemDecoration(mContext, 0, 2, 0));
        InfoGuardListAdpater adpater = new InfoGuardListAdpater(mContext);
        binding.guardList.setAdapter(adpater);
        adpater.getGuardList(retModel, 1);
        adpater.setGuardListCallBack(new InfoGuardListAdpater.GuardListCallBack() {
            @Override
            public void onClick(int poistion) {
                LiveConstants.TOUID = retModel.get(poistion).userId;
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.Information, null);
            }
        });
    }


    //获取直播购的信息
    public void getLiveShopInformation() {
        HttpApiShopBusiness.getLiveInfo(LiveConstants.ANCHORID, new HttpApiCallBack<ShopLiveInfoDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ShopLiveInfoDTO retModel) {
                try {
                    if (code == 1) {
                        if (retModel.shopLiveGoods != null) {
                            binding.liveShopGoods.setVisibility(View.VISIBLE);
                            binding.liveShopGoods.setTag(retModel.shopLiveGoods.goodsId);
                            if (!TextUtils.isEmpty(retModel.shopLiveGoods.goodsPicture)) {
                                List<String> pictures = Arrays.asList(retModel.shopLiveGoods.goodsPicture.split(","));
                                if (pictures.size() > 0) {
                                    ImageLoader.display(pictures.get(0), binding.liveShopGoodsImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                                }
                            }
                        } else {
                            binding.liveShopGoods.setVisibility(View.GONE);
                        }
                        if (retModel.shopLiveGoods.idExplain == 1) {
                            binding.liveShopGoods.setVisibility(View.VISIBLE);
                        } else {
                            binding.liveShopGoods.setVisibility(View.GONE);
                        }
                        if (null == apiShopLiveGoods) {
                            apiShopLiveGoods = new ApiShopLiveGoods();
                        }
                        apiShopLiveGoods.channelId = retModel.shopLiveGoods.channelId;
                        apiShopLiveGoods.productLinks = retModel.shopLiveGoods.productLinks;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取贵族弹幕特权信息
     */
    private void getNobleBarrage() {
        HttpApiNoble.vipPrivilegeShow(new HttpApiCallBack<VipPrivilegeDto>() {
            @Override
            public void onHttpRet(int code, String msg, VipPrivilegeDto retModel) {
                if (code == 1 && retModel != null) {
                    SpUtil.getInstance().put(SpUtil.NOBLE_BARRAGE, retModel.nobleBarrage);
                }
            }
        });
    }
}
