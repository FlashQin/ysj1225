package com.kalacheng.busshop.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 商品相关接口API
 */
public class HttpApiShopGoods
{




/**
 * 商品修改
 * @param categoryId 分类id
 * @param channelId 渠道id
 * @param detailPicture 商品详情图片地址
 * @param favorablePrice 优惠价格
 * @param goodsId 商品id
 * @param goodsName 商品名称
 * @param goodsPicture 商品简介图片地址
 * @param present 商品详情
 * @param price 商品价格
 * @param productLinks 商品链接
 * @param sort 商品排序
 * @param type 渠道类型 1 第三方 2自营
 */
public static void updateGoods(com.kalacheng.busshop.model_fun.ShopGoods_updateGoods _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/goods/updateGoods","/api/goods/updateGoods")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("categoryId", _mdl.categoryId)
.params("channelId", _mdl.channelId)
.params("detailPicture", _mdl.detailPicture)
.params("favorablePrice", _mdl.favorablePrice)
.params("goodsId", _mdl.goodsId)
.params("goodsName", _mdl.goodsName)
.params("goodsPicture", _mdl.goodsPicture)
.params("present", _mdl.present)
.params("price", _mdl.price)
.params("productLinks", _mdl.productLinks)
.params("sort", _mdl.sort)
.params("type", _mdl.type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 商品修改
 * @param categoryId 分类id
 * @param channelId 渠道id
 * @param detailPicture 商品详情图片地址
 * @param favorablePrice 优惠价格
 * @param goodsId 商品id
 * @param goodsName 商品名称
 * @param goodsPicture 商品简介图片地址
 * @param present 商品详情
 * @param price 商品价格
 * @param productLinks 商品链接
 * @param sort 商品排序
 * @param type 渠道类型 1 第三方 2自营
 */
public static void updateGoods(long categoryId,long channelId,String detailPicture,double favorablePrice,long goodsId,String goodsName,String goodsPicture,String present,double price,String productLinks,int sort,int type,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/goods/updateGoods","/api/goods/updateGoods")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("categoryId", categoryId)
.params("channelId", channelId)
.params("detailPicture", detailPicture)
.params("favorablePrice", favorablePrice)
.params("goodsId", goodsId)
.params("goodsName", goodsName)
.params("goodsPicture", goodsPicture)
.params("present", present)
.params("price", price)
.params("productLinks", productLinks)
.params("sort", sort)
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 商品属性添加
 * @param goodsId 商品id
 * @param shopGoodsAttrCompositeEntities shopGoodsAttrCompositeEntities
 */
public static void createAttribute(long goodsId,List<com.kalacheng.busshop.model.ShopGoodsAttrComposite>  shopGoodsAttrCompositeEntities,HttpApiCallBackArr<com.kalacheng.busshop.model.ShopAttrCompose> callback)
{
    String shopGoodsAttrCompositeEntities_json =JSON.toJSONString(shopGoodsAttrCompositeEntities);
    String strUrl="/api/goods/createAttribute?";
    strUrl+="_uid_="+HttpClient.getUid();
    strUrl+="&_token_="+HttpClient.getToken();
    strUrl+="&_OS_="+HttpClient.urlEncode(HttpClient.getOSType());
    strUrl+="&_OSV_="+HttpClient.urlEncode(HttpClient.getOSVersion());
    strUrl+="&_OSInfo_="+HttpClient.urlEncode(HttpClient.getOSInfo());
try {

strUrl+="&goodsId="+URLEncoder.encode(""+goodsId, "UTF-8");
}catch (UnsupportedEncodingException e)
{

}
HttpClient.getInstance().postJson(strUrl,shopGoodsAttrCompositeEntities_json,"/api/goods/createAttribute")
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busshop.model.ShopAttrCompose_RetArr.class));

}





/**
 * 商品渠道
 */
public static void getChannelList(HttpApiCallBackArr<com.kalacheng.busshop.model.ShopGoodsChannel> callback)
{
HttpClient.getInstance().post("/api/goods/getChannelList","/api/goods/getChannelList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busshop.model.ShopGoodsChannel_RetArr.class));

}





/**
 * 直播开播商品列表
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param status 商品状态 全部0,待上架1,已上架2,冻结中3
 */
public static void getLiveGoodsList(com.kalacheng.busshop.model_fun.ShopGoods_getLiveGoodsList _mdl,HttpApiCallBackArr<com.kalacheng.busshop.model.ShopGoodsDTO> callback)
{
HttpClient.getInstance().post("/api/goods/getLiveGoodsList","/api/goods/getLiveGoodsList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.params("status", _mdl.status)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busshop.model.ShopGoodsDTO_RetArr.class));

}



/**
 * 直播开播商品列表
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param status 商品状态 全部0,待上架1,已上架2,冻结中3
 */
public static void getLiveGoodsList(int pageIndex,int pageSize,int status,HttpApiCallBackArr<com.kalacheng.busshop.model.ShopGoodsDTO> callback)
{
HttpClient.getInstance().post("/api/goods/getLiveGoodsList","/api/goods/getLiveGoodsList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.params("status", status)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busshop.model.ShopGoodsDTO_RetArr.class));

}





/**
 * 商品录入
 * @param categoryId 分类id
 * @param channelId 渠道id
 * @param detailPicture 商品详情图片地址
 * @param favorablePrice 优惠价格
 * @param goodsId 商品id
 * @param goodsName 商品名称
 * @param goodsPicture 商品简介图片地址
 * @param present 商品详情
 * @param price 商品价格
 * @param productLinks 商品链接
 * @param type 渠道类型 1 第三方 2自营
 */
public static void creatGoods(com.kalacheng.busshop.model_fun.ShopGoods_creatGoods _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/goods/creatGoods","/api/goods/creatGoods")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("categoryId", _mdl.categoryId)
.params("channelId", _mdl.channelId)
.params("detailPicture", _mdl.detailPicture)
.params("favorablePrice", _mdl.favorablePrice)
.params("goodsId", _mdl.goodsId)
.params("goodsName", _mdl.goodsName)
.params("goodsPicture", _mdl.goodsPicture)
.params("present", _mdl.present)
.params("price", _mdl.price)
.params("productLinks", _mdl.productLinks)
.params("type", _mdl.type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 商品录入
 * @param categoryId 分类id
 * @param channelId 渠道id
 * @param detailPicture 商品详情图片地址
 * @param favorablePrice 优惠价格
 * @param goodsId 商品id
 * @param goodsName 商品名称
 * @param goodsPicture 商品简介图片地址
 * @param present 商品详情
 * @param price 商品价格
 * @param productLinks 商品链接
 * @param type 渠道类型 1 第三方 2自营
 */
public static void creatGoods(long categoryId,long channelId,String detailPicture,double favorablePrice,long goodsId,String goodsName,String goodsPicture,String present,double price,String productLinks,int type,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/goods/creatGoods","/api/goods/creatGoods")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("categoryId", categoryId)
.params("channelId", channelId)
.params("detailPicture", detailPicture)
.params("favorablePrice", favorablePrice)
.params("goodsId", goodsId)
.params("goodsName", goodsName)
.params("goodsPicture", goodsPicture)
.params("present", present)
.params("price", price)
.params("productLinks", productLinks)
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 获取商品属性组合值列表
 * @param goodsId 商品id
 */
public static void getAttrCompose(long goodsId,HttpApiCallBack<com.kalacheng.busshop.model.ShopAttrAndComposeDTO> callback)
{
HttpClient.getInstance().post("/api/goods/getAttrCompose","/api/goods/getAttrCompose")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("goodsId", goodsId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopAttrAndComposeDTO_Ret.class));

}





/**
 * 商品信息
 * @param productId 商品id
 */
public static void getShopGoods(long productId,HttpApiCallBack<com.kalacheng.busshop.model.ShopGoods> callback)
{
HttpClient.getInstance().post("/api/goods/getShopGoods","/api/goods/getShopGoods")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("productId", productId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopGoods_Ret.class));

}





/**
 * 设置讲解中状态
 * @param liveGoodsId 直播商品id
 * @param roomId 房间号
 */
public static void setExplainStatus(com.kalacheng.busshop.model_fun.ShopGoods_setExplainStatus _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/goods/setExplainStatus","/api/goods/setExplainStatus")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveGoodsId", _mdl.liveGoodsId)
.params("roomId", _mdl.roomId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 设置讲解中状态
 * @param liveGoodsId 直播商品id
 * @param roomId 房间号
 */
public static void setExplainStatus(long liveGoodsId,long roomId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/goods/setExplainStatus","/api/goods/setExplainStatus")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveGoodsId", liveGoodsId)
.params("roomId", roomId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 删除商品
 * @param goodsId 商品id
 */
public static void delGoods(long goodsId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/goods/delGoods","/api/goods/delGoods")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("goodsId", goodsId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 商品列表
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param status 商品状态 全部0,待上架1,已上架2,冻结中3
 */
public static void getGoodsList(com.kalacheng.busshop.model_fun.ShopGoods_getGoodsList _mdl,HttpApiCallBackArr<com.kalacheng.busshop.model.ShopGoodsDTO> callback)
{
HttpClient.getInstance().post("/api/goods/getGoodsList","/api/goods/getGoodsList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.params("status", _mdl.status)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busshop.model.ShopGoodsDTO_RetArr.class));

}



/**
 * 商品列表
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param status 商品状态 全部0,待上架1,已上架2,冻结中3
 */
public static void getGoodsList(int pageIndex,int pageSize,int status,HttpApiCallBackArr<com.kalacheng.busshop.model.ShopGoodsDTO> callback)
{
HttpClient.getInstance().post("/api/goods/getGoodsList","/api/goods/getGoodsList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.params("status", status)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busshop.model.ShopGoodsDTO_RetArr.class));

}





/**
 * 添加直播商品
 * @param goodsId 商品id
 * @param sort 排序
 */
public static void saveLiveGoods(com.kalacheng.busshop.model_fun.ShopGoods_saveLiveGoods _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/goods/saveLiveGoods","/api/goods/saveLiveGoods")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("goodsId", _mdl.goodsId)
.params("sort", _mdl.sort)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 添加直播商品
 * @param goodsId 商品id
 * @param sort 排序
 */
public static void saveLiveGoods(long goodsId,int sort,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/goods/saveLiveGoods","/api/goods/saveLiveGoods")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("goodsId", goodsId)
.params("sort", sort)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 商品详情
 * @param goodsId 商品id
 */
public static void getGoodsDetail(long goodsId,HttpApiCallBack<com.kalacheng.busshop.model.ShopGoodsDetailDTO> callback)
{
HttpClient.getInstance().post("/api/goods/getGoodsDetail","/api/goods/getGoodsDetail")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("goodsId", goodsId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopGoodsDetailDTO_Ret.class));

}





/**
 * 商品上下架
 * @param goodsId 商品id
 * @param status 商品状态 1下架 2 上架
 */
public static void upAndLower(com.kalacheng.busshop.model_fun.ShopGoods_upAndLower _mdl,HttpApiCallBack<com.kalacheng.busshop.model.ShopGoods> callback)
{
HttpClient.getInstance().post("/api/goods/upAndLower","/api/goods/upAndLower")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("goodsId", _mdl.goodsId)
.params("status", _mdl.status)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopGoods_Ret.class));

}



/**
 * 商品上下架
 * @param goodsId 商品id
 * @param status 商品状态 1下架 2 上架
 */
public static void upAndLower(long goodsId,int status,HttpApiCallBack<com.kalacheng.busshop.model.ShopGoods> callback)
{
HttpClient.getInstance().post("/api/goods/upAndLower","/api/goods/upAndLower")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("goodsId", goodsId)
.params("status", status)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopGoods_Ret.class));

}





/**
 * 获取商品属性
 * @param goodsId 商品id
 */
public static void getArrDetailList(long goodsId,HttpApiCallBackArr<com.kalacheng.busshop.model.ShopGoodsAttrDTO> callback)
{
HttpClient.getInstance().post("/api/goods/getArrDetailList","/api/goods/getArrDetailList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("goodsId", goodsId)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busshop.model.ShopGoodsAttrDTO_RetArr.class));

}





/**
 * 商品分类
 */
public static void getCategoryList(HttpApiCallBackArr<com.kalacheng.busshop.model.ShopGoodsCategory> callback)
{
HttpClient.getInstance().post("/api/goods/getCategoryList","/api/goods/getCategoryList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busshop.model.ShopGoodsCategory_RetArr.class));

}





/**
 * 修改直播间商品排序
 * @param liveGoodsId 直播商品id
 * @param sort 排序
 */
public static void updateLiveGoodsSort(com.kalacheng.busshop.model_fun.ShopGoods_updateLiveGoodsSort _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/goods/updateLiveGoodsSort","/api/goods/updateLiveGoodsSort")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveGoodsId", _mdl.liveGoodsId)
.params("sort", _mdl.sort)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 修改直播间商品排序
 * @param liveGoodsId 直播商品id
 * @param sort 排序
 */
public static void updateLiveGoodsSort(long liveGoodsId,int sort,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/goods/updateLiveGoodsSort","/api/goods/updateLiveGoodsSort")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveGoodsId", liveGoodsId)
.params("sort", sort)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 设置商品价格与库存
 * @param shopAttrComposes shopAttrComposes
 */
public static void setPriceInventory(List<com.kalacheng.busshop.model.ShopAttrCompose>  shopAttrComposes,HttpApiCallBackArr<com.kalacheng.busshop.model.ShopGoodsAttr> callback)
{
    String shopAttrComposes_json =JSON.toJSONString(shopAttrComposes);
    String strUrl="/api/goods/setPriceInventory?";
    strUrl+="_uid_="+HttpClient.getUid();
    strUrl+="&_token_="+HttpClient.getToken();
    strUrl+="&_OS_="+HttpClient.urlEncode(HttpClient.getOSType());
    strUrl+="&_OSV_="+HttpClient.urlEncode(HttpClient.getOSVersion());
    strUrl+="&_OSInfo_="+HttpClient.urlEncode(HttpClient.getOSInfo());
HttpClient.getInstance().postJson(strUrl,shopAttrComposes_json,"/api/goods/setPriceInventory")
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busshop.model.ShopGoodsAttr_RetArr.class));

}





/**
 * 商品属性修改
 * @param goodsId 商品id
 * @param shopGoodsAttrCompositeEntities shopGoodsAttrCompositeEntities
 */
public static void updateAttribute(long goodsId,List<com.kalacheng.busshop.model.ShopGoodsAttrComposite>  shopGoodsAttrCompositeEntities,HttpApiCallBackArr<com.kalacheng.busshop.model.ShopAttrCompose> callback)
{
    String shopGoodsAttrCompositeEntities_json =JSON.toJSONString(shopGoodsAttrCompositeEntities);
    String strUrl="/api/goods/updateAttribute?";
    strUrl+="_uid_="+HttpClient.getUid();
    strUrl+="&_token_="+HttpClient.getToken();
    strUrl+="&_OS_="+HttpClient.urlEncode(HttpClient.getOSType());
    strUrl+="&_OSV_="+HttpClient.urlEncode(HttpClient.getOSVersion());
    strUrl+="&_OSInfo_="+HttpClient.urlEncode(HttpClient.getOSInfo());
try {

strUrl+="&goodsId="+URLEncoder.encode(""+goodsId, "UTF-8");
}catch (UnsupportedEncodingException e)
{

}
HttpClient.getInstance().postJson(strUrl,shopGoodsAttrCompositeEntities_json,"/api/goods/updateAttribute")
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busshop.model.ShopAttrCompose_RetArr.class));

}





/**
 * 直播间商品列表
 * @param anchorId 主播Id
 */
public static void getLiveGoods(long anchorId,HttpApiCallBack<com.kalacheng.busshop.model.ShopLiveGoodsDTO> callback)
{
HttpClient.getInstance().post("/api/goods/getLiveGoods","/api/goods/getLiveGoods")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busshop.model.ShopLiveGoodsDTO_Ret.class));

}


}
