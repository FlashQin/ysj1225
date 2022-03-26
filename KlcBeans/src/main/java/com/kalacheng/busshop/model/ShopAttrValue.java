package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 商品属性值表
 */
public class ShopAttrValue  implements Parcelable
{
 public ShopAttrValue()
{
}

/**
 * 商品属性id
 */
public long attributeId;

/**
 * 商品id
 */
public long goodsId;

/**
 * 属性值名称
 */
public String name;

/**
 * null
 */
public long id;

   public ShopAttrValue(Parcel in) 
{
attributeId=in.readLong();
goodsId=in.readLong();
name=in.readString();
id=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(attributeId);
dest.writeLong(goodsId);
dest.writeString(name);
dest.writeLong(id);

}

  public static void cloneObj(ShopAttrValue source,ShopAttrValue target)
{

target.attributeId=source.attributeId;

target.goodsId=source.goodsId;

target.name=source.name;

target.id=source.id;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopAttrValue> CREATOR = new Creator<ShopAttrValue>() {
        @Override
        public ShopAttrValue createFromParcel(Parcel in) {
            return new ShopAttrValue(in);
        }

        @Override
        public ShopAttrValue[] newArray(int size) {
            return new ShopAttrValue[size];
        }
    };
}
