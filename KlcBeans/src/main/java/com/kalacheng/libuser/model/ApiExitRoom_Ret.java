package com.kalacheng.libuser.model;

import com.kalacheng.base.http.HttpRet;





public class ApiExitRoom_Ret implements HttpRet
{
    public int code;
    public String msg;
    public ApiExitRoom retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
