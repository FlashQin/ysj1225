package com.kalacheng.busooolive.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 挂断响应
 */
public class OOOHangupReturn  implements Parcelable
{
 public OOOHangupReturn()
{
}

/**
 * 挂断方id
 */
public long callUpUid;

/**
 * 挂断方为观众时的支出金额
 */
public double payCoin;

/**
 * 挂断方的角色 0用户 1主播 2副播
 */
public int role;

/**
 * 挂断原因:1:正常挂断 2:直播云异常 11:费用不足 12:对方网络断开 13:超时自动撤销 22:用户掉线拒接 31:直播被封禁
 */
public int hangupResion;

/**
 * 挂断方为主播或副播时的获得收入
 */
public double totalCoin;

/**
 * 挂断方头像
 */
public String avatar;

/**
 * 是否已经接通
 */
public int isTalked;

/**
 * 贵族优惠类型信息
 */
public String vipGradeMsg;

/**
 * 通话时长
 */
public long callTime;

/**
 * 贵族优惠金额
 */
public double vipCount;

/**
 * 挂断方用户名
 */
public String username;

   public OOOHangupReturn(Parcel in) 
{
callUpUid=in.readLong();
payCoin=in.readDouble();
role=in.readInt();
hangupResion=in.readInt();
totalCoin=in.readDouble();
avatar=in.readString();
isTalked=in.readInt();
vipGradeMsg=in.readString();
callTime=in.readLong();
vipCount=in.readDouble();
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(callUpUid);
dest.writeDouble(payCoin);
dest.writeInt(role);
dest.writeInt(hangupResion);
dest.writeDouble(totalCoin);
dest.writeString(avatar);
dest.writeInt(isTalked);
dest.writeString(vipGradeMsg);
dest.writeLong(callTime);
dest.writeDouble(vipCount);
dest.writeString(username);

}

  public static void cloneObj(OOOHangupReturn source,OOOHangupReturn target)
{

target.callUpUid=source.callUpUid;

target.payCoin=source.payCoin;

target.role=source.role;

target.hangupResion=source.hangupResion;

target.totalCoin=source.totalCoin;

target.avatar=source.avatar;

target.isTalked=source.isTalked;

target.vipGradeMsg=source.vipGradeMsg;

target.callTime=source.callTime;

target.vipCount=source.vipCount;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OOOHangupReturn> CREATOR = new Creator<OOOHangupReturn>() {
        @Override
        public OOOHangupReturn createFromParcel(Parcel in) {
            return new OOOHangupReturn(in);
        }

        @Override
        public OOOHangupReturn[] newArray(int size) {
            return new OOOHangupReturn[size];
        }
    };
}
