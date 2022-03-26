package com.kalacheng.libbas.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 单个字符串封装类
 */
public class SingleString  implements Parcelable
{
 public SingleString()
{
}

/**
 * 值
 */
public String value;

   public SingleString(Parcel in) 
{
value=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(value);

}

  public static void cloneObj(SingleString source,SingleString target)
{

target.value=source.value;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SingleString> CREATOR = new Creator<SingleString>() {
        @Override
        public SingleString createFromParcel(Parcel in) {
            return new SingleString(in);
        }

        @Override
        public SingleString[] newArray(int size) {
            return new SingleString[size];
        }
    };
}
