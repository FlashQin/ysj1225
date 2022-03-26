package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 商品列表返回
 */
public class ShopGoodsDTO  implements Parcelable
{
 public ShopGoodsDTO()
{
}

/**
 * 商品链接
 */
public String productLinks;

/**
 * 商品已售数量
 */
public int soldNum;

/**
 * 商品id
 */
public long goodsId;

/**
 * 商品编号
 */
public String num;

/**
 * 排序
 */
public int sort;

/**
 * 商品分类名称
 */
public String categoryName;

/**
 * 商品优惠价格
 */
public double favorablePrice;

/**
 * 商品简介图片地址
 */
public String goodsPicture;

/**
 * 商品价格
 */
public double price;

/**
 * 是否选取 0 没有选取 1 选取
 */
public int checked;

/**
 * 商品渠道名称
 */
public String channelName;

/**
 * 商品详情
 */
public String present;

/**
 * 商品名称
 */
public String goodsName;

/**
 * 属性名称
 */
public String attrName;

/**
 * 商品分类Id
 */
public long categoryId;

/**
 * 商品渠道id
 */
public long channelId;

/**
 * 商品详情图片地址
 */
public String detailPicture;

/**
 * 状态
 */
public int status;

   public ShopGoodsDTO(Parcel in) 
{
productLinks=in.readString();
soldNum=in.readInt();
goodsId=in.readLong();
num=in.readString();
sort=in.readInt();
categoryName=in.readString();
favorablePrice=in.readDouble();
goodsPicture=in.readString();
price=in.readDouble();
checked=in.readInt();
channelName=in.readString();
present=in.readString();
goodsName=in.readString();
attrName=in.readString();
categoryId=in.readLong();
channelId=in.readLong();
detailPicture=in.readString();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(productLinks);
dest.writeInt(soldNum);
dest.writeLong(goodsId);
dest.writeString(num);
dest.writeInt(sort);
dest.writeString(categoryName);
dest.writeDouble(favorablePrice);
dest.writeString(goodsPicture);
dest.writeDouble(price);
dest.writeInt(checked);
dest.writeString(channelName);
dest.writeString(present);
dest.writeString(goodsName);
dest.writeString(attrName);
dest.writeLong(categoryId);
dest.writeLong(channelId);
dest.writeString(detailPicture);
dest.writeInt(status);

}

  public static void cloneObj(ShopGoodsDTO source,ShopGoodsDTO target)
{

target.productLinks=source.productLinks;

target.soldNum=source.soldNum;

target.goodsId=source.goodsId;

target.num=source.num;

target.sort=source.sort;

target.categoryName=source.categoryName;

target.favorablePrice=source.favorablePrice;

target.goodsPicture=source.goodsPicture;

target.price=source.price;

target.checked=source.checked;

target.channelName=source.channelName;

target.present=source.present;

target.goodsName=source.goodsName;

target.attrName=source.attrName;

target.categoryId=source.categoryId;

target.channelId=source.channelId;

target.detailPicture=source.detailPicture;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopGoodsDTO> CREATOR = new Creator<ShopGoodsDTO>() {
        @Override
        public ShopGoodsDTO createFromParcel(Parcel in) {
            return new ShopGoodsDTO(in);
        }

        @Override
        public ShopGoodsDTO[] newArray(int size) {
            return new ShopGoodsDTO[size];
        }
    };
}
