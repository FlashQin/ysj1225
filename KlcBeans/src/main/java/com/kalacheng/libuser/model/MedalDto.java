package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 勋章页数据
 */
public class MedalDto  implements Parcelable
{
 public MedalDto()
{
}

/**
 * 我没有的贵族勋章
 */
public List<com.kalacheng.libuser.model.AppMedal> noNobleMedals;

/**
 * 我没有的财富勋章
 */
public List<com.kalacheng.libuser.model.AppMedal> noWealthMedals;

/**
 * 我没有的用户勋章
 */
public List<com.kalacheng.libuser.model.AppMedal> noUserMedals;

/**
 * 我的全部勋章
 */
public List<com.kalacheng.libuser.model.AppMedal> myAllMedals;

   public MedalDto(Parcel in) 
{

if(noNobleMedals==null){
noNobleMedals=  new ArrayList<>();
 }
in.readTypedList(noNobleMedals,com.kalacheng.libuser.model.AppMedal.CREATOR);

if(noWealthMedals==null){
noWealthMedals=  new ArrayList<>();
 }
in.readTypedList(noWealthMedals,com.kalacheng.libuser.model.AppMedal.CREATOR);

if(noUserMedals==null){
noUserMedals=  new ArrayList<>();
 }
in.readTypedList(noUserMedals,com.kalacheng.libuser.model.AppMedal.CREATOR);

if(myAllMedals==null){
myAllMedals=  new ArrayList<>();
 }
in.readTypedList(myAllMedals,com.kalacheng.libuser.model.AppMedal.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(noNobleMedals);

dest.writeTypedList(noWealthMedals);

dest.writeTypedList(noUserMedals);

dest.writeTypedList(myAllMedals);

}

  public static void cloneObj(MedalDto source,MedalDto target)
{

        if(source.noNobleMedals==null)
        {
            target.noNobleMedals=null;
        }else
        {
            target.noNobleMedals=new ArrayList();
            for(int i=0;i<source.noNobleMedals.size();i++)
            {
            AppMedal.cloneObj(source.noNobleMedals.get(i),target.noNobleMedals.get(i));
            }
        }


        if(source.noWealthMedals==null)
        {
            target.noWealthMedals=null;
        }else
        {
            target.noWealthMedals=new ArrayList();
            for(int i=0;i<source.noWealthMedals.size();i++)
            {
            AppMedal.cloneObj(source.noWealthMedals.get(i),target.noWealthMedals.get(i));
            }
        }


        if(source.noUserMedals==null)
        {
            target.noUserMedals=null;
        }else
        {
            target.noUserMedals=new ArrayList();
            for(int i=0;i<source.noUserMedals.size();i++)
            {
            AppMedal.cloneObj(source.noUserMedals.get(i),target.noUserMedals.get(i));
            }
        }


        if(source.myAllMedals==null)
        {
            target.myAllMedals=null;
        }else
        {
            target.myAllMedals=new ArrayList();
            for(int i=0;i<source.myAllMedals.size();i++)
            {
            AppMedal.cloneObj(source.myAllMedals.get(i),target.myAllMedals.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MedalDto> CREATOR = new Creator<MedalDto>() {
        @Override
        public MedalDto createFromParcel(Parcel in) {
            return new MedalDto(in);
        }

        @Override
        public MedalDto[] newArray(int size) {
            return new MedalDto[size];
        }
    };
}
