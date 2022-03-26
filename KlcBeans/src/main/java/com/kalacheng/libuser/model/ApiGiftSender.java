package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.buscommon.model.*;




/**
 * APP发礼物响应
 */
public class ApiGiftSender  implements Parcelable
{
 public ApiGiftSender()
{
}

/**
 * 礼物图标
 */
public String gifticon;

/**
 * 状态描述
 */
public String msg;

/**
 * 状态1成功2失败
 */
public int code;

/**
 * 主播头像
 */
public String anchorAvatar;

/**
 * 礼物名称
 */
public String giftName;

/**
 * 直播类型
 */
public int liveType;

/**
 * 用户头像
 */
public String userAvatar;

/**
 * 用户余额
 */
public double userCoin;

/**
 * 主播id
 */
public long anchorId;

/**
 * 类型 0:普通礼物 1:粉丝团(豪华礼物) 2:守护(专属礼物) 3:贵族礼物(特殊礼物) 4:背包礼物
 */
public int type;

/**
 * 用户名
 */
public String userName;

/**
 * 主播姓名
 */
public String anchorName;

/**
 * 用户id
 */
public long userId;

/**
 * 房间号
 */
public long roomId;

/**
 * 礼物名称
 */
public String giftname;

/**
 * 礼物动态
 */
public String giftswf;

/**
 * 连送次数
 */
public int continuousNumber;

/**
 * 礼物id
 */
public long giftId;

/**
 * 直播间观众列表
 */
public List<com.kalacheng.buscommon.model.ApiUserBasicInfo> userList;

/**
 * 是否有送礼特效特权 0没有 1有
 */
public int sendGiftPrivilege;

/**
 * 魅力值
 */
public double votes;

/**
 * 动图时长
 */
public float swftime;

/**
 * 礼物个数
 */
public int giftNumber;

   public ApiGiftSender(Parcel in) 
{
gifticon=in.readString();
msg=in.readString();
code=in.readInt();
anchorAvatar=in.readString();
giftName=in.readString();
liveType=in.readInt();
userAvatar=in.readString();
userCoin=in.readDouble();
anchorId=in.readLong();
type=in.readInt();
userName=in.readString();
anchorName=in.readString();
userId=in.readLong();
roomId=in.readLong();
giftname=in.readString();
giftswf=in.readString();
continuousNumber=in.readInt();
giftId=in.readLong();

if(userList==null){
userList=  new ArrayList<>();
 }
in.readTypedList(userList,com.kalacheng.buscommon.model.ApiUserBasicInfo.CREATOR);
sendGiftPrivilege=in.readInt();
votes=in.readDouble();
swftime=in.readFloat();
giftNumber=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(gifticon);
dest.writeString(msg);
dest.writeInt(code);
dest.writeString(anchorAvatar);
dest.writeString(giftName);
dest.writeInt(liveType);
dest.writeString(userAvatar);
dest.writeDouble(userCoin);
dest.writeLong(anchorId);
dest.writeInt(type);
dest.writeString(userName);
dest.writeString(anchorName);
dest.writeLong(userId);
dest.writeLong(roomId);
dest.writeString(giftname);
dest.writeString(giftswf);
dest.writeInt(continuousNumber);
dest.writeLong(giftId);

dest.writeTypedList(userList);
dest.writeInt(sendGiftPrivilege);
dest.writeDouble(votes);
dest.writeFloat(swftime);
dest.writeInt(giftNumber);

}

  public static void cloneObj(ApiGiftSender source,ApiGiftSender target)
{

target.gifticon=source.gifticon;

target.msg=source.msg;

target.code=source.code;

target.anchorAvatar=source.anchorAvatar;

target.giftName=source.giftName;

target.liveType=source.liveType;

target.userAvatar=source.userAvatar;

target.userCoin=source.userCoin;

target.anchorId=source.anchorId;

target.type=source.type;

target.userName=source.userName;

target.anchorName=source.anchorName;

target.userId=source.userId;

target.roomId=source.roomId;

target.giftname=source.giftname;

target.giftswf=source.giftswf;

target.continuousNumber=source.continuousNumber;

target.giftId=source.giftId;

        if(source.userList==null)
        {
            target.userList=null;
        }else
        {
            target.userList=new ArrayList();
            for(int i=0;i<source.userList.size();i++)
            {
            ApiUserBasicInfo.cloneObj(source.userList.get(i),target.userList.get(i));
            }
        }


target.sendGiftPrivilege=source.sendGiftPrivilege;

target.votes=source.votes;

target.swftime=source.swftime;

target.giftNumber=source.giftNumber;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiGiftSender> CREATOR = new Creator<ApiGiftSender>() {
        @Override
        public ApiGiftSender createFromParcel(Parcel in) {
            return new ApiGiftSender(in);
        }

        @Override
        public ApiGiftSender[] newArray(int size) {
            return new ApiGiftSender[size];
        }
    };
}
