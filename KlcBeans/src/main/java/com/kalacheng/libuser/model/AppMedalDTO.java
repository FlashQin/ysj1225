package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 勋章
 */
public class AppMedalDTO  implements Parcelable
{
 public AppMedalDTO()
{
}

/**
 * 勋章logo
 */
public String medalLogo;

/**
 * 勋章名称
 */
public String name;

/**
 * 勋章级别
 */
public int lv;

/**
 * 简介描述
 */
public String desr;

   public AppMedalDTO(Parcel in) 
{
medalLogo=in.readString();
name=in.readString();
lv=in.readInt();
desr=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(medalLogo);
dest.writeString(name);
dest.writeInt(lv);
dest.writeString(desr);

}

  public static void cloneObj(AppMedalDTO source,AppMedalDTO target)
{

target.medalLogo=source.medalLogo;

target.name=source.name;

target.lv=source.lv;

target.desr=source.desr;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppMedalDTO> CREATOR = new Creator<AppMedalDTO>() {
        @Override
        public AppMedalDTO createFromParcel(Parcel in) {
            return new AppMedalDTO(in);
        }

        @Override
        public AppMedalDTO[] newArray(int size) {
            return new AppMedalDTO[size];
        }
    };
}
