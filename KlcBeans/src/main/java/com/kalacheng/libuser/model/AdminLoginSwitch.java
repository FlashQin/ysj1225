package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 后台管理-系统配置-登录开关
 */
public class AdminLoginSwitch  implements Parcelable
{
 public AdminLoginSwitch()
{
}

/**
 * 默认个性签名
 */
public String defaultSignature;

/**
 * 登录方式 1:QQ 2:微信
 */
public String loginType;

/**
 * null
 */
public long id;

/**
 * 必填邀请码
 */
public int isRegCode;

/**
 * 分享方式 1:QQ 2:qq空间 3:微信 4:微信朋友圈
 */
public String shareType;

   public AdminLoginSwitch(Parcel in) 
{
defaultSignature=in.readString();
loginType=in.readString();
id=in.readLong();
isRegCode=in.readInt();
shareType=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(defaultSignature);
dest.writeString(loginType);
dest.writeLong(id);
dest.writeInt(isRegCode);
dest.writeString(shareType);

}

  public static void cloneObj(AdminLoginSwitch source,AdminLoginSwitch target)
{

target.defaultSignature=source.defaultSignature;

target.loginType=source.loginType;

target.id=source.id;

target.isRegCode=source.isRegCode;

target.shareType=source.shareType;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdminLoginSwitch> CREATOR = new Creator<AdminLoginSwitch>() {
        @Override
        public AdminLoginSwitch createFromParcel(Parcel in) {
            return new AdminLoginSwitch(in);
        }

        @Override
        public AdminLoginSwitch[] newArray(int size) {
            return new AdminLoginSwitch[size];
        }
    };
}
