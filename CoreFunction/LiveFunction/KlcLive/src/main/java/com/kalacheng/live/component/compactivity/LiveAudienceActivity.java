package com.kalacheng.live.component.compactivity;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.buscommon.model.ApiUserBasicInfo;
import com.kalacheng.commonview.utils.LookRoomUtlis;
import com.kalacheng.base.event.KickOutRoomEvent;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.live.R;
import com.kalacheng.live.component.ComponentConfig;
import com.kalacheng.livecommon.component.LiveBaseActivity;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.xuantongyun.livecloud.protocol.VideoLiveUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;


@Route(path = ARouterPath.LiveAudienceActivity)
public class LiveAudienceActivity extends LiveBaseActivity {

    @Autowired(name = "ApiJoinRoom")
    public AppJoinRoomVO retModel;
    @Autowired(name = "userList")
    public ArrayList<ApiUserBasicInfo> userList;

    ObjectAnimator giftAnimatorEnd2;
    ObjectAnimator giftAnimator2;

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
        if (null != mAudioFocusChangeListener) {
            requestAudioFocus();
        } else {
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
        LiveConstants.IDENTITY = LiveConstants.IDENTITY.AUDIENCE;
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ExitRoom, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                LiveConstants.IsLookLive = false;
                clean();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //向左滑，显示
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LIFT, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                giftAnimator2 = ObjectAnimator.ofFloat(frameLayout2, "translationX", 1500, 0);
                giftAnimator2.setDuration(500);
                giftAnimator2.setInterpolator(new LinearInterpolator());
                giftAnimator2.start();
                frameLayout4.setVisibility(View.VISIBLE);
                frameLayout5.setVisibility(View.VISIBLE);

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //向右滑，消失
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_RIGHT, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                giftAnimatorEnd2 = ObjectAnimator.ofFloat(frameLayout2, "translationX", 1500);
                giftAnimatorEnd2.setDuration(500);
                giftAnimatorEnd2.setInterpolator(new LinearInterpolator());
                giftAnimatorEnd2.start();
                frameLayout4.setVisibility(View.GONE);
                frameLayout5.setVisibility(View.GONE);

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
    FrameLayout frameLayout7;
    float downX;
    float downY;
    float upX;
    float upY;

    //1左 2右
    private int mSlide = 2;

    protected void initComponent() {
        frameLayout1 = findViewById(R.id.fl_root1);
        frameLayout2 = findViewById(R.id.fl_root2);
        frameLayout4 = findViewById(R.id.fl_root4);
        frameLayout5 = findViewById(R.id.fl_root5);
        frameLayout6 = findViewById(R.id.fl_root6);

        retModel = getIntent().getParcelableExtra("ApiJoinRoom");
        userList = getIntent().getParcelableArrayListExtra("userList");
        setComponent(ComponentConfig.AUDIENCECOMPONENT1, (FrameLayout) findViewById(R.id.fl_root1));
        setComponent(ComponentConfig.AUDIENCECOMPONENT2, (FrameLayout) findViewById(R.id.fl_root2));
        setComponent(ComponentConfig.AUDIENCECOMPONENT4, (FrameLayout) findViewById(R.id.fl_root4));
        setComponent(ComponentConfig.AUDIENCECOMPONENT5, (FrameLayout) findViewById(R.id.fl_root5));

        if (ConfigUtil.getBoolValue(R.bool.containShopping)) {
            frameLayout7 = findViewById(R.id.fl_root7);
            setComponent(ComponentConfig.AUDIENCECOMPONENT6, (FrameLayout) findViewById(R.id.fl_root7)); // 直播购 观众 最外层拖动的商品图片
            frameLayout7.setVisibility(View.VISIBLE);
        }
        if (null != retModel) {
            retModel.userList = userList;
        }
        LiveConstants.IsLookLive = true;
        LiveConstants.LiveType = 1;
        LiveConstants.LiveAddress = 1;
        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.RoomInfoList, retModel);

        //setFocusChangeListener();

        frameLayout6.setOnTouchListener(new View.OnTouchListener() {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onKickOutRoomEvent(KickOutRoomEvent e) {
        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, null);
        ToastUtil.show("直播君出小差，重新进入房间吧");
    }

    //如果支付成功 继续播放扣费视频  7101：计时房间/门票房间 余额不足  99：贵族房间 不是贵族
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 7101:
                if (null != data && data.getBooleanExtra("isSuccess", false)) {
                    goDeduction();
                }
                break;
            case 99:
                goDeduction();
                break;
            default:
                break;
        }
    }

    private void goDeduction() {
        //延迟一会儿 避免充值成功 后台没有到账
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_Deduction, 2);
            }
        }, 500);
    }

    @Override
    public void onBackPressed() {
        LookRoomUtlis.getInstance().closeLive();
//        super.onBackPressed();
    }

    public void clean() {
//        IMUtil.removeReceiver(this.getLocalClassName());

        if (giftAnimator2 != null) {
            giftAnimator2.cancel();
        }
        giftAnimator2 = null;
        if (giftAnimatorEnd2 != null) {
            giftAnimatorEnd2.cancel();
        }
        giftAnimatorEnd2 = null;
        LiveConstants.ROOMID = 0;
        LiveConstants.ANCHORID = 0;
        LiveConstants.LiveAddress = 0;
        LiveConstants.IsRemoteAudio = false;
        VideoLiveUtils.getInstance().clear();
        finish();
    }

    private void setFocusChangeListener() {
        //Build.VERSION.SDK_INT表示当前SDK的版本，Build.VERSION_CODES.ECLAIR_MR1为SDK 7版本 ，
        //因为AudioManager.OnAudioFocusChangeListener在SDK8版本开始才有。
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ECLAIR_MR1) {
            mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        //失去焦点之后的操作
                        VideoLiveUtils.getInstance().setVolume(0);
                        Log.e("live>>>", "AUDIOFOCUS_LOSS: 丢失焦点");
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        //获得焦点之后的操作
                        Log.e("live>>>", "AUDIOFOCUS_GAIN: 获得焦点");
                    }
                }
            };
        }
    }

    // 要请求音频焦点
    private void requestAudioFocus() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ECLAIR_MR1) {
            return;
        }
        if (mAudioMgr == null)
            mAudioMgr = (AudioManager) getApplication().getSystemService(Context.AUDIO_SERVICE);
        if (mAudioMgr != null) {
            Log.e("live>>>", "请求音频焦点");
            int ret = mAudioMgr.requestAudioFocus(mAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            if (ret == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                Log.e("live>>>", "请求获取焦点成功. " + ret);
                VideoLiveUtils.getInstance().setVolume(1);
            } else {
                Log.e("live>>>", "请求获取焦点失败. " + ret);
            }
        }
    }

    // 放弃焦点
    private void abandonAudioFocus() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ECLAIR_MR1) {
            return;
        }
        if (mAudioMgr != null) {

            Log.e("live>>>", "放弃音频聚焦");

            mAudioMgr.abandonAudioFocus(mAudioFocusChangeListener);

            mAudioMgr = null;
        }
    }

}
