package com.kalacheng.busshop.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 购物车相关API
 */
public class HttpApiShopCar
{




/**
 * 删除购物车中商品
 * @param shopCarId 购物车id
 */
public static void delShopCar(long shopCarId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/car/delShopCar","/api/car/delShopCar")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("shopCarId", shopCarId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 购物车商品列表
 */
public static void getShopCarList(HttpApiCallBackArr<com.kalacheng.busshop.model.ShopCarDTO> callback)
{
HttpClient.getInstance().post("/api/car/getShopCarList","/api/car/getShopCarList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busshop.model.ShopCarDTO_RetArr.class));

}





/**
 * 删除收货地址
 * @param addressId 收货地址id
 */
public static void delShopAddress(long addressId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/car/delShopAddress","/api/car/delShopAddress")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("addressId", addressId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 修改收货地址
 * @param address 收货人地址
 * @param addressId 收货地址id
 * @param area 区
 * @param city 城市
 * @param isDefault 是否默认1默认0非默认
 * @param phoneNum 收货人手机号
 * @param pro 省份
 * @param userName 收货人姓名
 */
public static void updateShopAddress(com.kalacheng.busshop.model_fun.ShopCar_updateShopAddress _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/car/updateShopAddress","/api/car/updateShopAddress")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("address", _mdl.address)
.params("addressId", _mdl.addressId)
.params("area", _mdl.area)
.params("city", _mdl.city)
.params("isDefault", _mdl.isDefault)
.params("phoneNum", _mdl.phoneNum)
.params("pro", _mdl.pro)
.params("userName", _mdl.userName)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 修改收货地址
 * @param address 收货人地址
 * @param addressId 收货地址id
 * @param area 区
 * @param city 城市
 * @param isDefault 是否默认1默认0非默认
 * @param phoneNum 收货人手机号
 * @param pro 省份
 * @param userName 收货人姓名
 */
public static void updateShopAddress(String address,long addressId,String area,String city,int isDefault,String phoneNum,String pro,String userName,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/car/updateShopAddress","/api/car/updateShopAddress")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("address", address)
.params("addressId", addressId)
.params("area", area)
.params("city", city)
.params("isDefault", isDefault)
.params("phoneNum", phoneNum)
.params("pro", pro)
.params("userName", userName)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 添加商品到购物车
 * @param composeId 商品属性组合id(skuId)
 * @param goodsId 商品id
 * @param goodsNum 商品数量
 */
public static void saveShopCar(com.kalacheng.busshop.model_fun.ShopCar_saveShopCar _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/car/saveShopCar","/api/car/saveShopCar")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("composeId", _mdl.composeId)
.params("goodsId", _mdl.goodsId)
.params("goodsNum", _mdl.goodsNum)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 添加商品到购物车
 * @param composeId 商品属性组合id(skuId)
 * @param goodsId 商品id
 * @param goodsNum 商品数量
 */
public static void saveShopCar(long composeId,long goodsId,int goodsNum,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/car/saveShopCar","/api/car/saveShopCar")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("composeId", composeId)
.params("goodsId", goodsId)
.params("goodsNum", goodsNum)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 添加地址
 * @param address 详细地址
 * @param area 区
 * @param city 城市
 * @param isDefault 是否默认1默认0非默认
 * @param phoneNum 收货人手机号
 * @param pro 省份
 * @param userName 收货人姓名
 */
public static void saveAddress(com.kalacheng.busshop.model_fun.ShopCar_saveAddress _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/car/saveAddress","/api/car/saveAddress")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("address", _mdl.address)
.params("area", _mdl.area)
.params("city", _mdl.city)
.params("isDefault", _mdl.isDefault)
.params("phoneNum", _mdl.phoneNum)
.params("pro", _mdl.pro)
.params("userName", _mdl.userName)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 添加地址
 * @param address 详细地址
 * @param area 区
 * @param city 城市
 * @param isDefault 是否默认1默认0非默认
 * @param phoneNum 收货人手机号
 * @param pro 省份
 * @param userName 收货人姓名
 */
public static void saveAddress(String address,String area,String city,int isDefault,String phoneNum,String pro,String userName,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/car/saveAddress","/api/car/saveAddress")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("address", address)
.params("area", area)
.params("city", city)
.params("isDefault", isDefault)
.params("phoneNum", phoneNum)
.params("pro", pro)
.params("userName", userName)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 购物车确认订单接口
 * @param addressId 收货地址id
 * @param shopCarDTOS shopCarDTOS
 */
public static void purchaseGoods(long addressId,List<com.kalacheng.busshop.model.ShopCarAskDTO>  shopCarDTOS,HttpApiCallBack<com.kalacheng.busshop.model.ShopParentOrder> callback)
{
    String shopCarDTOS_json =JSON.toJSONString(shopCarDTOS);
    String strUrl="/api/car/purchaseGoods?";
    strUrl+="_uid_="+HttpClient.getUid();
    strUrl+="&_token_="+HttpClient.getToken();
    strUrl+="&_OS_="+HttpClient.urlEncode(HttpClient.getOSType());
    strUrl+="&_OSV_="+HttpClient.urlEncode(HttpClient.getOSVersion());
    strUrl+="&_OSInfo_="+HttpClient.urlEncode(HttpClient.getOSInfo());
try {

strUrl+="&addressId="+URLEncoder.encode(""+addressId, "UTF-8");
}catch (UnsupportedEncodingException e)
{

}
HttpClient.getInstance().postJson(strUrl,shopCarDTOS_json,"/api/car/purchaseGoods")
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopParentOrder_Ret.class));

}





/**
 * 收货地址列表
 */
public static void getShopAddrList(HttpApiCallBackArr<com.kalacheng.busshop.model.ShopAddress> callback)
{
HttpClient.getInstance().post("/api/car/getShopAddrList","/api/car/getShopAddrList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busshop.model.ShopAddress_RetArr.class));

}





/**
 * 修改购物车
 * @param composeId 商品属性组合id(skuId)
 * @param goodsNum 商品数量
 * @param shopCarId 购物车id
 */
public static void updateShopCar(com.kalacheng.busshop.model_fun.ShopCar_updateShopCar _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/car/updateShopCar","/api/car/updateShopCar")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("composeId", _mdl.composeId)
.params("goodsNum", _mdl.goodsNum)
.params("shopCarId", _mdl.shopCarId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 修改购物车
 * @param composeId 商品属性组合id(skuId)
 * @param goodsNum 商品数量
 * @param shopCarId 购物车id
 */
public static void updateShopCar(long composeId,int goodsNum,long shopCarId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/car/updateShopCar","/api/car/updateShopCar")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("composeId", composeId)
.params("goodsNum", goodsNum)
.params("shopCarId", shopCarId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}


}
