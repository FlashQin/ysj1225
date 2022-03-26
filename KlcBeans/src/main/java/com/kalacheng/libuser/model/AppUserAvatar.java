package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 用户基础信息表
 */
public class AppUserAvatar  implements Parcelable
{
 public AppUserAvatar()
{
}

/**
 * 用户头像
 */
public String avatar;

/**
 * 用户ID
 */
public long userid;

   public AppUserAvatar(Parcel in) 
{
avatar=in.readString();
userid=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(avatar);
dest.writeLong(userid);

}

  public static void cloneObj(AppUserAvatar source,AppUserAvatar target)
{

target.avatar=source.avatar;

target.userid=source.userid;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppUserAvatar> CREATOR = new Creator<AppUserAvatar>() {
        @Override
        public AppUserAvatar createFromParcel(Parcel in) {
            return new AppUserAvatar(in);
        }

        @Override
        public AppUserAvatar[] newArray(int size) {
            return new AppUserAvatar[size];
        }
    };
}
