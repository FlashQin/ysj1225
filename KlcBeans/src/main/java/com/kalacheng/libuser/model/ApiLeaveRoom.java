package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP离开直播响应
 */
public class ApiLeaveRoom  implements Parcelable
{
 public ApiLeaveRoom()
{
}

/**
 * 是否从麦位上退出的房间  1是 0否
 */
public int isOnAssistan;

/**
 * 用户id
 */
public long uid;

/**
 * 用户头像
 */
public String userAvatar;

/**
 * 离开后房间人数
 */
public int watchNumber;

/**
 * 房主ID
 */
public long authorId;

/**
 * 用户名称
 */
public String userName;

/**
 * 推送内容
 */
public String content;

/**
 * 房间号
 */
public long roomId;

   public ApiLeaveRoom(Parcel in) 
{
isOnAssistan=in.readInt();
uid=in.readLong();
userAvatar=in.readString();
watchNumber=in.readInt();
authorId=in.readLong();
userName=in.readString();
content=in.readString();
roomId=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(isOnAssistan);
dest.writeLong(uid);
dest.writeString(userAvatar);
dest.writeInt(watchNumber);
dest.writeLong(authorId);
dest.writeString(userName);
dest.writeString(content);
dest.writeLong(roomId);

}

  public static void cloneObj(ApiLeaveRoom source,ApiLeaveRoom target)
{

target.isOnAssistan=source.isOnAssistan;

target.uid=source.uid;

target.userAvatar=source.userAvatar;

target.watchNumber=source.watchNumber;

target.authorId=source.authorId;

target.userName=source.userName;

target.content=source.content;

target.roomId=source.roomId;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiLeaveRoom> CREATOR = new Creator<ApiLeaveRoom>() {
        @Override
        public ApiLeaveRoom createFromParcel(Parcel in) {
            return new ApiLeaveRoom(in);
        }

        @Override
        public ApiLeaveRoom[] newArray(int size) {
            return new ApiLeaveRoom[size];
        }
    };
}
