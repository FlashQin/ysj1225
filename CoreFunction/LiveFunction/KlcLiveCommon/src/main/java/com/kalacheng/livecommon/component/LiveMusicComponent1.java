package com.kalacheng.livecommon.component;


import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentActivity;

import com.kalacheng.commonview.music.LiveMusicPlayer1;
import com.kalacheng.commonview.music.MusicDbManager1;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.livecommon.R;
import com.kalacheng.commonview.music.dialog.MusicVoiceDialogFragment;
import com.kalacheng.commonview.music.dialog.LiveMusicDialogFragment1;
import com.kalacheng.util.utils.DpUtil;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.base.base.BaseViewHolder;
import com.kalacheng.base.listener.MsgListener;

import java.util.Random;


public class LiveMusicComponent1 extends BaseViewHolder implements View.OnClickListener, View.OnTouchListener {
    LiveMusicPlayer1 mLiveMusicPlayer;
    private int mParentWidth;
    private int mParentHeight;
    private float mLastX;
    private float mLastY;
    private long mTime;
    private boolean mPaused;
    MsgListener.SimpleMsgListener functionListener;

    private boolean IsPaly = false;

    //小框口拖动
    private int screenWidth, screenHeight;
    private int lastX, lastY;
    private int endX1, endY1;
    private int startx;
    long startTime;
    long endTime;
    boolean isclick;

    private RoundedImageView LiveMusic;
    private ImageView LiveMusic_loop, LiveMusic_last, LiveMusic_start, LiveMusic_next, LiveMusic_voice, LiveMusic_list, LiveMusic_close;

    //播放模式 playType
    private int playType = 1;//1 顺序循环模式 2随机模式

    //播放的游标
    private int playPoistion;

    //暂停或者开始
    private boolean isStart = true;

    private String mUsicId;

    public LiveMusicComponent1(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_live_music1;
    }

    @Override
    protected void init() {
        IsPaly = false;


        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.FunctionMsg, functionListener);


        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ExitRoom, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                release();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_CloseLive, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                release();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //一对一关闭
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOCloseLive, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                release();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });


        //一对一通话结束
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOCallEnd, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                //停止/恢复音频采集和播放
                release();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });


       LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.RoomInfoList, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (LiveConstants.isPalyMusic){
                    addToParent();
                    intiView();
                }
            }

           @Override
           public void onTagMsg(String tag, Object o) {

           }
        });
        //背景音樂
//        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_Music, new MsgListener.SimpleMsgListener() {
//            @Override
//            public void onMsg(Object o) {
               /* LiveMusicDialogFragment fragment = new LiveMusicDialogFragment();
                fragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveMusicDialogFragment");
                fragment.setActionListener(new LiveMusicDialogFragment.ActionListener() {
                    @Override
                    public void onChoose(String musicId) {
                        if (!IsPaly){
                            addToParent();
                            intiView();
//                            IsPaly = true;
                        }
//                        mUsicId = musicId;
//                        play(musicId);

                    }
                });*/
//                addToParent();
//                intiView();
//
//                play((Integer) o);
//            }
//        });

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LiveMusicChooice, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (!IsPaly) {
                    LiveConstants.isPalyMusic = true;
                    addToParent();
                    intiView();
                    IsPaly = true;
                }
                playPoistion = (Integer) o;

                play(playPoistion);

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });


        //向左滑，消失
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LIFT, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                setGone();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //向右滑，显示
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_RIGHT, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                setVisibility();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
    }

    public void intiView() {
        LiveMusic = (RoundedImageView) findViewById(R.id.LiveMusic);
        LiveMusic.setOnClickListener(this);
        LiveMusic_loop = (ImageView) findViewById(R.id.LiveMusic_loop);
        LiveMusic_loop.setOnClickListener(this);
        LiveMusic_last = (ImageView) findViewById(R.id.LiveMusic_last);
        LiveMusic_last.setOnClickListener(this);
        LiveMusic_start = (ImageView) findViewById(R.id.LiveMusic_start);
        LiveMusic_start.setOnClickListener(this);
        LiveMusic_next = (ImageView) findViewById(R.id.LiveMusic_next);
        LiveMusic_next.setOnClickListener(this);
        LiveMusic_voice = (ImageView) findViewById(R.id.LiveMusic_voice);
        LiveMusic_voice.setOnClickListener(this);
        LiveMusic_list = (ImageView) findViewById(R.id.LiveMusic_list);
        LiveMusic_list.setOnClickListener(this);
        LiveMusic_close = (ImageView) findViewById(R.id.LiveMusic_close);
        LiveMusic_close.setOnClickListener(this);

        mContentView.setOnTouchListener(this);
        if (mLiveMusicPlayer!=null){
            mLiveMusicPlayer = null;
        }
        mLiveMusicPlayer = new LiveMusicPlayer1();
        mLiveMusicPlayer.setLiveMusicPlayerCallBack(new LiveMusicPlayer1.LiveMusicPlayerCallBack() {
            @Override
            public void onCompletion() {

                next();
            }

            @Override
            public void onPrepared(String path) {
                LiveMusic_start.setBackgroundResource(R.mipmap.icon_music_start);

            }
        });

        final RelativeLayout music_Re = (RelativeLayout) findViewById(R.id.music_Re);
        screenWidth = DpUtil.getScreenWidth();//获取屏幕宽度
        screenHeight = DpUtil.getScreenHeight() - DpUtil.getStatusHeight();//屏幕高度-状态栏


        music_Re.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        startTime = System.currentTimeMillis();
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();

                        startx = (int) event.getX();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;

                        int top = v.getTop() + dy;


                        int left = v.getLeft() + dx;


                        if (top <= 0) {
                            top = 0;
                        }
                        if (top >= screenHeight - music_Re.getHeight()) {
                            top = screenHeight - music_Re.getHeight();
                        }
                        if (left >= screenWidth - music_Re.getWidth()) {
                            left = screenWidth - music_Re.getWidth();
                        }

                        if (left <= 0) {
                            left = 0;
                        }

                        FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(v.getWidth(), v.getHeight());
                        param.leftMargin = left;
                        param.topMargin = top;
                        v.setLayoutParams(param);
//            v.layout(left, top, left+v.getWidth(), top+v.getHeight());

                        v.postInvalidate();

                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();


                        break;
                    case MotionEvent.ACTION_UP:
                        endX1 = (int) event.getX();//记录结束位置
                        endY1 = (int) event.getRawY();


                        break;
                }
                return true;

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.LiveMusic_close) {//关闭
            release();
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

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        float x = e.getRawX();
        float y = e.getRawY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;
                float dy = y - mLastY;
                if (dx != 0) {
                    float targetX = mContentView.getTranslationX() + dx;
                    if (targetX < 0) {
                        targetX = 0;
                    }
                    int rightLimit = mParentWidth - mContentView.getWidth();
                    if (targetX > rightLimit) {
                        targetX = rightLimit;
                    }
                    mContentView.setTranslationX(targetX);
                }
                if (dy != 0) {
                    float targetY = mContentView.getTranslationY() + dy;
                    if (targetY < 0) {
                        targetY = 0;
                    }
                    int bottomLimit = mParentHeight - mContentView.getHeight();
                    if (targetY > bottomLimit) {
                        targetY = bottomLimit;
                    }
                    mContentView.setTranslationY(targetY);
                }
        }
        mLastX = x;
        mLastY = y;
        return false;

    }

    /**
     * 播放背景音乐
     */
    public void play(int poistion) {

        if (mLiveMusicPlayer != null) {
            mLiveMusicPlayer.play(String.valueOf(MusicDbManager1.getInstace().queryList().get(poistion).id), IsPaly);
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
    }

    /*
     *暂停音乐播放
     * */
    public void stop() {
        IsPaly = false;
        if (mLiveMusicPlayer != null) {
            mLiveMusicPlayer.stop();
        }
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
    public void voice() {
        MusicVoiceDialogFragment musicVoiceDialogFragment = new MusicVoiceDialogFragment();
        musicVoiceDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveMusicDialogFragment1");
        musicVoiceDialogFragment.setMusicVoiceDialogCallBack(new MusicVoiceDialogFragment.MusicVoiceDialogCallBack() {
            @Override
            public void onProgressChanged(int progress) {
                if (mLiveMusicPlayer!=null){
                    mLiveMusicPlayer.setVoice((float)progress/100,(float)progress/100);
                }
            }
        });
    }

    /*
     * 列表
     * */
    public void list() {
        LiveMusicDialogFragment1 musicDialogFragment1 = new LiveMusicDialogFragment1();
        musicDialogFragment1.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveMusicDialogFragment1");
    }


    public void release() {
        if (mLiveMusicPlayer != null) {
            mLiveMusicPlayer.end();
            mLiveMusicPlayer.release();
            mLiveMusicPlayer.end();
        }
        IsPaly = false;
        LiveConstants.isPalyMusic = false;
        removeFromParent();
    }


}
