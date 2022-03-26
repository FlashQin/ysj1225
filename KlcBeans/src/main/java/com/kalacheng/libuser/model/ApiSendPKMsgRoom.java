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
public class ApiSendPKMsgRoom  implements Parcelable
{
 public ApiSendPKMsgRoom()
{
}

/**
 * 响应状态1同意2拒绝3超时
 */
public int status;

/**
 * 对方房间id
 */
public long toRoomId;

/**
 * 对方用户id
 */
public long toUid;

/**
 * PK时长
 */
public int pkTime;

   public ApiSendPKMsgRoom(Parcel in) 
{
status=in.readInt();
toRoomId=in.readLong();
toUid=in.readLong();
pkTime=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(status);
dest.writeLong(toRoomId);
dest.writeLong(toUid);
dest.writeInt(pkTime);

}

  public static void cloneObj(ApiSendPKMsgRoom source,ApiSendPKMsgRoom target)
{

target.status=source.status;

target.toRoomId=source.toRoomId;

target.toUid=source.toUid;

target.pkTime=source.pkTime;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiSendPKMsgRoom> CREATOR = new Creator<ApiSendPKMsgRoom>() {
        @Override
        public ApiSendPKMsgRoom createFromParcel(Parcel in) {
            return new ApiSendPKMsgRoom(in);
        }

        @Override
        public ApiSendPKMsgRoom[] newArray(int size) {
            return new ApiSendPKMsgRoom[size];
        }
    };
}
