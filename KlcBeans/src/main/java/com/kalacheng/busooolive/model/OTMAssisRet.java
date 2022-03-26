package com.kalacheng.busooolive.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 副播回应邀请的返回实体
 */
public class OTMAssisRet  implements Parcelable
{
 public OTMAssisRet()
{
}

/**
 * 是否打开麦克风; 默认为打开
 */
public int isOpenVolumn;

/**
 * 是否被禁言; 默认为未禁言
 */
public int isShutUp;

/**
 * 副播头像
 */
public String assisAvatar;

/**
 * 副播id
 */
public long assisId;

/**
 * 副播用户名
 */
public String assisName;

   public OTMAssisRet(Parcel in) 
{
isOpenVolumn=in.readInt();
isShutUp=in.readInt();
assisAvatar=in.readString();
assisId=in.readLong();
assisName=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(isOpenVolumn);
dest.writeInt(isShutUp);
dest.writeString(assisAvatar);
dest.writeLong(assisId);
dest.writeString(assisName);

}

  public static void cloneObj(OTMAssisRet source,OTMAssisRet target)
{

target.isOpenVolumn=source.isOpenVolumn;

target.isShutUp=source.isShutUp;

target.assisAvatar=source.assisAvatar;

target.assisId=source.assisId;

target.assisName=source.assisName;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OTMAssisRet> CREATOR = new Creator<OTMAssisRet>() {
        @Override
        public OTMAssisRet createFromParcel(Parcel in) {
            return new OTMAssisRet(in);
        }

        @Override
        public OTMAssisRet[] newArray(int size) {
            return new OTMAssisRet[size];
        }
    };
}
