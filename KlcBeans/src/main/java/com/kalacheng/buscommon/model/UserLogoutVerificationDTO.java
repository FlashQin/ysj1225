package com.kalacheng.buscommon.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 用户注销验证model
 */
public class UserLogoutVerificationDTO  implements Parcelable
{
 public UserLogoutVerificationDTO()
{
}

/**
 * 角色 0:普通用户 1:主播
 */
public int role;

/**
 * 注销开关 0:开启 1:关闭
 */
public int logOffSwitch;

/**
 * 映票余额/可提现金额
 */
public double votes;

/**
 * 金币 (男朋友中为钻石)/充值金额
 */
public double coin;

   public UserLogoutVerificationDTO(Parcel in) 
{
role=in.readInt();
logOffSwitch=in.readInt();
votes=in.readDouble();
coin=in.readDouble();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(role);
dest.writeInt(logOffSwitch);
dest.writeDouble(votes);
dest.writeDouble(coin);

}

  public static void cloneObj(UserLogoutVerificationDTO source,UserLogoutVerificationDTO target)
{

target.role=source.role;

target.logOffSwitch=source.logOffSwitch;

target.votes=source.votes;

target.coin=source.coin;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserLogoutVerificationDTO> CREATOR = new Creator<UserLogoutVerificationDTO>() {
        @Override
        public UserLogoutVerificationDTO createFromParcel(Parcel in) {
            return new UserLogoutVerificationDTO(in);
        }

        @Override
        public UserLogoutVerificationDTO[] newArray(int size) {
            return new UserLogoutVerificationDTO[size];
        }
    };
}
