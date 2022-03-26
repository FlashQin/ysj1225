package com.kalacheng.onevoicelive.component;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import com.kalacheng.base.base.BaseMVVMViewHolder;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.busooolive.httpApi.HttpApiOOOCall;
import com.kalacheng.busooolive.model.OOOReturn;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.onevoicelive.R;
import com.kalacheng.onevoicelive.databinding.One2oneVoiceseekchatliveLaunchBinding;
import com.kalacheng.onevoicelive.viewmodel.One2OneVoiceSeekChatViewModel;
import com.kalacheng.util.utils.PermissionsUtil;
import com.klc.bean.live.LiveBundleKeyName;

import java.io.File;

//import com.klc.IMApi.IMApiOOOLive;

public class One2OneVoiceSeekChatLaunchComponent extends BaseMVVMViewHolder<One2oneVoiceseekchatliveLaunchBinding, One2OneVoiceSeekChatViewModel> implements View.OnClickListener {
    //来视频铃声
    private MediaPlayer mPlayer;


    //权限
    PermissionsUtil mProcessResultUtil;

    ApiUserInfo userInfo;

    //判断收钱一方
    private long feelUid;

    public One2OneVoiceSeekChatLaunchComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.one2one_voiceseekchatlive_launch;
    }

    @Override
    protected void init() {
        mProcessResultUtil = new PermissionsUtil((AppCompatActivity) mContext);
        addToParent();
        userInfo = SpUtil.getInstance().<ApiUserInfo>getModel("UserInfo", ApiUserInfo.class);

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveJoinRoom, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                viewModel.bean.set((ApiUserInfo) o);
                initView();
                playMusic();

                setAnimator();


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
        //接通视频
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveLinkOK, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                OOOReturn anchorinfo = (OOOReturn) o;
                AppJoinRoomVO joinRoom = new AppJoinRoomVO();
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

                clean();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });


    }

    public void initView() {
        binding.voiceseekeChatOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.voiceseekeChat_out) {
            HttpApiOOOCall.exitPushChat(new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_CloseLive, null);
                }
            });
        }

    }

    public void clean() {
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
        //y对方加入关闭铃声
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer = null;
        }
        removeFromParent();
    }


    /**
     * 来视频铃声
     */
    private void playMusic() {
        try {
            if (mPlayer == null) {
                String url = (String) SpUtil.getInstance().getSharedPreference("Muisc", null);
                File file = new File(url);
                if (mPlayer == null) {
                    mPlayer = new MediaPlayer();
                    mPlayer.reset();
                    mPlayer.setDataSource(file.getPath());
                    mPlayer.prepareAsync();
                    mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mPlayer.start();
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //界面两个小球转动
    ObjectAnimator animator;

    public void setAnimator() {
        animator = ObjectAnimator.ofFloat(binding.seekChatBall, "rotation", 0f, 720f);
        animator.setInterpolator(new LinearInterpolator());
        //设置动画重复次数
        animator.setRepeatCount(200);
        //旋转时长
        animator.setDuration(4000);
        //开始旋转
        animator.start();
    }

}
