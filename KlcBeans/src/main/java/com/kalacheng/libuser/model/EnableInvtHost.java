package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 1v1v7可邀请的主播返回信息
 */
public class EnableInvtHost  implements Parcelable
{
 public EnableInvtHost()
{
}

/**
 * 视频通话时间金币/min
 */
public double videoCoin;

/**
 * 邀请状态 0未被邀请 3被邀请中
 */
public int invited;

/**
 * 用户头像
 */
public String avatar;

/**
 * 主播用户id
 */
public long userid;

/**
 * 主播用户名
 */
public String username;

   public EnableInvtHost(Parcel in) 
{
videoCoin=in.readDouble();
invited=in.readInt();
avatar=in.readString();
userid=in.readLong();
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeDouble(videoCoin);
dest.writeInt(invited);
dest.writeString(avatar);
dest.writeLong(userid);
dest.writeString(username);

}

  public static void cloneObj(EnableInvtHost source,EnableInvtHost target)
{

target.videoCoin=source.videoCoin;

target.invited=source.invited;

target.avatar=source.avatar;

target.userid=source.userid;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EnableInvtHost> CREATOR = new Creator<EnableInvtHost>() {
        @Override
        public EnableInvtHost createFromParcel(Parcel in) {
            return new EnableInvtHost(in);
        }

        @Override
        public EnableInvtHost[] newArray(int size) {
            return new EnableInvtHost[size];
        }
    };
}
