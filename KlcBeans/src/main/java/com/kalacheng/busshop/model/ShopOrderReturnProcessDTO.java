package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 预估订单退货流程model
 */
public class ShopOrderReturnProcessDTO  implements Parcelable
{
 public ShopOrderReturnProcessDTO()
{
}

/**
 * 节点名称
 */
public String nodeName;

/**
 * 变更时间
 */
public Date upTime;

/**
 * 节点序号
 */
public int nodeNo;

/**
 * 是否操作 0：待操作; 1:已操作
 */
public int operateStatus;

/**
 * 订单号
 */
public String orderNo;

/**
 * 节点状态 0：正常; 1:失效
 */
public int nodeStatus;

   public ShopOrderReturnProcessDTO(Parcel in) 
{
nodeName=in.readString();
upTime=new Date( in.readLong());
nodeNo=in.readInt();
operateStatus=in.readInt();
orderNo=in.readString();
nodeStatus=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(nodeName);
dest.writeLong(upTime==null?0:upTime.getTime());
dest.writeInt(nodeNo);
dest.writeInt(operateStatus);
dest.writeString(orderNo);
dest.writeInt(nodeStatus);

}

  public static void cloneObj(ShopOrderReturnProcessDTO source,ShopOrderReturnProcessDTO target)
{

target.nodeName=source.nodeName;

target.upTime=source.upTime;

target.nodeNo=source.nodeNo;

target.operateStatus=source.operateStatus;

target.orderNo=source.orderNo;

target.nodeStatus=source.nodeStatus;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopOrderReturnProcessDTO> CREATOR = new Creator<ShopOrderReturnProcessDTO>() {
        @Override
        public ShopOrderReturnProcessDTO createFromParcel(Parcel in) {
            return new ShopOrderReturnProcessDTO(in);
        }

        @Override
        public ShopOrderReturnProcessDTO[] newArray(int size) {
            return new ShopOrderReturnProcessDTO[size];
        }
    };
}
