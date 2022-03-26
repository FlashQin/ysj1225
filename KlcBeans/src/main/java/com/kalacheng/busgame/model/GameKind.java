package com.kalacheng.busgame.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 游戏种类表
 */
public class GameKind  implements Parcelable
{
 public GameKind()
{
}

/**
 * 是否启用新用户保护
 */
public int isProtect;

/**
 * 幸运加成概率
 */
public double luckyProbability;

/**
 * 保护奖项ID
 */
public long protectPrizeId;

/**
 * 添加时间
 */
public String addTime;

/**
 * 游戏说明
 */
public String gameExplain;

/**
 * 幸运加成奖项id
 */
public long luckyPrizeId;

/**
 * 保底比例
 */
public double bottomProportion;

/**
 * 备注
 */
public String remake;

/**
 * 是否启用幸运加成
 */
public int isLuckyBonus;

/**
 * 是否启用保底
 */
public int isBottom;

/**
 * 幸运宝箱结束时间
 */
public int luckyEndTime;

/**
 * 是否开启 0否1开启
 */
public int isOpen;

/**
 * 保底抽奖次数
 */
public int bottomPrizeNum;

/**
 * 幸运加成抽奖次数
 */
public int luckyPrizeNum;

/**
 * 特别说明
 */
public String specialNote;

/**
 * 游戏名称
 */
public String name;

/**
 * null
 */
public long id;

/**
 * 保底奖项ID
 */
public long bottomPrizeId;

/**
 * 幸运宝箱开始时间
 */
public int luckyStartTime;

   public GameKind(Parcel in) 
{
isProtect=in.readInt();
luckyProbability=in.readDouble();
protectPrizeId=in.readLong();
addTime=in.readString();
gameExplain=in.readString();
luckyPrizeId=in.readLong();
bottomProportion=in.readDouble();
remake=in.readString();
isLuckyBonus=in.readInt();
isBottom=in.readInt();
luckyEndTime=in.readInt();
isOpen=in.readInt();
bottomPrizeNum=in.readInt();
luckyPrizeNum=in.readInt();
specialNote=in.readString();
name=in.readString();
id=in.readLong();
bottomPrizeId=in.readLong();
luckyStartTime=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(isProtect);
dest.writeDouble(luckyProbability);
dest.writeLong(protectPrizeId);
dest.writeString(addTime);
dest.writeString(gameExplain);
dest.writeLong(luckyPrizeId);
dest.writeDouble(bottomProportion);
dest.writeString(remake);
dest.writeInt(isLuckyBonus);
dest.writeInt(isBottom);
dest.writeInt(luckyEndTime);
dest.writeInt(isOpen);
dest.writeInt(bottomPrizeNum);
dest.writeInt(luckyPrizeNum);
dest.writeString(specialNote);
dest.writeString(name);
dest.writeLong(id);
dest.writeLong(bottomPrizeId);
dest.writeInt(luckyStartTime);

}

  public static void cloneObj(GameKind source,GameKind target)
{

target.isProtect=source.isProtect;

target.luckyProbability=source.luckyProbability;

target.protectPrizeId=source.protectPrizeId;

target.addTime=source.addTime;

target.gameExplain=source.gameExplain;

target.luckyPrizeId=source.luckyPrizeId;

target.bottomProportion=source.bottomProportion;

target.remake=source.remake;

target.isLuckyBonus=source.isLuckyBonus;

target.isBottom=source.isBottom;

target.luckyEndTime=source.luckyEndTime;

target.isOpen=source.isOpen;

target.bottomPrizeNum=source.bottomPrizeNum;

target.luckyPrizeNum=source.luckyPrizeNum;

target.specialNote=source.specialNote;

target.name=source.name;

target.id=source.id;

target.bottomPrizeId=source.bottomPrizeId;

target.luckyStartTime=source.luckyStartTime;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GameKind> CREATOR = new Creator<GameKind>() {
        @Override
        public GameKind createFromParcel(Parcel in) {
            return new GameKind(in);
        }

        @Override
        public GameKind[] newArray(int size) {
            return new GameKind[size];
        }
    };
}
