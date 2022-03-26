package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 搜索记录
 */
public class AppSearchRecord  implements Parcelable
{
 public AppSearchRecord()
{
}

/**
 * 添加时间
 */
public Date addTime;

/**
 * 累计搜索次数
 */
public int num;

/**
 * null
 */
public long id;

/**
 * 用户ID
 */
public long userId;

/**
 * 搜索内容
 */
public String content;

   public AppSearchRecord(Parcel in) 
{
addTime=new Date( in.readLong());
num=in.readInt();
id=in.readLong();
userId=in.readLong();
content=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeInt(num);
dest.writeLong(id);
dest.writeLong(userId);
dest.writeString(content);

}

  public static void cloneObj(AppSearchRecord source,AppSearchRecord target)
{

target.addTime=source.addTime;

target.num=source.num;

target.id=source.id;

target.userId=source.userId;

target.content=source.content;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppSearchRecord> CREATOR = new Creator<AppSearchRecord>() {
        @Override
        public AppSearchRecord createFromParcel(Parcel in) {
            return new AppSearchRecord(in);
        }

        @Override
        public AppSearchRecord[] newArray(int size) {
            return new AppSearchRecord[size];
        }
    };
}
