package com.kalacheng.busnobility.model;

import com.kalacheng.base.http.HttpRet;





public class ApiNobLiveGift_Ret implements HttpRet
{
    public int code;
    public String msg;
    public ApiNobLiveGift retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
