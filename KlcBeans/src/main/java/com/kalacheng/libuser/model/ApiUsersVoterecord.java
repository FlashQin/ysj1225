package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP收益榜
 */
public class ApiUsersVoterecord  implements Parcelable
{
 public ApiUsersVoterecord()
{
}

/**
 * 排名
 */
public int number;

/**
 * 用户id
 */
public long uid;

/**
 * 主播等级图片
 */
public String anchorGradeImg;

/**
 * 主播等级
 */
public int anchorGrade;

/**
 * 总金额
 */
public double totalvotes;

/**
 * 性别；0：保密，1：男；2：女
 */
public int sex;

/**
 * 用户头像
 */
public String avatar;

/**
 * 用户名称
 */
public String username;

   public ApiUsersVoterecord(Parcel in) 
{
number=in.readInt();
uid=in.readLong();
anchorGradeImg=in.readString();
anchorGrade=in.readInt();
totalvotes=in.readDouble();
sex=in.readInt();
avatar=in.readString();
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(number);
dest.writeLong(uid);
dest.writeString(anchorGradeImg);
dest.writeInt(anchorGrade);
dest.writeDouble(totalvotes);
dest.writeInt(sex);
dest.writeString(avatar);
dest.writeString(username);

}

  public static void cloneObj(ApiUsersVoterecord source,ApiUsersVoterecord target)
{

target.number=source.number;

target.uid=source.uid;

target.anchorGradeImg=source.anchorGradeImg;

target.anchorGrade=source.anchorGrade;

target.totalvotes=source.totalvotes;

target.sex=source.sex;

target.avatar=source.avatar;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiUsersVoterecord> CREATOR = new Creator<ApiUsersVoterecord>() {
        @Override
        public ApiUsersVoterecord createFromParcel(Parcel in) {
            return new ApiUsersVoterecord(in);
        }

        @Override
        public ApiUsersVoterecord[] newArray(int size) {
            return new ApiUsersVoterecord[size];
        }
    };
}
