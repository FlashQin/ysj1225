package com.kalacheng.buspersonalcenter.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 新版在线商城
 */
public class HttpApiH5OnlineMallController
{




/**
 * 我的背包
 */
public static void myPackage(HttpApiCallBack<com.kalacheng.libuser.model.MyPackageDTO> callback)
{
HttpClient.getInstance().post("/api/h5/myPackage","/api/h5/myPackage")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.MyPackageDTO_Ret.class));

}





/**
 * 查询坐骑详情
 * @param carId carId
 * @param ismy ismy
 */
public static void getCarDetail(com.kalacheng.buspersonalcenter.model_fun.H5OnlineMallController_getCarDetail _mdl,HttpApiCallBack<com.kalacheng.libuser.model.MountDetailDTO> callback)
{
HttpClient.getInstance().post("/api/h5/getCarDetail","/api/h5/getCarDetail")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("carId", _mdl.carId)
.params("ismy", _mdl.ismy)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.MountDetailDTO_Ret.class));

}



/**
 * 查询坐骑详情
 * @param carId carId
 * @param ismy ismy
 */
public static void getCarDetail(long carId,int ismy,HttpApiCallBack<com.kalacheng.libuser.model.MountDetailDTO> callback)
{
HttpClient.getInstance().post("/api/h5/getCarDetail","/api/h5/getCarDetail")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("carId", carId)
.params("ismy", ismy)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.MountDetailDTO_Ret.class));

}





/**
 * 购买用户坐骑
 * @param carid carid
 * @param touid touid
 */
public static void toBuyCar(com.kalacheng.buspersonalcenter.model_fun.H5OnlineMallController_toBuyCar _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/h5/toBuyCar","/api/h5/toBuyCar")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("carid", _mdl.carid)
.params("touid", _mdl.touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 购买用户坐骑
 * @param carid carid
 * @param touid touid
 */
public static void toBuyCar(int carid,int touid,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/h5/toBuyCar","/api/h5/toBuyCar")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("carid", carid)
.params("touid", touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 修改用户坐骑状态
 * @param carid carid
 * @param state state
 */
public static void changeCar(com.kalacheng.buspersonalcenter.model_fun.H5OnlineMallController_changeCar _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/h5/changeCar","/api/h5/changeCar")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("carid", _mdl.carid)
.params("state", _mdl.state)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 修改用户坐骑状态
 * @param carid carid
 * @param state state
 */
public static void changeCar(long carid,int state,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/h5/changeCar","/api/h5/changeCar")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("carid", carid)
.params("state", state)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 装扮中心信息
 */
public static void appUserVip(HttpApiCallBack<com.kalacheng.libuser.model.OnlineMallDTO> callback)
{
HttpClient.getInstance().post("/api/h5/appUserVip","/api/h5/appUserVip")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.OnlineMallDTO_Ret.class));

}





/**
 * 贵族中心-开通贵族
 * @param grade 贵族等级
 */
public static void openNoble(int grade,HttpApiCallBack<com.kalacheng.libuser.model.AppOpenNobleDTO> callback)
{
HttpClient.getInstance().post("/api/h5/openNoble","/api/h5/openNoble")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("grade", grade)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppOpenNobleDTO_Ret.class));

}





/**
 * 贵族中心
 * @param grade 贵族等级
 */
public static void nobleCenter(int grade,HttpApiCallBack<com.kalacheng.libuser.model.AppNobleCenterDTO> callback)
{
HttpClient.getInstance().post("/api/h5/nobleCenter","/api/h5/nobleCenter")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("grade", grade)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.AppNobleCenterDTO_Ret.class));

}





/**
 * 查看靓号详情
 * @param ismy ismy
 * @param liangId liangId
 */
public static void getLiangDetail(com.kalacheng.buspersonalcenter.model_fun.H5OnlineMallController_getLiangDetail _mdl,HttpApiCallBack<com.kalacheng.libuser.model.MountDetailDTO> callback)
{
HttpClient.getInstance().post("/api/h5/getLiangDetail","/api/h5/getLiangDetail")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("ismy", _mdl.ismy)
.params("liangId", _mdl.liangId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.MountDetailDTO_Ret.class));

}



/**
 * 查看靓号详情
 * @param ismy ismy
 * @param liangId liangId
 */
public static void getLiangDetail(int ismy,long liangId,HttpApiCallBack<com.kalacheng.libuser.model.MountDetailDTO> callback)
{
HttpClient.getInstance().post("/api/h5/getLiangDetail","/api/h5/getLiangDetail")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("ismy", ismy)
.params("liangId", liangId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.MountDetailDTO_Ret.class));

}





/**
 * 通过uid搜索用户
 * @param uid uid
 */
public static void searchUser(long uid,HttpApiCallBack<com.kalacheng.libuser.model.SimpleUserDTO> callback)
{
HttpClient.getInstance().post("/api/h5/searchUser","/api/h5/searchUser")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("uid", uid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.SimpleUserDTO_Ret.class));

}





/**
 * 金币购买贵族
 * @param grade 贵族等级
 * @param nobleId 贵族价格ID
 */
public static void buyNoble(com.kalacheng.buspersonalcenter.model_fun.H5OnlineMallController_buyNoble _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/h5/buyNoble","/api/h5/buyNoble")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("grade", _mdl.grade)
.params("nobleId", _mdl.nobleId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 金币购买贵族
 * @param grade 贵族等级
 * @param nobleId 贵族价格ID
 */
public static void buyNoble(int grade,long nobleId,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/h5/buyNoble","/api/h5/buyNoble")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("grade", grade)
.params("nobleId", nobleId)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 修改用户靓号状态
 * @param liangid liangid
 * @param state state
 */
public static void changeLiang(com.kalacheng.buspersonalcenter.model_fun.H5OnlineMallController_changeLiang _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/h5/changeLiang","/api/h5/changeLiang")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liangid", _mdl.liangid)
.params("state", _mdl.state)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 修改用户靓号状态
 * @param liangid liangid
 * @param state state
 */
public static void changeLiang(long liangid,int state,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/h5/changeLiang","/api/h5/changeLiang")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liangid", liangid)
.params("state", state)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 购买用户靓号
 * @param liangid liangid
 * @param touid touid
 */
public static void toBuyLiang(com.kalacheng.buspersonalcenter.model_fun.H5OnlineMallController_toBuyLiang _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/h5/toBuyLiang","/api/h5/toBuyLiang")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liangid", _mdl.liangid)
.params("touid", _mdl.touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 购买用户靓号
 * @param liangid liangid
 * @param touid touid
 */
public static void toBuyLiang(long liangid,long touid,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/h5/toBuyLiang","/api/h5/toBuyLiang")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("liangid", liangid)
.params("touid", touid)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}


}
