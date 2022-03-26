package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP计时房间余额不足退出房间
 */
public class ApiTimerExitRoom  implements Parcelable
{
 public ApiTimerExitRoom()
{
}

/**
 * 房间号
 */
public long roomId;

/**
 * 退出用户id
 */
public long userId;

   public ApiTimerExitRoom(Parcel in) 
{
roomId=in.readLong();
userId=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(roomId);
dest.writeLong(userId);

}

  public static void cloneObj(ApiTimerExitRoom source,ApiTimerExitRoom target)
{

target.roomId=source.roomId;

target.userId=source.userId;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiTimerExitRoom> CREATOR = new Creator<ApiTimerExitRoom>() {
        @Override
        public ApiTimerExitRoom createFromParcel(Parcel in) {
            return new ApiTimerExitRoom(in);
        }

        @Override
        public ApiTimerExitRoom[] newArray(int size) {
            return new ApiTimerExitRoom[size];
        }
    };
}
