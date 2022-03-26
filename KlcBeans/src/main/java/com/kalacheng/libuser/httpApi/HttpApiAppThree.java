package com.kalacheng.libuser.httpApi;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.http.*;

import java.util.List;

import java.net.URLEncoder;

import java.io.UnsupportedEncodingException;




/**
 * 第三方操作接口
 */
public class HttpApiAppThree
{




/**
 * 轩嗵云token提供给app端,文件后缀前端拼接,由于a、上传到轩嗵云的文件必须按照后端统一的命名规则命名；b、后端会定期清理轩嗵云的文件。参数需要区分应用场景已list参数传入,拿到的filePath需要拼接文件路径,和token一并传入轩嗵云 参数案例'[{'scene':1},{'scene':1},{'scene':1}]'
 * @param apiFileUploadParams apiFileUploadParams
 */
public static void getFileUploadToken(List<com.kalacheng.libuser.model.ApiFileUploadParams>  apiFileUploadParams,HttpApiCallBack<com.kalacheng.libuser.model.ApiFileUpload> callback)
{
    String apiFileUploadParams_json =JSON.toJSONString(apiFileUploadParams);
    String strUrl="/api/three/getFileUploadToken?";
    strUrl+="_uid_="+HttpClient.getUid();
    strUrl+="&_token_="+HttpClient.getToken();
    strUrl+="&_OS_="+HttpClient.urlEncode(HttpClient.getOSType());
    strUrl+="&_OSV_="+HttpClient.urlEncode(HttpClient.getOSVersion());
    strUrl+="&_OSInfo_="+HttpClient.urlEncode(HttpClient.getOSInfo());
HttpClient.getInstance().postJson(strUrl,apiFileUploadParams_json,"/api/three/getFileUploadToken")
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiFileUpload_Ret.class));

}





/**
 * 轩嗵云token提供给app端,文件后缀前端拼接,由于a、上传到轩嗵云的文件必须按照后端统一的命名规则命名；b、后端会定期清理轩嗵云的文件。参数需要区分应用场景已list参数传入,拿到的filePath需要拼接文件路径,和token一并传入轩嗵云 参数案例'[{'scene':1},{'scene':1},{'scene':1}]'
 * @param apiFileUploadParams apiFileUploadParams
 */
public static void getUploadInfo(List<com.kalacheng.libuser.model.ApiFileUploadParams>  apiFileUploadParams,HttpApiCallBack<com.kalacheng.libuser.model.ApiFileUpload> callback)
{
    String apiFileUploadParams_json =JSON.toJSONString(apiFileUploadParams);
    String strUrl="/api/three/getUploadInfo?";
    strUrl+="_uid_="+HttpClient.getUid();
    strUrl+="&_token_="+HttpClient.getToken();
    strUrl+="&_OS_="+HttpClient.urlEncode(HttpClient.getOSType());
    strUrl+="&_OSV_="+HttpClient.urlEncode(HttpClient.getOSVersion());
    strUrl+="&_OSInfo_="+HttpClient.urlEncode(HttpClient.getOSInfo());
HttpClient.getInstance().postJson(strUrl,apiFileUploadParams_json,"/api/three/getUploadInfo")
.execute(new HttpApiCallBackConvert(callback,com.kalacheng.libuser.model.ApiFileUpload_Ret.class));

}


}
