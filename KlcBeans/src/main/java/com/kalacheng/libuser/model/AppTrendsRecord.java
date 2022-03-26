package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 我的动态记录
 */
public class AppTrendsRecord  implements Parcelable
{
 public AppTrendsRecord()
{
}

/**
 * 定位地址
 */
public String address;

/**
 * 预留字段1
 */
public String reserve4;

/**
 * 纬度
 */
public String latitude;

/**
 * 预留字段1
 */
public String reserve1;

/**
 * 预留字段1
 */
public String reserve3;

/**
 * 目标用户ID, 无则默认0(前端不要使用)
 */
public long targetUserId;

/**
 * 预留字段1
 */
public String reserve2;

/**
 * 资源null, 1：图片、封面地址 2：视频地址  3：文字， 4：流
 */
public String source;

/**
 * 动态标题
 */
public String title;

/**
 * 业务类型， 参考TrendsType的枚举
 */
public int type;

/**
 * 动态用户ID
 */
public long userId;

/**
 * 目标ID, 无则默认0(前端可以使用)
 */
public long fkId;

/**
 * 对应的记录ID
 */
public long recordId;

/**
 * 创建时间
 */
public Date createTime;

/**
 * 资源类型；0：未知，1：图片；2：视频; 3:文字，4：流
 */
public int sourceType;

/**
 * null
 */
public long id;

/**
 * 经度
 */
public String longitude;

   public AppTrendsRecord(Parcel in) 
{
address=in.readString();
reserve4=in.readString();
latitude=in.readString();
reserve1=in.readString();
reserve3=in.readString();
targetUserId=in.readLong();
reserve2=in.readString();
source=in.readString();
title=in.readString();
type=in.readInt();
userId=in.readLong();
fkId=in.readLong();
recordId=in.readLong();
createTime=new Date( in.readLong());
sourceType=in.readInt();
id=in.readLong();
longitude=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(address);
dest.writeString(reserve4);
dest.writeString(latitude);
dest.writeString(reserve1);
dest.writeString(reserve3);
dest.writeLong(targetUserId);
dest.writeString(reserve2);
dest.writeString(source);
dest.writeString(title);
dest.writeInt(type);
dest.writeLong(userId);
dest.writeLong(fkId);
dest.writeLong(recordId);
dest.writeLong(createTime==null?0:createTime.getTime());
dest.writeInt(sourceType);
dest.writeLong(id);
dest.writeString(longitude);

}

  public static void cloneObj(AppTrendsRecord source,AppTrendsRecord target)
{

target.address=source.address;

target.reserve4=source.reserve4;

target.latitude=source.latitude;

target.reserve1=source.reserve1;

target.reserve3=source.reserve3;

target.targetUserId=source.targetUserId;

target.reserve2=source.reserve2;

target.source=source.source;

target.title=source.title;

target.type=source.type;

target.userId=source.userId;

target.fkId=source.fkId;

target.recordId=source.recordId;

target.createTime=source.createTime;

target.sourceType=source.sourceType;

target.id=source.id;

target.longitude=source.longitude;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppTrendsRecord> CREATOR = new Creator<AppTrendsRecord>() {
        @Override
        public AppTrendsRecord createFromParcel(Parcel in) {
            return new AppTrendsRecord(in);
        }

        @Override
        public AppTrendsRecord[] newArray(int size) {
            return new AppTrendsRecord[size];
        }
    };
}
