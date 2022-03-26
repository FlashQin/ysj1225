package com.kalacheng.buscommon.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 用户简要信息
 */
public class ApiUserBasicInfo  implements Parcelable
{
 public ApiUserBasicInfo()
{
}

/**
 * 签名
 */
public String signature;

/**
 * 是否超管 1:否 0:是
 */
public int issuper;

/**
 * 加入房间隐身 0:不隐身 1:隐身
 */
public int joinRoomShow;

/**
 * 性别 0:保密 1：男 2:女
 */
public int sex;

/**
 * 用户等级图片
 */
public String userGradeImg;

/**
 * 是否僵尸粉 1:否 0:是
 */
public int iszombiep;

/**
 * 手机号码
 */
public String mobile;

/**
 * 头像
 */
public String avatar;

/**
 * 封面图
 */
public String avatarThumb;

/**
 * 用户对当前直播间总贡献值
 */
public double currContValue;

/**
 * 用户id
 */
public long uid;

/**
 * 贵族等级
 */
public int nobleGrade;

/**
 * 贵族等级图片地址
 */
public String nobleGradeColor;

/**
 * 用户名称
 */
public String username;

   public ApiUserBasicInfo(Parcel in) 
{
signature=in.readString();
issuper=in.readInt();
joinRoomShow=in.readInt();
sex=in.readInt();
userGradeImg=in.readString();
iszombiep=in.readInt();
mobile=in.readString();
avatar=in.readString();
avatarThumb=in.readString();
currContValue=in.readDouble();
uid=in.readLong();
nobleGrade=in.readInt();
nobleGradeColor=in.readString();
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(signature);
dest.writeInt(issuper);
dest.writeInt(joinRoomShow);
dest.writeInt(sex);
dest.writeString(userGradeImg);
dest.writeInt(iszombiep);
dest.writeString(mobile);
dest.writeString(avatar);
dest.writeString(avatarThumb);
dest.writeDouble(currContValue);
dest.writeLong(uid);
dest.writeInt(nobleGrade);
dest.writeString(nobleGradeColor);
dest.writeString(username);

}

  public static void cloneObj(ApiUserBasicInfo source,ApiUserBasicInfo target)
{

target.signature=source.signature;

target.issuper=source.issuper;

target.joinRoomShow=source.joinRoomShow;

target.sex=source.sex;

target.userGradeImg=source.userGradeImg;

target.iszombiep=source.iszombiep;

target.mobile=source.mobile;

target.avatar=source.avatar;

target.avatarThumb=source.avatarThumb;

target.currContValue=source.currContValue;

target.uid=source.uid;

target.nobleGrade=source.nobleGrade;

target.nobleGradeColor=source.nobleGradeColor;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiUserBasicInfo> CREATOR = new Creator<ApiUserBasicInfo>() {
        @Override
        public ApiUserBasicInfo createFromParcel(Parcel in) {
            return new ApiUserBasicInfo(in);
        }

        @Override
        public ApiUserBasicInfo[] newArray(int size) {
            return new ApiUserBasicInfo[size];
        }
    };
}
