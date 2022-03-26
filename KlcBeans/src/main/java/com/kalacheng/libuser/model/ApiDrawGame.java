package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP用户抽奖
 */
public class ApiDrawGame  implements Parcelable
{
 public ApiDrawGame()
{
}

/**
 * 礼物图标
 */
public String gifticon;

/**
 * 礼物名称
 */
public String giftname;

/**
 * 中奖概率
 */
public int prob;

/**
 * 礼物ID
 */
public long gid;

/**
 * 中奖数量
 */
public int num;

/**
 * 礼物价格
 */
public int needcoin;

/**
 * ID
 */
public long id;

   public ApiDrawGame(Parcel in) 
{
gifticon=in.readString();
giftname=in.readString();
prob=in.readInt();
gid=in.readLong();
num=in.readInt();
needcoin=in.readInt();
id=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(gifticon);
dest.writeString(giftname);
dest.writeInt(prob);
dest.writeLong(gid);
dest.writeInt(num);
dest.writeInt(needcoin);
dest.writeLong(id);

}

  public static void cloneObj(ApiDrawGame source,ApiDrawGame target)
{

target.gifticon=source.gifticon;

target.giftname=source.giftname;

target.prob=source.prob;

target.gid=source.gid;

target.num=source.num;

target.needcoin=source.needcoin;

target.id=source.id;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiDrawGame> CREATOR = new Creator<ApiDrawGame>() {
        @Override
        public ApiDrawGame createFromParcel(Parcel in) {
            return new ApiDrawGame(in);
        }

        @Override
        public ApiDrawGame[] newArray(int size) {
            return new ApiDrawGame[size];
        }
    };
}
