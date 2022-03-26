package com.kalacheng.frame.config;

import com.kalacheng.base.http.HttpApiCallBackConvert;

/**
 * Created by hgy on 2019/9/5.
 * 路由path
 */
public class ARouterPath {
    /**
     * 主页
     */
    public static final String MainActivity = "/KlcMainPage/MainActivity";

    /**
     * 登录
     */
    public static final String LoginActivity = HttpApiCallBackConvert.LOGIN_AROUTER;

    /**
     * 注册
     */
    public static final String RegisterActivity = "/login/RegisterActivity";
    /**
     * 手机登录
     */
    public static final String PhoneLoginActivity = "/login/PhoneLoginActivity";
    /**
     * 修改密码
     */
    public static final String ChangePassword = "/login/ChangePasswordActivity";
    /**
     * 主播直播
     */
    public static final String LiveAnchorActivity = "/live/LiveAnchorActivity";

    /**
     * 观众直播
     */
    public static final String LiveAudienceActivity = "/live/LiveAudienceActivity";
    /**
     * 关闭直播
     */
    public static final String LiveEndActivity = "/live/LiveEndActivity";
    /**
     * 编辑资料
     */
    public static final String EidtInformation = "/KlcCenterCommon/EditInformationActivity";
    /**
     * 修改昵称
     */
    public static final String NameActivity = "/KlcCenterCommon/NameActivity";
    /**
     * 个性签名
     */
    public static final String PersonalActivity = "/KlcCenterCommon/PersonalActivity";
    /**
     * 编辑生日
     */
    public static final String BirthdayActivity = "/KlcCenterCommon/BirthdayActivity";
    /**
     * 我的标签
     */
    public static final String TagActivity = "/KlcCenterCommon/TagActivity";
    /**
     * 设置 - 1
     */
    public static final String SettingApp = "/KlcCenterCommon/SettingAppActivity";
    /**
     * 权限设置
     */
    public static final String PowerSetting = "/KlcCenterCommon/PowerSettingActivity";

    /**
     * 主播中心
     */
    public static final String MeAnchorCenter = "/KlcCenter/MeAnchorCenterActivity";

    /**
     * 关于我们
     */
    public static final String AboutUsActivity = "/KlcCenterCommon/AboutUsActivity";

    /**
     * 男朋友设置页面
     */
    public static final String MsgSetting = "/boyfriend/MsgSettingActivity";


    /**
     * 用户注销
     */
    public static final String AccountCancellationActivity = "/KlcCenterCommon/AccountCancellationActivity";
    /**
     * 账户验证
     */
    public static final String AccountCancellationConfirmActivity = "/KlcCenterCommon/AccountCancellationConfirmActivity";
    /**
     * 认证中心，短视频
     */
    public static final String AnchorCenterActivity = "/KlcCenterCommon/AnchorCenterActivity";
    /**
     * 个人主页
     */
    public static final String HomePage = "/KlcHomePage/HomePageActivity";

    /**
     * 搜索
     */
    public static final String Search = "/fans/SearchActivity";
    /**
     * 关注
     */
    public static final String Follow = "/fans/FollowActivity";
    /**
     * 粉丝
     */
    public static final String Fans = "/fans/FansActivity";
    /**
     * 房间管理
     */
    public static final String AdminList = "/KlcLiveCommon/RoomManagerActivity ";
    /**
     * 充值中心
     */
    public static final String MyCoinActivity = "/money/MyCoinActivity";
    /*
     * 礼物排行榜
     * */
    public static final String GiftContribution = "/KlcPoints/GiftContribution";
    /**
     * 贡献榜
     */
    public static final String FansContributionActivity = "/KlcPoints/FansContributionActivity";

    /*
     * 上传动态
     * */
    public static final String VideoPublish = "/KlcVideoRecord/VideoPublish";
    /*
     *  动态详情
     * */
    public static final String TrendDetails = "/KlcMessage/TrendActivity";
    /*
     * 视频查看
     * */
    public static final String LookVideo = "/KlcDynamicCircle/Video";
    /**
     * 社区
     */
    public static final String CommunityActivity = "/KlcDynamicCircle/CommunityActivity";

    //语音直播
    public static final String VoiceLive = "/KlcVoiceLive/VoiceLive";

    //语音背景图
    public static final String VoiceBg = "/KlcVoiceLive/VoiceBg";

    //加入语音房间
    public static final String JOINVoiceRoom = "/KlcVoiceLive/JOINVoiceRoom";


    //加入一对一语音
    public static final String OneVoiceLive = "/OneVoiceLive/JOINOneVoiceRoom";
    //求聊语音
    public static final String OneVoiceSeekChatLive = "/OneVoiceLive/OneVoiceSeekChatLive";

    //加入一对一视频
    public static final String One2OneLive = "/One2OneLive/JOINLive";
    //加入svip视频
    public static final String One2OneSvipLive = "/One2OneSvipLive/JOINLive";
    //邀请加入svip视频
    public static final String One2OneSvipSideshowLive = "/One2OneSvipLive/SideshowJOINLive";
    //求聊加入视频
    public static final String One2OneSeekChatLive = "/One2OneSvipLive/One2OneSeekChatLive";

    public static final String RankListActivity = "/KlcMain/RankListActivity";

    public static final String SquareTagActivity = "/KlcMain/SquareTagActivity";

    public static final String RequiredInfoActivity = "/login/RequiredInfoActivity";

    public static final String TagSelectActivity = "/login/TagSelectActivity";

    /**
     * 申请认证
     */
    public static final String ApplyAnchorActivity = "/KlcCenterCommon/ApplyAnchorActivity";
    /**
     * 身份证上传
     */
    public static final String UpLoadIdCardActivity = "/KlcCenterCommon/UpLoadIdCardActivity";
    /**
     * 上传认证视频
     */
    public static final String UpLoadAuthVideoActivity = "/KlcCenterCommon/UpLoadAuthVideoActivity";

    /**
     * 礼物墙
     */
    public static final String GiftActivity = "/KlcCommonView/GiftActivity";
    /**
     * 勋章墙
     */
    public static final String HonorActivity = "/KlcCommonView/HonorActivity";
    /**
     * 我的守护
     */
    public static final String GuardActivity = "/KlcCommonView/GuardActivity";
    /**
     * 举报
     */
    public static final String VideoReport = "/KlcCommonView/VideoReportActivity";
    /**
     * TA的直播
     */
    public static final String TALiveActivity = "/KlcCommonView/TALiveActivity";
    /**
     * WebView
     */
    public static final String WebActivity = "/KlcCommonView/WebActivity";
    /**
     * 收益中心
     */
    public static final String CashActivity = "/KlcCenterCommon/CashActivity";
    /**
     * 客服中心
     */
    public static final String CustomerServeActivity = "/KlcCenterCommon/CustomerServeActivity";
    /**
     * 装扮中心
     */
    public static final String DressingCenterActivity = "/KlcCenterCommon/DressingCenterActivity";
    /**
     * 提现账户
     */
    public static final String CashAccountActivity = "/KlcCenterCommon/CashAccountActivity";
    /**
     * 添加提现账户
     */
    public static final String AddCashAccountActivity = "/KlcCenterCommon/AddCashAccountActivity";
    /**
     * 邀请赚钱
     */
    public static final String InvitationCodeActivity = "/KlcCenterCommon/InvitationCodeActivity";
    /**
     * 收入排行
     */
    public static final String InvitationRankActivity = "/KlcCenterCommon/InvitationSortActivity";
    /**
     * 图片分享
     */
    public static final String SaveInvitationCodeActivity = "/KlcCenterCommon/SaveInvitationCodeActivity";
    /**
     * 佣金提现
     */
    public static final String InvitationExtractActivity = "/KlcCenterCommon/InvitationExtractActivity";
    /**
     * 等级特权
     */
    public static final String MyPrivilegeActivity = "/KlcCenterCommon/MyPrivilegeActivity";
    /**
     * 我的时间轴
     */
    public static final String MyTimeAxisActivity = "/KlcCenterCommon/MyTimeAxisActivity";
    /**
     * 粉丝团
     */
    public static final String FansGroupActivity = "/KlcCenterCommon/FansGroupActivity";
    /**
     * 付费设置
     */
    public static final String PaySettingActivity = "/KlcCenterCommon/PaySettingActivity";
    /**
     * 话费设置
     */
    public static final String One2OneCallActivity = "/KlcCenterCommon/One2OneCallActivity";
    /**
     * 一对一视频预览
     */
    public static final String One2OneVideo = "/KlcCenterCommon/One2OneVideo";
    /**
     * 我的声音
     */
    public static final String MyVoiceActivity = "/KlcCenterCommon/MyVoiceActivity";
    /**
     * 直播数据
     */
    public static final String FansLiveDataActivity = "/KlcCenterCommon/FansLiveDataActivity";
    /**
     * 用户任务中心
     */
    public static final String UserTaskCenterActivity = "/KlcCenterCommon/UserTaskCenterActivity";
    /**
     * 任务中心
     */
    public static final String TaskCenterActivity = "/KlcCenterCommon/TaskCenterActivity";

    /*
     * 青少年模式
     * */
    public static final String YoungPatternActivity = "/KlcCenterCommon/YoungPatternActivity";
    /*
     * 青少年模式设置密码
     * */
    public static final String YoungPatternSetPassWordActivity = "/KlcCenterCommon/YoungPatternSetPassWordActivity";

    /*
     * 青少年模式确认密码
     * */
    public static final String YoungPatternConfirmPassWordActivity = "/KlcCenterCommon/YoungPatternConfirmPassWordActivity";

    /**
     * 美颜设置
     */
    public static final String BeautySettingActivity = "/KlcCenterCommon/BeautySettingActivity";

    public static final String MeetAudienceSingleActivity = "/KlcMain/MeetAudienceSingleActivity";

    public static final String MeetAudienceManyActivity = "/KlcMain/MeetAudienceManyActivity";

    public static final String MeetAnchorActivity = "/KlcMain/MeetAnchorActivity";

    public static final String MapActivity = "/KlcMap/MapActivity";

    public static final String ChatRoomActivity = "/KlcMessage/ChatRoomActivity";

    public static final String OfficialNewsActivity = "/KlcMessage/OfficialNewsActivity";

    public static final String OfficialNewsDetailsActivity = "/KlcMessage/OfficialNewsDetailsActivity";

    public static final String ConversationListActivity = "/KlcMessage/ConversationListActivity";

    public static final String NotifyListActivity = "/KlcMessage/NotifyListActivity";

    public static final String ReviewsListActivity = "/KlcMessage/ReviewsListActivity";

    /**
     * 我加入的群组
     */
    public static final String MyJoinGroupActivity = "/KlcMessage/MyJoinGroupActivity";

    public static final String MyViewActivity = "/KlcShortVideo/MyViewActivity";

    public static final String VideoClassifyActivity = "/KlcShortVideo/VideoClassifyActivity";

    /**
     * 入驻协议
     */
    public static final String MoveInAgreeActivity = "/KlcShopping/MoveInAgreeActivity";
    public static final String VideoPlayActivity = "/KlcShortVideo/VideoPlayActivity";
    /**
     * 商家简介
     */
    public static final String UpShopDataActivity = "/KlcShopping/UpShopDataActivity";
    /**
     * 官方小店
     */
    public static final String ShopManageActivity = "/KlcShopping/ShopManageActivity";
    /**
     * 我的收入
     */
    public static final String MyIncomeActivity = "/KlcShopping/MyIncomeActivity";
    /**
     * 添加商品
     */
    public static final String AddGoodsActivity = "/KlcShopping/AddGoodsActivity";
    /**
     * 商品分类
     */
    public static final String GoodsClassifyActivity = "/KlcShopping/GoodsClassifyActivity";
    /**
     * 商品属性
     */
    public static final String GoodsAttributeActivity = "/KlcShopping/GoodsAttributeActivity";
    /**
     * 编辑属性价格
     */
    public static final String EditPriceActivity = "/KlcShopping/EditPriceActivity";
    /**
     * 商品管理
     */
    public static final String GoodsManageActivity = "/KlcShopping/GoodsManageActivity";
    /**
     * 直播预告
     */
    public static final String LiveBuyNoticeActivity = "/KlcShopping/LiveBuyNoticeActivity";
    /**
     * 提现
     */
    public static final String ShopCashActivity = "/KlcShopping/ShopCashActivity";
    /**
     * 商品详情
     */
    public static final String GoodsDetailsActivity = "/KlcShopping/GoodsDetailsActivity";
    /**
     * 购物车
     */
    public static final String ShoppingCartActivity = "/KlcShopping/ShoppingCartActivity";
    /**
     * 确认订单
     */
    public static final String SubmitOrderActivity = "/KlcShopping/SubmitOrderActivity";
    /**
     * 添加收货地址
     */
    public static final String AddAddressActivity = "/KlcShopping/AddAddressActivity";
    /**
     * 收货地址
     */
    public static final String AddressListActivity = "/KlcShopping/AddressListActivity";
    /**
     * 购物订单 - 买家
     */
    public static final String OrderManageActivity1 = "/KlcShopping/OrderManageActivity1";
    /**
     * 全部订单 - 卖家
     */
    public static final String OrderManageActivity2 = "/KlcShopping/OrderManageActivity2";
    /**
     * 小店预览
     */
    public static final String ShopActivity = "/KlcShopping/ShopActivity";
    /**
     * 订单详情
     */
    public static final String OrderDetailsActivity = "/KlcShopping/OrderDetailsActivity";
    /**
     * 物流详情
     */
    public static final String LogisdticsDetailsActivity = "/KlcShopping/LogisdticsDetailsActivity";
    /**
     * 选择物流
     */
    public static final String ChooseLogisticsActivity = "/KlcShopping/ChooseLogisticsActivity";
    /**
     * 审核退款申请
     */
    public static final String ReviewRefundActivity = "/KlcShopping/ReviewRefundActivity";

}
