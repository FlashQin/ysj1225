package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 分享配置
 */
public class AppShareConfig  implements Parcelable
{
 public AppShareConfig()
{
}

/**
 * 直播分享标题
 */
public String shareTitle;

/**
 * ios下载链接
 */
public String ipaEwm;

/**
 * 分享logo
 */
public String logo;

/**
 * 直播话术
 */
public String shareDes;

/**
 * null
 */
public long id;

/**
 * 推广分享话术
 */
public String inviteShareDes;

/**
 * 视图分享话术
 */
public String videoShareDes;

/**
 * 视图分享标题
 */
public String videoShareTitle;

/**
 * android下载二维码
 */
public String apkEwm;

/**
 * 推广分享标题
 */
public String inviteShareTitle;

   public AppShareConfig(Parcel in) 
{
shareTitle=in.readString();
ipaEwm=in.readString();
logo=in.readString();
shareDes=in.readString();
id=in.readLong();
inviteShareDes=in.readString();
videoShareDes=in.readString();
videoShareTitle=in.readString();
apkEwm=in.readString();
inviteShareTitle=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(shareTitle);
dest.writeString(ipaEwm);
dest.writeString(logo);
dest.writeString(shareDes);
dest.writeLong(id);
dest.writeString(inviteShareDes);
dest.writeString(videoShareDes);
dest.writeString(videoShareTitle);
dest.writeString(apkEwm);
dest.writeString(inviteShareTitle);

}

  public static void cloneObj(AppShareConfig source,AppShareConfig target)
{

target.shareTitle=source.shareTitle;

target.ipaEwm=source.ipaEwm;

target.logo=source.logo;

target.shareDes=source.shareDes;

target.id=source.id;

target.inviteShareDes=source.inviteShareDes;

target.videoShareDes=source.videoShareDes;

target.videoShareTitle=source.videoShareTitle;

target.apkEwm=source.apkEwm;

target.inviteShareTitle=source.inviteShareTitle;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppShareConfig> CREATOR = new Creator<AppShareConfig>() {
        @Override
        public AppShareConfig createFromParcel(Parcel in) {
            return new AppShareConfig(in);
        }

        @Override
        public AppShareConfig[] newArray(int size) {
            return new AppShareConfig[size];
        }
    };
}
