package com.kalacheng.busooolive.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 邀请用户响应
 */
public class OOOInviteRet  implements Parcelable
{
 public OOOInviteRet()
{
}

/**
 * hostId封面图
 */
public String thumb;

/**
 * 被收费用户ID
 */
public long feeUid;

/**
 * 等待用户超时的毫秒数
 */
public long InviteTimeOutMilliSecond;

/**
 * 会话ID；后面发送的大
 */
public long sessionID;

/**
 * 通话每分钟费用
 */
public double oooFee;

   public OOOInviteRet(Parcel in) 
{
thumb=in.readString();
feeUid=in.readLong();
InviteTimeOutMilliSecond=in.readLong();
sessionID=in.readLong();
oooFee=in.readDouble();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(thumb);
dest.writeLong(feeUid);
dest.writeLong(InviteTimeOutMilliSecond);
dest.writeLong(sessionID);
dest.writeDouble(oooFee);

}

  public static void cloneObj(OOOInviteRet source,OOOInviteRet target)
{

target.thumb=source.thumb;

target.feeUid=source.feeUid;

target.InviteTimeOutMilliSecond=source.InviteTimeOutMilliSecond;

target.sessionID=source.sessionID;

target.oooFee=source.oooFee;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OOOInviteRet> CREATOR = new Creator<OOOInviteRet>() {
        @Override
        public OOOInviteRet createFromParcel(Parcel in) {
            return new OOOInviteRet(in);
        }

        @Override
        public OOOInviteRet[] newArray(int size) {
            return new OOOInviteRet[size];
        }
    };
}
