package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 后台管理-系统配置-直播管理
 */
public class AdminLiveManage  implements Parcelable
{
 public AdminLiveManage()
{
}

/**
 * 语音房间类型 0:普通房间 1:是私密直播
 */
public String voiceRoomType;

/**
 * 计时收费梯价
 */
public String liveTimeCoin;

/**
 * 虚拟货币单位
 */
public String vcUnit;

/**
 * 判断是直播还是语音
 */
public int isVideo;

/**
 * null
 */
public long id;

/**
 * 直播画质 0:标清 1:高清 2:超清
 */
public int imageQuality;

/**
 * 密码房间密码长度
 */
public int passwordLength;

/**
 * 多人直播间房间类型 0:普通房间 1:是私密直播 2:是收费直播 3:是计时直播 4:贵族房间
 */
public String roomType;

   public AdminLiveManage(Parcel in) 
{
voiceRoomType=in.readString();
liveTimeCoin=in.readString();
vcUnit=in.readString();
isVideo=in.readInt();
id=in.readLong();
imageQuality=in.readInt();
passwordLength=in.readInt();
roomType=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(voiceRoomType);
dest.writeString(liveTimeCoin);
dest.writeString(vcUnit);
dest.writeInt(isVideo);
dest.writeLong(id);
dest.writeInt(imageQuality);
dest.writeInt(passwordLength);
dest.writeString(roomType);

}

  public static void cloneObj(AdminLiveManage source,AdminLiveManage target)
{

target.voiceRoomType=source.voiceRoomType;

target.liveTimeCoin=source.liveTimeCoin;

target.vcUnit=source.vcUnit;

target.isVideo=source.isVideo;

target.id=source.id;

target.imageQuality=source.imageQuality;

target.passwordLength=source.passwordLength;

target.roomType=source.roomType;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdminLiveManage> CREATOR = new Creator<AdminLiveManage>() {
        @Override
        public AdminLiveManage createFromParcel(Parcel in) {
            return new AdminLiveManage(in);
        }

        @Override
        public AdminLiveManage[] newArray(int size) {
            return new AdminLiveManage[size];
        }
    };
}
