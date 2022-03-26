package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.buscommon.model.*;

import com.kalacheng.libuser.model.*;




/**
 * 搜索条件
 */
public class SearchConditionDto  implements Parcelable
{
 public SearchConditionDto()
{
}

/**
 * 热门主播
 */
public List<com.kalacheng.buscommon.model.ApiUserInfo> hotAnchorList;

/**
 * 直播标签
 */
public List<com.kalacheng.libuser.model.AppLiveTag> liveTagList;

/**
 * 全部用户标签信息
 */
public List<com.kalacheng.libuser.model.AppTabInfo> allTabInfoList;

/**
 * 性别选择
 */
public List<com.kalacheng.libuser.model.KeyValueDto> sexs;

/**
 * 浏览主播
 */
public List<com.kalacheng.libuser.model.AppUser> browseAnchorList;

/**
 * 热门城市
 */
public List<com.kalacheng.libuser.model.AppArea> hotCitys;

/**
 * 历史搜索记录
 */
public List<com.kalacheng.libuser.model.AppSearchRecord> historyRecords;

   public SearchConditionDto(Parcel in) 
{

if(hotAnchorList==null){
hotAnchorList=  new ArrayList<>();
 }
in.readTypedList(hotAnchorList,com.kalacheng.buscommon.model.ApiUserInfo.CREATOR);

if(liveTagList==null){
liveTagList=  new ArrayList<>();
 }
in.readTypedList(liveTagList,com.kalacheng.libuser.model.AppLiveTag.CREATOR);

if(allTabInfoList==null){
allTabInfoList=  new ArrayList<>();
 }
in.readTypedList(allTabInfoList,com.kalacheng.libuser.model.AppTabInfo.CREATOR);

if(sexs==null){
sexs=  new ArrayList<>();
 }
in.readTypedList(sexs,com.kalacheng.libuser.model.KeyValueDto.CREATOR);

if(browseAnchorList==null){
browseAnchorList=  new ArrayList<>();
 }
in.readTypedList(browseAnchorList,com.kalacheng.libuser.model.AppUser.CREATOR);

if(hotCitys==null){
hotCitys=  new ArrayList<>();
 }
in.readTypedList(hotCitys,com.kalacheng.libuser.model.AppArea.CREATOR);

if(historyRecords==null){
historyRecords=  new ArrayList<>();
 }
in.readTypedList(historyRecords,com.kalacheng.libuser.model.AppSearchRecord.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(hotAnchorList);

dest.writeTypedList(liveTagList);

dest.writeTypedList(allTabInfoList);

dest.writeTypedList(sexs);

dest.writeTypedList(browseAnchorList);

dest.writeTypedList(hotCitys);

dest.writeTypedList(historyRecords);

}

  public static void cloneObj(SearchConditionDto source,SearchConditionDto target)
{

        if(source.hotAnchorList==null)
        {
            target.hotAnchorList=null;
        }else
        {
            target.hotAnchorList=new ArrayList();
            for(int i=0;i<source.hotAnchorList.size();i++)
            {
            ApiUserInfo.cloneObj(source.hotAnchorList.get(i),target.hotAnchorList.get(i));
            }
        }


        if(source.liveTagList==null)
        {
            target.liveTagList=null;
        }else
        {
            target.liveTagList=new ArrayList();
            for(int i=0;i<source.liveTagList.size();i++)
            {
            AppLiveTag.cloneObj(source.liveTagList.get(i),target.liveTagList.get(i));
            }
        }


        if(source.allTabInfoList==null)
        {
            target.allTabInfoList=null;
        }else
        {
            target.allTabInfoList=new ArrayList();
            for(int i=0;i<source.allTabInfoList.size();i++)
            {
            AppTabInfo.cloneObj(source.allTabInfoList.get(i),target.allTabInfoList.get(i));
            }
        }


        if(source.sexs==null)
        {
            target.sexs=null;
        }else
        {
            target.sexs=new ArrayList();
            for(int i=0;i<source.sexs.size();i++)
            {
            KeyValueDto.cloneObj(source.sexs.get(i),target.sexs.get(i));
            }
        }


        if(source.browseAnchorList==null)
        {
            target.browseAnchorList=null;
        }else
        {
            target.browseAnchorList=new ArrayList();
            for(int i=0;i<source.browseAnchorList.size();i++)
            {
            AppUser.cloneObj(source.browseAnchorList.get(i),target.browseAnchorList.get(i));
            }
        }


        if(source.hotCitys==null)
        {
            target.hotCitys=null;
        }else
        {
            target.hotCitys=new ArrayList();
            for(int i=0;i<source.hotCitys.size();i++)
            {
            AppArea.cloneObj(source.hotCitys.get(i),target.hotCitys.get(i));
            }
        }


        if(source.historyRecords==null)
        {
            target.historyRecords=null;
        }else
        {
            target.historyRecords=new ArrayList();
            for(int i=0;i<source.historyRecords.size();i++)
            {
            AppSearchRecord.cloneObj(source.historyRecords.get(i),target.historyRecords.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchConditionDto> CREATOR = new Creator<SearchConditionDto>() {
        @Override
        public SearchConditionDto createFromParcel(Parcel in) {
            return new SearchConditionDto(in);
        }

        @Override
        public SearchConditionDto[] newArray(int size) {
            return new SearchConditionDto[size];
        }
    };
}
