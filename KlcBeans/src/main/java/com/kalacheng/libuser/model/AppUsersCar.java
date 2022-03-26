package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 用户坐骑表
 */
public class AppUsersCar  implements Parcelable
{
 public AppUsersCar()
{
}

/**
 * 坐骑头像
 */
public String thumb;

/**
 * 添加时间
 */
public Date addtime;

/**
 * 坐骑名称
 */
public String name;

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

/**
 * 坐骑ID
 */
public int carid;

/**
 * 是否启用
 */
public int status;

   public AppUsersCar(Parcel in) 
{
thumb=in.readString();
addtime=new Date( in.readLong());
name=in.readString();
endtime=new Date( in.readLong());
id=in.readLong();
userid=in.readLong();
carid=in.readInt();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(thumb);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeString(name);
dest.writeLong(endtime==null?0:endtime.getTime());
dest.writeLong(id);
dest.writeLong(userid);
dest.writeInt(carid);
dest.writeInt(status);

}

  public static void cloneObj(AppUsersCar source,AppUsersCar target)
{

target.thumb=source.thumb;

target.addtime=source.addtime;

target.name=source.name;

target.endtime=source.endtime;

target.id=source.id;

target.userid=source.userid;

target.carid=source.carid;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppUsersCar> CREATOR = new Creator<AppUsersCar>() {
        @Override
        public AppUsersCar createFromParcel(Parcel in) {
            return new AppUsersCar(in);
        }

        @Override
        public AppUsersCar[] newArray(int size) {
            return new AppUsersCar[size];
        }
    };
}
