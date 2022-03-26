package com.kalacheng.libuser.model;

import com.kalacheng.base.http.HttpRet;





public class AppSearchRecord_Ret implements HttpRet
{
    public int code;
    public String msg;
    public AppSearchRecord retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
