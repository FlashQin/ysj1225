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
public class ApiDiscover  implements Parcelable
{
 public ApiDiscover()
{
}

/**
 * 热门主播
 */
public List<com.kalacheng.libuser.model.AppUser> anchor;

/**
 * 热门话题
 */
public List<com.kalacheng.libuser.model.AppVideoTopic> hotTopics;

   public ApiDiscover(Parcel in) 
{

if(anchor==null){
anchor=  new ArrayList<>();
 }
in.readTypedList(anchor,com.kalacheng.libuser.model.AppUser.CREATOR);

if(hotTopics==null){
hotTopics=  new ArrayList<>();
 }
in.readTypedList(hotTopics,com.kalacheng.libuser.model.AppVideoTopic.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(anchor);

dest.writeTypedList(hotTopics);

}

  public static void cloneObj(ApiDiscover source,ApiDiscover target)
{

        if(source.anchor==null)
        {
            target.anchor=null;
        }else
        {
            target.anchor=new ArrayList();
            for(int i=0;i<source.anchor.size();i++)
            {
            AppUser.cloneObj(source.anchor.get(i),target.anchor.get(i));
            }
        }


        if(source.hotTopics==null)
        {
            target.hotTopics=null;
        }else
        {
            target.hotTopics=new ArrayList();
            for(int i=0;i<source.hotTopics.size();i++)
            {
            AppVideoTopic.cloneObj(source.hotTopics.get(i),target.hotTopics.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiDiscover> CREATOR = new Creator<ApiDiscover>() {
        @Override
        public ApiDiscover createFromParcel(Parcel in) {
            return new ApiDiscover(in);
        }

        @Override
        public ApiDiscover[] newArray(int size) {
            return new ApiDiscover[size];
        }
    };
}
