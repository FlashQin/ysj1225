package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 购物车生成订单请求
 */
public class ShopCarAskDTO  implements Parcelable
{
 public ShopCarAskDTO()
{
}

/**
 * 商品id
 */
public long goodsId;

/**
 * 商品数量
 */
public int goodsNum;

/**
 * 商品skuId
 */
public long skuId;

/**
 * 购物车id
 */
public long carId;

   public ShopCarAskDTO(Parcel in) 
{
goodsId=in.readLong();
goodsNum=in.readInt();
skuId=in.readLong();
carId=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(goodsId);
dest.writeInt(goodsNum);
dest.writeLong(skuId);
dest.writeLong(carId);

}

  public static void cloneObj(ShopCarAskDTO source,ShopCarAskDTO target)
{

target.goodsId=source.goodsId;

target.goodsNum=source.goodsNum;

target.skuId=source.skuId;

target.carId=source.carId;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopCarAskDTO> CREATOR = new Creator<ShopCarAskDTO>() {
        @Override
        public ShopCarAskDTO createFromParcel(Parcel in) {
            return new ShopCarAskDTO(in);
        }

        @Override
        public ShopCarAskDTO[] newArray(int size) {
            return new ShopCarAskDTO[size];
        }
    };
}
