package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP守护功能
 */
public class GuardEffect  implements Parcelable
{
 public GuardEffect()
{
}

/**
 * 标识logo
 */
public String img;

/**
 * 描述
 */
public String des;

/**
 * 是否可用1可用0不可用
 */
public int isAble;

/**
 * 标题
 */
public String title;

   public GuardEffect(Parcel in) 
{
img=in.readString();
des=in.readString();
isAble=in.readInt();
title=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(img);
dest.writeString(des);
dest.writeInt(isAble);
dest.writeString(title);

}

  public static void cloneObj(GuardEffect source,GuardEffect target)
{

target.img=source.img;

target.des=source.des;

target.isAble=source.isAble;

target.title=source.title;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GuardEffect> CREATOR = new Creator<GuardEffect>() {
        @Override
        public GuardEffect createFromParcel(Parcel in) {
            return new GuardEffect(in);
        }

        @Override
        public GuardEffect[] newArray(int size) {
            return new GuardEffect[size];
        }
    };
}
