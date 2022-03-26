package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 讲解商品数据
 */
public class ApiShopLiveGoods  implements Parcelable
{
 public ApiShopLiveGoods()
{
}

/**
 * null
 */
public long serialVersionUID;

/**
 * null
 */
public long id;

/**
 * 排序
 */
public int sort;

/**
 * 主播Id
 */
public long anchorId;

/**
 * 商品id
 */
public long goodsId;

/**
 * 商品名称
 */
public String name;

/**
 * 商品图片地址
 */
public String goodsPicture;

/**
 * 是否讲解
 */
public int idExplain;

/**
 * 商品价格
 */
public double goodsPrice;

/**
 * 商品链接
 */
public String productLinks;

/**
 * 商品渠道id
 */
public long channelId;

   public ApiShopLiveGoods(Parcel in) 
{
serialVersionUID=in.readLong();
id=in.readLong();
sort=in.readInt();
anchorId=in.readLong();
goodsId=in.readLong();
name=in.readString();
goodsPicture=in.readString();
idExplain=in.readInt();
goodsPrice=in.readDouble();
productLinks=in.readString();
channelId=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(serialVersionUID);
dest.writeLong(id);
dest.writeInt(sort);
dest.writeLong(anchorId);
dest.writeLong(goodsId);
dest.writeString(name);
dest.writeString(goodsPicture);
dest.writeInt(idExplain);
dest.writeDouble(goodsPrice);
dest.writeString(productLinks);
dest.writeLong(channelId);

}

  public static void cloneObj(ApiShopLiveGoods source,ApiShopLiveGoods target)
{

target.serialVersionUID=source.serialVersionUID;

target.id=source.id;

target.sort=source.sort;

target.anchorId=source.anchorId;

target.goodsId=source.goodsId;

target.name=source.name;

target.goodsPicture=source.goodsPicture;

target.idExplain=source.idExplain;

target.goodsPrice=source.goodsPrice;

target.productLinks=source.productLinks;

target.channelId=source.channelId;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiShopLiveGoods> CREATOR = new Creator<ApiShopLiveGoods>() {
        @Override
        public ApiShopLiveGoods createFromParcel(Parcel in) {
            return new ApiShopLiveGoods(in);
        }

        @Override
        public ApiShopLiveGoods[] newArray(int size) {
            return new ApiShopLiveGoods[size];
        }
    };
}
