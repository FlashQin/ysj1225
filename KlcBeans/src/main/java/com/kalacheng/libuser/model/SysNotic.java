package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 系统公告
 */
public class SysNotic  implements Parcelable
{
 public SysNotic()
{
}

/**
 * 公告形式 1:文字 2:图片
 */
public int shape;

/**
 * 添加时间
 */
public Date addtime;

/**
 * 图片地址
 */
public String imageUrl;

/**
 * 展示方式 1:启动app时 2:每天弹一次
 */
public int showType;

/**
 * null
 */
public long id;

/**
 * 标题
 */
public String title;

/**
 * 类型 1:首页系统公告
 */
public int type;

/**
 * 内容
 */
public String content;

/**
 * 连接地址
 */
public String url;

/**
 * 状态 0:开启 1:关闭
 */
public int status;

   public SysNotic(Parcel in) 
{
shape=in.readInt();
addtime=new Date( in.readLong());
imageUrl=in.readString();
showType=in.readInt();
id=in.readLong();
title=in.readString();
type=in.readInt();
content=in.readString();
url=in.readString();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(shape);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeString(imageUrl);
dest.writeInt(showType);
dest.writeLong(id);
dest.writeString(title);
dest.writeInt(type);
dest.writeString(content);
dest.writeString(url);
dest.writeInt(status);

}

  public static void cloneObj(SysNotic source,SysNotic target)
{

target.shape=source.shape;

target.addtime=source.addtime;

target.imageUrl=source.imageUrl;

target.showType=source.showType;

target.id=source.id;

target.title=source.title;

target.type=source.type;

target.content=source.content;

target.url=source.url;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SysNotic> CREATOR = new Creator<SysNotic>() {
        @Override
        public SysNotic createFromParcel(Parcel in) {
            return new SysNotic(in);
        }

        @Override
        public SysNotic[] newArray(int size) {
            return new SysNotic[size];
        }
    };
}
