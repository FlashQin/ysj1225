package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 直播间靓号表
 */
public class AppLiang  implements Parcelable
{
 public AppLiang()
{
}

/**
 * 序号
 */
public int orderno;

/**
 * 添加时间
 */
public Date addtime;

/**
 * 靓号长度
 */
public int length;

/**
 * 名称
 */
public String name;

/**
 * 购买时间
 */
public Date buytime;

/**
 * null
 */
public long id;

/**
 * 启用状态  0:未启用  1：正在启用
 */
public int state;

/**
 * 购买用户ID
 */
public long userid;

/**
 * 价格
 */
public double coin;

/**
 * 靓号销售状态
 */
public int status;

   public AppLiang(Parcel in) 
{
orderno=in.readInt();
addtime=new Date( in.readLong());
length=in.readInt();
name=in.readString();
buytime=new Date( in.readLong());
id=in.readLong();
state=in.readInt();
userid=in.readLong();
coin=in.readDouble();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(orderno);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeInt(length);
dest.writeString(name);
dest.writeLong(buytime==null?0:buytime.getTime());
dest.writeLong(id);
dest.writeInt(state);
dest.writeLong(userid);
dest.writeDouble(coin);
dest.writeInt(status);

}

  public static void cloneObj(AppLiang source,AppLiang target)
{

target.orderno=source.orderno;

target.addtime=source.addtime;

target.length=source.length;

target.name=source.name;

target.buytime=source.buytime;

target.id=source.id;

target.state=source.state;

target.userid=source.userid;

target.coin=source.coin;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppLiang> CREATOR = new Creator<AppLiang>() {
        @Override
        public AppLiang createFromParcel(Parcel in) {
            return new AppLiang(in);
        }

        @Override
        public AppLiang[] newArray(int size) {
            return new AppLiang[size];
        }
    };
}
