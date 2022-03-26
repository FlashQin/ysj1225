package com.kalacheng.centercommon.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAPPAnchor;
import com.kalacheng.centercommon.R;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libuser.model.AppUsersAuth;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.view.FullScreenVideoView;
import com.kalacheng.commonview.activity.DynamicMakeActivity;
import com.xuantongyun.storagecloud.upload.UploadUtil;
import com.xuantongyun.storagecloud.upload.base.VideoUploadBean;
import com.xuantongyun.storagecloud.upload.base.VideoUploadCallback;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 上传认证视频
 */
@Route(path = ARouterPath.UpLoadAuthVideoActivity)
public class UpLoadAuthVideoActivity extends BaseActivity implements View.OnClickListener {
    @Autowired(name = ARouterValueNameConfig.VIDEO_PATH)
    public String videoPath;

    @Autowired(name = ARouterValueNameConfig.VideoType)
    public int VideoType;

    @Autowired(name = ARouterValueNameConfig.VIDEO_TIME_LONG)
    public long VideoTime;

    private FullScreenVideoView videoView;
    private TextView tvConfirm;
    private TextView tvRemake;
    private ImageView ivBack;

    private Dialog uploadDialog;
    private Disposable uploadVideoDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_auth_video);
        initView();
        initData();
    }

    private void initView() {
        videoView = findViewById(R.id.videoView);
        tvConfirm = findViewById(R.id.tvConfirm);
        tvRemake = findViewById(R.id.tvRemake);
        ivBack = findViewById(R.id.ivBack);

        tvConfirm.setOnClickListener(this);
        tvRemake.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    private void initData() {
        if (TextUtils.isEmpty(videoPath)) {
            return;
        }
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();
    }

    @Override
    public void onBackPressed() {
        if (videoView != null) {
            videoView.stopPlayback();
        }
        if (uploadVideoDisposable != null) {
            uploadVideoDisposable.dispose();
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvConfirm) {
            authThird();
        } else if (view.getId() == R.id.tvRemake) {
            DynamicMakeActivity.forward(this, 2, 5, true, 1001);
        } else if (view.getId() == R.id.ivBack) {
            onBackPressed();
        }
    }

    /**
     * 提交视频认证
     */
    private void authThird() {
        uploadDialog = DialogUtil.loadingDialog(mContext, com.kalacheng.videorecord.R.style.dialog2, com.kalacheng.util.R.layout.dialog_loading, false, false, getString(com.kalacheng.videorecord.R.string.uploading));
        uploadDialog.show();
        uploadVideoDisposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext(HuaWeiYun.getInstance().uploadVideo(new File(videoPath), HuaWeiYun.VIDEO_PATH));
                VideoUploadBean bean = new VideoUploadBean(new File(videoPath), null);
                UploadUtil.getInstance().uploadVideo(4, bean, new VideoUploadCallback() {
                    @Override
                    public void onSuccess(VideoUploadBean videoUploadBean) {
                        emitter.onNext(videoUploadBean.getResultVideoUrl());
                    }

                    @Override
                    public void onFailure() {
                        if (null != uploadDialog)
                            uploadDialog.dismiss();
                    }
                });
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String url) throws Exception {
                uploadDialog.dismiss();
                if (!TextUtils.isEmpty(url)) {
                    HttpApiAPPAnchor.authThird(url, new HttpApiCallBack<AppUsersAuth>() {
                        @Override
                        public void onHttpRet(int code, String msg, AppUsersAuth retModel) {
                            if (code == 1) {
                                ToastUtil.show("上传视频成功");
                                finish();
                            } else {
                                ToastUtil.show(msg);
                            }
                        }
                    });
                } else {
                    ToastUtil.show("上传视频失败,请稍后重试");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1001) {
                String path = data.getStringExtra(ARouterValueNameConfig.VIDEO_PATH);
                if (!TextUtils.isEmpty(path)) {
                    videoPath = path;
                    VideoType = data.getIntExtra(ARouterValueNameConfig.VideoType, 1);
                    VideoTime = data.getLongExtra(ARouterValueNameConfig.VIDEO_TIME_LONG, 0);
                    videoView.setVideoURI(Uri.parse(videoPath));
                    videoView.start();
                }
            }
        } else {
            videoView.start();
        }
    }
}
