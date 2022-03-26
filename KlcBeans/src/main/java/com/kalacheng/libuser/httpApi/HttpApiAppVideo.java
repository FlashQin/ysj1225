package com.kalacheng.libuser.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 动态API
 */
public class HttpApiAppVideo
{




/**
 * 动态举报
 * @param classifyId 举报类型id
 * @param content 举报内容
 * @param videoId 动态id
 */
public static void videoReport(com.kalacheng.libuser.model_fun.AppVideo_videoReport _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/video/videoReport","/api/video/videoReport")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("classifyId", _mdl.classifyId)
.params("content", _mdl.content)
.params("videoId", _mdl.videoId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 动态举报
 * @param classifyId 举报类型id
 * @param content 举报内容
 * @param videoId 动态id
 */
public static void videoReport(long classifyId,String content,long videoId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/video/videoReport","/api/video/videoReport")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("classifyId", classifyId)
.params("content", content)
.params("videoId", videoId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 查询评论个数
 * @param videoId 动态id
 */
public static void getCommentCount(long videoId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/video/getCommentCount","/api/video/getCommentCount")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("videoId", videoId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 动态话题列表
 * @param page 页数
 * @param pageSize 每页的条数
 */
public static void getTopicList(com.kalacheng.libuser.model_fun.AppVideo_getTopicList _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.AppVideoTopic> callback)
{
HttpClient.getInstance().post("/api/video/getTopicList","/api/video/getTopicList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppVideoTopic_RetArr.class));

}



/**
 * 动态话题列表
 * @param page 页数
 * @param pageSize 每页的条数
 */
public static void getTopicList(int page,int pageSize,HttpApiCallBackArr<com.kalacheng.libuser.model.AppVideoTopic> callback)
{
HttpClient.getInstance().post("/api/video/getTopicList","/api/video/getTopicList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", page)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppVideoTopic_RetArr.class));

}





/**
 * 发布评论/回复
 * @param commentType 评论类型1评论2回复
 * @param content 评论内容
 * @param objId 视频/评论id
 */
public static void addComment(com.kalacheng.libuser.model_fun.AppVideo_addComment _mdl,HttpApiCallBack<com.kalacheng.libuser.model.ApiUsersVideoComments> callback)
{
HttpClient.getInstance().post("/api/video/addComment","/api/video/addComment")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("commentType", _mdl.commentType)
.params("content", _mdl.content)
.params("objId", _mdl.objId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiUsersVideoComments_Ret.class));

}



/**
 * 发布评论/回复
 * @param commentType 评论类型1评论2回复
 * @param content 评论内容
 * @param objId 视频/评论id
 */
public static void addComment(int commentType,String content,long objId,HttpApiCallBack<com.kalacheng.libuser.model.ApiUsersVideoComments> callback)
{
HttpClient.getInstance().post("/api/video/addComment","/api/video/addComment")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("commentType", commentType)
.params("content", content)
.params("objId", objId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiUsersVideoComments_Ret.class));

}





/**
 * 发布短视频（动态）code为3时请先认证
 * @param address 详细地址
 * @param channelId 频道id
 * @param city 城市
 * @param coin 默认0元即可， 填写金币金额
 * @param height 视频高
 * @param href 视频地址(视频类型时才传入)
 * @param images 动态图片（逗号拼接）
 * @param isPrivate 是否私密 0：正常 1：私密
 * @param lat 纬度
 * @param lng 经度
 * @param musicId 音乐id
 * @param sourceFrom 资源来源 1:首页发布动态 2:其他地方发布出来的动态
 * @param thumb 动态封面图
 * @param title 动态标题
 * @param topicId 动态话题ID
 * @param type 类型 0:只有文字 1:视频动态 2:图片动态
 * @param videoTime 视频时长
 * @param width 视频宽
 */
public static void setVideo(com.kalacheng.libuser.model_fun.AppVideo_setVideo _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/video/setVideo","/api/video/setVideo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("address", _mdl.address)
.params("channelId", _mdl.channelId)
.params("city", _mdl.city)
.params("coin", _mdl.coin)
.params("height", _mdl.height)
.params("href", _mdl.href)
.params("images", _mdl.images)
.params("isPrivate", _mdl.isPrivate)
.params("lat", _mdl.lat)
.params("lng", _mdl.lng)
.params("musicId", _mdl.musicId)
.params("sourceFrom", _mdl.sourceFrom)
.params("thumb", _mdl.thumb)
.params("title", _mdl.title)
.params("topicId", _mdl.topicId)
.params("type", _mdl.type)
.params("videoTime", _mdl.videoTime)
.params("width", _mdl.width)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 发布短视频（动态）code为3时请先认证
 * @param address 详细地址
 * @param channelId 频道id
 * @param city 城市
 * @param coin 默认0元即可， 填写金币金额
 * @param height 视频高
 * @param href 视频地址(视频类型时才传入)
 * @param images 动态图片（逗号拼接）
 * @param isPrivate 是否私密 0：正常 1：私密
 * @param lat 纬度
 * @param lng 经度
 * @param musicId 音乐id
 * @param sourceFrom 资源来源 1:首页发布动态 2:其他地方发布出来的动态
 * @param thumb 动态封面图
 * @param title 动态标题
 * @param topicId 动态话题ID
 * @param type 类型 0:只有文字 1:视频动态 2:图片动态
 * @param videoTime 视频时长
 * @param width 视频宽
 */
public static void setVideo(String address,long channelId,String city,double coin,int height,String href,String images,int isPrivate,double lat,double lng,long musicId,int sourceFrom,String thumb,String title,long topicId,int type,String videoTime,int width,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/video/setVideo","/api/video/setVideo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("address", address)
.params("channelId", channelId)
.params("city", city)
.params("coin", coin)
.params("height", height)
.params("href", href)
.params("images", images)
.params("isPrivate", isPrivate)
.params("lat", lat)
.params("lng", lng)
.params("musicId", musicId)
.params("sourceFrom", sourceFrom)
.params("thumb", thumb)
.params("title", title)
.params("topicId", topicId)
.params("type", type)
.params("videoTime", videoTime)
.params("width", width)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 动态详情
 * @param commentId 评论id(通知列表的commentId)没有则传-1
 * @param type 来源 -1:查看详情 1:消息评论 2:消息点赞
 * @param videoId 视频ID
 */
public static void getUserVideoInfo(com.kalacheng.libuser.model_fun.AppVideo_getUserVideoInfo _mdl,HttpApiCallBack<com.kalacheng.libuser.model.ApiUserVideo> callback)
{
HttpClient.getInstance().post("/api/video/getUserVideoInfo","/api/video/getUserVideoInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("commentId", _mdl.commentId)
.params("type", _mdl.type)
.params("videoId", _mdl.videoId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiUserVideo_Ret.class));

}



/**
 * 动态详情
 * @param commentId 评论id(通知列表的commentId)没有则传-1
 * @param type 来源 -1:查看详情 1:消息评论 2:消息点赞
 * @param videoId 视频ID
 */
public static void getUserVideoInfo(int commentId,int type,long videoId,HttpApiCallBack<com.kalacheng.libuser.model.ApiUserVideo> callback)
{
HttpClient.getInstance().post("/api/video/getUserVideoInfo","/api/video/getUserVideoInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("commentId", commentId)
.params("type", type)
.params("videoId", videoId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiUserVideo_Ret.class));

}





/**
 * 删除评论/回复
 * @param commentId 评论id
 */
public static void delComment(long commentId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/video/delComment","/api/video/delComment")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("commentId", commentId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 短视频（动态）列表
 * @param hotId 分类id
 * @param page 页数
 * @param pageSize 每页的条数
 * @param touid 要查看的用户的ID
 * @param type 类型 0:全部 1:推荐 2:关注
 */
public static void getVideoList(com.kalacheng.libuser.model_fun.AppVideo_getVideoList _mdl,HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiUserVideo> callback)
{
HttpClient.getInstance().post("/api/video/getVideoList","/api/video/getVideoList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("hotId", _mdl.hotId)
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.params("touid", _mdl.touid)
.params("type", _mdl.type)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.ApiUserVideo_RetPageArr.class));

}



/**
 * 短视频（动态）列表
 * @param hotId 分类id
 * @param page 页数
 * @param pageSize 每页的条数
 * @param touid 要查看的用户的ID
 * @param type 类型 0:全部 1:推荐 2:关注
 */
public static void getVideoList(long hotId,int page,int pageSize,long touid,int type,HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiUserVideo> callback)
{
HttpClient.getInstance().post("/api/video/getVideoList","/api/video/getVideoList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("hotId", hotId)
.params("page", page)
.params("pageSize", pageSize)
.params("touid", touid)
.params("type", type)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.ApiUserVideo_RetPageArr.class));

}





/**
 * 发现页接口
 */
public static void discoverPage(HttpApiCallBack<com.kalacheng.libuser.model.ApiDiscover> callback)
{
HttpClient.getInstance().post("/api/video/discoverPage","/api/video/discoverPage")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiDiscover_Ret.class));

}





/**
 * 删除动态
 * @param videoId 动态id
 */
public static void videoDel(long videoId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/video/videoDel","/api/video/videoDel")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("videoId", videoId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 动态点赞/取消
 * @param videoId 动态id
 */
public static void videoZan(long videoId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/video/videoZan","/api/video/videoZan")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("videoId", videoId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 音乐列表
 * @param keyWord 关键字
 * @param page 页数
 * @param pageSize 每页的条数
 */
public static void musicList(com.kalacheng.libuser.model_fun.AppVideo_musicList _mdl,HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiMusic> callback)
{
HttpClient.getInstance().post("/api/video/musicList","/api/video/musicList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("keyWord", _mdl.keyWord)
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.ApiMusic_RetPageArr.class));

}



/**
 * 音乐列表
 * @param keyWord 关键字
 * @param page 页数
 * @param pageSize 每页的条数
 */
public static void musicList(String keyWord,int page,int pageSize,HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiMusic> callback)
{
HttpClient.getInstance().post("/api/video/musicList","/api/video/musicList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("keyWord", keyWord)
.params("page", page)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.ApiMusic_RetPageArr.class));

}





/**
 * 获取音乐地址
 * @param id 音乐id
 */
public static void musicInfo(String id,HttpApiCallBack<com.kalacheng.libuser.model.ApiMusic> callback)
{
HttpClient.getInstance().post("/api/video/musicInfo","/api/video/musicInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("id", id)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiMusic_Ret.class));

}





/**
 * 动态举报分类
 */
public static void videoReportClassify(HttpApiCallBackArr<com.kalacheng.libuser.model.AppUsersVideoReportClassify> callback)
{
HttpClient.getInstance().post("/api/video/videoReportClassify","/api/video/videoReportClassify")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppUsersVideoReportClassify_RetArr.class));

}





/**
 * 动态评论列表
 * @param page 页数
 * @param pageSize 每页的条数
 * @param videoId 动态id
 */
public static void getCommentBasicInfo(com.kalacheng.libuser.model_fun.AppVideo_getCommentBasicInfo _mdl,HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiUsersVideoComments> callback)
{
HttpClient.getInstance().post("/api/video/getCommentBasicInfo","/api/video/getCommentBasicInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.params("videoId", _mdl.videoId)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.ApiUsersVideoComments_RetPageArr.class));

}



/**
 * 动态评论列表
 * @param page 页数
 * @param pageSize 每页的条数
 * @param videoId 动态id
 */
public static void getCommentBasicInfo(int page,int pageSize,long videoId,HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiUsersVideoComments> callback)
{
HttpClient.getInstance().post("/api/video/getCommentBasicInfo","/api/video/getCommentBasicInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", page)
.params("pageSize", pageSize)
.params("videoId", videoId)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.ApiUsersVideoComments_RetPageArr.class));

}


}
