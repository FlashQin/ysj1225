package com.kalacheng.live.component.componentlive;


import android.content.Context;
import android.os.Handler;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.base.base.BaseViewHolder;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.buslive.socketcontroller.IMApiLiveUserLine;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.SingleString;
import com.kalacheng.libuser.model.ApiCloseLine;
import com.kalacheng.libuser.model.ApiUserLineRoom;
import com.kalacheng.live.R;
import com.kalacheng.util.utils.DpUtil;
import com.wengying666.imsocket.IMApiCallBack;
import com.wengying666.imsocket.SocketClient;
import com.xuantongyun.livecloud.agora.framework.TiPreprocessor;
import com.xuantongyun.livecloud.config.TTTConfigUtils;
import com.xuantongyun.livecloud.protocol.PulicUtils;
import com.xuantongyun.livecloud.protocol.VideoLiveUtils;

import io.agora.capture.video.camera.CameraVideoManager;

public class AudienceVideoComponent extends BaseViewHolder implements LiveBundle.onLiveSocket {

    private SurfaceView surfaceView;
    private RelativeLayout relativeLayout;
    private SurfaceView surfaceViewAnchor;
    private ImageView closeAudienceImage, cameraImage;

    private IMApiLiveUserLine imApiLiveUserLine;
    private CameraVideoManager mVideoManager;

    public AudienceVideoComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_live_video;
    }

    @Override
    protected void init() {
        addToParent();
        LiveBundle.getInstance().addLiveSocketListener(this);
        playCDN();

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ExitRoom, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                clear();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        /*LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_PKFinish, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (LiveConstants.ANCHORID !=HttpClient.getUid()){
                    List<Long> list = new ArrayList<>();
                    list.add(LiveConstants.ANCHORID);
                    VideoCompositingLayout layout = TTTcloud.getVideoCompositingLayout(list, TTTcloud.LinkMicState.WINDOWTOBIG);
                    TTTRtcEngine.getInstance().setVideoCompositingLayout(layout);
                }

            }
        });*/

        // 退出连麦
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LaunchCloseLinkMic, new MsgListener.SimpleMsgListener<ApiCloseLine>() {
            @Override
            public void onMsg(ApiCloseLine apiCloseLine) {
                closeLinkMic();
                VideoLiveUtils.getInstance().clear();
                playCDN();
                if (LiveConstants.liveMicStatus != 0) {
                    PulicUtils.getInstance().muteAllRemoteAudioStreams(false);
                }
                LiveConstants.liveMicStatus = 0;
            }

            @Override
            public void onTagMsg(String tag, ApiCloseLine apiCloseLine) {

            }
        });

        // 主播同意连麦 （自己身份为用户）
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ReceiveStartLinkMic, new MsgListener.SimpleMsgListener<ApiUserLineRoom>() {
            @Override
            public void onMsg(ApiUserLineRoom apiSendLineMsgRoom) {
                if (apiSendLineMsgRoom.uid == HttpClient.getUid()) {
                    LiveConstants.liveMicStatus = 2;
                    PulicUtils.getInstance().setTTTKey();
                    VideoLiveUtils.getInstance().init(mContext, VideoLiveUtils.getInstance().onCloudEventListener);
                    VideoLiveUtils.getInstance().setOnLinkMicAudienceListener(new VideoLiveUtils.onLinkMicAudienceListener() {
                        @Override
                        public void onLinkMicAudience(long uid) {

                        }

                        @Override
                        public void onLinkMicAnchor(long toUid) {

                        }
                    });

                    VideoLiveUtils.getInstance().stopCDNVideo(relativeLayout);

                    surfaceViewAnchor = VideoLiveUtils.getInstance().playRemoteVideo((int) apiSendLineMsgRoom.toUid);
                    relativeLayout.addView(surfaceViewAnchor);
                    surfaceViewAnchor.setZOrderMediaOverlay(false);

                    surfaceView = VideoLiveUtils.getInstance().playLocalVideo((int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_IMAGE_QUALITY, 0), HttpClient.getUid());
                    surfaceView.setZOrderMediaOverlay(true);
                    relativeLayout.addView(surfaceView);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (BaseApplication.getInstance().getResources().getDisplayMetrics().widthPixels * LiveConstants.VIDEORATIO), (int) (BaseApplication.getInstance().getResources().getDisplayMetrics().heightPixels * 0.25));
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    params.setMargins(0, 0, 0, DpUtil.dp2px(60));
                    surfaceView.setLayoutParams(params);
                    if (!TTTConfigUtils.getInstance().getConfig().isTTT() && TTTConfigUtils.getInstance().getConfig().getBeautySwitch() == 1) {
                        TiPreprocessor.mEnabled = true;
                        mVideoManager = ((BaseApplication) BaseApplication.getInstance()).getCameraVideoManager();
                        if (mVideoManager != null) {
                            mVideoManager.setLocalPreview(surfaceView);
                            mVideoManager.startCapture();
                        } else {
                            ((BaseApplication) BaseApplication.getInstance()).initVideoCaptureAsync();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mVideoManager = ((BaseApplication) BaseApplication.getInstance()).getCameraVideoManager();
                                    mVideoManager.setLocalPreview(surfaceView);
                                    mVideoManager.startCapture();
                                }
                            }, 500);
                        }

                        VideoLiveUtils.getInstance().setChannelProfile(1);
                        VideoLiveUtils.getInstance().setClientRole(1);
                    } else {
                        VideoLiveUtils.getInstance().setClientRole(2);
                    }

                    VideoLiveUtils.getInstance().joinChannel(apiSendLineMsgRoom.toRoomId + "");

                    if (null == closeAudienceImage) {
                        closeAudienceImage = new ImageView(mContext);
                        closeAudienceImage.setImageResource(R.mipmap.icon_text_delete);

                        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        params3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        params3.setMargins(DpUtil.dp2px(5), 0, DpUtil.dp2px(5), DpUtil.dp2px(35) + (int) (BaseApplication.getInstance().getResources().getDisplayMetrics().heightPixels * 0.25));
                        relativeLayout.addView(closeAudienceImage);
                        closeAudienceImage.setLayoutParams(params3);
                        closeAudienceImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                imApiLiveUserLine.invitationUserLineClose(LiveConstants.ROOMID, LiveConstants.LinkSessionID, new IMApiCallBack<SingleString>() {
                                    @Override
                                    public void onImRet(int i, String s, SingleString singleString) {
                                        if (i == 0) {

                                        }
                                    }
                                });
                            }
                        });
                    }
                    if (null == cameraImage) {
                        cameraImage = new ImageView(mContext);
                        cameraImage.setImageResource(R.mipmap.icon_live_ready_camera);
                        RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        params4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        params4.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        params4.setMargins(DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(85), DpUtil.dp2px(35) + (int) (BaseApplication.getInstance().getResources().getDisplayMetrics().heightPixels * 0.25));
                        relativeLayout.addView(cameraImage);
                        cameraImage.setLayoutParams(params4);
                        cameraImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                PulicUtils.getInstance().switchCamera();
                            }
                        });
                    }
                }
            }

            @Override
            public void onTagMsg(String tag, ApiUserLineRoom apiUserLineRoom) {

            }
        });

        //向左滑，显示
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LIFT, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        // 暂停播放 （暂用于 观众）
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_StopAndPlayMedia, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if ((boolean) o) {
                    stopVideo();
                } else {
                    playCDN();
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
    }

    public void playCDN() {
        if (null != relativeLayout){
            VideoLiveUtils.getInstance().stopCDNVideo(relativeLayout);
        }
        relativeLayout = (RelativeLayout) findViewById(R.id.rl_video);
        if (null != surfaceView) {
            relativeLayout.removeView(surfaceView);
            surfaceView = null;
        }
        if (null != surfaceViewAnchor) {
            relativeLayout.removeView(surfaceViewAnchor);
            surfaceViewAnchor = null;
        }
        VideoLiveUtils.getInstance().playCDNVideo(mContext, relativeLayout, LiveConstants.PULL, VideoLiveUtils.getInstance().onCloudEventListener);
        relativeLayout.setBackgroundResource(R.mipmap.bg_pk);
        PulicUtils.getInstance().muteLocalAudioStream(LiveConstants.IsRemoteAudio);

        if (!LiveConstants.IsRemoteAudio){
            if (LiveConstants.liveMicStatus != 0){
                PulicUtils.getInstance().muteAllRemoteAudioStreams(false);
            }else {
                VideoLiveUtils.getInstance().setVolume(0.5f);
            }
        }else {
            if (LiveConstants.liveMicStatus != 0){
                PulicUtils.getInstance().muteAllRemoteAudioStreams(true);
            }else {
                VideoLiveUtils.getInstance().setVolume(0f);
            }
        }
    }

    public void clear() {
        VideoLiveUtils.getInstance().clear();
        if (null != surfaceViewAnchor) {
            relativeLayout.removeView(surfaceViewAnchor);
            surfaceViewAnchor = null;
        }
        if (null != surfaceView) {
            relativeLayout.removeView(surfaceView);
            surfaceView = null;
        }
        relativeLayout.removeView(closeAudienceImage);
        closeAudienceImage = null;
        relativeLayout.removeView(cameraImage);
        cameraImage = null;
        relativeLayout.removeAllViews();
        removeFromParent();
    }

    private void stopVideo() {
        VideoLiveUtils.getInstance().stopCDNVideo(relativeLayout);
    }

    private void closeLinkMic() {
        if (mVideoManager != null) {
            TiPreprocessor.mEnabled = false;
            mVideoManager.stopCapture();
        }
        relativeLayout.removeView(surfaceViewAnchor);
        surfaceViewAnchor = null;
        relativeLayout.removeView(closeAudienceImage);
        closeAudienceImage = null;
        relativeLayout.removeView(cameraImage);
        cameraImage = null;
    }

    @Override
    public void init(String groupName, SocketClient socketClient) {
        imApiLiveUserLine = new IMApiLiveUserLine();
        imApiLiveUserLine.init(socketClient);
    }


}
