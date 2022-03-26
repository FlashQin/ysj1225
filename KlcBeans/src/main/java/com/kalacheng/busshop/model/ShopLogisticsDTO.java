package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busshop.model.*;




/**
 * 物流信息
 */
public class ShopLogisticsDTO  implements Parcelable
{
 public ShopLogisticsDTO()
{
}

/**
 * 物流信息
 */
public com.kalacheng.busshop.model.LogisticsBean logisticsBean;

/**
 * 可用物流列表
 */
public List<String> logisticsList;

   public ShopLogisticsDTO(Parcel in) 
{

logisticsBean=in.readParcelable(com.kalacheng.busshop.model.LogisticsBean.class.getClassLoader());

if(logisticsList==null){
logisticsList=  new ArrayList<>();
 }
in.readStringList(logisticsList);

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeParcelable(logisticsBean,flags);
dest.writeStringList(logisticsList);

}

  public static void cloneObj(ShopLogisticsDTO source,ShopLogisticsDTO target)
{
        if(source.logisticsBean==null)
        {
            target.logisticsBean=null;
        }else
        {
            LogisticsBean.cloneObj(source.logisticsBean,target.logisticsBean);
        }

        if(source.logisticsList==null)
        {
            target.logisticsList=null;
        }else
        {
            target.logisticsList=new ArrayList();
            for(int i=0;i<source.logisticsList.size();i++)
            {
                target.logisticsList.add(source.logisticsList.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopLogisticsDTO> CREATOR = new Creator<ShopLogisticsDTO>() {
        @Override
        public ShopLogisticsDTO createFromParcel(Parcel in) {
            return new ShopLogisticsDTO(in);
        }

        @Override
        public ShopLogisticsDTO[] newArray(int size) {
            return new ShopLogisticsDTO[size];
        }
    };
}
