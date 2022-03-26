package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP直播间踢人
 */
public class ApiKick  implements Parcelable
{
 public ApiKick()
{
}

/**
 * 用户id
 */
public long uid;

/**
 * 会员等级
 */
public String userLevel;

/**
 * 个性签名
 */
public String signature;

/**
 * 性别；0：保密，1：男；2：女
 */
public int sex;

/**
 * 踢人到期时间(时间戳)
 */
public long endtime;

/**
 * 用户头像
 */
public String avatar;

/**
 * 主播等级
 */
public String anchorLevel;

/**
 * 用户名
 */
public String username;

   public ApiKick(Parcel in) 
{
uid=in.readLong();
userLevel=in.readString();
signature=in.readString();
sex=in.readInt();
endtime=in.readLong();
avatar=in.readString();
anchorLevel=in.readString();
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(uid);
dest.writeString(userLevel);
dest.writeString(signature);
dest.writeInt(sex);
dest.writeLong(endtime);
dest.writeString(avatar);
dest.writeString(anchorLevel);
dest.writeString(username);

}

  public static void cloneObj(ApiKick source,ApiKick target)
{

target.uid=source.uid;

target.userLevel=source.userLevel;

target.signature=source.signature;

target.sex=source.sex;

target.endtime=source.endtime;

target.avatar=source.avatar;

target.anchorLevel=source.anchorLevel;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiKick> CREATOR = new Creator<ApiKick>() {
        @Override
        public ApiKick createFromParcel(Parcel in) {
            return new ApiKick(in);
        }

        @Override
        public ApiKick[] newArray(int size) {
            return new ApiKick[size];
        }
    };
}
