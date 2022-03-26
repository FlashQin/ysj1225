package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 商家列表
 */
public class ShopBusiness  implements Parcelable
{
 public ShopBusiness()
{
}

/**
 * 营业执照
 */
public String businessLicense;

/**
 * 剩余可提现金额
 */
public double amount;

/**
 * 已售商品总数量
 */
public int totalSoldNum;

/**
 * 申请时间
 */
public Date addTime;

/**
 * 商家简介图片地址
 */
public String presentPicture;

/**
 * 联系电话
 */
public String mobile;

/**
 * 审核备注
 */
public String auditNotes;

/**
 * 备注
 */
public String remake;

/**
 * 主播Id
 */
public long anchorId;

/**
 * 总收益金额
 */
public double totalAmount;

/**
 * 审核时间
 */
public Date auditTime;

/**
 * 商家名称
 */
public String name;

/**
 * 累计提现金额
 */
public double totalCash;

/**
 * 商家LOGO
 */
public String logo;

/**
 * null
 */
public long id;

/**
 * 商家简介
 */
public String present;

/**
 * 已上架商品数量
 */
public int effectiveGoodsNum;

/**
 * 审核状态 0：未申请；1：审核中; 2:审核通过; 3:审核拒绝; 4:冻结
 */
public int status;

   public ShopBusiness(Parcel in) 
{
businessLicense=in.readString();
amount=in.readDouble();
totalSoldNum=in.readInt();
addTime=new Date( in.readLong());
presentPicture=in.readString();
mobile=in.readString();
auditNotes=in.readString();
remake=in.readString();
anchorId=in.readLong();
totalAmount=in.readDouble();
auditTime=new Date( in.readLong());
name=in.readString();
totalCash=in.readDouble();
logo=in.readString();
id=in.readLong();
present=in.readString();
effectiveGoodsNum=in.readInt();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(businessLicense);
dest.writeDouble(amount);
dest.writeInt(totalSoldNum);
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeString(presentPicture);
dest.writeString(mobile);
dest.writeString(auditNotes);
dest.writeString(remake);
dest.writeLong(anchorId);
dest.writeDouble(totalAmount);
dest.writeLong(auditTime==null?0:auditTime.getTime());
dest.writeString(name);
dest.writeDouble(totalCash);
dest.writeString(logo);
dest.writeLong(id);
dest.writeString(present);
dest.writeInt(effectiveGoodsNum);
dest.writeInt(status);

}

  public static void cloneObj(ShopBusiness source,ShopBusiness target)
{

target.businessLicense=source.businessLicense;

target.amount=source.amount;

target.totalSoldNum=source.totalSoldNum;

target.addTime=source.addTime;

target.presentPicture=source.presentPicture;

target.mobile=source.mobile;

target.auditNotes=source.auditNotes;

target.remake=source.remake;

target.anchorId=source.anchorId;

target.totalAmount=source.totalAmount;

target.auditTime=source.auditTime;

target.name=source.name;

target.totalCash=source.totalCash;

target.logo=source.logo;

target.id=source.id;

target.present=source.present;

target.effectiveGoodsNum=source.effectiveGoodsNum;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopBusiness> CREATOR = new Creator<ShopBusiness>() {
        @Override
        public ShopBusiness createFromParcel(Parcel in) {
            return new ShopBusiness(in);
        }

        @Override
        public ShopBusiness[] newArray(int size) {
            return new ShopBusiness[size];
        }
    };
}
