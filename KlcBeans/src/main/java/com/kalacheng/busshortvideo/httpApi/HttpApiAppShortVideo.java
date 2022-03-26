package com.kalacheng.busshortvideo.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 短视频API
 */
public class HttpApiAppShortVideo
{




/**
 * 查看单个短视频
 * @param commentId 评论id(通知列表的commentId)没有则传-1
 * @param shortVideoId 短视频id
 * @param type 来源 -1:查看详情 1:消息评论 2:消息点赞
 */
public static void getShortVideoInfoList(com.kalacheng.busshortvideo.model_fun.AppShortVideo_getShortVideoInfoList _mdl,HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiShortVideoDto> callback)
{
HttpClient.getInstance().post("/api/shortvideo/getShortVideoInfoList","/api/shortvideo/getShortVideoInfoList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("commentId", _mdl.commentId)
.params("shortVideoId", _mdl.shortVideoId)
.params("type", _mdl.type)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.ApiShortVideoDto_RetPageArr.class));

}



/**
 * 查看单个短视频
 * @param commentId 评论id(通知列表的commentId)没有则传-1
 * @param shortVideoId 短视频id
 * @param type 来源 -1:查看详情 1:消息评论 2:消息点赞
 */
public static void getShortVideoInfoList(int commentId,long shortVideoId,int type,HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiShortVideoDto> callback)
{
HttpClient.getInstance().post("/api/shortvideo/getShortVideoInfoList","/api/shortvideo/getShortVideoInfoList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("commentId", commentId)
.params("shortVideoId", shortVideoId)
.params("type", type)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.ApiShortVideoDto_RetPageArr.class));

}





/**
 * 购买短视频code 1:可以观看,2:观影次数不足,3.余额不足
 * @param shortVideoId 短视频id
 * @param type 类型 -1:观看普通视频 0:观影次数购买 1:金币购买
 */
public static void useReadShortVideoNumber(com.kalacheng.busshortvideo.model_fun.AppShortVideo_useReadShortVideoNumber _mdl,HttpApiCallBack<com.kalacheng.libuser.model.ApiBaseEntity> callback)
{
HttpClient.getInstance().post("/api/shortvideo/useReadShortVideoNumber","/api/shortvideo/useReadShortVideoNumber")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("shortVideoId", _mdl.shortVideoId)
.params("type", _mdl.type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiBaseEntity_Ret.class));

}



/**
 * 购买短视频code 1:可以观看,2:观影次数不足,3.余额不足
 * @param shortVideoId 短视频id
 * @param type 类型 -1:观看普通视频 0:观影次数购买 1:金币购买
 */
public static void useReadShortVideoNumber(long shortVideoId,int type,HttpApiCallBack<com.kalacheng.libuser.model.ApiBaseEntity> callback)
{
HttpClient.getInstance().post("/api/shortvideo/useReadShortVideoNumber","/api/shortvideo/useReadShortVideoNumber")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("shortVideoId", shortVideoId)
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiBaseEntity_Ret.class));

}





/**
 * 删除短视频
 * @param shortVideoId 短视频id
 */
public static void delShortVideo(long shortVideoId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/shortvideo/delShortVideo","/api/shortvideo/delShortVideo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("shortVideoId", shortVideoId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 短视频分类列表
 */
public static void getShortVideoClassifyList(HttpApiCallBackArr<com.kalacheng.libuser.model.AppHotSort> callback)
{
HttpClient.getInstance().post("/api/shortvideo/getShortVideoClassifyList","/api/shortvideo/getShortVideoClassifyList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppHotSort_RetArr.class));

}





/**
 * 短视频评论列表
 * @param page 页数
 * @param pageSize 每页的条数
 * @param shortVideoId 短视频id
 */
public static void getShortVideoCommentBasicInfo(com.kalacheng.busshortvideo.model_fun.AppShortVideo_getShortVideoCommentBasicInfo _mdl,HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiUsersVideoComments> callback)
{
HttpClient.getInstance().post("/api/shortvideo/getShortVideoCommentBasicInfo","/api/shortvideo/getShortVideoCommentBasicInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.params("shortVideoId", _mdl.shortVideoId)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.ApiUsersVideoComments_RetPageArr.class));

}



/**
 * 短视频评论列表
 * @param page 页数
 * @param pageSize 每页的条数
 * @param shortVideoId 短视频id
 */
public static void getShortVideoCommentBasicInfo(int page,int pageSize,long shortVideoId,HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiUsersVideoComments> callback)
{
HttpClient.getInstance().post("/api/shortvideo/getShortVideoCommentBasicInfo","/api/shortvideo/getShortVideoCommentBasicInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", page)
.params("pageSize", pageSize)
.params("shortVideoId", shortVideoId)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.ApiUsersVideoComments_RetPageArr.class));

}





/**
 * 短视频点赞/取消
 * @param shortVideoId 短视频id
 */
public static void shortVideoZan(long shortVideoId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/shortvideo/shortVideoZan","/api/shortvideo/shortVideoZan")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("shortVideoId", shortVideoId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 某个人/自己发的作品、点赞的作品、购买的作品
 * @param pageSize 每页的条数
 * @param toUid 对方用户ID（传-1查询自己的）
 */
public static void getUserShortVideoList(com.kalacheng.busshortvideo.model_fun.AppShortVideo_getUserShortVideoList _mdl,HttpApiCallBack<com.kalacheng.libuser.model.ApiMyShortVideo> callback)
{
HttpClient.getInstance().post("/api/shortvideo/getUserShortVideoList","/api/shortvideo/getUserShortVideoList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageSize", _mdl.pageSize)
.params("toUid", _mdl.toUid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiMyShortVideo_Ret.class));

}



/**
 * 某个人/自己发的作品、点赞的作品、购买的作品
 * @param pageSize 每页的条数
 * @param toUid 对方用户ID（传-1查询自己的）
 */
public static void getUserShortVideoList(int pageSize,long toUid,HttpApiCallBack<com.kalacheng.libuser.model.ApiMyShortVideo> callback)
{
HttpClient.getInstance().post("/api/shortvideo/getUserShortVideoList","/api/shortvideo/getUserShortVideoList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageSize", pageSize)
.params("toUid", toUid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiMyShortVideo_Ret.class));

}





/**
 * (分页)某个人/自己发的作品、点赞的作品、购买的作品
 * @param page 页数
 * @param pageSize 每页的条数
 * @param toUid 对方用户ID（传-1查询自己的）
 * @param type 类型 1:我的作品 2:我喜欢的 3:我购买的
 */
public static void getUserShortVideoPage(com.kalacheng.busshortvideo.model_fun.AppShortVideo_getUserShortVideoPage _mdl,HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiShortVideoDto> callback)
{
HttpClient.getInstance().post("/api/shortvideo/getUserShortVideoPage","/api/shortvideo/getUserShortVideoPage")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.params("toUid", _mdl.toUid)
.params("type", _mdl.type)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.ApiShortVideoDto_RetPageArr.class));

}



/**
 * (分页)某个人/自己发的作品、点赞的作品、购买的作品
 * @param page 页数
 * @param pageSize 每页的条数
 * @param toUid 对方用户ID（传-1查询自己的）
 * @param type 类型 1:我的作品 2:我喜欢的 3:我购买的
 */
public static void getUserShortVideoPage(int page,int pageSize,long toUid,int type,HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiShortVideoDto> callback)
{
HttpClient.getInstance().post("/api/shortvideo/getUserShortVideoPage","/api/shortvideo/getUserShortVideoPage")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", page)
.params("pageSize", pageSize)
.params("toUid", toUid)
.params("type", type)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.ApiShortVideoDto_RetPageArr.class));

}





/**
 * 删除短视频评论
 * @param commentId 评论id
 */
public static void delShortVideoComment(long commentId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/shortvideo/delShortVideoComment","/api/shortvideo/delShortVideoComment")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("commentId", commentId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 短视频首页列表、根据分类查询短视频
 * @param adsId 最后一个广告id(没有传-1)
 * @param classifyId 分类id(根据分类查询短视频默认传-1)
 * @param page 页数
 * @param pageSize 每页的条数
 * @param sort 排序(-1:默认 1:最多观看 2:最多评论 3:最多点赞 4:最多付费 5:最多观看)
 * @param type 列表类型 0:推荐 1:关注
 */
public static void getShortVideoList(com.kalacheng.busshortvideo.model_fun.AppShortVideo_getShortVideoList _mdl,HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiShortVideoDto> callback)
{
HttpClient.getInstance().post("/api/shortvideo/getShortVideoList","/api/shortvideo/getShortVideoList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("adsId", _mdl.adsId)
.params("classifyId", _mdl.classifyId)
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.params("sort", _mdl.sort)
.params("type", _mdl.type)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.ApiShortVideoDto_RetPageArr.class));

}



/**
 * 短视频首页列表、根据分类查询短视频
 * @param adsId 最后一个广告id(没有传-1)
 * @param classifyId 分类id(根据分类查询短视频默认传-1)
 * @param page 页数
 * @param pageSize 每页的条数
 * @param sort 排序(-1:默认 1:最多观看 2:最多评论 3:最多点赞 4:最多付费 5:最多观看)
 * @param type 列表类型 0:推荐 1:关注
 */
public static void getShortVideoList(long adsId,long classifyId,int page,int pageSize,int sort,int type,HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiShortVideoDto> callback)
{
HttpClient.getInstance().post("/api/shortvideo/getShortVideoList","/api/shortvideo/getShortVideoList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("adsId", adsId)
.params("classifyId", classifyId)
.params("page", page)
.params("pageSize", pageSize)
.params("sort", sort)
.params("type", type)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.ApiShortVideoDto_RetPageArr.class));

}





/**
 * 发布短视频code为3时请先认证
 * @param address 详细地址
 * @param city 城市
 * @param classifyId 分类ID
 * @param coin 默认0元即可， 填写金币金额
 * @param content 文字内容
 * @param height 封面图高
 * @param href 视频地址(短视频时才传入)
 * @param images 图片（逗号拼接）
 * @param isPrivate 是否私密 0：正常 1：私密
 * @param lat 纬度
 * @param lng 经度
 * @param productId 商品id
 * @param thumb 封面图(如果是视频就取第一帧,如果是图片取第一个图片)
 * @param type 类型 1:视频 2:图片
 * @param videoTime 视频时长（单位秒）
 * @param width 封面图宽
 */
public static void setShortVideo(com.kalacheng.busshortvideo.model_fun.AppShortVideo_setShortVideo _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/shortvideo/setShortVideo","/api/shortvideo/setShortVideo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("address", _mdl.address)
.params("city", _mdl.city)
.params("classifyId", _mdl.classifyId)
.params("coin", _mdl.coin)
.params("content", _mdl.content)
.params("height", _mdl.height)
.params("href", _mdl.href)
.params("images", _mdl.images)
.params("isPrivate", _mdl.isPrivate)
.params("lat", _mdl.lat)
.params("lng", _mdl.lng)
.params("productId", _mdl.productId)
.params("thumb", _mdl.thumb)
.params("type", _mdl.type)
.params("videoTime", _mdl.videoTime)
.params("width", _mdl.width)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 发布短视频code为3时请先认证
 * @param address 详细地址
 * @param city 城市
 * @param classifyId 分类ID
 * @param coin 默认0元即可， 填写金币金额
 * @param content 文字内容
 * @param height 封面图高
 * @param href 视频地址(短视频时才传入)
 * @param images 图片（逗号拼接）
 * @param isPrivate 是否私密 0：正常 1：私密
 * @param lat 纬度
 * @param lng 经度
 * @param productId 商品id
 * @param thumb 封面图(如果是视频就取第一帧,如果是图片取第一个图片)
 * @param type 类型 1:视频 2:图片
 * @param videoTime 视频时长（单位秒）
 * @param width 封面图宽
 */
public static void setShortVideo(String address,String city,String classifyId,double coin,String content,int height,String href,String images,int isPrivate,double lat,double lng,long productId,String thumb,int type,int videoTime,int width,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/shortvideo/setShortVideo","/api/shortvideo/setShortVideo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("address", address)
.params("city", city)
.params("classifyId", classifyId)
.params("coin", coin)
.params("content", content)
.params("height", height)
.params("href", href)
.params("images", images)
.params("isPrivate", isPrivate)
.params("lat", lat)
.params("lng", lng)
.params("productId", productId)
.params("thumb", thumb)
.params("type", type)
.params("videoTime", videoTime)
.params("width", width)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 查看免费观影私密视频次数
 */
public static void isReadShortVideoNumber(HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/shortvideo/isReadShortVideoNumber","/api/shortvideo/isReadShortVideoNumber")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 短视频评论/回复
 * @param commentType 评论类型 1:评论 2:回复
 * @param content 评论内容
 * @param objId 视频/评论id
 */
public static void shortVideoComment(com.kalacheng.busshortvideo.model_fun.AppShortVideo_shortVideoComment _mdl,HttpApiCallBack<com.kalacheng.libuser.model.ApiUsersVideoComments> callback)
{
HttpClient.getInstance().post("/api/shortvideo/shortVideoComment","/api/shortvideo/shortVideoComment")
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
 * 短视频评论/回复
 * @param commentType 评论类型 1:评论 2:回复
 * @param content 评论内容
 * @param objId 视频/评论id
 */
public static void shortVideoComment(int commentType,String content,long objId,HttpApiCallBack<com.kalacheng.libuser.model.ApiUsersVideoComments> callback)
{
HttpClient.getInstance().post("/api/shortvideo/shortVideoComment","/api/shortvideo/shortVideoComment")
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
 * 看点页面接口
 * @param page 页数
 * @param pageSize 每页的条数
 */
public static void getHighlights(com.kalacheng.busshortvideo.model_fun.AppShortVideo_getHighlights _mdl,HttpApiCallBack<com.kalacheng.libuser.model.ApiShortVideo> callback)
{
HttpClient.getInstance().post("/api/shortvideo/getHighlights","/api/shortvideo/getHighlights")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiShortVideo_Ret.class));

}



/**
 * 看点页面接口
 * @param page 页数
 * @param pageSize 每页的条数
 */
public static void getHighlights(int page,int pageSize,HttpApiCallBack<com.kalacheng.libuser.model.ApiShortVideo> callback)
{
HttpClient.getInstance().post("/api/shortvideo/getHighlights","/api/shortvideo/getHighlights")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", page)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiShortVideo_Ret.class));

}


}
