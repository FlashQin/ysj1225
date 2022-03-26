package com.kalacheng.busgame.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 游戏相关API
 */
public class HttpApiGame
{




/**
 * 游戏抽奖开始
 * @param anchorId 主播id
 * @param gamePriceId 游戏收费对应的id
 * @param type 宝箱类型 1:普通宝箱 2:幸运宝箱
 */
public static void starPlayGame(com.kalacheng.busgame.model_fun.Game_starPlayGame _mdl,HttpApiCallBack<com.kalacheng.busgame.model.GameUserLuckDrawDTO> callback)
{
HttpClient.getInstance().post("/api/game/starPlayGame","/api/game/starPlayGame")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", _mdl.anchorId)
.params("gamePriceId", _mdl.gamePriceId)
.params("type", _mdl.type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busgame.model.GameUserLuckDrawDTO_Ret.class));

}



/**
 * 游戏抽奖开始
 * @param anchorId 主播id
 * @param gamePriceId 游戏收费对应的id
 * @param type 宝箱类型 1:普通宝箱 2:幸运宝箱
 */
public static void starPlayGame(long anchorId,long gamePriceId,int type,HttpApiCallBack<com.kalacheng.busgame.model.GameUserLuckDrawDTO> callback)
{
HttpClient.getInstance().post("/api/game/starPlayGame","/api/game/starPlayGame")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorId", anchorId)
.params("gamePriceId", gamePriceId)
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busgame.model.GameUserLuckDrawDTO_Ret.class));

}





/**
 * 我的获奖记录
 * @param gameKindId 游戏id -1查所有,其它传对应的id
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 */
public static void getUserPrizeList(com.kalacheng.busgame.model_fun.Game_getUserPrizeList _mdl,HttpApiCallBackArr<com.kalacheng.busgame.model.GameUserPrizeDTO> callback)
{
HttpClient.getInstance().post("/api/game/getUserPrizeList","/api/game/getUserPrizeList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("gameKindId", _mdl.gameKindId)
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busgame.model.GameUserPrizeDTO_RetArr.class));

}



/**
 * 我的获奖记录
 * @param gameKindId 游戏id -1查所有,其它传对应的id
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 */
public static void getUserPrizeList(long gameKindId,int pageIndex,int pageSize,HttpApiCallBackArr<com.kalacheng.busgame.model.GameUserPrizeDTO> callback)
{
HttpClient.getInstance().post("/api/game/getUserPrizeList","/api/game/getUserPrizeList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("gameKindId", gameKindId)
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busgame.model.GameUserPrizeDTO_RetArr.class));

}





/**
 * 获取最新十条中奖信息
 * @param gameKindId 游戏id -1查所有,其它传对应的id
 */
public static void getUserPrizeRecordList(long gameKindId,HttpApiCallBackArr<com.kalacheng.busgame.model.GameAwardsDTO> callback)
{
HttpClient.getInstance().post("/api/game/getUserPrizeRecordList","/api/game/getUserPrizeRecordList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("gameKindId", gameKindId)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busgame.model.GameAwardsDTO_RetArr.class));

}





/**
 * 获取游戏奖项以及收费标准
 * @param gameKindId 游戏id -1查所有,其它传对应的id
 */
public static void getGamePriceAndAwardsList(long gameKindId,HttpApiCallBack<com.kalacheng.busgame.model.GameAwardsAndPriceDTO> callback)
{
HttpClient.getInstance().post("/api/game/getGamePriceAndAwardsList","/api/game/getGamePriceAndAwardsList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("gameKindId", gameKindId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busgame.model.GameAwardsAndPriceDTO_Ret.class));

}





/**
 * 获取游戏说明
 * @param gameKindId 游戏id
 */
public static void getGameKind(long gameKindId,HttpApiCallBack<com.kalacheng.busgame.model.GameKind> callback)
{
HttpClient.getInstance().post("/api/game/getGameKind","/api/game/getGameKind")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("gameKindId", gameKindId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.busgame.model.GameKind_Ret.class));

}





/**
 * 我的抽奖记录
 * @param gameKindId 游戏id -1查所有,其它传对应的id
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 */
public static void getUserLuckDrawList(com.kalacheng.busgame.model_fun.Game_getUserLuckDrawList _mdl,HttpApiCallBackArr<com.kalacheng.busgame.model.GameLuckDraw> callback)
{
HttpClient.getInstance().post("/api/game/getUserLuckDrawList","/api/game/getUserLuckDrawList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("gameKindId", _mdl.gameKindId)
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busgame.model.GameLuckDraw_RetArr.class));

}



/**
 * 我的抽奖记录
 * @param gameKindId 游戏id -1查所有,其它传对应的id
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 */
public static void getUserLuckDrawList(long gameKindId,int pageIndex,int pageSize,HttpApiCallBackArr<com.kalacheng.busgame.model.GameLuckDraw> callback)
{
HttpClient.getInstance().post("/api/game/getUserLuckDrawList","/api/game/getUserLuckDrawList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("gameKindId", gameKindId)
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.busgame.model.GameLuckDraw_RetArr.class));

}


}
