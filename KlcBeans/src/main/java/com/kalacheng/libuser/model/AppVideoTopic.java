package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 短视频话题
 */
public class AppVideoTopic  implements Parcelable
{
 public AppVideoTopic()
{
}

/**
 * 主题图片
 */
public String image;

/**
 * 添加时间
 */
public Date addTime;

/**
 * 背景色
 */
public String tagStyle;

/**
 * 话题名
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
 * 是否热门0：非热门， 1热门
 */
public int isHot;

   public AppVideoTopic(Parcel in) 
{
image=in.readString();
addTime=new Date( in.readLong());
tagStyle=in.readString();
name=in.readString();
id=in.readLong();
sort=in.readInt();
isTip=in.readInt();
isHot=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(image);
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeString(tagStyle);
dest.writeString(name);
dest.writeLong(id);
dest.writeInt(sort);
dest.writeInt(isTip);
dest.writeInt(isHot);

}

  public static void cloneObj(AppVideoTopic source,AppVideoTopic target)
{

target.image=source.image;

target.addTime=source.addTime;

target.tagStyle=source.tagStyle;

target.name=source.name;

target.id=source.id;

target.sort=source.sort;

target.isTip=source.isTip;

target.isHot=source.isHot;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppVideoTopic> CREATOR = new Creator<AppVideoTopic>() {
        @Override
        public AppVideoTopic createFromParcel(Parcel in) {
            return new AppVideoTopic(in);
        }

        @Override
        public AppVideoTopic[] newArray(int size) {
            return new AppVideoTopic[size];
        }
    };
}
