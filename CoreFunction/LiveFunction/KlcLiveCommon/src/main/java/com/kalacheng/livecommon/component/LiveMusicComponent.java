package com.kalacheng.livecommon.component;


import android.content.Context;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.kalacheng.base.base.BaseViewHolder;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.base.activty.ActivityLife;
import com.kalacheng.commonview.music.dialog.LiveMusicDialogFragment1;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.music.LiveMusicDialogFragment;
import com.kalacheng.livecommon.music.LiveMusicPlayer;
import com.kalacheng.livecommon.music.LrcTextView;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.StringUtil;
import com.xuantongyun.livecloud.protocol.PulicUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class LiveMusicComponent extends BaseViewHolder implements View.OnClickListener, View.OnTouchListener {
    private LrcTextView mLrcTextView;//歌词控件
    private TextView mBtnEnd;//关闭按钮
    private TextView mTimeTextView;//时间
    private LiveMusicPlayer mLiveMusicPlayer;
    private int mParentWidth;
    private int mParentHeight;
    private float mLastX;
    private float mLastY;
    private long mTime;
    private boolean mPaused;
    private MsgListener.SimpleMsgListener functionListener;
    private Disposable timeDisposable;

    private boolean IsPaly = false;
    private boolean stop = false;
    private String music = "";

    //小框口拖动
    private int screenWidth, screenHeight;
    private int lastX, lastY;
    private int endX1, endY1;
    private int startx;
    private long startTime;
    private long endTime;
    private boolean isclick;
    private String url = "";

    private LiveMusicDialogFragment1 musicDialogFragment1;

    public LiveMusicComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_live_music;
    }

    @Override
    protected void init() {
        IsPaly = false;


        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.FunctionMsg, functionListener);

        ActivityLife.getInstance().addActivityResume(new MsgListener.NullMsgListener() {
            @Override
            public void onMsg() {
                resume();
            }
        });
        ActivityLife.getInstance().addActivityPause(new MsgListener.NullMsgListener() {
            @Override
            public void onMsg() {
                pause();
            }
        });

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
                if (LiveConstants.isInsideRoomType == 0){
                    release();
                }
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
        //背景音樂
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_Music, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (ConfigUtil.getBoolValue(R.bool.useMusicOld)) {
                    LiveMusicDialogFragment fragment = new LiveMusicDialogFragment();
                    fragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveMusicDialogFragment");
                    fragment.setActionListener(new LiveMusicDialogFragment.ActionListener() {
                        @Override
                        public void onChoose(String musicId) {
                            if (timeDisposable != null) {
                                timeDisposable.dispose();
                            }
                            if (!IsPaly) {
                                addToParent();
                                intiView();
                                IsPaly = true;
                            }
                            music = musicId;
                            play(musicId);

                        }
                    });
                }else {
                    if (musicDialogFragment1 != null) {
                        musicDialogFragment1.dismiss();
                        musicDialogFragment1 = null;
                    }
                    musicDialogFragment1 = new LiveMusicDialogFragment1((FragmentActivity) mContext);
                    musicDialogFragment1.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveMusicDialogFragment1");
                }
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

        //主播BGM 背景音乐
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOAudienceBGM, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                try {
                    if (LiveConstants.IDENTITY == LiveConstants.IDENTITY.ANCHOR) {
                        if ((int) o == 1 && !url.isEmpty()) {
                            resume();
//                            TTTRtcEngine.getInstance().resumeAudioMixing();
                        } else {
                            pause();
//                            TTTRtcEngine.getInstance().pauseAudioMixing();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
    }

    public void intiView() {
        mBtnEnd = (TextView) findViewById(R.id.btn_end);
        mBtnEnd.setOnClickListener(this);

        mLrcTextView = (LrcTextView) findViewById(R.id.lrc);
        mTimeTextView = (TextView) findViewById(R.id.time);
        mContentView.setOnTouchListener(this);
        mLiveMusicPlayer = new LiveMusicPlayer();

        mLiveMusicPlayer.setActionListener(new LiveMusicPlayer.ActionListener() {
            @Override
            public void onPrepareSuccess(String path) {
                if (!TextUtils.isEmpty(path)) {
                    url = path;
                    PulicUtils.getInstance().startBgm(path);
                }

                mTime = 0;
                if (mTimeTextView != null) {
                    mTimeTextView.setText("00:00");
                }
                timeDisposable = Observable.interval(1000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
//                        mTime = mTime + aLong;
                        mTimeTextView.setText(StringUtil.getDurationText(aLong * 1000));
                    }
                });
            }

            @Override
            public void onParseLrcResult(boolean success) {
                if (!success && mLrcTextView != null) {
                    mLrcTextView.setText(R.string.music_lrc_not_found);
                }
            }


            @Override
            public void onLrcChanged(String lrc) {
                if (mLrcTextView != null) {
                    mLrcTextView.setText(lrc);
                }
            }

            @Override
            public void onLrcProgressChanged(float progress) {
                if (mLrcTextView != null) {
                    mLrcTextView.setProgress(progress);
                }
            }

            @Override
            public void onCompletion() {

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
        if (v.getId() == R.id.btn_end) {
            mLiveMusicPlayer.stop();
            stop();
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
    public void play(String musicId) {
        if (TextUtils.isEmpty(musicId)) {
            return;
        }
        if (mLiveMusicPlayer != null) {
            mLiveMusicPlayer.play(musicId, IsPaly);
        }
    }


    public void pause() {
        mPaused = true;
        if (mLiveMusicPlayer != null) {
            mLiveMusicPlayer.pause();
        }
    }

    public void resume() {
        if (mPaused) {
            mPaused = false;
            if (mLiveMusicPlayer != null) {
                mLiveMusicPlayer.resume();
            }
        }
    }

    public void release() {
        if (mLiveMusicPlayer != null) {
            mLiveMusicPlayer.release();
        }
        stop();
    }

    public void stop() {
        IsPaly = false;
        if (null != timeDisposable)
            timeDisposable.dispose();
        removeFromParent();
        PulicUtils.getInstance().stopBgm();
    }
}
