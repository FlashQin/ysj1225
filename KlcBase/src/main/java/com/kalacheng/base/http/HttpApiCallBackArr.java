package com.kalacheng.base.http;


import java.util.List;

public interface HttpApiCallBackArr<T> {

    void onHttpRet(int code, String msg, List<T> retModel);

}
