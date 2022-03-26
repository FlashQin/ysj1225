package com.kalacheng.busvoicelive.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 转盘抽奖
 */
public class HttpApiAppGame
{




/**
 * 开启转盘抽奖
 * @param nums 抽奖次数
 * @param roomId 房间ID
 */
public static void startGame(com.kalacheng.busvoicelive.model_fun.AppGame_startGame _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiDrawGame> callback)
{
HttpClient.getInstance().post("/api/drawGame/startGame","/api/drawGame/startGame")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("nums", _mdl.nums)
.params("roomId", _mdl.roomId)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiDrawGame_RetArr.class));

}



/**
 * 开启转盘抽奖
 * @param nums 抽奖次数
 * @param roomId 房间ID
 */
public static void startGame(int nums,long roomId,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiDrawGame> callback)
{
HttpClient.getInstance().post("/api/drawGame/startGame","/api/drawGame/startGame")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("nums", nums)
.params("roomId", roomId)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiDrawGame_RetArr.class));

}


}
