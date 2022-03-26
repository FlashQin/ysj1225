package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 用户的配置
 */
public class CfgPayCallOneVsOne  implements Parcelable
{
 public CfgPayCallOneVsOne()
{
}

/**
 * 展示声音
 */
public String voice;

/**
 * 标签
 */
public String tabName;

/**
 * 一对一打开状态 0：默认，不打开  1：打开
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
 * 更新时间
 */
public Date updateTime;

/**
 * 展示视频
 */
public String video;

/**
 * 用户ID
 */
public long userId;

/**
 * 热门标签
 */
public long hotSortId;

/**
 * 创建时间
 */
public Date createTime;

/**
 * 语音通话时间金币/min
 */
public double voiceCoin;

/**
 * null
 */
public long id;

/**
 * 海报
 */
public String poster;

/**
 * 备注
 */
public String remarks;

/**
 * 一对一状态 0:在线 1:忙碌 2:离开 3:通话中
 */
public int liveState;

   public CfgPayCallOneVsOne(Parcel in) 
{
voice=in.readString();
tabName=in.readString();
openState=in.readInt();
videoCoin=in.readDouble();
videoImg=in.readString();
updateTime=new Date( in.readLong());
video=in.readString();
userId=in.readLong();
hotSortId=in.readLong();
createTime=new Date( in.readLong());
voiceCoin=in.readDouble();
id=in.readLong();
poster=in.readString();
remarks=in.readString();
liveState=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(voice);
dest.writeString(tabName);
dest.writeInt(openState);
dest.writeDouble(videoCoin);
dest.writeString(videoImg);
dest.writeLong(updateTime==null?0:updateTime.getTime());
dest.writeString(video);
dest.writeLong(userId);
dest.writeLong(hotSortId);
dest.writeLong(createTime==null?0:createTime.getTime());
dest.writeDouble(voiceCoin);
dest.writeLong(id);
dest.writeString(poster);
dest.writeString(remarks);
dest.writeInt(liveState);

}

  public static void cloneObj(CfgPayCallOneVsOne source,CfgPayCallOneVsOne target)
{

target.voice=source.voice;

target.tabName=source.tabName;

target.openState=source.openState;

target.videoCoin=source.videoCoin;

target.videoImg=source.videoImg;

target.updateTime=source.updateTime;

target.video=source.video;

target.userId=source.userId;

target.hotSortId=source.hotSortId;

target.createTime=source.createTime;

target.voiceCoin=source.voiceCoin;

target.id=source.id;

target.poster=source.poster;

target.remarks=source.remarks;

target.liveState=source.liveState;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CfgPayCallOneVsOne> CREATOR = new Creator<CfgPayCallOneVsOne>() {
        @Override
        public CfgPayCallOneVsOne createFromParcel(Parcel in) {
            return new CfgPayCallOneVsOne(in);
        }

        @Override
        public CfgPayCallOneVsOne[] newArray(int size) {
            return new CfgPayCallOneVsOne[size];
        }
    };
}
