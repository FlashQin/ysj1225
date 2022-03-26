package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * app系统通知条目
 */
public class AppSystemNotice  implements Parcelable
{
 public AppSystemNotice()
{
}

/**
 * 未读数
 */
public int noReadNumber;

/**
 * 该类型的通知id(多个逗号隔开)
 */
public String type_strid;

/**
 * 最新一条消息内容
 */
public String firstMsg;

/**
 * 通知标题
 */
public String title;

/**
 * 类型
 */
public int type;

/**
 * 通知内容
 */
public String content;

/**
 * 新消息时间
 */
public String addtime;

/**
 * 通知标题(前端展示)
 */
public String showTitle;

/**
 * logo
 */
public String logo;

/**
 * 该类型的通知id(多个逗号隔开)
 */
public String typeids;

/**
 * null
 */
public long id;

/**
 * 新消息时间时间戳
 */
public long addtimes;

/**
 * 是否开启0未开启1开启
 */
public int status;

   public AppSystemNotice(Parcel in) 
{
noReadNumber=in.readInt();
type_strid=in.readString();
firstMsg=in.readString();
title=in.readString();
type=in.readInt();
content=in.readString();
addtime=in.readString();
showTitle=in.readString();
logo=in.readString();
typeids=in.readString();
id=in.readLong();
addtimes=in.readLong();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(noReadNumber);
dest.writeString(type_strid);
dest.writeString(firstMsg);
dest.writeString(title);
dest.writeInt(type);
dest.writeString(content);
dest.writeString(addtime);
dest.writeString(showTitle);
dest.writeString(logo);
dest.writeString(typeids);
dest.writeLong(id);
dest.writeLong(addtimes);
dest.writeInt(status);

}

  public static void cloneObj(AppSystemNotice source,AppSystemNotice target)
{

target.noReadNumber=source.noReadNumber;

target.type_strid=source.type_strid;

target.firstMsg=source.firstMsg;

target.title=source.title;

target.type=source.type;

target.content=source.content;

target.addtime=source.addtime;

target.showTitle=source.showTitle;

target.logo=source.logo;

target.typeids=source.typeids;

target.id=source.id;

target.addtimes=source.addtimes;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppSystemNotice> CREATOR = new Creator<AppSystemNotice>() {
        @Override
        public AppSystemNotice createFromParcel(Parcel in) {
            return new AppSystemNotice(in);
        }

        @Override
        public AppSystemNotice[] newArray(int size) {
            return new AppSystemNotice[size];
        }
    };
}
