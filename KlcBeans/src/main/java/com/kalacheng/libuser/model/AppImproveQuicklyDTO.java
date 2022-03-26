package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 快速提升配置
 */
public class AppImproveQuicklyDTO  implements Parcelable
{
 public AppImproveQuicklyDTO()
{
}

/**
 * 名称
 */
public String name;

/**
 * logo
 */
public String logo;

/**
 * 简介描述
 */
public String desr;

   public AppImproveQuicklyDTO(Parcel in) 
{
name=in.readString();
logo=in.readString();
desr=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(name);
dest.writeString(logo);
dest.writeString(desr);

}

  public static void cloneObj(AppImproveQuicklyDTO source,AppImproveQuicklyDTO target)
{

target.name=source.name;

target.logo=source.logo;

target.desr=source.desr;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppImproveQuicklyDTO> CREATOR = new Creator<AppImproveQuicklyDTO>() {
        @Override
        public AppImproveQuicklyDTO createFromParcel(Parcel in) {
            return new AppImproveQuicklyDTO(in);
        }

        @Override
        public AppImproveQuicklyDTO[] newArray(int size) {
            return new AppImproveQuicklyDTO[size];
        }
    };
}
