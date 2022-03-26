package com.kalacheng.busgame.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 用户中奖记录表
 */
public class GamePrizeRecord  implements Parcelable
{
 public GamePrizeRecord()
{
}

/**
 * 奖项id
 */
public long awardsId;

/**
 * 添加时间
 */
public String addTime;

/**
 * 获奖奖项数量
 */
public int awardsNum;

/**
 * 获奖礼物名称
 */
public String giftName;

/**
 * 游戏id
 */
public long gameKindId;

/**
 * 备注
 */
public String remake;

/**
 * 用户姓名
 */
public String userName;

/**
 * 奖项类型0金币1礼物
 */
public int awardsType;

/**
 * 奖品图片
 */
public String picture;

/**
 * 获奖礼物id
 */
public long giftId;

/**
 * 用户id
 */
public long uid;

/**
 * 获奖金币
 */
public double awardsCoin;

/**
 * 奖项名称
 */
public String awardsName;

/**
 * null
 */
public long id;

/**
 * 时间对应毫秒值
 */
public long time;

/**
 * 抽奖记录Id
 */
public long luckDrawId;

   public GamePrizeRecord(Parcel in) 
{
awardsId=in.readLong();
addTime=in.readString();
awardsNum=in.readInt();
giftName=in.readString();
gameKindId=in.readLong();
remake=in.readString();
userName=in.readString();
awardsType=in.readInt();
picture=in.readString();
giftId=in.readLong();
uid=in.readLong();
awardsCoin=in.readDouble();
awardsName=in.readString();
id=in.readLong();
time=in.readLong();
luckDrawId=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(awardsId);
dest.writeString(addTime);
dest.writeInt(awardsNum);
dest.writeString(giftName);
dest.writeLong(gameKindId);
dest.writeString(remake);
dest.writeString(userName);
dest.writeInt(awardsType);
dest.writeString(picture);
dest.writeLong(giftId);
dest.writeLong(uid);
dest.writeDouble(awardsCoin);
dest.writeString(awardsName);
dest.writeLong(id);
dest.writeLong(time);
dest.writeLong(luckDrawId);

}

  public static void cloneObj(GamePrizeRecord source,GamePrizeRecord target)
{

target.awardsId=source.awardsId;

target.addTime=source.addTime;

target.awardsNum=source.awardsNum;

target.giftName=source.giftName;

target.gameKindId=source.gameKindId;

target.remake=source.remake;

target.userName=source.userName;

target.awardsType=source.awardsType;

target.picture=source.picture;

target.giftId=source.giftId;

target.uid=source.uid;

target.awardsCoin=source.awardsCoin;

target.awardsName=source.awardsName;

target.id=source.id;

target.time=source.time;

target.luckDrawId=source.luckDrawId;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GamePrizeRecord> CREATOR = new Creator<GamePrizeRecord>() {
        @Override
        public GamePrizeRecord createFromParcel(Parcel in) {
            return new GamePrizeRecord(in);
        }

        @Override
        public GamePrizeRecord[] newArray(int size) {
            return new GamePrizeRecord[size];
        }
    };
}
