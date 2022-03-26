package com.kalacheng.busfinance.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * H5公会相关
 */
public class HttpApiApiGuild
{




/**
 * 查询公会列表
 */
public static void queryGuildList(HttpApiCallBackArr<com.kalacheng.libuser.model.GuildDto> callback)
{
HttpClient.getInstance().post("/api/guild/queryGuildList","/api/guild/queryGuildList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.GuildDto_RetArr.class));

}





/**
 * 查询主播公会收益详情
 * @param guildId 公会id
 */
public static void queryAssociationIncomeRecords(long guildId,HttpApiCallBackArr<com.kalacheng.libuser.model.AnchorGuildIncomeRecordDTO> callback)
{
HttpClient.getInstance().post("/api/guild/queryAssociationIncomeRecords","/api/guild/queryAssociationIncomeRecords")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("guildId", guildId)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AnchorGuildIncomeRecordDTO_RetArr.class));

}


}
