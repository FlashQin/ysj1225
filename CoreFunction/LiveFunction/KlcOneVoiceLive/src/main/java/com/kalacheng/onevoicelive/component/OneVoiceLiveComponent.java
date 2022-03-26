package com.kalacheng.onevoicelive.component;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.onevoicelive.R;
import com.kalacheng.base.base.BaseViewHolder;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.base.http.HttpClient;
import com.klc.bean.live.JniObjs;
import com.klc.bean.live.LiveBundleKeyName;
import com.klc.bean.live.LocalConstans;
import com.klc.bean.live.VoiceLiveOwnStateBean;
import com.xuantongyun.livecloud.base.OnCloudEventListener;
import com.xuantongyun.livecloud.protocol.OOOVoiceUtils;
import com.xuantongyun.livecloud.protocol.PulicUtils;


public class OneVoiceLiveComponent extends BaseViewHolder {

    public OneVoiceLiveComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_live_voice;
    }

    @Override
    protected void init() {
        addToParent();

        //退出房间
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_BackHome, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                clean();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ExitRoom, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {

                clean();
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


        PulicUtils.getInstance().setTTTKey();
        OOOVoiceUtils.getInstance().init(mContext, new OnCloudEventListener() {
                    @Override
                    public void onError(int errorType) {

                    }

                    @Override
                    public void onJoinChannelSuccess(String channel, long uid) {

                    }

                    @Override
                    public void onUserJoined(long uid, int identity) {

                    }

                    @Override
                    public void onUserOffline(long uid, int reason) {

                    }

                    @Override
                    public void onAudioVolumeIndication(long nUserID, int audioLevel) {
                        JniObjs mJniObjs = new JniObjs();
                        mJniObjs.mJniType = LocalConstans.CALL_BACK_ON_AUDIO_VOLUME_INDICATION;
                        mJniObjs.mUid = nUserID;
                        mJniObjs.mAudioLevel = audioLevel;

                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OneVoiceVolume, mJniObjs);
                    }

            @Override
            public void onCameraConnectError(int i) {

            }
        });
        initListener();
    }

    float downX;
    float downY;
    float upX;
    float upY;

    //1左 2右
    private int mSlide = 2;

    private void initListener() {
        //滑动清屏
        if (LiveConstants.FEEUID == HttpClient.getUid()) {
            RelativeLayout rl_video = (RelativeLayout) findViewById(R.id.rl_video);
            rl_video.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    switch (event.getAction()) {

                        case MotionEvent.ACTION_DOWN:
                            downX = event.getX();
                            downY = event.getY();
                            break;

                        case MotionEvent.ACTION_MOVE:


                            break;
                        case MotionEvent.ACTION_UP:
                            upX = event.getX();
                            upY = event.getY();
                            float x = Math.abs(upX - downX);
                            float y = Math.abs(upY - downY);

                            if (downX - upX > 50) {//左
                                if (mSlide == 1) {
                                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LIFT, null);
                                    mSlide = 2;
                                }
                            } else if (upX - downX > 50) {//右
                                if (mSlide == 2) {//优先向左滑动
                                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_RIGHT, null);
                                    mSlide = 1;
                                }
                            }


                            break;


                    }
                    return true;
                }
            });
        }

        //创建房间
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveTTTEstablish, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {


            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveTTTJoin, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {


            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        LiveBundle.getInstance().addSimpleMsgListener(LiveBundleKeyName.JoinVoiceRoom, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {

                LiveConstants.ANCHORID = ((AppJoinRoomVO) o).anchorId;
                LiveConstants.ROOMID = ((AppJoinRoomVO) o).roomId;

                if (LiveConstants.ANCHORID == HttpClient.getUid()) {
                    VoiceLiveOwnStateBean.OwnIdentity = VoiceLiveOwnStateBean.Anchor;
                } else {
                    VoiceLiveOwnStateBean.OwnIdentity = VoiceLiveOwnStateBean.Administrators;

                }
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.RoomInfoList, (AppJoinRoomVO) o);
                OOOVoiceUtils.getInstance().startLiveSuccess(0, 2, ((AppJoinRoomVO) o).roomId);
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });


    }

    public void clean() {
        OOOVoiceUtils.getInstance().clean();
        removeFromParent();
    }
}
