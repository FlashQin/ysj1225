package com.kalacheng.livecommon.music;

import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import com.kalacheng.frame.config.APPProConfig;

import java.io.IOException;
import java.util.List;

/**
 * Created by cxf on 2018/10/22.
 */

public class LiveMusicPlayer implements MediaPlayer.OnPreparedListener, ValueAnimator.AnimatorUpdateListener {

    private MediaPlayer mPlayer;//MediaPlayer用来获取音频时长
    private boolean mPaused;
    private volatile List<LrcBean> mLrcList;
    private ActionListener mActionListener;
    private int mLrcIndex;//当前歌词的索引
    private ValueAnimator mAnimator;
    private String mMusicPath;

    public LiveMusicPlayer() {
        mPlayer = new MediaPlayer();
        mPlayer.setOnPreparedListener(this);
        mAnimator = ValueAnimator.ofFloat();
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.setRepeatCount(-1);
        mAnimator.addUpdateListener(this);
    }

    public void play(String musicId, boolean isPaly) {
        if (TextUtils.isEmpty(musicId)) {
            return;
        }

        if (null != mLrcList)
            mLrcList.clear();
        mLrcList = LrcParser.getLrcListByMusicId(musicId);

        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
        }

        try {
            mMusicPath = APPProConfig.MUSIC_PATH + musicId + ".mp3";

            if (isPaly) {
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
                mPlayer = new MediaPlayer();
            }
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Log.i("aaa", "onCompletion");
                }
            });
//            mPlayer.reset();
            mPlayer.reset();
            mPlayer.setDataSource(mMusicPath);
            mPlayer.prepare();
            // 开始播放
            mPlayer.start();
            mPlayer.setOnPreparedListener(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mActionListener != null) {
            mActionListener.onPrepareSuccess(mMusicPath);
        }
        long duration = mp.getDuration();
        boolean hasLrc = mLrcList != null && mLrcList.size() > 0;
        if (mActionListener != null) {
            mActionListener.onParseLrcResult(hasLrc);
        }
        mLrcIndex = -1;
        if (mAnimator != null) {
            mAnimator.cancel();
        }
        if (hasLrc) {
            for (int i = 0, size = mLrcList.size(); i < size; i++) {
                LrcBean bean = mLrcList.get(i);
                bean.setIndex(i);
                if (i == size - 1) {
                    bean.setEndTime(duration);
                } else {
                    bean.setEndTime(mLrcList.get(i + 1).getStartTime());
                }
            }
            if (mAnimator != null) {
                mAnimator.setFloatValues(0, duration);
                mAnimator.setDuration(duration);
                mAnimator.start();
            }
        }
    }

    /**
     * 获取歌词
     */
    private LrcBean getLrc(long time) {
        if (mLrcIndex >= 0 && mLrcList != null && mLrcIndex < mLrcList.size()) {
            LrcBean curLrcBean = mLrcList.get(mLrcIndex);
            if (time >= curLrcBean.getStartTime() && time <= curLrcBean.getEndTime()) {
                float progress = (time - curLrcBean.getStartTime()) / curLrcBean.getDuration();
                if (progress < 0.01f) {
                    progress = 0;
                }
                curLrcBean.setProgress(progress);
                return curLrcBean;
            }
        }
        if (mLrcList != null) {
            for (LrcBean bean : mLrcList) {
                if (time >= bean.getStartTime() && time <= bean.getEndTime()) {
                    float progress = (time - bean.getStartTime()) / bean.getDuration();
                    if (progress < 0.01f) {
                        progress = 0;
                    }
                    bean.setProgress(progress);
                    return bean;
                }
            }
        }
        return null;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        if (mActionListener != null) {
            float v = (float) animation.getAnimatedValue();
            LrcBean bean = getLrc((long) v);
            if (bean != null) {
                if (mLrcIndex != bean.getIndex()) {
                    mLrcIndex = bean.getIndex();
                    mActionListener.onLrcChanged(bean.getLrc());
                }
                mActionListener.onLrcProgressChanged(bean.getProgress());
            }
        }
    }


    public void pause() {
        if (mPlayer != null) {
            mPlayer.pause();
        }
        if (mAnimator != null) {
            mAnimator.pause();
        }
        mPaused = true;
    }

    public void resume() {
        if (mPlayer != null) {
            mPlayer.reset();
        }
        if (mPaused) {
            if (mAnimator != null) {
                mAnimator.resume();
            }
            mPaused = false;
        }
    }

    public void release() {
        mActionListener = null;
        if (mPlayer != null) {
            mPlayer.release();
        }
        if (mAnimator != null) {
            mAnimator.cancel();
        }
        mPlayer = null;
    }

    public void stop() {
        if (mPlayer != null) {
            mPlayer.stop();
        }
        if (mAnimator != null) {
            mAnimator.cancel();
        }
    }


    public interface ActionListener {

        void onPrepareSuccess(String path);

        void onParseLrcResult(boolean success);

        void onLrcChanged(String lrc);

        void onLrcProgressChanged(float progress);

        void onCompletion();
    }

    public ActionListener getActionListener() {
        return mActionListener;
    }

    public void setActionListener(ActionListener actionListener) {
        mActionListener = actionListener;
    }
}
