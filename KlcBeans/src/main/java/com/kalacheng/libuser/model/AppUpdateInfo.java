package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP版本更新信息
 */
public class AppUpdateInfo  implements Parcelable
{
 public AppUpdateInfo()
{
}

/**
 * 安卓最新版APK下载链接
 */
public String apkUrl;

/**
 * IOS最新版IPA下载链接
 */
public String ipaUrl;

/**
 * IOS APP最新的版本号
 */
public String ipaVersion;

/**
 * IPA更新说明
 */
public String apkDesc;

/**
 * 安卓APP最新的版本号
 */
public String apkVersion;

/**
 * 苹果openinstall下载地址
 */
public int iosOpenInstall;

/**
 * IOS最新版本code
 */
public int ipaVersionCode;

/**
 * 安卓APP最新版本code
 */
public int apkVersionCode;

/**
 * ipa是否强制更新0非强制1强制
 */
public int isConstraintIpa;

/**
 * apk是否强制更新0非强制1强制
 */
public int isConstraintApk;

/**
 * 更新说明
 */
public String ipaDesc;

/**
 * IOS上架审核中版本的版本号
 */
public int ipaShelves;

   public AppUpdateInfo(Parcel in) 
{
apkUrl=in.readString();
ipaUrl=in.readString();
ipaVersion=in.readString();
apkDesc=in.readString();
apkVersion=in.readString();
iosOpenInstall=in.readInt();
ipaVersionCode=in.readInt();
apkVersionCode=in.readInt();
isConstraintIpa=in.readInt();
isConstraintApk=in.readInt();
ipaDesc=in.readString();
ipaShelves=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(apkUrl);
dest.writeString(ipaUrl);
dest.writeString(ipaVersion);
dest.writeString(apkDesc);
dest.writeString(apkVersion);
dest.writeInt(iosOpenInstall);
dest.writeInt(ipaVersionCode);
dest.writeInt(apkVersionCode);
dest.writeInt(isConstraintIpa);
dest.writeInt(isConstraintApk);
dest.writeString(ipaDesc);
dest.writeInt(ipaShelves);

}

  public static void cloneObj(AppUpdateInfo source,AppUpdateInfo target)
{

target.apkUrl=source.apkUrl;

target.ipaUrl=source.ipaUrl;

target.ipaVersion=source.ipaVersion;

target.apkDesc=source.apkDesc;

target.apkVersion=source.apkVersion;

target.iosOpenInstall=source.iosOpenInstall;

target.ipaVersionCode=source.ipaVersionCode;

target.apkVersionCode=source.apkVersionCode;

target.isConstraintIpa=source.isConstraintIpa;

target.isConstraintApk=source.isConstraintApk;

target.ipaDesc=source.ipaDesc;

target.ipaShelves=source.ipaShelves;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppUpdateInfo> CREATOR = new Creator<AppUpdateInfo>() {
        @Override
        public AppUpdateInfo createFromParcel(Parcel in) {
            return new AppUpdateInfo(in);
        }

        @Override
        public AppUpdateInfo[] newArray(int size) {
            return new AppUpdateInfo[size];
        }
    };
}
