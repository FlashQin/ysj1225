package com.kalacheng.commonview.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import com.kalacheng.commonview.activity.PictureChooseActivity;
import com.kalacheng.commonview.bean.PictureChooseBean;
import com.kalacheng.frame.config.APPProConfig;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.util.R;
import com.kalacheng.util.utils.DateUtil;
import com.kalacheng.util.utils.ImageResultCallback;
import com.kalacheng.util.utils.ProcessResultUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.fragment.ActivityResultCallback;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxf on 2018/9/29.
 * 选择图片 裁剪
 */

public class ProcessImageUtil extends ProcessResultUtil {

    private Context mContext;
    private String[] mCameraPermissions;
    private String[] mAlumbPermissions;
    private String[] mAlbumCustomPermissions;
    private Runnable mCameraPermissionCallback;
    private Runnable mAlumbPermissionCallback;
    private Runnable mAlbumCustomPermissionCallback;
    private ActivityResultCallback mCameraResultCallback;
    private ActivityResultCallback mAlumbResultCallback;
    private ActivityResultCallback mlbumCustomResultCallback;
    private ActivityResultCallback mCropResultCallback;
    private File mCameraResult;//拍照后得到的图片
    private File mCorpResult;//裁剪后得到的图片
    private ImageResultCallback mResultCallback;
    private boolean mNeedCrop;//是否需要裁剪
    private int mWidthRatio = 1;//剪裁后宽度比例
    private int mHeightRatio = 1;//剪裁后高度比例

    public ProcessImageUtil(FragmentActivity activity) {
        super(activity);
        mContext = activity;
        mCameraPermissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };
        mAlumbPermissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };
        mAlbumCustomPermissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };
        mCameraPermissionCallback = new Runnable() {
            @Override
            public void run() {
                takePhoto();
            }
        };
        mAlumbPermissionCallback = new Runnable() {
            @Override
            public void run() {
                chooseFile();
            }
        };
        mAlbumCustomPermissionCallback = new Runnable() {
            @Override
            public void run() {
                chooseFileCustom();
            }
        };
        mCameraResultCallback = new ActivityResultCallback() {
            @Override
            public void onSuccess(Intent intent) {
                if (mNeedCrop) {
                    Uri uri = null;
                    if (Build.VERSION.SDK_INT >= 24) {
                        uri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".fileprovider", mCameraResult);
                    } else {
                        uri = Uri.fromFile(mCameraResult);
                    }
                    if (uri != null) {
                        crop(uri);
                    }
                } else {
                    if (mResultCallback != null) {
                        mResultCallback.onSuccess(mCameraResult);
                    }
                }
            }

            @Override
            public void onFailure() {
                ToastUtil.show(R.string.img_camera_cancel);
            }
        };
        mAlumbResultCallback = new ActivityResultCallback() {
            @Override
            public void onSuccess(Intent intent) {
                if (mNeedCrop) {
                    crop(intent.getData());
                } else {
                    if (mResultCallback != null) {
                        mResultCallback.onSuccess(uri2File(intent.getData()));
                    }
                }
            }

            @Override
            public void onFailure() {
                ToastUtil.show(R.string.img_alumb_cancel);
            }
        };
        mlbumCustomResultCallback = new ActivityResultCallback() {
            @Override
            public void onSuccess(Intent intent) {
                if (mNeedCrop) {
                    List<PictureChooseBean> pictures = new ArrayList<>();
                    pictures.addAll(intent.<PictureChooseBean>getParcelableArrayListExtra(ARouterValueNameConfig.PICTURE_LIST));
                    if (pictures.size() > 0) {
                        crop(Uri.fromFile(new File(pictures.get(0).getPath())));
                    }
                } else {
                    List<PictureChooseBean> pictures = new ArrayList<>();
                    pictures.addAll(intent.<PictureChooseBean>getParcelableArrayListExtra(ARouterValueNameConfig.PICTURE_LIST));
                    if (pictures.size() > 0) {
                        final File file = new File(pictures.get(0).getPath());
                        if (file != null && mResultCallback != null) {
                            mResultCallback.onSuccess(file);
                        }
                    }
                }
            }

            @Override
            public void onFailure() {
                ToastUtil.show(R.string.img_alumb_cancel);
            }
        };
        mCropResultCallback = new ActivityResultCallback() {
            @Override
            public void onSuccess(Intent intent) {
                if (mResultCallback != null) {
                    mResultCallback.onSuccess(mCorpResult);
                }
            }

            @Override
            public void onFailure() {
                ToastUtil.show(R.string.img_crop_cancel);
            }
        };
    }

    /**
     * 拍照获取图片
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getImageByCamera(boolean needCrop) {
        mNeedCrop = needCrop;
        mWidthRatio = 1;
        mHeightRatio = 1;
        requestPermissions(mCameraPermissions, mCameraPermissionCallback);
    }

    /**
     * 拍照获取图片
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getImageByCamera() {
        getImageByCamera(true);
    }

    /**
     * 拍照获取图片
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getImageByCamera(int widthRatio, int heightRatio) {
        mNeedCrop = true;
        mWidthRatio = widthRatio;
        mHeightRatio = heightRatio;
        requestPermissions(mCameraPermissions, mCameraPermissionCallback);
    }

    /**
     * 相册获取图片
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getImageByAlumb(boolean needCrop) {
        mNeedCrop = needCrop;
        mWidthRatio = 1;
        mHeightRatio = 1;
        requestPermissions(mAlumbPermissions, mAlumbPermissionCallback);
    }

    /**
     * 相册获取图片
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getImageByAlumb() {
        getImageByAlumb(true);
    }

    /**
     * 相册获取图片
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getImageByAlumb(int widthRatio, int heightRatio) {
        mNeedCrop = true;
        mWidthRatio = widthRatio;
        mHeightRatio = heightRatio;
        requestPermissions(mAlumbPermissions, mAlumbPermissionCallback);
    }

    /**
     * 自定义相册获取图片
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getImageByAlbumCustom(boolean needCrop) {
        mNeedCrop = needCrop;
        mWidthRatio = 1;
        mHeightRatio = 1;
        requestPermissions(mAlbumCustomPermissions, mAlbumCustomPermissionCallback);
    }

    /**
     * 自定义相册获取图片
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getImageByAlbumCustom() {
        getImageByAlbumCustom(true);
    }

    /**
     * 自定义相册获取图片
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getImageByAlbumCustom(int widthRatio, int heightRatio) {
        mNeedCrop = true;
        mWidthRatio = widthRatio;
        mHeightRatio = heightRatio;
        requestPermissions(mAlbumCustomPermissions, mAlbumCustomPermissionCallback);
    }

    /**
     * 开启摄像头，执行照相
     */
    private void takePhoto() {
        if (mResultCallback != null) {
            mResultCallback.beforeCamera();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mCameraResult = getNewFile();
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".fileprovider", mCameraResult);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(mCameraResult);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, mCameraResultCallback);
    }

    private File getNewFile() {
        // 裁剪头像的绝对路径
        File dir;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            dir = new File(APPProConfig.CAMERA_IMAGE_PATH);
        } else {
            dir = new File(APPProConfig.CAMERA_IMAGE_PATH_SYSTEM_6);
        }


        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, new DateUtil().getDateToFormat("yyyyMMddHHmmssSSS") + ".png");
    }


    /**
     * 打开相册，选择文件
     */
    private void chooseFile() {
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }
        startActivityForResult(intent, mAlumbResultCallback);
    }

    private void chooseFileCustom() {
        Intent intent = new Intent(mContext, PictureChooseActivity.class);
        intent.putExtra(PictureChooseActivity.PICTURE_CHOOSE_NUM, 1);
        startActivityForResult(intent, mlbumCustomResultCallback);
    }

    /**
     * 裁剪
     */
    private void crop(Uri inputUri) {
        mCorpResult = getNewFile();
        try {
            Uri resultUri = Uri.fromFile(mCorpResult);
            if (resultUri == null || mFragment == null || mContext == null) {
                return;
            }
            UCrop uCrop = UCrop.of(inputUri, resultUri)
                    .withAspectRatio(mWidthRatio, mHeightRatio)
                    .withMaxResultSize(400, 400);
            Intent intent = uCrop.getIntent(mContext);

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(intent, mCropResultCallback);
        } catch (Exception e) {
            try {
                Uri resultUri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".fileprovider", mCorpResult);
                if (resultUri == null || mFragment == null || mContext == null) {
                    return;
                }
                UCrop uCrop = UCrop.of(inputUri, resultUri)
                        .withAspectRatio(mWidthRatio, mHeightRatio)
                        .withMaxResultSize(400, 400);
                Intent intent = uCrop.getIntent(mContext);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(intent, mCropResultCallback);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }


    public void setImageResultCallback(ImageResultCallback resultCallback) {
        mResultCallback = resultCallback;
    }

    @Override
    public void release() {
        super.release();
        mResultCallback = null;
    }

    /**
     * 存在问题，暂不使用
     * Uri转换为file文件
     * 返回值为file类型
     */
    private File uri2File(Uri uri) {
        String img_path;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = ((Activity) mContext).managedQuery(uri, proj, null, null, null);
        if (actualimagecursor == null) {
            img_path = uri.getPath();
        } else {
            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            img_path = actualimagecursor.getString(actual_image_column_index);
        }
        File file = new File(img_path);
        return file;
    }
}
