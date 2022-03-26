package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 动态评论消息
 */
public class ApiCommentsMsg  implements Parcelable
{
 public ApiCommentsMsg()
{
}

/**
 * 用户id
 */
public long uid;

/**
 * 评论类型-1无0只有文字1视频动态2图片动态
 */
public int sourceType;

/**
 * 消息时间
 */
public Date addtime;

/**
 * 是否已读0已读1未读
 */
public int isRead;

/**
 * 评论id
 */
public long commentId;

/**
 * 动态/短视频id
 */
public long videoId;

/**
 * 用户头像
 */
public String avatar;

/**
 * 类型1评论2点赞
 */
public int type;

/**
 * 用户名称
 */
public String userName;

/**
 * 评论内容
 */
public String content;

/**
 * 图片或视频地址
 */
public String url;

   public ApiCommentsMsg(Parcel in) 
{
uid=in.readLong();
sourceType=in.readInt();
addtime=new Date( in.readLong());
isRead=in.readInt();
commentId=in.readLong();
videoId=in.readLong();
avatar=in.readString();
type=in.readInt();
userName=in.readString();
content=in.readString();
url=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(uid);
dest.writeInt(sourceType);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeInt(isRead);
dest.writeLong(commentId);
dest.writeLong(videoId);
dest.writeString(avatar);
dest.writeInt(type);
dest.writeString(userName);
dest.writeString(content);
dest.writeString(url);

}

  public static void cloneObj(ApiCommentsMsg source,ApiCommentsMsg target)
{

target.uid=source.uid;

target.sourceType=source.sourceType;

target.addtime=source.addtime;

target.isRead=source.isRead;

target.commentId=source.commentId;

target.videoId=source.videoId;

target.avatar=source.avatar;

target.type=source.type;

target.userName=source.userName;

target.content=source.content;

target.url=source.url;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiCommentsMsg> CREATOR = new Creator<ApiCommentsMsg>() {
        @Override
        public ApiCommentsMsg createFromParcel(Parcel in) {
            return new ApiCommentsMsg(in);
        }

        @Override
        public ApiCommentsMsg[] newArray(int size) {
            return new ApiCommentsMsg[size];
        }
    };
}
