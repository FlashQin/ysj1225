package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 查询时间
 */
public class CfgSearchDate  implements Parcelable
{
 public CfgSearchDate()
{
}

/**
 * 查询范围值
 */
public int val;

/**
 * 标准值单位
 */
public String unit;

/**
 * 添加时间
 */
public Date addTime;

/**
 * 时间间隔描述
 */
public String name;

/**
 * null
 */
public long id;

/**
 * 排序
 */
public int sort;

/**
 * 类型 1:分钟  2：小时 3：天 4：月  5：年
 */
public int type;

   public CfgSearchDate(Parcel in) 
{
val=in.readInt();
unit=in.readString();
addTime=new Date( in.readLong());
name=in.readString();
id=in.readLong();
sort=in.readInt();
type=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(val);
dest.writeString(unit);
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeString(name);
dest.writeLong(id);
dest.writeInt(sort);
dest.writeInt(type);

}

  public static void cloneObj(CfgSearchDate source,CfgSearchDate target)
{

target.val=source.val;

target.unit=source.unit;

target.addTime=source.addTime;

target.name=source.name;

target.id=source.id;

target.sort=source.sort;

target.type=source.type;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CfgSearchDate> CREATOR = new Creator<CfgSearchDate>() {
        @Override
        public CfgSearchDate createFromParcel(Parcel in) {
            return new CfgSearchDate(in);
        }

        @Override
        public CfgSearchDate[] newArray(int size) {
            return new CfgSearchDate[size];
        }
    };
}
