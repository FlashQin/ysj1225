package com.kalacheng.one2onelive.component;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.base.BaseMVVMViewHolder;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.busooolive.httpApi.HttpApiOOOCall;
import com.kalacheng.busooolive.httpApi.HttpApiOTMCall;
import com.kalacheng.busooolive.model.OOOHangupReturn;
import com.kalacheng.busooolive.model.OOOReturn;
import com.kalacheng.busooolive.model.OOOVolumeRet;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libbas.model.SingleString;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.one2onelive.R;
import com.kalacheng.one2onelive.databinding.One2oneSvipBottomBinding;
import com.kalacheng.one2onelive.viewmodel.One2OneSvipBottomViewModel;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.StringUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.klc.bean.OOOLiveHangUpBean;
import com.klc.bean.SendGiftPeopleBean;
import com.xuantongyun.livecloud.protocol.PulicUtils;

import java.util.ArrayList;
import java.util.List;

public class One2OneSvipBottomComponent extends BaseMVVMViewHolder<One2oneSvipBottomBinding, One2OneSvipBottomViewModel> implements View.OnClickListener {

    //判断是否开通svip
    private boolean IsSvip = true;

    ObjectAnimator giftAnimatorEnd2;
    ObjectAnimator giftAnimator2;

    //屏蔽所以用户的声音
    private boolean IsStopAllVoice = false;

    private OOOReturn mOOOReturn;

    private int voiceState = 0;

    public One2OneSvipBottomComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.one2one_svip_bottom;
    }

    @Override
    protected void init() {

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OpenLiveMsg, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                addToParent();
                viewModel.joinRoom.set((AppJoinRoomVO) o);
                getInit();
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

        //向左滑，消失
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LIFT, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {

                giftAnimator2 = ObjectAnimator.ofFloat(binding.one2oneBottom, "translationX", 1500, 0);
                giftAnimator2.setDuration(500);
                giftAnimator2.setInterpolator(new LinearInterpolator());
                giftAnimator2.start();


            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //向右滑，显示
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_RIGHT, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                giftAnimatorEnd2 = ObjectAnimator.ofFloat(binding.one2oneBottom, "translationX", 1500);
                giftAnimatorEnd2.setDuration(500);
                giftAnimatorEnd2.setInterpolator(new LinearInterpolator());
                giftAnimatorEnd2.start();


            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //直播云异常退出
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOTTTErrorHangUp, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                HttpApiOOOCall.hangupCall(0, LiveConstants.mOOOSessionID, new HttpApiCallBack<OOOHangupReturn>() {
                    @Override
                    public void onHttpRet(int code, String msg, OOOHangupReturn retModel) {
                        //ggm 在这里应该去显示费用的界面，不应该退出；当然retModel可能为空
                        OOOLiveHangUpBean bean = new OOOLiveHangUpBean();
                        bean.callTime = retModel.callTime;
                        bean.totalCoin = (int) retModel.totalCoin;
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOCallEnd, bean);
//                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, new ApiCloseLine());
                        LiveConstants.mOOOSessionID = 0;
                        ToastUtil.show(msg);

                    }
                });

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //副播加入成功
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveSVipJoinSuccess, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                mOOOReturn = (OOOReturn) o;

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //禁止发言
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveSVipEstoppelSpeake, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                OOOVolumeRet oooVolumeRet = (OOOVolumeRet) o;
                //主持人音量状态1开启0关播
                if (oooVolumeRet.hostUid == HttpClient.getUid()) {
                    if (oooVolumeRet.hostStatus == 0) {
                        voiceState = 1;
                        binding.btnExternalTone.setBackgroundResource(R.mipmap.svip_closevoice);
                        PulicUtils.getInstance().muteLocalAudioStream(true);
                    } else {
                        voiceState = 0;
                        binding.btnExternalTone.setBackgroundResource(R.mipmap.svip_openvoice);
                        PulicUtils.getInstance().muteLocalAudioStream(false);
                    }
                } else {
                    if (oooVolumeRet.feeUid == HttpClient.getUid()) {
                        if (oooVolumeRet.feeStatus == 0) {
                            voiceState = 1;
                            binding.btnExternalTone.setBackgroundResource(R.mipmap.svip_closevoice);
                            PulicUtils.getInstance().muteLocalAudioStream(true);
                        } else {
                            binding.btnExternalTone.setBackgroundResource(R.mipmap.svip_openvoice);
                            voiceState = 0;
                            PulicUtils.getInstance().muteLocalAudioStream(false);
                        }
                    } else {
                        if (oooVolumeRet.assisRets != null) {
                            for (int i = 0; i < oooVolumeRet.assisRets.size(); i++) {
                                if (oooVolumeRet.assisRets.get(i).assisId == HttpClient.getUid()) {
                                    if (oooVolumeRet.assisRets.get(i).isOpenVolumn == 0) {
                                        voiceState = 1;
                                        binding.btnExternalTone.setBackgroundResource(R.mipmap.svip_closevoice);
                                        PulicUtils.getInstance().muteLocalAudioStream(true);
                                    } else {
                                        voiceState = 0;
                                        binding.btnExternalTone.setBackgroundResource(R.mipmap.svip_openvoice);
                                        PulicUtils.getInstance().muteLocalAudioStream(false);
                                    }
                                }
                            }
                        }
                    }
                }


            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //开通svip成功
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OpenSvipSusser, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                binding.btnIssvip.setVisibility(View.GONE);
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });


        binding.btnChat.setOnClickListener(this);
        binding.btnGift.setOnClickListener(this);
        binding.btnMore.setOnClickListener(this);
        binding.btnRecharge.setOnClickListener(this);
        binding.btnExternalTone.setOnClickListener(this);
        binding.btnBeautiful.setOnClickListener(this);
        binding.btnMuice.setOnClickListener(this);
        binding.btnCamera.setOnClickListener(this);
        binding.btnGift.setOnClickListener(this);
        binding.btnWish.setOnClickListener(this);
        binding.btnSvipRe.setOnClickListener(this);
        binding.btnVoiceStop.setOnClickListener(this);
        binding.btnTreasure.setOnClickListener(this);

        if (ConfigUtil.getBoolValue(R.bool.openAnchorPhone)) {
            binding.anchorPhone.setOnClickListener(this);
            http();
        }

        //getPersonCenter();
    }

    public void getInit() {
        //判断自己是否是主播
        if (LiveConstants.FEEUID == 0 || LiveConstants.FEEUID == HttpClient.getUid()) {
            binding.btnGift.setVisibility(View.VISIBLE);
            binding.btnCamera.setVisibility(View.VISIBLE);
            binding.btnWish.setVisibility(View.GONE);
            binding.btnMuice.setVisibility(View.GONE);
            binding.btnBeautiful.setVisibility(View.GONE);
            binding.btnRecharge.setVisibility(View.VISIBLE);
            binding.btnSvipRe.setVisibility(View.VISIBLE);
            binding.btnVoiceStop.setVisibility(View.GONE);
        } else {
            if (LiveConstants.ANCHORID == HttpClient.getUid()) {
                binding.btnWish.setVisibility(View.VISIBLE);
                binding.btnCamera.setVisibility(View.GONE);
                binding.btnMuice.setVisibility(View.VISIBLE);
                binding.btnVoiceStop.setVisibility(View.GONE);
            } else {
                binding.btnWish.setVisibility(View.GONE);
                binding.btnCamera.setVisibility(View.VISIBLE);
                binding.btnMuice.setVisibility(View.GONE);
                binding.btnVoiceStop.setVisibility(View.VISIBLE);
            }
            binding.btnGift.setVisibility(View.GONE);
            binding.btnBeautiful.setVisibility(View.VISIBLE);
            binding.btnRecharge.setVisibility(View.GONE);
            binding.btnSvipRe.setVisibility(View.GONE);

        }

        if (ConfigUtil.getBoolValue(R.bool.openTreasure)) {
            binding.btnTreasure.setVisibility(View.VISIBLE);
        } else {
            binding.btnTreasure.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.btn_chat) {//聊天
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OpenChat, null);
        } else if (view.getId() == R.id.btn_more) {//更多
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveMore, null);
        } else if (view.getId() == R.id.btn_recharge) {//充值
//            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveRecharge, null);
            ARouter.getInstance().build(ARouterPath.MyCoinActivity).navigation();
        } else if (view.getId() == R.id.btn_externalTone) {//静音

            HttpApiOTMCall.otmVolume(LiveConstants.mOOOSessionID, voiceState, new HttpApiCallBack<OOOVolumeRet>() {
                @Override
                public void onHttpRet(int code, String msg, OOOVolumeRet retModel) {
                    if (code == 1) {
                        ToastUtil.show(msg);
                    }
                }
            });


        } else if (view.getId() == R.id.btn_beautiful) {//美颜
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_BeautyShow, null);
        } else if (view.getId() == R.id.btn_muice) {//音乐
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_Music, null);
        } else if (view.getId() == R.id.btn_camera) {//翻转
            PulicUtils.getInstance().switchCamera();
        } else if (view.getId() == R.id.btn_gift) {//礼物
            List<SendGiftPeopleBean> beanList = new ArrayList<>();
            if (mOOOReturn == null) {
                SendGiftPeopleBean bean = new SendGiftPeopleBean();
                bean.name = viewModel.joinRoom.get().userName;
                bean.headimage = viewModel.joinRoom.get().anchorAvatar;
                bean.uid = viewModel.joinRoom.get().anchorId;
                bean.liveType = viewModel.joinRoom.get().liveType;
                bean.showid = viewModel.joinRoom.get().showid;
                bean.shortVideoId = -1;
                bean.anchorID = viewModel.joinRoom.get().anchorId;
                bean.roomID = viewModel.joinRoom.get().roomId;
                beanList.add(bean);
            } else {
                SendGiftPeopleBean bean = new SendGiftPeopleBean();
                bean.name = mOOOReturn.userName;
                bean.headimage = mOOOReturn.avatar;
                bean.uid = mOOOReturn.hostId;
                bean.liveType = viewModel.joinRoom.get().liveType;
                bean.showid = viewModel.joinRoom.get().showid;
                bean.shortVideoId = -1;
                bean.anchorID = viewModel.joinRoom.get().anchorId;
                bean.roomID = viewModel.joinRoom.get().roomId;
                beanList.add(bean);

                if (mOOOReturn.assisRets != null && mOOOReturn.assisRets.size() != 0) {
                    for (int i = 0; i < mOOOReturn.assisRets.size(); i++) {
                        SendGiftPeopleBean sendGiftPeopleBean = new SendGiftPeopleBean();
                        sendGiftPeopleBean.name = mOOOReturn.assisRets.get(i).assisName;
                        sendGiftPeopleBean.headimage = mOOOReturn.assisRets.get(i).assisAvatar;
                        sendGiftPeopleBean.uid = mOOOReturn.assisRets.get(i).assisId;
                        sendGiftPeopleBean.liveType = viewModel.joinRoom.get().liveType;
                        sendGiftPeopleBean.showid = viewModel.joinRoom.get().showid;
                        sendGiftPeopleBean.shortVideoId = -1;
                        sendGiftPeopleBean.anchorID = viewModel.joinRoom.get().anchorId;
                        sendGiftPeopleBean.roomID = viewModel.joinRoom.get().roomId;
                        beanList.add(sendGiftPeopleBean);
                    }
                }
            }


            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveSendGift, beanList);
        } else if (view.getId() == R.id.btn_wish) {//心愿单
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_WishList, null);

        } else if (view.getId() == R.id.btn_SvipRe) {//开通超级vip
            ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
            if (apiUserInfo.isSvip == 1) {
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveChoiceSVip, null);
            } else {
                getSVipInfo();
            }
        } else if (view.getId() == R.id.btn_voiceStop) {//禁远程声音
            if (IsStopAllVoice) {
                PulicUtils.getInstance().muteAllRemoteAudioStreams(false);
                IsStopAllVoice = false;
                binding.btnVoiceStop.setBackgroundResource(R.mipmap.voice_live_external_close);
            } else {
                PulicUtils.getInstance().muteAllRemoteAudioStreams(true);
                IsStopAllVoice = true;
                binding.btnVoiceStop.setBackgroundResource(R.mipmap.voice_live_external_open);

            }
        } else if (view.getId() == R.id.btn_treasure) {//百宝箱
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveTreasureChest, null);
        } else if (view.getId() == R.id.anchor_phone) {//点击主播联系方式
            if (LiveConstants.ANCHORID == HttpClient.getUid()) {
                // 如果是主播 跳转去设置
                ARouter.getInstance().build(ARouterPath.PaySettingActivity).navigation();
            } else {
                //游客展示dialog
                getPersonCenter();

            }
        }
    }

    private void http() {
        HttpApiAppUser.takeAnchorContact(new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1 && retModel.no_use.equals("0")) {
                    binding.anchorPhone.setVisibility(View.VISIBLE);
                } else {
                    binding.anchorPhone.setVisibility(View.GONE);
                }
            }
        });
    }

    private ApiUserInfo userInfo;

    private void getPersonCenter() {
        HttpApiAppUser.personCenter(-1, -1, LiveConstants.ANCHORID, new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && null != retModel) {
                    userInfo = retModel;
                    showAnchorPhoneDialog(mContext, new ContactInformationCallBack() {
                        @Override
                        public void onWeClick() {
                            showBrowseDialog(2, userInfo.wechatCoin);
                        }

                        @Override
                        public void onPhoneClick() {
                            showBrowseDialog(1, userInfo.mobileCoin);
                        }
                    });
                }
            }

        });
    }

    private void showBrowseDialog(final int type, final double coin) {
        String info;
        if (type == 1) {
            info = "获取TA的手机号需支付" + coin + SpUtil.getInstance().getCoinUnit() + ",是否继续支付？";
        } else {
            info = "获取TA的微信号需支付" + coin + SpUtil.getInstance().getCoinUnit() + ",是否继续支付？";
        }
        DialogUtil.showSimpleDialog(mContext, "提示", info, true, new DialogUtil.SimpleCallback() {
            @Override
            public void onConfirmClick() {
                HttpApiAppUser.payViewContact(type, userInfo.userId, new HttpApiCallBack<SingleString>() {
                    @Override
                    public void onHttpRet(int code, String msg, SingleString retModel) {
                        if (code == 1 && null != retModel) {
                            setDialog(retModel, type);
                            getPersonCenter();
                        } else {
                            ToastUtil.show(msg);
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
    }

    private Dialog dialog;
    private TextView tv_we, btn_we_code, tv_we_numb, tv_phone, btn_phone_code, tv_phone_numb;
    private ImageView iv_code, iv_money, iv_close;
    private LinearLayout ll_we, ll_phone, phone_layout, we_layout;

    // 查看联系主播联系方式
    private void showAnchorPhoneDialog(Context context, final ContactInformationCallBack callBack) {
        if (dialog == null) {
            dialog = new Dialog(context, com.kalacheng.util.R.style.dialog);
            dialog.setContentView(com.kalacheng.util.R.layout.dialog_anchor_phone_layout);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            iv_close = dialog.findViewById(com.kalacheng.util.R.id.iv_close);
            phone_layout = dialog.findViewById(com.kalacheng.util.R.id.ll_phone_button);
            we_layout = dialog.findViewById(com.kalacheng.util.R.id.ll_we_button);

            tv_we = dialog.findViewById(com.kalacheng.util.R.id.tv_we);
            btn_we_code = dialog.findViewById(com.kalacheng.util.R.id.btn_we_code);
            tv_we_numb = dialog.findViewById(com.kalacheng.util.R.id.tv_we_numb);
            tv_phone = dialog.findViewById(com.kalacheng.util.R.id.tv_phone);
            btn_phone_code = dialog.findViewById(com.kalacheng.util.R.id.btn_phone_code);
            tv_phone_numb = dialog.findViewById(com.kalacheng.util.R.id.tv_phone_numb);
            iv_code = dialog.findViewById(com.kalacheng.util.R.id.iv_code);
            iv_money = dialog.findViewById(com.kalacheng.util.R.id.iv_money);
            ll_we = dialog.findViewById(com.kalacheng.util.R.id.ll_we);
            ll_phone = dialog.findViewById(com.kalacheng.util.R.id.ll_phone);
            ll_we.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != callBack && userInfo.wechatCoin > 0) {
                        callBack.onWeClick();
                    } else if (tv_we_numb.getText().toString().equals("点击复制Ta的微信号")) {
                        StringUtil.CopyText(mContext, btn_we_code.getText().toString().trim());
                    }
                }
            });
            ll_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != callBack && userInfo.mobileCoin > 0) {
                        callBack.onPhoneClick();
                    } else if (tv_phone_numb.getText().toString().equals("点击复制Ta的手机号")) {
                        StringUtil.CopyText(mContext, btn_phone_code.getText().toString().trim());
                    }
                }
            });
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }


        if (userInfo instanceof ApiUserInfo) {
            if (ConfigUtil.getBoolValue(R.bool.containOne2One)) {
                if ("-1".equals(userInfo.wechatNo)) {
                    tv_we.setText("未绑定微信号");
                    btn_we_code.setVisibility(View.GONE);
                    iv_code.setVisibility(View.GONE);
                    tv_we_numb.setVisibility(View.GONE);
                    we_layout.setVisibility(View.GONE);
                } else if ("-2".equals(userInfo.wechatNo)) {
                    tv_we_numb.setText(DecimalFormatUtils.isIntegerDouble(userInfo.wechatCoin) + " 获取");
                } else {
                    iv_code.setVisibility(View.GONE);
                    tv_we_numb.setText("点击复制Ta的微信号");
                    btn_we_code.setText(userInfo.wechatNo);
                }
                if ("-1".equals(userInfo.mobile)) {
                    tv_phone.setText("未绑定手机号");
                    btn_phone_code.setVisibility(View.GONE);
                    iv_money.setVisibility(View.GONE);
                    tv_phone_numb.setVisibility(View.GONE);
                    phone_layout.setVisibility(View.GONE);
                } else if ("-2".equals(userInfo.mobile)) {
                    tv_phone_numb.setText(DecimalFormatUtils.isIntegerDouble(userInfo.mobileCoin) + " 获取");
                } else {
                    iv_money.setVisibility(View.GONE);
                    tv_phone_numb.setText("点击复制Ta的手机号");
                    btn_phone_code.setText(userInfo.mobile);
                }

            }
        }

        dialog.show();
    }

    private void setDialog(SingleString singleString, int type) {
        // 1是电话 2是微信
        if (type == 1) {
            iv_money.setVisibility(View.GONE);
            tv_phone_numb.setText("点击复制Ta的手机号");
            btn_phone_code.setText(singleString.value);
        } else if (type == 2) {
            iv_code.setVisibility(View.GONE);
            tv_we_numb.setText("点击复制Ta的微信号");
            btn_we_code.setText(singleString.value);
        }
    }

    public void clean() {
        if (giftAnimator2 != null) {
            giftAnimator2.cancel();
        }
        giftAnimator2 = null;
        if (giftAnimatorEnd2 != null) {
            giftAnimatorEnd2.cancel();
        }
        giftAnimatorEnd2 = null;
        removeFromParent();
    }

    // 获取联系方式 选择 获取微信号 获取手机号
    public interface ContactInformationCallBack {
        void onWeClick();

        void onPhoneClick();
    }

    /**
     * 获取SVIP信息
     */
    private void getSVipInfo() {
        HttpApiAppUser.getMyHeadInfo(new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && null != retModel) {
                    if (retModel.isSvip == 1) {
                        ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
                        apiUserInfo.isSvip = 1;
                        SpUtil.getInstance().putModel(SpUtil.USER_INFO, apiUserInfo);
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveChoiceSVip, null);
                    } else {
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveOpenSVip, null);
                    }
                } else {
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveOpenSVip, null);
                }
            }
        });
    }
}
