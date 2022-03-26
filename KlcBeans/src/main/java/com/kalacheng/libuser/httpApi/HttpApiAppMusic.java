package com.kalacheng.libuser.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * app音乐
 */
public class HttpApiAppMusic
{




/**
 * 播放列表
 * @param name 音乐名称
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 */
public static void queryMyMusicList(com.kalacheng.libuser.model_fun.AppMusic_queryMyMusicList _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.AppUserMusicDTO> callback)
{
HttpClient.getInstance().post("/api/music/queryMyMusicList","/api/music/queryMyMusicList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("name", _mdl.name)
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppUserMusicDTO_RetArr.class));

}



/**
 * 播放列表
 * @param name 音乐名称
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 */
public static void queryMyMusicList(String name,int pageIndex,int pageSize,HttpApiCallBackArr<com.kalacheng.libuser.model.AppUserMusicDTO> callback)
{
HttpClient.getInstance().post("/api/music/queryMyMusicList","/api/music/queryMyMusicList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("name", name)
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppUserMusicDTO_RetArr.class));

}





/**
 * 添加/移除音乐
 * @param musicId 音乐id
 * @param type 1添加，2移除
 */
public static void setMusic(com.kalacheng.libuser.model_fun.AppMusic_setMusic _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/music/setMusic","/api/music/setMusic")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("musicId", _mdl.musicId)
.params("type", _mdl.type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 添加/移除音乐
 * @param musicId 音乐id
 * @param type 1添加，2移除
 */
public static void setMusic(long musicId,int type,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/music/setMusic","/api/music/setMusic")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("musicId", musicId)
.params("type", type)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}





/**
 * 音乐库
 * @param name 音乐名称
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 */
public static void queryList(com.kalacheng.libuser.model_fun.AppMusic_queryList _mdl,HttpApiCallBackArr<com.kalacheng.libuser.model.AppMusicDTO> callback)
{
HttpClient.getInstance().post("/api/music/queryList","/api/music/queryList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("name", _mdl.name)
.params("pageIndex", _mdl.pageIndex)
.params("pageSize", _mdl.pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppMusicDTO_RetArr.class));

}



/**
 * 音乐库
 * @param name 音乐名称
 * @param pageIndex 当前页
 * @param pageSize 每页大小
 */
public static void queryList(String name,int pageIndex,int pageSize,HttpApiCallBackArr<com.kalacheng.libuser.model.AppMusicDTO> callback)
{
HttpClient.getInstance().post("/api/music/queryList","/api/music/queryList")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("name", name)
.params("pageIndex", pageIndex)
.params("pageSize", pageSize)
.execute(new HttpApiCallBackArrConvert(callback,com.kalacheng.libuser.model.AppMusicDTO_RetArr.class));

}





/**
 * 上传音乐
 * @param author 歌手名称
 * @param cover 音乐封面
 * @param musicUrl 音乐地址
 * @param name 音乐名称
 */
public static void uploadMusic(com.kalacheng.libuser.model_fun.AppMusic_uploadMusic _mdl,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/music/uploadMusic","/api/music/uploadMusic")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("author", _mdl.author)
.params("cover", _mdl.cover)
.params("musicUrl", _mdl.musicUrl)
.params("name", _mdl.name)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}



/**
 * 上传音乐
 * @param author 歌手名称
 * @param cover 音乐封面
 * @param musicUrl 音乐地址
 * @param name 音乐名称
 */
public static void uploadMusic(String author,String cover,String musicUrl,String name,HttpApiCallBack<com.kalacheng.libbas.model.HttpNone> callback)
{
HttpClient.getInstance().post("/api/music/uploadMusic","/api/music/uploadMusic")
        .params("_uid_", HttpClient.getUid())
        .params("_token_",HttpClient.getToken())
        .params("_OS_",HttpClient.getOSType())
        .params("_OSV_",HttpClient.getOSVersion())
        .params("_OSInfo_",HttpClient.getOSInfo())
.params("author", author)
.params("cover", cover)
.params("musicUrl", musicUrl)
.params("name", name)
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libbas.model.HttpNone_Ret.class));

}


}
