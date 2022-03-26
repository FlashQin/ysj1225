package com.kalacheng.busshop.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 重构购物接口
 */
public class HttpApiRestShop
{




/**
 * 商家中心
 * @param businessId 商家id
 */
public static void businessCenter(long businessId,HttpApiCallBack<com.kalacheng.busshop.model.ShopBusinessDTO> callback)
{
HttpClient.getInstance().post("/api/restshop/businessCenter","/api/restshop/businessCenter")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessId", businessId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopBusinessDTO_Ret.class));

}





/**
 * 搜索店内宝贝
 * @param businessId 商家id
 * @param productName 商品名称
 */
public static void searchBusinessProduct(com.kalacheng.busshop.model_fun.RestShop_searchBusinessProduct _mdl,HttpApiCallBack<com.kalacheng.busshop.model.ShopBusinessDTO> callback)
{
HttpClient.getInstance().post("/api/restshop/searchBusinessProduct","/api/restshop/searchBusinessProduct")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessId", _mdl.businessId)
.params("productName", _mdl.productName)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopBusinessDTO_Ret.class));

}



/**
 * 搜索店内宝贝
 * @param businessId 商家id
 * @param productName 商品名称
 */
public static void searchBusinessProduct(long businessId,String productName,HttpApiCallBack<com.kalacheng.busshop.model.ShopBusinessDTO> callback)
{
HttpClient.getInstance().post("/api/restshop/searchBusinessProduct","/api/restshop/searchBusinessProduct")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("businessId", businessId)
.params("productName", productName)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopBusinessDTO_Ret.class));

}


}
