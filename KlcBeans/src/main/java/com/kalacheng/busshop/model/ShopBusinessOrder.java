package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 商家订单表
 */
public class ShopBusinessOrder  implements Parcelable
{
 public ShopBusinessOrder()
{
}

/**
 * 申请退款原因
 */
public String reason;

/**
 * 发货物流id
 */
public long logisticsId;

/**
 * 人工退款金额
 */
public double manualRefundMoney;

/**
 * 添加时间
 */
public Date addTime;

/**
 * 商家id
 */
public long businessId;

/**
 * 商家名称
 */
public String businessName;

/**
 * 申请退款审核不通过原因
 */
public String auditFailureReason;

/**
 * 订单号
 */
public String orderNum;

/**
 * 收货人手机号
 */
public String phoneNum;

/**
 * 交易成功时间
 */
public Date transactionTime;

/**
 * 收货人地址id
 */
public long addressId;

/**
 * 退款类型 1.仅退款 2.退货退款
 */
public int refundType;

/**
 * 用户id
 */
public long uid;

/**
 * 订单金额
 */
public double orderAmount;

/**
 * 退货物流id
 */
public long refundLogisticsId;

/**
 * 人工退款操作人
 */
public String manualRefundOperator;

/**
 * 交易金额(实付金额)
 */
public double transactionAmount;

/**
 * null
 */
public long id;

/**
 * 收货人地址
 */
public String address;

/**
 * 退款完成时间
 */
public Date refundTime;

/**
 * 商家logo
 */
public String businessLogo;

/**
 * 备注
 */
public String remake;

/**
 * 订单来源id
 */
public long goodsChannelId;

/**
 * 退货订单状态 1.待审核 2.待发货 3.待收货 4.退款中 5.退款完成 6.退款失败 7.审核拒绝
 */
public int quitStatus;

/**
 * 人工退款方式：1支付宝，2微信，3人工转账
 */
public int manualRefundType;

/**
 * 退款备注图片
 */
public String refundNotesImages;

/**
 * 是否人工退款 1是，2否
 */
public int isManualRefund;

/**
 * 收货人姓名
 */
public String name;

/**
 * 申请退款备注
 */
public String refundNotes;

/**
 * 父订单id
 */
public long payId;

/**
 * 任务id
 */
public long taskId;

/**
 * 订单状态 1.待付款 2.待发货 3.待收货 4. 已完成 5. 已取消
 */
public int status;

   public ShopBusinessOrder(Parcel in) 
{
reason=in.readString();
logisticsId=in.readLong();
manualRefundMoney=in.readDouble();
addTime=new Date( in.readLong());
businessId=in.readLong();
businessName=in.readString();
auditFailureReason=in.readString();
orderNum=in.readString();
phoneNum=in.readString();
transactionTime=new Date( in.readLong());
addressId=in.readLong();
refundType=in.readInt();
uid=in.readLong();
orderAmount=in.readDouble();
refundLogisticsId=in.readLong();
manualRefundOperator=in.readString();
transactionAmount=in.readDouble();
id=in.readLong();
address=in.readString();
refundTime=new Date( in.readLong());
businessLogo=in.readString();
remake=in.readString();
goodsChannelId=in.readLong();
quitStatus=in.readInt();
manualRefundType=in.readInt();
refundNotesImages=in.readString();
isManualRefund=in.readInt();
name=in.readString();
refundNotes=in.readString();
payId=in.readLong();
taskId=in.readLong();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(reason);
dest.writeLong(logisticsId);
dest.writeDouble(manualRefundMoney);
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeLong(businessId);
dest.writeString(businessName);
dest.writeString(auditFailureReason);
dest.writeString(orderNum);
dest.writeString(phoneNum);
dest.writeLong(transactionTime==null?0:transactionTime.getTime());
dest.writeLong(addressId);
dest.writeInt(refundType);
dest.writeLong(uid);
dest.writeDouble(orderAmount);
dest.writeLong(refundLogisticsId);
dest.writeString(manualRefundOperator);
dest.writeDouble(transactionAmount);
dest.writeLong(id);
dest.writeString(address);
dest.writeLong(refundTime==null?0:refundTime.getTime());
dest.writeString(businessLogo);
dest.writeString(remake);
dest.writeLong(goodsChannelId);
dest.writeInt(quitStatus);
dest.writeInt(manualRefundType);
dest.writeString(refundNotesImages);
dest.writeInt(isManualRefund);
dest.writeString(name);
dest.writeString(refundNotes);
dest.writeLong(payId);
dest.writeLong(taskId);
dest.writeInt(status);

}

  public static void cloneObj(ShopBusinessOrder source,ShopBusinessOrder target)
{

target.reason=source.reason;

target.logisticsId=source.logisticsId;

target.manualRefundMoney=source.manualRefundMoney;

target.addTime=source.addTime;

target.businessId=source.businessId;

target.businessName=source.businessName;

target.auditFailureReason=source.auditFailureReason;

target.orderNum=source.orderNum;

target.phoneNum=source.phoneNum;

target.transactionTime=source.transactionTime;

target.addressId=source.addressId;

target.refundType=source.refundType;

target.uid=source.uid;

target.orderAmount=source.orderAmount;

target.refundLogisticsId=source.refundLogisticsId;

target.manualRefundOperator=source.manualRefundOperator;

target.transactionAmount=source.transactionAmount;

target.id=source.id;

target.address=source.address;

target.refundTime=source.refundTime;

target.businessLogo=source.businessLogo;

target.remake=source.remake;

target.goodsChannelId=source.goodsChannelId;

target.quitStatus=source.quitStatus;

target.manualRefundType=source.manualRefundType;

target.refundNotesImages=source.refundNotesImages;

target.isManualRefund=source.isManualRefund;

target.name=source.name;

target.refundNotes=source.refundNotes;

target.payId=source.payId;

target.taskId=source.taskId;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopBusinessOrder> CREATOR = new Creator<ShopBusinessOrder>() {
        @Override
        public ShopBusinessOrder createFromParcel(Parcel in) {
            return new ShopBusinessOrder(in);
        }

        @Override
        public ShopBusinessOrder[] newArray(int size) {
            return new ShopBusinessOrder[size];
        }
    };
}
