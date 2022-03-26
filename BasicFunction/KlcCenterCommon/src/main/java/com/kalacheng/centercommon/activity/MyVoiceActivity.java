package com.kalacheng.centercommon.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.frame.config.APPProConfig;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.centercommon.R;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.FileUtil;
import com.kalacheng.util.utils.Rx2Timer;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 我的声音
 */
@Route(path = ARouterPath.MyVoiceActivity)
public class MyVoiceActivity extends BaseActivity implements View.OnClickListener {
    ImageView ivStart, ivRepeat, ivVoice;
    // 录音类
    private MediaRecorder mediaRecorder;
    // 以文件的形式保存
    private File recordFile = new File(APPProConfig.VOICE_PATH, "record.amr");
    private RecordPlayer player;
    Disposable timeDisposable;
    private TextView tvTime;
    boolean isPlay;
    long time;
    Rx2Timer rx2Timer;
    private ImageView ivSign2, ivSign3;
    private ScaleAnimation animation1, animation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_voice);
        initView();
        initData();
    }

    private void initView() {
        TextView tvSign = findViewById(R.id.tv_sign);
        TextView textView = findViewById(R.id.titleView);
        textView.setText("我的声音");
        ivSign2 = findViewById(R.id.iv_sign2);
        ivSign3 = findViewById(R.id.iv_sign3);
        tvTime = findViewById(R.id.tv_time);
        tvSign.setText("  描述一下我吧" + "\n" + "能吸引更多用户哦");
        ivStart = (ImageView) findViewById(R.id.iv_start);
        ivRepeat = (ImageView) findViewById(R.id.iv_repeat);
        ivVoice = (ImageButton) findViewById(R.id.iv_voice);
        ivStart.setOnClickListener(this);
        ivRepeat.setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        animation1 = new ScaleAnimation(1, 1.1f, 1, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, 1, 0.5f);
        animation1.setRepeatCount(ScaleAnimation.INFINITE);
        animation1.setDuration(1500);
        animation2 = new ScaleAnimation(1, 1.2f, 1, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, 1, 0.5f);
        animation2.setRepeatCount(ScaleAnimation.INFINITE);
        animation2.setDuration(1500);
    }

    private void initData() {
        player = new RecordPlayer(MyVoiceActivity.this);

        ivVoice.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startRecording();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (time < 3) {
                            ToastUtil.show("录音时间小于3秒");
                            if (null != timeDisposable)
                                timeDisposable.dispose();
                            tvTime.setText("00:00:00");
                            ivSign2.clearAnimation();
                            ivSign3.clearAnimation();
                            return true;
                        }
                        stopRecording();
                        showPlayVoice();
                        break;
                }
                return true;
            }
        });
    }

    private void showPlayVoice() {
        ivSign2.clearAnimation();
        ivSign3.clearAnimation();
        findViewById(R.id.rl_voice).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_next).setVisibility(View.VISIBLE);
        ivVoice.setVisibility(View.GONE);
        if (null != timeDisposable)
            timeDisposable.dispose();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_start) {
            if (isPlay) {
                player.pausePalyer();
                rx2Timer.pause();
                ivStart.setImageResource(R.mipmap.icon_my_voice_play);
            } else if (!isPlay) {
                ivStart.setImageResource(R.mipmap.icon_my_voice_puase);
                player.playRecordFile(recordFile);
            }
            isPlay = !isPlay;
        } else if (view.getId() == R.id.iv_repeat) {
            stopRecording();
            player.stopPalyer();
            if (null != timeDisposable)
                timeDisposable.dispose();
            if (null != rx2Timer) {
                rx2Timer.stop();
                rx2Timer = null;
            }
            tvTime.setText("00:00:00");
            findViewById(R.id.rl_voice).setVisibility(View.GONE);
            findViewById(R.id.btn_next).setVisibility(View.GONE);
            ivVoice.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.btn_next) {
            stopRecording();
            player.stopPalyer();
            Intent intent = new Intent();
            intent.putExtra(ARouterValueNameConfig.VOICE_PATH, recordFile.getAbsolutePath());
            setResult(RESULT_OK, intent);
            finish();
        }
    }


    private void startRecording() {
        ivSign2.startAnimation(animation1);
        ivSign3.startAnimation(animation2);
        time = 0;
        tvTime.setText("00:00:01");
        timeDisposable = Observable.interval(1000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                if (aLong + 1 < 10)
                    tvTime.setText("00:00:0" + (aLong + 1));
                else
                    tvTime.setText("00:00:" + (aLong + 1));
                time = aLong;
            }
        });
        mediaRecorder = new MediaRecorder();
        // 判断，若当前文件已存在，则删除
        if (recordFile.exists()) {
            recordFile.delete();
        }
        FileUtil.createFolder(APPProConfig.VOICE_PATH);
        try {
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            mediaRecorder.setOutputFile(recordFile.getAbsolutePath());
            // 准备好开始录音
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (recordFile != null) {
            try {
                if (null != mediaRecorder) {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;
                }
            } catch (RuntimeException e) {
                if (null != mediaRecorder) {
                    mediaRecorder.reset();
                    mediaRecorder.release();
                    mediaRecorder = null;
                }
            }

        }
    }


    public class RecordPlayer {
        private MediaPlayer mediaPlayer;
        private Context mcontext;

        public RecordPlayer(Context context) {
            this.mcontext = context;
        }

        // 播放录音文件
        public void playRecordFile(File file) {
            if (file.exists() && file != null) {
                if (mediaPlayer == null) {
                    Uri uri = Uri.fromFile(file);
                    mediaPlayer = MediaPlayer.create(mcontext, uri);
                }
                mediaPlayer.start();
                if (null == rx2Timer) {
                    rx2Timer = Rx2Timer.builder()
                            .take((int) time) //default is 60
                            .onEmit(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    if (time - aLong < 10)
                                        tvTime.setText("00:00:0" + (time - aLong));
                                    else
                                        tvTime.setText("00:00:" + (time - aLong));
                                }
                            })
                            .onComplete(new Action() {
                                @Override
                                public void run() throws Exception {

                                }
                            })
                            .build();
                    rx2Timer.start();
                } else {
                    rx2Timer.resume();
                }
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (rx2Timer != null) {
                            rx2Timer.stop();
                            rx2Timer = null;
                        }
                        isPlay = false;
                        ivStart.setImageResource(R.mipmap.icon_my_voice_play);
                    }
                });
            }
        }

        // 暂停播放录音
        public void pausePalyer() {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }

        }


        // 停止播放录音
        public void stopPalyer() {
            // 这里不调用stop()，调用seekto(0),把播放进度还原到最开始
            if (null != mediaPlayer && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                mediaPlayer.reset();
                mediaPlayer.stop();
                mediaPlayer = null;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRecording();
        player.stopPalyer();
        ivSign2.clearAnimation();
        ivSign3.clearAnimation();
        if (null != timeDisposable)
            timeDisposable.dispose();
        if (null != rx2Timer) {
            rx2Timer.stop();
            rx2Timer = null;
        }
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }
}
