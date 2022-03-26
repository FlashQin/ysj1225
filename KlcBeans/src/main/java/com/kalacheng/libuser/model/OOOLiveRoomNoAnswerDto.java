package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP遇见主播端未接来电
 */
public class OOOLiveRoomNoAnswerDto  implements Parcelable
{
 public OOOLiveRoomNoAnswerDto()
{
}

/**
 * 用户id
 */
public long uid;

/**
 * 结束时间
 */
public Date createTime;

/**
 * 用户在线状态,0离线1在线
 */
public int onlineStatus;

/**
 * 时间
 */
public String timeStr;

/**
 * 观众ID
 */
public long audienceId;

/**
 * 用户头像
 */
public String avatar;

/**
 * null
 */
public long id;

/**
 * 类型0语音1视频
 */
public int type;

/**
 * 主播海报
 */
public String poster;

/**
 * 用户名称
 */
public String username;

   public OOOLiveRoomNoAnswerDto(Parcel in) 
{
uid=in.readLong();
createTime=new Date( in.readLong());
onlineStatus=in.readInt();
timeStr=in.readString();
audienceId=in.readLong();
avatar=in.readString();
id=in.readLong();
type=in.readInt();
poster=in.readString();
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(uid);
dest.writeLong(createTime==null?0:createTime.getTime());
dest.writeInt(onlineStatus);
dest.writeString(timeStr);
dest.writeLong(audienceId);
dest.writeString(avatar);
dest.writeLong(id);
dest.writeInt(type);
dest.writeString(poster);
dest.writeString(username);

}

  public static void cloneObj(OOOLiveRoomNoAnswerDto source,OOOLiveRoomNoAnswerDto target)
{

target.uid=source.uid;

target.createTime=source.createTime;

target.onlineStatus=source.onlineStatus;

target.timeStr=source.timeStr;

target.audienceId=source.audienceId;

target.avatar=source.avatar;

target.id=source.id;

target.type=source.type;

target.poster=source.poster;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OOOLiveRoomNoAnswerDto> CREATOR = new Creator<OOOLiveRoomNoAnswerDto>() {
        @Override
        public OOOLiveRoomNoAnswerDto createFromParcel(Parcel in) {
            return new OOOLiveRoomNoAnswerDto(in);
        }

        @Override
        public OOOLiveRoomNoAnswerDto[] newArray(int size) {
            return new OOOLiveRoomNoAnswerDto[size];
        }
    };
}
