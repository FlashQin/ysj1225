package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 商品类别表
 */
public class ShopGoodsCategory  implements Parcelable
{
 public ShopGoodsCategory()
{
}

/**
 * 累计成交金额
 */
public double totalAmount;

/**
 * 商品累计销量
 */
public int sale;

/**
 * 添加时间
 */
public Date addTime;

/**
 * 商品数量
 */
public int num;

/**
 * 商品分类名称
 */
public String name;

/**
 * null
 */
public long id;

/**
 * 备注
 */
public String remake;

/**
 * 排序
 */
public int sort;

/**
 * 上级分类ID
 */
public long parentId;

   public ShopGoodsCategory(Parcel in) 
{
totalAmount=in.readDouble();
sale=in.readInt();
addTime=new Date( in.readLong());
num=in.readInt();
name=in.readString();
id=in.readLong();
remake=in.readString();
sort=in.readInt();
parentId=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeDouble(totalAmount);
dest.writeInt(sale);
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeInt(num);
dest.writeString(name);
dest.writeLong(id);
dest.writeString(remake);
dest.writeInt(sort);
dest.writeLong(parentId);

}

  public static void cloneObj(ShopGoodsCategory source,ShopGoodsCategory target)
{

target.totalAmount=source.totalAmount;

target.sale=source.sale;

target.addTime=source.addTime;

target.num=source.num;

target.name=source.name;

target.id=source.id;

target.remake=source.remake;

target.sort=source.sort;

target.parentId=source.parentId;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopGoodsCategory> CREATOR = new Creator<ShopGoodsCategory>() {
        @Override
        public ShopGoodsCategory createFromParcel(Parcel in) {
            return new ShopGoodsCategory(in);
        }

        @Override
        public ShopGoodsCategory[] newArray(int size) {
            return new ShopGoodsCategory[size];
        }
    };
}
