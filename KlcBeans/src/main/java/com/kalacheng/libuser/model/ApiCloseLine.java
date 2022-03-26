package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP关闭主播连麦
 */
public class ApiCloseLine  implements Parcelable
{
 public ApiCloseLine()
{
}

/**
 * 房间号
 */
public long roomId;

/**
 * 对方房间号
 */
public long toRoomId;

/**
 * 用户id
 */
public long uid;

/**
 * 对方id
 */
public long touid;

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

   public ApiCloseLine(Parcel in) 
{
roomId=in.readLong();
toRoomId=in.readLong();
uid=in.readLong();
touid=in.readLong();
conetnt=in.readString();
code=in.readInt();
msg=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(roomId);
dest.writeLong(toRoomId);
dest.writeLong(uid);
dest.writeLong(touid);
dest.writeString(conetnt);
dest.writeInt(code);
dest.writeString(msg);

}

  public static void cloneObj(ApiCloseLine source,ApiCloseLine target)
{

target.roomId=source.roomId;

target.toRoomId=source.toRoomId;

target.uid=source.uid;

target.touid=source.touid;

target.conetnt=source.conetnt;

target.code=source.code;

target.msg=source.msg;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiCloseLine> CREATOR = new Creator<ApiCloseLine>() {
        @Override
        public ApiCloseLine createFromParcel(Parcel in) {
            return new ApiCloseLine(in);
        }

        @Override
        public ApiCloseLine[] newArray(int size) {
            return new ApiCloseLine[size];
        }
    };
}
