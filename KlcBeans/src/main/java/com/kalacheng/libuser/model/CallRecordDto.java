package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 通话记录数据
 */
public class CallRecordDto  implements Parcelable
{
 public CallRecordDto()
{
}

/**
 * 创建时间
 */
public Date createTime;

/**
 * 第n次通话
 */
public int num;

/**
 * 是否视频直播 0：语音  1：视频
 */
public int isVideo;

/**
 * 创建时间描述
 */
public String createTimeDesr;

/**
 * 时长描述
 */
public String longTimeDesr;

/**
 * 开始时间
 */
public Date startTime;

/**
 * 头像
 */
public String avatar;

/**
 * 结束时间
 */
public Date endTime;

/**
 * 主播ID
 */
public long anchorId;

/**
 * 观众ID
 */
public long userId;

/**
 * 用户名
 */
public String username;

   public CallRecordDto(Parcel in) 
{
createTime=new Date( in.readLong());
num=in.readInt();
isVideo=in.readInt();
createTimeDesr=in.readString();
longTimeDesr=in.readString();
startTime=new Date( in.readLong());
avatar=in.readString();
endTime=new Date( in.readLong());
anchorId=in.readLong();
userId=in.readLong();
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(createTime==null?0:createTime.getTime());
dest.writeInt(num);
dest.writeInt(isVideo);
dest.writeString(createTimeDesr);
dest.writeString(longTimeDesr);
dest.writeLong(startTime==null?0:startTime.getTime());
dest.writeString(avatar);
dest.writeLong(endTime==null?0:endTime.getTime());
dest.writeLong(anchorId);
dest.writeLong(userId);
dest.writeString(username);

}

  public static void cloneObj(CallRecordDto source,CallRecordDto target)
{

target.createTime=source.createTime;

target.num=source.num;

target.isVideo=source.isVideo;

target.createTimeDesr=source.createTimeDesr;

target.longTimeDesr=source.longTimeDesr;

target.startTime=source.startTime;

target.avatar=source.avatar;

target.endTime=source.endTime;

target.anchorId=source.anchorId;

target.userId=source.userId;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CallRecordDto> CREATOR = new Creator<CallRecordDto>() {
        @Override
        public CallRecordDto createFromParcel(Parcel in) {
            return new CallRecordDto(in);
        }

        @Override
        public CallRecordDto[] newArray(int size) {
            return new CallRecordDto[size];
        }
    };
}
