package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;

import com.kalacheng.buscommon.*;




/**
 * 首页数据
 */
public class HomeDto  implements Parcelable
{
 public HomeDto()
{
}

/**
 * 业务类型值TAG
 */
public List<com.kalacheng.libuser.model.KeyValueDto> headerTypes;

/**
 * 热门主播(语音)
 */
public List<com.kalacheng.buscommon.AppHomeHallDTO> hotAnchors;

/**
 * 直播频道
 */
public List<com.kalacheng.libuser.model.AppLiveChannel> liveChannels;

/**
 * 视频分类(视频)
 */
public List<com.kalacheng.libuser.model.AppHotSort> hotSorts;

   public HomeDto(Parcel in) 
{

if(headerTypes==null){
headerTypes=  new ArrayList<>();
 }
in.readTypedList(headerTypes,com.kalacheng.libuser.model.KeyValueDto.CREATOR);

if(hotAnchors==null){
hotAnchors=  new ArrayList<>();
 }
in.readTypedList(hotAnchors,com.kalacheng.buscommon.AppHomeHallDTO.CREATOR);

if(liveChannels==null){
liveChannels=  new ArrayList<>();
 }
in.readTypedList(liveChannels,com.kalacheng.libuser.model.AppLiveChannel.CREATOR);

if(hotSorts==null){
hotSorts=  new ArrayList<>();
 }
in.readTypedList(hotSorts,com.kalacheng.libuser.model.AppHotSort.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(headerTypes);

dest.writeTypedList(hotAnchors);

dest.writeTypedList(liveChannels);

dest.writeTypedList(hotSorts);

}

  public static void cloneObj(HomeDto source,HomeDto target)
{

        if(source.headerTypes==null)
        {
            target.headerTypes=null;
        }else
        {
            target.headerTypes=new ArrayList();
            for(int i=0;i<source.headerTypes.size();i++)
            {
            KeyValueDto.cloneObj(source.headerTypes.get(i),target.headerTypes.get(i));
            }
        }


        if(source.hotAnchors==null)
        {
            target.hotAnchors=null;
        }else
        {
            target.hotAnchors=new ArrayList();
            for(int i=0;i<source.hotAnchors.size();i++)
            {
            AppHomeHallDTO.cloneObj(source.hotAnchors.get(i),target.hotAnchors.get(i));
            }
        }


        if(source.liveChannels==null)
        {
            target.liveChannels=null;
        }else
        {
            target.liveChannels=new ArrayList();
            for(int i=0;i<source.liveChannels.size();i++)
            {
            AppLiveChannel.cloneObj(source.liveChannels.get(i),target.liveChannels.get(i));
            }
        }


        if(source.hotSorts==null)
        {
            target.hotSorts=null;
        }else
        {
            target.hotSorts=new ArrayList();
            for(int i=0;i<source.hotSorts.size();i++)
            {
            AppHotSort.cloneObj(source.hotSorts.get(i),target.hotSorts.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HomeDto> CREATOR = new Creator<HomeDto>() {
        @Override
        public HomeDto createFromParcel(Parcel in) {
            return new HomeDto(in);
        }

        @Override
        public HomeDto[] newArray(int size) {
            return new HomeDto[size];
        }
    };
}
