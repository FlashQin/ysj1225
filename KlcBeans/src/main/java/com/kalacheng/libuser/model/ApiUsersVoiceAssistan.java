package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP语音直播麦位数据
 */
public class ApiUsersVoiceAssistan  implements Parcelable
{
 public ApiUsersVoiceAssistan()
{
}

/**
 * 状态描述
 */
public String msg;

/**
 * 封麦状态 0:封麦 1:未封麦
 */
public int retireState;

/**
 * 麦序编号
 */
public int no;

/**
 * 状态 1:成功 2:失败
 */
public int code;

/**
 * 该麦位是否被禁言 1:是 0:否 默认0
 */
public int isShutUp;

/**
 * 音量值
 */
public int volumeVal;

/**
 * 性别 1:男 2:女 3:其他 0:麦位为空
 */
public int sex;

/**
 * 当前主持id
 */
public long anchorId;

/**
 * 麦序类型 1:主播 2:普通用户
 */
public int type;

/**
 * 用户名称
 */
public String userName;

/**
 * 房间id
 */
public long roomId;

/**
 * 麦序名称
 */
public String assistanName;

/**
 * 开关麦状态 0:关麦 1:开麦(音量)
 */
public int onOffState;

/**
 * 用户头像
 */
public String avatarThumb;

/**
 * 麦序上用户id
 */
public long uid;

/**
 * 表情包URL
 */
public String strickerURL;

/**
 * 用户收益
 */
public double coin;

/**
 * 麦位状态 0:无人 1:有人
 */
public int status;

/**
 * 表情包ID
 */
public long strickerId;

   public ApiUsersVoiceAssistan(Parcel in) 
{
msg=in.readString();
retireState=in.readInt();
no=in.readInt();
code=in.readInt();
isShutUp=in.readInt();
volumeVal=in.readInt();
sex=in.readInt();
anchorId=in.readLong();
type=in.readInt();
userName=in.readString();
roomId=in.readLong();
assistanName=in.readString();
onOffState=in.readInt();
avatarThumb=in.readString();
uid=in.readLong();
strickerURL=in.readString();
coin=in.readDouble();
status=in.readInt();
strickerId=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(msg);
dest.writeInt(retireState);
dest.writeInt(no);
dest.writeInt(code);
dest.writeInt(isShutUp);
dest.writeInt(volumeVal);
dest.writeInt(sex);
dest.writeLong(anchorId);
dest.writeInt(type);
dest.writeString(userName);
dest.writeLong(roomId);
dest.writeString(assistanName);
dest.writeInt(onOffState);
dest.writeString(avatarThumb);
dest.writeLong(uid);
dest.writeString(strickerURL);
dest.writeDouble(coin);
dest.writeInt(status);
dest.writeLong(strickerId);

}

  public static void cloneObj(ApiUsersVoiceAssistan source,ApiUsersVoiceAssistan target)
{

target.msg=source.msg;

target.retireState=source.retireState;

target.no=source.no;

target.code=source.code;

target.isShutUp=source.isShutUp;

target.volumeVal=source.volumeVal;

target.sex=source.sex;

target.anchorId=source.anchorId;

target.type=source.type;

target.userName=source.userName;

target.roomId=source.roomId;

target.assistanName=source.assistanName;

target.onOffState=source.onOffState;

target.avatarThumb=source.avatarThumb;

target.uid=source.uid;

target.strickerURL=source.strickerURL;

target.coin=source.coin;

target.status=source.status;

target.strickerId=source.strickerId;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiUsersVoiceAssistan> CREATOR = new Creator<ApiUsersVoiceAssistan>() {
        @Override
        public ApiUsersVoiceAssistan createFromParcel(Parcel in) {
            return new ApiUsersVoiceAssistan(in);
        }

        @Override
        public ApiUsersVoiceAssistan[] newArray(int size) {
            return new ApiUsersVoiceAssistan[size];
        }
    };
}
