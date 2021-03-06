package com.kalacheng.live.component.componentlive;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.baseLive.httpApi.HttpApiPublicLive;
import com.kalacheng.buslivebas.entity.LiveRoomType;
import com.kalacheng.commonview.beauty.BaBaBeautyComponent;
import com.kalacheng.commonview.beauty.DefaultBeautyViewHolder;
import com.kalacheng.commonview.beauty.LiveBeautyComponent;
import com.kalacheng.commonview.dialog.GuardDialogFragment;
import com.kalacheng.commonview.dialog.NoMoneyTipsDialogFragment;
import com.kalacheng.commonview.dialog.RoomPayTipsDialogFragment;
import com.kalacheng.commonview.dialog.WishBillAddDialogFragment;
import com.kalacheng.commonview.music.dialog.LiveMusicDialogFragment1;
import com.kalacheng.commonview.utils.LookRoomUtlis;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.ApiExitRoom;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.live.R;
import com.kalacheng.live.component.fragment.AnchorMoreDialogFragment;
import com.kalacheng.live.component.fragment.AudienceMoreDialogFragment;
import com.kalacheng.live.component.viewmodel.LiveDialogViewModel;
import com.kalacheng.live.databinding.LiveDialogBinding;
import com.kalacheng.livecommon.fragment.AnchorSettingDialogFragment;
import com.kalacheng.livecommon.fragment.AnchorTaskDialogFragment;
import com.kalacheng.livecommon.fragment.AudienceTaskDialogFragment;
import com.kalacheng.livecommon.fragment.FansContributionDialogFragment;
import com.kalacheng.livecommon.fragment.FollowDialogFragment;
import com.kalacheng.livecommon.fragment.GoldInsufficientDialogFragment;
import com.kalacheng.livecommon.fragment.KickListDialogFragment;
import com.kalacheng.livecommon.fragment.LiveAdminListDialogFragment;
import com.kalacheng.livecommon.fragment.LiveChannelDialogFragment;
import com.kalacheng.livecommon.fragment.LiveExplainGoodsListDialogFragment;
import com.kalacheng.livecommon.fragment.LiveGapListDialogFragment;
import com.kalacheng.livecommon.fragment.LiveGoodsListDialogFragment;
import com.kalacheng.livecommon.fragment.LiveGoodsWindowDialogFragment;
import com.kalacheng.livecommon.fragment.LiveHotDialogFragment;
import com.kalacheng.livecommon.fragment.LiveShopActivityDialogFragment;
import com.kalacheng.livecommon.fragment.LiveTimeDialogFragment;
import com.kalacheng.livecommon.fragment.LiveUserDialogFragment;
import com.kalacheng.livecommon.fragment.ModifyRoomNoticeDialogFragment;
import com.kalacheng.livecommon.fragment.RoomModeDialogFragment;
import com.kalacheng.livecommon.fragment.TipsAddFansGroupDialogFragment;
import com.kalacheng.livecommon.fragment.UserFansGroupDialogFragment;
import com.kalacheng.livecommon.fragment.VoiceVipSeatsFragmentDialog;
import com.kalacheng.livecommon.fragment.VoiceVipSeatsListFragmentDialog;
import com.kalacheng.mob.MobConst;
import com.kalacheng.mob.MobShareUtil;
import com.kalacheng.money.utils.ChargeUtils;
import com.kalacheng.commonview.dialog.ShareDialog;
import com.kalacheng.commonview.dialog.LiveGiftDialogFragment;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.base.base.BaseMVVMViewHolder;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.klc.bean.LiveRoomTypeBean;
import com.klc.bean.OpenLiveBean;
import com.klc.bean.VoiceOpenLiveBean;
import com.wengying666.imsocket.SocketClient;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * ??????????????? Dialog ?????????
 */
public class LiveDialogFragmentComponent extends BaseMVVMViewHolder<LiveDialogBinding, LiveDialogViewModel> implements LiveBundle.onLiveSocket {

    public LiveDialogFragmentComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    private WishBillAddDialogFragment dialogFragmentWish;
    private LiveChannelDialogFragment liveChannelDialogFragment;
    private FansContributionDialogFragment lookMumberOrContributionListFragment;
    private RoomModeDialogFragment roomModeDialogFragment;
    private AnchorMoreDialogFragment anchorMoreDialogFragment;
    private AudienceMoreDialogFragment audienceMoreDialogFragment;
    private ShareDialog shareDialogFragment;
    private AnchorSettingDialogFragment anchorSettingDialogFragment;
    private LiveGapListDialogFragment liveGapListDialogFragment;
    private LiveAdminListDialogFragment liveAdminListDialogFragment;
    private KickListDialogFragment kickListDialogFragment;
    private GuardDialogFragment guardDialogFragment;
    private LiveGiftDialogFragment giftdialogFragment;
    private UserFansGroupDialogFragment userFansGroupDialogFragment;
    private AnchorTaskDialogFragment anchorTaskDialogFragment;
    private AudienceTaskDialogFragment audienceTaskDialogFragment;
    private LiveUserDialogFragment liveUserDialogFragment;
    private ModifyRoomNoticeDialogFragment modifyRoomNoticeDialogFragment;
    private LiveTimeDialogFragment liveTimeDialogFragment;
    private FollowDialogFragment followDialogFragment;
    private TipsAddFansGroupDialogFragment tipsAddFansGroupDialogFragment;
    private LiveHotDialogFragment liveHotDialogFragment;
    private VoiceVipSeatsFragmentDialog voiceVipSeatsFragmentDialog;
    private LiveGoodsWindowDialogFragment liveGoodsWindowDialogFragment;
    private LiveShopActivityDialogFragment liveShopActivityDialogFragment;
    private LiveGoodsListDialogFragment liveGoodsListDialogFragment;
    private LiveExplainGoodsListDialogFragment liveExplainGoodsListDialogFragment;
    private LiveMusicDialogFragment1 musicDialogFragment1;
    private RoomPayTipsDialogFragment roomPayTipsDialogFragment; // ???????????? ????????????
    private NoMoneyTipsDialogFragment noMoneyTipsDialogFragment; // ???????????? ????????????
    private GoldInsufficientDialogFragment goldInsufficientDialogFragment; //?????? ????????????????????????
//    private UpdateRoomModeTipsDialogFragment updateRoomModeTipsDialogFragment; // ??????????????????????????? ?????????????????????

    // ????????????
    private DefaultBeautyViewHolder mLiveBeautyViewHolder;
    private BaBaBeautyComponent baBaBeautyComponent;

    private Disposable timeDisposable;
    private long OpenLiveTime = 0;
    private LiveBeautyComponent liveBeautyComponent;
    //???????????????????????????
    public int isJoinSeats;

    private FrameLayout live_dialog;
    //????????????
    private boolean muteAllRemote = false;

    @Override
    public void init(String activityName, SocketClient socketClient) {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.live_dialog;
    }

    @Override
    protected void init() {
        LiveBundle.getInstance().addLiveSocketListener(this);
        addToParent();
        live_dialog = mParentView.findViewById(R.id.live_dialog);

        // ?????????????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.RoomInfoList, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                viewModel.joinRoom.set((AppJoinRoomVO) o);
                timeDisposable = Observable.interval(1000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        OpenLiveTime = aLong;
                    }
                });
                isJoinSeats = viewModel.joinRoom.get().isJoinSeats;
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        // ???????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ExitRoom, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                clear();
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        // ????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LaunchCloseLinkMic, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ChoiceChannelType, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                liveChannelDialogFragment = new LiveChannelDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("LiveType", (int) o);
                liveChannelDialogFragment.setArguments(bundle);
                liveChannelDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveChannelDialogFragment");
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //??????????????????????????? ??????????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_AddWishList, new MsgListener.SimpleMsgListener<Integer>() {
            @Override
            public void onMsg(Integer integer) {
                dialogFragmentWish = new WishBillAddDialogFragment();
                Bundle bundle = new Bundle();
                if (viewModel.joinRoom.get() == null) {
                    bundle.putLong("RoomID", 0);
                } else {
                    bundle.putLong("RoomID", viewModel.joinRoom.get().roomId);
                }
                bundle.putLong("UserID", -1);
                dialogFragmentWish.setArguments(bundle);
                FragmentTransaction ft1 = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                ft1.add(dialogFragmentWish, this.getClass().getSimpleName());
                ft1.commitAllowingStateLoss();
            }

            @Override
            public void onTagMsg(String tag, Integer integer) {
            }
        });

        //??????????????????????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LookNumber, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                lookMumberOrContributionListFragment = new FansContributionDialogFragment();
                lookMumberOrContributionListFragment.setRoomType((Integer) o);
                if (viewModel.joinRoom.get().userList != null) {
                    lookMumberOrContributionListFragment.getWatchNumberList(viewModel.joinRoom.get().userList);
                }
                lookMumberOrContributionListFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "FansContributionDialogFragment");
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LiveMore, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                if (HttpClient.getUid() == LiveConstants.ANCHORID) {
                    anchorMoreDialogFragment = new AnchorMoreDialogFragment();
                    Bundle TrendBundle = new Bundle();
                    TrendBundle.putBoolean("muteAllRemote", muteAllRemote);
                    TrendBundle.putInt("liveFunction", viewModel.joinRoom.get().liveFunction);
                    anchorMoreDialogFragment.setArguments(TrendBundle);
                    anchorMoreDialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "AnchorMoreDialogFragment");
                } else {
                    audienceMoreDialogFragment = new AudienceMoreDialogFragment();
                    audienceMoreDialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "AudienceMoreDialogFragment");
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        // ?????????????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OPEN_CLOSE_MIC, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                try {
                    muteAllRemote = (boolean) o;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //??????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LiveShare, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                shareDialogFragment = new ShareDialog();
                shareDialogFragment.setShareDialogListener(new ShareDialog.ShareDialogListener() {
                    @Override
                    public void onItemClick(long id) {
                        if (id == 1) {
                            MobShareUtil.getInstance().shareLive(1, 1, MobConst.Type.WX);
                        } else if (id == 2) {
                            MobShareUtil.getInstance().shareLive(1, 1, MobConst.Type.WX_PYQ);
                        } else if (id == 3) {
                            MobShareUtil.getInstance().shareLive(1, 1, MobConst.Type.QQ);
                        } else if (id == 4) {
                            MobShareUtil.getInstance().shareLive(1, 1, MobConst.Type.QZONE);
                        }
                    }
                });
                shareDialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "ShareDialog");
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LiveSetting, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                anchorSettingDialogFragment = new AnchorSettingDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("ApiJoinRoom", viewModel.joinRoom.get());
                anchorSettingDialogFragment.setArguments(bundle);
                anchorSettingDialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "AnchorSettingDialogFragment");
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LiveForbidden, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                liveGapListDialogFragment = new LiveGapListDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("AnchorId", viewModel.joinRoom.get().anchorId);
                bundle.putLong(ARouterValueNameConfig.Livetype, viewModel.joinRoom.get().liveType);
                liveGapListDialogFragment.setArguments(bundle);
                liveGapListDialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "LiveAdminListDialogFragment");
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //???????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LiveAdministrators, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                liveAdminListDialogFragment = new LiveAdminListDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("AnchorId", viewModel.joinRoom.get().anchorId);
                bundle.putLong(ARouterValueNameConfig.Livetype, viewModel.joinRoom.get().liveType);
                liveAdminListDialogFragment.setArguments(bundle);
                liveAdminListDialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "LiveAdminListDialogFragment");
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_KickList, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                kickListDialogFragment = new KickListDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("AnchorId", viewModel.joinRoom.get().anchorId);
                bundle.putLong(ARouterValueNameConfig.Livetype, viewModel.joinRoom.get().liveType);
                kickListDialogFragment.setArguments(bundle);
                kickListDialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "KickListDialogFragment");
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //??????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LiveGuard, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                AppJoinRoomVO apiJoinRoom = viewModel.joinRoom.get();
                if (apiJoinRoom != null) {
                    guardDialogFragment = new GuardDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 0);
                    bundle.putLong("anchorId", apiJoinRoom.anchorId);
                    bundle.putString("anchorAvatar", apiJoinRoom.anchorAvatar);
                    bundle.putString("anchorName", apiJoinRoom.anchorName);
                    guardDialogFragment.setArguments(bundle);
                    guardDialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "GuardDialogFragment");
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //????????????
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

        //???????????????
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

        //????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_AnchorTask, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                // ?????????????????????
                ARouter.getInstance().build(ARouterPath.UserTaskCenterActivity).navigation();
//                anchorTaskDialogFragment = new AnchorTaskDialogFragment();
//                anchorTaskDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "AnchorTaskDialogFragment");
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_AudienceTask, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                // ?????????????????????
                ARouter.getInstance().build(ARouterPath.UserTaskCenterActivity).navigation();
//                audienceTaskDialogFragment = new AudienceTaskDialogFragment();
//                audienceTaskDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "AudienceTaskDialogFragment");
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //??????????????????
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
                liveUserDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveUserDialogFragment");
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ModifyRoomNotice, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                modifyRoomNoticeDialogFragment = new ModifyRoomNoticeDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("ApiJoinRoom", viewModel.joinRoom.get());
                modifyRoomNoticeDialogFragment.setArguments(bundle);
                modifyRoomNoticeDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "ModifyRoomNoticeDialogFragment");
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LiveTime, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                liveTimeDialogFragment = new LiveTimeDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("Time", OpenLiveTime);
                liveTimeDialogFragment.setArguments(bundle);
                liveTimeDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveTimeDialogFragment");
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //???????????????
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

        //??????
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

        //??????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_BeautyShow, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                showBeautyDialog();
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //????????????2?????????????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_UserFollew, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                followDialogFragment = new FollowDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("ApiJoinRoom", viewModel.joinRoom.get());
                followDialogFragment.setArguments(bundle);
                followDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "followDialogFragment");
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //??????3???????????????????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_TipsAddFansGroup, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                tipsAddFansGroupDialogFragment = new TipsAddFansGroupDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("ApiJoinRoom", viewModel.joinRoom.get());
                tipsAddFansGroupDialogFragment.setArguments(bundle);
                tipsAddFansGroupDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "TipsAddFansGroupDialogFragment");
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        // ???????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_VoiceBuyVipSeats, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                //???????????????
                if (LiveConstants.ANCHORID == HttpClient.getUid()) {
                    //???????????????
                    VoiceVipSeatsListFragmentDialog voiceVipSeatsListFragmentDialog = new VoiceVipSeatsListFragmentDialog();
                    voiceVipSeatsListFragmentDialog.getInfotmation(viewModel.joinRoom.get());
                    voiceVipSeatsListFragmentDialog.show(((FragmentActivity) mContext).getSupportFragmentManager(), "VoiceVipSeatsListFragmentDialog");
                } else {
                    if (isJoinSeats == 0) {
                        voiceVipSeatsFragmentDialog = new VoiceVipSeatsFragmentDialog();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("apiJoinRoom", viewModel.joinRoom.get());
                        voiceVipSeatsFragmentDialog.setArguments(bundle);
                        voiceVipSeatsFragmentDialog.show(((FragmentActivity) mContext).getSupportFragmentManager(), "VoiceVipSeatsFragmentDialog");
                    } else {
                        //???????????????
                        VoiceVipSeatsListFragmentDialog voiceVipSeatsListFragmentDialog = new VoiceVipSeatsListFragmentDialog();
                        voiceVipSeatsListFragmentDialog.getInfotmation(viewModel.joinRoom.get());
                        voiceVipSeatsListFragmentDialog.show(((FragmentActivity) mContext).getSupportFragmentManager(), "VoiceVipSeatsListFragmentDialog");
                    }
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //?????????????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_VoiceBuyVipSeatsSuccess, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                isJoinSeats = 1;
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //?????????????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LiveGoodsWindow, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                liveGoodsWindowDialogFragment = new LiveGoodsWindowDialogFragment();
                liveGoodsWindowDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveGoodsWindowDialogFragment");
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //??????????????????????????? ?????? ??????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LiveGoodsActivity, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                liveShopActivityDialogFragment = new LiveShopActivityDialogFragment();
                liveShopActivityDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveShopActivityDialogFragment");
                liveShopActivityDialogFragment.getActivityImage(viewModel.joinRoom.get().shopLiveBanner);
                liveShopActivityDialogFragment.setListener(new LiveShopActivityDialogFragment.ShopLiveBannerListener() {
                    @Override
                    public void onSuccessUrl(String url) {
                        viewModel.joinRoom.get().shopLiveBanner = url;
                    }
                });
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //???????????????????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LiveGoodsList, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (HttpClient.getUid() == LiveConstants.ANCHORID) {
                    liveExplainGoodsListDialogFragment = new LiveExplainGoodsListDialogFragment();
                    liveExplainGoodsListDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveExplainGoodsListDialogFragment");
                } else {
                    if ((int) o == 1) {
                        liveGoodsListDialogFragment = new LiveGoodsListDialogFragment();
                        liveGoodsListDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveGoodsListDialogFragment");
                    } else if ((int) o == 2) {
                        liveExplainGoodsListDialogFragment = new LiveExplainGoodsListDialogFragment();
                        liveExplainGoodsListDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveExplainGoodsListDialogFragment");
                    }
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        // ????????????????????????????????? type   ??????????????????????????? dismiss???????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_UPDATE_LIVE_STATUS, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                try {
                    if ((int) o == 0 && null != liveExplainGoodsListDialogFragment) {
                        liveExplainGoodsListDialogFragment.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //????????????
//        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_Music, new MsgListener.SimpleMsgListener() {
//            @Override
//            public void onMsg(Object o) {
//                if (!ConfigUtil.getBoolValue(R.bool.useMusicOld)) {
//                    if (musicDialogFragment1 != null) {
//                        musicDialogFragment1.dismiss();
//                        musicDialogFragment1 = null;
//                    }
//                    musicDialogFragment1 = new LiveMusicDialogFragment1((FragmentActivity) mContext);
//                    musicDialogFragment1.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveMusicDialogFragment1");
//                }
//            }
//
//            @Override
//            public void onTagMsg(String tag, Object o) {
//            }
//        });

        //??????????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ChoiceLiveType, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                final LiveRoomTypeBean bean = (LiveRoomTypeBean) o;
                roomModeDialogFragment = new RoomModeDialogFragment();
                //??????????????????
                String showId = "";
                if (null != viewModel.joinRoom.get() && !TextUtils.isEmpty(viewModel.joinRoom.get().showid)) {
                    showId = viewModel.joinRoom.get().showid;
                } else {
                    showId = "-1";
                }
                HttpApiPublicLive.getLiveRoomType(bean.LiveType, showId, new HttpApiCallBackArr<LiveRoomType>() {
                    @Override
                    public void onHttpRet(int code, String msg, List<LiveRoomType> retModel) {
                        if (code == 1 && null != retModel && retModel.size() > 0) {
                            Bundle bundle = new Bundle();
                            if (bean.type == 2) {
                                bundle.putString("showId", "-1");
                            } else if (bean.type == 1) {
                                bundle.putString("showId", viewModel.joinRoom.get().showid);
                            }
                            bundle.putParcelable("LiveRoomTypeBean", bean);
                            roomModeDialogFragment.setArguments(bundle);
                            roomModeDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "RoomModeDialogFragment");
                        } else {
                            ToastUtil.show(msg);
                        }
                    }
                });
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //?????????????????? ??????????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ChoiceLiveTypeSusser, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (null == o) {
                    return;
                }
                if (o instanceof OpenLiveBean) {
                    OpenLiveBean openLiveBean = (OpenLiveBean) o;
                    viewModel.joinRoom.get().roomType = openLiveBean.type;
                    viewModel.joinRoom.get().roomTypeVal = openLiveBean.typeVal;
                } else if (o instanceof VoiceOpenLiveBean) {
                    VoiceOpenLiveBean voiceOpenLiveBean = (VoiceOpenLiveBean) o;
                    viewModel.joinRoom.get().roomType = voiceOpenLiveBean.type;
                    viewModel.joinRoom.get().roomTypeVal = voiceOpenLiveBean.typeVal;
                }
                //???????????????
                if (LiveConstants.ANCHORID != HttpClient.getUid()) {
                    if (o instanceof ApiExitRoom){
                        ApiExitRoom apiExitRoom = ((ApiExitRoom) o);
                        ToastUtil.show("??????????????????????????????" + apiExitRoom.roomName);
                        viewModel.joinRoom.get().roomType = apiExitRoom.roomType;
                        viewModel.joinRoom.get().roomTypeVal = apiExitRoom.roomTypeVal;
                    }
                    cleanMoneyDialog();
                    if (viewModel.joinRoom.get().roomType != 0 && viewModel.joinRoom.get().roomType != 4) {
                        showRoomPayTipsDialog(2);
                    } else if (viewModel.joinRoom.get().roomType == 4) {
                        startDeduction(viewModel.joinRoom.get(), 2);
                    }else if (viewModel.joinRoom.get().roomType == 0){
                        // ??????????????????????????? ????????????????????? dismiss????????????????????????dialog  ?????????????????? ???????????????
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_StopAndPlayMedia, false);
                    }
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //???????????? 3?????? 1?????? ?????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOGoldInsufficient, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                goldInsufficientDialogFragment = new GoldInsufficientDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("Time", (int) o);
                goldInsufficientDialogFragment.setArguments(bundle);
                goldInsufficientDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "OOOLiveEndDialogFragment");
                goldInsufficientDialogFragment.setListener(new GoldInsufficientDialogFragment.GoldInsufficientListener() {
                    @Override
                    public void onClose() {
                        // ???????????????????????? dismiss ???????????? ?????????????????????Fragment ?????????????????? dismiss ??????????????????
                        goldInsufficientDialogFragment.dismiss();
                    }
                });
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //?????????????????? ???????????? ?????????????????????????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_NoMoney, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (null == o) {
                    noMoneyTipsDialogFragment = new NoMoneyTipsDialogFragment();
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_StopAndPlayMedia, true);
                    noMoneyTipsDialogFragment.setCanCancel(false);
                    Bundle bundle = new Bundle();
                    bundle.putDouble("money", 0);
                    bundle.putParcelable("AppJoinRoomVO", viewModel.joinRoom.get());
                    noMoneyTipsDialogFragment.setArguments(bundle);
                    noMoneyTipsDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "NoMoneyTipsDialogFragment");
                    noMoneyTipsDialogFragment.setListener(new NoMoneyTipsDialogFragment.NoMoneyTipsListener() {
                        @Override
                        public void goRecharge() {
                            ARouter.getInstance().build(ARouterPath.MyCoinActivity).navigation((Activity) mContext, 7101);
                            noMoneyTipsDialogFragment.dismiss();
                            showRoomPayTipsDialog(1);
                        }

                        @Override
                        public void close() {
                            noMoneyTipsDialogFragment.dismiss();
                            LookRoomUtlis.getInstance().closeLive();
                        }
                    });
                } else {
                    ARouter.getInstance().build(ARouterPath.MyCoinActivity).navigation();
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //????????????  ??????????????????????????????????????????dialog    ?????????????????? ?????????????????????????????????
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_Deduction, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (null != o && (int) o == 1) {
                    showRoomPayTipsDialog(1);
                } else if (null != o && (int) o == 2) {
                    startDeduction(viewModel.joinRoom.get(), 1);
                }

            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });
    }

    // ???????????? ???????????? ???????????? ????????????
    // type 1????????????????????????   2????????????????????????   ????????????show??????????????? ?????????show??????
    private void showRoomPayTipsDialog(int type) {
        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_StopAndPlayMedia, true);
        if (null == roomPayTipsDialogFragment || !roomPayTipsDialogFragment.isVisible()) {
            roomPayTipsDialogFragment = new RoomPayTipsDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", type);
            bundle.putParcelable("AppJoinRoomVO", viewModel.joinRoom.get());
            roomPayTipsDialogFragment.setArguments(bundle);
            roomPayTipsDialogFragment.setCanCancel(false);
            roomPayTipsDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "RoomPayTipsDialogFragment");
            roomPayTipsDialogFragment.setListener(new RoomPayTipsDialogFragment.VideoPayTipsChoiceListener() {
                @Override
                public void close() {
                    LookRoomUtlis.getInstance().closeLive();
                }
                @Override
                public void pey() {
                    HttpApiPublicLive.startDeduction(viewModel.joinRoom.get().anchorId, viewModel.joinRoom.get().liveType, viewModel.joinRoom.get().roomId, viewModel.joinRoom.get().roomType, viewModel.joinRoom.get().roomTypeVal, viewModel.joinRoom.get().showid, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            if (code == 1) {
                                // ??????????????????????????????????????? ??????????????? ??????????????????????????????
                                cleanMoneyDialog();
                                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_StopAndPlayMedia, false);
                            } else if (code == 7101) {
                                // ????????????????????????????????? ??????????????? ??????????????????
                                if (viewModel.joinRoom.get().roomType == 2 || viewModel.joinRoom.get().roomType == 3) {
                                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_NoMoney, null);
                                    roomPayTipsDialogFragment.dismiss();
                                }
                            } else if (code == 99) {
                                if (viewModel.joinRoom.get().roomType == 4) {
                                    ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                                            + HttpConstants.URL_NOBLE + "_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken()).navigation((Activity) mContext, 99);
                                }
                            } else {
                                ToastUtil.show(msg);
                            }
                        }
                    });
                }
            });
        }
    }

    // ?????? ?????? ?????? ????????????  type 1????????????????????????   2????????????????????????
    private void startDeduction(final AppJoinRoomVO joinRoom, final int type) {
        HttpApiPublicLive.startDeduction(
                joinRoom.anchorId,
                joinRoom.liveType,
                joinRoom.roomId,
                joinRoom.roomType,
                joinRoom.roomTypeVal,
                joinRoom.showid, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            // ??????????????????????????????????????? ??????????????? ??????????????????????????????
                            // ?????????????????????
                            cleanMoneyDialog();
                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_StopAndPlayMedia, false);
                        } else if (code == 7101) {
                            // ????????????????????????????????? ??????????????? ??????????????????
                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_StopAndPlayMedia, true);
                            showRoomPayTipsDialog(type);
                        } else if (code == 99) {
                            // ??????
                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_StopAndPlayMedia, true);
                            showRoomPayTipsDialog(type);
                        } else {
                            ToastUtil.show(msg);
                        }
                    }
                });
    }

    // dismiss????????????????????????????????? Dialog
    private void cleanMoneyDialog(){
        if (null != goldInsufficientDialogFragment && goldInsufficientDialogFragment.getDialog().isShowing()){
            goldInsufficientDialogFragment.dismiss();
        }
        if (null != noMoneyTipsDialogFragment && noMoneyTipsDialogFragment.getDialog().isShowing()){
            noMoneyTipsDialogFragment.dismiss();
        }
        if (null != roomPayTipsDialogFragment && roomPayTipsDialogFragment.getDialog().isShowing()){
            roomPayTipsDialogFragment.dismiss();
        }
    }

    //???????????????????????????
    public void getFirstRecharge() {
        ChargeUtils.getInstance().init(mContext).showChargeDialog();
    }

    public void showBeautyDialog() {
        int beautySwitch = (int) SpUtil.getInstance().getSharedPreference(SpUtil.BEAUTY_SWITCH, 0);
        if (beautySwitch == 0) {
            mLiveBeautyViewHolder = new DefaultBeautyViewHolder(mContext, live_dialog);
            mLiveBeautyViewHolder.show();
        } else if (beautySwitch == 1) {
            if (null == liveBeautyComponent) {
                liveBeautyComponent = new LiveBeautyComponent(mContext, live_dialog);
            }
            liveBeautyComponent.show();
        } else if (beautySwitch == 2) {
            if (null == baBaBeautyComponent) {
                baBaBeautyComponent = new BaBaBeautyComponent(mContext, live_dialog);
            }
            baBaBeautyComponent.show();
        }
    }

    private void clear() {
        ChargeUtils.getInstance().dismiss();
        if (null != dialogFragmentWish)
            dialogFragmentWish.dismiss();
        if (null != liveChannelDialogFragment)
            liveChannelDialogFragment.dismiss();
        if (null != lookMumberOrContributionListFragment)
            lookMumberOrContributionListFragment.dismiss();
        if (null != roomModeDialogFragment && roomModeDialogFragment.isVisible())
            roomModeDialogFragment.dismiss();
        if (null != anchorMoreDialogFragment)
            anchorMoreDialogFragment.dismiss();
        if (null != audienceMoreDialogFragment)
            audienceMoreDialogFragment.dismiss();
        if (null != shareDialogFragment)
            shareDialogFragment.dismiss();
        if (null != anchorSettingDialogFragment)
            anchorSettingDialogFragment.dismiss();
        if (null != liveGapListDialogFragment)
            liveGapListDialogFragment.dismiss();
        if (null != liveAdminListDialogFragment)
            liveAdminListDialogFragment.dismiss();
        if (null != kickListDialogFragment)
            kickListDialogFragment.dismiss();
        if (null != guardDialogFragment)
            guardDialogFragment.dismiss();
        if (null != giftdialogFragment)
            giftdialogFragment.dismiss();
        if (null != userFansGroupDialogFragment)
            userFansGroupDialogFragment.dismiss();
        if (null != anchorTaskDialogFragment)
            anchorTaskDialogFragment.dismiss();
        if (null != audienceTaskDialogFragment)
            audienceTaskDialogFragment.dismiss();
        if (null != liveUserDialogFragment)
            liveUserDialogFragment.dismiss();
        if (null != modifyRoomNoticeDialogFragment)
            modifyRoomNoticeDialogFragment.dismiss();
        if (null != liveTimeDialogFragment)
            liveTimeDialogFragment.dismiss();
        if (null != followDialogFragment)
            followDialogFragment.dismiss();
        if (null != liveHotDialogFragment)
            liveHotDialogFragment.dismiss();
        if (null != tipsAddFansGroupDialogFragment)
            tipsAddFansGroupDialogFragment.dismiss();
        if (null != voiceVipSeatsFragmentDialog)
            voiceVipSeatsFragmentDialog.dismiss();
        if (null != liveGoodsWindowDialogFragment) {
            liveGoodsWindowDialogFragment.dismiss();
        }
        if (null != liveShopActivityDialogFragment) {
            liveShopActivityDialogFragment.dismiss();
        }
        if (null != liveGoodsListDialogFragment) {
            liveGoodsListDialogFragment.dismiss();
        }
        if (null != liveExplainGoodsListDialogFragment) {
            liveExplainGoodsListDialogFragment.dismiss();
        }
        if (null != noMoneyTipsDialogFragment) {
            noMoneyTipsDialogFragment.dismiss();
        }
        if (null != goldInsufficientDialogFragment) {
            goldInsufficientDialogFragment.dismiss();
        }
        if (null != roomPayTipsDialogFragment) {
            roomPayTipsDialogFragment.dismiss();
        }

        if (null != timeDisposable)
            timeDisposable.dispose();
        if (mLiveBeautyViewHolder != null && mLiveBeautyViewHolder.isShowed()) {
            mLiveBeautyViewHolder.hide();
            mLiveBeautyViewHolder = null;
        }
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
