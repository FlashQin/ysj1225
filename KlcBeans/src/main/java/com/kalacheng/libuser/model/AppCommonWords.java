package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 常用语
 */
public class AppCommonWords  implements Parcelable
{
 public AppCommonWords()
{
}

/**
 * 常用语内容
 */
public String name;

/**
 * null
 */
public long id;

/**
 * 常用语类型1一对一常用语
 */
public int type;

   public AppCommonWords(Parcel in) 
{
name=in.readString();
id=in.readLong();
type=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(name);
dest.writeLong(id);
dest.writeInt(type);

}

  public static void cloneObj(AppCommonWords source,AppCommonWords target)
{

target.name=source.name;

target.id=source.id;

target.type=source.type;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppCommonWords> CREATOR = new Creator<AppCommonWords>() {
        @Override
        public AppCommonWords createFromParcel(Parcel in) {
            return new AppCommonWords(in);
        }

        @Override
        public AppCommonWords[] newArray(int size) {
            return new AppCommonWords[size];
        }
    };
}
