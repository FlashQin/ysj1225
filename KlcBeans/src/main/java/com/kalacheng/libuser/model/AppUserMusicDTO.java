package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 我的音乐列表
 */
public class AppUserMusicDTO  implements Parcelable
{
 public AppUserMusicDTO()
{
}

/**
 * 封面
 */
public String cover;

/**
 * 音乐id
 */
public long musicId;

/**
 * 原创/作者
 */
public String author;

/**
 * 音乐播放地址
 */
public String musicUrl;

/**
 * 音乐名称
 */
public String name;

/**
 * id
 */
public long id;

   public AppUserMusicDTO(Parcel in) 
{
cover=in.readString();
musicId=in.readLong();
author=in.readString();
musicUrl=in.readString();
name=in.readString();
id=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(cover);
dest.writeLong(musicId);
dest.writeString(author);
dest.writeString(musicUrl);
dest.writeString(name);
dest.writeLong(id);

}

  public static void cloneObj(AppUserMusicDTO source,AppUserMusicDTO target)
{

target.cover=source.cover;

target.musicId=source.musicId;

target.author=source.author;

target.musicUrl=source.musicUrl;

target.name=source.name;

target.id=source.id;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppUserMusicDTO> CREATOR = new Creator<AppUserMusicDTO>() {
        @Override
        public AppUserMusicDTO createFromParcel(Parcel in) {
            return new AppUserMusicDTO(in);
        }

        @Override
        public AppUserMusicDTO[] newArray(int size) {
            return new AppUserMusicDTO[size];
        }
    };
}
