package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 举报类型
 */
public class AppUsersVideoReportClassify  implements Parcelable
{
 public AppUsersVideoReportClassify()
{
}

/**
 * 排序
 */
public int orderno;

/**
 * 添加时间
 */
public Date addtime;

/**
 * 举报类型名称
 */
public String name;

/**
 * null
 */
public long id;

   public AppUsersVideoReportClassify(Parcel in) 
{
orderno=in.readInt();
addtime=new Date( in.readLong());
name=in.readString();
id=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(orderno);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeString(name);
dest.writeLong(id);

}

  public static void cloneObj(AppUsersVideoReportClassify source,AppUsersVideoReportClassify target)
{

target.orderno=source.orderno;

target.addtime=source.addtime;

target.name=source.name;

target.id=source.id;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppUsersVideoReportClassify> CREATOR = new Creator<AppUsersVideoReportClassify>() {
        @Override
        public AppUsersVideoReportClassify createFromParcel(Parcel in) {
            return new AppUsersVideoReportClassify(in);
        }

        @Override
        public AppUsersVideoReportClassify[] newArray(int size) {
            return new AppUsersVideoReportClassify[size];
        }
    };
}
