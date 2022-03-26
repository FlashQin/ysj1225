package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * APP守护列表
 */
public class GuardListEntity  implements Parcelable
{
 public GuardListEntity()
{
}

/**
 * 守护人数
 */
public int number;

/**
 * 时长类型，0天，1月，2年
 */
public int lengthType;

/**
 * 守护用户列表
 */
public List<com.kalacheng.libuser.model.ApiGuardEntityChild> getguardEntityChildList;

/**
 * 到期时间
 */
public String endTime;

   public GuardListEntity(Parcel in) 
{
number=in.readInt();
lengthType=in.readInt();

if(getguardEntityChildList==null){
getguardEntityChildList=  new ArrayList<>();
 }
in.readTypedList(getguardEntityChildList,com.kalacheng.libuser.model.ApiGuardEntityChild.CREATOR);
endTime=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(number);
dest.writeInt(lengthType);

dest.writeTypedList(getguardEntityChildList);
dest.writeString(endTime);

}

  public static void cloneObj(GuardListEntity source,GuardListEntity target)
{

target.number=source.number;

target.lengthType=source.lengthType;

        if(source.getguardEntityChildList==null)
        {
            target.getguardEntityChildList=null;
        }else
        {
            target.getguardEntityChildList=new ArrayList();
            for(int i=0;i<source.getguardEntityChildList.size();i++)
            {
            ApiGuardEntityChild.cloneObj(source.getguardEntityChildList.get(i),target.getguardEntityChildList.get(i));
            }
        }


target.endTime=source.endTime;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GuardListEntity> CREATOR = new Creator<GuardListEntity>() {
        @Override
        public GuardListEntity createFromParcel(Parcel in) {
            return new GuardListEntity(in);
        }

        @Override
        public GuardListEntity[] newArray(int size) {
            return new GuardListEntity[size];
        }
    };
}
