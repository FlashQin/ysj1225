package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 勋章
 */
public class AppMedal  implements Parcelable
{
 public AppMedal()
{
}

/**
 * 添加时间
 */
public Date addTime;

/**
 * 勋章logo
 */
public String medalLogo;

/**
 * 勋章名称
 */
public String name;

/**
 * 勋章级别
 */
public int lv;

/**
 * null
 */
public long id;

/**
 * 类型 1:用户勋章  2：财富勋章 3：贵族勋章
 */
public int type;

/**
 * 简介描述
 */
public String desr;

/**
 * 用户持有状态 0：未持有  1：已持有  在数据库无实际意义
 */
public int holdState;

   public AppMedal(Parcel in) 
{
addTime=new Date( in.readLong());
medalLogo=in.readString();
name=in.readString();
lv=in.readInt();
id=in.readLong();
type=in.readInt();
desr=in.readString();
holdState=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeString(medalLogo);
dest.writeString(name);
dest.writeInt(lv);
dest.writeLong(id);
dest.writeInt(type);
dest.writeString(desr);
dest.writeInt(holdState);

}

  public static void cloneObj(AppMedal source,AppMedal target)
{

target.addTime=source.addTime;

target.medalLogo=source.medalLogo;

target.name=source.name;

target.lv=source.lv;

target.id=source.id;

target.type=source.type;

target.desr=source.desr;

target.holdState=source.holdState;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppMedal> CREATOR = new Creator<AppMedal>() {
        @Override
        public AppMedal createFromParcel(Parcel in) {
            return new AppMedal(in);
        }

        @Override
        public AppMedal[] newArray(int size) {
            return new AppMedal[size];
        }
    };
}
