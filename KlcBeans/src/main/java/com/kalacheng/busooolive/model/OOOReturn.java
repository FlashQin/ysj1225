package com.kalacheng.busooolive.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busooolive.model.*;




/**
 * 一对一返回
 */
public class OOOReturn  implements Parcelable
{
 public OOOReturn()
{
}

/**
 * 消费者是否打开麦克风
 */
public int feeOpenVolumn;

/**
 * 免费通话提示信息
 */
public String freeCallMsg;

/**
 * 角色0普通用户1主播
 */
public int role;

/**
 * 直播间推送警告内容
 */
public String noticeMsg;

/**
 * 主持人id
 */
public long hostId;

/**
 * 是否为视频通话 1视频 0语音
 */
public int isVideo;

/**
 * 主播头像
 */
public String avatar;

/**
 * 会话ID
 */
public long sessionID;

/**
 * 送礼物类型 1:多人视频 2:语音直播 3:一对一 4:派对 5:电台 6:直播购物
 */
public int type;

/**
 * 主持人用户名称
 */
public String userName;

/**
 * 消费者id
 */
public long feeId;

/**
 * 房间号
 */
public long roomId;

/**
 * 本场总火力值
 */
public int hotvotes;

/**
 * 头像缩略图
 */
public String avatarThumb;

/**
 * 是否打开麦克风
 */
public int isOpenVolumn;

/**
 * 消费者用户名称
 */
public String feeUserName;

/**
 * 直播标识
 */
public String showid;

/**
 * 是否被关注
 */
public int isAtten;

/**
 * 是否是用户和用户通话 0:不是 1:是
 */
public int userToUser;

/**
 * 副播实体集合
 */
public List<com.kalacheng.busooolive.model.OTMAssisRet> assisRets;

/**
 * 消费者头像
 */
public String feeAvatar;

/**
 * 类型 1:一对一正常通话 2:抢聊通话
 */
public int m_type;

   public OOOReturn(Parcel in) 
{
feeOpenVolumn=in.readInt();
freeCallMsg=in.readString();
role=in.readInt();
noticeMsg=in.readString();
hostId=in.readLong();
isVideo=in.readInt();
avatar=in.readString();
sessionID=in.readLong();
type=in.readInt();
userName=in.readString();
feeId=in.readLong();
roomId=in.readLong();
hotvotes=in.readInt();
avatarThumb=in.readString();
isOpenVolumn=in.readInt();
feeUserName=in.readString();
showid=in.readString();
isAtten=in.readInt();
userToUser=in.readInt();

if(assisRets==null){
assisRets=  new ArrayList<>();
 }
in.readTypedList(assisRets,com.kalacheng.busooolive.model.OTMAssisRet.CREATOR);
feeAvatar=in.readString();
m_type=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(feeOpenVolumn);
dest.writeString(freeCallMsg);
dest.writeInt(role);
dest.writeString(noticeMsg);
dest.writeLong(hostId);
dest.writeInt(isVideo);
dest.writeString(avatar);
dest.writeLong(sessionID);
dest.writeInt(type);
dest.writeString(userName);
dest.writeLong(feeId);
dest.writeLong(roomId);
dest.writeInt(hotvotes);
dest.writeString(avatarThumb);
dest.writeInt(isOpenVolumn);
dest.writeString(feeUserName);
dest.writeString(showid);
dest.writeInt(isAtten);
dest.writeInt(userToUser);

dest.writeTypedList(assisRets);
dest.writeString(feeAvatar);
dest.writeInt(m_type);

}

  public static void cloneObj(OOOReturn source,OOOReturn target)
{

target.feeOpenVolumn=source.feeOpenVolumn;

target.freeCallMsg=source.freeCallMsg;

target.role=source.role;

target.noticeMsg=source.noticeMsg;

target.hostId=source.hostId;

target.isVideo=source.isVideo;

target.avatar=source.avatar;

target.sessionID=source.sessionID;

target.type=source.type;

target.userName=source.userName;

target.feeId=source.feeId;

target.roomId=source.roomId;

target.hotvotes=source.hotvotes;

target.avatarThumb=source.avatarThumb;

target.isOpenVolumn=source.isOpenVolumn;

target.feeUserName=source.feeUserName;

target.showid=source.showid;

target.isAtten=source.isAtten;

target.userToUser=source.userToUser;

        if(source.assisRets==null)
        {
            target.assisRets=null;
        }else
        {
            target.assisRets=new ArrayList();
            for(int i=0;i<source.assisRets.size();i++)
            {
            OTMAssisRet.cloneObj(source.assisRets.get(i),target.assisRets.get(i));
            }
        }


target.feeAvatar=source.feeAvatar;

target.m_type=source.m_type;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OOOReturn> CREATOR = new Creator<OOOReturn>() {
        @Override
        public OOOReturn createFromParcel(Parcel in) {
            return new OOOReturn(in);
        }

        @Override
        public OOOReturn[] newArray(int size) {
            return new OOOReturn[size];
        }
    };
}
