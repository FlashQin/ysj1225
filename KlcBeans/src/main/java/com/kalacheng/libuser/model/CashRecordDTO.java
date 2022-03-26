package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 提现记录
 */
public class CashRecordDTO  implements Parcelable
{
 public CashRecordDTO()
{
}

/**
 * 实际到账金额
 */
public double actualMoney;

/**
 * 用户ID
 */
public long uid;

/**
 * 剩余映票数
 */
public double surplusVotes;

/**
 * 申请时间
 */
public Date addtime;

/**
 * 提现映票数
 */
public double votes;

/**
 * 账号类型：1。支付宝 2。微信 3。银行卡
 */
public int type;

/**
 * 公会ID
 */
public long guildId;

/**
 * 状态，0审核中，1审核通过，2审核拒绝
 */
public int status;

   public CashRecordDTO(Parcel in) 
{
actualMoney=in.readDouble();
uid=in.readLong();
surplusVotes=in.readDouble();
addtime=new Date( in.readLong());
votes=in.readDouble();
type=in.readInt();
guildId=in.readLong();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeDouble(actualMoney);
dest.writeLong(uid);
dest.writeDouble(surplusVotes);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeDouble(votes);
dest.writeInt(type);
dest.writeLong(guildId);
dest.writeInt(status);

}

  public static void cloneObj(CashRecordDTO source,CashRecordDTO target)
{

target.actualMoney=source.actualMoney;

target.uid=source.uid;

target.surplusVotes=source.surplusVotes;

target.addtime=source.addtime;

target.votes=source.votes;

target.type=source.type;

target.guildId=source.guildId;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CashRecordDTO> CREATOR = new Creator<CashRecordDTO>() {
        @Override
        public CashRecordDTO createFromParcel(Parcel in) {
            return new CashRecordDTO(in);
        }

        @Override
        public CashRecordDTO[] newArray(int size) {
            return new CashRecordDTO[size];
        }
    };
}
