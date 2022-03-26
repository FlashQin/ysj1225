package com.kalacheng.live.component.componentlive;


import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
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
import com.kalacheng.buslive.socketcontroller.IMApiLiveAnchorLine;
import com.kalacheng.buslive.socketcontroller.IMApiLiveUserLine;
import com.kalacheng.frame.config.APPProConfig;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.SingleString;
import com.kalacheng.libuser.model.ApiCloseLine;
import com.kalacheng.libuser.model.ApiUserLineRoom;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.live.R;
import com.kalacheng.util.utils.DpUtil;
import com.wengying666.imsocket.IMApiCallBack;
import com.wengying666.imsocket.SocketClient;
import com.xuantongyun.livecloud.agora.framework.TiPreprocessor;
import com.xuantongyun.livecloud.config.TTTConfigUtils;
import com.xuantongyun.livecloud.protocol.PulicUtils;
import com.xuantongyun.livecloud.protocol.VideoLiveUtils;

import io.agora.capture.video.camera.CameraVideoManager;


public class LiveAnchorVideoComponent extends BaseViewHolder implements LiveBundle.onLiveSocket {
    private SurfaceView surfaceView;
    private RelativeLayout relativeLayout;
    private IMApiLiveAnchorLine imApiLive;
    IMApiLiveUserLine imApiLiveUserLine;
    SocketClient mSocket;
    private SurfaceView remoteVideo;
    private ImageView closeAnchorImage;
    private ImageView closeAudienceImage;
    long toRoomId;
    IMApiCallBack<SingleString> respCallback;
    private SurfaceView remoteView;
    boolean mCameraFront = true;
    boolean isFlash;
    private long broadcasterId;
    private CameraVideoManager mVideoManager;

    public LiveAnchorVideoComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_live_video;
    }


    @Override
    protected void init() {
        addToParent();
        if (null == respCallback) {
            respCallback = new IMApiCallBack<SingleString>() {
                @Override
                public void onImRet(int code, String errMsg, SingleString retModel) {
                    if (TextUtils.isEmpty(errMsg))
                        ToastUtil.show(errMsg);
                    relativeLayout.setBackgroundResource(R.mipmap.bg_pk);
                }
            };
        }
        LiveBundle.getInstance().addLiveSocketListener(this);
        PulicUtils.getInstance().setTTTKey();
        VideoLiveUtils.getInstance().init(mContext, VideoLiveUtils.getInstance().onCloudEventListener);
        VideoLiveUtils.getInstance().setChannelProfile(1);
        VideoLiveUtils.getInstance().setClientRole(1);

        surfaceView = VideoLiveUtils.getInstance().playLocalVideo((int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_IMAGE_QUALITY, 0), HttpClient.getUid());
        relativeLayout = (RelativeLayout) findViewById(R.id.rl_video);
        surfaceView.setZOrderMediaOverlay(false);
        relativeLayout.addView(surfaceView);
        if (!TTTConfigUtils.getInstance().getConfig().isTTT() && TTTConfigUtils.getInstance().getConfig().getBeautySwitch() == 1) {
            TiPreprocessor.mEnabled = true;
            mVideoManager = ((BaseApplication)BaseApplication.getInstance()).getCameraVideoManager();
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

        initListener();
    }

    private void initListener() {
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OpenLiveMsg, new MsgListener.SimpleMsgListener<AppJoinRoomVO>() {
            @Override
            public void onMsg(AppJoinRoomVO apiJoinRoom) {
                VideoLiveUtils.getInstance().startPush(LiveConstants.ROOMID, TTTConfigUtils.getInstance().getTTTPush() + "/" + LiveConstants.ANCHORID);
            }

            @Override
            public void onTagMsg(String tag, AppJoinRoomVO apiJoinRoom) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_StartInteraction, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                anchorLinkMic((ApiUserLineRoom) o);
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ReceiveCloseInteraction, new MsgListener.SimpleMsgListener<ApiCloseLine>() {
            @Override
            public void onMsg(ApiCloseLine apiCloseLine) {
                unSubscription(toRoomId);
            }

            @Override
            public void onTagMsg(String tag, ApiCloseLine apiCloseLine) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ExitRoom, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                clear();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
       /* LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_PKFinish, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                unSubscription(toRoomId);
            }
        });*/

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LaunchCloseLinkMic, new MsgListener.SimpleMsgListener<ApiCloseLine>() {
            @Override
            public void onMsg(ApiCloseLine apiCloseLive) {
                closeLinkMic();
            }

            @Override
            public void onTagMsg(String tag, ApiCloseLine apiCloseLine) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.FunctionMsg, new MsgListener.SimpleMsgListener<Integer>() {
            @Override
            public void onMsg(Integer integer) {
                if (integer == R.mipmap.icon_live_func_beauty) {
//                    showBeautyDailog();
                } else if (integer == R.mipmap.icon_live_func_camera) {
                    mCameraFront = !mCameraFront;
                    PulicUtils.getInstance().switchCamera();
                } else if (integer == R.mipmap.icon_live_func_flash) {
                    if (mCameraFront) {
                        ToastUtil.show(R.string.live_open_flash);
                        isFlash = false;
                        PulicUtils.getInstance().setCameraTorchOn(false);
                        return;
                    } else {
                        if (isFlash) {
                            PulicUtils.getInstance().setCameraTorchOn(false);
                        } else {
                            PulicUtils.getInstance().setCameraTorchOn(true);
                        }
                        isFlash = !isFlash;
                    }
                }
            }

            @Override
            public void onTagMsg(String tag, Integer integer) {

            }
        });
        //监听第三方平台
        VideoLiveUtils.getInstance().setOnLinkMicAudienceListener(new VideoLiveUtils.onLinkMicAudienceListener() {
            @Override
            public void onLinkMicAnchor(long toUid) {
                if (null == remoteVideo) {
                    remoteVideo = VideoLiveUtils.getInstance().playRemoteVideo(toUid);
                    remoteVideo.setZOrderMediaOverlay(true);
                    relativeLayout.addView(remoteVideo);
                    RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(DpUtil.getScreenWidth() / 2, APPProConfig.getInstance().getVidowHeight());
                    params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    params2.setMargins(0, DpUtil.dp2px(130), 0, 0);
                    remoteVideo.setLayoutParams(params2);
                }

                if (null == closeAnchorImage) {
                    closeAnchorImage = new ImageView(mContext);
                    closeAnchorImage.setImageResource(R.mipmap.icon_text_delete);
                    RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    params3.setMargins(DpUtil.dp2px(5), DpUtil.dp2px(132), DpUtil.dp2px(5), DpUtil.dp2px(5));
                    relativeLayout.addView(closeAnchorImage);
                    closeAnchorImage.setLayoutParams(params3);
                    closeAnchorImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            imApiLive.invitationAnchorLineClose(LiveConstants.ROOMID, LiveConstants.LinkSessionID, respCallback);
                        }
                    });
                }
            }

            @Override
            public void onLinkMicAudience(long uid) {
                remoteView = VideoLiveUtils.getInstance().playRemoteVideo(uid);
                relativeLayout.addView(remoteView);
                remoteView.setZOrderMediaOverlay(true);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (BaseApplication.getInstance().getResources().getDisplayMetrics().widthPixels * LiveConstants.VIDEORATIO), (int) (BaseApplication.getInstance().getResources().getDisplayMetrics().heightPixels * 0.25));
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.setMargins(0, 0, 0, DpUtil.dp2px(120));
                remoteView.setLayoutParams(params);
                if (null == closeAudienceImage) {
                    closeAudienceImage = new ImageView(mContext);
                    closeAudienceImage.setImageResource(R.mipmap.icon_text_delete);
                    RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    params3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    params3.setMargins(DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(95) + (int) (BaseApplication.getInstance().getResources().getDisplayMetrics().heightPixels * 0.25));
                    relativeLayout.addView(closeAudienceImage);
                    closeAudienceImage.setLayoutParams(params3);
                    closeAudienceImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            imApiLiveUserLine.invitationUserLineClose(LiveConstants.ANCHORID, LiveConstants.LinkSessionID, respCallback);
                        }
                    });


                }
            }
        });
    }

    private void anchorLinkMic(ApiUserLineRoom apiSendLineMsgRoom) {
        if (surfaceView != null) {
            VideoLiveUtils.getInstance().isLinkMicPK(true);
            toRoomId = apiSendLineMsgRoom.toRoomId;
            VideoLiveUtils.getInstance().linkMic(toRoomId);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(DpUtil.getScreenWidth() / 2, APPProConfig.getInstance().getVidowHeight());
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.setMargins(0, DpUtil.dp2px(130), 0, 0);
            surfaceView.setLayoutParams(params);
            relativeLayout.setBackgroundResource(R.mipmap.bg_pk);

        }
    }

    @Override
    public void init(String activityName, SocketClient socketClient) {
        mSocket = socketClient;
        imApiLive = new IMApiLiveAnchorLine();
        imApiLiveUserLine = new IMApiLiveUserLine();
        imApiLive.init(mSocket);
        imApiLiveUserLine.init(mSocket);

    }

    public void unSubscription(long toRoomId) {
      /*  if (surfaceView==null){
            surfaceView = TTTcloud.getInstance().playLocalVideo((int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_IMAGE_QUALITY, 0), HttpClient.getUid());
            TTTcloud.getInstance().setClientRole(Constants.CLIENT_ROLE_ANCHOR);
            relativeLayout = (RelativeLayout) findViewById(R.id.rl_video);
            surfaceView.setZOrderMediaOverlay(false);

            relativeLayout.addView(surfaceView);

        }*/
        if (surfaceView != null) {
            VideoLiveUtils.getInstance().stopLinkMic(toRoomId);
            VideoLiveUtils.getInstance().updateRtmpUrl(TTTConfigUtils.getInstance().getTTTPush() + "/" + LiveConstants.ANCHORID);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            surfaceView.setLayoutParams(params);
            relativeLayout.removeView(remoteVideo);
            relativeLayout.removeView(closeAnchorImage);
            relativeLayout.removeView(closeAudienceImage);
            remoteVideo = null;
            closeAnchorImage = null;
            closeAudienceImage = null;
            relativeLayout.setBackgroundResource(0);
        }

    }

    private void clear() {
        if (mVideoManager != null) {
            TiPreprocessor.mEnabled = false;
            mVideoManager.stopCapture();
        }
        VideoLiveUtils.getInstance().clear();
        relativeLayout.removeAllViews();
        remoteVideo = null;
        closeAnchorImage = null;
        closeAudienceImage = null;
        surfaceView = null;
        remoteView = null;

    }

    private void closeLinkMic() {
        relativeLayout.removeView(remoteView);
        remoteView = null;
        relativeLayout.removeView(closeAudienceImage);
        closeAudienceImage = null;
        relativeLayout.setBackgroundResource(0);
    }


}
