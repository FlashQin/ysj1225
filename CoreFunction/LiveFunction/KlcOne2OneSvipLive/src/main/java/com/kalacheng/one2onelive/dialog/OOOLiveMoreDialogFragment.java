package com.kalacheng.one2onelive.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.one2onelive.R;
import com.klc.bean.live.VoiceLiveOwnStateBean;
import com.xuantongyun.livecloud.protocol.PulicUtils;


public class OOOLiveMoreDialogFragment extends BaseDialogFragment implements View.OnClickListener {


    private LinearLayout live_dialog_close, live_dialog_camera, live_dialog_beautiful, live_dialog_Openvideo, live_dialog_closevideo,
            live_dialog_voice, live_dialog_fans;

    private ImageView live_dialog_video_image, live_dialog_voice_image;

    private AppJoinRoomVO joinRoom;

    @Override
    protected int getLayoutId() {
        return R.layout.ooo_live_layout;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(com.kalacheng.livecommon.R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    public void getApiJoinRoom(AppJoinRoomVO joinRoom) {
        this.joinRoom = joinRoom;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        getInit();
    }

    public void getInit() {

        live_dialog_close = mRootView.findViewById(R.id.live_dialog_close);
        live_dialog_close.setOnClickListener(this);
        live_dialog_camera = mRootView.findViewById(R.id.live_dialog_camera);
        live_dialog_camera.setOnClickListener(this);
        live_dialog_beautiful = mRootView.findViewById(R.id.live_dialog_beautiful);
        live_dialog_beautiful.setOnClickListener(this);
        live_dialog_Openvideo = mRootView.findViewById(R.id.live_dialog_Openvideo);
        live_dialog_Openvideo.setOnClickListener(this);
        live_dialog_voice = mRootView.findViewById(R.id.live_dialog_voice);
        live_dialog_voice.setOnClickListener(this);
        live_dialog_fans = mRootView.findViewById(R.id.live_dialog_fans);
        live_dialog_fans.setOnClickListener(this);

        live_dialog_voice_image = mRootView.findViewById(R.id.live_dialog_voice_image);

        live_dialog_video_image = mRootView.findViewById(R.id.live_dialog_video_image);

        if (LiveConstants.FEEUID == 0 || LiveConstants.FEEUID == HttpClient.getUid()) {
            live_dialog_beautiful.setVisibility(View.VISIBLE);
            live_dialog_camera.setVisibility(View.GONE);
            live_dialog_voice.setVisibility(View.VISIBLE);
            live_dialog_Openvideo.setVisibility(View.VISIBLE);
        } else {
            if (LiveConstants.ANCHORID == HttpClient.getUid()) {
                live_dialog_beautiful.setVisibility(View.GONE);
                live_dialog_camera.setVisibility(View.VISIBLE);
                live_dialog_voice.setVisibility(View.VISIBLE);
                live_dialog_Openvideo.setVisibility(View.VISIBLE);
            } else {
                live_dialog_Openvideo.setVisibility(View.GONE);
                live_dialog_beautiful.setVisibility(View.GONE);
                live_dialog_camera.setVisibility(View.GONE);
                live_dialog_voice.setVisibility(View.GONE);
            }
        }

        setShowOpenVideo();
        getShowOpenVoice();
    }

    /*------ 打开视频功能 S ----------------------------------------------------------------------*/

    /**
     * 【初始化】 打开视频功能显示状态
     * (启动视频/关闭视频)
     */
    private void setShowOpenVideo() {
        setShowOpenVideo(false);
    }

    /**
     * 【设置】 打开视频功能显示状态
     * (启动视频/关闭视频)
     *
     * @param isOnClick true 标识为点击事件 / false 标识为初始化显示变化
     */
    private void setShowOpenVideo(boolean isOnClick) {

        if (VoiceLiveOwnStateBean.IsOpen3T) {
            if (isOnClick) {
                PulicUtils.getInstance().enableVideo();//启动视频
                VoiceLiveOwnStateBean.IsOpen3T = false;
            }
        } else {
            if (isOnClick) {
                PulicUtils.getInstance().disableVideo();//关闭视频
                VoiceLiveOwnStateBean.IsOpen3T = true;
            }
        }

        //判断是否打开3t云视频 false 打开 true 关闭
        if (VoiceLiveOwnStateBean.IsOpen3T) {
            live_dialog_video_image.setBackgroundResource(R.mipmap.video_live_close);
        } else {
            live_dialog_video_image.setBackgroundResource(R.mipmap.video_live_open);
        }
    }

    /*------ 打开视频功能 E ----------------------------------------------------------------------*/


    /*------ 静音功能 S ----------------------------------------------------------------------*/
    // 获取静音状态设置icon
    private void getShowOpenVoice() {
        if (LiveConstants.IsRemoteAudio) {
            live_dialog_voice_image.setBackgroundResource(R.mipmap.live_voice_close);
        } else {
            live_dialog_voice_image.setBackgroundResource(R.mipmap.voice_open);
        }
    }

    // 修改静音状态
    private void setShowOpenVoice() {
        if (LiveConstants.IsRemoteAudio) {
            PulicUtils.getInstance().muteAllRemoteAudioStreams(false);
            LiveConstants.IsRemoteAudio = false;
        } else {
            PulicUtils.getInstance().muteAllRemoteAudioStreams(true);
            LiveConstants.IsRemoteAudio = true;
        }
        getShowOpenVoice();
    }

    /*------ 静音功能 E ----------------------------------------------------------------------*/

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.live_dialog_camera) {//翻转张相机
            PulicUtils.getInstance().switchCamera();
        } else if (view.getId() == R.id.live_dialog_close) {//关闭视频
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOEndRequestGetTime, null);
        } else if (view.getId() == R.id.live_dialog_beautiful) {//美颜
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_BeautyShow, null);
            dismiss();
        } else if (view.getId() == R.id.live_dialog_Openvideo) {//打开视频
            setShowOpenVideo(true);
        } else if (view.getId() == R.id.live_dialog_fans) {//粉丝团
            if (LiveConstants.FEEUID == HttpClient.getUid()) {
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_AddFansGroup, null);
            } else {
                ARouter.getInstance().build(ARouterPath.FansGroupActivity).navigation();
            }
            dismiss();
        } else if (view.getId() == R.id.live_dialog_voice) {
            setShowOpenVoice();
        }

    }
}
