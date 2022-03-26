package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * APP守护类目
 */
public class ApiGuardEntity  implements Parcelable
{
 public ApiGuardEntity()
{
}

/**
 * 守护类目
 */
public List<com.kalacheng.libuser.model.ApiGuard> apiGuardList;

/**
 * 守护天数
 */
public long dayNumber;

/**
 * 用户总金额
 */
public int totalCoin;

   public ApiGuardEntity(Parcel in) 
{

if(apiGuardList==null){
apiGuardList=  new ArrayList<>();
 }
in.readTypedList(apiGuardList,com.kalacheng.libuser.model.ApiGuard.CREATOR);
dayNumber=in.readLong();
totalCoin=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(apiGuardList);
dest.writeLong(dayNumber);
dest.writeInt(totalCoin);

}

  public static void cloneObj(ApiGuardEntity source,ApiGuardEntity target)
{

        if(source.apiGuardList==null)
        {
            target.apiGuardList=null;
        }else
        {
            target.apiGuardList=new ArrayList();
            for(int i=0;i<source.apiGuardList.size();i++)
            {
            ApiGuard.cloneObj(source.apiGuardList.get(i),target.apiGuardList.get(i));
            }
        }


target.dayNumber=source.dayNumber;

target.totalCoin=source.totalCoin;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiGuardEntity> CREATOR = new Creator<ApiGuardEntity>() {
        @Override
        public ApiGuardEntity createFromParcel(Parcel in) {
            return new ApiGuardEntity(in);
        }

        @Override
        public ApiGuardEntity[] newArray(int size) {
            return new ApiGuardEntity[size];
        }
    };
}
