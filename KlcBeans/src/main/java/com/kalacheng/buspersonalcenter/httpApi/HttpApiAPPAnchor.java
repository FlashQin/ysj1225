package com.kalacheng.buspersonalcenter.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 主播接口API
 */
public class HttpApiAPPAnchor
{




/**
 * 靓号列表
 * @param pageIndex pageIndex
 * @param pageSize pageSize
 */
public static void getNumberList(com.kalacheng.buspersonalcenter.model_fun.APPAnchor_getNumberList _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.AppLiang> callback)
{
HttpClient.getInstance().post("/api/anchor/getNumberList","/api/anchor/getNumberList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppLiang_RetArr.class));

}



/**
 * 靓号列表
 * @param pageIndex pageIndex
 * @param pageSize pageSize
 */
public static void getNumberList(int pageIndex,int pageSize,HttpApiCallBackArr<com.kalacheng.libuser.model.AppLiang> callback)
{
HttpClient.getInstance().post("/api/anchor/getNumberList","/api/anchor/getNumberList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppLiang_RetArr.class));

}





/**
 * 我的粉丝团基本信息
 * @param anchorId 主播ID
 */
public static void fansTeamInfo(long anchorId,HttpApiCallBack<com.kalacheng.libuser.model.FansInfoDto> callback)
{
HttpClient.getInstance().post("/api/anchor/fansTeamInfo","/api/anchor/fansTeamInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.FansInfoDto_Ret.class));

}





/**
 * 加入粉丝团
 * @param anchorId 主播ID
 */
public static void joinFansTeam(long anchorId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/anchor/joinFansTeam","/api/anchor/joinFansTeam")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 主播认证第一步
 * @param cerNo 身份证号码
 * @param extraInfo 附加信息
 * @param mobile 手机号码
 * @param qq qq号
 * @param realName 真实姓名
 * @param wechat 微信号
 */
public static void authFirst(com.kalacheng.buspersonalcenter.model_fun.APPAnchor_authFirst _mdl,HttpApiCallBack<com.kalacheng.libuser.model.AppUsersAuth> callback)
{
HttpClient.getInstance().post("/api/anchor/authFirst","/api/anchor/authFirst")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("cerNo", _mdl.cerNo)
.params("extraInfo", _mdl.extraInfo)
.params("mobile", _mdl.mobile)
.params("qq", _mdl.qq)
.params("realName", _mdl.realName)
.params("wechat", _mdl.wechat)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppUsersAuth_Ret.class));

}



/**
 * 主播认证第一步
 * @param cerNo 身份证号码
 * @param extraInfo 附加信息
 * @param mobile 手机号码
 * @param qq qq号
 * @param realName 真实姓名
 * @param wechat 微信号
 */
public static void authFirst(String cerNo,String extraInfo,String mobile,String qq,String realName,String wechat,HttpApiCallBack<com.kalacheng.libuser.model.AppUsersAuth> callback)
{
HttpClient.getInstance().post("/api/anchor/authFirst","/api/anchor/authFirst")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("cerNo", cerNo)
.params("extraInfo", extraInfo)
.params("mobile", mobile)
.params("qq", qq)
.params("realName", realName)
.params("wechat", wechat)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppUsersAuth_Ret.class));

}





/**
 * 守护列表
 * @param anchorId 主播id
 * @param page 当前页
 * @param pageSize 每页的条数
 */
public static void guardList(com.kalacheng.buspersonalcenter.model_fun.APPAnchor_guardList _mdl,HttpApiCallBack<com.kalacheng.libuser.model.GuardListEntity> callback)
{
HttpClient.getInstance().post("/api/anchor/guardList","/api/anchor/guardList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.GuardListEntity_Ret.class));

}



/**
 * 守护列表
 * @param anchorId 主播id
 * @param page 当前页
 * @param pageSize 每页的条数
 */
public static void guardList(long anchorId,int page,int pageSize,HttpApiCallBack<com.kalacheng.libuser.model.GuardListEntity> callback)
{
HttpClient.getInstance().post("/api/anchor/guardList","/api/anchor/guardList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("page", page)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.GuardListEntity_Ret.class));

}





/**
 * 设置粉丝团信息
 * @param coin 入团费用
 * @param name 粉丝团昵称
 */
public static void setFansTeamInfo(com.kalacheng.buspersonalcenter.model_fun.APPAnchor_setFansTeamInfo _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/anchor/setFansTeamInfo","/api/anchor/setFansTeamInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("coin", _mdl.coin)
.params("name", _mdl.name)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 设置粉丝团信息
 * @param coin 入团费用
 * @param name 粉丝团昵称
 */
public static void setFansTeamInfo(double coin,String name,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/anchor/setFansTeamInfo","/api/anchor/setFansTeamInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("coin", coin)
.params("name", name)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 粉丝团直播间信息
 * @param anchorId 主播ID
 */
public static void liveFansTeamInfo(long anchorId,HttpApiCallBack<com.kalacheng.libuser.model.FansInfoDto> callback)
{
HttpClient.getInstance().post("/api/anchor/liveFansTeamInfo","/api/anchor/liveFansTeamInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.FansInfoDto_Ret.class));

}





/**
 * 查询在线用户和在线主播列表
 * @param city 城市筛选 没有指定城市就传空字符串
 * @param lat 纬度
 * @param lng 经度
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param sex 性别 0:未设置 1:男 2:女 (男朋友新增 3(0) 4(0.5) 5(...)
 * @param status 状态 -1:全部 1:直播中 2:房间中 3:在线 4:离线
 */
public static void getLineUser(com.kalacheng.buspersonalcenter.model_fun.APPAnchor_getLineUser _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiUsersLine> callback)
{
HttpClient.getInstance().post("/api/anchor/getLineUser","/api/anchor/getLineUser")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("city", _mdl.city)
.params("lat", _mdl.lat)
.params("lng", _mdl.lng)
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.params("sex", _mdl.sex)
.params("status", _mdl.status)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiUsersLine_RetArr.class));

}



/**
 * 查询在线用户和在线主播列表
 * @param city 城市筛选 没有指定城市就传空字符串
 * @param lat 纬度
 * @param lng 经度
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param sex 性别 0:未设置 1:男 2:女 (男朋友新增 3(0) 4(0.5) 5(...)
 * @param status 状态 -1:全部 1:直播中 2:房间中 3:在线 4:离线
 */
public static void getLineUser(String city,double lat,double lng,int pageIndex,int pageSize,int sex,int status,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiUsersLine> callback)
{
HttpClient.getInstance().post("/api/anchor/getLineUser","/api/anchor/getLineUser")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("city", city)
.params("lat", lat)
.params("lng", lng)
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.params("sex", sex)
.params("status", status)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiUsersLine_RetArr.class));

}





/**
 * 粉丝团排行
 * @param anchorId 主播ID
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 */
public static void getFansTeamRank(com.kalacheng.buspersonalcenter.model_fun.APPAnchor_getFansTeamRank _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.RanksDto> callback)
{
HttpClient.getInstance().post("/api/anchor/getFansTeamRank","/api/anchor/getFansTeamRank")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.RanksDto_RetArr.class));

}



/**
 * 粉丝团排行
 * @param anchorId 主播ID
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 */
public static void getFansTeamRank(long anchorId,int pageIndex,int pageSize,HttpApiCallBackArr<com.kalacheng.libuser.model.RanksDto> callback)
{
HttpClient.getInstance().post("/api/anchor/getFansTeamRank","/api/anchor/getFansTeamRank")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.RanksDto_RetArr.class));

}





/**
 * 赠送靓号
 * @param anchorId 主播id
 * @param numId 靓号Id
 */
public static void purchaseNumber(com.kalacheng.buspersonalcenter.model_fun.APPAnchor_purchaseNumber _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/anchor/purchaseNumber","/api/anchor/purchaseNumber")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("numId", _mdl.numId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 赠送靓号
 * @param anchorId 主播id
 * @param numId 靓号Id
 */
public static void purchaseNumber(long anchorId,long numId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/anchor/purchaseNumber","/api/anchor/purchaseNumber")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("numId", numId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 主播认证第三步
 * @param videoUrl 短视频地址
 */
public static void authThird(String videoUrl,HttpApiCallBack<com.kalacheng.libuser.model.AppUsersAuth> callback)
{
HttpClient.getInstance().post("/api/anchor/authThird","/api/anchor/authThird")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("videoUrl", videoUrl)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppUsersAuth_Ret.class));

}





/**
 * 守护类目列表
 * @param anchorId 主播id
 */
public static void getGuardList(long anchorId,HttpApiCallBack<com.kalacheng.libuser.model.ApiGuardEntity> callback)
{
HttpClient.getInstance().post("/api/anchor/getGuardList","/api/anchor/getGuardList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiGuardEntity_Ret.class));

}





/**
 * 购买守护操作
 * @param anchorId 主播id
 * @param tid 守护规则id
 */
public static void guardListBuy(com.kalacheng.buspersonalcenter.model_fun.APPAnchor_guardListBuy _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/anchor/guardListBuy","/api/anchor/guardListBuy")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("tid", _mdl.tid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 购买守护操作
 * @param anchorId 主播id
 * @param tid 守护规则id
 */
public static void guardListBuy(long anchorId,long tid,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/anchor/guardListBuy","/api/anchor/guardListBuy")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("tid", tid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 主播认证第二步
 * @param backView 身份证反面(同上)
 * @param frontView 身份证正面(通过轩嗵云上传后返回的图片地址)
 * @param handsetView 手持身份证(同上)
 */
public static void authSecond(com.kalacheng.buspersonalcenter.model_fun.APPAnchor_authSecond _mdl,HttpApiCallBack<com.kalacheng.libuser.model.AppUsersAuth> callback)
{
HttpClient.getInstance().post("/api/anchor/authSecond","/api/anchor/authSecond")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("backView", _mdl.backView)
.params("frontView", _mdl.frontView)
.params("handsetView", _mdl.handsetView)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppUsersAuth_Ret.class));

}



/**
 * 主播认证第二步
 * @param backView 身份证反面(同上)
 * @param frontView 身份证正面(通过轩嗵云上传后返回的图片地址)
 * @param handsetView 手持身份证(同上)
 */
public static void authSecond(String backView,String frontView,String handsetView,HttpApiCallBack<com.kalacheng.libuser.model.AppUsersAuth> callback)
{
HttpClient.getInstance().post("/api/anchor/authSecond","/api/anchor/authSecond")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("backView", backView)
.params("frontView", frontView)
.params("handsetView", handsetView)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppUsersAuth_Ret.class));

}





/**
 * 未认证成功资料回显
 */
public static void authShow(HttpApiCallBack<com.kalacheng.libuser.model.AppUsersAuth> callback)
{
HttpClient.getInstance().post("/api/anchor/authShow","/api/anchor/authShow")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppUsersAuth_Ret.class));

}





/**
 * 主播认证接口返回的no_use 0:已认证 1:未认证 2:等待审核
 * @param type 类型 1:直播 2:动态 3:实名认证 4:短视频 5:语音
 */
public static void is_auth(int type,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/anchor/is_auth","/api/anchor/is_auth")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}


}
