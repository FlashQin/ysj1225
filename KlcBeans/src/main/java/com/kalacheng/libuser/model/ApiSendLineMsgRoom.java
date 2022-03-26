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
public class ApiSendLineMsgRoom  implements Parcelable
{
 public ApiSendLineMsgRoom()
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
 * 麦位
 */
public int no;

/**
 * 请求者用户id
 */
public long uid;

/**
 * 对方主播头像
 */
public String toAvatar;

/**
 * 对方主播名称
 */
public String toName;

   public ApiSendLineMsgRoom(Parcel in) 
{
status=in.readInt();
toRoomId=in.readLong();
toUid=in.readLong();
no=in.readInt();
uid=in.readLong();
toAvatar=in.readString();
toName=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(status);
dest.writeLong(toRoomId);
dest.writeLong(toUid);
dest.writeInt(no);
dest.writeLong(uid);
dest.writeString(toAvatar);
dest.writeString(toName);

}

  public static void cloneObj(ApiSendLineMsgRoom source,ApiSendLineMsgRoom target)
{

target.status=source.status;

target.toRoomId=source.toRoomId;

target.toUid=source.toUid;

target.no=source.no;

target.uid=source.uid;

target.toAvatar=source.toAvatar;

target.toName=source.toName;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiSendLineMsgRoom> CREATOR = new Creator<ApiSendLineMsgRoom>() {
        @Override
        public ApiSendLineMsgRoom createFromParcel(Parcel in) {
            return new ApiSendLineMsgRoom(in);
        }

        @Override
        public ApiSendLineMsgRoom[] newArray(int size) {
            return new ApiSendLineMsgRoom[size];
        }
    };
}
