package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP主播回来直播响应
 */
public class ApiJoinRoomAnchor  implements Parcelable
{
 public ApiJoinRoomAnchor()
{
}

/**
 * null
 */
public long serialVersionUID;

/**
 * 房间号
 */
public long roomId;

/**
 * 用户名称
 */
public String userName;

/**
 * 用户id
 */
public long anchorId;

/**
 * 推送内容
 */
public String content;

   public ApiJoinRoomAnchor(Parcel in) 
{
serialVersionUID=in.readLong();
roomId=in.readLong();
userName=in.readString();
anchorId=in.readLong();
content=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(serialVersionUID);
dest.writeLong(roomId);
dest.writeString(userName);
dest.writeLong(anchorId);
dest.writeString(content);

}

  public static void cloneObj(ApiJoinRoomAnchor source,ApiJoinRoomAnchor target)
{

target.serialVersionUID=source.serialVersionUID;

target.roomId=source.roomId;

target.userName=source.userName;

target.anchorId=source.anchorId;

target.content=source.content;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiJoinRoomAnchor> CREATOR = new Creator<ApiJoinRoomAnchor>() {
        @Override
        public ApiJoinRoomAnchor createFromParcel(Parcel in) {
            return new ApiJoinRoomAnchor(in);
        }

        @Override
        public ApiJoinRoomAnchor[] newArray(int size) {
            return new ApiJoinRoomAnchor[size];
        }
    };
}
