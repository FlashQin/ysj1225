package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 用户评价
 */
public class ApiUserComment  implements Parcelable
{
 public ApiUserComment()
{
}

/**
 * 添加时间(展示)
 */
public String addtimeStr;

/**
 * 添加时间
 */
public Date addtime;

/**
 * 用户头像
 */
public String avatar;

/**
 * 主播id
 */
public long anchorId;

/**
 * 用户名称
 */
public String userName;

/**
 * 用户id
 */
public long userId;

/**
 * cid
 */
public long cid;

/**
 * 评价内容标签(json)
 */
public String labels;

   public ApiUserComment(Parcel in) 
{
addtimeStr=in.readString();
addtime=new Date( in.readLong());
avatar=in.readString();
anchorId=in.readLong();
userName=in.readString();
userId=in.readLong();
cid=in.readLong();
labels=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(addtimeStr);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeString(avatar);
dest.writeLong(anchorId);
dest.writeString(userName);
dest.writeLong(userId);
dest.writeLong(cid);
dest.writeString(labels);

}

  public static void cloneObj(ApiUserComment source,ApiUserComment target)
{

target.addtimeStr=source.addtimeStr;

target.addtime=source.addtime;

target.avatar=source.avatar;

target.anchorId=source.anchorId;

target.userName=source.userName;

target.userId=source.userId;

target.cid=source.cid;

target.labels=source.labels;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiUserComment> CREATOR = new Creator<ApiUserComment>() {
        @Override
        public ApiUserComment createFromParcel(Parcel in) {
            return new ApiUserComment(in);
        }

        @Override
        public ApiUserComment[] newArray(int size) {
            return new ApiUserComment[size];
        }
    };
}
