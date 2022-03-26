package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 支付配置
 */
public class PayConfigDto  implements Parcelable
{
 public PayConfigDto()
{
}

/**
 * 类型1原生支付,2:H5
 */
public int isPayType;

/**
 * 1:支付宝 2:微信
 */
public int id;

/**
 * 支付名称
 */
public String payName;

   public PayConfigDto(Parcel in) 
{
isPayType=in.readInt();
id=in.readInt();
payName=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(isPayType);
dest.writeInt(id);
dest.writeString(payName);

}

  public static void cloneObj(PayConfigDto source,PayConfigDto target)
{

target.isPayType=source.isPayType;

target.id=source.id;

target.payName=source.payName;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PayConfigDto> CREATOR = new Creator<PayConfigDto>() {
        @Override
        public PayConfigDto createFromParcel(Parcel in) {
            return new PayConfigDto(in);
        }

        @Override
        public PayConfigDto[] newArray(int size) {
            return new PayConfigDto[size];
        }
    };
}
