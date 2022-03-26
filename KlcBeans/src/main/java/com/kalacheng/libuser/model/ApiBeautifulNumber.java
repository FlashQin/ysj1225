package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP购买贵宾席
 */
public class ApiBeautifulNumber  implements Parcelable
{
 public ApiBeautifulNumber()
{
}

/**
 * null
 */
public long serialVersionUID;

/**
 * 用户头像
 */
public String avatar;

/**
 * 用户名
 */
public String username;

/**
 * 用户id
 */
public long uid;

/**
 * 靓号号码
 */
public String number;

   public ApiBeautifulNumber(Parcel in) 
{
serialVersionUID=in.readLong();
avatar=in.readString();
username=in.readString();
uid=in.readLong();
number=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(serialVersionUID);
dest.writeString(avatar);
dest.writeString(username);
dest.writeLong(uid);
dest.writeString(number);

}

  public static void cloneObj(ApiBeautifulNumber source,ApiBeautifulNumber target)
{

target.serialVersionUID=source.serialVersionUID;

target.avatar=source.avatar;

target.username=source.username;

target.uid=source.uid;

target.number=source.number;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiBeautifulNumber> CREATOR = new Creator<ApiBeautifulNumber>() {
        @Override
        public ApiBeautifulNumber createFromParcel(Parcel in) {
            return new ApiBeautifulNumber(in);
        }

        @Override
        public ApiBeautifulNumber[] newArray(int size) {
            return new ApiBeautifulNumber[size];
        }
    };
}
