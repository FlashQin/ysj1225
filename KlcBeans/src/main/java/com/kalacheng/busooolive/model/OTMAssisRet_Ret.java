package com.kalacheng.busooolive.model;

import com.kalacheng.base.http.HttpRet;





public class OTMAssisRet_Ret implements HttpRet
{
    public int code;
    public String msg;
    public OTMAssisRet retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
