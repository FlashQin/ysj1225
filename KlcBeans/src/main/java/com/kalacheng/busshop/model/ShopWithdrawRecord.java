package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 提现记录
 */
public class ShopWithdrawRecord  implements Parcelable
{
 public ShopWithdrawRecord()
{
}

/**
 * 提现金额
 */
public double amount;

/**
 * 订单号
 */
public String orderNo;

/**
 * 添加时间
 */
public Date addTime;

/**
 * 商家id
 */
public long businessId;

/**
 * 主播Id
 */
public long anchorId;

/**
 * 实际提现金额
 */
public double realAmount;

/**
 * 支付账号Id
 */
public long accountId;

/**
 * 修改时间
 */
public Date upTime;

/**
 * 小店余额
 */
public double balance;

/**
 * 服务费率
 */
public double servicePerc;

/**
 * null
 */
public long id;

/**
 * 服务费
 */
public double serviceMoney;

/**
 * 备注
 */
public String remarks;

/**
 * 审核状态：0待审核，1审核通过，2拒绝
 */
public int status;

   public ShopWithdrawRecord(Parcel in) 
{
amount=in.readDouble();
orderNo=in.readString();
addTime=new Date( in.readLong());
businessId=in.readLong();
anchorId=in.readLong();
realAmount=in.readDouble();
accountId=in.readLong();
upTime=new Date( in.readLong());
balance=in.readDouble();
servicePerc=in.readDouble();
id=in.readLong();
serviceMoney=in.readDouble();
remarks=in.readString();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeDouble(amount);
dest.writeString(orderNo);
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeLong(businessId);
dest.writeLong(anchorId);
dest.writeDouble(realAmount);
dest.writeLong(accountId);
dest.writeLong(upTime==null?0:upTime.getTime());
dest.writeDouble(balance);
dest.writeDouble(servicePerc);
dest.writeLong(id);
dest.writeDouble(serviceMoney);
dest.writeString(remarks);
dest.writeInt(status);

}

  public static void cloneObj(ShopWithdrawRecord source,ShopWithdrawRecord target)
{

target.amount=source.amount;

target.orderNo=source.orderNo;

target.addTime=source.addTime;

target.businessId=source.businessId;

target.anchorId=source.anchorId;

target.realAmount=source.realAmount;

target.accountId=source.accountId;

target.upTime=source.upTime;

target.balance=source.balance;

target.servicePerc=source.servicePerc;

target.id=source.id;

target.serviceMoney=source.serviceMoney;

target.remarks=source.remarks;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopWithdrawRecord> CREATOR = new Creator<ShopWithdrawRecord>() {
        @Override
        public ShopWithdrawRecord createFromParcel(Parcel in) {
            return new ShopWithdrawRecord(in);
        }

        @Override
        public ShopWithdrawRecord[] newArray(int size) {
            return new ShopWithdrawRecord[size];
        }
    };
}
