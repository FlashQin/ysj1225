package com.kalacheng.onevoicelive.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.base.BaseMVVMViewHolder;
import com.kalacheng.busgraderight.httpApi.HttpApiNoble;
import com.kalacheng.busooolive.model.OOOReturn;
import com.kalacheng.commonview.utils.LiveMusicView;
import com.kalacheng.libuser.model.VipPrivilegeDto;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buscommon.socketmsg.IMRcvCommonMsgSend;
import com.kalacheng.busdynamiccircle.socketmsg.IMRcvDynamiccircleSend;
import com.kalacheng.busgraderight.socketmsg.IMRcvGradeRightMsgSender;
import com.kalacheng.buslivebas.socketmsg.IMRcvLiveMsgSend;
import com.kalacheng.busliveplugin.socketmsg.IMRcvLiveWishSend;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiAnchorWishList;
import com.kalacheng.libuser.model.ApiElasticFrame;
import com.kalacheng.libuser.model.ApiExitRoom;
import com.kalacheng.libuser.model.ApiGiftSender;
import com.kalacheng.libuser.model.ApiSendMsgRoom;
import com.kalacheng.libuser.model.ApiSendVideoUnReadNumber;
import com.kalacheng.libuser.model.ApiSimpleMsgRoom;
import com.kalacheng.libuser.model.ApiTimerExitRoom;
import com.kalacheng.libuser.model.ApiUsersLiveWish;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.onevoicelive.R;
import com.kalacheng.onevoicelive.databinding.OneVoiceInformationBinding;
import com.kalacheng.onevoicelive.viewmodel.OneVoiceInformationViewModel;
import com.kalacheng.shortvideo.socketmsg.IMRcvShortVideoSend;
import com.kalacheng.util.utils.StringUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wengying666.imsocket.IMUtil;
import com.wengying666.imsocket.SocketClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class OneVoiceInformationComponent extends BaseMVVMViewHolder<OneVoiceInformationBinding, OneVoiceInformationViewModel> implements LiveBundle.onLiveSocket, View.OnClickListener {

    private Disposable timeDisposable;
    private ApiUserInfo apiUserInfo;
    private boolean isUserToUser;
    private int IsFollow = 0;
    private long isFree = 0;

    public OneVoiceInformationComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.one_voice_information;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void init() {
        LiveBundle.getInstance().addLiveSocketListener(this);

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
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.RoomInfoList, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                addToParent();
                apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel("UserInfo", ApiUserInfo.class);
                viewModel.joinRoom.set((AppJoinRoomVO) o);
                getInit();
                getWishList();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveLinkOK, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                OOOReturn anchorinfo = (OOOReturn) o;
                //是否是用户和用户通话 如果是不显示对方金币
                if (anchorinfo.userToUser == 1) {
                    isUserToUser = true;
                } else {
                    isUserToUser = false;
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        //添加心愿单成功
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_AddWishList, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //关闭通话的请求
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOEndRequestGetTime, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOEndRequest, binding.liveTime.getText().toString());
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //通话结束 停止倒计时
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOCallEnd, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (timeDisposable != null) {
                    timeDisposable.dispose();
                }

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //金币更新
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOUpDataGold, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                binding.liveUserGold.setText("他的余额 " + String.valueOf(o));
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_MessageForGift, new MsgListener.SimpleMsgListener<ApiGiftSender>() {
            @Override
            public void onMsg(ApiGiftSender apiGiftSender) {
                binding.tvAnchorVoiceHot.setText(String.valueOf(apiGiftSender.votes));
            }

            @Override
            public void onTagMsg(String tag, ApiGiftSender apiGiftSender) {

            }
        });

        //该用户有免费通话时长提示
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OneFeeCall, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (o != null) {
                    if (LiveConstants.ANCHORID == HttpClient.getUid()) {
                        if (TextUtils.isEmpty(((String) o))) {
                            binding.vipFree.setVisibility(View.GONE);
                        } else {
                            binding.vipFree.setVisibility(View.VISIBLE);
                            binding.vipFree.setText("小主：该用户正在使用" + (String) o + "与你聊天，尽情展示你的魅力留住他！奥力给");
                        }
                    } else {
                        binding.vipFree.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });


        binding.tvAnchorFollow.setOnClickListener(this);
        binding.ivAnchorAvatar.setOnClickListener(this);
        binding.tvAnchorVoiceHot.setOnClickListener(this);

        getNobleBarrage();
    }

    public void getInit() {
        //视频计时
        timeDisposable = Observable.interval(1000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                binding.liveTime.setText(StringUtil.getDurationText(aLong * 1000));
                // 主播免费时长展示10秒钟 隐藏
                if (isFree < 11) {
                    isFree++;
                    if (isFree == 10 && binding.vipFree.getVisibility() == View.VISIBLE) {
                        binding.vipFree.setVisibility(View.GONE);
                    }
                }
            }
        });

        if (isUserToUser) {
            // 判断直播间内  通话的两人是否都是普通用户 如果两人都是用户 不展示双方的金币
            binding.liveUserGoldLin.setVisibility(View.GONE);
        } else if (LiveConstants.FEEUID == HttpClient.getUid()) {
            // 如果是观众 消费者 不展示对方金币
            binding.liveUserGoldLin.setVisibility(View.GONE);
        } else if (LiveConstants.ANCHORID == HttpClient.getUid()) {
            // 如果是主播 才展示对方金币
            binding.liveUserGoldLin.setVisibility(View.VISIBLE);
        }
        if (LiveConstants.FEEUID == HttpClient.getUid()) {
            binding.liveUserGoldLin.setVisibility(View.GONE);
        } else {
            if ((int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_ISSHWCOIN, 0) == 0) {
                binding.liveUserGoldLin.setVisibility(View.VISIBLE);
            } else {
                binding.liveUserGoldLin.setVisibility(View.GONE);
            }
        }

        if (LiveConstants.FEEUID == HttpClient.getUid()) {
            IsFollow = viewModel.joinRoom.get().isFollow;
            if (viewModel.joinRoom.get().isFollow == 0) {
                binding.tvAnchorFollow.setBackgroundResource(R.mipmap.follow_icon);
            } else {
                binding.tvAnchorFollow.setBackgroundResource(R.mipmap.followed_icon);
            }
        } else {
            binding.tvAnchorFollow.setBackgroundResource(R.mipmap.followed_icon);
        }
    }

    //获取心愿单
    public void getWishList() {
        HttpApiAnchorWishList.getWishList(viewModel.joinRoom.get().anchorId, new HttpApiCallBackArr<ApiUsersLiveWish>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiUsersLiveWish> retModel) {
                if (code == 1) {
                    getMarquee(retModel);
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    //心愿单展示跑马灯
    public void getMarquee(List<ApiUsersLiveWish> awList) {
        binding.VoiceLiveMarquee.removeAllViews();
        if (null != awList && awList.size() > 0) {
            binding.VoiceLiveMarquee.setVisibility(View.VISIBLE);
            for (int i = 0; i < awList.size(); i++) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.will_roll_item, null);
                ((TextView) view.findViewById(R.id.gift_name)).setText(awList.get(i).giftname + " " + awList.get(i).sendNum + "/" + awList.get(i).num);
                RoundedImageView imageView = (RoundedImageView) view.findViewById(R.id.gift_image);
                ImageLoader.display(awList.get(i).gifticon, imageView);
                binding.VoiceLiveMarquee.addView(view);
            }
        } else {
            binding.VoiceLiveMarquee.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvAnchorFollow) {//关注
            if (LiveConstants.FEEUID == HttpClient.getUid()) {
                if (IsFollow == 1) {
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_AddFansGroup, null);
                } else {
                    getFollow();
                }
            } else {
                ARouter.getInstance().build(ARouterPath.FansGroupActivity).navigation();
            }
        } else if (view.getId() == R.id.ivAnchorAvatar) {//查看信息
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.Information, null);
        } else if (view.getId() == R.id.tvAnchorVoiceHot) {
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveHot, null);
        }
    }

    public void clean() {
        if (null != timeDisposable) {
            timeDisposable.dispose();
        }
        removeFromParent();
        //关闭播放器
        if (!ConfigUtil.getBoolValue(R.bool.useMusicOld)) {
            LiveMusicView.getInstance().close();
        }
    }

    @Override
    public void init(String groupName, SocketClient socketClient) {

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
            public void onSimpleMsgAll(ApiSimpleMsgRoom apiSimpleMsgRoom) {

            }

            @Override
            public void onSimpleMsgRoom(ApiSimpleMsgRoom apiSimpleMsgRoom) {

            }


            @Override
            public void onUserNoticMsg(String conetnt) {

            }

            @Override
            public void onUserSendMsgRoom(ApiSendMsgRoom apiSendMsgRoom) {

            }

            @Override
            public void onUserSendApiJoinRoom(AppJoinRoomVO apiJoinRoom) {

            }

            @Override
            public void onTimerExitRoom(ApiTimerExitRoom apiTimerExitRoom) {

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
            public void onElasticFrameMedal(ApiElasticFrame elasticFrame) {

            }

            @Override
            public void onElasticFrameFinshTask(ApiElasticFrame elasticFrame) {

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

            @Override
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

    }

    //关注
    public void getFollow() {
        HttpApiAppUser.set_atten(1, viewModel.joinRoom.get().anchorId, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    IsFollow = 1;
                    binding.tvAnchorFollow.setBackgroundResource(R.mipmap.followed_icon);
                } else {
                    ToastUtil.show(msg);
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
