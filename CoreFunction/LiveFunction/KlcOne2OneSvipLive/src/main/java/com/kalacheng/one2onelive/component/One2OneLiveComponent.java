package com.kalacheng.one2onelive.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.base.base.BaseViewHolder;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.commonview.beauty.DefaultBeautyViewHolder;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libuser.model.ApiBaseEntity;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.one2onelive.R;
import com.kalacheng.util.utils.DpUtil;
import com.klc.bean.SwitchBigAndSmallBean;
import com.klc.bean.live.JniObjs;
import com.klc.bean.live.LocalConstans;
import com.klc.bean.live.VoiceLiveOwnStateBean;
import com.wengying666.imsocket.IMApiCallBack;
import com.xuantongyun.livecloud.base.OnCloudEventListener;
import com.xuantongyun.livecloud.config.TTTConfigUtils;
import com.xuantongyun.livecloud.protocol.OOOSvipLiveUtils;
import com.xuantongyun.livecloud.protocol.PulicUtils;

import java.util.ArrayList;
import java.util.List;

import io.agora.capture.video.camera.CameraVideoManager;

public class One2OneLiveComponent extends BaseViewHolder {
    private SurfaceView surfaceView;
    private RelativeLayout rl_video;
    IMApiCallBack<ApiBaseEntity> respCallback;
    private DefaultBeautyViewHolder mLiveBeautyViewHolder;
    //视角切换
    private boolean IsSmall = false;

    //直播间的人数
    private List<Long> mList = new ArrayList<>();

    //大图的id
    private SwitchBigAndSmallBean mBigAndSmallBean;

    private AppJoinRoomVO apiJoinRoom;

    float downX;
    float downY;
    float upX;
    float upY;
    //1左 2右
    private int mSlide = 2;
    private CameraVideoManager mVideoManager;

    public One2OneLiveComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ooo_svip_live_video;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void init() {

        addToParent();
        mVideoManager = ((BaseApplication)BaseApplication.getInstance()).getCameraVideoManager();
        if (null == respCallback) {
            respCallback = new IMApiCallBack<ApiBaseEntity>() {
                @Override
                public void onImRet(int code, String errMsg, ApiBaseEntity retModel) {
                    if (retModel != null && retModel.msg != null)
                        ToastUtil.show(retModel.msg);
                }
            };
        }
        PulicUtils.getInstance().setTTTKey();
        OOOSvipLiveUtils.getInstance().init(mContext, new OnCloudEventListener() {
            @Override
            public void onError(int errorType) {
                ToastUtil.show("连接失败");
            }

            @Override
            public void onJoinChannelSuccess(String channel, long uid) {
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveTTTEstablish, uid);
            }

            @Override
            public void onUserJoined(long uid, int identity) {
                JniObjs mJniObjs = new JniObjs();
                mJniObjs.mJniType = LocalConstans.CALL_BACK_ON_USER_JOIN;
                mJniObjs.mUid = uid;
                mJniObjs.mIdentity = identity;
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveTTTEstablish, uid);
            }

            @Override
            public void onUserOffline(long uid, int reason) {
                if (uid != HttpClient.getUid()) {
                    if (reason == 203) {
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOTTTErrorHangUp, null);
                    }
                }
            }

            @Override
            public void onAudioVolumeIndication(long nUserID, int audioLevel) {

            }

            @Override
            public void onCameraConnectError(int i) {

            }
        });
   /*     OOOSvipLiveUtils.getInstance().setOnQualityListener(new OnNetworkQualityListener() {
            @Override
            public void onQualityBad() {

            }

            @Override
            public void onQualityDown() {//网络断开
//                ToastUtil.show("网络异常");
//                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_CloseLive,null);
            }

            @Override
            public void onQualityGood() {

            }
        });*/
        rl_video = (RelativeLayout) findViewById(R.id.rl_svip_video);
        if (LiveConstants.FEEUID == HttpClient.getUid()) {
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


        initListener();


    }


    private void initListener() {
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLinkTTT, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                apiJoinRoom = (AppJoinRoomVO) o;
                VoiceLiveOwnStateBean.IsMute = false;
                if (TTTConfigUtils.getInstance().getConfig().getBeautySwitch() == 1) {
                    PulicUtils.getInstance().setChannelProfile(1);
                    PulicUtils.getInstance().setClientRole(1);
                }
                OOOSvipLiveUtils.getInstance().startPush(((AppJoinRoomVO) o).roomId);
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOCloseLive, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                clear();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_CloseLive, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                clear();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //大小头切换  小图变大
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveSVipSwitchSmallTOBig, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(final Object o) {


                SwitchBigAndSmallBean bigAndSmallBean = (SwitchBigAndSmallBean) o;
                if (bigAndSmallBean != null && bigAndSmallBean.surfaceView != null) {
                    rl_video.removeAllViews();

                    if (!bigAndSmallBean.isOut) {
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveSVipSwitchBigTOSmall, mBigAndSmallBean);
                    }
                    mBigAndSmallBean = bigAndSmallBean;

                    if (mBigAndSmallBean.id == HttpClient.getUid()) {
                        surfaceView = bigAndSmallBean.getSurfaceView();
                        surfaceView.setZOrderOnTop(false);
                        surfaceView.setZOrderMediaOverlay(false);
                    } else {
                        surfaceView = bigAndSmallBean.getSurfaceView();
                        surfaceView.setZOrderOnTop(false);
                        surfaceView.setZOrderMediaOverlay(false);
                    }

                    RelativeLayout parent = (RelativeLayout) surfaceView.getParent();
                    if (parent != null) {
                        parent.removeAllViews();
                    }

                    rl_video.addView(surfaceView);
                }


            }


            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //创建房间
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveTTTEstablish, new MsgListener.SimpleMsgListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onMsg(Object o) {
                mList.add((Long) o);

                if (HttpClient.getUid() == LiveConstants.ANCHORID) {//判断创建是否是主播:主播段显示
                    if ((Long) o == LiveConstants.ANCHORID) {//判断加入房间的是否是主播
                        surfaceView = OOOSvipLiveUtils.getInstance().setupLocalVideo((int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_IMAGE_QUALITY, 0));
                        surfaceView.setZOrderMediaOverlay(false);
                        surfaceView.setZOrderOnTop(false);
                        rl_video.addView(surfaceView);

                        if (!TTTConfigUtils.getInstance().getConfig().isTTT() && TTTConfigUtils.getInstance().getConfig().getBeautySwitch() == 1) {
                            if (mVideoManager != null) {
                                mVideoManager.setLocalPreview(surfaceView);
                                mVideoManager.startCapture();
                            } else {
                                ((BaseApplication)BaseApplication.getInstance()).initVideoCaptureAsync();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mVideoManager = ((BaseApplication)BaseApplication.getInstance()).getCameraVideoManager();
                                        mVideoManager.setLocalPreview(surfaceView);
                                        mVideoManager.startCapture();
                                    }
                                }, 500);
                            }
                        }
                    }
                } else {//:观众端显示
                    if ((Long) o == LiveConstants.ANCHORID) {
                        surfaceView = OOOSvipLiveUtils.getInstance().setupRemoteVideo((Long) o);
                        surfaceView.setZOrderMediaOverlay(true);
                        surfaceView.setZOrderOnTop(false);
                        rl_video.addView(surfaceView);

//                        if (!TTTConfigUtils.getInstance().getConfig().isTTT() && TTTConfigUtils.getInstance().getConfig().getBeautySwitch() == 1) {
//                            mVideoManager.setLocalPreview(surfaceView);
//                            mVideoManager.startCapture();
//                        }
                    }

                }


                SwitchBigAndSmallBean bigAndSmallBean = new SwitchBigAndSmallBean();
                bigAndSmallBean.id = LiveConstants.ANCHORID;
                bigAndSmallBean.surfaceView = surfaceView;
                bigAndSmallBean.userName = apiJoinRoom.anchorName;
                bigAndSmallBean.isOut = false;
                mBigAndSmallBean = bigAndSmallBean;

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });


    }

    private void clear() {
        OOOSvipLiveUtils.getInstance().leaveChannel();
        rl_video.removeAllViews();
        surfaceView = null;
        if (mLiveBeautyViewHolder != null && mLiveBeautyViewHolder.isShowed()) {
            mLiveBeautyViewHolder.hide();
            mLiveBeautyViewHolder = null;
        }
    }


    //切换视图
    public void switchBigAndSmall() {
//        Receive_layout.removeAllViews();
        rl_video.removeAllViews();
        if (IsSmall) {
            IsSmall = false;
            surfaceView.setZOrderMediaOverlay(false);
            surfaceView.setZOrderOnTop(false);
            rl_video.addView(surfaceView);//本地大屏
//            Receive_layout.addView(remoteView);//远程小屏

            if (LiveConstants.FEEUID == HttpClient.getUid()) {
//                if (null == closeAudienceImage) {
                final ImageView closeAudienceImage = new ImageView(mContext);
                closeAudienceImage.setImageResource(R.mipmap.video_live_close);
                RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                    params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                    params3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params3.setMargins(DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5));
                closeAudienceImage.setLayoutParams(params3);
//                Receive_layout.addView(closeAudienceImage);

                closeAudienceImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!VoiceLiveOwnStateBean.IsOpen3T) {
                            PulicUtils.getInstance().disableVideo();//关闭视频
                            VoiceLiveOwnStateBean.IsOpen3T = true;
                            closeAudienceImage.setBackgroundResource(R.mipmap.video_live_close);
                        } else {
                            PulicUtils.getInstance().enableVideo();//启动视频
                            VoiceLiveOwnStateBean.IsOpen3T = false;
                            closeAudienceImage.setBackgroundResource(R.mipmap.video_live_open);
                        }
                    }
                });

            }

        } else {
            IsSmall = true;
            surfaceView.setZOrderMediaOverlay(true);
            surfaceView.setZOrderOnTop(true);
//            Receive_layout.addView(surfaceView);//本地小屏

        }

    }


}
