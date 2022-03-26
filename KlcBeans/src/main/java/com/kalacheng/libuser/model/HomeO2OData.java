package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 首页一对一垂直显示数据
 */
public class HomeO2OData  implements Parcelable
{
 public HomeO2OData()
{
}

/**
 * 是否推荐 0:不推荐 1:推荐中
 */
public int isRecommend;

/**
 * 主播等级
 */
public int anchorGrade;

/**
 * 短视频/图片 是否付费：1已支付，0未支付
 */
public int dspPay;

/**
 * 试看时长(0为没有试看时间了)
 */
public int freeWatchTime;

/**
 * 房间ID, 动态之类的无房间：0
 */
public long roomId;

/**
 * 点赞数
 */
public int likeNum;

/**
 * null
 */
public long id;

/**
 * 经度
 */
public double lat;

/**
 * 观众人数
 */
public int nums;

/**
 * 房间模式 0:普通房间 1:私密房间 2:收费房间 3:计时房间 4:贵族房间
 */
public int roomType;

/**
 * 经度
 */
public String longitude;

/**
 * 类型值(对应类型1视频私密直播2视频收费直播3视频计时直播6 one2one语音7one2one视频11.贵族房间)
 */
public String typeVal;

/**
 * 标签名
 */
public String tabName;

/**
 * 头像
 */
public String headImg;

/**
 * 纬度
 */
public double lng;

/**
 * 是否有直播购 0:没有直播购 1:有直播购
 */
public int liveFunction;

/**
 * 权重排序字段
 */
public int sort;

/**
 * 0:未点击 1:点击
 */
public int isChecked;

/**
 * 评论数
 */
public int commentNum;

/**
 * 热门分类ID
 */
public long hotSortId;

/**
 * 一对一语音地址
 */
public String oooVoice;

/**
 * 资源类型：0:视频一般直播 1:视频私密直播 2:视频收费直播 3:视频计时直播 4:语音普通直播 5:语音私密直播 6:one2one语音 7:one2one视频 8:文字动态 9:视频动态 10:图片动态 11:多人直播贵族房间 12:短视频 13:短视频图片 14:直播购 15:语音付费 16:语音贵族 17:语音计时
 */
public int sourceType;

/**
 * 位置
 */
public String position;

/**
 * 资源封面
 */
public String sourceCover;

/**
 * 海报 垂直显示为海报
 */
public String poster;

/**
 * 金币 包含付费金币，以及计时付费金币  (男朋友中为钻石)
 */
public double coin;

/**
 * 贵族等级名称
 */
public String nobleGradeName;

/**
 * 主播等级ICON
 */
public String anchorGradeIcon;

/**
 * 距离
 */
public String distance;

/**
 * 城市
 */
public String city;

/**
 * 签名
 */
public String signature;

/**
 * 类型 1:直播 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 */
public int liveType;

/**
 * 纬度
 */
public String latitude;

/**
 * 视频价格
 */
public double videoCoin;

/**
 * 短视频/图片 是否私密：0：正常 1私密
 */
public int isPrivate;

/**
 * 直播标题
 */
public String title;

/**
 * 判断是否是贵族
 */
public int isvip;

/**
 * 类型描述
 */
public String typeDec;

/**
 * 贵族折扣
 */
public double rechargeDiscount;

/**
 * 语音价格
 */
public double voiceCoin;

/**
 * 标签样式
 */
public String tabStyle;

/**
 * 火力值
 */
public double fireVale;

/**
 * 频道ID
 */
public long channelId;

/**
 * 注册时间
 */
public Date createDate;

/**
 * 一对一视频封面地址
 */
public String oooVideoImg;

/**
 * 一对一视频地址
 */
public String oooVideo;

/**
 * 门票房间是否需要付费 0:不需要付费 1:需要付费
 */
public int isPay;

/**
 * 性别 0：保密 1：男， 2女
 */
public int sex;

/**
 * 商家头像
 */
public String businessLogo;

/**
 * 频道背景图
 */
public String channelImage;

/**
 * 用户ID
 */
public long userId;

/**
 * 播流地址
 */
public String pull;

/**
 * 房间标识
 */
public String showid;

/**
 * 资源状态 所有直播类型资源：0关播， 1：直播 2:忙碌中  动态类型默认全部是1（一对一时:0在线1忙碌2离开3通话中）
 */
public int sourceState;

/**
 * 年龄
 */
public int age;

/**
 * 用户名
 */
public String username;

   public HomeO2OData(Parcel in) 
{
isRecommend=in.readInt();
anchorGrade=in.readInt();
dspPay=in.readInt();
freeWatchTime=in.readInt();
roomId=in.readLong();
likeNum=in.readInt();
id=in.readLong();
lat=in.readDouble();
nums=in.readInt();
roomType=in.readInt();
longitude=in.readString();
typeVal=in.readString();
tabName=in.readString();
headImg=in.readString();
lng=in.readDouble();
liveFunction=in.readInt();
sort=in.readInt();
isChecked=in.readInt();
commentNum=in.readInt();
hotSortId=in.readLong();
oooVoice=in.readString();
sourceType=in.readInt();
position=in.readString();
sourceCover=in.readString();
poster=in.readString();
coin=in.readDouble();
nobleGradeName=in.readString();
anchorGradeIcon=in.readString();
distance=in.readString();
city=in.readString();
signature=in.readString();
liveType=in.readInt();
latitude=in.readString();
videoCoin=in.readDouble();
isPrivate=in.readInt();
title=in.readString();
isvip=in.readInt();
typeDec=in.readString();
rechargeDiscount=in.readDouble();
voiceCoin=in.readDouble();
tabStyle=in.readString();
fireVale=in.readDouble();
channelId=in.readLong();
createDate=new Date( in.readLong());
oooVideoImg=in.readString();
oooVideo=in.readString();
isPay=in.readInt();
sex=in.readInt();
businessLogo=in.readString();
channelImage=in.readString();
userId=in.readLong();
pull=in.readString();
showid=in.readString();
sourceState=in.readInt();
age=in.readInt();
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(isRecommend);
dest.writeInt(anchorGrade);
dest.writeInt(dspPay);
dest.writeInt(freeWatchTime);
dest.writeLong(roomId);
dest.writeInt(likeNum);
dest.writeLong(id);
dest.writeDouble(lat);
dest.writeInt(nums);
dest.writeInt(roomType);
dest.writeString(longitude);
dest.writeString(typeVal);
dest.writeString(tabName);
dest.writeString(headImg);
dest.writeDouble(lng);
dest.writeInt(liveFunction);
dest.writeInt(sort);
dest.writeInt(isChecked);
dest.writeInt(commentNum);
dest.writeLong(hotSortId);
dest.writeString(oooVoice);
dest.writeInt(sourceType);
dest.writeString(position);
dest.writeString(sourceCover);
dest.writeString(poster);
dest.writeDouble(coin);
dest.writeString(nobleGradeName);
dest.writeString(anchorGradeIcon);
dest.writeString(distance);
dest.writeString(city);
dest.writeString(signature);
dest.writeInt(liveType);
dest.writeString(latitude);
dest.writeDouble(videoCoin);
dest.writeInt(isPrivate);
dest.writeString(title);
dest.writeInt(isvip);
dest.writeString(typeDec);
dest.writeDouble(rechargeDiscount);
dest.writeDouble(voiceCoin);
dest.writeString(tabStyle);
dest.writeDouble(fireVale);
dest.writeLong(channelId);
dest.writeLong(createDate==null?0:createDate.getTime());
dest.writeString(oooVideoImg);
dest.writeString(oooVideo);
dest.writeInt(isPay);
dest.writeInt(sex);
dest.writeString(businessLogo);
dest.writeString(channelImage);
dest.writeLong(userId);
dest.writeString(pull);
dest.writeString(showid);
dest.writeInt(sourceState);
dest.writeInt(age);
dest.writeString(username);

}

  public static void cloneObj(HomeO2OData source,HomeO2OData target)
{

target.isRecommend=source.isRecommend;

target.anchorGrade=source.anchorGrade;

target.dspPay=source.dspPay;

target.freeWatchTime=source.freeWatchTime;

target.roomId=source.roomId;

target.likeNum=source.likeNum;

target.id=source.id;

target.lat=source.lat;

target.nums=source.nums;

target.roomType=source.roomType;

target.longitude=source.longitude;

target.typeVal=source.typeVal;

target.tabName=source.tabName;

target.headImg=source.headImg;

target.lng=source.lng;

target.liveFunction=source.liveFunction;

target.sort=source.sort;

target.isChecked=source.isChecked;

target.commentNum=source.commentNum;

target.hotSortId=source.hotSortId;

target.oooVoice=source.oooVoice;

target.sourceType=source.sourceType;

target.position=source.position;

target.sourceCover=source.sourceCover;

target.poster=source.poster;

target.coin=source.coin;

target.nobleGradeName=source.nobleGradeName;

target.anchorGradeIcon=source.anchorGradeIcon;

target.distance=source.distance;

target.city=source.city;

target.signature=source.signature;

target.liveType=source.liveType;

target.latitude=source.latitude;

target.videoCoin=source.videoCoin;

target.isPrivate=source.isPrivate;

target.title=source.title;

target.isvip=source.isvip;

target.typeDec=source.typeDec;

target.rechargeDiscount=source.rechargeDiscount;

target.voiceCoin=source.voiceCoin;

target.tabStyle=source.tabStyle;

target.fireVale=source.fireVale;

target.channelId=source.channelId;

target.createDate=source.createDate;

target.oooVideoImg=source.oooVideoImg;

target.oooVideo=source.oooVideo;

target.isPay=source.isPay;

target.sex=source.sex;

target.businessLogo=source.businessLogo;

target.channelImage=source.channelImage;

target.userId=source.userId;

target.pull=source.pull;

target.showid=source.showid;

target.sourceState=source.sourceState;

target.age=source.age;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HomeO2OData> CREATOR = new Creator<HomeO2OData>() {
        @Override
        public HomeO2OData createFromParcel(Parcel in) {
            return new HomeO2OData(in);
        }

        @Override
        public HomeO2OData[] newArray(int size) {
            return new HomeO2OData[size];
        }
    };
}
