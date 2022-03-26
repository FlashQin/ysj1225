package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 勋章
 */
public class AppArea  implements Parcelable
{
 public AppArea()
{
}

/**
 * 城市编号, 咱不管， 为后续做考虑
 */
public int code;

/**
 * 添加时间
 */
public Date addTime;

/**
 * 城市名称（带省，市，区单位）
 */
public String name;

/**
 * null
 */
public long id;

/**
 * 城市简称， 不带省市区
 */
public String shortName;

/**
 * 是否热门城市 0：否 1：是
 */
public int isHot;

   public AppArea(Parcel in) 
{
code=in.readInt();
addTime=new Date( in.readLong());
name=in.readString();
id=in.readLong();
shortName=in.readString();
isHot=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(code);
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeString(name);
dest.writeLong(id);
dest.writeString(shortName);
dest.writeInt(isHot);

}

  public static void cloneObj(AppArea source,AppArea target)
{

target.code=source.code;

target.addTime=source.addTime;

target.name=source.name;

target.id=source.id;

target.shortName=source.shortName;

target.isHot=source.isHot;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppArea> CREATOR = new Creator<AppArea>() {
        @Override
        public AppArea createFromParcel(Parcel in) {
            return new AppArea(in);
        }

        @Override
        public AppArea[] newArray(int size) {
            return new AppArea[size];
        }
    };
}
