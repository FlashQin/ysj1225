package com.kalacheng.commonview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.bean.PictureChooseBean;
import com.kalacheng.commonview.databinding.DynamicmakeBinding;
import com.kalacheng.commonview.viewmodel.DynamicMakeViewModel;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.util.utils.BitmapUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;
import com.xuantongyun.storagecloud.camera.TakePictureLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态制作
 */
public class DynamicMakeActivity extends BaseMVVMActivity<DynamicmakeBinding, DynamicMakeViewModel> {

    public static void forward(Activity activity, int operationType, int maxDuration, boolean forbidFlip, int requestCode) {
        Intent intent = new Intent(activity, DynamicMakeActivity.class);
        intent.putExtra("operation_type", operationType);
        intent.putExtra("max_duration", maxDuration);
        intent.putExtra("forbid_flip", forbidFlip);
        intent.putExtra("requestCode", requestCode);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void forward(Fragment fragment, int operationType, int maxDuration, boolean forbidFlip, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), DynamicMakeActivity.class);
        intent.putExtra("operation_type", operationType);
        intent.putExtra("max_duration", maxDuration);
        intent.putExtra("forbid_flip", forbidFlip);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void forward(Fragment fragment, int operationType, int maxDuration, boolean forbidFlip, int requestCode, int maxPictureNum) {
        Intent intent = new Intent(fragment.getContext(), DynamicMakeActivity.class);
        intent.putExtra("operation_type", operationType);
        intent.putExtra("max_duration", maxDuration);
        intent.putExtra("forbid_flip", forbidFlip);
        intent.putExtra("max_picture_num", maxPictureNum);
        fragment.startActivityForResult(intent, requestCode);
    }

    //0 拍照及视频；1 拍照；2 视频；-1 选中视频
    private int mOperationType;
    //是否禁止翻转
    private boolean mForbidFlip;
    //录制视频最大时间
    private int mMaxDuration;
    //最大选择图片数量
    private int mMaxPictureNum;
    // requestCode
    private int requestCode;

    public static final int TYPE_ALL = 0;
    public static final int TYPE_PICTURE = 1;
    public static final int TYPE_VIDEO = 2;

    //最小录制时间5s
    public static final int MIN_DURATION = 5000;
    //最大录制时间15s
    public static final int MAX_DURATION = 15000;
    //录制视频的长度
    private long mDuration;
    //照片地址
    private String photoPath;
    //视频地址
    private String recorderPath;

    private TXVodPlayer mVodPlayer;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.dynamicmake;
    }

    @Override
    public void initData() {
        mOperationType = getIntent().getIntExtra("operation_type", 0);
        mForbidFlip = getIntent().getBooleanExtra("forbid_flip", false);
        mMaxPictureNum = getIntent().getIntExtra("max_picture_num", 9);
        int maxDuration = getIntent().getIntExtra("max_duration", 0);
        requestCode = getIntent().getIntExtra("requestCode", 0);
        if (maxDuration <= 0) {
            mMaxDuration = MAX_DURATION;
        } else if (maxDuration <= 5) {
            mMaxDuration = MIN_DURATION;
        } else if (maxDuration > 60) {
            mMaxDuration = 60000;
        } else {
            mMaxDuration = maxDuration * 1000;
        }

        initCameraRecord();
        initTxVideoView();
        if (mOperationType == TYPE_PICTURE) {
            binding.rbPicture.setChecked(true);
            binding.rgType.setVisibility(View.GONE);
            binding.btnUpload.setVisibility(View.GONE);
            if (requestCode == 1002){
                binding.btnUpload.setVisibility(View.VISIBLE);
            }
        } else if (mOperationType == TYPE_VIDEO) {
            binding.rbVideo.setChecked(true);
            binding.rgType.setVisibility(View.GONE);
            binding.btnUpload.setVisibility(View.GONE);
            if (requestCode == 1002){
                binding.btnUpload.setVisibility(View.VISIBLE);
            }
        } else if (mOperationType == -1) {
            mOperationType = 0;
            binding.rbVideo.setChecked(true);
        }
        if (mForbidFlip) {
            binding.btnCamera.setVisibility(View.GONE);
            binding.btnFlash.setVisibility(View.GONE);
        }

        ((RadioGroup) findViewById(R.id.rgType)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.rbPicture) {

                } else if (checkedId == R.id.rbVideo) {

                }
            }
        });

        binding.layoutPicturePreview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.takePictureLayout.onResume();
        if (binding.txVideoView.getVisibility() == View.VISIBLE && mVodPlayer != null) {
            mVodPlayer.resume();
        }
    }

    @Override
    protected void onPause() {
        if (binding.txVideoView.getVisibility() == View.VISIBLE && mVodPlayer != null) {
            mVodPlayer.pause();
        }
        if (binding.takePictureLayout.isRecording()) {
            reRecord();
        }
        binding.takePictureLayout.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (null != mVodPlayer) {
            mVodPlayer.stopPlay(false);
            mVodPlayer = null;
        }
        binding.takePictureLayout.release();
        super.onDestroy();
    }

    /**
     * 初始化录制器
     */
    private void initCameraRecord() {
        binding.takePictureLayout.initCameraRecord(getApplication());
        binding.takePictureLayout.setMaxDuration(mMaxDuration);
        binding.takePictureLayout.setOnTakePictureListener(new TakePictureLayout.OnTakePictureListener() {
            @Override
            public void onFlashing(boolean flashing) {
                binding.btnFlash.setSelected(flashing);
            }

            @Override
            public void onPhotoPath(String photoPath) {
                DynamicMakeActivity.this.photoPath = photoPath;
                binding.layoutPicturePreview.setVisibility(View.VISIBLE);
                binding.ivPicturePreview.setImageBitmap(BitmapUtil.getLocalBitmap(photoPath));
            }

            @Override
            public void onRecordStart() {
                binding.layoutRight.setVisibility(View.GONE);
                binding.rgType.setVisibility(View.GONE);
                binding.btnUpload.setVisibility(View.GONE);
            }

            @Override
            public void onRecordProgress(Long milliSecond) {
                binding.dynamicProgressView.setProgress((int) ((milliSecond * 10000) / mMaxDuration));
                if (binding.time != null) {
                    binding.time.setText(String.format("%.1f", milliSecond / 10f) + "s");
                }
                mDuration = milliSecond * 100;
                if (milliSecond * 100 >= MIN_DURATION) {
                    binding.btnNext.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onRecordComplete(String recordPath) {
                binding.dynamicProgressView.setProgress(100);
                binding.time.setText(String.format("%.1f", mMaxDuration / 1000f) + "s");
                binding.btnNext.setVisibility(View.VISIBLE);
                binding.btnDelete.setVisibility(View.VISIBLE);
                DynamicMakeActivity.this.recorderPath = recordPath;

                binding.txVideoView.setVisibility(View.VISIBLE);
                mVodPlayer.startPlay(recordPath);
            }

            @Override
            public void onActiveRecordComplete(String recordPath) {
                DynamicMakeActivity.this.recorderPath = recordPath;
            }
        });
    }

    /**
     * 初始化腾讯控件
     */
    private void initTxVideoView() {
        mVodPlayer = new TXVodPlayer(this);
        mVodPlayer.setPlayerView(binding.txVideoView);
        mVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
        mVodPlayer.setLoop(true);
        mVodPlayer.setVodListener(new ITXVodPlayListener() {
            @Override
            public void onPlayEvent(TXVodPlayer txVodPlayer, int i, Bundle bundle) {

            }

            @Override
            public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

            }
        });
    }

    /**
     * 点击事件
     */
    public void recordClick(View v) {
        int i = v.getId();//录制
        if (i == R.id.btn_start_record) {
            if (binding.rgType.getCheckedRadioButtonId() == R.id.rbPicture) {
                binding.takePictureLayout.takePhoto();
            } else {
                if (TextUtils.isEmpty(recorderPath)) {
                    binding.takePictureLayout.startRecord();
                }
            }
        } else if (i == R.id.btn_camera) {
            binding.takePictureLayout.changeCameraFacing();
        } else if (i == R.id.btn_flash) {
            binding.takePictureLayout.clickFlash();
        } else if (i == R.id.btn_upload) {
            clickUpload();
        } else if (i == R.id.tvTakePictureCancel) {
            binding.layoutPicturePreview.setVisibility(View.GONE);
        } else if (i == R.id.tvTakePictureConfirm) {
            pictureConfirm();
        } else if (i == R.id.btn_next) {
            binding.takePictureLayout.activeCompleteRecord();
            if (null != recorderPath && mDuration != 0) {
                onVideoFinish(recorderPath, mDuration);
            }
        } else if (i == R.id.btn_delete) {
            if (binding.txVideoView.getVisibility() == View.VISIBLE) {
                binding.txVideoView.setVisibility(View.GONE);
                mVodPlayer.stopPlay(false);
            }
            reRecord();
        } else if (i == R.id.btn_back) {
            onBackPressed();
        }
    }

    /**
     * 拍照确认
     */
    private void pictureConfirm() {
        if (photoPath == null)
            return;
        ArrayList<PictureChooseBean> list = new ArrayList<>();
        PictureChooseBean pictureChooseBean = new PictureChooseBean();
        pictureChooseBean.path = photoPath;
        pictureChooseBean.num = 0;
        list.add(pictureChooseBean);

        onPictureFinish(list);
    }

    /**
     * 点击上传，选择本地视频
     */
    private void clickUpload() {
        if (binding.rgType.getCheckedRadioButtonId() == R.id.rbPicture) {
            Intent intent = new Intent(mContext, PictureChooseActivity.class);
            intent.putExtra(PictureChooseActivity.PICTURE_CHOOSE_NUM, mMaxPictureNum);
            startActivityForResult(intent, 1);
        } else {
            Intent intent = new Intent(mContext, VideoChooseActivity.class);
            intent.putExtra(ARouterValueNameConfig.VIDEO_DURATION, mMaxDuration);
            startActivityForResult(intent, 0);
        }
    }

    /**
     * 重新录制
     */
    private void reRecord() {
        recorderPath = "";
        mDuration = 0;
        binding.time.setText("");
        binding.dynamicProgressView.setProgress(0);
        binding.btnNext.setVisibility(View.INVISIBLE);
        binding.btnDelete.setVisibility(View.INVISIBLE);
        binding.layoutRight.setVisibility(View.VISIBLE);
        if (mOperationType == TYPE_ALL) {
            binding.rgType.setVisibility(View.VISIBLE);
            binding.btnUpload.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        List<Integer> list = new ArrayList<>();
        if (mDuration > 0) {
            list.add(R.string.video_re_record);
        }
        list.add(R.string.video_exit);
        DialogUtil.showStringArrayDialog(mContext, list.toArray(new Integer[list.size()]), new DialogUtil.StringArrayDialogCallback() {
            @Override
            public void onItemClick(String text, int tag) {
                if (tag == R.string.video_re_record) {
                    if (binding.takePictureLayout.isRecording()) {
                        binding.takePictureLayout.reRecord();
                    }
                    reRecord();
                    if (binding.txVideoView.getVisibility() == View.VISIBLE) {
                        binding.txVideoView.setVisibility(View.GONE);
                        mVodPlayer.stopPlay(false);
                    }
                } else if (tag == R.string.video_exit) {
                    setResult(RESULT_CANCELED);
                    DynamicMakeActivity.super.onBackPressed();
                }
            }
        });

    }

    /**
     * 选择本地视频的回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                onVideoFinish(data.getStringExtra(ARouterValueNameConfig.VIDEO_PATH), data.getLongExtra(ARouterValueNameConfig.VIDEO_DURATION, 0));
            } else if (requestCode == 1) {
                onPictureFinish(data.<PictureChooseBean>getParcelableArrayListExtra(ARouterValueNameConfig.PICTURE_LIST));
            }
        }
    }

    /**
     * 图片完成
     */
    private void onPictureFinish(ArrayList<PictureChooseBean> pictureChooseBeans) {
        Intent intent = new Intent();
        intent.putExtra(ARouterValueNameConfig.DYNAMIC_RESULT_TYPE, 0);
        intent.putParcelableArrayListExtra(ARouterValueNameConfig.PICTURE_LIST, pictureChooseBeans);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 视频完成
     */
    private void onVideoFinish(String videoPath, long videoDuration) {
        Intent intent = new Intent();
        intent.putExtra(ARouterValueNameConfig.DYNAMIC_RESULT_TYPE, 1);
        intent.putExtra(ARouterValueNameConfig.VIDEO_PATH, videoPath);
        intent.putExtra(ARouterValueNameConfig.VIDEO_TIME_LONG, videoDuration);
        intent.putExtra(ARouterValueNameConfig.VideoType, 1);
        setResult(RESULT_OK, intent);
        finish();
    }
}
