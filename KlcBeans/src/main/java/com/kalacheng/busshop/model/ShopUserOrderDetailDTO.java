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
 * 订单详情返回
 */
public class ShopUserOrderDetailDTO  implements Parcelable
{
 public ShopUserOrderDetailDTO()
{
}

/**
 * 退款流程集合
 */
public List<com.kalacheng.busshop.model.ShopOrderReturnProcessDTO> processList;

/**
 * 父订单信息
 */
public com.kalacheng.busshop.model.ShopParentOrder parentOrder;

/**
 * 商家订单信息
 */
public com.kalacheng.busshop.model.ShopBusinessOrder businessOrder;

/**
 * 卖家发货物流信息
 */
public String sellerLogisticsContent;

/**
 * 买家信息
 */
public com.kalacheng.libuser.model.AppUser buyUser;

/**
 * 对应的商品信息
 */
public List<com.kalacheng.busshop.model.ShopSubOrder> subOrderList;

/**
 * 主播id
 */
public long anchorId;

/**
 * 截至时间
 */
public Date closingDate;

/**
 * 物流名称
 */
public String logisticsName;

/**
 * 卖家发货物流时间点
 */
public Date sellerLogisticsTime;

/**
 * 退货物流名称
 */
public String refundLogisticsName;

/**
 * 买家发货物流单号
 */
public String logisticsNum;

/**
 * 退货物流单号
 */
public String refundLogisticsNum;

/**
 * 商品总数量
 */
public int goodsNum;

/**
 * 买家发货物流信息
 */
public String buyerLogisticsContent;

/**
 * 买家发货物流时间点
 */
public Date buyerLogisticsTime;

   public ShopUserOrderDetailDTO(Parcel in) 
{

if(processList==null){
processList=  new ArrayList<>();
 }
in.readTypedList(processList,com.kalacheng.busshop.model.ShopOrderReturnProcessDTO.CREATOR);

parentOrder=in.readParcelable(com.kalacheng.busshop.model.ShopParentOrder.class.getClassLoader());

businessOrder=in.readParcelable(com.kalacheng.busshop.model.ShopBusinessOrder.class.getClassLoader());
sellerLogisticsContent=in.readString();

buyUser=in.readParcelable(com.kalacheng.libuser.model.AppUser.class.getClassLoader());

if(subOrderList==null){
subOrderList=  new ArrayList<>();
 }
in.readTypedList(subOrderList,com.kalacheng.busshop.model.ShopSubOrder.CREATOR);
anchorId=in.readLong();
closingDate=new Date( in.readLong());
logisticsName=in.readString();
sellerLogisticsTime=new Date( in.readLong());
refundLogisticsName=in.readString();
logisticsNum=in.readString();
refundLogisticsNum=in.readString();
goodsNum=in.readInt();
buyerLogisticsContent=in.readString();
buyerLogisticsTime=new Date( in.readLong());

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(processList);

dest.writeParcelable(parentOrder,flags);

dest.writeParcelable(businessOrder,flags);
dest.writeString(sellerLogisticsContent);

dest.writeParcelable(buyUser,flags);

dest.writeTypedList(subOrderList);
dest.writeLong(anchorId);
dest.writeLong(closingDate==null?0:closingDate.getTime());
dest.writeString(logisticsName);
dest.writeLong(sellerLogisticsTime==null?0:sellerLogisticsTime.getTime());
dest.writeString(refundLogisticsName);
dest.writeString(logisticsNum);
dest.writeString(refundLogisticsNum);
dest.writeInt(goodsNum);
dest.writeString(buyerLogisticsContent);
dest.writeLong(buyerLogisticsTime==null?0:buyerLogisticsTime.getTime());

}

  public static void cloneObj(ShopUserOrderDetailDTO source,ShopUserOrderDetailDTO target)
{

        if(source.processList==null)
        {
            target.processList=null;
        }else
        {
            target.processList=new ArrayList();
            for(int i=0;i<source.processList.size();i++)
            {
            ShopOrderReturnProcessDTO.cloneObj(source.processList.get(i),target.processList.get(i));
            }
        }

        if(source.parentOrder==null)
        {
            target.parentOrder=null;
        }else
        {
            ShopParentOrder.cloneObj(source.parentOrder,target.parentOrder);
        }
        if(source.businessOrder==null)
        {
            target.businessOrder=null;
        }else
        {
            ShopBusinessOrder.cloneObj(source.businessOrder,target.businessOrder);
        }

target.sellerLogisticsContent=source.sellerLogisticsContent;
        if(source.buyUser==null)
        {
            target.buyUser=null;
        }else
        {
            AppUser.cloneObj(source.buyUser,target.buyUser);
        }

        if(source.subOrderList==null)
        {
            target.subOrderList=null;
        }else
        {
            target.subOrderList=new ArrayList();
            for(int i=0;i<source.subOrderList.size();i++)
            {
            ShopSubOrder.cloneObj(source.subOrderList.get(i),target.subOrderList.get(i));
            }
        }


target.anchorId=source.anchorId;

target.closingDate=source.closingDate;

target.logisticsName=source.logisticsName;

target.sellerLogisticsTime=source.sellerLogisticsTime;

target.refundLogisticsName=source.refundLogisticsName;

target.logisticsNum=source.logisticsNum;

target.refundLogisticsNum=source.refundLogisticsNum;

target.goodsNum=source.goodsNum;

target.buyerLogisticsContent=source.buyerLogisticsContent;

target.buyerLogisticsTime=source.buyerLogisticsTime;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopUserOrderDetailDTO> CREATOR = new Creator<ShopUserOrderDetailDTO>() {
        @Override
        public ShopUserOrderDetailDTO createFromParcel(Parcel in) {
            return new ShopUserOrderDetailDTO(in);
        }

        @Override
        public ShopUserOrderDetailDTO[] newArray(int size) {
            return new ShopUserOrderDetailDTO[size];
        }
    };
}
