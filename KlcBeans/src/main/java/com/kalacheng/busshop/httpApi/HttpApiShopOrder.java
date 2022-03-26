package com.kalacheng.busshop.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 商品订单信息API
 */
public class HttpApiShopOrder
{




/**
 * 获取可用物流列表
 */
public static void getLogisticsList(HttpApiCallBack<com.kalacheng.busshop.model.ShopLogisticsDTO> callback)
{
HttpClient.getInstance().post("/api/shopOrder/getLogisticsList","/api/shopOrder/getLogisticsList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopLogisticsDTO_Ret.class));

}





/**
 * 确认收货
 * @param businessOrderId 商家订单id
 */
public static void confirmReceipt(long businessOrderId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/shopOrder/confirmReceipt","/api/shopOrder/confirmReceipt")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessOrderId", businessOrderId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 获取物流信息
 * @param number 物流单号
 * @param orderNo 订单编号
 */
public static void getLogistics(com.kalacheng.busshop.model_fun.ShopOrder_getLogistics _mdl,HttpApiCallBack<com.kalacheng.busshop.model.ApiShopLogisticsDTO> callback)
{
HttpClient.getInstance().post("/api/shopOrder/getLogistics","/api/shopOrder/getLogistics")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("number", _mdl.number)
.params("orderNo", _mdl.orderNo)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ApiShopLogisticsDTO_Ret.class));

}



/**
 * 获取物流信息
 * @param number 物流单号
 * @param orderNo 订单编号
 */
public static void getLogistics(String number,String orderNo,HttpApiCallBack<com.kalacheng.busshop.model.ApiShopLogisticsDTO> callback)
{
HttpClient.getInstance().post("/api/shopOrder/getLogistics","/api/shopOrder/getLogistics")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("number", number)
.params("orderNo", orderNo)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ApiShopLogisticsDTO_Ret.class));

}





/**
 * 获取订单详情
 * @param businessOrderId 商家订单id
 */
public static void getUserOrderDetail(long businessOrderId,HttpApiCallBack<com.kalacheng.busshop.model.ShopUserOrderDetailDTO> callback)
{
HttpClient.getInstance().post("/api/shopOrder/getUserOrderDetail","/api/shopOrder/getUserOrderDetail")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessOrderId", businessOrderId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopUserOrderDetailDTO_Ret.class));

}





/**
 * 获取商家订单数据
 * @param searchDay 区分日期标识 0:今日 1:昨日 2:本周 3:上周 4:上月
 */
public static void getBusinessOrderInfo(int searchDay,HttpApiCallBack<com.kalacheng.busshop.model.ShopBusinessOrderInfoDTO> callback)
{
HttpClient.getInstance().post("/api/shopOrder/getBusinessOrderInfo","/api/shopOrder/getBusinessOrderInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("searchDay", searchDay)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopBusinessOrderInfoDTO_Ret.class));

}





/**
 * 修改订单地址信息
 * @param addressId 订单地址id
 * @param businessOrderId 商家订单id
 */
public static void updateOrderAddress(com.kalacheng.busshop.model_fun.ShopOrder_updateOrderAddress _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/shopOrder/updateOrderAddress","/api/shopOrder/updateOrderAddress")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("addressId", _mdl.addressId)
.params("businessOrderId", _mdl.businessOrderId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 修改订单地址信息
 * @param addressId 订单地址id
 * @param businessOrderId 商家订单id
 */
public static void updateOrderAddress(long addressId,long businessOrderId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/shopOrder/updateOrderAddress","/api/shopOrder/updateOrderAddress")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("addressId", addressId)
.params("businessOrderId", businessOrderId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 取消订单
 * @param businessOrderId 商家订单id
 */
public static void cancelOrder(long businessOrderId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/shopOrder/cancelOrder","/api/shopOrder/cancelOrder")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessOrderId", businessOrderId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 获取订单数量
 * @param type 1.买家，2卖家
 */
public static void getOrderNum(int type,HttpApiCallBack<com.kalacheng.libuser.model.ShopOrderNumDTO> callback)
{
HttpClient.getInstance().post("/api/shopOrder/getOrderNum","/api/shopOrder/getOrderNum")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ShopOrderNumDTO_Ret.class));

}





/**
 * 确认发货
 * @param businessOrderId 商家订单id
 * @param logisticsName 物流名称
 * @param logisticsNum 物流单号
 */
public static void confirmSent(com.kalacheng.busshop.model_fun.ShopOrder_confirmSent _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/shopOrder/confirmSent","/api/shopOrder/confirmSent")
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
 * 确认发货
 * @param businessOrderId 商家订单id
 * @param logisticsName 物流名称
 * @param logisticsNum 物流单号
 */
public static void confirmSent(long businessOrderId,String logisticsName,String logisticsNum,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/shopOrder/confirmSent","/api/shopOrder/confirmSent")
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





/**
 * 获取用户订单列表
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param quitStatus 退款订单状态0,全部 1,待审核 2,待发货 3,待收货 4,退款中 5.退款完成 6.退款失败
 * @param status 订单状态0,全部 1,待付款 2,待发货 3,待收货 4,订单完成
 */
public static void getUserOrderList(com.kalacheng.busshop.model_fun.ShopOrder_getUserOrderList _mdl,HttpApiCallBackArr<com.kalacheng.busshop.model.ShopUserOrderDTO> callback)
{
HttpClient.getInstance().post("/api/shopOrder/getUserOrderList","/api/shopOrder/getUserOrderList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.params("quitStatus", _mdl.quitStatus)
.params("status", _mdl.status)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busshop.model.ShopUserOrderDTO_RetArr.class));

}



/**
 * 获取用户订单列表
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param quitStatus 退款订单状态0,全部 1,待审核 2,待发货 3,待收货 4,退款中 5.退款完成 6.退款失败
 * @param status 订单状态0,全部 1,待付款 2,待发货 3,待收货 4,订单完成
 */
public static void getUserOrderList(int pageIndex,int pageSize,int quitStatus,int status,HttpApiCallBackArr<com.kalacheng.busshop.model.ShopUserOrderDTO> callback)
{
HttpClient.getInstance().post("/api/shopOrder/getUserOrderList","/api/shopOrder/getUserOrderList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.params("quitStatus", quitStatus)
.params("status", status)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busshop.model.ShopUserOrderDTO_RetArr.class));

}





/**
 * 获取主播(商家)订单列表
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param quitStatus 退款订单状态0,全部 1,待审核 2,待发货 3,待收货 4,退款中 5.退款完成 6.退款失败
 * @param status 订单状态0.全部 1.待付款 2.待发货 3.待收货 4.订单完成
 */
public static void getAnchorOrderList(com.kalacheng.busshop.model_fun.ShopOrder_getAnchorOrderList _mdl,HttpApiCallBackArr<com.kalacheng.busshop.model.ShopUserOrderDTO> callback)
{
HttpClient.getInstance().post("/api/shopOrder/getAnchorOrderList","/api/shopOrder/getAnchorOrderList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.params("quitStatus", _mdl.quitStatus)
.params("status", _mdl.status)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busshop.model.ShopUserOrderDTO_RetArr.class));

}



/**
 * 获取主播(商家)订单列表
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param quitStatus 退款订单状态0,全部 1,待审核 2,待发货 3,待收货 4,退款中 5.退款完成 6.退款失败
 * @param status 订单状态0.全部 1.待付款 2.待发货 3.待收货 4.订单完成
 */
public static void getAnchorOrderList(int pageIndex,int pageSize,int quitStatus,int status,HttpApiCallBackArr<com.kalacheng.busshop.model.ShopUserOrderDTO> callback)
{
HttpClient.getInstance().post("/api/shopOrder/getAnchorOrderList","/api/shopOrder/getAnchorOrderList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.params("quitStatus", quitStatus)
.params("status", status)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busshop.model.ShopUserOrderDTO_RetArr.class));

}


}
