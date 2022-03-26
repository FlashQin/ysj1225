package com.kalacheng.util.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lzy.okgo.utils.IOUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class BitmapUtil {
    private static BitmapUtil sInstance;
    private Resources mResources;
    private BitmapFactory.Options mOptions;
    /**
     * 限制图片最大宽度进行压缩
     */
    private static final int MAX_WIDTH = 720;
    /**
     * 限制图片最大高度进行压缩
     */
    private static final int MAX_HEIGHT = 1280;
    /**
     * 上传最大图片限制
     */
    private static final int MAX_UPLOAD_PHOTO_SIZE = 300 * 1024;

    private BitmapUtil() {
        mResources = ApplicationUtil.getInstance().getResources();
        mOptions = new BitmapFactory.Options();
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        mOptions.inDither = true;
        mOptions.inSampleSize = 1;
    }

    public static BitmapUtil getInstance() {
        if (sInstance == null) {
            synchronized (BitmapUtil.class) {
                if (sInstance == null) {
                    sInstance = new BitmapUtil();
                }
            }
        }
        return sInstance;
    }


    public Bitmap decodeBitmap(int imgRes) {
        Bitmap bitmap = null;
        try {
            byte[] bytes = IOUtils.toByteArray(mResources.openRawResource(imgRes));
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, mOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SoftReference<>(bitmap).get();
    }

    /**
     * 把Bitmap保存成图片文件
     *
     * @param bitmap
     */
    public String saveBitmap(Bitmap bitmap, String dirPath) {
        String path = null;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File imageFile = new File(dir, new DateUtil().getDateToFormat("yyyyMMddHHmmssSSS") + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            path = imageFile.getAbsolutePath();
        } catch (Exception e) {
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
        return path;
    }

    /**
     * 保存拍摄图片
     *
     * @param data
     * @param isFrontFacing 是否为前置拍摄
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String savePhoto(byte[] data, boolean isFrontFacing, String dirPath) {
        String path = "";
//        InputStream byteStream = new ByteArrayInputStream(data);
//        ExifInterface exifInterface = null;
        int degree = 0;

        if (data != null) {
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File imageFile = new File(dir.getPath(), new DateUtil().getDateToFormat("yyyyMMddHHmmssSSS") + ".jpg");
            FileOutputStream fos = null;
            try {
                Bitmap preBitmap = compressBitmap(data, MAX_WIDTH, MAX_HEIGHT);
                Bitmap newBitmap = null;
                if (isFrontFacing) {
                    Matrix matrix = new Matrix();
                    matrix.postScale(1, -1);
                    matrix.postRotate(-degree);
                    newBitmap = Bitmap.createBitmap(preBitmap, 0, 0, preBitmap.getWidth(), preBitmap.getHeight(), matrix, true);
                } else {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(degree);
                    newBitmap = Bitmap.createBitmap(preBitmap, 0, 0, preBitmap.getWidth(), preBitmap.getHeight(), matrix, true);
                }
                byte[] newDatas = compressBitmapToBytes(newBitmap, MAX_UPLOAD_PHOTO_SIZE);
                fos = new FileOutputStream(imageFile);
                fos.write(newDatas);
                path = imageFile.getAbsolutePath();
                preBitmap.recycle();
                newBitmap.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeCloseable(fos);
            }
        }
        return path;
    }

    /**
     * 把字节流按照图片方式大小进行压缩
     *
     * @param datas
     * @param w
     * @param h
     * @return
     */
    public static Bitmap compressBitmap(byte[] datas, int w, int h) {
        if (datas != null) {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(datas, 0, datas.length, opts);
            if (opts.outWidth != 0 && opts.outHeight != 0) {
                int scaleX = opts.outWidth / w;
                int scaleY = opts.outHeight / h;
                int scale = 1;
                if (scaleX >= scaleY && scaleX >= 1) {
                    scale = scaleX;
                }
                if (scaleX < scaleY && scaleY >= 1) {
                    scale = scaleY;
                }
                opts.inJustDecodeBounds = false;
                opts.inSampleSize = scale;
                return BitmapFactory.decodeByteArray(datas, 0, datas.length, opts);
            }
        }
        return null;
    }

    /**
     * 质量压缩图片
     *
     * @param bitmap
     * @param maxSize
     * @return
     */
    public static byte[] compressBitmapToBytes(Bitmap bitmap, int maxSize) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] datas = baos.toByteArray();
        int options = 80;
        int longs = datas.length;
        while (longs > maxSize && options > 0) {
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            datas = baos.toByteArray();
            longs = datas.length;
            options -= 20;
        }
        return datas;
    }

    /**
     * 关闭资源
     *
     * @param close
     */
    public static void closeCloseable(Closeable close) {
        if (close != null) {
            try {
                close.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理旋转后的图片
     *
     * @param originpath    原图路径
     * @param isReplaceFile 是否替换之前的文件 true 替换 false 不替换 默认保存位置
     * @return 返回修复完毕后的图片路径
     */
    public static String amendRotatePhoto(String originpath, boolean isReplaceFile) {

        if (TextUtils.isEmpty(originpath)) return originpath;

        // 取得图片旋转角度
        int angle = readPictureDegree(originpath);

        //是否压缩
        Bitmap bmp = null;
        Bitmap localBitmap = BitmapFactory.decodeFile(originpath);
        if (localBitmap == null) return originpath;
        //处理旋转
        Bitmap bitmap = null;
        if (angle != 0) {
            // 修复图片被旋转的角度
            bitmap = rotaingImageView(angle, localBitmap);
        }
        if (bitmap != null) {
            return savePhotoToSD(bitmap, originpath, isReplaceFile);
        } else {
            return originpath;
        }

    }

    /**
     * 读取照片旋转角度
     *
     * @param path 照片路径
     * @return 角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /* 旋转图片
     *
     * @param angle  被旋转角度
     * @param bitmap 图片对象
     * @return 旋转后的图片
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bitmap;
        }
        if (bitmap != returnBm) {
            bitmap.recycle();
        }
        return returnBm;
    }

    /**
     * 保存Bitmap图片在SD卡中
     * 如果没有SD卡则存在手机中
     *
     * @param mbitmap       需要保存的Bitmap图片
     * @param originpath    文件的原路径
     * @param isReplaceFile 是否替换原文件
     * @return 保存成功时返回图片的路径，失败时返回null
     */
    public static String savePhotoToSD(Bitmap mbitmap, String originpath, boolean isReplaceFile) {
        FileOutputStream outStream = null;
        String fileName = "";
        if (mbitmap == null) return originpath;
        if (isReplaceFile) {
            fileName = getPhotoFileName(originpath);
        } else {
            if (TextUtils.isEmpty(originpath)) return originpath;
            fileName = originpath;
        }
        try {
            outStream = new FileOutputStream(fileName);
            // 把数据写入文件，100表示不压缩
            mbitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (outStream != null) {
                    // 记得要关闭流！
                    outStream.close();
                }
                if (mbitmap != null) {
                    mbitmap.recycle();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getPhotoFileName(String pathandname) {
        int start = pathandname.lastIndexOf("/");
        int end = pathandname.lastIndexOf(".");
        if (start != -1 && end != -1) {
            return pathandname.substring(start + 1, end);
        } else {
            return null;
        }

    }

    /**
     * 生成固定大小的二维码(不需网络权限)
     *
     * @param content 需要生成的内容
     * @param width   二维码宽度
     * @param height  二维码高度
     * @return
     */
    public static Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * View截图，并保存到相册中
     */
    public static void saveViewBitmapFile(View toSaveView, String dirPath) {
        //开启缓存
        toSaveView.setDrawingCacheEnabled(true);
        //获取bitmap
        Bitmap bitmapTemp = toSaveView.getDrawingCache();
        bitmapTemp = Bitmap.createBitmap(bitmapTemp);
        //关闭缓存
        toSaveView.setDrawingCacheEnabled(false);
        //保存本地
        FileUtil.createFolder(dirPath);
        File file = new File(dirPath, "pic_" + System.currentTimeMillis() + ".png");
        if (file.exists()) {
            ToastUtil.show("图片已存在");
            return;
        }
        try {
            //创建文件
            if (file.createNewFile()) {
                //Bitmap写入文件
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmapTemp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
                //通知文件扫描刷新
                try {
                    VideoLocalUtil.savePicInfo(ApplicationUtil.getInstance(), file.getAbsolutePath());
                    ToastUtil.show("图片已经保存到相册中");
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.show("保存失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.show("保存失败");
        }
    }

    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    // 获取视频的缩略图
    public Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap;
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind); //調用ThumbnailUtils類的靜態方法createVideoThumbnail獲取視頻的截圖；
        if (bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);//調用ThumbnailUtils類的靜態方法extractThumbnail將原圖片（即上方截取的圖片）轉化為指定大小；
        }
        return bitmap;
    }

    /**
     * 加载本地图片
     */
    public static Bitmap getLocalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
