package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 贵族中心贵族等级
 */
public class NobleCenterNobleGradeDTO  implements Parcelable
{
 public NobleCenterNobleGradeDTO()
{
}

/**
 * 贵族等级图标
 */
public String gradeIcon;

/**
 * 贵族等级
 */
public int grade;

/**
 * 贵族名称
 */
public String name;

/**
 * 贵族id
 */
public long id;

   public NobleCenterNobleGradeDTO(Parcel in) 
{
gradeIcon=in.readString();
grade=in.readInt();
name=in.readString();
id=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(gradeIcon);
dest.writeInt(grade);
dest.writeString(name);
dest.writeLong(id);

}

  public static void cloneObj(NobleCenterNobleGradeDTO source,NobleCenterNobleGradeDTO target)
{

target.gradeIcon=source.gradeIcon;

target.grade=source.grade;

target.name=source.name;

target.id=source.id;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NobleCenterNobleGradeDTO> CREATOR = new Creator<NobleCenterNobleGradeDTO>() {
        @Override
        public NobleCenterNobleGradeDTO createFromParcel(Parcel in) {
            return new NobleCenterNobleGradeDTO(in);
        }

        @Override
        public NobleCenterNobleGradeDTO[] newArray(int size) {
            return new NobleCenterNobleGradeDTO[size];
        }
    };
}
