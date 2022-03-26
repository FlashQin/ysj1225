package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP直播间发送文字消息
 */
public class ApiSendMsgRoom  implements Parcelable
{
 public ApiSendMsgRoom()
{
}

/**
 * 房间号
 */
public long roomId;

/**
 * 发送者名称
 */
public String userName;

/**
 * 发送者id
 */
public long uid;

/**
 * 推送内容
 */
public String content;

/**
 * 用户等级
 */
public String level;

/**
 * 禁言/踢出时长
 */
public int time;

/**
 * 点亮次数
 */
public int heart;

/**
 * 0系统消息1是普通消息  2弹幕消息
 */
public int type;

/**
 * 靓号
 */
public String liangName;

/**
 * 是否为VIP0不是1是vip
 */
public int vipType;

/**
 * 是否为守护0不是1是守护
 */
public int guardType;

/**
 * 是否是管理0不是1是管理
 */
public int manager;

/**
 * 直播间名称
 */
public String liveNiceName;

/**
 * 是否有直播购 0没有直播购  1有直播购
 */
public int liveFunction;

   public ApiSendMsgRoom(Parcel in) 
{
roomId=in.readLong();
userName=in.readString();
uid=in.readLong();
content=in.readString();
level=in.readString();
time=in.readInt();
heart=in.readInt();
type=in.readInt();
liangName=in.readString();
vipType=in.readInt();
guardType=in.readInt();
manager=in.readInt();
liveNiceName=in.readString();
liveFunction=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(roomId);
dest.writeString(userName);
dest.writeLong(uid);
dest.writeString(content);
dest.writeString(level);
dest.writeInt(time);
dest.writeInt(heart);
dest.writeInt(type);
dest.writeString(liangName);
dest.writeInt(vipType);
dest.writeInt(guardType);
dest.writeInt(manager);
dest.writeString(liveNiceName);
dest.writeInt(liveFunction);

}

  public static void cloneObj(ApiSendMsgRoom source,ApiSendMsgRoom target)
{

target.roomId=source.roomId;

target.userName=source.userName;

target.uid=source.uid;

target.content=source.content;

target.level=source.level;

target.time=source.time;

target.heart=source.heart;

target.type=source.type;

target.liangName=source.liangName;

target.vipType=source.vipType;

target.guardType=source.guardType;

target.manager=source.manager;

target.liveNiceName=source.liveNiceName;

target.liveFunction=source.liveFunction;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiSendMsgRoom> CREATOR = new Creator<ApiSendMsgRoom>() {
        @Override
        public ApiSendMsgRoom createFromParcel(Parcel in) {
            return new ApiSendMsgRoom(in);
        }

        @Override
        public ApiSendMsgRoom[] newArray(int size) {
            return new ApiSendMsgRoom[size];
        }
    };
}
