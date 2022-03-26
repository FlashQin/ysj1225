package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 获取商家订单数据
 */
public class ShopBusinessOrderInfoDTO  implements Parcelable
{
 public ShopBusinessOrderInfoDTO()
{
}

/**
 * 卖货收入
 */
public double income;

/**
 * 订单金额
 */
public double price;

/**
 * 订单数量
 */
public int count;

   public ShopBusinessOrderInfoDTO(Parcel in) 
{
income=in.readDouble();
price=in.readDouble();
count=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeDouble(income);
dest.writeDouble(price);
dest.writeInt(count);

}

  public static void cloneObj(ShopBusinessOrderInfoDTO source,ShopBusinessOrderInfoDTO target)
{

target.income=source.income;

target.price=source.price;

target.count=source.count;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopBusinessOrderInfoDTO> CREATOR = new Creator<ShopBusinessOrderInfoDTO>() {
        @Override
        public ShopBusinessOrderInfoDTO createFromParcel(Parcel in) {
            return new ShopBusinessOrderInfoDTO(in);
        }

        @Override
        public ShopBusinessOrderInfoDTO[] newArray(int size) {
            return new ShopBusinessOrderInfoDTO[size];
        }
    };
}
