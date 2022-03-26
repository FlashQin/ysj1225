package com.kalacheng.buscommon.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 礼物墙返回结果
 */
public class GiftWallDto  implements Parcelable
{
 public GiftWallDto()
{
}

/**
 * null
 */
public String gifticon;

/**
 * null
 */
public String giftname;

/**
 * null
 */
public long giftId;

/**
 * null
 */
public int totalNum;

   public GiftWallDto(Parcel in) 
{
gifticon=in.readString();
giftname=in.readString();
giftId=in.readLong();
totalNum=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(gifticon);
dest.writeString(giftname);
dest.writeLong(giftId);
dest.writeInt(totalNum);

}

  public static void cloneObj(GiftWallDto source,GiftWallDto target)
{

target.gifticon=source.gifticon;

target.giftname=source.giftname;

target.giftId=source.giftId;

target.totalNum=source.totalNum;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GiftWallDto> CREATOR = new Creator<GiftWallDto>() {
        @Override
        public GiftWallDto createFromParcel(Parcel in) {
            return new GiftWallDto(in);
        }

        @Override
        public GiftWallDto[] newArray(int size) {
            return new GiftWallDto[size];
        }
    };
}
