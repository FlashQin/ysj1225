package com.kalacheng.busooolive.model;

import com.kalacheng.base.http.HttpRet;





public class OOOVolumeRet_Ret implements HttpRet
{
    public int code;
    public String msg;
    public OOOVolumeRet retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
