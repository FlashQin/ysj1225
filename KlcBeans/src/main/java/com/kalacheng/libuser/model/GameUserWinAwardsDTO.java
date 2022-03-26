package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 用户中奖socket返回
 */
public class GameUserWinAwardsDTO  implements Parcelable
{
 public GameUserWinAwardsDTO()
{
}

/**
 * null
 */
public long serialVersionUID;

/**
 * 用户id
 */
public long userId;

/**
 * 用户名
 */
public String userName;

/**
 * 游戏名称
 */
public String gameName;

/**
 * 奖品名称
 */
public String awardsName;

/**
 * 奖品数量
 */
public int awardsNum;

   public GameUserWinAwardsDTO(Parcel in) 
{
serialVersionUID=in.readLong();
userId=in.readLong();
userName=in.readString();
gameName=in.readString();
awardsName=in.readString();
awardsNum=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(serialVersionUID);
dest.writeLong(userId);
dest.writeString(userName);
dest.writeString(gameName);
dest.writeString(awardsName);
dest.writeInt(awardsNum);

}

  public static void cloneObj(GameUserWinAwardsDTO source,GameUserWinAwardsDTO target)
{

target.serialVersionUID=source.serialVersionUID;

target.userId=source.userId;

target.userName=source.userName;

target.gameName=source.gameName;

target.awardsName=source.awardsName;

target.awardsNum=source.awardsNum;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GameUserWinAwardsDTO> CREATOR = new Creator<GameUserWinAwardsDTO>() {
        @Override
        public GameUserWinAwardsDTO createFromParcel(Parcel in) {
            return new GameUserWinAwardsDTO(in);
        }

        @Override
        public GameUserWinAwardsDTO[] newArray(int size) {
            return new GameUserWinAwardsDTO[size];
        }
    };
}
