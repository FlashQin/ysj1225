package com.kalacheng.centercommon.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.centercommon.R;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAPPAnchor;
import com.kalacheng.libuser.model.AppUsersAuth;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.ImageResultCallback;
import com.kalacheng.util.utils.PermissionsUtil;
import com.kalacheng.commonview.utils.ProcessImageUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.commonview.activity.DynamicMakeActivity;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBack;
import com.xuantongyun.storagecloud.upload.UploadUtil;
import com.xuantongyun.storagecloud.upload.base.PictureUploadCallback;

import java.io.File;

/**
 * 身份证上传
 */
@Route(path = ARouterPath.UpLoadIdCardActivity)
public class UpLoadIdCardActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivIdFront, ivIdReverse, ivIdHand, pickImg;
    String imgTag;
    private ProcessImageUtil mImageUtil;
    private String frontStr;
    private String reverseStr;
    private String handStr;
    PermissionsUtil mProcessResultUtil;
    private Dialog uploadDialog;
    private TextView btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard_upload);
        initView();
        initData();
    }

    private void initView() {
        ivIdFront = findViewById(R.id.iv_id_front);
        ivIdReverse = findViewById(R.id.iv_id_reverse);
        ivIdHand = findViewById(R.id.iv_id_hand);
        btn_next = findViewById(R.id.btn_next);
        if (ConfigUtil.getIntValue(R.integer.authenticationStep) == 2) {
            btn_next.setText("提交资料");
        } else {
            btn_next.setText("去视频认证");
        }
        btn_next.setOnClickListener(this);
        ivIdFront.setOnClickListener(this);
        ivIdReverse.setOnClickListener(this);
        ivIdHand.setOnClickListener(this);
    }

    private void initData() {
        setTitle("身份证上传");
        uploadDialog = DialogUtil.loadingDialog(mContext, R.style.dialog2, com.kalacheng.util.R.layout.dialog_loading, false, false, getString(com.kalacheng.videorecord.R.string.uploading));
        frontStr = (String) SpUtil.getInstance().getSharedPreference("front", "");
        reverseStr = (String) SpUtil.getInstance().getSharedPreference("reverse", "");
        handStr = (String) SpUtil.getInstance().getSharedPreference("hand", "");
        if (!TextUtils.isEmpty(frontStr)) {
            ImageLoader.display(frontStr, ivIdFront);
        }
        if (!TextUtils.isEmpty(reverseStr)) {
            ImageLoader.display(reverseStr, ivIdReverse);
        }
        if (!TextUtils.isEmpty(handStr)) {
            ImageLoader.display(handStr, ivIdHand);
        }
        mProcessResultUtil = new PermissionsUtil(this);
        mImageUtil = new ProcessImageUtil(this);
        mImageUtil.setImageResultCallback(new ImageResultCallback() {
            @Override
            public void beforeCamera() {

            }

            @Override
            public void onSuccess(final File file) {
                if (file != null) {
                    ImageLoader.display(file, pickImg);
                    if (uploadDialog != null) {
                        uploadDialog.show();
                    }
                    UploadUtil.getInstance().uploadPicture(1, file, new PictureUploadCallback() {
                        @Override
                        public void onSuccess(String imgStr) {
                            if (!TextUtils.isEmpty(imgStr)) {
                                SpUtil.getInstance().put(imgTag, imgStr);
                                if (imgTag.equals("front"))
                                    frontStr = imgStr;
                                else if (imgTag.equals("reverse"))
                                    reverseStr = imgStr;
                                else if (imgTag.equals("hand"))
                                    handStr = imgStr;
                                if (uploadDialog != null && uploadDialog.isShowing()) {
                                    uploadDialog.dismiss();
                                }
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
                }
            }

            @Override
            public void onFailure() {
            }
        });

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_id_front) {
            pickImg = ivIdFront;
            imgTag = "front";
            setImage();
        } else if (view.getId() == R.id.iv_id_reverse) {
            pickImg = ivIdReverse;
            imgTag = "reverse";
            setImage();
        } else if (view.getId() == R.id.iv_id_hand) {
            pickImg = ivIdHand;
            imgTag = "hand";
            setImage();
        } else if (view.getId() == R.id.btn_next) {
            /**
             * 主播认证第二步
             *
             * @param backView    身份证反面(同上)
             * @param frontView   身份证正面(通过叭叭云上传后返回的图片地址)
             * @param handsetView 手持身份证(同上)
             */
            HttpApiAPPAnchor.authSecond(reverseStr, frontStr, handStr, new HttpApiCallBack<AppUsersAuth>() {
                @Override
                public void onHttpRet(int code, String msg, AppUsersAuth retModel) {
                    if (code == 1) {
                        ToastUtil.show("信息上传成功");
                        if (ConfigUtil.getIntValue(R.integer.authenticationStep) == 2) {
                            finish();
                        } else {
                            mProcessResultUtil.requestPermissions(new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.RECORD_AUDIO
                            }, new Runnable() {
                                @Override
                                public void run() {
                                    DynamicMakeActivity.forward(UpLoadIdCardActivity.this, 2, 5, true, 1001);
                                }
                            });
                        }

                    } else {
                        ToastUtil.show(msg);
                    }
                }
            });
        }
    }

    Integer[] srt;

    private void setImage() {
        if (ConfigUtil.getBoolValue(R.bool.containPhotograph)) {
            srt = new Integer[]{R.string.camera, R.string.alumb};
        } else {
            srt = new Integer[]{R.string.alumb};
        }

        DialogUtil.showStringArrayDialog(mContext, srt, new DialogUtil.StringArrayDialogCallback() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(String text, int tag) {
                if (tag == R.string.camera) {
                    mImageUtil.getImageByCamera(false);
                } else if (tag == R.string.alumb) {
                    mImageUtil.getImageByAlbumCustom(false);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1001) {
                ARouter.getInstance().build(ARouterPath.UpLoadAuthVideoActivity).withString(ARouterValueNameConfig.VIDEO_PATH, data.getStringExtra(ARouterValueNameConfig.VIDEO_PATH))
                        .withInt(ARouterValueNameConfig.VideoType, data.getIntExtra(ARouterValueNameConfig.VideoType, 1))
                        .withLong(ARouterValueNameConfig.VIDEO_TIME_LONG, data.getLongExtra(ARouterValueNameConfig.VIDEO_TIME_LONG, 0)).navigation();
                finish();
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (requestCode == 1001) {
                finish();
            }
        }
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }
}
