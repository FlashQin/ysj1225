package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP版本控制返回值
 */
public class ApiVersion  implements Parcelable
{
 public ApiVersion()
{
}

/**
 * 描述
 */
public String des;

/**
 * 是否强制更新,-1:不需要更新,0:非强制,1:强制
 */
public int isConstraint;

/**
 * 版本号
 */
public String versionNo;

/**
 * 下载地址
 */
public String url;

/**
 * app版本code
 */
public int versionCode;

   public ApiVersion(Parcel in) 
{
des=in.readString();
isConstraint=in.readInt();
versionNo=in.readString();
url=in.readString();
versionCode=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(des);
dest.writeInt(isConstraint);
dest.writeString(versionNo);
dest.writeString(url);
dest.writeInt(versionCode);

}

  public static void cloneObj(ApiVersion source,ApiVersion target)
{

target.des=source.des;

target.isConstraint=source.isConstraint;

target.versionNo=source.versionNo;

target.url=source.url;

target.versionCode=source.versionCode;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiVersion> CREATOR = new Creator<ApiVersion>() {
        @Override
        public ApiVersion createFromParcel(Parcel in) {
            return new ApiVersion(in);
        }

        @Override
        public ApiVersion[] newArray(int size) {
            return new ApiVersion[size];
        }
    };
}
