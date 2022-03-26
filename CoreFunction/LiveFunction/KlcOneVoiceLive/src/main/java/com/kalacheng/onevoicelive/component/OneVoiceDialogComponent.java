package com.kalacheng.onevoicelive.component;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.kalacheng.base.base.BaseMVVMViewHolder;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.commonview.dialog.LiveGiftDialogFragment;
import com.kalacheng.commonview.dialog.WishBillAddDialogFragment;
import com.kalacheng.commonview.jguangIm.ImMessageUtil;
import com.kalacheng.commonview.music.dialog.LiveMusicDialogFragment1;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.livecommon.fragment.AudienceVipRechargeDialogFragment;
import com.kalacheng.livecommon.fragment.BeautyDialogFragment;
import com.kalacheng.livecommon.fragment.GoldInsufficientDialogFragment;
import com.kalacheng.livecommon.fragment.LiveHotDialogFragment;
import com.kalacheng.livecommon.fragment.LiveTreasureChestDialogFragment;
import com.kalacheng.livecommon.fragment.LiveUserDialogFragment;
import com.kalacheng.livecommon.fragment.OOOIsLiveEndDialogFragment;
import com.kalacheng.livecommon.fragment.OOOLiveEndDialogFragment;
import com.kalacheng.livecommon.fragment.OOOLiveUserEndDialogFragment;
import com.kalacheng.livecommon.fragment.UnconnectedDialogFragment;
import com.kalacheng.livecommon.fragment.UserFansGroupDialogFragment;
import com.kalacheng.money.utils.ChargeUtils;
import com.kalacheng.onevoicelive.R;
import com.kalacheng.onevoicelive.databinding.OneVoiceDialogBinding;
import com.kalacheng.onevoicelive.viewmodel.OneVoiceDialogViewModel;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.StringUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.klc.bean.OOOLiveHangUpBean;
import com.wengying666.imsocket.SocketClient;
import com.xuantongyun.livecloud.protocol.PulicUtils;

import java.util.ArrayList;


public class OneVoiceDialogComponent extends BaseMVVMViewHolder<OneVoiceDialogBinding, OneVoiceDialogViewModel> implements LiveBundle.onLiveSocket {

    private SocketClient mSocket;

    private UserFansGroupDialogFragment userFansGroupDialogFragment;
    private GoldInsufficientDialogFragment goldInsufficientDialogFragment;
    private LiveGiftDialogFragment dialogFragment;
    //    private OOOLiveMoreDialogFragment moreDialogFragment;
    private BeautyDialogFragment beautyDialogFragment;
    private WishBillAddDialogFragment wishBillAddDialogFragment;
    private OOOLiveEndDialogFragment oooLiveEndDialogFragment;
    private OOOIsLiveEndDialogFragment oooIsLiveEndDialogFragment;
    private LiveUserDialogFragment liveUserDialogFragment;
    private UnconnectedDialogFragment unconnectedDialogFragment;
    private LiveHotDialogFragment liveHotDialogFragment;
    private AudienceVipRechargeDialogFragment audienceVipRechargeDialogFragment;
    private OOOLiveUserEndDialogFragment oooLiveUserEndDialogFragment;
    private LiveGiftDialogFragment giftdialogFragment;
    private LiveMusicDialogFragment1 musicDialogFragment1;

    private String freeCallMsg;

    public OneVoiceDialogComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.one_voice_dialog;
    }

    @Override
    protected void init() {
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
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_CloseLive, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                clean();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOCloseLive, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                clean();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OneFeeCall, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                freeCallMsg = (String) o;
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });


        //查看信息
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.Information, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                liveUserDialogFragment = new LiveUserDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("ApiJoinRoom", viewModel.joinRoom.get());
                liveUserDialogFragment.setArguments(bundle);
                liveUserDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveUserDialogFragment1");

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //送礼弹框
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveSendGift, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                dialogFragment = new LiveGiftDialogFragment();
                Bundle bundle = new Bundle();
//                bundle.putInt(ARouterValueNameConfig.Livetype, viewModel.joinRoom.get().type);
//                bundle.putString(ARouterValueNameConfig.ShowID, viewModel.joinRoom.get().showid);
//                bundle.putLong(ARouterValueNameConfig.RoomID, viewModel.joinRoom.get().roomId);
//                bundle.putLong(ARouterValueNameConfig.ShortVideoId, -1);
//                bundle.putLong(ARouterValueNameConfig.LiveRoomAnchorID,viewModel.joinRoom.get().anchorId);
                bundle.putParcelableArrayList("SendList", (ArrayList<? extends Parcelable>) o);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "LiveGiftDialogFragment");

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //更多
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveMore, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
//                moreDialogFragment = new OOOLiveMoreDialogFragment();
//                moreDialogFragment.getApiJoinRoom(viewModel.joinRoom.get());
//                moreDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "OOOLiveMoreDialogFragment");

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //对方未接听
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveHangUp, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                ToastUtil.show("TA正在忙稍后再聊");
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_CloseLive, null);
//                unconnectedDialogFragment = new UnconnectedDialogFragment();
//                unconnectedDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "UnconnectedDialogFragment");

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
//活力值查看
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LiveHot, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                liveHotDialogFragment = new LiveHotDialogFragment();
                liveHotDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveHotDialogFragment");

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //赠送礼物
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_SendGift, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                giftdialogFragment = new LiveGiftDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("SendList", (ArrayList<? extends Parcelable>) o);
                giftdialogFragment.setArguments(bundle);
                giftdialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "LiveGiftDialogFragment");
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //加入粉丝团
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_AddFansGroup, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                userFansGroupDialogFragment = new UserFansGroupDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("ApiJoinRoom", viewModel.joinRoom.get());
                userFansGroupDialogFragment.setArguments(bundle);
                userFansGroupDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "UserFansGroupDialogFragment");

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //美颜
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_BeautyShow, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                beautyDialogFragment = new BeautyDialogFragment();
                beautyDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "BeautyDialogFragment");
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //充值
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LiveRecharge, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                ChargeUtils.getInstance().init(mContext).showChargeDialog();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //心愿单
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_WishList, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                wishBillAddDialogFragment = new WishBillAddDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("RoomID", viewModel.joinRoom.get().roomId);
                bundle.putLong("UserID", -1);
                wishBillAddDialogFragment.setArguments(bundle);
                wishBillAddDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "WishBillAddDialogFragment");
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //通话结束
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOCallEnd, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                //停止/恢复音频采集和播放
                PulicUtils.getInstance().muteLocalAudioStream(true);

                //	接收/停止接收所有视频流
                PulicUtils.getInstance().muteAllRemoteAudioStreams(true);

                if (LiveConstants.FEEUID == HttpClient.getUid()) {
                    oooLiveUserEndDialogFragment = new OOOLiveUserEndDialogFragment();
                    oooLiveUserEndDialogFragment.getOOOLiveHangUpInformation((OOOLiveHangUpBean) o, viewModel.joinRoom.get(), freeCallMsg);
                    oooLiveUserEndDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "OOOLiveEndDialogFragment");
//                    LiveBundle.getInstance().removeOtherMsgListener(LiveConstants.LM_OOOCloseLive);
                } else {
                    oooLiveEndDialogFragment = new OOOLiveEndDialogFragment();
                    oooLiveEndDialogFragment.getOOOLiveHangUpInformation((OOOLiveHangUpBean) o, freeCallMsg);
                    oooLiveEndDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "OOOLiveEndDialogFragment");

                }


                L.e("通话结束" + StringUtil.getDurationText(((OOOLiveHangUpBean) o).callTime));

                if (((OOOLiveHangUpBean) o).uid != HttpClient.getUid()) {
                    //求聊：给极光发消息
                    long time = ((OOOLiveHangUpBean) o).callTime * 1000;
                    ImMessageUtil.getInstance().sendCallMessage(((OOOLiveHangUpBean) o).uid, 0, time, false);
                }


            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //关闭通话的请求
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOEndRequest, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                oooIsLiveEndDialogFragment = new OOOIsLiveEndDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("LiveTime", (String) o);
                oooIsLiveEndDialogFragment.setArguments(bundle);
                oooIsLiveEndDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "OOOIsLiveEndDialogFragment");

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //金额不足
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOGoldInsufficient, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                goldInsufficientDialogFragment = new GoldInsufficientDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("Time", (int) o);
                goldInsufficientDialogFragment.setArguments(bundle);
                goldInsufficientDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "OOOLiveEndDialogFragment");

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //用户不是vip提示
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOAudienceNoVip, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                audienceVipRechargeDialogFragment = new AudienceVipRechargeDialogFragment();
                audienceVipRechargeDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "AudienceVipRechargeDialogFragment");

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //百宝箱
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LiveTreasureChest, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                LiveTreasureChestDialogFragment liveTreasureChestDialogFragment = new LiveTreasureChestDialogFragment();
                liveTreasureChestDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveTreasureChestDialogFragment");

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //背景音乐
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_Music, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (!ConfigUtil.getBoolValue(R.bool.useMusicOld)) {
                    if (musicDialogFragment1 != null) {
                        musicDialogFragment1.dismiss();
                        musicDialogFragment1 = null;
                    }
                    musicDialogFragment1 = new LiveMusicDialogFragment1((FragmentActivity) mContext);
                    musicDialogFragment1.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveMusicDialogFragment1");
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

    }

    @Override
    public void init(String activityName, SocketClient socketClient) {
        mSocket = socketClient;
    }

    public void clean() {
        ChargeUtils.getInstance().dismiss();
        if (userFansGroupDialogFragment != null)
            userFansGroupDialogFragment.dismiss();
        if (dialogFragment != null)
            dialogFragment.dismiss();
//        if (moreDialogFragment!=null)
//            moreDialogFragment.dismiss();
        if (beautyDialogFragment != null)
            beautyDialogFragment.dismiss();
        if (wishBillAddDialogFragment != null)
            wishBillAddDialogFragment.dismiss();
        if (oooLiveEndDialogFragment != null)
            oooLiveEndDialogFragment.dismiss();

        if (oooIsLiveEndDialogFragment != null)
            oooIsLiveEndDialogFragment.dismiss();

        if (liveUserDialogFragment != null)
            liveUserDialogFragment.dismiss();

        if (unconnectedDialogFragment != null)
            unconnectedDialogFragment.dismiss();

        if (liveHotDialogFragment != null)
            liveHotDialogFragment.dismiss();

        if (audienceVipRechargeDialogFragment != null)
            audienceVipRechargeDialogFragment.dismiss();

        if (null != musicDialogFragment1){
            musicDialogFragment1.dismiss();
        }

        if (oooLiveUserEndDialogFragment != null)
            oooLiveUserEndDialogFragment.dismiss();
        if (null != giftdialogFragment)
            giftdialogFragment.dismiss();
        removeFromParent();
    }
}
