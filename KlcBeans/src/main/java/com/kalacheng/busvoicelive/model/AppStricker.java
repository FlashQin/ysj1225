package com.kalacheng.busvoicelive.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 表情包
 */
public class AppStricker  implements Parcelable
{
 public AppStricker()
{
}

/**
 * 表情动图url地址
 */
public String gifUrl;

/**
 * 表情名称
 */
public String name;

/**
 * null
 */
public long id;

/**
 * 表情动图时长
 */
public double gifDuration;

/**
 * 表情图片url地址
 */
public String url;

   public AppStricker(Parcel in) 
{
gifUrl=in.readString();
name=in.readString();
id=in.readLong();
gifDuration=in.readDouble();
url=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(gifUrl);
dest.writeString(name);
dest.writeLong(id);
dest.writeDouble(gifDuration);
dest.writeString(url);

}

  public static void cloneObj(AppStricker source,AppStricker target)
{

target.gifUrl=source.gifUrl;

target.name=source.name;

target.id=source.id;

target.gifDuration=source.gifDuration;

target.url=source.url;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppStricker> CREATOR = new Creator<AppStricker>() {
        @Override
        public AppStricker createFromParcel(Parcel in) {
            return new AppStricker(in);
        }

        @Override
        public AppStricker[] newArray(int size) {
            return new AppStricker[size];
        }
    };
}
