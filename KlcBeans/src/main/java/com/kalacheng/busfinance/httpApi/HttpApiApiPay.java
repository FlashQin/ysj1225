package com.kalacheng.busfinance.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 支付相关的接口
 */
public class HttpApiApiPay
{




/**
 * 苹果内购校验  1校验成功  2苹果验证失败，返回数据为空
 * @param orderNo 订单号
 * @param payload 校验体（base64字符串）
 * @param transactionId 苹果内购交易ID
 */
public static void iosPayCallBack(com.kalacheng.busfinance.model_fun.ApiPay_iosPayCallBack _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/pay/iosPayCallBack","/api/pay/iosPayCallBack")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("orderNo", _mdl.orderNo)
.params("payload", _mdl.payload)
.params("transactionId", _mdl.transactionId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 苹果内购校验  1校验成功  2苹果验证失败，返回数据为空
 * @param orderNo 订单号
 * @param payload 校验体（base64字符串）
 * @param transactionId 苹果内购交易ID
 */
public static void iosPayCallBack(String orderNo,String payload,String transactionId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/pay/iosPayCallBack","/api/pay/iosPayCallBack")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("orderNo", orderNo)
.params("payload", payload)
.params("transactionId", transactionId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 选择充值规则进行充值,返回value为支付宝/微信支付等需要的参数
 * @param channelId 支付渠道ID 1:支付宝 2:微信 3:ios内购
 * @param productId 商品id
 * @param type 业务类型 1：金币充值  2：购物支付 3.待定 4.购买贵族 5.购买SVIP
 */
public static void startPay(com.kalacheng.busfinance.model_fun.ApiPay_startPay _mdl,HttpApiCallBack<com.kalacheng.libbas.model.SingleString> callback)
{
HttpClient.getInstance().post("/api/pay/startPay","/api/pay/startPay")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("channelId", _mdl.channelId)
.params("productId", _mdl.productId)
.params("type", _mdl.type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.SingleString_Ret.class));

}



/**
 * 选择充值规则进行充值,返回value为支付宝/微信支付等需要的参数
 * @param channelId 支付渠道ID 1:支付宝 2:微信 3:ios内购
 * @param productId 商品id
 * @param type 业务类型 1：金币充值  2：购物支付 3.待定 4.购买贵族 5.购买SVIP
 */
public static void startPay(long channelId,long productId,int type,HttpApiCallBack<com.kalacheng.libbas.model.SingleString> callback)
{
HttpClient.getInstance().post("/api/pay/startPay","/api/pay/startPay")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("channelId", channelId)
.params("productId", productId)
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.SingleString_Ret.class));

}


}
