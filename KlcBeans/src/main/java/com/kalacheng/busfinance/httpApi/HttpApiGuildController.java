package com.kalacheng.busfinance.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 公会相关
 */
public class HttpApiGuildController
{
//  /api/guild/toGuildDetail
//  /api/guild/toGuildDetail  此函数没有开放POST请求。
//  /api/guild/toGuildList
//  /api/guild/toGuildList  此函数没有开放POST请求。




/**
 * 申请加入公会
 * @param enclosure enclosure
 * @param endTime endTime
 * @param expectIncome expectIncome
 * @param feat feat
 * @param guildId guildId
 * @param idCard idCard
 * @param mobile mobile
 * @param realname realname
 * @param sex sex
 * @param startTime startTime
 */
public static void applyJoinGuild(com.kalacheng.busfinance.model_fun.GuildController_applyJoinGuild _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/guild/applyJoinGuild","/api/guild/applyJoinGuild")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("enclosure", _mdl.enclosure)
.params("endTime", _mdl.endTime)
.params("expectIncome", _mdl.expectIncome)
.params("feat", _mdl.feat)
.params("guildId", _mdl.guildId)
.params("idCard", _mdl.idCard)
.params("mobile", _mdl.mobile)
.params("realname", _mdl.realname)
.params("sex", _mdl.sex)
.params("startTime", _mdl.startTime)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 申请加入公会
 * @param enclosure enclosure
 * @param endTime endTime
 * @param expectIncome expectIncome
 * @param feat feat
 * @param guildId guildId
 * @param idCard idCard
 * @param mobile mobile
 * @param realname realname
 * @param sex sex
 * @param startTime startTime
 */
public static void applyJoinGuild(String enclosure,String endTime,double expectIncome,String feat,long guildId,String idCard,String mobile,String realname,int sex,String startTime,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/guild/applyJoinGuild","/api/guild/applyJoinGuild")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("enclosure", enclosure)
.params("endTime", endTime)
.params("expectIncome", expectIncome)
.params("feat", feat)
.params("guildId", guildId)
.params("idCard", idCard)
.params("mobile", mobile)
.params("realname", realname)
.params("sex", sex)
.params("startTime", startTime)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 申请退出公会
 * @param guildId guildId
 * @param reason reason
 */
public static void applyQuitGuild(com.kalacheng.busfinance.model_fun.GuildController_applyQuitGuild _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/guild/applyQuitGuild","/api/guild/applyQuitGuild")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("guildId", _mdl.guildId)
.params("reason", _mdl.reason)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 申请退出公会
 * @param guildId guildId
 * @param reason reason
 */
public static void applyQuitGuild(long guildId,String reason,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/guild/applyQuitGuild","/api/guild/applyQuitGuild")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("guildId", guildId)
.params("reason", reason)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}

//  /api/guild/toJoinGuild
//  /api/guild/toJoinGuild  此函数没有开放POST请求。
//  /api/guild/toAnchorEquity
//  /api/guild/toAnchorEquity  此函数没有开放POST请求。

}
