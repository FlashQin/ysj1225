package com.kalacheng.one2onelive.component;

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
import com.kalacheng.one2onelive.R;
import com.kalacheng.one2onelive.databinding.One2oneSeekchatliveLaunchBinding;
import com.kalacheng.one2onelive.viewmodel.One2OneSeekChatViewModel;
import com.kalacheng.util.utils.PermissionsUtil;
import com.wengying666.imsocket.SocketClient;

import java.io.File;

//import com.klc.IMApi.IMApiOOOLive;

public class One2OneSeekChatLaunchComponent extends BaseMVVMViewHolder<One2oneSeekchatliveLaunchBinding, One2OneSeekChatViewModel> implements LiveBundle.onLiveSocket, View.OnClickListener {
    //来视频铃声
    private MediaPlayer mPlayer;


    //权限
    PermissionsUtil mProcessResultUtil;

    ApiUserInfo userInfo;

    //判断收钱一方
    private long feelUid;

    public One2OneSeekChatLaunchComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.one2one_seekchatlive_launch;
    }

    @Override
    protected void init() {
        LiveBundle.getInstance().addLiveSocketListener(this);
        mProcessResultUtil = new PermissionsUtil((AppCompatActivity) mContext);
        addToParent();
        userInfo = SpUtil.getInstance().<ApiUserInfo>getModel("UserInfo", ApiUserInfo.class);

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveJoinRoom, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                viewModel.bean.set((ApiUserInfo) o);
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
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLinkTTT, joinRoom);
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OpenLiveMsg, joinRoom);
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.RoomInfoList, joinRoom);
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OneFeeCall, anchorinfo.freeCallMsg);

                clean();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });


    }

    public void initView() {

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.seekeChat_out) {
            HttpApiOOOCall.exitPushChat(new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_CloseLive, null);
                    clean();
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


    @Override
    public void init(String activityName, SocketClient socketClient) {

    }


    /**
     * 来视频铃声
     */
    private void playMusic() {
        binding.seekeChatOut.setOnClickListener(this);
        try {
            if (mPlayer == null) {
                String url = (String) SpUtil.getInstance().getSharedPreference("Muisc", null);
                if (url != null) {
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
                        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                mediaPlayer.start();
                                mediaPlayer.setLooping(true);
                            }
                        });
                    }
                } else {
                    if (mPlayer == null) {
                        mPlayer = new MediaPlayer();
                        //设置音频文件到MediaPlayer对象中
                        mPlayer.setDataSource((String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_MUISC, ""));
                        //让MediaPlayer对象准备，用这个方法防止加载时耗时导致anr
                        mPlayer.prepareAsync();
                    }
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
