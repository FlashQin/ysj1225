package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP语音直播连麦请求数据
 */
public class ApiLineVoice  implements Parcelable
{
 public ApiLineVoice()
{
}

/**
 * 申请的麦位编号 (被邀请上麦时该值为-1)
 */
public int no;

/**
 * 主播等级图标
 */
public String anchorGrade;

/**
 * 当前申请/被邀请角色类型 1主播 0普通用户
 */
public int role;

/**
 * 当前申请/被邀请的时间
 */
public long addTime;

/**
 * 用户等级图标
 */
public String userGrade;

/**
 * 当前申请/被邀请角色性别  0：保密，1：男；2：女
 */
public int sex;

/**
 * 预计缓存过期时间
 */
public long estimatedExpirationTime;

/**
 * 当前申请/被邀请用户头像
 */
public String avatar;

/**
 * 直播间主播id
 */
public long anchorId;

/**
 * 当前申请/被邀请用户名称
 */
public String userName;

/**
 * 直播间房间id
 */
public long roomId;

/**
 * 连麦状态1等待房主同意连麦 2房主已同意连麦已接入 3连麦被房主拒绝   ---   5等待被邀请方同意连麦  6被邀请方已接受连麦已接入 7被邀请方拒绝连麦 
 */
public int lineStatus;

/**
 * 当前申请/被邀请用户id
 */
public long uid;

/**
 * 财富等级图标
 */
public String wealthGrade;

/**
 * 贵族等级图标
 */
public String nobleGrade;

/**
 * 当前用户贡献值
 */
public double coin;

/**
 * 是否有视频连麦特权 0没有 1有
 */
public int mikePrivilege;

   public ApiLineVoice(Parcel in) 
{
no=in.readInt();
anchorGrade=in.readString();
role=in.readInt();
addTime=in.readLong();
userGrade=in.readString();
sex=in.readInt();
estimatedExpirationTime=in.readLong();
avatar=in.readString();
anchorId=in.readLong();
userName=in.readString();
roomId=in.readLong();
lineStatus=in.readInt();
uid=in.readLong();
wealthGrade=in.readString();
nobleGrade=in.readString();
coin=in.readDouble();
mikePrivilege=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(no);
dest.writeString(anchorGrade);
dest.writeInt(role);
dest.writeLong(addTime);
dest.writeString(userGrade);
dest.writeInt(sex);
dest.writeLong(estimatedExpirationTime);
dest.writeString(avatar);
dest.writeLong(anchorId);
dest.writeString(userName);
dest.writeLong(roomId);
dest.writeInt(lineStatus);
dest.writeLong(uid);
dest.writeString(wealthGrade);
dest.writeString(nobleGrade);
dest.writeDouble(coin);
dest.writeInt(mikePrivilege);

}

  public static void cloneObj(ApiLineVoice source,ApiLineVoice target)
{

target.no=source.no;

target.anchorGrade=source.anchorGrade;

target.role=source.role;

target.addTime=source.addTime;

target.userGrade=source.userGrade;

target.sex=source.sex;

target.estimatedExpirationTime=source.estimatedExpirationTime;

target.avatar=source.avatar;

target.anchorId=source.anchorId;

target.userName=source.userName;

target.roomId=source.roomId;

target.lineStatus=source.lineStatus;

target.uid=source.uid;

target.wealthGrade=source.wealthGrade;

target.nobleGrade=source.nobleGrade;

target.coin=source.coin;

target.mikePrivilege=source.mikePrivilege;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiLineVoice> CREATOR = new Creator<ApiLineVoice>() {
        @Override
        public ApiLineVoice createFromParcel(Parcel in) {
            return new ApiLineVoice(in);
        }

        @Override
        public ApiLineVoice[] newArray(int size) {
            return new ApiLineVoice[size];
        }
    };
}
