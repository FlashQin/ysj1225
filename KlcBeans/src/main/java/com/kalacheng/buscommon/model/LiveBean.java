package com.kalacheng.buscommon.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP直播大厅
 */
public class LiveBean  implements Parcelable
{
 public LiveBean()
{
}

/**
 * 类型值
 */
public String typeVal;

/**
 * 正在进行的游戏
 */
public String game;

/**
 * 直播状态 1:直播中 0:关播
 */
public int islive;

/**
 * 门票房间是否需要付费 0:不需要付费 1:需要付费
 */
public int isPay;

/**
 * 直播位置
 */
public String distance;

/**
 * 城市
 */
public String city;

/**
 * 个性签名
 */
public String signature;

/**
 * 封面图
 */
public String thumb;

/**
 * 直播性别
 */
public int sex;

/**
 * 头像
 */
public String avatar;

/**
 * 游戏状态
 */
public int gameAction;

/**
 * 直播间标题
 */
public String title;

/**
 * 直播类型 0:是一般直播 1:是私密直播 2:是收费直播 3:是计时直播 
 */
public int type;

/**
 * 主播等级
 */
public String anchorLevel;

/**
 * 房间id
 */
public long roomId;

/**
 * 类型描述
 */
public String typeDec;

/**
 * 迷你头像
 */
public String avatarThumb;

/**
 * 播流地址
 */
public String pull;

/**
 * 用户id
 */
public long uid;

/**
 * 用户等级
 */
public String userLevel;

/**
 * 流名
 */
public String stream;

/**
 * 用户昵称
 */
public String nickname;

/**
 * 主播的靓号
 */
public String goodnum;

/**
 * 直播间人数
 */
public int nums;

   public LiveBean(Parcel in) 
{
typeVal=in.readString();
game=in.readString();
islive=in.readInt();
isPay=in.readInt();
distance=in.readString();
city=in.readString();
signature=in.readString();
thumb=in.readString();
sex=in.readInt();
avatar=in.readString();
gameAction=in.readInt();
title=in.readString();
type=in.readInt();
anchorLevel=in.readString();
roomId=in.readLong();
typeDec=in.readString();
avatarThumb=in.readString();
pull=in.readString();
uid=in.readLong();
userLevel=in.readString();
stream=in.readString();
nickname=in.readString();
goodnum=in.readString();
nums=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(typeVal);
dest.writeString(game);
dest.writeInt(islive);
dest.writeInt(isPay);
dest.writeString(distance);
dest.writeString(city);
dest.writeString(signature);
dest.writeString(thumb);
dest.writeInt(sex);
dest.writeString(avatar);
dest.writeInt(gameAction);
dest.writeString(title);
dest.writeInt(type);
dest.writeString(anchorLevel);
dest.writeLong(roomId);
dest.writeString(typeDec);
dest.writeString(avatarThumb);
dest.writeString(pull);
dest.writeLong(uid);
dest.writeString(userLevel);
dest.writeString(stream);
dest.writeString(nickname);
dest.writeString(goodnum);
dest.writeInt(nums);

}

  public static void cloneObj(LiveBean source,LiveBean target)
{

target.typeVal=source.typeVal;

target.game=source.game;

target.islive=source.islive;

target.isPay=source.isPay;

target.distance=source.distance;

target.city=source.city;

target.signature=source.signature;

target.thumb=source.thumb;

target.sex=source.sex;

target.avatar=source.avatar;

target.gameAction=source.gameAction;

target.title=source.title;

target.type=source.type;

target.anchorLevel=source.anchorLevel;

target.roomId=source.roomId;

target.typeDec=source.typeDec;

target.avatarThumb=source.avatarThumb;

target.pull=source.pull;

target.uid=source.uid;

target.userLevel=source.userLevel;

target.stream=source.stream;

target.nickname=source.nickname;

target.goodnum=source.goodnum;

target.nums=source.nums;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LiveBean> CREATOR = new Creator<LiveBean>() {
        @Override
        public LiveBean createFromParcel(Parcel in) {
            return new LiveBean(in);
        }

        @Override
        public LiveBean[] newArray(int size) {
            return new LiveBean[size];
        }
    };
}
