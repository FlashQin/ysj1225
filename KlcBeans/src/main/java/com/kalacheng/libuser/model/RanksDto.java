package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 排行榜
 */
public class RanksDto  implements Parcelable
{
 public RanksDto()
{
}

/**
 * 交易金额名
 */
public String changeFieldName;

/**
 * 交易类型名
 */
public String changeName;

/**
 * 距上级所差的金币
 */
public double upperLevelDelta;

/**
 * 用户等级
 */
public int userGrade;

/**
 * 用户性别
 */
public int sex;

/**
 * 用户消费金额
 */
public double delta;

/**
 * 等级ICON
 */
public String icon;

/**
 * 用户头像
 */
public String avatar;

/**
 * 排名
 */
public int sort;

/**
 * 粉丝ID
 */
public long userId;

/**
 * 用户名
 */
public String username;

   public RanksDto(Parcel in) 
{
changeFieldName=in.readString();
changeName=in.readString();
upperLevelDelta=in.readDouble();
userGrade=in.readInt();
sex=in.readInt();
delta=in.readDouble();
icon=in.readString();
avatar=in.readString();
sort=in.readInt();
userId=in.readLong();
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(changeFieldName);
dest.writeString(changeName);
dest.writeDouble(upperLevelDelta);
dest.writeInt(userGrade);
dest.writeInt(sex);
dest.writeDouble(delta);
dest.writeString(icon);
dest.writeString(avatar);
dest.writeInt(sort);
dest.writeLong(userId);
dest.writeString(username);

}

  public static void cloneObj(RanksDto source,RanksDto target)
{

target.changeFieldName=source.changeFieldName;

target.changeName=source.changeName;

target.upperLevelDelta=source.upperLevelDelta;

target.userGrade=source.userGrade;

target.sex=source.sex;

target.delta=source.delta;

target.icon=source.icon;

target.avatar=source.avatar;

target.sort=source.sort;

target.userId=source.userId;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RanksDto> CREATOR = new Creator<RanksDto>() {
        @Override
        public RanksDto createFromParcel(Parcel in) {
            return new RanksDto(in);
        }

        @Override
        public RanksDto[] newArray(int size) {
            return new RanksDto[size];
        }
    };
}
