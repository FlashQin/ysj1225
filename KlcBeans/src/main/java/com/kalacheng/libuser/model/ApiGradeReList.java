package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 等级升级大礼包列表
 */
public class ApiGradeReList  implements Parcelable
{
 public ApiGradeReList()
{
}

/**
 * 数量
 */
public int number;

/**
 * 图片地址
 */
public String img;

/**
 * 礼包名称
 */
public String title;

/**
 * 礼包类型1金币2坐骑3礼物4短视频
 */
public int type;

   public ApiGradeReList(Parcel in) 
{
number=in.readInt();
img=in.readString();
title=in.readString();
type=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(number);
dest.writeString(img);
dest.writeString(title);
dest.writeInt(type);

}

  public static void cloneObj(ApiGradeReList source,ApiGradeReList target)
{

target.number=source.number;

target.img=source.img;

target.title=source.title;

target.type=source.type;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiGradeReList> CREATOR = new Creator<ApiGradeReList>() {
        @Override
        public ApiGradeReList createFromParcel(Parcel in) {
            return new ApiGradeReList(in);
        }

        @Override
        public ApiGradeReList[] newArray(int size) {
            return new ApiGradeReList[size];
        }
    };
}
