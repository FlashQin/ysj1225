package com.kalacheng.frame.config;


import android.os.Environment;

import com.kalacheng.base.activty.BaseApplication;

import java.io.File;


public class APPProConfig {

    public static final float mVideoRadio = 1.72f;

    /**
     * 视频高度
     */
    public static int getVidowHeight() {
        return (int) (BaseApplication.getInstance().getResources().getDisplayMetrics().widthPixels / 2 * mVideoRadio);
    }

    private static class SingletonHolder {
        private static final APPProConfig INSTANCE = new APPProConfig();
    }

    private APPProConfig() {
    }

    public static final APPProConfig getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //权限申请
    public static final int REQUEST_PERMISSION_CODE = 0x01;//权限申请
    //图片选择
    public static final int REQUEST_CODE_CHOOSE = 0x02;
    //相机拍照
    public static final int REQUEST_CODE_CAMERA = 0x03;
    public static final int REQUSET_CODE_UP = 1000; //身份证正面
    public static final int REQUSET_CODE_DOWN = 1000; //身份证反面
    public static final int REQUSET_CODE_HEAD = 1000; //手持身份证
    //SD卡目录
    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();
    //存放文件到ychat中
    public static final String YCHAT_DIR = SDCARD_PATH + File.separator + "new/";
    //裁剪后的文件目录,个人中心头像
    public static final String HEAD_AFTER_SHEAR_DIR = YCHAT_DIR + "head/";

    //android 6.0以上外部sd卡
    public static final String DCMI_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();

    //android 6.0以下外部sd卡

    public static final String DCMI_PATH_SYSTEM_6 = Environment.getExternalStorageDirectory().getAbsolutePath();
    //内部存储 /data/data/<application package>/files目录
    public static final String INNER_PATH = BaseApplication.getInstance().getFilesDir().getAbsolutePath();
    //文件夹名字
    private static final String DIR_NAME = "mxd";
    //保存视频的时候，在sd卡存储短视频的路径DCIM下
    public static final String VIDEO_PATH = DCMI_PATH + "/" + DIR_NAME + "/video/";
    //保存视频的时候，在sd卡存储短视频的路径DCIM下
    public static final String VOICE_PATH = DCMI_PATH + "/" + DIR_NAME + "/voice/";
    //下载贴纸的时候保存的路径
    public static final String VIDEO_TIE_ZHI_PATH = DCMI_PATH + "/" + DIR_NAME + "/tieZhi/";
    //下载音乐的时候保存的路径
    public static final String MUSIC_PATH = DCMI_PATH + "/" + DIR_NAME + "/music/";
    //拍照时图片保存路径6.0以上
    public static final String CAMERA_IMAGE_PATH = DCMI_PATH + "/" + DIR_NAME + "/camera/";

    //拍照时图片保存路径6.0以下
    public static final String CAMERA_IMAGE_PATH_SYSTEM_6 = DCMI_PATH + "/" + DIR_NAME + "/camera/";

    public static final String GIF_PATH = DCMI_PATH + "/" + DIR_NAME + "/gif/";

    public static final String PIC_PATH = DCMI_PATH + "/" + DIR_NAME + "/pic/";
    //音效路径
    public static final String AIR_PATH = INNER_PATH + "/air/";
    //注册
    public static final int REGISTER = 1;
    //找回密码
    public static final int FINDPEW = 2;
    //绑定手机号
    public static final int BINDING_PHONE = 5;
    //修改手机号
    public static final int MODIFY_PHONE = 7;

    //评论回复
    public static final int ReplyType = 2;
    //评论
    public static final int CommentType = 1;

    //QQ登录是否与PC端互通
    public static final boolean QQ_LOGIN_WITH_PC = false;

    public static final int GUARD_TYPE_NONE = 0;
    public static final int GUARD_TYPE_MONTH = 1;
    public static final int GUARD_TYPE_YEAR = 2;

}
