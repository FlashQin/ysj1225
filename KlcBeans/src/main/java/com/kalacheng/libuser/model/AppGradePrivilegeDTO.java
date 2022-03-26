package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 等级特权
 */
public class AppGradePrivilegeDTO  implements Parcelable
{
 public AppGradePrivilegeDTO()
{
}

/**
 * 等级
 */
public int grade;

/**
 * 特权名称
 */
public String name;

/**
 * logo
 */
public String logo;

   public AppGradePrivilegeDTO(Parcel in) 
{
grade=in.readInt();
name=in.readString();
logo=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(grade);
dest.writeString(name);
dest.writeString(logo);

}

  public static void cloneObj(AppGradePrivilegeDTO source,AppGradePrivilegeDTO target)
{

target.grade=source.grade;

target.name=source.name;

target.logo=source.logo;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppGradePrivilegeDTO> CREATOR = new Creator<AppGradePrivilegeDTO>() {
        @Override
        public AppGradePrivilegeDTO createFromParcel(Parcel in) {
            return new AppGradePrivilegeDTO(in);
        }

        @Override
        public AppGradePrivilegeDTO[] newArray(int size) {
            return new AppGradePrivilegeDTO[size];
        }
    };
}
