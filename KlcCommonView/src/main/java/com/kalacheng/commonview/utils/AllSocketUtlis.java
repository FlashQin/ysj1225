package com.kalacheng.commonview.utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.base.SuspensionFramePermission;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buscommon.model.GuardUserDto;
import com.kalacheng.buscommon.socketmsg.IMRcvCommonMsgSend;
import com.kalacheng.busdynamiccircle.socketmsg.IMRcvDynamiccircleSend;
import com.kalacheng.busfinance.socketmsg.IMRcvLiveMoneyMsgAllSend;
import com.kalacheng.busgraderight.socketmsg.IMRcvGradeRightMsgSender;
import com.kalacheng.buslivebas.socketmsg.IMRcvLiveMsgAllSend;
import com.kalacheng.buslivebas.socketmsg.IMRcvLiveMsgSend;
import com.kalacheng.buslivebas.socketmsg.IMRcvLiveSend;
import com.kalacheng.buslivebas.socketmsg.IMRcvVoiceInvite;
import com.kalacheng.busooolive.model.OOOHangupReturn;
import com.kalacheng.busooolive.model.OOOReturn;
import com.kalacheng.busooolive.model.OOOVolumeRet;
import com.kalacheng.busooolive.socketmsg.IMRcvOOOLive;
import com.kalacheng.busooolive.socketmsg.IMRcvOTMLive;
import com.kalacheng.buspersonalcenter.socketmsg.IMRcvUserMsgSender;
import com.kalacheng.bususer.socketmsg.IMRcvAnchorMsgSender;
import com.kalacheng.commonview.component.AllFloatingScreenComponent;
import com.kalacheng.commonview.dialog.RobChatWowenDialogFragment;
import com.kalacheng.commonview.dialog.SuspensionFramePermissionDialog;
import com.kalacheng.commonview.dialog.SvipInvitationDialogFragment;
import com.kalacheng.commonview.dialog.VoiceAnchorInvitationDialogFragment;
import com.kalacheng.commonview.jguangIm.UnReadCountEvent;
import com.kalacheng.base.event.AccountDisableEvent;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.game.socketmsg.IMRcvLiveGameMsgAllSend;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libuser.httpApi.HttpApiChatRoom;
import com.kalacheng.libuser.model.ApiBeautifulNumber;
import com.kalacheng.libuser.model.ApiCloseLive;
import com.kalacheng.libuser.model.ApiElasticFrame;
import com.kalacheng.libuser.model.ApiExitRoom;
import com.kalacheng.libuser.model.ApiGiftSender;
import com.kalacheng.libuser.model.ApiJoinRoomAnchor;
import com.kalacheng.libuser.model.ApiKickLive;
import com.kalacheng.libuser.model.ApiLeaveRoom;
import com.kalacheng.libuser.model.ApiLeaveRoomAnchor;
import com.kalacheng.libuser.model.ApiNoRead;
import com.kalacheng.libuser.model.ApiPushChat;
import com.kalacheng.libuser.model.ApiSendMsgRoom;
import com.kalacheng.libuser.model.ApiSendVideoUnReadNumber;
import com.kalacheng.libuser.model.ApiShopLiveGoods;
import com.kalacheng.libuser.model.ApiSimpleMsgRoom;
import com.kalacheng.libuser.model.ApiTimerExitRoom;
import com.kalacheng.libuser.model.ApiUserSeats;
import com.kalacheng.libuser.model.ApiUsersVoiceAssistan;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.libuser.model.GameUserWinAwardsDTO;
import com.kalacheng.libuser.model.UserBuyDTO;
import com.kalacheng.shop.socketmsg.IMRcvShopMsgSend;
import com.kalacheng.shortvideo.socketmsg.IMRcvShortVideoSend;
import com.kalacheng.util.permission.FloatPermissionManager;
import com.kalacheng.util.utils.SpUtil;
import com.klc.bean.OOOLiveHangUpBean;
import com.klc.bean.RechargeBean;
import com.wengying666.imsocket.IMUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class AllSocketUtlis {
    private SvipInvitationDialogFragment svipInvitationToastView;

    private static volatile AllSocketUtlis singleton;
    //判断是否在后台
    private boolean isBackstage = false;

    RobChatWowenDialogFragment robChatWowenDialogFragment;

    private AllSocketUtlis() {
    }

    public static AllSocketUtlis getInstance() {

        if (singleton == null) {
            synchronized (AllSocketUtlis.class) {
                if (singleton == null) {
                    singleton = new AllSocketUtlis();
                }
            }
        }
        return singleton;
    }

    public void getSocket(final Context mContext) {

        IMUtil.addReceiver("oooSvipLiveService", new IMRcvOTMLive() {

            @Override
            public void cancelInvite(long sessionID) {

            }

            //副播退出 ，发送所有人
            @Override
            public void hangupCall(long sessionID, OOOHangupReturn hangupInfo) {
                if (sessionID == LiveConstants.mOOOSessionID) {
                    //挂断原因：1对方主动挂断；2对方掉线；3余额不足
               /*     if (sessionID == LiveConstants.mOOOSessionID) {
                        //挂断原因：1对方主动挂断；2对方掉线；3余额不足
                        OOOLiveHangUpBean hangUpBean = new OOOLiveHangUpBean();
                        hangUpBean.callTime = hangupInfo.callTime;
                        hangUpBean.totalCoin = (int) hangupInfo.totalCoin;
                        hangUpBean.sessionID = sessionID;
                        hangUpBean.uid = hangupInfo.callUpUid;
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOCallEnd, hangUpBean);
                        LiveConstants.mOOOSessionID = 0;
                    }*/
                    //* role 挂断方的角色 0用户 1主播 2副播
                    if (hangupInfo.role == 0) {
                        OOOLiveHangUpBean hangUpBean = new OOOLiveHangUpBean();
                        hangUpBean.callTime = hangupInfo.callTime;
                        hangUpBean.totalCoin = (int) hangupInfo.totalCoin;
                        hangUpBean.sessionID = sessionID;
                        hangUpBean.uid = hangupInfo.callUpUid;
                        hangUpBean.vipGradeMsg = hangupInfo.vipGradeMsg;
                        hangUpBean.vipCount = hangupInfo.vipCount;
                        hangUpBean.name = hangupInfo.username;

                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOCallEnd, hangUpBean);
                        LiveConstants.mOOOSessionID = 0;
                    } else if (hangupInfo.role == 1) {
                        OOOLiveHangUpBean hangUpBean = new OOOLiveHangUpBean();
                        hangUpBean.callTime = hangupInfo.callTime;
                        hangUpBean.totalCoin = (int) hangupInfo.totalCoin;
                        hangUpBean.sessionID = sessionID;
                        hangUpBean.uid = hangupInfo.callUpUid;
                        hangUpBean.vipGradeMsg = hangupInfo.vipGradeMsg;
                        hangUpBean.vipCount = hangupInfo.vipCount;
                        hangUpBean.name = hangupInfo.username;

                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOCallEnd, hangUpBean);
                        LiveConstants.mOOOSessionID = 0;
                    } else if (hangupInfo.role == 2) {
                        OOOLiveHangUpBean hangUpBean = new OOOLiveHangUpBean();
                        hangUpBean.callTime = hangupInfo.callTime;
                        hangUpBean.totalCoin = (int) hangupInfo.totalCoin;
                        hangUpBean.sessionID = sessionID;
                        hangUpBean.uid = hangupInfo.callUpUid;
                        hangUpBean.vipGradeMsg = hangupInfo.vipGradeMsg;
                        hangUpBean.vipCount = hangupInfo.vipCount;
                        hangUpBean.name = hangupInfo.username;

                        if (hangupInfo.callUpUid == HttpClient.getUid()) {

                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOCallEnd, hangUpBean);
                            LiveConstants.mOOOSessionID = 0;
                        } else {

                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveSVipSSideshowignOut, hangUpBean);
                        }
                    }

                }
            }

            @Override
            public void inviteYouToChat(ApiUserInfo userInfo, long sessionID, long TimeOutMilliSecond, int isVideo, long feeUid, int userStatus, double oooFee) {
                if (SuspensionFramePermission.getInstance().checkFloatPermission(mContext)) {
                    LiveConstants.mOOOSessionID = sessionID;
                    if (svipInvitationToastView == null) {
                        svipInvitationToastView = new SvipInvitationDialogFragment(mContext);
                    }
                    svipInvitationToastView.show(userInfo, feeUid);
                } else {
                    SuspensionFramePermissionDialog.getInstance().show(mContext, null);
                }
            }

            @Override
            public void refuseLive(long sessionID) {

            }


            //副播加入成功
            @Override
            public void agreeLive(OOOReturn info) {
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveSVipJoinSuccess, info);
            }

            @Override
            public void upVolumeOperation(long sessionID, OOOVolumeRet volumeRet) {
                if (LiveConstants.mOOOSessionID == sessionID) {
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveSVipEstoppelSpeake, volumeRet);
                }
            }

            @Override
            public void kickOutRoom(long assisId, long sessionID, OOOHangupReturn hangupInfo) {
                OOOLiveHangUpBean hangUpBean = new OOOLiveHangUpBean();
                hangUpBean.callTime = hangupInfo.callTime;
                hangUpBean.totalCoin = (int) hangupInfo.totalCoin;
                hangUpBean.sessionID = sessionID;
                hangUpBean.uid = hangupInfo.callUpUid;
                hangUpBean.name = hangupInfo.username;
                hangUpBean.vipGradeMsg = hangupInfo.vipGradeMsg;
                hangUpBean.vipCount = hangupInfo.vipCount;

                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveSVipSSideshowignOut, hangUpBean);
            }

            @Override
            public void otmUptUserCoin(long sessionID, double coin) {
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOUpDataGold, coin);

            }

            //用户和主播退出 //给所有发
            @Override
            public void hangupCallUser(long sessionID, OOOHangupReturn hangupInfo) {
                if (LiveConstants.mOOOSessionID == sessionID) {
                    if (hangupInfo.callUpUid != HttpClient.getUid()) {
                        OOOLiveHangUpBean hangUpBean = new OOOLiveHangUpBean();
                        hangUpBean.uid = hangupInfo.callUpUid;
                        hangUpBean.callTime = hangupInfo.callTime;
                        hangUpBean.totalCoin = (int) hangupInfo.totalCoin;
                        hangUpBean.sessionID = sessionID;
                        hangUpBean.vipGradeMsg = hangupInfo.vipGradeMsg;
                        hangUpBean.vipCount = hangupInfo.vipCount;

                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOCallEnd, hangUpBean);
                        LiveConstants.mOOOSessionID = 0;
                    }

                }

            }

            @Override
            public void otmInviteEnd(long sessionID, int reason) {

            }

            @Override
            public void runningOutOfCoin(long sessionID, int times) {
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOGoldInsufficient, times);
            }


            @Override
            public void onOtherMsg(Object o) {

            }

        });

        IMUtil.addReceiver("oooLiveService", new IMRcvOOOLive() {
            @Override
            public void oooInviteYouToChat(final ApiUserInfo userInfo, long sessionID, long TimeOutMilliSecond, final int isVideo, final long feeUid, int userStatus, double oooFee) {

                LiveConstants.mOOOSessionID = sessionID;
                LiveConstants.mIsOOOSend = false;//接收者
                LiveConstants.OOOFEE = oooFee;
                LiveConstants.LiveType = 2;
                if (userInfo.userId != HttpClient.getUid()) {
                    if (LiveConstants.IsLookLive) {
                        LookRoomUtlis.getInstance().closeLive();
                    }

                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_CloseLive, null);

                    // 1v1 如果是在语音直播间 先关闭掉直播间 在进入1v1
                    if (LiveConstants.isInsideRoomType == 2 && LiveConstants.ANCHORID != HttpClient.getUid()) {
                        if (LiveConstants.isSamll) {
                            // 是否最小化
                            SmallLiveRoomDialogFragment.getInstance().closeRoom();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (isVideo == 1) {//一对一视频
                                        ARouter.getInstance().build(ARouterPath.One2OneSvipLive).withParcelable(ARouterValueNameConfig.OOOLiveJoinRoom, userInfo).withLong(ARouterValueNameConfig.OOOLiveJFeeUid, feeUid).withInt(ARouterValueNameConfig.OOOLiveType, 1).navigation();
                                    } else {//一对一语音
                                        ARouter.getInstance().build(ARouterPath.OneVoiceLive).withParcelable(ARouterValueNameConfig.OOOLiveJoinRoom, userInfo).withLong(ARouterValueNameConfig.OOOLiveJFeeUid, feeUid).withInt(ARouterValueNameConfig.OOOLiveType, 1).navigation();
                                    }
                                }
                            }, 500);
                            return;
                        } else {
                            // 是否在房间内 在语音房间 先退出语音房间 在进行1v1通话
                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, null);
                            LiveConstants.isInsideRoomType = 0;
                        }
                    }

                    if (isVideo == 1) {//一对一视频
                        ARouter.getInstance().build(ARouterPath.One2OneSvipLive).withParcelable(ARouterValueNameConfig.OOOLiveJoinRoom, userInfo).withLong(ARouterValueNameConfig.OOOLiveJFeeUid, feeUid).withInt(ARouterValueNameConfig.OOOLiveType, 1).navigation();
                    } else {//一对一语音
                        ARouter.getInstance().build(ARouterPath.OneVoiceLive).withParcelable(ARouterValueNameConfig.OOOLiveJoinRoom, userInfo).withLong(ARouterValueNameConfig.OOOLiveJFeeUid, feeUid).withInt(ARouterValueNameConfig.OOOLiveType, 1).navigation();
                    }

                }

            }

            @Override//同意
            public void oooAgreeLive(OOOReturn info) {
                if (info.sessionID == LiveConstants.mOOOSessionID) {//会话ID相关才能做相关操作
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveLinkOK, info);
                }
            }

            @Override
            public void oooInviteEnd(long sessionID, int resion) {
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveHangUp, sessionID);
            }

            @Override
            public void oooCancelInvite(long sessionID) {
                if (sessionID == LiveConstants.mOOOSessionID) {
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveHangUp, null);
                }
            }


            @Override//拒绝
            public void oooRefuseLive(long sessionID) {
                if (sessionID == LiveConstants.mOOOSessionID) {
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveHangUp, sessionID);
                }
            }

            @Override
            public void oooHangupCall(long sessionID, OOOHangupReturn hangupInfo) {

                /*if (sessionID == LiveConstants.mOOOSessionID) {
                    //挂断原因：1对方主动挂断；2对方掉线；3余额不足
                    OOOLiveHangUpBean hangUpBean = new OOOLiveHangUpBean();
                    hangUpBean.callTime = hangupInfo.callTime;
                    hangUpBean.totalCoin = (int) hangupInfo.totalCoin;
                    hangUpBean.sessionID = sessionID;
                    hangUpBean.uid = hangupInfo.callUpUid;

                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOCallEnd, hangUpBean);
                    LiveConstants.mOOOSessionID = 0;
                }*/

            }

            @Override
            public void onGirlUserToMaleUser(ApiPushChat pushChat) {

            }


            @Override
            public void onOtherMsg(Object obj) {

            }
        });
        IMUtil.addReceiver("oooLiveService", new IMRcvOTMLive() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void cancelInvite(long sessionID) {

            }

            @Override
            public void inviteYouToChat(ApiUserInfo userInfo, long sessionID, long TimeOutMilliSecond, int isVideo, long feeUid, int userStatus, double oooFee) {

            }

            @Override
            public void agreeLive(OOOReturn info) {

            }

            @Override
            public void kickOutRoom(long assisId, long sessionID, OOOHangupReturn hangupInfo) {

            }

            @Override
            public void upVolumeOperation(long sessionID, OOOVolumeRet volumeRet) {
                /*if (LiveConstants.mOOOSessionID == sessionID) {

                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OneVoiceMike, volumeRet);

                }*/
            }

            @Override
            public void hangupCall(long sessionID, OOOHangupReturn hangupInfo) {

            }

            @Override
            public void runningOutOfCoin(long sessionID, int times) {
            }

            @Override
            public void otmInviteEnd(long sessionID, int reason) {

            }


            @Override
            public void otmUptUserCoin(long sessionID, double coin) {
//                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOUpDataGold, coin);
            }

            @Override
            public void refuseLive(long sessionID) {

            }

            @Override
            public void hangupCallUser(long sessionID, OOOHangupReturn hangupInfo) {

            }
        });

        IMUtil.addReceiver("ooonUserGetNoReadAll", new IMRcvLiveMsgSend() {
            @Override
            public void onUserBan(long sessionID, String banInfo) {
                // 封禁用户
                AccountDisableEvent accountDisableEvent = new AccountDisableEvent();
                accountDisableEvent.obj = banInfo;
                EventBus.getDefault().post(accountDisableEvent);
            }

            @Override
            public void onRoomBan(long sessionID, String banInfo) {
                // 封禁房间
                AccountDisableEvent accountDisableEvent = new AccountDisableEvent();
                accountDisableEvent.obj = banInfo;
                EventBus.getDefault().post(accountDisableEvent);
            }

            @Override
            public void onSimpleMsgRoom(ApiSimpleMsgRoom apiSimpleMsgRoom) {
                Log.e(">>>", "" + apiSimpleMsgRoom.content);
            }

            @Override
            public void onUserSendMsgRoom(ApiSendMsgRoom apiSendMsgRoom) {

            }

            @Override
            public void onUserSendApiJoinRoom(AppJoinRoomVO apiJoinRoom) {

            }


            @Override
            public void onSimpleMsgAll(ApiSimpleMsgRoom apiSimpleMsgRoom) {

            }

            @Override
            public void onAppointUserSend(ApiSendMsgRoom apiSendMsgRoom) {

            }

            @Override
            public void onUserNoticMsg(String conetnt) {

            }


            @Override
            public void onUserUpLiveTypeExitRoom(ApiExitRoom apiExitRoom) {

            }

            @Override
            public void onUserTimmerRoomRemind(int times) {

            }

            @Override
            public void onTimerExitRoom(ApiTimerExitRoom apiTimerExitRoom) {

            }

            @Override
            public void onOtherMsg(Object o) {

            }
        });
        IMUtil.addReceiver("ooonUserGetNoReadAll", new IMRcvDynamiccircleSend() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUserVideoCommentCount(ApiSendVideoUnReadNumber apiSendVideoUnReadNumber) {
                //动态评论数发生变化
            }
        });
        IMUtil.addReceiver("ooonUserGetNoReadAll", new IMRcvShortVideoSend() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUserShortVideoCommentCount(ApiSendVideoUnReadNumber apiSendVideoUnReadNumber) {

            }
        });
        IMUtil.addReceiver("ooonUserGetNoReadAll", new IMRcvCommonMsgSend() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUserGetNoReadAll(int count) {
                HttpApiChatRoom.getAppSystemNoRead(new HttpApiCallBack<ApiNoRead>() {
                    @Override
                    public void onHttpRet(int code, String msg, ApiNoRead retModel) {
                        if (code == 1) {
                            EventBus.getDefault().post(new UnReadCountEvent(retModel.totalNoRead, retModel.systemNoRead, retModel.videoNoRead, retModel.shortVideoNoRead, retModel.officialNewsNoRead));
                        }
                    }
                });
            }
        });
        IMUtil.addReceiver("ooonUserGetNoReadAll", new IMRcvGradeRightMsgSender() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onElasticFrameUpgrade(ApiElasticFrame elasticFrame) {

            }

            @Override
            public void onElasticFrameFinshTask(ApiElasticFrame elasticFrame) {

            }

            @Override
            public void onElasticFrameMedal(ApiElasticFrame elasticFrame) {

            }
        });


        IMUtil.addReceiver("All", new IMRcvLiveMsgAllSend() {
            @Override
            public void onMsgAll(ApiGiftSender apiGiftSender) {
                //权限开了才能显示
                if (FloatPermissionManager.getInstance().applyFloatWindow(mContext)) {
                    if (!isBackstage) {
                        AllFloatingScreenComponent.getInstance().getAllGift(mContext, apiGiftSender);
                    }
                }
            }

            @Override
            public void onMsgAllForBroadCast(long roomId, ApiSimpleMsgRoom apiSimpleMsgRoom) {
                //权限开了才能显示  全站广播
                if (FloatPermissionManager.getInstance().applyFloatWindow(mContext)) {
                    if (!isBackstage) {
                        AllFloatingScreenComponent.getInstance().getBroadCastData(mContext, apiSimpleMsgRoom);
                    }
                }
            }

            @Override
            public void onOtherMsg(Object o) {

            }
//            @Override
//            public void onUserRechargeCallbackMsg(double coin, ApiUserInfo user) {
//                //权限开了才能显示
//                if (FloatPermissionManager.getInstance().applyFloatWindow(mContext)) {
//                    if (!isBackstage) {
//                        RechargeBean bean = new RechargeBean();
//                        bean.coin = coin;
//                        bean.apiUserInfo = user;
//                        AllFloatingScreenComponent.getInstance().getRecharge(mContext, bean);
//                    }
//                }
//            }
//
//            @Override
//            public void onAnchorAuthUser(ApiUserInfo user) {
//
//            }
//
//            @Override//抽奖socket
//            public void onUserWinAPrize(GameUserWinAwardsDTO gameUserWinAwardsDTO) {
//                //权限开了才能显示
//                if (FloatPermissionManager.getInstance().applyFloatWindow(mContext)) {
//                    if (!isBackstage) {
//
//                        AllFloatingScreenComponent.getInstance().getLuckDrawData(mContext, gameUserWinAwardsDTO);
//                    }
//                }
//            }
//
//            @Override
//            public void onMsgAllShortVideo(ApiGiftSender apiGiftSender) {
//
//            }
//
//            @Override
//            public void onElasticFrameMember(ApiElasticFrame elasticFrame) {
//                //权限开了才能显示
//                if (FloatPermissionManager.getInstance().applyFloatWindow(mContext)) {
//                    if (!isBackstage) {
//                        AllFloatingScreenComponent.getInstance().getVipData(mContext, elasticFrame);
//                    }
//                }
//
//            }
//
//            @Override
//            public void onGirlUserToMaleUser(ApiPushChat pushChat) {
//                //权限开了才能显示
//                if (LiveConstants.isAllDisplayRobChat) {
//                    if (!LiveConstants.isDisplayRobChat) {
//                        if (FloatPermissionManager.getInstance().applyFloatWindow(mContext)) {
//                            if (!isBackstage) {
//                                if (SpUtil.getInstance().getModel("UserInfo", ApiUserInfo.class).sex != 1) {
//                                    if (robChatWowenDialogFragment != null) {
//                                        robChatWowenDialogFragment.dismiss();
//                                        robChatWowenDialogFragment = null;
//                                    }
//                                    robChatWowenDialogFragment = new RobChatWowenDialogFragment();
//                                    robChatWowenDialogFragment.show(mContext, pushChat);
//                                }
//                            } else {
//                                if (robChatWowenDialogFragment != null) {
//                                    robChatWowenDialogFragment.dismiss();
//                                    robChatWowenDialogFragment = null;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
        });

        IMUtil.addReceiver("All", new IMRcvOOOLive() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void oooInviteYouToChat(ApiUserInfo userInfo, long sessionID, long TimeOutMilliSecond, int isVideo, long feeUid, int userStatus, double oooFee) {

            }

            @Override
            public void oooCancelInvite(long sessionID) {

            }

            @Override
            public void oooRefuseLive(long sessionID) {

            }

            @Override
            public void oooAgreeLive(OOOReturn info) {

            }

            @Override
            public void oooHangupCall(long sessionID, OOOHangupReturn hangupInfo) {

            }

            @Override
            public void onGirlUserToMaleUser(ApiPushChat pushChat) {
                //权限开了才能显示
                if ((boolean) SpUtil.getInstance().getSharedPreference(SpUtil.STATIC_GRAD_CHAT, false)) {
                    if (!LiveConstants.isDisplayRobChat) {
                        if (FloatPermissionManager.getInstance().applyFloatWindow(mContext)) {
                            if (!isBackstage) {
                                if (SpUtil.getInstance().getModel("UserInfo", ApiUserInfo.class).sex != 1) {
                                    if (robChatWowenDialogFragment != null) {
                                        robChatWowenDialogFragment.dismiss();
                                        robChatWowenDialogFragment = null;
                                    }
                                    robChatWowenDialogFragment = new RobChatWowenDialogFragment();
                                    robChatWowenDialogFragment.show(mContext, pushChat);
                                }
                            } else {
                                if (robChatWowenDialogFragment != null) {
                                    robChatWowenDialogFragment.dismiss();
                                    robChatWowenDialogFragment = null;
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void oooInviteEnd(long sessionID, int resion) {

            }
        });
        IMUtil.addReceiver("All", new IMRcvAnchorMsgSender() {
            @Override
            public void onAnchorAuthUser(ApiUserInfo user) {

            }

            @Override
            public void onOtherMsg(Object o) {

            }
        });

        IMUtil.addReceiver("All", new IMRcvLiveMoneyMsgAllSend() {
            @Override
            public void onUserRechargeCallbackMsg(double coin, ApiUserInfo user) {
                //权限开了才能显示
                if (FloatPermissionManager.getInstance().applyFloatWindow(mContext)) {
                    if (!isBackstage) {
                        RechargeBean bean = new RechargeBean();
                        bean.coin = coin;
                        bean.apiUserInfo = user;
                        AllFloatingScreenComponent.getInstance().getRecharge(mContext, bean);
                    }
                }
            }

            @Override
            public void onElasticFrameMember(ApiElasticFrame elasticFrame) {
                //权限开了才能显示
                if (FloatPermissionManager.getInstance().applyFloatWindow(mContext)) {
                    if (!isBackstage) {
                        AllFloatingScreenComponent.getInstance().getVipData(mContext, elasticFrame);
                    }
                }

            }

            @Override
            public void onOtherMsg(Object o) {

            }
        });

        IMUtil.addReceiver("All", new IMRcvLiveGameMsgAllSend() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override//抽奖socket
            public void onUserWinAPrize(GameUserWinAwardsDTO gameUserWinAwardsDTO) {
                //权限开了才能显示
                if (FloatPermissionManager.getInstance().applyFloatWindow(mContext)) {
                    if (!isBackstage) {

                        AllFloatingScreenComponent.getInstance().getLuckDrawData(mContext, gameUserWinAwardsDTO);
                    }
                }
            }
        });


        //多人语音
        IMUtil.addReceiver("voiceLive", new IMRcvVoiceInvite() {

            @Override//主播邀请
            public void invtUserUpAssitan(AppJoinRoomVO joinRoom, ApiUserInfo inviteInfo, int isPay) {
                VoiceAnchorInvitationDialogFragment voiceAnchorInvitationDialogFragment = new VoiceAnchorInvitationDialogFragment(mContext);
                voiceAnchorInvitationDialogFragment.show(joinRoom, inviteInfo, isPay);
            }

            @Override
            public void refuseVoice(long roomID) {

            }

            @Override
            public void acceptVoice(long roomID) {

            }

            @Override
            public void invtTimeOut(long sendInvtUid) {

            }


            @Override
            public void onOtherMsg(Object o) {

            }
        });

        IMUtil.addReceiver("voiceLive", new IMRcvLiveSend() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUserBackground(String voicethumb) {

            }

            @Override
            public void onUsersVIPSeats(ApiUserSeats apiUserSeats) {

            }

            @Override
            public void onAnchorLeaveRoom(ApiLeaveRoomAnchor ApiLeaveRoomAnchor) {

            }

            @Override
            public void onAnchorJoinRoom(ApiJoinRoomAnchor apiJoinRoomAnchor) {

            }

            @Override
            public void onCloseLive(ApiCloseLive apiCloseLive) {

            }

            @Override
            public void onManageLeaveRoom(ApiCloseLive apiCloseLive) {

            }


            @Override
            public void onManageKickRoom(ApiKickLive apiKickLive) {

            }

            @Override
            public void onUserLeaveRoom(ApiLeaveRoom apiLeaveRoom) {

            }

            @Override
            public void onBuyGuardListRoom(List<GuardUserDto> list) {

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

        });


        IMUtil.addReceiver("voiceLive", new IMRcvShopMsgSend() {
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

        IMUtil.addReceiver("voiceLive", new IMRcvUserMsgSender() {

            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUsersBeautifulNumber(ApiBeautifulNumber user) {

            }

        });

    }

    //报告后台现在所在的位置
//    public void setLiveAddress() {
////        if (!TextUtils.isEmpty(HttpClient.getToken())) {
////            IMApiSocketStatus imApiSocketStatus = new IMApiSocketStatus();
////            imApiSocketStatus.init(IMUtil.getClient());
////            imApiSocketStatus.reportStatus(LiveConstants.LiveAddress);
////            handler.sendEmptyMessageDelayed(1, 20000);
////        } else {
////            delete();
////        }
//    }

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 1:
//                    setLiveAddress();
//                    break;
//            }
//        }
//    };

//    public void setSendLiveAddressSocket() {
//        setLiveAddress();
//    }

    //让悬浮在推到后台不显示
    public void clean() {
        isBackstage = true;
        AllFloatingScreenComponent.getInstance().clean();
    }

    //从后台进入到应用
    public void closeBackstage() {
        if (isBackstage) {
            isBackstage = false;
        }
    }

    //删除好使操作
    public void delete() {
//        handler.removeMessages(1);
        IMUtil.removeReceiver("oooSvipLiveService");
        IMUtil.removeReceiver("oooLiveService");
        IMUtil.removeReceiver("All");
        IMUtil.removeReceiver("voiceLive");
    }
}
