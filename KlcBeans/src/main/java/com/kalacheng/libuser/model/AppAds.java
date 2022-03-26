package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 轮播图
 */
public class AppAds  implements Parcelable
{
 public AppAds()
{
}

/**
 * 描述
 */
public String des;

/**
 * 序号
 */
public String orderno;

/**
 * 图片链接
 */
public String thumb;

/**
 * 时间
 */
public Date addtime;

/**
 * 广告名称
 */
public String name;

/**
 * 一级分类(启动图1,0;直播2,1;推荐3,2;附近4,3;一对一5,n;短视频看点6,7;语聊15,1;电台16,1;直播购17,1)
 */
public int pid;

/**
 * null
 */
public long id;

/**
 * 链接
 */
public String url;

/**
 * 二级分类
 */
public int sid;

   public AppAds(Parcel in) 
{
des=in.readString();
orderno=in.readString();
thumb=in.readString();
addtime=new Date( in.readLong());
name=in.readString();
pid=in.readInt();
id=in.readLong();
url=in.readString();
sid=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(des);
dest.writeString(orderno);
dest.writeString(thumb);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeString(name);
dest.writeInt(pid);
dest.writeLong(id);
dest.writeString(url);
dest.writeInt(sid);

}

  public static void cloneObj(AppAds source,AppAds target)
{

target.des=source.des;

target.orderno=source.orderno;

target.thumb=source.thumb;

target.addtime=source.addtime;

target.name=source.name;

target.pid=source.pid;

target.id=source.id;

target.url=source.url;

target.sid=source.sid;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppAds> CREATOR = new Creator<AppAds>() {
        @Override
        public AppAds createFromParcel(Parcel in) {
            return new AppAds(in);
        }

        @Override
        public AppAds[] newArray(int size) {
            return new AppAds[size];
        }
    };
}
