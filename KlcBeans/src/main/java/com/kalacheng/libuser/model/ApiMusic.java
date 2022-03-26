package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP音乐
 */
public class ApiMusic  implements Parcelable
{
 public ApiMusic()
{
}

/**
 * 音乐名称
 */
public String name;

/**
 * 歌曲下载进度
 */
public int progress;

/**
 * 音乐id
 */
public String id;

/**
 * 音乐歌词
 */
public String lyrics;

/**
 * 音乐歌手
 */
public String authors;

/**
 * 音乐地址
 */
public String playUrl;

   public ApiMusic(Parcel in) 
{
name=in.readString();
progress=in.readInt();
id=in.readString();
lyrics=in.readString();
authors=in.readString();
playUrl=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(name);
dest.writeInt(progress);
dest.writeString(id);
dest.writeString(lyrics);
dest.writeString(authors);
dest.writeString(playUrl);

}

  public static void cloneObj(ApiMusic source,ApiMusic target)
{

target.name=source.name;

target.progress=source.progress;

target.id=source.id;

target.lyrics=source.lyrics;

target.authors=source.authors;

target.playUrl=source.playUrl;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiMusic> CREATOR = new Creator<ApiMusic>() {
        @Override
        public ApiMusic createFromParcel(Parcel in) {
            return new ApiMusic(in);
        }

        @Override
        public ApiMusic[] newArray(int size) {
            return new ApiMusic[size];
        }
    };
}
