package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP直播间表Dto
 */
public class LiveRoomDTO  implements Parcelable
{
 public LiveRoomDTO()
{
}

/**
 * 是否推荐0不推荐1推荐中
 */
public int isRecommend;

/**
 * 收到礼物个数
 */
public int addGiftNumber;

/**
 * 打赏人数
 */
public int rewardNumber;

/**
 * 开播时间
 */
public Date starttime;

/**
 * 游戏类型
 */
public int gameAction;

/**
 * 房间id
 */
public long roomId;

/**
 * 当前直播次数
 */
public int number;

/**
 * 直播状态 1:直播中 0:关播
 */
public int isLive;

/**
 * 直播购房间标签
 */
public String shopRoomLabel;

/**
 * 省份
 */
public String province;

/**
 * 直播视频地址
 */
public String liveUrl;

/**
 * null
 */
public long id;

/**
 * 直播封面图
 */
public String liveThumbs;

/**
 * 纬度
 */
public double lat;

/**
 * 房间类型 0:是一般直播 1:是私密直播 2:是收费直播 3:是计时直播 4:贵族房间
 */
public int roomType;

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
 * 经度
 */
public double lng;

/**
 * 新增关注人数
 */
public int addFollow;

/**
 * 是否有直播购 0:没有直播购 1:有直播购
 */
public int liveFunction;

/**
 * 用户昵称
 */
public String nickName;

/**
 * 推流地址
 */
public String push;

/**
 * 热门分类ID
 */
public long hotSortId;

/**
 * 直播类型 0:是一般直播 1:是私密直播 2:是收费直播 3:是计时直播 4:贵族房间
 */
public int sourceType;

/**
 * 房间类型值
 */
public String roomTypeVal;

/**
 * 付费/计时房间金额
 */
public double coin;

/**
 * 直播状态 0:直播结束 1:直播中
 */
public int status;

/**
 * 连麦开关 0:关 1:开
 */
public int isMic;

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
 * 观众人数
 */
public int audienceNumber;

/**
 * 热门礼物总额
 */
public int hotVotes;

/**
 * 标题
 */
public String title;

/**
 * 类型描述
 */
public String typeDec;

/**
 * 主播id
 */
public long uid;

/**
 * 封面图未通过原因
 */
public String thumbAuditReason;

/**
 * 直播横幅
 */
public String shopLiveBanner;

/**
 * 流名
 */
public String stream;

/**
 * 加入粉丝团人数
 */
public int addFansGroup;

/**
 * 用户昵称
 */
public String nickname;

/**
 * 直播时长(单位秒)
 */
public long liveTime;

/**
 * 开播时间
 */
public Date startTime;

/**
 * 标签样式
 */
public String tabStyle;

/**
 * 主播开播地址
 */
public String addr;

/**
 * 封面审核状态 0：初始状态 1未审核 2已通过 3未通过
 */
public int thumbState;

/**
 * 平台庄家余额
 */
public int bankerCoin;

/**
 * 总收益
 */
public double profit;

/**
 * 频道ID
 */
public long channelId;

/**
 * 公告
 */
public String notice;

/**
 * 城市
 */
public String address;

/**
 * 门票房间是否需要付费0不需要付费1需要付费
 */
public int isPay;

/**
 * 流地址
 */
public String rtmpUrl;

/**
 * 用户头像
 */
public String avatar;

/**
 * 是否演示账号 0:否 1:是
 */
public int demoAccount;

/**
 * 用户id
 */
public long userId;

/**
 * 热门礼物总额
 */
public int hotvotes;

/**
 * 用户迷你头像
 */
public String avatarThumb;

/**
 * 播流地址
 */
public String pull;

/**
 * 直播标识
 */
public String showid;

/**
 * 创建时间
 */
public Date addtime;

/**
 * 结束时间
 */
public Date endTime;

/**
 * 房间标题
 */
public String roomTitle;

/**
 * 靓号
 */
public String goodnum;

   public LiveRoomDTO(Parcel in) 
{
isRecommend=in.readInt();
addGiftNumber=in.readInt();
rewardNumber=in.readInt();
starttime=new Date( in.readLong());
gameAction=in.readInt();
roomId=in.readLong();
number=in.readInt();
isLive=in.readInt();
shopRoomLabel=in.readString();
province=in.readString();
liveUrl=in.readString();
id=in.readLong();
liveThumbs=in.readString();
lat=in.readDouble();
roomType=in.readInt();
ismic=in.readInt();
islive=in.readInt();
tabName=in.readString();
lng=in.readDouble();
addFollow=in.readInt();
liveFunction=in.readInt();
nickName=in.readString();
push=in.readString();
hotSortId=in.readLong();
sourceType=in.readInt();
roomTypeVal=in.readString();
coin=in.readDouble();
status=in.readInt();
isMic=in.readInt();
thumbAuditBy=in.readString();
city=in.readString();
thumb=in.readString();
isvideo=in.readInt();
thumbAuditTime=new Date( in.readLong());
audienceNumber=in.readInt();
hotVotes=in.readInt();
title=in.readString();
typeDec=in.readString();
uid=in.readLong();
thumbAuditReason=in.readString();
shopLiveBanner=in.readString();
stream=in.readString();
addFansGroup=in.readInt();
nickname=in.readString();
liveTime=in.readLong();
startTime=new Date( in.readLong());
tabStyle=in.readString();
addr=in.readString();
thumbState=in.readInt();
bankerCoin=in.readInt();
profit=in.readDouble();
channelId=in.readLong();
notice=in.readString();
address=in.readString();
isPay=in.readInt();
rtmpUrl=in.readString();
avatar=in.readString();
demoAccount=in.readInt();
userId=in.readLong();
hotvotes=in.readInt();
avatarThumb=in.readString();
pull=in.readString();
showid=in.readString();
addtime=new Date( in.readLong());
endTime=new Date( in.readLong());
roomTitle=in.readString();
goodnum=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(isRecommend);
dest.writeInt(addGiftNumber);
dest.writeInt(rewardNumber);
dest.writeLong(starttime==null?0:starttime.getTime());
dest.writeInt(gameAction);
dest.writeLong(roomId);
dest.writeInt(number);
dest.writeInt(isLive);
dest.writeString(shopRoomLabel);
dest.writeString(province);
dest.writeString(liveUrl);
dest.writeLong(id);
dest.writeString(liveThumbs);
dest.writeDouble(lat);
dest.writeInt(roomType);
dest.writeInt(ismic);
dest.writeInt(islive);
dest.writeString(tabName);
dest.writeDouble(lng);
dest.writeInt(addFollow);
dest.writeInt(liveFunction);
dest.writeString(nickName);
dest.writeString(push);
dest.writeLong(hotSortId);
dest.writeInt(sourceType);
dest.writeString(roomTypeVal);
dest.writeDouble(coin);
dest.writeInt(status);
dest.writeInt(isMic);
dest.writeString(thumbAuditBy);
dest.writeString(city);
dest.writeString(thumb);
dest.writeInt(isvideo);
dest.writeLong(thumbAuditTime==null?0:thumbAuditTime.getTime());
dest.writeInt(audienceNumber);
dest.writeInt(hotVotes);
dest.writeString(title);
dest.writeString(typeDec);
dest.writeLong(uid);
dest.writeString(thumbAuditReason);
dest.writeString(shopLiveBanner);
dest.writeString(stream);
dest.writeInt(addFansGroup);
dest.writeString(nickname);
dest.writeLong(liveTime);
dest.writeLong(startTime==null?0:startTime.getTime());
dest.writeString(tabStyle);
dest.writeString(addr);
dest.writeInt(thumbState);
dest.writeInt(bankerCoin);
dest.writeDouble(profit);
dest.writeLong(channelId);
dest.writeString(notice);
dest.writeString(address);
dest.writeInt(isPay);
dest.writeString(rtmpUrl);
dest.writeString(avatar);
dest.writeInt(demoAccount);
dest.writeLong(userId);
dest.writeInt(hotvotes);
dest.writeString(avatarThumb);
dest.writeString(pull);
dest.writeString(showid);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeLong(endTime==null?0:endTime.getTime());
dest.writeString(roomTitle);
dest.writeString(goodnum);

}

  public static void cloneObj(LiveRoomDTO source,LiveRoomDTO target)
{

target.isRecommend=source.isRecommend;

target.addGiftNumber=source.addGiftNumber;

target.rewardNumber=source.rewardNumber;

target.starttime=source.starttime;

target.gameAction=source.gameAction;

target.roomId=source.roomId;

target.number=source.number;

target.isLive=source.isLive;

target.shopRoomLabel=source.shopRoomLabel;

target.province=source.province;

target.liveUrl=source.liveUrl;

target.id=source.id;

target.liveThumbs=source.liveThumbs;

target.lat=source.lat;

target.roomType=source.roomType;

target.ismic=source.ismic;

target.islive=source.islive;

target.tabName=source.tabName;

target.lng=source.lng;

target.addFollow=source.addFollow;

target.liveFunction=source.liveFunction;

target.nickName=source.nickName;

target.push=source.push;

target.hotSortId=source.hotSortId;

target.sourceType=source.sourceType;

target.roomTypeVal=source.roomTypeVal;

target.coin=source.coin;

target.status=source.status;

target.isMic=source.isMic;

target.thumbAuditBy=source.thumbAuditBy;

target.city=source.city;

target.thumb=source.thumb;

target.isvideo=source.isvideo;

target.thumbAuditTime=source.thumbAuditTime;

target.audienceNumber=source.audienceNumber;

target.hotVotes=source.hotVotes;

target.title=source.title;

target.typeDec=source.typeDec;

target.uid=source.uid;

target.thumbAuditReason=source.thumbAuditReason;

target.shopLiveBanner=source.shopLiveBanner;

target.stream=source.stream;

target.addFansGroup=source.addFansGroup;

target.nickname=source.nickname;

target.liveTime=source.liveTime;

target.startTime=source.startTime;

target.tabStyle=source.tabStyle;

target.addr=source.addr;

target.thumbState=source.thumbState;

target.bankerCoin=source.bankerCoin;

target.profit=source.profit;

target.channelId=source.channelId;

target.notice=source.notice;

target.address=source.address;

target.isPay=source.isPay;

target.rtmpUrl=source.rtmpUrl;

target.avatar=source.avatar;

target.demoAccount=source.demoAccount;

target.userId=source.userId;

target.hotvotes=source.hotvotes;

target.avatarThumb=source.avatarThumb;

target.pull=source.pull;

target.showid=source.showid;

target.addtime=source.addtime;

target.endTime=source.endTime;

target.roomTitle=source.roomTitle;

target.goodnum=source.goodnum;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LiveRoomDTO> CREATOR = new Creator<LiveRoomDTO>() {
        @Override
        public LiveRoomDTO createFromParcel(Parcel in) {
            return new LiveRoomDTO(in);
        }

        @Override
        public LiveRoomDTO[] newArray(int size) {
            return new LiveRoomDTO[size];
        }
    };
}
