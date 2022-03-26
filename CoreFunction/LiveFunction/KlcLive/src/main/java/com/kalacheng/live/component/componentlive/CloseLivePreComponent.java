package com.kalacheng.live.component.componentlive;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.kalacheng.base.base.BaseMVVMViewHolder;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.buscommon.model.GuardUserDto;
import com.kalacheng.buslivebas.socketmsg.IMRcvLiveSend;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.buspersonalcenter.socketmsg.IMRcvUserMsgSender;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.ApiBeautifulNumber;
import com.kalacheng.libuser.model.ApiCloseLive;
import com.kalacheng.libuser.model.ApiJoinRoomAnchor;
import com.kalacheng.libuser.model.ApiKickLive;
import com.kalacheng.libuser.model.ApiLeaveRoom;
import com.kalacheng.libuser.model.ApiLeaveRoomAnchor;
import com.kalacheng.libuser.model.ApiShopLiveGoods;
import com.kalacheng.libuser.model.ApiSimpleMsgRoom;
import com.kalacheng.libuser.model.ApiUserSeats;
import com.kalacheng.libuser.model.ApiUsersVoiceAssistan;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.libuser.model.UserBuyDTO;
import com.kalacheng.live.R;
import com.kalacheng.live.component.viewmodel.ApiCloseLiveViewModel;
import com.kalacheng.live.databinding.ViewLiveEndBinding;
import com.kalacheng.shop.socketmsg.IMRcvShopMsgSend;
import com.kalacheng.util.utils.StringUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.wengying666.imsocket.IMUtil;
import com.wengying666.imsocket.SocketClient;

import java.util.List;

public class CloseLivePreComponent extends BaseMVVMViewHolder<ViewLiveEndBinding, ApiCloseLiveViewModel> implements LiveBundle.onLiveSocket {


    public CloseLivePreComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_live_end;
    }

    @Override
    protected void init() {
        LiveBundle.getInstance().addLiveSocketListener(this);

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_HttpCloseLive, new MsgListener.SimpleMsgListener<ApiCloseLive>() {
            @Override
            public void onMsg(ApiCloseLive apiCloseLive) {
                addToParent();
                closeLive(apiCloseLive);
            }

            @Override
            public void onTagMsg(String tag, ApiCloseLive apiCloseLive) {

            }
        });
//        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ExitRoom, new MsgListener.SimpleMsgListener() {
//            @Override
//            public void onMsg(Object o) {
//                removeFromParent();
//            }
//        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.RoomInfoList, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                viewModel.apijoinroom.set((AppJoinRoomVO) o);


            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
    }

    @Override
    public void init(String groupName, SocketClient socketClient) {
        IMUtil.addReceiver(groupName, new IMRcvLiveSend() {

            @Override
            public void onJoinRoomMsgRoom(ApiSimpleMsgRoom apiSimpleMsgRoom) {

            }

            @Override
            public void onCloseLive(ApiCloseLive apiCloseLive) {
                if (LiveConstants.IDENTITY == LiveConstants.IDENTITY.AUDIENCE) {
                    addToParent();
                    closeLive(apiCloseLive);
                }
            }

            @Override
            public void onUserJoinRoom(AppJoinRoomVO apiJoinRoom) {
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
            public void onUserLeaveRoom(ApiLeaveRoom apiLeaveRoom) {
            }

            @Override
            public void onManageKickRoom(ApiKickLive apiKickLive) {

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

        IMUtil.addReceiver(groupName, new IMRcvShopMsgSend() {
            @Override
            public void onUsersShopBanner(String shopLiveBanner) {

            }

            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onBuyGoodsRoom(UserBuyDTO userBuyDTO) {

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

            }
        });
    }

    public void closeLive(final ApiCloseLive apiCloseLive) {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFromParent();
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, null);


            }
        });
        if (viewModel.apijoinroom.get().liveThumb != null) {
            ImageLoader.display(viewModel.apijoinroom.get().liveThumb, binding.CloseRe);
        }
        if (apiCloseLive.anchorId == HttpClient.getUid()) {
            binding.closeAnchorFollow.setVisibility(View.GONE);
            binding.linAnchor.setVisibility(View.VISIBLE);
        } else {
            binding.closeAnchorFollow.setVisibility(View.VISIBLE);
            binding.linAnchor.setVisibility(View.GONE);
            if (LiveInfoComponent.isFollow == 1) {
                binding.closeAnchorFollow.setText("已关注");
                binding.closeAnchorFollow.setBackgroundResource(R.drawable.bg_live_end_follwed);
            } else {
                binding.closeAnchorFollow.setText("+  关注");
                binding.closeAnchorFollow.setBackgroundResource(R.drawable.bg_live_end_btn);
                binding.closeAnchorFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpApiAppUser.set_atten(1, LiveConstants.ANCHORID, new HttpApiCallBack<HttpNone>() {
                            @Override
                            public void onHttpRet(int code, String msg, HttpNone retModel) {
                                if (code == 1) {
                                    binding.closeAnchorFollow.setText("已关注");
                                    binding.closeAnchorFollow.setBackgroundResource(R.drawable.bg_live_end_follwed);
                                }
                            }
                        });
                    }
                });
            }
        }

        binding.getViewModel().bean.set(apiCloseLive);
        binding.duration.setText(StringUtil.getDurationText(apiCloseLive.duration * 1000));
//        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_CloseLive, apiCloseLive);
    }
}
