package com.kalacheng.busooolive.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 一对一直播间Http接口
 */
public class HttpApiOOOLive
{




/**
 * 打开一对一房间
 */
public static void openRoom(HttpApiCallBack<com.kalacheng.libuser.model.AppJoinRoomVO> callback)
{
HttpClient.getInstance().post("/api/OOOLive/openRoom","/api/OOOLive/openRoom")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppJoinRoomVO_Ret.class));

}





/**
 * 用户在前段点击遇见按钮显示的页面数据,单人和多人都是这个接口
 * @param lat 纬度
 * @param lng 经度
 */
public static void meetUser(com.kalacheng.busooolive.model_fun.OOOLive_meetUser _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiCfgPayCallOneVsOne> callback)
{
HttpClient.getInstance().post("/api/OOOLive/meetUser","/api/OOOLive/meetUser")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("lat", _mdl.lat)
.params("lng", _mdl.lng)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiCfgPayCallOneVsOne_RetArr.class));

}



/**
 * 用户在前段点击遇见按钮显示的页面数据,单人和多人都是这个接口
 * @param lat 纬度
 * @param lng 经度
 */
public static void meetUser(double lat,double lng,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiCfgPayCallOneVsOne> callback)
{
HttpClient.getInstance().post("/api/OOOLive/meetUser","/api/OOOLive/meetUser")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("lat", lat)
.params("lng", lng)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiCfgPayCallOneVsOne_RetArr.class));

}





/**
 * 遇见-规则
 * @param type 类型9遇见规则
 */
public static void meetposts(int type,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/OOOLive/meetposts","/api/OOOLive/meetposts")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 主播端匹配用户
 */
public static void meetAnchor(HttpApiCallBack<com.kalacheng.libuser.model.OOOMeetAnchor> callback)
{
HttpClient.getInstance().post("/api/OOOLive/meetAnchor","/api/OOOLive/meetAnchor")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.OOOMeetAnchor_Ret.class));

}





/**
 * 通话记录
 * @param anchorId 主播ID
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 */
public static void getCallRecordList(com.kalacheng.busooolive.model_fun.OOOLive_getCallRecordList _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.CallRecordDto> callback)
{
HttpClient.getInstance().post("/api/OOOLive/getCallRecordList","/api/OOOLive/getCallRecordList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.CallRecordDto_RetArr.class));

}



/**
 * 通话记录
 * @param anchorId 主播ID
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 */
public static void getCallRecordList(long anchorId,int pageIndex,int pageSize,HttpApiCallBackArr<com.kalacheng.libuser.model.CallRecordDto> callback)
{
HttpClient.getInstance().post("/api/OOOLive/getCallRecordList","/api/OOOLive/getCallRecordList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.CallRecordDto_RetArr.class));

}





/**
 * 一对一连线付费提示
 * @param type 类型1语音2视频
 */
public static void getLineBeforeOOO(int type,HttpApiCallBack<com.kalacheng.libuser.model.ApiLineBeforeOOO> callback)
{
HttpClient.getInstance().post("/api/OOOLive/getLineBeforeOOO","/api/OOOLive/getLineBeforeOOO")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiLineBeforeOOO_Ret.class));

}





/**
 * 用户在前段点击遇见按钮显示的页面数据,单人和多人都是这个接口
 */
public static void exitMeetUser(HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/OOOLive/exitMeetUser","/api/OOOLive/exitMeetUser")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}


}
