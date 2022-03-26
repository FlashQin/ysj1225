package com.kalacheng.base.http;

public interface HttpApiCallBack<T> {


     void onHttpRet(int code, String msg, T retModel);


}
