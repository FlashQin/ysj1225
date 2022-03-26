package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 官方消息
 */
public class AppOfficialNewsDTO  implements Parcelable
{
 public AppOfficialNewsDTO()
{
}

/**
 * 发布时间
 */
public Date publishTime;

/**
 * 是否已读：1已读，0未读
 */
public int isRead;

/**
 * 图片
 */
public String logo;

/**
 * null
 */
public long id;

/**
 * 消息标题
 */
public String title;

/**
 * 消息内容
 */
public String content;

/**
 * 消息简介
 */
public String introduction;

   public AppOfficialNewsDTO(Parcel in) 
{
publishTime=new Date( in.readLong());
isRead=in.readInt();
logo=in.readString();
id=in.readLong();
title=in.readString();
content=in.readString();
introduction=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(publishTime==null?0:publishTime.getTime());
dest.writeInt(isRead);
dest.writeString(logo);
dest.writeLong(id);
dest.writeString(title);
dest.writeString(content);
dest.writeString(introduction);

}

  public static void cloneObj(AppOfficialNewsDTO source,AppOfficialNewsDTO target)
{

target.publishTime=source.publishTime;

target.isRead=source.isRead;

target.logo=source.logo;

target.id=source.id;

target.title=source.title;

target.content=source.content;

target.introduction=source.introduction;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppOfficialNewsDTO> CREATOR = new Creator<AppOfficialNewsDTO>() {
        @Override
        public AppOfficialNewsDTO createFromParcel(Parcel in) {
            return new AppOfficialNewsDTO(in);
        }

        @Override
        public AppOfficialNewsDTO[] newArray(int size) {
            return new AppOfficialNewsDTO[size];
        }
    };
}
