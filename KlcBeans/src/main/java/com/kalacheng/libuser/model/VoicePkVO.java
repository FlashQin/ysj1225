package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 语音直播间PK信息
 */
public class VoicePkVO  implements Parcelable
{
 public VoicePkVO()
{
}

/**
 * 对方房间showid
 */
public String otherShowId;

/**
 * 对方直播间总的PK礼物值 (房间PK时是右队)
 */
public double otherTotalVotes;

/**
 * 对方主播性别
 */
public int otherSex;

/**
 * 对面房主礼物值 （房间PK时为右队）
 */
public double otherAnchorVotes;

/**
 * 当前PK房间送礼排行榜 （房间PK时为左队的排行榜）
 */
public List<com.kalacheng.libuser.model.PkGiftSender> thisSenders;

/**
 * 对面表情包ID
 */
public long otherStrickerId;

/**
 * 对手Pk房间送礼排行榜 （房间PK时为右队的排行榜）
 */
public List<com.kalacheng.libuser.model.PkGiftSender> otherSenders;

/**
 * 对方直播间房主ID
 */
public long otherUid;

/**
 * PK开始倒计时时长 单位秒
 */
public long pkCountdownMillis;

/**
 * 当前总的PK礼物值 (房间PK时是左队)
 */
public double totalVotes;

/**
 * 当前房间showid
 */
public String thisShowId;

/**
 * 当前房主ID
 */
public long thisUid;

/**
 * 当前房主用户名
 */
public String thisUsername;

/**
 * 表情包ID
 */
public long strickerId;

/**
 * 本房间麦位副播 (房间PK时为左边麦位 )
 */
public List<com.kalacheng.libuser.model.PkUserVoiceAssistan> thisAssistans;

/**
 * PK发起时主播麦克风  1开启 0关闭 默认开启 
 */
public int hostVolumn;

/**
 * pk进行状态 1:匹配成功通知主播环节 2:倒计时环节 3:正式PK环节 4:PK结果展示环节 5:PK结束
 */
public int pkProcess;

/**
 * 当前主播性别
 */
public int thisSex;

/**
 * 对方房间副播麦位集合 (房间PK时为右边麦位 )
 */
public List<com.kalacheng.libuser.model.PkUserVoiceAssistan> otherAssistans;

/**
 * 当前房主头像
 */
public String thisAvatar;

/**
 * 当前房主礼物值 （房间PK时为左队）
 */
public double anchorVotes;

/**
 * 当前Pk进行状态的截止时刻
 */
public long processEndMills;

/**
 * 对方房主用户名
 */
public String otherUsername;

/**
 * 对面表情包URL
 */
public String otherStrickerURL;

/**
 * 当前房间ID
 */
public long thisRoomID;

/**
 * PK发起时对手主播麦克风  1开启 0关闭 默认开启 
 */
public int otherHostVolumn;

/**
 * PK结果信息 0:不在PK中 1:匹配成功通知主播环节 2:倒计时环节 3:正式PK环节 4:PK结果展示环节 5:PK结束
 */
public com.kalacheng.libuser.model.ApiPkResultRoom pkResultRoom;

/**
 * 当前Pk进行状态的结束时长 单位：秒
 */
public long processEndTime;

/**
 * pk随机主题
 */
public String rdTitle;

/**
 * 对方直播间房主的头像
 */
public String otherAvatar;

/**
 * PK时长 单位秒
 */
public long pkMillis;

/**
 * 对方房间ID
 */
public long otherRoomID;

/**
 * 表情包URL
 */
public String strickerURL;

/**
 * pk类型 1:房间PK 2:单人PK 3:激情团战
 */
public int pkType;

   public VoicePkVO(Parcel in) 
{
otherShowId=in.readString();
otherTotalVotes=in.readDouble();
otherSex=in.readInt();
otherAnchorVotes=in.readDouble();

if(thisSenders==null){
thisSenders=  new ArrayList<>();
 }
in.readTypedList(thisSenders,com.kalacheng.libuser.model.PkGiftSender.CREATOR);
otherStrickerId=in.readLong();

if(otherSenders==null){
otherSenders=  new ArrayList<>();
 }
in.readTypedList(otherSenders,com.kalacheng.libuser.model.PkGiftSender.CREATOR);
otherUid=in.readLong();
pkCountdownMillis=in.readLong();
totalVotes=in.readDouble();
thisShowId=in.readString();
thisUid=in.readLong();
thisUsername=in.readString();
strickerId=in.readLong();

if(thisAssistans==null){
thisAssistans=  new ArrayList<>();
 }
in.readTypedList(thisAssistans,com.kalacheng.libuser.model.PkUserVoiceAssistan.CREATOR);
hostVolumn=in.readInt();
pkProcess=in.readInt();
thisSex=in.readInt();

if(otherAssistans==null){
otherAssistans=  new ArrayList<>();
 }
in.readTypedList(otherAssistans,com.kalacheng.libuser.model.PkUserVoiceAssistan.CREATOR);
thisAvatar=in.readString();
anchorVotes=in.readDouble();
processEndMills=in.readLong();
otherUsername=in.readString();
otherStrickerURL=in.readString();
thisRoomID=in.readLong();
otherHostVolumn=in.readInt();

pkResultRoom=in.readParcelable(com.kalacheng.libuser.model.ApiPkResultRoom.class.getClassLoader());
processEndTime=in.readLong();
rdTitle=in.readString();
otherAvatar=in.readString();
pkMillis=in.readLong();
otherRoomID=in.readLong();
strickerURL=in.readString();
pkType=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(otherShowId);
dest.writeDouble(otherTotalVotes);
dest.writeInt(otherSex);
dest.writeDouble(otherAnchorVotes);

dest.writeTypedList(thisSenders);
dest.writeLong(otherStrickerId);

dest.writeTypedList(otherSenders);
dest.writeLong(otherUid);
dest.writeLong(pkCountdownMillis);
dest.writeDouble(totalVotes);
dest.writeString(thisShowId);
dest.writeLong(thisUid);
dest.writeString(thisUsername);
dest.writeLong(strickerId);

dest.writeTypedList(thisAssistans);
dest.writeInt(hostVolumn);
dest.writeInt(pkProcess);
dest.writeInt(thisSex);

dest.writeTypedList(otherAssistans);
dest.writeString(thisAvatar);
dest.writeDouble(anchorVotes);
dest.writeLong(processEndMills);
dest.writeString(otherUsername);
dest.writeString(otherStrickerURL);
dest.writeLong(thisRoomID);
dest.writeInt(otherHostVolumn);

dest.writeParcelable(pkResultRoom,flags);
dest.writeLong(processEndTime);
dest.writeString(rdTitle);
dest.writeString(otherAvatar);
dest.writeLong(pkMillis);
dest.writeLong(otherRoomID);
dest.writeString(strickerURL);
dest.writeInt(pkType);

}

  public static void cloneObj(VoicePkVO source,VoicePkVO target)
{

target.otherShowId=source.otherShowId;

target.otherTotalVotes=source.otherTotalVotes;

target.otherSex=source.otherSex;

target.otherAnchorVotes=source.otherAnchorVotes;

        if(source.thisSenders==null)
        {
            target.thisSenders=null;
        }else
        {
            target.thisSenders=new ArrayList();
            for(int i=0;i<source.thisSenders.size();i++)
            {
            PkGiftSender.cloneObj(source.thisSenders.get(i),target.thisSenders.get(i));
            }
        }


target.otherStrickerId=source.otherStrickerId;

        if(source.otherSenders==null)
        {
            target.otherSenders=null;
        }else
        {
            target.otherSenders=new ArrayList();
            for(int i=0;i<source.otherSenders.size();i++)
            {
            PkGiftSender.cloneObj(source.otherSenders.get(i),target.otherSenders.get(i));
            }
        }


target.otherUid=source.otherUid;

target.pkCountdownMillis=source.pkCountdownMillis;

target.totalVotes=source.totalVotes;

target.thisShowId=source.thisShowId;

target.thisUid=source.thisUid;

target.thisUsername=source.thisUsername;

target.strickerId=source.strickerId;

        if(source.thisAssistans==null)
        {
            target.thisAssistans=null;
        }else
        {
            target.thisAssistans=new ArrayList();
            for(int i=0;i<source.thisAssistans.size();i++)
            {
            PkUserVoiceAssistan.cloneObj(source.thisAssistans.get(i),target.thisAssistans.get(i));
            }
        }


target.hostVolumn=source.hostVolumn;

target.pkProcess=source.pkProcess;

target.thisSex=source.thisSex;

        if(source.otherAssistans==null)
        {
            target.otherAssistans=null;
        }else
        {
            target.otherAssistans=new ArrayList();
            for(int i=0;i<source.otherAssistans.size();i++)
            {
            PkUserVoiceAssistan.cloneObj(source.otherAssistans.get(i),target.otherAssistans.get(i));
            }
        }


target.thisAvatar=source.thisAvatar;

target.anchorVotes=source.anchorVotes;

target.processEndMills=source.processEndMills;

target.otherUsername=source.otherUsername;

target.otherStrickerURL=source.otherStrickerURL;

target.thisRoomID=source.thisRoomID;

target.otherHostVolumn=source.otherHostVolumn;
        if(source.pkResultRoom==null)
        {
            target.pkResultRoom=null;
        }else
        {
            ApiPkResultRoom.cloneObj(source.pkResultRoom,target.pkResultRoom);
        }

target.processEndTime=source.processEndTime;

target.rdTitle=source.rdTitle;

target.otherAvatar=source.otherAvatar;

target.pkMillis=source.pkMillis;

target.otherRoomID=source.otherRoomID;

target.strickerURL=source.strickerURL;

target.pkType=source.pkType;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VoicePkVO> CREATOR = new Creator<VoicePkVO>() {
        @Override
        public VoicePkVO createFromParcel(Parcel in) {
            return new VoicePkVO(in);
        }

        @Override
        public VoicePkVO[] newArray(int size) {
            return new VoicePkVO[size];
        }
    };
}
