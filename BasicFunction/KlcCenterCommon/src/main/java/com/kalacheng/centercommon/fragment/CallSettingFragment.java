package com.kalacheng.centercommon.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.activity.MyVoiceActivity;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.libuser.model.CfgPayCallOneVsOne;
import com.kalacheng.commonview.activity.VideoChooseActivity;
import com.kalacheng.util.utils.BitmapUtil;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.ImageResultCallback;
import com.kalacheng.util.utils.PermissionsUtil;
import com.kalacheng.commonview.utils.ProcessImageUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.commonview.activity.DynamicMakeActivity;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBack;
import com.xuantongyun.storagecloud.upload.UploadUtil;
import com.xuantongyun.storagecloud.upload.base.PictureUploadCallback;
import com.xuantongyun.storagecloud.upload.base.VideoUploadBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CallSettingFragment extends BaseFragment implements View.OnClickListener {
    private ImageView ivSwitch;
    TextView tvState, tvLivePrice, tvVoicePrice, tvChatImg, tvChatVideo, tvChatVoice;
    private ProcessImageUtil mImageUtil;
    PermissionsUtil mProcessResultUtil;
    ImageView ivChatImg, ivChatVideo, ivChatVoice, ivState, ivVoicePlay, ivVideoPlay;
    public String imgUrl, voiceUrl, videoUrl;
    public double voiceCoin, videoCoin;
    public int liveState;
    Disposable uploadImgDisposable, uploadVoiceDisposable, uploadVideoDisposable, imgThumbDisposable;
    private Dialog uploadDialog;
    public static int TACK_RECORD = 10000;
    public static int TACK_VIDEO = 10001;
    MediaPlayer mMediaPlayer;
    boolean isVoicePlay;
    private int openState;
    private int state;
    private File imageFile;//视频封面图

    public CallSettingFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_call_setting;
    }

    @Override
    protected void initView() {
        ivSwitch = mParentView.findViewById(R.id.ivSwitch);
        tvState = mParentView.findViewById(R.id.tv_state);
        ivState = mParentView.findViewById(R.id.iv_state);
        tvLivePrice = mParentView.findViewById(R.id.tv_live_price);
        tvVoicePrice = mParentView.findViewById(R.id.tv_video_price);
        tvChatImg = mParentView.findViewById(R.id.tv_chat_img);
        ivChatImg = mParentView.findViewById(R.id.iv_chat_img);
        tvChatVideo = mParentView.findViewById(R.id.tv_chat_video);
        ivChatVideo = mParentView.findViewById(R.id.iv_chat_video);
        tvChatVoice = mParentView.findViewById(R.id.tv_chat_voice);
        ivChatVoice = mParentView.findViewById(R.id.iv_chat_voice);
        ivVoicePlay = mParentView.findViewById(R.id.iv_voice_play);
        ivVideoPlay = mParentView.findViewById(R.id.iv_video_play);
        mParentView.findViewById(R.id.rl_state).setOnClickListener(this);
        mParentView.findViewById(R.id.rl_live).setOnClickListener(this);
        mParentView.findViewById(R.id.rl_voice).setOnClickListener(this);
        mParentView.findViewById(R.id.rl_chat_img).setOnClickListener(this);
        mParentView.findViewById(R.id.rl_chat_video).setOnClickListener(this);
        mParentView.findViewById(R.id.rl_chat_voice).setOnClickListener(this);
        ivSwitch.setOnClickListener(this);

        uploadDialog = DialogUtil.loadingDialog(getActivity(), R.style.dialog2, com.kalacheng.util.R.layout.dialog_loading, false, false, "上传中");
    }

    @Override
    protected void initData() {
        mImageUtil = new ProcessImageUtil(getActivity());
        mProcessResultUtil = new PermissionsUtil(getActivity());
        HttpApiAppUser.getPayCallOneVsOneCfg(new HttpApiCallBack<CfgPayCallOneVsOne>() {
            @Override
            public void onHttpRet(int code, final String msg, CfgPayCallOneVsOne retModel) {
                if (code == 1 && retModel != null) {
                    openState = retModel.openState;
                    if (openState == 1) {
                        ivSwitch.setImageResource(R.mipmap.icon_o2o_switch_open);
                    } else {
                        ivSwitch.setImageResource(R.mipmap.icon_o2o_switch_close);
                    }
                    tvLivePrice.setText(String.valueOf((int) retModel.videoCoin));
                    tvVoicePrice.setText(String.valueOf((int) retModel.voiceCoin));
                    videoCoin = retModel.videoCoin;
                    voiceCoin = retModel.voiceCoin;
                    liveState = retModel.liveState;
                    if (liveState == 0) {
                        tvState.setText("在线");
                        ivState.setImageResource(R.drawable.green_oval);
                    } else if (liveState == 1) {
                        tvState.setText("忙碌");
                        ivState.setImageResource(R.mipmap.icon_busy);
                    } else if (liveState == 2) {
                        tvState.setText("离开");
                        ivState.setImageResource(R.drawable.lightgrey_oval);
                    }
                    if (!TextUtils.isEmpty(retModel.poster)) {
                        ImageLoader.display(retModel.poster, ivChatImg);
                        imgUrl = retModel.poster;
                        tvChatImg.setVisibility(View.GONE);
                    }
                    if (null != retModel.voice && !TextUtils.isEmpty(retModel.voice)) {
                        tvChatVoice.setVisibility(View.GONE);
                        ivVoicePlay.setVisibility(View.VISIBLE);
                        ImageLoader.display(retModel.poster, ivChatVoice);
                        voiceUrl = retModel.voice;
                    }
                    if (null != retModel.video && !TextUtils.isEmpty(retModel.video)) {
                        videoUrl = retModel.video;
                        tvChatVideo.setVisibility(View.GONE);
                        ivVideoPlay.setVisibility(View.VISIBLE);
                        if (!TextUtils.isEmpty(retModel.videoImg)) {
                            ImageLoader.display(retModel.videoImg, ivChatVideo);
                        } else {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    final Bitmap bitmap = BitmapUtil.getNetVideoBitmap(videoUrl);
                                    if (bitmap != null) {
                                        CallSettingFragment.this.getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ivChatVideo.setImageBitmap(bitmap);
                                            }
                                        });
                                    }
                                }
                            }).start();
                        }
                    }
                }
            }
        });
        mImageUtil.setImageResultCallback(new ImageResultCallback() {
            @Override
            public void beforeCamera() {

            }

            @Override
            public void onSuccess(final File file) {
                upImage(file);
            }

            @Override
            public void onFailure() {
            }
        });

    }

    public void upImage(final File file) {
        if (file != null) {
            uploadDialog.show();
            uploadImgDisposable = Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                    UploadUtil.getInstance().uploadPicture(1, file, new PictureUploadCallback() {
                        @Override
                        public void onSuccess(String imgStr) {
                            if (!TextUtils.isEmpty(imgStr)) {
                                emitter.onNext(imgStr);
                            } else {
                                if (uploadDialog != null && uploadDialog.isShowing()) {
                                    uploadDialog.dismiss();
                                }
                                ToastUtil.show("上传失败");
                            }
                        }

                        @Override
                        public void onFailure() {
                            if (uploadDialog != null && uploadDialog.isShowing()) {
                                uploadDialog.dismiss();
                            }
                            ToastUtil.show("上传失败");
                        }
                    });
//                    emitter.onNext(HuaWeiYun.getInstance().upload(file, HuaWeiYun.IMAGE_PATH));

                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
                @Override
                public void accept(@NonNull String url) throws Exception {
                    uploadDialog.dismiss();
                    if (null != url && !TextUtils.isEmpty(url)) {
                        ImageLoader.display(file, ivChatImg);
                        tvChatImg.setVisibility(View.GONE);
                        imgUrl = url;
                        HttpApiAppUser.setPayCallOneVsOne(liveState, openState, imgUrl, videoUrl, videoCoin, "", voiceUrl, voiceCoin, new HttpApiCallBack<HttpNone>() {
                            @Override
                            public void onHttpRet(int code, String msg, HttpNone retModel) {
                                if (code == 1) {
                                    ToastUtil.show("上传图片成功");
                                } else {
                                    ToastUtil.show("上传图片失败");
                                }
                            }
                        });
                    } else {
                        ToastUtil.show("上传图片失败,请稍后重试");
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.rl_state) {
            showPopwindow();
        } else if (view.getId() == R.id.rl_live) {
            DialogUtil.showSimpleInputDialog(getActivity(), "修改视频聊天金额", "", "请输入修改视频聊天金额", true, DialogUtil.INPUT_TYPE_NUMBER, 4, getResources().getColor(R.color.gray3), new DialogUtil.SimpleCallback() {
                @Override
                public void onConfirmClick() {

                }

                @Override
                public void onConfirmClick(String input) {
                    if (null == input || TextUtils.isEmpty(input.trim())) {
                        ToastUtil.show("输入的金额不能为空");
                        return;
                    }
                    videoCoin = Double.parseDouble(input);
                    HttpApiAppUser.setPayCallOneVsOne(liveState, openState, imgUrl, videoUrl, videoCoin, "", voiceUrl, voiceCoin, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            uploadDialog.dismiss();
                            if (code == 1) {
                                tvLivePrice.setText(setPayCall(videoCoin));
                                ToastUtil.show("修改成功");
                            } else {
                                ToastUtil.show("修改失败");
                            }
                        }
                    });
                }

                @Override
                public void onCancelClick() {

                }
            });
        } else if (view.getId() == R.id.rl_voice) {
            DialogUtil.showSimpleInputDialog(getActivity(), "修改语音聊天金额", "", "请输入修改语音聊天金额", true, DialogUtil.INPUT_TYPE_NUMBER, 4, getResources().getColor(R.color.gray3), new DialogUtil.SimpleCallback() {
                @Override
                public void onConfirmClick() {

                }

                @Override
                public void onConfirmClick(String input) {
                    if (null == input || TextUtils.isEmpty(input.trim())) {
                        ToastUtil.show("输入的金额不能为空");
                        return;
                    }
                    voiceCoin = Double.parseDouble(input);
                    HttpApiAppUser.setPayCallOneVsOne(liveState, openState, imgUrl, videoUrl, videoCoin, "", voiceUrl, voiceCoin, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            uploadDialog.dismiss();
                            if (code == 1) {

                                tvVoicePrice.setText(setPayCall(voiceCoin));
                                ToastUtil.show("修改成功");
                            } else {
                                ToastUtil.show("修改失败");
                            }
                        }
                    });
                }

                @Override
                public void onCancelClick() {

                }
            });
        } else if (view.getId() == R.id.rl_chat_img) {
            if (null != imgUrl && !TextUtils.isEmpty(imgUrl)) {
                DialogUtil.showStringArrayDialog(getActivity(), new Integer[]{
                        R.string.camera, R.string.alumb, R.string.preview}, new DialogUtil.StringArrayDialogCallback() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onItemClick(String text, int tag) {
                        if (tag == R.string.camera) {
                            mImageUtil.getImageByCamera(false);
                        } else if (tag == R.string.alumb) {
                            mImageUtil.getImageByAlbumCustom(false);
                        } else if (tag == R.string.preview) {
                            new ShowImageDialog(getContext(), imgUrl).show();
                        }
                    }
                });
            } else {
                DialogUtil.showStringArrayDialog(getActivity(), new Integer[]{
                        R.string.camera, R.string.alumb}, new DialogUtil.StringArrayDialogCallback() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onItemClick(String text, int tag) {
                        if (tag == R.string.camera) {
                            mImageUtil.getImageByCamera();
                        } else if (tag == R.string.alumb) {
                            mImageUtil.getImageByAlbumCustom(false);
                        }
                    }
                });
            }
        } else if (view.getId() == R.id.rl_chat_video) {
            if (null == videoUrl || TextUtils.isEmpty(videoUrl)) {
                DialogUtil.showStringArrayDialog(getActivity(), new Integer[]{
                        R.string.radio_picture, R.string.video_local}, new DialogUtil.StringArrayDialogCallback() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onItemClick(String text, int tag) {
                        if (tag == R.string.radio_picture) {
                            mProcessResultUtil.requestPermissions(new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.RECORD_AUDIO
                            }, new Runnable() {
                                @Override
                                public void run() {
                                    DynamicMakeActivity.forward(CallSettingFragment.this, 2, 0, false, TACK_VIDEO);
                                }
                            });
                        } else if (tag == R.string.video_local) {
                            Intent intent = new Intent(getActivity(), VideoChooseActivity.class);
                            intent.putExtra(ARouterValueNameConfig.VIDEO_DURATION, DynamicMakeActivity.MAX_DURATION);
                            startActivityForResult(intent, TACK_VIDEO);
                        }
                    }
                });
            } else {
                DialogUtil.showStringArrayDialog(getActivity(), new Integer[]{
                        R.string.radio_picture, R.string.video_local, R.string.preview}, new DialogUtil.StringArrayDialogCallback() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onItemClick(String text, int tag) {
                        if (tag == R.string.radio_picture) {
                            mProcessResultUtil.requestPermissions(new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.RECORD_AUDIO
                            }, new Runnable() {
                                @Override
                                public void run() {
                                    DynamicMakeActivity.forward(CallSettingFragment.this, 2, 0, false, TACK_VIDEO);
                                }
                            });
                        } else if (tag == R.string.video_local) {
                            Intent intent = new Intent(getActivity(), VideoChooseActivity.class);
                            intent.putExtra(ARouterValueNameConfig.VIDEO_DURATION, DynamicMakeActivity.MAX_DURATION);
                            startActivityForResult(intent, TACK_VIDEO);
                        } else if (tag == R.string.preview) {
                            ARouter.getInstance().build(ARouterPath.One2OneVideo).withString(ARouterValueNameConfig.VIDEO_URL, videoUrl).navigation();
                        }
                    }
                });
            }
        } else if (view.getId() == R.id.rl_chat_voice) {
            if (null == voiceUrl || TextUtils.isEmpty(voiceUrl.trim())) {
                mProcessResultUtil.requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.MODIFY_AUDIO_SETTINGS,
                        Manifest.permission.RECORD_AUDIO
                }, new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getActivity(), MyVoiceActivity.class);
                        startActivityForResult(intent, TACK_RECORD);
                    }
                });
            } else {
                DialogUtil.showStringArrayDialog(getActivity(), new Integer[]{
                        R.string.video_record, R.string.preview}, new DialogUtil.StringArrayDialogCallback() {
                    @Override
                    public void onItemClick(String text, int tag) {
                        if (tag == R.string.video_record) {
                            mProcessResultUtil.requestPermissions(new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.MODIFY_AUDIO_SETTINGS,
                                    Manifest.permission.RECORD_AUDIO
                            }, new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getActivity(), MyVoiceActivity.class);
                                    startActivityForResult(intent, TACK_RECORD);
                                }
                            });
                        } else if (tag == R.string.preview) {
                            playVoice(voiceUrl);
                        }
                    }
                });
            }
        } else if (view.getId() == R.id.ivSwitch) {
            if (imgUrl == null || TextUtils.isEmpty(imgUrl)) {
                ToastUtil.show("海报不能为空!");
                return;
            }
            if (videoUrl == null || TextUtils.isEmpty(videoUrl)) {
                ToastUtil.show("视频不能为空!");
                return;
            }
            if (videoCoin == 0) {
                ToastUtil.show("视频" + SpUtil.getInstance().getCoinUnit() + "不能为0!");
                return;
            }
            if (voiceCoin == 0) {
                ToastUtil.show("语音" + SpUtil.getInstance().getCoinUnit() + "不能为0!");
                return;
            }
            if (openState == 1) {
                state = 0;
            } else {
                state = 1;
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    HttpApiAppUser.setPayCallOneVsOne(liveState, state, imgUrl, videoUrl, videoCoin, "", voiceUrl, voiceCoin, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            uploadDialog.dismiss();
                            if (code == 1) {
                                openState = state;
                                if (openState == 1) {
                                    ivSwitch.setImageResource(R.mipmap.icon_o2o_switch_open);
                                } else {
                                    ivSwitch.setImageResource(R.mipmap.icon_o2o_switch_close);
                                }
                                ToastUtil.show("修改成功");
                            }
                        }
                    });
                }
            }, 500);
        }
    }

    /**
     * 设置 聊天话费
     * @param Price
     * @return
     */
    private String setPayCall(double Price) {
        DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
        String priceStr = decimalFormat.format(Price);

        return priceStr;
    }

    private void playVoice(String voiceUrl) {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
        if (isVoicePlay) {
            ToastUtil.show("录音正在播放");
            return;
        }
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(voiceUrl);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayer.start();
                    isVoicePlay = true;
                }
            });
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    isVoicePlay = false;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void deleteImage() {
        DialogUtil.showSimpleDialog(getActivity(), "提示", "是否要删除这张图片", true, new DialogUtil.SimpleCallback() {
            @Override
            public void onConfirmClick() {
                imgUrl = "";
                HttpApiAppUser.setPayCallOneVsOne(liveState, openState, imgUrl, videoUrl, videoCoin, "", voiceUrl, voiceCoin, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        uploadDialog.dismiss();
                        if (code == 1) {
                            ivChatImg.setImageResource(R.mipmap.bg_addpic);
                            tvChatImg.setVisibility(View.VISIBLE);
                            ToastUtil.show("修改成功");
                        } else {
                            ToastUtil.show("上传失败");
                        }
                    }
                });
            }

            @Override
            public void onConfirmClick(java.lang.String input) {

            }

            @Override
            public void onCancelClick() {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == TACK_RECORD) {
                uploadRecord(data.getStringExtra(ARouterValueNameConfig.VOICE_PATH));
            } else if (requestCode == TACK_VIDEO) {
                publishVideoImage(data.getStringExtra(ARouterValueNameConfig.VIDEO_PATH));
            }
        }
    }

    /**
     * 获取视频封面
     */
    private void publishVideoImage(final String videoPath) {
        uploadDialog.show();
        Bitmap bitmap = null;
        //生成视频封面图
        MediaMetadataRetriever mmr = null;
        try {
            mmr = new MediaMetadataRetriever();
            mmr.setDataSource(videoPath);
            bitmap = mmr.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mmr != null) {
                mmr.release();
            }
        }
        String coverImagePath = videoPath.replace(".mp4", ".jpg");
        imageFile = new File(coverImagePath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            imageFile = null;
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (bitmap == null || imageFile == null) {
            if (uploadDialog != null) {
                uploadDialog.dismiss();
            }
            ToastUtil.show(com.kalacheng.videorecord.R.string.video_cover_img_failed);
            return;
        }
        if (bitmap != null) {
            bitmap.recycle();
        }
        uploadVideo(videoPath, imageFile);
    }

    public void uploadVideo(final String videoPath, final File videoImg) {
        VideoUploadBean bean = new VideoUploadBean(new File(videoPath), videoImg);
        UploadUtil.getInstance().uploadVideo(4, bean, new com.xuantongyun.storagecloud.upload.base.VideoUploadCallback() {
            @Override
            public void onSuccess(final VideoUploadBean bean) {
                uploadDialog.dismiss();
                HttpApiAppUser.setPayCallOneVsOne(liveState, openState, imgUrl, bean.getResultVideoUrl(), videoCoin, bean.getResultImageUrl(), voiceUrl, voiceCoin, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        uploadDialog.dismiss();
                        if (code == 1) {
                            ToastUtil.show("上传视频成功");
                            videoUrl = bean.getResultVideoUrl();
                            tvChatVideo.setVisibility(View.GONE);
                            ivVideoPlay.setVisibility(View.VISIBLE);
                            ImageLoader.display(bean.getResultImageUrl(), ivChatVideo);
//                                Bitmap bitmap = BitmapUtil.getNetVideoBitmap(videoUrl);
//                                ivChatVideo.setImageBitmap(bitmap);
                        } else {
                            ToastUtil.show("上传失败");
                        }
                    }
                });

            }

            @Override
            public void onFailure() {
                if (null != uploadDialog)
                    uploadDialog.dismiss();
            }
        });
    }

    private void uploadRecord(final String voicePath) {
        uploadDialog.show();
        uploadVoiceDisposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext(HuaWeiYun.getInstance().uploadVideo(new File(voicePath), HuaWeiYun.VIDEO_PATH));
                UploadUtil.getInstance().uploadPicture(5, new File(voicePath), new PictureUploadCallback() {
                    @Override
                    public void onSuccess(String imgStr) {
                        uploadDialog.dismiss();
                        if (!TextUtils.isEmpty(imgStr)) {
                            emitter.onNext(imgStr);
                        } else {
                            ToastUtil.show("上传失败");
                        }
                    }

                    @Override
                    public void onFailure() {
                        uploadDialog.dismiss();
                        ToastUtil.show("上传失败");
                    }
                });
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {

            @Override
            public void accept(@NonNull final String url) throws Exception {
                if (url != null || !TextUtils.isEmpty(url)) {
                    HttpApiAppUser.setPayCallOneVsOne(liveState, openState, imgUrl, videoUrl, videoCoin, "", url, voiceCoin, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            uploadDialog.dismiss();
                            if (code == 1) {
                                ToastUtil.show("上传成功");
                                voiceUrl = url;
                                tvChatVoice.setVisibility(View.GONE);
                                ivVoicePlay.setVisibility(View.VISIBLE);
                                ImageLoader.display(imgUrl, ivChatVoice);

                            } else {
                                voiceUrl = "";
                                ToastUtil.show("上传失败");
                            }
                        }
                    });
                } else {
                    uploadDialog.dismiss();
                }
            }
        });
    }

    private void showPopwindow() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_live_state, null);
        final PopupWindow popupWindow = new PopupWindow(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(tvState);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;//代表透明程度，范围为0 - 1.0f
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        contentView.findViewById(R.id.ll_online).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                setState(0);
            }
        });
        contentView.findViewById(R.id.ll_busy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                setState(1);
            }
        });
        contentView.findViewById(R.id.ll_leave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                setState(2);
            }
        });
    }

    public void setState(final int state) {

        HttpApiAppUser.setPayCallOneVsOne(state, openState, imgUrl, videoUrl, videoCoin, "", voiceUrl, voiceCoin, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    liveState = state;
                    ToastUtil.show("修改成功");
                    if (liveState == 0) {
                        tvState.setText("在线");
                        ivState.setImageResource(R.drawable.green_oval);
                    } else if (liveState == 1) {
                        tvState.setText("忙碌");
                        ivState.setImageResource(R.mipmap.icon_busy);
                    } else if (liveState == 2) {
                        tvState.setText("离开");
                        ivState.setImageResource(R.drawable.lightgrey_oval);
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        if (uploadImgDisposable != null) {
            uploadImgDisposable.dispose();
        }
        if (uploadVoiceDisposable != null) {
            uploadVoiceDisposable.dispose();
        }
        if (uploadVideoDisposable != null) {
            uploadVideoDisposable.dispose();
        }
        if (imgThumbDisposable != null) {
            imgThumbDisposable.dispose();
        }
        if (null != mMediaPlayer)
            mMediaPlayer.release();
        super.onDestroy();
    }

    private String getAudioFilePathFromUri(Uri uri) {
        Cursor cursor = getActivity().getContentResolver()
                .query(uri, null, null, null, null);
        cursor.moveToFirst();
        int index = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
        return cursor.getString(index);
    }

    class ShowImageDialog extends Dialog {

        private String imageUrl;

        public ShowImageDialog(Context context, String imageUrl) {
            super(context, R.style.dialog);
            this.imageUrl = imageUrl;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_showimage);

            ImageView imageView = findViewById(R.id.imageView);
            ImageLoader.display(imageUrl, imageView);

            setCanceledOnTouchOutside(true); // 设置点击屏幕或物理返回键，dialog是否消失
//            Window w = getWindow();
//            assert w != null;
//            WindowManager.LayoutParams lp = w.getAttributes();
//            lp.x = 0;
//            lp.y = 40;
//            onWindowAttributesChanged(lp);
            imageView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dismiss();
                        }
                    });
        }
    }
}
