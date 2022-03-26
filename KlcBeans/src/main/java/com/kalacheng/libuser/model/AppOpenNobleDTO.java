package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 开通贵族支付方式和价格
 */
public class AppOpenNobleDTO  implements Parcelable
{
 public AppOpenNobleDTO()
{
}

/**
 * 支付方式集合
 */
public List<com.kalacheng.libuser.model.CfgPayWayDTO> payWayList;

/**
 * 货币类型 金币，喵币之类
 */
public String coinUnit;

/**
 * 价格集合
 */
public List<com.kalacheng.libuser.model.NobleCenterVipPriceDTO> priceList;

   public AppOpenNobleDTO(Parcel in) 
{

if(payWayList==null){
payWayList=  new ArrayList<>();
 }
in.readTypedList(payWayList,com.kalacheng.libuser.model.CfgPayWayDTO.CREATOR);
coinUnit=in.readString();

if(priceList==null){
priceList=  new ArrayList<>();
 }
in.readTypedList(priceList,com.kalacheng.libuser.model.NobleCenterVipPriceDTO.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(payWayList);
dest.writeString(coinUnit);

dest.writeTypedList(priceList);

}

  public static void cloneObj(AppOpenNobleDTO source,AppOpenNobleDTO target)
{

        if(source.payWayList==null)
        {
            target.payWayList=null;
        }else
        {
            target.payWayList=new ArrayList();
            for(int i=0;i<source.payWayList.size();i++)
            {
            CfgPayWayDTO.cloneObj(source.payWayList.get(i),target.payWayList.get(i));
            }
        }


target.coinUnit=source.coinUnit;

        if(source.priceList==null)
        {
            target.priceList=null;
        }else
        {
            target.priceList=new ArrayList();
            for(int i=0;i<source.priceList.size();i++)
            {
            NobleCenterVipPriceDTO.cloneObj(source.priceList.get(i),target.priceList.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppOpenNobleDTO> CREATOR = new Creator<AppOpenNobleDTO>() {
        @Override
        public AppOpenNobleDTO createFromParcel(Parcel in) {
            return new AppOpenNobleDTO(in);
        }

        @Override
        public AppOpenNobleDTO[] newArray(int size) {
            return new AppOpenNobleDTO[size];
        }
    };
}
