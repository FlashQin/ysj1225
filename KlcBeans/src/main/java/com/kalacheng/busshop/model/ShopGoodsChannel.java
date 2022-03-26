package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 商品渠道表
 */
public class ShopGoodsChannel  implements Parcelable
{
 public ShopGoodsChannel()
{
}

/**
 * 添加时间
 */
public Date addTime;

/**
 * null
 */
public long id;

/**
 * 排序
 */
public int sort;

/**
 * 渠道名称
 */
public String goodsChannel;

   public ShopGoodsChannel(Parcel in) 
{
addTime=new Date( in.readLong());
id=in.readLong();
sort=in.readInt();
goodsChannel=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeLong(id);
dest.writeInt(sort);
dest.writeString(goodsChannel);

}

  public static void cloneObj(ShopGoodsChannel source,ShopGoodsChannel target)
{

target.addTime=source.addTime;

target.id=source.id;

target.sort=source.sort;

target.goodsChannel=source.goodsChannel;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopGoodsChannel> CREATOR = new Creator<ShopGoodsChannel>() {
        @Override
        public ShopGoodsChannel createFromParcel(Parcel in) {
            return new ShopGoodsChannel(in);
        }

        @Override
        public ShopGoodsChannel[] newArray(int size) {
            return new ShopGoodsChannel[size];
        }
    };
}
