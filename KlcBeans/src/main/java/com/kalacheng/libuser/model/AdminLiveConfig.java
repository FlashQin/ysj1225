package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 后台管理-三方设置-直播配置
 */
public class AdminLiveConfig  implements Parcelable
{
 public AdminLiveConfig()
{
}

/**
 * 聊天服务器带端口
 */
public String chatServer;

/**
 * 是否显示用户余额 0:开启 1:关闭
 */
public int isShowCoin;

/**
 * 注销开关 0:开启 1:关闭
 */
public int logOffSwitch;

/**
 * PK平局时长(秒)
 */
public int drawTime;

/**
 * 求聊话费说明
 */
public String rushtotalkExplian;

/**
 * 短视频试看 0:关闭(默认) 1:开启
 */
public int shortVideoTrial;

/**
 * 多人匹配规则
 */
public String MultiplayerRule;

/**
 * 是否贵族才能聊天 0:开启 1:关闭
 */
public int isNobleChat;

/**
 * 提现规则
 */
public String withdrawalRule;

/**
 * 直播间公告
 */
public String liveRoomAnnouncement;

/**
 * 主播抽成比例
 */
public double anchorPerc;

/**
 * 短视频贵族免费 0:开启 1:关闭
 */
public int isShortVideoNobleFree;

/**
 * 女性用户发起通话是否需要认证 0:需要 1:不需要
 */
public int fTalkNeedAuth;

/**
 * 求聊音乐
 */
public String pushMusic;

/**
 * null
 */
public long id;

/**
 * 语音开播认证 0:开启认证 1:关闭认证
 */
public int voiceStartCertification;

/**
 * 直播等级控制
 */
public int levelIslimit;

/**
 * 同性别女是否可以通话 0:用户和主播不能通话 1:用户与主播可以通话
 */
public int fTalkua;

/**
 * 贵族免费聊天 0:开启 1:关闭
 */
public int nobleChatFree;

/**
 * 贵宾席费用
 */
public double VIPStatesFee;

/**
 * 匹配成功后倒计时(秒)
 */
public int showMatched;

/**
 * 用户注销内容
 */
public String userCancel;

/**
 * 1v1通话只能是贵族 0:开启 1:关闭
 */
public int authIsNoble;

/**
 * 短视频试看时间(秒)
 */
public int shortVideoTrialTime;

/**
 * 直播云拉流地址
 */
public String styPull;

/**
 * 踢出时长
 */
public int kickTime;

/**
 * 视频开播认证限制 0:开启认证 1:关闭认证
 */
public int authIslimit;

/**
 * banner跳转方式 0:app内 1:外部浏览器
 */
public int jumpMode;

/**
 * 连麦等级限制
 */
public int miclimit;

/**
 * 动态认证限制 0:开启认证 1:关闭认证
 */
public int authVideo;

/**
 * 短视频是否需要收费 0:开启 1:关闭
 */
public int isShortVideoFee;

/**
 * pk倒计时时长(秒)
 */
public int pkCountdown;

/**
 * 弹幕费用
 */
public int barrageFee;

/**
 * 发言等级限制
 */
public int barrageLimit;

/**
 * 聊场是否直播显示用户余额 0:开启 1:关闭
 */
public int isSayCoin;

/**
 * 美颜开关
 */
public int beautySwitch;

/**
 * 1v1是否评论开关 0:开启 1:关闭
 */
public int isComment;

/**
 * PK时长(秒)
 */
public int pkTime;

/**
 * 主播和主播发起通话 0:开启 1:关闭
 */
public int anchorToAnchor;

/**
 * 美颜key
 */
public String beautyKey;

/**
 * 谁看过我是否需要VIP 0:开启 1:关闭
 */
public int isVipSee;

/**
 * 允许主播和主播聊天 0:开启 1:关闭
 */
public int anchorTalkAnchor;

/**
 * 女性才能认证 0:开启 1:关闭
 */
public int authIsSex;

/**
 * 私聊扣费金额
 */
public int privateCoin;

/**
 * 注册赠送通话次数
 */
public int registerCallSecond;

/**
 * 最少发起金币
 */
public int lessCoin;

/**
 * 同性别女是否可以通话 0:主播和主播不能通话 1:主播与主播可以通话
 */
public int fTalkaa;

/**
 * 同性别女是否可以通话 0:用户和用户不能通话 1:用户与用户可以通话
 */
public int fTalkuu;

/**
 * 一对一金币费用
 */
public int oooFee;

/**
 * 短视频认证限制 0:开启认证 1:关闭认证
 */
public int authShortVideo;

/**
 * 直播限制等级
 */
public int levelLimit;

/**
 * 短视频是否需要审核 0:开启 1:关闭
 */
public int isAuthShortVideo;

/**
 * 是否必须绑定手机号码 0:开启 1:关闭
 */
public int isBindPhone;

/**
 * 直播间查看主播联系方式
 */
public int takeAnchorContact;

/**
 * 允许用户和用户聊天 0:开启 1:关闭
 */
public int userTalkUser;

/**
 * 允许主播和用户聊天 0:开启 1:关闭
 */
public int anchorTalkUser;

/**
 * 用户和用户发起通话 0:开启 1:关闭
 */
public int userToUser;

/**
 * 直播云推流地址
 */
public String styPush;

/**
 * 直播云key
 */
public String styKey;

/**
 * 同性别男是否可以通话 0:用户和主播不能通话 1:用户与主播可以通话
 */
public int mTalkua;

/**
 * 私聊扣费提示
 */
public String privateChatDeductionTips;

/**
 * 隐私协议提示
 */
public String xieyiRule;

/**
 * 禁言时长
 */
public int shuttime;

/**
 * PK惩罚时长(秒)
 */
public int punishTime;

/**
 * 全局飘屏礼物金额
 */
public int bidFee;

/**
 * CDN开关
 */
public int cdnSwitch;

/**
 * 同性别男是否可以通话 0:主播和主播不能通话 1:主播与主播可以通话
 */
public int mTalkaa;

/**
 * 同性别男是否可以通话 0:用户和用户不能通话 1:用户与用户可以通话
 */
public int mTalkuu;

/**
 * 注册赠送通话时长
 */
public int registerCallTime;

   public AdminLiveConfig(Parcel in) 
{
chatServer=in.readString();
isShowCoin=in.readInt();
logOffSwitch=in.readInt();
drawTime=in.readInt();
rushtotalkExplian=in.readString();
shortVideoTrial=in.readInt();
MultiplayerRule=in.readString();
isNobleChat=in.readInt();
withdrawalRule=in.readString();
liveRoomAnnouncement=in.readString();
anchorPerc=in.readDouble();
isShortVideoNobleFree=in.readInt();
fTalkNeedAuth=in.readInt();
pushMusic=in.readString();
id=in.readLong();
voiceStartCertification=in.readInt();
levelIslimit=in.readInt();
fTalkua=in.readInt();
nobleChatFree=in.readInt();
VIPStatesFee=in.readDouble();
showMatched=in.readInt();
userCancel=in.readString();
authIsNoble=in.readInt();
shortVideoTrialTime=in.readInt();
styPull=in.readString();
kickTime=in.readInt();
authIslimit=in.readInt();
jumpMode=in.readInt();
miclimit=in.readInt();
authVideo=in.readInt();
isShortVideoFee=in.readInt();
pkCountdown=in.readInt();
barrageFee=in.readInt();
barrageLimit=in.readInt();
isSayCoin=in.readInt();
beautySwitch=in.readInt();
isComment=in.readInt();
pkTime=in.readInt();
anchorToAnchor=in.readInt();
beautyKey=in.readString();
isVipSee=in.readInt();
anchorTalkAnchor=in.readInt();
authIsSex=in.readInt();
privateCoin=in.readInt();
registerCallSecond=in.readInt();
lessCoin=in.readInt();
fTalkaa=in.readInt();
fTalkuu=in.readInt();
oooFee=in.readInt();
authShortVideo=in.readInt();
levelLimit=in.readInt();
isAuthShortVideo=in.readInt();
isBindPhone=in.readInt();
takeAnchorContact=in.readInt();
userTalkUser=in.readInt();
anchorTalkUser=in.readInt();
userToUser=in.readInt();
styPush=in.readString();
styKey=in.readString();
mTalkua=in.readInt();
privateChatDeductionTips=in.readString();
xieyiRule=in.readString();
shuttime=in.readInt();
punishTime=in.readInt();
bidFee=in.readInt();
cdnSwitch=in.readInt();
mTalkaa=in.readInt();
mTalkuu=in.readInt();
registerCallTime=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(chatServer);
dest.writeInt(isShowCoin);
dest.writeInt(logOffSwitch);
dest.writeInt(drawTime);
dest.writeString(rushtotalkExplian);
dest.writeInt(shortVideoTrial);
dest.writeString(MultiplayerRule);
dest.writeInt(isNobleChat);
dest.writeString(withdrawalRule);
dest.writeString(liveRoomAnnouncement);
dest.writeDouble(anchorPerc);
dest.writeInt(isShortVideoNobleFree);
dest.writeInt(fTalkNeedAuth);
dest.writeString(pushMusic);
dest.writeLong(id);
dest.writeInt(voiceStartCertification);
dest.writeInt(levelIslimit);
dest.writeInt(fTalkua);
dest.writeInt(nobleChatFree);
dest.writeDouble(VIPStatesFee);
dest.writeInt(showMatched);
dest.writeString(userCancel);
dest.writeInt(authIsNoble);
dest.writeInt(shortVideoTrialTime);
dest.writeString(styPull);
dest.writeInt(kickTime);
dest.writeInt(authIslimit);
dest.writeInt(jumpMode);
dest.writeInt(miclimit);
dest.writeInt(authVideo);
dest.writeInt(isShortVideoFee);
dest.writeInt(pkCountdown);
dest.writeInt(barrageFee);
dest.writeInt(barrageLimit);
dest.writeInt(isSayCoin);
dest.writeInt(beautySwitch);
dest.writeInt(isComment);
dest.writeInt(pkTime);
dest.writeInt(anchorToAnchor);
dest.writeString(beautyKey);
dest.writeInt(isVipSee);
dest.writeInt(anchorTalkAnchor);
dest.writeInt(authIsSex);
dest.writeInt(privateCoin);
dest.writeInt(registerCallSecond);
dest.writeInt(lessCoin);
dest.writeInt(fTalkaa);
dest.writeInt(fTalkuu);
dest.writeInt(oooFee);
dest.writeInt(authShortVideo);
dest.writeInt(levelLimit);
dest.writeInt(isAuthShortVideo);
dest.writeInt(isBindPhone);
dest.writeInt(takeAnchorContact);
dest.writeInt(userTalkUser);
dest.writeInt(anchorTalkUser);
dest.writeInt(userToUser);
dest.writeString(styPush);
dest.writeString(styKey);
dest.writeInt(mTalkua);
dest.writeString(privateChatDeductionTips);
dest.writeString(xieyiRule);
dest.writeInt(shuttime);
dest.writeInt(punishTime);
dest.writeInt(bidFee);
dest.writeInt(cdnSwitch);
dest.writeInt(mTalkaa);
dest.writeInt(mTalkuu);
dest.writeInt(registerCallTime);

}

  public static void cloneObj(AdminLiveConfig source,AdminLiveConfig target)
{

target.chatServer=source.chatServer;

target.isShowCoin=source.isShowCoin;

target.logOffSwitch=source.logOffSwitch;

target.drawTime=source.drawTime;

target.rushtotalkExplian=source.rushtotalkExplian;

target.shortVideoTrial=source.shortVideoTrial;

target.MultiplayerRule=source.MultiplayerRule;

target.isNobleChat=source.isNobleChat;

target.withdrawalRule=source.withdrawalRule;

target.liveRoomAnnouncement=source.liveRoomAnnouncement;

target.anchorPerc=source.anchorPerc;

target.isShortVideoNobleFree=source.isShortVideoNobleFree;

target.fTalkNeedAuth=source.fTalkNeedAuth;

target.pushMusic=source.pushMusic;

target.id=source.id;

target.voiceStartCertification=source.voiceStartCertification;

target.levelIslimit=source.levelIslimit;

target.fTalkua=source.fTalkua;

target.nobleChatFree=source.nobleChatFree;

target.VIPStatesFee=source.VIPStatesFee;

target.showMatched=source.showMatched;

target.userCancel=source.userCancel;

target.authIsNoble=source.authIsNoble;

target.shortVideoTrialTime=source.shortVideoTrialTime;

target.styPull=source.styPull;

target.kickTime=source.kickTime;

target.authIslimit=source.authIslimit;

target.jumpMode=source.jumpMode;

target.miclimit=source.miclimit;

target.authVideo=source.authVideo;

target.isShortVideoFee=source.isShortVideoFee;

target.pkCountdown=source.pkCountdown;

target.barrageFee=source.barrageFee;

target.barrageLimit=source.barrageLimit;

target.isSayCoin=source.isSayCoin;

target.beautySwitch=source.beautySwitch;

target.isComment=source.isComment;

target.pkTime=source.pkTime;

target.anchorToAnchor=source.anchorToAnchor;

target.beautyKey=source.beautyKey;

target.isVipSee=source.isVipSee;

target.anchorTalkAnchor=source.anchorTalkAnchor;

target.authIsSex=source.authIsSex;

target.privateCoin=source.privateCoin;

target.registerCallSecond=source.registerCallSecond;

target.lessCoin=source.lessCoin;

target.fTalkaa=source.fTalkaa;

target.fTalkuu=source.fTalkuu;

target.oooFee=source.oooFee;

target.authShortVideo=source.authShortVideo;

target.levelLimit=source.levelLimit;

target.isAuthShortVideo=source.isAuthShortVideo;

target.isBindPhone=source.isBindPhone;

target.takeAnchorContact=source.takeAnchorContact;

target.userTalkUser=source.userTalkUser;

target.anchorTalkUser=source.anchorTalkUser;

target.userToUser=source.userToUser;

target.styPush=source.styPush;

target.styKey=source.styKey;

target.mTalkua=source.mTalkua;

target.privateChatDeductionTips=source.privateChatDeductionTips;

target.xieyiRule=source.xieyiRule;

target.shuttime=source.shuttime;

target.punishTime=source.punishTime;

target.bidFee=source.bidFee;

target.cdnSwitch=source.cdnSwitch;

target.mTalkaa=source.mTalkaa;

target.mTalkuu=source.mTalkuu;

target.registerCallTime=source.registerCallTime;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdminLiveConfig> CREATOR = new Creator<AdminLiveConfig>() {
        @Override
        public AdminLiveConfig createFromParcel(Parcel in) {
            return new AdminLiveConfig(in);
        }

        @Override
        public AdminLiveConfig[] newArray(int size) {
            return new AdminLiveConfig[size];
        }
    };
}
