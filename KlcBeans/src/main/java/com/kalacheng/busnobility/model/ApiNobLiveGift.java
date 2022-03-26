package com.kalacheng.busnobility.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 直播礼物列表响应
 */
public class ApiNobLiveGift  implements Parcelable
{
 public ApiNobLiveGift()
{
}

/**
 * 礼物列表
 */
public List<com.kalacheng.libuser.model.NobLiveGift> giftList;

/**
 * 礼物类型名称
 */
public String giftTypeName;

/**
 * 礼物类型id
 */
public int giftTypeId;

/**
 * 用户余额
 */
public double coin;

   public ApiNobLiveGift(Parcel in) 
{

if(giftList==null){
giftList=  new ArrayList<>();
 }
in.readTypedList(giftList,com.kalacheng.libuser.model.NobLiveGift.CREATOR);
giftTypeName=in.readString();
giftTypeId=in.readInt();
coin=in.readDouble();

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(giftList);
dest.writeString(giftTypeName);
dest.writeInt(giftTypeId);
dest.writeDouble(coin);

}

  public static void cloneObj(ApiNobLiveGift source,ApiNobLiveGift target)
{

        if(source.giftList==null)
        {
            target.giftList=null;
        }else
        {
            target.giftList=new ArrayList();
            for(int i=0;i<source.giftList.size();i++)
            {
            NobLiveGift.cloneObj(source.giftList.get(i),target.giftList.get(i));
            }
        }


target.giftTypeName=source.giftTypeName;

target.giftTypeId=source.giftTypeId;

target.coin=source.coin;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiNobLiveGift> CREATOR = new Creator<ApiNobLiveGift>() {
        @Override
        public ApiNobLiveGift createFromParcel(Parcel in) {
            return new ApiNobLiveGift(in);
        }

        @Override
        public ApiNobLiveGift[] newArray(int size) {
            return new ApiNobLiveGift[size];
        }
    };
}
