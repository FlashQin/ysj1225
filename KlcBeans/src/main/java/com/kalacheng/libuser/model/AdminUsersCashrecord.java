package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 消费榜
 */
public class AdminUsersCashrecord  implements Parcelable
{
 public AdminUsersCashrecord()
{
}

/**
 * 银行名称
 */
public String accountBank;

/**
 * 排行
 */
public int number;

/**
 * 用户id
 */
public long uid;

/**
 * 类型，1表示支付宝，2表示微信，3表示银行卡
 */
public int accountType;

/**
 * 用户头像
 */
public String userAvatar;

/**
 * 姓名
 */
public String name;

/**
 * 总金额
 */
public int totalcoin;

/**
 * 姓名
 */
public String userName;

/**
 * 账号
 */
public String account;

   public AdminUsersCashrecord(Parcel in) 
{
accountBank=in.readString();
number=in.readInt();
uid=in.readLong();
accountType=in.readInt();
userAvatar=in.readString();
name=in.readString();
totalcoin=in.readInt();
userName=in.readString();
account=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(accountBank);
dest.writeInt(number);
dest.writeLong(uid);
dest.writeInt(accountType);
dest.writeString(userAvatar);
dest.writeString(name);
dest.writeInt(totalcoin);
dest.writeString(userName);
dest.writeString(account);

}

  public static void cloneObj(AdminUsersCashrecord source,AdminUsersCashrecord target)
{

target.accountBank=source.accountBank;

target.number=source.number;

target.uid=source.uid;

target.accountType=source.accountType;

target.userAvatar=source.userAvatar;

target.name=source.name;

target.totalcoin=source.totalcoin;

target.userName=source.userName;

target.account=source.account;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdminUsersCashrecord> CREATOR = new Creator<AdminUsersCashrecord>() {
        @Override
        public AdminUsersCashrecord createFromParcel(Parcel in) {
            return new AdminUsersCashrecord(in);
        }

        @Override
        public AdminUsersCashrecord[] newArray(int size) {
            return new AdminUsersCashrecord[size];
        }
    };
}
