package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 等级特权
 */
public class AppUserPrivilegeDTO  implements Parcelable
{
 public AppUserPrivilegeDTO()
{
}

/**
 * 头像
 */
public String avatarThumb;

/**
 * 用户/财富/主播 快速提升
 */
public List<com.kalacheng.libuser.model.AppImproveQuicklyDTO> aiqList;

/**
 * 下一级所需经验
 */
public int nextNeedPoint;

/**
 * 等级
 */
public int grade;

/**
 * 下一级经验值
 */
public int nextStartVal;

/**
 * 用户/财富 勋章
 */
public List<com.kalacheng.libuser.model.AppMedalDTO> amDTOList;

/**
 * 当前财富经验
 */
public double moneyPoint;

/**
 * 下一级所需消费额
 */
public double nextNeedMoney;

/**
 * 当前经验值
 */
public int point;

/**
 * 用户/财富/主播 特权
 */
public List<com.kalacheng.libuser.model.AppGradePrivilegeDTO> agpDTOList;

   public AppUserPrivilegeDTO(Parcel in) 
{
avatarThumb=in.readString();

if(aiqList==null){
aiqList=  new ArrayList<>();
 }
in.readTypedList(aiqList,com.kalacheng.libuser.model.AppImproveQuicklyDTO.CREATOR);
nextNeedPoint=in.readInt();
grade=in.readInt();
nextStartVal=in.readInt();

if(amDTOList==null){
amDTOList=  new ArrayList<>();
 }
in.readTypedList(amDTOList,com.kalacheng.libuser.model.AppMedalDTO.CREATOR);
moneyPoint=in.readDouble();
nextNeedMoney=in.readDouble();
point=in.readInt();

if(agpDTOList==null){
agpDTOList=  new ArrayList<>();
 }
in.readTypedList(agpDTOList,com.kalacheng.libuser.model.AppGradePrivilegeDTO.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(avatarThumb);

dest.writeTypedList(aiqList);
dest.writeInt(nextNeedPoint);
dest.writeInt(grade);
dest.writeInt(nextStartVal);

dest.writeTypedList(amDTOList);
dest.writeDouble(moneyPoint);
dest.writeDouble(nextNeedMoney);
dest.writeInt(point);

dest.writeTypedList(agpDTOList);

}

  public static void cloneObj(AppUserPrivilegeDTO source,AppUserPrivilegeDTO target)
{

target.avatarThumb=source.avatarThumb;

        if(source.aiqList==null)
        {
            target.aiqList=null;
        }else
        {
            target.aiqList=new ArrayList();
            for(int i=0;i<source.aiqList.size();i++)
            {
            AppImproveQuicklyDTO.cloneObj(source.aiqList.get(i),target.aiqList.get(i));
            }
        }


target.nextNeedPoint=source.nextNeedPoint;

target.grade=source.grade;

target.nextStartVal=source.nextStartVal;

        if(source.amDTOList==null)
        {
            target.amDTOList=null;
        }else
        {
            target.amDTOList=new ArrayList();
            for(int i=0;i<source.amDTOList.size();i++)
            {
            AppMedalDTO.cloneObj(source.amDTOList.get(i),target.amDTOList.get(i));
            }
        }


target.moneyPoint=source.moneyPoint;

target.nextNeedMoney=source.nextNeedMoney;

target.point=source.point;

        if(source.agpDTOList==null)
        {
            target.agpDTOList=null;
        }else
        {
            target.agpDTOList=new ArrayList();
            for(int i=0;i<source.agpDTOList.size();i++)
            {
            AppGradePrivilegeDTO.cloneObj(source.agpDTOList.get(i),target.agpDTOList.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppUserPrivilegeDTO> CREATOR = new Creator<AppUserPrivilegeDTO>() {
        @Override
        public AppUserPrivilegeDTO createFromParcel(Parcel in) {
            return new AppUserPrivilegeDTO(in);
        }

        @Override
        public AppUserPrivilegeDTO[] newArray(int size) {
            return new AppUserPrivilegeDTO[size];
        }
    };
}
