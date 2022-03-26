package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 音乐列表
 */
public class AppMusicDTO  implements Parcelable
{
 public AppMusicDTO()
{
}

/**
 * 封面
 */
public String cover;

/**
 * 是否添加：1已添加，0未添加
 */
public int added;

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

   public AppMusicDTO(Parcel in) 
{
cover=in.readString();
added=in.readInt();
author=in.readString();
musicUrl=in.readString();
name=in.readString();
id=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(cover);
dest.writeInt(added);
dest.writeString(author);
dest.writeString(musicUrl);
dest.writeString(name);
dest.writeLong(id);

}

  public static void cloneObj(AppMusicDTO source,AppMusicDTO target)
{

target.cover=source.cover;

target.added=source.added;

target.author=source.author;

target.musicUrl=source.musicUrl;

target.name=source.name;

target.id=source.id;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppMusicDTO> CREATOR = new Creator<AppMusicDTO>() {
        @Override
        public AppMusicDTO createFromParcel(Parcel in) {
            return new AppMusicDTO(in);
        }

        @Override
        public AppMusicDTO[] newArray(int size) {
            return new AppMusicDTO[size];
        }
    };
}
