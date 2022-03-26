package com.kalacheng.shopping.buyer.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.kalacheng.busshop.model.ShopBusinessOrder;
import com.kalacheng.busshop.model.ShopSubOrder;
import com.kalacheng.libuser.model.AppUser;


public class ShopUserOrderBean implements Parcelable {

    /**
     * 商家订单信息
     */
    private ShopBusinessOrder businessOrder;

    /**
     * 买家信息
     */
    private AppUser buyUser;

    /**
     * 对应的商品信息
     */
    private ShopSubOrder subOrder;

    /**
     * 商品总数量
     */
    private int goodsNum;

    private double total;

    private int viewType;

    private long anchorId;

    /**
     * 买家发货物流单号
     */
    public String logisticsNum;

    /**
     * 退货物流单号
     */
    public String refundLogisticsNum;


    public ShopBusinessOrder getBusinessOrder() {
        return businessOrder;
    }

    public void setBusinessOrder(ShopBusinessOrder businessOrder) {
        this.businessOrder = businessOrder;
    }

    public AppUser getBuyUser() {
        return buyUser;
    }

    public void setBuyUser(AppUser buyUser) {
        this.buyUser = buyUser;
    }

    public ShopSubOrder getSubOrder() {
        return subOrder;
    }

    public void setSubOrder(ShopSubOrder subOrder) {
        this.subOrder = subOrder;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }


    public long getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(long anchorId) {
        this.anchorId = anchorId;
    }

    public String getLogisticsNum() {
        return logisticsNum;
    }

    public void setLogisticsNum(String logisticsNum) {
        this.logisticsNum = logisticsNum;
    }

    public String getRefundLogisticsNum() {
        return refundLogisticsNum;
    }

    public void setRefundLogisticsNum(String refundLogisticsNum) {
        this.refundLogisticsNum = refundLogisticsNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.businessOrder, flags);
        dest.writeParcelable(this.buyUser, flags);
        dest.writeParcelable(this.subOrder, flags);
        dest.writeInt(this.goodsNum);
        dest.writeDouble(this.total);
        dest.writeInt(this.viewType);
        dest.writeLong(this.anchorId);
        dest.writeString(this.logisticsNum);
        dest.writeString(this.refundLogisticsNum);
    }

    public ShopUserOrderBean() {
    }

    protected ShopUserOrderBean(Parcel in) {
        this.businessOrder = in.readParcelable(ShopBusinessOrder.class.getClassLoader());
        this.buyUser = in.readParcelable(AppUser.class.getClassLoader());
        this.subOrder = in.readParcelable(ShopSubOrder.class.getClassLoader());
        this.goodsNum = in.readInt();
        this.total = in.readDouble();
        this.viewType = in.readInt();
        this.anchorId = in.readLong();
        this.logisticsNum = in.readString();
        this.refundLogisticsNum = in.readString();
    }

    public static final Parcelable.Creator<ShopUserOrderBean> CREATOR = new Parcelable.Creator<ShopUserOrderBean>() {
        @Override
        public ShopUserOrderBean createFromParcel(Parcel source) {
            return new ShopUserOrderBean(source);
        }

        @Override
        public ShopUserOrderBean[] newArray(int size) {
            return new ShopUserOrderBean[size];
        }
    };
}
