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
public class ApiShareConfig  implements Parcelable
{
 public ApiShareConfig()
{
}

/**
 * 分享标题
 */
public String shareTitle;

/**
 * logo
 */
public String logo;

/**
 * 分享话术
 */
public String shareDes;

/**
 * 下载地址
 */
public String url;

   public ApiShareConfig(Parcel in) 
{
shareTitle=in.readString();
logo=in.readString();
shareDes=in.readString();
url=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(shareTitle);
dest.writeString(logo);
dest.writeString(shareDes);
dest.writeString(url);

}

  public static void cloneObj(ApiShareConfig source,ApiShareConfig target)
{

target.shareTitle=source.shareTitle;

target.logo=source.logo;

target.shareDes=source.shareDes;

target.url=source.url;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiShareConfig> CREATOR = new Creator<ApiShareConfig>() {
        @Override
        public ApiShareConfig createFromParcel(Parcel in) {
            return new ApiShareConfig(in);
        }

        @Override
        public ApiShareConfig[] newArray(int size) {
            return new ApiShareConfig[size];
        }
    };
}
