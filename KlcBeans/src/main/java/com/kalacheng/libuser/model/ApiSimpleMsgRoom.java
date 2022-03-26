package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP直播间简单消息展示
 */
public class ApiSimpleMsgRoom  implements Parcelable
{
 public ApiSimpleMsgRoom()
{
}

/**
 * null
 */
public long serialVersionUID;

/**
 * 消息类型 1:进场消息 2:退场消息 3:礼物消息 4:点亮 5:红包 6:禁言解禁消息 7:设置取消管理员 8:踢人消息 9:购买守护 10:关注和取消关注 11:文字聊天消息 12:主播离开回来消息 13:系统消息 14:弹幕消息 15:骰子 16警告消息
 */
public int type;

/**
 * 用户id
 */
public long uid;

/**
 * 用户名称
 */
public String uname;

/**
 * 对方用户id
 */
public long touid;

/**
 * 对方用户名称
 */
public String touname;

/**
 * 消息内容
 */
public String content;

/**
 * 是否守护 1:是 0:否
 */
public int isSh;

/**
 * 是否vip 1:是 0:否
 */
public int isVip;

/**
 * 靓号
 */
public String goodnum;

/**
 * 用户头像
 */
public String avatar;

/**
 * 守护类型 0:没有守护 1:普通 2:尊贵
 */
public int guardType;

/**
 * 是否为当前直播间主播 0:普通用户 1:主播
 */
public int role;

/**
 * 1:全部直播间的礼物 2:直播间内的礼物 3:直播间内粉丝团礼物 4:直播间内贵族礼物 5:守护礼物
 */
public int styleType;

/**
 * 主播头像
 */
public String anchorAvatar;

/**
 * 主播姓名
 */
public String anchorName;

/**
 * 礼物图标
 */
public String gifticon;

/**
 * 礼物名称
 */
public String giftname;

/**
 * 礼物数量
 */
public int giftNumber;

/**
 * 主播等级图片
 */
public String anchorGrade;

/**
 * 用户等级图片
 */
public String userGrade;

/**
 * 财富等级图片
 */
public String wealthGrade;

/**
 * 贵族等级图标
 */
public String nobleGrade;

/**
 * 是否是粉丝 1:是粉丝 0:不是粉丝
 */
public int isFans;

/**
 * 是否字体变色 1:-变色 2:-不变色
 */
public int fontDiscoloration;

/**
 * 房间号
 */
public long roomId;

   public ApiSimpleMsgRoom(Parcel in) 
{
serialVersionUID=in.readLong();
type=in.readInt();
uid=in.readLong();
uname=in.readString();
touid=in.readLong();
touname=in.readString();
content=in.readString();
isSh=in.readInt();
isVip=in.readInt();
goodnum=in.readString();
avatar=in.readString();
guardType=in.readInt();
role=in.readInt();
styleType=in.readInt();
anchorAvatar=in.readString();
anchorName=in.readString();
gifticon=in.readString();
giftname=in.readString();
giftNumber=in.readInt();
anchorGrade=in.readString();
userGrade=in.readString();
wealthGrade=in.readString();
nobleGrade=in.readString();
isFans=in.readInt();
fontDiscoloration=in.readInt();
roomId=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(serialVersionUID);
dest.writeInt(type);
dest.writeLong(uid);
dest.writeString(uname);
dest.writeLong(touid);
dest.writeString(touname);
dest.writeString(content);
dest.writeInt(isSh);
dest.writeInt(isVip);
dest.writeString(goodnum);
dest.writeString(avatar);
dest.writeInt(guardType);
dest.writeInt(role);
dest.writeInt(styleType);
dest.writeString(anchorAvatar);
dest.writeString(anchorName);
dest.writeString(gifticon);
dest.writeString(giftname);
dest.writeInt(giftNumber);
dest.writeString(anchorGrade);
dest.writeString(userGrade);
dest.writeString(wealthGrade);
dest.writeString(nobleGrade);
dest.writeInt(isFans);
dest.writeInt(fontDiscoloration);
dest.writeLong(roomId);

}

  public static void cloneObj(ApiSimpleMsgRoom source,ApiSimpleMsgRoom target)
{

target.serialVersionUID=source.serialVersionUID;

target.type=source.type;

target.uid=source.uid;

target.uname=source.uname;

target.touid=source.touid;

target.touname=source.touname;

target.content=source.content;

target.isSh=source.isSh;

target.isVip=source.isVip;

target.goodnum=source.goodnum;

target.avatar=source.avatar;

target.guardType=source.guardType;

target.role=source.role;

target.styleType=source.styleType;

target.anchorAvatar=source.anchorAvatar;

target.anchorName=source.anchorName;

target.gifticon=source.gifticon;

target.giftname=source.giftname;

target.giftNumber=source.giftNumber;

target.anchorGrade=source.anchorGrade;

target.userGrade=source.userGrade;

target.wealthGrade=source.wealthGrade;

target.nobleGrade=source.nobleGrade;

target.isFans=source.isFans;

target.fontDiscoloration=source.fontDiscoloration;

target.roomId=source.roomId;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiSimpleMsgRoom> CREATOR = new Creator<ApiSimpleMsgRoom>() {
        @Override
        public ApiSimpleMsgRoom createFromParcel(Parcel in) {
            return new ApiSimpleMsgRoom(in);
        }

        @Override
        public ApiSimpleMsgRoom[] newArray(int size) {
            return new ApiSimpleMsgRoom[size];
        }
    };
}
