package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP用户关注,粉丝,浏览记录
 */
public class ApiUserAtten  implements Parcelable
{
 public ApiUserAtten()
{
}

/**
 * 生日
 */
public String birthday;

/**
 * 财富等级图片
 */
public String wealthGradeImg;

/**
 * 是否在直播 0:未直播 1:直播中
 */
public int islive;

/**
 * 角色 0:普通用户 1:主播
 */
public int role;

/**
 * 个性签名
 */
public String signature;

/**
 * 性别；0：保密，1：男；2：女
 */
public int sex;

/**
 * 用户等级图片
 */
public String userGradeImg;

/**
 * 用户头像
 */
public String avatar_thumb;

/**
 * 贵族等级图片
 */
public String nobleGradeImg;

/**
 * 用户id
 */
public long uid;

/**
 * 主播等级图片
 */
public String anchorGradeImg;

/**
 * 用户等级
 */
public String userLevel;

/**
 * 年龄
 */
public int age;

/**
 * 关注状态 0:未关注 1:已关注
 */
public int status;

/**
 * 用户名称
 */
public String username;

   public ApiUserAtten(Parcel in) 
{
birthday=in.readString();
wealthGradeImg=in.readString();
islive=in.readInt();
role=in.readInt();
signature=in.readString();
sex=in.readInt();
userGradeImg=in.readString();
avatar_thumb=in.readString();
nobleGradeImg=in.readString();
uid=in.readLong();
anchorGradeImg=in.readString();
userLevel=in.readString();
age=in.readInt();
status=in.readInt();
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(birthday);
dest.writeString(wealthGradeImg);
dest.writeInt(islive);
dest.writeInt(role);
dest.writeString(signature);
dest.writeInt(sex);
dest.writeString(userGradeImg);
dest.writeString(avatar_thumb);
dest.writeString(nobleGradeImg);
dest.writeLong(uid);
dest.writeString(anchorGradeImg);
dest.writeString(userLevel);
dest.writeInt(age);
dest.writeInt(status);
dest.writeString(username);

}

  public static void cloneObj(ApiUserAtten source,ApiUserAtten target)
{

target.birthday=source.birthday;

target.wealthGradeImg=source.wealthGradeImg;

target.islive=source.islive;

target.role=source.role;

target.signature=source.signature;

target.sex=source.sex;

target.userGradeImg=source.userGradeImg;

target.avatar_thumb=source.avatar_thumb;

target.nobleGradeImg=source.nobleGradeImg;

target.uid=source.uid;

target.anchorGradeImg=source.anchorGradeImg;

target.userLevel=source.userLevel;

target.age=source.age;

target.status=source.status;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiUserAtten> CREATOR = new Creator<ApiUserAtten>() {
        @Override
        public ApiUserAtten createFromParcel(Parcel in) {
            return new ApiUserAtten(in);
        }

        @Override
        public ApiUserAtten[] newArray(int size) {
            return new ApiUserAtten[size];
        }
    };
}
