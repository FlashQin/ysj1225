package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * APP签到页面数据
 */
public class ApiSignInDto  implements Parcelable
{
 public ApiSignInDto()
{
}

/**
 * 签到列表
 */
public List<com.kalacheng.libuser.model.ApiSignIn> signList;

/**
 * 连续签到天数
 */
public int signDay;

/**
 * 今日是否签到 0:未签到 1:已签到
 */
public int isSign;

   public ApiSignInDto(Parcel in) 
{

if(signList==null){
signList=  new ArrayList<>();
 }
in.readTypedList(signList,com.kalacheng.libuser.model.ApiSignIn.CREATOR);
signDay=in.readInt();
isSign=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(signList);
dest.writeInt(signDay);
dest.writeInt(isSign);

}

  public static void cloneObj(ApiSignInDto source,ApiSignInDto target)
{

        if(source.signList==null)
        {
            target.signList=null;
        }else
        {
            target.signList=new ArrayList();
            for(int i=0;i<source.signList.size();i++)
            {
            ApiSignIn.cloneObj(source.signList.get(i),target.signList.get(i));
            }
        }


target.signDay=source.signDay;

target.isSign=source.isSign;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiSignInDto> CREATOR = new Creator<ApiSignInDto>() {
        @Override
        public ApiSignInDto createFromParcel(Parcel in) {
            return new ApiSignInDto(in);
        }

        @Override
        public ApiSignInDto[] newArray(int size) {
            return new ApiSignInDto[size];
        }
    };
}
