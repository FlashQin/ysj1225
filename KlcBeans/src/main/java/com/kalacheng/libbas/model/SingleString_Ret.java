package com.kalacheng.libbas.model;

import com.kalacheng.base.http.HttpRet;





public class SingleString_Ret implements HttpRet
{
    public int code;
    public String msg;
    public SingleString retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
