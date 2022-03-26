package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 商品信息表
 */
public class ShopGoods  implements Parcelable
{
 public ShopGoods()
{
}

/**
 * 商品链接
 */
public String productLinks;

/**
 * 创建时间
 */
public Date addTime;

/**
 * 商品已售数量
 */
public int soldNum;

/**
 * 商品编号
 */
public String num;

/**
 * 商家id
 */
public long businessId;

/**
 * 审核备注
 */
public String remake;

/**
 * 排序
 */
public int sort;

/**
 * 商品优惠价格
 */
public double favorablePrice;

/**
 * 商品简介图片地址
 */
public String goodsPicture;

/**
 * 修改时间
 */
public Date upTime;

/**
 * 是否自营 1:自营 ;2: 非自营 
 */
public int myGoods;

/**
 * 商品价格
 */
public double price;

/**
 * null
 */
public long id;

/**
 * 商品详情
 */
public String present;

/**
 * 商品名称
 */
public String goodsName;

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
 * 状态 1：未上架; 2:上架中; 3:冻结中
 */
public int status;

   public ShopGoods(Parcel in) 
{
productLinks=in.readString();
addTime=new Date( in.readLong());
soldNum=in.readInt();
num=in.readString();
businessId=in.readLong();
remake=in.readString();
sort=in.readInt();
favorablePrice=in.readDouble();
goodsPicture=in.readString();
upTime=new Date( in.readLong());
myGoods=in.readInt();
price=in.readDouble();
id=in.readLong();
present=in.readString();
goodsName=in.readString();
categoryId=in.readLong();
channelId=in.readLong();
detailPicture=in.readString();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(productLinks);
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeInt(soldNum);
dest.writeString(num);
dest.writeLong(businessId);
dest.writeString(remake);
dest.writeInt(sort);
dest.writeDouble(favorablePrice);
dest.writeString(goodsPicture);
dest.writeLong(upTime==null?0:upTime.getTime());
dest.writeInt(myGoods);
dest.writeDouble(price);
dest.writeLong(id);
dest.writeString(present);
dest.writeString(goodsName);
dest.writeLong(categoryId);
dest.writeLong(channelId);
dest.writeString(detailPicture);
dest.writeInt(status);

}

  public static void cloneObj(ShopGoods source,ShopGoods target)
{

target.productLinks=source.productLinks;

target.addTime=source.addTime;

target.soldNum=source.soldNum;

target.num=source.num;

target.businessId=source.businessId;

target.remake=source.remake;

target.sort=source.sort;

target.favorablePrice=source.favorablePrice;

target.goodsPicture=source.goodsPicture;

target.upTime=source.upTime;

target.myGoods=source.myGoods;

target.price=source.price;

target.id=source.id;

target.present=source.present;

target.goodsName=source.goodsName;

target.categoryId=source.categoryId;

target.channelId=source.channelId;

target.detailPicture=source.detailPicture;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopGoods> CREATOR = new Creator<ShopGoods>() {
        @Override
        public ShopGoods createFromParcel(Parcel in) {
            return new ShopGoods(in);
        }

        @Override
        public ShopGoods[] newArray(int size) {
            return new ShopGoods[size];
        }
    };
}
