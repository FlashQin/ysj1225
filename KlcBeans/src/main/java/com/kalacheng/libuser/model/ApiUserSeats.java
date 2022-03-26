package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 贵宾席列表返回
 */
public class ApiUserSeats  implements Parcelable
{
 public ApiUserSeats()
{
}

/**
 * null
 */
public long serialVersionUID;

/**
 * 用户id
 */
public long id;

/**
 * 用户名
 */
public String userName;

/**
 * 性别
 */
public int sex;

/**
 * 贵族等级
 */
public int nobleGrade;

/**
 * 贵族等级图片地址
 */
public String nobleGradeColor;

/**
 * 头像地址
 */
public String avatar;

/**
 * 等级
 */
public int grade;

/**
 * 等级图片地址
 */
public String gradeColor;

/**
 * 贡献值
 */
public double goldCoin;

   public ApiUserSeats(Parcel in) 
{
serialVersionUID=in.readLong();
id=in.readLong();
userName=in.readString();
sex=in.readInt();
nobleGrade=in.readInt();
nobleGradeColor=in.readString();
avatar=in.readString();
grade=in.readInt();
gradeColor=in.readString();
goldCoin=in.readDouble();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(serialVersionUID);
dest.writeLong(id);
dest.writeString(userName);
dest.writeInt(sex);
dest.writeInt(nobleGrade);
dest.writeString(nobleGradeColor);
dest.writeString(avatar);
dest.writeInt(grade);
dest.writeString(gradeColor);
dest.writeDouble(goldCoin);

}

  public static void cloneObj(ApiUserSeats source,ApiUserSeats target)
{

target.serialVersionUID=source.serialVersionUID;

target.id=source.id;

target.userName=source.userName;

target.sex=source.sex;

target.nobleGrade=source.nobleGrade;

target.nobleGradeColor=source.nobleGradeColor;

target.avatar=source.avatar;

target.grade=source.grade;

target.gradeColor=source.gradeColor;

target.goldCoin=source.goldCoin;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiUserSeats> CREATOR = new Creator<ApiUserSeats>() {
        @Override
        public ApiUserSeats createFromParcel(Parcel in) {
            return new ApiUserSeats(in);
        }

        @Override
        public ApiUserSeats[] newArray(int size) {
            return new ApiUserSeats[size];
        }
    };
}
