package com.kalacheng.buscommon.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 用户信息 增添了用户等级图标，主播等级图标，贵族等级图标 
 */
public class AppUserDto  implements Parcelable
{
 public AppUserDto()
{
}

/**
 * 贵族等级名称
 */
public String nobleGradeName;

/**
 * 主播等级
 */
public int anchorGrade;

/**
 * 角色类型
 */
public int role;

/**
 * 城市
 */
public String city;

/**
 * 用户等级图标
 */
public String userGradeImg;

/**
 * 直属上级
 */
public long pid;

/**
 * 短视频剩余可观看私密视频次数
 */
public int readShortVideoNumber;

/**
 * 贵族等级图标
 */
public String nobleGradeImg;

/**
 * 角色id
 */
public long userid;

/**
 * 财富等级图标
 */
public String wealthGradeImg;

/**
 * 剩余佣金/可提现佣金
 */
public double amount;

/**
 * 映票总额
 */
public double votestotal;

/**
 * 用户等级
 */
public int userGrade;

/**
 * 性别
 */
public int sex;

/**
 * 用户头像
 */
public String avatar;

/**
 * 所属公会ID
 */
public long guildId;

/**
 * 主播等级图标
 */
public String anchorGradeImg;

/**
 * 多人语音直播状态
 */
public int voiceStatus;

/**
 * 财富等级
 */
public int wealthGrade;

/**
 * 邀请码
 */
public String inviteCode;

/**
 * 映票余额/可提现金额
 */
public double votes;

/**
 * 贵族等级
 */
public int nobleGrade;

/**
 * 用户年龄
 */
public int age;

/**
 * 当前装备靓号
 */
public String goodnum;

/**
 * 用户的余额
 */
public double coin;

/**
 * 注册赠送通话时间(单位为分钟)
 */
public int registerCallTime;

/**
 * 用户名
 */
public String username;

   public AppUserDto(Parcel in) 
{
nobleGradeName=in.readString();
anchorGrade=in.readInt();
role=in.readInt();
city=in.readString();
userGradeImg=in.readString();
pid=in.readLong();
readShortVideoNumber=in.readInt();
nobleGradeImg=in.readString();
userid=in.readLong();
wealthGradeImg=in.readString();
amount=in.readDouble();
votestotal=in.readDouble();
userGrade=in.readInt();
sex=in.readInt();
avatar=in.readString();
guildId=in.readLong();
anchorGradeImg=in.readString();
voiceStatus=in.readInt();
wealthGrade=in.readInt();
inviteCode=in.readString();
votes=in.readDouble();
nobleGrade=in.readInt();
age=in.readInt();
goodnum=in.readString();
coin=in.readDouble();
registerCallTime=in.readInt();
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(nobleGradeName);
dest.writeInt(anchorGrade);
dest.writeInt(role);
dest.writeString(city);
dest.writeString(userGradeImg);
dest.writeLong(pid);
dest.writeInt(readShortVideoNumber);
dest.writeString(nobleGradeImg);
dest.writeLong(userid);
dest.writeString(wealthGradeImg);
dest.writeDouble(amount);
dest.writeDouble(votestotal);
dest.writeInt(userGrade);
dest.writeInt(sex);
dest.writeString(avatar);
dest.writeLong(guildId);
dest.writeString(anchorGradeImg);
dest.writeInt(voiceStatus);
dest.writeInt(wealthGrade);
dest.writeString(inviteCode);
dest.writeDouble(votes);
dest.writeInt(nobleGrade);
dest.writeInt(age);
dest.writeString(goodnum);
dest.writeDouble(coin);
dest.writeInt(registerCallTime);
dest.writeString(username);

}

  public static void cloneObj(AppUserDto source,AppUserDto target)
{

target.nobleGradeName=source.nobleGradeName;

target.anchorGrade=source.anchorGrade;

target.role=source.role;

target.city=source.city;

target.userGradeImg=source.userGradeImg;

target.pid=source.pid;

target.readShortVideoNumber=source.readShortVideoNumber;

target.nobleGradeImg=source.nobleGradeImg;

target.userid=source.userid;

target.wealthGradeImg=source.wealthGradeImg;

target.amount=source.amount;

target.votestotal=source.votestotal;

target.userGrade=source.userGrade;

target.sex=source.sex;

target.avatar=source.avatar;

target.guildId=source.guildId;

target.anchorGradeImg=source.anchorGradeImg;

target.voiceStatus=source.voiceStatus;

target.wealthGrade=source.wealthGrade;

target.inviteCode=source.inviteCode;

target.votes=source.votes;

target.nobleGrade=source.nobleGrade;

target.age=source.age;

target.goodnum=source.goodnum;

target.coin=source.coin;

target.registerCallTime=source.registerCallTime;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppUserDto> CREATOR = new Creator<AppUserDto>() {
        @Override
        public AppUserDto createFromParcel(Parcel in) {
            return new AppUserDto(in);
        }

        @Override
        public AppUserDto[] newArray(int size) {
            return new AppUserDto[size];
        }
    };
}
