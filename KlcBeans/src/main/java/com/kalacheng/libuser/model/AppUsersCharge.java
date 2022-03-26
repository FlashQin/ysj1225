package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 充值规则表
 */
public class AppUsersCharge  implements Parcelable
{
 public AppUsersCharge()
{
}

/**
 * 苹果金额
 */
public double discountMoneyIos;

/**
 * 赠送金币
 */
public int give;

/**
 * 苹果充值折扣描述
 */
public String iosDicountDesr;

/**
 * 序号
 */
public int orderno;

/**
 * 苹果项目ID
 */
public String productId;

/**
 * 安卓折扣金额
 */
public double discountMoney;

/**
 * 是否启用 0：不启用  1：启用
 */
public int isTip;

/**
 * 安卓折扣vip金额
 */
public double discountMoneyVip;

/**
 * 苹果vip金额
 */
public double discountMoneyIosVip;

/**
 * 安卓金额， 划线价
 */
public double money;

/**
 * 安卓充值折扣描述
 */
public String dicountDesr;

/**
 * 添加时间
 */
public Date addtime;

/**
 * 充值的类型 1:安卓 2:ios
 */
public int appType;

/**
 * 名称
 */
public String name;

/**
 * null
 */
public long id;

/**
 * 苹果金额， 划线价
 */
public double moneyIos;

/**
 * 金币数量
 */
public double coin;

   public AppUsersCharge(Parcel in) 
{
discountMoneyIos=in.readDouble();
give=in.readInt();
iosDicountDesr=in.readString();
orderno=in.readInt();
productId=in.readString();
discountMoney=in.readDouble();
isTip=in.readInt();
discountMoneyVip=in.readDouble();
discountMoneyIosVip=in.readDouble();
money=in.readDouble();
dicountDesr=in.readString();
addtime=new Date( in.readLong());
appType=in.readInt();
name=in.readString();
id=in.readLong();
moneyIos=in.readDouble();
coin=in.readDouble();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeDouble(discountMoneyIos);
dest.writeInt(give);
dest.writeString(iosDicountDesr);
dest.writeInt(orderno);
dest.writeString(productId);
dest.writeDouble(discountMoney);
dest.writeInt(isTip);
dest.writeDouble(discountMoneyVip);
dest.writeDouble(discountMoneyIosVip);
dest.writeDouble(money);
dest.writeString(dicountDesr);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeInt(appType);
dest.writeString(name);
dest.writeLong(id);
dest.writeDouble(moneyIos);
dest.writeDouble(coin);

}

  public static void cloneObj(AppUsersCharge source,AppUsersCharge target)
{

target.discountMoneyIos=source.discountMoneyIos;

target.give=source.give;

target.iosDicountDesr=source.iosDicountDesr;

target.orderno=source.orderno;

target.productId=source.productId;

target.discountMoney=source.discountMoney;

target.isTip=source.isTip;

target.discountMoneyVip=source.discountMoneyVip;

target.discountMoneyIosVip=source.discountMoneyIosVip;

target.money=source.money;

target.dicountDesr=source.dicountDesr;

target.addtime=source.addtime;

target.appType=source.appType;

target.name=source.name;

target.id=source.id;

target.moneyIos=source.moneyIos;

target.coin=source.coin;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppUsersCharge> CREATOR = new Creator<AppUsersCharge>() {
        @Override
        public AppUsersCharge createFromParcel(Parcel in) {
            return new AppUsersCharge(in);
        }

        @Override
        public AppUsersCharge[] newArray(int size) {
            return new AppUsersCharge[size];
        }
    };
}
