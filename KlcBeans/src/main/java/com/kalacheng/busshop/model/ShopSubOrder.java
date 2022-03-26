package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 子商品订单表
 */
public class ShopSubOrder  implements Parcelable
{
 public ShopSubOrder()
{
}

/**
 * 添加时间
 */
public Date addTime;

/**
 * 商品id
 */
public long goodsId;

/**
 * 订单号
 */
public String orderNum;

/**
 * 备注
 */
public String remake;

/**
 * 商品简介图片地址
 */
public String goodsPicture;

/**
 * 物流名称
 */
public String logisticsName;

/**
 * sku组合名称
 */
public String skuName;

/**
 * 商家订单id
 */
public long businessOrderId;

/**
 * 商品价格
 */
public double goodsPrice;

/**
 * null
 */
public long id;

/**
 * 物流单号
 */
public String logisticsNum;

/**
 * 父订单(支付订单)id
 */
public long payId;

/**
 * skuId
 */
public long composeId;

/**
 * 商品名称
 */
public String goodsName;

/**
 * 商品数量
 */
public int goodsNum;

/**
 * 订单状态 1.待付款 2.待发货 3.待收货 4. 已完成 5. 已取消
 */
public int status;

   public ShopSubOrder(Parcel in) 
{
addTime=new Date( in.readLong());
goodsId=in.readLong();
orderNum=in.readString();
remake=in.readString();
goodsPicture=in.readString();
logisticsName=in.readString();
skuName=in.readString();
businessOrderId=in.readLong();
goodsPrice=in.readDouble();
id=in.readLong();
logisticsNum=in.readString();
payId=in.readLong();
composeId=in.readLong();
goodsName=in.readString();
goodsNum=in.readInt();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeLong(goodsId);
dest.writeString(orderNum);
dest.writeString(remake);
dest.writeString(goodsPicture);
dest.writeString(logisticsName);
dest.writeString(skuName);
dest.writeLong(businessOrderId);
dest.writeDouble(goodsPrice);
dest.writeLong(id);
dest.writeString(logisticsNum);
dest.writeLong(payId);
dest.writeLong(composeId);
dest.writeString(goodsName);
dest.writeInt(goodsNum);
dest.writeInt(status);

}

  public static void cloneObj(ShopSubOrder source,ShopSubOrder target)
{

target.addTime=source.addTime;

target.goodsId=source.goodsId;

target.orderNum=source.orderNum;

target.remake=source.remake;

target.goodsPicture=source.goodsPicture;

target.logisticsName=source.logisticsName;

target.skuName=source.skuName;

target.businessOrderId=source.businessOrderId;

target.goodsPrice=source.goodsPrice;

target.id=source.id;

target.logisticsNum=source.logisticsNum;

target.payId=source.payId;

target.composeId=source.composeId;

target.goodsName=source.goodsName;

target.goodsNum=source.goodsNum;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopSubOrder> CREATOR = new Creator<ShopSubOrder>() {
        @Override
        public ShopSubOrder createFromParcel(Parcel in) {
            return new ShopSubOrder(in);
        }

        @Override
        public ShopSubOrder[] newArray(int size) {
            return new ShopSubOrder[size];
        }
    };
}
