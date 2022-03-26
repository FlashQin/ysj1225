package com.kalacheng.busooolive.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 1v1文字聊天数据
 */
public class OOOLiveRoomTextChatData  implements Parcelable
{
 public OOOLiveRoomTextChatData()
{
}

/**
 * 第一次聊天时间
 */
public Date firstTime;

/**
 * 认识天数
 */
public int knowDay;

/**
 * 对方用户id
 */
public long hostUid;

/**
 * 当前用户id
 */
public long feeUid;

/**
 * 热度
 */
public int hotNumber;

/**
 * 聊天次数
 */
public int chatNumber;

/**
 * null
 */
public long id;

   public OOOLiveRoomTextChatData(Parcel in) 
{
firstTime=new Date( in.readLong());
knowDay=in.readInt();
hostUid=in.readLong();
feeUid=in.readLong();
hotNumber=in.readInt();
chatNumber=in.readInt();
id=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(firstTime==null?0:firstTime.getTime());
dest.writeInt(knowDay);
dest.writeLong(hostUid);
dest.writeLong(feeUid);
dest.writeInt(hotNumber);
dest.writeInt(chatNumber);
dest.writeLong(id);

}

  public static void cloneObj(OOOLiveRoomTextChatData source,OOOLiveRoomTextChatData target)
{

target.firstTime=source.firstTime;

target.knowDay=source.knowDay;

target.hostUid=source.hostUid;

target.feeUid=source.feeUid;

target.hotNumber=source.hotNumber;

target.chatNumber=source.chatNumber;

target.id=source.id;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OOOLiveRoomTextChatData> CREATOR = new Creator<OOOLiveRoomTextChatData>() {
        @Override
        public OOOLiveRoomTextChatData createFromParcel(Parcel in) {
            return new OOOLiveRoomTextChatData(in);
        }

        @Override
        public OOOLiveRoomTextChatData[] newArray(int size) {
            return new OOOLiveRoomTextChatData[size];
        }
    };
}
