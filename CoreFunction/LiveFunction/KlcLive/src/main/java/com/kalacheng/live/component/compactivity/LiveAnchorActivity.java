package com.kalacheng.live.component.compactivity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.buslive.httpApi.HttpApiHttpLive;
import com.kalacheng.buslive.socketcontroller.IMApiLive;
import com.kalacheng.base.event.KickOutRoomEvent;
import com.kalacheng.libuser.model.ApiCloseLive;
import com.kalacheng.live.component.ComponentConfig;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.live.R;
import com.kalacheng.livecommon.component.LiveBaseActivity;
import com.kalacheng.util.utils.DialogUtil;
import com.xuantongyun.livecloud.protocol.VideoLiveUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


// 主播直播
@Route(path = ARouterPath.LiveAnchorActivity)
public class LiveAnchorActivity extends LiveBaseActivity{

    private IMApiLive imApiLive;
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = null;
    private AudioManager mAudioMgr;

    @Override
    protected void onPause() {
        super.onPause();
        requestAudioFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        VideoLiveUtils.getInstance().setVolume(1);
        if (null != mAudioFocusChangeListener){
            requestAudioFocus();
        }else {
            setFocusChangeListener();
        }
    }

    @Override
    protected void onDestroy() {
        abandonAudioFocus();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initParams() {
        LiveConstants.IDENTITY = LiveConstants.IDENTITY.ANCHOR;
        LiveConstants.ANCHORID = HttpClient.getUid();
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ExitRoom, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                clean();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

    }

    @Override
    protected int getLayout() {
        return R.layout.liveandience_layout;
    }
    FrameLayout frameLayout1;
    FrameLayout frameLayout2;
    FrameLayout frameLayout4;
    FrameLayout frameLayout5;

    FrameLayout frameLayout6;

    protected void initComponent() {
        frameLayout1 = findViewById(R.id.fl_root1);
        frameLayout2 = findViewById(R.id.fl_root2);
        frameLayout4 = findViewById(R.id.fl_root4);
        frameLayout5 = findViewById(R.id.fl_root5);
        frameLayout6 = findViewById(R.id.fl_root6);
        frameLayout6.setVisibility(View.GONE);
        LiveConstants.ROOMID =0;
        LiveConstants.LiveType = 1;
        LiveConstants.LiveAddress = 1;
        setComponent(ComponentConfig.LIVECOMPONENT1, (FrameLayout) findViewById(R.id.fl_root1));
        setComponent(ComponentConfig.LIVECOMPONENT2, (FrameLayout) findViewById(R.id.fl_root2));
        setComponent(ComponentConfig.LIVECOMPONENT4, (FrameLayout) findViewById(R.id.fl_root4));
        setComponent(ComponentConfig.LIVECOMPONENT5, (FrameLayout) findViewById(R.id.fl_root5));


        //initAudioManager();
        //setFocusChangeListener();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onKickOutRoomEvent(KickOutRoomEvent e) {
        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, null);
        ToastUtil.show("直播君出小差，重新进入房间吧");
    }
    @Override
    protected void initSocket() {
        super.initSocket();
        imApiLive = new IMApiLive();
        imApiLive.init(mSocket);
    }

    @Override
    public void onBackPressed() {
        if (LiveConstants.ROOMID==0){
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, null);
        }else {
            DialogUtil.showSimpleDialog(mContext, null, WordUtil.getString(R.string.live_end_live), true, new DialogUtil.SimpleCallback() {
                @Override
                public void onConfirmClick() {
                    HttpApiHttpLive.closeLive(LiveConstants.ROOMID, new HttpApiCallBack<ApiCloseLive>() {
                        @Override
                        public void onHttpRet(int code, String msg, ApiCloseLive retModel) {
                            if (code == 1 && null != retModel && retModel.code != 7201) {
                                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_HttpCloseLive, retModel);
                            } else if (null != retModel && retModel.code == 7201) {
                                // 时间未到 禁止关播
                                ToastUtil.show("" + retModel.msg);
                            }else {
                                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, null);
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

    }

    public void clean(){
        LiveConstants.ROOMID = 0;
        LiveConstants.ANCHORID = 0;
        LiveConstants.LiveAddress = 0;
        LiveConstants.IsRemoteAudio = false;
        VideoLiveUtils.getInstance().clear();
        finish();
    }

    private void setFocusChangeListener(){
        //Build.VERSION.SDK_INT表示当前SDK的版本，Build.VERSION_CODES.ECLAIR_MR1为SDK 7版本 ，
        //因为AudioManager.OnAudioFocusChangeListener在SDK8版本开始才有。
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.ECLAIR_MR1){
            mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                        //失去焦点之后的操作
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOAudienceBGM,0);
                        Log.e("live>>>", "AUDIOFOCUS_LOSS: 丢失焦点");
                    }else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                        //获得焦点之后的操作
                        Log.e("live>>>", "AUDIOFOCUS_GAIN: 获得焦点");
                    }
                }
            };
        }
    }

    // 要请求音频焦点
    private void requestAudioFocus() {
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.ECLAIR_MR1){
            return;
        }
        if (mAudioMgr == null)
            mAudioMgr = (AudioManager) getApplication().getSystemService(Context.AUDIO_SERVICE);
        if (mAudioMgr != null) {
            Log.e("live>>>", "请求音频焦点");
            int ret = mAudioMgr.requestAudioFocus(mAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            if (ret == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                Log.e("live>>>", "请求获取焦点成功. " + ret);
            }else {
                Log.e("live>>>", "请求获取焦点失败. " + ret);
            }
        }
    }

    // 放弃焦点
    private void abandonAudioFocus() {
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.ECLAIR_MR1){
            return;
        }
        if (mAudioMgr != null) {

            Log.e("live>>>", "放弃音频聚焦");

            mAudioMgr.abandonAudioFocus(mAudioFocusChangeListener);

            mAudioMgr = null;
        }
    }
}
