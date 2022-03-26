package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP守护列表用户信息
 */
public class ApiGuardEntityChild  implements Parcelable
{
 public ApiGuardEntityChild()
{
}

/**
 * 用户id
 */
public long uid;

/**
 * 时长类型，0天，1月，2年
 */
public int lengthType;

/**
 * 用户等级
 */
public String userLevel;

/**
 * 性别1男2女
 */
public int sex;

/**
 * 用户头像
 */
public String avatar;

/**
 * 用户名称
 */
public String userName;

/**
 * 周贡献魅力值
 */
public int coin;

   public ApiGuardEntityChild(Parcel in) 
{
uid=in.readLong();
lengthType=in.readInt();
userLevel=in.readString();
sex=in.readInt();
avatar=in.readString();
userName=in.readString();
coin=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(uid);
dest.writeInt(lengthType);
dest.writeString(userLevel);
dest.writeInt(sex);
dest.writeString(avatar);
dest.writeString(userName);
dest.writeInt(coin);

}

  public static void cloneObj(ApiGuardEntityChild source,ApiGuardEntityChild target)
{

target.uid=source.uid;

target.lengthType=source.lengthType;

target.userLevel=source.userLevel;

target.sex=source.sex;

target.avatar=source.avatar;

target.userName=source.userName;

target.coin=source.coin;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiGuardEntityChild> CREATOR = new Creator<ApiGuardEntityChild>() {
        @Override
        public ApiGuardEntityChild createFromParcel(Parcel in) {
            return new ApiGuardEntityChild(in);
        }

        @Override
        public ApiGuardEntityChild[] newArray(int size) {
            return new ApiGuardEntityChild[size];
        }
    };
}
