package com.kalacheng.busgame.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 奖项设置表
 */
public class GameAwards  implements Parcelable
{
 public GameAwards()
{
}

/**
 * 游戏id
 */
public long gameId;

/**
 * 添加时间
 */
public String addTime;

/**
 * 奖品对应的礼物名称
 */
public String giftName;

/**
 * 中奖概率
 */
public double winningProbability;

/**
 * 备注
 */
public String remake;

/**
 * 奖品类型金币/礼物
 */
public int awardsType;

/**
 * 奖品图片
 */
public String picture;

/**
 * 奖品对应的礼物id
 */
public long giftId;

/**
 * 是否全局飘屏
 */
public int flutterScreen;

/**
 * 奖品金币数
 */
public int coinNum;

/**
 *  奖项名称
 */
public String name;

/**
 * null
 */
public long id;

/**
 * 时间对应毫秒值
 */
public long time;

   public GameAwards(Parcel in) 
{
gameId=in.readLong();
addTime=in.readString();
giftName=in.readString();
winningProbability=in.readDouble();
remake=in.readString();
awardsType=in.readInt();
picture=in.readString();
giftId=in.readLong();
flutterScreen=in.readInt();
coinNum=in.readInt();
name=in.readString();
id=in.readLong();
time=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(gameId);
dest.writeString(addTime);
dest.writeString(giftName);
dest.writeDouble(winningProbability);
dest.writeString(remake);
dest.writeInt(awardsType);
dest.writeString(picture);
dest.writeLong(giftId);
dest.writeInt(flutterScreen);
dest.writeInt(coinNum);
dest.writeString(name);
dest.writeLong(id);
dest.writeLong(time);

}

  public static void cloneObj(GameAwards source,GameAwards target)
{

target.gameId=source.gameId;

target.addTime=source.addTime;

target.giftName=source.giftName;

target.winningProbability=source.winningProbability;

target.remake=source.remake;

target.awardsType=source.awardsType;

target.picture=source.picture;

target.giftId=source.giftId;

target.flutterScreen=source.flutterScreen;

target.coinNum=source.coinNum;

target.name=source.name;

target.id=source.id;

target.time=source.time;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GameAwards> CREATOR = new Creator<GameAwards>() {
        @Override
        public GameAwards createFromParcel(Parcel in) {
            return new GameAwards(in);
        }

        @Override
        public GameAwards[] newArray(int size) {
            return new GameAwards[size];
        }
    };
}
