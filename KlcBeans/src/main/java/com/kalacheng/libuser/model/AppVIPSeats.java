package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 贵宾席表
 */
public class AppVIPSeats  implements Parcelable
{
 public AppVIPSeats()
{
}

/**
 * 用户id
 */
public long uid;

/**
 * 购买时间
 */
public Date creatTime;

/**
 * null
 */
public long id;

/**
 * 主播id
 */
public long anchorId;

/**
 * 贵宾席类型 1:视频 2:语音
 */
public int type;

/**
 * 购买状态 0:没有购买 1:购买
 */
public int status;

   public AppVIPSeats(Parcel in) 
{
uid=in.readLong();
creatTime=new Date( in.readLong());
id=in.readLong();
anchorId=in.readLong();
type=in.readInt();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(uid);
dest.writeLong(creatTime==null?0:creatTime.getTime());
dest.writeLong(id);
dest.writeLong(anchorId);
dest.writeInt(type);
dest.writeInt(status);

}

  public static void cloneObj(AppVIPSeats source,AppVIPSeats target)
{

target.uid=source.uid;

target.creatTime=source.creatTime;

target.id=source.id;

target.anchorId=source.anchorId;

target.type=source.type;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppVIPSeats> CREATOR = new Creator<AppVIPSeats>() {
        @Override
        public AppVIPSeats createFromParcel(Parcel in) {
            return new AppVIPSeats(in);
        }

        @Override
        public AppVIPSeats[] newArray(int size) {
            return new AppVIPSeats[size];
        }
    };
}
