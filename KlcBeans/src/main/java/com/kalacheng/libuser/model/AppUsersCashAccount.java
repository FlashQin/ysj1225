package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 用户提现账户表
 */
public class AppUsersCashAccount  implements Parcelable
{
 public AppUsersCashAccount()
{
}

/**
 * 银行名称
 */
public String accountBank;

/**
 * 用户ID
 */
public long uid;

/**
 * 是否默认 1:默认 0:非默认
 */
public int isDefault;

/**
 * 添加时间
 */
public Date addtime;

/**
 * 姓名
 */
public String name;

/**
 * null
 */
public long id;

/**
 * 账号类型 1:表示支付宝 2:表示微信 3:表示银行卡
 */
public int type;

/**
 * 支行
 */
public String branch;

/**
 * 账号
 */
public String account;

   public AppUsersCashAccount(Parcel in) 
{
accountBank=in.readString();
uid=in.readLong();
isDefault=in.readInt();
addtime=new Date( in.readLong());
name=in.readString();
id=in.readLong();
type=in.readInt();
branch=in.readString();
account=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(accountBank);
dest.writeLong(uid);
dest.writeInt(isDefault);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeString(name);
dest.writeLong(id);
dest.writeInt(type);
dest.writeString(branch);
dest.writeString(account);

}

  public static void cloneObj(AppUsersCashAccount source,AppUsersCashAccount target)
{

target.accountBank=source.accountBank;

target.uid=source.uid;

target.isDefault=source.isDefault;

target.addtime=source.addtime;

target.name=source.name;

target.id=source.id;

target.type=source.type;

target.branch=source.branch;

target.account=source.account;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppUsersCashAccount> CREATOR = new Creator<AppUsersCashAccount>() {
        @Override
        public AppUsersCashAccount createFromParcel(Parcel in) {
            return new AppUsersCashAccount(in);
        }

        @Override
        public AppUsersCashAccount[] newArray(int size) {
            return new AppUsersCashAccount[size];
        }
    };
}
