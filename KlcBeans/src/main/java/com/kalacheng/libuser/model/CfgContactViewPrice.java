package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 查看收费设置项
 */
public class CfgContactViewPrice  implements Parcelable
{
 public CfgContactViewPrice()
{
}

/**
 * 金额设置
 */
public double price;

/**
 * null
 */
public long id;

/**
 * 收费状态 0：不收费  1：收费开启
 */
public int state;

/**
 * 类型 1：手机号  2：微信号， 3：直播回放
 */
public int type;

/**
 * 用户ID
 */
public long userId;

   public CfgContactViewPrice(Parcel in) 
{
price=in.readDouble();
id=in.readLong();
state=in.readInt();
type=in.readInt();
userId=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeDouble(price);
dest.writeLong(id);
dest.writeInt(state);
dest.writeInt(type);
dest.writeLong(userId);

}

  public static void cloneObj(CfgContactViewPrice source,CfgContactViewPrice target)
{

target.price=source.price;

target.id=source.id;

target.state=source.state;

target.type=source.type;

target.userId=source.userId;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CfgContactViewPrice> CREATOR = new Creator<CfgContactViewPrice>() {
        @Override
        public CfgContactViewPrice createFromParcel(Parcel in) {
            return new CfgContactViewPrice(in);
        }

        @Override
        public CfgContactViewPrice[] newArray(int size) {
            return new CfgContactViewPrice[size];
        }
    };
}
