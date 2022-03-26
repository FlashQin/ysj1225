package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 发现页数据
 */
public class ApiShortVideo  implements Parcelable
{
 public ApiShortVideo()
{
}

/**
 * 本周必看
 */
public List<com.kalacheng.libuser.model.ApiShortVideoDto> weekList;

/**
 * 热门分类
 */
public List<com.kalacheng.libuser.model.AppHotSort> classifyList;

/**
 * 广告图
 */
public List<com.kalacheng.libuser.model.AppAds> adsList;

   public ApiShortVideo(Parcel in) 
{

if(weekList==null){
weekList=  new ArrayList<>();
 }
in.readTypedList(weekList,com.kalacheng.libuser.model.ApiShortVideoDto.CREATOR);

if(classifyList==null){
classifyList=  new ArrayList<>();
 }
in.readTypedList(classifyList,com.kalacheng.libuser.model.AppHotSort.CREATOR);

if(adsList==null){
adsList=  new ArrayList<>();
 }
in.readTypedList(adsList,com.kalacheng.libuser.model.AppAds.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(weekList);

dest.writeTypedList(classifyList);

dest.writeTypedList(adsList);

}

  public static void cloneObj(ApiShortVideo source,ApiShortVideo target)
{

        if(source.weekList==null)
        {
            target.weekList=null;
        }else
        {
            target.weekList=new ArrayList();
            for(int i=0;i<source.weekList.size();i++)
            {
            ApiShortVideoDto.cloneObj(source.weekList.get(i),target.weekList.get(i));
            }
        }


        if(source.classifyList==null)
        {
            target.classifyList=null;
        }else
        {
            target.classifyList=new ArrayList();
            for(int i=0;i<source.classifyList.size();i++)
            {
            AppHotSort.cloneObj(source.classifyList.get(i),target.classifyList.get(i));
            }
        }


        if(source.adsList==null)
        {
            target.adsList=null;
        }else
        {
            target.adsList=new ArrayList();
            for(int i=0;i<source.adsList.size();i++)
            {
            AppAds.cloneObj(source.adsList.get(i),target.adsList.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiShortVideo> CREATOR = new Creator<ApiShortVideo>() {
        @Override
        public ApiShortVideo createFromParcel(Parcel in) {
            return new ApiShortVideo(in);
        }

        @Override
        public ApiShortVideo[] newArray(int size) {
            return new ApiShortVideo[size];
        }
    };
}
