package com.kalacheng.commonview.utils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.event.KickOutRoomEvent;
import com.kalacheng.base.event.TokenInvalidEvent;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.buscommon.model.GuardUserDto;
import com.kalacheng.buslivebas.socketmsg.IMRcvLiveMsgSend;
import com.kalacheng.buslivebas.socketmsg.IMRcvLiveSend;
import com.kalacheng.buslivebas.socketmsg.IMRcvVoiceOperation;
import com.kalacheng.buslivebas.socketmsg.IMRcvVoicePK;
import com.kalacheng.busnobility.model.ApiPkResult;
import com.kalacheng.busnobility.socketmsg.IMRcvLiveGiftSend;
import com.kalacheng.buspersonalcenter.socketmsg.IMRcvUserMsgSender;
import com.kalacheng.busvoicelive.httpApi.HttpApiHttpVoice;
import com.kalacheng.commonview.R;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.model.ApiBeautifulNumber;
import com.kalacheng.libuser.model.ApiCloseLive;
import com.kalacheng.libuser.model.ApiExitRoom;
import com.kalacheng.libuser.model.ApiGiftSender;
import com.kalacheng.libuser.model.ApiJoinRoomAnchor;
import com.kalacheng.libuser.model.ApiKickLive;
import com.kalacheng.libuser.model.ApiLeaveRoom;
import com.kalacheng.libuser.model.ApiLeaveRoomAnchor;
import com.kalacheng.libuser.model.ApiSendMsgRoom;
import com.kalacheng.libuser.model.ApiShopLiveGoods;
import com.kalacheng.libuser.model.ApiSimpleMsgRoom;
import com.kalacheng.libuser.model.ApiTimerExitRoom;
import com.kalacheng.libuser.model.ApiUserSeats;
import com.kalacheng.libuser.model.ApiUsersVoiceAssistan;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.libuser.model.PkGiftSender;
import com.kalacheng.libuser.model.UserBuyDTO;
import com.kalacheng.libuser.model.VoicePkVO;
import com.kalacheng.shop.socketmsg.IMRcvShopMsgSend;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.JsonUtil;
import com.kalacheng.util.utils.LruJsonCache;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.VoiceAnimationUtlis;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.klc.bean.live.VoiceLiveOwnStateBean;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wengying666.imsocket.IMUtil;
import com.xuantongyun.livecloud.protocol.VoiceLiveCloudUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.jmessage.support.google.gson.Gson;
import cn.jmessage.support.google.gson.reflect.TypeToken;

// 最小化旋转视图
public class SmallLiveRoomDialogFragment {

    private static volatile SmallLiveRoomDialogFragment smallLiveRoom;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams wmParams;
    private Context mContext;
    private LayoutInflater inflater;
    private View mFloatingLayout;    //布局View
    private ObjectAnimator animator;  //图片旋转
    private AppJoinRoomVO apiJoinRoom;
    private LruJsonCache cache;
    private List<ApiSimpleMsgRoom> mList = new ArrayList<ApiSimpleMsgRoom>();

    /**
     * 设置悬浮框基本参数（位置、宽高等）
     */
    private RoundedImageView HeadImage;

    public static SmallLiveRoomDialogFragment getInstance() {

        if (smallLiveRoom == null) {
            synchronized (AllSocketUtlis.class) {
                if (smallLiveRoom == null) {
                    smallLiveRoom = new SmallLiveRoomDialogFragment();
                }
            }
        }
        return smallLiveRoom;
    }

    public void show(Context mContext, AppJoinRoomVO apiJoinRoom) {
        if (!EventBus.getDefault().isRegistered(SmallLiveRoomDialogFragment.this)) {
            EventBus.getDefault().register(SmallLiveRoomDialogFragment.this);
        }
        this.mContext = mContext;
        this.apiJoinRoom = apiJoinRoom;
        initWindow();
        socket();
    }

    private void initWindow() {
        cache = LruJsonCache.get(mContext);
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wmParams = getParams();
        inflater = LayoutInflater.from(mContext);
        mFloatingLayout = inflater.inflate(R.layout.small_liveroom, null);

        HeadImage = mFloatingLayout.findViewById(R.id.HeadImage);

        if (apiJoinRoom != null) {
            ImageLoader.display(apiJoinRoom.anchorAvatar, HeadImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        }

        ImageView voice_Animation = mFloatingLayout.findViewById(R.id.voice_Animation);

        //语音音符动画
//        VoiceAnimationUtlis.getInstance().showAnimation(voice_Animation);

        rotateAnimation();
        mFloatingLayout.setOnTouchListener(new FloatingListener());
        mWindowManager.addView(mFloatingLayout, wmParams);

        if (!TextUtils.isEmpty(cache.getAsString(LiveConstants.Cache_SmallMessage))) {
            String json = cache.getAsString(LiveConstants.Cache_SmallMessage);
            if (!TextUtils.isEmpty(json)) {
                List<ApiSimpleMsgRoom> msgRooms = new Gson().fromJson(json, new TypeToken<List<ApiSimpleMsgRoom>>() {
                }.getType());
                if (null != msgRooms) {
                    mList.addAll(msgRooms);
                }
            }
        }

    }

    private WindowManager.LayoutParams getParams() {
        wmParams = new WindowManager.LayoutParams();
//        wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        wmParams.format = PixelFormat.RGBA_8888;
        //设置可以显示在状态栏上
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //这是悬浮窗居中位置
        wmParams.gravity = Gravity.TOP;
        //70、210是我项目中的位置哦
        wmParams.x = 300;
        wmParams.y = DpUtil.getScreenHeight() - DpUtil.dp2px(200);
        return wmParams;
    }

    private int mTouchStartX, mTouchStartY, mTouchCurrentX, mTouchCurrentY;
    private int mStartX, mStartY, mStopX, mStopY;
    private boolean isMove;
    long startTime;
    long endTime;
    boolean isclick;

    private class FloatingListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    startTime = System.currentTimeMillis();
                    isMove = false;
                    mTouchStartX = (int) event.getRawX();
                    mTouchStartY = (int) event.getRawY();
                    mStartX = (int) event.getX();
                    mStartY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mTouchCurrentX = (int) event.getRawX();
                    mTouchCurrentY = (int) event.getRawY();
                    wmParams.x += mTouchCurrentX - mTouchStartX;
                    wmParams.y += mTouchCurrentY - mTouchStartY;
                    try {
                        mWindowManager.updateViewLayout(mFloatingLayout, wmParams);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    mTouchStartX = mTouchCurrentX;
                    mTouchStartY = mTouchCurrentY;
                    break;
                case MotionEvent.ACTION_UP:
                    mStopX = (int) event.getX();
                    mStopY = (int) event.getY();
                    if (Math.abs(mStartX - mStopX) >= 1 || Math.abs(mStartY - mStopY) >= 1) {
                        isMove = true;
                    }
                    endTime = System.currentTimeMillis();
                    //当从点击到弹起小于半秒的时候,则判断为点击,如果超过则不响应点击事件
                    if ((endTime - startTime) > 0.1 * 1000L) {
                        isclick = false;
                    } else {
                        isclick = true;
                    }
                    startTime = 0;
                    endTime = 0;
                    break;
            }
            if (isclick) {
                isclick = false;
                goBackRoom();
            }
            return isMove;
        }
    }

    public void close() {
        VoiceAnimationUtlis.getInstance().stopAnimation();
        if (smallLiveRoom != null) {
            if (mWindowManager == null) {
                if (mContext != null) {
                    mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                } else {
                    return;
                }
            }
            try {
                if (null != mFloatingLayout) {
                    mWindowManager.removeView(mFloatingLayout);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            smallLiveRoom = null;
        }
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
        IMUtil.removeReceiver("Small");
        IMUtil.removeReceiver("SmallMike");
        IMUtil.removeReceiver("SmallMikePK");
        IMUtil.removeReceiver("SmallGift");

        if (EventBus.getDefault().isRegistered(SmallLiveRoomDialogFragment.this)) {
            EventBus.getDefault().unregister(SmallLiveRoomDialogFragment.this);
        }
    }

    public void rotateAnimation() {

        animator = ObjectAnimator.ofFloat(HeadImage, "rotation", 0f, 360f);
        animator.setInterpolator(new LinearInterpolator());
        //设置动画重复次数
        animator.setRepeatCount(Animation.INFINITE);
        //旋转时长
        animator.setDuration(4000);
        //开始旋转
        animator.start();
    }

    //在最小化的时候进入其他房间关闭当前房间
    public void closeRoom() {
        if (apiJoinRoom == null || apiJoinRoom.anchorId != HttpClient.getUid()) {
            cache.remove(LiveConstants.Cache_SmallMessage);
            clearRoom();
        }
    }

    //最小化回到直播间
    public void goBackRoom() {
        getApiJoinRoom();
    }

    //获取直播间的信息
    public void getApiJoinRoom() {
        if (apiJoinRoom != null) {
            HttpApiHttpVoice.getApiJoinRoom(apiJoinRoom.liveType, apiJoinRoom.roomId, new HttpApiCallBack<AppJoinRoomVO>() {
                @Override
                public void onHttpRet(int code, String msg, AppJoinRoomVO retModel) {
                    if (code == 1) {
                        close();
                        if (null != cache && null != mList && mList.size() > 0) {
                            cache.put(LiveConstants.Cache_SmallMessage, JsonUtil.toJson(mList));
                            mList.clear();
                        }
                        if (retModel.anchorId == HttpClient.getUid()) {
                            ARouter.getInstance().build(ARouterPath.VoiceLive).withParcelable("ApiJoinRoom", retModel).navigation();
                        } else {
                            ARouter.getInstance().build(ARouterPath.JOINVoiceRoom).withParcelable("ApiJoinRoom", retModel).withString(ARouterValueNameConfig.isSmall, "isSmall").navigation();
                        }
                    } else if (code == 2) {
                        ToastUtil.show(msg);
                        close();
                        closeRoom();
                    } else if (code == 44001) {
                        ToastUtil.show("网络请求失败");
                    } else {
                        ToastUtil.show(msg);
                    }
                }
            });
        }
    }

    /**
     * 清除房间
     */
    private void clearRoom() {
        LiveConstants.isSamll = false;
        LiveBundle.getInstance().removeAllListener();

        LiveConstants.ROOMID = 0;
        LiveConstants.ANCHORID = 0;
        SpUtil.getInstance().put(SpUtil.ANCHOR_ID, LiveConstants.ANCHORID);
        LiveConstants.LiveAddress = 0;
        LiveConstants.IsRemoteAudio = false;
        LiveConstants.VoiceAnchorInvitation = false;
        LiveConstants.IsStopPushStream = false;
        VoiceLiveOwnStateBean.MikeState = 1;
        VoiceLiveOwnStateBean.IsMike = false;
        VoiceLiveOwnStateBean.IsMute = false;
        LiveConstants.isPalyMusic = false;
        LiveConstants.UpMikeState = 1;

        //关闭播放器
        LiveMusicView.getInstance().close();

        VoiceLiveCloudUtils.getInstance().clean();
        close();
        leaveRoomOpt();
    }

    //用户退出房间
    public void leaveRoomOpt() {
        if (apiJoinRoom != null) {
            HttpApiHttpVoice.leaveVoiceRoomOpt(apiJoinRoom.roomId, new HttpApiCallBack<ApiLeaveRoom>() {
                @Override
                public void onHttpRet(int code, String msg, ApiLeaveRoom retModel) {
                    if (code == 1) {
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, new ApiCloseLive());
                    } else {
                        ToastUtil.show(msg);
                    }
                }
            });
        } else {
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, new ApiCloseLive());
        }
    }


    public void socket() {
        IMUtil.addReceiver("Small", new IMRcvLiveSend() {
            @Override
            public void onCloseLive(ApiCloseLive apiCloseLive) {
                if (LiveConstants.IDENTITY == LiveConstants.IDENTITY.ANCHOR) {
                    clearRoom();
                } else if (LiveConstants.IDENTITY == LiveConstants.IDENTITY.AUDIENCE) {
                    closeRoom();
                }
            }

            @Override
            public void onUserJoinRoom(AppJoinRoomVO apiJoinRoom) {

            }

            @Override
            public void onJoinRoomMsgRoom(ApiSimpleMsgRoom apiSimpleMsgRoom) {
                // 土豪进场
            }

            @Override
            public void onDownVoiceAssistan(ApiUsersVoiceAssistan apiVoiceAssistanEntity) {

            }

            @Override
            public void onAnchorJoinRoom(ApiJoinRoomAnchor apiJoinRoomAnchor) {

            }

            @Override//背景图片
            public void onUserBackground(String voicethumb) {

            }

            @Override
            public void onUsersVIPSeats(ApiUserSeats apiUserSeats) {

            }


            @Override
            public void onUserLeaveRoom(ApiLeaveRoom apiLeaveRoom) {

            }

            @Override
            public void onManageKickRoom(ApiKickLive apiKickLive) {
                if (apiKickLive.touid == HttpClient.getUid()) {
                    closeRoom();
                }

            }

            @Override
            public void onManageLeaveRoom(ApiCloseLive apiCloseLive) {

            }

            @Override
            public void onBuyGuardListRoom(List<GuardUserDto> list) {

            }


            @Override
            public void onAnchorLeaveRoom(ApiLeaveRoomAnchor ApiLeaveRoomAnchor) {

            }

            @Override
            public void onOtherMsg(Object obj) {

            }
        });

        IMUtil.addReceiver("Small", new IMRcvShopMsgSend() {
            @Override
            public void onUsersShopBanner(String shopLiveBanner) {

            }

            @Override
            public void onBuyGoodsRoom(UserBuyDTO userBuyDTO) {

            }

            @Override
            public void onUsersLiveGoodsStatus(ApiShopLiveGoods apiShopLiveGoods) {

            }

            @Override
            public void onOtherMsg(Object o) {

            }
        });

        IMUtil.addReceiver("Small", new IMRcvUserMsgSender() {

            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUsersBeautifulNumber(ApiBeautifulNumber user) {

            }
        });


        //麦位socket
        IMUtil.addReceiver("SmallMike", new IMRcvVoiceOperation() {
            @Override
            public void downVoiceAssistan(List<ApiUsersVoiceAssistan> apiVoiceAssistanEntity, long downUid) {

            }

            @Override
            public void kickOutAssistan(long assisId, List<ApiUsersVoiceAssistan> assitan) {
                if (assisId == HttpClient.getUid()) {
                    VoiceLiveOwnStateBean.IsMike = false;
                    VoiceLiveOwnStateBean.OwnIdentity = VoiceLiveOwnStateBean.Audience;
                    VoiceLiveCloudUtils.getInstance().setClientRole(3);
                }
            }

            @Override
            public void hostOffVolumn(int onOffState) {
            }

            @Override
            public void offVolumn(List<ApiUsersVoiceAssistan> apiVoiceAssistanEntity, long setUid) {
                //禁麦
                if (null != apiVoiceAssistanEntity && apiVoiceAssistanEntity.size() > 0) {
                    for (int i = 0; i < apiVoiceAssistanEntity.size(); i++) {
                        if (HttpClient.getUid() == apiVoiceAssistanEntity.get(i).uid) {//判断下麦是否是自己
                            VoiceLiveOwnStateBean.MikeState = apiVoiceAssistanEntity.get(i).onOffState;
                            if (apiVoiceAssistanEntity.get(i).onOffState == 1) {
                                int muteLocalAudioStream = VoiceLiveCloudUtils.getInstance().muteLocalAudioStream(false);
                                if (muteLocalAudioStream != 0) {
                                    ToastUtil.show("开启失败，请重新打开麦克风");
                                }
                            } else {
                                int muteLocalAudioStream = VoiceLiveCloudUtils.getInstance().muteLocalAudioStream(true);
                                if (muteLocalAudioStream != 0) {
                                    ToastUtil.show("禁言失败，请重新关闭麦克风");
                                }
                            }
                        }
                    }
                }

            }

            @Override
            public void LockThisAssistan(int assistanNo, List<ApiUsersVoiceAssistan> assitan) {

            }

            @Override
            public void sendAnchorSticker(String gifUrl) {

            }

            @Override//语音房间非PK状态下的发送表情包
            public void sendStricker(int no, List<ApiUsersVoiceAssistan> hGetAssistanList) {

            }

            @Override
            public void onUpVoiceAssistan(List<ApiUsersVoiceAssistan> apiVoiceAssistanEntity, long upUid) {

            }

            @Override
            public void sendGift(List<ApiUsersVoiceAssistan> hGetAssistanList) {

            }

            @Override
            public void onOtherMsg(Object o) {

            }
        });
        IMUtil.addReceiver("SmallMikePK", new IMRcvVoicePK() {
            @Override
            public void tellAuthorMatched(VoicePkVO thisPkVO) {

            }

            @Override
            public void quitPK(List<ApiUsersVoiceAssistan> thisAssistans, long optUid, int pkType) {

            }

            @Override
            public void updatePK(VoicePkVO thisPkVO, long optUid, int optType) {
                if (optType == 6) {
                    if (optType == HttpClient.getUid()) {
                        VoiceLiveOwnStateBean.IsMike = false;
                        VoiceLiveOwnStateBean.OwnIdentity = VoiceLiveOwnStateBean.Audience;
                        VoiceLiveCloudUtils.getInstance().setClientRole(3);
                    }
                }
            }

            @Override
            public void kickedBeforeOpen(int pkType) {

            }

            @Override
            public void OpenPKSuccess(VoicePkVO thisPkVO) {

            }

            @Override
            public void startPK(VoicePkVO thisPkVO) {

            }

            @Override
            public void beforefinishPK(VoicePkVO thisPkVO) {

            }

            @Override
            public void matchPkTimeOut(int pktype) {

            }

            @Override
            public void sendGiftPk(List<PkGiftSender> thisSenders, List<PkGiftSender> otherSenders) {

            }

            @Override
            public void onOtherMsg(Object o) {

            }
        });

        IMUtil.addReceiver("Small", new IMRcvLiveMsgSend() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onSimpleMsgRoom(ApiSimpleMsgRoom apiSimpleMsgRoom) {
                // 普通消息 加入缓存
                // 消息类型 1:进场消息 2:退场消息 3:礼物消息 4:点亮 5:红包 6:禁言解禁消息 7:设置取消管理员 8:踢人消息 9:购买守护 10:关注和取消关注 11:文字聊天消息 12:主播离开回来消息 13:系统消息 14:弹幕消息 15:骰子 16警告消息
                setCache(apiSimpleMsgRoom);
            }

            @Override
            public void onUserNoticMsg(String conetnt) {

            }

            @Override
            public void onUserSendApiJoinRoom(AppJoinRoomVO appJoinRoomVO) {

            }

            @Override
            public void onAppointUserSend(ApiSendMsgRoom apiSendMsgRoom) {

            }

            @Override
            public void onUserUpLiveTypeExitRoom(ApiExitRoom apiExitRoom) {
                // 按照以前的逻辑是 最小化主播更改房间模式 最小化旋转Dialog取消显示
                //clearRoom();
                apiJoinRoom.roomId = apiExitRoom.roomId;
                apiJoinRoom.roomType = apiExitRoom.roomType;
                apiJoinRoom.roomTypeVal = apiExitRoom.roomTypeVal;
            }

            @Override
            public void onSimpleMsgAll(ApiSimpleMsgRoom apiSimpleMsgRoom) {

            }

            @Override
            public void onUserSendMsgRoom(ApiSendMsgRoom apiSendMsgRoom) {

            }

            @Override
            public void onUserTimmerRoomRemind(int times) {

            }

            @Override
            public void onTimerExitRoom(ApiTimerExitRoom apiTimerExitRoom) {

            }

            @Override
            public void onRoomBan(long sessionID, String banInfo) {

            }

            @Override
            public void onUserBan(long sessionID, String banInfo) {

            }
        });

        IMUtil.addReceiver("SmallGift", new IMRcvLiveGiftSend() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onSimpleGiftMsgRoom(ApiSimpleMsgRoom apiSimpleMsgRoom) {
                // 礼物消息
                setCache(apiSimpleMsgRoom);
            }

            @Override
            public void onGiftMsgAll(ApiGiftSender apiGiftSender) {

            }

            @Override
            public void onGiveGiftUser(ApiGiftSender apiGiftSender) {

            }

            @Override
            public void onGiveGift(ApiGiftSender apiGiftSender) {

            }

            @Override
            public void onGiftPKResult(ApiPkResult apiPKResult) {

            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onKickOutRoomEvent(KickOutRoomEvent e) {
        closeRoom();
        ToastUtil.show("语音君出小差，重新进入房间吧");
    }

    //token失效
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTokenInvalidEvent(TokenInvalidEvent e) {
        closeRoom();
    }

    //后台隐藏
    public void setGone() {
        if (mFloatingLayout != null) {
            mFloatingLayout.setVisibility(View.GONE);
        }
    }

    //回到app显示
    public void setVisible() {
        if (mFloatingLayout != null) {
            mFloatingLayout.setVisibility(View.VISIBLE);
        }
    }

    private void setCache(ApiSimpleMsgRoom apiSimpleMsgRoom) {
        mList.add(apiSimpleMsgRoom);
    }

}
