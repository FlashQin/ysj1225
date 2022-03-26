package com.kalacheng.libuser.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 首页接口
 */
public class HttpApiHome
{




/**
 * 首页列表数据
 * @param address 地址
 * @param channelId 频道ID(全部传-1)
 * @param hotSortId 热门类型ID(没有传-1)
 * @param isRecommend 是否推荐 -1:全部 0:不推荐 1:已推荐
 * @param liveFunction 是否有直播购 -1:全部 0:没有 1:有
 * @param liveType 类型 1:直播 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param sex 性别
 * @param tabIds 标签ID数组， 逗号隔开
 */
public static void getHomeDataList(com.kalacheng.libuser.model_fun.Home_getHomeDataList _mdl,HttpApiCallBackArr<com.kalacheng.buscommon.AppHomeHallDTO> callback)
{
HttpClient.getInstance().post("/api/home/getHomeDataList","/api/home/getHomeDataList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("address", _mdl.address)
.params("channelId", _mdl.channelId)
.params("hotSortId", _mdl.hotSortId)
.params("isRecommend", _mdl.isRecommend)
.params("liveFunction", _mdl.liveFunction)
.params("liveType", _mdl.liveType)
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.params("sex", _mdl.sex)
.params("tabIds", _mdl.tabIds)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.buscommon.AppHomeHallDTO_RetArr.class));

}



/**
 * 首页列表数据
 * @param address 地址
 * @param channelId 频道ID(全部传-1)
 * @param hotSortId 热门类型ID(没有传-1)
 * @param isRecommend 是否推荐 -1:全部 0:不推荐 1:已推荐
 * @param liveFunction 是否有直播购 -1:全部 0:没有 1:有
 * @param liveType 类型 1:直播 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param sex 性别
 * @param tabIds 标签ID数组， 逗号隔开
 */
public static void getHomeDataList(String address,long channelId,long hotSortId,int isRecommend,int liveFunction,int liveType,int pageIndex,int pageSize,int sex,String tabIds,HttpApiCallBackArr<com.kalacheng.buscommon.AppHomeHallDTO> callback)
{
HttpClient.getInstance().post("/api/home/getHomeDataList","/api/home/getHomeDataList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("address", address)
.params("channelId", channelId)
.params("hotSortId", hotSortId)
.params("isRecommend", isRecommend)
.params("liveFunction", liveFunction)
.params("liveType", liveType)
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.params("sex", sex)
.params("tabIds", tabIds)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.buscommon.AppHomeHallDTO_RetArr.class));

}





/**
 * 获取附近搜索条件
 */
public static void getNearbySearchCondition(HttpApiCallBackArr<com.kalacheng.libuser.model.AppArea> callback)
{
HttpClient.getInstance().post("/api/home/getNearbySearchCondition","/api/home/getNearbySearchCondition")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppArea_RetArr.class));

}





/**
 * 首页数据信息(单个)
 * @param liveType 类型 1:直播 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param roomId 房间id
 */
public static void getHomeDataInfo(com.kalacheng.libuser.model_fun.Home_getHomeDataInfo _mdl,HttpApiCallBack<com.kalacheng.buscommon.AppHomeHallDTO> callback)
{
HttpClient.getInstance().post("/api/home/getHomeDataInfo","/api/home/getHomeDataInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveType", _mdl.liveType)
.params("roomId", _mdl.roomId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.AppHomeHallDTO_Ret.class));

}



/**
 * 首页数据信息(单个)
 * @param liveType 类型 1:直播 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 * @param roomId 房间id
 */
public static void getHomeDataInfo(int liveType,long roomId,HttpApiCallBack<com.kalacheng.buscommon.AppHomeHallDTO> callback)
{
HttpClient.getInstance().post("/api/home/getHomeDataInfo","/api/home/getHomeDataInfo")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liveType", liveType)
.params("roomId", roomId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.buscommon.AppHomeHallDTO_Ret.class));

}





/**
 * 获取首页搜索标签条件
 */
public static void getHomeSearchCondition(HttpApiCallBack<com.kalacheng.libuser.model.SearchConditionDto> callback)
{
HttpClient.getInstance().post("/api/home/getHomeSearchCondition","/api/home/getHomeSearchCondition")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.SearchConditionDto_Ret.class));

}





/**
 * 首页广场直播头部数据
 * @param type 类型 1:直播 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
 */
public static void getHomeSquareLiveHeader(int type,HttpApiCallBack<com.kalacheng.libuser.model.HomeDto> callback)
{
HttpClient.getInstance().post("/api/home/getHomeSquareLiveHeader","/api/home/getHomeSquareLiveHeader")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.HomeDto_Ret.class));

}





/**
 * 获取首页一对一搜索条件
 */
public static void getO2OSearchCondition(HttpApiCallBack<com.kalacheng.libuser.model.SearchConditionDto> callback)
{
HttpClient.getInstance().post("/api/home/getO2OSearchCondition","/api/home/getO2OSearchCondition")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.SearchConditionDto_Ret.class));

}





/**
 * 获取直播频道
 * @param type 类型 1:直播 2:语音 3:1v1 4:电台 5:派对
 */
public static void getLiveChannel(int type,HttpApiCallBackArr<com.kalacheng.libuser.model.AppLiveChannel> callback)
{
HttpClient.getInstance().post("/api/home/getLiveChannel","/api/home/getLiveChannel")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("type", type)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppLiveChannel_RetArr.class));

}





/**
 * 首页列表数据
 * @param address 地址
 * @param channelId 频道ID
 * @param hotSortId 热门类型ID
 * @param lat 纬度
 * @param lng 经度
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param sex 性别
 * @param tabIds 标签ID数组， 逗号隔开
 */
public static void getHomO2ODataList(com.kalacheng.libuser.model_fun.Home_getHomO2ODataList _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.HomeO2OData> callback)
{
HttpClient.getInstance().post("/api/home/getHomO2ODataList","/api/home/getHomO2ODataList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("address", _mdl.address)
.params("channelId", _mdl.channelId)
.params("hotSortId", _mdl.hotSortId)
.params("lat", _mdl.lat)
.params("lng", _mdl.lng)
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.params("sex", _mdl.sex)
.params("tabIds", _mdl.tabIds)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.HomeO2OData_RetArr.class));

}



/**
 * 首页列表数据
 * @param address 地址
 * @param channelId 频道ID
 * @param hotSortId 热门类型ID
 * @param lat 纬度
 * @param lng 经度
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 * @param sex 性别
 * @param tabIds 标签ID数组， 逗号隔开
 */
public static void getHomO2ODataList(String address,long channelId,long hotSortId,double lat,double lng,int pageIndex,int pageSize,int sex,String tabIds,HttpApiCallBackArr<com.kalacheng.libuser.model.HomeO2OData> callback)
{
HttpClient.getInstance().post("/api/home/getHomO2ODataList","/api/home/getHomO2ODataList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("address", address)
.params("channelId", channelId)
.params("hotSortId", hotSortId)
.params("lat", lat)
.params("lng", lng)
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.params("sex", sex)
.params("tabIds", tabIds)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.HomeO2OData_RetArr.class));

}


}
