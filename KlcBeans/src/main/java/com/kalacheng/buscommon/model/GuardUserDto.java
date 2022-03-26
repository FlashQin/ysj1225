package com.kalacheng.buscommon.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 守护结果
 */
public class GuardUserDto  implements Parcelable
{
 public GuardUserDto()
{
}

/**
 * 用户头像
 */
public String userHeadImg;

/**
 * 欠费是天数
 */
public long freeDay;

/**
 * 被守护用户头像
 */
public String anchorIdImg;

/**
 * 创建时间
 */
public Date addtime;

/**
 * 剩余天数
 */
public long leftDay;

/**
 * 已守护天数
 */
public long guardDay;

/**
 * 开始守护时间
 */
public Date startTime;

/**
 * 结束守护时间
 */
public Date endTime;

/**
 * 被守护用户ID
 */
public long anchorId;

/**
 * 用户ID
 */
public long userId;

/**
 * 当前时间
 */
public Date nowTime;

/**
 * 用户名称
 */
public String username;

   public GuardUserDto(Parcel in) 
{
userHeadImg=in.readString();
freeDay=in.readLong();
anchorIdImg=in.readString();
addtime=new Date( in.readLong());
leftDay=in.readLong();
guardDay=in.readLong();
startTime=new Date( in.readLong());
endTime=new Date( in.readLong());
anchorId=in.readLong();
userId=in.readLong();
nowTime=new Date( in.readLong());
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(userHeadImg);
dest.writeLong(freeDay);
dest.writeString(anchorIdImg);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeLong(leftDay);
dest.writeLong(guardDay);
dest.writeLong(startTime==null?0:startTime.getTime());
dest.writeLong(endTime==null?0:endTime.getTime());
dest.writeLong(anchorId);
dest.writeLong(userId);
dest.writeLong(nowTime==null?0:nowTime.getTime());
dest.writeString(username);

}

  public static void cloneObj(GuardUserDto source,GuardUserDto target)
{

target.userHeadImg=source.userHeadImg;

target.freeDay=source.freeDay;

target.anchorIdImg=source.anchorIdImg;

target.addtime=source.addtime;

target.leftDay=source.leftDay;

target.guardDay=source.guardDay;

target.startTime=source.startTime;

target.endTime=source.endTime;

target.anchorId=source.anchorId;

target.userId=source.userId;

target.nowTime=source.nowTime;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GuardUserDto> CREATOR = new Creator<GuardUserDto>() {
        @Override
        public GuardUserDto createFromParcel(Parcel in) {
            return new GuardUserDto(in);
        }

        @Override
        public GuardUserDto[] newArray(int size) {
            return new GuardUserDto[size];
        }
    };
}
