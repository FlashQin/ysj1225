package com.kalacheng.libuser.model;

import com.kalacheng.base.http.HttpRet;





public class ApiCloseLine_Ret implements HttpRet
{
    public int code;
    public String msg;
    public ApiCloseLine retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
