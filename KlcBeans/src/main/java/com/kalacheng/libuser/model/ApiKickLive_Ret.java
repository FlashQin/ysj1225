package com.kalacheng.libuser.model;

import com.kalacheng.base.http.HttpRet;





public class ApiKickLive_Ret implements HttpRet
{
    public int code;
    public String msg;
    public ApiKickLive retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
