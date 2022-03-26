package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP获取禁言列表
 */
public class ApiShutUp  implements Parcelable
{
 public ApiShutUp()
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
 * 用户头像
 */
public String avatar;

/**
 * id
 */
public long id;

/**
 * 主播等级
 */
public String anchorLevel;

/**
 * 用户名
 */
public String username;

   public ApiShutUp(Parcel in) 
{
uid=in.readLong();
userLevel=in.readString();
signature=in.readString();
sex=in.readInt();
avatar=in.readString();
id=in.readLong();
anchorLevel=in.readString();
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(uid);
dest.writeString(userLevel);
dest.writeString(signature);
dest.writeInt(sex);
dest.writeString(avatar);
dest.writeLong(id);
dest.writeString(anchorLevel);
dest.writeString(username);

}

  public static void cloneObj(ApiShutUp source,ApiShutUp target)
{

target.uid=source.uid;

target.userLevel=source.userLevel;

target.signature=source.signature;

target.sex=source.sex;

target.avatar=source.avatar;

target.id=source.id;

target.anchorLevel=source.anchorLevel;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiShutUp> CREATOR = new Creator<ApiShutUp>() {
        @Override
        public ApiShutUp createFromParcel(Parcel in) {
            return new ApiShutUp(in);
        }

        @Override
        public ApiShutUp[] newArray(int size) {
            return new ApiShutUp[size];
        }
    };
}
