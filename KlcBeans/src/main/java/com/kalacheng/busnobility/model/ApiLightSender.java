package com.kalacheng.busnobility.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP点亮发送消息
 */
public class ApiLightSender  implements Parcelable
{
 public ApiLightSender()
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
 * 状态1成功2失败
 */
public int code;

/**
 * 状态描述
 */
public String msg;

   public ApiLightSender(Parcel in) 
{
roomId=in.readLong();
userName=in.readString();
uid=in.readLong();
content=in.readString();
level=in.readString();
time=in.readInt();
heart=in.readInt();
liangName=in.readString();
vipType=in.readInt();
guardType=in.readInt();
code=in.readInt();
msg=in.readString();

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
dest.writeString(liangName);
dest.writeInt(vipType);
dest.writeInt(guardType);
dest.writeInt(code);
dest.writeString(msg);

}

  public static void cloneObj(ApiLightSender source,ApiLightSender target)
{

target.roomId=source.roomId;

target.userName=source.userName;

target.uid=source.uid;

target.content=source.content;

target.level=source.level;

target.time=source.time;

target.heart=source.heart;

target.liangName=source.liangName;

target.vipType=source.vipType;

target.guardType=source.guardType;

target.code=source.code;

target.msg=source.msg;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiLightSender> CREATOR = new Creator<ApiLightSender>() {
        @Override
        public ApiLightSender createFromParcel(Parcel in) {
            return new ApiLightSender(in);
        }

        @Override
        public ApiLightSender[] newArray(int size) {
            return new ApiLightSender[size];
        }
    };
}
