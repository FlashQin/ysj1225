package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 用户信息
 */
public class SimpleUserDTO  implements Parcelable
{
 public SimpleUserDTO()
{
}

/**
 * 小头像
 */
public String avatarThumb;

/**
 * null
 */
public long userid;

/**
 * 昵称
 */
public String username;

   public SimpleUserDTO(Parcel in) 
{
avatarThumb=in.readString();
userid=in.readLong();
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(avatarThumb);
dest.writeLong(userid);
dest.writeString(username);

}

  public static void cloneObj(SimpleUserDTO source,SimpleUserDTO target)
{

target.avatarThumb=source.avatarThumb;

target.userid=source.userid;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SimpleUserDTO> CREATOR = new Creator<SimpleUserDTO>() {
        @Override
        public SimpleUserDTO createFromParcel(Parcel in) {
            return new SimpleUserDTO(in);
        }

        @Override
        public SimpleUserDTO[] newArray(int size) {
            return new SimpleUserDTO[size];
        }
    };
}
