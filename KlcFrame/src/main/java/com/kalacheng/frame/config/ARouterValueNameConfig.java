package com.kalacheng.frame.config;


/*
 * 路由跳转传值的key的名称
 * */
public class ARouterValueNameConfig {
    public static final String ANCHORID = "anchor_id";//主播id

    public static final String USERID = "user_id";//用户id

    public static final String LOOkUSERUD = "loog_id";//查看人id

    public static final String VOIDE = "video";//看视频

    public static final String VIDEO_URL = "videoUrl";
    public static final String VIDEO_FROM_RECORD = "videoFromRecord";
    public static final String VIDEO_MUSIC_BEAN = "videoMusicBean";
    public static final String VIDEO_MUSIC_NAME_PREFIX = "videoMusicName_";
    public static final String VIDEO_SAVE_TYPE = "videoSaveType";
    public static final String VIDEO_TIME_LONG = "videoTimeLong";
    public static final int VIDEO_SAVE_SAVE_AND_PUB = 1;//保存并发布
    public static final int VIDEO_SAVE_SAVE = 2;//仅保存
    public static final int VIDEO_SAVE_PUB = 3;//仅发布

    public static final String DYNAMIC_RESULT_TYPE = "dynamicResultType";//拍摄返回类型，0 图片；1 视频
    public static final String PICTURE_LIST = "pictureList";//图片上传
    public static final String VIDEO_DURATION = "videoDuration";
    public static final String VIDEO_PATH = "videoPath";
    public static final String VOICE_PATH = "voicePath";
    public static final String VideoType = "VIDEO_SAVE_SAVE_AND_PUB";
    public static final String SHORT_VIDEO = "shortVideo";

    public static final String FindPwdOrRegister = "FindPwdOrRegister";//找回密码或者注册
    public static final String FROM_PAGE = "from_page";//从那个页面跳转过来

    public static final String ACTIVITYID = "activityid";//活动id

    public static final String COMMENTNUM = "CommentNum";//评论数

    public static final String COMMEMTBEAN = "CommentBean";//评论对象

    public static final String ACTIVITYBEAN = "ActivityBean";//评论对象

    public static final String COMMENTTYPE = "commentType";//评论或者回复

    public static final String WEBURL = "weburl";
    public static final String WebTitleHide = "webTitleHide";//隐藏WebViewActivity标题栏
    public static final String WebActivityType = "WebActivityType";//WebViewActivity特殊类型

    public static final String LIVE_UID = "LIVE_UID";

    public static final String TO_UID = "TO_UID";

    public static final String RoomType = "RoomType";//房间类型

    public static final String ReportType = "ReportType";//举报类型

    public static final String Dynamic = "Dynamic";//动态

    public static final String Live = "Live";//直播举报

    public static final String Information = "Information";//信息详情举报

    public static final String VocieLiveBg = "VocieLiveBg";//语音直播背景


    public static final String VocieLiveRoom = "VocieLiveRoom";//加入语音房间

    public static final String isSmall = "isSmall";//加入语音房间是否是最小化

    public static final String Livetype = "Livetype";//直播类型

    public static final String LiveID = "LiveID";//直播id

    public static final String RoomID = "RoomID";//

    public static final String ShortVideoId = "ShortVideoId";//短视频id

    public static final String LiveRoomAnchorID = "LiveRoomAnchorID";//直播房间的主播id

    public static final String UpStatus = "upStatus";

    public static final String ShowID = "ShowID";

    public static final String ApiPkResultRoom = "ApiPkResultRoom";

    public static final String OOOLiveJFeeUid = "OOOLiveJFeeUid";//判断收钱方

    public static final String OOOLiveJoinRoom = "OOOLiveJoinRoom";//一对一视频

    public static final String OOOInviteRet = "OOOInviteRet";//一对一视频 通话验证 验证成功 把返回值 传到直播间内

    public static final String OOOLiveWaitTime = "OOOLiveWaitTime";//一对一视频

    public static final String OOOSeekChatLiveSideshow = "OOOSeekChatLiveSideshow";//svip一对一视频副播


    public static final String OOOLiveSvipReceiveJoin = "OOOLiveSvipReceiveJoin";//一对一svip接收邀请视频

    public static final String OOOLiveType = "OOOLiveType";//1 正常语音和视频 ，2 求聊

    public static final String isEdit = "isEdit";//一对一视频

    public static final String TYPE = "TYPE";//

    public static final String AppUsersCashAccount = "AppUsersCashAccount";

    public static final String AddCashAccountActivity = "AddCashAccountActivity";

    public static final String InviteDto = "InviteDto";

    public static final String AppHotSort = "AppHotSort";
    public static final String ApiCfgPayCallOneVsOne = "ApiCfgPayCallOneVsOne";

    public static final String HOT_ID = "hotId";
    public static final String Name = "Name";

    public static final String URL = "url";

    public static final String EDIT_USER_OTHER = "editOther";//修改个人信息
    public static final String EDIT_USER_PERSONAL = "editPersonal";//个性签名
    public static final String EDIT_USER_BIRTH = "editBirth";//生日
    public static final String EDIT_USER_CONSTELLATION = "editConstellation";//星座

    public static final String GUARD_TITLE = "guardTitle";//我的守护，标题
    public static final String GUARD_TYPE = "guardType";//类型
    public static final String GUARD_ID = "guardId";//用户id
    public static final String USER_ID = "userId";//用户id

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String ADDRESS = "ADDRESS";
    public static final String CITY = "city";
    public static final String ApiUserInfo = "ApiUserInfo";
    public static final String ApiShortVideoDto = "ApiShortVideoDto";

    public static final String IS_SINGLE = "isSingle";
    public static final String TITLE = "title";//我的守护，标题
    public static final String POSITION = "position";//我的守护，标题
    public static final String Beans = "beans";//我的守护，标题
    public static final String ITEM_POSITION = "itemPosition";//动态 第几条位置 （用于点赞评论后 刷新外层列表使用）
    public static final String COMMENT_LOCATION = "commentLocation";// 记录是哪个界面跳转过来的  （用于记录哪个界面跳转过来 评论点赞后 刷新改界面的列表使用）
    public static final String VIDEO_TYPE = "videoType";
    public static final String MESSAGE_TYPE = "messageType";// 来源-1查看详情1消息评论2消息点赞
    public static final String COMMENT_ID = "commentId";// 评论id(通知列表的commentId)没有则传-1
    public static final String VIDEO_PAGE = "videoPage";
    public static final String CLASSIFY_ID = "classifyId";
    public static final String VIDEO_SORT = "videoSort";
    public static final String VIDEO_WORKS_USER_ID = "videoWorksUserId";
    public static final String VIDEO_WORKS_TYPE = "videoWorksType";
    public static final String TITLE_NAME = "titleName";
    public static final String POST = "post";
    public static final String COMMUNITY_TYPE = "communityType";
    public static final String COMMUNITY_HOT_ID = "communityHotId";
    public static final String COMMUNITY_UID = "communityUid";

    public static final String AuditStatus = "AuditStatus"; // 官方小店审核状态
    public static final String AuditRemake = "AuditRemake"; // 官方小店审核内容
    public static final String isUp = "isUp";

    public static final String goodsId = "goodsId";
    public static final String shopCarBeans = "shopCarBeans";
    public static final String shopAddress = "shopAddress";
    public static final String businessId = "businessId";
    public static final String shopGoods = "shopGoods";
    public static final String ShopAttrList = "ShopAttrList";
    public static final String shopBusiness = "shopBusiness";
    public static final String orderId = "orderId";
    public static final String addressId = "addressId";
    public static final String addressBean = "addressBean";
    public static final String orderNo = "orderNo";
    public static final String logisticsNumber = "logisticsNumber";

    //动态详情
    public static final String commentType = "commentType";
    public static final String trendDetailsType = "type";
    public static final String trendDetailsCommentId = "commentId";
    public static final String trendDetailsVideoId = "videoId";
    public static final String trendDetailsUserName = "userName";


}
