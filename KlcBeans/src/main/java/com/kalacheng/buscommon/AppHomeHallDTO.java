package com.kalacheng.buscommon;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 首页大厅数据
 */
public class AppHomeHallDTO  implements Parcelable
{
 public AppHomeHallDTO()
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
 * 主播等级ICON
 */
public String anchorGradeIcon;

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
 * 短视频/图片 是否付费：1已支付，0未支付
 */
public int dspPay;

/**
 * 短视频/图片 是否私密：0：正常 1私密
 */
public int isPrivate;

/**
 * 试看时长(0为没有试看时间了)
 */
public int freeWatchTime;

/**
 * 直播标题
 */
public String title;

/**
 * 房间ID, 动态之类的无房间：0
 */
public long roomId;

/**
 * 类型描述
 */
public String typeDec;

/**
 * 点赞数
 */
public int likeNum;

/**
 * null
 */
public long id;

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
 * 观众人数
 */
public int nums;

/**
 * 房间模式 0:普通房间 1:私密房间 2:收费房间 3:计时房间 4:贵族房间
 */
public int roomType;

/**
 * 注册时间
 */
public Date createDate;

/**
 * 经度
 */
public String longitude;

/**
 * 一对一视频封面地址
 */
public String oooVideoImg;

/**
 * 一对一视频地址
 */
public String oooVideo;

/**
 * 类型值(对应类型1视频私密直播2视频收费直播3视频计时直播6 one2one语音7one2one视频11.贵族房间)
 */
public String typeVal;

/**
 * 标签名
 */
public String tabName;

/**
 * 门票房间是否需要付费 0:不需要付费 1:需要付费
 */
public int isPay;

/**
 * 头像
 */
public String headImg;

/**
 * 是否有直播购 0:没有直播购 1:有直播购
 */
public int liveFunction;

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
 * 权重排序字段
 */
public int sort;

/**
 * 安卓用
 */
public int isChecked;

/**
 * 用户ID
 */
public long userId;

/**
 * 评论数
 */
public int commentNum;

/**
 * 热门分类ID
 */
public long hotSortId;

/**
 * 播流地址
 */
public String pull;

/**
 * 一对一语音地址
 */
public String oooVoice;

/**
 * 房间标识
 */
public String showid;

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
 * 资源状态 所有直播类型资源：0关播， 1：直播 2:忙碌中  动态类型默认全部是1（一对一时:0在线1忙碌2离开3通话中）
 */
public int sourceState;

/**
 * 年龄
 */
public int age;

/**
 * 金币 包含付费金币，以及计时付费金币  (男朋友中为钻石)
 */
public double coin;

/**
 * 用户名
 */
public String username;

   public AppHomeHallDTO(Parcel in) 
{
isRecommend=in.readInt();
anchorGrade=in.readInt();
anchorGradeIcon=in.readString();
city=in.readString();
signature=in.readString();
liveType=in.readInt();
latitude=in.readString();
dspPay=in.readInt();
isPrivate=in.readInt();
freeWatchTime=in.readInt();
title=in.readString();
roomId=in.readLong();
typeDec=in.readString();
likeNum=in.readInt();
id=in.readLong();
tabStyle=in.readString();
fireVale=in.readDouble();
channelId=in.readLong();
nums=in.readInt();
roomType=in.readInt();
createDate=new Date( in.readLong());
longitude=in.readString();
oooVideoImg=in.readString();
oooVideo=in.readString();
typeVal=in.readString();
tabName=in.readString();
isPay=in.readInt();
headImg=in.readString();
liveFunction=in.readInt();
sex=in.readInt();
businessLogo=in.readString();
channelImage=in.readString();
sort=in.readInt();
isChecked=in.readInt();
userId=in.readLong();
commentNum=in.readInt();
hotSortId=in.readLong();
pull=in.readString();
oooVoice=in.readString();
showid=in.readString();
sourceType=in.readInt();
position=in.readString();
sourceCover=in.readString();
sourceState=in.readInt();
age=in.readInt();
coin=in.readDouble();
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(isRecommend);
dest.writeInt(anchorGrade);
dest.writeString(anchorGradeIcon);
dest.writeString(city);
dest.writeString(signature);
dest.writeInt(liveType);
dest.writeString(latitude);
dest.writeInt(dspPay);
dest.writeInt(isPrivate);
dest.writeInt(freeWatchTime);
dest.writeString(title);
dest.writeLong(roomId);
dest.writeString(typeDec);
dest.writeInt(likeNum);
dest.writeLong(id);
dest.writeString(tabStyle);
dest.writeDouble(fireVale);
dest.writeLong(channelId);
dest.writeInt(nums);
dest.writeInt(roomType);
dest.writeLong(createDate==null?0:createDate.getTime());
dest.writeString(longitude);
dest.writeString(oooVideoImg);
dest.writeString(oooVideo);
dest.writeString(typeVal);
dest.writeString(tabName);
dest.writeInt(isPay);
dest.writeString(headImg);
dest.writeInt(liveFunction);
dest.writeInt(sex);
dest.writeString(businessLogo);
dest.writeString(channelImage);
dest.writeInt(sort);
dest.writeInt(isChecked);
dest.writeLong(userId);
dest.writeInt(commentNum);
dest.writeLong(hotSortId);
dest.writeString(pull);
dest.writeString(oooVoice);
dest.writeString(showid);
dest.writeInt(sourceType);
dest.writeString(position);
dest.writeString(sourceCover);
dest.writeInt(sourceState);
dest.writeInt(age);
dest.writeDouble(coin);
dest.writeString(username);

}

  public static void cloneObj(AppHomeHallDTO source,AppHomeHallDTO target)
{

target.isRecommend=source.isRecommend;

target.anchorGrade=source.anchorGrade;

target.anchorGradeIcon=source.anchorGradeIcon;

target.city=source.city;

target.signature=source.signature;

target.liveType=source.liveType;

target.latitude=source.latitude;

target.dspPay=source.dspPay;

target.isPrivate=source.isPrivate;

target.freeWatchTime=source.freeWatchTime;

target.title=source.title;

target.roomId=source.roomId;

target.typeDec=source.typeDec;

target.likeNum=source.likeNum;

target.id=source.id;

target.tabStyle=source.tabStyle;

target.fireVale=source.fireVale;

target.channelId=source.channelId;

target.nums=source.nums;

target.roomType=source.roomType;

target.createDate=source.createDate;

target.longitude=source.longitude;

target.oooVideoImg=source.oooVideoImg;

target.oooVideo=source.oooVideo;

target.typeVal=source.typeVal;

target.tabName=source.tabName;

target.isPay=source.isPay;

target.headImg=source.headImg;

target.liveFunction=source.liveFunction;

target.sex=source.sex;

target.businessLogo=source.businessLogo;

target.channelImage=source.channelImage;

target.sort=source.sort;

target.isChecked=source.isChecked;

target.userId=source.userId;

target.commentNum=source.commentNum;

target.hotSortId=source.hotSortId;

target.pull=source.pull;

target.oooVoice=source.oooVoice;

target.showid=source.showid;

target.sourceType=source.sourceType;

target.position=source.position;

target.sourceCover=source.sourceCover;

target.sourceState=source.sourceState;

target.age=source.age;

target.coin=source.coin;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppHomeHallDTO> CREATOR = new Creator<AppHomeHallDTO>() {
        @Override
        public AppHomeHallDTO createFromParcel(Parcel in) {
            return new AppHomeHallDTO(in);
        }

        @Override
        public AppHomeHallDTO[] newArray(int size) {
            return new AppHomeHallDTO[size];
        }
    };
}
