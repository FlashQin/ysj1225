package com.kalacheng.shopping.buyer.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class ShopCarBean implements Parcelable {

    /**
     * 商家logo
     */
    public String businessLogo;

    /**
     * 商家名称
     */
    public String businessName;

    /**
     * 安卓使用
     */
    public int viewType;

    /**
     * 安卓使用1
     */
    public int checked;

    public int count;

    public double total;

    /**
     * sku组合名
     */
    public String skuName;

    /**
     * 用户id
     */
    public long uid;

    /**
     * 添加时间
     */
    public Date addTime;

    /**
     * 商品id
     */
    public long goodsId;

    /**
     * 商品价格(单价)
     */
    public double goodsPrice;

    /**
     * 商家id
     */
    public long businessId;

    /**
     * null
     */
    public long id;

    /**
     * skuId
     */
    public long composeId;

    /**
     * 商品名称
     */
    public String goodsName;

    /**
     * 商品数量
     */
    public int goodsNum;

    public String goodsPicture;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.businessLogo);
        dest.writeString(this.businessName);
        dest.writeInt(this.viewType);
        dest.writeInt(this.checked);
        dest.writeInt(this.count);
        dest.writeDouble(this.total);
        dest.writeString(this.skuName);
        dest.writeLong(this.uid);
        dest.writeLong(this.addTime != null ? this.addTime.getTime() : -1);
        dest.writeLong(this.goodsId);
        dest.writeDouble(this.goodsPrice);
        dest.writeLong(this.businessId);
        dest.writeLong(this.id);
        dest.writeLong(this.composeId);
        dest.writeString(this.goodsName);
        dest.writeInt(this.goodsNum);
        dest.writeString(this.goodsPicture);
    }

    public ShopCarBean() {
    }

    protected ShopCarBean(Parcel in) {
        this.businessLogo = in.readString();
        this.businessName = in.readString();
        this.viewType = in.readInt();
        this.checked = in.readInt();
        this.count = in.readInt();
        this.total = in.readDouble();
        this.skuName = in.readString();
        this.uid = in.readLong();
        long tmpAddTime = in.readLong();
        this.addTime = tmpAddTime == -1 ? null : new Date(tmpAddTime);
        this.goodsId = in.readLong();
        this.goodsPrice = in.readDouble();
        this.businessId = in.readLong();
        this.id = in.readLong();
        this.composeId = in.readLong();
        this.goodsName = in.readString();
        this.goodsNum = in.readInt();
        this.goodsPicture = in.readString();
    }

    public static final Parcelable.Creator<ShopCarBean> CREATOR = new Parcelable.Creator<ShopCarBean>() {
        @Override
        public ShopCarBean createFromParcel(Parcel source) {
            return new ShopCarBean(source);
        }

        @Override
        public ShopCarBean[] newArray(int size) {
            return new ShopCarBean[size];
        }
    };
}
