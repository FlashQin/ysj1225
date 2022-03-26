package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 在线商城
 */
public class OnlineMallDTO  implements Parcelable
{
 public OnlineMallDTO()
{
}

/**
 * 图像
 */
public String avatarThumb;

/**
 * null
 */
public List<com.kalacheng.libuser.model.AppLiang> liangList;

/**
 * null
 */
public List<com.kalacheng.libuser.model.AppVip> vipList;

/**
 * 商城内科学计数转换
 */
public String coinShow;

/**
 * 币种名称：钻石
 */
public String vcUnit;

/**
 * null
 */
public com.kalacheng.libuser.model.AppUsersVip userVip;

/**
 * 用户类型，1:admin ;2:会员
 */
public int vipType;

/**
 * null
 */
public List<com.kalacheng.libuser.model.AppCar> carList;

/**
 * 名称
 */
public String username;

   public OnlineMallDTO(Parcel in) 
{
avatarThumb=in.readString();

if(liangList==null){
liangList=  new ArrayList<>();
 }
in.readTypedList(liangList,com.kalacheng.libuser.model.AppLiang.CREATOR);

if(vipList==null){
vipList=  new ArrayList<>();
 }
in.readTypedList(vipList,com.kalacheng.libuser.model.AppVip.CREATOR);
coinShow=in.readString();
vcUnit=in.readString();

userVip=in.readParcelable(com.kalacheng.libuser.model.AppUsersVip.class.getClassLoader());
vipType=in.readInt();

if(carList==null){
carList=  new ArrayList<>();
 }
in.readTypedList(carList,com.kalacheng.libuser.model.AppCar.CREATOR);
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(avatarThumb);

dest.writeTypedList(liangList);

dest.writeTypedList(vipList);
dest.writeString(coinShow);
dest.writeString(vcUnit);

dest.writeParcelable(userVip,flags);
dest.writeInt(vipType);

dest.writeTypedList(carList);
dest.writeString(username);

}

  public static void cloneObj(OnlineMallDTO source,OnlineMallDTO target)
{

target.avatarThumb=source.avatarThumb;

        if(source.liangList==null)
        {
            target.liangList=null;
        }else
        {
            target.liangList=new ArrayList();
            for(int i=0;i<source.liangList.size();i++)
            {
            AppLiang.cloneObj(source.liangList.get(i),target.liangList.get(i));
            }
        }


        if(source.vipList==null)
        {
            target.vipList=null;
        }else
        {
            target.vipList=new ArrayList();
            for(int i=0;i<source.vipList.size();i++)
            {
            AppVip.cloneObj(source.vipList.get(i),target.vipList.get(i));
            }
        }


target.coinShow=source.coinShow;

target.vcUnit=source.vcUnit;
        if(source.userVip==null)
        {
            target.userVip=null;
        }else
        {
            AppUsersVip.cloneObj(source.userVip,target.userVip);
        }

target.vipType=source.vipType;

        if(source.carList==null)
        {
            target.carList=null;
        }else
        {
            target.carList=new ArrayList();
            for(int i=0;i<source.carList.size();i++)
            {
            AppCar.cloneObj(source.carList.get(i),target.carList.get(i));
            }
        }


target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OnlineMallDTO> CREATOR = new Creator<OnlineMallDTO>() {
        @Override
        public OnlineMallDTO createFromParcel(Parcel in) {
            return new OnlineMallDTO(in);
        }

        @Override
        public OnlineMallDTO[] newArray(int size) {
            return new OnlineMallDTO[size];
        }
    };
}
