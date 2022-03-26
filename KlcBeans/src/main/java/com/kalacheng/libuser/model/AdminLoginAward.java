package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 连续登录奖励
 */
public class AdminLoginAward  implements Parcelable
{
 public AdminLoginAward()
{
}

/**
 * 添加时间
 */
public Date addtime;

/**
 * null
 */
public long id;

/**
 * 连续登录天数
 */
public int day;

/**
 * 上次登录时间
 */
public String lastLoginDay;

/**
 * 奖励金币
 */
public int coin;

/**
 * 更新时间
 */
public Date uptime;

   public AdminLoginAward(Parcel in) 
{
addtime=new Date( in.readLong());
id=in.readLong();
day=in.readInt();
lastLoginDay=in.readString();
coin=in.readInt();
uptime=new Date( in.readLong());

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeLong(id);
dest.writeInt(day);
dest.writeString(lastLoginDay);
dest.writeInt(coin);
dest.writeLong(uptime==null?0:uptime.getTime());

}

  public static void cloneObj(AdminLoginAward source,AdminLoginAward target)
{

target.addtime=source.addtime;

target.id=source.id;

target.day=source.day;

target.lastLoginDay=source.lastLoginDay;

target.coin=source.coin;

target.uptime=source.uptime;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdminLoginAward> CREATOR = new Creator<AdminLoginAward>() {
        @Override
        public AdminLoginAward createFromParcel(Parcel in) {
            return new AdminLoginAward(in);
        }

        @Override
        public AdminLoginAward[] newArray(int size) {
            return new AdminLoginAward[size];
        }
    };
}
