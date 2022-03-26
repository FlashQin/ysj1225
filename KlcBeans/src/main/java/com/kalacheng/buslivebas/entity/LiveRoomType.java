package com.kalacheng.buslivebas.entity;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 房间模式
 */
public class LiveRoomType  implements Parcelable
{
 public LiveRoomType()
{
}

/**
 * null
 */
public long id;

/**
 * 房间名称
 */
public String roomName;

/**
 * 房间模式标识 0:普通房间 1:私密房间 2:收费房间 3:计时房间 4:贵族房间
 */
public int roomType;

   public LiveRoomType(Parcel in) 
{
id=in.readLong();
roomName=in.readString();
roomType=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(id);
dest.writeString(roomName);
dest.writeInt(roomType);

}

  public static void cloneObj(LiveRoomType source,LiveRoomType target)
{

target.id=source.id;

target.roomName=source.roomName;

target.roomType=source.roomType;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LiveRoomType> CREATOR = new Creator<LiveRoomType>() {
        @Override
        public LiveRoomType createFromParcel(Parcel in) {
            return new LiveRoomType(in);
        }

        @Override
        public LiveRoomType[] newArray(int size) {
            return new LiveRoomType[size];
        }
    };
}
