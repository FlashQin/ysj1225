package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * APP求聊页面数据
 */
public class ApiPushChatData  implements Parcelable
{
 public ApiPushChatData()
{
}

/**
 * 话费列表
 */
public List<com.kalacheng.libuser.model.AdminRushToTalkConfig> coinList;

/**
 * 筛选列表
 */
public List<com.kalacheng.libuser.model.AdminRushToTalkSeach> seachList;

/**
 * 话费说明
 */
public String content;

   public ApiPushChatData(Parcel in) 
{

if(coinList==null){
coinList=  new ArrayList<>();
 }
in.readTypedList(coinList,com.kalacheng.libuser.model.AdminRushToTalkConfig.CREATOR);

if(seachList==null){
seachList=  new ArrayList<>();
 }
in.readTypedList(seachList,com.kalacheng.libuser.model.AdminRushToTalkSeach.CREATOR);
content=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(coinList);

dest.writeTypedList(seachList);
dest.writeString(content);

}

  public static void cloneObj(ApiPushChatData source,ApiPushChatData target)
{

        if(source.coinList==null)
        {
            target.coinList=null;
        }else
        {
            target.coinList=new ArrayList();
            for(int i=0;i<source.coinList.size();i++)
            {
            AdminRushToTalkConfig.cloneObj(source.coinList.get(i),target.coinList.get(i));
            }
        }


        if(source.seachList==null)
        {
            target.seachList=null;
        }else
        {
            target.seachList=new ArrayList();
            for(int i=0;i<source.seachList.size();i++)
            {
            AdminRushToTalkSeach.cloneObj(source.seachList.get(i),target.seachList.get(i));
            }
        }


target.content=source.content;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiPushChatData> CREATOR = new Creator<ApiPushChatData>() {
        @Override
        public ApiPushChatData createFromParcel(Parcel in) {
            return new ApiPushChatData(in);
        }

        @Override
        public ApiPushChatData[] newArray(int size) {
            return new ApiPushChatData[size];
        }
    };
}
