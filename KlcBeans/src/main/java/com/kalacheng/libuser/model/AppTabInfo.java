package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 后台管理用户信息表
 */
public class AppTabInfo  implements Parcelable
{
 public AppTabInfo()
{
}

/**
 * 标签类型ID
 */
public long tabTypeId;

/**
 * tab名称
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
 * tab描述
 */
public String desr;

/**
 * tab字体颜色
 */
public String fontColor;

   public AppTabInfo(Parcel in) 
{
tabTypeId=in.readLong();
name=in.readString();
id=in.readLong();
sort=in.readInt();
desr=in.readString();
fontColor=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(tabTypeId);
dest.writeString(name);
dest.writeLong(id);
dest.writeInt(sort);
dest.writeString(desr);
dest.writeString(fontColor);

}

  public static void cloneObj(AppTabInfo source,AppTabInfo target)
{

target.tabTypeId=source.tabTypeId;

target.name=source.name;

target.id=source.id;

target.sort=source.sort;

target.desr=source.desr;

target.fontColor=source.fontColor;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppTabInfo> CREATOR = new Creator<AppTabInfo>() {
        @Override
        public AppTabInfo createFromParcel(Parcel in) {
            return new AppTabInfo(in);
        }

        @Override
        public AppTabInfo[] newArray(int size) {
            return new AppTabInfo[size];
        }
    };
}
