package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 坐骑详情
 */
public class MountDetailDTO  implements Parcelable
{
 public MountDetailDTO()
{
}

/**
 * 名称
 */
public String buyuname;

/**
 * 币种名称：钻石
 */
public String vcUnit;

/**
 * null
 */
public com.kalacheng.libuser.model.AppCar appCar;

/**
 * null
 */
public com.kalacheng.libuser.model.AppLiang liang;

   public MountDetailDTO(Parcel in) 
{
buyuname=in.readString();
vcUnit=in.readString();

appCar=in.readParcelable(com.kalacheng.libuser.model.AppCar.class.getClassLoader());

liang=in.readParcelable(com.kalacheng.libuser.model.AppLiang.class.getClassLoader());

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(buyuname);
dest.writeString(vcUnit);

dest.writeParcelable(appCar,flags);

dest.writeParcelable(liang,flags);

}

  public static void cloneObj(MountDetailDTO source,MountDetailDTO target)
{

target.buyuname=source.buyuname;

target.vcUnit=source.vcUnit;
        if(source.appCar==null)
        {
            target.appCar=null;
        }else
        {
            AppCar.cloneObj(source.appCar,target.appCar);
        }
        if(source.liang==null)
        {
            target.liang=null;
        }else
        {
            AppLiang.cloneObj(source.liang,target.liang);
        }

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MountDetailDTO> CREATOR = new Creator<MountDetailDTO>() {
        @Override
        public MountDetailDTO createFromParcel(Parcel in) {
            return new MountDetailDTO(in);
        }

        @Override
        public MountDetailDTO[] newArray(int size) {
            return new MountDetailDTO[size];
        }
    };
}
