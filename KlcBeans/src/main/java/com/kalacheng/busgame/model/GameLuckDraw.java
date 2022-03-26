package com.kalacheng.busgame.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 游戏抽奖记录
 */
public class GameLuckDraw  implements Parcelable
{
 public GameLuckDraw()
{
}

/**
 * 游戏id
 */
public long gameId;

/**
 * 子交易类型
 */
public String sonType;

/**
 * 抽奖次数
 */
public int gameNum;

/**
 * 添加时间
 */
public String addTime;

/**
 * 花费金币
 */
public int gameCoin;

/**
 * 交易单号
 */
public String num;

/**
 * 备注
 */
public String remake;

/**
 * 主播id
 */
public long anchorId;

/**
 * 主播名称
 */
public String anchorName;

/**
 * 是否中奖
 */
public int isAwards;

/**
 * 主交易类型
 */
public String parentType;

/**
 * 用户id
 */
public long uid;

/**
 * 游戏名称
 */
public String gameName;

/**
 * null
 */
public long id;

/**
 * 时间对应毫秒值
 */
public long time;

   public GameLuckDraw(Parcel in) 
{
gameId=in.readLong();
sonType=in.readString();
gameNum=in.readInt();
addTime=in.readString();
gameCoin=in.readInt();
num=in.readString();
remake=in.readString();
anchorId=in.readLong();
anchorName=in.readString();
isAwards=in.readInt();
parentType=in.readString();
uid=in.readLong();
gameName=in.readString();
id=in.readLong();
time=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(gameId);
dest.writeString(sonType);
dest.writeInt(gameNum);
dest.writeString(addTime);
dest.writeInt(gameCoin);
dest.writeString(num);
dest.writeString(remake);
dest.writeLong(anchorId);
dest.writeString(anchorName);
dest.writeInt(isAwards);
dest.writeString(parentType);
dest.writeLong(uid);
dest.writeString(gameName);
dest.writeLong(id);
dest.writeLong(time);

}

  public static void cloneObj(GameLuckDraw source,GameLuckDraw target)
{

target.gameId=source.gameId;

target.sonType=source.sonType;

target.gameNum=source.gameNum;

target.addTime=source.addTime;

target.gameCoin=source.gameCoin;

target.num=source.num;

target.remake=source.remake;

target.anchorId=source.anchorId;

target.anchorName=source.anchorName;

target.isAwards=source.isAwards;

target.parentType=source.parentType;

target.uid=source.uid;

target.gameName=source.gameName;

target.id=source.id;

target.time=source.time;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GameLuckDraw> CREATOR = new Creator<GameLuckDraw>() {
        @Override
        public GameLuckDraw createFromParcel(Parcel in) {
            return new GameLuckDraw(in);
        }

        @Override
        public GameLuckDraw[] newArray(int size) {
            return new GameLuckDraw[size];
        }
    };
}
