package com.kalacheng.base.http;


import java.util.List;

public interface HttpApiCallBackPageArr<T> {

    void onHttpRet(int code, String msg, PageInfo pageInfo, List<T> retModel);

}
