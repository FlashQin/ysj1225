package com.kalacheng.buscommon.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 公会排行榜的vo
 */
public class GuildListVO  implements Parcelable
{
 public GuildListVO()
{
}

/**
 * 序号
 */
public double serialNumber;

/**
 * 公会头像
 */
public String guildAvatar;

/**
 * 公会收益
 */
public double guildTotalVotes;

/**
 * 公会id
 */
public long guildId;

/**
 * 公会名称
 */
public String guildName;

   public GuildListVO(Parcel in) 
{
serialNumber=in.readDouble();
guildAvatar=in.readString();
guildTotalVotes=in.readDouble();
guildId=in.readLong();
guildName=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeDouble(serialNumber);
dest.writeString(guildAvatar);
dest.writeDouble(guildTotalVotes);
dest.writeLong(guildId);
dest.writeString(guildName);

}

  public static void cloneObj(GuildListVO source,GuildListVO target)
{

target.serialNumber=source.serialNumber;

target.guildAvatar=source.guildAvatar;

target.guildTotalVotes=source.guildTotalVotes;

target.guildId=source.guildId;

target.guildName=source.guildName;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GuildListVO> CREATOR = new Creator<GuildListVO>() {
        @Override
        public GuildListVO createFromParcel(Parcel in) {
            return new GuildListVO(in);
        }

        @Override
        public GuildListVO[] newArray(int size) {
            return new GuildListVO[size];
        }
    };
}
