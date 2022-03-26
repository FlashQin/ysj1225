package com.kalacheng.libbas.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 没有实际业务用途，在API不需要反复内容时的占位用。
 */
public class HttpNone  implements Parcelable
{
 public HttpNone()
{
}

/**
 * 没有用的东西
 */
public String no_use;

   public HttpNone(Parcel in) 
{
no_use=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(no_use);

}

  public static void cloneObj(HttpNone source,HttpNone target)
{

target.no_use=source.no_use;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HttpNone> CREATOR = new Creator<HttpNone>() {
        @Override
        public HttpNone createFromParcel(Parcel in) {
            return new HttpNone(in);
        }

        @Override
        public HttpNone[] newArray(int size) {
            return new HttpNone[size];
        }
    };
}
