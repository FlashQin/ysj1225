package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busshop.model.*;




/**
 * 商家主页主播开播返回
 */
public class ShopLiveDetailDTO  implements Parcelable
{
 public ShopLiveDetailDTO()
{
}

/**
 * 直播封面图
 */
public String thumb;

/**
 * 直播间人数
 */
public int liveUsers;

/**
 * 直播标题
 */
public String title;

/**
 * 直播橱窗商品列表
 */
public List<com.kalacheng.busshop.model.ShopLiveGoods> liveGoodsList;

   public ShopLiveDetailDTO(Parcel in) 
{
thumb=in.readString();
liveUsers=in.readInt();
title=in.readString();

if(liveGoodsList==null){
liveGoodsList=  new ArrayList<>();
 }
in.readTypedList(liveGoodsList,com.kalacheng.busshop.model.ShopLiveGoods.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(thumb);
dest.writeInt(liveUsers);
dest.writeString(title);

dest.writeTypedList(liveGoodsList);

}

  public static void cloneObj(ShopLiveDetailDTO source,ShopLiveDetailDTO target)
{

target.thumb=source.thumb;

target.liveUsers=source.liveUsers;

target.title=source.title;

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

    public static final Creator<ShopLiveDetailDTO> CREATOR = new Creator<ShopLiveDetailDTO>() {
        @Override
        public ShopLiveDetailDTO createFromParcel(Parcel in) {
            return new ShopLiveDetailDTO(in);
        }

        @Override
        public ShopLiveDetailDTO[] newArray(int size) {
            return new ShopLiveDetailDTO[size];
        }
    };
}
