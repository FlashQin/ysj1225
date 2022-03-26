package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 用户svip套餐表
 */
public class AppSvipPackage  implements Parcelable
{
 public AppSvipPackage()
{
}

/**
 * 消费类型 0:金币 1:RMB
 */
public int coinType;

/**
 * 苹果项目id
 */
public String iosId;

/**
 * 序号
 */
public int orderno;

/**
 * 充值类型 1:Android  2:IOS
 */
public int chargeType;

/**
 * null
 */
public long pid;

/**
 * 套餐时长的显示数字
 */
public int time;

/**
 * 套餐时长的单位
 */
public String timeUnits;

/**
 * 开通价格
 */
public double coin;

/**
 * 对应app_svip表的主键
 */
public long sid;

   public AppSvipPackage(Parcel in) 
{
coinType=in.readInt();
iosId=in.readString();
orderno=in.readInt();
chargeType=in.readInt();
pid=in.readLong();
time=in.readInt();
timeUnits=in.readString();
coin=in.readDouble();
sid=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(coinType);
dest.writeString(iosId);
dest.writeInt(orderno);
dest.writeInt(chargeType);
dest.writeLong(pid);
dest.writeInt(time);
dest.writeString(timeUnits);
dest.writeDouble(coin);
dest.writeLong(sid);

}

  public static void cloneObj(AppSvipPackage source,AppSvipPackage target)
{

target.coinType=source.coinType;

target.iosId=source.iosId;

target.orderno=source.orderno;

target.chargeType=source.chargeType;

target.pid=source.pid;

target.time=source.time;

target.timeUnits=source.timeUnits;

target.coin=source.coin;

target.sid=source.sid;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppSvipPackage> CREATOR = new Creator<AppSvipPackage>() {
        @Override
        public AppSvipPackage createFromParcel(Parcel in) {
            return new AppSvipPackage(in);
        }

        @Override
        public AppSvipPackage[] newArray(int size) {
            return new AppSvipPackage[size];
        }
    };
}
