package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 评论列表
 */
public class ApiUsersVideoComments  implements Parcelable
{
 public ApiUsersVideoComments()
{
}

/**
 * 评论时间(展示)
 */
public String addtimeStr;

/**
 * 用户id
 */
public long uid;

/**
 * 用户id
 */
public long toUid;

/**
 * 评论时间
 */
public String addtime;

/**
 * 评论类型1评论2回复
 */
public int commentType;

/**
 * 评论id
 */
public long commentid;

/**
 * 用户名称
 */
public String toUserName;

/**
 * 回复列表
 */
public List<com.kalacheng.libuser.model.ApiUsersVideoComments> replyList;

/**
 * 用户头像
 */
public String avatar;

/**
 * 用户名称
 */
public String userName;

/**
 * 评论内容
 */
public String content;

   public ApiUsersVideoComments(Parcel in) 
{
addtimeStr=in.readString();
uid=in.readLong();
toUid=in.readLong();
addtime=in.readString();
commentType=in.readInt();
commentid=in.readLong();
toUserName=in.readString();

if(replyList==null){
replyList=  new ArrayList<>();
 }
in.readTypedList(replyList,com.kalacheng.libuser.model.ApiUsersVideoComments.CREATOR);
avatar=in.readString();
userName=in.readString();
content=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(addtimeStr);
dest.writeLong(uid);
dest.writeLong(toUid);
dest.writeString(addtime);
dest.writeInt(commentType);
dest.writeLong(commentid);
dest.writeString(toUserName);

dest.writeTypedList(replyList);
dest.writeString(avatar);
dest.writeString(userName);
dest.writeString(content);

}

  public static void cloneObj(ApiUsersVideoComments source,ApiUsersVideoComments target)
{

target.addtimeStr=source.addtimeStr;

target.uid=source.uid;

target.toUid=source.toUid;

target.addtime=source.addtime;

target.commentType=source.commentType;

target.commentid=source.commentid;

target.toUserName=source.toUserName;

        if(source.replyList==null)
        {
            target.replyList=null;
        }else
        {
            target.replyList=new ArrayList();
            for(int i=0;i<source.replyList.size();i++)
            {
            ApiUsersVideoComments.cloneObj(source.replyList.get(i),target.replyList.get(i));
            }
        }


target.avatar=source.avatar;

target.userName=source.userName;

target.content=source.content;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiUsersVideoComments> CREATOR = new Creator<ApiUsersVideoComments>() {
        @Override
        public ApiUsersVideoComments createFromParcel(Parcel in) {
            return new ApiUsersVideoComments(in);
        }

        @Override
        public ApiUsersVideoComments[] newArray(int size) {
            return new ApiUsersVideoComments[size];
        }
    };
}
