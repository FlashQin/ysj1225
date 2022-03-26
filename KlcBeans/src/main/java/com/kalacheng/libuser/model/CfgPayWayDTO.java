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
public class CfgPayWayDTO  implements Parcelable
{
 public CfgPayWayDTO()
{
}

/**
 * 支付渠道名称
 */
public String name;

/**
 * 支付渠道LOGO
 */
public String logo;

/**
 * 支付渠道类型 1：支付宝  2：微信 3:ios内购, 4金币
 */
public int payChannel;

   public CfgPayWayDTO(Parcel in) 
{
name=in.readString();
logo=in.readString();
payChannel=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(name);
dest.writeString(logo);
dest.writeInt(payChannel);

}

  public static void cloneObj(CfgPayWayDTO source,CfgPayWayDTO target)
{

target.name=source.name;

target.logo=source.logo;

target.payChannel=source.payChannel;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CfgPayWayDTO> CREATOR = new Creator<CfgPayWayDTO>() {
        @Override
        public CfgPayWayDTO createFromParcel(Parcel in) {
            return new CfgPayWayDTO(in);
        }

        @Override
        public CfgPayWayDTO[] newArray(int size) {
            return new CfgPayWayDTO[size];
        }
    };
}
