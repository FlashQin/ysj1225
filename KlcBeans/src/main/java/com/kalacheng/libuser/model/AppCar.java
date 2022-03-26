package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * App坐骑表
 */
public class AppCar  implements Parcelable
{
 public AppCar()
{
}

/**
 * 是否是贵族坐骑 1:是 0:否
 */
public int vipCar;

/**
 * 序号
 */
public int orderno;

/**
 * 动画链接
 */
public String swf;

/**
 * 图片链接
 */
public String thumb;

/**
 * 添加时间
 */
public Date addtime;

/**
 * 名称
 */
public String name;

/**
 * 价格
 */
public double needcoin;

/**
 * 进场话术
 */
public String words;

/**
 * null
 */
public long id;

/**
 * 动画时长
 */
public double swftime;

/**
 * 状态（0显示1不显示）
 */
public int status;

/**
 * 更新时间
 */
public Date uptime;

   public AppCar(Parcel in) 
{
vipCar=in.readInt();
orderno=in.readInt();
swf=in.readString();
thumb=in.readString();
addtime=new Date( in.readLong());
name=in.readString();
needcoin=in.readDouble();
words=in.readString();
id=in.readLong();
swftime=in.readDouble();
status=in.readInt();
uptime=new Date( in.readLong());

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(vipCar);
dest.writeInt(orderno);
dest.writeString(swf);
dest.writeString(thumb);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeString(name);
dest.writeDouble(needcoin);
dest.writeString(words);
dest.writeLong(id);
dest.writeDouble(swftime);
dest.writeInt(status);
dest.writeLong(uptime==null?0:uptime.getTime());

}

  public static void cloneObj(AppCar source,AppCar target)
{

target.vipCar=source.vipCar;

target.orderno=source.orderno;

target.swf=source.swf;

target.thumb=source.thumb;

target.addtime=source.addtime;

target.name=source.name;

target.needcoin=source.needcoin;

target.words=source.words;

target.id=source.id;

target.swftime=source.swftime;

target.status=source.status;

target.uptime=source.uptime;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppCar> CREATOR = new Creator<AppCar>() {
        @Override
        public AppCar createFromParcel(Parcel in) {
            return new AppCar(in);
        }

        @Override
        public AppCar[] newArray(int size) {
            return new AppCar[size];
        }
    };
}
