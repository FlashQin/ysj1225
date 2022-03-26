package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP语音直播背景图
 */
public class AppVoiceThumb  implements Parcelable
{
 public AppVoiceThumb()
{
}

/**
 * 描述
 */
public String des;

/**
 * 排序
 */
public int orderno;

/**
 * 图片地址
 */
public String thumb;

/**
 * null
 */
public long id;

/**
 * 类型0多人语音1一对一语音
 */
public int type;

   public AppVoiceThumb(Parcel in) 
{
des=in.readString();
orderno=in.readInt();
thumb=in.readString();
id=in.readLong();
type=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(des);
dest.writeInt(orderno);
dest.writeString(thumb);
dest.writeLong(id);
dest.writeInt(type);

}

  public static void cloneObj(AppVoiceThumb source,AppVoiceThumb target)
{

target.des=source.des;

target.orderno=source.orderno;

target.thumb=source.thumb;

target.id=source.id;

target.type=source.type;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppVoiceThumb> CREATOR = new Creator<AppVoiceThumb>() {
        @Override
        public AppVoiceThumb createFromParcel(Parcel in) {
            return new AppVoiceThumb(in);
        }

        @Override
        public AppVoiceThumb[] newArray(int size) {
            return new AppVoiceThumb[size];
        }
    };
}
