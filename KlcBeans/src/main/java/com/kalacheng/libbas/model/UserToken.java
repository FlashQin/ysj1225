package com.kalacheng.libbas.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * token-userid 返回封装类
 */
public class UserToken  implements Parcelable
{
 public UserToken()
{
}

/**
 * null
 */
public long userid;

/**
 * null
 */
public String UserToken;

   public UserToken(Parcel in) 
{
userid=in.readLong();
UserToken=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(userid);
dest.writeString(UserToken);

}

  public static void cloneObj(UserToken source,UserToken target)
{

target.userid=source.userid;

target.UserToken=source.UserToken;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserToken> CREATOR = new Creator<UserToken>() {
        @Override
        public UserToken createFromParcel(Parcel in) {
            return new UserToken(in);
        }

        @Override
        public UserToken[] newArray(int size) {
            return new UserToken[size];
        }
    };
}
