package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 收益中心
 */
public class ProfitCenterDTO  implements Parcelable
{
 public ProfitCenterDTO()
{
}

/**
 * 公会收益
 */
public double guildAccount;

/**
 * 公会图像
 */
public String guildAvatar;

/**
 * 映票提现最低额度
 */
public double cashMin;

/**
 * 佣金手续费 小数
 */
public double amountService;

/**
 * 主播与公会的分成比例
 */
public double perc;

/**
 * 主播公会的累计收益
 */
public double anchorGuildCumulativeIncome;

/**
 * 主播抽成比例
 */
public double anchorPerc;

/**
 * 佣金提现最低额度
 */
public double amountMin;

/**
 * 主播等级图标
 */
public String gradeAvatar;

/**
 * 提现期限开始时间
 */
public int cashStart;

/**
 * 单日最大提现次数
 */
public int cashMaxDay;

/**
 * 单月最大提现次数
 */
public int cashMaxMonth;

/**
 * 提现比例
 */
public double cashRate;

/**
 * 平台总收益
 */
public double votestotal;

/**
 * 用户图像
 */
public String avatar;

/**
 * 公会的分成比例
 */
public double guildPerc;

/**
 * 公会id
 */
public long guildId;

/**
 * 累计获得的分成映票
 */
public double totalvotes;

/**
 * 金币手续费 小数
 */
public double service;

/**
 * 主播等级
 */
public int grade;

/**
 * 平台可提收益
 */
public double votes;

/**
 * 提现期限结束时间
 */
public int cashEnd;

/**
 * 规则描述
 */
public String describe;

/**
 * 默认账号
 */
public com.kalacheng.libuser.model.AppUsersCashAccount account;

/**
 * 公会的累计收益
 */
public double guildCumulativeIncome;

   public ProfitCenterDTO(Parcel in) 
{
guildAccount=in.readDouble();
guildAvatar=in.readString();
cashMin=in.readDouble();
amountService=in.readDouble();
perc=in.readDouble();
anchorGuildCumulativeIncome=in.readDouble();
anchorPerc=in.readDouble();
amountMin=in.readDouble();
gradeAvatar=in.readString();
cashStart=in.readInt();
cashMaxDay=in.readInt();
cashMaxMonth=in.readInt();
cashRate=in.readDouble();
votestotal=in.readDouble();
avatar=in.readString();
guildPerc=in.readDouble();
guildId=in.readLong();
totalvotes=in.readDouble();
service=in.readDouble();
grade=in.readInt();
votes=in.readDouble();
cashEnd=in.readInt();
describe=in.readString();

account=in.readParcelable(com.kalacheng.libuser.model.AppUsersCashAccount.class.getClassLoader());
guildCumulativeIncome=in.readDouble();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeDouble(guildAccount);
dest.writeString(guildAvatar);
dest.writeDouble(cashMin);
dest.writeDouble(amountService);
dest.writeDouble(perc);
dest.writeDouble(anchorGuildCumulativeIncome);
dest.writeDouble(anchorPerc);
dest.writeDouble(amountMin);
dest.writeString(gradeAvatar);
dest.writeInt(cashStart);
dest.writeInt(cashMaxDay);
dest.writeInt(cashMaxMonth);
dest.writeDouble(cashRate);
dest.writeDouble(votestotal);
dest.writeString(avatar);
dest.writeDouble(guildPerc);
dest.writeLong(guildId);
dest.writeDouble(totalvotes);
dest.writeDouble(service);
dest.writeInt(grade);
dest.writeDouble(votes);
dest.writeInt(cashEnd);
dest.writeString(describe);

dest.writeParcelable(account,flags);
dest.writeDouble(guildCumulativeIncome);

}

  public static void cloneObj(ProfitCenterDTO source,ProfitCenterDTO target)
{

target.guildAccount=source.guildAccount;

target.guildAvatar=source.guildAvatar;

target.cashMin=source.cashMin;

target.amountService=source.amountService;

target.perc=source.perc;

target.anchorGuildCumulativeIncome=source.anchorGuildCumulativeIncome;

target.anchorPerc=source.anchorPerc;

target.amountMin=source.amountMin;

target.gradeAvatar=source.gradeAvatar;

target.cashStart=source.cashStart;

target.cashMaxDay=source.cashMaxDay;

target.cashMaxMonth=source.cashMaxMonth;

target.cashRate=source.cashRate;

target.votestotal=source.votestotal;

target.avatar=source.avatar;

target.guildPerc=source.guildPerc;

target.guildId=source.guildId;

target.totalvotes=source.totalvotes;

target.service=source.service;

target.grade=source.grade;

target.votes=source.votes;

target.cashEnd=source.cashEnd;

target.describe=source.describe;
        if(source.account==null)
        {
            target.account=null;
        }else
        {
            AppUsersCashAccount.cloneObj(source.account,target.account);
        }

target.guildCumulativeIncome=source.guildCumulativeIncome;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProfitCenterDTO> CREATOR = new Creator<ProfitCenterDTO>() {
        @Override
        public ProfitCenterDTO createFromParcel(Parcel in) {
            return new ProfitCenterDTO(in);
        }

        @Override
        public ProfitCenterDTO[] newArray(int size) {
            return new ProfitCenterDTO[size];
        }
    };
}
