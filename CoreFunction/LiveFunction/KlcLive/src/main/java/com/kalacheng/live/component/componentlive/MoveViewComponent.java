package com.kalacheng.live.component.componentlive;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.kalacheng.base.base.BaseMVVMViewHolder;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.buscommon.model.GuardUserDto;
import com.kalacheng.buslivebas.socketmsg.IMRcvLiveSend;
import com.kalacheng.buspersonalcenter.socketmsg.IMRcvUserMsgSender;
import com.kalacheng.busshop.httpApi.HttpApiShopBusiness;
import com.kalacheng.busshop.model.ShopLiveInfoDTO;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
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
import com.kalacheng.live.component.view.SlideImageView;
import com.kalacheng.live.component.viewmodel.MoveViewViewModel;
import com.kalacheng.live.databinding.ViewMoveViewBinding;
import com.kalacheng.shop.socketmsg.IMRcvShopMsgSend;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.wengying666.imsocket.IMUtil;
import com.wengying666.imsocket.SocketClient;

import java.util.List;

/**
 * @author: Administrator
 * @date: 2020/8/8
 * @info: 拖拽 移动
 */
public class MoveViewComponent extends BaseMVVMViewHolder<ViewMoveViewBinding, MoveViewViewModel> implements LiveBundle.onLiveSocket {

    public MoveViewComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_move_view;
    }

    @Override
    protected void init() {
        addToParent();
        setListener();
        setLiveBundleListener();
        if (ConfigUtil.getBoolValue(R.bool.containShopping)) {
            binding.ivGoods.isAnchor(true);
        }
    }

    private void setListener() {
        binding.ivGoods.setListener(new SlideImageView.slideListener() {
            @Override
            public void positionListener(int l, int t, int r, int b) {
//                Log.e("cjh", "" + "  l" + l+ "  t" + t+ "  r" + r+ "  b" + b);
//
//                int w = DpUtil.getScreenWidth();
//                int h = DpUtil.getScreenHeight() - DpUtil.getStatusHeight();
//                // 百分比
//                double left;
//                double top;
//                double right;
//                double bottom;
//                left = (double)l/ w;
//                right = (double)r/ w;
//                top = (double)t/h;
//                bottom = (double)b/h;
//
//                Log.e("cjh", "" + "  left" + left+ "  top" + top+ "  right" + right+ "  bottom" + bottom);

            }
        });

    }

    private void setLiveBundleListener() {
        LiveBundle.getInstance().addLiveSocketListener(this);
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.RoomInfoList, new MsgListener.SimpleMsgListener<AppJoinRoomVO>() {
            @Override
            public void onMsg(AppJoinRoomVO apiJoinRoom) {
                if (null != apiJoinRoom) {
                    // 如果是直播购
                    if (ConfigUtil.getBoolValue(R.bool.containShopping)) {
                        getLiveShopInformation();
                    }
                }
            }

            @Override
            public void onTagMsg(String tag, AppJoinRoomVO apiJoinRoom) {

            }
        });
    }

    private void move(double l, double t, double r, double b) {

        int left;
        int top;
        int right;
        int bottom;

        int w = DpUtil.getScreenWidth();
        int h = DpUtil.getScreenHeight() - DpUtil.getStatusHeight();

        left = (int) (l * w);
        top = (int) (t * h);
        right = (int) (r * w);
        bottom = (int) (b * h);
        Log.e("cjh", "Width：" + DpUtil.getScreenWidth() + "   Height：" + (DpUtil.getScreenHeight() - DpUtil.getStatusHeight()));
        Log.e("cjh", "最后坐标：" + "  left" + left + "  top" + top + "  right" + right + "  bottom" + bottom);
        binding.ivGoods.setPosition(left, top, right, bottom);
    }

    @Override
    public void init(String groupName, SocketClient socketClient) {
        IMUtil.addReceiver(groupName, new IMRcvLiveSend() {

            @Override
            public void onJoinRoomMsgRoom(ApiSimpleMsgRoom apiSimpleMsgRoom) {

            }

            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onManageLeaveRoom(ApiCloseLive apiCloseLive) {

            }

            @Override
            public void onCloseLive(ApiCloseLive apiCloseLive) {
//                if (LiveConstants.IDENTITY == LiveConstants.IDENTITY.AUDIENCE) {
//                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, null);
//                    ARouter.getInstance().build(ARouterPath.LiveEndActivity).withParcelable("ApiJoinRoom", viewModel.apiJoinRoom.get()).withParcelable("ApiCloseLive", apiCloseLive).navigation();
//                }
            }

            @Override
            public void onManageKickRoom(ApiKickLive apiKickLive) {

            }

            @Override
            public void onBuyGuardListRoom(List<GuardUserDto> list) {

            }

            @Override
            public void onAnchorLeaveRoom(ApiLeaveRoomAnchor ApiLeaveRoomAnchor) {

            }

            @Override
            public void onDownVoiceAssistan(ApiUsersVoiceAssistan apiVoiceAssistanEntity) {

            }

            @Override
            public void onUsersVIPSeats(ApiUserSeats apiUserSeats) {

            }

            @Override
            public void onUserJoinRoom(AppJoinRoomVO apiJoinRoom) {

            }

            @Override
            public void onUserLeaveRoom(ApiLeaveRoom apiLeaveRoom) {

            }

            @Override
            public void onAnchorJoinRoom(ApiJoinRoomAnchor apiJoinRoomAnchor) {

            }

            @Override
            public void onUserBackground(String voicethumb) {

            }

        });

        IMUtil.addReceiver(groupName, new IMRcvShopMsgSend() {
            @Override
            public void onUsersShopBanner(String shopLiveBanner) {
                if (shopLiveBanner != null && !shopLiveBanner.equals("")) {
                    ImageLoader.display(shopLiveBanner, binding.ivGoods, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                    binding.ivGoods.setVisibility(View.VISIBLE);
                } else {
                    binding.ivGoods.setVisibility(View.GONE);
                }
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

    //获取直播购的信息
    public void getLiveShopInformation() {
        HttpApiShopBusiness.getLiveInfo(LiveConstants.ANCHORID, new HttpApiCallBack<ShopLiveInfoDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ShopLiveInfoDTO retModel) {
                if (code == 1) {
                    if (retModel.appUsersLive.shopLiveBanner != null && !retModel.appUsersLive.shopLiveBanner.equals("")) {
                        ImageLoader.display(retModel.appUsersLive.shopLiveBanner, binding.ivGoods, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                        binding.ivGoods.setVisibility(View.VISIBLE);
                    } else {
                        binding.ivGoods.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

}