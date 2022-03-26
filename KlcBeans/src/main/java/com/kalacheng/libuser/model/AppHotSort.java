package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 首页热门分类
 */
public class AppHotSort  implements Parcelable
{
 public AppHotSort()
{
}

/**
 * 图片
 */
public String image;

/**
 * 展示名称
 */
public String showName;

/**
 * 添加时间
 */
public Date addTime;

/**
 * 排序
 */
public int sort;

/**
 * 是否启用  0:不启用  1：启用
 */
public int isTip;

/**
 * 参数
 */
public String params;

/**
 * 类型 1:直播 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 */
public int type;

/**
 * 安卓用
 */
public int isChecked;

/**
 * 前端是否显示 1:显示 0:不显示
 */
public int isShow;

/**
 * 数量
 */
public int number;

/**
 * 路径
 */
public String path;

/**
 * 菜单名称
 */
public String name;

/**
 * 展示分类 0:启动图 1:直播 2:推荐 3:附近 4:听音 5...
 */
public int showType;

/**
 * null
 */
public long id;

   public AppHotSort(Parcel in) 
{
image=in.readString();
showName=in.readString();
addTime=new Date( in.readLong());
sort=in.readInt();
isTip=in.readInt();
params=in.readString();
type=in.readInt();
isChecked=in.readInt();
isShow=in.readInt();
number=in.readInt();
path=in.readString();
name=in.readString();
showType=in.readInt();
id=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(image);
dest.writeString(showName);
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeInt(sort);
dest.writeInt(isTip);
dest.writeString(params);
dest.writeInt(type);
dest.writeInt(isChecked);
dest.writeInt(isShow);
dest.writeInt(number);
dest.writeString(path);
dest.writeString(name);
dest.writeInt(showType);
dest.writeLong(id);

}

  public static void cloneObj(AppHotSort source,AppHotSort target)
{

target.image=source.image;

target.showName=source.showName;

target.addTime=source.addTime;

target.sort=source.sort;

target.isTip=source.isTip;

target.params=source.params;

target.type=source.type;

target.isChecked=source.isChecked;

target.isShow=source.isShow;

target.number=source.number;

target.path=source.path;

target.name=source.name;

target.showType=source.showType;

target.id=source.id;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppHotSort> CREATOR = new Creator<AppHotSort>() {
        @Override
        public AppHotSort createFromParcel(Parcel in) {
            return new AppHotSort(in);
        }

        @Override
        public AppHotSort[] newArray(int size) {
            return new AppHotSort[size];
        }
    };
}
