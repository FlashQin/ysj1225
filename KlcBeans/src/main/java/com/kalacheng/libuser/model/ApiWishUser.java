package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP心愿单用户
 */
public class ApiWishUser  implements Parcelable
{
 public ApiWishUser()
{
}

/**
 * 数量
 */
public int number;

/**
 * 用户id
 */
public long uid;

/**
 * 头像
 */
public String avatar;

   public ApiWishUser(Parcel in) 
{
number=in.readInt();
uid=in.readLong();
avatar=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(number);
dest.writeLong(uid);
dest.writeString(avatar);

}

  public static void cloneObj(ApiWishUser source,ApiWishUser target)
{

target.number=source.number;

target.uid=source.uid;

target.avatar=source.avatar;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiWishUser> CREATOR = new Creator<ApiWishUser>() {
        @Override
        public ApiWishUser createFromParcel(Parcel in) {
            return new ApiWishUser(in);
        }

        @Override
        public ApiWishUser[] newArray(int size) {
            return new ApiWishUser[size];
        }
    };
}
