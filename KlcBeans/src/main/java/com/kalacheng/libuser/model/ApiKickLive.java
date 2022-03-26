package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP直播间踢人
 */
public class ApiKickLive  implements Parcelable
{
 public ApiKickLive()
{
}

/**
 * 房间号
 */
public long roomId;

/**
 * 踢人用户id)
 */
public long uid;

/**
 * 被踢用户id
 */
public long touid;

/**
 * 被踢人名称
 */
public String toUserName;

/**
 * 内容
 */
public String content;

/**
 * 离开后房间人数
 */
public int watchNumber;

/**
 * 状态1成功2失败
 */
public int code;

/**
 * 状态描述
 */
public String msg;

   public ApiKickLive(Parcel in) 
{
roomId=in.readLong();
uid=in.readLong();
touid=in.readLong();
toUserName=in.readString();
content=in.readString();
watchNumber=in.readInt();
code=in.readInt();
msg=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(roomId);
dest.writeLong(uid);
dest.writeLong(touid);
dest.writeString(toUserName);
dest.writeString(content);
dest.writeInt(watchNumber);
dest.writeInt(code);
dest.writeString(msg);

}

  public static void cloneObj(ApiKickLive source,ApiKickLive target)
{

target.roomId=source.roomId;

target.uid=source.uid;

target.touid=source.touid;

target.toUserName=source.toUserName;

target.content=source.content;

target.watchNumber=source.watchNumber;

target.code=source.code;

target.msg=source.msg;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiKickLive> CREATOR = new Creator<ApiKickLive>() {
        @Override
        public ApiKickLive createFromParcel(Parcel in) {
            return new ApiKickLive(in);
        }

        @Override
        public ApiKickLive[] newArray(int size) {
            return new ApiKickLive[size];
        }
    };
}
