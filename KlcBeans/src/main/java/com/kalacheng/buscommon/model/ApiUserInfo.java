package com.kalacheng.buscommon.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;

import com.kalacheng.buscommon.model.*;




/**
 * APP登录接口返回值
 */
public class ApiUserInfo  implements Parcelable
{
 public ApiUserInfo()
{
}

/**
 * 加入房间隐身 0:不隐身 1:隐身
 */
public int joinRoomShow;

/**
 * 用户真实在线状态 0:离线 1:在线
 */
public int onlineStatus;

/**
 * 所有勋章-勋章墙（3个）
 */
public List<com.kalacheng.libuser.model.AppMedal> threeMedal;

/**
 * 用户勋章logo
 */
public String userLogo;

/**
 * 贵族等级特权
 */
public List<com.kalacheng.libuser.model.AppGradePrivilege> nobleGradePrivilegeList;

/**
 * 用户设置的在线状态 0:在线 1:忙碌 2:离开 3:通话中
 */
public int userSetOnlineStatus;

/**
 * 点赞数
 */
public int likeNum;

/**
 * 当前用户跟直播间的关系 1:当前直播间主播 2:管理员 3:普通用户
 */
public int relation;

/**
 * 谁看过我人数
 */
public int readMeUsersNumber;

/**
 * 对方Uid 包括：连麦/互动/PK
 */
public long otherUid;

/**
 * 是否开启青少年模式 1:开启 2:未开启
 */
public int isYouthModel;

/**
 * 星座
 */
public String constellation;

/**
 * 省份
 */
public String province;

/**
 * 封面轮播
 */
public List<com.kalacheng.libuser.model.AppCoverExamine> userCovers;

/**
 * 是否关闭提示音 0:开启 1:关闭
 */
public int isTone;

/**
 * 0:直播 1:是演示视频的直播
 */
public int roomIsVideo;

/**
 * 直播状态 0:未进行直播 1:直播主播 2:直播观众
 */
public int liveStatus;

/**
 * 房间类型 0:普通房间 1:密码房间 2:门票房间 3:计时房间 4:贵族房间
 */
public int roomType;

/**
 * 时间轴
 */
public List<com.kalacheng.libuser.model.AppTrendsRecord> trends;

/**
 * 身高
 */
public int height;

/**
 * 查看微信号所需金币 (男朋友中为钻石)
 */
public double wechatCoin;

/**
 * 是否允许连麦 0:关 1:开
 */
public int canLinkMic;

/**
 * 关注我的数量（粉丝数量）
 */
public int fansNum;

/**
 * 经度
 */
public double lng;

/**
 * 用户等级
 */
public int userGrade;

/**
 * 配置相关
 */
public com.kalacheng.libuser.model.AdminLiveConfig adminLiveConfig;

/**
 * 是否僵尸粉 1:否 0:是
 */
public int iszombiep;

/**
 * PK过程的ID
 */
public long roomPkSid;

/**
 * 主播等级图片
 */
public String anchorGradeImg;

/**
 * 财富等级
 */
public int wealthGrade;

/**
 * 海报
 */
public String poster;

/**
 * 总收益映票
 */
public double totalIncomeVotes;

/**
 * 用户状态 1:禁用 0:解禁
 */
public int status;

/**
 * 兴趣标签列表展示
 */
public com.kalacheng.buscommon.model.TabTypeDto interestListStr;

/**
 * 角色 0:普通用户 1:主播
 */
public int role;

/**
 * 谁看过我是否需要贵族开关 0:需要 1:不需要
 */
public int isVipSee;

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
 * 剩余的注册赠送通话次数
 */
public int registerCallSecond;

/**
 * 直播间 PK过程中 的魅力值
 */
public double roomPKVotes;

/**
 * 与他人链接的会话ID（互动会话ID）
 */
public long linkOtherSid;

/**
 * 短视频剩余可观看私密视频次数
 */
public int readShortVideoNumber;

/**
 * 用户ID
 */
public long uid;

/**
 * 充值隐身 0:不隐身 1:隐身
 */
public int chargeShow;

/**
 * 是否填写邀请码 1:不弹出 0:弹出
 */
public int isAlertCode;

/**
 * 对方用户跟直播间的关系 1:当前直播间主播 2:管理员 3:普通用户
 */
public int toRelation;

/**
 * 财富等级特权
 */
public List<com.kalacheng.libuser.model.AppGradePrivilege> wealthGradePrivilegeList;

/**
 * 一对一直播状态 0:未进行直播 1:通话中 2:邀请他人通话 3:正在被邀请
 */
public int oooLiveStatus;

/**
 * 体重Str
 */
public String weightStr;

/**
 * 地址
 */
public String address;

/**
 * 是否禁言 1:已禁言 0:未禁言
 */
public int isShutUp;

/**
 * 商城内科学计数转换
 */
public String coinShow;

/**
 * 性别 1:男 2:女
 */
public int sex;

/**
 * 是否为VIP 0:非vip 1:是vip
 */
public int vipType;

/**
 * 用户任务列表
 */
public List<com.kalacheng.buscommon.model.TaskDto> userTaskList;

/**
 * 头像
 */
public String avatar;

/**
 * 用户ID
 */
public long userId;

/**
 * 职业
 */
public String vocation;

/**
 * 直播间魅力值
 */
public double roomVotes;

/**
 * 注册时间
 */
public String createTime;

/**
 * 映票余额/可提现金额
 */
public double votes;

/**
 * 贡献榜排行隐身 0:不隐身 1:隐身
 */
public int devoteShow;

/**
 * 主播等级
 */
public int anchorGrade;

/**
 * 与他人链接的状态(互动状态) 0:未链接 1:邀请他人连麦 2:被邀请连麦 3:连麦进行中 11:邀请他人互动 12:被邀请互动 13:互动进行中 21:邀请他人PK 22:被邀请PK 23:PK中
 */
public int linkOtherStatus;

/**
 * 守护我的List
 */
public List<com.kalacheng.buscommon.model.GuardUserDto> guardMyList;

/**
 * 群id
 */
public long groupId;

/**
 * 打赏人数
 */
public int rewardNum;

/**
 * 贵族名称
 */
public String nobleName;

/**
 * 被禁麦(麦克风) 0: 可以发言(默认) 1:被禁止发言
 */
public int noTalking;

/**
 * pid
 */
public long pid;

/**
 * 贵族等级图片
 */
public String nobleGradeImg;

/**
 * 直播房间号
 */
public long roomId;

/**
 * 连续登录奖励
 */
public com.kalacheng.libuser.model.AdminLoginAward adminLoginAward;

/**
 * 主播审核状态
 */
public int anchorAuditStatus;

/**
 * 全站广播功能 0:关闭功能 1:开启功能
 */
public int broadCast;

/**
 * 是否能开播 1:否 0:是
 */
public int isLive;

/**
 * 是否开启僵尸粉 1:未开启(默认) 0:已开启
 */
public int iszombie;

/**
 * 主播等级特权
 */
public List<com.kalacheng.libuser.model.AppGradePrivilege> anchorGradePrivilegeList;

/**
 * 纬度
 */
public double lat;

/**
 * 财富勋章logo
 */
public String wealthLogo;

/**
 * 兴趣标签列表
 */
public List<com.kalacheng.buscommon.model.TabTypeDto> interestList;

/**
 * 是否第一次登录 1:是 2:不是
 */
public int isFirstLogin;

/**
 * 关注状态 0:未关注， 1：已关注
 */
public int followStatus;

/**
 * 是否超管 1:否 0:是
 */
public int issuper;

/**
 * 体重
 */
public double weight;

/**
 * 主播审核原因
 */
public String anchorAuditReason;

/**
 * 是否开启定位显示 0:开启 1:未开启
 */
public int whetherEnablePositioningShow;

/**
 * 主播评价
 */
public List<com.kalacheng.libuser.model.ApiUserComment> userCommentList;

/**
 * 我关注的数量
 */
public int followNum;

/**
 * 麦克风状态 0: 关闭 1:开启
 */
public int hostVolumed;

/**
 * 多人语音直播状态 0:不在语音房间中 2:上麦标识 3:被邀上麦中 4:被踢下麦 5:下麦标识 6:申请上麦中 8:被踢出房间
 */
public int voiceStatus;

/**
 * 总消费金币 (男朋友中为钻石)
 */
public double totalConsumeCoin;

/**
 * 用户等级特权
 */
public List<com.kalacheng.libuser.model.AppGradePrivilege> userGradePrivilegeList;

/**
 * 礼物墙
 */
public List<com.kalacheng.buscommon.model.GiftWallDto> giftWall;

/**
 * 是否开通SVIP服务 1:是 0:否
 */
public int isSvip;

/**
 * 金币/充值金额 (男朋友中为钻石)
 */
public double coin;

/**
 * 生日
 */
public String birthday;

/**
 * 是否开启消息推送 0:开启 1:关闭
 */
public int isPush;

/**
 * 直播类型 0:没有直播,无类型 1:视频直播  2:语音直播
 */
public int liveType;

/**
 * 用户等级图片
 */
public String userGradeImg;

/**
 * 视频通话费用
 */
public double videoCoin;

/**
 * 下一级升级大礼包
 */
public com.kalacheng.libuser.model.ApiGradeReWarRe levelPackList;

/**
 * 直播封面
 */
public String liveThumb;

/**
 * 总消费金币(展示) (男朋友中为钻石)
 */
public String totalConsumeCoinStr;

/**
 * 贵族剩余天数
 */
public long nobleExpireDay;

/**
 * 直播房间标识
 */
public String showId;

/**
 * 主播是否认证 0:未认证 1:已认证  后台添加主播时,如果是认证状态, 需要添加认证记录
 */
public int isAnchorAuth;

/**
 * 系统通知
 */
public com.kalacheng.libuser.model.SysNotic sysNotic;

/**
 * 他的直播
 */
public List<com.kalacheng.libuser.model.LiveDto> liveList;

/**
 * 语音通话费用
 */
public double voiceCoin;

/**
 * 用户token
 */
public String user_token;

/**
 * 查看手机号所需金币 (男朋友中为钻石)
 */
public double mobileCoin;

/**
 * 微信号
 */
public String wechatNo;

/**
 * 新手大礼包
 */
public List<com.kalacheng.libuser.model.AdminGiftPack> packList;

/**
 * 财富等级图片
 */
public String wealthGradeImg;

/**
 * 直播间礼物金额
 */
public double liveCoin;

/**
 * 手机号
 */
public String mobile;

/**
 * 是否发布动态 1:否 0:是
 */
public int isVideo;

/**
 * 前端显示的状态字段：0:离线 1:忙碌 2:在线 3:通话中 4:看直播 5:匹配中 6:直播中 7:离开
 */
public int showStatus;

/**
 * 用户勋章-勋章墙3个
 */
public List<com.kalacheng.libuser.model.AppMedal> medalWall;

/**
 * 谁查看过我
 */
public List<com.kalacheng.libuser.model.ApiUserAtten> readMeUsers;

/**
 * 个人资料图片,逗号隔开
 */
public String portrait;

/**
 * 贵族勋章logo
 */
public String nobleLogo;

/**
 * 头像缩略图
 */
public String avatarThumb;

/**
 * 用户对当前直播间总贡献值
 */
public double currContValue;

/**
 * 我守护的List
 */
public List<com.kalacheng.buscommon.model.GuardUserDto> myGuardList;

/**
 * 总收益映票(展示)
 */
public String totalIncomeVotesStr;

/**
 * 是否开启定位 0:开启 1:关闭
 */
public int isLocation;

/**
 * 是否开启勿扰 0:未开启 1:开启
 */
public int isNotDisturb;

/**
 * 贵族等级
 */
public int nobleGrade;

/**
 * 是否弹出邀请码 1:弹出 2:不弹出
 */
public int isPid;

/**
 * 年龄
 */
public int age;

/**
 * 靓号
 */
public String goodnum;

/**
 * 注册赠送通话时间(单位为分钟)
 */
public int registerCallTime;

/**
 * 昵称
 */
public String username;

   public ApiUserInfo(Parcel in) 
{
joinRoomShow=in.readInt();
onlineStatus=in.readInt();

if(threeMedal==null){
threeMedal=  new ArrayList<>();
 }
in.readTypedList(threeMedal,com.kalacheng.libuser.model.AppMedal.CREATOR);
userLogo=in.readString();

if(nobleGradePrivilegeList==null){
nobleGradePrivilegeList=  new ArrayList<>();
 }
in.readTypedList(nobleGradePrivilegeList,com.kalacheng.libuser.model.AppGradePrivilege.CREATOR);
userSetOnlineStatus=in.readInt();
likeNum=in.readInt();
relation=in.readInt();
readMeUsersNumber=in.readInt();
otherUid=in.readLong();
isYouthModel=in.readInt();
constellation=in.readString();
province=in.readString();

if(userCovers==null){
userCovers=  new ArrayList<>();
 }
in.readTypedList(userCovers,com.kalacheng.libuser.model.AppCoverExamine.CREATOR);
isTone=in.readInt();
roomIsVideo=in.readInt();
liveStatus=in.readInt();
roomType=in.readInt();

if(trends==null){
trends=  new ArrayList<>();
 }
in.readTypedList(trends,com.kalacheng.libuser.model.AppTrendsRecord.CREATOR);
height=in.readInt();
wechatCoin=in.readDouble();
canLinkMic=in.readInt();
fansNum=in.readInt();
lng=in.readDouble();
userGrade=in.readInt();

adminLiveConfig=in.readParcelable(com.kalacheng.libuser.model.AdminLiveConfig.class.getClassLoader());
iszombiep=in.readInt();
roomPkSid=in.readLong();
anchorGradeImg=in.readString();
wealthGrade=in.readInt();
poster=in.readString();
totalIncomeVotes=in.readDouble();
status=in.readInt();

interestListStr=in.readParcelable(com.kalacheng.buscommon.model.TabTypeDto.class.getClassLoader());
role=in.readInt();
isVipSee=in.readInt();
city=in.readString();
signature=in.readString();
thumb=in.readString();
registerCallSecond=in.readInt();
roomPKVotes=in.readDouble();
linkOtherSid=in.readLong();
readShortVideoNumber=in.readInt();
uid=in.readLong();
chargeShow=in.readInt();
isAlertCode=in.readInt();
toRelation=in.readInt();

if(wealthGradePrivilegeList==null){
wealthGradePrivilegeList=  new ArrayList<>();
 }
in.readTypedList(wealthGradePrivilegeList,com.kalacheng.libuser.model.AppGradePrivilege.CREATOR);
oooLiveStatus=in.readInt();
weightStr=in.readString();
address=in.readString();
isShutUp=in.readInt();
coinShow=in.readString();
sex=in.readInt();
vipType=in.readInt();

if(userTaskList==null){
userTaskList=  new ArrayList<>();
 }
in.readTypedList(userTaskList,com.kalacheng.buscommon.model.TaskDto.CREATOR);
avatar=in.readString();
userId=in.readLong();
vocation=in.readString();
roomVotes=in.readDouble();
createTime=in.readString();
votes=in.readDouble();
devoteShow=in.readInt();
anchorGrade=in.readInt();
linkOtherStatus=in.readInt();

if(guardMyList==null){
guardMyList=  new ArrayList<>();
 }
in.readTypedList(guardMyList,com.kalacheng.buscommon.model.GuardUserDto.CREATOR);
groupId=in.readLong();
rewardNum=in.readInt();
nobleName=in.readString();
noTalking=in.readInt();
pid=in.readLong();
nobleGradeImg=in.readString();
roomId=in.readLong();

adminLoginAward=in.readParcelable(com.kalacheng.libuser.model.AdminLoginAward.class.getClassLoader());
anchorAuditStatus=in.readInt();
broadCast=in.readInt();
isLive=in.readInt();
iszombie=in.readInt();

if(anchorGradePrivilegeList==null){
anchorGradePrivilegeList=  new ArrayList<>();
 }
in.readTypedList(anchorGradePrivilegeList,com.kalacheng.libuser.model.AppGradePrivilege.CREATOR);
lat=in.readDouble();
wealthLogo=in.readString();

if(interestList==null){
interestList=  new ArrayList<>();
 }
in.readTypedList(interestList,com.kalacheng.buscommon.model.TabTypeDto.CREATOR);
isFirstLogin=in.readInt();
followStatus=in.readInt();
issuper=in.readInt();
weight=in.readDouble();
anchorAuditReason=in.readString();
whetherEnablePositioningShow=in.readInt();

if(userCommentList==null){
userCommentList=  new ArrayList<>();
 }
in.readTypedList(userCommentList,com.kalacheng.libuser.model.ApiUserComment.CREATOR);
followNum=in.readInt();
hostVolumed=in.readInt();
voiceStatus=in.readInt();
totalConsumeCoin=in.readDouble();

if(userGradePrivilegeList==null){
userGradePrivilegeList=  new ArrayList<>();
 }
in.readTypedList(userGradePrivilegeList,com.kalacheng.libuser.model.AppGradePrivilege.CREATOR);

if(giftWall==null){
giftWall=  new ArrayList<>();
 }
in.readTypedList(giftWall,com.kalacheng.buscommon.model.GiftWallDto.CREATOR);
isSvip=in.readInt();
coin=in.readDouble();
birthday=in.readString();
isPush=in.readInt();
liveType=in.readInt();
userGradeImg=in.readString();
videoCoin=in.readDouble();

levelPackList=in.readParcelable(com.kalacheng.libuser.model.ApiGradeReWarRe.class.getClassLoader());
liveThumb=in.readString();
totalConsumeCoinStr=in.readString();
nobleExpireDay=in.readLong();
showId=in.readString();
isAnchorAuth=in.readInt();

sysNotic=in.readParcelable(com.kalacheng.libuser.model.SysNotic.class.getClassLoader());

if(liveList==null){
liveList=  new ArrayList<>();
 }
in.readTypedList(liveList,com.kalacheng.libuser.model.LiveDto.CREATOR);
voiceCoin=in.readDouble();
user_token=in.readString();
mobileCoin=in.readDouble();
wechatNo=in.readString();

if(packList==null){
packList=  new ArrayList<>();
 }
in.readTypedList(packList,com.kalacheng.libuser.model.AdminGiftPack.CREATOR);
wealthGradeImg=in.readString();
liveCoin=in.readDouble();
mobile=in.readString();
isVideo=in.readInt();
showStatus=in.readInt();

if(medalWall==null){
medalWall=  new ArrayList<>();
 }
in.readTypedList(medalWall,com.kalacheng.libuser.model.AppMedal.CREATOR);

if(readMeUsers==null){
readMeUsers=  new ArrayList<>();
 }
in.readTypedList(readMeUsers,com.kalacheng.libuser.model.ApiUserAtten.CREATOR);
portrait=in.readString();
nobleLogo=in.readString();
avatarThumb=in.readString();
currContValue=in.readDouble();

if(myGuardList==null){
myGuardList=  new ArrayList<>();
 }
in.readTypedList(myGuardList,com.kalacheng.buscommon.model.GuardUserDto.CREATOR);
totalIncomeVotesStr=in.readString();
isLocation=in.readInt();
isNotDisturb=in.readInt();
nobleGrade=in.readInt();
isPid=in.readInt();
age=in.readInt();
goodnum=in.readString();
registerCallTime=in.readInt();
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(joinRoomShow);
dest.writeInt(onlineStatus);

dest.writeTypedList(threeMedal);
dest.writeString(userLogo);

dest.writeTypedList(nobleGradePrivilegeList);
dest.writeInt(userSetOnlineStatus);
dest.writeInt(likeNum);
dest.writeInt(relation);
dest.writeInt(readMeUsersNumber);
dest.writeLong(otherUid);
dest.writeInt(isYouthModel);
dest.writeString(constellation);
dest.writeString(province);

dest.writeTypedList(userCovers);
dest.writeInt(isTone);
dest.writeInt(roomIsVideo);
dest.writeInt(liveStatus);
dest.writeInt(roomType);

dest.writeTypedList(trends);
dest.writeInt(height);
dest.writeDouble(wechatCoin);
dest.writeInt(canLinkMic);
dest.writeInt(fansNum);
dest.writeDouble(lng);
dest.writeInt(userGrade);

dest.writeParcelable(adminLiveConfig,flags);
dest.writeInt(iszombiep);
dest.writeLong(roomPkSid);
dest.writeString(anchorGradeImg);
dest.writeInt(wealthGrade);
dest.writeString(poster);
dest.writeDouble(totalIncomeVotes);
dest.writeInt(status);

dest.writeParcelable(interestListStr,flags);
dest.writeInt(role);
dest.writeInt(isVipSee);
dest.writeString(city);
dest.writeString(signature);
dest.writeString(thumb);
dest.writeInt(registerCallSecond);
dest.writeDouble(roomPKVotes);
dest.writeLong(linkOtherSid);
dest.writeInt(readShortVideoNumber);
dest.writeLong(uid);
dest.writeInt(chargeShow);
dest.writeInt(isAlertCode);
dest.writeInt(toRelation);

dest.writeTypedList(wealthGradePrivilegeList);
dest.writeInt(oooLiveStatus);
dest.writeString(weightStr);
dest.writeString(address);
dest.writeInt(isShutUp);
dest.writeString(coinShow);
dest.writeInt(sex);
dest.writeInt(vipType);

dest.writeTypedList(userTaskList);
dest.writeString(avatar);
dest.writeLong(userId);
dest.writeString(vocation);
dest.writeDouble(roomVotes);
dest.writeString(createTime);
dest.writeDouble(votes);
dest.writeInt(devoteShow);
dest.writeInt(anchorGrade);
dest.writeInt(linkOtherStatus);

dest.writeTypedList(guardMyList);
dest.writeLong(groupId);
dest.writeInt(rewardNum);
dest.writeString(nobleName);
dest.writeInt(noTalking);
dest.writeLong(pid);
dest.writeString(nobleGradeImg);
dest.writeLong(roomId);

dest.writeParcelable(adminLoginAward,flags);
dest.writeInt(anchorAuditStatus);
dest.writeInt(broadCast);
dest.writeInt(isLive);
dest.writeInt(iszombie);

dest.writeTypedList(anchorGradePrivilegeList);
dest.writeDouble(lat);
dest.writeString(wealthLogo);

dest.writeTypedList(interestList);
dest.writeInt(isFirstLogin);
dest.writeInt(followStatus);
dest.writeInt(issuper);
dest.writeDouble(weight);
dest.writeString(anchorAuditReason);
dest.writeInt(whetherEnablePositioningShow);

dest.writeTypedList(userCommentList);
dest.writeInt(followNum);
dest.writeInt(hostVolumed);
dest.writeInt(voiceStatus);
dest.writeDouble(totalConsumeCoin);

dest.writeTypedList(userGradePrivilegeList);

dest.writeTypedList(giftWall);
dest.writeInt(isSvip);
dest.writeDouble(coin);
dest.writeString(birthday);
dest.writeInt(isPush);
dest.writeInt(liveType);
dest.writeString(userGradeImg);
dest.writeDouble(videoCoin);

dest.writeParcelable(levelPackList,flags);
dest.writeString(liveThumb);
dest.writeString(totalConsumeCoinStr);
dest.writeLong(nobleExpireDay);
dest.writeString(showId);
dest.writeInt(isAnchorAuth);

dest.writeParcelable(sysNotic,flags);

dest.writeTypedList(liveList);
dest.writeDouble(voiceCoin);
dest.writeString(user_token);
dest.writeDouble(mobileCoin);
dest.writeString(wechatNo);

dest.writeTypedList(packList);
dest.writeString(wealthGradeImg);
dest.writeDouble(liveCoin);
dest.writeString(mobile);
dest.writeInt(isVideo);
dest.writeInt(showStatus);

dest.writeTypedList(medalWall);

dest.writeTypedList(readMeUsers);
dest.writeString(portrait);
dest.writeString(nobleLogo);
dest.writeString(avatarThumb);
dest.writeDouble(currContValue);

dest.writeTypedList(myGuardList);
dest.writeString(totalIncomeVotesStr);
dest.writeInt(isLocation);
dest.writeInt(isNotDisturb);
dest.writeInt(nobleGrade);
dest.writeInt(isPid);
dest.writeInt(age);
dest.writeString(goodnum);
dest.writeInt(registerCallTime);
dest.writeString(username);

}

  public static void cloneObj(ApiUserInfo source,ApiUserInfo target)
{

target.joinRoomShow=source.joinRoomShow;

target.onlineStatus=source.onlineStatus;

        if(source.threeMedal==null)
        {
            target.threeMedal=null;
        }else
        {
            target.threeMedal=new ArrayList();
            for(int i=0;i<source.threeMedal.size();i++)
            {
            AppMedal.cloneObj(source.threeMedal.get(i),target.threeMedal.get(i));
            }
        }


target.userLogo=source.userLogo;

        if(source.nobleGradePrivilegeList==null)
        {
            target.nobleGradePrivilegeList=null;
        }else
        {
            target.nobleGradePrivilegeList=new ArrayList();
            for(int i=0;i<source.nobleGradePrivilegeList.size();i++)
            {
            AppGradePrivilege.cloneObj(source.nobleGradePrivilegeList.get(i),target.nobleGradePrivilegeList.get(i));
            }
        }


target.userSetOnlineStatus=source.userSetOnlineStatus;

target.likeNum=source.likeNum;

target.relation=source.relation;

target.readMeUsersNumber=source.readMeUsersNumber;

target.otherUid=source.otherUid;

target.isYouthModel=source.isYouthModel;

target.constellation=source.constellation;

target.province=source.province;

        if(source.userCovers==null)
        {
            target.userCovers=null;
        }else
        {
            target.userCovers=new ArrayList();
            for(int i=0;i<source.userCovers.size();i++)
            {
            AppCoverExamine.cloneObj(source.userCovers.get(i),target.userCovers.get(i));
            }
        }


target.isTone=source.isTone;

target.roomIsVideo=source.roomIsVideo;

target.liveStatus=source.liveStatus;

target.roomType=source.roomType;

        if(source.trends==null)
        {
            target.trends=null;
        }else
        {
            target.trends=new ArrayList();
            for(int i=0;i<source.trends.size();i++)
            {
            AppTrendsRecord.cloneObj(source.trends.get(i),target.trends.get(i));
            }
        }


target.height=source.height;

target.wechatCoin=source.wechatCoin;

target.canLinkMic=source.canLinkMic;

target.fansNum=source.fansNum;

target.lng=source.lng;

target.userGrade=source.userGrade;
        if(source.adminLiveConfig==null)
        {
            target.adminLiveConfig=null;
        }else
        {
            AdminLiveConfig.cloneObj(source.adminLiveConfig,target.adminLiveConfig);
        }

target.iszombiep=source.iszombiep;

target.roomPkSid=source.roomPkSid;

target.anchorGradeImg=source.anchorGradeImg;

target.wealthGrade=source.wealthGrade;

target.poster=source.poster;

target.totalIncomeVotes=source.totalIncomeVotes;

target.status=source.status;
        if(source.interestListStr==null)
        {
            target.interestListStr=null;
        }else
        {
            TabTypeDto.cloneObj(source.interestListStr,target.interestListStr);
        }

target.role=source.role;

target.isVipSee=source.isVipSee;

target.city=source.city;

target.signature=source.signature;

target.thumb=source.thumb;

target.registerCallSecond=source.registerCallSecond;

target.roomPKVotes=source.roomPKVotes;

target.linkOtherSid=source.linkOtherSid;

target.readShortVideoNumber=source.readShortVideoNumber;

target.uid=source.uid;

target.chargeShow=source.chargeShow;

target.isAlertCode=source.isAlertCode;

target.toRelation=source.toRelation;

        if(source.wealthGradePrivilegeList==null)
        {
            target.wealthGradePrivilegeList=null;
        }else
        {
            target.wealthGradePrivilegeList=new ArrayList();
            for(int i=0;i<source.wealthGradePrivilegeList.size();i++)
            {
            AppGradePrivilege.cloneObj(source.wealthGradePrivilegeList.get(i),target.wealthGradePrivilegeList.get(i));
            }
        }


target.oooLiveStatus=source.oooLiveStatus;

target.weightStr=source.weightStr;

target.address=source.address;

target.isShutUp=source.isShutUp;

target.coinShow=source.coinShow;

target.sex=source.sex;

target.vipType=source.vipType;

        if(source.userTaskList==null)
        {
            target.userTaskList=null;
        }else
        {
            target.userTaskList=new ArrayList();
            for(int i=0;i<source.userTaskList.size();i++)
            {
            TaskDto.cloneObj(source.userTaskList.get(i),target.userTaskList.get(i));
            }
        }


target.avatar=source.avatar;

target.userId=source.userId;

target.vocation=source.vocation;

target.roomVotes=source.roomVotes;

target.createTime=source.createTime;

target.votes=source.votes;

target.devoteShow=source.devoteShow;

target.anchorGrade=source.anchorGrade;

target.linkOtherStatus=source.linkOtherStatus;

        if(source.guardMyList==null)
        {
            target.guardMyList=null;
        }else
        {
            target.guardMyList=new ArrayList();
            for(int i=0;i<source.guardMyList.size();i++)
            {
            GuardUserDto.cloneObj(source.guardMyList.get(i),target.guardMyList.get(i));
            }
        }


target.groupId=source.groupId;

target.rewardNum=source.rewardNum;

target.nobleName=source.nobleName;

target.noTalking=source.noTalking;

target.pid=source.pid;

target.nobleGradeImg=source.nobleGradeImg;

target.roomId=source.roomId;
        if(source.adminLoginAward==null)
        {
            target.adminLoginAward=null;
        }else
        {
            AdminLoginAward.cloneObj(source.adminLoginAward,target.adminLoginAward);
        }

target.anchorAuditStatus=source.anchorAuditStatus;

target.broadCast=source.broadCast;

target.isLive=source.isLive;

target.iszombie=source.iszombie;

        if(source.anchorGradePrivilegeList==null)
        {
            target.anchorGradePrivilegeList=null;
        }else
        {
            target.anchorGradePrivilegeList=new ArrayList();
            for(int i=0;i<source.anchorGradePrivilegeList.size();i++)
            {
            AppGradePrivilege.cloneObj(source.anchorGradePrivilegeList.get(i),target.anchorGradePrivilegeList.get(i));
            }
        }


target.lat=source.lat;

target.wealthLogo=source.wealthLogo;

        if(source.interestList==null)
        {
            target.interestList=null;
        }else
        {
            target.interestList=new ArrayList();
            for(int i=0;i<source.interestList.size();i++)
            {
            TabTypeDto.cloneObj(source.interestList.get(i),target.interestList.get(i));
            }
        }


target.isFirstLogin=source.isFirstLogin;

target.followStatus=source.followStatus;

target.issuper=source.issuper;

target.weight=source.weight;

target.anchorAuditReason=source.anchorAuditReason;

target.whetherEnablePositioningShow=source.whetherEnablePositioningShow;

        if(source.userCommentList==null)
        {
            target.userCommentList=null;
        }else
        {
            target.userCommentList=new ArrayList();
            for(int i=0;i<source.userCommentList.size();i++)
            {
            ApiUserComment.cloneObj(source.userCommentList.get(i),target.userCommentList.get(i));
            }
        }


target.followNum=source.followNum;

target.hostVolumed=source.hostVolumed;

target.voiceStatus=source.voiceStatus;

target.totalConsumeCoin=source.totalConsumeCoin;

        if(source.userGradePrivilegeList==null)
        {
            target.userGradePrivilegeList=null;
        }else
        {
            target.userGradePrivilegeList=new ArrayList();
            for(int i=0;i<source.userGradePrivilegeList.size();i++)
            {
            AppGradePrivilege.cloneObj(source.userGradePrivilegeList.get(i),target.userGradePrivilegeList.get(i));
            }
        }


        if(source.giftWall==null)
        {
            target.giftWall=null;
        }else
        {
            target.giftWall=new ArrayList();
            for(int i=0;i<source.giftWall.size();i++)
            {
            GiftWallDto.cloneObj(source.giftWall.get(i),target.giftWall.get(i));
            }
        }


target.isSvip=source.isSvip;

target.coin=source.coin;

target.birthday=source.birthday;

target.isPush=source.isPush;

target.liveType=source.liveType;

target.userGradeImg=source.userGradeImg;

target.videoCoin=source.videoCoin;
        if(source.levelPackList==null)
        {
            target.levelPackList=null;
        }else
        {
            ApiGradeReWarRe.cloneObj(source.levelPackList,target.levelPackList);
        }

target.liveThumb=source.liveThumb;

target.totalConsumeCoinStr=source.totalConsumeCoinStr;

target.nobleExpireDay=source.nobleExpireDay;

target.showId=source.showId;

target.isAnchorAuth=source.isAnchorAuth;
        if(source.sysNotic==null)
        {
            target.sysNotic=null;
        }else
        {
            SysNotic.cloneObj(source.sysNotic,target.sysNotic);
        }

        if(source.liveList==null)
        {
            target.liveList=null;
        }else
        {
            target.liveList=new ArrayList();
            for(int i=0;i<source.liveList.size();i++)
            {
            LiveDto.cloneObj(source.liveList.get(i),target.liveList.get(i));
            }
        }


target.voiceCoin=source.voiceCoin;

target.user_token=source.user_token;

target.mobileCoin=source.mobileCoin;

target.wechatNo=source.wechatNo;

        if(source.packList==null)
        {
            target.packList=null;
        }else
        {
            target.packList=new ArrayList();
            for(int i=0;i<source.packList.size();i++)
            {
            AdminGiftPack.cloneObj(source.packList.get(i),target.packList.get(i));
            }
        }


target.wealthGradeImg=source.wealthGradeImg;

target.liveCoin=source.liveCoin;

target.mobile=source.mobile;

target.isVideo=source.isVideo;

target.showStatus=source.showStatus;

        if(source.medalWall==null)
        {
            target.medalWall=null;
        }else
        {
            target.medalWall=new ArrayList();
            for(int i=0;i<source.medalWall.size();i++)
            {
            AppMedal.cloneObj(source.medalWall.get(i),target.medalWall.get(i));
            }
        }


        if(source.readMeUsers==null)
        {
            target.readMeUsers=null;
        }else
        {
            target.readMeUsers=new ArrayList();
            for(int i=0;i<source.readMeUsers.size();i++)
            {
            ApiUserAtten.cloneObj(source.readMeUsers.get(i),target.readMeUsers.get(i));
            }
        }


target.portrait=source.portrait;

target.nobleLogo=source.nobleLogo;

target.avatarThumb=source.avatarThumb;

target.currContValue=source.currContValue;

        if(source.myGuardList==null)
        {
            target.myGuardList=null;
        }else
        {
            target.myGuardList=new ArrayList();
            for(int i=0;i<source.myGuardList.size();i++)
            {
            GuardUserDto.cloneObj(source.myGuardList.get(i),target.myGuardList.get(i));
            }
        }


target.totalIncomeVotesStr=source.totalIncomeVotesStr;

target.isLocation=source.isLocation;

target.isNotDisturb=source.isNotDisturb;

target.nobleGrade=source.nobleGrade;

target.isPid=source.isPid;

target.age=source.age;

target.goodnum=source.goodnum;

target.registerCallTime=source.registerCallTime;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiUserInfo> CREATOR = new Creator<ApiUserInfo>() {
        @Override
        public ApiUserInfo createFromParcel(Parcel in) {
            return new ApiUserInfo(in);
        }

        @Override
        public ApiUserInfo[] newArray(int size) {
            return new ApiUserInfo[size];
        }
    };
}
