package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 商品详情表
 */
public class ShopAttrCompose  implements Parcelable
{
 public ShopAttrCompose()
{
}

/**
 * 商品属性值2id
 */
public long attribute2Id;

/**
 * 商品id
 */
public long goodsId;

/**
 * 商品价格
 */
public double price;

/**
 * 商品属性值1id
 */
public long attribute1Id;

/**
 * 冻结库存
 */
public int frozenStock;

/**
 * null
 */
public long id;

/**
 * 属性值2名称
 */
public String name2;

/**
 * 库存
 */
public int stock;

/**
 * 优惠价格
 */
public double favorablePrice;

/**
 * 属性值1名称
 */
public String name1;

   public ShopAttrCompose(Parcel in) 
{
attribute2Id=in.readLong();
goodsId=in.readLong();
price=in.readDouble();
attribute1Id=in.readLong();
frozenStock=in.readInt();
id=in.readLong();
name2=in.readString();
stock=in.readInt();
favorablePrice=in.readDouble();
name1=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(attribute2Id);
dest.writeLong(goodsId);
dest.writeDouble(price);
dest.writeLong(attribute1Id);
dest.writeInt(frozenStock);
dest.writeLong(id);
dest.writeString(name2);
dest.writeInt(stock);
dest.writeDouble(favorablePrice);
dest.writeString(name1);

}

  public static void cloneObj(ShopAttrCompose source,ShopAttrCompose target)
{

target.attribute2Id=source.attribute2Id;

target.goodsId=source.goodsId;

target.price=source.price;

target.attribute1Id=source.attribute1Id;

target.frozenStock=source.frozenStock;

target.id=source.id;

target.name2=source.name2;

target.stock=source.stock;

target.favorablePrice=source.favorablePrice;

target.name1=source.name1;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopAttrCompose> CREATOR = new Creator<ShopAttrCompose>() {
        @Override
        public ShopAttrCompose createFromParcel(Parcel in) {
            return new ShopAttrCompose(in);
        }

        @Override
        public ShopAttrCompose[] newArray(int size) {
            return new ShopAttrCompose[size];
        }
    };
}
