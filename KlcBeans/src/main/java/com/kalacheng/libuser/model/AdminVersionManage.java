package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 后台管理-系统配置-APP版本管理
 */
public class AdminVersionManage  implements Parcelable
{
 public AdminVersionManage()
{
}

/**
 * IOS最新版本code
 */
public int ipaVersionCode;

/**
 * 更新说明
 */
public String ipaDesc;

/**
 * IOS上架审核中版本的版本号
 */
public int ipaShelves;

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
 * 安卓APP最新版本code
 */
public int apkVersionCode;

/**
 * ipa是否强制更新0非强制1强制
 */
public int isConstraintIpa;

/**
 * null
 */
public long id;

/**
 * apk是否强制更新0非强制1强制
 */
public int isConstraintApk;

   public AdminVersionManage(Parcel in) 
{
ipaVersionCode=in.readInt();
ipaDesc=in.readString();
ipaShelves=in.readInt();
apkUrl=in.readString();
ipaUrl=in.readString();
ipaVersion=in.readString();
apkDesc=in.readString();
apkVersion=in.readString();
iosOpenInstall=in.readInt();
apkVersionCode=in.readInt();
isConstraintIpa=in.readInt();
id=in.readLong();
isConstraintApk=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(ipaVersionCode);
dest.writeString(ipaDesc);
dest.writeInt(ipaShelves);
dest.writeString(apkUrl);
dest.writeString(ipaUrl);
dest.writeString(ipaVersion);
dest.writeString(apkDesc);
dest.writeString(apkVersion);
dest.writeInt(iosOpenInstall);
dest.writeInt(apkVersionCode);
dest.writeInt(isConstraintIpa);
dest.writeLong(id);
dest.writeInt(isConstraintApk);

}

  public static void cloneObj(AdminVersionManage source,AdminVersionManage target)
{

target.ipaVersionCode=source.ipaVersionCode;

target.ipaDesc=source.ipaDesc;

target.ipaShelves=source.ipaShelves;

target.apkUrl=source.apkUrl;

target.ipaUrl=source.ipaUrl;

target.ipaVersion=source.ipaVersion;

target.apkDesc=source.apkDesc;

target.apkVersion=source.apkVersion;

target.iosOpenInstall=source.iosOpenInstall;

target.apkVersionCode=source.apkVersionCode;

target.isConstraintIpa=source.isConstraintIpa;

target.id=source.id;

target.isConstraintApk=source.isConstraintApk;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdminVersionManage> CREATOR = new Creator<AdminVersionManage>() {
        @Override
        public AdminVersionManage createFromParcel(Parcel in) {
            return new AdminVersionManage(in);
        }

        @Override
        public AdminVersionManage[] newArray(int size) {
            return new AdminVersionManage[size];
        }
    };
}
