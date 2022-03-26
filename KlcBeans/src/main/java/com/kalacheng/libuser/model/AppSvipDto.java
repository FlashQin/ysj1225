package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * svip各套餐明细
 */
public class AppSvipDto  implements Parcelable
{
 public AppSvipDto()
{
}

/**
 * svip服务套餐明细
 */
public List<com.kalacheng.libuser.model.AppSvipPackage> svipPackages;

/**
 * 服务名称
 */
public String name;

/**
 * 到期天数
 */
public int leftDays;

/**
 * 是否开启 默认开启 1开启 0未开启
 */
public int isTip;

/**
 * 宣传图片
 */
public String picture;

/**
 * svip id目前只有一种，默认为1
 */
public long sid;

   public AppSvipDto(Parcel in) 
{

if(svipPackages==null){
svipPackages=  new ArrayList<>();
 }
in.readTypedList(svipPackages,com.kalacheng.libuser.model.AppSvipPackage.CREATOR);
name=in.readString();
leftDays=in.readInt();
isTip=in.readInt();
picture=in.readString();
sid=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(svipPackages);
dest.writeString(name);
dest.writeInt(leftDays);
dest.writeInt(isTip);
dest.writeString(picture);
dest.writeLong(sid);

}

  public static void cloneObj(AppSvipDto source,AppSvipDto target)
{

        if(source.svipPackages==null)
        {
            target.svipPackages=null;
        }else
        {
            target.svipPackages=new ArrayList();
            for(int i=0;i<source.svipPackages.size();i++)
            {
            AppSvipPackage.cloneObj(source.svipPackages.get(i),target.svipPackages.get(i));
            }
        }


target.name=source.name;

target.leftDays=source.leftDays;

target.isTip=source.isTip;

target.picture=source.picture;

target.sid=source.sid;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppSvipDto> CREATOR = new Creator<AppSvipDto>() {
        @Override
        public AppSvipDto createFromParcel(Parcel in) {
            return new AppSvipDto(in);
        }

        @Override
        public AppSvipDto[] newArray(int size) {
            return new AppSvipDto[size];
        }
    };
}
