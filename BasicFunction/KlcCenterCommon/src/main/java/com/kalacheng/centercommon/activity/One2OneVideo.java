package com.kalacheng.centercommon.activity;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.centercommon.R;
import com.kalacheng.util.view.FullScreenVideoView;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.activty.BaseActivity;

/**
 * 一对一视频预览
 */
@Route(path = ARouterPath.One2OneVideo)
public class One2OneVideo extends BaseActivity {
    @Autowired(name = ARouterValueNameConfig.VIDEO_URL)
    public String url;
    private FullScreenVideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_video);
        initData();
    }

    private void initData() {
        video = findViewById(R.id.video_view);
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Uri uri = Uri.parse(url);
        video.setVideoURI(uri);
        video.start();
        video.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                video.stopPlayback();
                finish();
                return false;
            }
        });
    }
}
