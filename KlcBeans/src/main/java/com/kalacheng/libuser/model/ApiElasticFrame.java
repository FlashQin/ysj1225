package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 弹框socket数据
 */
public class ApiElasticFrame  implements Parcelable
{
 public ApiElasticFrame()
{
}

/**
 * null
 */
public long serialVersionUID;

/**
 * 房间id
 */
public long roomid;

/**
 * 用户名称
 */
public String userName;

/**
 * 用户id
 */
public long uid;

/**
 * 用户头像
 */
public String avatar;

/**
 * 消息内容
 */
public String content;

/**
 * 消息类型 1:用戶升级 2:完成任务 3:获得勋章 4:开通贵族
 */
public int type;

/**
 * type = 1时 childType: 1:用户等级升级  2：主播等级升级  3：财富等级升级   （贵族无等级通知，仅勋章通知即可）;type = 2时 childType: 1:用户任务（限时任务）  2：主播任务（累计任务）;type = 3时 childType: 1:普通勋章  2：财富勋章  3：贵族勋章
 */
public int childType;

/**
 * 对应childType名称
 */
public String childTypeName;

/**
 * 贵族名称(会员)
 */
public String vipName;

/**
 * 等级
 */
public String grade;

/**
 * 任务标题
 */
public String taskName;

/**
 * 经验值
 */
public int taskPoint;

/**
 * 勋章名称
 */
public String medalName;

/**
 * 勋章LOGO
 */
public String medalLogo;

   public ApiElasticFrame(Parcel in) 
{
serialVersionUID=in.readLong();
roomid=in.readLong();
userName=in.readString();
uid=in.readLong();
avatar=in.readString();
content=in.readString();
type=in.readInt();
childType=in.readInt();
childTypeName=in.readString();
vipName=in.readString();
grade=in.readString();
taskName=in.readString();
taskPoint=in.readInt();
medalName=in.readString();
medalLogo=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(serialVersionUID);
dest.writeLong(roomid);
dest.writeString(userName);
dest.writeLong(uid);
dest.writeString(avatar);
dest.writeString(content);
dest.writeInt(type);
dest.writeInt(childType);
dest.writeString(childTypeName);
dest.writeString(vipName);
dest.writeString(grade);
dest.writeString(taskName);
dest.writeInt(taskPoint);
dest.writeString(medalName);
dest.writeString(medalLogo);

}

  public static void cloneObj(ApiElasticFrame source,ApiElasticFrame target)
{

target.serialVersionUID=source.serialVersionUID;

target.roomid=source.roomid;

target.userName=source.userName;

target.uid=source.uid;

target.avatar=source.avatar;

target.content=source.content;

target.type=source.type;

target.childType=source.childType;

target.childTypeName=source.childTypeName;

target.vipName=source.vipName;

target.grade=source.grade;

target.taskName=source.taskName;

target.taskPoint=source.taskPoint;

target.medalName=source.medalName;

target.medalLogo=source.medalLogo;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiElasticFrame> CREATOR = new Creator<ApiElasticFrame>() {
        @Override
        public ApiElasticFrame createFromParcel(Parcel in) {
            return new ApiElasticFrame(in);
        }

        @Override
        public ApiElasticFrame[] newArray(int size) {
            return new ApiElasticFrame[size];
        }
    };
}
