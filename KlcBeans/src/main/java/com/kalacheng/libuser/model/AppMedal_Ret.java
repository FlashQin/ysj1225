package com.kalacheng.libuser.model;

import com.kalacheng.base.http.HttpRet;





public class AppMedal_Ret implements HttpRet
{
    public int code;
    public String msg;
    public AppMedal retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
