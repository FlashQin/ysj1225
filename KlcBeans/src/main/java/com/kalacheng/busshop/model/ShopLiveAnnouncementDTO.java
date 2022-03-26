package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busshop.model.*;




/**
 * 商家直播预告信息
 */
public class ShopLiveAnnouncementDTO  implements Parcelable
{
 public ShopLiveAnnouncementDTO()
{
}

/**
 * shopLiveAnnouncementList
 */
public List<com.kalacheng.busshop.model.ShopLiveAnnouncementDetailDTO> shopLiveAnnouncementList;

   public ShopLiveAnnouncementDTO(Parcel in) 
{

if(shopLiveAnnouncementList==null){
shopLiveAnnouncementList=  new ArrayList<>();
 }
in.readTypedList(shopLiveAnnouncementList,com.kalacheng.busshop.model.ShopLiveAnnouncementDetailDTO.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(shopLiveAnnouncementList);

}

  public static void cloneObj(ShopLiveAnnouncementDTO source,ShopLiveAnnouncementDTO target)
{

        if(source.shopLiveAnnouncementList==null)
        {
            target.shopLiveAnnouncementList=null;
        }else
        {
            target.shopLiveAnnouncementList=new ArrayList();
            for(int i=0;i<source.shopLiveAnnouncementList.size();i++)
            {
            ShopLiveAnnouncementDetailDTO.cloneObj(source.shopLiveAnnouncementList.get(i),target.shopLiveAnnouncementList.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopLiveAnnouncementDTO> CREATOR = new Creator<ShopLiveAnnouncementDTO>() {
        @Override
        public ShopLiveAnnouncementDTO createFromParcel(Parcel in) {
            return new ShopLiveAnnouncementDTO(in);
        }

        @Override
        public ShopLiveAnnouncementDTO[] newArray(int size) {
            return new ShopLiveAnnouncementDTO[size];
        }
    };
}
