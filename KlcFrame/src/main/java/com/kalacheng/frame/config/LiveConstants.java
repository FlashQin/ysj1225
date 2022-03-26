package com.kalacheng.frame.config;

/**
 * 此文件应该被分成多个文件。
 * 1、一些Constants信息。
 * 2、多人直播间共享数据。
 * 3、一对一直播间共享数据。
 * 4、多人语音直播间共享数据。
 */
public class LiveConstants {
    public static long ROOMID;
    public static long ANCHORID;
    public static String PULL;
    public static String PUSH;
    public static IDENTITY IDENTITY;
    public static long FEEUID;
    public static double OOOFEE;//一对一收费标准
    public static long LinkSessionID;//一对一收费标准
    public static int LiveType;//直播类型 1是多人直播 ，2是一对一语音和视频

    public static boolean IsLookLive = false;//是否在观看直播

    public static boolean IsRemoteAudio = false;//是否静音
    public static final String VOTES = "魅力值";//原“映票”

    public static boolean VoiceAnchorInvitation = false;//判断是否是主播邀请

    public static long activeId = 0;

    public static boolean IsStopPushStream = false;//静音所有远端音频

    public static boolean isDisplayRobChat = false;//1V1，显示抢单的弹框，false 显示；true 不显示

    public static int isInsideRoomType = 0;//是否在房间内  0是房间内  1是视频房间  2是语音房间

    public static boolean isSamll = false;//语音直播间 是否是最小化。

    public static boolean isEndLive = false;//是否结束直播

    public static boolean isPalyMusic = false;//判断是否在播放音乐

    public static boolean isJump = false;// 判断是否推送点进来。

    /**
     * 视频直播 主播用户连麦状态
     */
    public static int liveMicStatus = 0;

    /**
     * 一对一直播会话ID；
     */
    public static long mOOOSessionID;

    /**
     * 一对一直播邀请，发起者
     */
    public static boolean mIsOOOSend;

    public static int VoiceUpStatus = 0;//语音自动上麦的状态值 //0 自动上麦开 ，1 自动上麦关

    public static int VoiceLiveStatus;//语音直播状态 直播状态1正常直播2连麦3互动4pk

    public static long TOUID;

    public static int LiveAddress = 0;//0空闲状态；1在多人直播；2在一对一直播

    public static int UpMikeState = 1;//1 未上麦 ，2 上麦申请中 ，3以上麦


    public enum IDENTITY {
        ANCHOR, AUDIENCE, BROADCAST
    }

    public static double VIDEORATIO = 0.32;

    public static String LM_ReceiveStartLinkMic = "LM_ReceiveStartLinkMic"; // 主播同意连麦 （自己身份为用户）
    public static String LM_LaunchCloseLinkMic = "LM_LaunchCloseLinkMic"; // 退出连麦
    public static String LM_LaunchInteraction = "LM_LaunchInteraction";
    public static String LM_ReceiveCloseInteraction = "LM_ReceiveCloseInteraction";//主播PK连麦断开
    public static String LM_StartInteraction = "LM_StartInteraction";
    public static String LM_StartFloatingScreen = "LM_StartFloatingScreen";
    public static String LM_ReceiveCloseFloatingScreen = "LM_ReceiveCloseFloatingScreen";

    public static String LM_Small = "LM_Small";//最小化
    public static String LM_isSmall = "LM_isSmall";//最小化 (用于跳转到观众Activity 通知Component 是最小化跳转进来)
    public static String LM_SmallButton = "LM_SmallButton";//最小化按钮操作

    public static String LM_CloseLive = "LM_CloseLive";
    public static String LM_BackHome = "LM_BackHome";
    public static String LM_ChoiceLiveType = "LM_ChoiceLiveType";//选择直播类型
    public static String LM_ChoiceLiveTypeValue = "LM_ChoiceLiveTypeValue";//选择直播类型值
    public static String LM_ChoiceChannelType = "LM_ChoiceChannelType";//选择直播频道
    public static String LM_ChoiceChannelTypeValue = "LM_ChoiceChannelTypeValue";//选择直播频道类型值
    public static String LM_LookNumber = "LM_LookNumber";//视频直播查看观看人数
    public static String LM_LiveMore = "LM_LiveMore";//视频直播查看观看人数
    public static String LM_LiveSetting = "LM_LiveSetting";//视频主播设置

    public static String LM_LiveForbidden = "LM_LiveForbidden";//主播禁言列表
    public static String LM_LiveAdministrators = "LM_LiveAdministrators";//主播管理员列表
    public static String LM_KickList = "LM_KickList";//主播管理员列表
    public static String LM_LiveGuard = "LM_LiveGuard";//主播守护
    public static String LM_SendGift = "LM_SendGift";//赠送礼物
    public static String LM_AddFansGroup = "LM_AddFansGroup";//加入粉丝团
    public static String LM_AnchorTask = "LM_AnchorTask";//主播任务
    public static String LM_AudienceTask = "LM_AudienceTask";//观众任务
    public static String LM_ModifyRoomNotice = "LM_ModifyRoomNotice";//主播修改直播公告
    public static String LM_ChoiceLiveTypeSusser = "LM_ChoiceLiveTypeSusser";//主播修改直播类型成功
    public static String LM_LiveTime = "LM_LiveTime";//直播时长
    public static String LM_UserFollew = "LM_UserFollew";//用户关注
    public static String LM_UserFollewSusser = "LM_UserFollewSusser";//用户关注
    public static String LM_NoMoney = "LM_NoMoney";//余额不足充值
    public static String LM_LiveHot = "LM_LiveHot";//活力值
    public static String LM_TipsAddFansGroup = "LM_TipsAddFansGroup";//提示用户加入粉丝团
    public static String LM_AddFansGroupSusser = "LM_AddFansGroupSusser";//加入粉丝团成功
    public static String LM_OpenSvipSusser = "LM_OpenSvipSusser";//开通svip成功
    public static String LM_OneFeeCall = "LM_OneFeeCall";//一对一免费通话提示
    public static String LM_GetMapAddress = "LM_GetMapAddress";//获取地图定位


    public static String LM_IsRequestMick = "LM_IsRequestMick";//连麦请求控制
    public static String LM_IsRequestPK = "LM_IsRequestPK";//pk请求控制

    //
    public static String LM_OpenVoiceLiveTime = "LM_OpenVoiceLiveTime";//语音开播倒计时

    //开播数据存储
    public static String LiveOpenValue = "LiveOpenValue";
    //语音开播数据存储
    public static String VoiceLiveOpenValue = "VoiceLiveOpenValue";

    public static String LM_DialogDismiss = "LM_DialogDismiss";
    public static String FunctionMsg = "FunctionMsg";
    public static String LM_ExitRoom = "LM_ExitRoom";
//    public static String LM_StopAndPlayRoom = "LM_StopAndPlayRoom"; // 房间暂停或播放 （付费房间 门票房间 付费使用  用于弹框）
    public static String LM_StopAndPlayMedia = "LM_StopAndPlayMedia"; // 房间暂停或播放 （暂停直播 视频和声音 用于控制播放）
    public static String LM_Deduction = "LM_Deduction"; // 扣费验证 （付费房间 门票房间 贵族房间  用于扣费）
    public static String LM_OpenChat = "LM_OpenChat";
    public static String Information = "Information";
    public static String LiveShare = "LiveShare";
    public static String LM_GuardNumber = "LM_GuardNumber";
    public static String LM_PKStart = "LM_PKStart";
    public static String LM_PKFinish = "LM_PKFinish";
    public static String LM_OpenLiveMsg = "LM_OpenLiveMsg";
    public static String LM_BeautyShow = "LM_BeautyShow";
    public static String RoomInfoList = "RoomInfoList"; // AppJoinRoomVO App 加入直播间响应
    public static String VoiceRoomJoin = "VoiceRoomJoin";//加入语音房间
    public static String LM_HttpCloseLive = "LM_HttpCloseLive";
    public static String LM_MsgContent = "LM_MsgContent";
    public static String LM_MessageForGift = "LM_MessageForGift";//火力值修改
    public static String LM_ChangePKValue = "LM_ChangePKValue";
    public static String LM_GiftMsg = "LM_GiftMsg";
    public static String LM_Music = "LM_Music";//音乐
    public static String LM_Music_UpLoad = "LM_Music_UpLoad";//上传音乐
    public static String LM_WishList = "LM_WishList";//心愿单
    public static String LM_AddWishList = "LM_AddWishList";//心愿单
    public static String LM_AddWishListSuccess = "LM_AddWishListSuccess";//心愿单成功
    public static String LM_VOICE_LIVE_AIR = "voiceLiveAir";//音效氛围
    public static String LM_VOICE_LIVE_EMJ = "voiceLiveEmj";//表情包
    public static String LM_MusicVoice = "LM_MusicVoice";//音乐声音

    //语音
    public static String LM_VoicePkDialog = "LM_VoicePkDialog";
    public static String LM_VoicePkMacthDialog = "LM_VoicePkMacthDialog";
    public static String LM_VoicePkEnd = "LM_VoicePkEnd";

    public static String LM_VoicePKConnection = "LM_VoicePKConnection";//连麦
    public static String LM_VoiceUpMikeRequest = "LM_VoiceUpMikeRequest";//上麦请求
    public static String LM_VoiceUpMikeRequest_OK = "LM_VoiceUpMikeRequest_OK";//上麦请求成功
    public static String LM_VoiceMore = "LM_VoiceMore";//更多
    public static String LM_VoiceGiftSend = "LM_VoiceGiftSend";//语音送礼
    public static String LM_VoiceBuyVipSeats = "LM_VoiceBuyVipSeats";//语音购买贵宾席
    public static String LM_VoiceBuyVipSeatsSuccess = "LM_VoiceBuyVipSeatsSuccess";//语音购买贵宾席成功
    public static String LM_UserApplyConnectMike = "LM_UserApplyConnectMike";//直播间内游客申请上麦，给主播发送申请通知信息
    public static String LM_AnchorOperationMike = "LM_AnchorOperationMike";//主播操作麦上的人

    public static String LM_AnchorSticker = "LM_AnchorSticker";//主播动态表情


    public static String LM_VoiceGiveBeautifulNumber = "LM_VoiceGiveBeautifulNumber";//赠送靓号


    public static String Socket = "Socket";

    //一对一语音
    public static String LM_OneVoiceInvitationFailed = "LM_OneVoiceInvitationFailed";//邀请失败
    public static String LM_OneVoiceVolume = "LM_OneVoiceVolume";//邀请失败
    public static String LM_OneVoiceMike = "LM_OneVoiceMike";//一对一语音麦的状态

    //一对一视频
    public static String LM_OOOLiveJoinRoom = "LM_OOOLiveJoinRoom";//一对一视频邀请
    public static String LM_OOOLiveType = "LM_OOOLiveType";//发起通话
    public static String LM_OOOLiveHangUp = "LM_OOOLiveHangUp";//挂断
    public static String LM_OOOLinkTTT = "LM_OOOLinkTTT";//链接直播云
    public static String LM_OOOLiveLinkOK = "LM_OOOLiveLinkOK";//视频接通
    public static String LM_OOOLiveTTTEstablish = "LM_OOOLiveTTTEstablish";//创建3T云回调
    public static String LM_OOOLiveTTTJoin = "LM_OOOLiveTTTJoin";//加入3T云回调
    public static String LM_OOOLiveMore = "LM_OOOLiveMore";//更过
    public static String LM_OOOLiveSendGift = "LM_OOOLiveSendGift";//一对一视频送礼
    public static String LM_OOOLiveGoldInsufficient = "LM_OOOLiveGoldInsufficient";//一对一视频金额不足
    public static String LM_OOOTTTErrorHangUp = "LM_OOOTTTErrorHangUp";//直播云异常退出
    public static String LM_OOOCallEnd = "LM_OOOCallEnd";//通话结束
    public static String LM_OOOEndRequest = "LM_OOOEndRequest";//通话结束请求
    public static String LM_OOOEndRequestGetTime = "LM_OOOEndRequestGetTime";//通话结束请求获取时间
    public static String LM_OOOGoldInsufficient = "LM_OOOGoldInsufficient";//通话金额不足
    public static String LM_OOOUpDataGold = "LM_OOOUpDataGold";//通话金额不足
    public static String LM_OOOCloseLive = "LM_OOOCloseLive";
    public static String LM_OOOLiveOpenSVip = "LM_OOOLiveOpenSVip";//开通超级svip
    public static String LM_OOOLiveChoiceSVip = "LM_OOOLiveChoiceSVip";//打开主播列表svip
    public static String LM_OOOLiveSVipPeopleSatisfy = "LM_OOOLiveSVipPeopleSatisfy";//svip邀请人数到了
    public static String LM_OOOLiveSVipPeopleSignOut = "LM_OOOLiveSVipPeopleSignOut";//svip邀请人退出直播间
    public static String LM_OOOLiveSVipIsKickOut = "LM_OOOLiveSVipIsKickOut";//svip是否踢出邀请人
    public static String LM_OOOLiveSVipSwitchUI = "LM_OOOLiveSVipSwitchUI";//svip是否踢出邀请人
    public static String LM_OOOLiveSVipJoinSuccess = "LM_OOOLiveSVipJoinSuccess";//svip副播加入成功
    public static String LM_OOOLiveSVipSSideshowignOut = "LM_OOOLiveSVipSSideshowignOut";//svip副播退出或者踢出成功
    public static String LM_OOOAudienceNoVip = "LM_OOOAudienceNoVip";//用户没有vip提示
    public static String LM_OOOAudienceBGM = "LM_OOOAudienceBgm";// 主播BGM 背景音乐


    public static String LM_OOOLiveSVipEstoppelSpeake = "LM_OOOLiveSVipEstoppelSpeake";//svip禁止某人发言

    //svip大小图切换
    public static String LM_OOOLiveSVipSwitchSmallTOBig = "LM_OOOLiveSVipSwitchSmallTOBig";//小去大
    public static String LM_OOOLiveSVipSwitchBigTOSmall = "LM_OOOLiveSVipSwitchBigTOSmall";//大去小


    //滑动屏幕的方向
    public static String LM_LIFT = "LM_LIFT";//左
    public static String LM_RIGHT = "LM_RIGHT";//右

    //多人语音
    public static String LM_UpMike = "LM_UpMike";//上麦
    public static String LM_DownMike = "LM_DownMike";//下麦
    public static String LM_CloseMike = "LM_CloseMike";//封麦
    public static String LM_OffMike = "LM_CloseMike";//开关麦(音量)操作
    public static String LM_AnchorOffMike = "LM_AnchorOffMike";//主播开关麦(音量)操作

    public static String LM_VolumeValue = "LM_VolumeValue";//多人语音音量值
    public static String LM_BGImage = "LM_BGImage";//更改背景图片

    public static String LM_VoiceMatchSuccess = "LM_VoiceMatchSuccess";//语音匹配成功

    public static String LM_VoiceOBOPkUpData = "LM_VoiceOBOPkUpData";//单人pk中更新数据
    public static String LM_VoicePkMacthOBO = "LM_VoicePkMacthOBO";//单人pk准备
    public static String LM_VoicePKOBOPunishment = "LM_VoicePKOBOPunishment";//语音一对一pk惩罚
    public static String LM_VoicePkOBOStart = "LM_VoicePkStart";//语音一对一pk开始
    public static String LM_VoiceOBOCancelPK = "LM_VoiceOBOCancelPK";//退出一对一pk
    public static String LM_VoicePKOBOGiftValue = "LM_VoicePKOBOGiftValue";//一对一pk送礼数据
    public static String LM_VoicePKOBOExpression = "LM_VoicePKOBOExpression";//一对一pk表情表


    public static String LM_VoiceRoomPkUpData = "LM_VoiceRoomPkUpData";//房间内pk中更新数据
    public static String LM_VoicePkMacthRoom = "LM_VoicePkMacthRoom";//房间内pk准备
    public static String LM_VoiceRoomPK = "LM_VoiceRoomPK";//语音房间内pk
    public static String LM_VoiceRoomPKWin = "LM_VoiceRoomPKWin";//语音房间内pk胜利
    public static String LM_VoicePKRoomPunishment = "LM_VoicePKRoomPunishment";//语音房间内pk惩罚
    public static String LM_VoicePkRoomStart = "LM_VoicePkRoomStart";//语音房间pk开始
    public static String LM_VoiceRoomCancelPK = "LM_VoiceRoomCancelPK";//退出房间pk
    public static String LM_VoicePKRoomGiftValue = "LM_VoicePKRoomGiftValue";//房间pk送礼数据
    public static String LM_VoiceRoomPkUpExpression = "LM_VoiceRoomPkUpExpression";//房间pk更新表情


    public static String LM_VoicePKSeftSendGift = "LM_VoicePKSeftSendGift";//自己pk送礼
    public static String LM_VoicePKOtherSendGift = "LM_VoicePKOtherSendGift";//对方pk送礼


    public static String LM_VoicePkMacthTeam = "LM_VoicePkMacthTeam";//激情团队pk（直播间对直播间）准备
    public static String LM_VoicePKTeamPunishment = "LM_VoicePKTeamPunishment";//激情团队pk惩罚
    public static String LM_VoicePkTeamStart = "LM_VoicePkTeamStart";//激情团队pk开始
    public static String LM_VoiceTeamCancelPK = "LM_VoiceTeamCancelPK";//退出激情团队pk
    public static String LM_VoiceTeamPkUpData = "LM_VoiceTeamPkUpData";//激情团战更新数据
    public static String LM_VoicePKTeamGiftValue = "LM_VoicePKTeamGiftValue";//激情团战pk送礼数据


    public static String LM_VoicePkUserKicked = "LM_VoicePkUserKicked";//语音pk时用户退出连麦
    public static String LM_VoicePKPunishmentEnd = "LM_VoicePKPunishmentEnd";//语音pk惩罚结束
    public static String LM_VoicePkTimeOut = "LM_VoicePkTimeOut";//语音pk超时

    public static String LM_LiveRecharge = "LM_LiveRecharge";//充值

    public static String LM_LiveMusicChooice = "LM_LiveMusicChooice";//音乐选择


    //直播购
    public static String LM_LiveGoodsWindow = "LM_LiveGoodsWindow";//设置商品橱窗
    public static String LM_LiveGoodsActivity = "LM_LiveGoodsActivity";//设置直播购活动
    public static String LM_LiveGoodsList = "LM_LiveGoodsList";//设置直播购商品列表
    public static String LM_LiveSetExplainSuccess = "LM_LiveSetExplainSuccess";//设置直播购讲解成功
    public static String LM_UPDATE_LIVE_STATUS = "LM_UpdateLiveStatus"; // 切换普通直播和带货直播 type
    public static String LM_OPEN_CLOSE_MIC = "LM_OpenCloseMic"; // 是否开启麦克风

    //游戏
    public static String LM_LiveTreasureChest = "LM_LiveTreasureChest";//百宝箱

    // 缓存
    public static String Cache_SmallMessage = "Cache_SmallMessage";// 最小化 socket message消息
    public static String Cache_SmallStatus = "Cache_SmallStatus";// 最小化 状态


    public static String[] RANKTITLE = {"收益榜", "贡献榜"};

    public static String[] PAYSETTITLE = {"获取手机号", "获取微信号"};

    public static String[] USERFANSCONTRIBUTION = {"粉丝贡献榜", "在线观众席"};

    public static String[] GUARD = {"开通守护", "TA的守护"};

    public static String[] ANCHOR_GUARD = {"TA的守护"};

    public static String[] OPEN_GUARD = {"开通守护"};

    public static String[] HOTVALUE = {"今日收益榜", "累计收益榜"};

    public static String[] LIVEMUSIC = {"播放列表", "音乐库"};

    public static String[] DRESSING_CENTER = {"购买装扮", "使用装扮"};

    public enum LiveState {
        LIVE, ENDLIVE, ANCHORLINKMIC, AUDIENCELINKMIC, GAMEPK, NORMALPK
    }

    public enum PKType {
        NormalPK, GamePK
    }

    public enum LinkMicResponse {
        ACCEPT, REFUSE, NORESPONSE
    }


}
