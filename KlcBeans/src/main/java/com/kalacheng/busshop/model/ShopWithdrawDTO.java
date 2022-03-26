package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busshop.model.*;

import com.kalacheng.libuser.model.*;




/**
 * 商家提现信息
 */
public class ShopWithdrawDTO  implements Parcelable
{
 public ShopWithdrawDTO()
{
}

/**
 * 提现金额
 */
public double realAmount;

/**
 * 提现记录
 */
public List<com.kalacheng.busshop.model.ShopWithdrawRecord> shopWithdrawRecordList;

/**
 * 佣金比例
 */
public double sellRate;

/**
 * 手续费比例
 */
public double service;

/**
 * 默认账号
 */
public com.kalacheng.libuser.model.AppUsersCashAccount account;

   public ShopWithdrawDTO(Parcel in) 
{
realAmount=in.readDouble();

if(shopWithdrawRecordList==null){
shopWithdrawRecordList=  new ArrayList<>();
 }
in.readTypedList(shopWithdrawRecordList,com.kalacheng.busshop.model.ShopWithdrawRecord.CREATOR);
sellRate=in.readDouble();
service=in.readDouble();

account=in.readParcelable(com.kalacheng.libuser.model.AppUsersCashAccount.class.getClassLoader());

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeDouble(realAmount);

dest.writeTypedList(shopWithdrawRecordList);
dest.writeDouble(sellRate);
dest.writeDouble(service);

dest.writeParcelable(account,flags);

}

  public static void cloneObj(ShopWithdrawDTO source,ShopWithdrawDTO target)
{

target.realAmount=source.realAmount;

        if(source.shopWithdrawRecordList==null)
        {
            target.shopWithdrawRecordList=null;
        }else
        {
            target.shopWithdrawRecordList=new ArrayList();
            for(int i=0;i<source.shopWithdrawRecordList.size();i++)
            {
            ShopWithdrawRecord.cloneObj(source.shopWithdrawRecordList.get(i),target.shopWithdrawRecordList.get(i));
            }
        }


target.sellRate=source.sellRate;

target.service=source.service;
        if(source.account==null)
        {
            target.account=null;
        }else
        {
            AppUsersCashAccount.cloneObj(source.account,target.account);
        }

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopWithdrawDTO> CREATOR = new Creator<ShopWithdrawDTO>() {
        @Override
        public ShopWithdrawDTO createFromParcel(Parcel in) {
            return new ShopWithdrawDTO(in);
        }

        @Override
        public ShopWithdrawDTO[] newArray(int size) {
            return new ShopWithdrawDTO[size];
        }
    };
}
