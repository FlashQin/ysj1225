package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP用户任务
 */
public class AppUserTaskRecord  implements Parcelable
{
 public AppUserTaskRecord()
{
}

/**
 * 最后一次完成任务时间
 */
public Date upTime;

/**
 * 任务角色 1:用户任务  2：主播任务
 */
public int role;

/**
 * 任务完成时间
 */
public Date addTime;

/**
 * 名称
 */
public String name;

/**
 * null
 */
public long id;

/**
 * 任务是否已完成 0:未完成 1：已完成
 */
public int isFinish;

/**
 * 完成任务次数
 */
public double totalTaskVal;

/**
 * 用户ID
 */
public long userId;

/**
 * 任务ID
 */
public long taskId;

/**
 * 任务类型编码
 */
public String typeCode;

   public AppUserTaskRecord(Parcel in) 
{
upTime=new Date( in.readLong());
role=in.readInt();
addTime=new Date( in.readLong());
name=in.readString();
id=in.readLong();
isFinish=in.readInt();
totalTaskVal=in.readDouble();
userId=in.readLong();
taskId=in.readLong();
typeCode=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(upTime==null?0:upTime.getTime());
dest.writeInt(role);
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeString(name);
dest.writeLong(id);
dest.writeInt(isFinish);
dest.writeDouble(totalTaskVal);
dest.writeLong(userId);
dest.writeLong(taskId);
dest.writeString(typeCode);

}

  public static void cloneObj(AppUserTaskRecord source,AppUserTaskRecord target)
{

target.upTime=source.upTime;

target.role=source.role;

target.addTime=source.addTime;

target.name=source.name;

target.id=source.id;

target.isFinish=source.isFinish;

target.totalTaskVal=source.totalTaskVal;

target.userId=source.userId;

target.taskId=source.taskId;

target.typeCode=source.typeCode;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppUserTaskRecord> CREATOR = new Creator<AppUserTaskRecord>() {
        @Override
        public AppUserTaskRecord createFromParcel(Parcel in) {
            return new AppUserTaskRecord(in);
        }

        @Override
        public AppUserTaskRecord[] newArray(int size) {
            return new AppUserTaskRecord[size];
        }
    };
}
