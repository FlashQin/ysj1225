package com.kalacheng.busgame.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 最新十条中奖信息
 */
public class GameAwardsDTO  implements Parcelable
{
 public GameAwardsDTO()
{
}

/**
 * 奖品名称
 */
public String prizeName;

/**
 * 奖品类型
 */
public int prizeType;

/**
 * 奖品数量
 */
public int prizeNum;

/**
 * 用户头像
 */
public String avatar;

/**
 * 用户名称
 */
public String userName;

/**
 * 用户id
 */
public long userId;

   public GameAwardsDTO(Parcel in) 
{
prizeName=in.readString();
prizeType=in.readInt();
prizeNum=in.readInt();
avatar=in.readString();
userName=in.readString();
userId=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(prizeName);
dest.writeInt(prizeType);
dest.writeInt(prizeNum);
dest.writeString(avatar);
dest.writeString(userName);
dest.writeLong(userId);

}

  public static void cloneObj(GameAwardsDTO source,GameAwardsDTO target)
{

target.prizeName=source.prizeName;

target.prizeType=source.prizeType;

target.prizeNum=source.prizeNum;

target.avatar=source.avatar;

target.userName=source.userName;

target.userId=source.userId;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GameAwardsDTO> CREATOR = new Creator<GameAwardsDTO>() {
        @Override
        public GameAwardsDTO createFromParcel(Parcel in) {
            return new GameAwardsDTO(in);
        }

        @Override
        public GameAwardsDTO[] newArray(int size) {
            return new GameAwardsDTO[size];
        }
    };
}
