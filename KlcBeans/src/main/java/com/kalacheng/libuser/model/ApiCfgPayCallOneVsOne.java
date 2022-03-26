package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP遇见用户端相应
 */
public class ApiCfgPayCallOneVsOne  implements Parcelable
{
 public ApiCfgPayCallOneVsOne()
{
}

/**
 * 兴趣(json)
 */
public String tabIdList;

/**
 * 展示声音 
 */
public String voice;

/**
 * 距离
 */
public String distance;

/**
 * 经度
 */
public String lng;

/**
 * 城市
 */
public String city;

/**
 * 一对一打开状态0：默认，不打开  1：打开 
 */
public int openState;

/**
 * 视频通话时间金币/min
 */
public double videoCoin;

/**
 * 展示视频封面
 */
public String videoImg;

/**
 * 用户头像
 */
public String liveThumb;

/**
 * 更新时间
 */
public Date updateTime;

/**
 * 展示视频
 */
public String video;

/**
 * 用户名
 */
public String userName;

/**
 * 用户ID 
 */
public long userId;

/**
 * 省份
 */
public String province;

/**
 * 创建时间
 */
public Date createTime;

/**
 * 主播被邀请的状态 0.待邀请 1.邀请中
 */
public int invitedFlag;

/**
 * 语音通话时间金币/min
 */
public double voiceCoin;

/**
 * id
 */
public long id;

/**
 * 海报
 */
public String poster;

/**
 * 纬度
 */
public String lat;

/**
 * 备注 
 */
public String remarks;

   public ApiCfgPayCallOneVsOne(Parcel in) 
{
tabIdList=in.readString();
voice=in.readString();
distance=in.readString();
lng=in.readString();
city=in.readString();
openState=in.readInt();
videoCoin=in.readDouble();
videoImg=in.readString();
liveThumb=in.readString();
updateTime=new Date( in.readLong());
video=in.readString();
userName=in.readString();
userId=in.readLong();
province=in.readString();
createTime=new Date( in.readLong());
invitedFlag=in.readInt();
voiceCoin=in.readDouble();
id=in.readLong();
poster=in.readString();
lat=in.readString();
remarks=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(tabIdList);
dest.writeString(voice);
dest.writeString(distance);
dest.writeString(lng);
dest.writeString(city);
dest.writeInt(openState);
dest.writeDouble(videoCoin);
dest.writeString(videoImg);
dest.writeString(liveThumb);
dest.writeLong(updateTime==null?0:updateTime.getTime());
dest.writeString(video);
dest.writeString(userName);
dest.writeLong(userId);
dest.writeString(province);
dest.writeLong(createTime==null?0:createTime.getTime());
dest.writeInt(invitedFlag);
dest.writeDouble(voiceCoin);
dest.writeLong(id);
dest.writeString(poster);
dest.writeString(lat);
dest.writeString(remarks);

}

  public static void cloneObj(ApiCfgPayCallOneVsOne source,ApiCfgPayCallOneVsOne target)
{

target.tabIdList=source.tabIdList;

target.voice=source.voice;

target.distance=source.distance;

target.lng=source.lng;

target.city=source.city;

target.openState=source.openState;

target.videoCoin=source.videoCoin;

target.videoImg=source.videoImg;

target.liveThumb=source.liveThumb;

target.updateTime=source.updateTime;

target.video=source.video;

target.userName=source.userName;

target.userId=source.userId;

target.province=source.province;

target.createTime=source.createTime;

target.invitedFlag=source.invitedFlag;

target.voiceCoin=source.voiceCoin;

target.id=source.id;

target.poster=source.poster;

target.lat=source.lat;

target.remarks=source.remarks;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiCfgPayCallOneVsOne> CREATOR = new Creator<ApiCfgPayCallOneVsOne>() {
        @Override
        public ApiCfgPayCallOneVsOne createFromParcel(Parcel in) {
            return new ApiCfgPayCallOneVsOne(in);
        }

        @Override
        public ApiCfgPayCallOneVsOne[] newArray(int size) {
            return new ApiCfgPayCallOneVsOne[size];
        }
    };
}
