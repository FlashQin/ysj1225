package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busshop.model.*;




/**
 * 购物车列表返回
 */
public class ShopCarDTO  implements Parcelable
{
 public ShopCarDTO()
{
}

/**
 * 商家id
 */
public long businessId;

/**
 * 商家logo
 */
public String businessLogo;

/**
 * 商家名称
 */
public String businessName;

/**
 * 购物车数据列表
 */
public List<com.kalacheng.busshop.model.ShopCar> shopCarList;

/**
 * 安卓使用
 */
public int viewType;

/**
 * 安卓使用1
 */
public int checked;

   public ShopCarDTO(Parcel in) 
{
businessId=in.readLong();
businessLogo=in.readString();
businessName=in.readString();

if(shopCarList==null){
shopCarList=  new ArrayList<>();
 }
in.readTypedList(shopCarList,com.kalacheng.busshop.model.ShopCar.CREATOR);
viewType=in.readInt();
checked=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(businessId);
dest.writeString(businessLogo);
dest.writeString(businessName);

dest.writeTypedList(shopCarList);
dest.writeInt(viewType);
dest.writeInt(checked);

}

  public static void cloneObj(ShopCarDTO source,ShopCarDTO target)
{

target.businessId=source.businessId;

target.businessLogo=source.businessLogo;

target.businessName=source.businessName;

        if(source.shopCarList==null)
        {
            target.shopCarList=null;
        }else
        {
            target.shopCarList=new ArrayList();
            for(int i=0;i<source.shopCarList.size();i++)
            {
            ShopCar.cloneObj(source.shopCarList.get(i),target.shopCarList.get(i));
            }
        }


target.viewType=source.viewType;

target.checked=source.checked;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopCarDTO> CREATOR = new Creator<ShopCarDTO>() {
        @Override
        public ShopCarDTO createFromParcel(Parcel in) {
            return new ShopCarDTO(in);
        }

        @Override
        public ShopCarDTO[] newArray(int size) {
            return new ShopCarDTO[size];
        }
    };
}
