package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 用户基础信息表
 */
public class AppUser  implements Parcelable
{
 public AppUser()
{
}

/**
 * 当前版本号
 */
public String appVersion;

/**
 * 加入房间隐身 0:不隐身 1:隐身
 */
public int joinRoomShow;

/**
 * 连续签到次数
 */
public int signCount;

/**
 * 用户在线状态 0:离线 1:在线
 */
public int onlineStatus;

/**
 * 用户当前设备信息ID
 */
public String deviceId;

/**
 * 推送平台 1:小米 2:华为 3:vivo 4:oppo 5:苹果 6:极光 7:apns 8:miApns
 */
public int pushPlatform;

/**
 * 用户设置的在线状态 0:在线 1:忙碌 2:离开 3:通话中
 */
public int userSetOnlineStatus;

/**
 * null
 */
public long userid;

/**
 * 跟随房间类型 1:视频 2:语音
 */
public int gsRoomType;

/**
 * 用户积分
 */
public int score;

/**
 * 是否开启青少年模式 1:开启 2:未开启
 */
public int isYouthModel;

/**
 * 密码
 */
public String password;

/**
 * 手机型号
 */
public String phoneModel;

/**
 * 星座
 */
public String constellation;

/**
 * 省份
 */
public String province;

/**
 * 是否关闭提示音 0:开启 1:关闭
 */
public int isTone;

/**
 * 直播视频地址
 */
public String liveUrl;

/**
 * 累计提现映票
 */
public double totalCash;

/**
 * 上次抽奖进度
 */
public int lastGameNum;

/**
 * 用户当前的位置信息ID
 */
public String ipaddr;

/**
 * 是否热门显示 1:否 0:是
 */
public int ishot;

/**
 * 视频直播状态:0:未进行直播 1:直播中的主播 2:直播中的观众
 */
public int liveStatus;

/**
 * 房间类型 0:是一般直播 1:是私密直播 2:是收费直播 3:是计时直播 4:贵族房间
 */
public int roomType;

/**
 * 身高（CM）
 */
public int height;

/**
 * 房间号(跟随用)
 */
public long gsRoomId;

/**
 * 映票总额
 */
public double votestotal;

/**
 * 经度
 */
public double lng;

/**
 * 推送平台对应的id
 */
public String pushRegisterId;

/**
 * 用户等级
 */
public int userGrade;

/**
 * 是否有直播购 0:没有直播购 1:有直播购
 */
public int liveFunction;

/**
 * 微信企业ID
 */
public String openid;

/**
 * 是否僵尸粉 1:否 0:是
 */
public int iszombiep;

/**
 * 0:未编辑过 1:编辑过了
 */
public int cityEdit;

/**
 * 所属公会ID
 */
public long guildId;

/**
 * 上次登录时间(连续登录用)
 */
public String lastLoginDay;

/**
 * 最大连续签到天数
 */
public int maxSignCount;

/**
 * 累计充值金额
 */
public double totalCharge;

/**
 * 财富等级
 */
public int wealthGrade;

/**
 * 离线时间
 */
public Date lastOffLineTime;

/**
 * 海报
 */
public String poster;

/**
 * 用户状态 1:禁用 0:正常
 */
public int status;

/**
 * 角色 0:普通用户 1:主播
 */
public int role;

/**
 * 城市
 */
public String city;

/**
 * 个性签名
 */
public String signature;

/**
 * 首页一对一排序编号
 */
public int oooHomePageSortNo;

/**
 * 剩余的注册赠送通话次数
 */
public int registerCallSecond;

/**
 * 连续登录天数
 */
public int awardLoginDay;

/**
 * 删除状态 0:未删除(默认) 1:已删除
 */
public int delFlag;

/**
 * 短视频剩余可观看私密视频次数
 */
public int readShortVideoNumber;

/**
 * 最后登录ip
 */
public String lastLoginIp;

/**
 * 充值隐身 0:不隐身 1:隐身
 */
public int chargeShow;

/**
 * 禁用时间
 */
public Date lockTime;

/**
 * 真实姓名
 */
public String nickname;

/**
 * 登录邮箱
 */
public String userEmail;

/**
 * 一对一直播状态:0:未进行直播 1:通话中 2:邀请他人通话 3:正在被邀请
 */
public int oooLiveStatus;

/**
 * 注册类型
 */
public int regType;

/**
 * 直播频道
 */
public int channelId;

/**
 * 地址
 */
public String address;

/**
 * 盐
 */
public String salt;

/**
 * 性别 0:未设置 1:男 2:女 (男朋友新增 3(0) 4(0.5) 5(...)
 */
public int sex;

/**
 * 手机厂商
 */
public String phoneFirm;

/**
 * 用户头像
 */
public String avatar;

/**
 * 职业
 */
public String vocation;

/**
 * 上次登录时间
 */
public Date lastLoginTime;

/**
 * 注册时间
 */
public Date createTime;

/**
 * 映票余额/可提现金额
 */
public double votes;

/**
 * 青少年密码
 */
public String youthPassword;

/**
 * 贡献榜排行隐身 0:不隐身 1:隐身
 */
public int devoteShow;

/**
 * null
 */
public String voice;

/**
 * 是否推荐 0:不推荐 1:推荐中
 */
public int isRecommend;

/**
 * 是否是一对一演示账号 1:是 0:否
 */
public int isOOOAccount;

/**
 * 主播等级
 */
public int anchorGrade;

/**
 * 用户资料图片5
 */
public String portrait5;

/**
 * 用户资料图片4
 */
public String portrait4;

/**
 * 主播粉丝团群聊id
 */
public long groupId;

/**
 * 用户资料图片3
 */
public String portrait3;

/**
 * 用户资料图片2
 */
public String portrait2;

/**
 * 用户资料图片1
 */
public String portrait1;

/**
 * 直属上级
 */
public long pid;

/**
 * 操作用户名,如禁用操作/解禁操作等
 */
public String optUserName;

/**
 * 注册来源
 */
public String source;

/**
 * 当前版本code
 */
public String appVersionCode;

/**
 * 全站广播功能 0:关闭功能 1:开启功能
 */
public int broadCast;

/**
 * 累计提现佣金
 */
public double totalAmountCash;

/**
 * 主播抽成比例
 */
public double anchorPerc;

/**
 * 手机系统
 */
public String phoneSystem;

/**
 * 是否开启僵尸粉 1:未开启(默认) 0:已开启
 */
public int iszombie;

/**
 * 直播封面图
 */
public String liveThumbs;

/**
 * 纬度
 */
public double lat;

/**
 * 用户个人网站
 */
public String userUrl;

/**
 * 上次签到时间
 */
public Date signTime;

/**
 * 层级
 */
public int level;

/**
 * 是否超管 1:否 0:是
 */
public int issuper;

/**
 * 体重（KG）
 */
public double weight;

/**
 * 是否开启定位显示 0:开启 1:未开启
 */
public int whetherEnablePositioningShow;

/**
 * 是否显示在首页 1:展示在首页 0:不展示在首页
 */
public int isShowHomePage;

/**
 * 总收益佣金
 */
public double totalAmount;

/**
 * 被踢到期时间戳
 */
public long kickTime;

/**
 * 多人语音直播状态 0:不在语音房间中 2:上麦标识 3:被邀上麦中 4:被踢下麦 5:下麦标识 6:申请上麦中 8:被踢出房间
 */
public int voiceStatus;

/**
 * 房间类型值
 */
public String roomTypeVal;

/**
 * 苹果voip
 */
public String voipToken;

/**
 * 用户类型 1:admin 2:会员
 */
public int userType;

/**
 * 用户是否开通svip 1是 0否
 */
public int isSvip;

/**
 * 激活码
 */
public String userActivationKey;

/**
 * 金币 (男朋友中为钻石)/充值金额
 */
public double coin;

/**
 * 生日
 */
public String birthday;

/**
 * 直属上级代理（后台管理员）
 */
public long agentId;

/**
 * 是否开启消息推送 0:开启 1:关闭
 */
public int isPush;

/**
 * 微信唯一ID
 */
public String unionid;

/**
 * 主播积分
 */
public int anchorPoint;

/**
 * 注册方式 1:手机注册 2:微信注册 3:QQ注册
 */
public String loginType;

/**
 * 视频通话时间金币 (男朋友中为钻石)/min
 */
public double videoCoin;

/**
 * 封面
 */
public String liveThumb;

/**
 * 展示视频
 */
public String video;

/**
 * 主播是否认证 0:未认证 1:已认证  后台添加主播时,如果是认证状态, 需要添加认证记录
 */
public int isAnchorAuth;

/**
 * 语音通话时间金币 (男朋友中为钻石)/min
 */
public double voiceCoin;

/**
 * 是否自动填充主播分成比例  0:自动  1:人工调整
 */
public int isAutomatic;

/**
 * 剩余佣金/可提现佣金
 */
public double amount;

/**
 * 手机唯一标识
 */
public String phoneUuid;

/**
 * 用户积分
 */
public int userPoint;

/**
 * 手机号
 */
public String mobile;

/**
 * 修改密码时间
 */
public Date updatePwdTime;

/**
 * 展示视频封面
 */
public String videoImg;

/**
 * 微信号
 */
public String wechat;

/**
 * 消费总额
 */
public double consumption;

/**
 * 是否是多人直播演示账号 1:多人视频直播演示账号 2:多人语音直播演示账号 0:否
 */
public int isLiveAccount;

/**
 * 用户资料图片,英文逗号隔开
 */
public String portrait;

/**
 * 禁用原因
 */
public String lockReason;

/**
 * 小头像
 */
public String avatarThumb;

/**
 * ooo展示位置关联app_live_channel表
 */
public long headNo;

/**
 * 是否开起回放 1:未开启 0:已开启
 */
public int isrecord;

/**
 * 邀请码
 */
public String inviteCode;

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
 * 房间标题
 */
public String roomTitle;

/**
 * 当前装备靓号
 */
public String goodnum;

/**
 * 注册赠送通话时间(单位为分钟)
 */
public int registerCallTime;

/**
 * 用户名
 */
public String username;

   public AppUser(Parcel in) 
{
appVersion=in.readString();
joinRoomShow=in.readInt();
signCount=in.readInt();
onlineStatus=in.readInt();
deviceId=in.readString();
pushPlatform=in.readInt();
userSetOnlineStatus=in.readInt();
userid=in.readLong();
gsRoomType=in.readInt();
score=in.readInt();
isYouthModel=in.readInt();
password=in.readString();
phoneModel=in.readString();
constellation=in.readString();
province=in.readString();
isTone=in.readInt();
liveUrl=in.readString();
totalCash=in.readDouble();
lastGameNum=in.readInt();
ipaddr=in.readString();
ishot=in.readInt();
liveStatus=in.readInt();
roomType=in.readInt();
height=in.readInt();
gsRoomId=in.readLong();
votestotal=in.readDouble();
lng=in.readDouble();
pushRegisterId=in.readString();
userGrade=in.readInt();
liveFunction=in.readInt();
openid=in.readString();
iszombiep=in.readInt();
cityEdit=in.readInt();
guildId=in.readLong();
lastLoginDay=in.readString();
maxSignCount=in.readInt();
totalCharge=in.readDouble();
wealthGrade=in.readInt();
lastOffLineTime=new Date( in.readLong());
poster=in.readString();
status=in.readInt();
role=in.readInt();
city=in.readString();
signature=in.readString();
oooHomePageSortNo=in.readInt();
registerCallSecond=in.readInt();
awardLoginDay=in.readInt();
delFlag=in.readInt();
readShortVideoNumber=in.readInt();
lastLoginIp=in.readString();
chargeShow=in.readInt();
lockTime=new Date( in.readLong());
nickname=in.readString();
userEmail=in.readString();
oooLiveStatus=in.readInt();
regType=in.readInt();
channelId=in.readInt();
address=in.readString();
salt=in.readString();
sex=in.readInt();
phoneFirm=in.readString();
avatar=in.readString();
vocation=in.readString();
lastLoginTime=new Date( in.readLong());
createTime=new Date( in.readLong());
votes=in.readDouble();
youthPassword=in.readString();
devoteShow=in.readInt();
voice=in.readString();
isRecommend=in.readInt();
isOOOAccount=in.readInt();
anchorGrade=in.readInt();
portrait5=in.readString();
portrait4=in.readString();
groupId=in.readLong();
portrait3=in.readString();
portrait2=in.readString();
portrait1=in.readString();
pid=in.readLong();
optUserName=in.readString();
source=in.readString();
appVersionCode=in.readString();
broadCast=in.readInt();
totalAmountCash=in.readDouble();
anchorPerc=in.readDouble();
phoneSystem=in.readString();
iszombie=in.readInt();
liveThumbs=in.readString();
lat=in.readDouble();
userUrl=in.readString();
signTime=new Date( in.readLong());
level=in.readInt();
issuper=in.readInt();
weight=in.readDouble();
whetherEnablePositioningShow=in.readInt();
isShowHomePage=in.readInt();
totalAmount=in.readDouble();
kickTime=in.readLong();
voiceStatus=in.readInt();
roomTypeVal=in.readString();
voipToken=in.readString();
userType=in.readInt();
isSvip=in.readInt();
userActivationKey=in.readString();
coin=in.readDouble();
birthday=in.readString();
agentId=in.readLong();
isPush=in.readInt();
unionid=in.readString();
anchorPoint=in.readInt();
loginType=in.readString();
videoCoin=in.readDouble();
liveThumb=in.readString();
video=in.readString();
isAnchorAuth=in.readInt();
voiceCoin=in.readDouble();
isAutomatic=in.readInt();
amount=in.readDouble();
phoneUuid=in.readString();
userPoint=in.readInt();
mobile=in.readString();
updatePwdTime=new Date( in.readLong());
videoImg=in.readString();
wechat=in.readString();
consumption=in.readDouble();
isLiveAccount=in.readInt();
portrait=in.readString();
lockReason=in.readString();
avatarThumb=in.readString();
headNo=in.readLong();
isrecord=in.readInt();
inviteCode=in.readString();
isLocation=in.readInt();
isNotDisturb=in.readInt();
nobleGrade=in.readInt();
roomTitle=in.readString();
goodnum=in.readString();
registerCallTime=in.readInt();
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(appVersion);
dest.writeInt(joinRoomShow);
dest.writeInt(signCount);
dest.writeInt(onlineStatus);
dest.writeString(deviceId);
dest.writeInt(pushPlatform);
dest.writeInt(userSetOnlineStatus);
dest.writeLong(userid);
dest.writeInt(gsRoomType);
dest.writeInt(score);
dest.writeInt(isYouthModel);
dest.writeString(password);
dest.writeString(phoneModel);
dest.writeString(constellation);
dest.writeString(province);
dest.writeInt(isTone);
dest.writeString(liveUrl);
dest.writeDouble(totalCash);
dest.writeInt(lastGameNum);
dest.writeString(ipaddr);
dest.writeInt(ishot);
dest.writeInt(liveStatus);
dest.writeInt(roomType);
dest.writeInt(height);
dest.writeLong(gsRoomId);
dest.writeDouble(votestotal);
dest.writeDouble(lng);
dest.writeString(pushRegisterId);
dest.writeInt(userGrade);
dest.writeInt(liveFunction);
dest.writeString(openid);
dest.writeInt(iszombiep);
dest.writeInt(cityEdit);
dest.writeLong(guildId);
dest.writeString(lastLoginDay);
dest.writeInt(maxSignCount);
dest.writeDouble(totalCharge);
dest.writeInt(wealthGrade);
dest.writeLong(lastOffLineTime==null?0:lastOffLineTime.getTime());
dest.writeString(poster);
dest.writeInt(status);
dest.writeInt(role);
dest.writeString(city);
dest.writeString(signature);
dest.writeInt(oooHomePageSortNo);
dest.writeInt(registerCallSecond);
dest.writeInt(awardLoginDay);
dest.writeInt(delFlag);
dest.writeInt(readShortVideoNumber);
dest.writeString(lastLoginIp);
dest.writeInt(chargeShow);
dest.writeLong(lockTime==null?0:lockTime.getTime());
dest.writeString(nickname);
dest.writeString(userEmail);
dest.writeInt(oooLiveStatus);
dest.writeInt(regType);
dest.writeInt(channelId);
dest.writeString(address);
dest.writeString(salt);
dest.writeInt(sex);
dest.writeString(phoneFirm);
dest.writeString(avatar);
dest.writeString(vocation);
dest.writeLong(lastLoginTime==null?0:lastLoginTime.getTime());
dest.writeLong(createTime==null?0:createTime.getTime());
dest.writeDouble(votes);
dest.writeString(youthPassword);
dest.writeInt(devoteShow);
dest.writeString(voice);
dest.writeInt(isRecommend);
dest.writeInt(isOOOAccount);
dest.writeInt(anchorGrade);
dest.writeString(portrait5);
dest.writeString(portrait4);
dest.writeLong(groupId);
dest.writeString(portrait3);
dest.writeString(portrait2);
dest.writeString(portrait1);
dest.writeLong(pid);
dest.writeString(optUserName);
dest.writeString(source);
dest.writeString(appVersionCode);
dest.writeInt(broadCast);
dest.writeDouble(totalAmountCash);
dest.writeDouble(anchorPerc);
dest.writeString(phoneSystem);
dest.writeInt(iszombie);
dest.writeString(liveThumbs);
dest.writeDouble(lat);
dest.writeString(userUrl);
dest.writeLong(signTime==null?0:signTime.getTime());
dest.writeInt(level);
dest.writeInt(issuper);
dest.writeDouble(weight);
dest.writeInt(whetherEnablePositioningShow);
dest.writeInt(isShowHomePage);
dest.writeDouble(totalAmount);
dest.writeLong(kickTime);
dest.writeInt(voiceStatus);
dest.writeString(roomTypeVal);
dest.writeString(voipToken);
dest.writeInt(userType);
dest.writeInt(isSvip);
dest.writeString(userActivationKey);
dest.writeDouble(coin);
dest.writeString(birthday);
dest.writeLong(agentId);
dest.writeInt(isPush);
dest.writeString(unionid);
dest.writeInt(anchorPoint);
dest.writeString(loginType);
dest.writeDouble(videoCoin);
dest.writeString(liveThumb);
dest.writeString(video);
dest.writeInt(isAnchorAuth);
dest.writeDouble(voiceCoin);
dest.writeInt(isAutomatic);
dest.writeDouble(amount);
dest.writeString(phoneUuid);
dest.writeInt(userPoint);
dest.writeString(mobile);
dest.writeLong(updatePwdTime==null?0:updatePwdTime.getTime());
dest.writeString(videoImg);
dest.writeString(wechat);
dest.writeDouble(consumption);
dest.writeInt(isLiveAccount);
dest.writeString(portrait);
dest.writeString(lockReason);
dest.writeString(avatarThumb);
dest.writeLong(headNo);
dest.writeInt(isrecord);
dest.writeString(inviteCode);
dest.writeInt(isLocation);
dest.writeInt(isNotDisturb);
dest.writeInt(nobleGrade);
dest.writeString(roomTitle);
dest.writeString(goodnum);
dest.writeInt(registerCallTime);
dest.writeString(username);

}

  public static void cloneObj(AppUser source,AppUser target)
{

target.appVersion=source.appVersion;

target.joinRoomShow=source.joinRoomShow;

target.signCount=source.signCount;

target.onlineStatus=source.onlineStatus;

target.deviceId=source.deviceId;

target.pushPlatform=source.pushPlatform;

target.userSetOnlineStatus=source.userSetOnlineStatus;

target.userid=source.userid;

target.gsRoomType=source.gsRoomType;

target.score=source.score;

target.isYouthModel=source.isYouthModel;

target.password=source.password;

target.phoneModel=source.phoneModel;

target.constellation=source.constellation;

target.province=source.province;

target.isTone=source.isTone;

target.liveUrl=source.liveUrl;

target.totalCash=source.totalCash;

target.lastGameNum=source.lastGameNum;

target.ipaddr=source.ipaddr;

target.ishot=source.ishot;

target.liveStatus=source.liveStatus;

target.roomType=source.roomType;

target.height=source.height;

target.gsRoomId=source.gsRoomId;

target.votestotal=source.votestotal;

target.lng=source.lng;

target.pushRegisterId=source.pushRegisterId;

target.userGrade=source.userGrade;

target.liveFunction=source.liveFunction;

target.openid=source.openid;

target.iszombiep=source.iszombiep;

target.cityEdit=source.cityEdit;

target.guildId=source.guildId;

target.lastLoginDay=source.lastLoginDay;

target.maxSignCount=source.maxSignCount;

target.totalCharge=source.totalCharge;

target.wealthGrade=source.wealthGrade;

target.lastOffLineTime=source.lastOffLineTime;

target.poster=source.poster;

target.status=source.status;

target.role=source.role;

target.city=source.city;

target.signature=source.signature;

target.oooHomePageSortNo=source.oooHomePageSortNo;

target.registerCallSecond=source.registerCallSecond;

target.awardLoginDay=source.awardLoginDay;

target.delFlag=source.delFlag;

target.readShortVideoNumber=source.readShortVideoNumber;

target.lastLoginIp=source.lastLoginIp;

target.chargeShow=source.chargeShow;

target.lockTime=source.lockTime;

target.nickname=source.nickname;

target.userEmail=source.userEmail;

target.oooLiveStatus=source.oooLiveStatus;

target.regType=source.regType;

target.channelId=source.channelId;

target.address=source.address;

target.salt=source.salt;

target.sex=source.sex;

target.phoneFirm=source.phoneFirm;

target.avatar=source.avatar;

target.vocation=source.vocation;

target.lastLoginTime=source.lastLoginTime;

target.createTime=source.createTime;

target.votes=source.votes;

target.youthPassword=source.youthPassword;

target.devoteShow=source.devoteShow;

target.voice=source.voice;

target.isRecommend=source.isRecommend;

target.isOOOAccount=source.isOOOAccount;

target.anchorGrade=source.anchorGrade;

target.portrait5=source.portrait5;

target.portrait4=source.portrait4;

target.groupId=source.groupId;

target.portrait3=source.portrait3;

target.portrait2=source.portrait2;

target.portrait1=source.portrait1;

target.pid=source.pid;

target.optUserName=source.optUserName;

target.source=source.source;

target.appVersionCode=source.appVersionCode;

target.broadCast=source.broadCast;

target.totalAmountCash=source.totalAmountCash;

target.anchorPerc=source.anchorPerc;

target.phoneSystem=source.phoneSystem;

target.iszombie=source.iszombie;

target.liveThumbs=source.liveThumbs;

target.lat=source.lat;

target.userUrl=source.userUrl;

target.signTime=source.signTime;

target.level=source.level;

target.issuper=source.issuper;

target.weight=source.weight;

target.whetherEnablePositioningShow=source.whetherEnablePositioningShow;

target.isShowHomePage=source.isShowHomePage;

target.totalAmount=source.totalAmount;

target.kickTime=source.kickTime;

target.voiceStatus=source.voiceStatus;

target.roomTypeVal=source.roomTypeVal;

target.voipToken=source.voipToken;

target.userType=source.userType;

target.isSvip=source.isSvip;

target.userActivationKey=source.userActivationKey;

target.coin=source.coin;

target.birthday=source.birthday;

target.agentId=source.agentId;

target.isPush=source.isPush;

target.unionid=source.unionid;

target.anchorPoint=source.anchorPoint;

target.loginType=source.loginType;

target.videoCoin=source.videoCoin;

target.liveThumb=source.liveThumb;

target.video=source.video;

target.isAnchorAuth=source.isAnchorAuth;

target.voiceCoin=source.voiceCoin;

target.isAutomatic=source.isAutomatic;

target.amount=source.amount;

target.phoneUuid=source.phoneUuid;

target.userPoint=source.userPoint;

target.mobile=source.mobile;

target.updatePwdTime=source.updatePwdTime;

target.videoImg=source.videoImg;

target.wechat=source.wechat;

target.consumption=source.consumption;

target.isLiveAccount=source.isLiveAccount;

target.portrait=source.portrait;

target.lockReason=source.lockReason;

target.avatarThumb=source.avatarThumb;

target.headNo=source.headNo;

target.isrecord=source.isrecord;

target.inviteCode=source.inviteCode;

target.isLocation=source.isLocation;

target.isNotDisturb=source.isNotDisturb;

target.nobleGrade=source.nobleGrade;

target.roomTitle=source.roomTitle;

target.goodnum=source.goodnum;

target.registerCallTime=source.registerCallTime;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppUser> CREATOR = new Creator<AppUser>() {
        @Override
        public AppUser createFromParcel(Parcel in) {
            return new AppUser(in);
        }

        @Override
        public AppUser[] newArray(int size) {
            return new AppUser[size];
        }
    };
}
