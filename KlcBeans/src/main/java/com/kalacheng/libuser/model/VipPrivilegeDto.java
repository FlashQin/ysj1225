package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 全站广播特权
 */
public class VipPrivilegeDto  implements Parcelable
{
 public VipPrivilegeDto()
{
}

/**
 * 贵族生日特权 0无特权1有特权
 */
public int birthday;

/**
 * 全站广播特权 0无特权1有特权
 */
public int broadCast;

/**
 * 贵族弹幕特权 0无特权1有特权
 */
public int nobleBarrage;

   public VipPrivilegeDto(Parcel in) 
{
birthday=in.readInt();
broadCast=in.readInt();
nobleBarrage=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(birthday);
dest.writeInt(broadCast);
dest.writeInt(nobleBarrage);

}

  public static void cloneObj(VipPrivilegeDto source,VipPrivilegeDto target)
{

target.birthday=source.birthday;

target.broadCast=source.broadCast;

target.nobleBarrage=source.nobleBarrage;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VipPrivilegeDto> CREATOR = new Creator<VipPrivilegeDto>() {
        @Override
        public VipPrivilegeDto createFromParcel(Parcel in) {
            return new VipPrivilegeDto(in);
        }

        @Override
        public VipPrivilegeDto[] newArray(int size) {
            return new VipPrivilegeDto[size];
        }
    };
}
