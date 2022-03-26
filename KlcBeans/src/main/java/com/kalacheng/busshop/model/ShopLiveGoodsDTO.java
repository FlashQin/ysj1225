package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busshop.model.*;




/**
 * 直播间商品列表返回
 */
public class ShopLiveGoodsDTO  implements Parcelable
{
 public ShopLiveGoodsDTO()
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
 * 直播商品列表
 */
public List<com.kalacheng.busshop.model.ShopLiveGoods> liveGoodsList;

   public ShopLiveGoodsDTO(Parcel in) 
{
businessId=in.readLong();
businessLogo=in.readString();
businessName=in.readString();

if(liveGoodsList==null){
liveGoodsList=  new ArrayList<>();
 }
in.readTypedList(liveGoodsList,com.kalacheng.busshop.model.ShopLiveGoods.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(businessId);
dest.writeString(businessLogo);
dest.writeString(businessName);

dest.writeTypedList(liveGoodsList);

}

  public static void cloneObj(ShopLiveGoodsDTO source,ShopLiveGoodsDTO target)
{

target.businessId=source.businessId;

target.businessLogo=source.businessLogo;

target.businessName=source.businessName;

        if(source.liveGoodsList==null)
        {
            target.liveGoodsList=null;
        }else
        {
            target.liveGoodsList=new ArrayList();
            for(int i=0;i<source.liveGoodsList.size();i++)
            {
            ShopLiveGoods.cloneObj(source.liveGoodsList.get(i),target.liveGoodsList.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopLiveGoodsDTO> CREATOR = new Creator<ShopLiveGoodsDTO>() {
        @Override
        public ShopLiveGoodsDTO createFromParcel(Parcel in) {
            return new ShopLiveGoodsDTO(in);
        }

        @Override
        public ShopLiveGoodsDTO[] newArray(int size) {
            return new ShopLiveGoodsDTO[size];
        }
    };
}
