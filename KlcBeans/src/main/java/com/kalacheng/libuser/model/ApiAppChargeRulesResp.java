package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * APP获取充值规则接口
 */
public class ApiAppChargeRulesResp  implements Parcelable
{
 public ApiAppChargeRulesResp()
{
}

/**
 * 贵族等级名称
 */
public String nobleGradeName;

/**
 * 贵族剩余时长
 */
public String nobleLongTime;

/**
 * 贵族等级折扣
 */
public double rechargeDiscount;

/**
 * 是否首次充值 1:是 2:否
 */
public int isFirstRecharge;

/**
 * 魅力值数
 */
public double votes;

/**
 * 贵族等级
 */
public int nobleGrade;

/**
 * 充值规则list
 */
public List<com.kalacheng.libuser.model.AppUsersCharge> appChargeRules;

/**
 * 是否是贵族
 */
public int isvip;

/**
 * 用户钻石
 */
public double coin;

   public ApiAppChargeRulesResp(Parcel in) 
{
nobleGradeName=in.readString();
nobleLongTime=in.readString();
rechargeDiscount=in.readDouble();
isFirstRecharge=in.readInt();
votes=in.readDouble();
nobleGrade=in.readInt();

if(appChargeRules==null){
appChargeRules=  new ArrayList<>();
 }
in.readTypedList(appChargeRules,com.kalacheng.libuser.model.AppUsersCharge.CREATOR);
isvip=in.readInt();
coin=in.readDouble();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(nobleGradeName);
dest.writeString(nobleLongTime);
dest.writeDouble(rechargeDiscount);
dest.writeInt(isFirstRecharge);
dest.writeDouble(votes);
dest.writeInt(nobleGrade);

dest.writeTypedList(appChargeRules);
dest.writeInt(isvip);
dest.writeDouble(coin);

}

  public static void cloneObj(ApiAppChargeRulesResp source,ApiAppChargeRulesResp target)
{

target.nobleGradeName=source.nobleGradeName;

target.nobleLongTime=source.nobleLongTime;

target.rechargeDiscount=source.rechargeDiscount;

target.isFirstRecharge=source.isFirstRecharge;

target.votes=source.votes;

target.nobleGrade=source.nobleGrade;

        if(source.appChargeRules==null)
        {
            target.appChargeRules=null;
        }else
        {
            target.appChargeRules=new ArrayList();
            for(int i=0;i<source.appChargeRules.size();i++)
            {
            AppUsersCharge.cloneObj(source.appChargeRules.get(i),target.appChargeRules.get(i));
            }
        }


target.isvip=source.isvip;

target.coin=source.coin;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiAppChargeRulesResp> CREATOR = new Creator<ApiAppChargeRulesResp>() {
        @Override
        public ApiAppChargeRulesResp createFromParcel(Parcel in) {
            return new ApiAppChargeRulesResp(in);
        }

        @Override
        public ApiAppChargeRulesResp[] newArray(int size) {
            return new ApiAppChargeRulesResp[size];
        }
    };
}
