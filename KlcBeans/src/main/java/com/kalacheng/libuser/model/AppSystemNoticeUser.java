package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * app系统通知用户表
 */
public class AppSystemNoticeUser  implements Parcelable
{
 public AppSystemNoticeUser()
{
}

/**
 * 创建时间
 */
public Date addtime;

/**
 * null
 */
public long id;

/**
 * 通知标题
 */
public String title;

/**
 * 用户id
 */
public long userId;

/**
 * 通知内容
 */
public String content;

/**
 * 通知id
 */
public long noticeId;

/**
 * 是否已读0已读1未读
 */
public int status;

   public AppSystemNoticeUser(Parcel in) 
{
addtime=new Date( in.readLong());
id=in.readLong();
title=in.readString();
userId=in.readLong();
content=in.readString();
noticeId=in.readLong();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeLong(id);
dest.writeString(title);
dest.writeLong(userId);
dest.writeString(content);
dest.writeLong(noticeId);
dest.writeInt(status);

}

  public static void cloneObj(AppSystemNoticeUser source,AppSystemNoticeUser target)
{

target.addtime=source.addtime;

target.id=source.id;

target.title=source.title;

target.userId=source.userId;

target.content=source.content;

target.noticeId=source.noticeId;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppSystemNoticeUser> CREATOR = new Creator<AppSystemNoticeUser>() {
        @Override
        public AppSystemNoticeUser createFromParcel(Parcel in) {
            return new AppSystemNoticeUser(in);
        }

        @Override
        public AppSystemNoticeUser[] newArray(int size) {
            return new AppSystemNoticeUser[size];
        }
    };
}
