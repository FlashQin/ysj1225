package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busshop.model.*;




/**
 * 快递信息model
 */
public class ApiShopLogisticsDTO  implements Parcelable
{
 public ApiShopLogisticsDTO()
{
}

/**
 * 快递单号
 */
public String number;

/**
 * 快递名称
 */
public String expName;

/**
 * 快递物流信息
 */
public List<com.kalacheng.busshop.model.LogisticsNodeDTO> nodeList;

   public ApiShopLogisticsDTO(Parcel in) 
{
number=in.readString();
expName=in.readString();

if(nodeList==null){
nodeList=  new ArrayList<>();
 }
in.readTypedList(nodeList,com.kalacheng.busshop.model.LogisticsNodeDTO.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(number);
dest.writeString(expName);

dest.writeTypedList(nodeList);

}

  public static void cloneObj(ApiShopLogisticsDTO source,ApiShopLogisticsDTO target)
{

target.number=source.number;

target.expName=source.expName;

        if(source.nodeList==null)
        {
            target.nodeList=null;
        }else
        {
            target.nodeList=new ArrayList();
            for(int i=0;i<source.nodeList.size();i++)
            {
            LogisticsNodeDTO.cloneObj(source.nodeList.get(i),target.nodeList.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiShopLogisticsDTO> CREATOR = new Creator<ApiShopLogisticsDTO>() {
        @Override
        public ApiShopLogisticsDTO createFromParcel(Parcel in) {
            return new ApiShopLogisticsDTO(in);
        }

        @Override
        public ApiShopLogisticsDTO[] newArray(int size) {
            return new ApiShopLogisticsDTO[size];
        }
    };
}
