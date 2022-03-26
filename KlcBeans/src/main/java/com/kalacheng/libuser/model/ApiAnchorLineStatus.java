package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP主播修改连麦状态socket
 */
public class ApiAnchorLineStatus  implements Parcelable
{
 public ApiAnchorLineStatus()
{
}

/**
 * 连麦状态1开启2关闭
 */
public int status;

/**
 * 对方房间id
 */
public long roomId;

/**
 * 对方用户id
 */
public long uid;

/**
 * 状态1成功2失败
 */
public int code;

/**
 * 状态描述
 */
public String msg;

   public ApiAnchorLineStatus(Parcel in) 
{
status=in.readInt();
roomId=in.readLong();
uid=in.readLong();
code=in.readInt();
msg=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(status);
dest.writeLong(roomId);
dest.writeLong(uid);
dest.writeInt(code);
dest.writeString(msg);

}

  public static void cloneObj(ApiAnchorLineStatus source,ApiAnchorLineStatus target)
{

target.status=source.status;

target.roomId=source.roomId;

target.uid=source.uid;

target.code=source.code;

target.msg=source.msg;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiAnchorLineStatus> CREATOR = new Creator<ApiAnchorLineStatus>() {
        @Override
        public ApiAnchorLineStatus createFromParcel(Parcel in) {
            return new ApiAnchorLineStatus(in);
        }

        @Override
        public ApiAnchorLineStatus[] newArray(int size) {
            return new ApiAnchorLineStatus[size];
        }
    };
}
