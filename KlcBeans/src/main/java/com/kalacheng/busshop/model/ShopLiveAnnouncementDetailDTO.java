package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 商家直播预告详情
 */
public class ShopLiveAnnouncementDetailDTO  implements Parcelable
{
 public ShopLiveAnnouncementDetailDTO()
{
}

/**
 * 是否关注1关注0未关注
 */
public int isFollow;

/**
 * 直播日期
 */
public String liveDate;

/**
 * 添加时间
 */
public Date addTime;

/**
 * 开始时间
 */
public String startTime;

/**
 * null
 */
public long id;

/**
 * 主播Id
 */
public long anchorId;

/**
 * 海报贴纸
 */
public String posterStickers;

/**
 * 标题
 */
public String title;

/**
 * 直播id
 */
public long roomId;

/**
 * 购物分类
 */
public String shopCategory;

   public ShopLiveAnnouncementDetailDTO(Parcel in) 
{
isFollow=in.readInt();
liveDate=in.readString();
addTime=new Date( in.readLong());
startTime=in.readString();
id=in.readLong();
anchorId=in.readLong();
posterStickers=in.readString();
title=in.readString();
roomId=in.readLong();
shopCategory=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(isFollow);
dest.writeString(liveDate);
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeString(startTime);
dest.writeLong(id);
dest.writeLong(anchorId);
dest.writeString(posterStickers);
dest.writeString(title);
dest.writeLong(roomId);
dest.writeString(shopCategory);

}

  public static void cloneObj(ShopLiveAnnouncementDetailDTO source,ShopLiveAnnouncementDetailDTO target)
{

target.isFollow=source.isFollow;

target.liveDate=source.liveDate;

target.addTime=source.addTime;

target.startTime=source.startTime;

target.id=source.id;

target.anchorId=source.anchorId;

target.posterStickers=source.posterStickers;

target.title=source.title;

target.roomId=source.roomId;

target.shopCategory=source.shopCategory;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopLiveAnnouncementDetailDTO> CREATOR = new Creator<ShopLiveAnnouncementDetailDTO>() {
        @Override
        public ShopLiveAnnouncementDetailDTO createFromParcel(Parcel in) {
            return new ShopLiveAnnouncementDetailDTO(in);
        }

        @Override
        public ShopLiveAnnouncementDetailDTO[] newArray(int size) {
            return new ShopLiveAnnouncementDetailDTO[size];
        }
    };
}
