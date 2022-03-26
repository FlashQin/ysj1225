package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 1vs1-求聊筛选条件
 */
public class AdminRushToTalkSeach  implements Parcelable
{
 public AdminRushToTalkSeach()
{
}

/**
 * 添加时间
 */
public Date createTime;

/**
 * 条件名称
 */
public String name;

/**
 * null
 */
public long id;

/**
 * 类型1外貌筛选
 */
public int type;

/**
 * 聊天类型1语音聊天2视频聊天
 */
public int chatType;

   public AdminRushToTalkSeach(Parcel in) 
{
createTime=new Date( in.readLong());
name=in.readString();
id=in.readLong();
type=in.readInt();
chatType=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(createTime==null?0:createTime.getTime());
dest.writeString(name);
dest.writeLong(id);
dest.writeInt(type);
dest.writeInt(chatType);

}

  public static void cloneObj(AdminRushToTalkSeach source,AdminRushToTalkSeach target)
{

target.createTime=source.createTime;

target.name=source.name;

target.id=source.id;

target.type=source.type;

target.chatType=source.chatType;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdminRushToTalkSeach> CREATOR = new Creator<AdminRushToTalkSeach>() {
        @Override
        public AdminRushToTalkSeach createFromParcel(Parcel in) {
            return new AdminRushToTalkSeach(in);
        }

        @Override
        public AdminRushToTalkSeach[] newArray(int size) {
            return new AdminRushToTalkSeach[size];
        }
    };
}
