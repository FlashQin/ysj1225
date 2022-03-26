package com.kalacheng.buscommon.model;

import com.kalacheng.base.http.HttpRet;





public class AppUserDto_Ret implements HttpRet
{
    public int code;
    public String msg;
    public AppUserDto retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
