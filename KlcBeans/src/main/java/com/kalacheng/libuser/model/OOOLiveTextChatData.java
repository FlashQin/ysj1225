package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busooolive.model.*;

import com.kalacheng.libuser.model.*;




/**
 * 1v1文字聊天页面数据
 */
public class OOOLiveTextChatData  implements Parcelable
{
 public OOOLiveTextChatData()
{
}

/**
 * 共同标签(多个用逗号隔开)
 */
public String tabList;

/**
 * 我和TA的故事
 */
public com.kalacheng.busooolive.model.OOOLiveRoomTextChatData chatData;

/**
 * 最近是否有发动态 1:发过 0:没有发过
 */
public int isVideo;

/**
 * 最新动态id
 */
public long videoId;

/**
 * 常用语
 */
public List<com.kalacheng.libuser.model.AppCommonWords> commonWordsList;

   public OOOLiveTextChatData(Parcel in) 
{
tabList=in.readString();

chatData=in.readParcelable(com.kalacheng.busooolive.model.OOOLiveRoomTextChatData.class.getClassLoader());
isVideo=in.readInt();
videoId=in.readLong();

if(commonWordsList==null){
commonWordsList=  new ArrayList<>();
 }
in.readTypedList(commonWordsList,com.kalacheng.libuser.model.AppCommonWords.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(tabList);

dest.writeParcelable(chatData,flags);
dest.writeInt(isVideo);
dest.writeLong(videoId);

dest.writeTypedList(commonWordsList);

}

  public static void cloneObj(OOOLiveTextChatData source,OOOLiveTextChatData target)
{

target.tabList=source.tabList;
        if(source.chatData==null)
        {
            target.chatData=null;
        }else
        {
            OOOLiveRoomTextChatData.cloneObj(source.chatData,target.chatData);
        }

target.isVideo=source.isVideo;

target.videoId=source.videoId;

        if(source.commonWordsList==null)
        {
            target.commonWordsList=null;
        }else
        {
            target.commonWordsList=new ArrayList();
            for(int i=0;i<source.commonWordsList.size();i++)
            {
            AppCommonWords.cloneObj(source.commonWordsList.get(i),target.commonWordsList.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OOOLiveTextChatData> CREATOR = new Creator<OOOLiveTextChatData>() {
        @Override
        public OOOLiveTextChatData createFromParcel(Parcel in) {
            return new OOOLiveTextChatData(in);
        }

        @Override
        public OOOLiveTextChatData[] newArray(int size) {
            return new OOOLiveTextChatData[size];
        }
    };
}
