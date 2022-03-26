package com.kalacheng.busshop.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 商家接口API
 */
public class HttpApiShopBusiness
{




/**
 * 获取商家提现记录
 * @param page 当前页
 * @param pageSize 每页的条数
 */
public static void getWithdrawList(com.kalacheng.busshop.model_fun.ShopBusiness_getWithdrawList _mdl,HttpApiCallBack<com.kalacheng.busshop.model.ShopWithdrawDTO> callback)
{
HttpClient.getInstance().post("/api/business/getWithdrawList","/api/business/getWithdrawList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", _mdl.page)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopWithdrawDTO_Ret.class));

}



/**
 * 获取商家提现记录
 * @param page 当前页
 * @param pageSize 每页的条数
 */
public static void getWithdrawList(int page,int pageSize,HttpApiCallBack<com.kalacheng.busshop.model.ShopWithdrawDTO> callback)
{
HttpClient.getInstance().post("/api/business/getWithdrawList","/api/business/getWithdrawList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("page", page)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopWithdrawDTO_Ret.class));

}





/**
 * 商家提现
 * @param accountId 支付账号id
 * @param amount 提现金额
 */
public static void withdraw(com.kalacheng.busshop.model_fun.ShopBusiness_withdraw _mdl,HttpApiCallBack<com.kalacheng.busshop.model.ShopWithdrawDTO> callback)
{
HttpClient.getInstance().post("/api/business/withdraw","/api/business/withdraw")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("accountId", _mdl.accountId)
.params("amount", _mdl.amount)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopWithdrawDTO_Ret.class));

}



/**
 * 商家提现
 * @param accountId 支付账号id
 * @param amount 提现金额
 */
public static void withdraw(long accountId,double amount,HttpApiCallBack<com.kalacheng.busshop.model.ShopWithdrawDTO> callback)
{
HttpClient.getInstance().post("/api/business/withdraw","/api/business/withdraw")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("accountId", accountId)
.params("amount", amount)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopWithdrawDTO_Ret.class));

}





/**
 * 商家入驻申请
 * @param businessLicense 营业执照
 * @param logo 商家LOGO
 * @param mobile 联系电话
 * @param name 商家名称
 * @param present 商家简介
 * @param presentPicture 商家简介图片地址
 */
public static void applicationForResidence(com.kalacheng.busshop.model_fun.ShopBusiness_applicationForResidence _mdl,HttpApiCallBack<com.kalacheng.busshop.model.AppMerchantAgreementDTO> callback)
{
HttpClient.getInstance().post("/api/business/applicationForResidence","/api/business/applicationForResidence")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessLicense", _mdl.businessLicense)
.params("logo", _mdl.logo)
.params("mobile", _mdl.mobile)
.params("name", _mdl.name)
.params("present", _mdl.present)
.params("presentPicture", _mdl.presentPicture)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.AppMerchantAgreementDTO_Ret.class));

}



/**
 * 商家入驻申请
 * @param businessLicense 营业执照
 * @param logo 商家LOGO
 * @param mobile 联系电话
 * @param name 商家名称
 * @param present 商家简介
 * @param presentPicture 商家简介图片地址
 */
public static void applicationForResidence(String businessLicense,String logo,String mobile,String name,String present,String presentPicture,HttpApiCallBack<com.kalacheng.busshop.model.AppMerchantAgreementDTO> callback)
{
HttpClient.getInstance().post("/api/business/applicationForResidence","/api/business/applicationForResidence")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessLicense", businessLicense)
.params("logo", logo)
.params("mobile", mobile)
.params("name", name)
.params("present", present)
.params("presentPicture", presentPicture)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.AppMerchantAgreementDTO_Ret.class));

}





/**
 * 修改商家信息
 * @param businessId 商家id
 * @param businessLicense 营业执照
 * @param logo 商家LOGO
 * @param mobile 联系电话
 * @param name 商家名称
 * @param present 商家简介
 * @param presentPicture 商家简介图片地址
 */
public static void updateBusiness(com.kalacheng.busshop.model_fun.ShopBusiness_updateBusiness _mdl,HttpApiCallBack<com.kalacheng.busshop.model.AppMerchantAgreementDTO> callback)
{
HttpClient.getInstance().post("/api/business/updateBusiness","/api/business/updateBusiness")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessId", _mdl.businessId)
.params("businessLicense", _mdl.businessLicense)
.params("logo", _mdl.logo)
.params("mobile", _mdl.mobile)
.params("name", _mdl.name)
.params("present", _mdl.present)
.params("presentPicture", _mdl.presentPicture)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.AppMerchantAgreementDTO_Ret.class));

}



/**
 * 修改商家信息
 * @param businessId 商家id
 * @param businessLicense 营业执照
 * @param logo 商家LOGO
 * @param mobile 联系电话
 * @param name 商家名称
 * @param present 商家简介
 * @param presentPicture 商家简介图片地址
 */
public static void updateBusiness(long businessId,String businessLicense,String logo,String mobile,String name,String present,String presentPicture,HttpApiCallBack<com.kalacheng.busshop.model.AppMerchantAgreementDTO> callback)
{
HttpClient.getInstance().post("/api/business/updateBusiness","/api/business/updateBusiness")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessId", businessId)
.params("businessLicense", businessLicense)
.params("logo", logo)
.params("mobile", mobile)
.params("name", name)
.params("present", present)
.params("presentPicture", presentPicture)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.AppMerchantAgreementDTO_Ret.class));

}





/**
 * 获取商家直播信息
 * @param anchorId 主播ID
 */
public static void getLiveInfo(long anchorId,HttpApiCallBack<com.kalacheng.busshop.model.ShopLiveInfoDTO> callback)
{
HttpClient.getInstance().post("/api/business/getLiveInfo","/api/business/getLiveInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopLiveInfoDTO_Ret.class));

}





/**
 * 获取直播预告列表
 */
public static void getLiveAnnouncementList(HttpApiCallBack<com.kalacheng.busshop.model.ShopLiveAnnouncementDTO> callback)
{
HttpClient.getInstance().post("/api/business/getLiveAnnouncementList","/api/business/getLiveAnnouncementList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopLiveAnnouncementDTO_Ret.class));

}





/**
 * 删除直播预告
 * @param liveAnnouncementId 预告id
 */
public static void delLiveAnnouncement(long liveAnnouncementId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/business/delLiveAnnouncement","/api/business/delLiveAnnouncement")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveAnnouncementId", liveAnnouncementId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 获取商家信息
 */
public static void getOne(HttpApiCallBack<com.kalacheng.busshop.model.ShopBusiness> callback)
{
HttpClient.getInstance().post("/api/business/getOne","/api/business/getOne")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopBusiness_Ret.class));

}





/**
 * 添加直播预告
 * @param liveDate 直播日期
 * @param posterStickers 海报贴纸
 * @param shopCategory 购物分类
 * @param startTime 开始时间
 * @param title 标题
 */
public static void saveLiveAnnouncement(com.kalacheng.busshop.model_fun.ShopBusiness_saveLiveAnnouncement _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/business/saveLiveAnnouncement","/api/business/saveLiveAnnouncement")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveDate", _mdl.liveDate)
.params("posterStickers", _mdl.posterStickers)
.params("shopCategory", _mdl.shopCategory)
.params("startTime", _mdl.startTime)
.params("title", _mdl.title)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 添加直播预告
 * @param liveDate 直播日期
 * @param posterStickers 海报贴纸
 * @param shopCategory 购物分类
 * @param startTime 开始时间
 * @param title 标题
 */
public static void saveLiveAnnouncement(String liveDate,String posterStickers,String shopCategory,String startTime,String title,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/business/saveLiveAnnouncement","/api/business/saveLiveAnnouncement")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveDate", liveDate)
.params("posterStickers", posterStickers)
.params("shopCategory", shopCategory)
.params("startTime", startTime)
.params("title", title)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 获取商家直播预告列表
 */
public static void getBusinessLiveAnnouncementList(HttpApiCallBack<com.kalacheng.busshop.model.ShopBusinessLiveAnnouncementDTO> callback)
{
HttpClient.getInstance().post("/api/business/getBusinessLiveAnnouncementList","/api/business/getBusinessLiveAnnouncementList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopBusinessLiveAnnouncementDTO_Ret.class));

}





/**
 * 是否入驻
 */
public static void settleIn(HttpApiCallBack<com.kalacheng.busshop.model.AppMerchantAgreementDTO> callback)
{
HttpClient.getInstance().post("/api/business/settleIn","/api/business/settleIn")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.AppMerchantAgreementDTO_Ret.class));

}





/**
 * 获取商家提现配置信息
 */
public static void getWithdrawInfo(HttpApiCallBack<com.kalacheng.busshop.model.ShopWithdrawDTO> callback)
{
HttpClient.getInstance().post("/api/business/getWithdrawInfo","/api/business/getWithdrawInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopWithdrawDTO_Ret.class));

}


}
