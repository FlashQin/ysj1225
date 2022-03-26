package com.kalacheng.busshop.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 退款订单控制器
 */
public class HttpApiShopQuiteOrder
{




/**
 * 确认申请退款
 * @param businessOrderId 商家订单id
 * @param reasonId 退款原因id
 * @param refundNotes 退款备注
 * @param refundNotesImages 退款备注图片
 * @param type 1.仅退款，2.退货退款
 */
public static void applyRefund(com.kalacheng.busshop.model_fun.ShopQuiteOrder_applyRefund _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/quiteOrder/applyRefund","/api/quiteOrder/applyRefund")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessOrderId", _mdl.businessOrderId)
.params("reasonId", _mdl.reasonId)
.params("refundNotes", _mdl.refundNotes)
.params("refundNotesImages", _mdl.refundNotesImages)
.params("type", _mdl.type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 确认申请退款
 * @param businessOrderId 商家订单id
 * @param reasonId 退款原因id
 * @param refundNotes 退款备注
 * @param refundNotesImages 退款备注图片
 * @param type 1.仅退款，2.退货退款
 */
public static void applyRefund(long businessOrderId,long reasonId,String refundNotes,String refundNotesImages,int type,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/quiteOrder/applyRefund","/api/quiteOrder/applyRefund")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessOrderId", businessOrderId)
.params("reasonId", reasonId)
.params("refundNotes", refundNotes)
.params("refundNotesImages", refundNotesImages)
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 退款-卖家收货
 * @param businessOrderId 商家订单id
 * @param reason 拒绝原因
 * @param state 退款审核状态：1.同意，2.拒绝
 */
public static void sellerReceipt(com.kalacheng.busshop.model_fun.ShopQuiteOrder_sellerReceipt _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/quiteOrder/sellerReceipt","/api/quiteOrder/sellerReceipt")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessOrderId", _mdl.businessOrderId)
.params("reason", _mdl.reason)
.params("state", _mdl.state)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 退款-卖家收货
 * @param businessOrderId 商家订单id
 * @param reason 拒绝原因
 * @param state 退款审核状态：1.同意，2.拒绝
 */
public static void sellerReceipt(long businessOrderId,String reason,int state,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/quiteOrder/sellerReceipt","/api/quiteOrder/sellerReceipt")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessOrderId", businessOrderId)
.params("reason", reason)
.params("state", state)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 申请退款
 * @param businessOrderId 商家订单id
 */
public static void beforApplyRefund(long businessOrderId,HttpApiCallBack<com.kalacheng.busshop.model.ApplyRefundDTO> callback)
{
HttpClient.getInstance().post("/api/quiteOrder/beforApplyRefund","/api/quiteOrder/beforApplyRefund")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessOrderId", businessOrderId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ApplyRefundDTO_Ret.class));

}





/**
 * 退款审核
 * @param businessOrderId 商家订单id
 * @param reason 拒绝原因
 * @param state 退款审核状态：1.同意，2.拒绝
 */
public static void refundAudit(com.kalacheng.busshop.model_fun.ShopQuiteOrder_refundAudit _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/quiteOrder/refundAudit","/api/quiteOrder/refundAudit")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessOrderId", _mdl.businessOrderId)
.params("reason", _mdl.reason)
.params("state", _mdl.state)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 退款审核
 * @param businessOrderId 商家订单id
 * @param reason 拒绝原因
 * @param state 退款审核状态：1.同意，2.拒绝
 */
public static void refundAudit(long businessOrderId,String reason,int state,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/quiteOrder/refundAudit","/api/quiteOrder/refundAudit")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessOrderId", businessOrderId)
.params("reason", reason)
.params("state", state)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 取消申请退款
 * @param businessOrderId 商家订单id
 */
public static void cancelApplyRefund(long businessOrderId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/quiteOrder/cancelApplyRefund","/api/quiteOrder/cancelApplyRefund")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessOrderId", businessOrderId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 退款-买家发货
 * @param businessOrderId 商家订单id
 * @param logisticsName 物流名称
 * @param logisticsNum 物流单号
 */
public static void buyerDeliver(com.kalacheng.busshop.model_fun.ShopQuiteOrder_buyerDeliver _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/quiteOrder/buyerDeliver","/api/quiteOrder/buyerDeliver")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessOrderId", _mdl.businessOrderId)
.params("logisticsName", _mdl.logisticsName)
.params("logisticsNum", _mdl.logisticsNum)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 退款-买家发货
 * @param businessOrderId 商家订单id
 * @param logisticsName 物流名称
 * @param logisticsNum 物流单号
 */
public static void buyerDeliver(long businessOrderId,String logisticsName,String logisticsNum,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/quiteOrder/buyerDeliver","/api/quiteOrder/buyerDeliver")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessOrderId", businessOrderId)
.params("logisticsName", logisticsName)
.params("logisticsNum", logisticsNum)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}


}
