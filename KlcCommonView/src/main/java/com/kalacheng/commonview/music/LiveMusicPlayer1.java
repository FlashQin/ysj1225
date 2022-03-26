package com.kalacheng.commonview.music;

import android.media.MediaPlayer;

import com.kalacheng.frame.config.APPProConfig;

import java.io.IOException;

public class LiveMusicPlayer1 {

    private MediaPlayer mediaPlayer;
    private String mMusicPath;


    public void play(String musicId, boolean isPaly) {

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            //静音
            mediaPlayer.setVolume(0, 0);
        }

        mMusicPath = APPProConfig.MUSIC_PATH + musicId + ".mp3";

        if (isPaly) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            mediaPlayer = new MediaPlayer();
        }
        //播放介绍
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                liveMusicPlayerCallBack.onCompletion();
            }
        });
        //准备好资源
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                liveMusicPlayerCallBack.onPrepared(mMusicPath);
            }
        });
        try {
            // 切歌之前先重置，释放掉之前的资源
            mediaPlayer.reset();
            // 设置播放源
            mediaPlayer.setDataSource(mMusicPath);
            // 开始播放前的准备工作，加载多媒体资源，获取相关信息
            mediaPlayer.prepare();
            // 开始播放
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = null;
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void start() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void end() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void setVoice(float leftVolume, float rightVolume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(leftVolume, rightVolume);
        }
    }

    private LiveMusicPlayerCallBack liveMusicPlayerCallBack;

    public void setLiveMusicPlayerCallBack(LiveMusicPlayerCallBack callBack) {
        this.liveMusicPlayerCallBack = callBack;
    }

    public interface LiveMusicPlayerCallBack {
        public void onCompletion();

        public void onPrepared(String path);

    }
}
