package com.kalacheng.buscommon.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 我的邀请码-收入排行
 */
public class AppUserIncomeRankingDto  implements Parcelable
{
 public AppUserIncomeRankingDto()
{
}

/**
 * 小头像
 */
public String avatarThumb;

/**
 * 收入总金额
 */
public double totalAmount;

/**
 * 1:在排名内 0: 不在排名内 是否在app端显示的排名里面,例如 1 - 100
 */
public int isRanking;

/**
 * 序号
 */
public double serialNumber;

/**
 * 用户头像
 */
public String avatar;

/**
 * 邀请人数
 */
public int numberOfInvitations;

/**
 * 用户id 
 */
public long userid;

   public AppUserIncomeRankingDto(Parcel in) 
{
avatarThumb=in.readString();
totalAmount=in.readDouble();
isRanking=in.readInt();
serialNumber=in.readDouble();
avatar=in.readString();
numberOfInvitations=in.readInt();
userid=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(avatarThumb);
dest.writeDouble(totalAmount);
dest.writeInt(isRanking);
dest.writeDouble(serialNumber);
dest.writeString(avatar);
dest.writeInt(numberOfInvitations);
dest.writeLong(userid);

}

  public static void cloneObj(AppUserIncomeRankingDto source,AppUserIncomeRankingDto target)
{

target.avatarThumb=source.avatarThumb;

target.totalAmount=source.totalAmount;

target.isRanking=source.isRanking;

target.serialNumber=source.serialNumber;

target.avatar=source.avatar;

target.numberOfInvitations=source.numberOfInvitations;

target.userid=source.userid;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppUserIncomeRankingDto> CREATOR = new Creator<AppUserIncomeRankingDto>() {
        @Override
        public AppUserIncomeRankingDto createFromParcel(Parcel in) {
            return new AppUserIncomeRankingDto(in);
        }

        @Override
        public AppUserIncomeRankingDto[] newArray(int size) {
            return new AppUserIncomeRankingDto[size];
        }
    };
}
