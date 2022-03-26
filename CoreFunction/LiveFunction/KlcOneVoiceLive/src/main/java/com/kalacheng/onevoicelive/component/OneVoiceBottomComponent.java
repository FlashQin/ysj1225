package com.kalacheng.onevoicelive.component;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.base.BaseMVVMViewHolder;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.busooolive.httpApi.HttpApiOTMCall;
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
import com.kalacheng.onevoicelive.R;
import com.kalacheng.onevoicelive.databinding.OneVoiceBottomBinding;
import com.kalacheng.onevoicelive.viewmodel.OneVoiceBottomViewModel;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.StringUtil;
import com.klc.bean.SendGiftPeopleBean;
import com.klc.bean.live.VoiceLiveOwnStateBean;
import com.xuantongyun.livecloud.protocol.PulicUtils;

import java.util.ArrayList;
import java.util.List;

//import com.wushuangtech.wstechapi.TTTRtcEngine;

public class OneVoiceBottomComponent extends BaseMVVMViewHolder<OneVoiceBottomBinding, OneVoiceBottomViewModel> implements View.OnClickListener {

    public OneVoiceBottomComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.one_voice_bottom;
    }

    @Override
    protected void init() {
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOCloseLive, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                removeFromParent();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.RoomInfoList, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                addToParent();
                viewModel.joinRoom.set((AppJoinRoomVO) o);
                intiView();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        if (ConfigUtil.getBoolValue(R.bool.openAnchorPhone)) {
            binding.anchorPhone.setOnClickListener(this);
            http();
        }

    }

    public void intiView() {
        binding.btnRefuse.setOnClickListener(this);
        binding.btnChat.setOnClickListener(this);
        binding.voiceLiveSpeak.setOnClickListener(this);
        binding.btnGift.setOnClickListener(this);
        binding.btnWish.setOnClickListener(this);
        binding.btnMuise.setOnClickListener(this);
        binding.ExternalTone.setOnClickListener(this);
        binding.btnRecharge.setOnClickListener(this);
        binding.btnFans.setOnClickListener(this);
        binding.btnTreasure.setOnClickListener(this);

        if (LiveConstants.FEEUID == HttpClient.getUid() || LiveConstants.FEEUID == 0) {
            binding.btnWish.setVisibility(View.GONE);
            binding.btnMuise.setVisibility(View.GONE);
            binding.ExternalTone.setVisibility(View.GONE);
            binding.btnRecharge.setVisibility(View.VISIBLE);
            binding.btnGift.setVisibility(View.VISIBLE);
        } else {
            binding.btnWish.setVisibility(View.VISIBLE);
            binding.btnMuise.setVisibility(View.VISIBLE);
            binding.ExternalTone.setVisibility(View.VISIBLE);
            binding.btnRecharge.setVisibility(View.GONE);
            binding.btnGift.setVisibility(View.GONE);
        }
        if (ConfigUtil.getBoolValue(R.bool.openTreasure)) {
            binding.btnTreasure.setVisibility(View.VISIBLE);
        } else {
            binding.btnTreasure.setVisibility(View.GONE);

        }
    }

    //status 状态1打开0关闭
    int status;

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_refuse) {//退出房间
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOEndRequestGetTime, null);
        } else if (i == R.id.voice_live_speak) {//静自己音

            if (!VoiceLiveOwnStateBean.IsMute) {
                status = 0;

            } else {
                status = 1;

            }

            HttpApiOTMCall.otmVolume(LiveConstants.mOOOSessionID, status, new HttpApiCallBack<OOOVolumeRet>() {
                @Override
                public void onHttpRet(int code, String msg, OOOVolumeRet retModel) {
                    if (code == 1) {
                        if (!VoiceLiveOwnStateBean.IsMute) {
                            VoiceLiveOwnStateBean.IsMute = true;
                            binding.voiceLiveSpeak.setBackgroundResource(R.mipmap.anchor_voice_open);
                        } else {
                            VoiceLiveOwnStateBean.IsMute = false;
                            binding.voiceLiveSpeak.setBackgroundResource(R.mipmap.anchor_voice_close);
                        }
                        PulicUtils.getInstance().muteLocalAudioStream(VoiceLiveOwnStateBean.IsMute);
                    }
                }
            });


        } else if (i == R.id.ExternalTone) {//静别人的音
            if (LiveConstants.IsRemoteAudio) {
                binding.ExternalTone.setBackgroundResource(R.mipmap.voice_live_external_open);
                PulicUtils.getInstance().muteAllRemoteAudioStreams(false);
                LiveConstants.IsRemoteAudio = false;
            } else {
                binding.ExternalTone.setBackgroundResource(R.mipmap.voice_live_external_close);
                PulicUtils.getInstance().muteAllRemoteAudioStreams(true);
                LiveConstants.IsRemoteAudio = true;
            }
        } else if (i == R.id.btn_gift) {//送礼物
            SendGiftPeopleBean bean = new SendGiftPeopleBean();
            bean.name = viewModel.joinRoom.get().anchorName;
            bean.headimage = viewModel.joinRoom.get().anchorAvatar;
            bean.uid = viewModel.joinRoom.get().anchorId;
            bean.liveType = viewModel.joinRoom.get().liveType;
            bean.showid = viewModel.joinRoom.get().showid;
            bean.shortVideoId = -1;
            bean.anchorID = viewModel.joinRoom.get().anchorId;
            bean.roomID = viewModel.joinRoom.get().roomId;
            List<SendGiftPeopleBean> beanList = new ArrayList<>();
            beanList.add(bean);
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveSendGift, beanList);
        } else if (i == R.id.btn_wish) {//心愿单
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_WishList, null);
        } else if (i == R.id.btn_chat) {//聊天
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OpenChat, null);
        } else if (i == R.id.btn_muise) {
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_Music, null);
        } else if (i == R.id.btn_recharge) {//充值
//            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveRecharge, null);
            ARouter.getInstance().build(ARouterPath.MyCoinActivity).navigation();
        } else if (i == R.id.btn_fans) {//粉丝团
            if (LiveConstants.FEEUID == HttpClient.getUid()) {
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_AddFansGroup, null);

            } else {
                ARouter.getInstance().build(ARouterPath.FansGroupActivity).navigation();

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
                if (code == 1) {
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

                    if (null != userInfo) {
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

    // 获取联系方式 选择 获取微信号 获取手机号
    public interface ContactInformationCallBack {
        void onWeClick();

        void onPhoneClick();
    }

}
