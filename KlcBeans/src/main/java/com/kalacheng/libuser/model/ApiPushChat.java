package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP抢聊列表
 */
public class ApiPushChat  implements Parcelable
{
 public ApiPushChat()
{
}

/**
 * 用户在线状态 0:离线 1:在线
 */
public int onlineStatus;

/**
 * 用户等级
 */
public String userGradeImg;

/**
 * 用户头像
 */
public String avatar;

/**
 * sessionID
 */
public long sessionID;

/**
 * 主播id
 */
public long anchorId;

/**
 * 话费id
 */
public long feeId;

/**
 * 用户id
 */
public long userId;

/**
 * 查询内容
 */
public String search;

/**
 * 发布时间
 */
public Date createTime;

/**
 * null
 */
public long id;

/**
 * 聊天类型 1:视频聊天 2:语音聊天
 */
public int chatType;

/**
 * 话费金额/分钟
 */
public int coin;

/**
 * 状态 0:发布中 1:通话中 2:已结束
 */
public int status;

/**
 * 用户贵族
 */
public String userNobleImg;

/**
 * 用户名
 */
public String username;

   public ApiPushChat(Parcel in) 
{
onlineStatus=in.readInt();
userGradeImg=in.readString();
avatar=in.readString();
sessionID=in.readLong();
anchorId=in.readLong();
feeId=in.readLong();
userId=in.readLong();
search=in.readString();
createTime=new Date( in.readLong());
id=in.readLong();
chatType=in.readInt();
coin=in.readInt();
status=in.readInt();
userNobleImg=in.readString();
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(onlineStatus);
dest.writeString(userGradeImg);
dest.writeString(avatar);
dest.writeLong(sessionID);
dest.writeLong(anchorId);
dest.writeLong(feeId);
dest.writeLong(userId);
dest.writeString(search);
dest.writeLong(createTime==null?0:createTime.getTime());
dest.writeLong(id);
dest.writeInt(chatType);
dest.writeInt(coin);
dest.writeInt(status);
dest.writeString(userNobleImg);
dest.writeString(username);

}

  public static void cloneObj(ApiPushChat source,ApiPushChat target)
{

target.onlineStatus=source.onlineStatus;

target.userGradeImg=source.userGradeImg;

target.avatar=source.avatar;

target.sessionID=source.sessionID;

target.anchorId=source.anchorId;

target.feeId=source.feeId;

target.userId=source.userId;

target.search=source.search;

target.createTime=source.createTime;

target.id=source.id;

target.chatType=source.chatType;

target.coin=source.coin;

target.status=source.status;

target.userNobleImg=source.userNobleImg;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiPushChat> CREATOR = new Creator<ApiPushChat>() {
        @Override
        public ApiPushChat createFromParcel(Parcel in) {
            return new ApiPushChat(in);
        }

        @Override
        public ApiPushChat[] newArray(int size) {
            return new ApiPushChat[size];
        }
    };
}
