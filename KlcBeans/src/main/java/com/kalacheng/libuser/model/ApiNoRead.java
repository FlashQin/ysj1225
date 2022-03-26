package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP消息未读数
 */
public class ApiNoRead  implements Parcelable
{
 public ApiNoRead()
{
}

/**
 * 总未读数
 */
public int totalNoRead;

/**
 * 短视频未读数
 */
public int shortVideoNoRead;

/**
 * 通知未读数
 */
public int systemNoRead;

/**
 * 官方未读数
 */
public int officialNewsNoRead;

/**
 * 动态未读数
 */
public int videoNoRead;

   public ApiNoRead(Parcel in) 
{
totalNoRead=in.readInt();
shortVideoNoRead=in.readInt();
systemNoRead=in.readInt();
officialNewsNoRead=in.readInt();
videoNoRead=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(totalNoRead);
dest.writeInt(shortVideoNoRead);
dest.writeInt(systemNoRead);
dest.writeInt(officialNewsNoRead);
dest.writeInt(videoNoRead);

}

  public static void cloneObj(ApiNoRead source,ApiNoRead target)
{

target.totalNoRead=source.totalNoRead;

target.shortVideoNoRead=source.shortVideoNoRead;

target.systemNoRead=source.systemNoRead;

target.officialNewsNoRead=source.officialNewsNoRead;

target.videoNoRead=source.videoNoRead;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiNoRead> CREATOR = new Creator<ApiNoRead>() {
        @Override
        public ApiNoRead createFromParcel(Parcel in) {
            return new ApiNoRead(in);
        }

        @Override
        public ApiNoRead[] newArray(int size) {
            return new ApiNoRead[size];
        }
    };
}
