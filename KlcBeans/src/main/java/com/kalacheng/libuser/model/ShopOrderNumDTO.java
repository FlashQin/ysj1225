package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 订单数量信息
 */
public class ShopOrderNumDTO  implements Parcelable
{
 public ShopOrderNumDTO()
{
}

/**
 * 已完成
 */
public int finishedNum;

/**
 * 待收货
 */
public int toBeReceivedNum;

/**
 * 待付款
 */
public int toBePayNum;

/**
 * 待发货
 */
public int toBeDeliveredNum;

/**
 * 退货
 */
public int cancelGoodsNum;

   public ShopOrderNumDTO(Parcel in) 
{
finishedNum=in.readInt();
toBeReceivedNum=in.readInt();
toBePayNum=in.readInt();
toBeDeliveredNum=in.readInt();
cancelGoodsNum=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(finishedNum);
dest.writeInt(toBeReceivedNum);
dest.writeInt(toBePayNum);
dest.writeInt(toBeDeliveredNum);
dest.writeInt(cancelGoodsNum);

}

  public static void cloneObj(ShopOrderNumDTO source,ShopOrderNumDTO target)
{

target.finishedNum=source.finishedNum;

target.toBeReceivedNum=source.toBeReceivedNum;

target.toBePayNum=source.toBePayNum;

target.toBeDeliveredNum=source.toBeDeliveredNum;

target.cancelGoodsNum=source.cancelGoodsNum;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopOrderNumDTO> CREATOR = new Creator<ShopOrderNumDTO>() {
        @Override
        public ShopOrderNumDTO createFromParcel(Parcel in) {
            return new ShopOrderNumDTO(in);
        }

        @Override
        public ShopOrderNumDTO[] newArray(int size) {
            return new ShopOrderNumDTO[size];
        }
    };
}
