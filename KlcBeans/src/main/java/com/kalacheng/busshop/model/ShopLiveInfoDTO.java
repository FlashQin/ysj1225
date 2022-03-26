package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;

import com.kalacheng.busshop.model.*;




/**
 * 商家直播信息
 */
public class ShopLiveInfoDTO  implements Parcelable
{
 public ShopLiveInfoDTO()
{
}

/**
 * 直播信息
 */
public com.kalacheng.libuser.model.LiveLive appUsersLive;

/**
 * 讲解商品
 */
public com.kalacheng.busshop.model.ShopLiveGoods shopLiveGoods;

/**
 * 商家信息
 */
public com.kalacheng.busshop.model.ShopBusiness shopBusiness;

   public ShopLiveInfoDTO(Parcel in) 
{

appUsersLive=in.readParcelable(com.kalacheng.libuser.model.LiveLive.class.getClassLoader());

shopLiveGoods=in.readParcelable(com.kalacheng.busshop.model.ShopLiveGoods.class.getClassLoader());

shopBusiness=in.readParcelable(com.kalacheng.busshop.model.ShopBusiness.class.getClassLoader());

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeParcelable(appUsersLive,flags);

dest.writeParcelable(shopLiveGoods,flags);

dest.writeParcelable(shopBusiness,flags);

}

  public static void cloneObj(ShopLiveInfoDTO source,ShopLiveInfoDTO target)
{
        if(source.appUsersLive==null)
        {
            target.appUsersLive=null;
        }else
        {
            LiveLive.cloneObj(source.appUsersLive,target.appUsersLive);
        }
        if(source.shopLiveGoods==null)
        {
            target.shopLiveGoods=null;
        }else
        {
            ShopLiveGoods.cloneObj(source.shopLiveGoods,target.shopLiveGoods);
        }
        if(source.shopBusiness==null)
        {
            target.shopBusiness=null;
        }else
        {
            ShopBusiness.cloneObj(source.shopBusiness,target.shopBusiness);
        }

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopLiveInfoDTO> CREATOR = new Creator<ShopLiveInfoDTO>() {
        @Override
        public ShopLiveInfoDTO createFromParcel(Parcel in) {
            return new ShopLiveInfoDTO(in);
        }

        @Override
        public ShopLiveInfoDTO[] newArray(int size) {
            return new ShopLiveInfoDTO[size];
        }
    };
}
