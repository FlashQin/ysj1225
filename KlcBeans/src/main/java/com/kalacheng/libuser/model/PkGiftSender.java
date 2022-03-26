package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * Pk中的礼物发送者
 */
public class PkGiftSender  implements Parcelable
{
 public PkGiftSender()
{
}

/**
 * 直播间类型 1语音PK 2视频PK
 */
public int isVoicePk;

/**
 * 头像
 */
public String avatar;

/**
 * 用户ID
 */
public long userId;

/**
 * 在该PK小队中的送礼总值
 */
public double coin;

/**
 * PK类型
 */
public int pkType;

   public PkGiftSender(Parcel in) 
{
isVoicePk=in.readInt();
avatar=in.readString();
userId=in.readLong();
coin=in.readDouble();
pkType=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(isVoicePk);
dest.writeString(avatar);
dest.writeLong(userId);
dest.writeDouble(coin);
dest.writeInt(pkType);

}

  public static void cloneObj(PkGiftSender source,PkGiftSender target)
{

target.isVoicePk=source.isVoicePk;

target.avatar=source.avatar;

target.userId=source.userId;

target.coin=source.coin;

target.pkType=source.pkType;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PkGiftSender> CREATOR = new Creator<PkGiftSender>() {
        @Override
        public PkGiftSender createFromParcel(Parcel in) {
            return new PkGiftSender(in);
        }

        @Override
        public PkGiftSender[] newArray(int size) {
            return new PkGiftSender[size];
        }
    };
}
