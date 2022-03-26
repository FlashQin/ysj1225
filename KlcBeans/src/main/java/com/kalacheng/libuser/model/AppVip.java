package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP-VIP
 */
public class AppVip  implements Parcelable
{
 public AppVip()
{
}

/**
 * 序号
 */
public int orderno;

/**
 * 时长（月）
 */
public int length;

/**
 * 赠送金币
 */
public double sendCoin;

/**
 * 是否启用 0：不启用  1：启用
 */
public int isTip;

/**
 * ios-价格
 */
public double iosCoin;

/**
 * 苹果项目ID
 */
public String iosId;

/**
 * 支付方式：0RMB，1金币，2RMB/金币
 */
public int payType;

/**
 * 添加时间
 */
public Date addtime;

/**
 * 贵族等级
 */
public int grade;

/**
 * 名称
 */
public String name;

/**
 * 安卓-价格
 */
public double androidCoin;

/**
 * null
 */
public long id;

/**
 * 金币-价格
 */
public double coin;

   public AppVip(Parcel in) 
{
orderno=in.readInt();
length=in.readInt();
sendCoin=in.readDouble();
isTip=in.readInt();
iosCoin=in.readDouble();
iosId=in.readString();
payType=in.readInt();
addtime=new Date( in.readLong());
grade=in.readInt();
name=in.readString();
androidCoin=in.readDouble();
id=in.readLong();
coin=in.readDouble();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(orderno);
dest.writeInt(length);
dest.writeDouble(sendCoin);
dest.writeInt(isTip);
dest.writeDouble(iosCoin);
dest.writeString(iosId);
dest.writeInt(payType);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeInt(grade);
dest.writeString(name);
dest.writeDouble(androidCoin);
dest.writeLong(id);
dest.writeDouble(coin);

}

  public static void cloneObj(AppVip source,AppVip target)
{

target.orderno=source.orderno;

target.length=source.length;

target.sendCoin=source.sendCoin;

target.isTip=source.isTip;

target.iosCoin=source.iosCoin;

target.iosId=source.iosId;

target.payType=source.payType;

target.addtime=source.addtime;

target.grade=source.grade;

target.name=source.name;

target.androidCoin=source.androidCoin;

target.id=source.id;

target.coin=source.coin;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppVip> CREATOR = new Creator<AppVip>() {
        @Override
        public AppVip createFromParcel(Parcel in) {
            return new AppVip(in);
        }

        @Override
        public AppVip[] newArray(int size) {
            return new AppVip[size];
        }
    };
}
