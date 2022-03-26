package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 直播频道
 */
public class AppLiveChannel  implements Parcelable
{
 public AppLiveChannel()
{
}

/**
 * 图片
 */
public String image;

/**
 * 添加时间
 */
public Date addTime;

/**
 * 直播间数量累计
 */
public int num;

/**
 * null
 */
public long id;

/**
 * 排序
 */
public int sort;

/**
 * 是否启用  0:不启用  1：启用
 */
public int isTip;

/**
 * 频道名
 */
public String title;

/**
 * 类型 1:直播 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 */
public int type;

/**
 * 1:选中 0:未选中
 */
public int isChecked;

   public AppLiveChannel(Parcel in) 
{
image=in.readString();
addTime=new Date( in.readLong());
num=in.readInt();
id=in.readLong();
sort=in.readInt();
isTip=in.readInt();
title=in.readString();
type=in.readInt();
isChecked=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(image);
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeInt(num);
dest.writeLong(id);
dest.writeInt(sort);
dest.writeInt(isTip);
dest.writeString(title);
dest.writeInt(type);
dest.writeInt(isChecked);

}

  public static void cloneObj(AppLiveChannel source,AppLiveChannel target)
{

target.image=source.image;

target.addTime=source.addTime;

target.num=source.num;

target.id=source.id;

target.sort=source.sort;

target.isTip=source.isTip;

target.title=source.title;

target.type=source.type;

target.isChecked=source.isChecked;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppLiveChannel> CREATOR = new Creator<AppLiveChannel>() {
        @Override
        public AppLiveChannel createFromParcel(Parcel in) {
            return new AppLiveChannel(in);
        }

        @Override
        public AppLiveChannel[] newArray(int size) {
            return new AppLiveChannel[size];
        }
    };
}
