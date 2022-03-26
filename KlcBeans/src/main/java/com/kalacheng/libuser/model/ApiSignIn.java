package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP签到管理
 */
public class ApiSignIn  implements Parcelable
{
 public ApiSignIn()
{
}

/**
 * 礼物id
 */
public long giftId;

/**
 * 图片
 */
public String image;

/**
 * 领取状态0可领取1已领取2不可领取
 */
public int isGet;

/**
 * 类型值:金币 (男朋友中为钻石)/个数
 */
public int typeVal;

/**
 * 创建时间
 */
public Date createTime;

/**
 * 签到天数
 */
public int dayNumber;

/**
 * 礼物名称
 */
public String name;

/**
 * null
 */
public int id;

/**
 * 类型 1:金币 (男朋友中为钻石)2:礼物
 */
public int type;

   public ApiSignIn(Parcel in) 
{
giftId=in.readLong();
image=in.readString();
isGet=in.readInt();
typeVal=in.readInt();
createTime=new Date( in.readLong());
dayNumber=in.readInt();
name=in.readString();
id=in.readInt();
type=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(giftId);
dest.writeString(image);
dest.writeInt(isGet);
dest.writeInt(typeVal);
dest.writeLong(createTime==null?0:createTime.getTime());
dest.writeInt(dayNumber);
dest.writeString(name);
dest.writeInt(id);
dest.writeInt(type);

}

  public static void cloneObj(ApiSignIn source,ApiSignIn target)
{

target.giftId=source.giftId;

target.image=source.image;

target.isGet=source.isGet;

target.typeVal=source.typeVal;

target.createTime=source.createTime;

target.dayNumber=source.dayNumber;

target.name=source.name;

target.id=source.id;

target.type=source.type;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiSignIn> CREATOR = new Creator<ApiSignIn>() {
        @Override
        public ApiSignIn createFromParcel(Parcel in) {
            return new ApiSignIn(in);
        }

        @Override
        public ApiSignIn[] newArray(int size) {
            return new ApiSignIn[size];
        }
    };
}
