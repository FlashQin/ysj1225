package com.kalacheng.one2onelive.component;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.kalacheng.base.base.BaseMVVMViewHolder;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.busfinance.httpApi.HttpApiAPPFinance;
import com.kalacheng.commonview.beauty.BaBaBeautyComponent;
import com.kalacheng.commonview.beauty.DefaultBeautyViewHolder;
import com.kalacheng.commonview.beauty.LiveBeautyComponent;
import com.kalacheng.commonview.dialog.LiveGiftDialogFragment;
import com.kalacheng.commonview.dialog.OpenSVipDialogFragment;
import com.kalacheng.commonview.dialog.WishBillAddDialogFragment;
import com.kalacheng.commonview.jguangIm.ImMessageUtil;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libuser.model.ApiAppChargeRulesResp;
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
import com.kalacheng.money.dialog.FirstRechargeDialogFragment;
import com.kalacheng.money.dialog.LiveRechargeDialogFragment;
import com.kalacheng.one2onelive.R;
import com.kalacheng.one2onelive.databinding.One2oneDialogBinding;
import com.kalacheng.one2onelive.dialog.OOOLiveMoreDialogFragment;
import com.kalacheng.one2onelive.dialog.PeopleSatisfyDialogFragment;
import com.kalacheng.one2onelive.dialog.SVipIsKickOutDialogFragment;
import com.kalacheng.one2onelive.dialog.SVipPeopleSignOutDialogFragment;
import com.kalacheng.one2onelive.dialog.SvipAnchorListDialogFragment;
import com.kalacheng.one2onelive.viewmodel.One2OneDialogViewModel;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.klc.bean.OOOLiveHangUpBean;
import com.klc.bean.live.VoiceLiveOwnStateBean;
import com.wengying666.imsocket.SocketClient;
import com.xuantongyun.livecloud.agora.framework.TiPreprocessor;
import com.xuantongyun.livecloud.protocol.OOOSvipLiveUtils;

import java.util.ArrayList;


public class One2OneDialogFragmentComponent extends BaseMVVMViewHolder<One2oneDialogBinding, One2OneDialogViewModel> implements LiveBundle.onLiveSocket {

    private SocketClient mSocket;

    private UserFansGroupDialogFragment userFansGroupDialogFragment;
    private GoldInsufficientDialogFragment goldInsufficientDialogFragment;
    private LiveGiftDialogFragment dialogFragment;
    private OOOLiveMoreDialogFragment moreDialogFragment;
    private BeautyDialogFragment beautyDialogFragment;
    private LiveRechargeDialogFragment liveRechargeDialogFragment;
    private FirstRechargeDialogFragment firstRechargeDialogFragment;
    private WishBillAddDialogFragment wishBillAddDialogFragment;
    private OOOLiveEndDialogFragment oooLiveEndDialogFragment;
    private OOOIsLiveEndDialogFragment oooIsLiveEndDialogFragment;
    private LiveUserDialogFragment liveUserDialogFragment;
    private UnconnectedDialogFragment unconnectedDialogFragment;
    private SVipIsKickOutDialogFragment sVipIsKickOutDialogFragment;
    private OOOLiveUserEndDialogFragment oooLiveUserEndDialogFragment;
    private AudienceVipRechargeDialogFragment audienceVipRechargeDialogFragment;
    private LiveTreasureChestDialogFragment liveTreasureChestDialogFragment;
    private LiveHotDialogFragment liveHotDialogFragment;
    private LiveGiftDialogFragment giftdialogFragment;

    private LinearLayout live_dialog;
    private String freeCallMsg;

    public One2OneDialogFragmentComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.one2one_dialog;
    }

    @Override
    protected void init() {
        addToParent();
        live_dialog = mParentView.findViewById(R.id.one2onelive_dialog);
        LiveBundle.getInstance().addLiveSocketListener(this);

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OpenLiveMsg, new MsgListener.SimpleMsgListener() {
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


        //副播退出直播间
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveSVipSSideshowignOut, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                OOOLiveHangUpBean hangupInfo = (OOOLiveHangUpBean) o;
                if (hangupInfo.uid == HttpClient.getUid()) {//副播挂断方的提示

                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOCallEnd, hangupInfo);
                } else {//其他人的提示
                    ToastUtil.show(hangupInfo.name + "退出直播间");
                }
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
//                bundle.putLong(ARouterValueNameConfig.LiveRoomAnchorID, viewModel.joinRoom.get().anchorId);
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
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                moreDialogFragment = new OOOLiveMoreDialogFragment();
                moreDialogFragment.getApiJoinRoom(viewModel.joinRoom.get());
                moreDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "OOOLiveMoreDialogFragment");

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

        //金币不足
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveGoldInsufficient, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                goldInsufficientDialogFragment = new GoldInsufficientDialogFragment();
                goldInsufficientDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "GoldInsufficientDialogFragment");
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
                showBeautyDailog();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //充值
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LiveRecharge, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                getFirstRecharge();
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
                TiPreprocessor.mEnabled = false;

                VoiceLiveOwnStateBean.IsOpen3T = false;
                OOOSvipLiveUtils.getInstance().leaveChannel();

                LiveConstants.mOOOSessionID = 0;

                if (LiveConstants.FEEUID == HttpClient.getUid()) {
                    oooLiveUserEndDialogFragment = new OOOLiveUserEndDialogFragment();
                    oooLiveUserEndDialogFragment.getOOOLiveHangUpInformation((OOOLiveHangUpBean) o, viewModel.joinRoom.get(), freeCallMsg);
                    oooLiveUserEndDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "OOOLiveUserEndDialogFragment");
//                    LiveBundle.getInstance().removeOtherMsgListener(LiveConstants.LM_OOOCloseLive);
                } else {
                    oooLiveEndDialogFragment = new OOOLiveEndDialogFragment();
                    oooLiveEndDialogFragment.getOOOLiveHangUpInformation((OOOLiveHangUpBean) o, freeCallMsg);
                    oooLiveEndDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "OOOLiveEndDialogFragment");

                }

                // 给极光发消息
                if (HttpClient.getUid() != ((OOOLiveHangUpBean) o).uid) {
                    long time = ((OOOLiveHangUpBean) o).callTime * 1000;
                    ImMessageUtil.getInstance().sendCallMessage(((OOOLiveHangUpBean) o).uid, 0, time, true);
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
                oooIsLiveEndDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "OOOLiveEndDialogFragment");

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

        //开通Svip
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveOpenSVip, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                OpenSVipDialogFragment openSVipDialogFragment = new OpenSVipDialogFragment();
                openSVipDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "OpenSVipDialogFragment");

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //svip选择主播列表
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveChoiceSVip, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                SvipAnchorListDialogFragment svipAnchorListDialogFragment = new SvipAnchorListDialogFragment();
                svipAnchorListDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "SvipAnchorListDialogFragment");

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //svip邀请人生已经到了8人
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveSVipPeopleSatisfy, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                PeopleSatisfyDialogFragment peopleSatisfyDialogFragment = new PeopleSatisfyDialogFragment();
                peopleSatisfyDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "PeopleSatisfyDialogFragment");

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //svip踢出邀请人
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveSVipIsKickOut, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                sVipIsKickOutDialogFragment = new SVipIsKickOutDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("UserID", (Long) o);
                sVipIsKickOutDialogFragment.setArguments(bundle);
                sVipIsKickOutDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "SVipIsKickOutDialogFragment");

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //邀请人退出聊天
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveSVipPeopleSignOut, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                SVipPeopleSignOutDialogFragment sVipPeopleSignOutDialogFragment = new SVipPeopleSignOutDialogFragment();
                sVipPeopleSignOutDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "SVipPeopleSignOutDialogFragment");
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //用户不是vip提示
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOAudienceNoVip, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
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
                liveTreasureChestDialogFragment = new LiveTreasureChestDialogFragment();
                liveTreasureChestDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveTreasureChestDialogFragment");

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
    }

    //获取是否有首充奖励
    public void getFirstRecharge() {
        HttpApiAPPFinance.rules_list(new HttpApiCallBack<ApiAppChargeRulesResp>() {
            @Override
            public void onHttpRet(int code, String msg, ApiAppChargeRulesResp retModel) {
                if (code == 1) {
                    if (retModel.isFirstRecharge == 1) {
                        firstRechargeDialogFragment = new FirstRechargeDialogFragment();
                        firstRechargeDialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "FirstRechargeDialogFragment");
                    } else {
                        liveRechargeDialogFragment = new LiveRechargeDialogFragment();
                        liveRechargeDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveRechargeDialogFragment");
                    }

                }

            }
        });
    }

    private DefaultBeautyViewHolder mLiveBeautyViewHolder;
    private LiveBeautyComponent liveBeautyComponent;
    private BaBaBeautyComponent baBaBeautyComponent;

    public void showBeautyDailog() {
        int beautySwitch = (int) SpUtil.getInstance().getSharedPreference(SpUtil.BEAUTY_SWITCH, 0);
        if (beautySwitch == 0) {
            if (mLiveBeautyViewHolder == null) {
                mLiveBeautyViewHolder = new DefaultBeautyViewHolder(mContext, live_dialog);
            }
            mLiveBeautyViewHolder.show();

        } else if (beautySwitch == 1) {
            if (null == liveBeautyComponent) {
                liveBeautyComponent = new LiveBeautyComponent(mContext, live_dialog);
            }
            liveBeautyComponent.show();

        } else if (beautySwitch == 2) {
            if (null == baBaBeautyComponent) {
                baBaBeautyComponent = new BaBaBeautyComponent(mContext, live_dialog);
                baBaBeautyComponent.show();
            } else {
                baBaBeautyComponent.show();
            }
        }
    }

    @Override
    public void init(String activityName, SocketClient socketClient) {
        mSocket = socketClient;
    }

    public void clean() {
        if (userFansGroupDialogFragment != null)
            userFansGroupDialogFragment.dismiss();
        if (goldInsufficientDialogFragment != null)
            goldInsufficientDialogFragment.dismiss();
        if (dialogFragment != null)
            dialogFragment.dismiss();
        if (moreDialogFragment != null)
            moreDialogFragment.dismiss();
        if (beautyDialogFragment != null)
            beautyDialogFragment.dismiss();
        if (firstRechargeDialogFragment != null)
            firstRechargeDialogFragment.dismiss();
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

        if (oooLiveUserEndDialogFragment != null)
            oooLiveUserEndDialogFragment.dismiss();

        if (audienceVipRechargeDialogFragment != null)
            audienceVipRechargeDialogFragment.dismiss();

        if (liveTreasureChestDialogFragment != null)
            liveTreasureChestDialogFragment.dismiss();
        if (null != giftdialogFragment)
            giftdialogFragment.dismiss();
        if ((int) SpUtil.getInstance().getSharedPreference(SpUtil.BEAUTY_SWITCH, 0) == 1) {
//            if (TiSDKManager.getInstance() != null) {
//                TiSDKManager.getInstance().destroy();
//            }
        } else if ((int) SpUtil.getInstance().getSharedPreference(SpUtil.BEAUTY_SWITCH, 0) == 2) {
//            BaBaBeautyUtil.getInstance().clear();
        }
        removeFromParent();
    }
}
