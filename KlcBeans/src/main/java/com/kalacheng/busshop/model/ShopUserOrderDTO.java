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
 * 用户(主播)订单列表返回
 */
public class ShopUserOrderDTO  implements Parcelable
{
 public ShopUserOrderDTO()
{
}

/**
 * 商家订单信息
 */
public com.kalacheng.busshop.model.ShopBusinessOrder businessOrder;

/**
 * 买家信息
 */
public com.kalacheng.libuser.model.AppUser buyUser;

/**
 * 买家发货物流单号
 */
public String logisticsNum;

/**
 * 退货物流单号
 */
public String refundLogisticsNum;

/**
 * 对应的商品信息
 */
public List<com.kalacheng.busshop.model.ShopSubOrder> subOrderList;

/**
 * 主播id
 */
public long anchorId;

/**
 * 商品总数量
 */
public int goodsNum;

   public ShopUserOrderDTO(Parcel in) 
{

businessOrder=in.readParcelable(com.kalacheng.busshop.model.ShopBusinessOrder.class.getClassLoader());

buyUser=in.readParcelable(com.kalacheng.libuser.model.AppUser.class.getClassLoader());
logisticsNum=in.readString();
refundLogisticsNum=in.readString();

if(subOrderList==null){
subOrderList=  new ArrayList<>();
 }
in.readTypedList(subOrderList,com.kalacheng.busshop.model.ShopSubOrder.CREATOR);
anchorId=in.readLong();
goodsNum=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeParcelable(businessOrder,flags);

dest.writeParcelable(buyUser,flags);
dest.writeString(logisticsNum);
dest.writeString(refundLogisticsNum);

dest.writeTypedList(subOrderList);
dest.writeLong(anchorId);
dest.writeInt(goodsNum);

}

  public static void cloneObj(ShopUserOrderDTO source,ShopUserOrderDTO target)
{
        if(source.businessOrder==null)
        {
            target.businessOrder=null;
        }else
        {
            ShopBusinessOrder.cloneObj(source.businessOrder,target.businessOrder);
        }
        if(source.buyUser==null)
        {
            target.buyUser=null;
        }else
        {
            AppUser.cloneObj(source.buyUser,target.buyUser);
        }

target.logisticsNum=source.logisticsNum;

target.refundLogisticsNum=source.refundLogisticsNum;

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

target.goodsNum=source.goodsNum;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopUserOrderDTO> CREATOR = new Creator<ShopUserOrderDTO>() {
        @Override
        public ShopUserOrderDTO createFromParcel(Parcel in) {
            return new ShopUserOrderDTO(in);
        }

        @Override
        public ShopUserOrderDTO[] newArray(int size) {
            return new ShopUserOrderDTO[size];
        }
    };
}
