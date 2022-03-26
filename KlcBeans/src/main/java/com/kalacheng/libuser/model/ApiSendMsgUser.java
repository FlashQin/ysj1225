package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP给用户发送消息
 */
public class ApiSendMsgUser  implements Parcelable
{
 public ApiSendMsgUser()
{
}

/**
 * 用户id
 */
public long userId;

/**
 * 房间id
 */
public long roomId;

/**
 * 用户名
 */
public String userName;

/**
 * 推送内容
 */
public String content;

/**
 * 连麦倒计时
 */
public int line_time;

/**
 * 大头像
 */
public String avatar;

/**
 * 小头像
 */
public String avatarThumb;

/**
 * 房间类型0是一般直播，1是私密直播
 */
public int type;

/**
 * 会话ID,用于连麦、互动、PK的整个过程
 */
public long sessionID;

/**
 * 状态1成功2失败
 */
public int code;

/**
 * 状态描述
 */
public String msg;

   public ApiSendMsgUser(Parcel in) 
{
userId=in.readLong();
roomId=in.readLong();
userName=in.readString();
content=in.readString();
line_time=in.readInt();
avatar=in.readString();
avatarThumb=in.readString();
type=in.readInt();
sessionID=in.readLong();
code=in.readInt();
msg=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(userId);
dest.writeLong(roomId);
dest.writeString(userName);
dest.writeString(content);
dest.writeInt(line_time);
dest.writeString(avatar);
dest.writeString(avatarThumb);
dest.writeInt(type);
dest.writeLong(sessionID);
dest.writeInt(code);
dest.writeString(msg);

}

  public static void cloneObj(ApiSendMsgUser source,ApiSendMsgUser target)
{

target.userId=source.userId;

target.roomId=source.roomId;

target.userName=source.userName;

target.content=source.content;

target.line_time=source.line_time;

target.avatar=source.avatar;

target.avatarThumb=source.avatarThumb;

target.type=source.type;

target.sessionID=source.sessionID;

target.code=source.code;

target.msg=source.msg;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiSendMsgUser> CREATOR = new Creator<ApiSendMsgUser>() {
        @Override
        public ApiSendMsgUser createFromParcel(Parcel in) {
            return new ApiSendMsgUser(in);
        }

        @Override
        public ApiSendMsgUser[] newArray(int size) {
            return new ApiSendMsgUser[size];
        }
    };
}
