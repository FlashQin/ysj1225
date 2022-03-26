package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 商品属性表
 */
public class ShopGoodsAttr  implements Parcelable
{
 public ShopGoodsAttr()
{
}

/**
 * 商品id
 */
public long goodsId;

/**
 * 属性名称
 */
public String name;

/**
 * null
 */
public long id;

   public ShopGoodsAttr(Parcel in) 
{
goodsId=in.readLong();
name=in.readString();
id=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(goodsId);
dest.writeString(name);
dest.writeLong(id);

}

  public static void cloneObj(ShopGoodsAttr source,ShopGoodsAttr target)
{

target.goodsId=source.goodsId;

target.name=source.name;

target.id=source.id;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopGoodsAttr> CREATOR = new Creator<ShopGoodsAttr>() {
        @Override
        public ShopGoodsAttr createFromParcel(Parcel in) {
            return new ShopGoodsAttr(in);
        }

        @Override
        public ShopGoodsAttr[] newArray(int size) {
            return new ShopGoodsAttr[size];
        }
    };
}
