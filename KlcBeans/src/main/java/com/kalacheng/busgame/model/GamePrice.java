package com.kalacheng.busgame.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 游戏价格表
 */
public class GamePrice  implements Parcelable
{
 public GamePrice()
{
}

/**
 * 游戏id
 */
public long gameId;

/**
 * 抽奖次数
 */
public int gameNum;

/**
 * 添加时间
 */
public String addTime;

/**
 * 名称
 */
public String name;

/**
 * null
 */
public long id;

/**
 * 备注
 */
public String remake;

/**
 * 时间对应毫秒值
 */
public long time;

/**
 * 消耗金币数
 */
public int useCoin;

   public GamePrice(Parcel in) 
{
gameId=in.readLong();
gameNum=in.readInt();
addTime=in.readString();
name=in.readString();
id=in.readLong();
remake=in.readString();
time=in.readLong();
useCoin=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(gameId);
dest.writeInt(gameNum);
dest.writeString(addTime);
dest.writeString(name);
dest.writeLong(id);
dest.writeString(remake);
dest.writeLong(time);
dest.writeInt(useCoin);

}

  public static void cloneObj(GamePrice source,GamePrice target)
{

target.gameId=source.gameId;

target.gameNum=source.gameNum;

target.addTime=source.addTime;

target.name=source.name;

target.id=source.id;

target.remake=source.remake;

target.time=source.time;

target.useCoin=source.useCoin;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GamePrice> CREATOR = new Creator<GamePrice>() {
        @Override
        public GamePrice createFromParcel(Parcel in) {
            return new GamePrice(in);
        }

        @Override
        public GamePrice[] newArray(int size) {
            return new GamePrice[size];
        }
    };
}
