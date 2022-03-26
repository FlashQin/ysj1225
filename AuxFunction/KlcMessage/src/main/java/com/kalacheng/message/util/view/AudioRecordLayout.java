package com.kalacheng.message.util.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kalacheng.frame.config.APPProConfig;
import com.kalacheng.message.R;
import com.kalacheng.message.util.MediaRecordUtil;
import com.kalacheng.util.utils.DateUtil;

import java.io.File;

public class AudioRecordLayout extends FrameLayout {

    final String TAG = "AudioRecordLayout";

    Context context;
    View view;
    TextView timeTv;
    ImageButton recordBtn;
    Button deleteBtn;
    int[] ints = new int[2];

    public static final int CHANGE_TIPS_TIMER_INTERVAL = 100;
    Handler handler;
    Runnable runnable;
    long time = 0;

    MediaRecordUtil mediaRecordUtil;
    File mRecordVoiceFile;

    OnAudioComplete onAudioComplete;

    public AudioRecordLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public AudioRecordLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AudioRecordLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(Context context) {
        this.context = context;

        mediaRecordUtil = new MediaRecordUtil();

        view = LayoutInflater.from(context).inflate(R.layout.layout_audio_record, this, false);
        timeTv = view.findViewById(R.id.timeTv);

        deleteBtn = view.findViewById(R.id.deleteBtn);
        deleteBtn.setTag(false);

        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                time += 100;
                String str = DateUtil.formatDuring2(time);
                timeTv.setText(str);
                handler.postDelayed(this, CHANGE_TIPS_TIMER_INTERVAL);
            }
        };

        recordBtn = view.findViewById(R.id.recordBtn);
        recordBtn.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        deleteBtn.setVisibility(VISIBLE);
                        startTimer();
                        mediaRecordUtil.startRecord(onCreateAudioFile().getAbsolutePath());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        getL(event.getRawX(), event.getRawY());
                        break;
                    case MotionEvent.ACTION_UP:
                        deleteBtn.setVisibility(INVISIBLE);
                        long duration = mediaRecordUtil.stopRecord();
                        if (time >= 1000) {
                            if (!(Boolean) deleteBtn.getTag()) {
                                if (onAudioComplete != null) {
                                    onAudioComplete.onAudioComlete(mRecordVoiceFile, duration / 1000);
                                }
                            } else {
                                onDeleteFile();
                            }
                        } else {
                            onDeleteFile();
                        }
                        endTimer();
                        break;
                }

                return false;
            }
        });

        addView(view);
    }

    public void setOnAudioComplete(OnAudioComplete onAudioComplete) {
        this.onAudioComplete = onAudioComplete;
    }

    /**
     * 判断是否移动到删除键上
     */
    private void getL(float x, float y) {
        deleteBtn.getLocationOnScreen(ints);
        int xWidth = ints[0] + deleteBtn.getMeasuredWidth();
        int yHeight = ints[1] + deleteBtn.getMeasuredHeight();
        boolean tag = (boolean) deleteBtn.getTag();
        if (x > ints[0] && x < xWidth && y > ints[1] && y < yHeight) {
//            L.e(TAG,"》》》》》》》》》》》》》》》》 到删除按钮了");
            if (!tag) {
                deleteBtn.setTag(true);
                deleteBtn.setBackgroundResource(R.drawable.bg_message_count);
            }
        } else {
            if (tag) {
                deleteBtn.setTag(false);
                deleteBtn.setBackgroundResource(R.drawable.bg_delete_btn);
            }
        }
    }

    /**
     * 开始计时
     */
    private void startTimer() {
        handler.postDelayed(runnable, CHANGE_TIPS_TIMER_INTERVAL);
    }

    /**
     * 计时结束
     */
    private void endTimer() {
        handler.removeCallbacks(runnable);
        time = 0;
        timeTv.setText("00:00");
    }

    /**
     * 创建音频文件
     */
    private File onCreateAudioFile() {
        File dir = new File(APPProConfig.MUSIC_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        mRecordVoiceFile = new File(dir, new DateUtil().getDateToFormat("yyyyMMddHHmmssSSS") + ".m4a");
        return mRecordVoiceFile;
    }

    /**
     * 删除音频文件
     */
    private void onDeleteFile() {
        if (mRecordVoiceFile != null && mRecordVoiceFile.exists()) {
            mRecordVoiceFile.delete();
        }
        mRecordVoiceFile = null;
    }

    /**
     * 释放资源
     */
    public void release() {
        if (mediaRecordUtil != null) {
            mediaRecordUtil.release();
        }
        onDeleteFile();
    }

    public interface OnAudioComplete {

        void onAudioComlete(File audioFile, long duration);

    }

}
