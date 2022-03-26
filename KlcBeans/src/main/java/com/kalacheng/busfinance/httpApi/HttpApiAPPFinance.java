package com.kalacheng.busfinance.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 资金相关接口
 */
public class HttpApiAPPFinance
{




/**
 * 获取主播可提现账户余额(优先显示公会)
 */
public static void anchorVotes(HttpApiCallBack<com.kalacheng.libuser.model.AnchorVotesDTO> callback)
{
HttpClient.getInstance().post("/api/finance/anchorVotes","/api/finance/anchorVotes")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AnchorVotesDTO_Ret.class));

}





/**
 * 我的收益里提现账号列表
 */
public static void withdraw_account(HttpApiCallBackArr<com.kalacheng.libuser.model.AppUsersCashAccount> callback)
{
HttpClient.getInstance().post("/api/finance/withdraw_account","/api/finance/withdraw_account")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppUsersCashAccount_RetArr.class));

}





/**
 * 充值规则接口
 */
public static void rules_list(HttpApiCallBack<com.kalacheng.libuser.model.ApiAppChargeRulesResp> callback)
{
HttpClient.getInstance().post("/api/finance/rules_list","/api/finance/rules_list")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiAppChargeRulesResp_Ret.class));

}





/**
 * 收益榜
 * @param page 页数
 * @param pageSize 每页的条数
 * @param type 类型 0:总榜 1:日榜 2:周榜 3:月榜
 * @param uid 用户id(查看礼物榜时必传,默认传0)
 */
public static void votesList(com.kalacheng.busfinance.model_fun.APPFinance_votesList _mdl,HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiUsersVoterecord> callback)
{
HttpClient.getInstance().post("/api/finance/votesList","/api/finance/votesList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.params("type", _mdl.type)
.params("uid", _mdl.uid)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.ApiUsersVoterecord_RetPageArr.class));

}



/**
 * 收益榜
 * @param page 页数
 * @param pageSize 每页的条数
 * @param type 类型 0:总榜 1:日榜 2:周榜 3:月榜
 * @param uid 用户id(查看礼物榜时必传,默认传0)
 */
public static void votesList(int page,int pageSize,int type,int uid,HttpApiCallBackPageArr<com.kalacheng.libuser.model.ApiUsersVoterecord> callback)
{
HttpClient.getInstance().post("/api/finance/votesList","/api/finance/votesList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", page)
.params("pageSize", pageSize)
.params("type", type)
.params("uid", uid)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.ApiUsersVoterecord_RetPageArr.class));

}





/**
 * 提现记录(平台/公会)
 * @param date 日期
 * @param pageIndex 每页的条数
 * @param pageSize 每页的条数
 * @param status 状态，0审核中，1审核通过，2审核拒绝，-1全部
 * @param type 0.平台，1.公会
 */
public static void userCashRecord_list(com.kalacheng.busfinance.model_fun.APPFinance_userCashRecord_list _mdl,HttpApiCallBackPageArr<com.kalacheng.libuser.model.CashRecordDTO> callback)
{
HttpClient.getInstance().post("/api/finance/userCashRecord_list","/api/finance/userCashRecord_list")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("date", _mdl.date)
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.params("status", _mdl.status)
.params("type", _mdl.type)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.CashRecordDTO_RetPageArr.class));

}



/**
 * 提现记录(平台/公会)
 * @param date 日期
 * @param pageIndex 每页的条数
 * @param pageSize 每页的条数
 * @param status 状态，0审核中，1审核通过，2审核拒绝，-1全部
 * @param type 0.平台，1.公会
 */
public static void userCashRecord_list(String date,int pageIndex,int pageSize,int status,int type,HttpApiCallBackPageArr<com.kalacheng.libuser.model.CashRecordDTO> callback)
{
HttpClient.getInstance().post("/api/finance/userCashRecord_list","/api/finance/userCashRecord_list")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("date", date)
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.params("status", status)
.params("type", type)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.CashRecordDTO_RetPageArr.class));

}





/**
 * 我的收益里添加提现账号
 * @param account 账号
 * @param accountBank 银行名称(type为3时传入)
 * @param branch 支行
 * @param name 姓名
 * @param recordId 相关记录ID
 * @param type 类型1表示支付宝，2表示微信，3表示银行卡
 */
public static void withdraw_account_add(com.kalacheng.busfinance.model_fun.APPFinance_withdraw_account_add _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/finance/withdraw_account_add","/api/finance/withdraw_account_add")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("account", _mdl.account)
.params("accountBank", _mdl.accountBank)
.params("branch", _mdl.branch)
.params("name", _mdl.name)
.params("recordId", _mdl.recordId)
.params("type", _mdl.type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 我的收益里添加提现账号
 * @param account 账号
 * @param accountBank 银行名称(type为3时传入)
 * @param branch 支行
 * @param name 姓名
 * @param recordId 相关记录ID
 * @param type 类型1表示支付宝，2表示微信，3表示银行卡
 */
public static void withdraw_account_add(String account,String accountBank,String branch,String name,long recordId,int type,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/finance/withdraw_account_add","/api/finance/withdraw_account_add")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("account", account)
.params("accountBank", accountBank)
.params("branch", branch)
.params("name", name)
.params("recordId", recordId)
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 我的收益提现申请
 * @param accountId 提现账号id
 * @param accountName 账号
 * @param accountType 账号类型 1：支付宝  2：微信，3：银行卡
 * @param delta 提现金币/佣金数量 (男朋友中为钻石)
 * @param type 佣金提现 1：金币/钻石提现 (男朋友中为钻石)  2：佣金提现 3：公会账户提现， 4：商家提现
 */
public static void withdraw_account_apply(com.kalacheng.busfinance.model_fun.APPFinance_withdraw_account_apply _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/finance/withdraw_account_apply","/api/finance/withdraw_account_apply")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("accountId", _mdl.accountId)
.params("accountName", _mdl.accountName)
.params("accountType", _mdl.accountType)
.params("delta", _mdl.delta)
.params("type", _mdl.type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 我的收益提现申请
 * @param accountId 提现账号id
 * @param accountName 账号
 * @param accountType 账号类型 1：支付宝  2：微信，3：银行卡
 * @param delta 提现金币/佣金数量 (男朋友中为钻石)
 * @param type 佣金提现 1：金币/钻石提现 (男朋友中为钻石)  2：佣金提现 3：公会账户提现， 4：商家提现
 */
public static void withdraw_account_apply(long accountId,String accountName,int accountType,int delta,int type,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/finance/withdraw_account_apply","/api/finance/withdraw_account_apply")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("accountId", accountId)
.params("accountName", accountName)
.params("accountType", accountType)
.params("delta", delta)
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 我的收益里提现记录
 * @param page 页数
 * @param pageSize 每页的条数
 */
public static void cashrecord_list(com.kalacheng.busfinance.model_fun.APPFinance_cashrecord_list _mdl,HttpApiCallBackPageArr<com.kalacheng.libuser.model.AdminUsersCashrecord> callback)
{
HttpClient.getInstance().post("/api/finance/cashrecord_list","/api/finance/cashrecord_list")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.AdminUsersCashrecord_RetPageArr.class));

}



/**
 * 我的收益里提现记录
 * @param page 页数
 * @param pageSize 每页的条数
 */
public static void cashrecord_list(int page,int pageSize,HttpApiCallBackPageArr<com.kalacheng.libuser.model.AdminUsersCashrecord> callback)
{
HttpClient.getInstance().post("/api/finance/cashrecord_list","/api/finance/cashrecord_list")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", page)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.libuser.model.AdminUsersCashrecord_RetPageArr.class));

}





/**
 * 我的收益里删除提现账号
 * @param id 提现账号id
 */
public static void withdraw_account_del(long id,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/finance/withdraw_account_del","/api/finance/withdraw_account_del")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("id", id)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 获取所有的支付方式
 */
public static void getPayWayList(HttpApiCallBackArr<com.kalacheng.libuser.model.CfgPayWay> callback)
{
HttpClient.getInstance().post("/api/finance/getPayWayList","/api/finance/getPayWayList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.CfgPayWay_RetArr.class));

}





/**
 * 贡献榜
 * @param anchorId 主播ID(查看礼物榜时必传,默认传0)
 * @param page 页数
 * @param pageSize 每页的条数
 * @param type 类型 0:总榜 1:日榜 2:周榜 3:月榜
 */
public static void contributionList(com.kalacheng.busfinance.model_fun.APPFinance_contributionList _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.RanksDto> callback)
{
HttpClient.getInstance().post("/api/finance/contributionList","/api/finance/contributionList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.params("type", _mdl.type)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.RanksDto_RetArr.class));

}



/**
 * 贡献榜
 * @param anchorId 主播ID(查看礼物榜时必传,默认传0)
 * @param page 页数
 * @param pageSize 每页的条数
 * @param type 类型 0:总榜 1:日榜 2:周榜 3:月榜
 */
public static void contributionList(long anchorId,int page,int pageSize,int type,HttpApiCallBackArr<com.kalacheng.libuser.model.RanksDto> callback)
{
HttpClient.getInstance().post("/api/finance/contributionList","/api/finance/contributionList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("page", page)
.params("pageSize", pageSize)
.params("type", type)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.RanksDto_RetArr.class));

}





/**
 * 首冲奖励
 */
public static void first_recharge(HttpApiCallBackArr<com.kalacheng.libuser.model.AdminGiftPack> callback)
{
HttpClient.getInstance().post("/api/finance/first_recharge","/api/finance/first_recharge")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AdminGiftPack_RetArr.class));

}





/**
 * 用户收益接口/用户公会收益 （新接口）
 * @param type 1.平台收益，2.公会收益
 */
public static void userProfit(int type,HttpApiCallBack<com.kalacheng.libuser.model.ProfitCenterDTO> callback)
{
HttpClient.getInstance().post("/api/finance/userProfit","/api/finance/userProfit")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ProfitCenterDTO_Ret.class));

}





/**
 * 公会榜
 * @param page 页数
 * @param pageSize 每页的条数
 * @param type 类型 0:总榜 1:日榜 2:周榜 3:月榜
 */
public static void guildList(com.kalacheng.busfinance.model_fun.APPFinance_guildList _mdl,HttpApiCallBackPageArr<com.kalacheng.buscommon.model.GuildListVO> callback)
{
HttpClient.getInstance().post("/api/finance/guildList","/api/finance/guildList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.params("type", _mdl.type)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.buscommon.model.GuildListVO_RetPageArr.class));

}



/**
 * 公会榜
 * @param page 页数
 * @param pageSize 每页的条数
 * @param type 类型 0:总榜 1:日榜 2:周榜 3:月榜
 */
public static void guildList(int page,int pageSize,int type,HttpApiCallBackPageArr<com.kalacheng.buscommon.model.GuildListVO> callback)
{
HttpClient.getInstance().post("/api/finance/guildList","/api/finance/guildList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", page)
.params("pageSize", pageSize)
.params("type", type)
.execute(new HttpApiCallBackPageArrConvert(callback,com.kalacheng.buscommon.model.GuildListVO_RetPageArr.class));

}


}
