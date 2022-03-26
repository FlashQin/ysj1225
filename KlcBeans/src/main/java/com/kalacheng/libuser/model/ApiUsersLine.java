package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 在线用户数据
 */
public class ApiUsersLine  implements Parcelable
{
 public ApiUsersLine()
{
}

/**
 * 出生日期
 */
public String birthday;

/**
 * 角色id
 */
public int role;

/**
 * 距离
 */
public String distance;

/**
 * 用户签名
 */
public String signature;

/**
 * 封面图
 */
public String thumb;

/**
 * 类型 1:直播 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 */
public int liveType;

/**
 * 用户在线状态 0:离线 1:在线
 */
public int onlineStatus;

/**
 * 用户等级
 */
public String userGradeImg;

/**
 * 视频通话时间金币/min
 */
public double videoCoin;

/**
 * 直播间名称
 */
public String title;

/**
 * 直播类型(1:多人视频 2:多人语音)
 */
public int type;

/**
 * 贵族等级
 */
public String nobleGradeImg;

/**
 * null
 */
public String userSetOnlineStatus;

/**
 * 房间id
 */
public long roomId;

/**
 * 类型描述
 */
public String typeDec;

/**
 * 用户id
 */
public long uid;

/**
 * 星座
 */
public String constellation;

/**
 * 离线时间
 */
public String offLineTime;

/**
 * 语音通话时间金币/min
 */
public double voiceCoin;

/**
 * 视频直播状态：0:未进行直播 1:直播中的主播 2:直播中的观众
 */
public int liveStatus;

/**
 * 身高（CM）
 */
public int height;

/**
 * 币种类型
 */
public String coinType;

/**
 * 类型值
 */
public String typeVal;

/**
 * 门票房间是否需要付费 0:不需要付费 1:需要付费
 */
public int isPay;

/**
 * 性别；0：保密，1：男；2：女
 */
public int sex;

/**
 * 体重（KG）
 */
public double weight;

/**
 * 头像图片
 */
public String avatar;

/**
 * 用户资料图片， 英文逗号隔开
 */
public String portrait;

/**
 * 用户名称
 */
public String userName;

/**
 * 主播等级
 */
public String anchorGradeImg;

/**
 * 直播标识(房间号)
 */
public String showid;

/**
 * 离线时间Data
 */
public Date offLineTimeData;

/**
 * 直播类型 0:是一般直播 1:是私密直播 2:是收费直播 3:是计时直播 4:贵族房间
 */
public int sourceType;

/**
 * 是否显示距离：1-不显示，0-显示
 */
public int isLocation;

/**
 * 用户年龄
 */
public int age;

/**
 * 用户金币
 */
public double coin;

/**
 * 聊场是否显示金币 0:显示 1:不显示
 */
public int isSayCoin;

/**
 * 直播状态 0:直播结束 1:直播中
 */
public int status;

   public ApiUsersLine(Parcel in) 
{
birthday=in.readString();
role=in.readInt();
distance=in.readString();
signature=in.readString();
thumb=in.readString();
liveType=in.readInt();
onlineStatus=in.readInt();
userGradeImg=in.readString();
videoCoin=in.readDouble();
title=in.readString();
type=in.readInt();
nobleGradeImg=in.readString();
userSetOnlineStatus=in.readString();
roomId=in.readLong();
typeDec=in.readString();
uid=in.readLong();
constellation=in.readString();
offLineTime=in.readString();
voiceCoin=in.readDouble();
liveStatus=in.readInt();
height=in.readInt();
coinType=in.readString();
typeVal=in.readString();
isPay=in.readInt();
sex=in.readInt();
weight=in.readDouble();
avatar=in.readString();
portrait=in.readString();
userName=in.readString();
anchorGradeImg=in.readString();
showid=in.readString();
offLineTimeData=new Date( in.readLong());
sourceType=in.readInt();
isLocation=in.readInt();
age=in.readInt();
coin=in.readDouble();
isSayCoin=in.readInt();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(birthday);
dest.writeInt(role);
dest.writeString(distance);
dest.writeString(signature);
dest.writeString(thumb);
dest.writeInt(liveType);
dest.writeInt(onlineStatus);
dest.writeString(userGradeImg);
dest.writeDouble(videoCoin);
dest.writeString(title);
dest.writeInt(type);
dest.writeString(nobleGradeImg);
dest.writeString(userSetOnlineStatus);
dest.writeLong(roomId);
dest.writeString(typeDec);
dest.writeLong(uid);
dest.writeString(constellation);
dest.writeString(offLineTime);
dest.writeDouble(voiceCoin);
dest.writeInt(liveStatus);
dest.writeInt(height);
dest.writeString(coinType);
dest.writeString(typeVal);
dest.writeInt(isPay);
dest.writeInt(sex);
dest.writeDouble(weight);
dest.writeString(avatar);
dest.writeString(portrait);
dest.writeString(userName);
dest.writeString(anchorGradeImg);
dest.writeString(showid);
dest.writeLong(offLineTimeData==null?0:offLineTimeData.getTime());
dest.writeInt(sourceType);
dest.writeInt(isLocation);
dest.writeInt(age);
dest.writeDouble(coin);
dest.writeInt(isSayCoin);
dest.writeInt(status);

}

  public static void cloneObj(ApiUsersLine source,ApiUsersLine target)
{

target.birthday=source.birthday;

target.role=source.role;

target.distance=source.distance;

target.signature=source.signature;

target.thumb=source.thumb;

target.liveType=source.liveType;

target.onlineStatus=source.onlineStatus;

target.userGradeImg=source.userGradeImg;

target.videoCoin=source.videoCoin;

target.title=source.title;

target.type=source.type;

target.nobleGradeImg=source.nobleGradeImg;

target.userSetOnlineStatus=source.userSetOnlineStatus;

target.roomId=source.roomId;

target.typeDec=source.typeDec;

target.uid=source.uid;

target.constellation=source.constellation;

target.offLineTime=source.offLineTime;

target.voiceCoin=source.voiceCoin;

target.liveStatus=source.liveStatus;

target.height=source.height;

target.coinType=source.coinType;

target.typeVal=source.typeVal;

target.isPay=source.isPay;

target.sex=source.sex;

target.weight=source.weight;

target.avatar=source.avatar;

target.portrait=source.portrait;

target.userName=source.userName;

target.anchorGradeImg=source.anchorGradeImg;

target.showid=source.showid;

target.offLineTimeData=source.offLineTimeData;

target.sourceType=source.sourceType;

target.isLocation=source.isLocation;

target.age=source.age;

target.coin=source.coin;

target.isSayCoin=source.isSayCoin;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiUsersLine> CREATOR = new Creator<ApiUsersLine>() {
        @Override
        public ApiUsersLine createFromParcel(Parcel in) {
            return new ApiUsersLine(in);
        }

        @Override
        public ApiUsersLine[] newArray(int size) {
            return new ApiUsersLine[size];
        }
    };
}
