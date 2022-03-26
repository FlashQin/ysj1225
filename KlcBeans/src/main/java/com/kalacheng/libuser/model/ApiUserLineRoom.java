package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP互动请求回应响应
 */
public class ApiUserLineRoom  implements Parcelable
{
 public ApiUserLineRoom()
{
}

/**
 * 响应状态1同意2拒绝3超时
 */
public int status;

/**
 * uid
 */
public long uid;

/**
 * 对方房间id
 */
public long toRoomId;

/**
 * 对方用户id
 */
public long toUid;

/**
 * 对方流名
 */
public String toStream;

/**
 * 对方主播头像
 */
public String toAvatar;

/**
 * 对方主播名称
 */
public String toName;

   public ApiUserLineRoom(Parcel in) 
{
status=in.readInt();
uid=in.readLong();
toRoomId=in.readLong();
toUid=in.readLong();
toStream=in.readString();
toAvatar=in.readString();
toName=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(status);
dest.writeLong(uid);
dest.writeLong(toRoomId);
dest.writeLong(toUid);
dest.writeString(toStream);
dest.writeString(toAvatar);
dest.writeString(toName);

}

  public static void cloneObj(ApiUserLineRoom source,ApiUserLineRoom target)
{

target.status=source.status;

target.uid=source.uid;

target.toRoomId=source.toRoomId;

target.toUid=source.toUid;

target.toStream=source.toStream;

target.toAvatar=source.toAvatar;

target.toName=source.toName;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiUserLineRoom> CREATOR = new Creator<ApiUserLineRoom>() {
        @Override
        public ApiUserLineRoom createFromParcel(Parcel in) {
            return new ApiUserLineRoom(in);
        }

        @Override
        public ApiUserLineRoom[] newArray(int size) {
            return new ApiUserLineRoom[size];
        }
    };
}
