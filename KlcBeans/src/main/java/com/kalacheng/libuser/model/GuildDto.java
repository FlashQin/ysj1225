package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 公会资料
 */
public class GuildDto  implements Parcelable
{
 public GuildDto()
{
}

/**
 * 退出状态
 */
public int quitState;

/**
 * 用户状态, 数据库状态无关
 */
public int userState;

/**
 * 公会名称
 */
public String name;

/**
 * 家族LOGO
 */
public String logo;

/**
 * 公会成员申请记录ID
 */
public long guildProfitId;

/**
 * 公会ID
 */
public long guildId;

/**
 * 公会简介
 */
public String desr;

   public GuildDto(Parcel in) 
{
quitState=in.readInt();
userState=in.readInt();
name=in.readString();
logo=in.readString();
guildProfitId=in.readLong();
guildId=in.readLong();
desr=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(quitState);
dest.writeInt(userState);
dest.writeString(name);
dest.writeString(logo);
dest.writeLong(guildProfitId);
dest.writeLong(guildId);
dest.writeString(desr);

}

  public static void cloneObj(GuildDto source,GuildDto target)
{

target.quitState=source.quitState;

target.userState=source.userState;

target.name=source.name;

target.logo=source.logo;

target.guildProfitId=source.guildProfitId;

target.guildId=source.guildId;

target.desr=source.desr;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GuildDto> CREATOR = new Creator<GuildDto>() {
        @Override
        public GuildDto createFromParcel(Parcel in) {
            return new GuildDto(in);
        }

        @Override
        public GuildDto[] newArray(int size) {
            return new GuildDto[size];
        }
    };
}
