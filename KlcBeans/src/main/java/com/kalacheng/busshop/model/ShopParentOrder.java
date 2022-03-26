package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 父商品订单表
 */
public class ShopParentOrder  implements Parcelable
{
 public ShopParentOrder()
{
}

/**
 * 用户id
 */
public long uid;

/**
 * 订单金额
 */
public double amount;

/**
 * 下单时间
 */
public Date addTime;

/**
 * 支付时间
 */
public Date payTime;

/**
 * 实付金额
 */
public double nhrAmount;

/**
 * 订单号
 */
public String orderNum;

/**
 * null
 */
public long id;

/**
 * 备注
 */
public String remake;

/**
 * 支付渠道 1.支付宝 2.微信  
 */
public long channelId;

/**
 * 支付交易号(支付宝或者微信返回)
 */
public String payOrder;

/**
 * 订单状态 1.待付款 2.付款成功 3.付款失败 
 */
public int status;

   public ShopParentOrder(Parcel in) 
{
uid=in.readLong();
amount=in.readDouble();
addTime=new Date( in.readLong());
payTime=new Date( in.readLong());
nhrAmount=in.readDouble();
orderNum=in.readString();
id=in.readLong();
remake=in.readString();
channelId=in.readLong();
payOrder=in.readString();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(uid);
dest.writeDouble(amount);
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeLong(payTime==null?0:payTime.getTime());
dest.writeDouble(nhrAmount);
dest.writeString(orderNum);
dest.writeLong(id);
dest.writeString(remake);
dest.writeLong(channelId);
dest.writeString(payOrder);
dest.writeInt(status);

}

  public static void cloneObj(ShopParentOrder source,ShopParentOrder target)
{

target.uid=source.uid;

target.amount=source.amount;

target.addTime=source.addTime;

target.payTime=source.payTime;

target.nhrAmount=source.nhrAmount;

target.orderNum=source.orderNum;

target.id=source.id;

target.remake=source.remake;

target.channelId=source.channelId;

target.payOrder=source.payOrder;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopParentOrder> CREATOR = new Creator<ShopParentOrder>() {
        @Override
        public ShopParentOrder createFromParcel(Parcel in) {
            return new ShopParentOrder(in);
        }

        @Override
        public ShopParentOrder[] newArray(int size) {
            return new ShopParentOrder[size];
        }
    };
}
