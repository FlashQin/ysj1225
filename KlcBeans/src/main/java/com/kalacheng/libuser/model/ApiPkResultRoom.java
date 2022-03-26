package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * APPPK返回结果
 */
public class ApiPkResultRoom  implements Parcelable
{
 public ApiPkResultRoom()
{
}

/**
 * 麦位列表
 */
public List<com.kalacheng.libuser.model.ApiUsersVoiceAssistan> assistans;

/**
 * PK惩罚/平局时间,单位:秒
 */
public int punishTime;

/**
 * 胜利者用户名称
 */
public String winUserName;

/**
 * 推送内容
 */
public String content;

/**
 * 房间id
 */
public long roomId;

/**
 * 胜利者用户头像
 */
public String winUserAvatar;

/**
 * 单人/激情团战PK结果1胜利0平局-1输了(如果是房间PK,1:1队胜利,0:平局,2:2队胜利)
 */
public int isWin;

/**
 * 1:单人pk(语音/视频) 2:激情团战 3:房间pk
 */
public int pkType;

/**
 * 胜利者用户id
 */
public long winUid;

   public ApiPkResultRoom(Parcel in) 
{

if(assistans==null){
assistans=  new ArrayList<>();
 }
in.readTypedList(assistans,com.kalacheng.libuser.model.ApiUsersVoiceAssistan.CREATOR);
punishTime=in.readInt();
winUserName=in.readString();
content=in.readString();
roomId=in.readLong();
winUserAvatar=in.readString();
isWin=in.readInt();
pkType=in.readInt();
winUid=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(assistans);
dest.writeInt(punishTime);
dest.writeString(winUserName);
dest.writeString(content);
dest.writeLong(roomId);
dest.writeString(winUserAvatar);
dest.writeInt(isWin);
dest.writeInt(pkType);
dest.writeLong(winUid);

}

  public static void cloneObj(ApiPkResultRoom source,ApiPkResultRoom target)
{

        if(source.assistans==null)
        {
            target.assistans=null;
        }else
        {
            target.assistans=new ArrayList();
            for(int i=0;i<source.assistans.size();i++)
            {
            ApiUsersVoiceAssistan.cloneObj(source.assistans.get(i),target.assistans.get(i));
            }
        }


target.punishTime=source.punishTime;

target.winUserName=source.winUserName;

target.content=source.content;

target.roomId=source.roomId;

target.winUserAvatar=source.winUserAvatar;

target.isWin=source.isWin;

target.pkType=source.pkType;

target.winUid=source.winUid;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiPkResultRoom> CREATOR = new Creator<ApiPkResultRoom>() {
        @Override
        public ApiPkResultRoom createFromParcel(Parcel in) {
            return new ApiPkResultRoom(in);
        }

        @Override
        public ApiPkResultRoom[] newArray(int size) {
            return new ApiPkResultRoom[size];
        }
    };
}
