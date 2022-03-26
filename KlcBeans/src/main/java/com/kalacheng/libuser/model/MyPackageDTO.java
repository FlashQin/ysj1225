package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 我的背包
 */
public class MyPackageDTO  implements Parcelable
{
 public MyPackageDTO()
{
}

/**
 * 靓号集合
 */
public List<com.kalacheng.libuser.model.AppLiang> userLiangList;

/**
 * 坐骑集合
 */
public List<com.kalacheng.libuser.model.AppUsersCar> userCarList;

/**
 * 礼物集合
 */
public List<com.kalacheng.libuser.model.NobLiveGift> myGiftList;

   public MyPackageDTO(Parcel in) 
{

if(userLiangList==null){
userLiangList=  new ArrayList<>();
 }
in.readTypedList(userLiangList,com.kalacheng.libuser.model.AppLiang.CREATOR);

if(userCarList==null){
userCarList=  new ArrayList<>();
 }
in.readTypedList(userCarList,com.kalacheng.libuser.model.AppUsersCar.CREATOR);

if(myGiftList==null){
myGiftList=  new ArrayList<>();
 }
in.readTypedList(myGiftList,com.kalacheng.libuser.model.NobLiveGift.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(userLiangList);

dest.writeTypedList(userCarList);

dest.writeTypedList(myGiftList);

}

  public static void cloneObj(MyPackageDTO source,MyPackageDTO target)
{

        if(source.userLiangList==null)
        {
            target.userLiangList=null;
        }else
        {
            target.userLiangList=new ArrayList();
            for(int i=0;i<source.userLiangList.size();i++)
            {
            AppLiang.cloneObj(source.userLiangList.get(i),target.userLiangList.get(i));
            }
        }


        if(source.userCarList==null)
        {
            target.userCarList=null;
        }else
        {
            target.userCarList=new ArrayList();
            for(int i=0;i<source.userCarList.size();i++)
            {
            AppUsersCar.cloneObj(source.userCarList.get(i),target.userCarList.get(i));
            }
        }


        if(source.myGiftList==null)
        {
            target.myGiftList=null;
        }else
        {
            target.myGiftList=new ArrayList();
            for(int i=0;i<source.myGiftList.size();i++)
            {
            NobLiveGift.cloneObj(source.myGiftList.get(i),target.myGiftList.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyPackageDTO> CREATOR = new Creator<MyPackageDTO>() {
        @Override
        public MyPackageDTO createFromParcel(Parcel in) {
            return new MyPackageDTO(in);
        }

        @Override
        public MyPackageDTO[] newArray(int size) {
            return new MyPackageDTO[size];
        }
    };
}
