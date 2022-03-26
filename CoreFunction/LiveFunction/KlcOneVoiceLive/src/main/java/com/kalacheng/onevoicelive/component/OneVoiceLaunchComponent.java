package com.kalacheng.onevoicelive.component;

import android.Manifest;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.kalacheng.base.base.BaseMVVMViewHolder;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.busooolive.httpApi.HttpApiOOOCall;
import com.kalacheng.busooolive.model.OOOInviteRet;
import com.kalacheng.busooolive.model.OOOReturn;
import com.kalacheng.commonview.jguangIm.ImMessageUtil;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.onevoicelive.R;
import com.kalacheng.onevoicelive.databinding.OneVoiceLaunchBinding;
import com.kalacheng.onevoicelive.viewmodel.OneVoiceLaunchViewModel;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.PermissionsUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.klc.bean.live.LiveBundleKeyName;
import com.wengying666.imsocket.SocketClient;

public class OneVoiceLaunchComponent extends BaseMVVMViewHolder<OneVoiceLaunchBinding, OneVoiceLaunchViewModel> implements LiveBundle.onLiveSocket, View.OnClickListener {
    //来视频铃声
    private MediaPlayer mPlayer;

    //权限
    private PermissionsUtil mProcessResultUtil;

    public OneVoiceLaunchComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.one_voice_launch;
    }

    @Override
    protected void init() {
        LiveBundle.getInstance().addLiveSocketListener(this);
        mProcessResultUtil = new PermissionsUtil((AppCompatActivity) mContext);
        addToParent();
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveJoinRoom, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                viewModel.bean.set((ApiUserInfo) o);
                if (!LiveConstants.mIsOOOSend){
                    playMusic();
                    initView();
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveType, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (LiveConstants.mIsOOOSend){
                    playMusic();
                    OOOInviteRet oooInviteRet = (OOOInviteRet) o;
                    inviteAnchorChat(oooInviteRet);
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        binding.tvConversationMoneyUnit.setText(SpUtil.getInstance().getCoinUnit() + "/分钟");
        binding.refuse.setOnClickListener(this);
        binding.accept.setOnClickListener(this);

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
        //接通视频
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveLinkOK, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                final OOOReturn anchorinfo = (OOOReturn) o;
                final AppJoinRoomVO joinRoom = new AppJoinRoomVO();
                joinRoom.anchorId = anchorinfo.hostId;
                joinRoom.anchorName = anchorinfo.userName;
                joinRoom.anchorAvatar = anchorinfo.avatar;
                joinRoom.role = anchorinfo.role;
                joinRoom.roomId = anchorinfo.roomId;
                joinRoom.isFollow = anchorinfo.isAtten;
                joinRoom.liveType = anchorinfo.type;
                joinRoom.noticeMsg = anchorinfo.noticeMsg;
                joinRoom.showid = anchorinfo.showid;
                joinRoom.voiceThumb = anchorinfo.avatarThumb;
                joinRoom.userAvatar = anchorinfo.feeAvatar;
                joinRoom.notice = "";

                LiveConstants.ROOMID = anchorinfo.roomId;
                if (anchorinfo.role == 3) {
                    LiveConstants.IDENTITY = LiveConstants.IDENTITY.ANCHOR;
                } else if (anchorinfo.role == 2) {
                    LiveConstants.IDENTITY = LiveConstants.IDENTITY.AUDIENCE;
                } else {
                    LiveConstants.IDENTITY = LiveConstants.IDENTITY.BROADCAST;
                }
                LiveConstants.ANCHORID = anchorinfo.hostId;
                LiveBundle.getInstance().sendSimpleMsg(LiveBundleKeyName.JoinVoiceRoom, joinRoom);
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OneFeeCall, anchorinfo.freeCallMsg);

                clean();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //对方未接通
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveHangUp, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                //y对方加入关闭铃声
                if (mPlayer != null) {
                    mPlayer.stop();
                    mPlayer = null;
                }
                if (o == null) return;
                if (((Long) o) == 12) {
                    ImMessageUtil.getInstance().sendCallMessage(viewModel.bean.get().userId, 3, 0, false);

                } else {
                    ImMessageUtil.getInstance().sendCallMessage(viewModel.bean.get().userId, 2, 0, false);
                }
            }


            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
    }

    public void initView() {
        if (LiveConstants.FEEUID == 0 || LiveConstants.FEEUID == HttpClient.getUid()) {
            binding.Conversation.setVisibility(View.VISIBLE);
        } else {
            binding.Conversation.setVisibility(View.GONE);
        }
        ImageLoader.display(viewModel.bean.get().thumb, binding.bgImage);
        binding.ConversationMoney.setText(String.valueOf(LiveConstants.OOOFEE));
        //1发送
        if (LiveConstants.mIsOOOSend == true) {
            binding.accept.setVisibility(View.GONE);
            binding.OneVoiceLaunchLoading.setText("你正在与" + viewModel.bean.get().username + "语音...");
        } else {
            binding.accept.setVisibility(View.VISIBLE);
            binding.OneVoiceLaunchLoading.setText(viewModel.bean.get().username + "想和你语音...");

        }

    }

    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.refuse) {//拒绝和挂断
            if (LiveConstants.mIsOOOSend == true) {//撤销通话
                HttpApiOOOCall.cancelInvite(LiveConstants.mOOOSessionID, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        // ggm 这个地方应该关闭Activity或者根据UI来
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_CloseLive, null);
                    }
                });
            } else {
                HttpApiOOOCall.replyInvite(0, LiveConstants.mOOOSessionID, new HttpApiCallBack<OOOReturn>() {
                    @Override
                    public void onHttpRet(int code, String msg, OOOReturn retModel) {
                        // ggm 你拒绝了别人应该 转到别的地方去。
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_CloseLive, null);
                    }
                });
            }
            if (binding.accept.getVisibility() == View.GONE) {
                ImMessageUtil.getInstance().sendCallMessage(viewModel.bean.get().userId, 1, 0, false);
            }
        } else if (view.getId() == R.id.accept) {//接听
            mProcessResultUtil.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO
            }, new Runnable() {
                @Override
                public void run() {
                    HttpApiOOOCall.replyInvite(1, LiveConstants.mOOOSessionID, new HttpApiCallBack<OOOReturn>() {
                        @Override
                        public void onHttpRet(int code, String msg, OOOReturn retModel) {
                            if (code == 1) {
                                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveLinkOK, retModel);
                                ToastUtil.show(msg);
                            } else if (code == 12) {
                                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOAudienceNoVip, null);
                            } else {
                                //ggm 失败了，应该做些什么
                                ToastUtil.show(msg);
                                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_CloseLive, null);
                            }
                        }
                    });
                }
            });
        }
    }

    public void clean() {
        //y对方加入关闭铃声
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer = null;
        }
        removeFromParent();
    }

    @Override
    public void init(String activityName, SocketClient socketClient) {
    }

    //邀请
    public void inviteAnchorChat(OOOInviteRet oooInviteRet) {
        //2对方正忙；3对方正在通话！；9用户余额不足无法邀请通话；10不能向自己发起邀请； 11数据错误
        //ggm 在这个地方应该有更多的提示。
        //ggm 应该关掉Activity或者干点什么。
        //1成功；
        LiveConstants.FEEUID = oooInviteRet.feeUid;
        LiveConstants.mOOOSessionID = oooInviteRet.sessionID;//记录下会话ID，备用
        LiveConstants.OOOFEE = oooInviteRet.oooFee;
        viewModel.bean.get().thumb = oooInviteRet.thumb;
        initView();
    }

    /**
     * 来视频铃声
     */
    private void playMusic() {
        try {
            if (mPlayer == null) {
                mPlayer = MediaPlayer.create(mContext, R.raw.call_come);
                mPlayer.setLooping(true);
                mPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
