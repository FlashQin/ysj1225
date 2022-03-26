package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 修改房间模式,发送的socket实体
 */
public class ApiExitRoom  implements Parcelable
{
 public ApiExitRoom()
{
}

/**
 * 房间号
 */
public long roomId;

/**
 * 房间模式标识 0:普通房间 1:私密房间 2:收费房间 3:计时房间 4:贵族房间
 */
public int roomType;

/**
 * 房间模式对应的值
 */
public String roomTypeVal;

/**
 * 房间名称
 */
public String roomName;

/**
 * 消息内容
 */
public String conetnt;

/**
 * 状态1成功2失败
 */
public int code;

/**
 * 状态描述
 */
public String msg;

   public ApiExitRoom(Parcel in) 
{
roomId=in.readLong();
roomType=in.readInt();
roomTypeVal=in.readString();
roomName=in.readString();
conetnt=in.readString();
code=in.readInt();
msg=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(roomId);
dest.writeInt(roomType);
dest.writeString(roomTypeVal);
dest.writeString(roomName);
dest.writeString(conetnt);
dest.writeInt(code);
dest.writeString(msg);

}

  public static void cloneObj(ApiExitRoom source,ApiExitRoom target)
{

target.roomId=source.roomId;

target.roomType=source.roomType;

target.roomTypeVal=source.roomTypeVal;

target.roomName=source.roomName;

target.conetnt=source.conetnt;

target.code=source.code;

target.msg=source.msg;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiExitRoom> CREATOR = new Creator<ApiExitRoom>() {
        @Override
        public ApiExitRoom createFromParcel(Parcel in) {
            return new ApiExitRoom(in);
        }

        @Override
        public ApiExitRoom[] newArray(int size) {
            return new ApiExitRoom[size];
        }
    };
}
