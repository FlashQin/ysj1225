package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP主播离开直播响应
 */
public class ApiLeaveRoomAnchor  implements Parcelable
{
 public ApiLeaveRoomAnchor()
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
 * 主播名称
 */
public String userName;

/**
 * 主播id
 */
public long anchorId;

/**
 * 推送内容
 */
public String content;

   public ApiLeaveRoomAnchor(Parcel in) 
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

  public static void cloneObj(ApiLeaveRoomAnchor source,ApiLeaveRoomAnchor target)
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

    public static final Creator<ApiLeaveRoomAnchor> CREATOR = new Creator<ApiLeaveRoomAnchor>() {
        @Override
        public ApiLeaveRoomAnchor createFromParcel(Parcel in) {
            return new ApiLeaveRoomAnchor(in);
        }

        @Override
        public ApiLeaveRoomAnchor[] newArray(int size) {
            return new ApiLeaveRoomAnchor[size];
        }
    };
}
