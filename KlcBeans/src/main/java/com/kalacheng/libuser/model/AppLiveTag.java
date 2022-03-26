package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP直播/一对一/短视频等标签
 */
public class AppLiveTag  implements Parcelable
{
 public AppLiveTag()
{
}

/**
 * 添加时间
 */
public Date addTime;

/**
 * 标签样式
 */
public String tagStyle;

/**
 * 直播标签名
 */
public String name;

/**
 * null
 */
public long id;

/**
 * 排序
 */
public int sort;

/**
 * 是否开启 0：关闭， 1开启
 */
public int isTip;

/**
 * 直播频道
 */
public long channel_id;

   public AppLiveTag(Parcel in) 
{
addTime=new Date( in.readLong());
tagStyle=in.readString();
name=in.readString();
id=in.readLong();
sort=in.readInt();
isTip=in.readInt();
channel_id=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeString(tagStyle);
dest.writeString(name);
dest.writeLong(id);
dest.writeInt(sort);
dest.writeInt(isTip);
dest.writeLong(channel_id);

}

  public static void cloneObj(AppLiveTag source,AppLiveTag target)
{

target.addTime=source.addTime;

target.tagStyle=source.tagStyle;

target.name=source.name;

target.id=source.id;

target.sort=source.sort;

target.isTip=source.isTip;

target.channel_id=source.channel_id;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppLiveTag> CREATOR = new Creator<AppLiveTag>() {
        @Override
        public AppLiveTag createFromParcel(Parcel in) {
            return new AppLiveTag(in);
        }

        @Override
        public AppLiveTag[] newArray(int size) {
            return new AppLiveTag[size];
        }
    };
}
