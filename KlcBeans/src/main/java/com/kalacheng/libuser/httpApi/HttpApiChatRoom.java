package com.kalacheng.libuser.httpApi;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;


/**
 * 消息API
 */
public class HttpApiChatRoom {


    /**
     * 系统通知类型列表
     */
    public static void getAppSystemNoticeList(HttpApiCallBackPageArr<com.kalacheng.libuser.model.AppSystemNotice> callback) {
        HttpClient.getInstance().post("/api/message/getAppSystemNoticeList", "/api/message/getAppSystemNoticeList")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .execute(new HttpApiCallBackPageArrConvert(callback, com.kalacheng.libuser.model.AppSystemNotice_RetPageArr.class));

    }


    /**
     * 用户系统通知列表
     *
     * @param noticId  通知类型id
     * @param page     页数
     * @param pageSize 每页的条数
     */
    public static void getAppSystemNoticeUserList(com.kalacheng.libuser.model_fun.ChatRoom_getAppSystemNoticeUserList _mdl, HttpApiCallBackPageArr<com.kalacheng.libuser.model.AppSystemNoticeUser> callback) {
        HttpClient.getInstance().post("/api/message/getAppSystemNoticeUserList", "/api/message/getAppSystemNoticeUserList")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .params("noticId", _mdl.noticId)
                .params("page", _mdl.page)
                .params("pageSize", _mdl.pageSize)
                .execute(new HttpApiCallBackPageArrConvert(callback, com.kalacheng.libuser.model.AppSystemNoticeUser_RetPageArr.class));

    }


    /**
     * 用户系统通知列表
     *
     * @param noticId  通知类型id
     * @param page     页数
     * @param pageSize 每页的条数
     */
    public static void getAppSystemNoticeUserList(int noticId, int page, int pageSize, HttpApiCallBackPageArr<com.kalacheng.libuser.model.AppSystemNoticeUser> callback) {
        HttpClient.getInstance().post("/api/message/getAppSystemNoticeUserList", "/api/message/getAppSystemNoticeUserList")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .params("noticId", noticId)
                .params("page", page)
                .params("pageSize", pageSize)
                .execute(new HttpApiCallBackPageArrConvert(callback, com.kalacheng.libuser.model.AppSystemNoticeUser_RetPageArr.class));

    }


    /**
     * 消息-拉黑操作
     *
     * @param type   类型0全部1语音2视频
     * @param userId 拉黑用户id
     */
    public static void blockOperation(com.kalacheng.libuser.model_fun.ChatRoom_blockOperation _mdl, HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback) {
        HttpClient.getInstance().post("/api/message/blockOperation", "/api/message/blockOperation")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .params("type", _mdl.type)
                .params("userId", _mdl.userId)
                .execute(new HttpApiCallBackConvert(callback, com.kalacheng.libbas.model.HttpNone_Ret.class));

    }


    /**
     * 消息-拉黑操作
     *
     * @param type   类型0全部1语音2视频
     * @param userId 拉黑用户id
     */
    public static void blockOperation(int type, long userId, HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback) {
        HttpClient.getInstance().post("/api/message/blockOperation", "/api/message/blockOperation")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .params("type", type)
                .params("userId", userId)
                .execute(new HttpApiCallBackConvert(callback, com.kalacheng.libbas.model.HttpNone_Ret.class));

    }


    /**
     * 获取系统通知未读数
     *
     * @param userId 拉黑用户id
     */
    public static void getBlockinfo(long userId, HttpApiCallBack<com.kalacheng.libuser.model.ApiUsersVideoBlack> callback) {
        HttpClient.getInstance().post("/api/message/getBlockinfo", "/api/message/getBlockinfo")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .params("userId", userId)
                .execute(new HttpApiCallBackConvert(callback, com.kalacheng.libuser.model.ApiUsersVideoBlack_Ret.class));

    }


    /**
     * 获取系统通知未读数
     */
    public static void getAppSystemNoRead(HttpApiCallBack<com.kalacheng.libuser.model.ApiNoRead> callback) {
        HttpClient.getInstance().post("/api/message/getAppSystemNoRead", "/api/message/getAppSystemNoRead")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .execute(new HttpApiCallBackConvert(callback, com.kalacheng.libuser.model.ApiNoRead_Ret.class));

    }


    /**
     * 消息-清除未读为已读
     *
     * @param objId 对象id(动态消息id/通知id,全清时传-1)
     * @param type  类型 0:全部 1:动态 2:点赞 3:通知 4:短视频评论 5:官方消息
     */
    public static void clearNoticeMsg(com.kalacheng.libuser.model_fun.ChatRoom_clearNoticeMsg _mdl, HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback) {
        HttpClient.getInstance().post("/api/message/clearNoticeMsg", "/api/message/clearNoticeMsg")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .params("objId", _mdl.objId)
                .params("type", _mdl.type)
                .execute(new HttpApiCallBackConvert(callback, com.kalacheng.libbas.model.HttpNone_Ret.class));

    }


    /**
     * 消息-清除未读为已读
     *
     * @param objId 对象id(动态消息id/通知id,全清时传-1)
     * @param type  类型 0:全部 1:动态 2:点赞 3:通知 4:短视频评论 5:官方消息
     */
    public static void clearNoticeMsg(int objId, int type, HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback) {
        HttpClient.getInstance().post("/api/message/clearNoticeMsg", "/api/message/clearNoticeMsg")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .params("objId", objId)
                .params("type", type)
                .execute(new HttpApiCallBackConvert(callback, com.kalacheng.libbas.model.HttpNone_Ret.class));

    }


    /**
     * 动态回复消息列表
     *
     * @param page     页数
     * @param pageSize 每页的条数
     */
    public static void videoCommentsList(com.kalacheng.libuser.model_fun.ChatRoom_videoCommentsList _mdl, HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiCommentsMsg> callback) {
        HttpClient.getInstance().post("/api/VideoComment/videoCommentsList", "/api/VideoComment/videoCommentsList")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .params("page", _mdl.page)
                .params("pageSize", _mdl.pageSize)
                .execute(new HttpApiCallBackPageArrConvert(callback, com.kalacheng.libuser.model.ApiCommentsMsg_RetPageArr.class));

    }


    /**
     * 动态回复消息列表
     *
     * @param page     页数
     * @param pageSize 每页的条数
     */
    public static void videoCommentsList(int page, int pageSize, HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiCommentsMsg> callback) {
        HttpClient.getInstance().post("/api/VideoComment/videoCommentsList", "/api/VideoComment/videoCommentsList")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .params("page", page)
                .params("pageSize", pageSize)
                .execute(new HttpApiCallBackPageArrConvert(callback, com.kalacheng.libuser.model.ApiCommentsMsg_RetPageArr.class));

    }


    /**
     * 1V1文字聊天数据展示
     */
    public static void getCommonWordsList(HttpApiCallBack<com.kalacheng.buschatroom.model.CommonTipsDTO> callback) {
        HttpClient.getInstance().post("/api/chatRoom/getCommonWordsList", "/api/chatRoom/getCommonWordsList")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .execute(new HttpApiCallBackConvert(callback, com.kalacheng.buschatroom.model.CommonTipsDTO_Ret.class));

    }


    /**
     * 1V1发消息
     *
     * @param content 发送内容
     */
    public static void keywordTransform(String content, HttpApiCallBack<com.kalacheng.libbas.model.SingleString> callback) {
        HttpClient.getInstance().post("/api/message/keywordTransform", "/api/message/keywordTransform")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .params("content", content)
                .execute(new HttpApiCallBackConvert(callback, com.kalacheng.libbas.model.SingleString_Ret.class));

    }


    /**
     * 消息-清除未读为已读
     *
     * @param noticeId 对象id(动态消息id/通知id,全清时传-1)
     */
    public static void delNoticeMsg(int noticeId, HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback) {
        HttpClient.getInstance().post("/api/message/delNoticeMsg", "/api/message/delNoticeMsg")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .params("noticeId", noticeId)
                .execute(new HttpApiCallBackConvert(callback, com.kalacheng.libbas.model.HttpNone_Ret.class));

    }


    /**
     * 短视频回复消息列表
     *
     * @param page     页数
     * @param pageSize 每页的条数
     */
    public static void shortVideoCommentsList(com.kalacheng.libuser.model_fun.ChatRoom_shortVideoCommentsList _mdl, HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiCommentsMsg> callback) {
        HttpClient.getInstance().post("/api/VideoComment/shortVideoCommentsList", "/api/VideoComment/shortVideoCommentsList")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .params("page", _mdl.page)
                .params("pageSize", _mdl.pageSize)
                .execute(new HttpApiCallBackPageArrConvert(callback, com.kalacheng.libuser.model.ApiCommentsMsg_RetPageArr.class));

    }


    /**
     * 短视频回复消息列表
     *
     * @param page     页数
     * @param pageSize 每页的条数
     */
    public static void shortVideoCommentsList(int page, int pageSize, HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiCommentsMsg> callback) {
        HttpClient.getInstance().post("/api/VideoComment/shortVideoCommentsList", "/api/VideoComment/shortVideoCommentsList")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .params("page", page)
                .params("pageSize", pageSize)
                .execute(new HttpApiCallBackPageArrConvert(callback, com.kalacheng.libuser.model.ApiCommentsMsg_RetPageArr.class));

    }


    /**
     * 1V1发消息,code1成功2余额不足3贵族才能发消息
     *
     * @param touid 对方用户id
     */
    public static void privateChat(long touid, HttpApiCallBack<com.kalacheng.libbas.model.SingleString> callback) {
        HttpClient.getInstance().post("/api/chatRoom/privateChat", "/api/chatRoom/privateChat")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .params("touid", touid)
                .execute(new HttpApiCallBackConvert(callback, com.kalacheng.libbas.model.SingleString_Ret.class));

    }


    /**
     * 开启关闭 消息/提示音
     *
     * @param btnValue 类型0开启1关闭
     * @param type     类型：1是否开启消息推送，2是否关闭消息提示音
     */
    public static void operateMessageButton(com.kalacheng.libuser.model_fun.ChatRoom_operateMessageButton _mdl, HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback) {
        HttpClient.getInstance().post("/api/message/operateMessageButton", "/api/message/operateMessageButton")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .params("btnValue", _mdl.btnValue)
                .params("type", _mdl.type)
                .execute(new HttpApiCallBackConvert(callback, com.kalacheng.libbas.model.HttpNone_Ret.class));

    }


    /**
     * 开启关闭 消息/提示音
     *
     * @param btnValue 类型0开启1关闭
     * @param type     类型：1是否开启消息推送，2是否关闭消息提示音
     */
    public static void operateMessageButton(int btnValue, int type, HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback) {
        HttpClient.getInstance().post("/api/message/operateMessageButton", "/api/message/operateMessageButton")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .params("btnValue", btnValue)
                .params("type", type)
                .execute(new HttpApiCallBackConvert(callback, com.kalacheng.libbas.model.HttpNone_Ret.class));

    }


    /**
     * 1V1发消息成功记录数据
     *
     * @param content     消息内容
     * @param feeId       付钱人用户id(发送方)
     * @param hostId      受益人用户id(接收方)
     * @param sendMsgType 发送的消息类型: 1:文字 2:图片 3:语音 4:视频通话 5:语音通话
     */
    public static void oooSendMsg(com.kalacheng.libuser.model_fun.ChatRoom_oooSendMsg _mdl, HttpApiCallBack<com.kalacheng.libbas.model.SingleString> callback) {
        HttpClient.getInstance().post("/api/chatRoom/oooSendMsg", "/api/chatRoom/oooSendMsg")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .params("content", _mdl.content)
                .params("feeId", _mdl.feeId)
                .params("hostId", _mdl.hostId)
                .params("sendMsgType", _mdl.sendMsgType)
                .execute(new HttpApiCallBackConvert(callback, com.kalacheng.libbas.model.SingleString_Ret.class));

    }


    /**
     * 1V1发消息成功记录数据
     *
     * @param content     消息内容
     * @param feeId       付钱人用户id(发送方)
     * @param hostId      受益人用户id(接收方)
     * @param sendMsgType 发送的消息类型: 1:文字 2:图片 3:语音  4:视频通话 5:语音通话 6:礼物
     */
    public static void oooSendMsg(String content, long feeId, long hostId, int sendMsgType, HttpApiCallBack<com.kalacheng.libbas.model.SingleString> callback) {
        HttpClient.getInstance().post("/api/chatRoom/oooSendMsg", "/api/chatRoom/oooSendMsg")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .params("content", content)
                .params("feeId", feeId)
                .params("hostId", hostId)
                .params("sendMsgType", sendMsgType)
                .execute(new HttpApiCallBackConvert(callback, com.kalacheng.libbas.model.SingleString_Ret.class));

    }


    /**
     * 1V1文字聊天数据展示
     *
     * @param userId 对方用户id
     */
    public static void oooSendMsgText(long userId, HttpApiCallBack<com.kalacheng.libuser.model.OOOLiveTextChatData> callback) {
        HttpClient.getInstance().post("/api/chatRoom/oooSendMsgText", "/api/chatRoom/oooSendMsgText")
                .params("_uid_", HttpClient.getUid())
                .params("_token_", HttpClient.getToken())
                .params("_OS_", HttpClient.getOSType())
                .params("_OSV_", HttpClient.getOSVersion())
                .params("_OSInfo_", HttpClient.getOSInfo())
                .params("userId", userId)
                .execute(new HttpApiCallBackConvert(callback, com.kalacheng.libuser.model.OOOLiveTextChatData_Ret.class));

    }


}
