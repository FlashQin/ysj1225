package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;

import com.kalacheng.buscommon.model.*;




/**
 * APP加入直播响应
 */
public class AppJoinRoomVO  implements Parcelable
{
 public AppJoinRoomVO()
{
}

/**
 * 状态描述
 */
public String msg;

/**
 * 坐骑名称
 */
public String carName;

/**
 * 多人语音PK相关信息
 */
public com.kalacheng.libuser.model.VoicePkVO voicePkVO;

/**
 * PK时当前直播间血条值
 */
public int currVotesPk;

/**
 * 观看人数
 */
public int watchNumber;

/**
 * 房间试看时间(秒),0标识没有限制
 */
public int freeWatchTime;

/**
 * 当前用户贵族等级图标
 */
public String nobleGradeImg;

/**
 * 房间号
 */
public long roomId;

/**
 * 守护类型 1:普通守护 2:尊贵守护
 */
public int guardUsersType;

/**
 * 直播间观众列表
 */
public List<com.kalacheng.buscommon.model.ApiUserBasicInfo> userList;

/**
 * 直播状态 1:正常直播 2:连麦 3:互动 4:pk
 */
public int liveStatus;

/**
 * 房间模式 0:普通房间 1:私密房间 2:收费房间 3:计时房间 4:贵族房间
 */
public int roomType;

/**
 * 连麦状态 1:开 0:关
 */
public int ismic;

/**
 * 直播间推送警告内容
 */
public String noticeMsg;

/**
 * 是否有直播购 0:没有直播购 1:有直播购
 */
public int liveFunction;

/**
 * 是否是粉丝团成员 0:否 1:是
 */
public int isFans;

/**
 * PK时对方直播间血条值
 */
public int fromVotesPk;

/**
 * 主播id
 */
public long anchorId;

/**
 * 是否守护 1:是 0:否
 */
public int isSh;

/**
 * 推流地址
 */
public String push;

/**
 * 主播开关麦状态 0:关麦 1:开麦(音量)
 */
public int onOffState;

/**
 * 贵宾席头像地址
 */
public String seatsColor;

/**
 * 当前用户主播等级图标
 */
public String anchorGradeImg;

/**
 * 资源类型：0视频一般直播，1视频私密直播，2视频收费直播，3视频计时直播 4语音普通直播，5语音私密直播 6 one2one语音 7one2one视频 8文字动态 9视频动态 10图片动态11多人直播贵族房间12语音计时13语音付费14语音贵族
 */
public int sourceType;

/**
 * 房间模式对应的值 密码房间：密码  收费房间：收费金额
 */
public String roomTypeVal;

/**
 * 守护人数
 */
public int guardNumber;

/**
 * 是否有贵族弹幕特权 0:没有 1:有
 */
public int gzdmPrivilege;

/**
 * PK时剩余时间
 */
public int pkTime;

/**
 * 状态1成功2失败
 */
public int code;

/**
 * 角色 1:普通用户 2:管理员 3:主播
 */
public int role;

/**
 * 直播类型 1:视频 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 */
public int liveType;

/**
 * 用户头像
 */
public String userAvatar;

/**
 * 当前用户等级图标
 */
public String userGradeImg;

/**
 * 坐骑静态图
 */
public String carThumb;

/**
 * 封面图
 */
public String liveThumb;

/**
 * 主播名称
 */
public String anchorName;

/**
 * 推送内容
 */
public String content;

/**
 * 语音直播间背景
 */
public String voiceThumb;

/**
 * 直播横幅
 */
public String shopLiveBanner;

/**
 * 主播靓号
 */
public String anchorGoodNum;

/**
 * 语音直播间麦位列表
 */
public List<com.kalacheng.libuser.model.ApiUsersVoiceAssistan> assistanList;

/**
 * 分享配置信息
 */
public com.kalacheng.libuser.model.AppShareConfig share;

/**
 * 频道id
 */
public long channelId;

/**
 * 公告
 */
public String notice;

/**
 * 是否加入贵宾席 0:未加入 1: 加入
 */
public int isJoinSeats;

/**
 * 当前用户财富等级图标
 */
public String wealthGradeImg;

/**
 * 主播头像
 */
public String anchorAvatar;

/**
 * 用户靓号
 */
public String userGoodNum;

/**
 * 当前用户名称
 */
public String userName;

/**
 * 是否vip 1:是 0:否
 */
public int isVip;

/**
 * 当前用户主播等级图标
 */
public String levelImg;

/**
 * 当前用户贵族勋章logo
 */
public String nobleLogo;

/**
 * 性别 0：保密 1：男 2：女
 */
public int anchorSex;

/**
 * 是否关注 1:关注 0:未关注
 */
public int isFollow;

/**
 * 拉流地址
 */
public String pull;

/**
 * 直播间标识
 */
public String showid;

/**
 * 坐骑动态图
 */
public String carSwf;

/**
 * 当前魅力值
 */
public double votes;

/**
 * 坐骑动态图时长
 */
public String carSwftime;

/**
 * 0:没有在PK 1:房间Pk 2:单人Pk 3:激情团战
 */
public int pkType;

   public AppJoinRoomVO(Parcel in) 
{
msg=in.readString();
carName=in.readString();

voicePkVO=in.readParcelable(com.kalacheng.libuser.model.VoicePkVO.class.getClassLoader());
currVotesPk=in.readInt();
watchNumber=in.readInt();
freeWatchTime=in.readInt();
nobleGradeImg=in.readString();
roomId=in.readLong();
guardUsersType=in.readInt();

if(userList==null){
userList=  new ArrayList<>();
 }
in.readTypedList(userList,com.kalacheng.buscommon.model.ApiUserBasicInfo.CREATOR);
liveStatus=in.readInt();
roomType=in.readInt();
ismic=in.readInt();
noticeMsg=in.readString();
liveFunction=in.readInt();
isFans=in.readInt();
fromVotesPk=in.readInt();
anchorId=in.readLong();
isSh=in.readInt();
push=in.readString();
onOffState=in.readInt();
seatsColor=in.readString();
anchorGradeImg=in.readString();
sourceType=in.readInt();
roomTypeVal=in.readString();
guardNumber=in.readInt();
gzdmPrivilege=in.readInt();
pkTime=in.readInt();
code=in.readInt();
role=in.readInt();
liveType=in.readInt();
userAvatar=in.readString();
userGradeImg=in.readString();
carThumb=in.readString();
liveThumb=in.readString();
anchorName=in.readString();
content=in.readString();
voiceThumb=in.readString();
shopLiveBanner=in.readString();
anchorGoodNum=in.readString();

if(assistanList==null){
assistanList=  new ArrayList<>();
 }
in.readTypedList(assistanList,com.kalacheng.libuser.model.ApiUsersVoiceAssistan.CREATOR);

share=in.readParcelable(com.kalacheng.libuser.model.AppShareConfig.class.getClassLoader());
channelId=in.readLong();
notice=in.readString();
isJoinSeats=in.readInt();
wealthGradeImg=in.readString();
anchorAvatar=in.readString();
userGoodNum=in.readString();
userName=in.readString();
isVip=in.readInt();
levelImg=in.readString();
nobleLogo=in.readString();
anchorSex=in.readInt();
isFollow=in.readInt();
pull=in.readString();
showid=in.readString();
carSwf=in.readString();
votes=in.readDouble();
carSwftime=in.readString();
pkType=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(msg);
dest.writeString(carName);

dest.writeParcelable(voicePkVO,flags);
dest.writeInt(currVotesPk);
dest.writeInt(watchNumber);
dest.writeInt(freeWatchTime);
dest.writeString(nobleGradeImg);
dest.writeLong(roomId);
dest.writeInt(guardUsersType);

dest.writeTypedList(userList);
dest.writeInt(liveStatus);
dest.writeInt(roomType);
dest.writeInt(ismic);
dest.writeString(noticeMsg);
dest.writeInt(liveFunction);
dest.writeInt(isFans);
dest.writeInt(fromVotesPk);
dest.writeLong(anchorId);
dest.writeInt(isSh);
dest.writeString(push);
dest.writeInt(onOffState);
dest.writeString(seatsColor);
dest.writeString(anchorGradeImg);
dest.writeInt(sourceType);
dest.writeString(roomTypeVal);
dest.writeInt(guardNumber);
dest.writeInt(gzdmPrivilege);
dest.writeInt(pkTime);
dest.writeInt(code);
dest.writeInt(role);
dest.writeInt(liveType);
dest.writeString(userAvatar);
dest.writeString(userGradeImg);
dest.writeString(carThumb);
dest.writeString(liveThumb);
dest.writeString(anchorName);
dest.writeString(content);
dest.writeString(voiceThumb);
dest.writeString(shopLiveBanner);
dest.writeString(anchorGoodNum);

dest.writeTypedList(assistanList);

dest.writeParcelable(share,flags);
dest.writeLong(channelId);
dest.writeString(notice);
dest.writeInt(isJoinSeats);
dest.writeString(wealthGradeImg);
dest.writeString(anchorAvatar);
dest.writeString(userGoodNum);
dest.writeString(userName);
dest.writeInt(isVip);
dest.writeString(levelImg);
dest.writeString(nobleLogo);
dest.writeInt(anchorSex);
dest.writeInt(isFollow);
dest.writeString(pull);
dest.writeString(showid);
dest.writeString(carSwf);
dest.writeDouble(votes);
dest.writeString(carSwftime);
dest.writeInt(pkType);

}

  public static void cloneObj(AppJoinRoomVO source,AppJoinRoomVO target)
{

target.msg=source.msg;

target.carName=source.carName;
        if(source.voicePkVO==null)
        {
            target.voicePkVO=null;
        }else
        {
            VoicePkVO.cloneObj(source.voicePkVO,target.voicePkVO);
        }

target.currVotesPk=source.currVotesPk;

target.watchNumber=source.watchNumber;

target.freeWatchTime=source.freeWatchTime;

target.nobleGradeImg=source.nobleGradeImg;

target.roomId=source.roomId;

target.guardUsersType=source.guardUsersType;

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


target.liveStatus=source.liveStatus;

target.roomType=source.roomType;

target.ismic=source.ismic;

target.noticeMsg=source.noticeMsg;

target.liveFunction=source.liveFunction;

target.isFans=source.isFans;

target.fromVotesPk=source.fromVotesPk;

target.anchorId=source.anchorId;

target.isSh=source.isSh;

target.push=source.push;

target.onOffState=source.onOffState;

target.seatsColor=source.seatsColor;

target.anchorGradeImg=source.anchorGradeImg;

target.sourceType=source.sourceType;

target.roomTypeVal=source.roomTypeVal;

target.guardNumber=source.guardNumber;

target.gzdmPrivilege=source.gzdmPrivilege;

target.pkTime=source.pkTime;

target.code=source.code;

target.role=source.role;

target.liveType=source.liveType;

target.userAvatar=source.userAvatar;

target.userGradeImg=source.userGradeImg;

target.carThumb=source.carThumb;

target.liveThumb=source.liveThumb;

target.anchorName=source.anchorName;

target.content=source.content;

target.voiceThumb=source.voiceThumb;

target.shopLiveBanner=source.shopLiveBanner;

target.anchorGoodNum=source.anchorGoodNum;

        if(source.assistanList==null)
        {
            target.assistanList=null;
        }else
        {
            target.assistanList=new ArrayList();
            for(int i=0;i<source.assistanList.size();i++)
            {
            ApiUsersVoiceAssistan.cloneObj(source.assistanList.get(i),target.assistanList.get(i));
            }
        }

        if(source.share==null)
        {
            target.share=null;
        }else
        {
            AppShareConfig.cloneObj(source.share,target.share);
        }

target.channelId=source.channelId;

target.notice=source.notice;

target.isJoinSeats=source.isJoinSeats;

target.wealthGradeImg=source.wealthGradeImg;

target.anchorAvatar=source.anchorAvatar;

target.userGoodNum=source.userGoodNum;

target.userName=source.userName;

target.isVip=source.isVip;

target.levelImg=source.levelImg;

target.nobleLogo=source.nobleLogo;

target.anchorSex=source.anchorSex;

target.isFollow=source.isFollow;

target.pull=source.pull;

target.showid=source.showid;

target.carSwf=source.carSwf;

target.votes=source.votes;

target.carSwftime=source.carSwftime;

target.pkType=source.pkType;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppJoinRoomVO> CREATOR = new Creator<AppJoinRoomVO>() {
        @Override
        public AppJoinRoomVO createFromParcel(Parcel in) {
            return new AppJoinRoomVO(in);
        }

        @Override
        public AppJoinRoomVO[] newArray(int size) {
            return new AppJoinRoomVO[size];
        }
    };
}
