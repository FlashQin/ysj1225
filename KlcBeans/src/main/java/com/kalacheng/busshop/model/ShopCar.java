package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 购物车表
 */
public class ShopCar  implements Parcelable
{
 public ShopCar()
{
}

/**
 * sku组合名
 */
public String skuName;

/**
 * 用户id
 */
public long uid;

/**
 * 添加时间
 */
public Date addTime;

/**
 * 商品id
 */
public long goodsId;

/**
 * 商品价格(单价)
 */
public double goodsPrice;

/**
 * 商家id
 */
public long businessId;

/**
 * null
 */
public long id;

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
 * 商品图片
 */
public String goodsPicture;

   public ShopCar(Parcel in) 
{
skuName=in.readString();
uid=in.readLong();
addTime=new Date( in.readLong());
goodsId=in.readLong();
goodsPrice=in.readDouble();
businessId=in.readLong();
id=in.readLong();
composeId=in.readLong();
goodsName=in.readString();
goodsNum=in.readInt();
goodsPicture=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(skuName);
dest.writeLong(uid);
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeLong(goodsId);
dest.writeDouble(goodsPrice);
dest.writeLong(businessId);
dest.writeLong(id);
dest.writeLong(composeId);
dest.writeString(goodsName);
dest.writeInt(goodsNum);
dest.writeString(goodsPicture);

}

  public static void cloneObj(ShopCar source,ShopCar target)
{

target.skuName=source.skuName;

target.uid=source.uid;

target.addTime=source.addTime;

target.goodsId=source.goodsId;

target.goodsPrice=source.goodsPrice;

target.businessId=source.businessId;

target.id=source.id;

target.composeId=source.composeId;

target.goodsName=source.goodsName;

target.goodsNum=source.goodsNum;

target.goodsPicture=source.goodsPicture;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopCar> CREATOR = new Creator<ShopCar>() {
        @Override
        public ShopCar createFromParcel(Parcel in) {
            return new ShopCar(in);
        }

        @Override
        public ShopCar[] newArray(int size) {
            return new ShopCar[size];
        }
    };
}
