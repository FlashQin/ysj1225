package com.kalacheng.commonview.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.kalacheng.commonview.R;
import com.kalacheng.commonview.music.LiveMusicPlayer1;
import com.kalacheng.commonview.music.MusicDbManager1;
import com.kalacheng.commonview.music.dialog.MusicVoiceDialogFragment;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.model.AppUserMusicDTO;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xuantongyun.livecloud.protocol.PulicUtils;

import java.util.List;
import java.util.Random;

public class LiveMusicView implements View.OnClickListener {

    private static volatile LiveMusicView singleton;
    private Context mContext;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams wmParams;
    private LayoutInflater inflater;
    private View mFloatingLayout;    //布局View
    private LiveMusicPlayer1 mLiveMusicPlayer;
    //播放模式 playType
    private int playType = 1;//1 顺序循环模式 2随机模式
    private boolean IsPaly = false;
    //播放的游标
    private int playPoistion;

    //暂停或者开始
    private boolean isStart = true;

    private int mTouchStartX, mTouchStartY, mTouchCurrentX, mTouchCurrentY;
    private int mStartX, mStartY, mStopX, mStopY;
    private boolean isMove;
    private long startTime;
    private long endTime;
    private boolean isclick;

    /**
     * 设置悬浮框基本参数（位置、宽高等）
     */
    private RoundedImageView LiveMusic;
    private ImageView LiveMusic_loop, LiveMusic_last, LiveMusic_start, LiveMusic_next, LiveMusic_voice, LiveMusic_list, LiveMusic_close;


    private LiveMusicView() {
    }

    public static LiveMusicView getInstance() {

        if (singleton == null) {
            synchronized (LiveMusicView.class) {
                if (singleton == null) {
                    singleton = new LiveMusicView();
                }
            }
        }
        return singleton;
    }

    public void show(Context mContext) {
        this.mContext = mContext;
        initWindow();
    }

    private void initWindow() {
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wmParams = getParams();
        inflater = LayoutInflater.from(mContext);
        mFloatingLayout = inflater.inflate(R.layout.view_live_music1, null);

        if (mLiveMusicPlayer != null) {
            mLiveMusicPlayer = null;
        }
        mLiveMusicPlayer = new LiveMusicPlayer1();
        mLiveMusicPlayer.setLiveMusicPlayerCallBack(new LiveMusicPlayer1.LiveMusicPlayerCallBack() {
            @Override
            public void onCompletion() {
                next();
            }

            public void onPrepared(String path) {
                LiveMusic_start.setBackgroundResource(R.mipmap.icon_music_start);
                PulicUtils.getInstance().startBgm(path);
            }
        });

        LiveMusic = (RoundedImageView) mFloatingLayout.findViewById(R.id.LiveMusic);
        LiveMusic.setOnClickListener(this);
        LiveMusic_loop = (ImageView) mFloatingLayout.findViewById(R.id.LiveMusic_loop);
        LiveMusic_loop.setOnClickListener(this);
        LiveMusic_last = (ImageView) mFloatingLayout.findViewById(R.id.LiveMusic_last);
        LiveMusic_last.setOnClickListener(this);
        LiveMusic_start = (ImageView) mFloatingLayout.findViewById(R.id.LiveMusic_start);
        LiveMusic_start.setOnClickListener(this);
        LiveMusic_next = (ImageView) mFloatingLayout.findViewById(R.id.LiveMusic_next);
        LiveMusic_next.setOnClickListener(this);
        LiveMusic_voice = (ImageView) mFloatingLayout.findViewById(R.id.LiveMusic_voice);
        LiveMusic_voice.setOnClickListener(this);
        LiveMusic_list = (ImageView) mFloatingLayout.findViewById(R.id.LiveMusic_list);
        LiveMusic_list.setOnClickListener(this);
        LiveMusic_close = (ImageView) mFloatingLayout.findViewById(R.id.LiveMusic_close);
        LiveMusic_close.setOnClickListener(this);

        mFloatingLayout.setOnTouchListener(new FloatingListener());
        mWindowManager.addView(mFloatingLayout, wmParams);
    }

    private WindowManager.LayoutParams getParams() {
        wmParams = new WindowManager.LayoutParams();
        //wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        //wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        wmParams.format = PixelFormat.RGBA_8888;
        //设置可以显示在状态栏上
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //这是悬浮窗居中位置
        wmParams.gravity = Gravity.TOP | Gravity.LEFT;
        //70、210是我项目中的位置哦
        wmParams.x = 5;
        wmParams.y = DpUtil.dp2px(100);
        return wmParams;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.LiveMusic_close) {//关闭
            close();
        } else if (v.getId() == R.id.LiveMusic_loop) {//循环
            loop();
        } else if (v.getId() == R.id.LiveMusic_last) {//上一曲
            last();
        } else if (v.getId() == R.id.LiveMusic_start) {//开始
            if (isStart) {
                isStart = false;
                LiveMusic_start.setBackgroundResource(R.mipmap.icon_music_stop);
                stop();
            } else {
                isStart = true;
                LiveMusic_start.setBackgroundResource(R.mipmap.icon_music_start);
                start();
            }

        } else if (v.getId() == R.id.LiveMusic_next) {//下一曲
            next();

        } else if (v.getId() == R.id.LiveMusic_voice) {//声音
            voice();
        } else if (v.getId() == R.id.LiveMusic_list) {//列表
            list();
        }
    }

    /**
     * 播放背景音乐
     */
    public void play(int poistion) {
        List<AppUserMusicDTO> list = MusicDbManager1.getInstace().queryList();
        if (list != null && list.size() > poistion) {
            LiveConstants.isPalyMusic = true;
            ImageLoader.display(list.get(poistion).cover, LiveMusic, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            if (mLiveMusicPlayer != null) {
                mLiveMusicPlayer.play(String.valueOf(list.get(poistion).id), IsPaly);
//            PlayMusicEvent playMusicEvent =  new PlayMusicEvent();
//            playMusicEvent.id =list.get(poistion).id;
//            EventBus.getDefault().post(new PlayMusicEvent());
            }
        }
    }

    /*
     * 循环
     * */
    public void loop() {
        if (playType == 1) {
            playType = 2;
            LiveMusic_loop.setBackgroundResource(R.mipmap.icon_music_suiji);
        } else {
            playType = 1;
            LiveMusic_loop.setBackgroundResource(R.mipmap.icon_music_loop);

        }
    }

    /*
     * 上一曲
     * */
    public void last() {
        LiveMusic_start.setBackgroundResource(R.mipmap.icon_music_stop);


        if (playType == 1) {
            if (playPoistion == 0) {
                playPoistion = (MusicDbManager1.getInstace().queryList().size() - 1);
            } else {
                playPoistion--;
            }
            play(playPoistion);
        } else {
            Random random = new Random();
            if (playPoistion != random.nextInt(MusicDbManager1.getInstace().queryList().size())) {
                playPoistion = random.nextInt(MusicDbManager1.getInstace().queryList().size());

                play(playPoistion);
            } else {
                last();
            }
        }


    }

    /*
     * 开始
     * */
    public void start() {
        if (mLiveMusicPlayer != null) {
            mLiveMusicPlayer.start();
        }
        PulicUtils.getInstance().resumeBgm();
    }

    /*
     *暂停音乐播放
     * */
    public void stop() {
        IsPaly = false;
        if (mLiveMusicPlayer != null) {
            mLiveMusicPlayer.stop();
        }
        PulicUtils.getInstance().pauseBgm();
    }


    /*
     * 下一曲
     * */
    public void next() {
        LiveMusic_start.setBackgroundResource(R.mipmap.icon_music_stop);

        if (playType == 1) {
            if (playPoistion == (MusicDbManager1.getInstace().queryList().size() - 1)) {
                playPoistion = 0;
            } else {
                playPoistion++;
            }
            play(playPoistion);
        } else {
            Random random = new Random();
            if (playPoistion != random.nextInt(MusicDbManager1.getInstace().queryList().size())) {
                playPoistion = random.nextInt(MusicDbManager1.getInstace().queryList().size());

                play(playPoistion);
            } else {
                next();
            }
        }

    }

    /*
     * 声音
     * */
    MusicVoiceDialogFragment musicVoiceDialogFragment;

    public void voice() {

        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_MusicVoice, mLiveMusicPlayer);
    }

    /*
     * 列表
     * */

    public void list() {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_Music, null);
    }

    public void close() {
        if (mLiveMusicPlayer != null) {
            mLiveMusicPlayer.end();
            mLiveMusicPlayer.release();
        }
        PulicUtils.getInstance().stopBgm();

        if (musicVoiceDialogFragment != null) {
            musicVoiceDialogFragment = null;
        }

        IsPaly = false;
        LiveConstants.isPalyMusic = false;

        if (singleton != null) {
            singleton = null;
            if (null != mWindowManager && null != mFloatingLayout) {
                mWindowManager.removeView(mFloatingLayout);
            }
        }
    }


    private class FloatingListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    startTime = System.currentTimeMillis();
                    isMove = false;
                    mTouchStartX = (int) event.getRawX();
                    mTouchStartY = (int) event.getRawY();
                    mStartX = (int) event.getX();
                    mStartY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mTouchCurrentX = (int) event.getRawX();
                    mTouchCurrentY = (int) event.getRawY();
                    wmParams.x += mTouchCurrentX - mTouchStartX;
                    wmParams.y += mTouchCurrentY - mTouchStartY;
                    mWindowManager.updateViewLayout(mFloatingLayout, wmParams);

                    mTouchStartX = mTouchCurrentX;
                    mTouchStartY = mTouchCurrentY;
                    break;
                case MotionEvent.ACTION_UP:
                    mStopX = (int) event.getX();
                    mStopY = (int) event.getY();
                    if (Math.abs(mStartX - mStopX) >= 1 || Math.abs(mStartY - mStopY) >= 1) {
                        isMove = true;
                    }
                    endTime = System.currentTimeMillis();
                    //当从点击到弹起小于半秒的时候,则判断为点击,如果超过则不响应点击事件
                    if ((endTime - startTime) > 0.1 * 1000L) {
                        isclick = false;
                    } else {
                        isclick = true;
                    }
                    startTime = 0;
                    endTime = 0;
                    break;
            }
            if (isclick) {
                isclick = false;
            }
            return false;
        }
    }

    //后台隐藏
    public void setGone() {
        if (LiveConstants.isPalyMusic) {
            if (mFloatingLayout != null) {
                mFloatingLayout.setVisibility(View.GONE);
            }
        }
    }

    //回到app显示
    public void setVisible() {
        if (LiveConstants.isPalyMusic) {
            if (mFloatingLayout != null) {
                mFloatingLayout.setVisibility(View.VISIBLE);
            }
        }
    }


}
