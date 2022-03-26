package com.kalacheng.libuser.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 主播心愿单接口API
 */
public class HttpApiAnchorWishList
{




/**
 * 获取主播心愿单
 * @param anchorID 主播id
 */
public static void getWishList(long anchorID,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiUsersLiveWish> callback)
{
HttpClient.getInstance().post("/api/live/getWishList","/api/live/getWishList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorID", anchorID)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiUsersLiveWish_RetArr.class));

}





/**
 * 设置主播心愿单,参数对象传入giftid和num即可
 * @param gifts gifts
 * @param roomId 房间号(群聊和直播间时传入否则传-1)
 * @param touid 对方用户id(私聊时传入否则传-1)
 */
public static void setWish(List<com.kalacheng.libuser.model.ApiUsersLiveWish>  gifts,long roomId,long touid,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
    String gifts_json =JSON.toJSONString(gifts);
    String strUrl="/api/live/setWish?";
    strUrl+="_uid_="+HttpClient.getUid();
    strUrl+="&_token_="+HttpClient.getToken();
    strUrl+="&_OS_="+HttpClient.urlEncode(HttpClient.getOSType());
    strUrl+="&_OSV_="+HttpClient.urlEncode(HttpClient.getOSVersion());
    strUrl+="&_OSInfo_="+HttpClient.urlEncode(HttpClient.getOSInfo());
try {

strUrl+="&roomId="+URLEncoder.encode(""+roomId, "UTF-8");
strUrl+="&touid="+URLEncoder.encode(""+touid, "UTF-8");
}catch (UnsupportedEncodingException e)
{

}
HttpClient.getInstance().postJson(strUrl,gifts_json,"/api/live/setWish")
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 获取心愿单礼物列表
 * @param anchorID 主播id
 */
public static void getWishGiftList(long anchorID,HttpApiCallBackArr<com.kalacheng.libuser.model.ApiUsersLiveWish> callback)
{
HttpClient.getInstance().post("/api/live/getWishGiftList","/api/live/getWishGiftList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("anchorID", anchorID)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.ApiUsersLiveWish_RetArr.class));

}


}
