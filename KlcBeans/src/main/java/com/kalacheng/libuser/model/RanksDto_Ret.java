package com.kalacheng.libuser.model;

import com.kalacheng.base.http.HttpRet;





public class RanksDto_Ret implements HttpRet
{
    public int code;
    public String msg;
    public RanksDto retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
