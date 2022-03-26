package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 贵族中心
 */
public class AppNobleCenterDTO  implements Parcelable
{
 public AppNobleCenterDTO()
{
}

/**
 * 贵族名称集合
 */
public List<com.kalacheng.libuser.model.NobleCenterNobleGradeDTO> gradeList;

/**
 * 到期天数
 */
public int endDay;

/**
 * 我的等级
 */
public int grade;

/**
 * 我图像
 */
public String avatar;

/**
 * 我的贵族等级图片
 */
public String nobleGradeImg;

/**
 * 贵族对应的特权集合
 */
public List<com.kalacheng.libuser.model.AppGradePrivilegeDTO> noblePricelegeList;

   public AppNobleCenterDTO(Parcel in) 
{

if(gradeList==null){
gradeList=  new ArrayList<>();
 }
in.readTypedList(gradeList,com.kalacheng.libuser.model.NobleCenterNobleGradeDTO.CREATOR);
endDay=in.readInt();
grade=in.readInt();
avatar=in.readString();
nobleGradeImg=in.readString();

if(noblePricelegeList==null){
noblePricelegeList=  new ArrayList<>();
 }
in.readTypedList(noblePricelegeList,com.kalacheng.libuser.model.AppGradePrivilegeDTO.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(gradeList);
dest.writeInt(endDay);
dest.writeInt(grade);
dest.writeString(avatar);
dest.writeString(nobleGradeImg);

dest.writeTypedList(noblePricelegeList);

}

  public static void cloneObj(AppNobleCenterDTO source,AppNobleCenterDTO target)
{

        if(source.gradeList==null)
        {
            target.gradeList=null;
        }else
        {
            target.gradeList=new ArrayList();
            for(int i=0;i<source.gradeList.size();i++)
            {
            NobleCenterNobleGradeDTO.cloneObj(source.gradeList.get(i),target.gradeList.get(i));
            }
        }


target.endDay=source.endDay;

target.grade=source.grade;

target.avatar=source.avatar;

target.nobleGradeImg=source.nobleGradeImg;

        if(source.noblePricelegeList==null)
        {
            target.noblePricelegeList=null;
        }else
        {
            target.noblePricelegeList=new ArrayList();
            for(int i=0;i<source.noblePricelegeList.size();i++)
            {
            AppGradePrivilegeDTO.cloneObj(source.noblePricelegeList.get(i),target.noblePricelegeList.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppNobleCenterDTO> CREATOR = new Creator<AppNobleCenterDTO>() {
        @Override
        public AppNobleCenterDTO createFromParcel(Parcel in) {
            return new AppNobleCenterDTO(in);
        }

        @Override
        public AppNobleCenterDTO[] newArray(int size) {
            return new AppNobleCenterDTO[size];
        }
    };
}
