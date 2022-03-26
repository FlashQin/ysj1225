package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP直播间表
 */
public class LiveLive  implements Parcelable
{
 public LiveLive()
{
}

/**
 * 是否推荐 0:不推荐 1:推荐中
 */
public int isRecommend;

/**
 * 封面图审核人
 */
public String thumbAuditBy;

/**
 * 城市
 */
public String city;

/**
 * 封面图
 */
public String thumb;

/**
 * 是否假视频 0:直播 1:视频
 */
public int isvideo;

/**
 * 封面审核时间
 */
public Date thumbAuditTime;

/**
 * 开播时间
 */
public Date starttime;

/**
 * 游戏类型
 */
public int gameAction;

/**
 * 标题
 */
public String title;

/**
 * 直播购房间标签
 */
public String shopRoomLabel;

/**
 * 封面图未通过原因
 */
public String thumbAuditReason;

/**
 * 省份
 */
public String province;

/**
 * 直播横幅
 */
public String shopLiveBanner;

/**
 * 流名
 */
public String stream;

/**
 * 直播视频地址
 */
public String liveUrl;

/**
 * 用户昵称
 */
public String nickname;

/**
 * null
 */
public long id;

/**
 * 标签样式
 */
public String tabStyle;

/**
 * 封面审核状态 0：初始状态 1未审核 2已通过 3未通过
 */
public int thumbState;

/**
 * 平台庄家余额
 */
public int bankerCoin;

/**
 * 直播封面图
 */
public String liveThumbs;

/**
 * 频道ID
 */
public long channelId;

/**
 * 纬度
 */
public double lat;

/**
 * 房间类型 0:是一般直播 1:是私密直播 2:是收费直播 3:是计时直播 4:贵族房间
 */
public int roomType;

/**
 * 公告
 */
public String notice;

/**
 * 连麦开关，0是关，1是开
 */
public int ismic;

/**
 * 直播状态 1:直播中 0:关播
 */
public int islive;

/**
 * 标签名
 */
public String tabName;

/**
 * 城市
 */
public String address;

/**
 * 经度
 */
public double lng;

/**
 * 是否有直播购 0:没有直播购 1:有直播购
 */
public int liveFunction;

/**
 * 用户头像
 */
public String avatar;

/**
 * 是否演示账号： 0否  1是
 */
public int demoAccount;

/**
 * 用户id
 */
public long userId;

/**
 * 推流地址
 */
public String push;

/**
 * 热门礼物总额
 */
public int hotvotes;

/**
 * 用户迷你头像
 */
public String avatarThumb;

/**
 * 热门分类ID
 */
public long hotSortId;

/**
 * 播流地址
 */
public String pull;

/**
 * 直播标识
 */
public String showid;

/**
 * 房间类型值
 */
public String roomTypeVal;

/**
 * 房间标题
 */
public String roomTitle;

/**
 * 靓号
 */
public String goodnum;

   public LiveLive(Parcel in) 
{
isRecommend=in.readInt();
thumbAuditBy=in.readString();
city=in.readString();
thumb=in.readString();
isvideo=in.readInt();
thumbAuditTime=new Date( in.readLong());
starttime=new Date( in.readLong());
gameAction=in.readInt();
title=in.readString();
shopRoomLabel=in.readString();
thumbAuditReason=in.readString();
province=in.readString();
shopLiveBanner=in.readString();
stream=in.readString();
liveUrl=in.readString();
nickname=in.readString();
id=in.readLong();
tabStyle=in.readString();
thumbState=in.readInt();
bankerCoin=in.readInt();
liveThumbs=in.readString();
channelId=in.readLong();
lat=in.readDouble();
roomType=in.readInt();
notice=in.readString();
ismic=in.readInt();
islive=in.readInt();
tabName=in.readString();
address=in.readString();
lng=in.readDouble();
liveFunction=in.readInt();
avatar=in.readString();
demoAccount=in.readInt();
userId=in.readLong();
push=in.readString();
hotvotes=in.readInt();
avatarThumb=in.readString();
hotSortId=in.readLong();
pull=in.readString();
showid=in.readString();
roomTypeVal=in.readString();
roomTitle=in.readString();
goodnum=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(isRecommend);
dest.writeString(thumbAuditBy);
dest.writeString(city);
dest.writeString(thumb);
dest.writeInt(isvideo);
dest.writeLong(thumbAuditTime==null?0:thumbAuditTime.getTime());
dest.writeLong(starttime==null?0:starttime.getTime());
dest.writeInt(gameAction);
dest.writeString(title);
dest.writeString(shopRoomLabel);
dest.writeString(thumbAuditReason);
dest.writeString(province);
dest.writeString(shopLiveBanner);
dest.writeString(stream);
dest.writeString(liveUrl);
dest.writeString(nickname);
dest.writeLong(id);
dest.writeString(tabStyle);
dest.writeInt(thumbState);
dest.writeInt(bankerCoin);
dest.writeString(liveThumbs);
dest.writeLong(channelId);
dest.writeDouble(lat);
dest.writeInt(roomType);
dest.writeString(notice);
dest.writeInt(ismic);
dest.writeInt(islive);
dest.writeString(tabName);
dest.writeString(address);
dest.writeDouble(lng);
dest.writeInt(liveFunction);
dest.writeString(avatar);
dest.writeInt(demoAccount);
dest.writeLong(userId);
dest.writeString(push);
dest.writeInt(hotvotes);
dest.writeString(avatarThumb);
dest.writeLong(hotSortId);
dest.writeString(pull);
dest.writeString(showid);
dest.writeString(roomTypeVal);
dest.writeString(roomTitle);
dest.writeString(goodnum);

}

  public static void cloneObj(LiveLive source,LiveLive target)
{

target.isRecommend=source.isRecommend;

target.thumbAuditBy=source.thumbAuditBy;

target.city=source.city;

target.thumb=source.thumb;

target.isvideo=source.isvideo;

target.thumbAuditTime=source.thumbAuditTime;

target.starttime=source.starttime;

target.gameAction=source.gameAction;

target.title=source.title;

target.shopRoomLabel=source.shopRoomLabel;

target.thumbAuditReason=source.thumbAuditReason;

target.province=source.province;

target.shopLiveBanner=source.shopLiveBanner;

target.stream=source.stream;

target.liveUrl=source.liveUrl;

target.nickname=source.nickname;

target.id=source.id;

target.tabStyle=source.tabStyle;

target.thumbState=source.thumbState;

target.bankerCoin=source.bankerCoin;

target.liveThumbs=source.liveThumbs;

target.channelId=source.channelId;

target.lat=source.lat;

target.roomType=source.roomType;

target.notice=source.notice;

target.ismic=source.ismic;

target.islive=source.islive;

target.tabName=source.tabName;

target.address=source.address;

target.lng=source.lng;

target.liveFunction=source.liveFunction;

target.avatar=source.avatar;

target.demoAccount=source.demoAccount;

target.userId=source.userId;

target.push=source.push;

target.hotvotes=source.hotvotes;

target.avatarThumb=source.avatarThumb;

target.hotSortId=source.hotSortId;

target.pull=source.pull;

target.showid=source.showid;

target.roomTypeVal=source.roomTypeVal;

target.roomTitle=source.roomTitle;

target.goodnum=source.goodnum;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LiveLive> CREATOR = new Creator<LiveLive>() {
        @Override
        public LiveLive createFromParcel(Parcel in) {
            return new LiveLive(in);
        }

        @Override
        public LiveLive[] newArray(int size) {
            return new LiveLive[size];
        }
    };
}
