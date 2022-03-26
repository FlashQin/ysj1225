package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 后台管理用户信息表
 */
public class AppCoverExamine  implements Parcelable
{
 public AppCoverExamine()
{
}

/**
 * 图片地址
 */
public String imgUrl;

/**
 * 是否审核 0未审核， 1审核通过 2：审核失败
 */
public int isExamine;

/**
 * 创建时间
 */
public Date createTime;

/**
 * null
 */
public long id;

/**
 * 排序
 */
public int sort;

/**
 * 用户ID
 */
public long userId;

   public AppCoverExamine(Parcel in) 
{
imgUrl=in.readString();
isExamine=in.readInt();
createTime=new Date( in.readLong());
id=in.readLong();
sort=in.readInt();
userId=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(imgUrl);
dest.writeInt(isExamine);
dest.writeLong(createTime==null?0:createTime.getTime());
dest.writeLong(id);
dest.writeInt(sort);
dest.writeLong(userId);

}

  public static void cloneObj(AppCoverExamine source,AppCoverExamine target)
{

target.imgUrl=source.imgUrl;

target.isExamine=source.isExamine;

target.createTime=source.createTime;

target.id=source.id;

target.sort=source.sort;

target.userId=source.userId;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppCoverExamine> CREATOR = new Creator<AppCoverExamine>() {
        @Override
        public AppCoverExamine createFromParcel(Parcel in) {
            return new AppCoverExamine(in);
        }

        @Override
        public AppCoverExamine[] newArray(int size) {
            return new AppCoverExamine[size];
        }
    };
}
