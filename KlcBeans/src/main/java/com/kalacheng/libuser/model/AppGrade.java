package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 等级
 */
public class AppGrade  implements Parcelable
{
 public AppGrade()
{
}

/**
 * 主播铭牌铭牌等级
 */
public String nameplateGrade;

/**
 * 贵族坐骑名称
 */
public String vipCarName;

/**
 * 主播铭牌ICON, 需要有默认ICON
 */
public String nameplateIcon;

/**
 * 标准值范围_头
 */
public int startVal;

/**
 * 添加时间
 */
public Date addTime;

/**
 * 对应坐骑的ID =
 */
public int appCarId;

/**
 * 通话折扣
 */
public double callDiscount;

/**
 * 类型 1:用户等级 2：财富等级 3：主播等级 4：贵族等级
 */
public int type;

/**
 * 勋章级别， 唯一， 不变
 */
public int medalLv;

/**
 * 等级icon
 */
public String gradeIcon;

/**
 * 是否有奖励活动参与资格， 0:没有， 1：有
 */
public int isJoinActiveAuth;

/**
 * 标准值单位
 */
public String unit;

/**
 * 是否有系统推荐位， 0:没有， 1：有
 */
public int isSysRecommend;

/**
 * 充值折扣
 */
public double rechargeDiscount;

/**
 * 分成比例
 */
public double anchorPerc;

/**
 * 等级
 */
public int grade;

/**
 * 等级名称
 */
public String name;

/**
 * 主播铭牌描述
 */
public String nameplateDesc;

/**
 * null
 */
public long id;

/**
 * 勋章ID
 */
public long medalId;

   public AppGrade(Parcel in) 
{
nameplateGrade=in.readString();
vipCarName=in.readString();
nameplateIcon=in.readString();
startVal=in.readInt();
addTime=new Date( in.readLong());
appCarId=in.readInt();
callDiscount=in.readDouble();
type=in.readInt();
medalLv=in.readInt();
gradeIcon=in.readString();
isJoinActiveAuth=in.readInt();
unit=in.readString();
isSysRecommend=in.readInt();
rechargeDiscount=in.readDouble();
anchorPerc=in.readDouble();
grade=in.readInt();
name=in.readString();
nameplateDesc=in.readString();
id=in.readLong();
medalId=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(nameplateGrade);
dest.writeString(vipCarName);
dest.writeString(nameplateIcon);
dest.writeInt(startVal);
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeInt(appCarId);
dest.writeDouble(callDiscount);
dest.writeInt(type);
dest.writeInt(medalLv);
dest.writeString(gradeIcon);
dest.writeInt(isJoinActiveAuth);
dest.writeString(unit);
dest.writeInt(isSysRecommend);
dest.writeDouble(rechargeDiscount);
dest.writeDouble(anchorPerc);
dest.writeInt(grade);
dest.writeString(name);
dest.writeString(nameplateDesc);
dest.writeLong(id);
dest.writeLong(medalId);

}

  public static void cloneObj(AppGrade source,AppGrade target)
{

target.nameplateGrade=source.nameplateGrade;

target.vipCarName=source.vipCarName;

target.nameplateIcon=source.nameplateIcon;

target.startVal=source.startVal;

target.addTime=source.addTime;

target.appCarId=source.appCarId;

target.callDiscount=source.callDiscount;

target.type=source.type;

target.medalLv=source.medalLv;

target.gradeIcon=source.gradeIcon;

target.isJoinActiveAuth=source.isJoinActiveAuth;

target.unit=source.unit;

target.isSysRecommend=source.isSysRecommend;

target.rechargeDiscount=source.rechargeDiscount;

target.anchorPerc=source.anchorPerc;

target.grade=source.grade;

target.name=source.name;

target.nameplateDesc=source.nameplateDesc;

target.id=source.id;

target.medalId=source.medalId;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppGrade> CREATOR = new Creator<AppGrade>() {
        @Override
        public AppGrade createFromParcel(Parcel in) {
            return new AppGrade(in);
        }

        @Override
        public AppGrade[] newArray(int size) {
            return new AppGrade[size];
        }
    };
}
