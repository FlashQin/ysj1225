package com.kalacheng.commonview.component;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import com.kalacheng.base.base.BaseMVVMViewHolder;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buscommon.model.GuardUserDto;
import com.kalacheng.buscommon.socketmsg.IMRcvCommonMsgSend;
import com.kalacheng.busdynamiccircle.socketmsg.IMRcvDynamiccircleSend;
import com.kalacheng.busfinance.socketmsg.IMRcvLiveMoneyMsgAllSend;
import com.kalacheng.busgraderight.socketmsg.IMRcvGradeRightMsgSender;
import com.kalacheng.buslivebas.socketmsg.IMRcvLiveMsgAllSend;
import com.kalacheng.buslivebas.socketmsg.IMRcvLiveMsgSend;
import com.kalacheng.buslivebas.socketmsg.IMRcvLiveSend;
import com.kalacheng.busnobility.model.ApiPkResult;
import com.kalacheng.busnobility.socketmsg.IMRcvLiveGiftSend;
import com.kalacheng.busooolive.model.OOOHangupReturn;
import com.kalacheng.busooolive.model.OOOReturn;
import com.kalacheng.busooolive.socketmsg.IMRcvOOOLive;
import com.kalacheng.buspersonalcenter.socketmsg.IMRcvUserMsgSender;
import com.kalacheng.bususer.socketmsg.IMRcvAnchorMsgSender;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.databinding.FloatingScreenBinding;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.game.socketmsg.IMRcvLiveGameMsgAllSend;
import com.kalacheng.libuser.model.ApiBeautifulNumber;
import com.kalacheng.libuser.model.ApiCloseLive;
import com.kalacheng.libuser.model.ApiElasticFrame;
import com.kalacheng.libuser.model.ApiExitRoom;
import com.kalacheng.libuser.model.ApiGiftSender;
import com.kalacheng.libuser.model.ApiJoinRoomAnchor;
import com.kalacheng.libuser.model.ApiKickLive;
import com.kalacheng.libuser.model.ApiLeaveRoom;
import com.kalacheng.libuser.model.ApiLeaveRoomAnchor;
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
import com.kalacheng.commonview.dialog.VipGiftDialogFragment;
import com.kalacheng.commonview.toast.BeautifulNumToastView;
import com.kalacheng.util.view.MarqueeView;
import com.kalacheng.commonview.toast.MedalToastView;
import com.kalacheng.commonview.toast.TaskToastView;
import com.kalacheng.commonview.toast.UpgradeToastView;
import com.kalacheng.commonview.viewmodel.FloatingScreenViewModel;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.StringShowUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.wengying666.imsocket.IMUtil;
import com.wengying666.imsocket.SocketClient;

import java.util.ArrayList;
import java.util.List;


/**
 * 直播间飘屏
 */
public class FloatingScreenDialogComponent extends BaseMVVMViewHolder<FloatingScreenBinding, FloatingScreenViewModel> implements LiveBundle.onLiveSocket {
    private int mDp500;


    //普通礼物
    private boolean giftshowEnd = true;
    private boolean giftshow = false;
    private boolean giftshowEnd2 = true;
    private boolean giftshow2 = false;
    private boolean giftshowEnd3 = true;
    private boolean giftshow3 = false;
    List<ApiGiftSender> OrdinaryGiftList = new ArrayList<>();

    //守护礼物
    private boolean GuardgiftshowEnd = true;
    private boolean Guardgiftshow = false;
    List<ApiGiftSender> GuardGiftList = new ArrayList<>();

    //粉丝礼物
    private boolean FansgiftshowEnd = true;
    private boolean Fansgiftshow = false;
    List<ApiGiftSender> FansGiftList = new ArrayList<>();

    //贵族礼物
    private boolean NoblegiftshowEnd = true;
    private boolean Noblegiftshow = false;
    List<ApiGiftSender> NobleGiftList = new ArrayList<>();

    //vip进入房间
    private boolean VipJoinRoom = true;
    List<ApiSimpleMsgRoom> vipJoinroomList = new ArrayList<>();

    //土豪进场
    private boolean welcomeAnimatorShow = true;
    List<ApiSimpleMsgRoom> welcomeList = new ArrayList<>();

    //普通用户 进场
    private boolean baseUserWelcomeShow = true;
    List<ApiSimpleMsgRoom> baseUserWelcomeList = new ArrayList<>();

    //坐骑
    private boolean Mountsshow = true;
    List<AppJoinRoomVO> MountsList = new ArrayList<>();

    //任务
    private boolean Taskshow = true;
    List<ApiElasticFrame> TaskList = new ArrayList<>();

    //升级
    private boolean Upgradeshow = true;
    List<ApiElasticFrame> UpgradeList = new ArrayList<>();

    //勋章
    private boolean Medalshow = true;
    List<ApiElasticFrame> MedalList = new ArrayList<>();

    //直播购购买商品
    private boolean buyGoodsShow = false;
    List<UserBuyDTO> buyGoodsList = new ArrayList<>();

    // 送礼特效 弹窗
    private VipGiftDialogFragment vipGiftDialog;


    private Context mContext;

    public FloatingScreenDialogComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
        this.mContext = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.floating_screen;
    }

    @Override
    protected void init() {
        addToParent();
        mDp500 = DpUtil.dp2px(DpUtil.getScreenWidth());
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ExitRoom, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                clean();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //一对一关闭
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOCloseLive, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                clean();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        LiveBundle.getInstance().addLiveSocketListener(this);
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.RoomInfoList, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {

                viewModel.joinRoom.set((AppJoinRoomVO) o);


            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });


    }

    @Override
    public void init(String groupName, SocketClient socketClient) {

        IMUtil.addReceiver(groupName, new IMRcvLiveMsgSend() {

            @Override
            public void onUserSendMsgRoom(ApiSendMsgRoom apiSendMsgRoom) {
                Log.i("aaa", "apiSendMsgRoom");
            }

            @Override
            public void onUserSendApiJoinRoom(AppJoinRoomVO apiJoinRoom) {

            }


            @Override
            public void onSimpleMsgRoom(ApiSimpleMsgRoom apiSimpleMsgRoom) {
                try {
                    if (apiSimpleMsgRoom.type == 13) {

                    } else if (apiSimpleMsgRoom.type == 1) {
                        if (apiSimpleMsgRoom.isVip == 1) {
                            //vip 1:是
                            vipJoinroomList.add(apiSimpleMsgRoom);
                            if (Upgradeshow) {
                                GuardianEntry(vipJoinroomList.get(0));
                                VipJoinRoom = false;
                            }
                        } else {
                            if (apiSimpleMsgRoom.isSh == 1) {
                                //守护 1:是
                                vipJoinroomList.add(apiSimpleMsgRoom);
                                if (Upgradeshow) {
                                    GuardianEntry(vipJoinroomList.get(0));
                                    VipJoinRoom = false;
                                }
                            } else {
                                if (apiSimpleMsgRoom.isFans == 1) {
                                    //粉丝 1:是
                                    vipJoinroomList.add(apiSimpleMsgRoom);
                                    if (Upgradeshow) {
                                        GuardianEntry(vipJoinroomList.get(0));
                                        VipJoinRoom = false;
                                    }
                                } else {
                                    //普通用户 进场
                                    baseUserWelcomeList.add(apiSimpleMsgRoom);
                                    if (baseUserWelcomeShow) {
                                        baseUserWelcomeEntry(baseUserWelcomeList.get(0));
                                        baseUserWelcomeShow = false;
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onSimpleMsgAll(ApiSimpleMsgRoom apiSimpleMsgRoom) {
                if (apiSimpleMsgRoom.roomId == 0 || LiveConstants.ROOMID == apiSimpleMsgRoom.roomId) {
                    getNoticeAnimator(apiSimpleMsgRoom.content);

                    binding.noticeContent.setFocusable(true);
                    binding.noticeContent.requestFocus();
                    //设置滚动文字
                    binding.noticeContent.setText(apiSimpleMsgRoom.content);
                    //开始滚动
                    binding.noticeContent.startScroll();
                    binding.noticeContent.setOnMargueeListener(new MarqueeView.OnMargueeListener() {
                        @Override
                        public void onRollOver() {
                            if (noticeEndAnimator != null) {
                                noticeEndAnimator.start();
                            }
                        }
                    });
                }
            }

            @Override
            public void onAppointUserSend(ApiSendMsgRoom apiSendMsgRoom) {

            }

            @Override
            public void onUserNoticMsg(String conetnt) {


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

            @Override
            public void onUserUpLiveTypeExitRoom(ApiExitRoom apiExitRoom) {

            }

            @Override
            public void onUserTimmerRoomRemind(int times) {

            }

            @Override
            public void onOtherMsg(Object obj) {

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
            public void onElasticFrameUpgrade(ApiElasticFrame elasticFrame) {
                if (elasticFrame.type == 1) {
                    UpgradeList.add(elasticFrame);
                    if (Upgradeshow) {
                        UpgradeAnimator(UpgradeList.get(0));
                        Upgradeshow = false;
                    }
                }

            }

            @Override//任务
            public void onElasticFrameFinshTask(ApiElasticFrame elasticFrame) {
                if (elasticFrame.type == 2) {
                    TaskList.add(elasticFrame);
                    if (Taskshow) {
                        TaskAnimator(TaskList.get(0));
                        Taskshow = false;
                    }
                }
            }

            @Override
            public void onElasticFrameMedal(ApiElasticFrame elasticFrame) {
                if (elasticFrame.type == 3) {
                    MedalList.add(elasticFrame);
                    if (Medalshow) {
                        MedalAnimator(MedalList.get(0));
                        Medalshow = false;
                    }
                }

            }
        });

        IMUtil.addReceiver(groupName, new IMRcvLiveSend() {

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
            public void onCloseLive(ApiCloseLive apiCloseLive) {

            }

            @Override
            public void onUserLeaveRoom(ApiLeaveRoom apiLeaveRoom) {

            }

            @Override
            public void onUserJoinRoom(AppJoinRoomVO apiJoinRoom) {
                if (apiJoinRoom != null && apiJoinRoom.carThumb != null) {
                    MountsList.add(apiJoinRoom);
                    if (Mountsshow) {
                        MountsAnimator(apiJoinRoom);
                        Mountsshow = false;
                    }
                }

            }

            @Override
            public void onJoinRoomMsgRoom(ApiSimpleMsgRoom apiSimpleMsgRoom) {

                // 土豪进场
                welcomeEntry(apiSimpleMsgRoom);
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

            }

            @Override
            public void onManageKickRoom(ApiKickLive apiKickLive) {

            }


            @Override
            public void onOtherMsg(Object obj) {

            }
        });

        IMUtil.addReceiver(groupName, new IMRcvShopMsgSend() {
            @Override
            public void onUsersShopBanner(String shopLiveBanner) {

            }

            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onBuyGoodsRoom(UserBuyDTO userBuyDTO) {
                if (buyGoodsShow) {
                    buyGoodsList.add(userBuyDTO);

                } else {
                    LiveBuyGoods(userBuyDTO);
                    buyGoodsShow = true;
                }
            }

            @Override
            public void onUsersLiveGoodsStatus(ApiShopLiveGoods apiShopLiveGoods) {

            }
        });

        IMUtil.addReceiver(groupName, new IMRcvUserMsgSender() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUsersBeautifulNumber(ApiBeautifulNumber user) {
                BeautifulNumToastView.showToast(mContext, user);
            }
        });

        IMUtil.addReceiver(groupName, new IMRcvLiveGiftSend() {
            @Override
            public void onSimpleGiftMsgRoom(ApiSimpleMsgRoom apiSimpleMsgRoom) {
                Log.i("aaa", "apiSimpleMsgRoom");
            }

            @Override
            public void onGiftMsgAll(ApiGiftSender apiGiftSender) {
                if (apiGiftSender.sendGiftPrivilege == 1) {
//                    String Type = String.valueOf(apiGiftSender.userId) + String.valueOf(apiGiftSender.anchorId) + String.valueOf(apiGiftSender.giftId);
//                    vipGiftAgain = Type.equals(UserIDAndGiftID6)? true : false;
//                    getVipGiftAgainNumber(apiGiftSender.giftNumber);
//                    getVipGiftAnimmator(apiGiftSender);
                    showVipGiftDialog(apiGiftSender);
                }

            }

            @Override
            public void onGiveGiftUser(ApiGiftSender apiGiftSender) {
                if (apiGiftSender.sendGiftPrivilege == 1) {
                    showVipGiftDialog(apiGiftSender);
                }
                if (apiGiftSender.type == 0 || apiGiftSender.type == 4) {
                    String Type = String.valueOf(apiGiftSender.userId) + String.valueOf(apiGiftSender.anchorId) + String.valueOf(apiGiftSender.giftId);

                    if (Type.equals(UserIDAndGiftID1)) {
                        OrdinaryNoStopGive = true;
                        getOrdinaryGiftNumber(apiGiftSender.giftNumber);

                    } else if (Type.equals(UserIDAndGiftID2)) {
                        OrdinaryNoStopGive2 = true;
                        getOrdinaryGiftNumber2(apiGiftSender.giftNumber);

                    } else if (Type.equals(UserIDAndGiftID3)) {
                        OrdinaryNoStopGive3 = true;
                        getOrdinaryGiftNumber3(apiGiftSender.giftNumber);

                    } else {
                        if (OrdinaryGiftList.size() > 0) {
                            boolean joinList = true;
                            for (int i = 0; i < OrdinaryGiftList.size(); i++) {
                                if (OrdinaryGiftList.get(i).userId == apiGiftSender.userId && OrdinaryGiftList.get(i).giftId == apiGiftSender.giftId) {
                                    OrdinaryGiftList.get(i).giftNumber = OrdinaryGiftList.get(i).giftNumber + apiGiftSender.giftNumber;
                                    joinList = false;
                                    break;
                                }
                            }
                            if (joinList) {
                                OrdinaryGiftList.add(apiGiftSender);
                            }
                        } else {
                            OrdinaryGiftList.add(apiGiftSender);
                        }
                    }


                    if (giftshowEnd) {
                        if (OrdinaryGiftList.size() > 0) {
                            getGiftAnimmator(OrdinaryGiftList.get(0));
                            OrdinaryGiftList.remove(0);
                            giftshowEnd = false;
                        }
                    } else if (!giftshowEnd && giftshowEnd2) {
                        if (OrdinaryGiftList.size() > 0) {
                            getGiftAnimmator2(OrdinaryGiftList.get(0));
                            OrdinaryGiftList.remove(0);
                            giftshowEnd2 = false;
                        }
                    } else if (!giftshowEnd && !giftshowEnd2 && giftshowEnd3) {
                        if (OrdinaryGiftList.size() > 0) {
                            getGiftAnimmator3(OrdinaryGiftList.get(0));
                            OrdinaryGiftList.remove(0);
                            giftshowEnd3 = false;
                        }
                    }


                } else if (apiGiftSender.type == 1) {
                    if (Fansgiftshow) {
                        if (FansGiftList.get(0).userId == apiGiftSender.userId && FansGiftList.get(0).giftId == apiGiftSender.giftId) {
                            FansNoStopGive = true;
                            getFansGiftNumber(apiGiftSender.giftNumber);
                        } else {
                            if (FansGiftList.size() > 0) {
                                boolean joinList = true;
                                for (int i = 0; i < FansGiftList.size(); i++) {
                                    if (FansGiftList.get(i).userId == apiGiftSender.userId && FansGiftList.get(i).giftId == apiGiftSender.giftId) {
                                        FansGiftList.get(i).giftNumber = FansGiftList.get(i).giftNumber + apiGiftSender.giftNumber;
                                        joinList = false;
                                        break;
                                    }
                                }
                                if (joinList) {
                                    FansGiftList.add(apiGiftSender);
                                }
                            }
                        }

                    } else {
                        if (FansGiftList.size() > 0) {
                            boolean joinList = true;
                            for (int i = 0; i < FansGiftList.size(); i++) {
                                if (FansGiftList.get(i).userId == apiGiftSender.userId && FansGiftList.get(i).giftId == apiGiftSender.giftId) {
                                    FansGiftList.get(i).giftNumber = FansGiftList.get(i).giftNumber + apiGiftSender.giftNumber;
                                    joinList = false;
                                    break;
                                }
                            }
                            if (joinList) {
                                FansGiftList.add(apiGiftSender);
                            }
                        } else {
                            FansGiftList.add(apiGiftSender);
                        }
                        if (FansgiftshowEnd) {
                            getFansAnimmator(FansGiftList.get(0));
                            FansgiftshowEnd = false;
                        }
                    }


                } else if (apiGiftSender.type == 2) {
                    if (Guardgiftshow) {
                        if (GuardGiftList.get(0).userId == apiGiftSender.userId && GuardGiftList.get(0).giftId == apiGiftSender.giftId) {
                            GuardNoStopGive = true;
                            getGuardGiftNumber(apiGiftSender.giftNumber);
                        } else {
                            if (GuardGiftList.size() > 0) {
                                boolean joinList = true;
                                for (int i = 0; i < GuardGiftList.size(); i++) {
                                    if (GuardGiftList.get(i).userId == apiGiftSender.userId && GuardGiftList.get(i).giftId == apiGiftSender.giftId) {
                                        GuardGiftList.get(i).giftNumber = GuardGiftList.get(i).giftNumber + apiGiftSender.giftNumber;
                                        joinList = false;
                                        break;
                                    }
                                }
                                if (joinList) {
                                    GuardGiftList.add(apiGiftSender);
                                }
                            }
                        }

                    } else {
                        if (GuardGiftList.size() > 0) {
                            boolean joinList = true;
                            for (int i = 0; i < GuardGiftList.size(); i++) {
                                if (GuardGiftList.get(i).userId == apiGiftSender.userId && GuardGiftList.get(i).giftId == apiGiftSender.giftId) {
                                    GuardGiftList.get(i).giftNumber = GuardGiftList.get(i).giftNumber + apiGiftSender.giftNumber;
                                    joinList = false;
                                    break;
                                }
                            }
                            if (joinList) {
                                GuardGiftList.add(apiGiftSender);
                            }
                        } else {
                            GuardGiftList.add(apiGiftSender);
                        }
                        if (GuardgiftshowEnd) {
                            getGuardAnimmator(GuardGiftList.get(0));
                            GuardgiftshowEnd = false;
                        }
                    }
                } else if (apiGiftSender.type == 3) {
                    if (Noblegiftshow) {
                        if (NobleGiftList.get(0).userId == apiGiftSender.userId && NobleGiftList.get(0).giftId == apiGiftSender.giftId) {
                            NobleNoStopGive = true;
                            getNobleGiftNumberNumber(apiGiftSender.giftNumber);
                        } else {
                            if (NobleGiftList.size() > 0) {
                                boolean joinList = true;
                                for (int i = 0; i < NobleGiftList.size(); i++) {
                                    if (NobleGiftList.get(i).userId == apiGiftSender.userId && NobleGiftList.get(i).giftId == apiGiftSender.giftId) {
                                        NobleGiftList.get(i).giftNumber = NobleGiftList.get(i).giftNumber + apiGiftSender.giftNumber;
                                        joinList = false;
                                        break;
                                    }
                                }
                                if (joinList) {
                                    NobleGiftList.add(apiGiftSender);
                                }
                            }
                        }

                    } else {
                        if (NobleGiftList.size() > 0) {
                            boolean joinList = true;
                            for (int i = 0; i < NobleGiftList.size(); i++) {
                                if (NobleGiftList.get(i).userId == apiGiftSender.userId && NobleGiftList.get(i).giftId == apiGiftSender.giftId) {
                                    NobleGiftList.get(i).giftNumber = NobleGiftList.get(i).giftNumber + apiGiftSender.giftNumber;
                                    joinList = false;
                                    break;
                                }
                            }
                            if (joinList) {
                                NobleGiftList.add(apiGiftSender);
                            }
                        } else {
                            NobleGiftList.add(apiGiftSender);
                        }
                        if (NoblegiftshowEnd) {
                            NobleGiftAnimator(NobleGiftList.get(0));
                            NoblegiftshowEnd = false;
                        }
                    }

                }
            }

            @Override
            public void onGiftPKResult(ApiPkResult apiPKResult) {
                Log.i("aaa", "apiPKResult");
            }

            @Override
            public void onGiveGift(ApiGiftSender apiGiftSender) {
                // 直播间内消息
                // 类型0普通礼物，1粉丝团(豪华礼物)，2守护(专属礼物)，3贵族礼物(特殊礼物)4背包礼物
                if (apiGiftSender.sendGiftPrivilege == 1) {
                    showVipGiftDialog(apiGiftSender);
                }
                if (apiGiftSender.type == 0 || apiGiftSender.type == 4) {
                    String Type = String.valueOf(apiGiftSender.userId) + String.valueOf(apiGiftSender.anchorId) + String.valueOf(apiGiftSender.giftId);

                    if (Type.equals(UserIDAndGiftID1)) {
                        OrdinaryNoStopGive = true;
                        getOrdinaryGiftNumber(apiGiftSender.giftNumber);

                    } else if (Type.equals(UserIDAndGiftID2)) {
                        OrdinaryNoStopGive2 = true;
                        getOrdinaryGiftNumber2(apiGiftSender.giftNumber);

                    } else if (Type.equals(UserIDAndGiftID3)) {
                        OrdinaryNoStopGive3 = true;
                        getOrdinaryGiftNumber3(apiGiftSender.giftNumber);

                    } else {
                        if (OrdinaryGiftList.size() > 0) {
                            boolean joinList = true;
                            for (int i = 0; i < OrdinaryGiftList.size(); i++) {
                                if (OrdinaryGiftList.get(i).userId == apiGiftSender.userId && OrdinaryGiftList.get(i).giftId == apiGiftSender.giftId) {
                                    OrdinaryGiftList.get(i).giftNumber = OrdinaryGiftList.get(i).giftNumber + apiGiftSender.giftNumber;
                                    joinList = false;
                                    break;
                                }
                            }
                            if (joinList) {
                                OrdinaryGiftList.add(apiGiftSender);
                            }
                        } else {
                            OrdinaryGiftList.add(apiGiftSender);
                        }
                    }


                    if (giftshowEnd) {
                        if (OrdinaryGiftList.size() > 0) {
                            getGiftAnimmator(OrdinaryGiftList.get(0));
                            OrdinaryGiftList.remove(0);
                            giftshowEnd = false;
                        }
                    } else if (!giftshowEnd && giftshowEnd2) {
                        if (OrdinaryGiftList.size() > 0) {
                            getGiftAnimmator2(OrdinaryGiftList.get(0));
                            OrdinaryGiftList.remove(0);
                            giftshowEnd2 = false;
                        }
                    } else if (!giftshowEnd && !giftshowEnd2 && giftshowEnd3) {
                        if (OrdinaryGiftList.size() > 0) {
                            getGiftAnimmator3(OrdinaryGiftList.get(0));
                            OrdinaryGiftList.remove(0);
                            giftshowEnd3 = false;
                        }
                    }


                } else if (apiGiftSender.type == 1) {
                    if (Fansgiftshow) {
                        if (FansGiftList.get(0).userId == apiGiftSender.userId && FansGiftList.get(0).giftId == apiGiftSender.giftId) {
                            FansNoStopGive = true;
                            getFansGiftNumber(apiGiftSender.giftNumber);
                        } else {
                            if (FansGiftList.size() > 0) {
                                boolean joinList = true;
                                for (int i = 0; i < FansGiftList.size(); i++) {
                                    if (FansGiftList.get(i).userId == apiGiftSender.userId && FansGiftList.get(i).giftId == apiGiftSender.giftId) {
                                        FansGiftList.get(i).giftNumber = FansGiftList.get(i).giftNumber + apiGiftSender.giftNumber;
                                        joinList = false;
                                        break;
                                    }
                                }
                                if (joinList) {
                                    FansGiftList.add(apiGiftSender);
                                }
                            }
                        }

                    } else {
                        if (FansGiftList.size() > 0) {
                            boolean joinList = true;
                            for (int i = 0; i < FansGiftList.size(); i++) {
                                if (FansGiftList.get(i).userId == apiGiftSender.userId && FansGiftList.get(i).giftId == apiGiftSender.giftId) {
                                    FansGiftList.get(i).giftNumber = FansGiftList.get(i).giftNumber + apiGiftSender.giftNumber;
                                    joinList = false;
                                    break;
                                }
                            }
                            if (joinList) {
                                FansGiftList.add(apiGiftSender);
                            }
                        } else {
                            FansGiftList.add(apiGiftSender);
                        }
                        if (FansgiftshowEnd) {
                            getFansAnimmator(FansGiftList.get(0));
                            FansgiftshowEnd = false;
                        }
                    }


                } else if (apiGiftSender.type == 2) {
                    if (Guardgiftshow) {
                        if (GuardGiftList.get(0).userId == apiGiftSender.userId && GuardGiftList.get(0).giftId == apiGiftSender.giftId) {
                            GuardNoStopGive = true;
                            getGuardGiftNumber(apiGiftSender.giftNumber);
                        } else {
                            if (GuardGiftList.size() > 0) {
                                boolean joinList = true;
                                for (int i = 0; i < GuardGiftList.size(); i++) {
                                    if (GuardGiftList.get(i).userId == apiGiftSender.userId && GuardGiftList.get(i).giftId == apiGiftSender.giftId) {
                                        GuardGiftList.get(i).giftNumber = GuardGiftList.get(i).giftNumber + apiGiftSender.giftNumber;
                                        joinList = false;
                                        break;
                                    }
                                }
                                if (joinList) {
                                    GuardGiftList.add(apiGiftSender);
                                }
                            }
                        }

                    } else {
                        if (GuardGiftList.size() > 0) {
                            boolean joinList = true;
                            for (int i = 0; i < GuardGiftList.size(); i++) {
                                if (GuardGiftList.get(i).userId == apiGiftSender.userId && GuardGiftList.get(i).giftId == apiGiftSender.giftId) {
                                    GuardGiftList.get(i).giftNumber = GuardGiftList.get(i).giftNumber + apiGiftSender.giftNumber;
                                    joinList = false;
                                    break;
                                }
                            }
                            if (joinList) {
                                GuardGiftList.add(apiGiftSender);
                            }
                        } else {
                            GuardGiftList.add(apiGiftSender);
                        }
                        if (GuardgiftshowEnd) {
                            getGuardAnimmator(GuardGiftList.get(0));
                            GuardgiftshowEnd = false;
                        }
                    }
                } else if (apiGiftSender.type == 3) {
                    if (Noblegiftshow) {
                        if (NobleGiftList.get(0).userId == apiGiftSender.userId && NobleGiftList.get(0).giftId == apiGiftSender.giftId) {
                            NobleNoStopGive = true;
                            getNobleGiftNumberNumber(apiGiftSender.giftNumber);
                        } else {
                            if (NobleGiftList.size() > 0) {
                                boolean joinList = true;
                                for (int i = 0; i < NobleGiftList.size(); i++) {
                                    if (NobleGiftList.get(i).userId == apiGiftSender.userId && NobleGiftList.get(i).giftId == apiGiftSender.giftId) {
                                        NobleGiftList.get(i).giftNumber = NobleGiftList.get(i).giftNumber + apiGiftSender.giftNumber;
                                        joinList = false;
                                        break;
                                    }
                                }
                                if (joinList) {
                                    NobleGiftList.add(apiGiftSender);
                                }
                            }
                        }

                    } else {
                        if (NobleGiftList.size() > 0) {
                            boolean joinList = true;
                            for (int i = 0; i < NobleGiftList.size(); i++) {
                                if (NobleGiftList.get(i).userId == apiGiftSender.userId && NobleGiftList.get(i).giftId == apiGiftSender.giftId) {
                                    NobleGiftList.get(i).giftNumber = NobleGiftList.get(i).giftNumber + apiGiftSender.giftNumber;
                                    joinList = false;
                                    break;
                                }
                            }
                            if (joinList) {
                                NobleGiftList.add(apiGiftSender);
                            }
                        } else {
                            NobleGiftList.add(apiGiftSender);
                        }
                        if (NoblegiftshowEnd) {
                            NobleGiftAnimator(NobleGiftList.get(0));
                            NoblegiftshowEnd = false;
                        }
                    }

                }


            }

            @Override
            public void onOtherMsg(Object obj) {

            }
        });
        IMUtil.addReceiver(groupName, new IMRcvLiveMsgAllSend() {

            @Override
            public void onMsgAll(ApiGiftSender apiGiftSender) {

            }

            @Override
            public void onMsgAllForBroadCast(long roomId, ApiSimpleMsgRoom apiSimpleMsgRoom) {

            }

            @Override
            public void onOtherMsg(Object o) {

            }

//            @Override
//            public void onUserRechargeCallbackMsg(double coin, ApiUserInfo user) {
//
//            }
//
//            @Override
//            public void onAnchorAuthUser(ApiUserInfo user) {
//
//            }
//
//            @Override//短视频送礼
//            public void onMsgAllShortVideo(ApiGiftSender apiGiftSender) {
//
//            }
//
//
//            @Override
//            public void onElasticFrameMember(ApiElasticFrame elasticFrame) {
//
//            }
//
//            @Override
//            public void onGirlUserToMaleUser(ApiPushChat pushChat) {
//
//            }
//
//            @Override
//            public void onUserWinAPrize(GameUserWinAwardsDTO gameUserWinAwardsDTO) {
//
//            }
        });

        IMUtil.addReceiver(groupName, new IMRcvOOOLive() {
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

            }

            @Override
            public void oooInviteEnd(long sessionID, int resion) {

            }
        });
        IMUtil.addReceiver(groupName, new IMRcvAnchorMsgSender() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onAnchorAuthUser(ApiUserInfo user) {

            }
        });

        IMUtil.addReceiver(groupName, new IMRcvLiveMoneyMsgAllSend() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUserRechargeCallbackMsg(double coin, ApiUserInfo user) {

            }

            @Override
            public void onElasticFrameMember(ApiElasticFrame elasticFrame) {

            }

        });

        IMUtil.addReceiver(groupName, new IMRcvLiveGameMsgAllSend() {
            @Override
            public void onUserWinAPrize(GameUserWinAwardsDTO gameUserWinAwardsDTO) {

            }

            @Override
            public void onOtherMsg(Object o) {

            }
        });

    }

    //系统公告
    private ObjectAnimator noticeStartAnimator;
    private ObjectAnimator noticeEndAnimator;

    public void getNoticeAnimator(final String content) {

        binding.noticeRe.setVisibility(View.VISIBLE);
        noticeStartAnimator = ObjectAnimator.ofFloat(binding.noticeRe, "translationX", mDp500 / 2, 0);
        noticeStartAnimator.setDuration(1000);
        noticeStartAnimator.setInterpolator(new LinearInterpolator());
        noticeStartAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {



                /*binding.noticeContent.setText(content);

                SpannableString spannableString = new SpannableString(content);
                EasySpan easySpan  =new EasySpan(mContext,binding.noticeContent);
                easySpan.setDuration(1000);
                easySpan.setEasySpanListener(new EasySpan.EasySpanListener() {
                    @Override
                    public void over() {
                        if (noticeEndAnimator!=null){
                            noticeEndAnimator.start();
                        }
                    }
                });
                spannableString.setSpan(easySpan, 0, content.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                binding.noticeContent.setText(spannableString);*/

                //直播间公告
               /* binding.noticeContent.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                binding.noticeContent.setSingleLine(true);
                binding.noticeContent.setSelected(true);
                binding.noticeContent.setFocusable(true);
                binding.noticeContent.setFocusableInTouchMode(true);

                binding.noticeContent.setOnMarqueeCompleteListener(new AlwaysMarqueeTextView.OnMarqueeCompleteListener() {
                    @Override
                    public void onMarqueeComplete() {
                        if (noticeEndAnimator!=null){
                            noticeEndAnimator.start();
                        }
                        binding.noticeContent.setSelected(false);
//                        binding.noticeRe.setVisibility(View.GONE);
                    }
                });
*/

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


        noticeEndAnimator = ObjectAnimator.ofFloat(binding.noticeRe, "translationX", -mDp500 / 2);
        noticeEndAnimator.setDuration(1000);
        noticeEndAnimator.setInterpolator(new LinearInterpolator());
        noticeEndAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                binding.noticeRe.setVisibility(View.GONE);
                noticeEndAnimator.cancel();
                noticeEndAnimator = null;
                noticeStartAnimator.cancel();
                noticeStartAnimator = null;
            }
        });

        noticeStartAnimator.start();
    }


    //粉丝团礼物
    private ObjectAnimator FansAnimator;
    private ObjectAnimator FansAnimatorEnd;
    private int CountFansGiftNumber = 0;
    private boolean FansNoStopGive = false;

    private void getFansGiftNumber(int giftNumber) {
        CountFansGiftNumber = (CountFansGiftNumber + giftNumber);
        binding.FansGiftNumber.setText("x" + CountFansGiftNumber);
    }

    public void getFansAnimmator(ApiGiftSender apiGiftSender) {
        CountFansGiftNumber = 0;
        ImageLoader.display(apiGiftSender.userAvatar, binding.FansGiftUserImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        ImageLoader.display(apiGiftSender.gifticon, binding.FansGiftGiftImage);
        binding.FansGiftUserName.setText(apiGiftSender.userName);
        binding.FansGiftAnchorName.setText(apiGiftSender.anchorName);
        getFansGiftNumber(apiGiftSender.giftNumber);
        FansAnimator = ObjectAnimator.ofFloat(binding.ReFansGift, "translationX", 12);
        FansAnimator.setDuration(1000);
        FansAnimator.setInterpolator(new LinearInterpolator());

        FansAnimatorEnd = ObjectAnimator.ofFloat(binding.ReFansGift, "translationX", 0, -1000);
        FansAnimatorEnd.setDuration(1000);
        FansAnimatorEnd.setInterpolator(new LinearInterpolator());

        FansAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Fansgiftshow = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mHandler != null) {
                    mHandler.sendEmptyMessageDelayed(2, 5000);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        FansAnimatorEnd.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Fansgiftshow = false;
                if (FansGiftList.size() != 0) {
                    FansGiftList.remove(0);
                }

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                FansAnimator.cancel();
//                FansAnimatorEnd.cancel();
                FansAnimator.removeAllListeners();
                FansAnimator = null;
                FansAnimatorEnd.removeAllListeners();
                FansAnimatorEnd = null;

                FansgiftshowEnd = true;
                if (FansGiftList.size() != 0) {
                    if (FansgiftshowEnd) {
                        getFansAnimmator(FansGiftList.get(0));
                        FansgiftshowEnd = false;
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        FansAnimator.start();
    }

    //守护礼物
    private ObjectAnimator GuardAnimator;
    private ObjectAnimator GuardAnimatorEnd;
    private int CountGuardGiftNumber = 0;
    private boolean GuardNoStopGive = false;

    private void getGuardGiftNumber(int giftNumber) {
        CountGuardGiftNumber = (CountGuardGiftNumber + giftNumber);
        binding.GuardGiftNumber.setText("x" + CountGuardGiftNumber);
    }

    public void getGuardAnimmator(ApiGiftSender apiGiftSender) {
        CountGuardGiftNumber = 0;
        ImageLoader.display(apiGiftSender.anchorAvatar, binding.GuardGiftAnchorImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        ImageLoader.display(apiGiftSender.userAvatar, binding.GuardGiftUserImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        ImageLoader.display(apiGiftSender.gifticon, binding.GuardGiftGiftImage);
        binding.GuardGiftUserName.setText(apiGiftSender.userName);
        binding.GuardGiftAnchorName.setText(apiGiftSender.anchorName);
        getGuardGiftNumber(apiGiftSender.giftNumber);
        GuardAnimator = ObjectAnimator.ofFloat(binding.ReGuardGift, "translationX", 12);
        GuardAnimator.setDuration(1000);
        GuardAnimator.setInterpolator(new LinearInterpolator());

        GuardAnimatorEnd = ObjectAnimator.ofFloat(binding.ReGuardGift, "translationX", 0, -1000);
        GuardAnimatorEnd.setDuration(1000);
        GuardAnimatorEnd.setInterpolator(new LinearInterpolator());

        GuardAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Guardgiftshow = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mHandler != null) {
                    mHandler.sendEmptyMessageDelayed(13, 5000);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        GuardAnimatorEnd.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Guardgiftshow = false;
                if (GuardGiftList.size() != 0) {
                    GuardGiftList.remove(0);
                }

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                GuardAnimator.cancel();
//                GuardAnimatorEnd.cancel();
                GuardAnimator.removeAllListeners();
                GuardAnimator = null;
                GuardAnimatorEnd.removeAllListeners();
                GuardAnimatorEnd = null;

                GuardgiftshowEnd = true;
                if (GuardGiftList.size() != 0) {
                    if (GuardgiftshowEnd) {
                        getGuardAnimmator(GuardGiftList.get(0));
                        GuardgiftshowEnd = false;
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        GuardAnimator.start();
    }

    //普通礼物
    private ObjectAnimator giftAnimator;
    private ObjectAnimator giftAnimatorEnd;
    private int CountOrdinaryGiftNumber = 0;
    private String UserIDAndGiftID1;
    private boolean OrdinaryNoStopGive = false;//判断是否连续送礼物

    private void getOrdinaryGiftNumber(int giftNumber) {
        CountOrdinaryGiftNumber = (CountOrdinaryGiftNumber + giftNumber);
        binding.GiftNumber.setText("x" + CountOrdinaryGiftNumber);
    }

    public void getGiftAnimmator(ApiGiftSender apiGiftSender) {
        UserIDAndGiftID1 = String.valueOf(apiGiftSender.userId) + String.valueOf(apiGiftSender.anchorId) + String.valueOf(apiGiftSender.giftId);

        CountOrdinaryGiftNumber = 0;
        ImageLoader.display(apiGiftSender.userAvatar, binding.GiftUserImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        ImageLoader.display(apiGiftSender.gifticon, binding.GiftGiftImage);
        binding.GiftUserName.setText(apiGiftSender.userName);
        binding.GiftAnchorName.setText(apiGiftSender.anchorName);
        getOrdinaryGiftNumber(apiGiftSender.giftNumber);
        giftAnimator = ObjectAnimator.ofFloat(binding.ReGift, "translationX", 12);
        giftAnimator.setDuration(1000);
        giftAnimator.setInterpolator(new LinearInterpolator());

        giftAnimatorEnd = ObjectAnimator.ofFloat(binding.ReGift, "translationX", 0, -1000);
        giftAnimatorEnd.setDuration(1000);
        giftAnimatorEnd.setInterpolator(new LinearInterpolator());

        giftAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                giftshow = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (mHandler != null) {
                    mHandler.sendEmptyMessageDelayed(3, 5000);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        giftAnimatorEnd.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                giftshow = false;
                UserIDAndGiftID1 = "";

            }

            @Override
            public void onAnimationEnd(Animator animation) {

//                giftAnimator.cancel();
                giftAnimator.removeAllListeners();
                giftAnimator = null;
//                giftAnimatorEnd.cancel();
                giftAnimatorEnd.removeAllListeners();
                giftAnimatorEnd = null;

                giftshowEnd = true;
                if (OrdinaryGiftList.size() > 0) {
                    getGiftAnimmator(OrdinaryGiftList.get(0));
                    OrdinaryGiftList.remove(0);
                    giftshowEnd = false;
                }
//                if (OrdinaryGiftList.size()!=0){
//                    if (giftshowEnd){
//                        getGiftAnimmator(OrdinaryGiftList.get(0));
//                        giftshowEnd =false;
//                    }
//                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        giftAnimator.start();
    }

    //送礼特效
    private void showVipGiftDialog(ApiGiftSender apiGiftSender) {
        if (null == vipGiftDialog || null == vipGiftDialog.getDialog() || !vipGiftDialog.getDialog().isShowing()) {
            vipGiftDialog = new VipGiftDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("apiGiftSender", apiGiftSender);
            vipGiftDialog.setArguments(bundle);
            vipGiftDialog.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "VipGiftDialogFragment");
        } else {
            vipGiftDialog.setData(apiGiftSender);
        }
    }

    private ObjectAnimator giftAnimator2;
    private ObjectAnimator giftAnimatorEnd2;
    private int CountOrdinaryGiftNumber2 = 0;
    private boolean OrdinaryNoStopGive2 = false;
    private String UserIDAndGiftID2;

    private void getOrdinaryGiftNumber2(int giftNumber) {
        CountOrdinaryGiftNumber2 = (CountOrdinaryGiftNumber2 + giftNumber);
        binding.GiftNumberTwo.setText("x" + CountOrdinaryGiftNumber2);
    }

    public void getGiftAnimmator2(ApiGiftSender apiGiftSender) {
        UserIDAndGiftID2 = String.valueOf(apiGiftSender.userId) + String.valueOf(apiGiftSender.anchorId) + String.valueOf(apiGiftSender.giftId);

        CountOrdinaryGiftNumber2 = 0;
        ImageLoader.display(apiGiftSender.userAvatar, binding.GiftUserImageTwo, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        ImageLoader.display(apiGiftSender.gifticon, binding.GiftGiftImageTwo);
        binding.GiftUserNameTwo.setText(apiGiftSender.userName);
        binding.GiftAnchorNameTwo.setText(apiGiftSender.anchorName);
        getOrdinaryGiftNumber2(apiGiftSender.giftNumber);
        giftAnimator2 = ObjectAnimator.ofFloat(binding.ReGiftTwo, "translationX", 12);
        giftAnimator2.setDuration(1000);
        giftAnimator2.setInterpolator(new LinearInterpolator());

        giftAnimatorEnd2 = ObjectAnimator.ofFloat(binding.ReGiftTwo, "translationX", 0, -1000);
        giftAnimatorEnd2.setDuration(1000);
        giftAnimatorEnd2.setInterpolator(new LinearInterpolator());

        giftAnimator2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                giftshow2 = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (mHandler != null) {
                    mHandler.sendEmptyMessageDelayed(11, 5000);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        giftAnimatorEnd2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                giftshow2 = false;
                UserIDAndGiftID2 = "";
//                OrdinaryGiftList.remove(0);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

//                giftAnimator2.cancel();
                giftAnimator2.removeAllListeners();
                giftAnimator2 = null;
//                giftAnimatorEnd2.cancel();
                giftAnimatorEnd2.removeAllListeners();
                giftAnimatorEnd2 = null;

                giftshowEnd2 = true;
                if (OrdinaryGiftList.size() > 0) {
                    getGiftAnimmator2(OrdinaryGiftList.get(0));
                    OrdinaryGiftList.remove(0);
                    giftshowEnd2 = false;
                }
//                if (OrdinaryGiftList.size()!=0){
//                    if (giftshowEnd2){
//                        getGiftAnimmator(OrdinaryGiftList.get(0));
//                        giftshowEnd=false;
//                    }
//                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        giftAnimator2.start();
    }

    private ObjectAnimator giftAnimator3;
    private ObjectAnimator giftAnimatorEnd3;
    private int CountOrdinaryGiftNumber3 = 0;
    private String UserIDAndGiftID3;
    private boolean OrdinaryNoStopGive3 = false;

    private void getOrdinaryGiftNumber3(int giftNumber) {
        CountOrdinaryGiftNumber3 = (CountOrdinaryGiftNumber3 + giftNumber);
        binding.GiftNumberThree.setText("x" + CountOrdinaryGiftNumber3);
    }

    public void getGiftAnimmator3(ApiGiftSender apiGiftSender) {
        UserIDAndGiftID3 = String.valueOf(apiGiftSender.userId) + String.valueOf(apiGiftSender.giftId);
        CountOrdinaryGiftNumber3 = 0;
        ImageLoader.display(apiGiftSender.userAvatar, binding.GiftUserImageThree, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        ImageLoader.display(apiGiftSender.gifticon, binding.GiftGiftImageThree);
        binding.GiftUserNameThree.setText(apiGiftSender.userName);
        binding.GiftAnchorNameThree.setText(apiGiftSender.anchorName);
        getOrdinaryGiftNumber3(apiGiftSender.giftNumber);
        giftAnimator3 = ObjectAnimator.ofFloat(binding.ReGiftThree, "translationX", 12);
        giftAnimator3.setDuration(1000);
        giftAnimator3.setInterpolator(new LinearInterpolator());

        giftAnimatorEnd3 = ObjectAnimator.ofFloat(binding.ReGiftThree, "translationX", 0, -1000);
        giftAnimatorEnd3.setDuration(1000);
        giftAnimatorEnd3.setInterpolator(new LinearInterpolator());

        giftAnimator3.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                giftshow3 = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (mHandler != null) {
                    mHandler.sendEmptyMessageDelayed(12, 5000);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        giftAnimatorEnd3.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                giftshow3 = false;
                UserIDAndGiftID3 = "";
//                OrdinaryGiftList.remove(0);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

//                giftAnimator3.cancel();
                giftAnimator3.removeAllListeners();
                giftAnimator3 = null;
//                giftAnimatorEnd3.cancel();
                giftAnimatorEnd3.removeAllListeners();
                giftAnimatorEnd3 = null;

                giftshowEnd3 = true;
                if (OrdinaryGiftList.size() > 0) {
                    getGiftAnimmator3(OrdinaryGiftList.get(0));
                    OrdinaryGiftList.remove(0);
                    giftshowEnd3 = false;
                }
//                if (OrdinaryGiftList.size()!=0){
//                    if (giftshowEnd2){
//                        getGiftAnimmator(OrdinaryGiftList.get(0));
//                        giftshowEnd=false;
//                    }
//                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        giftAnimator3.start();
    }

    //VIP进入动画
    private ObjectAnimator GuardianEntryAnimator;
    private ObjectAnimator GuardianEntryAnimatorEnd;

    public void GuardianEntry(ApiSimpleMsgRoom apiSimpleMsgRoom) {
        if (apiSimpleMsgRoom.isVip == 1) {
            binding.ReVip1.setVisibility(View.VISIBLE);
            binding.ReFans.setVisibility(View.GONE);
            binding.ReGuardianEntry1.setVisibility(View.GONE);

            ImageLoader.display(apiSimpleMsgRoom.avatar, binding.VipUserImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            ImageLoader.display(apiSimpleMsgRoom.nobleGrade, binding.VipGrade);
            binding.VipName.setText(apiSimpleMsgRoom.uname);
        } else {
            if (apiSimpleMsgRoom.isSh == 1) {
                binding.ReVip1.setVisibility(View.GONE);
                binding.ReFans.setVisibility(View.GONE);
                binding.ReGuardianEntry1.setVisibility(View.VISIBLE);

                binding.GuardianEntryName.setText(apiSimpleMsgRoom.uname);
            } else {
                if (apiSimpleMsgRoom.isFans == 1) {
                    binding.ReVip1.setVisibility(View.GONE);
                    binding.ReFans.setVisibility(View.VISIBLE);
                    binding.ReGuardianEntry1.setVisibility(View.GONE);

                    binding.FansName.setText(apiSimpleMsgRoom.uname);
                }
            }
        }


        GuardianEntryAnimator = ObjectAnimator.ofFloat(binding.ReGuardianEntry, "translationX", 12);
        GuardianEntryAnimator.setDuration(1000);
        GuardianEntryAnimator.setInterpolator(new LinearInterpolator());

        GuardianEntryAnimatorEnd = ObjectAnimator.ofFloat(binding.ReGuardianEntry, "translationX", 0, -1000);
        GuardianEntryAnimatorEnd.setDuration(1000);
        GuardianEntryAnimatorEnd.setInterpolator(new LinearInterpolator());

        GuardianEntryAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mHandler != null) {
                    mHandler.sendEmptyMessageDelayed(4, 1000);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        GuardianEntryAnimatorEnd.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                GuardianEntryAnimator.cancel();
                GuardianEntryAnimator.removeAllListeners();
                GuardianEntryAnimator = null;
//                GuardianEntryAnimatorEnd.cancel();
                GuardianEntryAnimatorEnd.removeAllListeners();
                GuardianEntryAnimatorEnd = null;
                if (vipJoinroomList.size() != 0) {
                    vipJoinroomList.remove(0);
                }

                VipJoinRoom = true;
                if (vipJoinroomList.size() != 0) {
                    if (VipJoinRoom) {
                        GuardianEntry(vipJoinroomList.get(0));
                        VipJoinRoom = false;
                    }

                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        GuardianEntryAnimator.start();
    }


    // 土豪进场
    private ObjectAnimator welcomeAnimator;
    private ObjectAnimator welcomeAnimatorEnd;

    public void welcomeEntry(ApiSimpleMsgRoom apiSimpleMsgRoom) {
        ImageLoader.display(apiSimpleMsgRoom.avatar, binding.rlWelcomeUserImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        binding.rlWelcomeUserName.setText("【" + StringShowUtil.showName(apiSimpleMsgRoom.uname) + "】");

        welcomeAnimator = ObjectAnimator.ofFloat(binding.rlWelcome, "translationX", 12);
        welcomeAnimator.setDuration(1000);
        welcomeAnimator.setInterpolator(new LinearInterpolator());

        welcomeAnimatorEnd = ObjectAnimator.ofFloat(binding.rlWelcome, "translationX", 0, -1000);
        welcomeAnimatorEnd.setDuration(1000);
        welcomeAnimatorEnd.setInterpolator(new LinearInterpolator());

        welcomeAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mHandler != null) {
                    mHandler.sendEmptyMessageDelayed(15, 1000);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        welcomeAnimatorEnd.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                welcomeAnimator.cancel();
                welcomeAnimator.removeAllListeners();
                welcomeAnimator = null;
//                welcomeAnimatorEnd.cancel();
                welcomeAnimatorEnd.removeAllListeners();
                welcomeAnimatorEnd = null;
                if (welcomeList.size() != 0) {
                    welcomeList.remove(0);
                }

                welcomeAnimatorShow = true;
                if (welcomeList.size() != 0) {
                    if (welcomeAnimatorShow) {
                        welcomeEntry(welcomeList.get(0));
                        welcomeAnimatorShow = false;
                    }

                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        welcomeAnimator.start();
    }


    // 普通用户 进场
    private AnimatorSet baseUserWelcomeAnimator;

    public void baseUserWelcomeEntry(ApiSimpleMsgRoom apiSimpleMsgRoom) {

        if (binding.rlBaseUserWelcome.getVisibility() == View.GONE) {
            binding.rlBaseUserWelcome.setVisibility(View.VISIBLE);
        }

        //0:普通用户 1:主播
        if (apiSimpleMsgRoom.role == 1) {
            ImageLoader.display(apiSimpleMsgRoom.anchorGrade, binding.rlBaseUserWelcomeNobleGrade);
        } else {
            ImageLoader.display(apiSimpleMsgRoom.userGrade, binding.rlBaseUserWelcomeNobleGrade);
        }
        binding.rlBaseUserWelcomeUserName.setText(StringShowUtil.showName(apiSimpleMsgRoom.uname));

        baseUserWelcomeAnimator = new AnimatorSet();

        //1.从 左进入屏幕
        //2.暂停一会
        //透明度 1 到 0
        //移动会 屏幕左边缘
        baseUserWelcomeAnimator.playSequentially(
                ObjectAnimator.ofFloat(binding.rlBaseUserWelcome, "translationX", -1000, 0).setDuration(1000),
                ObjectAnimator.ofFloat(binding.rlBaseUserWelcome, "alpha", 1.0f, 1.0f).setDuration(1100),
                ObjectAnimator.ofFloat(binding.rlBaseUserWelcome, "alpha", 1.0f, 0.0f).setDuration(800)
                , ObjectAnimator.ofFloat(binding.rlBaseUserWelcome, "translationX", 0, -1000).setDuration(10)
                , ObjectAnimator.ofFloat(binding.rlBaseUserWelcome, "alpha", 0, 1.0f).setDuration(10)
        );


        baseUserWelcomeAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                baseUserWelcomeAnimator.removeAllListeners();
                baseUserWelcomeAnimator = null;

                if (baseUserWelcomeList != null) {
                    if (baseUserWelcomeList.size() != 0) {
                        baseUserWelcomeList.remove(0);
                    }
                }

                baseUserWelcomeShow = true;
                if (baseUserWelcomeList != null && baseUserWelcomeList.size() > 0) {
                    if (baseUserWelcomeShow) {
                        baseUserWelcomeEntry(baseUserWelcomeList.get(0));
                        baseUserWelcomeShow = false;
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {


            }
        });

        baseUserWelcomeAnimator.start();
    }


    //贵族送礼物
    private ObjectAnimator NobleGiftAnimator;
    private ObjectAnimator NobleGiftAnimatorEnd;
    private int CountNobleGiftNumber = 0;
    private boolean NobleNoStopGive = false;

    private void getNobleGiftNumberNumber(int giftNumber) {
        CountNobleGiftNumber = (CountNobleGiftNumber + giftNumber);
        binding.NobleGiftNumber.setText("x" + CountNobleGiftNumber);
    }

    public void NobleGiftAnimator(ApiGiftSender apiGiftSender) {
        CountNobleGiftNumber = 0;
        ImageLoader.display(apiGiftSender.userAvatar, binding.NobleGiftUserImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        binding.NobleGiftUserName.setText(apiGiftSender.userName);
        binding.NobleGiftAnchorName.setText(apiGiftSender.anchorName);

        ImageLoader.display(apiGiftSender.gifticon, binding.NobleGiftGiftImage);
        getNobleGiftNumberNumber(apiGiftSender.giftNumber);
        NobleGiftAnimator = ObjectAnimator.ofFloat(binding.ReNobleGift, "translationX", 12);
        NobleGiftAnimator.setDuration(1000);
        NobleGiftAnimator.setInterpolator(new LinearInterpolator());

        NobleGiftAnimatorEnd = ObjectAnimator.ofFloat(binding.ReNobleGift, "translationX", 0, -1000);
        NobleGiftAnimatorEnd.setDuration(1000);
        NobleGiftAnimatorEnd.setInterpolator(new LinearInterpolator());

        NobleGiftAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Noblegiftshow = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mHandler != null) {
                    mHandler.sendEmptyMessageDelayed(5, 5000);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        NobleGiftAnimatorEnd.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Noblegiftshow = false;
                if (NobleGiftList.size() != 0) {
                    NobleGiftList.remove(0);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                NobleGiftAnimator.cancel();
                NobleGiftAnimator.removeAllListeners();
                NobleGiftAnimator = null;
//                NobleGiftAnimatorEnd.cancel();
                NobleGiftAnimatorEnd.removeAllListeners();
                NobleGiftAnimatorEnd = null;

                NoblegiftshowEnd = true;
                if (NobleGiftList.size() != 0) {
                    if (NoblegiftshowEnd) {
                        NobleGiftAnimator(NobleGiftList.get(0));
                        NoblegiftshowEnd = false;
                    }

                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        NobleGiftAnimator.start();
    }

    //坐骑
    private ObjectAnimator MountsAnimator;
    private ObjectAnimator MountsAnimatorEnd;

    public void MountsAnimator(AppJoinRoomVO apiJoinRoom) {
        ImageLoader.display(apiJoinRoom.userAvatar, binding.MountsGiftUserImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        ImageLoader.display(apiJoinRoom.userGradeImg, binding.MountsGiftGarde);
        binding.MountsGiftUserName.setText(apiJoinRoom.userName);
        binding.MountsGiftMountsName.setText(apiJoinRoom.carName);

        MountsAnimator = ObjectAnimator.ofFloat(binding.ReMountsGift, "translationX", 12);
        MountsAnimator.setDuration(1000);
        MountsAnimator.setInterpolator(new LinearInterpolator());

        MountsAnimatorEnd = ObjectAnimator.ofFloat(binding.ReMountsGift, "translationX", 0, -1000);
        MountsAnimatorEnd.setDuration(1000);
        MountsAnimatorEnd.setInterpolator(new LinearInterpolator());

        MountsAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mHandler != null) {
                    mHandler.sendEmptyMessageDelayed(6, 1000);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        MountsAnimatorEnd.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                MountsAnimator.cancel();
                MountsAnimator.removeAllListeners();
                MountsAnimator = null;
//                MountsAnimatorEnd.cancel();
                MountsAnimatorEnd.removeAllListeners();
                MountsAnimatorEnd = null;

                if (MountsList.size() != 0) {
                    MountsList.remove(0);
                }

                Mountsshow = true;
                if (MountsList.size() != 0) {
                    if (Mountsshow) {
                        MountsAnimator(MountsList.get(0));
                        Mountsshow = false;
                    }

                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        MountsAnimator.start();
    }

    //直播间购买商品
    private ObjectAnimator BuyLiveGoodsAnimator;
    private ObjectAnimator BuyLiveGoodsAnimatorEnd;

    public void LiveBuyGoods(UserBuyDTO buyGoodsBean) {
        ImageLoader.display(buyGoodsBean.avatarThumb, binding.LiveBuyUserImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        binding.LiveBuyUserName.setText(buyGoodsBean.username);
        binding.LiveBuyBuyGoods.setText("购买了" + buyGoodsBean.goodsName);


        BuyLiveGoodsAnimator = ObjectAnimator.ofFloat(binding.ReLiveBuy, "translationX", DpUtil.dp2px(DpUtil.getScreenWidth()) / 2, 0);
        BuyLiveGoodsAnimator.setDuration(1000);
        BuyLiveGoodsAnimator.setInterpolator(new LinearInterpolator());

        BuyLiveGoodsAnimatorEnd = ObjectAnimator.ofFloat(binding.ReLiveBuy, "translationX", -DpUtil.dp2px(DpUtil.getScreenWidth()) / 2);
        BuyLiveGoodsAnimatorEnd.setDuration(1000);
        BuyLiveGoodsAnimatorEnd.setInterpolator(new LinearInterpolator());

        BuyLiveGoodsAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (mHandler != null) {
                    mHandler.sendEmptyMessageDelayed(14, 1000);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        BuyLiveGoodsAnimatorEnd.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
//                BuyLiveGoodsAnimator.cancel();
                BuyLiveGoodsAnimator.removeAllListeners();
                BuyLiveGoodsAnimator = null;
//                BuyLiveGoodsAnimatorEnd.cancel();
                BuyLiveGoodsAnimatorEnd.removeAllListeners();
                BuyLiveGoodsAnimatorEnd = null;

                if (buyGoodsList.size() != 0) {
                    buyGoodsList.remove(0);
                }

                buyGoodsShow = false;
                if (buyGoodsList.size() != 0) {
                    if (buyGoodsShow) {
                        LiveBuyGoods(buyGoodsList.get(0));
                        buyGoodsShow = true;
                    }

                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        BuyLiveGoodsAnimator.start();

    }


    //完成任务
//    TaskCompleteDialogFragment taskCompleteDialogFragment;
    public void TaskAnimator(ApiElasticFrame apiElasticFrame) {

        TaskToastView.showToast(mContext, apiElasticFrame);

//        if (taskCompleteDialogFragment!=null){
//            taskCompleteDialogFragment=null;
//        }
//        taskCompleteDialogFragment= new TaskCompleteDialogFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("ApiElasticFrame", apiElasticFrame);
//        taskCompleteDialogFragment.setArguments(bundle);
//        taskCompleteDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "AnchorTaskDialogFragment");

        if (TaskList.size() != 0) {
            TaskList.remove(0);
        }
        mHandler.sendEmptyMessageDelayed(8, 5000);

    }

    //升级
//    UserUpgradeDialgFragment userUpgradeDialgFragment;
    public void UpgradeAnimator(ApiElasticFrame apiElasticFrame) {
//        if (userUpgradeDialgFragment!=null){
//            userUpgradeDialgFragment=null;
//        }
//        userUpgradeDialgFragment = new UserUpgradeDialgFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("ApiElasticFrame", apiElasticFrame);
//        userUpgradeDialgFragment.setArguments(bundle);
//        userUpgradeDialgFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "userUpgradeDialgFragment");
        UpgradeToastView.showToast(mContext, apiElasticFrame);

        if (UpgradeList.size() != 0) {
            UpgradeList.remove(0);
        }
        mHandler.sendEmptyMessageDelayed(9, 5000);


    }

    //勋章

    //    UserMedalDialogFragment userMedalDialogFragment;
    public void MedalAnimator(ApiElasticFrame apiElasticFrame) {
//        if (userMedalDialogFragment!=null){
//            userMedalDialogFragment=null;
//        }
//        userMedalDialogFragment = new UserMedalDialogFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("ApiElasticFrame", apiElasticFrame);
//        userMedalDialogFragment.setArguments(bundle);
//        userMedalDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "UserMedalDialogFragment");

        MedalToastView.showToast(mContext, apiElasticFrame);


        if (MedalList.size() != 0) {
            MedalList.remove(0);
        }
        mHandler.sendEmptyMessageDelayed(10, 5000);


    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    break;
                case 2:
                    if (FansNoStopGive) {
                        FansNoStopGive = false;
                        mHandler.sendEmptyMessageDelayed(2, 3000);
                    } else {
                        if (FansAnimatorEnd != null) {
                            FansAnimatorEnd.start();
                        }
                    }

                    break;
                case 3:
                    if (OrdinaryNoStopGive) {
                        OrdinaryNoStopGive = false;
                        mHandler.sendEmptyMessageDelayed(3, 3000);
                    } else {
                        if (giftAnimatorEnd != null) {
                            giftAnimatorEnd.start();
                        }
                    }


                    break;
                case 4:
                    if (GuardianEntryAnimatorEnd != null) {
                        GuardianEntryAnimatorEnd.start();
                    }
                    break;
                case 5:
                    if (NobleNoStopGive) {
                        NobleNoStopGive = false;
                        mHandler.sendEmptyMessageDelayed(5, 3000);
                    } else {
                        NobleGiftAnimatorEnd.start();
                    }

                    break;
                case 6:
                    MountsAnimatorEnd.start();
                    break;
                case 7:
                    break;
                case 8:
//
                    Taskshow = true;
                    if (TaskList.size() != 0) {
                        if (Taskshow) {
                            TaskAnimator(TaskList.get(0));
                            Taskshow = false;
                        }
                    }
                    break;
                case 9:
                    Upgradeshow = true;
                    if (UpgradeList.size() != 0) {
                        if (Upgradeshow) {
                            UpgradeAnimator(UpgradeList.get(0));
                            Upgradeshow = false;
                        }

                    }
                    break;
                case 10:
                    Medalshow = true;
                    if (MedalList.size() != 0) {
                        if (Medalshow) {
                            MedalAnimator(MedalList.get(0));
                            Medalshow = false;
                        }

                    }
                    break;
                case 11:
                    if (OrdinaryNoStopGive2) {
                        OrdinaryNoStopGive2 = false;
                        mHandler.sendEmptyMessageDelayed(11, 3000);
                    } else {
                        if (giftAnimatorEnd2 != null) {
                            giftAnimatorEnd2.start();
                        }
                    }

                    break;
                case 12:
                    if (OrdinaryNoStopGive3) {
                        OrdinaryNoStopGive3 = false;
                        mHandler.sendEmptyMessageDelayed(12, 3000);
                    } else {
                        if (giftAnimatorEnd3 != null) {
                            giftAnimatorEnd3.start();
                        }

                    }

                    break;
                case 13:
                    if (GuardNoStopGive) {
                        GuardNoStopGive = false;
                        mHandler.sendEmptyMessageDelayed(13, 3000);
                    } else {
                        if (GuardAnimatorEnd != null) {
                            GuardAnimatorEnd.start();
                        }
                    }
                    break;
                case 14:
                    if (buyGoodsShow) {
                        buyGoodsShow = false;
                        mHandler.sendEmptyMessageDelayed(14, 1000);
                    } else {
                        if (BuyLiveGoodsAnimatorEnd != null) {
                            BuyLiveGoodsAnimatorEnd.start();
                        }
                    }
                    break;
                case 15:
                    if (welcomeAnimatorShow) {
                        welcomeAnimatorShow = false;
                        mHandler.sendEmptyMessageDelayed(15, 1000);
                    } else {
                        if (welcomeAnimatorEnd != null) {
                            welcomeAnimatorEnd.start();
                        }
                    }
                    break;

            }
        }
    };

    public void clean() {
        if (FansAnimatorEnd != null) {
            FansAnimatorEnd.cancel();
            FansAnimatorEnd = null;
        }
        if (FansAnimator != null) {
            FansAnimator.cancel();
            FansAnimator = null;
        }
        if (giftAnimatorEnd != null) {
            giftAnimatorEnd.cancel();
            giftAnimatorEnd = null;
        }
        if (giftAnimator != null) {
            giftAnimator.cancel();
            giftAnimator = null;
        }
        if (giftAnimatorEnd2 != null) {
            giftAnimatorEnd2.cancel();
            giftAnimatorEnd2 = null;
        }
        if (giftAnimator2 != null) {
            giftAnimator2.cancel();
            giftAnimator2 = null;
        }
        if (giftAnimatorEnd3 != null) {
            giftAnimatorEnd3.cancel();
            giftAnimatorEnd3 = null;
        }
        if (giftAnimator3 != null) {
            giftAnimator3.cancel();
            giftAnimator3 = null;
        }
        if (GuardianEntryAnimatorEnd != null) {
            GuardianEntryAnimatorEnd.cancel();
            GuardianEntryAnimatorEnd = null;
        }
        if (NobleGiftAnimatorEnd != null) {
            NobleGiftAnimatorEnd.cancel();
            NobleGiftAnimatorEnd = null;
        }
        if (NobleGiftAnimator != null) {
            NobleGiftAnimator.cancel();
            NobleGiftAnimator = null;
        }
        if (MountsAnimatorEnd != null) {
            MountsAnimatorEnd.cancel();
            MountsAnimatorEnd = null;
        }
        if (MountsAnimator != null) {
            MountsAnimator.cancel();
            MountsAnimator = null;
        }

        if (noticeStartAnimator != null) {
            noticeStartAnimator.cancel();
            noticeStartAnimator = null;
        }

//        if (noticeEndAnimator!=null){
//            noticeEndAnimator.cancel();
//            noticeEndAnimator = null;
//        }

//        if (taskCompleteDialogFragment!=null){
//            taskCompleteDialogFragment.dismiss();
//            taskCompleteDialogFragment =null;
//        }
//        if (userUpgradeDialgFragment!=null){
//            userUpgradeDialgFragment.dismiss();
//            userUpgradeDialgFragment =null;
//        }
//        if (userMedalDialogFragment!=null){
//            userMedalDialogFragment.dismiss();
//            userMedalDialogFragment =null;
//        }
        mHandler.removeMessages(1);
        mHandler.removeMessages(2);
        mHandler.removeMessages(3);
        mHandler.removeMessages(4);
        mHandler.removeMessages(5);
        mHandler.removeMessages(6);
        mHandler.removeMessages(7);
        mHandler.removeMessages(8);
        mHandler.removeMessages(9);
        mHandler.removeMessages(10);
        mHandler.removeMessages(11);
        mHandler.removeMessages(12);
        mHandler.removeMessages(13);
        mHandler.removeMessages(14);
        mHandler.removeMessages(15);
        removeFromParent();
    }
}
