package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 用户的短视频信息
 */
public class ApiMyShortVideo  implements Parcelable
{
 public ApiMyShortVideo()
{
}

/**
 * 购买
 */
public List<com.kalacheng.libuser.model.ApiShortVideoDto> buyList;

/**
 * 喜欢
 */
public List<com.kalacheng.libuser.model.ApiShortVideoDto> likeList;

/**
 * 购买数量
 */
public int buyNumber;

/**
 * 作品
 */
public List<com.kalacheng.libuser.model.ApiShortVideoDto> myList;

/**
 * 作品数量
 */
public int myNumber;

/**
 * 喜欢数量
 */
public int likeNumber;

   public ApiMyShortVideo(Parcel in) 
{

if(buyList==null){
buyList=  new ArrayList<>();
 }
in.readTypedList(buyList,com.kalacheng.libuser.model.ApiShortVideoDto.CREATOR);

if(likeList==null){
likeList=  new ArrayList<>();
 }
in.readTypedList(likeList,com.kalacheng.libuser.model.ApiShortVideoDto.CREATOR);
buyNumber=in.readInt();

if(myList==null){
myList=  new ArrayList<>();
 }
in.readTypedList(myList,com.kalacheng.libuser.model.ApiShortVideoDto.CREATOR);
myNumber=in.readInt();
likeNumber=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(buyList);

dest.writeTypedList(likeList);
dest.writeInt(buyNumber);

dest.writeTypedList(myList);
dest.writeInt(myNumber);
dest.writeInt(likeNumber);

}

  public static void cloneObj(ApiMyShortVideo source,ApiMyShortVideo target)
{

        if(source.buyList==null)
        {
            target.buyList=null;
        }else
        {
            target.buyList=new ArrayList();
            for(int i=0;i<source.buyList.size();i++)
            {
            ApiShortVideoDto.cloneObj(source.buyList.get(i),target.buyList.get(i));
            }
        }


        if(source.likeList==null)
        {
            target.likeList=null;
        }else
        {
            target.likeList=new ArrayList();
            for(int i=0;i<source.likeList.size();i++)
            {
            ApiShortVideoDto.cloneObj(source.likeList.get(i),target.likeList.get(i));
            }
        }


target.buyNumber=source.buyNumber;

        if(source.myList==null)
        {
            target.myList=null;
        }else
        {
            target.myList=new ArrayList();
            for(int i=0;i<source.myList.size();i++)
            {
            ApiShortVideoDto.cloneObj(source.myList.get(i),target.myList.get(i));
            }
        }


target.myNumber=source.myNumber;

target.likeNumber=source.likeNumber;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiMyShortVideo> CREATOR = new Creator<ApiMyShortVideo>() {
        @Override
        public ApiMyShortVideo createFromParcel(Parcel in) {
            return new ApiMyShortVideo(in);
        }

        @Override
        public ApiMyShortVideo[] newArray(int size) {
            return new ApiMyShortVideo[size];
        }
    };
}
