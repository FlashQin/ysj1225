package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 收货地址表
 */
public class ShopAddress  implements Parcelable
{
 public ShopAddress()
{
}

/**
 * 区
 */
public String area;

/**
 * 用户id
 */
public long uid;

/**
 * 是否默认1默认0非默认
 */
public int isDefault;

/**
 * 详情地址
 */
public String address;

/**
 * 添加时间
 */
public Date addTime;

/**
 * 市
 */
public String city;

/**
 * 收货人手机号
 */
public String phoneNum;

/**
 * null
 */
public long id;

/**
 * 省
 */
public String pro;

/**
 * 收货人姓名
 */
public String userName;

   public ShopAddress(Parcel in) 
{
area=in.readString();
uid=in.readLong();
isDefault=in.readInt();
address=in.readString();
addTime=new Date( in.readLong());
city=in.readString();
phoneNum=in.readString();
id=in.readLong();
pro=in.readString();
userName=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(area);
dest.writeLong(uid);
dest.writeInt(isDefault);
dest.writeString(address);
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeString(city);
dest.writeString(phoneNum);
dest.writeLong(id);
dest.writeString(pro);
dest.writeString(userName);

}

  public static void cloneObj(ShopAddress source,ShopAddress target)
{

target.area=source.area;

target.uid=source.uid;

target.isDefault=source.isDefault;

target.address=source.address;

target.addTime=source.addTime;

target.city=source.city;

target.phoneNum=source.phoneNum;

target.id=source.id;

target.pro=source.pro;

target.userName=source.userName;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopAddress> CREATOR = new Creator<ShopAddress>() {
        @Override
        public ShopAddress createFromParcel(Parcel in) {
            return new ShopAddress(in);
        }

        @Override
        public ShopAddress[] newArray(int size) {
            return new ShopAddress[size];
        }
    };
}
