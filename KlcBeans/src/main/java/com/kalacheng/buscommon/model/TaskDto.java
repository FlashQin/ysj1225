package com.kalacheng.buscommon.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 任务查询结果
 */
public class TaskDto  implements Parcelable
{
 public TaskDto()
{
}

/**
 * 任务值
 */
public double val;

/**
 * 任务图片
 */
public String image;

/**
 * 任务图片
 */
public String taskImg;

/**
 * 任务角色 1:用户任务  2：主播任务
 */
public int role;

/**
 * 添加时间
 */
public Date addTime;

/**
 * 短视频任务类型编码
 */
public String typeShortCode;

/**
 * 是否开启任务  0：关闭  1：开启
 */
public int isStart;

/**
 * 排序
 */
public int sort;

/**
 * 获得勋章的任务记录
 */
public com.kalacheng.libuser.model.AppUserTaskRecord appUserTaskRecord;

/**
 * 用户ID
 */
public long userId;

/**
 * 简介描述
 */
public String desr;

/**
 * 经验积分
 */
public int point;

/**
 * 任务类型编码
 */
public String typeCode;

/**
 * 任务分类  0：普通任务  1：短视频任务
 */
public int taskType;

/**
 * 任务值单位
 */
public String unit;

/**
 * 任务名称
 */
public String name;

/**
 * 添加时间
 */
public Date startTime;

/**
 * null
 */
public long id;

/**
 * 是否完成任务 ， 是否获得0：未完成(默认) 1：已完成
 */
public int status;

   public TaskDto(Parcel in) 
{
val=in.readDouble();
image=in.readString();
taskImg=in.readString();
role=in.readInt();
addTime=new Date( in.readLong());
typeShortCode=in.readString();
isStart=in.readInt();
sort=in.readInt();

appUserTaskRecord=in.readParcelable(com.kalacheng.libuser.model.AppUserTaskRecord.class.getClassLoader());
userId=in.readLong();
desr=in.readString();
point=in.readInt();
typeCode=in.readString();
taskType=in.readInt();
unit=in.readString();
name=in.readString();
startTime=new Date( in.readLong());
id=in.readLong();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeDouble(val);
dest.writeString(image);
dest.writeString(taskImg);
dest.writeInt(role);
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeString(typeShortCode);
dest.writeInt(isStart);
dest.writeInt(sort);

dest.writeParcelable(appUserTaskRecord,flags);
dest.writeLong(userId);
dest.writeString(desr);
dest.writeInt(point);
dest.writeString(typeCode);
dest.writeInt(taskType);
dest.writeString(unit);
dest.writeString(name);
dest.writeLong(startTime==null?0:startTime.getTime());
dest.writeLong(id);
dest.writeInt(status);

}

  public static void cloneObj(TaskDto source,TaskDto target)
{

target.val=source.val;

target.image=source.image;

target.taskImg=source.taskImg;

target.role=source.role;

target.addTime=source.addTime;

target.typeShortCode=source.typeShortCode;

target.isStart=source.isStart;

target.sort=source.sort;
        if(source.appUserTaskRecord==null)
        {
            target.appUserTaskRecord=null;
        }else
        {
            AppUserTaskRecord.cloneObj(source.appUserTaskRecord,target.appUserTaskRecord);
        }

target.userId=source.userId;

target.desr=source.desr;

target.point=source.point;

target.typeCode=source.typeCode;

target.taskType=source.taskType;

target.unit=source.unit;

target.name=source.name;

target.startTime=source.startTime;

target.id=source.id;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TaskDto> CREATOR = new Creator<TaskDto>() {
        @Override
        public TaskDto createFromParcel(Parcel in) {
            return new TaskDto(in);
        }

        @Override
        public TaskDto[] newArray(int size) {
            return new TaskDto[size];
        }
    };
}
