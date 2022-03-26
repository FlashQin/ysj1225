package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 直播商品列表
 */
public class ShopLiveGoods  implements Parcelable
{
 public ShopLiveGoods()
{
}

/**
 * 商品链接
 */
public String productLinks;

/**
 * 商品id
 */
public long goodsId;

/**
 * 商品价格
 */
public double goodsPrice;

/**
 * 商品名称
 */
public String name;

/**
 * null
 */
public long id;

/**
 * 是否讲解
 */
public int idExplain;

/**
 * 排序
 */
public int sort;

/**
 * 主播Id
 */
public long anchorId;

/**
 * 商品优惠价格
 */
public double favorablePrice;

/**
 * 商品渠道id
 */
public long channelId;

/**
 * 商品图片地址
 */
public String goodsPicture;

   public ShopLiveGoods(Parcel in) 
{
productLinks=in.readString();
goodsId=in.readLong();
goodsPrice=in.readDouble();
name=in.readString();
id=in.readLong();
idExplain=in.readInt();
sort=in.readInt();
anchorId=in.readLong();
favorablePrice=in.readDouble();
channelId=in.readLong();
goodsPicture=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(productLinks);
dest.writeLong(goodsId);
dest.writeDouble(goodsPrice);
dest.writeString(name);
dest.writeLong(id);
dest.writeInt(idExplain);
dest.writeInt(sort);
dest.writeLong(anchorId);
dest.writeDouble(favorablePrice);
dest.writeLong(channelId);
dest.writeString(goodsPicture);

}

  public static void cloneObj(ShopLiveGoods source,ShopLiveGoods target)
{

target.productLinks=source.productLinks;

target.goodsId=source.goodsId;

target.goodsPrice=source.goodsPrice;

target.name=source.name;

target.id=source.id;

target.idExplain=source.idExplain;

target.sort=source.sort;

target.anchorId=source.anchorId;

target.favorablePrice=source.favorablePrice;

target.channelId=source.channelId;

target.goodsPicture=source.goodsPicture;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopLiveGoods> CREATOR = new Creator<ShopLiveGoods>() {
        @Override
        public ShopLiveGoods createFromParcel(Parcel in) {
            return new ShopLiveGoods(in);
        }

        @Override
        public ShopLiveGoods[] newArray(int size) {
            return new ShopLiveGoods[size];
        }
    };
}
