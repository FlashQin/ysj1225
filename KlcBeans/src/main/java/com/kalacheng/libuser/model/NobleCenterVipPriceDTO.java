package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 贵族中心贵族价格
 */
public class NobleCenterVipPriceDTO  implements Parcelable
{
 public NobleCenterVipPriceDTO()
{
}

/**
 * ios价格
 */
public double iosCoin;

/**
 * 苹果项目id
 */
public String iosId;

/**
 * 支付方式：0RMB，1金币，2RMB/金币
 */
public int payType;

/**
 * 有效时间（月）
 */
public int endDay;

/**
 * 安卓价格
 */
public double androidCoin;

/**
 * 贵族对应的价格id
 */
public long id;

/**
 * 赠送金币
 */
public double sendCoin;

/**
 * 金币价格
 */
public double coin;

   public NobleCenterVipPriceDTO(Parcel in) 
{
iosCoin=in.readDouble();
iosId=in.readString();
payType=in.readInt();
endDay=in.readInt();
androidCoin=in.readDouble();
id=in.readLong();
sendCoin=in.readDouble();
coin=in.readDouble();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeDouble(iosCoin);
dest.writeString(iosId);
dest.writeInt(payType);
dest.writeInt(endDay);
dest.writeDouble(androidCoin);
dest.writeLong(id);
dest.writeDouble(sendCoin);
dest.writeDouble(coin);

}

  public static void cloneObj(NobleCenterVipPriceDTO source,NobleCenterVipPriceDTO target)
{

target.iosCoin=source.iosCoin;

target.iosId=source.iosId;

target.payType=source.payType;

target.endDay=source.endDay;

target.androidCoin=source.androidCoin;

target.id=source.id;

target.sendCoin=source.sendCoin;

target.coin=source.coin;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NobleCenterVipPriceDTO> CREATOR = new Creator<NobleCenterVipPriceDTO>() {
        @Override
        public NobleCenterVipPriceDTO createFromParcel(Parcel in) {
            return new NobleCenterVipPriceDTO(in);
        }

        @Override
        public NobleCenterVipPriceDTO[] newArray(int size) {
            return new NobleCenterVipPriceDTO[size];
        }
    };
}
