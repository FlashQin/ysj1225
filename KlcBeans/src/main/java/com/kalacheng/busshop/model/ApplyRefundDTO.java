package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busshop.model.*;




/**
 * 申请退款model
 */
public class ApplyRefundDTO  implements Parcelable
{
 public ApplyRefundDTO()
{
}

/**
 * 退款总金额
 */
public double amount;

/**
 * 退款原因集合
 */
public List<com.kalacheng.busshop.model.ApplyRefundReasonDTO> reasonList;

   public ApplyRefundDTO(Parcel in) 
{
amount=in.readDouble();

if(reasonList==null){
reasonList=  new ArrayList<>();
 }
in.readTypedList(reasonList,com.kalacheng.busshop.model.ApplyRefundReasonDTO.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeDouble(amount);

dest.writeTypedList(reasonList);

}

  public static void cloneObj(ApplyRefundDTO source,ApplyRefundDTO target)
{

target.amount=source.amount;

        if(source.reasonList==null)
        {
            target.reasonList=null;
        }else
        {
            target.reasonList=new ArrayList();
            for(int i=0;i<source.reasonList.size();i++)
            {
            ApplyRefundReasonDTO.cloneObj(source.reasonList.get(i),target.reasonList.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApplyRefundDTO> CREATOR = new Creator<ApplyRefundDTO>() {
        @Override
        public ApplyRefundDTO createFromParcel(Parcel in) {
            return new ApplyRefundDTO(in);
        }

        @Override
        public ApplyRefundDTO[] newArray(int size) {
            return new ApplyRefundDTO[size];
        }
    };
}
