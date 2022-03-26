package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 用户贵族(VIP)表
 */
public class AppUsersVip  implements Parcelable
{
 public AppUsersVip()
{
}

/**
 * 最后续费时间
 */
public Date lastPayTime;

/**
 * 添加时间
 */
public Date addtime;

/**
 * 购买的是哪个级别的会员
 */
public int grade;

/**
 * 到期时间
 */
public Date endtime;

/**
 * null
 */
public long id;

/**
 * 用户ID
 */
public long userid;

   public AppUsersVip(Parcel in) 
{
lastPayTime=new Date( in.readLong());
addtime=new Date( in.readLong());
grade=in.readInt();
endtime=new Date( in.readLong());
id=in.readLong();
userid=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(lastPayTime==null?0:lastPayTime.getTime());
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeInt(grade);
dest.writeLong(endtime==null?0:endtime.getTime());
dest.writeLong(id);
dest.writeLong(userid);

}

  public static void cloneObj(AppUsersVip source,AppUsersVip target)
{

target.lastPayTime=source.lastPayTime;

target.addtime=source.addtime;

target.grade=source.grade;

target.endtime=source.endtime;

target.id=source.id;

target.userid=source.userid;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppUsersVip> CREATOR = new Creator<AppUsersVip>() {
        @Override
        public AppUsersVip createFromParcel(Parcel in) {
            return new AppUsersVip(in);
        }

        @Override
        public AppUsersVip[] newArray(int size) {
            return new AppUsersVip[size];
        }
    };
}
