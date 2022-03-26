package com.kalacheng.libuser.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 收益明细
 */
public class HttpApiIncomeRecord
{




/**
 * 查询主播公会收益详情
 * @param date 日期（yyyy-MM-dd）
 * @param guildId 公会Id
 * @param pageIndex 页码（默认从0开始）
 * @param pageSize 每页条数
 */
public static void queryUserGuildIncomeRecords(com.kalacheng.libuser.model_fun.IncomeRecord_queryUserGuildIncomeRecords _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.AnchorGuildIncomeRecordDTO> callback)
{
HttpClient.getInstance().post("/api/h5/queryUserGuildIncomeRecords","/api/h5/queryUserGuildIncomeRecords")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("date", _mdl.date)
.params("guildId", _mdl.guildId)
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AnchorGuildIncomeRecordDTO_RetArr.class));

}



/**
 * 查询主播公会收益详情
 * @param date 日期（yyyy-MM-dd）
 * @param guildId 公会Id
 * @param pageIndex 页码（默认从0开始）
 * @param pageSize 每页条数
 */
public static void queryUserGuildIncomeRecords(String date,long guildId,int pageIndex,int pageSize,HttpApiCallBackArr<com.kalacheng.libuser.model.AnchorGuildIncomeRecordDTO> callback)
{
HttpClient.getInstance().post("/api/h5/queryUserGuildIncomeRecords","/api/h5/queryUserGuildIncomeRecords")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("date", date)
.params("guildId", guildId)
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AnchorGuildIncomeRecordDTO_RetArr.class));

}





/**
 * 我的收益明细记录数据
 * @param date 日期（yyyy-MM-dd）
 * @param pageIndex 页码（默认从0开始）
 * @param pageSize 每页条数
 * @param type 类型
 */
public static void getMyIncomeRecordList(com.kalacheng.libuser.model_fun.IncomeRecord_getMyIncomeRecordList _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.UserIncomeRecordDTO> callback)
{
HttpClient.getInstance().post("/api/h5/getMyIncomeRecordList","/api/h5/getMyIncomeRecordList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("date", _mdl.date)
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.params("type", _mdl.type)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.UserIncomeRecordDTO_RetArr.class));

}



/**
 * 我的收益明细记录数据
 * @param date 日期（yyyy-MM-dd）
 * @param pageIndex 页码（默认从0开始）
 * @param pageSize 每页条数
 * @param type 类型
 */
public static void getMyIncomeRecordList(String date,int pageIndex,int pageSize,String type,HttpApiCallBackArr<com.kalacheng.libuser.model.UserIncomeRecordDTO> callback)
{
HttpClient.getInstance().post("/api/h5/getMyIncomeRecordList","/api/h5/getMyIncomeRecordList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("date", date)
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.params("type", type)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.UserIncomeRecordDTO_RetArr.class));

}


}
