package com.kalacheng.message.util;

import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.text.TextUtils;

import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.message.R;
import com.kalacheng.message.util.view.MyImageView;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.ToastUtil;

import java.io.IOException;

/**
 * Created by cxf on 2018/7/19.
 * 播放语音消息的工具类
 */

public class MediaPlayerUtil {

    MediaPlayer mediaPlayer;
    MyImageView mImageView;
    String url;
    final AnimationDrawable animationDrawable;
    final AnimationDrawable animationDrawableB;
    private static volatile MediaPlayerUtil mediaPlayerUtil;

    private MediaPlayerUtil() {
        Resources resources = BaseApplication.getInstance().getResources();
        animationDrawable = new AnimationDrawable();
        animationDrawable.setOneShot(false);
        animationDrawable.addFrame(resources.getDrawable(R.mipmap.voice1), 100);
        animationDrawable.addFrame(resources.getDrawable(R.mipmap.voice2), 100);
        animationDrawable.addFrame(resources.getDrawable(R.mipmap.voice3), 100);
        animationDrawable.addFrame(resources.getDrawable(R.mipmap.voice4), 100);
        animationDrawable.addFrame(resources.getDrawable(R.mipmap.voice5), 100);

        animationDrawableB = new AnimationDrawable();
        animationDrawableB.setOneShot(false);
        animationDrawableB.addFrame(resources.getDrawable(R.mipmap.voice1_b), 100);
        animationDrawableB.addFrame(resources.getDrawable(R.mipmap.voice2_b), 100);
        animationDrawableB.addFrame(resources.getDrawable(R.mipmap.voice3_b), 100);
        animationDrawableB.addFrame(resources.getDrawable(R.mipmap.voice4_b), 100);
        animationDrawableB.addFrame(resources.getDrawable(R.mipmap.voice5_b), 100);
    }

    public static MediaPlayerUtil getMediaPlayer() {
        if (mediaPlayerUtil == null) {
            synchronized (MediaPlayerUtil.class) {
                if (mediaPlayerUtil == null) {
                    mediaPlayerUtil = new MediaPlayerUtil();
                }
            }
        }
        return mediaPlayerUtil;
    }

    public void player(String url, final MyImageView imageView) {
        if (TextUtils.isEmpty(url) || !url.startsWith("http")) {
            ToastUtil.show("播放失败 " + url);
            return;
        }

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    L.e("播放完毕");
                    stopPlayer();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    L.e("播放异常---> what = " + what + "extra = " + extra);
                    return false;
                }
            });
        }
        if (mediaPlayer.isPlaying()) {
            if (TextUtils.equals(this.url, url)) {
                L.e("同一语音停止播放");
                stopPlayer();
                return;
            } else {
                L.e("语音停止播放");
                stopPlayer();
            }
        }
        try {
            this.url = url;
            L.e("设置Url = " + url);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    L.e("开始播放");
                    if (imageView.isSelf()) {
//                        imageView.setBackground(animationDrawable);
//                        animationDrawable.start();
                        imageView.setBackground(animationDrawableB);
                        animationDrawableB.start();
                    } else {
                        imageView.setBackground(animationDrawableB);
                        animationDrawableB.start();
                    }
                    mImageView = imageView;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        if (mImageView.isSelf()) {
//            if (animationDrawable != null && animationDrawable.isRunning()) {
//                animationDrawable.stop();
//                mImageView.setBackgroundResource(0);
//                mImageView.setBackgroundResource(R.mipmap.voice1);
//            }
            if (animationDrawableB != null && animationDrawableB.isRunning()) {
                animationDrawableB.stop();
                mImageView.setBackgroundResource(0);
                mImageView.setBackgroundResource(R.mipmap.voice1_b);
            }
        } else {
            if (animationDrawableB != null && animationDrawableB.isRunning()) {
                animationDrawableB.stop();
                mImageView.setBackgroundResource(0);
                mImageView.setBackgroundResource(R.mipmap.voice1_b);
            }
        }

    }

    public void release() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
            mImageView = null;
        }
    }


}
