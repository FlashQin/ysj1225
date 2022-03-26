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
public class ShopBusinessLiveAnnouncementDTO  implements Parcelable
{
 public ShopBusinessLiveAnnouncementDTO()
{
}

/**
 * shopLiveAnnouncementList
 */
public List<com.kalacheng.busshop.model.ShopLiveAnnouncement> shopLiveAnnouncementList;

   public ShopBusinessLiveAnnouncementDTO(Parcel in) 
{

if(shopLiveAnnouncementList==null){
shopLiveAnnouncementList=  new ArrayList<>();
 }
in.readTypedList(shopLiveAnnouncementList,com.kalacheng.busshop.model.ShopLiveAnnouncement.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(shopLiveAnnouncementList);

}

  public static void cloneObj(ShopBusinessLiveAnnouncementDTO source,ShopBusinessLiveAnnouncementDTO target)
{

        if(source.shopLiveAnnouncementList==null)
        {
            target.shopLiveAnnouncementList=null;
        }else
        {
            target.shopLiveAnnouncementList=new ArrayList();
            for(int i=0;i<source.shopLiveAnnouncementList.size();i++)
            {
            ShopLiveAnnouncement.cloneObj(source.shopLiveAnnouncementList.get(i),target.shopLiveAnnouncementList.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopBusinessLiveAnnouncementDTO> CREATOR = new Creator<ShopBusinessLiveAnnouncementDTO>() {
        @Override
        public ShopBusinessLiveAnnouncementDTO createFromParcel(Parcel in) {
            return new ShopBusinessLiveAnnouncementDTO(in);
        }

        @Override
        public ShopBusinessLiveAnnouncementDTO[] newArray(int size) {
            return new ShopBusinessLiveAnnouncementDTO[size];
        }
    };
}
