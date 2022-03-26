package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 直播预告表
 */
public class ShopLiveAnnouncement  implements Parcelable
{
 public ShopLiveAnnouncement()
{
}

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
 * 购物分类
 */
public String shopCategory;

   public ShopLiveAnnouncement(Parcel in) 
{
liveDate=in.readString();
addTime=new Date( in.readLong());
startTime=in.readString();
id=in.readLong();
anchorId=in.readLong();
posterStickers=in.readString();
title=in.readString();
shopCategory=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(liveDate);
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeString(startTime);
dest.writeLong(id);
dest.writeLong(anchorId);
dest.writeString(posterStickers);
dest.writeString(title);
dest.writeString(shopCategory);

}

  public static void cloneObj(ShopLiveAnnouncement source,ShopLiveAnnouncement target)
{

target.liveDate=source.liveDate;

target.addTime=source.addTime;

target.startTime=source.startTime;

target.id=source.id;

target.anchorId=source.anchorId;

target.posterStickers=source.posterStickers;

target.title=source.title;

target.shopCategory=source.shopCategory;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopLiveAnnouncement> CREATOR = new Creator<ShopLiveAnnouncement>() {
        @Override
        public ShopLiveAnnouncement createFromParcel(Parcel in) {
            return new ShopLiveAnnouncement(in);
        }

        @Override
        public ShopLiveAnnouncement[] newArray(int size) {
            return new ShopLiveAnnouncement[size];
        }
    };
}
